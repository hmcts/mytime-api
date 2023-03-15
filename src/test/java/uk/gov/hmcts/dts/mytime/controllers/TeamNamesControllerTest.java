package uk.gov.hmcts.dts.mytime.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.gov.hmcts.dts.mytime.models.TeamNames;
import uk.gov.hmcts.dts.mytime.services.TeamNamesService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamNamesControllerTest {
    private static final Integer ID = 123;
    private static final String ASSERTION_MESSAGE = "Team Name does not match";

    private static final TeamNames TEAM_NAMES = new TeamNames();
    private static final TeamNames RESULT = new TeamNames();

    @Mock
    private TeamNamesService teamNamesService;

    @InjectMocks
    private TeamNamesController teamNamesController;

    @BeforeAll
    static void setUp() {

    }

    @Test
    void shouldCreateTeamName() {
        when(teamNamesService.createTeam(TEAM_NAMES)).thenReturn(RESULT);

        assertThat(teamNamesController.createTeamNames(TEAM_NAMES))
            .as(ASSERTION_MESSAGE)
            .extracting(
                ResponseEntity::getStatusCode,
                HttpEntity::getBody
            )
            .containsExactly(
                HttpStatus.CREATED,
                RESULT
            );
    }

    @Test
    void shouldGetTeamName() {
        when(teamNamesService.getTeamNameById(ID)).thenReturn(RESULT);

        assertThat(teamNamesController.getTeamName(ID))
            .as(ASSERTION_MESSAGE)
            .extracting(
                ResponseEntity::getStatusCode,
                HttpEntity::getBody
            )
            .containsExactly(
                HttpStatus.OK,
                RESULT
            );
    }

    @Test
    void shouldDeleteTeamName() {
        doNothing().when(teamNamesService).deleteTeamById(ID);

        assertThat(teamNamesController.deleteTeamNames(ID))
            .as(ASSERTION_MESSAGE)
            .extracting(
                ResponseEntity::getStatusCode,
                HttpEntity::getBody
            )
            .containsExactly(
                HttpStatus.OK,
                ID
            );
    }

    @Test
    void shouldGetAllTeamNames() {
        when(teamNamesService.getAllTeamNames()).thenReturn(List.of(RESULT));

        assertThat(teamNamesController.getAllTeamNames())
            .as(ASSERTION_MESSAGE)
            .extracting(
                ResponseEntity::getStatusCode,
                HttpEntity::getBody
            )
            .containsExactly(
                HttpStatus.OK,
                List.of(RESULT)
            );
    }

    @Test
    void shouldGetParentTeamName() {
        when(teamNamesService.getParentTeamNameById(ID)).thenReturn(RESULT);

        assertThat(teamNamesController.getParentTeamName(ID))
            .as(ASSERTION_MESSAGE)
            .extracting(
                ResponseEntity::getStatusCode,
                HttpEntity::getBody
            )
            .containsExactly(
                HttpStatus.OK,
                RESULT
            );
    }

    @Test
    void shouldUpdateTeamName() {
        when(teamNamesService.updateTeam(TEAM_NAMES)).thenReturn(RESULT);

        assertThat(teamNamesController.updateTeamNames(TEAM_NAMES))
            .as(ASSERTION_MESSAGE)
            .extracting(
                ResponseEntity::getStatusCode,
                HttpEntity::getBody
            )
            .containsExactly(
                HttpStatus.CREATED,
                RESULT
            );
    }
}
