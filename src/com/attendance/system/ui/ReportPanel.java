package com.attendance.system.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReportPanel extends JPanel {

    private JTable tblReport;
    private DefaultTableModel tableModel;

    public ReportPanel() {
        setBackground(new Color(0xF3F4F6));
        setLayout(new BorderLayout(16, 16));

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(16, 24, 24, 24));

        content.add(createReportCard());

        add(content, BorderLayout.CENTER);
    }

    private JComponent createReportCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xE5E7EB)),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel("Full Attendance Report");
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

        tblReport = new JTable(tableModel);
        tblReport.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(tblReport);
        card.add(scroll, BorderLayout.CENTER);

        addSampleRows();

        return card;
    }

    private void addSampleRows() {
        tableModel.addRow(new Object[] { "1MS24CS990", "John", "December 12th, 2025", "Absent" });
        tableModel.addRow(new Object[] { "1MS24CS041", "Arpitha R", "December 12th, 2025", "Present" });
        tableModel.addRow(new Object[] { "1MS24CS034", "Anusha Agarwal", "December 12th, 2025", "Present" });
        tableModel.addRow(new Object[] { "1MS24CS031", "Anouska Paul", "December 12th, 2025", "Present" });
    }
}