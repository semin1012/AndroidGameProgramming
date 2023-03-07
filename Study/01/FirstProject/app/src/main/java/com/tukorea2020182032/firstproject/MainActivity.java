package com.tukorea2020182032.firstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // AppCompatActivity 의 onCreate를 재정의(Override)한다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // R = Resource, activity_main 은 xml 나타내는 정수
        // 윗줄이 없다면 부모를 재정의 하지 않은 것과 똑같음.
        // 원래 부모가 하던 일도 하고, 추가로 자신이 할 일을 쓰고 있음.
    }
}