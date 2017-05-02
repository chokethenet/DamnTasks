package net.chokethe.damntasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import net.chokethe.damntasks.about.AboutActivity;
import net.chokethe.damntasks.db.DamnTasksDbHelper;
import net.chokethe.damntasks.utils.CommonUtils;

public class MainActivity extends AppCompatActivity {

    private DamnTasksDbHelper mDamnTasksDbHelper;
    private TasksAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO 1: login?
        // TODO 2: load data from ws to db

        mDamnTasksDbHelper = new DamnTasksDbHelper(this);
        mAdapter = new TasksAdapter(this, mDamnTasksDbHelper);
        mRecyclerView = (RecyclerView) findViewById(R.id.main_rv_tasks);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                TasksAdapter.TaskViewHolder taskViewHolder = (TasksAdapter.TaskViewHolder) viewHolder;
                mDamnTasksDbHelper.deleteTask(taskViewHolder.id);
                mAdapter.swapCursor();
                CommonUtils.showToast(MainActivity.this, "test swipe");
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                Intent startAboutActivity = new Intent(this, AboutActivity.class);
                startActivity(startAboutActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
