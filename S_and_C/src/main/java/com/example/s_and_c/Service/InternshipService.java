package com.example.s_and_c.Service;

import com.example.s_and_c.DTO.CompanyDTO;
import com.example.s_and_c.DTO.InternshipDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface InternshipService {
    InternshipDTO createInternship(InternshipDTO internshipDTO);

    InternshipDTO getInternship(int id);

    List<InternshipDTO> getAllInternships();

    InternshipDTO updateInternship(int id, InternshipDTO internshipDTODTO);

    void deleteInternship(int id);
}
