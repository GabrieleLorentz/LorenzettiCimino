package com.example.s_and_c.DTO.FormDTO;


import com.example.s_and_c.DTO.InternshipDTOs.FormDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormResponseDTO {
    private long internshipId;
    private List<FormDTO> formToCompile;
}
