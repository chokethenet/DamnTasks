package net.chokethe.damntasks.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class TasksContract {
    private TasksContract() {
    }

    public class TasksEntry implements BaseColumns {
        static final String TABLE_NAME = "tasks";
        public static final String COL_TYPE = "type";
        public static final String COL_TITLE = "title";
        public static final String COL_DESC = "desc"; // optional
        public static final String COL_REPEAT = "repeat";
        public static final String COL_NEXT_DATE = "nextDate"; // repeat
        public static final String COL_PERIOD = "period"; // !repeat
    }

    private static final String SQL_CREATE_TASKS_TABLE = "CREATE TABLE " + TasksEntry.TABLE_NAME + " (" +
            TasksEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TasksEntry.COL_TYPE + " INTEGER NOT NULL, " +
            TasksEntry.COL_TITLE + " TEXT NOT NULL, " +
            TasksEntry.COL_DESC + " TEXT, " +
            TasksEntry.COL_REPEAT + " INTEGER NOT NULL, " +
            TasksEntry.COL_NEXT_DATE + " INTEGER NOT NULL, " +
            TasksEntry.COL_PERIOD + " INTEGER" +
            ") ";

    static void createTable(SQLiteDatabase db) {
        db.execSQL(TasksContract.SQL_CREATE_TASKS_TABLE);
    }

//    private static final int[][] DEFAULT_BLINDS = {
//            {5, 10, 30}, {10, 25, 30}, {25, 50, 20}, {50, 75, 20}, {75, 100, 20},
//            {100, 150, 15}, {125, 200, 15}, {150, 250, 15}, {200, 350, 10}, {250, 500, 10}};

    static void populateDefaults(SQLiteDatabase db) {
        // FIXME: insert mock data
//        for (int[] DEFAULT_BLIND : DEFAULT_BLINDS) {
//            ContentValues insertValues = new ContentValues();
//            insertValues.put(TasksEntry.COLUMN_SMALL_BLIND, DEFAULT_BLIND[0]);
//            insertValues.put(TasksEntry.COLUMN_BIG_BLIND, DEFAULT_BLIND[1]);
//            insertValues.put(TasksEntry.COLUMN_RISE_TIME, DEFAULT_BLIND[2]);
//            db.insert(TasksEntry.TABLE_NAME, null, insertValues);
//        }
    }

    static Cursor selectAll(SQLiteDatabase db) {
        return db.query(TasksEntry.TABLE_NAME, null, null, null, null, null, TasksEntry.COL_NEXT_DATE + " DESC");
    }

    static void update(SQLiteDatabase db, ContentValues contentValues) {
        db.update(TasksEntry.TABLE_NAME, contentValues, TasksEntry._ID + "=" + contentValues.getAsString(TasksEntry._ID), null);
    }

    static long insert(SQLiteDatabase db, ContentValues contentValues) {
        return db.insert(TasksEntry.TABLE_NAME, null, contentValues);
    }

    static int delete(SQLiteDatabase db, long id) {
        return db.delete(TasksEntry.TABLE_NAME, TasksEntry._ID + "=" + id, null);
    }
}
