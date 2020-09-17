package com.progmasters.reactblog.service;

import com.progmasters.reactblog.domain.Suggestion;
import com.progmasters.reactblog.domain.SuggestionStatusEnum;
import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.UserStatusEnum;
import com.progmasters.reactblog.domain.dto.SuggestionFormDto;
import com.progmasters.reactblog.domain.dto.SuggestionStatusChangeDto;
import com.progmasters.reactblog.repository.SuggestionRepository;
import com.progmasters.reactblog.repository.SuggestionVoteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class SuggestionServiceTest {

    private SuggestionService suggestionService;

    @Mock
    private UserService userServiceMock;
    @Mock
    private SuggestionRepository suggestionRepositoryMock;
    @Mock
    private EmailSenderService emailSenderServiceMock;
    @Mock
    private SuggestionVoteRepository suggestionVoteRepositoryMock;

    @BeforeEach
    public void setUp() {
        suggestionService = new SuggestionService(userServiceMock, suggestionRepositoryMock, emailSenderServiceMock, suggestionVoteRepositoryMock);
    }

    @Test
    void testSaveSuggestionWithExistingUser() {
        SuggestionFormDto suggestionFormDto = SuggestionFormDtoFactory.getSuggestionFormDto(1L);
        Suggestion suggestion = SuggestionFactory.getSuggestion(suggestionFormDto);
        User user = suggestion.getUser();
        Suggestion suggestionAfterSave = SuggestionFactory.getSuggestion(suggestionFormDto);
        suggestionAfterSave.setId(1L);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(userServiceMock.findById(suggestionFormDto.getUserId())).thenReturn(user);
        when(suggestionRepositoryMock.save(any(Suggestion.class))).thenReturn(suggestionAfterSave);
        when(userServiceMock.findAllUsersWithStatus(UserStatusEnum.ACTIVE)).thenReturn(userList);

        Suggestion savedSuggestion = suggestionService.saveSuggestion(suggestionFormDto);

        Assertions.assertEquals(1L, savedSuggestion.getId());

        verify(userServiceMock, times(1)).findById(1L);
        verify(suggestionRepositoryMock, times(1)).save(any(Suggestion.class));
        verify(userServiceMock, times(1)).findAllUsersWithStatus(UserStatusEnum.ACTIVE);
        verifyNoMoreInteractions(userServiceMock);
        verifyNoMoreInteractions(suggestionRepositoryMock);
    }

    @Test
    void testSaveSuggestionWithNotExistingUser() {
        SuggestionFormDto suggestionFormDto = SuggestionFormDtoFactory.getSuggestionFormDto(1L);
        Suggestion suggestion = SuggestionFactory.getSuggestion(suggestionFormDto);
        User user = suggestion.getUser();
        Suggestion suggestionAfterSave = SuggestionFactory.getSuggestion(suggestionFormDto);

        when(userServiceMock.findById(suggestionFormDto.getUserId())).thenReturn(null);

        Assertions.assertNull(suggestionService.saveSuggestion(suggestionFormDto));

        verify(userServiceMock, times(1)).findById(suggestionFormDto.getUserId());
        verifyNoMoreInteractions(userServiceMock);
    }

    @Test
    void saveVote() {
    }

    @Test
    void changeSuggestionStatusToRejected() {
        Suggestion suggestion = SuggestionFactory.getSuggestion(SuggestionFormDtoFactory.getSuggestionFormDto(1L));
        SuggestionStatusChangeDto suggestionStatusChangeDto = SuggestionStatusChangeDtoFactory.getSuggestionStatusChangeDto(1L,SuggestionStatusEnum.REJECTED);
        when(suggestionRepositoryMock.findById(1L)).thenReturn(Optional.of(suggestion));

        suggestionService.changeSuggestionStatus(suggestionStatusChangeDto);

        Assertions.assertEquals(SuggestionStatusEnum.REJECTED,suggestion.getStatus());

        verify(suggestionRepositoryMock, times(1)).findById(1L);
        verifyNoMoreInteractions(suggestionRepositoryMock);
    }

    @Test
    void changeSuggestionStatusToAccepted() {
        Suggestion suggestion = SuggestionFactory.getSuggestion(SuggestionFormDtoFactory.getSuggestionFormDto(1L));
        SuggestionStatusChangeDto suggestionStatusChangeDto = SuggestionStatusChangeDtoFactory.getSuggestionStatusChangeDto(1L,SuggestionStatusEnum.ACCEPTED);
        when(suggestionRepositoryMock.findById(1L)).thenReturn(Optional.of(suggestion));

        suggestionService.changeSuggestionStatus(suggestionStatusChangeDto);

        Assertions.assertEquals(SuggestionStatusEnum.ACCEPTED,suggestion.getStatus());

        verify(suggestionRepositoryMock, times(1)).findById(1L);
        verifyNoMoreInteractions(suggestionRepositoryMock);
    }

    @Test
    void changeSuggestionStatusToActive() {
        Suggestion suggestion = SuggestionFactory.getSuggestion(SuggestionFormDtoFactory.getSuggestionFormDto(1L));
        suggestion.setStatus(SuggestionStatusEnum.REJECTED);
        SuggestionStatusChangeDto suggestionStatusChangeDto = SuggestionStatusChangeDtoFactory.getSuggestionStatusChangeDto(1L,SuggestionStatusEnum.ACTIVE);
        when(suggestionRepositoryMock.findById(1L)).thenReturn(Optional.of(suggestion));

        suggestionService.changeSuggestionStatus(suggestionStatusChangeDto);

        Assertions.assertEquals(SuggestionStatusEnum.ACTIVE,suggestion.getStatus());

        verify(suggestionRepositoryMock, times(1)).findById(1L);
        verifyNoMoreInteractions(suggestionRepositoryMock);
    }

    private static class UserFactory {
        public static User getUser(Long userId) {
            User user = new User();
            user.setId(userId);
            user.setUserStatus(UserStatusEnum.ACTIVE);
            return user;
        }
    }

    private static class SuggestionFactory {
        public static Suggestion getSuggestion(SuggestionFormDto suggestionFormDto) {
            Suggestion suggestion = new Suggestion(suggestionFormDto, UserFactory.getUser(suggestionFormDto.getUserId()));
            return suggestion;
        }
    }

    private static class SuggestionFormDtoFactory {
        public static SuggestionFormDto getSuggestionFormDto(Long userId) {
            SuggestionFormDto suggestionFormDto = new SuggestionFormDto();
            suggestionFormDto.setTitle("Test title");
            suggestionFormDto.setDescription("Test description");
            suggestionFormDto.setUserId(userId);
            return suggestionFormDto;
        }
    }

    private static class SuggestionStatusChangeDtoFactory {
        public static SuggestionStatusChangeDto getSuggestionStatusChangeDto(Long suggestionId, SuggestionStatusEnum suggestionStatusEnum){
            SuggestionStatusChangeDto suggestionStatusChangeDto = new SuggestionStatusChangeDto();
            suggestionStatusChangeDto.setStatus(suggestionStatusEnum.toString());
            suggestionStatusChangeDto.setSuggestionId(suggestionId);
            return suggestionStatusChangeDto;
        }
    }
}
