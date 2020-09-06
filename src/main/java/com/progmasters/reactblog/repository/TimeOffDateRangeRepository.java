package com.progmasters.reactblog.repository;

import com.progmasters.reactblog.domain.TimeOffDateRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeOffDateRangeRepository extends JpaRepository<TimeOffDateRange, Long> {

}
