package com.intuit.repositories;

import com.intuit.domain.LastAggregation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastAggregationRepository extends CrudRepository<LastAggregation, String> {
}
