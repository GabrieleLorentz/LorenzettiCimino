package com.example.s_and_c.config;

import com.example.s_and_c.Entities.Company;
import com.example.s_and_c.Entities.Student;
import com.example.s_and_c.Repositories.CompanyRepository;
import com.example.s_and_c.Repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // First try to find a student
        Student student = studentRepository.findByEmail(email).orElse(null);
        if (student != null) {
            return new User(student.getEmail(), student.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("STUDENT")));
        }

        // If not a student, try to find a company
        Company company = companyRepository.findByEmail(email).orElse(null);
        if (company != null) {
            return new User(company.getEmail(), company.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("COMPANY")));
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }

}
