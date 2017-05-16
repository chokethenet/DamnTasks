package net.chokethe.damntasks.tasks;

import android.content.ContentValues;
import android.database.Cursor;

import net.chokethe.damntasks.R;
import net.chokethe.damntasks.db.TasksContract;

import java.util.Calendar;
import java.util.Date;

public class TaskTO {

    // FIXME: add more types
    public enum TaskType {
        OTHER(0), SHOP(1), CLEAN(2), WORK(3), CARE(4), PET(5);

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

        public int getTaskResource() {
            switch (this) {
                case SHOP:
                    return R.drawable.ic_task_shop;
                case CLEAN:
                    return R.drawable.ic_task_clean;
                case WORK:
                    return R.drawable.ic_task_work;
                case CARE:
                    return R.drawable.ic_task_care;
                case PET:
                    return R.drawable.ic_task_pet;
                default:
                    return R.drawable.ic_task_other;
            }
        }
    }

    private Long id;
    private boolean archived;
    private TaskType type;
    private String title;
    private String desc;
    private boolean repeat;
    private long nextDate;
    private Period period;

    public TaskTO(TaskType type, String title, String desc, boolean repeat, long nextDate, String period) {
        this.archived = false;
        this.type = type;
        this.title = title;
        this.desc = desc;
        this.repeat = repeat;
        this.nextDate = nextDate;
        this.period = period != null ? new Period(period) : null;
    }

    public TaskTO(long id, boolean archived, TaskType type, String title, String desc, boolean repeat, long nextDate, String period) {
        this(type, title, desc, repeat, nextDate, period);
        this.id = id;
        this.archived = archived;
    }

    public TaskTO(Cursor cursor) {
        this(cursor.getLong(cursor.getColumnIndex(TasksContract.TasksEntry._ID)),
                cursor.getShort(cursor.getColumnIndex(TasksContract.TasksEntry.COL_ARCHIVED)) != 0,
                TaskType.getById(cursor.getInt(cursor.getColumnIndex(TasksContract.TasksEntry.COL_TYPE))),
                cursor.getString(cursor.getColumnIndex(TasksContract.TasksEntry.COL_TITLE)),
                cursor.getString(cursor.getColumnIndex(TasksContract.TasksEntry.COL_DESC)),
                cursor.getShort(cursor.getColumnIndex(TasksContract.TasksEntry.COL_REPEAT)) != 0,
                cursor.getLong(cursor.getColumnIndex(TasksContract.TasksEntry.COL_NEXT_DATE)),
                cursor.getString(cursor.getColumnIndex(TasksContract.TasksEntry.COL_PERIOD)));
    }

    // FIXME: just for test
    @Override
    public String toString() {
        StringBuilder sb =
                new StringBuilder("TASK (").append(id).append(")\n\r")
                        .append("type: ").append(type.toString()).append(" - ")
                        .append(title).append("\n\r")
                        .append(desc).append("\n\r")
                        .append(repeat ? "repeat" : "").append("\n\r")
                        .append("nextDate: ").append(new Date(nextDate).toString()).append("\n\r")
                        .append("period: ").append(period.toString());
        return sb.toString();
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        if (id != null) contentValues.put(TasksContract.TasksEntry._ID, id);
        contentValues.put(TasksContract.TasksEntry.COL_ARCHIVED, archived);
        contentValues.put(TasksContract.TasksEntry.COL_TYPE, type.getId());
        contentValues.put(TasksContract.TasksEntry.COL_TITLE, title);
        if (desc != null) contentValues.put(TasksContract.TasksEntry.COL_DESC, desc);
        contentValues.put(TasksContract.TasksEntry.COL_REPEAT, repeat);
        contentValues.put(TasksContract.TasksEntry.COL_NEXT_DATE, nextDate);
        if (period != null)
            contentValues.put(TasksContract.TasksEntry.COL_PERIOD, period.toString());
        return contentValues;
    }

    public long getId() {
        return id;
    }

    void archive() {
        archived = true;
    }

    TaskType getType() {
        return type;
    }

    String getTitle() {
        return title;
    }

    boolean isRepeat() {
        return repeat;
    }

    long getNextDate() {
        return nextDate;
    }

    public void setNextDate(long nextDate) {
        this.nextDate = nextDate;
    }

    Period getPeriod() {
        return period;
    }

    class Period {
        int unit;
        int value;
        String period;

        Period(String period) {
            this.period = period;
            if (period.contains("w")) {
                this.unit = Calendar.DAY_OF_WEEK;
                // TODO: add contains "w" to set days of week: all"w1234567", workdays "w23456", monday and fridays "w26"
                // how to do this? how to look for the next dow?
            } else {
                if (period.contains("y")) {
                    this.unit = Calendar.YEAR;
                } else if (period.contains("m")) {
                    this.unit = Calendar.MONTH;
                } else if (period.contains("d")) {
                    this.unit = Calendar.DATE;
                }
                this.value = period.charAt(0);
            }
        }

        @Override
        public String toString() {
            return period;
        }
    }
}
