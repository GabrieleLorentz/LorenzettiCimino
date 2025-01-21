package com.example.s_and_c.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InternshipDTO {
    private long id;
    private String name;
    private LocalDate start_date;
    private LocalDate End_date;
    private int Salary;
    private String qualification_required;
    private String description;
    private String company_email;
}
