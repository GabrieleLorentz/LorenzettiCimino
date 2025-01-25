package com.example.s_and_c.Repositories;

import com.example.s_and_c.Entities.Form;
import com.example.s_and_c.Entities.Internship;
import com.example.s_and_c.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FormRepository extends JpaRepository<Form, Integer> {
    List<Form> findByInternship(Internship internship);

    List<Form> findByInternshipAndStudent(Internship internship, Student student);
}
