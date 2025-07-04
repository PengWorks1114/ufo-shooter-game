package com.example.ufoshooter;

public class Bullet {
    public int x;
    public int y;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        y -= 5; // 每次向上移動 5 單位
    }

    public boolean isOutOfScreen() {
        return y < 0;
    }
}
