package ru.codecrafters.AuthenticationService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    @Size(max = 20)
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank
    private String password;

    @NotBlank
    @Size(max = 255)
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank
    @Size(max = 255)
    @JsonProperty("last_name")
    private String lastName;

    @Column(name = "middle_name")
    @Size(max = 255)
    @JsonProperty("middle_name")
    private String middleName;

    @JsonProperty("registration_address")
    private String registrationAddress;

    @NotBlank
    @Size(max = 10, min = 10)
    @JsonProperty("passport_details")
    private String passportDetails;
}
