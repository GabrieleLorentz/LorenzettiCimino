package com.example.s_and_c.DTO.StudentDTOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO for student updated data
 */
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
    private List<String> cv;
    private String newToken;

    /**
     * Constructor without token
     * @param name student name
     * @param surname student surname
     * @param email student email
     * @param password student password
     * @param description student description
     * @param cv student cv
     */
    public UpdatedStudentDTO(String name, String surname, String email, String password, String description, List<String> cv) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.description = description;
        this.cv = cv;
    }
}
