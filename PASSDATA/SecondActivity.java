package com.example.datepicker;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Get the passed data from the intent
        String name = getIntent().getStringExtra("userName");
        String selectedFood = getIntent().getStringExtra("selectedFood");

        // Display the data in a TextView
        TextView textViewDisplay = findViewById(R.id.textViewDisplay);
        textViewDisplay.setText("Name: " + name + "\nSelected Food: " + selectedFood);
    }
}

