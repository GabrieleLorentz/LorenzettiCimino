package com.example.s_and_c.Service;

import com.example.s_and_c.DTO.FormDTO.ComplaintDTO;
import com.example.s_and_c.DTO.FormDTO.FeedBackDTO;
import com.example.s_and_c.DTO.InternshipDTOs.InternshipForStudentsDTO;
import com.example.s_and_c.DTO.FormDTO.ReviewDTO;
import com.example.s_and_c.DTO.StudentDTOS.StudentDTO;
import com.example.s_and_c.DTO.StudentDTOS.UpdatedStudentDTO;

import java.util.List;

public interface StudentService {

    StudentDTO getStudent(String email);

    List<StudentDTO> getAllStudents();

    UpdatedStudentDTO updateStudent(String email, StudentDTO studentDTO);

    void deleteStudent(String email);

    void requestInternship(long internshipId, String authEmail);

    List<InternshipForStudentsDTO> getPersonalInternships(String authEmail);

    void handleComplaint(String authEmail, ComplaintDTO complaintDTO);

    void handleFeedBack(String authEmail, FeedBackDTO feedBackDTO);

    void handleReview(String authEmail, ReviewDTO reviewDTO);
}
