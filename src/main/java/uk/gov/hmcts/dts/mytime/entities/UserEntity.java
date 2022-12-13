package uk.gov.hmcts.dts.mytime.entities;

import lombok.Setter;
import lombok.Getter;
import uk.gov.hmcts.dts.mytime.models.UserModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
public class UserEntity {
    @Id
    @SequenceGenerator(name = "seq-gen", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq-gen")
    private Integer Id;
    @Column(nullable = false, name = "forename")
    private String foreName;
    @Column(nullable = false, name = "surname")
    private String surName;
    @Column(nullable = false, name = "startdate")
    private LocalDateTime startDate;
    @Column(nullable = false, name = "contracthours")
    private Double contractHours;

    @Column(name = "bonusentitlement")
    private Integer bonusEntitlement;

    @Column(name = "managerid")
    private Integer managerId;

    public UserEntity(UserModel userModel) {
        this.Id = userModel.getId();
        this.foreName = userModel.getForeName();
        this.surName = userModel.getSurName();
        this.startDate = userModel.getStartDate();
        this.contractHours = userModel.getContractHours();
        this.bonusEntitlement = userModel.getBonusEntitlement();
        this.managerId = userModel.getManagerId();
    }

    public UserEntity(
        int id,
        String foreName,
        String sureName,
        LocalDateTime startDate,
        double hours,
        int bonusEntitlement,
        int managerId) {
        this.Id = id;
        this.foreName = foreName;
        this.surName = sureName;
        this.startDate = startDate;
        this.contractHours = hours;
        this.bonusEntitlement = bonusEntitlement;
        this.managerId = managerId;
    }

    public UserEntity() {
    }
}
