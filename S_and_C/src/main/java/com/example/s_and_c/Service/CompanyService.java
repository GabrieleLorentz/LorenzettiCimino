package com.example.s_and_c.Service;

import com.example.s_and_c.DTO.CompanyDTOs.CompanyDTO;
import com.example.s_and_c.DTO.CompanyDTOs.UpdatedCompanyDTO;
import com.example.s_and_c.DTO.FormDTO.ComplaintDTO;
import com.example.s_and_c.DTO.FormDTO.FeedBackDTO;
import com.example.s_and_c.DTO.FormDTO.ReviewDTO;

import java.util.List;

public interface CompanyService {

    CompanyDTO getCompany(String email);

    List<CompanyDTO> getAllCompanies();

    UpdatedCompanyDTO updateCompany(String email, CompanyDTO companyDTO);

    void deleteCompany(String email);


    void handleFeedBackReceived(String authEmail, FeedBackDTO feedBackDTO);


    void handleComplaintReceived(String authEmail, ComplaintDTO complaintDTO);


    void handleReviewReceived(String authEmail, ReviewDTO reviewDTO);
}
