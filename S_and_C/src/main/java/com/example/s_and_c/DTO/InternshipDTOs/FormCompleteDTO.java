package com.example.s_and_c.DTO.InternshipDTOs;

import com.example.s_and_c.DTO.CompanyDTOs.ShortCompanyDTO;
import com.example.s_and_c.DTO.StudentDTOS.ShortStudentDTO;
import com.example.s_and_c.Entities.Status.FormType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FormCompleteDTO {
    private long formId;
    private String request;
    private String response;
    private ShortStudentDTO student;
    private ShortCompanyDTO company;
    private FormType formType;

    public FormCompleteDTO(long formId, String request, String response, ShortStudentDTO student, FormType formType) {
        this.formId = formId;
        this.request = request;
        this.response = response;
        this.student = student;
        this.formType = formType;
    }

    public FormCompleteDTO(long formId, String request, String response, ShortCompanyDTO company, FormType formType) {
        this.formId = formId;
        this.request = request;
        this.response = response;
        this.company = company;
        this.formType = formType;
    }
}
