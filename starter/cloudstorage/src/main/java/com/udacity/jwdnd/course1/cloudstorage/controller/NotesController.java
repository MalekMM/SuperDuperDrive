package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add-note")
    public String newNote(Authentication authentication, @ModelAttribute("fileForm") FileForm fileForm,
                          @ModelAttribute("noteForm") NoteForm noteForm, @ModelAttribute("credentialForm")
                                      CredentialForm credentialForm,  Model model){
        String noteTitle = noteForm.getNoteTitle();
        String noteDescription = noteForm.getNoteDescription();
        Integer noteID   =  noteForm.getNoteID();
        Integer userID  = getUserID(authentication);
        if (noteID == null){
            noteService.insertNote(noteTitle, noteDescription, userID);
        } else {
            noteService.updateNote(noteID, noteTitle, noteDescription);
        }
        model.addAttribute("notes", noteService.getAllNotes(userID));
        model.addAttribute("result", "success");
        return "tmp";
    }

    @GetMapping("/delete-note/{noteID}")
    public String deleteNote(Authentication authentication, @PathVariable String noteID,
                           @ModelAttribute("noteForm") NoteForm noteForm, Model model) {
        Integer intNoteID   = Integer.valueOf(noteID);
        noteService.deleteNote(intNoteID);
        model.addAttribute("result", "success");
        return "tmp";
    }

}
