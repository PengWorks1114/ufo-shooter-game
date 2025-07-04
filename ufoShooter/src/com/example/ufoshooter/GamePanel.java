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
    private ArrayList<Star> stars = new ArrayList<>(); // 背景星星列表
    private int score = 0; // 分數
    private int life = 3; // 玩家生命值
    private int level = 1; // 難度等級
    private boolean gameOver = false; // 遊戲結束判定

    public GamePanel() {
        setPreferredSize(new Dimension(600, 400));
        setBackground(new Color(10, 10, 30)); // 改為深藍色背景
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

        // 初始化背景星星
        for (int i = 0; i < 100; i++) {
            int x = (int)(Math.random() * 600);
            int y = (int)(Math.random() * 400);
            int speed = 1 + (int)(Math.random() * 2);
            Color color = new Color(200 + (int)(Math.random() * 55), 200 + (int)(Math.random() * 55), 200 + (int)(Math.random() * 55));
            stars.add(new Star(x, y, speed, color));
        }

        requestFocusInWindow(); // 確保能接收鍵盤輸入
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 畫出背景星星
        for (Star s : stars) {
            s.draw(g2d);
        }

        // 畫出玩家砲台（亮藍 + 邊框）
        g2d.setColor(Color.CYAN);
        g2d.fillRoundRect(playerX, 370, playerWidth, 12, 10, 10);
        g2d.setColor(Color.WHITE);
        g2d.drawRoundRect(playerX, 370, playerWidth, 12, 10, 10);

        // 畫出子彈（亮橘色）
        g2d.setColor(Color.ORANGE);
        for (Bullet b : bullets) {
            g2d.fillRoundRect(b.x, b.y, 4, 10, 4, 4);
        }

        // 畫出 UFO 敵人（銀灰 + 陰影）
        for (UFO u : ufos) {
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillOval(u.x + 3, u.y + 3, 40, 20); // 陰影
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillOval(u.x, u.y, 40, 20); // 本體
            g2d.setColor(Color.GRAY);
            g2d.drawOval(u.x, u.y, 40, 20); // 邊框
        }

        // 畫出分數、生命值與等級（白字 + 粗體）
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Consolas", Font.BOLD, 14));
        g2d.drawString("Score: " + score, 10, 20);
        g2d.drawString("Life: " + life, 10, 40);
        g2d.drawString("Level: " + level, getWidth() - 100, 20);

        // 若遊戲結束，顯示 Game Over 訊息（半透明黑底）
        if (gameOver) {
            g2d.setColor(new Color(0, 0, 0, 180));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
            g2d.drawString("Game Over!", 200, 180);
            g2d.setFont(new Font("Arial", Font.PLAIN, 16));
            g2d.drawString("Press R to restart", 210, 220);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;

        // 星星移動動畫
        for (Star s : stars) {
            s.move();
        }

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
            u.move(level); // 根據等級移動速度變化
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
                    level = score / 100 + 1; // 每 100 分升 1 級
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
        level = 1;
        gameOver = false;
    }
}
