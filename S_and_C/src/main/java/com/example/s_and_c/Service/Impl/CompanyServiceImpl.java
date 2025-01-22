package com.example.s_and_c.Service.Impl;

import com.example.s_and_c.DTO.AuthRequestDTO;
import com.example.s_and_c.DTO.CompanyDTO;
import com.example.s_and_c.DTO.UpdatedCompanyDTO;
import com.example.s_and_c.DTO.UserTokenDTO;
import com.example.s_and_c.Entities.Company;
import com.example.s_and_c.Entities.Internship;
import com.example.s_and_c.Exception.ResourceNotFoundException;
import com.example.s_and_c.Mapper.CompanyMapper;
import com.example.s_and_c.Repositories.CompanyRepository;
import com.example.s_and_c.Repositories.InternshipRepository;
import com.example.s_and_c.Service.CompanyService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final PasswordEncoder passwordEncoder;
    private final InternshipRepository internshipRepository;
    private CompanyRepository companyRepository;
    private final AuthService authService;

    @Override
    public CompanyDTO createCompany(CompanyDTO companyDTO) {

        Company company = CompanyMapper.mapToCompany(companyDTO);
        Company savedCompany = companyRepository.save(company);

        return CompanyMapper.mapToCompanyDTO(savedCompany);
    }

    @Override
    public CompanyDTO getCompany(String email) {
        Company company = companyRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Company with id " + email + " not found"));
        return CompanyMapper.mapToCompanyDTO(company);
    }

    @Override
    public List<CompanyDTO> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream().map(CompanyMapper::mapToCompanyDTO).collect(Collectors.toList());
    }

    @Override
    public UpdatedCompanyDTO updateCompany(String email, @NotNull CompanyDTO companyDTO) {
        Company company = companyRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Company with email " + email + " not found"));
        List<Internship> internships = internshipRepository.findByCompany(company);

        try {
            if (!companyDTO.getEmail().equals(company.getEmail())) {
                    Company newCompany = new Company();
                newCompany.setEmail(companyDTO.getEmail());
                newCompany.setName(companyDTO.getName());
                newCompany.setVat_number(companyDTO.getVat_number());
                newCompany.setDescription(companyDTO.getDescription());
                String rawPassword = companyDTO.getPassword();
                newCompany.setPassword(passwordEncoder.encode(rawPassword));

                companyRepository.save(newCompany);

                for (Internship internship : internships) {
                    internship.setCompany(newCompany);
                    internshipRepository.save(internship);
                }
                companyRepository.delete(company);
                UserTokenDTO user = authService.authenticate(
                        new AuthRequestDTO(newCompany.getEmail(), rawPassword)
                );

                String token = user.getToken();
                return CompanyMapper.mapToUpdatedCompanyDTO(newCompany, token);
            }

            // Aggiornamento campi standard
            if (!companyDTO.getName().equals(company.getName())) {
                company.setName(companyDTO.getName());
            }
            if (!companyDTO.getVat_number().equals(company.getVat_number())) {
                company.setVat_number(companyDTO.getVat_number());
            }
            if (!companyDTO.getDescription().equals(company.getDescription())) {
                company.setDescription(companyDTO.getDescription());
            }

            if (!passwordEncoder.matches(companyDTO.getPassword(), company.getPassword())) {
                String rawPassword = companyDTO.getPassword();
                company.setPassword(passwordEncoder.encode(rawPassword));

                UserTokenDTO user = authService.authenticate(
                        new AuthRequestDTO(company.getEmail(), rawPassword)
                );
                String token = user.getToken();
                return CompanyMapper.mapToUpdatedCompanyDTO(companyRepository.save(company), token);
            }

            return CompanyMapper.mapToUpdatedCompanyDTO(companyRepository.save(company));

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Inserted Data violate constraint");
        }
    }




    @Override
    public void deleteCompany(String email){
        Company company = companyRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Company with id " + email + " not found"));

        companyRepository.deleteCompanyByEmail(email);
    }

}
