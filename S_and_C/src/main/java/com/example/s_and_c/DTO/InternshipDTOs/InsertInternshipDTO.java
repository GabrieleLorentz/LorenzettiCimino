package com.example.s_and_c.DTO.InternshipDTOs;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO to get all info needed for a new internship
 */
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
    private List<String> qualificationRequired;
    private String description;
    private List<String> questions;

}
