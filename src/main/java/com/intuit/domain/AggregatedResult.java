package com.intuit.domain;

import javax.persistence.Column;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class AggregatedResult {

    private Long providerId;
    private Long createdErrorCode;
    private String products;
    private int count;
    List<Case> cases;

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Long getCreatedErrorCode() {
        return createdErrorCode;
    }

    public void setCreatedErrorCode(Long createdErrorCode) {
        this.createdErrorCode = createdErrorCode;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Case> getCases() {
        return cases;
    }

    public void setCases(List<Case> cases) {
        this.cases = cases;
    }

    public AggregatedResult() {
    }

    public AggregatedResult(Long providerId, Long createdErrorCode, String products, int count, List<Case> cases) {
        this.providerId = providerId;
        this.createdErrorCode = createdErrorCode;
        this.products = products;
        this.count = count;
        this.cases = cases;
    }

    public static List<AggregatedResult> generateAggregatedResult(List<Case> caseList) {
        Function<Case, List<Object>> compositeKey = actualCase ->
                Arrays.<Object>asList(actualCase.getProviderId(), actualCase.getCreatedErrorCode());
        Map<Object, List<Case>> collect = caseList.stream().collect(groupingBy(compositeKey, toList()));


        List<AggregatedResult> aggregatedResults = new ArrayList<>();

        for (Map.Entry<Object, List<Case>> pair : collect.entrySet()) {
            List<Long> keys = (List<Long>) pair.getKey();
            List<Case> cases = pair.getValue();
            int count = cases.size();
            String products = pair.getValue().stream().map( actualCase -> actualCase.getProduct().toString()).collect( Collectors.joining( "," ) );
            aggregatedResults.add(new AggregatedResult(keys.get(0), keys.get(1), products, count, cases));
        }

        return aggregatedResults;
    }

    @Override
    public String toString() {
        return "AggregatedResult{" +
                "providerId=" + providerId +
                ", createdErrorCode=" + createdErrorCode +
                ", products='" + products + '\'' +
                ", count=" + count +
                ", cases=" + cases +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AggregatedResult that = (AggregatedResult) o;
        return count == that.count &&
                Objects.equals(providerId, that.providerId) &&
                Objects.equals(createdErrorCode, that.createdErrorCode) &&
                Objects.equals(products, that.products) &&
                Objects.equals(cases, that.cases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerId, createdErrorCode, products, count, cases);
    }
}
