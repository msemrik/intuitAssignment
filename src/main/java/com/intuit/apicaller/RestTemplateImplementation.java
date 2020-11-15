package com.intuit.apicaller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.exceptions.ApiCallException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RestTemplateImplementation implements ApiCaller{

    @Override
    public List<Map<String, Object>> getAPICallToURL(String url) throws ApiCallException {
        String jsonResponse;
        try {
            RestTemplate restTemplate = new RestTemplate();
            jsonResponse = restTemplate.getForObject(url, String.class);
        } catch(HttpClientErrorException e) {
            throw new ApiCallException("Error while performing ApiCall. ApiCall returned status code: " + e.getRawStatusCode(), e);
        } catch (Exception e) {
            throw new ApiCallException("Error while performing ApiCall", e);
        }

        return convertResponseToListOfMaps(jsonResponse);
    }

    private List<Map<String, Object>> convertResponseToListOfMaps(String jsonResponse) throws ApiCallException {
        try {
            List<Map<String, Object>> listToBeReturned;

            if (jsonResponse.startsWith("[")) {
                ObjectMapper mapper = new ObjectMapper();
                listToBeReturned = mapper.readValue(jsonResponse, List.class);
            } else {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> mapToBeReturned = mapper.readValue(jsonResponse, Map.class);
                listToBeReturned = new ArrayList<>();
                listToBeReturned.add(mapToBeReturned);
            }

            return listToBeReturned;
        } catch (Exception e) {
            throw new ApiCallException("Error while converting response to Map ApiCall", e);
        }
    }
}
