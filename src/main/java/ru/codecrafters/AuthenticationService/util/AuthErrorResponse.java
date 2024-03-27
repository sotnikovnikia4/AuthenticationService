package ru.codecrafters.AuthenticationService.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthErrorResponse {
    private String message;
    private String status;
}
