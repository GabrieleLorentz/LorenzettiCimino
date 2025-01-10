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

    @Column(unique = true, nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private Long vat_number;
    @Id
    private String email;
    @Column(unique = false, nullable = false)
    private String password;
    @Column(unique = false, nullable = true)
    private String description;

}
