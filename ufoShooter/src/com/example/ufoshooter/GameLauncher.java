package com.example.ufoshooter;

import javax.swing.*;

public class GameLauncher {
    public static void main(String[] args) {
        JFrame frame = new JFrame("UFO Shooter");
        GamePanel gamePanel = new GamePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(gamePanel);
        frame.pack(); // 依照內容自動調整大小
        frame.setLocationRelativeTo(null); // 視窗置中
        frame.setVisible(true); // 顯示視窗

        gamePanel.requestFocusInWindow(); // ✅ 加上這行才能讓鍵盤事件有效
    }
}
