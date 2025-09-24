package dev.victor.parking.exception;

import org.springframework.http.ProblemDetail;

public abstract class AbacatePayException extends RuntimeException {

    public AbacatePayException(String message) {
        super(message);
    }

    public abstract ProblemDetail toProblemDetail();
}
