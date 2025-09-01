package dev.victor.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class ContractNotFoundException extends ParkingException {

    private final String detail;

    public ContractNotFoundException(String detail) {
        super(detail);
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle("Contract not found");
        pd.setDetail(detail);
        return pd;
    }
}
