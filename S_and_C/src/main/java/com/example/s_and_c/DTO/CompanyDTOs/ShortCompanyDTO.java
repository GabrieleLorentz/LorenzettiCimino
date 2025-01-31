package com.example.s_and_c.DTO.CompanyDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ShortCompanyDTO {
    private String name;
    private String email;
    private String description;
    private Long vat_number;
}
