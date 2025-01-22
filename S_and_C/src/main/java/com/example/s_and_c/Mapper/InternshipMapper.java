package com.example.s_and_c.Mapper;

import com.example.s_and_c.DTO.InsertInternshipDTO;
import com.example.s_and_c.DTO.InternshipDTO;
import com.example.s_and_c.DTO.StudentDTOS.StudentDTO;
import com.example.s_and_c.Entities.Company;
import com.example.s_and_c.Entities.Internship;
import com.example.s_and_c.Entities.Student;
import com.example.s_and_c.Service.CompanyService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class InternshipMapper {

    private final CompanyService companyService;

    public static InternshipDTO maptoInternshipDTO(Internship internship) {
        List<StudentDTO> appliedStudents = new ArrayList<>();
        for(Student student: internship.getAppliedStudents())
            appliedStudents.add(StudentMapper.mapToStudentDTO(student));
        return new InternshipDTO(
                internship.getInternship_id(),
                internship.getName(),
                internship.getStartDate(),
                internship.getEndDate(),
                internship.getSalary(),
                internship.getQualification_required(),
                internship.getDescription(),
                internship.getCompany().getEmail(),
                appliedStudents
        );
    }

    public static Internship maptoInternship(InsertInternshipDTO dto, Company company) {
        Internship internship = new Internship();
        internship.setName(dto.getName());
        internship.setStartDate(dto.getStart_date());
        internship.setEndDate(dto.getEnd_date());
        internship.setSalary(dto.getSalary());
        internship.setQualification_required(dto.getQualification_required());
        internship.setDescription(dto.getDescription());
        internship.setCompany(company);
        company.getInternships().add(internship);

        return internship;
    }
}
