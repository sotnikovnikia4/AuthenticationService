package ru.codecrafters.AuthenticationService.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.codecrafters.AuthenticationService.util.AnySuccessfulResponse;

@RestController
@RequestMapping("tests")
public class TestController {
    @PostMapping("/accept-creation-account")
    public Boolean testUrl(){
        return true;
    }
    @PostMapping("/accept-creation-user")
    public Boolean testUrl1(){
        return true;
    }
    @PostMapping("/accept-transfer-money")
    public Boolean testUrl2(){
        return true;
    }

    @PostMapping("/create-order")
    public ResponseEntity<AnySuccessfulResponse> testUrl3(){
        return new ResponseEntity<>(new AnySuccessfulResponse("Заявка успешно создана и находится на рассмотрении"), HttpStatus.ACCEPTED);
    }
}
