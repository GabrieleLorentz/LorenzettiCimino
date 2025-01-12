package com.example.s_and_c.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Date start_date;
    @Column(nullable = false)
    private Date end_date;
    @Column(nullable = false)
    private int Salary;
    private String qualification_required;
    private String description;
    @OneToOne
    private Company company;
    @ManyToMany(mappedBy = "internships")
    private List<Student> students = new ArrayList<>();

}
