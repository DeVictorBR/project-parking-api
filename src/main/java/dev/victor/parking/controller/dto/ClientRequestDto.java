package dev.victor.parking.controller.dto;

import dev.victor.parking.entity.Client;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record ClientRequestDto(
        @NotBlank String name,
        @NotBlank String phoneNumber,
        @Email @NotBlank String email,
        @CPF @NotBlank String cpf
) {
    public Client toEntity() {
        Client client = new Client();
        client.setName(name);
        client.setPhoneNumber(phoneNumber);
        client.setEmail(email);
        client.setCpf(cpf);
        return client;
    }
}
