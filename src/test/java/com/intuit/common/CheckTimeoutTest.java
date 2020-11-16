package com.intuit.common;

import com.intuit.domain.LastAggregation;
import com.intuit.repositories.LastAggregationRepository;
import com.intuit.requesthandler.ApplicationRequestHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckTimeoutTest {

    @Mock
    LastAggregationRepository lastAggregationRepository;

    @InjectMocks
    CheckTimeout checkTimeout;

    @Test
    void shouldReturnTrueIfTimeBetweenCallsIsGreaterThanThreshold() throws Exception {
        LastAggregation lastAggregation = new LastAggregation("id", LocalDateTime.now().minusDays(1));
        when(lastAggregationRepository.findById(any())).thenReturn(Optional.of(lastAggregation));

        Boolean shouldExecute = checkTimeout.shouldExecuteQuery("id", 1);

        assertEquals(true, shouldExecute);
    }

    @Test
    void shouldReturnFalseIfTimeBetweenCallsIsGreaterThanThreshold() throws Exception {
        LastAggregation lastAggregation = new LastAggregation("id", LocalDateTime.now().minusMinutes(9));
        when(lastAggregationRepository.findById(any())).thenReturn(Optional.of(lastAggregation));

        Boolean shouldExecute = checkTimeout.shouldExecuteQuery("id", 10);

        assertEquals(false, shouldExecute);
    }

}