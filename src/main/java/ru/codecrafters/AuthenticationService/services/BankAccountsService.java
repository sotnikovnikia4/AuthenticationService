package ru.codecrafters.AuthenticationService.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.codecrafters.AuthenticationService.controllers.BankAccountsController;
import ru.codecrafters.AuthenticationService.models.BankAccount;
import ru.codecrafters.AuthenticationService.models.Currency;
import ru.codecrafters.AuthenticationService.repositories.BankAccountsRepository;
import ru.codecrafters.AuthenticationService.repositories.CurrenciesRepository;
import ru.codecrafters.AuthenticationService.util.AccountNotCreatedException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankAccountsService {
    private final BankAccountsRepository accountsRepository;
    private final CurrenciesRepository currenciesRepository;

    public List<BankAccount> getAccountsByUserId(UUID id){
        return accountsRepository.findAllByUserId(id);
    }

    public BankAccount registerOrThrowException(UUID userId, String currencyCode) throws AccountNotCreatedException {

        BankAccount bankAccount = new BankAccount();
        Optional<Currency> currency = currenciesRepository.findByCode(currencyCode);

        if(currency.isEmpty()){
            throw new AccountNotCreatedException("Данная валюта не поддерживается или неправильно написана");
        }

        accountsRepository.save(bankAccount);

        return bankAccount;
    }
}
