package ru.codecrafters.AuthenticationService.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.codecrafters.AuthenticationService.dto.CreateOrderDTO;
import ru.codecrafters.AuthenticationService.util.AnySuccessfulResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@PropertySource("classpath:/.env")
@RequiredArgsConstructor
public class OrdersAPI {

    @Value("${orders_url_create_order}")
    private String ordersUrlCreateOrder;

    private final RestTemplate restTemplate;

    public String sendRequestCreateOrderAndGetMessageResponse(UUID userId, CreateOrderDTO createOrderDTO, String currencyFrom, String currencyTo){
        Map<String, Object> jsonBody = new HashMap<>();

        jsonBody.put("UserId", userId);
        jsonBody.put("OrderType", createOrderDTO.getOrderType());
        jsonBody.put("CurrencyFrom", currencyFrom);
        jsonBody.put("CurrencyFromValue", createOrderDTO.getCurrencyFromValue());
        jsonBody.put("BankAccountFrom", createOrderDTO.getBankAccountFrom());
        jsonBody.put("CurrencyTo", currencyTo);
        jsonBody.put("BankAccountTo", createOrderDTO.getBankAccountTo());

        return getResponse(jsonBody, ordersUrlCreateOrder);
    }

    private String getResponse(Object obj, String url){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> httpEntity = new HttpEntity<>(obj, headers);

        return restTemplate.postForObject(url, httpEntity, String.class);
    }
}
