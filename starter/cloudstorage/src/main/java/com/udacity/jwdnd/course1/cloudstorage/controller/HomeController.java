package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping(value = {"/home", ""})
public class HomeController {

    private final UserService userService;
    private final FileService fileService;

    public HomeController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }


    public Integer getUserID(Authentication authentication) {
        String username = getUserName(authentication);
        Users  user     = userService.getUser(username);
        return user.getUserID();
    }

    public String getUserName(Authentication authentication) {
        return authentication.getName();
    }


    @GetMapping
    public String homeView(Authentication authentication, @ModelAttribute("fileForm") FileForm fileForm,
                           Model model) throws IOException {
        Integer userID          = getUserID(authentication);
        String[] fileListings   = fileService.getAllFiles(userID);
        model.addAttribute("files", fileListings);
        return "home";
    }

    @PostMapping
    public String uploadFile(Authentication authentication, @ModelAttribute("fileForm") FileForm fileForm,
                             Model model) throws IOException {
        Integer userID  = getUserID(authentication);
        String username = getUserName(authentication);
        MultipartFile file  = fileForm.getFile();
        String[] allFiles   = fileService.getAllFiles(userID);
        boolean duplicate   = fileService.isDuplicate(file.getOriginalFilename(), userID);
        model.addAttribute("duplicate", duplicate);
        if (!duplicate) {
            fileService.addFile(file, username);
        }
        model.addAttribute("files", fileService.getAllFiles(userID));
        return "home";
    }

    @GetMapping(
            value = "/get-file/{fileName}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    @ResponseBody
    byte[] getFile(@PathVariable String fileName) {
        return fileService.getFile(fileName).getFileData();
    }



    @GetMapping("/delete-file/{fileName}")
    public String deleteFile(Authentication authentication, @PathVariable String fileName,
                             @ModelAttribute("fileForm") FileForm fileForm,  Model model){
        fileService.delete(fileName);
        model.addAttribute("files", fileService.getAllFiles(getUserID(authentication)));
        return "home";
    }


}
