package com.example.s_and_c.DTO.FormDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for new feedback creation
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedBackDTO {
    private String studentEmailForCompanyOnly;
    private long internshipId;
    private List<String> feedbacks = new ArrayList<>();
}
