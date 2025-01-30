package com.example.s_and_c.Service.Impl;

import com.example.s_and_c.DTO.AuthDTOs.AuthRequestDTO;
import com.example.s_and_c.DTO.AuthDTOs.RegisterRequestDTO;
import com.example.s_and_c.DTO.AuthDTOs.UserTokenDTO;
import com.example.s_and_c.Entities.Company;
import com.example.s_and_c.Entities.Form;
import com.example.s_and_c.Entities.Status.FormType;
import com.example.s_and_c.Entities.Status.Role;
import com.example.s_and_c.Entities.Student;
import com.example.s_and_c.Repositories.CompanyRepository;
import com.example.s_and_c.Repositories.FormRepository;
import com.example.s_and_c.Repositories.StudentRepository;
import com.example.s_and_c.Service.AuthorizationService;
import com.example.s_and_c.config.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthorizationService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CompanyRepository companyRepository;
    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthenticationManager authenticationManager;
    private final FormRepository formRepository;

    public UserTokenDTO registerStudent(RegisterRequestDTO registerRequestDTO) {

        if (companyRepository.findByEmail(registerRequestDTO.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        var student = new Student();
        student.setName(registerRequestDTO.getName());
        student.setSurname(registerRequestDTO.getSurname());
        student.setEmail(registerRequestDTO.getEmail());
        student.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        student.setDescription(registerRequestDTO.getDescription());
        student.setRole(Role.STUDENT);
        if(registerRequestDTO.getCv().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CV cannot be empty");
        studentRepository.save(student);

        for(String cv_string : registerRequestDTO.getCv()){
            Form form = new Form();
            form.setStudent(student);
            form.setFormType(FormType.CV);
            form.setResponse(cv_string);
            formRepository.save(form);
        }

        var jwtToken = jwtService.generateToken(student);

        return new UserTokenDTO(student.getEmail(),jwtToken, student.getRole().toString());
    }

    public UserTokenDTO registerCompany(RegisterRequestDTO request) {

        if (studentRepository.getStudentByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        Company company = companyRepository.findByEmail(request.getEmail()).orElse(null);
        if(company == null) {
            company = new Company();
            company.setEmail(request.getEmail());
            company.setName(request.getName());
            company.setPassword(passwordEncoder.encode(request.getPassword()));
            company.setVat_number(request.getVat_number());
            company.setDescription(request.getDescription());
            company.setRole(Role.COMPANY);
            companyRepository.save(company);
        }
        else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Company already exists");
            }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var jwtToken = jwtService.generateToken(company);
        return new UserTokenDTO(company.getEmail(),jwtToken, company.getRole().toString());
    }

    public UserTokenDTO authenticate(AuthRequestDTO authRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword())
            );
            System.out.println(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequestDTO.getEmail());
            // Generate JWT token
            String token = jwtService.generateToken(userDetails);

            return new UserTokenDTO(authRequestDTO.getEmail(), token, userDetails.getAuthorities().toString());
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid credentials");
        }

    }
}
