package ru.codecrafters.AuthenticationService.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.codecrafters.AuthenticationService.dto.AuthenticationDTO;
import ru.codecrafters.AuthenticationService.dto.UserDTO;
import ru.codecrafters.AuthenticationService.models.User;
import ru.codecrafters.AuthenticationService.security.JWTUtil;
import ru.codecrafters.AuthenticationService.security.UserDetailsImpl;
import ru.codecrafters.AuthenticationService.services.RegistrationService;
import ru.codecrafters.AuthenticationService.util.*;
import ru.codecrafters.AuthenticationService.util.ResponseStatus;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserValidator userValidator;

    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;

    private final RegistrationService registrationService;
    private final AuthenticationManager authManager;

    @GetMapping("login")
    public ResponseEntity<AnyErrorResponse> sendNotAuthenticatedMessage(){
        return new ResponseEntity<>(new AnyErrorResponse("Вы не авторизованы для выполнения этого запроса", ResponseStatus.FORBIDDEN),
                HttpStatus.FORBIDDEN);
    }

    @PostMapping("/registration")
    public ResponseEntity<AuthResponse> register(@RequestHeader(value = "Authorization", required = false) String authorization,
                                                 @RequestBody @Valid UserDTO userDTO,
                                                 BindingResult bindingResult){
        if(authorization != null && !authorization.isEmpty()){
            return new ResponseEntity<>(new AuthResponse("Invalid request", ResponseStatus.NOT_REGISTERED, ""), HttpStatus.BAD_REQUEST);
        }

        User user = convertToUser(userDTO);
        user.getDocuments().setUser(user);

        userValidator.validate(user, bindingResult);

        if(bindingResult.hasErrors()){
            throw new UserNotRegisteredException(ErrorMethods.formErrorMessage(bindingResult));
        }

        registrationService.register(user);

        String token = jwtUtil.generateToken(user.getId().toString(), user.getPhoneNumber());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Успешная регистрация");
        authResponse.setStatus(ResponseStatus.SUCCESS);
        authResponse.setToken(token);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthenticationDTO authenticationDTO){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authenticationDTO.getPhoneNumber(),
                authenticationDTO.getPassword()
        );

        UserDetailsImpl userDetails = (UserDetailsImpl) authManager.authenticate(authToken).getPrincipal();

        String token = jwtUtil.generateToken(userDetails.getUser().getId().toString(), userDetails.getUser().getPhoneNumber());

        AuthResponse response = new AuthResponse("Успешная авторизация", ResponseStatus.SUCCESS, token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private User convertToUser(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }



    @ExceptionHandler
    public ResponseEntity<AuthResponse> handleException(UserNotRegisteredException e){
        AuthResponse response = new AuthResponse(e.getMessage(), ResponseStatus.NOT_REGISTERED, "");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(AuthenticationException e){
        return new ResponseEntity<>("Неверный номер телефона или пароль", HttpStatus.BAD_REQUEST);
    }
}
