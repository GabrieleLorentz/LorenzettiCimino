package com.example.s_and_c.Controller;


import com.example.s_and_c.DTO.InsertInternshipDTO;
import com.example.s_and_c.Service.InternshipService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/Internship")
public class InternshipController {

    private final InternshipService internshipService;

    /*
    @PostMapping
    public ResponseEntity<InternshipDTO> addInternship(@RequestBody InternshipDTO internshipDTO) {
        InternshipDTO savedInternship = internshipService.createInternship(internshipDTO, email);
        return new ResponseEntity<>(savedInternship, HttpStatus.CREATED);
    }*/

    /*
    @GetMapping("{id}")
    public ResponseEntity<InsertInternshipDTO> getInternshipById(@PathVariable int id) {
        InsertInternshipDTO savedInternship = internshipService.getInternship(id);
        return ResponseEntity.ok(savedInternship);
    }

    @PutMapping("/id")
    public ResponseEntity<InsertInternshipDTO> updateInternship(
            @PathVariable("id") int id,
            @RequestBody InsertInternshipDTO insertInternshipDTO) {
        InsertInternshipDTO savedInternship = internshipService.updateInternship(id, insertInternshipDTO);
        return ResponseEntity.ok(savedInternship);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInternship(
            @PathVariable("id") int id) {
        internshipService.deleteInternship(id);
        return ResponseEntity.ok("Internship deleted succesfully");
    }*/
}
