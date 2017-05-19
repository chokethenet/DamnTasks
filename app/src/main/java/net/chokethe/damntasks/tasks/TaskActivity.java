package net.chokethe.damntasks.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import net.chokethe.damntasks.R;
import net.chokethe.damntasks.db.DamnTasksDbHelper;

public class TaskActivity extends AppCompatActivity {

    private DamnTasksDbHelper mDamnTasksDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        mDamnTasksDbHelper = new DamnTasksDbHelper(this);

        if (getIntent().hasExtra(Intent.EXTRA_INDEX)) {
            long id = getIntent().getLongExtra(Intent.EXTRA_INDEX, -1);
            TaskTO task = mDamnTasksDbHelper.getTaskById(id);

            ImageView typeView = (ImageView) findViewById(R.id.task_tv_type);
            EditText titleView = (EditText) findViewById(R.id.task_et_title);
            EditText descView = (EditText) findViewById(R.id.task_et_desc);
            CheckBox repeatView = (CheckBox) findViewById(R.id.task_cb_repeat);
            typeView.setImageResource(task.getType().getTaskResource());
            titleView.setText(task.getTitle());
            descView.setText(task.getDesc());
            repeatView.setChecked(task.isRepeat());
        }

        // TODO: add button to save and check for compulsory fields
        // TODO: add datepicker and a timepicker in dialogs (timepicker with clear button)
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDamnTasksDbHelper.close();
    }

    //TODO: add listener for check/uncheck repeat to change its text and show/hide repeat options and enable disable next date

}
