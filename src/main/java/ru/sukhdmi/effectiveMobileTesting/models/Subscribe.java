package ru.sukhdmi.effectiveMobileTesting.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

    @ManyToOne
    @JoinColumn(name="respondent_id")
    @Getter
    @Setter
    private User respondent;

    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    @Getter
    @Setter
    private User subscriber;




}
