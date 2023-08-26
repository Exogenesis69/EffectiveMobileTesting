package ru.sukhdmi.effectiveMobileTesting.exceptions;

public class NoRightsException extends Exception{

    private static final String MESSAGE =
            "User does not have rights to operation with '%s' with id '%d'";

    public NoRightsException(String username, String entity, Integer entityId) {
        super(String.format(MESSAGE, username, entity, entityId));
    }
}
