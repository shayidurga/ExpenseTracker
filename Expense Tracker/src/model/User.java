package model;

import java.sql.Timestamp;

public class User {
    private int userID;
    private String fullName;
    private String username;
    private String email;
    private String password;
    private String gender;
    private Timestamp createdAt;

    // Constructor
    public User(int userID, String fullName, String username, String email, String password, String gender, Timestamp createdAt) {
        this.userID = userID;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}

