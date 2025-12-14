package com.attendance.system.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public static final String CARD_LOGIN = "LOGIN";
    public static final String CARD_ATTENDANCE = "ATTENDANCE";
    public static final String CARD_REPORTS = "REPORTS";

    private CardLayout cardLayout;
    private JPanel contentCards;
    private JLabel lblTopTitle;
    private JPanel sidebar;

    public MainFrame() {
        setTitle("CampusAttend - Attendance Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        setIconImage(new ImageIcon("resources/icons/app_icon.jpg").getImage());

        sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        add(createMainArea(), BorderLayout.CENTER);

        // start on login screen
        showCard(CARD_LOGIN, "");
        sidebar.setVisible(false);

        setVisible(true);
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(new Color(0x111827));
        sidebar.setLayout(new BorderLayout());

        // top: app name
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 16));
        top.setOpaque(false);
        JLabel lblIcon = new JLabel("\u25CF");
        lblIcon.setForeground(new Color(0x4F46E5));
        lblIcon.setFont(lblIcon.getFont().deriveFont(Font.BOLD, 18f));
        JLabel lblAppName = new JLabel("CampusAttend");
        lblAppName.setForeground(Color.WHITE);
        lblAppName.setFont(lblAppName.getFont().deriveFont(Font.BOLD, 14f));
        top.add(lblIcon);
        top.add(lblAppName);
        sidebar.add(top, BorderLayout.NORTH);

        // middle: nav buttons
        JPanel nav = new JPanel();
        nav.setOpaque(false);
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));

        JButton btnAttendance = createNavButton("Attendance");
        JButton btnReports = createNavButton("Reports");

        nav.add(btnAttendance);
        nav.add(btnReports);
        nav.add(Box.createVerticalGlue());

        sidebar.add(nav, BorderLayout.CENTER);

        // bottom: exit
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        bottom.setOpaque(false);
        JButton btnExit = new JButton("Exit");
        btnExit.setFocusPainted(false);
        bottom.add(btnExit);
        sidebar.add(bottom, BorderLayout.SOUTH);

        btnAttendance.addActionListener(e -> showCard(CARD_ATTENDANCE, "Attendance"));
        btnReports.addActionListener(e -> showCard(CARD_REPORTS, "Reports"));
        btnExit.addActionListener(e -> dispose());

        return sidebar;
    }

    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(0x111827));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        return btn;
    }

    private JPanel createMainArea() {
        JPanel main = new JPanel(new BorderLayout());

        // top bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        topBar.setBackground(Color.WHITE);
        lblTopTitle = new JLabel("");
        lblTopTitle.setFont(lblTopTitle.getFont().deriveFont(Font.BOLD, 18f));
        topBar.add(lblTopTitle, BorderLayout.WEST);
        main.add(topBar, BorderLayout.NORTH);

        // cards
        cardLayout = new CardLayout();
        contentCards = new JPanel(cardLayout);

        LoginPanel loginPanel = new LoginPanel(this);
        JPanel attendancePanel = new AttendancePanel();
        JPanel reportPanel = new ReportPanel();

        contentCards.add(loginPanel, CARD_LOGIN);
        contentCards.add(attendancePanel, CARD_ATTENDANCE);
        contentCards.add(reportPanel, CARD_REPORTS);

        main.add(contentCards, BorderLayout.CENTER);
        return main;
    }

    private void showCard(String name, String title) {
        cardLayout.show(contentCards, name);
        lblTopTitle.setText(title);
    }

    // called from LoginPanel after successful login
    public void showAfterLogin() {
        sidebar.setVisible(true);
        showCard(CARD_ATTENDANCE, "Attendance");
    }
}