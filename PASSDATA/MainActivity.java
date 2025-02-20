package com.example.datepicker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private Spinner spinnerFoodOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        editTextName = findViewById(R.id.editTextName);
        spinnerFoodOptions = findViewById(R.id.spinnerFoodOptions);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);

        // On click, pass data to the next page
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered name and selected dropdown item
                String name = editTextName.getText().toString();
                String selectedFood = spinnerFoodOptions.getSelectedItem().toString();

                // Pass data to the next activity
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("userName", name);
                intent.putExtra("selectedFood", selectedFood);
                startActivity(intent);
            }
        });
    }
}
