package com.example.s_and_c.Repositories;

import com.example.s_and_c.Entities.Form;
import com.example.s_and_c.Entities.Internship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormRepository extends JpaRepository<Form, Integer> {
    List<Form> findByInternship(Internship internship);
}
