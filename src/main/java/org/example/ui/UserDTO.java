package org.example.ui;

import org.example.bo.enums.UserRole;

public record UserDTO(String username, String passwordHash, UserRole role, String id) {
}
