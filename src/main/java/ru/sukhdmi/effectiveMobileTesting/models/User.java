package ru.sukhdmi.effectiveMobileTesting.models;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(schema = "public")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String email;
    //TODO поменять мыло на стринг

    private String password;


    public User(){}

}
