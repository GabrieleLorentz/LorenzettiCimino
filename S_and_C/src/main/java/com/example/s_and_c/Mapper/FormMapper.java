package com.example.s_and_c.Mapper;

import com.example.s_and_c.DTO.FormWithStudentsDTO;
import com.example.s_and_c.DTO.InternshipDTOs.FormDTO;
import com.example.s_and_c.DTO.StudentDTOS.ShortStudentDTO;
import com.example.s_and_c.Entities.Form;
import com.example.s_and_c.Entities.Student;

public class FormMapper {
    public static FormDTO mapToFormDTO(Form form) {
        return new FormDTO(
                form.getFormId(),
                form.getRequest(),
                form.getResponse()
        );
    }

    public static FormWithStudentsDTO mapToFormWithStudentDTO(Form form, Student student) {
        return new FormWithStudentsDTO(
                form.getFormId(),
                form.getRequest(),
                form.getRequest(),
                new ShortStudentDTO(student.getEmail(), student.getName(), student.getSurname())
        );
    }
}
