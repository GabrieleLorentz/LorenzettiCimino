package com.example.s_and_c.DTO.StudentDTOS;

import com.example.s_and_c.DTO.FormDTO.FormCompleteDTO;
import com.example.s_and_c.DTO.FormDTO.FormDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO to show the public info of a student
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ShortStudentDTO {
    private String email;
    private String name;
    private String surname;
    private String description;
    private List<String> cv;
    private List<FormCompleteDTO> forms;

    /**
     * Constructor without cv and forms
     * @param email student email
     * @param name student name
     * @param surname student surname
     * @param description student description
     */
    public ShortStudentDTO(String email, String name, String surname, String description) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.description = description;
    }

    /**
     * Constructor without forms
     * @param email student email
     * @param name student name
     * @param surname student surname
     * @param description student description
     * @param cv student cv
     */
    public ShortStudentDTO(String email, String name, String surname, String description, List<String> cv) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.description = description;
        this.cv = cv;
    }
}

