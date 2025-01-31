package com.example.s_and_c.Controller;

import com.example.s_and_c.DTO.CompanyDTOs.CompanyDTO;
import com.example.s_and_c.DTO.FormDTO.ComplaintDTO;
import com.example.s_and_c.DTO.FormDTO.FeedBackDTO;
import com.example.s_and_c.DTO.FormDTO.FormResponseDTO;
import com.example.s_and_c.DTO.FormDTO.ReviewDTO;
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

@AllArgsConstructor
@RestController
@RequestMapping("api/student")
public class StudentController {


    private final StudentService studentService;
    private final InternshipService internshipService;

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

    /*@GetMapping
    //Build get all student REST API
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> allStudents = studentService.getAllStudents();
        return ResponseEntity.ok(allStudents);
    }*/

    @PostMapping("/updateData")
    //Build update student
    public ResponseEntity<UpdatedStudentDTO> updateStudent(
            @RequestBody UpdatedStudentDTO updatedStudentDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UpdatedStudentDTO studentDTO = studentService.updateStudent(auth.getName(), updatedStudentDTO);
        //aggiungere il controllo in caso di cambio dati, che sia sistemato anche nelle internship
        if (studentDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(studentDTO);
    }

    @PostMapping("/search")
    public ResponseEntity<List<InternshipForStudentsDTO>> searchInternships(
            @RequestBody SearchDTO searchDTO){
        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();*/
        List<InternshipForStudentsDTO> internshipDTOList = internshipService.findMatch(searchDTO);
        if(internshipDTOList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else return ResponseEntity.ok(internshipDTOList);

    }

    @PostMapping("/requestInternship")
    public ResponseEntity<InternshipDTO> requestInternship(@RequestBody InternshipIdDTO internshipId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        studentService.requestInternship(internshipId.getId(),authEmail);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/myInternships")
    public ResponseEntity<List<InternshipForStudentsDTO>> getMyInternships() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        List<InternshipForStudentsDTO> internshipDTOList = studentService.getPersonalInternships(authEmail);
        return internshipDTOList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(internshipDTOList);
    }

    @GetMapping("/allInternships")
    public ResponseEntity<List<InternshipForStudentsDTO>> getAllInternships() {
        List<InternshipForStudentsDTO> internshipForStudentsDTOS = internshipService.getAllForStudents();
        return new ResponseEntity<>(internshipForStudentsDTOS, HttpStatus.OK);
    }

    @PostMapping("/formResponses")
    public ResponseEntity<StudentDTO> formResponses(@RequestBody FormResponseDTO formResponseDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        internshipService.addFormResponse(formResponseDTO, authEmail);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/renounce/{internshipId}")
    public ResponseEntity<StudentDTO> internshipRenounce(@RequestParam int internshipId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        internshipService.renounce(internshipId, authEmail);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable("email") String email) {
        studentService.deleteStudent(email);
        return ResponseEntity.ok("Student deleted succesfully");
    }



    @PostMapping("/sendComplaints")
    public ResponseEntity<CompanyDTO> complaints(@RequestBody ComplaintDTO complaintDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        studentService.handleComplaintReceived(authEmail, complaintDTO);
        return ResponseEntity.ok().build();
    }



    @PostMapping("/sendFeedback")
    public ResponseEntity<CompanyDTO> feedback(@RequestBody FeedBackDTO feedBackDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        studentService.handleFeedBack(authEmail, feedBackDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sendReview")
    public ResponseEntity<CompanyDTO> review(@RequestBody ReviewDTO reviewDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        studentService.handleReviewReceived(authEmail, reviewDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/myForms")
    public ResponseEntity<List<FormCompleteDTO>> getMyForms() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        return new ResponseEntity<>(studentService.getMyForms(authEmail),HttpStatus.OK);
    }

    @ExceptionHandler(InternshipException.class)
    public ResponseEntity<Map<String, String>> handleInternshipException(InternshipException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());

        return ResponseEntity
                .status(e.getStatusCode())
                .body(response);
    }

}
