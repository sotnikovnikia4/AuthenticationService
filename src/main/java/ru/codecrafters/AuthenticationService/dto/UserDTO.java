package ru.codecrafters.AuthenticationService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    @JsonProperty("phone_number")
    @NotEmpty
    @Size(max = 20)
    private String phoneNumber;

    @JsonProperty("email")
    @Email
    @NotEmpty
    @Size(max = 255)
    private String email;

    @JsonProperty("password")
    @NotEmpty
    @Size(max = 255)
    private String password;

    @JsonProperty("first_name")
    @NotEmpty
    @Size(max = 255)
    private String firstName;

    @JsonProperty("last_name")
    @NotEmpty
    @Size(max = 255)
    private String lastName;

    @JsonProperty("middle_name")
    @Size(max = 255)
    private String middleName;

    @JsonProperty("documents")
    @NotNull
    private DocumentsDTO documents;
}
