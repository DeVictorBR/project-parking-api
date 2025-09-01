package dev.victor.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class ContractAlreadyExistsException extends ParkingException {

    private final String detail;

    public ContractAlreadyExistsException(String detail) {
        super(detail);
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pd.setTitle("Contract already exists");
        pd.setDetail(detail);
        return pd;
    }
}
