package ru.sukhdmi.effectiveMobileTesting.exceptions;

public class UserAlreadySubscribedException extends Exception{
    private static final String MESSAGE = "User '%s' is already subscribed on user '%s'";

    public UserAlreadySubscribedException(String subscriberUsername, String authorUsername) {
        super(String.format(MESSAGE, subscriberUsername, authorUsername));
    }
}
