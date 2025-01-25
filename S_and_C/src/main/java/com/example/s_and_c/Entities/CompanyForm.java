package com.example.s_and_c.Entities;

import com.example.s_and_c.Entities.Status.FormType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Company_Form")
public class CompanyForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long formId;
    private String request;
    private String response;

    @ManyToOne
    @JoinColumn(name = "internship_id")
    private Internship internship;

    @ManyToMany
    @JoinTable(
            name = "form_company",
            joinColumns = @JoinColumn(name = "form_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id")
    )
    private List<Company> companyList = new ArrayList<>();

    private FormType formType;

    public CompanyForm(String request, String response, Internship internship) {
        this.request = request;
        this.response = response;
        this.internship = internship;
    }

    public void addCompany(Company company) {
        companyList.add(company);
    }
}


