package ru.codecrafters.AuthenticationService.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.codecrafters.AuthenticationService.dto.BankAccountDTO;
import ru.codecrafters.AuthenticationService.dto.CreationAccountDTO;
import ru.codecrafters.AuthenticationService.models.BankAccount;
import ru.codecrafters.AuthenticationService.security.UserDetailsImpl;
import ru.codecrafters.AuthenticationService.services.BankAccountsService;
import ru.codecrafters.AuthenticationService.util.AccountNotCreatedException;
import ru.codecrafters.AuthenticationService.util.AnyErrorResponse;
import ru.codecrafters.AuthenticationService.util.ErrorMethods;
import ru.codecrafters.AuthenticationService.util.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class BankAccountsController {

    private final BankAccountsService accountsService;
    private final ModelMapper modelMapper;
    @GetMapping("/get_bank_accounts")
    public ResponseEntity<List<BankAccountDTO>> getAccounts(){

        List<BankAccountDTO> list = accountsService.getAccountsByUserId(
                ((UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId()
        ).stream().map(this::convertToBankAccountDTO).toList();


        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<BankAccountDTO> createAccount(@RequestBody @Valid CreationAccountDTO creationAccountDTO,
                                                        BindingResult bindingResult){

        if(bindingResult.hasErrors())
        {
            throw new AccountNotCreatedException(ErrorMethods.formErrorMessage(bindingResult));
        }

        BankAccount account = accountsService.registerOrThrowException(
                ((UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId(),
                creationAccountDTO.getCurrencyCode()
        );

        return new ResponseEntity<>(convertToBankAccountDTO(account), HttpStatus.CREATED);
    }

    private BankAccountDTO convertToBankAccountDTO(BankAccount bankAccount){
        return modelMapper.map(bankAccount, BankAccountDTO.class);
    }

    @ExceptionHandler
    public ResponseEntity<AnyErrorResponse> handleException(AccountNotCreatedException e){
        AnyErrorResponse response = new AnyErrorResponse(e.getMessage(), ResponseStatus.NOT_CREATED);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
