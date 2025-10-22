import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class EmployeesPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public EmployeesPanel() {
        setLayout(new BorderLayout());

        // Table model with sample columns
        String[] columns = { "ID", "Name", "Department", "Position", "Salary" };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        // Load sample data (later you’d fetch from backend)
        loadSampleData();

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        JButton btnAdd = new JButton("Add Employee");
        JButton btnEdit = new JButton("Edit Employee");
        JButton btnRefresh = new JButton("Refresh");

        buttonsPanel.add(btnAdd);
        buttonsPanel.add(btnEdit);
        buttonsPanel.add(btnRefresh);

        add(buttonsPanel, BorderLayout.SOUTH);

        // Button actions
        btnAdd.addActionListener(e -> showAddDialog());
        btnEdit.addActionListener(e -> showEditDialog());
        btnRefresh.addActionListener(e -> refreshTable());
    }

    private void loadSampleData() {
        Object[][] data = {
            { 1, "Alice Smith", "HR", "Manager", 75000 },
            { 2, "Bob Jones", "IT", "Developer", 65000 },
        };
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    private void showAddDialog() {
        EmployeeDialog dialog = new EmployeeDialog(null);
        dialog.setVisible(true);
        if (dialog.isSucceeded()) {
            Object[] row = dialog.getEmployeeData();
            tableModel.addRow(row);
        }
    }

    private void showEditDialog() {
        int selected = table.getSelectedRow();
        if (selected >= 0) {
            Object id = tableModel.getValueAt(selected, 0);
            Object name = tableModel.getValueAt(selected, 1);
            Object dept = tableModel.getValueAt(selected, 2);
            Object pos = tableModel.getValueAt(selected, 3);
            Object sal = tableModel.getValueAt(selected, 4);

            EmployeeDialog dialog = new EmployeeDialog(new Object[]{ id, name, dept, pos, sal });
            dialog.setVisible(true);
            if (dialog.isSucceeded()) {
                Object[] row = dialog.getEmployeeData();
                for (int i = 0; i < row.length; i++) {
                    tableModel.setValueAt(row[i], selected, i);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select an employee to edit.");
        }
    }

    private void refreshTable() {
        // In real app: reload from backend. Here: just show message.
        JOptionPane.showMessageDialog(this, "Refreshing data...");
    }
}
