package com.example.s_and_c.DTO.InternshipDTOs;

import com.example.s_and_c.DTO.StudentDTOS.ShortStudentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO to display most of an internship info
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InternshipDTO {
    private long id;
    private String name;
    private LocalDate start_date;
    private LocalDate End_date;
    private LocalDate endFormCompilingDate;
    private LocalDate endSelectionAcceptanceDate;
    private double Salary;
    private List<String> qualification_required;
    private String description;
    private String company_email;
    private List<ShortStudentDTO> appliedStudents;
    private List<ShortStudentDTO> acceptedStudents;
    private List<ShortStudentDTO> selectedStudents;
}
