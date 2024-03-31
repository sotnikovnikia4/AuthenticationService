package ru.codecrafters.AuthenticationService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Getter
@Setter
@NoArgsConstructor
public class TransferMoneyDTO {

    @NotBlank
    private String bankAccountFrom;

    @DecimalMin("0")
    @NotNull
    private BigDecimal moneyMinus;

    @NotBlank
    private String bankAccountTo;

    @DecimalMin("0")
    @NotNull
    private BigDecimal moneyPlus;
}
