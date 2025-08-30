package dev.victor.parking.service.dto;

import org.springframework.hateoas.RepresentationModel;

public class ClientHateoasResponse extends RepresentationModel<ClientHateoasResponse> {
    private final ClientResponseDto client;

    public ClientHateoasResponse(ClientResponseDto client) {
        this.client = client;
    }

    public ClientResponseDto getClient() {
        return client;
    }
}
