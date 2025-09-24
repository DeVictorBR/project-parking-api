package dev.victor.parking.service;

import dev.victor.parking.client.AbacatePayClient;
import dev.victor.parking.client.CustomerClient;
import dev.victor.parking.client.dto.CustomerDataRequestDto;
import dev.victor.parking.client.dto.CustomerResponseDto;
import dev.victor.parking.exception.CustomerException;
import org.springframework.stereotype.Service;

@Service
public class CustomerClientService {

    private final CustomerClient customerClient;
    private final AbacatePayClient abacatePayClient;

    public CustomerClientService(CustomerClient customerClient, AbacatePayClient abacatePayClient) {
        this.customerClient = customerClient;
        this.abacatePayClient = abacatePayClient;
    }

    public CustomerResponseDto createCustomer(CustomerDataRequestDto requestDto) {
        var response =  customerClient.createCustomer(abacatePayClient.getAPI_KEY(), requestDto);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomerException("Create customer error", response.getStatusCode().value());
        }
        return response.getBody();
    }


}
