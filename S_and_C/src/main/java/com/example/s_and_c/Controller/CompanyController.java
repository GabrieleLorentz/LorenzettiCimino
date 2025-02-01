package com.example.s_and_c.Controller;

import com.example.s_and_c.DTO.CompanyDTOs.CompanyDTO;
import com.example.s_and_c.DTO.CompanyDTOs.UpdatedCompanyDTO;
import com.example.s_and_c.DTO.FormDTO.ComplaintDTO;
import com.example.s_and_c.DTO.FormDTO.FeedBackDTO;
import com.example.s_and_c.DTO.FormDTO.ReviewDTO;
import com.example.s_and_c.DTO.FormDTO.FormCompleteDTO;
import com.example.s_and_c.DTO.InternshipDTOs.InsertInternshipDTO;
import com.example.s_and_c.DTO.InternshipDTOs.InternshipCompleteDTO;
import com.example.s_and_c.DTO.InternshipDTOs.InternshipDTO;
import com.example.s_and_c.DTO.StudentDTOS.StudentDTO;
import com.example.s_and_c.Service.CompanyService;
import com.example.s_and_c.Service.InternshipService;
import com.example.s_and_c.Utils.InternshipException;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller that handles the request made to api/company/
 */
@AllArgsConstructor
@RestController
@RequestMapping("api/company")
public class CompanyController {

    private final CompanyService companyService;
    private final InternshipService internshipService;


    /**
     * Function that get the personal data for the user, gets email from the token, and use it to get the correct elements
     * @return CompanyDTO with all user personal data
     */
    @GetMapping({"/personalData"})
    public ResponseEntity<CompanyDTO> getStudentByEmail() {
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

    /**
     * Function that help the student to add a new internship.
     * @param insertInternshipDTO The DTO with all the needed elements to create an internship
     * @return all the internship of the user, with the new one
     */
    @PostMapping("/insertInternship")
    public ResponseEntity<List<InternshipDTO>> createInternship(@RequestBody InsertInternshipDTO insertInternshipDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<InternshipDTO> savedInternship = internshipService.createInternship(auth.getName(), insertInternshipDTO);
        return new ResponseEntity<>(savedInternship, HttpStatus.CREATED);
    }

    /**
     * Function useful to retrieve the user's internships after creation
     * @return list of internship with all the correlated data
     */
    @GetMapping("/myInternship")
    public ResponseEntity<List<InternshipCompleteDTO>> getMyInternship() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<InternshipCompleteDTO> savedInternship = internshipService.getMyInternship(auth.getName());
        return new ResponseEntity<>(savedInternship, HttpStatus.OK);
    }

    /**
     * Help the user to accept a student to the formCompiling phase
     * @param email the accepted student email
     * @param internshipId the referred internship id
     * @return 200 ok response, if exception are not thrown internally
     */
    @PostMapping("/studentAccepted/{email}_{internshipId}")
    public ResponseEntity<StudentDTO> studentAccepted(@PathVariable ("email") String email, @PathVariable("internshipId") int internshipId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        internshipService.addAcceptedStudent(email, internshipId,authEmail);
        return ResponseEntity.ok().build();
    }

    /**
     * Help the user to select a student to the selected Student phase
     * @param email the selected student email
     * @param internshipId the referred internship id
     * @return 200 ok response, if exception are not thrown internally
     */
    @PostMapping("/studentSelected/{email}_{internshipId}")
    public ResponseEntity<StudentDTO> studentSelected(@PathVariable ("email") String email, @PathVariable ("internshipId") long internshipId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        internshipService.addSelectedStudent(email,internshipId,authEmail);
        return ResponseEntity.ok().build();
    }

    /*
     * Useful to retrieve all comp
     * @return
     *
    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        List<CompanyDTO> allCompanies = companyService.getAllCompanies();
        return ResponseEntity.ok(allCompanies);
    }*/

    /**
     * Function to handle the updates of the data by the user
     * @param updatedCompanyDTO DTO with all the modified data
     * @return 200 ok if no exception are internally thrown, 400 bad request if data are empty
     */
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

    /**
     * Function to send a complaint to the respective user
     * @param complaintDTO DTO with all object needed to represent a complaint
     * @return 200 ok if no exception are thrown internally
     */
    @PostMapping("/sendComplaints")
    public ResponseEntity<CompanyDTO> complaints(@RequestBody ComplaintDTO complaintDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        companyService.handleComplaintReceived(authEmail, complaintDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Function to send a feedback about the respective user
     * @param feedBackDTO DTO with all object needed to represent a complaint
     * @return 200 ok if no exception are thrown internally
     */
    @PostMapping("/sendFeedback")
    public ResponseEntity<CompanyDTO> feedback(@RequestBody FeedBackDTO feedBackDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        companyService.handleFeedBackReceived(authEmail, feedBackDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Function to send a review about the respective user
     * @param reviewDTO DTO with all object needed to represent a review
     * @return 200 ok if no exception are thrown internally
     */
    @PostMapping("/sendReview")
    public ResponseEntity<CompanyDTO> review(@RequestBody ReviewDTO reviewDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        companyService.handleReviewReceived(authEmail, reviewDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Function used to retrieve all the user's compiled forms
     * @return List of all the forms compiled
     */
    @GetMapping("/myForms")
    public ResponseEntity<List<FormCompleteDTO>> getMyForms() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        return new ResponseEntity<>(companyService.getMyForms(authEmail),HttpStatus.OK);
    }

    /**
     * Method to handle all internal exception messages
     * @param e message
     * @return status and message
     */
    @ExceptionHandler(InternshipException.class)
    public ResponseEntity<Map<String, String>> handleInternshipException(@NotNull InternshipException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());

        return ResponseEntity
                .status(e.getStatusCode())
                .body(response);
    }

    
    
}
