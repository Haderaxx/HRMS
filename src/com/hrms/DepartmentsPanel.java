import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class DepartmentsPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public DepartmentsPanel() {
        setLayout(new BorderLayout());

        String[] columns = { "Dept ID", "Dept Name", "Location" };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        loadSampleData();

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        JButton btnAdd = new JButton("Add Department");
        JButton btnEdit = new JButton("Edit Department");
        JButton btnRefresh = new JButton("Refresh");

        buttonsPanel.add(btnAdd);
        buttonsPanel.add(btnEdit);
        buttonsPanel.add(btnRefresh);

        add(buttonsPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> showAddDialog());
        btnEdit.addActionListener(e -> showEditDialog());
        btnRefresh.addActionListener(e -> refreshTable());
    }

    private void loadSampleData() {
        Object[][] data = {
            { 10, "HR", "Head Office" },
            { 20, "IT", "Mumbai" },
        };
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    private void showAddDialog() {
        DepartmentDialog dialog = new DepartmentDialog(null);
        dialog.setVisible(true);
        if (dialog.isSucceeded()) {
            Object[] row = dialog.getDeptData();
            tableModel.addRow(row);
        }
    }

    private void showEditDialog() {
        int selected = table.getSelectedRow();
        if (selected >= 0) {
            Object id = tableModel.getValueAt(selected, 0);
            Object name = tableModel.getValueAt(selected, 1);
            Object loc = tableModel.getValueAt(selected, 2);

            DepartmentDialog dialog = new DepartmentDialog(new Object[]{ id, name, loc });
            dialog.setVisible(true);
            if (dialog.isSucceeded()) {
                Object[] row = dialog.getDeptData();
                for (int i = 0; i < row.length; i++) {
                    tableModel.setValueAt(row[i], selected, i);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a department to edit.");
        }
    }

    private void refreshTable() {
        JOptionPane.showMessageDialog(this, "Refreshing departments data...");
    }
}
