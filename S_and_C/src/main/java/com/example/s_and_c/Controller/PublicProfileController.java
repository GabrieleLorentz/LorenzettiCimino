package com.example.s_and_c.Controller;

import com.example.s_and_c.DTO.StudentDTOS.ShortStudentDTO;
import com.example.s_and_c.Service.CompanyService;
import com.example.s_and_c.Service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/publicProfile")
public class PublicProfileController {

    private final CompanyService companyService;
    private final StudentService studentService;

    @GetMapping("/getDataFrom{studentEmail}")
    public ResponseEntity<ShortStudentDTO> getDataFromStudent(@PathVariable String studentEmail){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>(studentService.getPublicStudentData(studentEmail), HttpStatus.OK);
    }

}
