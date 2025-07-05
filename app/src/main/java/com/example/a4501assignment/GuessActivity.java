package com.example.a4501assignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GuessActivity extends AppCompatActivity {
    private ListView answerListView;
    private ArrayList<String> answerList;
    private int leftHandValue;
    private int rightHandValue;
    private String opponentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        leftHandValue = getIntent().getIntExtra("leftHandValue", 0);
        rightHandValue = getIntent().getIntExtra("rightHandValue", 0);
        opponentName = getIntent().getStringExtra("opponent_name"); // 获取对手名称

        answerListView = findViewById(R.id.answerList);
        answerList = new ArrayList<>();

        answerList.add("0");
        answerList.add("5");
        answerList.add("10");
        answerList.add("15");
        answerList.add("20");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                answerList
        );
        answerListView.setAdapter(adapter);

        answerListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedanswer = answerList.get(position);
            showConfirmationDialog(selectedanswer);
        });
    }

    private void showConfirmationDialog(String answer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm selection")
                .setMessage("Are you sure you want to choose " + answer + " as your answer?")
                .setPositiveButton("Confirm", (dialog, which) -> {
                    Toast.makeText(
                            GuessActivity.this,
                            "You have chosen " + answer + " as your answer",
                            Toast.LENGTH_SHORT
                    ).show();
                    startGameWithanswer(answer);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    private void startGameWithanswer(String answer) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("answer", Integer.parseInt(answer));
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}