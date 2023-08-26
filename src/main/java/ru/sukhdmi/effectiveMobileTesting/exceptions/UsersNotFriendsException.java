package ru.sukhdmi.effectiveMobileTesting.exceptions;

public class UsersNotFriendsException extends Exception{
    private static final String MESSAGE = "Users '%s' and '%s' are not friends";

    public UsersNotFriendsException(String firstUsername, String secondUsername) {
        super(String.format(MESSAGE, firstUsername, secondUsername));
    }
}
