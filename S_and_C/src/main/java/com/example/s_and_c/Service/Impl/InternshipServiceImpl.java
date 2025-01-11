package com.example.s_and_c.Service.Impl;


import com.example.s_and_c.DTO.InternshipDTO;
import com.example.s_and_c.Entities.Internship;
import com.example.s_and_c.Exception.ResourceNotFoundException;
import com.example.s_and_c.Mapper.InternshipMapper;
import com.example.s_and_c.Repositories.InternshipRepository;
import com.example.s_and_c.Service.InternshipService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InternshipServiceImpl implements InternshipService {
    private InternshipRepository internshipRepository;


    @Override
    public InternshipDTO createInternship(InternshipDTO internshipDTO) {

        Internship internship = InternshipMapper.maptoInternship(internshipDTO);
        Internship internshipSaved = internshipRepository.save(internship);

        return InternshipMapper.maptoInternshipDTO(internshipSaved);
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
    public InternshipDTO updateInternship(int id, InternshipDTO internshipDTODTO) {
        Internship internship = internshipRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Internship not found"));

        internship.setName(internshipDTODTO.getName());
        internship.setDescription(internshipDTODTO.getDescription());
        internship.setStart_date(internshipDTODTO.getStart_date());
        internship.setEnd_date(internshipDTODTO.getEnd_date());
        internship.setSalary(internshipDTODTO.getSalary());
        Internship updatedInternship = internshipRepository.save(internship);

        return InternshipMapper.maptoInternshipDTO(updatedInternship);

    }

    @Override
    public void deleteInternship(int id) {
        Internship internship = internshipRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Internship not found"));

        internshipRepository.delete(internship);
    }
}
