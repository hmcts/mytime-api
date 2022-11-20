package uk.gov.hmcts.dts.mytime.exceptions;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6579941826346533850L;

    public NotFoundException(String criteria) {
        super("Not found: " + criteria);
    }
}
