import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// Simple Employee class for in-memory storage
class SimpleEmployee {
    private int id;
    private String name;
    private String department;
    private double salary;
    private static int nextId = 1;

    public SimpleEmployee(String name, String department, double salary) {
        this.id = nextId++;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", department=" + department + ", salary=" + salary + "]";
    }
}

// Simple Attendance class for in-memory storage
class SimpleAttendance {
    private int id;
    private int employeeId;
    private String date;
    private String status;
    private static int nextId = 1;

    public SimpleAttendance(int employeeId, String date, String status) {
        this.id = nextId++;
        this.employeeId = employeeId;
        this.date = date;
        this.status = status;
    }

    public int getId() { return id; }
    public int getEmployeeId() { return employeeId; }
    public String getDate() { return date; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return "Attendance [id=" + id + ", employeeId=" + employeeId + ", date=" + date + ", status=" + status + "]";
    }
}

public class HRMSAppSimple extends JFrame {

    private List<SimpleEmployee> employees = new ArrayList<>();
    private List<SimpleAttendance> attendanceRecords = new ArrayList<>();
    private JTextArea outputArea;

    public HRMSAppSimple() {
        setTitle("HRMS Frontend (In-Memory Mode)");
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
        
        // Add some sample data
        employees.add(new SimpleEmployee("John Doe", "IT", 75000.0));
        employees.add(new SimpleEmployee("Jane Smith", "HR", 65000.0));
        employees.add(new SimpleEmployee("Alice Johnson", "Finance", 70000.0));
        
        attendanceRecords.add(new SimpleAttendance(1, "2024-01-15", "Present"));
        attendanceRecords.add(new SimpleAttendance(1, "2024-01-16", "Present"));
        attendanceRecords.add(new SimpleAttendance(2, "2024-01-15", "Present"));
        attendanceRecords.add(new SimpleAttendance(2, "2024-01-16", "Absent"));
        attendanceRecords.add(new SimpleAttendance(3, "2024-01-15", "Present"));
        
        // Show initial message
        outputArea.append("🚀 HRMS Application Started (In-Memory Mode)\n");
        outputArea.append("📝 This version stores data in memory - no database required\n");
        outputArea.append("📊 Sample data has been loaded\n\n");
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
            SimpleEmployee emp = new SimpleEmployee(name, dept, salary);
            employees.add(emp);
            outputArea.append("✅ Employee added: " + name + " (ID: " + emp.getId() + ")\n");
        } catch (Exception ex) {
            outputArea.append("❌ Error: " + ex.getMessage() + "\n");
        }
    }

    private void viewEmployees() {
        outputArea.append("\n--- Employees ---\n");
        if (employees.isEmpty()) {
            outputArea.append("No employees found.\n");
        } else {
            for (SimpleEmployee e : employees) {
                outputArea.append(e.toString() + "\n");
            }
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
            // Check if employee exists
            boolean employeeExists = employees.stream().anyMatch(emp -> emp.getId() == empId);
            if (!employeeExists) {
                outputArea.append("❌ Error: Employee with ID " + empId + " not found\n");
                return;
            }
            
            SimpleAttendance att = new SimpleAttendance(empId, dateStr, status);
            attendanceRecords.add(att);
            outputArea.append("✅ Attendance marked for Employee ID " + empId + " (Record ID: " + att.getId() + ")\n");
        } catch (Exception ex) {
            outputArea.append("❌ Error: " + ex.getMessage() + "\n");
        }
    }

    private void viewAttendance() {
        outputArea.append("\n--- Attendance Records ---\n");
        if (attendanceRecords.isEmpty()) {
            outputArea.append("No attendance records found.\n");
        } else {
            for (SimpleAttendance a : attendanceRecords) {
                outputArea.append(a.toString() + "\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HRMSAppSimple().setVisible(true);
        });
    }
}

