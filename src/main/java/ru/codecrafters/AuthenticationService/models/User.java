package ru.codecrafters.AuthenticationService.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

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
    @NotBlank
    @Size(max = 20)
    private String phoneNumber;

    @Column(name = "email")
    @Email
    @NotBlank
    @Size(max = 255)
    private String email;

    @Column(name = "password")
    @NotBlank
    @Size(max = 255)
    private String password;

    @Column(name = "first_name")
    @NotBlank
    @Size(max = 255)
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    @Size(max = 255)
    private String lastName;

    @Column(name = "middle_name")
    @Size(max = 255)
    private String middleName;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @NotNull
    @Valid
    private Documents documents;

    public User(UUID id){
        this.id = id;
    }
}
