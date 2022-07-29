package uk.gov.hmcts.dts.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.hmcts.dts.controllers.util.FunctionalTestBase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith({SpringExtension.class})
class RootControllerTest extends FunctionalTestBase {

    @Test
    void shouldDisplayWelcomeText() {
        final var response = doGetRequest("/");
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }
}
