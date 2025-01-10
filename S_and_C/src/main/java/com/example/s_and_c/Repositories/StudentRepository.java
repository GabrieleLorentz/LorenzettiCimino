package com.example.s_and_c.Repositories;

import com.example.s_and_c.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
