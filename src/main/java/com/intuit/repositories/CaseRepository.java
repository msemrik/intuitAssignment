package com.intuit.repositories;

import com.intuit.domain.Case;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseRepository extends CrudRepository<Case, Long> {
//    @Query("SELECT c FROM ShowUser su, Show s JOIN s.cast as c  WHERE su.user.userId = :userId AND su.show = s GROUP BY c ORDER BY COUNT(c) DESC")
//    List<Case> findMostRepeatedCastInShowsScheduledByUser(@Param("userId") Long userId);
}
