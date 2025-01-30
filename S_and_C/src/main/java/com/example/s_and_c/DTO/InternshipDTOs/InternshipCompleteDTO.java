package com.example.s_and_c.DTO.InternshipDTOs;

import com.example.s_and_c.DTO.FormDTO.FormWithStudentsDTO;
import com.example.s_and_c.DTO.StudentDTOS.ShortStudentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InternshipCompleteDTO {
    private long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate endFormCompilingDate;
    private LocalDate endSelectionAcceptanceDate;
    private int Salary;
    private List<String> qualification_required;
    private String description;
    private List<ShortStudentDTO> applicants;
    private List<ShortStudentDTO> selected;
    private List<FormWithStudentsDTO> formWithStudents;
    private String companyName;
}
