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
import org.springframework.web.bind.annotation.*;
import ru.sukhdmi.effectiveMobileTesting.dto.MessageDTO;
import ru.sukhdmi.effectiveMobileTesting.dto.SendMessageDTO;
import ru.sukhdmi.effectiveMobileTesting.exceptions.UserNotFoundException;
import ru.sukhdmi.effectiveMobileTesting.exceptions.UsersNotFriendsException;
import ru.sukhdmi.effectiveMobileTesting.models.Message;
import ru.sukhdmi.effectiveMobileTesting.services.MessageService;

import java.util.Collection;

@RestController
@RequestMapping("/messages")
@Tag(name = "API по отправке сообщений между пользователями.", description = "API операций по отправке сообщений.")
public class MessageController {

    private final MessageService messageService;
    private final ModelMapper modelMapper;

    public MessageController(MessageService messageService, ModelMapper modelMapper) {
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Получить пользовательскую переписку", description = """
            Endpoint для получение переписки с пользователем.
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список сообщений с пользователем.",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    @GetMapping
    public ResponseEntity<Collection<MessageDTO>> getDialog(@RequestParam Long interlocutorId) throws UserNotFoundException {
        Collection<MessageDTO> messages = messageService.getDialog(interlocutorId).stream()
                .map(message -> modelMapper.map(message, MessageDTO.class)).toList();

        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @Operation(summary = "Отправить сообщение пользователю.", description = """
            Endpoint для отправки сообщения пользователю.
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Отправленное сообщение",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Не удалось отправить сообщение.",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    @PostMapping
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody SendMessageDTO sendMessageDTO) throws UserNotFoundException, UsersNotFriendsException {
        Message message = messageService.sendMessage(sendMessageDTO.getReceiverId(), sendMessageDTO.getText());
        MessageDTO messageDTO= modelMapper.map(message, MessageDTO.class);

        return new ResponseEntity<>(messageDTO, HttpStatus.CREATED);
    }
}
