package com.example.s_and_c.Repositories;

import com.example.s_and_c.Entities.Company;
import com.example.s_and_c.Entities.Internship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternshipRepository extends JpaRepository<Internship, Integer> {
    List<Internship> findByCompany(Company company);
}
