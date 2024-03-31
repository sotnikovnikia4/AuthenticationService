package ru.codecrafters.AuthenticationService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrderDTO {
    @NotBlank
    private String orderType;

    @NotBlank
    private String currencyFrom;

    @NotNull
    private BigDecimal currencyFromValue;

    @NotBlank
    private String bankAccountFrom;

    @NotBlank
    private String currencyTo;

    @NotBlank
    private String bankAccountTo;
}
