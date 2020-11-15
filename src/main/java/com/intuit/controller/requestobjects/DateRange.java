package com.intuit.controller.requestobjects;

import java.time.LocalDate;

public class DateRange {
    private LocalDate startDate;
    private LocalDate endDate;

    public DateRange(LocalDate startDate, LocalDate endDate) throws Exception {
        if (startDate != null && endDate != null && startDate.compareTo(endDate) > 0) {
            throw new Exception("Start date is bigger or equal to End date.");
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}


