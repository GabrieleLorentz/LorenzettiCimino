package com.example.s_and_c.Controller;

import com.example.s_and_c.DTO.StudentDTO;
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

    //Build Add student REST API
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO savedStudent = studentService.createStudent(studentDTO);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @GetMapping({"{email}"})
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable("email") String email) {
        StudentDTO savedStudent = studentService.getStudent(email);
        return ResponseEntity.ok(savedStudent);
    }

    @GetMapping
    //Build get all employees REST API
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> allStudents = studentService.getAllStudents();
        return ResponseEntity.ok(allStudents);
    }
}
