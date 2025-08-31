package dev.victor.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.util.List;

public class DuplicateDataException extends ParkingException {

    private final List<String> fields;
    private final String detail;

    public DuplicateDataException(String detail, List<String> fields) {
        super(detail);
        this.fields = fields;
        this.detail = detail;
    }

    public List<String> getFields() {
        return fields;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pd.setTitle("Duplicate data error");
        pd.setDetail(detail);
        pd.setProperty("duplicated fields", getFields());
        return pd;
    }
}
