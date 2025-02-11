package com.example.s_and_c.DTO.InternshipDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO with all the available research filters
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    private String keyword;
    private int minSalary;
    private String minStart;
    private String maxEnd;
}
