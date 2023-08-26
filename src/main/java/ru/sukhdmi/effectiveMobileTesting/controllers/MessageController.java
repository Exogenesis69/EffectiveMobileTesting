package ru.sukhdmi.effectiveMobileTesting.controllers;

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
public class MessageController {

    private final MessageService messageService;
    private final ModelMapper modelMapper;

    public MessageController(MessageService messageService, ModelMapper modelMapper) {
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<Collection<MessageDTO>> getDialog(@RequestParam Long interlocutorId) throws UserNotFoundException {
        Collection<MessageDTO> messages = messageService.getDialog(interlocutorId).stream()
                .map(message -> modelMapper.map(message, MessageDTO.class)).toList();

        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody SendMessageDTO sendMessageDTO) throws UserNotFoundException, UsersNotFriendsException {
        Message message = messageService.sendMessage(sendMessageDTO.getReceiverId(), sendMessageDTO.getText());
        MessageDTO messageDTO= modelMapper.map(message, MessageDTO.class);

        return new ResponseEntity<>(messageDTO, HttpStatus.CREATED);
    }
}
