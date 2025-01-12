package com.example.s_and_c.Service.Impl;

import com.example.s_and_c.DTO.StudentDTOS.StudentDTO;
import com.example.s_and_c.Entities.Internship;
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

    public StudentDTO createStudent(StudentDTO studentDTO) {
        // Mappare il DTO all'entità
        Student student = new Student();
        student.setEmail(studentDTO.getEmail());
        student.setName(studentDTO.getName());
        student.setSurname(studentDTO.getSurname());
        student.setPassword(studentDTO.getPassword());
        student.setDescription(studentDTO.getDescription());

        // Recupera gli internship dagli ID
        List<Internship> internships = internshipRepository.findAllById(studentDTO.getInternshipIds());
        student.setInternships(internships);

        // Salvataggio dello studente
        Student savedStudent = studentRepository.save(student);

        // Converti l'entità salvata in DTO e restituiscila
        return mapInternshipToDTO(savedStudent);
    }

    private StudentDTO mapInternshipToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setEmail(student.getEmail());
        studentDTO.setName(student.getName());
        studentDTO.setSurname(student.getSurname());
        studentDTO.setDescription(student.getDescription());
        studentDTO.getIn(
                student.getInternships().stream().map(Internship::getId).collect(Collectors.toList())
        );
        return studentDTO;
    }

}
