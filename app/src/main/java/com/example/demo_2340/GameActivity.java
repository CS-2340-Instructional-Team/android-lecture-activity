package com.example.demo_2340;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private PlayerView playerView;
    private float playerX, playerY;
    private List<Dot> dots = new ArrayList<>();
    private Random random = new Random();
    RelativeLayout gameLayout;
    int screenWidth;
    int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameLayout = findViewById(R.id.gameLayout);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        playerX = screenWidth /2;
        playerY = screenHeight / 2;

        // Create red dot
        playerView = new PlayerView(this, playerX, playerY, 50);
        gameLayout.addView(playerView);
        initializeDots2();

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
        return true;
    }

    private void initializeDots() {
        // Create and add dots with random positions
        for (int i = 0; i < 10; i++) { // You can adjust the number of dots as needed
            float randomX = random.nextFloat() * screenWidth; // Random X position
            float randomY = random.nextFloat() * screenHeight; // Random Y position
            int radius = 10; // Adjust the dot radius as needed
            Dot dot = new Dot(randomX, randomY, radius);
            dots.add(dot);
        }
    }
    private void initializeDots2() {
        // Create and add DotView for each dot
        for (int i = 0; i < 5; i++) { // You can adjust the number of dots as needed
            float randomX = random.nextFloat() * screenWidth; // Random X position
            float randomY = random.nextFloat() * screenHeight; // Random Y position
            //float randomX = 500;
            //float randomY = 400;
            int radius = 100; // Adjust the dot radius as needed
            DotView2 dot = new DotView2(this, randomX, randomY, radius);
            Log.d("DOTS","X:" + randomX + "Y: " + randomY);
            gameLayout.addView(dot);
        }
    }
}