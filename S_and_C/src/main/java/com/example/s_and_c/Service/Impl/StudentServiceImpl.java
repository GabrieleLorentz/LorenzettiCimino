package com.example.s_and_c.Service.Impl;

import com.example.s_and_c.DTO.AuthRequestDTO;
import com.example.s_and_c.DTO.StudentDTOS.StudentDTO;
import com.example.s_and_c.DTO.StudentDTOS.StudentInternshipDTO;
import com.example.s_and_c.DTO.UpdatedStudentDTO;
import com.example.s_and_c.DTO.UserTokenDTO;
import com.example.s_and_c.Entities.Internship;
import com.example.s_and_c.Entities.Student;
import com.example.s_and_c.Exception.ResourceNotFoundException;
import com.example.s_and_c.Mapper.StudentMapper;
import com.example.s_and_c.Repositories.InternshipRepository;
import com.example.s_and_c.Repositories.StudentRepository;
import com.example.s_and_c.Service.StudentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
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
    @PersistenceContext
    private EntityManager entityManager;

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

    @Transactional
    @Override
    public UpdatedStudentDTO updateStudent(String email, @NotNull StudentDTO studentDTO) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + email + " not found"));

        try {
            if (!studentDTO.getEmail().equals(student.getEmail())) {
                Student newStudent = new Student();
                newStudent.setEmail(studentDTO.getEmail());
                newStudent.setName(studentDTO.getName());
                newStudent.setSurname(studentDTO.getSurname());
                newStudent.setDescription(studentDTO.getDescription());
                newStudent.setPassword(passwordEncoder.encode(studentDTO.getPassword()));

                entityManager.persist(newStudent);
                entityManager.flush();

                //internshipRepository.updateStudentInInternships(student.getEmail(),newStudent.getEmail());

                entityManager.remove(student);
                entityManager.flush();


                UserTokenDTO user = authService.authenticate(new AuthRequestDTO(newStudent.getEmail(), studentDTO.getPassword()));
                String token = user.getToken();
                return StudentMapper.mapToUpdatedStudentDTO(newStudent, token);
            }

            if (!studentDTO.getName().equals(student.getName())) {
                student.setName(studentDTO.getName());
            }
            if (!studentDTO.getSurname().equals(student.getSurname())) {
                student.setSurname(studentDTO.getSurname());
            }
            if (!studentDTO.getDescription().equals(student.getDescription())) {
                student.setDescription(studentDTO.getDescription());
            }
            String password = passwordEncoder.encode(studentDTO.getPassword());
            if (!student.getPassword().equals(password)) {
                student.setPassword(password);
                UserTokenDTO user = authService.authenticate(new AuthRequestDTO(student.getEmail(), password));
                String token = user.getToken();
                return StudentMapper.mapToUpdatedStudentDTO(studentRepository.save(student), token);
            }
            return StudentMapper.mapToUpdatedStudentDTO(studentRepository.save(student));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Inserted Data violate constraint");
        }
    }

    @Override
    public void deleteStudent(String email) {

        studentRepository.deleteStudentByEmail(email);
    }

    @Override
    public void requestInternship(long internshipId, String authEmail) {
        System.out.println(internshipId);
        Internship internship = internshipRepository.findById((int)internshipId).orElseThrow(()->new RuntimeException("Internship not found"));
        Student student = studentRepository.findByEmail(authEmail).orElseThrow(()->new RuntimeException("Student not found"));
        internship.addStudent(student);
        internshipRepository.save(internship);
    }

    /*private StudentDTO mapInternshipToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setEmail(student.getEmail());
        studentDTO.setName(student.getName());
        studentDTO.setSurname(student.getSurname());
        studentDTO.setDescription(student.getDescription());
        return studentDTO;
    }*/

}
