package com.example.template;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TaskDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TASK_NAME = "task_name";
    private static final String COLUMN_DUE_DATE = "due_date";
    private static final String COLUMN_PRIORITY = "priority";

    public TaskDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_TASKS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TASK_NAME + " TEXT, " +
                COLUMN_DUE_DATE + " TEXT, " +
                COLUMN_PRIORITY + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    public void addTask(String taskName, String dueDate, String priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, taskName);
        values.put(COLUMN_DUE_DATE, dueDate);
        values.put(COLUMN_PRIORITY, priority);

        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    public List<String> getAllTasks() {
        List<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TASKS, null, null, null, null, null, COLUMN_ID + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String task = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_NAME)) +
                        " - Due: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DUE_DATE)) +
                        " - Priority: " + cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY));
                taskList.add(task);
            } while (cursor.moveToNext());

            cursor.close();
        }
        db.close();
        return taskList;
    }

    public void clearAllTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM tasks");  // Delete all records from the table
        db.close();
    }


}
