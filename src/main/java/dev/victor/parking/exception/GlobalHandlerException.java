package dev.victor.parking.exception;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(ParkingException.class)
    public ProblemDetail handleParkingException(ParkingException e) {
        return e.toProblemaDetail();
    }
}
