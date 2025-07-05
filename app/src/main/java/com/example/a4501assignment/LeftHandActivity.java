package com.example.a4501assignment;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class LeftHandActivity extends AppCompatActivity {
    private String opponentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lefthand);

        ImageView fiveImageView = findViewById(R.id.fiveL);
        ImageView zeroImageView = findViewById(R.id.zeroL);
        opponentName = getIntent().getStringExtra("opponent_name");

        fiveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLeftHandValue(5);
            }
        });

        zeroImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLeftHandValue(0);
            }
        });
    }


    private void selectLeftHandValue(int value) {
        Toast.makeText(this, "Left Hand: " + value, Toast.LENGTH_SHORT).show();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("leftHandValue", value);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}