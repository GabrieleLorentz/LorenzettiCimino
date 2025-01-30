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
    public static Form mapToForm(FormDTO formDTO, Internship internship) {
        return new Form(
                formDTO.getRequest(),
                formDTO.getResponse(),
                internship
        );
    }

    /*public static ComplaintDTO mapToComplaintDTO(Form form) {
        FormDTO formDTO = FormMapper.mapToFormDTO(form);
        List<FormDTO> formDTOS= new ArrayList<>();
        formDTOS.add(formDTO);
        return new ComplaintDTO(
                (int) form.getInternship().getInternshipId(),
                formDTOS
        );
    }*/

    /*public static FeedBackDTO mapToFeedBackDTO(String authEmail, Form formIter) {
        FormDTO formDTO = FormMapper.mapToFormDTO(formIter);
        List<FormDTO> formDTOS= new ArrayList<>();
        formDTOS.add(formDTO);
        return new FeedBackDTO(
                authEmail,
                (int) formIter.getInternship().getInternshipId(),
                formDTOS
        );
    }*/

    public static ReviewDTO mapToReviewDTO(String authEmail, Form formIter) {
        FormDTO formDTO = FormMapper.mapToFormDTO(formIter);
        List<FormDTO> formDTOS= new ArrayList<>();
        formDTOS.add(formDTO);
        return new ReviewDTO(
                authEmail,
                (int) formIter.getInternship().getInternshipId(),
                formDTOS
        );
    }
}
