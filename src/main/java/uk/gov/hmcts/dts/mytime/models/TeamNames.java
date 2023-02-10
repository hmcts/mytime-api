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
public class TeamNames {
    
    Integer id;

    Integer parentTeamId;
    
    @NotNull(message = "Team Name is required")
    String teamName;

    public TeamNames(uk.gov.hmcts.dts.mytime.entities.TeamNames entity) {
        this.id = entity.getId();
        this.teamName = entity.getTeamName();
        this.parentTeamId = entity.getParentTeamId();
    }
}
