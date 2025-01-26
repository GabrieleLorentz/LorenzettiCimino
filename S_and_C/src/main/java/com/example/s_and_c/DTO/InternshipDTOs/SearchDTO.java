package com.example.s_and_c.DTO.InternshipDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    private String keyword;
    private int minSalary;
    private LocalDate minStart;
    private LocalDate maxEnd;
    private String companyName;
}
