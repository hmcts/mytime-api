package uk.gov.hmcts.dts.mytime.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private String message;
    private LocalDateTime timestamp;
}
