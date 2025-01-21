package com.example.s_and_c.Mapper;

import com.example.s_and_c.DTO.InsertInternshipDTO;
import com.example.s_and_c.DTO.InternshipDTO;
import com.example.s_and_c.Entities.Company;
import com.example.s_and_c.Entities.Internship;
import com.example.s_and_c.Service.CompanyService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InternshipMapper {

    private final CompanyService companyService;

    public static InternshipDTO maptoInternshipDTO(Internship internship) {
        return new InternshipDTO(
                internship.getInternship_id(),
                internship.getName(),
                internship.getStartDate(),
                internship.getEndDate(),
                internship.getSalary(),
                internship.getQualification_required(),
                internship.getDescription(),
                internship.getCompany().getEmail()
        );
    }

    public static Internship maptoInternship(InsertInternshipDTO insertInternshipDTO, Company company) {
        return new Internship(
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
