package com.example.s_and_c.Service.Impl;


import com.example.s_and_c.DTO.InsertInternshipDTO;
import com.example.s_and_c.DTO.InternshipDTO;
import com.example.s_and_c.Entities.Company;
import com.example.s_and_c.Entities.Internship;
import com.example.s_and_c.Exception.ResourceNotFoundException;
import com.example.s_and_c.Mapper.InternshipMapper;
import com.example.s_and_c.Repositories.CompanyRepository;
import com.example.s_and_c.Repositories.InternshipRepository;
import com.example.s_and_c.Service.InternshipService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InternshipServiceImpl implements InternshipService {
    private final InternshipRepository internshipRepository;
    private final CompanyRepository companyRepository;


    @Override
    public List<InternshipDTO> createInternship(String email, InsertInternshipDTO insertInternshipDTO) {

        Company insertingCompany = companyRepository.findByEmail(email).orElse(null);
        if (insertingCompany != null) {
            Internship internship = InternshipMapper.maptoInternship(insertInternshipDTO, insertingCompany);
            internshipRepository.save(internship);
        }
        else throw new ResourceNotFoundException("Company not found, invalid request");

        return getAllInternshipsByEmail(insertingCompany);
    }

    private List<InternshipDTO> getAllInternshipsByEmail(Company insCompany) {
        List<Internship> internships = internshipRepository.findByCompany(insCompany);
        return internships.stream().map(InternshipMapper::maptoInternshipDTO).collect(Collectors.toList());

    }

    @Override
    public InternshipDTO getInternship(int id) {
        Internship internship = internshipRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Internship not found"));
        return InternshipMapper.maptoInternshipDTO(internship);
    }

    @Override
    public List<InternshipDTO> getAllInternships() {
        List<Internship> internships = internshipRepository.findAll();
        return internships.stream().map(InternshipMapper::maptoInternshipDTO).collect(Collectors.toList());
    }

    @Override
    public InternshipDTO updateInternship(int id, InsertInternshipDTO insertInternshipDTODTO) {
        Internship internship = internshipRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Internship not found"));

        internship.setName(insertInternshipDTODTO.getName());
        internship.setDescription(insertInternshipDTODTO.getDescription());
        internship.setStart_date(insertInternshipDTODTO.getStart_date());
        internship.setEnd_date(insertInternshipDTODTO.getEnd_date());
        internship.setSalary(insertInternshipDTODTO.getSalary());
        Internship updatedInternship = internshipRepository.save(internship);

        return InternshipMapper.maptoInternshipDTO(updatedInternship);

    }

    @Override
    public void deleteInternship(int id) {
        Internship internship = internshipRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Internship not found"));

        internshipRepository.delete(internship);
    }
}
