package com.example.s_and_c.Service.Impl;

import com.example.s_and_c.DTO.AuthRequestDTO;
import com.example.s_and_c.DTO.RegisterRequestDTO;
import com.example.s_and_c.DTO.UserTokenDTO;
import com.example.s_and_c.Entities.Company;
import com.example.s_and_c.Entities.Status.Role;
import com.example.s_and_c.Entities.Student;
import com.example.s_and_c.Repositories.CompanyRepository;
import com.example.s_and_c.Repositories.StudentRepository;
import com.example.s_and_c.config.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CompanyRepository companyRepository;
    private final CustomUserDetailsService userDetailsService;

    public UserTokenDTO registerStudent(RegisterRequestDTO registerRequestDTO) {
        var student = new Student();
        student.setName(registerRequestDTO.getName());
        student.setSurname(registerRequestDTO.getSurname());
        student.setEmail(registerRequestDTO.getEmail());
        student.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        student.setDescription(registerRequestDTO.getDescription());
        student.setRole(Role.STUDENT);  // Imposta il ruolo a STUDENT

        // Salva lo studente nel repository
        studentRepository.save(student);

        // Genera il JWT token per lo studente
        var jwtToken = jwtService.generateToken(student);

        // Restituisci il token nell'AuthenticationResponse
        return new UserTokenDTO(student.getEmail(),jwtToken,student.getRole());
    }

    public UserTokenDTO registerCompany(RegisterRequestDTO request) {
        var company = new Company();
        company.setEmail(request.getEmail());
        company.setName(request.getName());
        company.setPassword(passwordEncoder.encode(request.getPassword()));
        company.setVat_number(request.getVat_number());
        company.setRole(Role.COMPANY);
        companyRepository.save(company);

        System.out.println("Company role set to: " + company.getRole());

        var jwtToken = jwtService.generateToken(company);
        return new UserTokenDTO(company.getEmail(),jwtToken,company.getRole());
    }

    public UserTokenDTO authenticate(AuthRequestDTO authRequestDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid credentials");
        }

        UserDetails user = userDetailsService.loadUserByUsername(authRequestDTO.getEmail());
        var jwtToken = jwtService.generateToken(user);
        System.out.println(" role set to: " + userDetailsService.getRole(user.getUsername()));
        System.out.println(" email set to: " + user.getUsername());
        System.out.println(" token set to: " + jwtToken);

        return new UserTokenDTO(user.getUsername(),jwtToken,userDetailsService.getRole(authRequestDTO.getEmail()));
    }
}
