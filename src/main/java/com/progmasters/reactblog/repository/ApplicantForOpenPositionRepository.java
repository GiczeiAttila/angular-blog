package com.progmasters.reactblog.repository;

import com.progmasters.reactblog.domain.ApplicantForOpenPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantForOpenPositionRepository extends JpaRepository<ApplicantForOpenPosition, Long> {
}
