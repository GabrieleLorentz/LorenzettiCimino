package com.example.s_and_c.DTO.CompanyDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO with all the company information, to update data
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private String name;
    private String email;
    private String password;
    private String description;
    private Long vat_number;
}
