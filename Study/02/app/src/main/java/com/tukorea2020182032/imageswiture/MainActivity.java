package com.tukorea2020182032.imageswiture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getCanonicalName();
    int indexImage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonPrev(View view) {
        Log.d(TAG, "Prev Pressed");

        if ( indexImage > 1 ) {
            indexImage -= 1;
        }

        ImageView iv = findViewById(R.id.mainImageView);;

        switch(indexImage){
            case 1:
                iv.setImageResource(R.mipmap.cat_1);
                break;
            case 2:
                iv.setImageResource(R.mipmap.cat_2);
                break;
            case 3:
                iv.setImageResource(R.mipmap.cat_3);
                break;
            case 4:
                iv.setImageResource(R.mipmap.cat_4);
                break;
            case 5:
                iv.setImageResource(R.mipmap.cat_5);
                break;
        }
    }

    public void onButtonNext(View view) {
        Log.d(TAG, "Next Pressed");

        if ( indexImage < 5 ) {
            indexImage += 1;
        }

        ImageView iv = findViewById(R.id.mainImageView);;

        switch(indexImage){
            case 1:
                iv.setImageResource(R.mipmap.cat_1);
                break;
            case 2:
                iv.setImageResource(R.mipmap.cat_2);
                break;
            case 3:
                iv.setImageResource(R.mipmap.cat_3);
                break;
            case 4:
                iv.setImageResource(R.mipmap.cat_4);
                break;
            case 5:
                iv.setImageResource(R.mipmap.cat_5);
                break;
        }
    }

}