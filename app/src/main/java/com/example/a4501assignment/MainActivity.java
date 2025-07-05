package com.example.a4501assignment;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button playButton = findViewById(R.id.playButton);
        Button recordsButton = findViewById(R.id.recordsButton);
        Button closeButton = findViewById(R.id.closeButton);

        playButton.setOnClickListener(v -> {

            v.setPressed(true);

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                v.setPressed(false);
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }, 100);
        });

        recordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecordsActivity.class);
                startActivity(intent);
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}