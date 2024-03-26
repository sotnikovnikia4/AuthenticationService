package ru.codecrafters.AuthenticationService.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.codecrafters.AuthenticationService.dto.BankAccountDTO;
import ru.codecrafters.AuthenticationService.models.BankAccount;
import ru.codecrafters.AuthenticationService.security.UserDetailsImpl;
import ru.codecrafters.AuthenticationService.services.BankAccountsService;

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

    private BankAccountDTO convertToBankAccountDTO(BankAccount bankAccount){
        return modelMapper.map(bankAccount, BankAccountDTO.class);
    }
}
