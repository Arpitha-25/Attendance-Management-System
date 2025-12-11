package com.attendance.system.ui;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblMessage;
    private MainFrame mainFrame;

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setBackground(new Color(0xF5F5F7));
        setLayout(new GridBagLayout());

        JPanel card = createCardPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(card, gbc);
    }

    private JPanel createCardPanel() {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xE0E0E0)),
                BorderFactory.createEmptyBorder(24, 32, 24, 32)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        ImageIcon appIcon = new ImageIcon("resources/icons/app_icon.jpg");
        Image scaled = appIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        JLabel lblIcon = new JLabel(new ImageIcon(scaled));
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);


        JLabel lblTitle = new JLabel("CampusAttend", SwingConstants.CENTER);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 22f));

        JLabel lblSubtitle = new JLabel("Enter your credentials to access your dashboard");
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitle.setFont(lblSubtitle.getFont().deriveFont(Font.PLAIN, 12f));
        lblSubtitle.setForeground(new Color(0x6B7280));

        card.add(Box.createVerticalStrut(8));
        card.add(lblIcon);
        card.add(Box.createVerticalStrut(8));
        card.add(lblTitle);
        card.add(Box.createVerticalStrut(4));
        card.add(lblSubtitle);
        card.add(Box.createVerticalStrut(18));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 0, 6, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        // Username
        gbc.gridy = 0;
        form.add(new JLabel("Username"), gbc);
        gbc.gridy = 1;
        txtUsername = new JTextField();
        txtUsername.setPreferredSize(new Dimension(260, 32));
        form.add(txtUsername, gbc);

        // Password
        gbc.gridy = 2;
        form.add(new JLabel("Password"), gbc);
        gbc.gridy = 3;
        txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(new Dimension(260, 32));
        form.add(txtPassword, gbc);

        card.add(form);
        card.add(Box.createVerticalStrut(18));

        btnLogin = new JButton("   Login");
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setFocusPainted(false);
        btnLogin.setBackground(new Color(0x3749DB));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setPreferredSize(new Dimension(260, 36));
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        card.add(btnLogin);
        card.add(Box.createVerticalStrut(8));

        lblMessage = new JLabel(" ", SwingConstants.CENTER);
        lblMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMessage.setForeground(Color.RED);
        lblMessage.setFont(lblMessage.getFont().deriveFont(Font.PLAIN, 11f));
        card.add(lblMessage);

        btnLogin.addActionListener(e -> onLoginClicked());

        return card;
    }

    private void onLoginClicked() {
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            lblMessage.setText("Please enter username and password.");
        } else if (user.equals("admin") && pass.equals("admin")) {
            lblMessage.setText("Login successful.");
            if (mainFrame != null) {
                mainFrame.showAfterLogin();
            }
        } else {
            lblMessage.setText("Invalid credentials.");
        }
    }
}
