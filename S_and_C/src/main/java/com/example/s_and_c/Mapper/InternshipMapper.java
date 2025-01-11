package com.example.s_and_c.Mapper;

import com.example.s_and_c.DTO.InternshipDTO;
import com.example.s_and_c.Entities.Internship;

public class InternshipMapper {

    public static InternshipDTO maptoInternshipDTO(Internship internship) {
        return new InternshipDTO(
                internship.getInternship_id(),
                internship.getName(),
                internship.getStart_date(),
                internship.getEnd_date(),
                internship.getSalary(),
                internship.getQualification_required(),
                internship.getDescription(),
                internship.getCompany(),
                internship.getStudentInternships()
        );
    }

    public static Internship maptoInternship(InternshipDTO internshipDTO) {
        return new Internship(
                internshipDTO.getInternship_id(),
                internshipDTO.getName(),
                internshipDTO.getStart_date(),
                internshipDTO.getEnd_date(),
                internshipDTO.getSalary(),
                internshipDTO.getQualification_required(),
                internshipDTO.getDescription(),
                internshipDTO.getCompany(),
                internshipDTO.getStudentInternships()
        );
    }
}
