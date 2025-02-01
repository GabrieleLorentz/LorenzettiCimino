package com.example.s_and_c.DTO.CompanyDTOs;

import com.example.s_and_c.DTO.FormDTO.FormCompleteDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Company DTO without private info, to show in public profile controller
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ShortCompanyDTO {
    private String name;
    private String email;
    private String description;
    private Long vat_number;
    private List<FormCompleteDTO> forms;

    /**
     * Constructor without forms
     * @param name company name
     * @param email company email
     * @param description company description
     * @param vat_number company vat number
     */
    public ShortCompanyDTO(String name, String email, String description, Long vat_number) {
        this.name = name;
        this.email = email;
        this.description = description;
        this.vat_number = vat_number;
    }

}
