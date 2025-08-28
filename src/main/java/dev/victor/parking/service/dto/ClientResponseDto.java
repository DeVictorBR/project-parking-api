package dev.victor.parking.service.dto;

import dev.victor.parking.entity.Client;

public record ClientResponseDto(Long clientId,
                                String name,
                                String phoneNumber,
                                String email,
                                String cpf) {
    public static ClientResponseDto toDto(Client client) {
        return new ClientResponseDto(
                client.getId(),
                client.getName(),
                client.getPhoneNumber(),
                client.getEmail(),
                client.getCpf()
        );
    }
}
