package ru.sukhdmi.effectiveMobileTesting.services;

import org.springframework.stereotype.Service;
import ru.sukhdmi.effectiveMobileTesting.exceptions.UserAlreadySubscribedException;
import ru.sukhdmi.effectiveMobileTesting.exceptions.UserNotFoundException;
import ru.sukhdmi.effectiveMobileTesting.exceptions.UserNotSubscribedException;
import ru.sukhdmi.effectiveMobileTesting.models.Subscribe;
import ru.sukhdmi.effectiveMobileTesting.models.User;
import ru.sukhdmi.effectiveMobileTesting.repositories.SubscribeRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;
    private final UsrDetailsService usrDetailsService;

    public SubscribeService(SubscribeRepository subscribeRepository, UsrDetailsService usrDetailsService) {
        this.subscribeRepository = subscribeRepository;
        this.usrDetailsService = usrDetailsService;
    }

    public Subscribe subscribe(Long respondentId) throws UserNotFoundException, UserAlreadySubscribedException {
        User currentUser = usrDetailsService.getCurrentUser();
        User respondent = usrDetailsService.getUserById(respondentId);

        if (subscribeRepository.findBySubscriberAndRespondent(currentUser, respondent).isPresent())
            throw new UserAlreadySubscribedException(currentUser.getUsername(), respondent.getUsername());

        Subscribe subscribe = new Subscribe();
        subscribe.setSubscriber(currentUser);
        subscribe.setRespondent(respondent);

        return subscribeRepository.save(subscribe);
    }

    public void unsubscribe(Long respondentId) throws UserNotFoundException, UserNotSubscribedException {
        User currentUser = usrDetailsService.getCurrentUser();
        User respondent = usrDetailsService.getUserById(respondentId);

        Optional<Subscribe> subscribe = subscribeRepository.findBySubscriberAndRespondent(currentUser, respondent);

        if (subscribe.isPresent()) {
            subscribeRepository.delete(subscribe.get());
        }else {
            throw new UserNotSubscribedException(currentUser.getUsername(), respondent.getUsername());
        }
    }

    public Collection<Subscribe> getSubscribeBySubscriber(User subscriber) {
        return subscribeRepository.findAllBySubscriber(subscriber);
    }

    public boolean areFriends(User first, User second) {
        return subscribeRepository.findBySubscriberAndRespondent(first, second).isPresent()
                && subscribeRepository.findByRespondentAndSubscriber(first, second).isPresent();
    }
}
