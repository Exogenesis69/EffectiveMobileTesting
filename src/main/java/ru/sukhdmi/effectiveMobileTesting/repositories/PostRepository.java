package ru.sukhdmi.effectiveMobileTesting.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sukhdmi.effectiveMobileTesting.models.Post;
import ru.sukhdmi.effectiveMobileTesting.models.User;

import java.util.Collection;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByAuthorIn(Collection<User> authors, Pageable pageable);
}
