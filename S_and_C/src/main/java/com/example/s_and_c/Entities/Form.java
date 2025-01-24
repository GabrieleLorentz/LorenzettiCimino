package com.example.s_and_c.Entities;



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

    @ManyToMany
    @JoinTable(
            name = "form_student",
            joinColumns = @JoinColumn(name = "form_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> studentList = new ArrayList<>();

    public Form(String request, String response, Internship internship) {
        this.request = request;
        this.response = response;
        this.internship = internship;
    }

    public void addStudent(Student student) {
        studentList.add(student);
    }
}
