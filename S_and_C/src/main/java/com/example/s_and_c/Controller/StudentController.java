package com.example.s_and_c.Controller;

import com.example.s_and_c.DTO.CompanyDTOs.CompanyDTO;
import com.example.s_and_c.DTO.FormDTO.*;
import com.example.s_and_c.DTO.InternshipDTOs.*;
import com.example.s_and_c.DTO.StudentDTOS.StudentDTO;
import com.example.s_and_c.DTO.StudentDTOS.UpdatedStudentDTO;
import com.example.s_and_c.Service.InternshipService;
import com.example.s_and_c.Service.StudentService;
import com.example.s_and_c.Utils.InternshipException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * REST Controller that handles the request made to api/student/
 */
@AllArgsConstructor
@RestController
@RequestMapping("api/student")
public class StudentController {


    private final StudentService studentService;
    private final InternshipService internshipService;

    /**
     * Function that get the personal data for the user, gets email from the token, and use it to get the correct elements
     * @return StudentDTO with all user personal data
     */
    @GetMapping({"/personalData"})
    public ResponseEntity<StudentDTO> getStudentById() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        try {
            StudentDTO student = studentService.getStudent(authEmail);
            if (student == null) {
                return ResponseEntity.notFound().build();
            }
            System.out.println(student);
            return ResponseEntity.ok(student);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Function to handle the updates of the data by the user
     * @param updatedStudentDTO DTO with all the modified data
     * @return 200 ok if no exception are internally thrown, 400 bad request if data are empty
     */
    @PostMapping("/updateData")
    public ResponseEntity<UpdatedStudentDTO> updateStudent(
            @RequestBody UpdatedStudentDTO updatedStudentDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UpdatedStudentDTO studentDTO = studentService.updateStudent(auth.getName(), updatedStudentDTO);
        if (studentDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(studentDTO);
    }

    /**
     * Function to search through all the internship in the platform
     * @param searchDTO DTO with a predefined filters to execute the research
     * @return 200 ok we have a response, 204 if no results are available
     */
    @PostMapping("/search")
    public ResponseEntity<List<InternshipForStudentsDTO>> searchInternships(
            @RequestBody SearchDTO searchDTO){
        List<InternshipForStudentsDTO> internshipDTOList = internshipService.findMatch(searchDTO);
        if(internshipDTOList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else return ResponseEntity.ok(internshipDTOList);

    }

    /**
     * Function that permits to a student to apply for an internship
     * @param internshipId the id of the referred Internship
     * @return 200 ok if no exception are internally thrown,
     */
    @PostMapping("/requestInternship")
    public ResponseEntity<InternshipDTO> requestInternship(@RequestBody InternshipIdDTO internshipId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        studentService.requestInternship(internshipId.getId(),authEmail);
        return ResponseEntity.ok().build();
    }

    /**
     * Function that get all the internship where the user is involved
     * @return List of InternshipForStudentDTO
     */
    @GetMapping("/myInternships")
    public ResponseEntity<List<InternshipForStudentsDTO>> getMyInternships() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        List<InternshipForStudentsDTO> internshipDTOList = studentService.getPersonalInternships(authEmail);
        return internshipDTOList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(internshipDTOList);
    }

    /**
     * Function that get all internship available to display
     * @return List of InternshipForStudentDTO
     */
    @GetMapping("/allInternships")
    public ResponseEntity<List<InternshipForStudentsDTO>> getAllInternships() {
        List<InternshipForStudentsDTO> internshipForStudentsDTOS = internshipService.getAllForStudents();
        return new ResponseEntity<>(internshipForStudentsDTOS, HttpStatus.OK);
    }

    /**
     * Function that permit to the student to respond to the company question form
     * @param formResponseDTO DTO with all the needed response to the questions form
     * @return 200 ok if no exceptions are internally thrown
     */
    @PostMapping("/formResponses")
    public ResponseEntity<StudentDTO> formResponses(@RequestBody FormResponseDTO formResponseDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        internshipService.addFormResponse(formResponseDTO, authEmail);
        return ResponseEntity.ok().build();
    }

    /**
     * Function that permit to the student to respond to the company question form
     * @param internshipId the id of the referred Internship
     * @return 200 ok if no exception are internally thrown
     */
    @DeleteMapping("/renounce/{internshipId}")
    public ResponseEntity<StudentDTO> internshipRenounce(@RequestParam long internshipId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        internshipService.renounce(internshipId, authEmail);
        return ResponseEntity.ok().build();
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
        studentService.handleComplaintReceived(authEmail, complaintDTO);
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
        studentService.handleFeedBack(authEmail, feedBackDTO);
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
        studentService.handleReviewReceived(authEmail, reviewDTO);
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
        return new ResponseEntity<>(studentService.getMyForms(authEmail),HttpStatus.OK);
    }

    /**
     * Method to handle all internal exception messages
     * @param e message
     * @return status and message
     */
    @ExceptionHandler(InternshipException.class)
    public ResponseEntity<Map<String, String>> handleInternshipException(InternshipException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());

        return ResponseEntity
                .status(e.getStatusCode())
                .body(response);
    }

}
