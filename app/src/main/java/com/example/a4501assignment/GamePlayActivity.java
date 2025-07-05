package com.example.a4501assignment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a4501assignment.Network;

public class GamePlayActivity extends AppCompatActivity {
    private static final int REQUEST_LEFT_HAND = 1;
    private static final int REQUEST_RIGHT_HAND = 2;
    private static final int REQUEST_GUESS = 3;

    private int currentRound = 1;
    private int playerStreak = 0;
    private int computerStreak = 0;
    private final int REQUIRED_STREAK = 2;
    private String opponentName;
    private String apiUrl = "https://assign-mobileasignment-ihudikcgpf.cn-hongkong.fcapp.run";
    private boolean isRoundActive = false;
    private int leftHandValue = 0;
    private int rightHandValue = 0;
    private int answer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        opponentName = getIntent().getStringExtra("opponent_name");
        if (opponentName == null) {
            opponentName = "Computer";
        }

        Button gameButton = findViewById(R.id.btn);
        gameButton.setOnClickListener(v -> {
            if (!isRoundActive) {
                if (gameButton.getText().equals("Finish!")) {
                    finishGame();
                } else {
                    startNewRound();
                }
            }
        });

        startNewRound();
    }

    private void startNewRound() {
        // 在一轮开始时更新 UI，显示当前轮数
        updateUI();
        isRoundActive = true;

        Intent intent = new Intent(this, LeftHandActivity.class);
        intent.putExtra("opponent_name", opponentName);
        startActivityForResult(intent, REQUEST_LEFT_HAND);
    }

    private void finishGame() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("opponent_name", opponentName);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_LEFT_HAND) {
            if (resultCode == RESULT_OK) {
                leftHandValue = data.getIntExtra("leftHandValue", 0);

                Intent intent = new Intent(this, RightHandActivity.class);
                intent.putExtra("leftHandValue", leftHandValue);
                intent.putExtra("opponent_name", opponentName);
                startActivityForResult(intent, REQUEST_RIGHT_HAND);
            } else {
                isRoundActive = false;
                updateUI();
            }
        } else if (requestCode == REQUEST_RIGHT_HAND) {
            if (resultCode == RESULT_OK) {
                rightHandValue = data.getIntExtra("rightHandValue", 0);

                Intent intent = new Intent(this, GuessActivity.class);
                intent.putExtra("leftHandValue", leftHandValue);
                intent.putExtra("rightHandValue", rightHandValue);
                intent.putExtra("opponent_name", opponentName);
                startActivityForResult(intent, REQUEST_GUESS);
            } else {
                isRoundActive = false;
                updateUI();
            }
        } else if (requestCode == REQUEST_GUESS) {
            if (resultCode == RESULT_OK) {
                answer = data.getIntExtra("answer", 0);

                updateUI();
                fetchAIData();
            } else {
                isRoundActive = false;
                updateUI();
            }
        }
    }

    private void fetchAIData() {
        Network.fetchData(apiUrl, new Network.ApiCallback() {
            @Override
            public void onSuccess(int left, int right, int guess) {
                runOnUiThread(() -> {
                    updateAIView(left, right, guess);

                    int actualSum = leftHandValue + rightHandValue + left + right;

                    boolean isPlayerWinner = currentRound % 2 == 1
                            ? (answer == actualSum)
                            : (guess != actualSum);

                    String resultText = isPlayerWinner ?
                            "You won this round!" : "You lost this round!";
                    Toast.makeText(GamePlayActivity.this, resultText, Toast.LENGTH_SHORT).show();

                    handleRoundResult(isPlayerWinner);
                });
            }

            @Override
            public void onError(String errorMessage) {
                runOnUiThread(() -> {
                    Toast.makeText(GamePlayActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                    isRoundActive = false;
                    updateUI();
                });
            }
        });
    }

    private void handleRoundResult(boolean isPlayerWinner) {
        if (isPlayerWinner) {
            playerStreak++;
            computerStreak = 0;
        } else {
            computerStreak++;
            playerStreak = 0;
        }

        if (playerStreak >= REQUIRED_STREAK || computerStreak >= REQUIRED_STREAK) {
            showFinalResult();
        } else {
            // 一轮结束后递增 currentRound
            currentRound++;
            isRoundActive = false;
            updateUI();
        }
    }

    private void updateUI() {
        TextView roundTextView = findViewById(R.id.roundTextView);
        roundTextView.setText("Round " + currentRound);

        TextView userHand = findViewById(R.id.userHand);
        userHand.setText(currentRound % 2 == 1
                ? "Your Hands, Your Guess: " + answer
                : "Your Hands");

        updatePlayerView();

        Button nextRoundButton = findViewById(R.id.btn);

        if (isRoundActive) {
            nextRoundButton.setText("Processing...");
            nextRoundButton.setEnabled(false);
        } else if (currentRound > 1) {
            nextRoundButton.setText("Finish!");
            nextRoundButton.setEnabled(true);
        } else {
            nextRoundButton.setText("Continue");
            nextRoundButton.setEnabled(true);
        }
    }

    private void updatePlayerView() {
        ImageView leftHandImage = findViewById(R.id.userLeft);
        ImageView rightHandImage = findViewById(R.id.userRight);

        if (leftHandValue == 0) {
            leftHandImage.setImageResource(R.drawable.stone);
        } else if (leftHandValue == 5) {
            leftHandImage.setImageResource(R.drawable.paper);
        }

        if (rightHandValue == 0) {
            rightHandImage.setImageResource(R.drawable.stone);
        } else if (rightHandValue == 5) {
            rightHandImage.setImageResource(R.drawable.paper);
        }
    }

    private void updateAIView(int left, int right, int guess) {
        TextView AiHand = findViewById(R.id.AiHand);
        AiHand.setText(currentRound % 2 == 1
                ? opponentName + "'s Hands"
                : opponentName + "'s Hands, " + opponentName + "'s Guess: " + guess);

        ImageView AileftHandImage = findViewById(R.id.AiLeft);
        ImageView AirightHandImage = findViewById(R.id.AiRight);

        if (left == 0) {
            AileftHandImage.setImageResource(R.drawable.stone139);
        } else if (left == 5) {
            AileftHandImage.setImageResource(R.drawable.paper139);
        }

        if (right == 0) {
            AirightHandImage.setImageResource(R.drawable.stone139);
        } else if (right == 5) {
            AirightHandImage.setImageResource(R.drawable.paper139);
        }
    }

    private void showFinalResult() {
        String resultMessage = playerStreak >= REQUIRED_STREAK
                ? "Congratulations! You won by winning " + REQUIRED_STREAK + " consecutive rounds!"
                : "Oh no! You lost. Computer won " + REQUIRED_STREAK + " consecutive rounds.";

        boolean isWin = playerStreak >= REQUIRED_STREAK;
        RecordsActivity.addRecord(this, opponentName, isWin);

        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage(resultMessage)
                .setPositiveButton("Play Again", (dialog, which) -> resetGame())
                .setNegativeButton("Exit", (dialog, which) -> finish())
                .show();
    }

    private void resetGame() {
        currentRound = 1;
        playerStreak = 0;
        computerStreak = 0;
        isRoundActive = false;
        leftHandValue = 0;
        rightHandValue = 0;
        answer = 0;
        updateUI();
    }
}