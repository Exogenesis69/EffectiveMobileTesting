package ru.sukhdmi.effectiveMobileTesting.exceptions;

public class PostNotFoundException extends Exception{
    private static final String MESSAGE = "Post with id '%d' not found";

    public PostNotFoundException(Long postId) {
        super(String.format(MESSAGE, postId));
    }
}
