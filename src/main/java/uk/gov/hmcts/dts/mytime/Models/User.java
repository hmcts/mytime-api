package uk.gov.hmcts.dts.mytime.Models;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private String foreName;
    private String sureName;
    private LocalDateTime startDate;
    private Double contractHours;
    private int bonusEntitlement;
    private int managerId;
}
