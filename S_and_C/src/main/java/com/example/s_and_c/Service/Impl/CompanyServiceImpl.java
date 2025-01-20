package com.example.s_and_c.Service.Impl;

import com.example.s_and_c.DTO.AuthRequestDTO;
import com.example.s_and_c.DTO.CompanyDTO;
import com.example.s_and_c.DTO.UpdatedCompanyDTO;
import com.example.s_and_c.DTO.UserTokenDTO;
import com.example.s_and_c.Entities.Company;
import com.example.s_and_c.Exception.ResourceNotFoundException;
import com.example.s_and_c.Mapper.CompanyMapper;
import com.example.s_and_c.Repositories.CompanyRepository;
import com.example.s_and_c.Service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final PasswordEncoder passwordEncoder;
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
    public UpdatedCompanyDTO updateCompany(String email, CompanyDTO companyDTO) {
        Company company = companyRepository.findByEmail(companyDTO.getEmail()).orElseThrow(()-> new ResourceNotFoundException("Company with id " + email + " not found"));

        Company newEmailNotExists = companyRepository.findByEmail(companyDTO.getEmail()).orElse(null);
        if (newEmailNotExists != null && newEmailNotExists.getEmail().equals(company.getEmail())) {
            if (!companyDTO.getEmail().equals(company.getEmail()) || !company.getPassword().equals(passwordEncoder.encode(companyDTO.getPassword()))) {
                company.setName(companyDTO.getName());
                company.setEmail(companyDTO.getEmail());
                company.setPassword(companyDTO.getPassword());
                company.setDescription(companyDTO.getDescription());
                company.setVat_number(companyDTO.getVat_number());
                Company updatedCompany = companyRepository.save(company);
                UserTokenDTO userTokenDTO = authService.authenticate(new AuthRequestDTO(company.getEmail(), companyDTO.getPassword()));
                return CompanyMapper.mapToUpdatedCompanyDTO(updatedCompany, userTokenDTO.getToken());
            }
            else {
                company.setName(companyDTO.getName());
                company.setDescription(companyDTO.getDescription());
                company.setVat_number(companyDTO.getVat_number());
                Company updatedCompany = companyRepository.save(company);
                return CompanyMapper.mapToUpdatedCompanyDTO(updatedCompany);
            }

        }
        else return null;

    }

    @Override
    public void deleteCompany(String email){
        Company company = companyRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Company with id " + email + " not found"));

        companyRepository.deleteCompanyByEmail(email);
    }

}
