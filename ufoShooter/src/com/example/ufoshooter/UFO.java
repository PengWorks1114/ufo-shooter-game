package com.example.ufoshooter;

public class UFO {
    public int x, y;

    public UFO(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int level) {
        y += 1 + level; // 基礎速度 + 等級加成
    }

    public boolean isOutOfScreen() {
        return y > 400;
    }
}

