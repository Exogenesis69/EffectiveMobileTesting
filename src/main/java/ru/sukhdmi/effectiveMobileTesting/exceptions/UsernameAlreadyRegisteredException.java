package ru.sukhdmi.effectiveMobileTesting.exceptions;

public class UsernameAlreadyRegisteredException extends Exception{
    private static final String MESSAGE = "Username '%s' already registered";

    public UsernameAlreadyRegisteredException(String username) {
        super(String.format(MESSAGE, username));
    }
}
