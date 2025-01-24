package com.example.s_and_c.Service;

import com.example.s_and_c.DTO.*;
import com.example.s_and_c.DTO.InternshipDTOs.InternshipCompleteDTO;
import com.example.s_and_c.DTO.InternshipDTOs.InternshipDTO;
import com.example.s_and_c.DTO.InternshipDTOs.InternshipForStudentsDTO;

import java.util.List;


public interface InternshipService {

    List<InternshipDTO> createInternship(String email, InsertInternshipDTO insertInternshipDTO);


    List<InternshipDTO> findMatch(SearchDTO searchDTO);

    List<InternshipCompleteDTO> getMyInternship(String name);

    void addAcceptedStudent(String email, int internshipId);

    List<InternshipForStudentsDTO> getAllForStudents();

    void addFormResponse(InternshipForStudentsDTO internshipForStudentsDTO, String authEmail);

    void addSelectedStudent(String email, int internshipId, String authEmail);
}
