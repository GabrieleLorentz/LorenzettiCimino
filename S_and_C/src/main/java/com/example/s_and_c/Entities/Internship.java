package com.example.s_and_c.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
    private LocalDate start_date;
    @Column(nullable = false)
    private LocalDate end_date;
    @Column(nullable = false)
    private int Salary;
    private String qualification_required;
    private String description;
    @OneToOne
    private Company company;

    public Internship(String name, LocalDate start_date, LocalDate end_date, int salary, String qualification_required, String description, Company company) {
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.Salary = salary;
        this.qualification_required = qualification_required;
        this.description = description;
        this.company = company;
    }
}
