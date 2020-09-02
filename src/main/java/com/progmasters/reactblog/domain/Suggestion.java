package com.progmasters.reactblog.domain;

import com.progmasters.reactblog.domain.dto.SuggestionFormDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Suggestion {

    @Id
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private SuggestionStatusEnum status;

    @OneToMany(mappedBy = "suggestion")
    private List<SuggestionVote> voteList;
    
    public Suggestion(SuggestionFormDto suggestionFormDto, User user) {
        this.title = suggestionFormDto.getTitle();
        this.description = suggestionFormDto.getDescription();
        this.status = SuggestionStatusEnum.ACTIVE;
        this.voteList = new ArrayList<>();
        this.user = user;
    }

    public Suggestion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<SuggestionVote> getVoteList() {
        return voteList;
    }

    public void setVoteList(List<SuggestionVote> voteList) {
        this.voteList = voteList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SuggestionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(SuggestionStatusEnum status) {
        this.status = status;
    }
}
