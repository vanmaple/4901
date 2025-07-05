package com.example.a4501assignment;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class RightHandActivity extends AppCompatActivity {
    private int leftHandValue;
    private String opponentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_righthand);

        // 从Intent中获取左手值和对手名称
        leftHandValue = getIntent().getIntExtra("leftHandValue", 0);
        opponentName = getIntent().getStringExtra("opponent_name");

        ImageView fiveImageView = findViewById(R.id.fiveR);
        ImageView zeroImageView = findViewById(R.id.zeroR);

        fiveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRightHandValue(5);
            }
        });

        zeroImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectRightHandValue(0);
            }
        });
    }

    private void selectRightHandValue(int value) {
        Toast.makeText(this, "Right Hand: " + value, Toast.LENGTH_SHORT).show();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("rightHandValue", value);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}