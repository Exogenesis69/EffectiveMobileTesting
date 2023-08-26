package ru.sukhdmi.effectiveMobileTesting.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.sukhdmi.effectiveMobileTesting.exceptions.NoRightsException;
import ru.sukhdmi.effectiveMobileTesting.exceptions.PostNotFoundException;
import ru.sukhdmi.effectiveMobileTesting.exceptions.UserNotFoundException;
import ru.sukhdmi.effectiveMobileTesting.models.Post;
import ru.sukhdmi.effectiveMobileTesting.models.Subscribe;
import ru.sukhdmi.effectiveMobileTesting.models.User;
import ru.sukhdmi.effectiveMobileTesting.repositories.PostRepository;
import ru.sukhdmi.effectiveMobileTesting.util.PostSort;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public final UsrDetailsService usrDetailsService;

    public final SubscribeService subscribeService;

    public PostService(PostRepository postRepository, UsrDetailsService usrDetailsService, SubscribeService subscribeService) {
        this.postRepository = postRepository;
        this.usrDetailsService = usrDetailsService;
        this.subscribeService = subscribeService;
    }

    public Collection<Post> getFeed(Integer offset, Integer limit, PostSort sort) throws UserNotFoundException {
        User subscriber = usrDetailsService.getCurrentUser();
        Collection<User> authors = subscribeService.getSubscribeBySubscriber(subscriber).stream().map(Subscribe::getRespondent).toList();

        return postRepository.findAllByAuthorIn(authors, PageRequest.of(offset, limit, sort.getSortValue())).getContent();
    }

    public Post createPost(String title, String text) throws UserNotFoundException {
        User author = usrDetailsService.getCurrentUser();

        Post post = new Post();
        post.setTime(new Timestamp(System.currentTimeMillis()));
        post.setTitle(title);
        post.setText(text);
        post.setAuthor(author);

        return postRepository.save(post);
    }
    public Post getPostById(Long id) throws PostNotFoundException {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
    }

    public Post editPost(Long id, String title, String text) throws UserNotFoundException, PostNotFoundException, NoRightsException {
        User user = usrDetailsService.getCurrentUser();
        Post post = getPostById(id);

        if (!user.equals(post.getAuthor())) {
            throw new NoRightsException(user.getUsername(), "post", post.getId());
        }

        Optional.of(title).ifPresent(post::setTitle);
        Optional.of(text).ifPresent(post::setText);

        return postRepository.save(post);
    }

    public void removePostById(Long id) throws UserNotFoundException, NoRightsException, PostNotFoundException {
        User currentUser = usrDetailsService.getCurrentUser();
        Post post = getPostById(id);

        if (currentUser.equals(post.getAuthor())) {
            postRepository.delete(post);
        } else {
            throw new NoRightsException(currentUser.getUsername(), "post", post.getId());
        }
    }


}
