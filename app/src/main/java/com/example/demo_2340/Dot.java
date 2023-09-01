package com.example.demo_2340;

public class Dot {

    private float x, y;
    private int radius;
    private boolean isVisible;

    public Dot(float x, float y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.isVisible = true;
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

    public boolean checkCollision(float playerX, float playerY, int playerRadius) {
        if (isVisible) {
            float dx = x - playerX;
            float dy = y - playerY;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            return distance < (radius + playerRadius);
        }
        return false;
    }
}
