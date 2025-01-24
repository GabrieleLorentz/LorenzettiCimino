package com.example.s_and_c.DTO;

import com.example.s_and_c.Entities.Form;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsertInternshipDTO {
    private String name;
    private String startDate;
    private String endDate;
    private int Salary;
    private String qualification_required;
    private String description;
    private List<String> Questions;

}
