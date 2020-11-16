package com.intuit.crmHandler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StrawberryHandler extends CRMHandler{
    @Value("${crm.strawberry.minimumTimeBetweenFullAggregationInMins}")
    private int MINIMUM_TIME_BETWEEN_CALLS;
    @Value("${crm.strawberry.identifier}")
    private String identifier;
    @Value("${crm.strawberry.fullAggregationUrl}")
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
}