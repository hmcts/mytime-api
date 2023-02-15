package uk.gov.hmcts.dts.mytime.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TeamNamesTest {
    private static final Integer PARENT_TEAM_ID = 1;
    private static final String TEAM_NAME = "Test Team 1";

    private static final uk.gov.hmcts.dts.mytime.entities.TeamNames ENTITY =
        new uk.gov.hmcts.dts.mytime.entities.TeamNames(TEAM_NAME, PARENT_TEAM_ID);

    @Test
    void testCreationWithEntity() {
        TeamNames teamNames = new TeamNames(ENTITY);

        assertThat(teamNames)
            .as("Value does not match")
            .extracting(
                TeamNames::getParentTeamId,
                TeamNames::getTeamName)
            .containsExactly(
                PARENT_TEAM_ID,
                TEAM_NAME
            );
    }
}
