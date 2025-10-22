import java.awt.*;
import javax.swing.*;

public class DepartmentDialog extends JDialog {
    private JTextField tfId;
    private JTextField tfName;
    private JTextField tfLocation;
    private boolean succeeded;

    public DepartmentDialog(Object[] existingData) {
        setTitle(existingData == null ? "Add Department" : "Edit Department");
        setModal(true);
        setSize(350, 220);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4,2,5,5));
        panel.add(new JLabel("Dept ID:"));
        tfId = new JTextField();
        panel.add(tfId);

        panel.add(new JLabel("Dept Name:"));
        tfName = new JTextField();
        panel.add(tfName);

        panel.add(new JLabel("Location:"));
        tfLocation = new JTextField();
        panel.add(tfLocation);

        getContentPane().add(panel, BorderLayout.CENTER);

        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnOk);
        btnPanel.add(btnCancel);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);

        if (existingData != null) {
            tfId.setText(existingData[0].toString());
            tfId.setEnabled(false);
            tfName.setText(existingData[1].toString());
            tfLocation.setText(existingData[2].toString());
        }

        btnOk.addActionListener(e -> {
            succeeded = true;
            dispose();
        });

        btnCancel.addActionListener(e -> {
            succeeded = false;
            dispose();
        });
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public Object[] getDeptData() {
        return new Object[] {
            Integer.parseInt(tfId.getText().trim()),
            tfName.getText().trim(),
            tfLocation.getText().trim()
        };
    }
}
