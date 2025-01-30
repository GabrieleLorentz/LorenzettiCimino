package com.example.s_and_c.Utils;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class DateUtils {
    public LocalDate getMidDate(LocalDate startDate, LocalDate endDate) {
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        long daysToAdd = daysBetween / 2;
        return startDate.plusDays(daysToAdd);
    }

}
