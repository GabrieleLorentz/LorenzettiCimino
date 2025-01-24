package com.example.s_and_c.Service;

import com.example.s_and_c.DTO.AuthDTOs.AuthRequestDTO;
import com.example.s_and_c.DTO.AuthDTOs.RegisterRequestDTO;
import com.example.s_and_c.DTO.AuthDTOs.UserTokenDTO;

public interface AuthorizationService {
    UserTokenDTO registerStudent(RegisterRequestDTO registerRequestDTO);
    UserTokenDTO registerCompany(RegisterRequestDTO request);
    UserTokenDTO authenticate(AuthRequestDTO authRequestDTO);
}
