package com.example.s_and_c.Service;

import com.example.s_and_c.DTO.*;

import java.util.List;


public interface InternshipService {

    List<InternshipDTO> createInternship(String email, InsertInternshipDTO insertInternshipDTO);


    List<InternshipDTO> findMatch(SearchDTO searchDTO);

    List<InternshipCompleteDTO> getMyInternship(String name);

    void addAcceptedStudent(String email, int internshipId);

    List<InternshipForStudentsDTO> getAllForStudents();

    void addFormResponse(InternshipForStudentsDTO internshipForStudentsDTO, String authEmail);
}
