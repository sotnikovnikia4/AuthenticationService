package ru.codecrafters.AuthenticationService.api;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.codecrafters.AuthenticationService.dto.RequestCreationAccountDTO;
import ru.codecrafters.AuthenticationService.dto.UserResponseDTO;
import ru.codecrafters.AuthenticationService.models.BankAccount;
import ru.codecrafters.AuthenticationService.models.User;

@Component
@PropertySource("classpath:/.env")
@RequiredArgsConstructor
public class CentralBankAPI {

    private final ModelMapper modelMapper;
    @Value("${central_bank_url_new_account}")
    private String centralBankUrlNewAccount;
    @Value("${central_bank_url_new_user}")
    private String centralBankUrlNewUser;

    public boolean confirmCreationAccount(BankAccount account) {
        return getConfirm(convertToRequestCreationAccountDTO(account), centralBankUrlNewAccount);
    }

    public boolean confirmRegistrationAccount(User user){
        return getConfirm(convertToUserResponseDTO(user), centralBankUrlNewUser);
    }

    private boolean getConfirm(Object obj, String url){
        RestTemplate restTemplate =  new RestTemplate();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> httpEntity = new HttpEntity<>(obj, headers);

        String response = restTemplate.postForObject(url, httpEntity, String.class);

        return Boolean.parseBoolean(response);
    }

    private RequestCreationAccountDTO convertToRequestCreationAccountDTO(BankAccount account){
        return modelMapper.map(account, RequestCreationAccountDTO.class);
    }

    private UserResponseDTO convertToUserResponseDTO(User user){
        return modelMapper.map(user, UserResponseDTO.class);
    }
}
