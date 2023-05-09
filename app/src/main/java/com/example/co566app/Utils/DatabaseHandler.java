package com.example.co566app.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.co566app.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String NAME = "toDoListDatabase";
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE +
            "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, "
            + STATUS + " INTEGER)";

    private SQLiteDatabase db;

    public DatabaseHandler(Context context)
    {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        // Recreate tables
        onCreate(db);
    }

    // Open DB
    public void openDatabase()
    {
        db = this.getWritableDatabase();
    }

    // Insert task into DB
    public void insertTask(ToDoModel task)
    {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(STATUS, 0);
        db.insert(TODO_TABLE, null, cv);
    }

    // Get all tasks
    public List<ToDoModel> getAllTasks()
    {
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try
        {
            cur = db.query(TODO_TABLE, null, null, null, null,
                    null, null, null);
            if(cur != null)
            {
                if(cur.moveToFirst())
                {
                    do
                    {
                        ToDoModel task = new ToDoModel();
                        task.setId(cur.getColumnIndex(ID));
                        task.setTask(cur.getString(0));
                        task.setStatus(cur.getInt(0));
                        taskList.add(task);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally
        {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }

    // Update task status
    public void updateStatus(int id, int status)
    {
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    // Update task text
    public void updateTask(int id, String task)
    {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    // Delete task
    public void deleteTask(int id)
    {
        db.delete(TODO_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
    }
}