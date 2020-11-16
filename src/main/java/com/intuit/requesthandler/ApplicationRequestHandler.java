package com.intuit.requesthandler;

import com.intuit.common.CheckTimeout;
import com.intuit.controller.requestobjects.CaseSearchFilter;
import com.intuit.crmHandler.BananaHandler;
import com.intuit.crmHandler.StrawberryHandler;
import com.intuit.domain.AggregatedResult;
import com.intuit.domain.Case;
import com.intuit.domain.LastAggregation;
import com.intuit.exceptions.ApiCallException;
import com.intuit.exceptions.ConvertingResponseToDomainException;
import com.intuit.repositories.CaseRepository;
import com.intuit.repositories.LastAggregationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApplicationRequestHandler {
    @Autowired
    CaseRepository caseRepository;

    @Autowired
    LastAggregationRepository lastAggregationRepository;

    @Autowired
    BananaHandler bananaHandler;

    @Autowired
    StrawberryHandler strawberryHandler;

    @Autowired
    CheckTimeout checkTimeout;

    @Value("${minimumRefreshTimeInMins}")
    private int MINIMUM_REFRESH_TIME;

    @Value("${relevantProducts}")
    private List<String> relevantProducts;

    @Transactional
    public List<Case> refreshDataForRequest() throws Exception {
        if (checkTimeout.shouldExecuteQuery("refresh", MINIMUM_REFRESH_TIME)) {
            return refreshData();
        } else {
            throw new Exception("Between refresh time it should pass at least " + MINIMUM_REFRESH_TIME + " minutes.");
        }
    }

    @Transactional
    public  List<Case> refreshData() throws ConvertingResponseToDomainException, ApiCallException {
        List<Case> casesList = new ArrayList<>();

        casesList.addAll(strawberryHandler.getListOfCases());
        casesList.addAll(bananaHandler.getListOfCases());
        casesList = casesList.stream().filter(caseToFilter -> relevantProducts.contains(caseToFilter.getProduct().toString())).collect(Collectors.toList());
        caseRepository.saveAll(casesList);
        LastAggregation lastAggregation = new LastAggregation("refresh", LocalDateTime.now());
        lastAggregationRepository.save(lastAggregation);

        return casesList;
    }

    public List<AggregatedResult> getCasesForRequest(CaseSearchFilter caseSearchFilter) throws Exception {
        Specification<Case> caseSpecification = Case.findUsingCaseSearchFilter(caseSearchFilter);

        List<Case> cases = caseRepository.findAll(caseSpecification);

        return AggregatedResult.generateAggregatedResult(cases);
    }
}
