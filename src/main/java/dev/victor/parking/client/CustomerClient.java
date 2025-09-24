package dev.victor.parking.client;

import dev.victor.parking.client.dto.CustomerDataRequestDto;
import dev.victor.parking.client.dto.CustomerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "CustomerClient", url = "${abacate-pay.api-url}")
public interface CustomerClient {

    @PostMapping("/customer/create")
    ResponseEntity<CustomerResponseDto> createCustomer(@RequestHeader("Authorization") String apiKey, @RequestBody CustomerDataRequestDto requestDto);
}
