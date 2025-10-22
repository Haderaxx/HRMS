package com.hrms.dao;

import com.hrms.util.DBConnection;
import com.hrms.model.LeaveRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaveDAO {

    // Add leave request
    public void addLeaveRequest(LeaveRequest leaveRequest) {
        String query = "INSERT INTO leave_requests (employee_id, leave_type, start_date, end_date, days_requested, reason, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, leaveRequest.getEmployeeId());
            ps.setString(2, leaveRequest.getLeaveType());
            ps.setDate(3, leaveRequest.getStartDate());
            ps.setDate(4, leaveRequest.getEndDate());
            ps.setInt(5, leaveRequest.getDaysRequested());
            ps.setString(6, leaveRequest.getReason());
            ps.setString(7, leaveRequest.getStatus());

            ps.executeUpdate();
            System.out.println("✅ Leave request added for employee ID: " + leaveRequest.getEmployeeId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all leave requests
    public List<LeaveRequest> getAllLeaveRequests() {
        List<LeaveRequest> requests = new ArrayList<>();
        String query = "SELECT * FROM leave_requests ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                LeaveRequest request = new LeaveRequest(
                        rs.getInt("leave_id"),
                        rs.getInt("employee_id"),
                        rs.getString("leave_type"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getInt("days_requested"),
                        rs.getString("reason"),
                        rs.getString("status")
                );
                requests.add(request);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return requests;
    }

    // Get leave requests by employee ID
    public List<LeaveRequest> getLeaveRequestsByEmployee(int empId) {
        List<LeaveRequest> requests = new ArrayList<>();
        String query = "SELECT * FROM leave_requests WHERE employee_id = ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                requests.add(new LeaveRequest(
                        rs.getInt("leave_id"),
                        rs.getInt("employee_id"),
                        rs.getString("leave_type"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getInt("days_requested"),
                        rs.getString("reason"),
                        rs.getString("status")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return requests;
    }

    // Update leave request status
    public boolean updateLeaveRequestStatus(int leaveId, String status, int approvedBy) {
        String query = "UPDATE leave_requests SET status = ?, approved_by = ? WHERE leave_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, status);
            ps.setInt(2, approvedBy);
            ps.setInt(3, leaveId);
            
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get leave requests by status
    public List<LeaveRequest> getLeaveRequestsByStatus(String status) {
        List<LeaveRequest> requests = new ArrayList<>();
        String query = "SELECT * FROM leave_requests WHERE status = ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                requests.add(new LeaveRequest(
                        rs.getInt("leave_id"),
                        rs.getInt("employee_id"),
                        rs.getString("leave_type"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getInt("days_requested"),
                        rs.getString("reason"),
                        rs.getString("status")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return requests;
    }
}