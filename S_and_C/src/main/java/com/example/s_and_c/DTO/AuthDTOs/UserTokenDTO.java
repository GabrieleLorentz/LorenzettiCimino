package com.example.s_and_c.DTO.AuthDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTokenDTO {
    private String email;
    private String token;
    private String role;

}
