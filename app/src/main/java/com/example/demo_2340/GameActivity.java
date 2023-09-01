package com.example.demo_2340;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
public class GameActivity extends AppCompatActivity {

    private PlayerView playerView;
    private float playerX, playerY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        RelativeLayout gameLayout = findViewById(R.id.gameLayout);
        playerX = screenWidth /2;
        playerY = screenHeight / 2;

        // Create red dot
        playerView = new PlayerView(this, playerX, playerY, 50);
        gameLayout.addView(playerView);
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
}