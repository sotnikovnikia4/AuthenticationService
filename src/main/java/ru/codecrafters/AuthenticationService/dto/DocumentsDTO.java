package ru.codecrafters.AuthenticationService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.codecrafters.AuthenticationService.models.User;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class DocumentsDTO {

    @JsonProperty("registration_address")
    @NotBlank
    private String registrationAddress;

    @JsonProperty("passport_details")
    @NotBlank
    @Size(max = 10, min = 10)
    private String passportDetails;

    @JsonProperty("passport_issued_at")
    @NotNull
    private Date passportIssuedAt;

    @JsonProperty("inn")
    @NotBlank
    @Size(min = 12, max=12)
    private String inn;

    @JsonProperty("snils")
    @NotBlank
    @Size(min=15, max=15)
    private String snils;

    @JsonProperty("place_of_birth")
    @NotBlank
    private String placeOfBirth;

    @JsonProperty("date_of_birth")
    @NotNull
    private Date dateOfBirth;
}