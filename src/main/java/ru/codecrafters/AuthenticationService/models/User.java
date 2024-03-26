package ru.codecrafters.AuthenticationService.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "phone_number")
    @NotEmpty
    @Size(max = 20)
    private String phoneNumber;

    @Column(name = "password")
    @NotEmpty
    private String password;

    @Column(name = "first_name")
    @NotEmpty
    @Size(max = 255)
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty
    @Size(max = 255)
    private String lastName;

    @Column(name = "middle_name")
    @Size(max = 255)
    private String middleName;

    @Column(name = "registration_address")
    private String registrationAddress;

    @Column(name = "passport_details")
    @NotEmpty
    @Size(max = 10, min = 10)
    private String passportDetails;
}
