package ru.codecrafters.AuthenticationService.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codecrafters.AuthenticationService.api.CentralBankAPI;
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
@Transactional(readOnly = true)
public class BankAccountsService {
    private final BankAccountsRepository accountsRepository;
    private final CurrenciesRepository currenciesRepository;

    private final int lengthAccountNumber = 19;

    private final CentralBankAPI centralBankAPI;

    public List<BankAccount> getAccountsByUserId(UUID id){
        return accountsRepository.findAllByUserId(id);
    }

    @Transactional
    public BankAccount registerOrThrowException(User user, String currencyCode) throws AccountNotCreatedException {
        Optional<Currency> currency = currenciesRepository.findByCode(currencyCode);

        if(currency.isEmpty()){
            throw new AccountNotCreatedException("Данная валюта не поддерживается или неправильно написана");
        }

        if(accountsRepository.findByUserAndCurrency(user, currency.get()).isPresent()){
            throw new AccountNotCreatedException("У данного пользователя уже открыт счет в этой валюте");
        }

        BankAccount bankAccount = new BankAccount(
                generateAccountNumber(lengthAccountNumber),
                new BigDecimal(0),
                currency.get(),
                user
        );

        if(!centralBankAPI.confirmCreationAccount(bankAccount)){
            throw new AccountNotCreatedException("Центральный банк отказал в создании счёта");
        }

        accountsRepository.save(bankAccount);

        return bankAccount;
    }

    private String generateAccountNumber(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public Optional<BankAccount> getAccountByUserAndAccountNumber(User user, String accountNumber) {

        return accountsRepository.findByUserAndAccountNumber(user, accountNumber);
    }
}
