package com.example.ufoshooter;

import java.awt.*;

public class Star {
    public int x, y, speed;
    public Color color;

    public Star(int x, int y, int speed, Color color) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.color = color;
    }

    public void move() {
        y += speed;
        if (y > 400) {
            y = 0;
            x = (int)(Math.random() * 600);
        }
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, 2, 2);
    }
}
