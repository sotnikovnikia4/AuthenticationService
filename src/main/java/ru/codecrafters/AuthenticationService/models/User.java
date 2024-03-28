package ru.codecrafters.AuthenticationService.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

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

    @Column(name = "email")
    @Email
    @NotEmpty
    @Size(max = 255)
    private String email;

    @Column(name = "password")
    @NotEmpty
    @Size(max = 255)
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

    @OneToOne(mappedBy = "user")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.ALL})
    @NotNull
    private Documents documents;

    public User(UUID id){
        this.id = id;
    }
}
