package ru.sukhdmi.effectiveMobileTesting.dto;

import lombok.Getter;
import lombok.Setter;

public class SubscribeDTO {

    @Getter
    @Setter
    private UserDTO respondent;

    @Getter
    @Setter
    private UserDTO subscriber;
}
