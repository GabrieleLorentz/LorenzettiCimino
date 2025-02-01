package com.example.s_and_c.Mapper;

import com.example.s_and_c.DTO.CompanyDTOs.ShortCompanyDTO;
import com.example.s_and_c.DTO.FormDTO.FormCompleteDTO;
import com.example.s_and_c.DTO.FormDTO.FormDTO;
import com.example.s_and_c.DTO.InternshipDTOs.ShortInternshipDTO;
import com.example.s_and_c.DTO.StudentDTOS.ShortStudentDTO;
import com.example.s_and_c.Entities.Form;
import com.example.s_and_c.Entities.Status.FormType;

public class FormMapper {
    public static FormDTO mapToFormDTO(Form form) {
        return new FormDTO(
                form.getFormId(),
                form.getRequest(),
                form.getResponse()
        );
    }

    public static FormCompleteDTO mapToCompleteFormDTO(Form form) {
        if(form.getFormType().equals(FormType.CV)){
            return new FormCompleteDTO(
                    form.getFormId(),
                    form.getRequest(),
                    form.getResponse(),
                    new ShortStudentDTO(form.getStudent().getEmail(), form.getStudent().getName(), form.getStudent().getSurname(), form.getStudent().getDescription()),
                    form.getFormType()
            );
        }
        if(form.getFormType().equals(FormType.C_COMPLAINT) ||form.getFormType().equals(FormType.S_COMPLAINT) ){
            return new FormCompleteDTO(form.getFormId(),
                    form.getRequest(),
                    form.getResponse(),
                    new ShortInternshipDTO(form.getInternship().getName(),form.getInternship().getCompany().getName(),form.getInternship().getCompany().getEmail()),
                    new ShortStudentDTO(form.getStudent().getEmail(), form.getStudent().getName(), form.getStudent().getSurname(), form.getStudent().getDescription()),
                    new ShortCompanyDTO(form.getCompany().getEmail(), form.getCompany().getName(), form.getCompany().getDescription(), form.getCompany().getVat_number()),
                    form.getFormType());
        }
        if (form.getStudent() != null) {
            return new FormCompleteDTO(
                    form.getFormId(),
                    form.getRequest(),
                    form.getResponse(),
                    new ShortInternshipDTO(form.getInternship().getName(),form.getInternship().getCompany().getName(),form.getInternship().getCompany().getEmail()),
                    new ShortStudentDTO(form.getStudent().getEmail(), form.getStudent().getName(), form.getStudent().getSurname(), form.getStudent().getDescription()),
                    form.getFormType()
            );
        }
        else
            return new FormCompleteDTO(
                    form.getFormId(),
                    form.getRequest(),
                    form.getResponse(),
                    new ShortInternshipDTO(form.getInternship().getName(),form.getInternship().getCompany().getName(),form.getInternship().getCompany().getEmail()),
                    new ShortCompanyDTO(form.getCompany().getEmail(), form.getCompany().getName(), form.getCompany().getDescription(), form.getCompany().getVat_number()),
                    form.getFormType()
            );
    }
}



