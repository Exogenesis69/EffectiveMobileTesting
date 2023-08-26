package ru.sukhdmi.effectiveMobileTesting.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Пользовательское API", description = "API операций над пользователями, включающее в себя подписку" +
        " и отписку на пользователей")
public class UserController {
    private final UsrDetailsService usrDetailsService;

    private final ModelMapper modelMapper;

    private final SubscribeService subscribeService;

    public UserController(UsrDetailsService usrDetailsService, ModelMapper modelMapper, SubscribeService subscribeService) {
        this.usrDetailsService = usrDetailsService;
        this.modelMapper = modelMapper;
        this.subscribeService = subscribeService;
    }

    @Operation(summary = "Показать информацию о пользователе", description = """
            Endpoint для получения информации о пользователе. """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь успешно получен.",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                                    //schema = @Schema(implementation = ResponseMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Не удалось получить пользователя.",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                                    //schema = @Schema(implementation = AppError.class)
                            )
                    }
            )
    })
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
    @Operation(summary = "Подписаться на пользователя.", description = """
            Endpoint для подписки на пользователя.
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Вы успешно подписались на пользователя.",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                                    //schema = @Schema(implementation = UserDtoResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Не удалось подписаться на пользователя.",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                                    //schema = @Schema(implementation = AppError.class)
                            )
                    }
            )
    })
    @PostMapping("/{id}/subscribe")
    public ResponseEntity<SubscribeDTO> subscribe(@PathVariable Long id)
            throws UserNotFoundException, UserAlreadySubscribedException {
        Subscribe subscribe = subscribeService.subscribe(id);

        return new ResponseEntity<>(modelMapper.map(subscribe, SubscribeDTO.class), HttpStatus.CREATED);
    }
    @Operation(summary = "Отписаться от пользователя.", description = """
            Endpoint для отписки от пользователя
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Вы успешно отписались от пользователя.",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                                    //schema = @Schema(implementation = ResponseMessage.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Не удалось отписаться от пользователя.",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                                    //schema = @Schema(implementation = AppError.class)
                            )
                    }
            )
    })
    @DeleteMapping ("/{id}/unsubscribe")
    public ResponseEntity<?> unSubscribe(@PathVariable Long id)
            throws UserNotFoundException, UserNotSubscribedException {
        subscribeService.unsubscribe(id);

        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }
}
