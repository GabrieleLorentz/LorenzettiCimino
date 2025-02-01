package com.example.s_and_c.Mapper;

import com.example.s_and_c.DTO.StudentDTOS.StudentDTO;
import com.example.s_and_c.DTO.StudentDTOS.UpdatedStudentDTO;
import com.example.s_and_c.Entities.Form;
import com.example.s_and_c.Entities.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentMapper {

    public static StudentDTO mapToStudentDTO(Student student, List<String> formDTOS) {
        return new StudentDTO(
                student.getName(),
                student.getSurname(),
                student.getEmail(),
                student.getPassword(),
                student.getDescription(),
                formDTOS
        );
    }


    public static UpdatedStudentDTO mapToUpdatedStudentDTO(Student student, List<Form> forms, String token) {
        List<String> cv = new ArrayList<>();
        for(Form form : forms)
            cv.add(form.getResponse());
        return new UpdatedStudentDTO(
                student.getName(),
                student.getSurname(),
                student.getEmail(),
                student.getPassword(),
                student.getDescription(),
                cv,
                token
        );
    }

    public static UpdatedStudentDTO mapToUpdatedStudentDTO(Student student, List<Form> forms) {
        List<String> cv = new ArrayList<>();
        for(Form form : forms)
            cv.add(form.getResponse());
        return new UpdatedStudentDTO(
            student.getName(),
            student.getSurname(),
            student.getEmail(),
            student.getPassword(),
            student.getDescription(),
            cv
    );
    }
}
