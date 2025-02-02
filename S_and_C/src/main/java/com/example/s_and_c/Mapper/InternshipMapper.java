package com.example.s_and_c.Mapper;

import com.example.s_and_c.DTO.FormDTO.FormDTO;
import com.example.s_and_c.DTO.FormDTO.FormWithStudentsDTO;
import com.example.s_and_c.DTO.InternshipDTOs.*;
import com.example.s_and_c.DTO.StudentDTOS.ShortStudentDTO;
import com.example.s_and_c.Entities.*;
import com.example.s_and_c.Entities.Status.FormType;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class InternshipMapper {

    public static @NotNull Internship maptoInternship(InsertInternshipDTO dto, Company company) {
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
        for(String qualification : dto.getQualificationRequired()){
            qualifications.add( new Qualification(qualification, internship));
        }
        internship.setQualification_required(qualifications);
        internship.setSalary(dto.getSalary());
        internship.setDescription(dto.getDescription());
        internship.setCompany(company);
        List<Form> questions = new ArrayList<>();
        if(dto.getQuestions() != null){ //during implementation
            for(String question: dto.getQuestions()){
                questions.add(new Form(question,internship,company, FormType.INTERVIEW));
            }
            internship.setForm(questions);
        }

        company.getInternships().add(internship);

        return internship;
    }

    @Contract("_, _, _, _ -> new")
    public static @NotNull InternshipCompleteDTO maptoInternshipCompleteDTO(@NotNull Internship internship, List<FormWithStudentsDTO> compiledForms,
                                                                            List<ShortStudentDTO> appliedStudents, List<ShortStudentDTO> selectedStudents) {

        List<String> qualifications = new ArrayList<>();
        for(Qualification qualification: internship.getQualification_required())
            qualifications.add(qualification.getQualificationName());

        return new InternshipCompleteDTO(
                internship.getInternshipId(),
                internship.getName(),
                internship.getStartDate(),
                internship.getEndDate(),
                internship.getEndFormCompilingDate(),
                internship.getEndSelectionAcceptanceDate(),
                internship.getSalary(),
                qualifications,
                internship.getDescription(),
                appliedStudents,
                selectedStudents,
                compiledForms,
                internship.getCompany().getName()
        );

    }

    @Contract("_, _, _, _ -> new")
    public static @NotNull InternshipForStudentsDTO maptoInternshipForStudentsDTO(@NotNull Internship internship, Boolean isApplied, boolean isAccepted, boolean isSelected) {
        List<String> qualifications = new ArrayList<>();
        for(Qualification qualification: internship.getQualification_required())
            qualifications.add(qualification.getQualificationName());
        return new InternshipForStudentsDTO(
                internship.getInternshipId(),
                internship.getName(),
                internship.getStartDate(),
                internship.getEndDate(),
                internship.getEndFormCompilingDate(),
                internship.getEndSelectionAcceptanceDate(),
                internship.getSalary(),
                qualifications,
                internship.getDescription(),
                internship.getCompany().getName(),
                internship.getCompany().getEmail(),
                isApplied,
                isAccepted,
                isSelected
        );
    }


    @Contract("_, _, _, _ -> new")
    public static @NotNull InternshipDTO maptoInternshipDTO(@NotNull Internship internship, List<ShortStudentDTO> appliedStudents,
                                                            List<ShortStudentDTO> acceptedStudents, List<ShortStudentDTO> selectedStudents) {

        List<String> qualifications = new ArrayList<>();
        for(Qualification qualification: internship.getQualification_required())
            qualifications.add(qualification.getQualificationName());
        return new InternshipDTO(
                internship.getInternshipId(),
                internship.getName(),
                internship.getStartDate(),
                internship.getEndDate(),
                internship.getEndFormCompilingDate(),
                internship.getEndSelectionAcceptanceDate(),
                internship.getSalary(),
                qualifications,
                internship.getDescription(),
                internship.getCompany().getEmail(),
                appliedStudents,
                acceptedStudents,
                selectedStudents
        );
    }

    @Contract("_, _, _, _, _ -> new")
    public static @NotNull InternshipForStudentsDTO mapToInternshipForAcceptedStudentDTO(Internship internship, @NotNull List<Form> forms, boolean isApplied, Boolean isAccepted, boolean isSelected) {
        List<FormDTO> formDTOList = new ArrayList<>();
        for(Form form: forms){
            formDTOList.add(FormMapper.mapToFormDTO(form));
        }
        List<String> qualifications = new ArrayList<>();
        for(Qualification qualification: internship.getQualification_required())
            qualifications.add(qualification.getQualificationName());
        return new InternshipForStudentsDTO(
                internship.getInternshipId(),
                internship.getName(),
                internship.getStartDate(),
                internship.getEndDate(),
                internship.getEndFormCompilingDate(),
                internship.getEndSelectionAcceptanceDate(),
                internship.getSalary(),
                qualifications,
                internship.getDescription(),
                internship.getCompany().getName(),
                internship.getCompany().getEmail(),
                formDTOList,
                isApplied,
                isAccepted,
                isSelected
        );
    }

}
