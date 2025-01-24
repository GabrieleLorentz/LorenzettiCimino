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
    private int Salary;
    private String qualification_required;
    private String description;
    private String company_name;
    private List<FormDTO> formToCompile;

    public InternshipForStudentsDTO(long internshipId, String name, LocalDate startDate,
                                    LocalDate endDate, int salary, String qualificationRequired,
                                    String description, String name1) {
    }
}
