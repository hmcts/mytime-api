package uk.gov.hmcts.dts.mytime.entities;

import lombok.Setter;
import lombok.Getter;
import uk.gov.hmcts.dts.mytime.models.UserModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private int Id;

    @Column(nullable = false)
    private String foreName;

    @Column(nullable = false)
    private String sureName;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private Double contractHours;

    private int bonusEntitlement;
    private int managerId;

    public UserEntity(UserModel userModel){
        this.Id = userModel.getId();
        this.foreName = userModel.getForeName();
        this.sureName = userModel.getSurName();
        this.startDate = userModel.getStartDate();
        this.contractHours = userModel.getContractHours();
        this.bonusEntitlement = userModel.getBonusEntitlement();
        this.managerId = userModel.getManagerId();
    }

    public UserEntity() {

    }
}
