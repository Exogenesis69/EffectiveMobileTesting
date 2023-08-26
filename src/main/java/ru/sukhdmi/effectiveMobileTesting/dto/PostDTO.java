package ru.sukhdmi.effectiveMobileTesting.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import ru.sukhdmi.effectiveMobileTesting.models.User;

import java.sql.Timestamp;

public class PostDTO {
    @Getter
    @Setter
    private Timestamp time;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String text;


    @Getter
    @Setter
    private UserDTO author;
}
