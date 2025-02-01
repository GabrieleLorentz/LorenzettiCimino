package com.example.s_and_c.Service;

import com.example.s_and_c.DTO.FormDTO.ComplaintDTO;
import com.example.s_and_c.DTO.FormDTO.FeedBackDTO;
import com.example.s_and_c.DTO.FormDTO.FormCompleteDTO;
import com.example.s_and_c.DTO.InternshipDTOs.InternshipForStudentsDTO;
import com.example.s_and_c.DTO.FormDTO.ReviewDTO;
import com.example.s_and_c.DTO.StudentDTOS.StudentDTO;
import com.example.s_and_c.DTO.StudentDTOS.UpdatedStudentDTO;

import java.util.List;

public interface StudentService {

    StudentDTO getStudent(String email);

    UpdatedStudentDTO updateStudent(String email, UpdatedStudentDTO studentDTO);

    void requestInternship(long internshipId, String authEmail);

    List<InternshipForStudentsDTO> getPersonalInternships(String authEmail);

    void handleComplaintReceived(String authEmail, ComplaintDTO internshipId);

    void handleFeedBack(String authEmail, FeedBackDTO feedBackDTO);


    void handleReviewReceived(String authEmail, ReviewDTO reviewDTO);

    List<FormCompleteDTO> getMyForms(String authEmail);
}
