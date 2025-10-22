import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.List;

import com.hrms.dao.EmployeeDAOMock;
import com.hrms.dao.AttendanceDAOMock;
import com.hrms.model.Employee;
import com.hrms.model.Attendance;

public class HRMSAppMock extends JFrame {

    private EmployeeDAOMock empDao = new EmployeeDAOMock();
    private AttendanceDAOMock attDao = new AttendanceDAOMock();

    private JTextArea outputArea;

    public HRMSAppMock() {
        setTitle("HRMS Frontend (Mock Database)");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Output Area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        JButton addEmpBtn = new JButton("Add Employee");
        JButton viewEmpBtn = new JButton("View Employees");
        JButton markAttBtn = new JButton("Mark Attendance");
        JButton viewAttBtn = new JButton("View Attendance");

        buttonPanel.add(addEmpBtn);
        buttonPanel.add(viewEmpBtn);
        buttonPanel.add(markAttBtn);
        buttonPanel.add(viewAttBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addEmpBtn.addActionListener(e -> addEmployee());
        viewEmpBtn.addActionListener(e -> viewEmployees());
        markAttBtn.addActionListener(e -> markAttendance());
        viewAttBtn.addActionListener(e -> viewAttendance());
        
        // Show initial message
        outputArea.append("🚀 HRMS Application Started (Mock Database Mode)\n");
        outputArea.append("📝 This version uses an in-memory database for testing\n\n");
    }

    private void addEmployee() {
        String name = JOptionPane.showInputDialog(this, "Enter Employee Name:");
        if (name == null || name.trim().isEmpty()) return;
        
        String dept = JOptionPane.showInputDialog(this, "Enter Department:");
        if (dept == null || dept.trim().isEmpty()) return;
        
        String salaryStr = JOptionPane.showInputDialog(this, "Enter Salary:");
        if (salaryStr == null || salaryStr.trim().isEmpty()) return;

        try {
            double salary = Double.parseDouble(salaryStr);
            empDao.addEmployee(new Employee(0, name, dept, salary));
            outputArea.append("✅ Employee added: " + name + "\n");
        } catch (Exception ex) {
            outputArea.append("❌ Error: " + ex.getMessage() + "\n");
        }
    }

    private void viewEmployees() {
        try {
            List<Employee> employees = empDao.getAllEmployees();
            outputArea.append("\n--- Employees ---\n");
            if (employees.isEmpty()) {
                outputArea.append("No employees found.\n");
            } else {
                for (Employee e : employees) {
                    outputArea.append(e.toString() + "\n");
                }
            }
        } catch (Exception ex) {
            outputArea.append("❌ Error: " + ex.getMessage() + "\n");
        }
    }

    private void markAttendance() {
        String empIdStr = JOptionPane.showInputDialog(this, "Enter Employee ID:");
        if (empIdStr == null || empIdStr.trim().isEmpty()) return;
        
        String dateStr = JOptionPane.showInputDialog(this, "Enter Date (YYYY-MM-DD):");
        if (dateStr == null || dateStr.trim().isEmpty()) return;
        
        String status = JOptionPane.showInputDialog(this, "Enter Status (Present/Absent):");
        if (status == null || status.trim().isEmpty()) return;

        try {
            int empId = Integer.parseInt(empIdStr);
            attDao.markAttendance(new Attendance(0, empId, Date.valueOf(dateStr), status));
            outputArea.append("✅ Attendance marked for Employee ID " + empId + "\n");
        } catch (Exception ex) {
            outputArea.append("❌ Error: " + ex.getMessage() + "\n");
        }
    }

    private void viewAttendance() {
        try {
            List<Attendance> records = attDao.getAllAttendance();
            outputArea.append("\n--- Attendance Records ---\n");
            if (records.isEmpty()) {
                outputArea.append("No attendance records found.\n");
            } else {
                for (Attendance a : records) {
                    outputArea.append(a.toString() + "\n");
                }
            }
        } catch (Exception ex) {
            outputArea.append("❌ Error: " + ex.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HRMSAppMock().setVisible(true);
        });
    }
}

