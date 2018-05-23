//Abhisek shrestha
//Homework 14
//Android Programming

package com.example.abhisekshrestha.hm14_shrestha;



import android.graphics.Canvas;

public class Bigbug {

    // States of a Bigbug
    enum BigBugState {
        Dead,
        ComingBackToLife,
        Alive, 			// in the game
        DrawDead,			// draw dead body on screen
    }

    BigBugState state;			// current state of Bigbug
    int x,y;				// location on screen (in screen coordinates)
    int tox, toy;           // location bug is moving to

    double speed;			// speed of Bigbug (in pixels per second)
    double reducedspeed;
    // All times are in seconds
    float timeToBirth;		// # seconds till birth
    float startBirthTimer;	// starting timestamp when decide to be born
    float deathTime;		// time of death
    float animateTimer;		// used to move and animate the Bigbug
    float dirx, diry;       // direction the bug is moving
    public static int touchcount=0;

    // Bigbug starts not alive
    public Bigbug() {
        state = BigBugState.Dead;
    }
    int imagestate = 1;		// keep track of current image displayed

    // Bigbug birth processing
    public void birth (Canvas canvas) {
        // Bring a Bigbug to life?
        if (state == BigBugState.Dead) {
            // Set it to coming alive
            state = BigBugState.ComingBackToLife;
            // Set a random number of seconds before it comes to life
            timeToBirth = (float)Math.random () * 5;
            // Note the current time
            startBirthTimer = System.nanoTime() / 1000000000f;
        }
        // Check if Bigbug is alive yet
        else if (state == BigBugState.ComingBackToLife) {
            float curTime = System.nanoTime() / 1000000000f;
            // Has birth timer expired?
            if (curTime - startBirthTimer >= timeToBirth) {
                // If so, then bring Bigbug to life
                state = BigBugState.Alive;
                // Set Bigbug starting location at top of screen
                x = (int)(Math.random() * canvas.getWidth());
                // Keep entire Bigbug on screen
                if (x < Assets.bigbug1.getWidth()/2)
                    x = Assets.bigbug1.getWidth()/2;
                else if (x > canvas.getWidth() - Assets.bigbug1.getWidth()/2)
                    x = canvas.getWidth() - Assets.bigbug1.getWidth()/2;
                y = 0;
                // Set speed of this Bigbug
                speed = canvas.getHeight() / 4; // no faster than 1/4 a screen per second
                // subtract a random amount off of this so some BigAnts are a little slower
                reducedspeed = (int)(Math.random() * (speed / 2)); //calculating random amount not less than half
                speed -= reducedspeed;
                // Record timestamp of this Bigbug being born
                animateTimer = curTime;
            }
        }
    }

    // Bigbug movement processing
    public void move (Canvas canvas) {
        // Make sure this Bigbug is alive
        if (state == BigBugState.Alive) {
            // Get elapsed time since last call here
            float curTime = System.nanoTime() / 1000000000f;
            float elapsedTime = curTime - animateTimer;
            animateTimer = curTime;
            // Compute the amount of pixels to move (vertically down the screen)
            //y += (speed * elapsedTime);
            x += (dirx * speed * elapsedTime);
            y += (diry * speed * elapsedTime);

            // Draw Bigbug on screen
            imagestate = (imagestate + 1) % 4;

            switch(imagestate){
                case 0:
                    canvas.drawBitmap(Assets.bigbug1, x,  y, null);
                case 1:
                    canvas.drawBitmap(Assets.bigbug2, x,  y, null);
                default:
                    canvas.drawBitmap(Assets.bigbug1, x,  y, null);
            }
            // Has it reached the bottom of the screen?
            if (y >= canvas.getHeight()) {
                // Kill the Bigbug
                state = BigBugState.Dead;
                // Subtract 1 life
                Assets.livesLeft--;
            }
            // Has it reached the to location (or passed it)?
            else if (y >= toy) {
                // Ccmpute a to location
                tox = (int)(Math.random() * canvas.getWidth());
                toy = (int)(Math.random() * (canvas.getHeight()-y-1) + y-1 );
                // Compute the direction the bug is moving
                dirx = tox - x;
                diry = toy - y;
                // Compute the length of this direction vector
                float len = (float)(Math.sqrt(dirx * dirx + diry * diry));
                // Normalize this direction vector
                dirx /= len;
                diry /= len;
            }
        }
    }

    // Process touch to see if kills Bigbug - return true if Bigbug killed
    public boolean touched (Canvas canvas, int touchx, int touchy) {
        boolean touched = false;
        // Make sure this Bigbug is alive
        if (state == BigBugState.Alive) {
            // Compute distance between touch and center of Bigbug
            float dis = (float)(Math.sqrt ((touchx - x) * (touchx - x) + (touchy - y) * (touchy - y)));
            // Is this close enough for a kill?
            if (dis <= Assets.bigbug1.getWidth()*0.75f) {
                touchcount++;
                if(touchcount == 4){
                    state = BigBugState.DrawDead;	// need to draw dead body on screen for a while
                    touched = true;
                    // Record time of death
                    deathTime = System.nanoTime() / 1000000000f;
                    Assets.score+=10;
                }



            }
        }
        return (touched);
    }

    // Draw dead Bigbug body on screen, if needed
    public void drawDead (Canvas canvas) {
        if (state == BigBugState.DrawDead) {
            canvas.drawBitmap(Assets.deadbigbug, x,  y, null);
            // Get time since death
            float curTime = System.nanoTime() / 1000000000f;
            float timeSinceDeath = curTime - deathTime;
            // Drawn dead body long enough (3 seconds) ?
            if (timeSinceDeath > 3)
                state = BigBugState.Dead;


        }
    }
}
