package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class NoteService {
    private final NotesMapper notesMapper;

    public NoteService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public Notes getNote(Integer noteID) {
        return notesMapper.getNote(noteID);
    }

    public Notes[] getAllNotes(Integer userID) {
        return notesMapper.getAllNotesForUserID(userID);
    }

    public void insertNote(String noteTitle, String noteDescription, Integer userID) {
        Notes note = new Notes(0, noteTitle, noteDescription, userID);
        notesMapper.insertNote(note);
    }

    public void deleteNote(Integer noteID){
        notesMapper.deleteNote(noteID);
    }

    public void updateNote(Integer noteID, String noteTitle, String noteDescription){
        notesMapper.updateNote(noteID, noteTitle, noteDescription);
    }

    public boolean isDuplicate(String noteTitle, Integer userID) {
        String[] allNoteTitles = notesMapper.getAllNoteTitleForUserID(userID);
        return Arrays.asList(allNoteTitles).contains(noteTitle);
    }
}
