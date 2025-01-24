package com.example.s_and_c.Mapper;

import com.example.s_and_c.DTO.CompanyDTOs.CompanyDTO;
import com.example.s_and_c.DTO.CompanyDTOs.UpdatedCompanyDTO;
import com.example.s_and_c.Entities.Company;

public class CompanyMapper {

    public static CompanyDTO mapToCompanyDTO(Company company) {
        return new CompanyDTO(
                company.getName(),
                company.getEmail(),
                company.getPassword(),
                company.getDescription(),
                company.getVat_number()
        );
    }
    public static Company mapToCompany(CompanyDTO companyDTO) {
        return new Company(
                companyDTO.getName(),
                companyDTO.getEmail(),
                companyDTO.getPassword(),
                companyDTO.getDescription(),
                companyDTO.getVat_number()
        );
    }

    public static UpdatedCompanyDTO mapToUpdatedCompanyDTO(Company company) {
        return new UpdatedCompanyDTO(
                company.getName(),
                company.getEmail(),
                company.getPassword(),
                company.getDescription(),
                company.getVat_number()
        );
    }

    public static UpdatedCompanyDTO mapToUpdatedCompanyDTO(Company company, String token) {
        return new UpdatedCompanyDTO(
                company.getName(),
                company.getEmail(),
                company.getPassword(),
                company.getDescription(),
                company.getVat_number(),
                token
        );
    }
}
