package uk.gov.hmcts.dts.mytime.entities;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.dts.mytime.models.LeaveStatus;
import uk.gov.hmcts.dts.mytime.models.LeaveType;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class LeaveRequestTest {
    private static final Integer EMPLOYEE_ID = 1;
    private static final Integer APPROVER_ID = 2;
    private static final LeaveType TYPE = LeaveType.HOLIDAY;
    private static final LeaveStatus STATUS = LeaveStatus.AWAITING;
    private static final LocalDate START_DATE = LocalDate.now();
    private static final LocalDate END_DATE = START_DATE.plusDays(5);
    private static final String REQUEST_COMMENT = "RequestComment";
    private static final String APPROVER_COMMENT = "ApproverComment";

    @Test
    void testCreation() {
        LeaveRequest leaveRequest = new LeaveRequest(EMPLOYEE_ID, APPROVER_ID, TYPE, STATUS, START_DATE, END_DATE,
                                                     REQUEST_COMMENT, APPROVER_COMMENT);
        assertThat(leaveRequest)
            .as("Value does not match")
            .extracting(
                LeaveRequest::getEmployeeId,
                LeaveRequest::getApproverId,
                LeaveRequest::getType,
                LeaveRequest::getStatus,
                LeaveRequest::getStartDate,
                LeaveRequest::getEndDate,
                LeaveRequest::getRequestComment,
                LeaveRequest::getApproverComment)
            .containsExactly(
                EMPLOYEE_ID,
                APPROVER_ID,
                TYPE,
                STATUS,
                START_DATE,
                END_DATE,
                REQUEST_COMMENT,
                APPROVER_COMMENT
            );
    }
}
