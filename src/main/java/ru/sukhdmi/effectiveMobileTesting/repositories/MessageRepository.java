package ru.sukhdmi.effectiveMobileTesting.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sukhdmi.effectiveMobileTesting.models.Message;
import ru.sukhdmi.effectiveMobileTesting.models.User;

import java.util.Collection;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

        @Query(
                "SELECT m FROM Message m WHERE ((m.sender = :first AND m.receiver = :second) OR (m.sender = :second AND m.receiver = :first))")

        Collection<Message> findMessageByUsers(User first, User second);
}
