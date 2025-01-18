package com.example.s_and_c.Service;

import com.example.s_and_c.DTO.AuthRequestDTO;
import com.example.s_and_c.DTO.RegisterRequestDTO;
import com.example.s_and_c.DTO.UserTokenDTO;

public interface AuthorizationService {
    public UserTokenDTO registerStudent(RegisterRequestDTO registerRequestDTO);
    public UserTokenDTO registerCompany(RegisterRequestDTO request);
    public UserTokenDTO authenticate(AuthRequestDTO authRequestDTO);
}
