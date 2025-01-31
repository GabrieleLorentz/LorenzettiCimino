package com.example.s_and_c.Mapper;

import com.example.s_and_c.DTO.CompanyDTOs.ShortCompanyDTO;
import com.example.s_and_c.DTO.FormDTO.ComplaintDTO;
import com.example.s_and_c.DTO.FormDTO.FeedBackDTO;
import com.example.s_and_c.DTO.FormDTO.ReviewDTO;
import com.example.s_and_c.DTO.InternshipDTOs.FormCompleteDTO;
import com.example.s_and_c.DTO.InternshipDTOs.FormDTO;
import com.example.s_and_c.DTO.StudentDTOS.ShortStudentDTO;
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

    public static FormCompleteDTO mapToCompleteFormDTO(Form form) {
        if (form.getStudent() != null) {
            return new FormCompleteDTO(
                    form.getFormId(),
                    form.getRequest(),
                    form.getResponse(),
                    new ShortStudentDTO(form.getStudent().getEmail(), form.getStudent().getName(), form.getStudent().getSurname(), form.getStudent().getDescription()),
                    form.getFormType()
            );
        }
        else
            return new FormCompleteDTO(
                    form.getFormId(),
                    form.getRequest(),
                    form.getResponse(),
                    new ShortCompanyDTO(form.getCompany().getEmail(), form.getCompany().getName(), form.getCompany().getDescription(), form.getCompany().getVat_number()),
                    form.getFormType()
            );
    }
}



