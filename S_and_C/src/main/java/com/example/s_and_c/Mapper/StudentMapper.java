package com.example.s_and_c.Mapper;

import com.example.s_and_c.DTO.StudentDTO;
import com.example.s_and_c.Entities.Student;

public class StudentMapper {

    public static StudentDTO mapToStudentDTO(Student student) {
        return new StudentDTO(
                student.getName(),
                student.getSurname(),
                student.getEmail(),
                student.getPassword(),
                student.getDescription()
        );
    }
    public static Student mapToStudent(StudentDTO studentDTO){
        return new Student(
                studentDTO.getName(),
                studentDTO.getSurname(),
                studentDTO.getEmail(),
                studentDTO.getPassword(),
                studentDTO.getDescription()
        );
    }
}
