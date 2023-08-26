package ru.sukhdmi.effectiveMobileTesting.exceptions;

public class UserNotSubscribedException extends Exception{
    private static final String MESSAGE = "User '%s' is not subscribed on user '%s'";

    public UserNotSubscribedException(String subscriberUsername, String authorUsername) {
        super(String.format(MESSAGE, subscriberUsername, authorUsername));
    }
}
