package com.example.s_and_c.Controller;

import com.example.s_and_c.DTO.CompanyDTO;
import com.example.s_and_c.Service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/company")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO) {
        CompanyDTO savedCompany = companyService.createCompany(companyDTO);
        return new ResponseEntity<>(savedCompany, HttpStatus.CREATED);
    }

    @GetMapping({"{email}"})
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable ("email") String email) {
        CompanyDTO savedCompany = companyService.getCompany(email);
        return ResponseEntity.ok(savedCompany);
    }

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        List<CompanyDTO> allCompanies = companyService.getAllCompanies();
        return ResponseEntity.ok(allCompanies);
    }

    @PutMapping("/email")
    public ResponseEntity<CompanyDTO> updateCompany(
            @PathVariable("email") String email,
            @RequestBody CompanyDTO updatedCompanyDTO) {
        CompanyDTO companyDTO = companyService.updateCompany(email, updatedCompanyDTO);
        return ResponseEntity.ok(companyDTO);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteCompany(
            @PathVariable("email") String email) {
        companyService.deleteCompany(email);
        return ResponseEntity.ok("Company deleted succesfully");
    }

}
