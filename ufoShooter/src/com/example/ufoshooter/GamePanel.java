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
    private ArrayList<UFO> ufos = new ArrayList<>(); // UFO 敵人列表
    private int score = 0; // 分數
    private int life = 3; // 玩家生命值
    private boolean gameOver = false; // 遊戲結束判定

    public GamePanel() {
        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(20, this); // 每 20ms 更新一次畫面
        timer.start();

        // UFO 產生計時器（每 1 秒）
        new Timer(1000, e -> {
            if (!gameOver) {
                int randomX = (int)(Math.random() * (getWidth() - 40));
                ufos.add(new UFO(randomX, 0));
            }
        }).start();

        requestFocusInWindow(); // 確保能接收鍵盤輸入
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

        // 畫出 UFO 敵人
        g.setColor(Color.RED);
        for (UFO u : ufos) {
            g.fillOval(u.x, u.y, 40, 20);
        }

        // 畫出分數與生命值
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Life: " + life, 10, 40);

        // 若遊戲結束，顯示 Game Over 訊息
        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over!", 200, 180);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.drawString("Press R to restart", 210, 220);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;

        // 子彈移動與移除畫面外的子彈
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            b.move();
            if (b.isOutOfScreen()) {
                it.remove();
            }
        }

        // UFO 移動與生命值扣減、Game Over 判定
        Iterator<UFO> uit = ufos.iterator();
        while (uit.hasNext()) {
            UFO u = uit.next();
            u.move();
            if (u.isOutOfScreen()) {
                uit.remove(); // 移除掉落的 UFO
                life--;       // 扣 1 點血
                if (life <= 0) {
                    gameOver = true;
                }
            }
        }

        // 子彈與 UFO 碰撞判定
        it = bullets.iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            Iterator<UFO> uit2 = ufos.iterator();
            while (uit2.hasNext()) {
                UFO u = uit2.next();
                if (b.x >= u.x && b.x <= u.x + 40 && b.y >= u.y && b.y <= u.y + 20) {
                    it.remove();
                    uit2.remove();
                    score += 10; // 擊中得分
                    break;
                }
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
        // R 鍵：重新開始遊戲
        else if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            resetGame();
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    // 重新開始遊戲的邏輯
    private void resetGame() {
        playerX = 250;
        bullets.clear();
        ufos.clear();
        score = 0;
        life = 3;
        gameOver = false;
    }
}
