package uk.gov.hmcts.dts.mytime.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class RootControllerTest {
    private final RootController rootController = new RootController();

    @Test
    void shouldWelcome() {
        ResponseEntity<String> welcomeResponse = rootController.welcome();
        assertThat(welcomeResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(welcomeResponse.getBody()).isEqualTo("Welcome to my-time-api");
    }
}
