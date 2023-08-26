package ru.sukhdmi.effectiveMobileTesting.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "API постов пользоватлей", description = "API CRUD операций над постами пользователей")
public class PostController {

    private final PostService postService;
    private final ModelMapper modelMapper;


    @Autowired
    public PostController(PostService postService, ModelMapper modelMapper) {
        this.postService = postService;
        this.modelMapper = modelMapper;
    }


    @Operation(summary = "Получить список постов", description = """
            Endpoint для получения списка постов для текущего пользователя
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список постов.",
                    content = {
                            @Content(
                                    mediaType = "application/json"

                            )
                    }
            ),
    })
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


    @Operation(summary = "Получить пост", description = """
            Endpoint для получения поста по его id
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пост пользователя.",
                    content = {
                            @Content(
                                    mediaType = "application/json"

                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Не удалось получить пост.",
                    content = {
                            @Content(
                                    mediaType = "application/json"

                            )
                    }
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long id) throws PostNotFoundException {
        Post post = postService.getPostById(id);

        return new ResponseEntity<>(modelMapper.map(post, PostDTO.class), HttpStatus.OK);
    }

    @Operation(summary = "Создание нового поста", description = """
            Endpoint для создания нового поста
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Созданный пост.",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                                    //schema = @Schema(implementation = PostDtoResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Не удалось создать пост.",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody CreatePostDTO createPostDto) throws UserNotFoundException {
        Post post = postService.createPost(createPostDto.getTitle(), createPostDto.getText());

        return new ResponseEntity<>(modelMapper.map(post, PostDTO.class), HttpStatus.CREATED);
    }

    @Operation(summary = "Редактировать данные поста", description = """
            Endpoint для редактирования пользовательского поста
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пост успешно отредактирован.",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Не удалось отредактировать пост.",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> editPost(@PathVariable Long id, @RequestBody CreatePostDTO editPostDto)
            throws UserNotFoundException, PostNotFoundException, NoRightsException {
        Post post = postService.editPost(id,editPostDto.getTitle(),editPostDto.getText());

        return new ResponseEntity<>(modelMapper.map(post, PostDTO.class), HttpStatus.OK);
    }

    @Operation(summary = "Удалить пост", description = """
            Endpoint для удаление поста
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пост успешно удален",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Не удалось удалить пост.",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removePost(@PathVariable Long id)
            throws UserNotFoundException, NoRightsException, PostNotFoundException {
        postService.removePostById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
