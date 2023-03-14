package com.tukorea2020182032.imageswiture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getCanonicalName();

    private ImageView mainImageView;
    private TextView pageTextView;
    private Button prevButton;
    private Button nextButton;

    int page = 1;
    private static final int[] IMG_RES_IDS = new int[]{
            // Build 할 때 생성 되어서 한 번도 안 바뀔 것이니 private 를 붙이고,
            // 변하지 않도록 static 을 붙이고, 멤버 함수 바깥에 위치하도록 한다. (최초 한 번만 생성)
            R.mipmap.cat_1,
            R.mipmap.cat_2,
            R.mipmap.cat_3,
            R.mipmap.cat_4,
            R.mipmap.cat_5,
            R.mipmap.cat_6,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainImageView = findViewById(R.id.mainImageView);
        pageTextView = findViewById(R.id.PageTextView);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        pageTextView.setText(page + " / " + IMG_RES_IDS.length);

        prevButton.setEnabled( page > 1 );
        nextButton.setEnabled( page < IMG_RES_IDS.length);
    }

    public void onButtonPrev(View view) {
        Log.d(TAG, "Prev Pressed");
        setPage(page - 1);
    }

    public void onButtonNext(View view) {
        Log.d(TAG, "Next Pressed");
        setPage(page + 1);
    }

    private void setPage(int page) {
        if (page < 1 || page > IMG_RES_IDS.length ) return;
        int resId = IMG_RES_IDS[page - 1];
        mainImageView.setImageResource(R.mipmap.cat_2);
        mainImageView.setImageResource(resId);
        pageTextView.setText(page + " / " + IMG_RES_IDS.length);

        prevButton.setEnabled( page > 1 );
        nextButton.setEnabled( page < IMG_RES_IDS.length);
        this.page = page;
    }
}