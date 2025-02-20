package com.example.datepicker;

import android.os.Bundle;
import android.app.DatePickerDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ensure your XML file is named activity_main.xml

        // Find the button by its ID
        Button selectDateButton = findViewById(R.id.button_select_date);

        // Set a click listener for the button
        selectDateButton.setOnClickListener(v -> {
            // Get the current date
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Show the DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    MainActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        // Update the button text with the selected date
                        String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        selectDateButton.setText("Selected Date: " + selectedDate);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });
    }
}
