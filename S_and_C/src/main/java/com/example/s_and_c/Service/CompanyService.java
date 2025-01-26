package com.example.s_and_c.Service;

import com.example.s_and_c.DTO.CompanyDTOs.CompanyDTO;
import com.example.s_and_c.DTO.CompanyDTOs.UpdatedCompanyDTO;
import com.example.s_and_c.DTO.FormDTO.ComplaintDTO;
import com.example.s_and_c.DTO.FormDTO.FeedBackDTO;
import com.example.s_and_c.DTO.FormDTO.ReviewDTO;

import java.util.List;

public interface CompanyService {
    CompanyDTO createCompany(CompanyDTO companyDTO);

    CompanyDTO getCompany(String email);

    List<CompanyDTO> getAllCompanies();

    UpdatedCompanyDTO updateCompany(String email, CompanyDTO companyDTO);

    void deleteCompany(String email);

    void handleComplaint(String authEmail, ComplaintDTO internshipId);

    void handleFeedBack(String authEmail, FeedBackDTO feedBackDTO);

    void handleReview(String authEmail, ReviewDTO formDTO);

    List<ComplaintDTO> handleComplaintToSend(String authEmail, int internshipId);

    List<FeedBackDTO> handleFeedBackToSend(String authEmail, int internshipId);

    List<ReviewDTO> handleReviewToSend(String authEmail, int internshipId);
}
