package ru.sukhdmi.effectiveMobileTesting.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sukhdmi.effectiveMobileTesting.dto.AuthenticationDTO;
import ru.sukhdmi.effectiveMobileTesting.dto.UserDTO;
import ru.sukhdmi.effectiveMobileTesting.models.User;
import ru.sukhdmi.effectiveMobileTesting.security.JWTUtil;
import ru.sukhdmi.effectiveMobileTesting.services.RegistrationService;
import ru.sukhdmi.effectiveMobileTesting.util.UserValidator;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Пользовательское API", description = "API операций по регистрации и аутентификации пользователей.")
public class LogInController {

    private final RegistrationService registrationService;
    private final UserValidator userValidator;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public LogInController(RegistrationService registrationService, UserValidator userValidator1,
                           JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager){
        this.registrationService = registrationService;
        this.userValidator = userValidator1;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    public Map<String, String> PerformRegistration(@RequestBody @Valid UserDTO userDTO,
                                  BindingResult bindingResult) {

        User user = convertToUser(userDTO);

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            //TODO выбросить исключение,обработать, хендлить(@ExceptionHandler) в отдельном методе и там выбросить JSON с ошибкой
            return Map.of("message","Ошибка!");

        registrationService.register(user);

        String token = jwtUtil.generateToken(user.getUsername());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO){
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        }catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");//TODO переписать норм ошибку
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return Map.of("jwt-token", token);

    }

    public User convertToUser(UserDTO userDTO){
        return this.modelMapper.map(userDTO, User.class);
    }

}
