package com.progmasters.reactblog.domain;

import com.progmasters.reactblog.domain.dto.SuggestionVoteDto;

import javax.persistence.*;

@Entity
public class SuggestionVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VoteType vote;

    @ManyToOne
    private Suggestion suggestion;

    @ManyToOne
    private User user;


    public SuggestionVote() {
    }

    public SuggestionVote(SuggestionVoteDto suggestionVoteDto, Suggestion suggestion, User user) {
        this.vote = VoteType.valueOf(suggestionVoteDto.getVote());
        this.suggestion = suggestion;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VoteType getVote() {
        return vote;
    }

    public void setVote(VoteType vote) {
        this.vote = vote;
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
