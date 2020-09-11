package com.progmasters.reactblog.repository;

import com.progmasters.reactblog.domain.SuggestionVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuggestionVoteRepository extends JpaRepository<SuggestionVote, Long> {
    @Query("SELECT sv FROM SuggestionVote sv WHERE sv.user.id=:votingUserId AND sv.suggestion.id=:suggestionId")
    Optional<SuggestionVote> findVoteByUserIdAndSuggestionId(Long votingUserId, Long suggestionId);
}
