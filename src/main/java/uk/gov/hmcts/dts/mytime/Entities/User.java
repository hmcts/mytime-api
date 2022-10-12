package uk.gov.hmcts.dts.mytime.Entities;

import lombok.Setter;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    private String foreName;
    private String sureName;
    private LocalDateTime startDate;
    private Double contractHours;
    private int bonusEntitlement;
    private int managerId;
}
