package com.example.s_and_c.Service.Impl;

import com.example.s_and_c.Entities.Company;
import com.example.s_and_c.Entities.Student;
import com.example.s_and_c.Repositories.CompanyRepository;
import com.example.s_and_c.Repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Try to find student
        Student student = studentRepository.findByEmail(email).orElse(null);
        if (student != null && passwordEncoder.matches(password, student.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    email,
                    password,
                    Collections.singleton(new SimpleGrantedAuthority("STUDENT"))
            );
        }

        // Try to find company
        Company company = companyRepository.findByEmail(email).orElse(null);
        if (company != null && passwordEncoder.matches(password, company.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    email,
                    password,
                    Collections.singleton(new SimpleGrantedAuthority("COMPANY"))
            );
        }

        throw new BadCredentialsException("Invalid credentials");
    }
}
