package ru.codecrafters.AuthenticationService.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
