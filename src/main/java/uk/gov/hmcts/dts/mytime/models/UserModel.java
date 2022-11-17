package uk.gov.hmcts.dts.mytime.models;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.gov.hmcts.dts.mytime.entities.UserEntity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserModel {

    private int Id;
    private String foreName;
    private String surName;
    private LocalDateTime startDate;
    private Double contractHours;
    private int bonusEntitlement;
    private int managerId;

    public UserModel(Optional<UserEntity> userEntity){
        this.Id = userEntity.get().getId();
        this.foreName = userEntity.get().getForeName();
        this.surName = userEntity.get().getSureName();
        this.startDate = userEntity.get().getStartDate();
        this.contractHours = userEntity.get().getContractHours();
        this.bonusEntitlement = userEntity.get().getBonusEntitlement();
        this.managerId = userEntity.get().getManagerId();
    }

}
