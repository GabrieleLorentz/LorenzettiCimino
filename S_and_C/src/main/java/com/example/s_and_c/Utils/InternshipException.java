package com.example.s_and_c.Utils;

import lombok.Getter;

@Getter
public class InternshipException extends RuntimeException {
    private final int statusCode;

    public InternshipException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}
