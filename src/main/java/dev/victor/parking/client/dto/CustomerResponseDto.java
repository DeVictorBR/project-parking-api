package dev.victor.parking.client.dto;

public record CustomerResponseDto(
        CustomerDataResponseDto data,
        String error
) {
}
