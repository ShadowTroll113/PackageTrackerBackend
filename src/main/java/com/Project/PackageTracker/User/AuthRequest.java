package com.Project.PackageTracker.User;

import lombok.Getter;

@Getter
public class AuthRequest {
    // Getters y Setters
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
