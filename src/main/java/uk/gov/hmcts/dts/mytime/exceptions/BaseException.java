package uk.gov.hmcts.dts.mytime.exceptions;

public class BaseException extends Exception {

    private int httpResponseCode;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    public BaseException(int httpResponseCode, String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.httpResponseCode = httpResponseCode;
    }
}
