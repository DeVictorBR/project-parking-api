package dev.victor.parking.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CustomerDataResponseDto(
        String id,
        @JsonProperty("metadata") CustomerMetadataResponse metadataResponse
) {
}
