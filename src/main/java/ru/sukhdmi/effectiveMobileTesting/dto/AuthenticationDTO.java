package ru.sukhdmi.effectiveMobileTesting.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class AuthenticationDTO {

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;
}
