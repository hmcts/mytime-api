package uk.gov.hmcts.dts.mytime.exceptions;

public class UserException extends BaseException {

    public static final long serialVersionUID = 42L;

    public UserException(int httpResponseCode, String errorMessage) {

        super(httpResponseCode, errorMessage);
    }
}
