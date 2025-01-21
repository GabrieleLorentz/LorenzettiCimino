package com.example.s_and_c.Controller;

import com.example.s_and_c.DTO.InternshipDTO;
import com.example.s_and_c.DTO.SearchDTO;
import com.example.s_and_c.DTO.StudentDTOS.StudentDTO;
import com.example.s_and_c.DTO.StudentDTOS.StudentInternshipDTO;
import com.example.s_and_c.DTO.UpdatedStudentDTO;
import com.example.s_and_c.Service.InternshipService;
import com.example.s_and_c.Service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/student")
public class StudentController {


    private final StudentService studentService;
    private final InternshipService internshipService;

    @GetMapping({"/personalData"})
    public ResponseEntity<StudentInternshipDTO> getStudentById() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        try {
            StudentInternshipDTO student = studentService.getStudent(authEmail);
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

    @GetMapping
    //Build get all student REST API
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> allStudents = studentService.getAllStudents();
        return ResponseEntity.ok(allStudents);
    }

    @PostMapping("/updateData")
    //Build update student
    public ResponseEntity<UpdatedStudentDTO> updateStudent(
            @RequestBody StudentDTO updatedStudentDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UpdatedStudentDTO studentDTO = studentService.updateStudent(auth.getName(), updatedStudentDTO);
        if (studentDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentDTO);
    }

    @PostMapping("/search")
    public ResponseEntity<List<InternshipDTO>> searchInternships(
            @RequestBody SearchDTO searchDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authEmail = auth.getName();
        List<InternshipDTO> internshipDTOList = internshipService.findMatch(searchDTO);
        if(internshipDTOList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else return ResponseEntity.ok(internshipDTOList);

    }


    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable("email") String email) {
        studentService.deleteStudent(email);
        return ResponseEntity.ok("Student deleted succesfully");
    }
}
