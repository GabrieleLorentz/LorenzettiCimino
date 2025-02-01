package com.example.s_and_c.DTO.FormDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintDTO {
    private long internshipId;
    private String complaint;
    private String studentEmailForCompanyOnly;

}
