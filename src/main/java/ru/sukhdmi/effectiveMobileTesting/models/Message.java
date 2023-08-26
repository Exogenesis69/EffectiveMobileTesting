package ru.sukhdmi.effectiveMobileTesting.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String text;

    @ManyToOne
    @JoinColumn(name="sender_id")
    @Getter
    @Setter
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    @Getter
    @Setter
    private User receiver;
}
