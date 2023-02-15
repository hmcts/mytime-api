package uk.gov.hmcts.dts.mytime.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TeamUsersTest {
    private static final Integer TEAM_ID = 1;
    private static final Integer USER_ID = 3;

    private static final uk.gov.hmcts.dts.mytime.entities.TeamUsers ENTITY =
        new uk.gov.hmcts.dts.mytime.entities.TeamUsers(TEAM_ID, USER_ID);

    @Test
    void testCreationWithEntity() {
        TeamUsers teamNames = new TeamUsers(ENTITY);

        assertThat(teamNames)
            .as("Value does not match")
            .extracting(
                TeamUsers::getTeamId,
                TeamUsers::getUserId)
            .containsExactly(
                TEAM_ID,
                USER_ID
            );
    }
}
