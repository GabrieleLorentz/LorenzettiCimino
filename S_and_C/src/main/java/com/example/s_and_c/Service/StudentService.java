package com.example.s_and_c.Service;

import com.example.s_and_c.DTO.StudentDTOS.StudentDTO;
import com.example.s_and_c.DTO.StudentDTOS.StudentInternshipDTO;

import java.util.List;

public interface StudentService {

    StudentInternshipDTO getStudent(String email);

    List<StudentDTO> getAllStudents();

    StudentDTO updateStudent(String email, StudentDTO studentDTO);

    void deleteStudent(String email);
}
