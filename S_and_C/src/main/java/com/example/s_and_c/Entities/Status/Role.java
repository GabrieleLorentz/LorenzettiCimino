package com.example.s_and_c.Entities.Status;

public enum Role {

    STUDENT,
    COMPANY;
public static Role getRole(String role) {
    return switch (role) {
        case "student" -> Role.STUDENT;
        case "company" -> Role.COMPANY;
        default -> null;
    };
}
}
