package ru.sukhdmi.effectiveMobileTesting.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.sukhdmi.effectiveMobileTesting.exceptions.*;

@ControllerAdvice
public class ExcHandler {

    @ExceptionHandler({UsersNotFriendsException.class, UserNotSubscribedException.class})
    public ResponseEntity<String> handleBadRequestException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoRightsException.class)
    public ResponseEntity<String> handleForbiddenException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({UserNotFoundException.class, PostNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UsernameAlreadyRegisteredException.class, UserAlreadySubscribedException.class})
    public ResponseEntity<String> handleConflictException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
