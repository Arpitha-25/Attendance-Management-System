package com.attendance.system;

import javax.swing.SwingUtilities;
import com.attendance.system.ui.MainFrame;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}