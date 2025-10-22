package com.hrms.model;

import java.sql.Date;

public class LeaveRequest {
    private int leaveId;
    private int employeeId;
    private String leaveType;
    private Date startDate;
    private Date endDate;
    private int daysRequested;
    private String reason;
    private String status;
    private int approvedBy;

    public LeaveRequest(int leaveId, int employeeId, String leaveType, Date startDate, Date endDate, int daysRequested, String reason, String status) {
        this.leaveId = leaveId;
        this.employeeId = employeeId;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.daysRequested = daysRequested;
        this.reason = reason;
        this.status = status;
    }

    // Getters and Setters
    public int getLeaveId() { return leaveId; }
    public void setLeaveId(int leaveId) { this.leaveId = leaveId; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public String getLeaveType() { return leaveType; }
    public void setLeaveType(String leaveType) { this.leaveType = leaveType; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public int getDaysRequested() { return daysRequested; }
    public void setDaysRequested(int daysRequested) { this.daysRequested = daysRequested; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getApprovedBy() { return approvedBy; }
    public void setApprovedBy(int approvedBy) { this.approvedBy = approvedBy; }

    @Override
    public String toString() {
        return "LeaveRequest [leaveId=" + leaveId + ", employeeId=" + employeeId + 
               ", leaveType=" + leaveType + ", startDate=" + startDate + 
               ", endDate=" + endDate + ", daysRequested=" + daysRequested + 
               ", reason=" + reason + ", status=" + status + "]";
    }
}