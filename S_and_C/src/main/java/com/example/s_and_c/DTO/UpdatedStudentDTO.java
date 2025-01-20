package com.example.s_and_c.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedStudentDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String description;
    private String newToken;

    public UpdatedStudentDTO(String name, String surname, String email, String password, String description) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.description = description;
    }
}
