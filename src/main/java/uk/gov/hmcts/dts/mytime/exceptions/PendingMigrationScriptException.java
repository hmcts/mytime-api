package uk.gov.hmcts.dts.mytime.exceptions;

public class PendingMigrationScriptException extends RuntimeException {

    private static final long serialVersionUID = -7544166728568238022L;

    public PendingMigrationScriptException(String script) {
        super("Found migration not yet applied: " + script);
    }
}
