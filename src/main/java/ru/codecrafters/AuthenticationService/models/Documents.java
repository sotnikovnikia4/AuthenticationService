package ru.codecrafters.AuthenticationService.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
public class Documents {//TODO

    @Id
    @Column(name = "documents_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "registration_address")
    @NotEmpty
    private String registrationAddress;

    @Column(name = "passport_details")
    @NotEmpty
    @Size(max = 10, min = 10)
    private String passportDetails;

    @Column(name = "passport_issued_at")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date passportIssuedAt;

    @Column(name = "inn")
    @NotEmpty
    @Size(min = 12, max=12)
    private String inn;

    @Column(name = "snils")
    @NotEmpty
    @Size(min=15, max=15)
    private String snils;

    @Column(name = "place_of_birth")
    @NotEmpty
    private String placeOfBirth;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date dateOfBirth;

    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User user;
}
