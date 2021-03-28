package com.udacity.jwdnd.course1.cloudstorage.model;

public class Users {
    private Integer userID;
    private String username;
    private String salt;
    private String password;
    private String firstName;
    private String lastName;

    // Constructor
    public Users(Integer userID, String username, String salt,
                 String password, String firstName, String lastName) {
        this.userID     = userID;
        this.username   = username;
        this.salt       = salt;
        this.password   = password;
        this.firstName  = firstName;
        this.lastName   = lastName;
    }

    // Getters
    public Integer getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getSalt() {
        return salt;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    // Setters
    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
