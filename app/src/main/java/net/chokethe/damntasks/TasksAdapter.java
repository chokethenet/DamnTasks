package net.chokethe.damntasks;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.chokethe.damntasks.db.DamnTasksDbHelper;
import net.chokethe.damntasks.db.TaskTO;

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
        long id = task.getId();

        holder.itemView.setTag(position);
        holder.id = id;
//        Resources res = mContext.getResources();
        holder.mTest.setText(task.toString());
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
        TextView mTest;
        TasksAdapter mAdapter;

        TaskViewHolder(TasksAdapter adapter, View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTest = (TextView) itemView.findViewById(R.id.task_tv_test);
            mAdapter = adapter;
        }

        @Override
        public void onClick(View v) {
//            BlindDialogHelper.showUpdate(mContext, mAdapter, mDamnTasksDbHelper, this);
        }
    }
}
