package ru.sukhdmi.effectiveMobileTesting.dto;

import lombok.Getter;
import lombok.Setter;

public class SendMessageDTO {
    @Getter
    @Setter
    private String text;



    @Getter
    @Setter
    private Long receiverId;
}
