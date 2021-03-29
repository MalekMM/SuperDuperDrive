package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteForm {
    private String noteTitle;
    private String noteDescription;
    private Integer noteID;

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public Integer getNoteID() {
        return noteID;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public void setNoteID(Integer noteID) {
        this.noteID = noteID;
    }
}
