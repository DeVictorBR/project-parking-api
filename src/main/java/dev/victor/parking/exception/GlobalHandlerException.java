package dev.victor.parking.exception;

import dev.victor.parking.exception.dto.InvalidParamDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(ParkingException.class)
    public ProblemDetail handleParkingException(ParkingException e) {
        return e.toProblemDetail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<InvalidParamDto> invalidParams = e.getFieldErrors()
                .stream()
                .map(fe -> new InvalidParamDto(fe.getField(), fe.getDefaultMessage()))
                .toList();
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Invalid request parameters");
        pd.setDetail("There is invalid fields on the request");
        pd.setProperty("invalid params", invalidParams);
        return pd;
    }
}
