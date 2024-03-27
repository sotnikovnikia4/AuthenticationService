package ru.codecrafters.AuthenticationService.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codecrafters.AuthenticationService.models.BankAccount;
import ru.codecrafters.AuthenticationService.models.Currency;
import ru.codecrafters.AuthenticationService.models.User;
import ru.codecrafters.AuthenticationService.repositories.BankAccountsRepository;
import ru.codecrafters.AuthenticationService.repositories.CurrenciesRepository;
import ru.codecrafters.AuthenticationService.util.AccountNotCreatedException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class BankAccountsService {
    private final BankAccountsRepository accountsRepository;
    private final CurrenciesRepository currenciesRepository;

    public List<BankAccount> getAccountsByUserId(UUID id){
        return accountsRepository.findAllByUserId(id);
    }

    @Transactional
    public BankAccount registerOrThrowException(UUID userId, String currencyCode) throws AccountNotCreatedException {
        Optional<Currency> currency = currenciesRepository.findByCode(currencyCode);

        if(currency.isEmpty()){
            throw new AccountNotCreatedException("Данная валюта не поддерживается или неправильно написана");
        }

        User currentUser = new User(userId);

        if(accountsRepository.findByUserAndCurrency(currentUser, currency.get()).isPresent()){
            throw new AccountNotCreatedException("У данного пользователя уже открыт счет с этой валютой");
        }

        BankAccount bankAccount = new BankAccount();
        bankAccount.setCurrency(currency.get());
        bankAccount.setBalance(new BigDecimal(0));
        bankAccount.setAccountNumber(generateAccountNumber());
        bankAccount.setUser(currentUser);

        accountsRepository.save(bankAccount);

        return bankAccount;
    }

    private String generateAccountNumber(){//TODO заглушка
        return new Random().nextInt(Integer.MAX_VALUE / 2, Integer.MAX_VALUE) + "";
    }

    public Optional<BankAccount> getAccountByUserAndAccountNumber(User user, String accountNumber) {

        return accountsRepository.findByUserAndAccountNumber(user, accountNumber);
    }
}
