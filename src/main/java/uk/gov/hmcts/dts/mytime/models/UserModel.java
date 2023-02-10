package uk.gov.hmcts.dts.mytime.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserModel {

    private Integer id;
    private String foreName;
    private String surName;
    private LocalDateTime startDate;
    private Double contractHours;
    private Integer bonusEntitlement;
    private Integer managerId;

    public UserModel(uk.gov.hmcts.dts.mytime.entities.UserEntity entity) {
        this.id = entity.getId();
        this.foreName = entity.getForeName();
        this.surName = entity.getSurName();
        this.startDate = entity.getStartDate();
        this.contractHours = entity.getContractHours();
        this.bonusEntitlement = entity.getBonusEntitlement();
        this.managerId = entity.getManagerId();
    }
}
