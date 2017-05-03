package net.chokethe.damntasks.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            TasksEntry.COL_PERIOD + " TEXT" +
            ") ";

    static void createTable(SQLiteDatabase db) {
        db.execSQL(TasksContract.SQL_CREATE_TASKS_TABLE);
    }

    // FIXME: set initial example tasks by language
    static void populateDefaults(SQLiteDatabase db) {
        List<TaskTO> tasks = new ArrayList();
        tasks.add(new TaskTO(TaskTO.TaskType.SHOP, "Comprar pata fregadero",
                "Comprar un hierro para hacer una pata para el fregadero",
                false, new Date(117, 5, 30).getTime(), null));
        tasks.add(new TaskTO(TaskTO.TaskType.WORK, "Arreglar enchufe",
                "Arreglar el enchufe del comedor debajo de la mesa",
                false, new Date(117, 11, 31).getTime(), null));
        tasks.add(new TaskTO(TaskTO.TaskType.CLEAN, "Limpiar ventanas",
                "Limpiar todas las ventanas de casa",
                true, new Date(117, 5, 20).getTime(), "2m"));
        tasks.add(new TaskTO(TaskTO.TaskType.CARE, "Regar plantas", null,
                true, new Date(117, 4, 4).getTime(), "3d"));
        tasks.add(new TaskTO(TaskTO.TaskType.CARE, "Vacunar perro", null,
                true, new Date(117, 4, 10).getTime(), "1y"));
        tasks.add(new TaskTO(TaskTO.TaskType.OTHER, "Cortarme el pelo", null,
                true, new Date(117, 3, 5).getTime(), "3m"));

        for (TaskTO task : tasks) {
            db.insert(TasksEntry.TABLE_NAME, null, task.toContentValues());
        }
    }

    static Cursor selectAll(SQLiteDatabase db) {
        return db.query(TasksEntry.TABLE_NAME, null, null, null, null, null, TasksEntry.COL_NEXT_DATE + " ASC");
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
