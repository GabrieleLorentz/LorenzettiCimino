package com.example.s_and_c.DTO.InternshipDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO to show short internship info
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShortInternshipDTO {
    private String internshipName;
    private String companyName;
    private String companyEmail;
}
