package com.example.s_and_c.Mapper;

import com.example.s_and_c.DTO.InternshipDTOs.FormDTO;
import com.example.s_and_c.Entities.Form;
import com.example.s_and_c.Entities.Internship;

public class FormMapper {
    public static FormDTO mapToFormDTO(Form form) {
        return new FormDTO(
                form.getFormId(),
                form.getRequest(),
                form.getResponse()
        );
    }
    public static Form mapToForm(FormDTO formDTO, Internship internship) {
        return new Form(
                formDTO.getRequest(),
                formDTO.getResponse(),
                internship
        );
    }
}
