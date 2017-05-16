package net.chokethe.damntasks.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

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

            // TODO: set task in views
            TextView test = (TextView) findViewById(R.id.task_tv_test);
            test.setText(task.toString());
        }

        // TODO: add button to save and check for compulsory fields
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDamnTasksDbHelper.close();
    }
}
