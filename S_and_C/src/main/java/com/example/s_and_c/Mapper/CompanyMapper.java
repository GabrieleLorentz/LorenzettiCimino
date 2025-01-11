package com.example.s_and_c.Mapper;

import com.example.s_and_c.DTO.CompanyDTO;
import com.example.s_and_c.Entities.Company;

public class CompanyMapper {

    public static CompanyDTO mapToCompanyDTO(Company company) {
        return new CompanyDTO(
                company.getName(),
                company.getVat_number(),
                company.getEmail(),
                company.getPassword(),
                company.getDescription()
        );
    }
    public static Company mapToCompany(CompanyDTO companyDTO) {
        return new Company(
                companyDTO.getName(),
                companyDTO.getVat_number(),
                companyDTO.getEmail(),
                companyDTO.getPassword(),
                companyDTO.getDescription()
        );
    }
}
