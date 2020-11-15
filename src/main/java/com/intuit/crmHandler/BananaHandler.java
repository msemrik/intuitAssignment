package com.intuit.crmHandler;

import com.intuit.apicaller.ApiCaller;
import com.intuit.domain.Case;
import com.intuit.exceptions.ApiCallException;
import com.intuit.exceptions.ConvertingResponseToDomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class BananaHandler implements CRMHandler{
    @Autowired
    ApiCaller apiCaller;

    @Override
    public List<Case> getListOfCases() throws ApiCallException, ConvertingResponseToDomainException {
        String URL = "https://fakebanky.herokuapp.com/fruit/strawberry";
        return this.convertResponseToCases(apiCaller.getAPICallToURL(URL), "banana");
    }


}
