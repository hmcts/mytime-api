package uk.gov.hmcts.dts.mytime.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamUsers {

    Integer id;

    @NotNull(message = "Team ID is required")
    Integer teamId;

    @NotNull(message = "User ID is required")
    Integer userId;

    public TeamUsers(uk.gov.hmcts.dts.mytime.entities.TeamUsers entity) {
        this.id = entity.getId();
        if (entity.getTeamId() == null || entity.getUserId() == null) {
            throw new IllegalArgumentException("TeamId and UserId must not be null");
        } else {
            this.teamId = entity.getTeamId();
            this.userId = entity.getUserId();
        }
    }
}
