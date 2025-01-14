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
@Table(name = "Company")
public class Company {

    @Column(nullable = false)
    private String name;
    @Id
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = true)
    private String description;
    @Column(unique = true)
    private Long vat_number;
}
