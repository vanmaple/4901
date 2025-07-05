package com.example.a4501assignment;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecordsActivity extends AppCompatActivity {

    private ListView recordsListView;
    private Button backButton;
    private List<String> recordsList;
    private ArrayAdapter<String> recordsAdapter;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "GameRecords";
    private static final String RECORDS_KEY = "records";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        recordsListView = findViewById(R.id.RecordsList);
        backButton = findViewById(R.id.backButton);
        recordsList = new ArrayList<>();
        recordsAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                recordsList
        );
        recordsListView.setAdapter(recordsAdapter);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        loadRecords();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadRecords() {
        String recordsString = sharedPreferences.getString(RECORDS_KEY, "");

        if (!recordsString.isEmpty()) {
            String[] recordsArray = recordsString.split("\\|");
            for (String record : recordsArray) {
                if (!record.isEmpty()) {
                    recordsList.add(record);
                }
            }
            recordsAdapter.notifyDataSetChanged();
        }
    }

    public static void addRecord(Context context, String opponentName, boolean isWin) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String dateTime = dateFormat.format(new Date());

        String result = isWin ? "Win" : "Loss";
        String record = "Against " + opponentName + " - " + result + " (" + dateTime + ")";

        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String existingRecords = prefs.getString(RECORDS_KEY, "");

        if (existingRecords.isEmpty()) {
            editor.putString(RECORDS_KEY, record);
        } else {
            editor.putString(RECORDS_KEY, record + "|" + existingRecords);
        }

        editor.apply();
    }

}