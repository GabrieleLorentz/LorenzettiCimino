package com.example.s_and_c.DTO.FormDTO;

import com.example.s_and_c.DTO.CompanyDTOs.ShortCompanyDTO;
import com.example.s_and_c.DTO.InternshipDTOs.ShortInternshipDTO;
import com.example.s_and_c.DTO.StudentDTOS.ShortStudentDTO;
import com.example.s_and_c.Entities.Status.FormType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO with all form info, with short info about users involved
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FormCompleteDTO {
    private long formId;
    private String request;
    private String response;
    private ShortInternshipDTO internship;
    private ShortStudentDTO student;
    private ShortCompanyDTO company;
    private FormType formType;

    /**
     * Constructor without company
     * @param formId formId
     * @param request request
     * @param response response inserted
     * @param internship referred internship
     * @param student interested student
     * @param formType type of form
     */
    public FormCompleteDTO(long formId, String request, String response, ShortInternshipDTO internship, ShortStudentDTO student, FormType formType) {
        this.formId = formId;
        this.request = request;
        this.response = response;
        this.internship = internship;
        this.student = student;
        this.formType = formType;
    }

    /**
     * Constructor without student
     * @param formId formId
     * @param request request
     * @param response response inserted
     * @param internship referred internship
     * @param company interested company
     * @param formType type of form
     */
    public FormCompleteDTO(long formId, String request, String response, ShortInternshipDTO internship, ShortCompanyDTO company, FormType formType) {
        this.formId = formId;
        this.request = request;
        this.response = response;
        this.internship = internship;
        this.company = company;
        this.formType = formType;
    }
    /**
     * Constructor without student, company and internship
     * @param formId formId
     * @param request request
     * @param response response inserted
     * @param formType type of form
     */
    public FormCompleteDTO(long formId, String request, String response, ShortStudentDTO shortStudentDTO, FormType formType) {
        this.formId = formId;
        this.request = request;
        this.response = response;
        this.student = shortStudentDTO;
        this.formType = formType;
    }

    /**
     * Constructor without student and company
     * @param formId formId
     * @param request request
     * @param response response inserted
     * @param shortInternshipDTO internship
     * @param formType type of form
     */
    public FormCompleteDTO(long formId, String request, String response, ShortInternshipDTO shortInternshipDTO, FormType formType) {
        this.formId = formId;
        this.request = request;
        this.response = response;
        this.internship = shortInternshipDTO;
        this.formType = formType;
    }
}
