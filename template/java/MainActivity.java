package com.example.template;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import android.content.Intent;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etName, etAge, etEmail;
    Spinner spinnerSelection;
    Button btnSave;
    ListView listViewData;
    ArrayList<String> dataList;
    ArrayAdapter<String> adapter;
    DatabaseHelper databaseHelper;
    String selectedOption = "";

    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etEmail = findViewById(R.id.etEmail);
        spinnerSelection = findViewById(R.id.spinnerSelection);
        btnSave = findViewById(R.id.btnSave);
        listViewData = findViewById(R.id.listViewData);

        databaseHelper = new DatabaseHelper(this);
        dataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listViewData.setAdapter(adapter);

        // Dummy options for the spinner
        String[] options = {"Option 1", "Option 2", "Option 3", "Option 4"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
        spinnerSelection.setAdapter(spinnerAdapter);

        toggleButton = findViewById(R.id.toggleButton);

        spinnerSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOption = options[position]; // Get selected option
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedOption = ""; // Default empty
            }
        });

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String age = etAge.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (name.isEmpty() || age.isEmpty() || email.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isInserted = databaseHelper.insertData(name, Integer.parseInt(age), email, selectedOption);
            if (isInserted) {
                Toast.makeText(MainActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                loadData(); // Refresh data
            } else {
                Toast.makeText(MainActivity.this, "Error Saving Data", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnGoToSecondActivity = findViewById(R.id.btnGoToSecondActivity);
        btnGoToSecondActivity.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

        Button btnShowToast = findViewById(R.id.btnShowToast);
        btnShowToast.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Hello! This is your Toast message!", Toast.LENGTH_SHORT).show();
        });

        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // If toggled ON
                Toast.makeText(MainActivity.this, "Toggle is ON", Toast.LENGTH_SHORT).show();
            } else {
                // If toggled OFF
                Toast.makeText(MainActivity.this, "Toggle is OFF", Toast.LENGTH_SHORT).show();
            }
        });

        loadData(); // Load existing data
    }

    private void loadData() {
        dataList.clear();
        Cursor cursor = databaseHelper.getAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
            return;
        }
        while (cursor.moveToNext()) {
            int selectionIndex = cursor.getColumnIndex("Selection");
            String selection = (selectionIndex != -1) ? cursor.getString(selectionIndex) : "N/A";

            String data = cursor.getString(1) + ", Age: " + cursor.getInt(2) + ", Email: " + cursor.getString(3) + ", Selection: " + selection;
            dataList.add(data);
        }
        adapter.notifyDataSetChanged();
    }
}
