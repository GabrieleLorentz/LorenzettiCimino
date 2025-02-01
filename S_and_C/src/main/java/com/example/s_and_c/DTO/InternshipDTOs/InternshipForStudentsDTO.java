package com.example.s_and_c.DTO.InternshipDTOs;

import com.example.s_and_c.DTO.FormDTO.FormDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO to display all internship info that need to be available for students
 */
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


    /**
     * Constructor withoud forms
     * @param internshipId internshipId
     * @param name name
     * @param startDate start internship date
     * @param endDate end internship date
     * @param endFormCompilingDate deadline for form compiling
     * @param endSelectionAcceptanceDate deadline for acceptance
     * @param salary salary
     * @param qualificationRequired qualification required
     * @param description internship description
     * @param companyName company name
     * @param isApplied flag to signal application
     * @param isAccepted flag to signal acceptation
     * @param isSelected flag to signal selection
     */
    public InternshipForStudentsDTO(long internshipId, String name, LocalDate startDate, LocalDate endDate, LocalDate endFormCompilingDate,
                                    LocalDate endSelectionAcceptanceDate, int salary, List<String> qualificationRequired, String description, String companyName,Boolean isApplied, boolean isAccepted, boolean isSelected) {
        this.internshipId = internshipId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.endFormCompilingDate = endFormCompilingDate;
        this.endSelectionAcceptanceDate = endSelectionAcceptanceDate;
        this.Salary = salary;
        this.qualificationRequired = qualificationRequired;
        this.description = description;
        this.company_name = companyName;
        this.isApplied = isApplied;
        this.isAccepted = isAccepted;
        this.isSelected = isSelected;
    }

}
