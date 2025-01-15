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
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Company")
public class Company implements UserDetails {

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
    @Enumerated(EnumType.STRING)
    private Role role;

    public Company(String name, String email, String password, String description, Long vat_number) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.description = description;
        this.vat_number = vat_number;
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
        return Collections.singletonList(new SimpleGrantedAuthority("COMPANY"));

    }
}
