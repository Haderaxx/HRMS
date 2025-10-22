package com.hrms.gui;

import com.hrms.dao.EmployeeDAO;
import com.hrms.model.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeManagementApp extends JFrame {

    private JTable employeeTable;
    private DefaultTableModel tableModel;

    public EmployeeManagementApp() {
        setTitle("HRMS Employee Management");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table setup
        String[] columnNames = {"ID", "Name", "Department", "Position", "Salary"};
        tableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);

        // Buttons
        JButton addButton = new JButton("Add Employee");
        JButton viewButton = new JButton("View Employees");
        JButton deleteButton = new JButton("Delete Selected");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(deleteButton);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> openAddEmployeeDialog());
        viewButton.addActionListener(e -> refreshEmployeeTable());
        deleteButton.addActionListener(e -> deleteSelectedEmployee());

        // Initial load
        refreshEmployeeTable();
    }

    private void openAddEmployeeDialog() {
        EmployeeDialog dialog = new EmployeeDialog(null);
        dialog.setVisible(true);

        if (dialog.isSucceeded()) {
            Object[] data = dialog.getEmployeeData();
            Employee emp = new Employee(
                    data[1].toString(),
                    data[2].toString(),
                    data[3].toString(),
                    Double.parseDouble(data[4].toString())
            );

            EmployeeDAO dao = new EmployeeDAO();
            dao.addEmployee(emp);

            refreshEmployeeTable();
        }
    }

    private void refreshEmployeeTable() {
        EmployeeDAO dao = new EmployeeDAO();
        List<Employee> employees = dao.getAllEmployees();

        tableModel.setRowCount(0); // Clear table

        for (Employee emp : employees) {
            tableModel.addRow(new Object[]{
                    emp.getId(),
                    emp.getName(),
                    emp.getDepartment(),
                    emp.getPosition(),
                    emp.getSalary()
            });
        }
    }

    private void deleteSelectedEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            EmployeeDAO dao = new EmployeeDAO();
            boolean success = dao.deleteEmployee(id);
            if (success) {
                JOptionPane.showMessageDialog(this, "Employee deleted successfully");
                refreshEmployeeTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete employee");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to delete");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EmployeeManagementApp().setVisible(true);
        });
    }
}
