package com.example.s_and_c.Repositories;

import com.example.s_and_c.Entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    Optional<Company> findByEmail(String email);

    Optional<Company> deleteCompanyByEmail(String email);
}
