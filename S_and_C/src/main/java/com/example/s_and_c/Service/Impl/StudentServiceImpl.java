package com.example.s_and_c.Service.Impl;

import com.example.s_and_c.DTO.StudentDTOS.StudentDTO;
import com.example.s_and_c.DTO.StudentDTOS.StudentInternshipDTO;
import com.example.s_and_c.Entities.Student;
import com.example.s_and_c.Exception.ResourceNotFoundException;
import com.example.s_and_c.Mapper.StudentMapper;
import com.example.s_and_c.Repositories.InternshipRepository;
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
    private InternshipRepository internshipRepository;

    @Override
    public StudentInternshipDTO getStudent(String email) {
        Student student = studentRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Student with id " + email + " not found"));
        return StudentMapper.mapToStudentInternshipDTO(student);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(StudentMapper::mapToStudentDTO).collect(Collectors.toList());
    }

    @Override
    public StudentDTO updateStudent(String email,StudentDTO studentDTO) {
        Student student = studentRepository.findByEmail(studentDTO.getEmail()).orElseThrow(()-> new ResourceNotFoundException("Student with id " + email + " not found"));

        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setPassword(studentDTO.getPassword());
        student.setDescription(studentDTO.getDescription());
        student.setSurname(studentDTO.getSurname());

        Student updatedStudent = studentRepository.save(student);
        return StudentMapper.mapToStudentDTO(updatedStudent);
    }

    @Override
    public void deleteStudent(String email) {
        Student student = studentRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Student with id " + email + " not found"));

        studentRepository.deleteStudentByEmail(email);
    }

    private StudentDTO mapInternshipToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setEmail(student.getEmail());
        studentDTO.setName(student.getName());
        studentDTO.setSurname(student.getSurname());
        studentDTO.setDescription(student.getDescription());
        return studentDTO;
    }

}
