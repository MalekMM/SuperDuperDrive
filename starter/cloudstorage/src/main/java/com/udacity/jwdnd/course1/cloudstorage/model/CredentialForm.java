package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialForm {
    private String url;
    private String username;
    private Integer credentialID;
    private String password;

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public Integer getCredentialID() {
        return credentialID;
    }

    public String getPassword() {
        return password;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setCredentialID(Integer credentialID) {
        this.credentialID = credentialID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
