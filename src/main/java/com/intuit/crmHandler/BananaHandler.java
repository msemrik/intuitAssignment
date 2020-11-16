package com.intuit.crmHandler;

import com.intuit.domain.Case;
import com.intuit.exceptions.ConvertingResponseToDomainException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class BananaHandler extends CRMHandler {
    @Value("${crm.banana.minimumTimeBetweenFullAggregationInMins}")
    private int MINIMUM_TIME_BETWEEN_CALLS;
    @Value("${crm.banana.identifier}")
    private String identifier;
    @Value("${crm.banana.fullAggregationUrl}")
    private String URL;

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getURL() {
        return URL;
    }

    public int getMINIMUM_TIME_BETWEEN_CALLS() {
        return MINIMUM_TIME_BETWEEN_CALLS;
    }

    List<Case> convertResponseListToCases(List listOfArrayOfCases, String crm) throws ConvertingResponseToDomainException {
        List cases = new ArrayList<Case>();

        try {
            for (int i = 0; i < listOfArrayOfCases.size(); i++) {
                cases.addAll(super.convertResponseListToCases((ArrayList<Map<String, Object>>) listOfArrayOfCases.get(i), crm));
            }
        } catch (Exception e) {
            throw new ConvertingResponseToDomainException("Error while trying to convert " + listOfArrayOfCases + "to list of episodes.", e);
        }

        return cases;
    }

}
