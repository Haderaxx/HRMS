package com.hrms.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.hrms.dao.EmployeeDAO;
import com.hrms.model.Employee;

public class EmployeePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public EmployeePanel() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new String[]{"ID", "Name", "Department", "Salary"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton refreshBtn = new JButton("Refresh");
        JButton addBtn = new JButton("Add Employee");

        refreshBtn.addActionListener(e -> loadEmployees());
        addBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Add Employee Form Placeholder"));

        btnPanel.add(refreshBtn);
        btnPanel.add(addBtn);
        add(btnPanel, BorderLayout.SOUTH);

        loadEmployees();
    }

    private void loadEmployees() {
        model.setRowCount(0);
        try {
            EmployeeDAO dao = new EmployeeDAO();
            List<Employee> employees = dao.getAllEmployees();
            for (Employee emp : employees) {
                model.addRow(new Object[]{emp.getId(), emp.getName(), emp.getDepartment(), emp.getSalary()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to load employees: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
