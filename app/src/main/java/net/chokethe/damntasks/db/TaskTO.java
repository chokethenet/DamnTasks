package net.chokethe.damntasks.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;

import net.chokethe.damntasks.R;

import java.util.Calendar;
import java.util.Date;

public class TaskTO {

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
    private TaskType type;
    private String title;
    private String desc;
    private boolean repeat;
    private long nextDate;
    private String period;

    public TaskTO(TaskType type, String title, String desc, boolean repeat, long nextDate, String period) {
        this.type = type;
        this.title = title;
        this.desc = desc;
        this.repeat = repeat;
        this.nextDate = nextDate;
        this.period = period;
    }

    public TaskTO(long id, TaskType type, String title, String desc, boolean repeat, long nextDate, String period) {
        this(type, title, desc, repeat, nextDate, period);
        this.id = id;
    }

    public TaskTO(Cursor cursor) {
        this(cursor.getLong(cursor.getColumnIndex(TasksContract.TasksEntry._ID)),
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
                        .append("period: ").append(period);
        return sb.toString();
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        if (id != null) contentValues.put(TasksContract.TasksEntry._ID, id);
        contentValues.put(TasksContract.TasksEntry.COL_TYPE, type.getId());
        contentValues.put(TasksContract.TasksEntry.COL_TITLE, title);
        if (desc != null) contentValues.put(TasksContract.TasksEntry.COL_DESC, desc);
        contentValues.put(TasksContract.TasksEntry.COL_REPEAT, repeat);
        contentValues.put(TasksContract.TasksEntry.COL_NEXT_DATE, nextDate);
        if (period != null) contentValues.put(TasksContract.TasksEntry.COL_PERIOD, period);
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

    public String getPeriod() {
        return period;
    }

    // TODO: improve calculation to be a percentage of the period so its better
    // if no period, then calculate by the accuracy of the nextDate, if is up to seconds or just to days
    // then set "hours" before if the time is specified or set "days" before if no time
    public int getColor() {
        Calendar today = Calendar.getInstance();

        Calendar next = Calendar.getInstance();
        next.setTimeInMillis(getNextDate());

        if (next.before(today)) {
            return Color.RED;
        }
        next.add(Calendar.DATE, -7);
        if (next.before(today)) {
            return Color.YELLOW;
        } else {
            return Color.GREEN;
        }
    }
}
