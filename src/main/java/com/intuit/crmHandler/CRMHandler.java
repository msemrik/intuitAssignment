package com.intuit.crmHandler;

import com.intuit.apicaller.ApiCaller;
import com.intuit.common.CheckTimeout;
import com.intuit.domain.LastAggregation;
import com.intuit.domain.Product;
import com.intuit.domain.Case;
import com.intuit.exceptions.ApiCallException;
import com.intuit.exceptions.ConvertingResponseToDomainException;
import com.intuit.repositories.LastAggregationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class CRMHandler {
    @Autowired
    ApiCaller apiCaller;

    @Autowired
    CheckTimeout checkTimeout;

    @Autowired
    LastAggregationRepository lastAggregationRepository;

    abstract String getURL();
    abstract String getIdentifier();
    abstract int getMINIMUM_TIME_BETWEEN_CALLS();

    public List<Case> getListOfCases() throws ApiCallException, ConvertingResponseToDomainException {
        if(checkTimeout.shouldExecuteQuery(getIdentifier() + "FullAggregation", getMINIMUM_TIME_BETWEEN_CALLS())) {
            List<Case> caseList = convertResponseListToCases(apiCaller.getAPICallToURL(getURL()), getIdentifier());

            lastAggregationRepository.save(new LastAggregation(getIdentifier()  + "FullAggregation", LocalDateTime.now()));

            return caseList;
        } else {
            System.out.println("Won't do API call to CRM " + getIdentifier() + " as the time between calls restriction was not reached.");
            return new ArrayList<Case>();
        }
    }

    List<Case> convertResponseListToCases(List<Map<String, Object>> casesList, String crm) throws ConvertingResponseToDomainException {
        List cases = new ArrayList<Case>();

        try {
            for (Map<String, Object> caseMap : casesList) {
                cases.add(convertMapToCase(caseMap, crm));
            }
            return cases;
        } catch (Exception e) {
            throw new ConvertingResponseToDomainException("Error while trying to convert " + casesList + "to list of cases.", e);
        }
    }

    Case convertMapToCase(Map<String, Object> casesMap, String crm) throws ConvertingResponseToDomainException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy HH:mm");

            Long caseId = new Long((Integer) casesMap.get("CaseID"));
            Long customerId = new Long((Integer) casesMap.get("CustomerID"));
            Long provider = new Long((Integer) casesMap.get("Provider"));
            Long createdErrorCode = new Long((Integer) casesMap.get("CREATED_ERROR_CODE"));
            Boolean isOpen = casesMap.get("STATUS").equals("Closed")? false : true;
            LocalDateTime ticketCreationDate =  LocalDateTime.parse(((String) casesMap.get("TICKET_CREATION_DATE")),
                    formatter);
            LocalDateTime lastModifiedDate = LocalDateTime.parse(((String) casesMap.get("LAST_MODIFIED_DATE")),
                    formatter);
            Product product = Product.valueOf(((String) casesMap.get("PRODUCT_NAME")));

            return new Case(new Case.CaseId(caseId, crm), customerId, product, provider, createdErrorCode,
                        isOpen,
                        ticketCreationDate,
                        lastModifiedDate);
        } catch (Exception e) {
            throw new ConvertingResponseToDomainException("Error while trying to convert " + casesMap + "to case.", e);
        }
    }
}
