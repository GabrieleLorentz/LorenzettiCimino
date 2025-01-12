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
@Table(name = "Student")
public class Student {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Id
    private String email;
    @Column(nullable = false)
    private String password;
    private String description;
    @ManyToMany
    @JoinTable(
            name = "student_internship", // Nome della tabella di join
            joinColumns = @JoinColumn(name = "student_email"), // FK verso `students`
            inverseJoinColumns = @JoinColumn(name = "internship_id") // FK verso `internships`
    )
    private List<Internship> internships = new ArrayList<>();

    public List<Internship> getInternships() {
        if (internships == null) {
            internships = new ArrayList<>();
        }
        return internships;
    }
}
