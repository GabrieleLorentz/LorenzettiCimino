package com.example.s_and_c.Mapper;

import com.example.s_and_c.DTO.FormDTO;
import com.example.s_and_c.Entities.Form;

public class FormMapper {
    public static void mapToFormDTO(Form form) {
        new FormDTO(
                form.getFormId(),
                form.getRequest(),
                form.getResponse()
        );
    }
}
