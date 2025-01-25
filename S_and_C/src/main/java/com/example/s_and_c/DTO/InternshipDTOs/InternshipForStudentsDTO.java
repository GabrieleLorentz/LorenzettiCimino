package com.example.s_and_c.DTO.InternshipDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InternshipForStudentsDTO {
    private long internship_id;
    private String name;
    private LocalDate start_date;
    private LocalDate End_date;
    private LocalDate endFormCompilingDate;
    private LocalDate endSelectionAcceptanceDate;
    private int Salary;
    private List<String> qualification_required;
    private String description;
    private String company_name;
    private List<FormDTO> formToCompile;



    public InternshipForStudentsDTO(long internshipId, String name, LocalDate startDate, LocalDate endDate, LocalDate endFormCompilingDate,
                                    LocalDate endSelectionAcceptanceDate, int salary, List<String> qualificationRequired, String description, String name1) {
        this.internship_id = internshipId;
        this.name = name;
        this.start_date = startDate;
        this.End_date = endDate;
        this.endFormCompilingDate = endFormCompilingDate;
        this.endSelectionAcceptanceDate = endSelectionAcceptanceDate;
        this.Salary = salary;
        this.qualification_required = qualificationRequired;
        this.description = description;
        this.company_name = name1;
    }
}
