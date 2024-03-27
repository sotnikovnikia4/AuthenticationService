package ru.codecrafters.AuthenticationService.models;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Documents {//TODO

    @Column(name = "registration_address")
    private String registrationAddress;

    @Column(name = "passport_details")
    @NotBlank
    @Size(max = 10, min = 10)
    private String passportetails;
}
