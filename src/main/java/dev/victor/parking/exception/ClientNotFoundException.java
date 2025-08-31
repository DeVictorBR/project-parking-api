package dev.victor.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class ClientNotFoundException extends ParkingException {

    private final String detail;

    public ClientNotFoundException(String detail) {
        super(detail);
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle("Client not found");
        pd.setDetail(detail);
        return pd;
    }
}
