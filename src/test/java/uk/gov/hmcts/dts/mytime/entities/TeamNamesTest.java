package uk.gov.hmcts.dts.mytime.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TeamNamesTest {
    private static final Integer PARENT_TEAM_ID = 1;
    private static final String TEAM_NAME = "Test Team 1";
    private static final String TEAM_NAME2 = "Test Team 2";

    private static final TeamNames TEAM_NAMES = new TeamNames(TEAM_NAME, PARENT_TEAM_ID);

    @Test
    void testCreation() {
        assertThat(TEAM_NAMES)
            .as("Value does not match")
            .extracting(
                TeamNames::getParentTeamId,
                TeamNames::getTeamName)
            .containsExactly(
                PARENT_TEAM_ID,
                TEAM_NAME
            );
    }

    @Test
    void testEqualsIfSameObject() {
        TeamNames anotherTeamNames = TEAM_NAMES;

        assertThat(TEAM_NAMES.equals(anotherTeamNames))
            .as("Equals should equal")
            .isTrue();
    }

    @Test
    void testEqualsIfOnlyIdFieldDifferent() {
        TeamNames anotherTeamNames = new TeamNames(200, PARENT_TEAM_ID, TEAM_NAME);
        assertThat(TEAM_NAMES.equals(anotherTeamNames))
            .as("Equals should equal")
            .isTrue();
    }

    @Test
    void testNotEqualsIfNonIdFieldDifferent() {
        TeamNames anotherTeamNames = new TeamNames(100, PARENT_TEAM_ID, TEAM_NAME2);
        assertThat(TEAM_NAMES.equals(anotherTeamNames))
            .as("Equals should not equal")
            .isFalse();
    }

    @Test
    void testHashCodeIfSameObject() {
        TeamNames anotherTeamNames = TEAM_NAMES;
        assertThat(TEAM_NAMES.hashCode())
            .as("Hashcode should equal")
            .isEqualTo(anotherTeamNames.hashCode());
    }

    @Test
    void testHashCodeIfOnlyIdFieldDifferent() {
        TeamNames anotherTeamNames = new TeamNames(200, PARENT_TEAM_ID, TEAM_NAME);
        assertThat(TEAM_NAMES.hashCode())
            .as("Hashcode should equal")
            .isEqualTo(anotherTeamNames.hashCode());
    }

    @Test
    void testHashCodeIfNonIdFieldDifferent() {
        TeamNames anotherTeamNames = new TeamNames(100, PARENT_TEAM_ID, TEAM_NAME2);
        assertThat(TEAM_NAMES.hashCode())
            .as("Hashcode should not equal")
            .isNotEqualTo(anotherTeamNames.hashCode());
    }
}
