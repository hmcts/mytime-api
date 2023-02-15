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
import uk.gov.hmcts.dts.mytime.models.TeamUsers;
import uk.gov.hmcts.dts.mytime.services.TeamUsersService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamUsersControllerTest {
    private static final Integer ID = 123;
    private static final String ASSERTION_MESSAGE = "Team Name does not match";

    private static final TeamUsers TEAM_USERS = new TeamUsers();
    private static final TeamUsers RESULT = new TeamUsers();

    @Mock
    private TeamUsersService teamUsersService;

    @InjectMocks
    private TeamUsersController teamUsersController;

    @BeforeAll
    static void setUp() {

    }

    @Test
    void shouldCreateTeamName() {
        when(teamUsersService.createTeamUser(TEAM_USERS)).thenReturn(RESULT);

        assertThat(teamUsersController.createTeamUsers(TEAM_USERS))
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
    void shouldGetTeamUser() {
        when(teamUsersService.getTeamUserById(ID)).thenReturn(RESULT);

        assertThat(teamUsersController.getTeamUsers(ID))
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
    void shouldDeleteTeamUser() {
        doNothing().when(teamUsersService).deleteTeamUserById(ID);

        assertThat(teamUsersController.deleteTeamUsers(ID))
                .as(ASSERTION_MESSAGE)
                .extracting(
                    ResponseEntity::getStatusCode,
                    HttpEntity::getBody
                )
                .containsExactly(
                        HttpStatus.OK,
                        ID);
    }

    @Test
    void shouldGetAllTeamUsers() {
        when(teamUsersService.getAllTeamUsers()).thenReturn(List.of(RESULT));

        assertThat(teamUsersController.getAllTeamUsers())
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
}
