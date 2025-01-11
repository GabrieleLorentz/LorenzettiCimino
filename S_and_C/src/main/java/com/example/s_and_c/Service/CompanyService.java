package com.example.s_and_c.Service;

import com.example.s_and_c.DTO.CompanyDTO;

import java.util.List;

public interface CompanyService {
    CompanyDTO createCompany(CompanyDTO companyDTO);

    CompanyDTO getCompany(String email);

    List<CompanyDTO> getAllCompanies();

    CompanyDTO updateCompany(String email, CompanyDTO companyDTO);

    void deleteCompany(String email);
}
