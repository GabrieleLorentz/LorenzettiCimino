package com.example.s_and_c.DTO.AuthDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO that is sent to the client after registration to store the token
 */
@Data
@AllArgsConstructor
public class UserTokenDTO {
    private String email;
    private String token;
    private String role;

}
