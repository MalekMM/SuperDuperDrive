package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Messages;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Controller
@RequestMapping("/files")
public class FilesController {

    private final UserService userService;
    private final FileService fileService;

    public FilesController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    public Integer getUserID(Authentication authentication) {
        String username = getUserName(authentication);
        Users user     = userService.getUser(username);
        return user.getUserID();
    }

    public String getUserName(Authentication authentication) {
        return authentication.getName();
    }


    @PostMapping
    public String uploadFile(Authentication authentication, @ModelAttribute("fileForm") FileForm fileForm,
                             Model model) throws IOException {
        try {
            Integer userID = getUserID(authentication);
            MultipartFile file = fileForm.getFile();
            if (file.isEmpty()) {
                model.addAttribute("message", Messages.emptyFile);
                model.addAttribute("result", "error");
            } else if (fileService.isDuplicate(file.getOriginalFilename(), userID)) {
                model.addAttribute("message", Messages.duplicateFile);
                model.addAttribute("result", "error");
            } else {
                fileService.addFile(file, getUserName(authentication));
                model.addAttribute("result", "success");
            }
            model.addAttribute("files", fileService.getAllFiles(userID));
        } catch (IOException e) {
            model.addAttribute("message", Messages.errorMessage);
            model.addAttribute("result", "error");
        }
        return "result";
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
        String[] files = fileService.getAllFiles(getUserID(authentication));
        model.addAttribute("files", files);
        if (Arrays.asList(files).contains(fileName)) {
            model.addAttribute("result", "notSaved");
        } else {
            model.addAttribute("result", "success");
        }
        return "result";
    }

}
