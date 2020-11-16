package com.intuit.crmHandler;


import com.intuit.apicaller.ApiCaller;
import com.intuit.common.CheckTimeout;
import com.intuit.controller.requestobjects.CaseSearchFilter;
import com.intuit.domain.AggregatedResult;
import com.intuit.domain.Case;
import com.intuit.domain.Product;
import com.intuit.repositories.CaseRepository;
import com.intuit.repositories.LastAggregationRepository;
import com.intuit.requesthandler.ApplicationRequestHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CRMHandlerTest {

    @Mock
    ApiCaller apiCaller;

    @Mock
    CheckTimeout checkTimeout;

    @Mock
    LastAggregationRepository lastAggregationRepository;

    @InjectMocks
    StrawberryHandler crmHandler;

    @Test
    void shouldCallToApiWithCorrectURLIfEnoughTimePassedSinceLastCall() throws Exception {
        final String EXAMPLE_URL = "http://example.url.com";
        final String EXAMPLE_IDENTIFIER = "Apple";
        ReflectionTestUtils.setField(crmHandler, "URL", EXAMPLE_URL);
        ReflectionTestUtils.setField(crmHandler, "identifier", EXAMPLE_IDENTIFIER);
        List<Map<String,Object>> linkedHashMaps = new ArrayList<>();
        LinkedHashMap<String, Object> caseHashMap = new LinkedHashMap<>();
        caseHashMap.put("CaseID", 1);
        caseHashMap.put("CustomerID", 818591);
        caseHashMap.put("Provider", 10001121);
        caseHashMap.put("CREATED_ERROR_CODE", 101);
        caseHashMap.put("STATUS", "Closed");
        caseHashMap.put("TICKET_CREATION_DATE", "4/1/2019 17:25");
        caseHashMap.put("LAST_MODIFIED_DATE", "4/2/2019 08:00");
        caseHashMap.put("PRODUCT_NAME", "RED");
        linkedHashMaps.add(caseHashMap);

        when(apiCaller.getAPICallToURL(any())).thenReturn(linkedHashMaps);
        when(checkTimeout.shouldExecuteQuery(any(), any())).thenReturn(true);

        List<Case> cases = crmHandler.getListOfCases();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy HH:mm");
        assertEquals(new Case(new Case.CaseId(1l, EXAMPLE_IDENTIFIER),
                818591l,
                Product.RED,
                10001121l,
                101l,
                false,
                LocalDateTime.parse( "4/1/2019 17:25",formatter),
                LocalDateTime.parse(  "4/2/2019 08:00",formatter)).toString(), cases.get(0).toString());
        verify(apiCaller, times(1)).getAPICallToURL(EXAMPLE_URL);
        verify(lastAggregationRepository, times(1)).save(any());
    }

    @Test
    void shouldNotCallToApiWithCorrectURLIfNotEnoughTimePassedSinceLastCall() throws Exception {
        when(checkTimeout.shouldExecuteQuery(any(), any())).thenReturn(false);

        crmHandler.getListOfCases();

        verify(apiCaller, times(0)).getAPICallToURL(any());
    }
}