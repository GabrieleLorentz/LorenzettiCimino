package com.example.s_and_c.Mapper;

import com.example.s_and_c.DTO.FormDTO.ComplaintDTO;
import com.example.s_and_c.DTO.FormDTO.FeedBackDTO;
import com.example.s_and_c.DTO.FormDTO.ReviewDTO;
import com.example.s_and_c.DTO.InternshipDTOs.FormDTO;
import com.example.s_and_c.Entities.Form;
import com.example.s_and_c.Entities.Internship;

import java.util.ArrayList;
import java.util.List;

public class FormMapper {
    public static FormDTO mapToFormDTO(Form form) {
        return new FormDTO(
                form.getFormId(),
                form.getRequest(),
                form.getResponse()
        );
    }

}
