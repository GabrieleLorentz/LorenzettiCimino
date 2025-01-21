package com.example.s_and_c.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    private String keyword;
    private String min_salary;
    private Date min_start;
    private Date max_end;
    private List<String> qualification;
    private String company_name;
}
