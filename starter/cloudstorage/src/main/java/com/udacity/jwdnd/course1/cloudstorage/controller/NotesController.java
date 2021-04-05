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
        if (noteTitle.length()>20 || noteDescription.length()>1000){
            model.addAttribute("result", "error");
            model.addAttribute("message", Messages.notesLengthExceeded);
        } else if (noteService.isDuplicate(noteTitle, userID)) {
            model.addAttribute("result", "error");
            model.addAttribute("message", Messages.duplicateNotesMessage);
        } else if (noteID == null) {
            noteService.insertNote(noteTitle, noteDescription, userID);
            model.addAttribute("result", "success");
        } else {
            noteService.updateNote(noteID, noteTitle, noteDescription);
            model.addAttribute("result", "success");
        }
        model.addAttribute("notes", noteService.getAllNotes(userID));
        return "result";
    }

    @GetMapping("/delete-note/{noteID}")
    public String deleteNote(Authentication authentication, @PathVariable String noteID,
                           @ModelAttribute("noteForm") NoteForm noteForm, Model model) {
        try {
            Integer intNoteID   = Integer.valueOf(noteID);
            noteService.deleteNote(intNoteID);
            model.addAttribute("result", "success");
        } catch (Exception e) {
            model.addAttribute("message", Messages.errorMessage);
            model.addAttribute("result", "error");
        }
        return "result";
    }

}
