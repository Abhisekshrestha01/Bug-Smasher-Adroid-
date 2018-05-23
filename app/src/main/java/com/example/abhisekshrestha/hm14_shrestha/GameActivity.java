//Abhisek shrestha
//Homework 14
//Android Programming


package com.example.abhisekshrestha.hm14_shrestha;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


public class GameActivity extends Activity{
    MainView v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Disable the title
        requestWindowFeature (Window.FEATURE_NO_TITLE);
        // Make full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Start the view
        v = new MainView(this);
        setContentView(v);
        //Assets.context = this;
    }

    @Override
    protected void onPause () {
        if (Assets.mp != null) {
            Assets.mp.pause();
            Assets.mp.release ();
            Assets.mp = null;
        }
        super.onPause();
        v.pause();
    }

    @Override
    protected void onResume () {

        super.onResume();
        v.resume();
        Assets.mp = null;
        Assets.mp = MediaPlayer.create(this, R.raw.gamebackground);
        if (Assets.mp != null) {
            Assets.mp.setLooping(true);
            Assets.mp.start();
        }
    }

}
