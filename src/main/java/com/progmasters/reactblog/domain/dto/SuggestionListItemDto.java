package com.progmasters.reactblog.domain.dto;

import com.progmasters.reactblog.domain.Suggestion;
import com.progmasters.reactblog.domain.SuggestionStatusEnum;
import com.progmasters.reactblog.domain.VoteType;

public class SuggestionListItemDto {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private Long countUp;
    private Long countDown;
    private SuggestionStatusEnum status;

    public SuggestionListItemDto() {
    }

    public SuggestionListItemDto(Suggestion suggestion) {
        this.id = suggestion.getId();
        this.userId = suggestion.getUser().getId();
        this.title = suggestion.getTitle();
        this.description = suggestion.getDescription();
        this.countUp = suggestion.getVoteList().stream().filter((suggestionVote -> suggestionVote.getVote()== VoteType.UP)).count();
        this.countDown = suggestion.getVoteList().stream().filter((suggestionVote -> suggestionVote.getVote()== VoteType.DOWN)).count();
        this.status = suggestion.getStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCountUp() {
        return countUp;
    }

    public void setCountUp(Long countUp) {
        this.countUp = countUp;
    }

    public Long getCountDown() {
        return countDown;
    }

    public void setCountDown(Long countDown) {
        this.countDown = countDown;
    }

    public SuggestionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(SuggestionStatusEnum status) {
        this.status = status;
    }
}
