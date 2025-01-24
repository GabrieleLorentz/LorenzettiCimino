package com.example.s_and_c.DTO.InternshipDTOs;

import com.example.s_and_c.DTO.FormWithStudentsDTO;
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
    private LocalDate start_date;
    private LocalDate End_date;
    private int Salary;
    private String qualification_required;
    private String description;
    private List<ShortStudentDTO> applicants;
    private List<FormWithStudentsDTO> formWithStudents;
    private String companyName;

    public InternshipCompleteDTO(long id, String name, LocalDate start_date, LocalDate end_date,
                                 int salary, String qualification_required, String description, String companyName) {
        this.id = id;
        this.name = name;
        this.start_date = start_date;
        End_date = end_date;
        Salary = salary;
        this.qualification_required = qualification_required;
        this.description = description;
        this.companyName = companyName;
    }
}
