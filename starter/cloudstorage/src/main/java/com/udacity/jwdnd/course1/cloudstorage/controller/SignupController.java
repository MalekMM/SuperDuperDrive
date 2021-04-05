package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Messages;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    private final String userExists = Messages.userExists;
    private final String signupErrorMsg = Messages.signupErrorMsg;
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupView() {
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute Users user, Model model) {
        boolean signupError = false;

        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = true;
            model.addAttribute("signupError", signupError);
            model.addAttribute("signupErrorMessage", userExists);
        }

        if (signupError == false) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signupError = true;
                model.addAttribute("signupError", signupError);
                model.addAttribute("signupErrorMessage", signupErrorMsg);
            }
        }

        if (signupError == false) {
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", signupError);
        }

        return "signup";
    }

}
