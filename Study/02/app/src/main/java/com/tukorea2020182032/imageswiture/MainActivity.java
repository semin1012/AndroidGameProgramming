package com.tukorea2020182032.imageswiture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonPrev(View view) {
        Log.d("MainActivity", "Prev Pressed");
    }

    public void onButtonNext(View view) {
        Log.d("MainActivity", "Next Pressed");
    }

}