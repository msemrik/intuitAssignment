package com.intuit.requesthandler;


import com.intuit.common.CheckTimeout;
import com.intuit.controller.requestobjects.CaseSearchFilter;
import com.intuit.crmHandler.BananaHandler;
import com.intuit.crmHandler.StrawberryHandler;
import com.intuit.domain.AggregatedResult;
import com.intuit.domain.Case;
import com.intuit.domain.Product;
import com.intuit.repositories.CaseRepository;
import com.intuit.repositories.LastAggregationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationRequestHandlerTest {

    @Mock
    CheckTimeout checkTimeout;

    @Mock
    CaseRepository caseRepository;

    @Mock
    LastAggregationRepository lastAggregationRepository;

    @Mock
    BananaHandler bananaHandler;

    @Mock
    StrawberryHandler strawberryHandler;

    @Mock
    Case cases;

    @InjectMocks
    ApplicationRequestHandler applicationRequestHandler;

    @Test
    void shouldRefreshDataIsLessTimeThanMinimumRefreshTimePassedSinceLastRefresh() throws Exception {
        ReflectionTestUtils.setField(applicationRequestHandler, "relevantProducts", new ArrayList<String>(Arrays.asList("BLUE")));

        when(checkTimeout.shouldExecuteQuery(anyString(), anyInt())).thenReturn(true);
        List<Case> strawberryListCase = new ArrayList<>();
        Case strawberryFirstCase = new Case(new Case.CaseId(1l, "1"), 1l, Product.BLUE, 1l, 404l, true, LocalDateTime.now(), LocalDateTime.now());
        Case strawberrySecondCase = new Case(new Case.CaseId(1l, "1"), 1l, Product.BLUE, 1l, 404l, true, LocalDateTime.now(), LocalDateTime.now());
        strawberryListCase.add(strawberryFirstCase);
        strawberryListCase.add(strawberrySecondCase);

        List<Case> bananaListCase = new ArrayList<>();
        Case bananaFirstCase = new Case(new Case.CaseId(1l, "2"), 1l, Product.BLUE, 1l, 404l, true, LocalDateTime.now(), LocalDateTime.now());
        Case bananaSecondCase = new Case(new Case.CaseId(1l, "2"), 1l, Product.BLUE, 1l, 404l, true, LocalDateTime.now(), LocalDateTime.now());
        bananaListCase.add(bananaFirstCase);
        bananaListCase.add(bananaSecondCase);

        when(strawberryHandler.getListOfCases()).thenReturn(strawberryListCase);
        when(bananaHandler.getListOfCases()).thenReturn(bananaListCase);

        List<Case> returnedCases = applicationRequestHandler.refreshDataForRequest();

        strawberryListCase.addAll(bananaListCase);
        assertEquals(strawberryListCase, returnedCases);
        verify(strawberryHandler, times(1)).getListOfCases();
        verify(bananaHandler, times(1)).getListOfCases();
    }

    @Test
    void shouldNotRefreshDataIsLessTimeThanMinimumRefreshTimePassedSinceLastRefresh() throws Exception {
        when(checkTimeout.shouldExecuteQuery(anyString(), anyInt())).thenReturn(false);

        assertThatThrownBy(() -> {
            applicationRequestHandler.refreshDataForRequest();
        }).isInstanceOf(Exception.class);

        verify(strawberryHandler, times(0)).getListOfCases();
        verify(bananaHandler, times(0)).getListOfCases();
    }

    @Test
    void getCasesForRequest() throws Exception {
        List<Case> listOfCases = new ArrayList<>();
        Case caseWithCertainErrorCodeAndProvider = new Case(new Case.CaseId(1l, "1"), 1l, Product.BLUE, 1l, 404l, true, LocalDateTime.now(), LocalDateTime.now());
        Case caseWithSameErrorCodeAndProvider = new Case(new Case.CaseId(1l, "1"), 1l, Product.RED, 1l, 404l, true, LocalDateTime.now(), LocalDateTime.now());
        Case caseWithDifferentErrorCodeAndSameProvider = new Case(new Case.CaseId(1l, "1"), 1l, Product.BLUE, 1l, 999l, true, LocalDateTime.now(), LocalDateTime.now());
        Case caseWithSameErrorCodeAndDifferentProvider = new Case(new Case.CaseId(1l, "1"), 1l, Product.BLUE, 999l, 404l, true, LocalDateTime.now(), LocalDateTime.now());
        Case caseWithDifferentErrorCodeAndDifferentProvider = new Case(new Case.CaseId(1l, "1"), 1l, Product.BLUE, 999l, 999l, true, LocalDateTime.now(), LocalDateTime.now());
        listOfCases.add(caseWithCertainErrorCodeAndProvider);
        listOfCases.add(caseWithSameErrorCodeAndProvider);
        listOfCases.add(caseWithDifferentErrorCodeAndSameProvider);
        listOfCases.add(caseWithSameErrorCodeAndDifferentProvider);
        listOfCases.add(caseWithDifferentErrorCodeAndDifferentProvider);
        when(caseRepository.findAll(any())).thenReturn(listOfCases);

        List<AggregatedResult> aggregatedResultList = applicationRequestHandler.getCasesForRequest(new CaseSearchFilter());

        List<Case> onlyExpectedList = new ArrayList<>();
        onlyExpectedList.add(caseWithCertainErrorCodeAndProvider);
        onlyExpectedList.add(caseWithSameErrorCodeAndProvider);
        assertEquals(4, aggregatedResultList.size());
        assertEquals(new AggregatedResult(999l, 999l, "BLUE",1, new ArrayList<>(Arrays.asList(caseWithDifferentErrorCodeAndDifferentProvider))), aggregatedResultList.get(0));
        assertEquals(new AggregatedResult(1l, 404l, "BLUE,RED",2, onlyExpectedList), aggregatedResultList.get(1));
        assertEquals(new AggregatedResult(1l, 999l, "BLUE", 1,new ArrayList<>(Arrays.asList(caseWithDifferentErrorCodeAndSameProvider))), aggregatedResultList.get(2));
        assertEquals(new AggregatedResult(999l, 404l, "BLUE",1, new ArrayList<>(Arrays.asList(caseWithSameErrorCodeAndDifferentProvider))), aggregatedResultList.get(3));
    }
}