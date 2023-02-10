package uk.gov.hmcts.dts.mytime.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "id" })
public class UserEntity {
    @Id
    @SequenceGenerator(name = "seq-gen", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq-gen")
    @Column(insertable = false, updatable = false, nullable = false)
    private Integer id;

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

    public UserEntity(String foreName,
            String sureName,
            LocalDateTime startDate,
            double hours,
            Integer bonusEntitlement,
            Integer managerId) {
        this.foreName = foreName;
        this.surName = sureName;
        this.startDate = startDate;
        this.contractHours = hours;
        this.bonusEntitlement = bonusEntitlement;
        this.managerId = managerId;
    }
}
