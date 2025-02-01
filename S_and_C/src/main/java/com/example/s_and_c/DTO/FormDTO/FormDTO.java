package com.example.s_and_c.DTO.FormDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for form display
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormDTO {
    private long formId;
    private String request;
    private String response;
}
