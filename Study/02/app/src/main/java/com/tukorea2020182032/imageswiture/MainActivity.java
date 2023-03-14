package com.tukorea2020182032.imageswiture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getCanonicalName();
    int page = 1;
    private static int[] resIds = new int[]{
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
        if (page < 1 || page > resIds.length ) return;
        int resId = resIds[page - 1];
        ImageView iv = findViewById(R.id.mainImageView);
        iv.setImageResource(R.mipmap.cat_2);
        iv.setImageResource(resId);
        TextView tv = findViewById(R.id.PageTextView);
        tv.setText(page + " / " + resIds.length);
        this.page = page;
    }
}