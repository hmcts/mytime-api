package uk.gov.hmcts.dts.mytime.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
