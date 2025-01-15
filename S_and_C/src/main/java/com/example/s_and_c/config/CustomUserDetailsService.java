package com.example.s_and_c.config;

import com.example.s_and_c.Entities.Company;
import com.example.s_and_c.Entities.Status.Role;
import com.example.s_and_c.Entities.Student;
import com.example.s_and_c.Repositories.CompanyRepository;
import com.example.s_and_c.Repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Prima cerca tra gli studenti
        Optional<Student> student = studentRepository.findByEmail(email);
        if (student.isPresent()) {
            return student.get();
        }

        // Se non è uno studente, cerca tra le compagnie
        Optional<Company> company = companyRepository.findByEmail(email);
        if (company.isPresent()) {
            return company.get();
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }

    public Role getRole(String email) {
        Optional<Student> student = studentRepository.findByEmail(email);
        if (student.isPresent()) {
            return student.get().getRole();
        }

        // Se non è uno studente, cerca tra le compagnie
        Optional<Company> company = companyRepository.findByEmail(email);
        if (company.isPresent()) {
            return company.get().getRole();
        }
        throw new UsernameNotFoundException("User not found with email: " + email);
    }

}
