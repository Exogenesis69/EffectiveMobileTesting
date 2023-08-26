package ru.sukhdmi.effectiveMobileTesting.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


public class MessageDTO {

    @Getter
    @Setter
    private String text;


    @Getter
    @Setter
    private UserDTO sender;


    @Getter
    @Setter
    private UserDTO receiver;

}
