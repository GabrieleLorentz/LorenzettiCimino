package com.example.s_and_c.Entities;

import com.example.s_and_c.Entities.Status.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Student")
public class Student implements UserDetails {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Id
    private String email;
    @Getter
    @Column(nullable = false)
    private String password;
    private String description;


    @Enumerated(EnumType.STRING)
    private Role role;

    public Student(String name, String surname, String email, String password, String description) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.description = description;
    }




    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(Role.STUDENT.toString()));

    }

}
