package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.apache.catalina.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Controller
@RequestMapping("credentials")
public class CredentialsController {

    private final CredentialService credentialService;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public CredentialsController(CredentialService credentialService, UserService userService,
                                 EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    public Integer getUserID(Authentication authentication) {
        String username = getUserName(authentication);
        Users user     = userService.getUser(username);
        return user.getUserID();
    }

    public String getUserName(Authentication authentication) {
        return authentication.getName();
    }

    @PostMapping("/add-credential")
    public String newCredential(Authentication authentication, @ModelAttribute("fileForm") FileForm fileForm,
                                @ModelAttribute("noteForm") NoteForm noteForm, @ModelAttribute("credentialForm")
                                            CredentialForm credentialForm, Model model) {
        Integer userID = getUserID(authentication);

        Integer credID = credentialForm.getCredentialID();
        String credUrl = credentialForm.getUrl();
        String credUsername = credentialForm.getUsername();
        String credPassword = credentialForm.getPassword();

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credPassword, encodedKey);

        if (credID == null) {
            credentialService.insertCredential(credUrl, credUsername, encodedKey, encryptedPassword, userID);
        } else {
            credentialService.updateCredential(credID, credUrl, encodedKey, encryptedPassword, credUsername);
        }
        model.addAttribute("credentials", credentialService.getAllCredentials(userID));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("result", "success");

        return "result";
    }

    @GetMapping("/delete-credential/{credentialID}")
    public String deleteCredential(Authentication authentication, @PathVariable Integer credentialID,
                                   @ModelAttribute("credentialForm") CredentialForm credentialForm, Model model) {
        try {
            credentialService.deleteCredential(credentialID);
            model.addAttribute("result", "success");
        } catch (Exception e){
            model.addAttribute("result", "error");
        }
        return "result";
    }
}
