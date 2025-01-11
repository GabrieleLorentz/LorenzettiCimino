package com.example.s_and_c.Repositories;

import com.example.s_and_c.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByEmail(String email);

    Optional<Student> deleteStudentByEmail(String email);
}
