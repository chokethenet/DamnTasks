package net.chokethe.damntasks.tasks;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import net.chokethe.damntasks.R;

import java.util.Calendar;

public class TaskBO {

    private TaskBO() {
    }

    public static int archiveOrRepeat(TaskTO task) {
        if (task.isRepeat()) {
            Calendar next = Calendar.getInstance();
            next.setTimeInMillis(task.getNextDate());
            TaskTO.Period period = task.getPeriod();

            next.add(period.unit, period.value);
            task.setNextDate(next.getTimeInMillis());

            return R.string.toast_task_done;
        } else {
            task.archive();

            return R.string.toast_task_archive;
        }
    }

    // TODO: improve calculation to be a percentage of the period so its better
    // if no period, then calculate by the accuracy of the nextDate, if is up to seconds or just to days
    // then set "hours" before if the time is specified or set "days" before if no time
    public static void setTypeView(ImageView typeView, TaskTO task) {
        Calendar now = Calendar.getInstance();
        Calendar next = Calendar.getInstance();
        next.setTimeInMillis(task.getNextDate());
        Calendar threshold = Calendar.getInstance();
        threshold.setTimeInMillis(next.getTimeInMillis());
        threshold.add(Calendar.DATE, -7); // FIXME: maybe add a threshold in TaskTO

        if (next.before(now)) {
            typeView.setImageResource(R.drawable.ic_error);
        } else if (threshold.before(now)) {
            typeView.setImageResource(R.drawable.ic_warning);
        } else {
            typeView.setImageResource(task.getType().getTaskResource());
        }
    }
}