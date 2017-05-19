package net.chokethe.damntasks.tasks;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.chokethe.damntasks.R;
import net.chokethe.damntasks.db.DamnTasksDbHelper;

import java.util.Date;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {
    private Context mContext;
    private DamnTasksDbHelper mDamnTasksDbHelper;
    private Cursor mCursor;

    public TasksAdapter(Context context, DamnTasksDbHelper db) {
        mContext = context;
        mDamnTasksDbHelper = db;
        mCursor = mDamnTasksDbHelper.getAllTasks();
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.task_layout, parent, false);
        return new TaskViewHolder(this, view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        TaskTO task = new TaskTO(mCursor);

        holder.itemView.setTag(position);
        holder.id = task.getId();
//        Resources res = mContext.getResources();
        TaskBO.setTypeAlertView(holder.mType, task);
        holder.mTitle.setText(task.getTitle());
        holder.mNext.setText(new Date(task.getNextDate()).toString());
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void close() {
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
    }

    public void swapCursor() {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = mDamnTasksDbHelper.getAllTasks();
        this.notifyDataSetChanged();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public long id;
        ImageView mType;
        TextView mTitle;
        TextView mNext;
        TasksAdapter mAdapter;

        TaskViewHolder(TasksAdapter adapter, View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mType = (ImageView) itemView.findViewById(R.id.taskItem_tv_type);
            mTitle = (TextView) itemView.findViewById(R.id.taskItem_tv_title);
            mNext = (TextView) itemView.findViewById(R.id.taskItem_tv_next);
            mAdapter = adapter;
        }

        @Override
        public void onClick(View v) {
            Intent configActivityIntent = new Intent(mContext, TaskActivity.class);
            configActivityIntent.putExtra(Intent.EXTRA_INDEX, id);
            mContext.startActivity(configActivityIntent);
        }
    }
}
