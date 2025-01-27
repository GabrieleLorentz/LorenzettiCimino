package com.example.s_and_c.DTO.StudentDTOS;

import com.example.s_and_c.DTO.InternshipDTOs.FormDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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




