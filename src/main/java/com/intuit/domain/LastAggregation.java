package com.intuit.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "last_aggregation")
public class LastAggregation implements Serializable {

    @Id
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private LocalDateTime lastAggregationDate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getLastAggregationDate() {
        return lastAggregationDate;
    }

    public void setLastAggregationDate(LocalDateTime lastAggregationDate) {
        this.lastAggregationDate = lastAggregationDate;
    }

    public LastAggregation(String type, LocalDateTime lastAggregationDate) {
        this.type = type;
        this.lastAggregationDate = lastAggregationDate;
    }

    public LastAggregation() {
    }
}


