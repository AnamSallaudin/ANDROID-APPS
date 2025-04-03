package com.example.template;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private EditText etTaskNameSecond;
    private TextView tvDueDateSecond;
    private Spinner spinnerPrioritySecond;
    private Button btnPickDateSecond, btnSaveTaskSecond;
    private ListView listViewTasksSecond;
    private TaskDatabaseHelper dbHelper;
    private ArrayList<String> taskList;  // Changed to ArrayList for dynamic storage
    private ArrayAdapter<String> adapter;

    private Button btnClearTasksSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etTaskNameSecond = findViewById(R.id.etTaskNameSecond);
        tvDueDateSecond = findViewById(R.id.tvDueDateSecond);
        spinnerPrioritySecond = findViewById(R.id.spinnerPrioritySecond);
        btnPickDateSecond = findViewById(R.id.btnPickDateSecond);
        btnSaveTaskSecond = findViewById(R.id.btnSaveTaskSecond);
        listViewTasksSecond = findViewById(R.id.listViewTasksSecond);

        btnClearTasksSecond = findViewById(R.id.btnClearTasksSecond);  // Link button

        dbHelper = new TaskDatabaseHelper(this);
        taskList = new ArrayList<>();

        // Setup ListView adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        listViewTasksSecond.setAdapter(adapter);

        // Setup Priority Spinner
        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(this,
                R.array.priority_levels, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrioritySecond.setAdapter(priorityAdapter);

        // Pick Date
        btnPickDateSecond.setOnClickListener(v -> showDatePicker());

        // Save Task
        btnSaveTaskSecond.setOnClickListener(v -> saveTask());

        // Clear All Tasks
        btnClearTasksSecond.setOnClickListener(v -> clearTasks());

        // Load saved tasks
        loadTasks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_second_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item1) {
            Toast.makeText(this, "Option 1 Selected", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.item2) {
            Toast.makeText(this, "Option 2 Selected", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.item3) {
            Toast.makeText(this, "Option 3 Selected", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            tvDueDateSecond.setText(date);
        }, year, month, day);

        datePickerDialog.show();
    }

    private void saveTask() {
        String taskName = etTaskNameSecond.getText().toString().trim();
        String dueDate = tvDueDateSecond.getText().toString();
        String priority = spinnerPrioritySecond.getSelectedItem().toString();

        if (taskName.isEmpty()) {
            Toast.makeText(this, "Task Name is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dueDate.equals("Selected Date: ")) {
            Toast.makeText(this, "Please select a due date", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save task to SQLite
        dbHelper.addTask(taskName, dueDate, priority);

        // Add task to ArrayList dynamically
        String taskEntry = taskName + " - Due: " + dueDate + " - Priority: " + priority;
        taskList.add(taskEntry);

        // Notify adapter about the new item
        adapter.notifyDataSetChanged();

        // Clear input fields after saving
        etTaskNameSecond.setText("");
        tvDueDateSecond.setText("Selected Date: ");
        spinnerPrioritySecond.setSelection(0);
    }

    private void loadTasks() {
        List<String> savedTasks = dbHelper.getAllTasks();
        if (savedTasks != null) {
            taskList.addAll(savedTasks);
            adapter.notifyDataSetChanged();
        }
    }

    // Function to clear all tasks
    private void clearTasks() {
        dbHelper.clearAllTasks();  // Clear from database
        taskList.clear();  // Clear from ListView
        adapter.notifyDataSetChanged();  // Update UI
        Toast.makeText(this, "All tasks cleared!", Toast.LENGTH_SHORT).show();
    }

}
