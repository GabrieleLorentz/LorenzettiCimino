package com.example.s_and_c.Service;

import com.example.s_and_c.DTO.InsertInternshipDTO;
import com.example.s_and_c.DTO.InternshipDTO;
import com.example.s_and_c.DTO.SearchDTO;

import java.util.List;


public interface InternshipService {

    List<InternshipDTO> createInternship(String email, InsertInternshipDTO insertInternshipDTO);

    InternshipDTO getInternship(int id);

    List<InternshipDTO> getAllInternships();

    InternshipDTO updateInternship(int id, InsertInternshipDTO insertInternshipDTODTO);

    void deleteInternship(int id);

    List<InternshipDTO> findMatch(SearchDTO searchDTO);
}
