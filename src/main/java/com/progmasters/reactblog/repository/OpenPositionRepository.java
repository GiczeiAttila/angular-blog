package com.progmasters.reactblog.repository;

import com.progmasters.reactblog.domain.OpenPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenPositionRepository extends JpaRepository<OpenPosition, Long> {
}
