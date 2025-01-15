package com.example.s_and_c.DTO;

import com.example.s_and_c.Entities.Status.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTokenDTO {
    private String email;
    private String token;
    private Role role;


}
