package com.example.s_and_c.Mapper;

import com.example.s_and_c.DTO.*;
import com.example.s_and_c.DTO.InternshipDTOs.FormDTO;
import com.example.s_and_c.DTO.InternshipDTOs.InternshipCompleteDTO;
import com.example.s_and_c.DTO.InternshipDTOs.InternshipDTO;
import com.example.s_and_c.DTO.InternshipDTOs.InternshipForStudentsDTO;
import com.example.s_and_c.DTO.StudentDTOS.ShortStudentDTO;
import com.example.s_and_c.DTO.StudentDTOS.StudentDTO;
import com.example.s_and_c.Entities.*;
import com.example.s_and_c.Entities.Status.FormType;
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
        List<String> qualifications = new ArrayList<>();
        for(Qualification qualification: internship.getQualification_required())
            qualifications.add(qualification.getQualificationName());
        return new InternshipDTO(
                internship.getInternship_id(),
                internship.getName(),
                internship.getStartDate(),
                internship.getEndDate(),
                internship.getEndFormCompilingDate(),
                internship.getEndSelectionAcceptanceDate(),
                internship.getSalary(),
                qualifications,
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
                DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        internship.setEndDate(LocalDate.parse(dto.getEndDate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        if(dto.getEndFormCompilingDate() == null || dto.getEndSelectionAcceptanceDate() == null)
            throw new IllegalArgumentException("End form compilation date cannot be null");
        internship.setEndFormCompilingDate(LocalDate.parse(dto.getEndFormCompilingDate(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        internship.setEndSelectionAcceptanceDate(LocalDate.parse(dto.getEndSelectionAcceptanceDate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        List<Qualification> qualifications = new ArrayList<>();
        for(String qualification : dto.getQualification_required()){
            qualifications.add( new Qualification(qualification, internship));
        }
        internship.setQualification_required(qualifications);
        internship.setSalary(dto.getSalary());
        internship.setDescription(dto.getDescription());
        internship.setCompany(company);
        List<Form> questions = new ArrayList<>();
        if(dto.getQuestions() != null){ //during implementation
            for(String question: dto.getQuestions()){
                questions.add(new Form(question,null,internship, FormType.INTERVIEW));
            }
            internship.setForm(questions);
        }

        company.getInternships().add(internship);

        return internship;
    }

    public static InternshipCompleteDTO maptoInternshipCompleteDTO(Internship internship, List<FormWithStudentsDTO> compiledForms) {

        List<ShortStudentDTO> appliedStudents = new ArrayList<>();
        for(Student student: internship.getAppliedStudents())
            appliedStudents.add(new ShortStudentDTO(student.getEmail(), student.getName(), student.getSurname()));
        List<String> qualifications = new ArrayList<>();
        for(Qualification qualification: internship.getQualification_required())
            qualifications.add(qualification.getQualificationName());
        return new InternshipCompleteDTO(
                internship.getInternship_id(),
                internship.getName(),
                internship.getStartDate(),
                internship.getEndDate(),
                internship.getEndFormCompilingDate(),
                internship.getEndSelectionAcceptanceDate(),
                internship.getSalary(),
                qualifications,
                internship.getDescription(),
                appliedStudents,
                compiledForms,
                internship.getCompany().getName()
        );

    }

    public static InternshipForStudentsDTO mapToInternshipForAppliedStudentsDTO(Internship internship) {
        List<String> qualifications = new ArrayList<>();
        for(Qualification qualification: internship.getQualification_required())
            qualifications.add(qualification.getQualificationName());
        return new InternshipForStudentsDTO(
                internship.getInternship_id(),
                internship.getName(),
                internship.getStartDate(),
                internship.getEndDate(),
                internship.getEndFormCompilingDate(),
                internship.getEndSelectionAcceptanceDate(),
                internship.getSalary(),
                qualifications,
                internship.getDescription(),
                internship.getCompany().getName()
        );
    }

    public static InternshipForStudentsDTO maptoInternshipForStudentsDTO(Internship internship) {
        List<FormDTO> forms = new ArrayList<>();
        for(Form form: internship.getForm()){
            forms.add(FormMapper.mapToFormDTO(form));
        }
        List<String> qualifications = new ArrayList<>();
        for(Qualification qualification: internship.getQualification_required())
            qualifications.add(qualification.getQualificationName());
        return new InternshipForStudentsDTO(
                internship.getInternship_id(),
                internship.getName(),
                internship.getStartDate(),
                internship.getEndDate(),
                internship.getEndFormCompilingDate(),
                internship.getEndSelectionAcceptanceDate(),
                internship.getSalary(),
                qualifications,
                internship.getDescription(),
                internship.getCompany().getName(),
                forms
        );
    }


}
