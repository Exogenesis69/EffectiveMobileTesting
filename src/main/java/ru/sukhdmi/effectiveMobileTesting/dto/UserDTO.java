package ru.sukhdmi.effectiveMobileTesting.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class UserDTO {

    @Getter
    @Setter
    private String username;


    @Getter
    @Setter
    private int email;


    @Getter
    @Setter
    private String password;
}
