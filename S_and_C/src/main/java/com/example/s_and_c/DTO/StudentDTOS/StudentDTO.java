package com.example.s_and_c.DTO.StudentDTOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO for student personal data
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class StudentDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String description;
    private List<String> cv;
}




