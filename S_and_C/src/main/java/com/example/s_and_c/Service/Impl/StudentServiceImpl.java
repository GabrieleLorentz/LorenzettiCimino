package com.example.s_and_c.Service.Impl;

import com.example.s_and_c.DTO.AuthRequestDTO;
import com.example.s_and_c.DTO.RegisterRequestDTO;
import com.example.s_and_c.DTO.StudentDTOS.StudentDTO;
import com.example.s_and_c.DTO.StudentDTOS.StudentInternshipDTO;
import com.example.s_and_c.DTO.UpdatedStudentDTO;
import com.example.s_and_c.DTO.UserTokenDTO;
import com.example.s_and_c.Entities.Student;
import com.example.s_and_c.Exception.ResourceNotFoundException;
import com.example.s_and_c.Mapper.StudentMapper;
import com.example.s_and_c.Repositories.InternshipRepository;
import com.example.s_and_c.Repositories.StudentRepository;
import com.example.s_and_c.Service.StudentService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final AuthService authService;
    private StudentRepository studentRepository;
    private InternshipRepository internshipRepository;
    private final PasswordEncoder passwordEncoder;
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
    public UpdatedStudentDTO updateStudent(String email, @NotNull StudentDTO studentDTO) {
        Student student = studentRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Student with id " + email + " not found"));
        System.out.println("qui ci arriva");
        Student newEmailNotExist = studentRepository.findByEmail(studentDTO.getEmail()).orElse(null);
        if (newEmailNotExist!=null && studentDTO.getEmail().equals(newEmailNotExist.getEmail())) {
            System.out.println("qui pure");
            if(!studentDTO.getEmail().equals(student.getEmail()) || !student.getPassword().equals(passwordEncoder.encode(studentDTO.getPassword()))) {
                student.setName(studentDTO.getName());
                student.setEmail(studentDTO.getEmail());
                student.setPassword(passwordEncoder.encode(studentDTO.getPassword()));
                student.setDescription(studentDTO.getDescription());
                student.setSurname(studentDTO.getSurname());
                Student updatedStudent = studentRepository.save(student);
                UserTokenDTO userTokenDTO = authService.authenticate(new AuthRequestDTO(student.getEmail(),studentDTO.getPassword()));

                return StudentMapper.mapToUpdatedStudentDTO(updatedStudent, userTokenDTO.getToken());

            }
            else
            {
                student.setName(studentDTO.getName());
                student.setDescription(studentDTO.getDescription());
                student.setSurname(studentDTO.getSurname());

                Student updatedStudent = studentRepository.save(student);
                return StudentMapper.mapToUpdatedStudentDTO(updatedStudent);
            }

            }
        else
            return null;
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
