package ru.sukhdmi.effectiveMobileTesting.exceptions;


public class UserNotFoundException extends Exception{

    private static final String MESSAGE_ID = "User with id '%d' not found";

    private static final String MESSAGE_USERNAME = "User with username '%s' not found";

    public UserNotFoundException(Long userId) {
        super(String.format(MESSAGE_ID, userId));
    }

    public UserNotFoundException(String username) {
        super(String.format(MESSAGE_USERNAME, username));
    }

}
