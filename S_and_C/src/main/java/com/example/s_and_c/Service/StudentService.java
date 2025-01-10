package com.example.s_and_c.Service;

import com.example.s_and_c.DTO.StudentDTO;

import java.util.List;

public interface StudentService {
    StudentDTO createStudent(StudentDTO studentDTO);

    StudentDTO getStudent(String email);

    List<StudentDTO> getAllStudents();

    StudentDTO updateStudent(Long student_id, StudentDTO studentDTO);
}
