package com.example.s_and_c.Service.Impl;

import com.example.s_and_c.DTO.StudentDTO;
import com.example.s_and_c.Entities.Student;
import com.example.s_and_c.Exception.ResourceNotFoundException;
import com.example.s_and_c.Mapper.StudentMapper;
import com.example.s_and_c.Repositories.StudentRepository;
import com.example.s_and_c.Service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {

        Student student = StudentMapper.mapToStudent(studentDTO);
        Student savedStudent = studentRepository.save(student);

        return StudentMapper.mapToStudentDTO(savedStudent);
    }

    @Override
    public StudentDTO getStudent(String email) {
        Student student = studentRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Student with id " + email + " not found"));
        return StudentMapper.mapToStudentDTO(student);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(StudentMapper::mapToStudentDTO).collect(Collectors.toList());
    }

    @Override
    public StudentDTO updateStudent(Long student_id, StudentDTO studentDTO) {
        return null;
    }

}
