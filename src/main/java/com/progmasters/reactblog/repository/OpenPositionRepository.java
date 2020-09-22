package com.progmasters.reactblog.repository;

import com.progmasters.reactblog.domain.OpenPosition;
import com.progmasters.reactblog.domain.dto.OpenPositionListItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OpenPositionRepository extends JpaRepository<OpenPosition, Long> {

    @Query("SELECT NEW com.progmasters.reactblog.domain.dto.OpenPositionListItemDto(o) " +
            " FROM OpenPosition o WHERE :today < o.deadline AND o.user.id <>:userId AND" +
            " 0 = (SELECT COUNT(a) FROM o.applicants a WHERE a.applicant.id = :userId) ")
    List<OpenPositionListItemDto> findAllActiveOpenPosition(@Param("today") Date today, @Param("userId") Long userId);

    @Query("SELECT  NEW com.progmasters.reactblog.domain.dto.OpenPositionListItemDto(o) " +
            " FROM OpenPosition o WHERE o.user.id =:userId")
    List<OpenPositionListItemDto> findAllByUserId(@Param("userId") Long userId);
}
