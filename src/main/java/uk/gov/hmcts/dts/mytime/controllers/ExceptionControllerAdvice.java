package uk.gov.hmcts.dts.mytime.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uk.gov.hmcts.dts.mytime.helpers.ErrorResponse;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler() {
        ErrorResponse error = new ErrorResponse(
            500, "Please contact the help desk"
        );

        return new ResponseEntity<>(error, HttpStatus.OK);
    }
}
