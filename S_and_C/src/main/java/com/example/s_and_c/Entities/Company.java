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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Company entity
 */
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
    @Column
    private String description;
    @Column(unique = true)
    private Long vat_number;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Internship> internships = new ArrayList<>();

    /**
     * Short constructor, with registration data
     * @param name name
     * @param email email
     * @param password password
     * @param description description
     * @param vat_number vat number
     */
    public Company(String name, String email, String password, String description, Long vat_number) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.description = description;
        this.vat_number = vat_number;
    }

    /**
     * UserDetails getUsername override
     * @return email
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * UserDetails isAccountNonExpired override
     * @return true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * UserDetails isAccountNonLocked override
     * @return true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * UserDetails isCredentialsNonExpired override
     * @return true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * UserDetails isEnabled override
     * @return true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * UserDetails isEnabled override
     * @return authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(Role.COMPANY.toString()));

    }
}
