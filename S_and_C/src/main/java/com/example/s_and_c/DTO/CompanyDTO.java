package com.example.s_and_c.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private long company_id;
    private String name;
    private Long vat_number;
    private String email;
    private String password;
    private String description;
}
