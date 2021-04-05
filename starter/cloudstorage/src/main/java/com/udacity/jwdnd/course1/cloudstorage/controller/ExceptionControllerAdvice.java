package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Messages;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String exceptionHandler(MaxUploadSizeExceededException e, Model model) {
        model.addAttribute("message", Messages.bigFile);
        model.addAttribute("result", "error");
        return "result";
    }
}
