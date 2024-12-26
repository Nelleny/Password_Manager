package com.example.passwordmanager_;

public class PasswordItem {
    private String title;
    private String username;
    private String password;

    public PasswordItem(String title, String username, String password) {
        this.title = title;
        this.username = username;
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
