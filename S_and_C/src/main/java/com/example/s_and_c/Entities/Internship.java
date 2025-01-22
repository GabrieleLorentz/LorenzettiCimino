package com.example.s_and_c.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Internship")
public class Internship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long internship_id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;
    @Column(nullable = false)
    private int Salary;
    private String qualification_required;
    private String description;
    @ManyToOne(optional = false)
    @JoinColumn(name = "company_email",nullable = false)
    private Company company;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "internship_students",
            joinColumns = @JoinColumn(name = "internship_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> appliedStudents = new ArrayList<>();


    public Internship(String name, LocalDate startDate, LocalDate endDate, int salary, String qualification_required, String description, Company company) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.Salary = salary;
        this.qualification_required = qualification_required;
        this.description = description;
        this.company = company;
    }

    public void addStudent(Student student) {
        appliedStudents.add(student);
    }

    public void deleteStudent(Student student) {
        appliedStudents.remove(student);
    }
}
