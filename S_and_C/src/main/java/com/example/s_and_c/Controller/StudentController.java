package com.example.s_and_c.Controller;

import com.example.s_and_c.DTO.StudentDTOS.StudentDTO;
import com.example.s_and_c.DTO.StudentDTOS.StudentInternshipDTO;
import com.example.s_and_c.Service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/student")
public class StudentController {

    private final StudentService studentService;

    @GetMapping({"/personalData"})
    public ResponseEntity<StudentInternshipDTO> getStudentById(@RequestParam String email) {
        StudentInternshipDTO savedStudent = studentService.getStudent(email);
        return ResponseEntity.ok(savedStudent);
    }

    @GetMapping
    //Build get all student REST API
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> allStudents = studentService.getAllStudents();
        return ResponseEntity.ok(allStudents);
    }

    @PutMapping("/updateData")
    //Build update student
    public ResponseEntity<StudentDTO> updateStudent(
            @RequestParam String email,
            @RequestBody StudentDTO updatedStudentDTO) {
        StudentDTO studentDTO = studentService.updateStudent(email, updatedStudentDTO);
        return ResponseEntity.ok(studentDTO);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable("email") String email) {
        studentService.deleteStudent(email);
        return ResponseEntity.ok("Student deleted succesfully");
    }
}
