package dev.victor.parking.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AbacatePayClient {

    @Value("${abacate-pay.api-key}")
    private String API_KEY;

    public String getAPI_KEY() {
        return API_KEY;
    }
}
