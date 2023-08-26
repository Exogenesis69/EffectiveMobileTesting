package ru.sukhdmi.effectiveMobileTesting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sukhdmi.effectiveMobileTesting.models.Subscribe;
import ru.sukhdmi.effectiveMobileTesting.models.User;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    Optional<Subscribe> findBySubscriberAndRespondent(User subscriber, User respondent);

    Optional<Subscribe> findByRespondentAndSubscriber(User respondent, User subscriber);

    Collection<Subscribe> findAllBySubscriber(User subscriber);

}
