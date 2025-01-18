package com.example.s_and_c.Mapper;

import com.example.s_and_c.DTO.InsertInternshipDTO;
import com.example.s_and_c.Entities.Company;
import com.example.s_and_c.Entities.Internship;
import com.example.s_and_c.Service.CompanyService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InternshipMapper {

    private final CompanyService companyService;

    public static InsertInternshipDTO maptoInternshipDTO(Internship internship) {
        return new InsertInternshipDTO(
                internship.getInternship_id(),
                internship.getName(),
                internship.getStart_date(),
                internship.getEnd_date(),
                internship.getSalary(),
                internship.getQualification_required(),
                internship.getDescription(),
                internship.getCompany().getEmail()
        );
    }

    public static Internship maptoInternship(InsertInternshipDTO insertInternshipDTO, Company company) {
        return new Internship(
                insertInternshipDTO.getInternship_id(),
                insertInternshipDTO.getName(),
                insertInternshipDTO.getStart_date(),
                insertInternshipDTO.getEnd_date(),
                insertInternshipDTO.getSalary(),
                insertInternshipDTO.getQualification_required(),
                insertInternshipDTO.getDescription(),
                company
        );
    }
}
