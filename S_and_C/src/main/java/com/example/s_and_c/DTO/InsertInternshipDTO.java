package com.example.s_and_c.DTO;


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
    private String endFormCompilingDate;
    private String endSelectionAcceptanceDate;
    private int salary;
    private String qualificationRequired;
    private String description;
    private List<String> questions;

}
