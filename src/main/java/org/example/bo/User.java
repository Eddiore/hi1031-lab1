package org.example.bo;

import org.example.bo.enums.UserRole;

public class User {
    private String username;
    private String passwordHash;
    private UserRole role;
    private String id;

    public User(String username, String passwordHash, UserRole role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public User(String username, String passwordHash, UserRole role, String id) {
        this(username, passwordHash, role);
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }
}
