package uk.gov.hmcts.dts.mytime.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequest {
    Integer id;
    @NotNull(message = "Employee ID is required")
    private Integer employeeId;

    @NotNull(message = "Approver ID is required")
    private Integer approverId;

    @NotNull(message = "Leave type is required")
    private LeaveType type;

    private LeaveStatus status;

    @NotNull(message = "start date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate endDate;

    private String requestComment;

    private String approverComment;

    public LeaveRequest(uk.gov.hmcts.dts.mytime.entities.LeaveRequest entity) {
        this.id = entity.getId();
        this.employeeId = entity.getEmployeeId();
        this.approverId = entity.getApproverId();
        this.type = entity.getType();
        this.status = entity.getStatus();
        this.startDate = entity.getStartDate();
        this.endDate = entity.getEndDate();
        this.requestComment = entity.getRequestComment();
        this.approverComment = entity.getApproverComment();
    }
}
