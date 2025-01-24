package com.example.s_and_c.DTO.CompanyDTOs;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedCompanyDTO {
    private String name;
    private String email;
    private String password;
    private String description;
    private Long vat_number;
    private String newToken;

    public UpdatedCompanyDTO(String name, String email, String password, String description, Long vat_number) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.description = description;
        this.vat_number = vat_number;
    }
}
