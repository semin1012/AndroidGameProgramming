package com.tukorea2020182032.firstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    // AppCompatActivity 의 onCreate를 재정의(Override)한다.
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // R = Resource, activity_main 은 xml 나타내는 정수
        // 윗줄이 없다면 부모를 재정의 하지 않은 것과 똑같음.
        // 원래 부모가 하던 일도 하고, 추가로 자신이 할 일을 쓰고 있음.


        TextView tv = findViewById(R.id.textView2);
        tv.setText("new Text");

    }
    public void onClickPush(View view) {
        // 클릭이 되거든 () 에게 알려라. - this: 나
        TextView tv = findViewById(R.id.PushMeButton);
        tv.setText("PushMe");
    }

    public void onClickAnother(View view) {
        TextView tv = findViewById(R.id.anotherButton);
        tv.setText("Another");
    }
}