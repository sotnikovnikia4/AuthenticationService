package ru.codecrafters.AuthenticationService.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Setter
@Getter
public class BankAccountDTO {

    public String number_count;
    public BigDecimal balance;
    public CurrencyDTO currency;
}
