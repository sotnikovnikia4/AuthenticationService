package ru.codecrafters.AuthenticationService.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.codecrafters.AuthenticationService.dto.AuthResponseDTO;
import ru.codecrafters.AuthenticationService.dto.UserDTO;
import ru.codecrafters.AuthenticationService.models.User;
import ru.codecrafters.AuthenticationService.security.JWTUtil;
import ru.codecrafters.AuthenticationService.util.UserValidator;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ModelMapper modelMapper;

    private final UserValidator userValidator;

    private final JWTUtil jwtUtil;

    @PostMapping("/registration")
    public ResponseEntity<AuthResponseDTO> register(@Valid UserDTO userDTO,
                                                    BindingResult bindingResult){
        User user = convertToUser(userDTO);

        userValidator.validate(user, bindingResult);

        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(new AuthResponseDTO(
                    "Произошла ошибка при регистрации",
                    "Error",
                    ""), HttpStatus.BAD_REQUEST);
        }

        String token = jwtUtil.generateToken(user.getId(), user.getLogin());

        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setMessage("Успешная регистрация");
        authResponseDTO.setStatus("Success");
        authResponseDTO.setToken(token);

        return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }

    private User convertToUser(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }
}
