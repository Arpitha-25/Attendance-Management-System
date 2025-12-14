package com.attendance.system.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.toedter.calendar.JDateChooser;   // JCalendar date chooser

public class AttendancePanel extends JPanel {

    private JTextField txtUsn;
    private JTextField txtName;
    private JDateChooser dateChooser;       // calendar component
    private JComboBox<String> cmbStatus;
    private JButton btnAddRecord;
    private JButton btnClear;

    private JTable tblRecent;
    private DefaultTableModel tableModel;

    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("MMMM dd'th', yyyy"); // e.g., December 12th, 2025

    public AttendancePanel() {
        setBackground(new Color(0xF3F4F6));
        setLayout(new BorderLayout(16, 16));

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(16, 24, 24, 24));

        content.add(createMarkAttendanceCard());
        content.add(Box.createVerticalStrut(16));
        content.add(createRecentEntriesCard());

        add(content, BorderLayout.CENTER);
    }

    private JComponent createMarkAttendanceCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xE5E7EB)),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel("Mark Attendance");
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 18f));
        JLabel lblSub = new JLabel("Fill in the details below to add a new attendance record.");
        lblSub.setFont(lblSub.getFont().deriveFont(Font.PLAIN, 12f));
        lblSub.setForeground(new Color(0x6B7280));

        header.add(lblTitle);
        header.add(Box.createVerticalStrut(4));
        header.add(lblSub);

        card.add(header, BorderLayout.NORTH);

        JPanel formRow = new JPanel(new GridBagLayout());
        formRow.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;
        gbc.weighty = 0;
        gbc.weightx = 0.25;

        txtUsn = createTextField("e.g., 1MS24CS001");
        txtName = createTextField("e.g., John Doe");

        // JDateChooser for calendar popup
        dateChooser = new JDateChooser();
        dateChooser.setDate(new Date());                 // today's date
        dateChooser.setDateFormatString("MMMM dd, yyyy"); // display format
        dateChooser.setPreferredSize(new Dimension(160, 32));

        cmbStatus = new JComboBox<>(new String[] { "Present", "Absent" });

        addLabeledField(formRow, gbc, 0, "USN", txtUsn);
        addLabeledField(formRow, gbc, 1, "Name", txtName);
        addLabeledField(formRow, gbc, 2, "Date", dateChooser); // use chooser
        addLabeledField(formRow, gbc, 3, "Status", cmbStatus);

        card.add(formRow, BorderLayout.CENTER);

        JPanel buttonsRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        buttonsRow.setOpaque(false);
        btnClear = new JButton("Clear");
        btnAddRecord = new JButton("Add Record");
        btnAddRecord.setBackground(new Color(0x059669));
        btnAddRecord.setForeground(Color.WHITE);
        btnAddRecord.setFocusPainted(false);
        buttonsRow.add(btnClear);
        buttonsRow.add(btnAddRecord);
        card.add(buttonsRow, BorderLayout.SOUTH);

        btnClear.addActionListener(e -> clearForm());
        btnAddRecord.addActionListener(e -> addRecord());

        return card;
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(160, 32));
        field.setToolTipText(placeholder);
        return field;
    }

    private void addLabeledField(JPanel panel, GridBagConstraints gbc,
                                 int gridx, String label, JComponent field) {
        gbc.gridx = gridx;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel(label), gbc);
        gbc.gridy = 1;
        panel.add(field, gbc);
    }

    private JComponent createRecentEntriesCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xE5E7EB)),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel("Recent Entries");
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 18f));
        JLabel lblSub = new JLabel("A list of student attendance records.");
        lblSub.setFont(lblSub.getFont().deriveFont(Font.PLAIN, 12f));
        lblSub.setForeground(new Color(0x6B7280));

        header.add(lblTitle);
        header.add(Box.createVerticalStrut(4));
        header.add(lblSub);
        card.add(header, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new Object[] { "Student ID (USN)", "Name", "Date", "Status" }, 0);

        tblRecent = new JTable(tableModel);
        tblRecent.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(tblRecent);
        card.add(scroll, BorderLayout.CENTER);

        return card;
    }

    private void clearForm() {
        txtUsn.setText("");
        txtName.setText("");
        dateChooser.setDate(new Date());
        cmbStatus.setSelectedIndex(0);
    }

    private void addRecord() {
        String usn = txtUsn.getText().trim();
        String name = txtName.getText().trim();
        Date selectedDate = dateChooser.getDate();
        String status = (String) cmbStatus.getSelectedItem();

        if (usn.isEmpty() || name.isEmpty() || selectedDate == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String dateString = dateFormat.format(selectedDate);

        tableModel.addRow(new Object[] { usn, name, dateString, status});
        clearForm();
    }
}