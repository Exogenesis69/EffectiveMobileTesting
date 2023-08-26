package ru.sukhdmi.effectiveMobileTesting.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.sukhdmi.effectiveMobileTesting.dto.SubscribeDTO;
import ru.sukhdmi.effectiveMobileTesting.dto.UserDTO;
import ru.sukhdmi.effectiveMobileTesting.exceptions.UserAlreadySubscribedException;
import ru.sukhdmi.effectiveMobileTesting.exceptions.UserNotFoundException;
import ru.sukhdmi.effectiveMobileTesting.exceptions.UserNotSubscribedException;
import ru.sukhdmi.effectiveMobileTesting.models.Subscribe;
import ru.sukhdmi.effectiveMobileTesting.models.User;
import ru.sukhdmi.effectiveMobileTesting.security.UsrDetails;
import ru.sukhdmi.effectiveMobileTesting.services.SubscribeService;
import ru.sukhdmi.effectiveMobileTesting.services.UsrDetailsService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UsrDetailsService usrDetailsService;

    private final ModelMapper modelMapper;

    private final SubscribeService subscribeService;

    public UserController(UsrDetailsService usrDetailsService, ModelMapper modelMapper, SubscribeService subscribeService) {
        this.usrDetailsService = usrDetailsService;
        this.modelMapper = modelMapper;
        this.subscribeService = subscribeService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) throws UserNotFoundException {
        User user = usrDetailsService.getUserById(id);

        return new ResponseEntity<>(modelMapper.map(user, UserDTO.class), HttpStatus.OK);

    }
    @GetMapping("/showUserInfo")
    @ResponseBody
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsrDetails usrDetails = (UsrDetails) authentication.getPrincipal();

        return usrDetails.getUsername();
    }

    @PostMapping("/{id}/subscribe")
    public ResponseEntity<SubscribeDTO> subscribe(@PathVariable Long id)
            throws UserNotFoundException, UserAlreadySubscribedException {
        Subscribe subscribe = subscribeService.subscribe(id);

        return new ResponseEntity<>(modelMapper.map(subscribe, SubscribeDTO.class), HttpStatus.CREATED);
    }

    @DeleteMapping ("/{id}/unsubscribe")
    public ResponseEntity<?> unSubscribe(@PathVariable Long id)
            throws UserNotFoundException, UserNotSubscribedException {
        subscribeService.unsubscribe(id);

        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }


}
