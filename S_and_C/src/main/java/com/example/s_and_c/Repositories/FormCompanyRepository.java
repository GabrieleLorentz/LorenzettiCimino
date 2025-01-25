package com.example.s_and_c.Repositories;

import com.example.s_and_c.Entities.CompanyForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormCompanyRepository extends JpaRepository<CompanyForm, Long> {
}
