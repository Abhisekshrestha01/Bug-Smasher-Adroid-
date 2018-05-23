//Abhisek shrestha
//Homework 14
//Android Programming

package com.example.abhisekshrestha.hm14_shrestha;


import android.graphics.Canvas;

public class Bug {

    // States of a bugs
    enum bugState {
        Dead,
        ComingBackToLife,
        Alive, 			// in the game
        DrawDead,			// draw dead body on screen
    };

    bugState state;			// current state of bugs
    int x,y;				// location on screen (in screen coordinates)
    int tox, toy;           // location bug is moving to

    double speed;			// speed of bugs (in pixels per second)
    double reducedspeed;
    // All times are in seconds
    float timeToBirth;		// # seconds till birth
    float startBirthTimer;	// starting timestamp when decide to be born
    float deathTime;		// time of death
    float animateTimer;		// used to move and animate the bugs
    float dirx, diry;       // direction the bug is moving

    // bugs starts not alive
    public Bug() {
        state = bugState.Dead;
    }
    int imagestate = 1;		// keep track of current image displayed

    // bugs birth processing
    public void birth (Canvas canvas) {
        // Bring a bugs to life?
        if (state == bugState.Dead) {
            // Set it to coming alive
            state = bugState.ComingBackToLife;
            // Set a random number of seconds before it comes to life
            timeToBirth = (float)Math.random () * 5;
            // Note the current time
            startBirthTimer = System.nanoTime() / 1000000000f;
        }
        // Check if bugs is alive yet
        else if (state == bugState.ComingBackToLife) {
            float curTime = System.nanoTime() / 1000000000f;
            // Has birth timer expired?
            if (curTime - startBirthTimer >= timeToBirth) {
                // If so, then bring bugs to life
                state = bugState.Alive;
                // Set bugs starting location at top of screen
                x = (int)(Math.random() * canvas.getWidth());
                // Keep entire bugs on screen
                if (x < Assets.bug1.getWidth()/2)
                    x = Assets.bug1.getWidth()/2;
                else if (x > canvas.getWidth() - Assets.bug1.getWidth()/2)
                    x = canvas.getWidth() - Assets.bug1.getWidth()/2;
                y = 0;
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
                // Set speed of this bugs
                speed = canvas.getHeight() / 4; // no faster than 1/4 a screen per second
                // subtract a random amount off of this so some antss are a little slower
                reducedspeed = (int)(Math.random() * (speed / 2)); //calculating random amount not less than half
                speed -= reducedspeed;
                // Record timestamp of this bugs being born
                animateTimer = curTime;
            }
        }
    }

    // bugs movement processing
    public void move (Canvas canvas) {
        // Make sure this bugs is alive
        if (state == bugState.Alive) {
            // Get elapsed time since last call here
            float curTime = System.nanoTime() / 1000000000f;
            float elapsedTime = curTime - animateTimer;
            animateTimer = curTime;
            // Compute the amount of pixels to move (vertically down the screen)
            //y += (speed * elapsedTime);
            x += (dirx * speed * elapsedTime);
            y += (diry * speed * elapsedTime);

            // Draw bugs on screen
            imagestate = (imagestate + 1) % 4;

            switch(imagestate){
                case 0:
                    canvas.drawBitmap(Assets.bug1, x,  y, null);
                    break;
                case 1:
                    canvas.drawBitmap(Assets.bug2, x,  y, null);
                    break;
                default:
                    canvas.drawBitmap(Assets.bug1, x,  y, null);
                    break;
            }
            // Has it reached the bottom of the screen?
            if (y >= toy) {
                // Kill the bugs
                state = bugState.Dead;
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

    // Process touch to see if kills bugs - return true if bugs killed
    public boolean touched (Canvas canvas, int touchx, int touchy) {
        boolean touched = false;
        // Make sure this bugs is alive
        if (state == bugState.Alive) {
            // Compute distance between touch and center of bugs
            float dis = (float)(Math.sqrt ((touchx - x) * (touchx - x) + (touchy - y) * (touchy - y)));
            // Is this close enough for a kill?
            if (dis <= Assets.bug1.getWidth()*0.88f) {
                state = bugState.DrawDead;	// need to draw dead body on screen for a while
                touched = true;
                // Record time of death
                deathTime = System.nanoTime() / 1000000000f;
                Assets.score ++;
            }
        }
        return (touched);
    }

    // Draw dead Bug body on screen, if needed
    public void drawDead (Canvas canvas) {
        if (state == bugState.DrawDead) {
            canvas.drawBitmap(Assets.deadbug, x,  y, null);
            // Get time since death
            float curTime = System.nanoTime() / 1000000000f;
            float timeSinceDeath = curTime - deathTime;
            // Drawn dead body long enough (3 seconds) ?
            if (timeSinceDeath > 3)
                state = bugState.Dead;


        }
    }

}
