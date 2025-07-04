package com.example.ufoshooter;

import java.awt.*;

public class UFO {
    public int x, y;
    private UFOType type;

    public UFO(int x, int y, UFOType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void move(int level) {
        switch (type) {
            case NORMAL -> y += 1 + level;
            case FAST -> y += 2 + level;
            case TANK -> y += 0.5 + level;
        }
    }

    public boolean isOutOfScreen() {
        return y > 400;
    }

    public UFOType getType() {
        return type;
    }

    public void draw(Graphics2D g2d) {
        Color color = switch (type) {
            case NORMAL -> Color.LIGHT_GRAY;
            case FAST -> Color.RED;
            case TANK -> Color.GREEN;
        };
        g2d.setColor(color);
        g2d.fillOval(x, y, 40, 20);
        g2d.setColor(Color.GRAY);
        g2d.drawOval(x, y, 40, 20);
    }

    public int getScore() {
        return switch (type) {
            case NORMAL -> 10;
            case FAST -> 20;
            case TANK -> 30;
        };
    }
}
