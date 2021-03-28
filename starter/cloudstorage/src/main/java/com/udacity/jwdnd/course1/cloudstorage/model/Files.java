package com.udacity.jwdnd.course1.cloudstorage.model;

public class Files {

    private Integer fileID;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Integer userID;
    private Byte[] fileData;

    public Files(Integer fileID, String fileName, String contentType, String fileSize, Integer userID, Byte[] fileData) {
        this.fileID = fileID;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userID = userID;
        this.fileData = fileData;
    }

    public Integer getFileID() {
        return fileID;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public Integer getUserID() {
        return userID;
    }

    public Byte[] getFileData() {
        return fileData;
    }

    public void setFileID(Integer fileID) {
        this.fileID = fileID;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public void setFileData(Byte[] fileData) {
        this.fileData = fileData;
    }
}
