package com.example.s_and_c.Controller;

import com.example.s_and_c.DTO.CompanyDTOs.CompanyDTO;
import com.example.s_and_c.DTO.CompanyDTOs.UpdatedCompanyDTO;
import com.example.s_and_c.DTO.FormDTO.ComplaintDTO;
import com.example.s_and_c.DTO.FormDTO.FeedBackDTO;
import com.example.s_and_c.DTO.FormDTO.ReviewDTO;
import com.example.s_and_c.DTO.InternshipDTOs.InsertInternshipDTO;
import com.example.s_and_c.DTO.InternshipDTOs.InternshipCompleteDTO;
import com.example.s_and_c.DTO.InternshipDTOs.InternshipDTO;
import com.example.s_and_c.DTO.StudentDTOS.StudentDTO;
import com.example.s_and_c.Service.CompanyService;
import com.example.s_and_c.Service.InternshipService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/company")
public class CompanyController {

    private final CompanyService companyService;
    private final InternshipService internshipService;

    /*@PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO) {
        CompanyDTO savedCompany = companyService.createCompany(companyDTO);
        return new ResponseEntity<>(savedCompany, HttpStatus.CREATED);
    }*/
    @GetMapping({"/personalData"})
    public ResponseEntity<CompanyDTO> getStudentById() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        try {
            CompanyDTO company = companyService.getCompany(authEmail);
            if (company == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(company);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/insertInternship")
    public ResponseEntity<List<InternshipDTO>> createInternship(@RequestBody InsertInternshipDTO insertInternshipDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<InternshipDTO> savedInternship = internshipService.createInternship(auth.getName(), insertInternshipDTO);
        return new ResponseEntity<>(savedInternship, HttpStatus.CREATED);
    }

    @GetMapping("/myInternship")
    public ResponseEntity<List<InternshipCompleteDTO>> getMyInternship() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<InternshipCompleteDTO> savedInternship = internshipService.getMyInternship(auth.getName());
        return new ResponseEntity<>(savedInternship, HttpStatus.OK);
    }

    @PostMapping("/studentAccepted/{email}_{internshipId}")
    public ResponseEntity<StudentDTO> studentAccepted(@PathVariable ("email") String email, @PathVariable("internshipId") int internshipId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        internshipService.addAcceptedStudent(email, internshipId,authEmail);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/studentSelected/{email}_{internshipId}")
    public ResponseEntity<StudentDTO> studentSelected(@PathVariable ("email") String email, @PathVariable ("internshipId") int internshipId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        internshipService.addSelectedStudent(email,internshipId,authEmail);
        return ResponseEntity.ok().build();
    }

    @GetMapping({"{email}"})
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable ("email") String email) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        if(!authEmail.equals(email)) {
            return ResponseEntity.badRequest().build();
        }
        CompanyDTO savedCompany = companyService.getCompany(email);
        if (savedCompany == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(savedCompany);
    }

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        List<CompanyDTO> allCompanies = companyService.getAllCompanies();
        return ResponseEntity.ok(allCompanies);
    }

    @PostMapping("/updateData")
    public ResponseEntity<UpdatedCompanyDTO> updateCompany(
            @RequestBody CompanyDTO updatedCompanyDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UpdatedCompanyDTO companyDTO = companyService.updateCompany(auth.getName(), updatedCompanyDTO);

        if(companyDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(companyDTO);
    }

    @GetMapping("/complaint/{internship_id}")
    public ResponseEntity<List<ComplaintDTO>> handleComplaintToSend( @PathVariable int internship_id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        List<ComplaintDTO> complaintDTO = companyService.handleComplaintToSend(authEmail,internship_id);
        return ResponseEntity.ok(complaintDTO);
    }

    @PostMapping("/sendComplaints")
    public ResponseEntity<CompanyDTO> complaints(@RequestBody ComplaintDTO complaintDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        companyService.handleComplaint(authEmail, complaintDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/feedback/{internship_id}")
    public ResponseEntity<List<FeedBackDTO>> handleFeedBackToSend(@PathVariable ("internship_id") int internshipId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        List<FeedBackDTO> feedBackDTOS = companyService.handleFeedBackToSend(authEmail,internshipId);
        return ResponseEntity.ok(feedBackDTOS);
    }


    @PostMapping("/feedback")
    public ResponseEntity<CompanyDTO> feedback(@RequestBody FeedBackDTO feedBackDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        companyService.handleFeedBack(authEmail, feedBackDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/review/{internship_id}")
    public ResponseEntity<List<ReviewDTO>> handleReviewToSend(@PathVariable ("internship_id") int internshipId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        List<ReviewDTO> reviewDTOS = companyService.handleReviewToSend(authEmail,internshipId);
        return ResponseEntity.ok(reviewDTOS);
    }

    @PostMapping("/review")
    public ResponseEntity<CompanyDTO> review(@RequestBody ReviewDTO reviewDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        companyService.handleReview(authEmail, reviewDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteCompany(
            @PathVariable("email") String email) {
        companyService.deleteCompany(email);
        return ResponseEntity.ok("Company deleted succesfully");
    }

}
