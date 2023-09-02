package com.example.demo_2340;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private PlayerView playerView;
    private float playerX, playerY;
    private List<Dot> dots = new ArrayList<>();
    private Random random = new Random();
    RelativeLayout gameLayout;
    int screenWidth;
    int screenHeight;
    private Map<Dot, DotView> dotViewMap = new HashMap<>();
    private Timer dotTimer;
    private static final int MAX_DOTS = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameLayout = findViewById(R.id.gameLayout);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        // Spawn player in middle of screen
        playerX = screenWidth / 2;
        playerY = screenHeight / 2;

        // Create red dot
        playerView = new PlayerView(this, playerX, playerY, 100);
        gameLayout.addView(playerView);
        // Create dot list
        initializeDots();
        // Draw dots on screen
        drawDots();


        /*
        Timer to call checkCollisions every .5 seconds to determine if dots have expired yet.
         */
        dotTimer = new Timer();
        dotTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkCollisions();
                        respawnDotsIfNeeded();
                    }
                });
            }
        }, 0, 500); // Check every .5 seconds
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
        for (int i = 0; i < 20; i++) {
            float randomX = random.nextFloat() * screenWidth;
            float randomY = random.nextFloat() * screenHeight;
            int radius = 50;
            Dot dot = new Dot(randomX, randomY, radius);
            dots.add(dot);
        }
    }

    /*
    Method to create dot objects. Maps a dot object to a specific dotView.
     */
    private void drawDots() {
        for (Dot dot : dots) {
            DotView newDot = new DotView(this, dot);
            gameLayout.addView(newDot);
            dotViewMap.put(dot, newDot);
        }
    }

    private void respawnDotsIfNeeded() {
        int visibleDotCount = 0;
        for (Dot dot : dots) {
            if (dot.isVisible()) {
                visibleDotCount++;
            }
        }

        int dotsToRespawn = MAX_DOTS - visibleDotCount;
        for (int i = 0; i < dotsToRespawn; i++) {
            respawnDot();
        }
    }

    private void respawnDot() {
        float randomX = random.nextFloat() * screenWidth;
        float randomY = random.nextFloat() * screenHeight;
        int radius = 50;
        Dot dot = new Dot(randomX, randomY, radius);
        dots.add(dot);
        DotView dotView = new DotView(this, dot);
        gameLayout.addView(dotView);
        dotViewMap.put(dot, dotView);
    }

    /*
    Method that checks to see if any collision has occurred.
     */
    private void checkCollisions() {
        for (int i = 0; i < dots.size(); i++) {
            Dot dot = dots.get(i);
            if (dot.isVisible() && isCollision(playerView, dot)) {
                dot.setInvisible();
                gameLayout.removeView(dotViewMap.get(dot));
                dots.remove(i);
            } else if (dot.isExpired()) { // Checks if dots have run out of time.
                dot.setInvisible();
                gameLayout.removeView(dotViewMap.get(dot));
                dots.remove(i);
            }
        }
    }

    /*
    Method that has logic to detect collisions.
    */
    private boolean isCollision(PlayerView playerView, Dot dot) {
        float playerX = playerView.getX();
        float playerY = playerView.getY();
        int playerRadius = playerView.getRadius();
        float dotX = dot.getX();
        float dotY = dot.getY();
        int dotRadius = dot.getRadius();

            /*
            Creates a rectangle around dot, and checks for an intersection between player rect and
            dot rect. Intersection = collision.
             */
        RectF playerRect = new RectF(playerX - playerRadius, playerY - playerRadius, playerX + playerRadius, playerY + playerRadius);
        RectF dotRect = new RectF(dotX - dotRadius, dotY - dotRadius, dotX + dotRadius, dotY + dotRadius);

        return playerRect.intersect(dotRect);
    }
}
