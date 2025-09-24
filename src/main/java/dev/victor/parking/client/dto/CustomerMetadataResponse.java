package dev.victor.parking.client.dto;

public record CustomerMetadataResponse(
        String name,
        String cellphone,
        String email,
        String taxId
) {
}
