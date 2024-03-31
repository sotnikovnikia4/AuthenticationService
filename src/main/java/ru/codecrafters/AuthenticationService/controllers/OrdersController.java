package ru.codecrafters.AuthenticationService.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.codecrafters.AuthenticationService.dto.TransferMoneyDTO;
import ru.codecrafters.AuthenticationService.services.BankAccountsService;
import ru.codecrafters.AuthenticationService.util.AnyErrorResponse;
import ru.codecrafters.AuthenticationService.util.AnySuccessfulResponse;
import ru.codecrafters.AuthenticationService.util.ErrorMethods;
import ru.codecrafters.AuthenticationService.util.MoneyNotTransferredException;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final BankAccountsService accountsService;

    @PostMapping("/transfer-money")
    public ResponseEntity<AnySuccessfulResponse> transferMoney(
            @RequestBody @Valid  TransferMoneyDTO transferMoneyDTO,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            throw new MoneyNotTransferredException(ErrorMethods.formErrorMessage(bindingResult));
        }

        accountsService.transferMoneyOrThrowException(transferMoneyDTO);

        AnySuccessfulResponse response = new AnySuccessfulResponse("Деньги успешно переведены");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<AnyErrorResponse> handleException(MoneyNotTransferredException e){
        AnyErrorResponse response = new AnyErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
