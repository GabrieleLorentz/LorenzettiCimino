package com.example.s_and_c.DTO.FormDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO to handle review info
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private String studentEmailForCompanyOnly;
    private long internshipId;
    private List<String> review;
}
