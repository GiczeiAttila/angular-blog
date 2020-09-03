package com.progmasters.reactblog.domain.dto;

public class SuggestionVoteDto {
    private Long suggestionId;
    private Long userId;
    private String vote;
    private Long votingUserId;

    public Long getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(Long suggestionId) {
        this.suggestionId = suggestionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public Long getVotingUserId() {
        return votingUserId;
    }

    public void setVotingUserId(Long votingUserId) {
        this.votingUserId = votingUserId;
    }
}
