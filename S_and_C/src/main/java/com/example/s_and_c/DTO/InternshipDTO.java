package com.example.s_and_c.DTO;

import com.example.s_and_c.Entities.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InternshipDTO {
    private long internship_id;
    private String name;
    private Date start_date;
    private Date End_date;
    private int Salary;
    private String qualification_required;
    private String description;
    private Company company;
}
