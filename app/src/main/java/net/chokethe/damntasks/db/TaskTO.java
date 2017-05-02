package net.chokethe.damntasks.db;

import android.content.ContentValues;

public class TaskTO {

    private enum TaskType {
        OTHER(0), SHOP(1), CLEAN(2), WORK(3), CARE(4);

        private int id;

        TaskType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static TaskType getById(int id) {
            for (TaskType e : values()) {
                if (e.id == id) return e;
            }
            return null;
        }
    }

    private Long id;
    private TaskType type;
    private String title;
    private String desc;
    private boolean repeat;
    private long nextDate;
    private long period;

    private TaskTO() {
    }

    public TaskTO(TaskType type, String title, String desc, boolean repeat, long nextDate, long period) {
        this.type = type;
        this.title = title;
        this.desc = desc;
        this.repeat = repeat;
        this.nextDate = nextDate;
        this.period = period;
    }

    public TaskTO(Long id, TaskType type, String title, String desc, boolean repeat, long nextDate, long period) {
        this(type, title, desc, repeat, nextDate, period);
        this.id = id;
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TasksContract.TasksEntry._ID, id);
        contentValues.put(TasksContract.TasksEntry.COL_TYPE, type.getId());
        contentValues.put(TasksContract.TasksEntry.COL_TITLE, title);
        contentValues.put(TasksContract.TasksEntry.COL_DESC, desc);
        contentValues.put(TasksContract.TasksEntry.COL_REPEAT, repeat);
        contentValues.put(TasksContract.TasksEntry.COL_NEXT_DATE, nextDate);
        contentValues.put(TasksContract.TasksEntry.COL_PERIOD, period);
        return contentValues;
    }

    public long getId() {
        return id;
    }

    public TaskType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public long getNextDate() {
        return nextDate;
    }

    public long getPeriod() {
        return period;
    }
}
