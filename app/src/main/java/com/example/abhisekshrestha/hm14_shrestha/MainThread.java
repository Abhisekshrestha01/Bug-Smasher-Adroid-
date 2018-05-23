//Abhisek shrestha
//Homework 14
//Android Programming

package com.example.abhisekshrestha.hm14_shrestha;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.SurfaceHolder;
import android.widget.Toast;

import java.util.Random;

public class MainThread extends Thread {
    private SurfaceHolder holder;
    private Handler handler; // required for running code in the UI thread
    private boolean isRunning = false;

    Context context;
    Paint paint;

    int touchx, touchy; // x,y of touch event

    boolean touched; // true if touch happened

    private static final Object lock = new Object();

    public MainThread(SurfaceHolder surfaceHolder, Context context) {
        holder = surfaceHolder;
        this.context = context;
        handler = new Handler();
        touched = false;
    }

    public void setRunning(boolean b) {
        isRunning = b; // no need to synchronize this since this is the only
        // line of code to writes this variable
    }

    // Set the touch event x,y location and flag indicating a touch has happened
    public void setXY(int x, int y) {
        synchronized (lock) {
            touchx = x;
            touchy = y;
            this.touched = true;
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            // Lock the canvas before drawing
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                // Perform drawing operations on the canvas
                render(canvas);
                // After drawing, unlock the canvas and display it
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    // Loads graphics, etc. used in game
    private void loadData(Canvas canvas) {
        Bitmap bmp;

        int newWidth, newHeight;

        float scaleFactor;

        // Create a paint object for drawing vector graphics
        paint = new Paint();

        // Load score bar
        bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.topbar);
        // Compute size of bitmap needed (suppose want height = 10% of screen height)
        newHeight = (int)(canvas.getHeight() * 0.1f);
        // Scale it to a new size
        Assets.scorebar = Bitmap.createScaledBitmap (bmp, canvas.getWidth(), newHeight, false);
        // Delete the original
        bmp = null;
        // Load food bar
        bmp = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.food);
        // Compute size of bitmap needed (suppose want height = 10% of screen
        // height)
        newHeight = (int) (canvas.getHeight() * 0.1f);
        // Scale it to a new size
        Assets.foodbar = Bitmap.createScaledBitmap(bmp, canvas.getWidth(),newHeight, false);
        // Delete the original
        bmp = null;

        // Load bug1
        bmp = BitmapFactory.decodeResource(context.getResources(),R.drawable.bug1);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int) (canvas.getWidth() * 0.2f);
        // What was the scaling factor to get to this?
        scaleFactor = (float) newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int) (bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.bug1 = Bitmap.createScaledBitmap(bmp, newWidth, newHeight,
                false);
        // Delete the original
        bmp = null;

        // Load bug2
        bmp = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.bug2);
        // Compute size of bitmap needed (suppose want width = 20% of screen
        // width)
        newWidth = (int) (canvas.getWidth() * 0.2f);
        // What was the scaling factor to get to this?
        scaleFactor = (float) newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int) (bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.bug2 = Bitmap.createScaledBitmap(bmp, newWidth, newHeight,
                false);

        // Load bug3
        bmp = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.deadbug);
        // Compute size of bitmap needed (suppose want width = 20% of screen
        // width)
        newWidth = (int) (canvas.getWidth() * 0.2f);
        // What was the scaling factor to get to this?
        scaleFactor = (float) newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int) (bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.deadbug = Bitmap.createScaledBitmap(bmp, newWidth, newHeight,
                false);

        // Load bigbug1
        bmp = BitmapFactory.decodeResource(context.getResources(),R.drawable.bigbug1);
        // Compute size of bitmap needed (suppose want width = 20% of screen width)
        newWidth = (int) (canvas.getWidth() * 0.3f);
        // What was the scaling factor to get to this?
        scaleFactor = (float) newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int) (bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.bigbug1 = Bitmap.createScaledBitmap(bmp, newWidth, newHeight,
                false);
        // Delete the original
        bmp = null;

        // Load bigbug2
        bmp = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.bigbug2);
        // Compute size of bitmap needed (suppose want width = 20% of screen
        // width)
        newWidth = (int) (canvas.getWidth() * 0.3f);
        // What was the scaling factor to get to this?
        scaleFactor = (float) newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int) (bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.bigbug2 = Bitmap.createScaledBitmap(bmp, newWidth, newHeight,
                false);

        // Load deadbigbug
        bmp = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.deadbigbug);
        // Compute size of bitmap needed (suppose want width = 20% of screen
        // width)
        newWidth = (int) (canvas.getWidth() * 0.3f);
        // What was the scaling factor to get to this?
        scaleFactor = (float) newWidth / bmp.getWidth();
        // Compute the new height
        newHeight = (int) (bmp.getHeight() * scaleFactor);
        // Scale it to a new size
        Assets.deadbigbug = Bitmap.createScaledBitmap(bmp, newWidth, newHeight,
                false);



        // Delete the original
        bmp = null;

        // Create a Bug
        Assets.bugs = new Bug[4];
        for (int i = 0; i < Assets.bugs.length; i++) {
            Assets.bugs[i] = new Bug();
        }

        Assets.bigbugs = new Bigbug();
    }

    // Load specific background screen
    private void loadBackground(Canvas canvas, int resId) {
        // Load background
        Bitmap bmp = BitmapFactory
                .decodeResource(context.getResources(), resId);
        // Scale it to fill entire canvas
        Assets.background = Bitmap.createScaledBitmap(bmp, canvas.getWidth(),
                canvas.getHeight(), false);
        // Delete the original
        bmp = null;
    }

    private void render(Canvas canvas) {
        int i, x, y;

        switch (Assets.state) {
            case GettingReady:
                if(Assets.mp != null)
                    Assets.mp.setVolume(0,0);
                loadBackground(canvas, R.drawable.gameboard);
                // Load data and other graphics needed by game
                loadData(canvas);
                // Draw the background screen
                canvas.drawBitmap(Assets.background, 0, 0, null);
                // Start a timer
                Assets.gameTimer = System.nanoTime() / 1000000000f;
                // Go to next state
                Assets.state = Assets.GameState.Starting;
                Assets.score = 0;
                break;
            case Starting:
                canvas.drawBitmap (Assets.background, 0, 0, null);
                // Has 3 seconds elapsed?
                Assets.soundPool.play(Assets.sound_getready, 1, 1, 1, 0, 1);
                float currentTime = System.nanoTime() / 1000000000f;
                if (currentTime - Assets.gameTimer >= 3)
                    // Goto next state
                    Assets.state = Assets.GameState.Running;
                break;

            case Running:



                // Draw the background screen
                canvas.drawBitmap (Assets.background, 0, 0, null);
                // Draw the score bar at top of screen
                // Load score bar
                paint = new Paint();


                paint.setColor(Color.BLACK);
                paint.setTextSize(55);
                paint.setTextAlign(Align.LEFT);
                paint.setTypeface(Typeface.DEFAULT_BOLD);

                canvas.drawText("Total Score: " + Assets.score,
                        canvas.getHeight() * 0.1f,50, paint);
                // Draw the foodbar at bottom of screen
                canvas.drawBitmap (Assets.foodbar, 0, canvas.getHeight()-Assets.foodbar.getHeight(), null);
                // Draw one circle for each life at top right corner of screen
                // Let circle radius be 5% of width of screen
                // Draw one circle for each life at top right corner of screen
                // Let circle radius be 5% of width of screen
                Bitmap life=BitmapFactory.decodeResource(context.getResources(),R.drawable.life);
                int radius = (int)(canvas.getWidth() * 0.05f);
                int spacing = 8; // spacing in between circles
                x = canvas.getWidth()-life.getWidth() - spacing;   // coordinates for rightmost circle to draw
                y = spacing;
                for (i=0; i<Assets.livesLeft; i++) {

                    //canvas.drawCircle(x, y, radius, paint);
                    canvas.drawBitmap(life,x,y,paint);
                    x=x-life.getWidth();
                }


                // Process a touch
                if (touched) {
                    // Set touch flag to false since we are processing this touch
                    // now
                    touched = false;
                    // See if this touch killed a bugs
                    //boolean antsKilled = Assets.bugs.touched(canvas, touchx, 	touchy);
                    boolean bigBugKilled = Assets.bigbugs.touched(canvas, touchx,
                            touchy);
                    if(bigBugKilled)
                    {
                        Assets.soundPool.play(Assets.sound_squishBigBug, 1, 1, 1, 0,
                                1);
                    }

                    boolean flagbugKill = false;
                    for (i = 0; i < 4; i++) {
                        boolean bugKilled = Assets.bugs[i].touched(canvas, touchx, touchy);
                        if (bugKilled) {
                            flagbugKill = true;
                        }
                    }
                    if (flagbugKill)
                    {
                        Random r = new Random();
                        int Low = 0;
                        int High = 3;
                        int value = r.nextInt(High-Low) + Low;
                        if (value == 0) {
                            Assets.soundPool.play(Assets.sound_squish1, 1, 1, 1, 0,
                                    1);
                        } else if (value == 1) {
                            Assets.soundPool.play(Assets.sound_squish2, 1, 1, 1, 0,
                                    1);
                        } else if (value == 2) {
                            Assets.soundPool.play(Assets.sound_squish3, 1, 1, 1, 0,
                                    1);
                        } else {

                        }
                    }
                        else
                            Assets.soundPool
                                    .play(Assets.sound_thump, 1, 1, 1, 0, 1);

                }
                for (i = 0; i < 4; i++) {
                    // Draw dead bugs on screen
                    Assets.bugs[i].drawDead(canvas);
                    // Move bugs on screen
                    Assets.bugs[i].move(canvas);
                    // Bring a dead bug to life?
                    Assets.bugs[i].birth(canvas);
                }

                Assets.bigbugs.drawDead(canvas);
                Assets.bigbugs.move(canvas);

                int bigBugBirth = (int) (Math.floor(Math.random() * 25));
                if (bigBugBirth == 15) {
                    Assets.bigbugs.birth(canvas);
                }



                // Are no lives left?
                if (Assets.livesLeft == 0)
                    // Goto next state
                    Assets.state = Assets.GameState.GameEnding;
                break;
            case GameEnding:
                if(Assets.mp != null)
                    Assets.mp.setVolume(0,0);
                SharedPreferences sprefs = PreferenceManager.getDefaultSharedPreferences(Assets.context);

                int highScr = sprefs.getInt("number", 0);

                Assets.highscore = highScr;

                if (Assets.highscore < Assets.score) {
                    Editor editor = sprefs.edit();
                    editor.putInt("number", Assets.score);
                    editor.commit();
                    // Assets.highscore=Assets.score;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context, "New High Score: "+ Assets.score,
                                    Toast.LENGTH_LONG).show();
                            Assets.soundPool
                                    .play(Assets.sound_highscore, 1, 1, 1, 0, 1);

                        }
                    });

                }
                // Show a game over message
                handler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(context, "Game Over", Toast.LENGTH_SHORT)
                                .show();

                        Assets.soundPool.play(Assets.sound_gameover, 1, 1, 1, 0, 1);
                    }
                });
                // Goto next state
                Assets.state = Assets.GameState.GameOver;
                break;
            case GameOver:
                if(Assets.mp != null)
                    Assets.mp.setVolume(0,0);
                loadBackground(canvas, R.drawable.gameboard);
                // Fill the entire canvas' bitmap with 'black
                canvas.drawBitmap(Assets.background,0,0,null);
                break;
        }
    }
}
