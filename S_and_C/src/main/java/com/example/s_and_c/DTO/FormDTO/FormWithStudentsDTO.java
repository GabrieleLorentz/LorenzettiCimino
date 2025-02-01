package com.example.s_and_c.DTO.FormDTO;

import com.example.s_and_c.DTO.StudentDTOS.ShortStudentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for form associated with student
 */
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
