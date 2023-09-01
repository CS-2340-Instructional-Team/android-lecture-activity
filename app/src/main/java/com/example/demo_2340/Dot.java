package com.example.demo_2340;

import android.graphics.Color;

public class Dot {
    private float x, y;
    private int radius;
    private boolean isVisible;
    private int color;

    public Dot(float x, float y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.isVisible = true;
        this.color = Color.BLUE;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setInvisible() {
        isVisible = false;
    }
}
