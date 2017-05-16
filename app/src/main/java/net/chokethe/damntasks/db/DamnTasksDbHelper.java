package net.chokethe.damntasks.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.chokethe.damntasks.tasks.TaskBO;
import net.chokethe.damntasks.tasks.TaskTO;

public class DamnTasksDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "damntasks.db";
    private static final int DATABASE_VERSION = 1;

    public DamnTasksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        TasksContract.createTable(db);
    }

    // FIXME: just for test
    public void reset() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS tasks");
        TasksContract.createTable(db);
        TasksContract.populateDefaults(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Nothing to do...
    }

    public Cursor getAllTasks() {
        return TasksContract.selectAll(getReadableDatabase());
    }

    public TaskTO getTaskById(long id) {
        return TasksContract.selectById(getReadableDatabase(), id);
    }

    public void updateTask(TaskTO task) {
        TasksContract.update(getWritableDatabase(), task.toContentValues());
    }

    public void insertTask(TaskTO task) {
        TasksContract.insert(getWritableDatabase(), task.toContentValues());
    }

//    public void deleteTask(long id) {
//        TasksContract.delete(getWritableDatabase(), id);
//    }

    public int archiveOrRepeatTask(long id) {
        TaskTO task = getTaskById(id);
        int msg = TaskBO.archiveOrRepeat(task);
        TasksContract.update(getWritableDatabase(), task.toContentValues());
        // TODO: send to ws
        return msg;
    }
}
