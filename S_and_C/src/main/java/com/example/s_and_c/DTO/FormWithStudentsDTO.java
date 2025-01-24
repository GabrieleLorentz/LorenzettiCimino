package com.example.s_and_c.DTO;

import com.example.s_and_c.DTO.StudentDTOS.ShortStudentDTO;
import com.example.s_and_c.DTO.StudentDTOS.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormWithStudentsDTO {
    private long formId;
    private String request;
    private String response;
    private ShortStudentDTO student;
}
