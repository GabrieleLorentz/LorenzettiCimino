/*package com.example.s_and_c.DAO;

import com.example.s_and_c.Entities.Student;
import com.example.s_and_c.Repositories.StudentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerResponse;

@Repository
public class UserDAO {

    private final StudentRepository studentRepository;

    public UserDAO(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }
}*/
