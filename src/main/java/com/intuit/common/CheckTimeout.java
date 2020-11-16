package com.intuit.common;

import com.intuit.domain.LastAggregation;
import com.intuit.repositories.LastAggregationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class CheckTimeout {

    @Autowired
    LastAggregationRepository lastAggregationRepository;

    public boolean shouldExecuteQuery(String identifier, int timeThatShouldHadPassed) {
        LastAggregation lastAggregation = lastAggregationRepository.findById(identifier).orElse(new LastAggregation("", LocalDateTime.MIN));

        long minutes = lastAggregation.getLastAggregationDate().until( LocalDateTime.now(), ChronoUnit.MINUTES );
        if(minutes < timeThatShouldHadPassed) {
            return false;
        } else {
            return true;
        }
    }

//    public boolean shouldExecute() {
//        return true
//    }
}
