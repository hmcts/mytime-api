package uk.gov.hmcts.dts.mytime.entities;

import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
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
}
