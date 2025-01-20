package com.example.s_and_c.Service;

import com.example.s_and_c.DTO.CompanyDTO;
import com.example.s_and_c.DTO.UpdatedCompanyDTO;

import java.util.List;

public interface CompanyService {
    CompanyDTO createCompany(CompanyDTO companyDTO);

    CompanyDTO getCompany(String email);

    List<CompanyDTO> getAllCompanies();

    UpdatedCompanyDTO updateCompany(String email, CompanyDTO companyDTO);

    void deleteCompany(String email);

}
