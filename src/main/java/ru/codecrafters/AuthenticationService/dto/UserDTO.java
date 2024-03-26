package ru.codecrafters.AuthenticationService.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    @NotEmpty
    @Size(max = 20)
    private String phoneNumber;

    @NotEmpty
    private String password;

    @NotEmpty
    @Size(max = 255)
    private String firstName;

    @NotEmpty
    @Size(max = 255)
    private String lastName;

    @Column(name = "middle_name")
    @Size(max = 255)
    private String middleName;

    private String registrationAddress;

    @NotEmpty
    @Size(max = 10, min = 10)
    private String passportDetails;
}
