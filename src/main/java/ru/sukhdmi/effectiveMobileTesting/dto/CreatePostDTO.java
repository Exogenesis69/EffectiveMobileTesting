package ru.sukhdmi.effectiveMobileTesting.dto;

import lombok.Getter;
import lombok.Setter;

public class CreatePostDTO {
    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String text;
}
