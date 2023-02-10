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
@Table(name = "team_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
public class TeamUsers {
    @Id
    @SequenceGenerator(name = "seq-gen", sequenceName = "team_users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq-gen")
    @Column(insertable = false, updatable = false, nullable = false)
    private int id;

    @Column(nullable = false, name = "team_id")
    private Integer teamId;

    @Column(nullable = false, name = "user_id")
    private Integer userId;

    public TeamUsers(Integer teamId, Integer userId) {
        this.teamId = teamId;
        this.userId = userId;
    }
}
