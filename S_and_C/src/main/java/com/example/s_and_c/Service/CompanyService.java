package com.example.s_and_c.Service;

import com.example.s_and_c.DTO.CompanyDTOs.CompanyDTO;
import com.example.s_and_c.DTO.CompanyDTOs.UpdatedCompanyDTO;
import com.example.s_and_c.DTO.ComplaintDTO;
import com.example.s_and_c.DTO.FeedBackDTO;

import java.util.List;

public interface CompanyService {
    CompanyDTO createCompany(CompanyDTO companyDTO);

    CompanyDTO getCompany(String email);

    List<CompanyDTO> getAllCompanies();

    UpdatedCompanyDTO updateCompany(String email, CompanyDTO companyDTO);

    void deleteCompany(String email);

    void handleComplaint(String authEmail, ComplaintDTO internshipId);

    void handleFeedBack(String authEmail, FeedBackDTO feedBackDTO);
}
