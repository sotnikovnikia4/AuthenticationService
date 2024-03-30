package ru.codecrafters.AuthenticationService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDTO {

    @JsonProperty("phone_number")
    @NotBlank
    @Size(max = 20)
    private String phoneNumber;

    @JsonProperty("email")
    @Email
    @NotBlank
    @Size(max = 255)
    private String email;

    @JsonProperty("password")
    @NotBlank
    @Size(max = 255)
    private String password;

    @JsonProperty("first_name")
    @NotBlank
    @Size(max = 255)
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank
    @Size(max = 255)
    private String lastName;

    @JsonProperty("middle_name")
    @Size(max = 255)
    private String middleName;

    @JsonProperty("documents")
    @NotNull
    @Valid
    private DocumentsDTO documents;
}
