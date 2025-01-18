package com.example.s_and_c.Service;

import com.example.s_and_c.DTO.InsertInternshipDTO;

import java.util.List;


public interface InternshipService {
    List<InsertInternshipDTO> createInternship(InsertInternshipDTO insertInternshipDTO);

    InsertInternshipDTO getInternship(int id);

    List<InsertInternshipDTO> getAllInternships();

    InsertInternshipDTO updateInternship(int id, InsertInternshipDTO insertInternshipDTODTO);

    void deleteInternship(int id);
}
