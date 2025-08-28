package dev.victor.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public abstract class ParkingException extends RuntimeException {

    public ParkingException(String message) {
        super(message);
    }

    public ParkingException(Throwable cause) {
        super(cause);
    }

    public ProblemDetail toProblemaDetail() {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pd.setTitle("Parking Internal Server Error");
        pd.setDetail("Contact Parking");
        return pd;
    }
}
