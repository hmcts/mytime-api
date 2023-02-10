package uk.gov.hmcts.dts.mytime.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "team_names")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
public class TeamNames {
    @Id
    @SequenceGenerator(name = "seq-gen", sequenceName = "team_names_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq-gen")
    @Column(insertable = false, updatable = false, nullable = false)
    private Integer id;

    @Column(nullable = true, name = "parent_team_id")
    private Integer parentTeamId;

    @Column(nullable = false, name = "team_name")
    private String teamName;

    public TeamNames(String teamName, Integer parentTeamId) {
        this.teamName = teamName;
        this.parentTeamId = parentTeamId;
    }
}
