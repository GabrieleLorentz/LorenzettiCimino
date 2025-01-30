package com.example.s_and_c.DTO.StudentDTOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ShortStudentDTO {
    private String email;
    private String name;
    private String surname;
    private String description;
    private List<String> cv;
}

