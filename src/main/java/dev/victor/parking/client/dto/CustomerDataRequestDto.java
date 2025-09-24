package dev.victor.parking.client.dto;

public record CustomerDataRequestDto(
        String name,
        String cellphone,
        String email,
        String taxId
) {
}
