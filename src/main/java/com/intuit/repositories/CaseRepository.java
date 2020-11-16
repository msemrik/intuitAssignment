package com.intuit.repositories;

import com.intuit.domain.Case;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseRepository extends CrudRepository<Case, Long>, JpaSpecificationExecutor<Case> {
}
