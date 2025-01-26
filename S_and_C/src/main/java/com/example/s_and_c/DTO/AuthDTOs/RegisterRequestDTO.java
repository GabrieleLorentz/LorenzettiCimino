package com.example.s_and_c.DTO.AuthDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    private String email;
    private String password;
    private String name;
    private String surname;
    private String description;
    private Long vat_number;
    private List<String> cv;
}
