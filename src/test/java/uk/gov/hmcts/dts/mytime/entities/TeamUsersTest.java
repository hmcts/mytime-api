package uk.gov.hmcts.dts.mytime.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TeamUsersTest {
    private static final Integer TEAM_ID = 11;
    private static final Integer USER_ID = 22;
    private static final Integer USER_ID2 = 44;

    private static final TeamUsers TEAM_USERS = new TeamUsers(TEAM_ID, USER_ID);

    @Test
    void testCreation() {
        assertThat(TEAM_USERS)
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
    void testEqualsIfSameObject() {
        TeamUsers anotherTeamUsers = TEAM_USERS;

        assertThat(TEAM_USERS.equals(anotherTeamUsers))
            .as("Equals should equal")
            .isTrue();
    }

    @Test
    void testEqualsIfOnlyIdFieldDifferent() {
        TeamUsers anotherTeamUsers = new TeamUsers(200, TEAM_ID, USER_ID);
        assertThat(TEAM_USERS.equals(anotherTeamUsers))
            .as("Equals should equal")
            .isTrue();
    }

    @Test
    void testNotEqualsIfNonIdFieldDifferent() {
        TeamUsers anotherTeamUsers = new TeamUsers(100, TEAM_ID, USER_ID2);
        assertThat(TEAM_USERS.equals(anotherTeamUsers))
            .as("Equals should not equal")
            .isFalse();
    }

    @Test
    void testHashCodeIfSameObject() {
        TeamUsers anotherTeamUsers = TEAM_USERS;
        assertThat(TEAM_USERS.hashCode())
            .as("Hashcode should equal")
            .hasSameHashCodeAs(anotherTeamUsers.hashCode());
    }

    @Test
    void testHashCodeIfOnlyIdFieldDifferent() {
        TeamUsers anotherTeamUsers = new TeamUsers(200, TEAM_ID, USER_ID);
        assertThat(TEAM_USERS.hashCode())
            .as("Hashcode should equal")
            .hasSameHashCodeAs(anotherTeamUsers.hashCode());
    }

    @Test
    void testHashCodeIfNonIdFieldDifferent() {
        TeamUsers anotherTeamUsers = new TeamUsers(100, TEAM_ID, USER_ID2);
        assertThat(TEAM_USERS.hashCode())
            .as("Hashcode should not equal")
            .isNotEqualTo(anotherTeamUsers.hashCode());
    }
}
