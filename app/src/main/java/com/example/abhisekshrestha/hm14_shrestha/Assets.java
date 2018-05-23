//Abhisek shrestha
//Homework 14
//Android Programming

package com.example.abhisekshrestha.hm14_shrestha;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.SoundPool;

// Contain global data items
public class Assets {
    static Bitmap background;
    static Bitmap foodbar;
    static Bitmap bug1;
    static Bitmap bug2;
    static Bitmap deadbug;
    static Bitmap bigbug1;
    static Bitmap bigbug2;
    static Bitmap deadbigbug;
    static Bitmap scorebar;
    static Bitmap life;
    static int score;
    static Context context;
    static int highscore;

    // States of the Game Screen
    enum GameState {
        GettingReady,	// play "get ready" sound and start timer, goto next state
        Starting,		// when 3 seconds have elapsed, goto next state
        Running, 		// play the game, when livesLeft == 0 goto next state
        GameEnding,	// show game over message
        GameOver,		// game is over, wait for any Touch and go back to title activity screen
    };

    static GameState state;		// current state of the game
    static float gameTimer;	// in seconds
    static int livesLeft;		// 0-3

    static SoundPool soundPool;
    static int sound_getready;
    static int sound_gameover;
    static int sound_squish1;
    static int sound_squish2;
    static int sound_squish3;
    static int sound_squishBigBug;
    static int sound_thump;
    static int sound_scream;
    static int sound_highscore;
    static MediaPlayer mp;


    static Bug[] bugs;
    static Bigbug bigbugs;
}