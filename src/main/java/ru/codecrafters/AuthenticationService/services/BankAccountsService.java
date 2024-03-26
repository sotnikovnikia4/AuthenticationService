package ru.codecrafters.AuthenticationService.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.codecrafters.AuthenticationService.controllers.BankAccountsController;
import ru.codecrafters.AuthenticationService.models.BankAccount;
import ru.codecrafters.AuthenticationService.repositories.BankAccountsRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankAccountsService {
    private final BankAccountsRepository accountsRepository;

    public List<BankAccount> getAccountsByUserId(UUID id){
        return accountsRepository.findAllByUserId(id);
    }
}
