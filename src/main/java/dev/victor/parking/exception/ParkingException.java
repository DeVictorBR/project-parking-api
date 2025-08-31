package dev.victor.parking.exception;

import org.springframework.http.ProblemDetail;

public abstract class ParkingException extends RuntimeException {

    public ParkingException(String message) {
        super(message);
    }

    public ParkingException(Throwable cause) {
        super(cause);
    }

    public abstract ProblemDetail toProblemDetail();
}
