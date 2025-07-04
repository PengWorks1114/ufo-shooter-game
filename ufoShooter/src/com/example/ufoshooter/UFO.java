package com.example.ufoshooter;

public class UFO {
    public int x, y;

    public UFO(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        y += 2; // 向下移動速度
    }

    public boolean isOutOfScreen() {
        return y > 400;
    }
}
