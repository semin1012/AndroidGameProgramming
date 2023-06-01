package kr.ac.tukorea.ge.DontStop.DontStop.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.DontStop.DontStop.R;
import kr.ac.tukorea.ge.DontStop.DontStop.databinding.ActivityTitleBinding;

public class TitleActivity extends AppCompatActivity {

    private static final int MAX_STAGE = 3;
    private static final String TAG = TitleActivity.class.getSimpleName();
    private int stage;
    private ActivityTitleBinding binding;
    private ImageButton outputTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTitleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        outputTextView = findViewById(R.id.startButton);
        GameStart();
    }

    private void GameStart() {
    }

    public void onBtnGameStart(View view) {
        outputTextView.setImageResource(R.mipmap.title_button_click);
        Log.d(TAG, "Starting game stage: " + 1);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onBtnNextStage(View view) {
        //setStage(stage + 1);
    }
    public void onBtnStartGame(View view) {
    }
}