package com.hrms.dao;

import com.hrms.model.Attendance;
import com.hrms.util.MockDBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAOMock {

    // Add attendance
    public void markAttendance(Attendance attendance) {
        String query = "INSERT INTO attendance (employee_id, date, status) VALUES (?, ?, ?)";
        try (Connection conn = MockDBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, attendance.getEmployeeId());
            ps.setDate(2, attendance.getDate());
            ps.setString(3, attendance.getStatus());

            ps.executeUpdate();
            System.out.println("✅ Attendance marked for employee ID: " + attendance.getEmployeeId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all attendance records
    public List<Attendance> getAllAttendance() {
        List<Attendance> records = new ArrayList<>();
        String query = "SELECT * FROM attendance";

        try (Connection conn = MockDBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                Attendance att = new Attendance(
                        rs.getInt("id"),
                        rs.getInt("employee_id"),
                        rs.getDate("date"),
                        rs.getString("status")
                );
                records.add(att);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return records;
    }

    // Get attendance by employee ID
    public List<Attendance> getAttendanceByEmployee(int empId) {
        List<Attendance> records = new ArrayList<>();
        String query = "SELECT * FROM attendance WHERE employee_id = ?";

        try (Connection conn = MockDBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                records.add(new Attendance(
                        rs.getInt("id"),
                        rs.getInt("employee_id"),
                        rs.getDate("date"),
                        rs.getString("status")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return records;
    }
}

