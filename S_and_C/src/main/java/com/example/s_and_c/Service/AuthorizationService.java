package com.example.s_and_c.Service;

import com.example.s_and_c.DTO.AuthRequestDTO;
import com.example.s_and_c.DTO.RegisterRequestDTO;
import com.example.s_and_c.DTO.UserTokenDTO;

public interface AuthorizationService {
    UserTokenDTO registerStudent(RegisterRequestDTO registerRequestDTO);
    UserTokenDTO registerCompany(RegisterRequestDTO request);
    UserTokenDTO authenticate(AuthRequestDTO authRequestDTO);
}
