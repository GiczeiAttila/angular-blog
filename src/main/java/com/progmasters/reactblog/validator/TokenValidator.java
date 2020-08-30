package com.progmasters.reactblog.validator;

import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.dto.UserConfirmationDto;
import com.progmasters.reactblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class TokenValidator implements Validator {
    private final UserRepository userRepository;

    @Autowired
    public TokenValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserConfirmationDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserConfirmationDto userConfirmationDto = (UserConfirmationDto) target;
        Long id = userConfirmationDto.getId();
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            errors.rejectValue("confirmation", "confirmationDto.wrong-id");
        } else {
            User user = userOptional.get();
            if (!user.getToken().equals(userConfirmationDto.getToken())) {
                errors.rejectValue("confirmation", "confirmationDto.wrong-token");
            }
        }
    }
}
