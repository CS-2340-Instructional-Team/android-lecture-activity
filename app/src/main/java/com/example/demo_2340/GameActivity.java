package com.example.demo_2340;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private PlayerView playerView;
    private float playerX, playerY;
    private List<Dot> dots = new ArrayList<>();
    private Random random = new Random();
    RelativeLayout gameLayout;
    int screenWidth;
    int screenHeight;
    private Map<Dot, DotView> dotViewMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameLayout = findViewById(R.id.gameLayout);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        playerX = screenWidth / 2;
        playerY = screenHeight / 2;

        // Create red dot
        playerView = new PlayerView(this, playerX, playerY, 100);
        gameLayout.addView(playerView);
        initializeDots();
        drawDots();

    }

    // Handle key events to move the player
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                playerX -= 50;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                playerX += 50;
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                playerY -= 50;
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                playerY += 50;
                break;
        }
        playerView.updatePosition(playerX, playerY);
        checkCollisions();
        return true;
    }

    private void initializeDots() {
        // Create and add dots with random positions
        for (int i = 0; i < 5; i++) {
            float randomX = random.nextFloat() * screenWidth;
            float randomY = random.nextFloat() * screenHeight;
            int radius = 50;
            Dot dot = new Dot(randomX, randomY, radius);
            dots.add(dot);
        }
    }

    private void drawDots() {
        for (Dot dot : dots) {
            DotView newDot = new DotView(this, dot);
            gameLayout.addView(newDot);
            dotViewMap.put(dot, newDot);
        }
    }

    private void checkCollisions() {
        for (int i = 0; i < dots.size(); i++) {
            Dot dot = dots.get(i);
            if (dot.isVisible() && isCollision(playerView, dot)) {
                Log.d("collide", "dot:" + dot.getX() + dot.getY());
                dot.setInvisible();
                gameLayout.removeView(dotViewMap.get(dot));
            }
        }
    }

    private boolean isCollision(PlayerView playerView, Dot dot) {
            float playerX = playerView.getX();
            float playerY = playerView.getY();
            int playerRadius = playerView.getRadius();
            float dotX = dot.getX();
            float dotY = dot.getY();
            int dotRadius = dot.getRadius();

            RectF playerRect = new RectF(playerX - playerRadius, playerY - playerRadius, playerX + playerRadius, playerY + playerRadius);
            RectF dotRect = new RectF(dotX - dotRadius, dotY - dotRadius, dotX + dotRadius, dotY + dotRadius);

            return playerRect.intersect(dotRect);
        }
    }
