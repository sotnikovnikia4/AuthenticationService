package ru.codecrafters.AuthenticationService.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.codecrafters.AuthenticationService.api.CentralBankAPI;
import ru.codecrafters.AuthenticationService.api.OrdersAPI;
import ru.codecrafters.AuthenticationService.dto.CreateOrderDTO;
import ru.codecrafters.AuthenticationService.dto.TransferMoneyDTO;
import ru.codecrafters.AuthenticationService.models.BankAccount;
import ru.codecrafters.AuthenticationService.models.Currency;
import ru.codecrafters.AuthenticationService.models.User;
import ru.codecrafters.AuthenticationService.repositories.BankAccountsRepository;
import ru.codecrafters.AuthenticationService.repositories.CurrenciesRepository;
import ru.codecrafters.AuthenticationService.util.AccountNotCreatedException;
import ru.codecrafters.AuthenticationService.util.AnySuccessfulResponse;
import ru.codecrafters.AuthenticationService.util.MoneyNotTransferredException;
import ru.codecrafters.AuthenticationService.util.OrderNotCreatedException;

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
    private final OrdersAPI ordersAPI;

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
                new BigDecimal(10000),
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

    @Transactional
    public void transferMoneyOrThrowException(TransferMoneyDTO transferMoneyDTO) throws MoneyNotTransferredException {
        Optional<BankAccount> accountFrom = accountsRepository.findByAccountNumber(transferMoneyDTO.getBankAccountFrom());
        if(accountFrom.isEmpty()){
            throw new MoneyNotTransferredException("Счет, с котороого нужно перевести деньги, не существует в базе данных");
        }

        Optional<BankAccount> accountTo = accountsRepository.findByAccountNumber(transferMoneyDTO.getBankAccountTo());
        if(accountTo.isEmpty()){
            throw new MoneyNotTransferredException("Счет, на который нужно перевести деньги, не существует в базе данных");
        }

        if(!centralBankAPI.confirmTransferMoney(transferMoneyDTO)){
            throw new MoneyNotTransferredException("Центральный банк отклонил запрос на перевод денег");
        }

        accountFrom.get().setBalance(accountFrom.get().getBalance().subtract(transferMoneyDTO.getMoneyMinus()));
        accountTo.get().setBalance(accountTo.get().getBalance().add(transferMoneyDTO.getMoneyPlus()));
    }

    @Transactional
    public String createOrderAndReturnAnswer(UUID userId, CreateOrderDTO createOrderDTO) {
        Optional<BankAccount> accountFrom = accountsRepository.findByAccountNumber(createOrderDTO.getBankAccountFrom());
        if(accountFrom.isEmpty()){
            throw new OrderNotCreatedException("Счет bankAccountFrom не существует в базе данных");
        }

        Optional<BankAccount> accountTo = accountsRepository.findByAccountNumber(createOrderDTO.getBankAccountTo());
        if(accountTo.isEmpty()){
            throw new OrderNotCreatedException("Счет bankAccountTo не существует в базе данных");
        }

        return ordersAPI.sendRequestCreateOrderAndGetMessageResponse(userId, createOrderDTO, accountFrom.get().getCurrency().getCode(), accountTo.get().getCurrency().getCode());
    }
}
