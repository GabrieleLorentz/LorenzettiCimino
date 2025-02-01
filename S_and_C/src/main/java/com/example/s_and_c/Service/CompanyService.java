package com.example.s_and_c.Service;

import com.example.s_and_c.DTO.CompanyDTOs.CompanyDTO;
import com.example.s_and_c.DTO.CompanyDTOs.ShortCompanyDTO;
import com.example.s_and_c.DTO.CompanyDTOs.UpdatedCompanyDTO;
import com.example.s_and_c.DTO.FormDTO.ComplaintDTO;
import com.example.s_and_c.DTO.FormDTO.FeedBackDTO;
import com.example.s_and_c.DTO.FormDTO.ReviewDTO;
import com.example.s_and_c.DTO.FormDTO.FormCompleteDTO;

import java.util.List;

public interface CompanyService {

    CompanyDTO getCompany(String email);

    List<CompanyDTO> getAllCompanies();

    UpdatedCompanyDTO updateCompany(String email, CompanyDTO companyDTO);


    void handleFeedBackReceived(String authEmail, FeedBackDTO feedBackDTO);


    void handleComplaintReceived(String authEmail, ComplaintDTO complaintDTO);


    void handleReviewReceived(String authEmail, ReviewDTO reviewDTO);

    List<FormCompleteDTO> getMyForms(String authEmail);

    ShortCompanyDTO getPublicCompanyData(String companyEmail);
}
