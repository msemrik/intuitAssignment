package com.intuit.apicaller;

import com.intuit.exceptions.ApiCallException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ApiCaller {

    public List<Map<String, Object>> getAPICallToURL(String url) throws ApiCallException;

}