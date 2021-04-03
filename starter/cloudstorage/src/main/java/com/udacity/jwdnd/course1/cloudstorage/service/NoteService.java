package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UsersMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private final NotesMapper notesMapper;
    private final UsersMapper usersMapper;

    public NoteService(NotesMapper notesMapper, UsersMapper usersMapper) {
        this.notesMapper = notesMapper;
        this.usersMapper = usersMapper;
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
}
