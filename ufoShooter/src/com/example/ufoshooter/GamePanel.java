package com.example.ufoshooter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private int playerX = 250;
    private final int playerWidth = 40;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public GamePanel() {
        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(20, this); // 每 20ms 更新一次畫面
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 畫出玩家砲台
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 370, playerWidth, 10);

        // 畫出子彈
        g.setColor(Color.YELLOW);
        for (Bullet b : bullets) {
            g.fillRect(b.x, b.y, 4, 10);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 子彈移動與移除畫面外的子彈
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            b.move();
            if (b.isOutOfScreen()) {
                it.remove();
            }
        }

        repaint(); // 重新繪製畫面
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // ← 鍵：左移
        if (e.getKeyCode() == KeyEvent.VK_LEFT && playerX > 0) {
            playerX -= 10;
        }
        // → 鍵：右移
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && playerX < getWidth() - playerWidth) {
            playerX += 10;
        }
        // 空白鍵：發射子彈
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            bullets.add(new Bullet(playerX + playerWidth / 2 - 2, 360));
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
