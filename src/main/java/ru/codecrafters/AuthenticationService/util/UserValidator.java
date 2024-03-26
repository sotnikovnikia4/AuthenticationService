package ru.codecrafters.AuthenticationService.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.codecrafters.AuthenticationService.models.User;
import ru.codecrafters.AuthenticationService.services.UsersService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UsersService usersService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {//TODO
        User userToCheck = (User)target;

        Optional<User> userWithSamePhoneNumber = usersService.findOneByPhoneNumber(userToCheck.getPhoneNumber());

        if(userWithSamePhoneNumber.isPresent()){
            errors.rejectValue("phoneNumber", "", "Пользователь с таким номером телефона уже зарегистрирован!");
        }
    }
}
