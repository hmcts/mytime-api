package uk.gov.hmcts.dts.mytime.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import uk.gov.hmcts.dts.mytime.exceptions.PendingMigrationScriptException;

import java.util.stream.Stream;

public class FlywayNoOpStrategy implements FlywayMigrationStrategy {

    @Override
    public void migrate(Flyway flyway) {
        Stream.of(flyway.info().all())
            .filter(info -> !info.getState().isApplied())
            .findFirst()
            .ifPresent(info -> {
                throw new PendingMigrationScriptException(info.getScript());
            });
    }
}
