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
@Table(name = "Form")
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long formId;
    private String request;
    private String response;

    @ManyToOne
    @JoinColumn(name = "internship_id")
    private Internship internship;


    @ManyToOne(optional = true)
    @JoinColumn(name = "student_email")
    private Student student;

    @ManyToOne(optional = true)
    @JoinColumn(name = "company_email")
    private Company company;

    private FormType formType;

    public Form(String request, String response, Internship internship) {
        this.request = request;
        this.response = response;
        this.internship = internship;
    }

    public Form(String request, String response, Internship internship, FormType formType) {
        this.request = request;
        this.response = response;
        this.internship = internship;
        this.formType = formType;
    }


}
