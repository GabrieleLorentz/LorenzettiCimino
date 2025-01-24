package com.example.s_and_c.Entities;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public Form(String request, String response, Internship internship) {
        this.request = request;
        this.response = response;
        this.internship = internship;
    }
}
