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
@Table(name = "Qualification")
public class Qualification {
    @Id
    private String qualificationName;

    @ManyToOne
    @JoinColumn(name = "internship_id")
    private Internship internship;

}
