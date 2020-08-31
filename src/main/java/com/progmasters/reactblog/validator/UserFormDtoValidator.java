package com.progmasters.reactblog.validator;

import com.progmasters.reactblog.domain.dto.UserFormDto;
import com.progmasters.reactblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserFormDtoValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserFormDtoValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserFormDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserFormDto userFormDto = (UserFormDto) o;
        validateUserId(userFormDto.getId(), errors);
        validateUserFirstName(userFormDto.getFirstName(), errors);
        validateUserLastName(userFormDto.getLastName(), errors);
        validateUserEmail(userFormDto.getEmail(), errors);
//        validateUserPassword(userFormDto.getPassword(), errors);
        validateUserPhoneNumber(userFormDto.getPhoneNumber(), errors);
    }

    private void validateUserId(Long id, Errors errors) {
        if(id == null){
            errors.rejectValue("id", "userFormDto.id-error");
        }else if (userService.idIsTaken(id)){
            errors.rejectValue("id", "userFormDto.id-already-taken");
        }
    }

    private void validateUserFirstName(String firstName, Errors errors) {
        if(firstName == null || firstName.equals("")){
            errors.rejectValue("firstName", "userFormDto.empty-firstName");
        }else if (firstName.length()<2 || firstName.length()>200){
            errors.rejectValue("firstName", "userFormDto.firstName-length");
        }
    }

    private void validateUserLastName(String lastName, Errors errors) {
        if(lastName == null || lastName.equals("")){
            errors.rejectValue("lastName", "userFormDto.empty-lastName");
        }else if (lastName.length()<2 || lastName.length()>200){
            errors.rejectValue("lastName", "userFormDto.lastName-length");
        }
    }

    private void validateUserEmail(String email, Errors errors) {
        if (!email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")){
            errors.rejectValue("email", "userFormDto.e-mail");
        }
    }

//    private void validateUserPassword(String password, Errors errors) {
//        if (!password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")){
//            errors.rejectValue("password", "userFormDto.wrong-password");
//        }
//    }

    private void validateUserPhoneNumber(String phoneNumber, Errors errors) {
        if (!phoneNumber.matches("\\+(9[976]\\d|8[987530]\\d|6[987]\\d|5[90]\\d|42\\d|3[875]\\d|2[98654321]\\d|9[8543210]|8[6421]|6[6543210]|5[87654321]|4[987654310]|3[9643210]|2[70]|7|1)\\d{1,14}$")){
            errors.rejectValue("phoneNumber", "userFormDto.wrong-phone-number");
        }

    }
}
