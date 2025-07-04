package com.example.ufoshooter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener {
    private Timer timer;
    private int playerX = 250; // 玩家起始 X 位置

    public GamePanel() {
        setPreferredSize(new Dimension(600, 400)); // 遊戲視窗大小
        setBackground(Color.BLACK);
        timer = new Timer(20, this); // 每 20ms 執行一次 actionPerformed
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 370, 40, 10); // 玩家砲台（固定畫面下方）
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint(); // 重繪畫面
    }
}
