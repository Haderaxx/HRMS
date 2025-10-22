package com.hrms.gui;

import com.hrms.dao.EmployeeDAO;
import com.hrms.dao.AttendanceDAO;
import com.hrms.model.Employee;
import com.hrms.model.Attendance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class HRMSAppGUI extends JFrame {

    private EmployeeDAO empDao = new EmployeeDAO();
    private AttendanceDAO attDao = new AttendanceDAO();

    private JTable employeeTable;
    private DefaultTableModel employeeModel;

    private JTable attendanceTable;
    private DefaultTableModel attendanceModel;

    public HRMSAppGUI() {
        setTitle("HRMS App");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Employee Table
        employeeModel = new DefaultTableModel(new String[]{"ID", "Name", "Department", "Salary"}, 0);
        employeeTable = new JTable(employeeModel);
        JScrollPane empScroll = new JScrollPane(employeeTable);
        empScroll.setBorder(BorderFactory.createTitledBorder("Employees"));

        // Attendance Table
        attendanceModel = new DefaultTableModel(new String[]{"ID", "Employee ID", "Date", "Status"}, 0);
        attendanceTable = new JTable(attendanceModel);
        JScrollPane attScroll = new JScrollPane(attendanceTable);
        attScroll.setBorder(BorderFactory.createTitledBorder("Attendance"));

        // Split Pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, empScroll, attScroll);
        splitPane.setDividerLocation(250);
        add(splitPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addEmpBtn = new JButton("Add Employee");
        JButton viewEmpBtn = new JButton("View Employees");
        JButton markAttBtn = new JButton("Mark Attendance");
        JButton viewAttBtn = new JButton("View Attendance");
        buttonPanel.add(addEmpBtn);
        buttonPanel.add(viewEmpBtn);
        buttonPanel.add(markAttBtn);
        buttonPanel.add(viewAttBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // Actions
        addEmpBtn.addActionListener(e -> addEmployee());
        viewEmpBtn.addActionListener(e -> refreshEmployees());
        markAttBtn.addActionListener(e -> markAttendance());
        viewAttBtn.addActionListener(e -> refreshAttendance());

        // Initial Load
        refreshEmployees();
        refreshAttendance();
    }

    private void addEmployee() {
        JTextField nameField = new JTextField();
        JTextField deptField = new JTextField();
        JTextField salaryField = new JTextField();
        Object[] message = {
                "Name:", nameField,
                "Department:", deptField,
                "Salary:", salaryField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Employee", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                String dept = deptField.getText().trim();
                double salary = Double.parseDouble(salaryField.getText().trim());

                Employee emp = new Employee(0, name, dept, salary);
                empDao.addEmployee(emp);
                JOptionPane.showMessageDialog(this, "Employee added successfully!");
                refreshEmployees();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void refreshEmployees() {
        List<Employee> employees = empDao.getAllEmployees();
        employeeModel.setRowCount(0);
        for (Employee e : employees) {
            employeeModel.addRow(new Object[]{e.getId(), e.getName(), e.getDepartment(), e.getSalary()});
        }
    }

    private void markAttendance() {
        JTextField empIdField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField statusField = new JTextField();
        Object[] message = {
                "Employee ID:", empIdField,
                "Date (YYYY-MM-DD):", dateField,
                "Status (Present/Absent):", statusField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Mark Attendance", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int empId = Integer.parseInt(empIdField.getText().trim());
                Date date = Date.valueOf(dateField.getText().trim());
                String status = statusField.getText().trim();

                Attendance att = new Attendance(0, empId, date, status);
                attDao.markAttendance(att);
                JOptionPane.showMessageDialog(this, "Attendance marked successfully!");
                refreshAttendance();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    private void refreshAttendance() {
        List<Attendance> records = attDao.getAllAttendance();
        attendanceModel.setRowCount(0);
        for (Attendance a : records) {
            attendanceModel.addRow(new Object[]{a.getId(), a.getEmployeeId(), a.getDate(), a.getStatus()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HRMSAppGUI().setVisible(true));
    }
}
