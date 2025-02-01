package com.example.s_and_c.DTO.FormDTO;

import com.example.s_and_c.DTO.InternshipDTOs.FormDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private String studentEmailForCompanyOnly;
    private int internshipId;
    private List<String> review;
}
