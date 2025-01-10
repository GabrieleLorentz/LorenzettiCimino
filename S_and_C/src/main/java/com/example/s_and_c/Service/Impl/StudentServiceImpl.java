package com.example.s_and_c.Service.Impl;

import com.example.s_and_c.DTO.StudentDTO;
import com.example.s_and_c.Entities.Student;
import com.example.s_and_c.Mapper.StudentMapper;
import com.example.s_and_c.Repositories.StudentRepository;
import com.example.s_and_c.Service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {

        Student student = StudentMapper.mapToStudent(studentDTO);
        Student savedStudent = studentRepository.save(student);

        return StudentMapper.mapStudent(savedStudent);
    }
}
