package ru.sukhdmi.effectiveMobileTesting.services;

import org.springframework.stereotype.Service;
import ru.sukhdmi.effectiveMobileTesting.exceptions.UserNotFoundException;
import ru.sukhdmi.effectiveMobileTesting.exceptions.UsersNotFriendsException;
import ru.sukhdmi.effectiveMobileTesting.models.Message;
import ru.sukhdmi.effectiveMobileTesting.models.User;
import ru.sukhdmi.effectiveMobileTesting.repositories.MessageRepository;

import java.util.Collection;

@Service
public class MessageService {
    private  final UsrDetailsService usrDetailsService;

    private final MessageRepository messageRepository;

    private  final SubscribeService subscribeService;

    public MessageService(UsrDetailsService usrDetailsService, MessageRepository messageRepository, SubscribeService subscribeService) {
        this.usrDetailsService = usrDetailsService;
        this.messageRepository = messageRepository;
        this.subscribeService = subscribeService;
    }

    public Collection<Message> getDialog(Long secondUserId) throws UserNotFoundException {
        User currentUser = usrDetailsService.getCurrentUser();
        User secondUser = usrDetailsService.getUserById(secondUserId);

        return messageRepository.findMessageByUsers(currentUser, secondUser);
    }

    public Message sendMessage(Long receiverId, String text) throws UserNotFoundException, UsersNotFriendsException {
        User currentUser = usrDetailsService.getCurrentUser();
        User receiver = usrDetailsService.getUserById(receiverId);

        if (!subscribeService.areFriends(currentUser,receiver)){
            throw new UsersNotFriendsException(currentUser.getUsername(), receiver.getUsername());
        }

        Message message = new Message();
        message.setSender(currentUser);
        message.setReceiver(receiver);
        message.setText(text);

        return messageRepository.save(message);
    }
}
