package dev.victor.parking.exception;

import org.springframework.http.ProblemDetail;

public class CustomerException extends AbacatePayException {

    private final String detail;
    private final Integer status;

    public CustomerException(String detail, Integer status) {
        super(detail);
        this.detail = detail;
        this.status = status;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(status);
        pd.setTitle("Customer exception");
        pd.setDetail(detail);
        return pd;
    }
}
