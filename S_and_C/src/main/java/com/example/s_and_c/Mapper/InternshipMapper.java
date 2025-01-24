package com.example.s_and_c.Mapper;

import com.example.s_and_c.DTO.*;
import com.example.s_and_c.DTO.InternshipDTOs.FormDTO;
import com.example.s_and_c.DTO.InternshipDTOs.InternshipCompleteDTO;
import com.example.s_and_c.DTO.InternshipDTOs.InternshipDTO;
import com.example.s_and_c.DTO.InternshipDTOs.InternshipForStudentsDTO;
import com.example.s_and_c.DTO.StudentDTOS.ShortStudentDTO;
import com.example.s_and_c.DTO.StudentDTOS.StudentDTO;
import com.example.s_and_c.Entities.Company;
import com.example.s_and_c.Entities.Form;
import com.example.s_and_c.Entities.Internship;
import com.example.s_and_c.Entities.Student;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class InternshipMapper {


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
        System.out.println(dto);
        if(dto.getStartDate() == null || dto.getEndDate() == null)
            throw new IllegalArgumentException("Start date and end date cannot be null");
        internship.setStartDate(LocalDate.parse(dto.getStartDate(),
                DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        internship.setEndDate(LocalDate.parse(dto.getEndDate(),
                DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        internship.setQualification_required(dto.getQualification_required());
        internship.setDescription(dto.getDescription());
        internship.setCompany(company);
        List<Form> questions = new ArrayList<>();
        for(String question: dto.getQuestions()){
            questions.add(new Form(question,null,internship));
        }
        internship.setForm(questions);
        company.getInternships().add(internship);

        return internship;
    }

    public static InternshipCompleteDTO maptoInternshipCompleteDTO(Internship internship, List<FormWithStudentsDTO> compiledForms) {

        List<ShortStudentDTO> appliedStudents = new ArrayList<>();
        for(Student student: internship.getAppliedStudents())
            appliedStudents.add(new ShortStudentDTO(student.getEmail(), student.getName(), student.getSurname()));

        return new InternshipCompleteDTO(
                internship.getInternship_id(),
                internship.getName(),
                internship.getStartDate(),
                internship.getEndDate(),
                internship.getSalary(),
                internship.getQualification_required(),
                internship.getDescription(),
                appliedStudents,
                compiledForms,
                internship.getCompany().getName()
        );

    }

    public static InternshipForStudentsDTO maptoInternshipForAppliedStudentsDTO(Internship internship) {
        return new InternshipForStudentsDTO(
                internship.getInternship_id(),
                internship.getName(),
                internship.getStartDate(),
                internship.getEndDate(),
                internship.getSalary(),
                internship.getQualification_required(),
                internship.getDescription(),
                internship.getCompany().getName()
        );
    }

    public static InternshipForStudentsDTO maptoInternshipForStudentsDTO(Internship internship) {
        List<FormDTO> forms = new ArrayList<>();
        for(Form form: internship.getForm()){
            forms.add(FormMapper.mapToFormDTO(form));
        }

        return new InternshipForStudentsDTO(
                internship.getInternship_id(),
                internship.getName(),
                internship.getStartDate(),
                internship.getEndDate(),
                internship.getSalary(),
                internship.getQualification_required(),
                internship.getDescription(),
                internship.getCompany().getName(),
                forms
        );
    }


}
