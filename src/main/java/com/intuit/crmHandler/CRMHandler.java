package com.intuit.crmHandler;

import com.intuit.controller.requestobjects.filters.Product;
import com.intuit.domain.Case;
import com.intuit.exceptions.ApiCallException;
import com.intuit.exceptions.ConvertingResponseToDomainException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CRMHandler {

    List<Case> getListOfCases() throws ApiCallException, ConvertingResponseToDomainException;

    default List<Case> convertResponseToCases(List<Map<String, Object>> episodesList, String crm) throws ConvertingResponseToDomainException {
        List episodes = new ArrayList<Case>();

        try {
            for (Map<String, Object> casesMap : episodesList) {
                episodes.add(convertResponseToCase(casesMap, crm));
            }
            return episodes;
        } catch (Exception e) {
            throw new ConvertingResponseToDomainException("Error while trying to convert " + episodesList + "to list of episodes.", e);
        }
    }

    default Case convertResponseToCase(Map<String, Object> casesMap, String crm) throws ConvertingResponseToDomainException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

            Long caseId = new Long((Integer) casesMap.get("CaseID"));
            Long customerId = new Long((Integer) casesMap.get("CustomerID"));
            Long provider = new Long((Integer) casesMap.get("Provider"));
            Long createdErrorCode = new Long((Integer) casesMap.get("CREATED_ERROR_CODE"));
            Boolean isOpen = ((String) casesMap.get("STATUS")).equals("Closed")? false : true;
            // TODO fix this
//            LocalDateTime ticketCreationDate =  LocalDateTime.parse(((String) casesMap.get("TICKET_CREATION_DATE")),
//                    formatter);
//            LocalDateTime lastModifiedDate = LocalDateTime.parse(((String) casesMap.get("LAST_MODIFIED_DATE")),
//                    formatter);
            LocalDateTime ticketCreationDate =  LocalDateTime.now();
            LocalDateTime lastModifiedDate = LocalDateTime.now();
            Product product = Product.valueOf(((String) casesMap.get("PRODUCT_NAME")));

            Case caseToReturn = null;
//            TODO implement correct filter (if product not in)
//            if(product != Product.ORANGE && product != P ) {
                caseToReturn = new Case(new Case.CaseId(caseId, crm), customerId, product, provider, createdErrorCode,
                        isOpen,
                        ticketCreationDate,
                        lastModifiedDate);
//            }

            return caseToReturn;
        } catch (Exception e) {
            throw new ConvertingResponseToDomainException("Error while trying to convert " + casesMap + "to episode.", e);
        }
    }
}
