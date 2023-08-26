package ru.sukhdmi.effectiveMobileTesting.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sukhdmi.effectiveMobileTesting.dto.CreatePostDTO;
import ru.sukhdmi.effectiveMobileTesting.dto.PostDTO;
import ru.sukhdmi.effectiveMobileTesting.exceptions.NoRightsException;
import ru.sukhdmi.effectiveMobileTesting.exceptions.PostNotFoundException;
import ru.sukhdmi.effectiveMobileTesting.exceptions.UserNotFoundException;
import ru.sukhdmi.effectiveMobileTesting.models.Post;
import ru.sukhdmi.effectiveMobileTesting.services.PostService;
import ru.sukhdmi.effectiveMobileTesting.util.PostSort;

import java.util.Collection;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final ModelMapper modelMapper;


    @Autowired
    public PostController(PostService postService, ModelMapper modelMapper) {
        this.postService = postService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<Collection<PostDTO>> getFeed(
            @RequestParam Integer offset, @RequestParam Integer limit, @RequestParam PostSort sort)
            throws UserNotFoundException {
        Collection<PostDTO> feed =
                postService.getFeed(offset, limit, sort).stream()
                        .map(p -> modelMapper.map(p, PostDTO.class))
                        .toList();

        return new ResponseEntity<>(feed, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long id) throws PostNotFoundException {
        Post post = postService.getPostById(id);

        return new ResponseEntity<>(modelMapper.map(post, PostDTO.class), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody CreatePostDTO createPostDto) throws UserNotFoundException {
        Post post = postService.createPost(createPostDto.getTitle(), createPostDto.getText());

        return new ResponseEntity<>(modelMapper.map(post, PostDTO.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> editPost(@PathVariable Long id, @RequestBody CreatePostDTO editPostDto)
            throws UserNotFoundException, PostNotFoundException, NoRightsException {
        Post post = postService.editPost(id,editPostDto.getTitle(),editPostDto.getText());

        return new ResponseEntity<>(modelMapper.map(post, PostDTO.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removePost(@PathVariable Long id)
            throws UserNotFoundException, NoRightsException, PostNotFoundException {
        postService.removePostById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
