package ru.sukhdmi.effectiveMobileTesting.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private Timestamp time;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String text;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @Getter
    @Setter
    private User author;

}
