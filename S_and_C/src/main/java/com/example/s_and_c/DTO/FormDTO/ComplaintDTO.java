package com.example.s_and_c.DTO.FormDTO;

import com.example.s_and_c.DTO.InternshipDTOs.FormDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class ComplaintDTO {
    private int internship_id;
    private List<FormDTO> complaints = new ArrayList<>();

}
