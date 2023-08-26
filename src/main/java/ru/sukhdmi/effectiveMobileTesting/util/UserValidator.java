package ru.sukhdmi.effectiveMobileTesting.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sukhdmi.effectiveMobileTesting.models.User;
import ru.sukhdmi.effectiveMobileTesting.services.UsrDetailsService;

@Component
public class UserValidator implements Validator {
    private final UsrDetailsService usrDetailsService;

    @Autowired
    public UserValidator(UsrDetailsService usrDetailsService) {
        this.usrDetailsService = usrDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        try {
            usrDetailsService.loadUserByUsername(user.getUsername());
        }catch (UsernameNotFoundException ignored){
            return;  // здесь все ок, пользователь не найден
        }

        errors.rejectValue("username", "", "Человек с таким именем уже существует");
    }
}
