package com.udacity.jwdnd.course1.cloudstorage.model;

public class Notes {
    private Integer noteID;
    private String noteTitle;
    private String noteDescription;
    private Integer userID;

    public Notes(Integer noteID, String noteTitle, String noteDescription, int userID) {
        this.noteID = noteID;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userID = userID;
    }

    public Integer getNoteID() {
        return noteID;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setNoteID(Integer noteID) {
        this.noteID = noteID;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }
}
