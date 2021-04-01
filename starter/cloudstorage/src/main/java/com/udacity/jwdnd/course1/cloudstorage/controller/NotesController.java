package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("notes")
public class NotesController {
        private final NoteService noteService;
        private final UserService userService;

    public NotesController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    public Integer getUserID(Authentication authentication) {
        String username = getUserName(authentication);
        Users user     = userService.getUser(username);
        return user.getUserID();
    }

    public String getUserName(Authentication authentication) {
        return authentication.getName();
    }
//
//    @GetMapping
//    public String homeViewTest(Authentication authentication, @ModelAttribute("fileForm") FileForm fileForm,
//            @ModelAttribute("noteForm") NoteForm noteForm, @ModelAttribute("credentialForm") CredentialForm credentialForm,
//            Model model) {
//        Integer userId = getUserID(authentication);
//        model.addAttribute("notes", this.noteService.getAllNotes(userId));
//
//        return "home";
//    }
//
    @PostMapping("/add-note")
    public String newNote(Authentication authentication, @ModelAttribute("fileForm") FileForm fileForm,
                          @ModelAttribute("noteForm") NoteForm noteForm, @ModelAttribute("credentialForm")
                                      CredentialForm credentialForm,  Model model){
        String noteTitle = noteForm.getNoteTitle();
        String noteDescription = noteForm.getNoteDescription();
        Integer userID  = getUserID(authentication);
        noteService.insertNote(noteTitle, noteDescription, userID);
        //model.addAttribute("notes", noteService.getAllNotes(userID));
        model.addAttribute("result", "success");
        return "tmp";
    }

//    @PostMapping("/add-note")
//    public String addNote(Authentication authentication, @ModelAttribute("newFile") FileForm newFile,
//                       @ModelAttribute("newNote") NoteForm newNote, @ModelAttribute("newCredential") CredentialForm newCredential,
//                       Model model){
//        model.addAttribute("result", "success");
//        return "tmp";
//
//    }

}
