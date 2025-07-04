package com.example.ufoshooter;

import java.awt.*;

public class Explosion {
    private int x, y;
    private int radius = 0;
    private int maxRadius = 30;
    private int alpha = 255;

    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        if (radius < maxRadius) {
            radius += 2;
            alpha -= 15;
        }
    }

    public boolean isFinished() {
        return radius >= maxRadius || alpha <= 0;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(new Color(255, 100, 0, Math.max(alpha, 0)));
        g2d.fillOval(x - radius / 2, y - radius / 2, radius, radius);
    }
}
