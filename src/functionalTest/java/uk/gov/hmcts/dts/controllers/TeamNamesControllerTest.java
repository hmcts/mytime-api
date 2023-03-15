package uk.gov.hmcts.dts.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.gov.hmcts.dts.controllers.util.FunctionalTestBase;
import uk.gov.hmcts.dts.mytime.models.TeamNames;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(SpringExtension.class)
@SuppressWarnings("PMD.TooManyMethods")
class TeamNamesControllerTest extends FunctionalTestBase {
    private static final String PATH = "/team-names";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Integer ID = 1;
    private static final Integer PARENT_TEAM_ID = 2;
    private static final String TEAM_NAME = "Functional";
    private static final TeamNames TEAM_NAMES = new TeamNames(ID, PARENT_TEAM_ID, TEAM_NAME);

    @BeforeAll
    static void setup() {
        OBJECT_MAPPER.findAndRegisterModules();
    }

    @Test
    void shouldCreateNewTeamNames() throws JsonProcessingException {
        String jsonInput = OBJECT_MAPPER.writeValueAsString(TEAM_NAMES);
        var response = doPostRequest(PATH, jsonInput);
        assertThat(response.statusCode())
            .isEqualTo(CREATED.value());

        TeamNames createdTeamName = response.getBody().as(TeamNames.class);
        assertThat(createdTeamName.getTeamName()).isEqualTo(TEAM_NAME);
        assertThat(createdTeamName.getParentTeamId()).isEqualTo(PARENT_TEAM_ID);
    }

    @Test
    void shouldUpdateTeamNames() throws JsonProcessingException {
        TeamNames teamNames = new TeamNames(ID, PARENT_TEAM_ID, TEAM_NAME);
        teamNames.setTeamName("Updated Team Name");
        var jsonObj = OBJECT_MAPPER.writeValueAsString(teamNames);
        var response = doPutRequest(PATH, jsonObj);
        var returnedTeamName = response.getBody().as(TeamNames.class);
        assertThat(returnedTeamName.getTeamName()).isEqualTo("Updated Team Name");
    }

    @Test
    void shouldGetTeamNameByID() {
        var path  = PATH + "/2";
        var response = doGetRequest(path);
        assertThat(response.statusCode())
            .isEqualTo(OK.value());

        TeamNames savedTeamName = response.getBody().as(TeamNames.class);
        assertThat(savedTeamName.getTeamName()).isEqualTo("Digital & Technology Services");
    }

    @Test
    void shouldGetParentTeamNameByID() {
        var path  = PATH + "/parent/5";
        var response = doGetRequest(path);
        assertThat(response.statusCode())
            .isEqualTo(OK.value());

        TeamNames savedTeamName = response.getBody().as(TeamNames.class);
        assertThat(savedTeamName.getTeamName()).isEqualTo("Cross Cutting");
    }

    @Test
    void shouldDeleteUser() throws JsonProcessingException {
        var path = PATH + "/1";
        TeamNames teamNameToDelete = new TeamNames();
        teamNameToDelete.setId(ID);
        var jsonObj = OBJECT_MAPPER.writeValueAsString(teamNameToDelete);
        var response = doDeleteRequest(path, jsonObj);
        assertThat(response.statusCode()).isEqualTo(OK.value());
    }
}
