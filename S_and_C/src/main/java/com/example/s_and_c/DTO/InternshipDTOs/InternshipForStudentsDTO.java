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
    private long internshipId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate endFormCompilingDate;
    private LocalDate endSelectionAcceptanceDate;
    private int Salary;
    private List<String> qualificationRequired;
    private String description;
    private String company_name;
    private List<FormDTO> formToCompile;
    private Boolean isApplied;
    private Boolean isAccepted;
    private Boolean isSelected;



    public InternshipForStudentsDTO(long internshipId, String name, LocalDate startDate, LocalDate endDate, LocalDate endFormCompilingDate,
                                    LocalDate endSelectionAcceptanceDate, int salary, List<String> qualificationRequired, String description, String name1,Boolean isApplied, boolean isAccepted, boolean isSelected) {
        this.internshipId = internshipId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.endFormCompilingDate = endFormCompilingDate;
        this.endSelectionAcceptanceDate = endSelectionAcceptanceDate;
        this.Salary = salary;
        this.qualificationRequired = qualificationRequired;
        this.description = description;
        this.company_name = name1;
        this.isApplied = isApplied;
        this.isAccepted = isAccepted;
        this.isSelected = isSelected;
    }

}
