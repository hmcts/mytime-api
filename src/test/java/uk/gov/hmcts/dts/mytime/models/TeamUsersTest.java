package uk.gov.hmcts.dts.mytime.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TeamUsersTest {
    private static final Integer TEAM_ID = 1;
    private static final Integer USER_ID = 3;
    private static final Integer TEAM_ID2 = null;
    private static final Integer USER_ID2 = null;

    private static final uk.gov.hmcts.dts.mytime.entities.TeamUsers ENTITY =
        new uk.gov.hmcts.dts.mytime.entities.TeamUsers(TEAM_ID, USER_ID);

    private static final uk.gov.hmcts.dts.mytime.entities.TeamUsers ENTITY2 =
        new uk.gov.hmcts.dts.mytime.entities.TeamUsers(TEAM_ID2, USER_ID2);

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

    @Test
    void testCreationWithNullEntity() {
        assertThatThrownBy(() -> new TeamUsers(ENTITY2))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("TeamId and UserId must not be null");
    }
}
