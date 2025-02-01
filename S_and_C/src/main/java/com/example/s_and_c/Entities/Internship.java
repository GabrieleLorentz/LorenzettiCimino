package com.example.s_and_c.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Internship entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Internship")
public class Internship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long internshipId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private int salary;

    @Column(nullable = false)
    private LocalDate endFormCompilingDate;

    @Column(nullable = false)
    private LocalDate endSelectionAcceptanceDate;

    @OneToMany(mappedBy = "internship",fetch = FetchType.LAZY)
    private List<Qualification> qualification_required;

    private String description;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "company_email",nullable = false)
    private Company company;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "internship_applied_Student",
            joinColumns = @JoinColumn(name = "internship_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> appliedStudents = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "internship_accepted_Students",
            joinColumns = @JoinColumn(name = "internship_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> acceptedStudents = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "internship_selected_Students",
            joinColumns = @JoinColumn(name = "internship_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> selectedStudents = new ArrayList<>();

    @OneToMany(mappedBy = "internship")
    private List<Form> form = new ArrayList<>();

    /**
     * Short constructor with internship insert data
     * @param name name
     * @param startDate start date
     * @param endDate end date
     * @param salary salary
     * @param qualification_required qualification required
     * @param description description
     * @param company company
     */
    public Internship(String name, LocalDate startDate, LocalDate endDate, int salary, List<Qualification> qualification_required, String description, Company company) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.salary = salary;
        this.qualification_required = qualification_required;
        this.description = description;
        this.company = company;
    }

    /**
     * Add an apply Student
     * @param student student
     */
    public void addAppliedStudent(Student student) {
        appliedStudents.add(student);
    }
    /**
     * Delete an apply Student
     * @param student student
     */
    public void deleteAppliedStudent(Student student) {
        appliedStudents.remove(student);
    }
    /**
     * Add an accepted Student
     * @param student student
     */
    public void addAcceptedStudent(Student student) {
        acceptedStudents.add(student);
    }
    /**
     * Delete an accepted Student
     * @param student student
     */
    public void deleteAcceptedStudent(Student student) {
        acceptedStudents.remove(student);
    }
    /**
     * Add a selected Student
     * @param student student
     */
    public void addSelectedStudent(Student student) {
        selectedStudents.add(student);
    }
    /**
     * Delete a selected Student
     * @param student student
     */
    public void deleteSelectedStudent(Student student) {
        selectedStudents.remove(student);
    }

}
