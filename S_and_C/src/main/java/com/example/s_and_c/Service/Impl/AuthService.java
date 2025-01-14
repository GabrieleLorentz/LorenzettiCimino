package com.example.s_and_c.Service.Impl;

import com.example.s_and_c.Controller.Auth.AuthRequest;
import com.example.s_and_c.Controller.Auth.AuthenticationResponse;
import com.example.s_and_c.Controller.Auth.RegisterRequest;
import com.example.s_and_c.Entities.Status.Role;
import com.example.s_and_c.Entities.Student;
import com.example.s_and_c.Repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var student = new Student();
        student.setName(registerRequest.getName());
        student.setSurname(registerRequest.getSurname());
        student.setEmail(registerRequest.getEmail());
        student.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        student.setDescription(registerRequest.getDescription());
        student.setRole(Role.STUDENT);  // Imposta il ruolo a STUDENT

        // Salva lo studente nel repository
        studentRepository.save(student);

        // Genera il JWT token per lo studente
        var jwtToken = jwtService.generateToken(student);

        // Restituisci il token nell'AuthenticationResponse
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(), authRequest.getPassword()));
        var student = studentRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var jwtToken = jwtService.generateToken(student);

        // Restituisci il token nell'AuthenticationResponse
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
