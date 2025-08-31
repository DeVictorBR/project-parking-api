package dev.victor.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class VehicleNotFoundException extends ParkingException {

    private final String detail;

    public VehicleNotFoundException(String detail) {
        super(detail);
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemaDetail() {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle("Vehicle not found");
        pd.setDetail(detail);
        return pd;
    }
}
