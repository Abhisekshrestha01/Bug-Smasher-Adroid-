//Abhisek shrestha
//Homework 14
//Android Programming

package com.example.abhisekshrestha.hm14_shrestha;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button b1;
    Button b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Assets.context = this;

        b1=(Button) findViewById(R.id.button1);
        b2=(Button) findViewById(R.id.button2);

        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(new Intent(MainActivity.this, PrefsActivity.class));
            }
        });
    }


    @Override
    public void onBackPressed () {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //MainActivity.this.finish();

                        finish();

                    }
                })
                .setNegativeButton("No", null)
                .show();

    }


}

