package com.progmasters.reactblog.repository;

import com.progmasters.reactblog.domain.TimeOffDateRange;
import com.progmasters.reactblog.domain.TimeOffStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeOffDateRangeRepository extends JpaRepository<TimeOffDateRange, Long> {

    @Query("SELECT t FROM TimeOffDateRange t WHERE t.user.id = :id ORDER BY t.startDate ASC")
    List<TimeOffDateRange> findTimeOffsByUserId(@Param("id") Long id);

    @Query("SELECT t FROM TimeOffDateRange t WHERE t.status = :status")
    List<TimeOffDateRange> findAccepted(@Param("status") TimeOffStatusEnum status);

    @Query("SELECT t FROM TimeOffDateRange t WHERE t.status = :status")
    List<TimeOffDateRange> findPending(@Param("status") TimeOffStatusEnum status);
}
