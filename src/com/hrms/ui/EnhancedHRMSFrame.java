package com.hrms.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.hrms.dao.EmployeeDAO;
import com.hrms.dao.AttendanceDAO;
import com.hrms.dao.LeaveDAO;
import com.hrms.dao.PerformanceDAO;
import com.hrms.model.Employee;
import com.hrms.model.Attendance;
import com.hrms.model.LeaveRequest;
import com.hrms.model.PerformanceReview;

public class EnhancedHRMSFrame extends JFrame {
    
    private EmployeeDAO empDao = new EmployeeDAO();
    private AttendanceDAO attDao = new AttendanceDAO();
    private LeaveDAO leaveDao = new LeaveDAO();
    private PerformanceDAO perfDao = new PerformanceDAO();
    
    private JTextArea outputArea;
    private JTabbedPane tabbedPane;
    
    public EnhancedHRMSFrame() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setFrameProperties();
        loadDashboardData();
    }
    
    private void initializeComponents() {
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        outputArea.setBackground(new Color(248, 249, 250));
        outputArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content area
        setupTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
        
        // Status bar
        JPanel statusPanel = createStatusPanel();
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Professional gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(70, 130, 180), // Steel blue
                    0, getHeight(), new Color(100, 149, 237) // Cornflower blue
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(0, 80));
        
        // Title
        JLabel titleLabel = new JLabel("HRMS - Human Resource Management System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // User info
        JLabel userLabel = new JLabel("Welcome, Admin");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(Color.WHITE);
        userLabel.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(userLabel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private void setupTabbedPane() {
        // Dashboard Tab
        JPanel dashboardPanel = createDashboardPanel();
        tabbedPane.addTab("📊 Dashboard", createIcon(Color.BLUE), dashboardPanel);
        
        // Employee Management Tab
        JPanel employeePanel = createEmployeePanel();
        tabbedPane.addTab("👥 Employees", createIcon(Color.GREEN), employeePanel);
        
        // Attendance Tab
        JPanel attendancePanel = createAttendancePanel();
        tabbedPane.addTab("⏰ Attendance", createIcon(Color.ORANGE), attendancePanel);
        
        // Leave Management Tab
        JPanel leavePanel = createLeavePanel();
        tabbedPane.addTab("🏖️ Leave Management", createIcon(Color.CYAN), leavePanel);
        
        // Performance Tab
        JPanel performancePanel = createPerformancePanel();
        tabbedPane.addTab("📈 Performance", createIcon(Color.MAGENTA), performancePanel);
        
        // Reports Tab
        JPanel reportsPanel = createReportsPanel();
        tabbedPane.addTab("📋 Reports", createIcon(Color.RED), reportsPanel);
    }
    
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Stats cards
        JPanel statsPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        statsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        statsPanel.setBackground(Color.WHITE);
        
        // Create stat cards with professional colors
        statsPanel.add(createStatCard("Total Employees", "25", new Color(70, 130, 180), "👥")); // Steel blue
        statsPanel.add(createStatCard("Present Today", "23", new Color(34, 139, 34), "✅")); // Forest green
        statsPanel.add(createStatCard("On Leave", "2", new Color(255, 140, 0), "🏖️")); // Dark orange
        statsPanel.add(createStatCard("Pending Reviews", "5", new Color(138, 43, 226), "📈")); // Blue violet
        statsPanel.add(createStatCard("Active Projects", "8", new Color(72, 209, 204), "🚀")); // Medium turquoise
        statsPanel.add(createStatCard("Departments", "6", new Color(220, 20, 60), "🏢")); // Crimson
        
        panel.add(statsPanel, BorderLayout.CENTER);
        
        // Recent activities
        JPanel activitiesPanel = new JPanel(new BorderLayout());
        activitiesPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        activitiesPanel.setBackground(Color.WHITE);
        
        JLabel activitiesLabel = new JLabel("Recent Activities");
        activitiesLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        activitiesLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        JTextArea activitiesArea = new JTextArea(10, 30);
        activitiesArea.setEditable(false);
        activitiesArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        activitiesArea.setBackground(new Color(248, 249, 250));
        activitiesArea.setText("• John Doe marked present today\n• Jane Smith applied for leave\n• Performance review completed for Alice Johnson\n• New employee Bob Wilson joined\n• Attendance report generated");
        
        activitiesPanel.add(activitiesLabel, BorderLayout.NORTH);
        activitiesPanel.add(new JScrollPane(activitiesArea), BorderLayout.CENTER);
        
        panel.add(activitiesPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createStatCard(String title, String value, Color color, String icon) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, color,
                    0, getHeight(), new Color(color.getRed(), color.getGreen(), color.getBlue(), 100)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                g2d.setColor(new Color(255, 255, 255, 200));
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
            }
        };
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(150, 100));
        card.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        card.add(iconLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        card.add(titleLabel, BorderLayout.SOUTH);
        
        return card;
    }
    
    private JPanel createEmployeePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Header with buttons
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);
        
        JButton addEmpBtn = createStyledButton("Add Employee", new Color(34, 139, 34), "➕"); // Forest green
        JButton viewEmpBtn = createStyledButton("View Employees", new Color(70, 130, 180), "👁️"); // Steel blue
        JButton editEmpBtn = createStyledButton("Edit Employee", new Color(255, 140, 0), "✏️"); // Dark orange
        JButton deleteEmpBtn = createStyledButton("Delete Employee", new Color(220, 20, 60), "🗑️"); // Crimson
        
        headerPanel.add(addEmpBtn);
        headerPanel.add(viewEmpBtn);
        headerPanel.add(editEmpBtn);
        headerPanel.add(deleteEmpBtn);
        
        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        outputArea.setBackground(new Color(248, 249, 250));
        outputArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        
        // Event handlers
        addEmpBtn.addActionListener(e -> addEmployee());
        viewEmpBtn.addActionListener(e -> viewEmployees());
        editEmpBtn.addActionListener(e -> editEmployee());
        deleteEmpBtn.addActionListener(e -> deleteEmployee());
        
        return panel;
    }
    
    private JPanel createAttendancePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);
        
        JButton markAttBtn = createStyledButton("Mark Attendance", new Color(34, 139, 34), "✅"); // Forest green
        JButton viewAttBtn = createStyledButton("View Attendance", new Color(70, 130, 180), "👁️"); // Steel blue
        JButton reportBtn = createStyledButton("Attendance Report", new Color(138, 43, 226), "📊"); // Blue violet
        
        headerPanel.add(markAttBtn);
        headerPanel.add(viewAttBtn);
        headerPanel.add(reportBtn);
        
        JTextArea attArea = new JTextArea();
        attArea.setEditable(false);
        attArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        attArea.setBackground(new Color(248, 249, 250));
        attArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(attArea), BorderLayout.CENTER);
        
        markAttBtn.addActionListener(e -> markAttendance());
        viewAttBtn.addActionListener(e -> viewAttendance());
        reportBtn.addActionListener(e -> generateAttendanceReport());
        
        return panel;
    }
    
    private JPanel createLeavePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);
        
        JButton applyLeaveBtn = createStyledButton("Apply Leave", new Color(34, 139, 34), "📝"); // Forest green
        JButton viewLeaveBtn = createStyledButton("View Leave Requests", new Color(70, 130, 180), "👁️"); // Steel blue
        JButton approveLeaveBtn = createStyledButton("Approve/Reject", new Color(255, 140, 0), "✅"); // Dark orange
        
        headerPanel.add(applyLeaveBtn);
        headerPanel.add(viewLeaveBtn);
        headerPanel.add(approveLeaveBtn);
        
        JTextArea leaveArea = new JTextArea();
        leaveArea.setEditable(false);
        leaveArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        leaveArea.setBackground(new Color(248, 249, 250));
        leaveArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(leaveArea), BorderLayout.CENTER);
        
        applyLeaveBtn.addActionListener(e -> applyLeave());
        viewLeaveBtn.addActionListener(e -> viewLeaveRequests());
        approveLeaveBtn.addActionListener(e -> approveLeaveRequests());
        
        return panel;
    }
    
    private JPanel createPerformancePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);
        
        JButton addReviewBtn = createStyledButton("Add Performance Review", new Color(34, 139, 34), "📝"); // Forest green
        JButton viewReviewBtn = createStyledButton("View Reviews", new Color(70, 130, 180), "👁️"); // Steel blue
        JButton reportBtn = createStyledButton("Performance Report", new Color(138, 43, 226), "📊"); // Blue violet
        
        headerPanel.add(addReviewBtn);
        headerPanel.add(viewReviewBtn);
        headerPanel.add(reportBtn);
        
        JTextArea perfArea = new JTextArea();
        perfArea.setEditable(false);
        perfArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        perfArea.setBackground(new Color(248, 249, 250));
        perfArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(perfArea), BorderLayout.CENTER);
        
        addReviewBtn.addActionListener(e -> addPerformanceReview());
        viewReviewBtn.addActionListener(e -> viewPerformanceReviews());
        reportBtn.addActionListener(e -> generatePerformanceReport());
        
        return panel;
    }
    
    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);
        
        JButton empReportBtn = createStyledButton("Employee Report", new Color(70, 130, 180), "👥"); // Steel blue
        JButton attReportBtn = createStyledButton("Attendance Report", new Color(34, 139, 34), "⏰"); // Forest green
        JButton leaveReportBtn = createStyledButton("Leave Report", new Color(255, 140, 0), "🏖️"); // Dark orange
        JButton perfReportBtn = createStyledButton("Performance Report", new Color(138, 43, 226), "📈"); // Blue violet
        
        headerPanel.add(empReportBtn);
        headerPanel.add(attReportBtn);
        headerPanel.add(leaveReportBtn);
        headerPanel.add(perfReportBtn);
        
        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        reportArea.setBackground(new Color(248, 249, 250));
        reportArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(reportArea), BorderLayout.CENTER);
        
        empReportBtn.addActionListener(e -> generateEmployeeReport());
        attReportBtn.addActionListener(e -> generateAttendanceReport());
        leaveReportBtn.addActionListener(e -> generateLeaveReport());
        perfReportBtn.addActionListener(e -> generatePerformanceReport());
        
        return panel;
    }
    
    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(new Color(44, 62, 80)); // Professional dark blue-gray
        statusPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        JLabel statusLabel = new JLabel("Ready");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JLabel timeLabel = new JLabel();
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Update time every second
        Timer timer = new Timer(1000, e -> {
            timeLabel.setText(new java.util.Date().toString());
        });
        timer.start();
        
        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusPanel.add(timeLabel, BorderLayout.EAST);
        
        return statusPanel;
    }
    
    private JButton createStyledButton(String text, Color color, String icon) {
        JButton button = new JButton(icon + " " + text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 35));
        button.setBorder(new EmptyBorder(8, 15, 8, 15));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private Icon createIcon(Color color) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(color);
                g2d.fillOval(x, y, getIconWidth(), getIconHeight());
            }
            
            @Override
            public int getIconWidth() { return 12; }
            
            @Override
            public int getIconHeight() { return 12; }
        };
    }
    
    private void setupEventHandlers() {
        // Add any global event handlers here
    }
    
    private void setFrameProperties() {
        setTitle("HRMS - Human Resource Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Set look and feel - using default for compatibility
    }
    
    private void loadDashboardData() {
        // Load initial dashboard data
        // This would typically load real data from the database
    }
    
    // Employee Management Methods
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
    
    private void editEmployee() {
        // First, show all employees to select from
        List<Employee> employees = empDao.getAllEmployees();
        if (employees.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No employees found to edit.", "No Data", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Create employee selection dialog
        String[] employeeOptions = new String[employees.size()];
        for (int i = 0; i < employees.size(); i++) {
            employeeOptions[i] = employees.get(i).getId() + " - " + employees.get(i).getName();
        }
        
        String selectedEmployee = (String) JOptionPane.showInputDialog(
            this,
            "Select employee to edit:",
            "Edit Employee",
            JOptionPane.QUESTION_MESSAGE,
            null,
            employeeOptions,
            employeeOptions[0]
        );
        
        if (selectedEmployee == null) return;
        
        // Extract employee ID
        int empId = Integer.parseInt(selectedEmployee.split(" - ")[0]);
        Employee empToEdit = empDao.getEmployeeById(empId);
        
        if (empToEdit == null) {
            JOptionPane.showMessageDialog(this, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create edit dialog
        JPanel editPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        editPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JTextField nameField = new JTextField(empToEdit.getName());
        JTextField deptField = new JTextField(empToEdit.getDepartment());
        JTextField salaryField = new JTextField(String.valueOf(empToEdit.getSalary()));
        
        editPanel.add(new JLabel("Name:"));
        editPanel.add(nameField);
        editPanel.add(new JLabel("Department:"));
        editPanel.add(deptField);
        editPanel.add(new JLabel("Salary:"));
        editPanel.add(salaryField);
        editPanel.add(new JLabel(""));
        editPanel.add(new JLabel(""));
        
        int result = JOptionPane.showConfirmDialog(
            this,
            editPanel,
            "Edit Employee",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                String newName = nameField.getText().trim();
                String newDept = deptField.getText().trim();
                double newSalary = Double.parseDouble(salaryField.getText().trim());
                
                if (newName.isEmpty() || newDept.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Name and Department cannot be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Update employee
                empToEdit.setName(newName);
                empToEdit.setDepartment(newDept);
                empToEdit.setSalary(newSalary);
                
                // Note: You would need to implement updateEmployee method in DAO
                outputArea.append("✅ Employee updated: " + newName + " (ID: " + empToEdit.getId() + ")\n");
                JOptionPane.showMessageDialog(this, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid salary format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteEmployee() {
        // First, show all employees to select from
        List<Employee> employees = empDao.getAllEmployees();
        if (employees.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No employees found to delete.", "No Data", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Create employee selection dialog
        String[] employeeOptions = new String[employees.size()];
        for (int i = 0; i < employees.size(); i++) {
            employeeOptions[i] = employees.get(i).getId() + " - " + employees.get(i).getName();
        }
        
        String selectedEmployee = (String) JOptionPane.showInputDialog(
            this,
            "Select employee to delete:",
            "Delete Employee",
            JOptionPane.QUESTION_MESSAGE,
            null,
            employeeOptions,
            employeeOptions[0]
        );
        
        if (selectedEmployee == null) return;
        
        // Extract employee ID
        int empId = Integer.parseInt(selectedEmployee.split(" - ")[0]);
        Employee empToDelete = empDao.getEmployeeById(empId);
        
        if (empToDelete == null) {
            JOptionPane.showMessageDialog(this, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Confirmation dialog
        int confirmResult = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete employee:\n" + empToDelete.getName() + " (ID: " + empToDelete.getId() + ")?\n\nThis action cannot be undone!",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirmResult == JOptionPane.YES_OPTION) {
            try {
                boolean deleted = empDao.deleteEmployee(empId);
                if (deleted) {
                    outputArea.append("✅ Employee deleted: " + empToDelete.getName() + " (ID: " + empId + ")\n");
                    JOptionPane.showMessageDialog(this, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete employee!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting employee: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Attendance Methods
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
    
    private void generateAttendanceReport() {
        try {
            List<Attendance> records = attDao.getAllAttendance();
            if (records.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No attendance records found.", "No Data", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Create report dialog
            JDialog reportDialog = new JDialog(this, "Attendance Report", true);
            reportDialog.setSize(800, 600);
            reportDialog.setLocationRelativeTo(this);
            
            JTextArea reportArea = new JTextArea();
            reportArea.setEditable(false);
            reportArea.setFont(new Font("Consolas", Font.PLAIN, 12));
            reportArea.setBackground(new Color(248, 249, 250));
            reportArea.setBorder(new EmptyBorder(10, 10, 10, 10));
            
            // Generate report content
            StringBuilder report = new StringBuilder();
            report.append("ATTENDANCE REPORT\n");
            report.append("================\n");
            report.append("Generated on: ").append(new java.util.Date()).append("\n\n");
            
            // Summary statistics
            int totalRecords = records.size();
            long presentCount = records.stream().filter(a -> "Present".equals(a.getStatus())).count();
            long absentCount = records.stream().filter(a -> "Absent".equals(a.getStatus())).count();
            
            report.append("SUMMARY:\n");
            report.append("--------\n");
            report.append("Total Records: ").append(totalRecords).append("\n");
            report.append("Present: ").append(presentCount).append("\n");
            report.append("Absent: ").append(absentCount).append("\n");
            report.append("Attendance Rate: ").append(String.format("%.1f%%", (presentCount * 100.0 / totalRecords))).append("\n\n");
            
            // Detailed records
            report.append("DETAILED RECORDS:\n");
            report.append("-----------------\n");
            report.append(String.format("%-5s %-15s %-12s %-10s\n", "ID", "Employee ID", "Date", "Status"));
            report.append("------------------------------------------------\n");
            
            for (Attendance att : records) {
                report.append(String.format("%-5d %-15d %-12s %-10s\n", 
                    att.getId(), att.getEmployeeId(), att.getDate(), att.getStatus()));
            }
            
            reportArea.setText(report.toString());
            
            JScrollPane scrollPane = new JScrollPane(reportArea);
            JPanel buttonPanel = new JPanel(new FlowLayout());
            
            JButton closeBtn = new JButton("Close");
            JButton exportBtn = new JButton("Export to File");
            
            closeBtn.addActionListener(e -> reportDialog.dispose());
            exportBtn.addActionListener(e -> exportReportToFile(report.toString(), "attendance_report.txt"));
            
            buttonPanel.add(closeBtn);
            buttonPanel.add(exportBtn);
            
            reportDialog.add(scrollPane, BorderLayout.CENTER);
            reportDialog.add(buttonPanel, BorderLayout.SOUTH);
            reportDialog.setVisible(true);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error generating report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void exportReportToFile(String content, String filename) {
        try {
            java.io.FileWriter writer = new java.io.FileWriter(filename);
            writer.write(content);
            writer.close();
            JOptionPane.showMessageDialog(this, "Report exported to: " + filename, "Export Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error exporting report: " + e.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Leave Management Methods
    private void applyLeave() {
        // Get employee list for selection
        List<Employee> employees = empDao.getAllEmployees();
        if (employees.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No employees found.", "No Data", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Create leave application dialog
        JDialog leaveDialog = new JDialog(this, "Apply Leave", true);
        leaveDialog.setSize(500, 400);
        leaveDialog.setLocationRelativeTo(this);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Employee selection
        JLabel empLabel = new JLabel("Employee:");
        String[] empOptions = new String[employees.size()];
        for (int i = 0; i < employees.size(); i++) {
            empOptions[i] = employees.get(i).getId() + " - " + employees.get(i).getName();
        }
        JComboBox<String> empCombo = new JComboBox<>(empOptions);
        
        // Leave type
        JLabel typeLabel = new JLabel("Leave Type:");
        String[] leaveTypes = {"Annual Leave", "Sick Leave", "Personal Leave", "Emergency Leave"};
        JComboBox<String> typeCombo = new JComboBox<>(leaveTypes);
        
        // Start date
        JLabel startLabel = new JLabel("Start Date:");
        JTextField startField = new JTextField(15);
        startField.setText("YYYY-MM-DD");
        
        // End date
        JLabel endLabel = new JLabel("End Date:");
        JTextField endField = new JTextField(15);
        endField.setText("YYYY-MM-DD");
        
        // Reason
        JLabel reasonLabel = new JLabel("Reason:");
        JTextArea reasonArea = new JTextArea(3, 20);
        reasonArea.setLineWrap(true);
        reasonArea.setWrapStyleWord(true);
        
        // Add components to dialog
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(empLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(empCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(typeLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(typeCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(startLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(startField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(endLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(endField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(reasonLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(new JScrollPane(reasonArea), gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton applyBtn = new JButton("Apply Leave");
        JButton cancelBtn = new JButton("Cancel");
        
        applyBtn.addActionListener(e -> {
            try {
                String selectedEmp = (String) empCombo.getSelectedItem();
                int empId = Integer.parseInt(selectedEmp.split(" - ")[0]);
                String leaveType = (String) typeCombo.getSelectedItem();
                Date startDate = Date.valueOf(startField.getText());
                Date endDate = Date.valueOf(endField.getText());
                String reason = reasonArea.getText().trim();
                
                // Calculate days
                long daysDiff = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24) + 1;
                
                LeaveRequest leaveRequest = new LeaveRequest(0, empId, leaveType, startDate, endDate, (int) daysDiff, reason, "Pending");
                leaveDao.addLeaveRequest(leaveRequest);
                
                JOptionPane.showMessageDialog(leaveDialog, "Leave request submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                leaveDialog.dispose();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(leaveDialog, "Error submitting leave request: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelBtn.addActionListener(e -> leaveDialog.dispose());
        
        buttonPanel.add(applyBtn);
        buttonPanel.add(cancelBtn);
        
        leaveDialog.add(mainPanel, BorderLayout.CENTER);
        leaveDialog.add(buttonPanel, BorderLayout.SOUTH);
        leaveDialog.setVisible(true);
    }
    
    private void viewLeaveRequests() {
        try {
            List<LeaveRequest> requests = leaveDao.getAllLeaveRequests();
            if (requests.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No leave requests found.", "No Data", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Create leave requests dialog
            JDialog requestsDialog = new JDialog(this, "Leave Requests", true);
            requestsDialog.setSize(900, 500);
            requestsDialog.setLocationRelativeTo(this);
            
            JTextArea requestsArea = new JTextArea();
            requestsArea.setEditable(false);
            requestsArea.setFont(new Font("Consolas", Font.PLAIN, 12));
            requestsArea.setBackground(new Color(248, 249, 250));
            requestsArea.setBorder(new EmptyBorder(10, 10, 10, 10));
            
            StringBuilder content = new StringBuilder();
            content.append("LEAVE REQUESTS\n");
            content.append("==============\n\n");
            content.append(String.format("%-5s %-10s %-15s %-12s %-12s %-8s %-10s %-20s\n", 
                "ID", "Emp ID", "Leave Type", "Start Date", "End Date", "Days", "Status", "Reason"));
            content.append("--------------------------------------------------------------------------------------------------------\n");
            
            for (LeaveRequest req : requests) {
                content.append(String.format("%-5d %-10d %-15s %-12s %-12s %-8d %-10s %-20s\n",
                    req.getLeaveId(), req.getEmployeeId(), req.getLeaveType(), 
                    req.getStartDate(), req.getEndDate(), req.getDaysRequested(), 
                    req.getStatus(), req.getReason()));
            }
            
            requestsArea.setText(content.toString());
            
            JScrollPane scrollPane = new JScrollPane(requestsArea);
            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton closeBtn = new JButton("Close");
            closeBtn.addActionListener(e -> requestsDialog.dispose());
            buttonPanel.add(closeBtn);
            
            requestsDialog.add(scrollPane, BorderLayout.CENTER);
            requestsDialog.add(buttonPanel, BorderLayout.SOUTH);
            requestsDialog.setVisible(true);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading leave requests: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void approveLeaveRequests() {
        try {
            List<LeaveRequest> pendingRequests = leaveDao.getLeaveRequestsByStatus("Pending");
            if (pendingRequests.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No pending leave requests found.", "No Data", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Create approval dialog
            JDialog approvalDialog = new JDialog(this, "Approve Leave Requests", true);
            approvalDialog.setSize(800, 500);
            approvalDialog.setLocationRelativeTo(this);
            
            JTextArea requestsArea = new JTextArea();
            requestsArea.setEditable(false);
            requestsArea.setFont(new Font("Consolas", Font.PLAIN, 12));
            requestsArea.setBackground(new Color(248, 249, 250));
            requestsArea.setBorder(new EmptyBorder(10, 10, 10, 10));
            
            StringBuilder content = new StringBuilder();
            content.append("PENDING LEAVE REQUESTS\n");
            content.append("======================\n\n");
            content.append(String.format("%-5s %-10s %-15s %-12s %-12s %-8s %-20s\n", 
                "ID", "Emp ID", "Leave Type", "Start Date", "End Date", "Days", "Reason"));
            content.append("----------------------------------------------------------------------------------------\n");
            
            for (LeaveRequest req : pendingRequests) {
                content.append(String.format("%-5d %-10d %-15s %-12s %-12s %-8d %-20s\n",
                    req.getLeaveId(), req.getEmployeeId(), req.getLeaveType(), 
                    req.getStartDate(), req.getEndDate(), req.getDaysRequested(), req.getReason()));
            }
            
            requestsArea.setText(content.toString());
            
            JScrollPane scrollPane = new JScrollPane(requestsArea);
            
            // Approval controls
            JPanel controlPanel = new JPanel(new FlowLayout());
            JLabel idLabel = new JLabel("Request ID:");
            JTextField idField = new JTextField(10);
            JButton approveBtn = new JButton("Approve");
            JButton rejectBtn = new JButton("Reject");
            
            approveBtn.addActionListener(e -> {
                try {
                    int requestId = Integer.parseInt(idField.getText());
                    boolean updated = leaveDao.updateLeaveRequestStatus(requestId, "Approved", 1); // Assuming admin ID is 1
                    if (updated) {
                        JOptionPane.showMessageDialog(approvalDialog, "Leave request approved!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        approvalDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(approvalDialog, "Failed to approve request!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(approvalDialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            
            rejectBtn.addActionListener(e -> {
                try {
                    int requestId = Integer.parseInt(idField.getText());
                    boolean updated = leaveDao.updateLeaveRequestStatus(requestId, "Rejected", 1); // Assuming admin ID is 1
                    if (updated) {
                        JOptionPane.showMessageDialog(approvalDialog, "Leave request rejected!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        approvalDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(approvalDialog, "Failed to reject request!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(approvalDialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            
            controlPanel.add(idLabel);
            controlPanel.add(idField);
            controlPanel.add(approveBtn);
            controlPanel.add(rejectBtn);
            
            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton closeBtn = new JButton("Close");
            closeBtn.addActionListener(e -> approvalDialog.dispose());
            buttonPanel.add(closeBtn);
            
            approvalDialog.add(scrollPane, BorderLayout.CENTER);
            approvalDialog.add(controlPanel, BorderLayout.NORTH);
            approvalDialog.add(buttonPanel, BorderLayout.SOUTH);
            approvalDialog.setVisible(true);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading leave requests: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Performance Management Methods
    private void addPerformanceReview() {
        // Get employee list for selection
        List<Employee> employees = empDao.getAllEmployees();
        if (employees.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No employees found.", "No Data", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Create performance review dialog
        JDialog reviewDialog = new JDialog(this, "Add Performance Review", true);
        reviewDialog.setSize(600, 500);
        reviewDialog.setLocationRelativeTo(this);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Employee selection
        JLabel empLabel = new JLabel("Employee:");
        String[] empOptions = new String[employees.size()];
        for (int i = 0; i < employees.size(); i++) {
            empOptions[i] = employees.get(i).getId() + " - " + employees.get(i).getName();
        }
        JComboBox<String> empCombo = new JComboBox<>(empOptions);
        
        // Reviewer selection
        JLabel reviewerLabel = new JLabel("Reviewer:");
        JComboBox<String> reviewerCombo = new JComboBox<>(empOptions);
        
        // Review period
        JLabel startLabel = new JLabel("Review Period Start:");
        JTextField startField = new JTextField(15);
        startField.setText("YYYY-MM-DD");
        
        JLabel endLabel = new JLabel("Review Period End:");
        JTextField endField = new JTextField(15);
        endField.setText("YYYY-MM-DD");
        
        // Overall rating
        JLabel ratingLabel = new JLabel("Overall Rating (1-5):");
        JSpinner ratingSpinner = new JSpinner(new SpinnerNumberModel(3.0, 1.0, 5.0, 0.1));
        
        // Goals achieved
        JLabel goalsLabel = new JLabel("Goals Achieved:");
        JTextArea goalsArea = new JTextArea(3, 30);
        goalsArea.setLineWrap(true);
        goalsArea.setWrapStyleWord(true);
        
        // Areas for improvement
        JLabel improvementLabel = new JLabel("Areas for Improvement:");
        JTextArea improvementArea = new JTextArea(3, 30);
        improvementArea.setLineWrap(true);
        improvementArea.setWrapStyleWord(true);
        
        // Comments
        JLabel commentsLabel = new JLabel("Comments:");
        JTextArea commentsArea = new JTextArea(3, 30);
        commentsArea.setLineWrap(true);
        commentsArea.setWrapStyleWord(true);
        
        // Add components to dialog
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(empLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(empCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(reviewerLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(reviewerCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(startLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(startField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(endLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(endField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(ratingLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(ratingSpinner, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(goalsLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(new JScrollPane(goalsArea), gbc);
        
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(improvementLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(new JScrollPane(improvementArea), gbc);
        
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(commentsLabel, gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(new JScrollPane(commentsArea), gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add Review");
        JButton cancelBtn = new JButton("Cancel");
        
        addBtn.addActionListener(e -> {
            try {
                String selectedEmp = (String) empCombo.getSelectedItem();
                int empId = Integer.parseInt(selectedEmp.split(" - ")[0]);
                String selectedReviewer = (String) reviewerCombo.getSelectedItem();
                int reviewerId = Integer.parseInt(selectedReviewer.split(" - ")[0]);
                Date startDate = Date.valueOf(startField.getText());
                Date endDate = Date.valueOf(endField.getText());
                double rating = (Double) ratingSpinner.getValue();
                String goals = goalsArea.getText().trim();
                String improvement = improvementArea.getText().trim();
                String comments = commentsArea.getText().trim();
                
                PerformanceReview review = new PerformanceReview(0, empId, reviewerId, startDate, endDate, rating, goals, improvement, comments);
                perfDao.addPerformanceReview(review);
                
                JOptionPane.showMessageDialog(reviewDialog, "Performance review added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                reviewDialog.dispose();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(reviewDialog, "Error adding performance review: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelBtn.addActionListener(e -> reviewDialog.dispose());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(cancelBtn);
        
        reviewDialog.add(mainPanel, BorderLayout.CENTER);
        reviewDialog.add(buttonPanel, BorderLayout.SOUTH);
        reviewDialog.setVisible(true);
    }
    
    private void viewPerformanceReviews() {
        try {
            List<PerformanceReview> reviews = perfDao.getAllPerformanceReviews();
            if (reviews.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No performance reviews found.", "No Data", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Create performance reviews dialog
            JDialog reviewsDialog = new JDialog(this, "Performance Reviews", true);
            reviewsDialog.setSize(1000, 600);
            reviewsDialog.setLocationRelativeTo(this);
            
            JTextArea reviewsArea = new JTextArea();
            reviewsArea.setEditable(false);
            reviewsArea.setFont(new Font("Consolas", Font.PLAIN, 12));
            reviewsArea.setBackground(new Color(248, 249, 250));
            reviewsArea.setBorder(new EmptyBorder(10, 10, 10, 10));
            
            StringBuilder content = new StringBuilder();
            content.append("PERFORMANCE REVIEWS\n");
            content.append("===================\n\n");
            content.append(String.format("%-5s %-10s %-10s %-12s %-12s %-8s %-30s\n", 
                "ID", "Emp ID", "Reviewer", "Start Date", "End Date", "Rating", "Goals Achieved"));
            content.append("--------------------------------------------------------------------------------------------------------\n");
            
            for (PerformanceReview review : reviews) {
                String goals = review.getGoalsAchieved();
                if (goals.length() > 30) goals = goals.substring(0, 27) + "...";
                content.append(String.format("%-5d %-10d %-10d %-12s %-12s %-8.1f %-30s\n",
                    review.getReviewId(), review.getEmployeeId(), review.getReviewerId(), 
                    review.getReviewPeriodStart(), review.getReviewPeriodEnd(), 
                    review.getOverallRating(), goals));
            }
            
            reviewsArea.setText(content.toString());
            
            JScrollPane scrollPane = new JScrollPane(reviewsArea);
            JPanel buttonPanel = new JPanel(new FlowLayout());
            JButton closeBtn = new JButton("Close");
            closeBtn.addActionListener(e -> reviewsDialog.dispose());
            buttonPanel.add(closeBtn);
            
            reviewsDialog.add(scrollPane, BorderLayout.CENTER);
            reviewsDialog.add(buttonPanel, BorderLayout.SOUTH);
            reviewsDialog.setVisible(true);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading performance reviews: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void generatePerformanceReport() {
        try {
            List<PerformanceReview> reviews = perfDao.getAllPerformanceReviews();
            if (reviews.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No performance reviews found.", "No Data", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Create report dialog
            JDialog reportDialog = new JDialog(this, "Performance Report", true);
            reportDialog.setSize(800, 600);
            reportDialog.setLocationRelativeTo(this);
            
            JTextArea reportArea = new JTextArea();
            reportArea.setEditable(false);
            reportArea.setFont(new Font("Consolas", Font.PLAIN, 12));
            reportArea.setBackground(new Color(248, 249, 250));
            reportArea.setBorder(new EmptyBorder(10, 10, 10, 10));
            
            // Generate report content
            StringBuilder report = new StringBuilder();
            report.append("PERFORMANCE REPORT\n");
            report.append("==================\n");
            report.append("Generated on: ").append(new java.util.Date()).append("\n\n");
            
            // Summary statistics
            int totalReviews = reviews.size();
            double avgRating = reviews.stream().mapToDouble(PerformanceReview::getOverallRating).average().orElse(0.0);
            double maxRating = reviews.stream().mapToDouble(PerformanceReview::getOverallRating).max().orElse(0.0);
            double minRating = reviews.stream().mapToDouble(PerformanceReview::getOverallRating).min().orElse(0.0);
            
            report.append("SUMMARY:\n");
            report.append("--------\n");
            report.append("Total Reviews: ").append(totalReviews).append("\n");
            report.append("Average Rating: ").append(String.format("%.2f", avgRating)).append("\n");
            report.append("Highest Rating: ").append(String.format("%.2f", maxRating)).append("\n");
            report.append("Lowest Rating: ").append(String.format("%.2f", minRating)).append("\n\n");
            
            // Detailed reviews
            report.append("DETAILED REVIEWS:\n");
            report.append("-----------------\n");
            report.append(String.format("%-5s %-10s %-10s %-12s %-12s %-8s %-20s\n", 
                "ID", "Emp ID", "Reviewer", "Start Date", "End Date", "Rating", "Goals Achieved"));
            report.append("--------------------------------------------------------------------------------------------------------\n");
            
            for (PerformanceReview review : reviews) {
                String goals = review.getGoalsAchieved();
                if (goals.length() > 20) goals = goals.substring(0, 17) + "...";
                report.append(String.format("%-5d %-10d %-10d %-12s %-12s %-8.1f %-20s\n",
                    review.getReviewId(), review.getEmployeeId(), review.getReviewerId(), 
                    review.getReviewPeriodStart(), review.getReviewPeriodEnd(), 
                    review.getOverallRating(), goals));
            }
            
            reportArea.setText(report.toString());
            
            JScrollPane scrollPane = new JScrollPane(reportArea);
            JPanel buttonPanel = new JPanel(new FlowLayout());
            
            JButton closeBtn = new JButton("Close");
            JButton exportBtn = new JButton("Export to File");
            
            closeBtn.addActionListener(e -> reportDialog.dispose());
            exportBtn.addActionListener(e -> exportReportToFile(report.toString(), "performance_report.txt"));
            
            buttonPanel.add(closeBtn);
            buttonPanel.add(exportBtn);
            
            reportDialog.add(scrollPane, BorderLayout.CENTER);
            reportDialog.add(buttonPanel, BorderLayout.SOUTH);
            reportDialog.setVisible(true);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error generating report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Report Methods
    private void generateEmployeeReport() {
        try {
            List<Employee> employees = empDao.getAllEmployees();
            if (employees.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No employees found.", "No Data", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Create report dialog
            JDialog reportDialog = new JDialog(this, "Employee Report", true);
            reportDialog.setSize(800, 600);
            reportDialog.setLocationRelativeTo(this);
            
            JTextArea reportArea = new JTextArea();
            reportArea.setEditable(false);
            reportArea.setFont(new Font("Consolas", Font.PLAIN, 12));
            reportArea.setBackground(new Color(248, 249, 250));
            reportArea.setBorder(new EmptyBorder(10, 10, 10, 10));
            
            // Generate report content
            StringBuilder report = new StringBuilder();
            report.append("EMPLOYEE REPORT\n");
            report.append("===============\n");
            report.append("Generated on: ").append(new java.util.Date()).append("\n\n");
            
            // Summary statistics
            int totalEmployees = employees.size();
            double totalSalary = employees.stream().mapToDouble(Employee::getSalary).sum();
            double avgSalary = employees.stream().mapToDouble(Employee::getSalary).average().orElse(0.0);
            double maxSalary = employees.stream().mapToDouble(Employee::getSalary).max().orElse(0.0);
            double minSalary = employees.stream().mapToDouble(Employee::getSalary).min().orElse(0.0);
            
            // Department statistics
            Map<String, Long> deptCount = employees.stream()
                .collect(java.util.stream.Collectors.groupingBy(Employee::getDepartment, java.util.stream.Collectors.counting()));
            
            report.append("SUMMARY:\n");
            report.append("--------\n");
            report.append("Total Employees: ").append(totalEmployees).append("\n");
            report.append("Total Salary Budget: $").append(String.format("%.2f", totalSalary)).append("\n");
            report.append("Average Salary: $").append(String.format("%.2f", avgSalary)).append("\n");
            report.append("Highest Salary: $").append(String.format("%.2f", maxSalary)).append("\n");
            report.append("Lowest Salary: $").append(String.format("%.2f", minSalary)).append("\n\n");
            
            report.append("DEPARTMENT BREAKDOWN:\n");
            report.append("--------------------\n");
            for (Map.Entry<String, Long> entry : deptCount.entrySet()) {
                report.append(entry.getKey()).append(": ").append(entry.getValue()).append(" employees\n");
            }
            report.append("\n");
            
            // Detailed employee list
            report.append("DETAILED EMPLOYEE LIST:\n");
            report.append("----------------------\n");
            report.append(String.format("%-5s %-20s %-15s %-12s\n", "ID", "Name", "Department", "Salary"));
            report.append("--------------------------------------------------------\n");
            
            for (Employee emp : employees) {
                report.append(String.format("%-5d %-20s %-15s $%-12.2f\n", 
                    emp.getId(), emp.getName(), emp.getDepartment(), emp.getSalary()));
            }
            
            reportArea.setText(report.toString());
            
            JScrollPane scrollPane = new JScrollPane(reportArea);
            JPanel buttonPanel = new JPanel(new FlowLayout());
            
            JButton closeBtn = new JButton("Close");
            JButton exportBtn = new JButton("Export to File");
            
            closeBtn.addActionListener(e -> reportDialog.dispose());
            exportBtn.addActionListener(e -> exportReportToFile(report.toString(), "employee_report.txt"));
            
            buttonPanel.add(closeBtn);
            buttonPanel.add(exportBtn);
            
            reportDialog.add(scrollPane, BorderLayout.CENTER);
            reportDialog.add(buttonPanel, BorderLayout.SOUTH);
            reportDialog.setVisible(true);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error generating report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void generateLeaveReport() {
        try {
            List<LeaveRequest> requests = leaveDao.getAllLeaveRequests();
            if (requests.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No leave requests found.", "No Data", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Create report dialog
            JDialog reportDialog = new JDialog(this, "Leave Report", true);
            reportDialog.setSize(800, 600);
            reportDialog.setLocationRelativeTo(this);
            
            JTextArea reportArea = new JTextArea();
            reportArea.setEditable(false);
            reportArea.setFont(new Font("Consolas", Font.PLAIN, 12));
            reportArea.setBackground(new Color(248, 249, 250));
            reportArea.setBorder(new EmptyBorder(10, 10, 10, 10));
            
            // Generate report content
            StringBuilder report = new StringBuilder();
            report.append("LEAVE REPORT\n");
            report.append("============\n");
            report.append("Generated on: ").append(new java.util.Date()).append("\n\n");
            
            // Summary statistics
            int totalRequests = requests.size();
            long pendingCount = requests.stream().filter(r -> "Pending".equals(r.getStatus())).count();
            long approvedCount = requests.stream().filter(r -> "Approved".equals(r.getStatus())).count();
            long rejectedCount = requests.stream().filter(r -> "Rejected".equals(r.getStatus())).count();
            int totalDays = requests.stream().mapToInt(LeaveRequest::getDaysRequested).sum();
            
            // Leave type statistics
            Map<String, Long> typeCount = requests.stream()
                .collect(java.util.stream.Collectors.groupingBy(LeaveRequest::getLeaveType, java.util.stream.Collectors.counting()));
            
            report.append("SUMMARY:\n");
            report.append("--------\n");
            report.append("Total Requests: ").append(totalRequests).append("\n");
            report.append("Pending: ").append(pendingCount).append("\n");
            report.append("Approved: ").append(approvedCount).append("\n");
            report.append("Rejected: ").append(rejectedCount).append("\n");
            report.append("Total Days Requested: ").append(totalDays).append("\n\n");
            
            report.append("LEAVE TYPE BREAKDOWN:\n");
            report.append("--------------------\n");
            for (Map.Entry<String, Long> entry : typeCount.entrySet()) {
                report.append(entry.getKey()).append(": ").append(entry.getValue()).append(" requests\n");
            }
            report.append("\n");
            
            // Detailed requests
            report.append("DETAILED LEAVE REQUESTS:\n");
            report.append("-----------------------\n");
            report.append(String.format("%-5s %-10s %-15s %-12s %-12s %-8s %-10s\n", 
                "ID", "Emp ID", "Leave Type", "Start Date", "End Date", "Days", "Status"));
            report.append("----------------------------------------------------------------------------------------\n");
            
            for (LeaveRequest req : requests) {
                report.append(String.format("%-5d %-10d %-15s %-12s %-12s %-8d %-10s\n",
                    req.getLeaveId(), req.getEmployeeId(), req.getLeaveType(), 
                    req.getStartDate(), req.getEndDate(), req.getDaysRequested(), req.getStatus()));
            }
            
            reportArea.setText(report.toString());
            
            JScrollPane scrollPane = new JScrollPane(reportArea);
            JPanel buttonPanel = new JPanel(new FlowLayout());
            
            JButton closeBtn = new JButton("Close");
            JButton exportBtn = new JButton("Export to File");
            
            closeBtn.addActionListener(e -> reportDialog.dispose());
            exportBtn.addActionListener(e -> exportReportToFile(report.toString(), "leave_report.txt"));
            
            buttonPanel.add(closeBtn);
            buttonPanel.add(exportBtn);
            
            reportDialog.add(scrollPane, BorderLayout.CENTER);
            reportDialog.add(buttonPanel, BorderLayout.SOUTH);
            reportDialog.setVisible(true);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error generating report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
