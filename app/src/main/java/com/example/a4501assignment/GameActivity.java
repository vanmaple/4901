package com.example.a4501assignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private static final int REQUEST_LEFT_HAND = 1;
    private ListView opponentListView;
    private ArrayList<String> opponentList;
    private String opponentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        opponentListView = findViewById(R.id.opponentList);
        Button backButton = findViewById(R.id.backButton);
        opponentList = new ArrayList<>();

        opponentList.add("Peter");
        opponentList.add("John");
        opponentList.add("Mary");
        opponentList.add("David");
        opponentList.add("Alan");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                opponentList
        );
        opponentListView.setAdapter(adapter);

        backButton.setOnClickListener(v -> finish());

        opponentListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedOpponent = opponentList.get(position);
            showConfirmationDialog(selectedOpponent);
        });
    }

    private void showConfirmationDialog(String opponentName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm selection")
                .setMessage("Are you sure you want to choose " + opponentName + " as your opponent?")
                .setPositiveButton("Confirm", (dialog, which) -> {
                    Toast.makeText(
                            GameActivity.this,
                            "You have chosen " + opponentName + " as your opponent",
                            Toast.LENGTH_SHORT
                    ).show();
                    this.opponentName = opponentName;
                    startGameWithOpponent(opponentName);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    private void startGameWithOpponent(String opponentName) {
        Intent intent = new Intent(GameActivity.this, GamePlayActivity.class);
        intent.putExtra("opponent_name", opponentName);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LEFT_HAND && resultCode == RESULT_OK) {
            Intent intent = new Intent(GameActivity.this, LeftHandActivity.class);
            intent.putExtra("opponent_name", opponentName);
            startActivity(intent);
            finish();
        }
    }
}