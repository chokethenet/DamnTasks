package net.chokethe.damntasks;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.chokethe.damntasks.db.DamnTasksDbHelper;
import net.chokethe.damntasks.db.TasksContract;

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
        long id = mCursor.getLong(mCursor.getColumnIndex(TasksContract.TasksEntry._ID));
//        int smallBlind = mCursor.getInt(mCursor.getColumnIndex(TasksContract.TasksEntry.COLUMN_SMALL_BLIND));
//        int bigBlind = mCursor.getInt(mCursor.getColumnIndex(TasksContract.TasksEntry.COLUMN_BIG_BLIND));
//        int riseTime = mCursor.getInt(mCursor.getColumnIndex(TasksContract.TasksEntry.COLUMN_RISE_TIME));

        holder.itemView.setTag(position);
        holder.id = id;
        Resources res = mContext.getResources();
//        ConfigActivity.setBlindTextWithAdaptableSize(res, holder.mSmallBlind, smallBlind, true);
//        ConfigActivity.setBlindTextWithAdaptableSize(res, holder.mBigBlind, bigBlind, true);
//        holder.mRiseTime.setText(TimeUtils.getTwoDigitsTime(riseTime));
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
        //        TextView mSmallBlind;
//        TextView mBigBlind;
//        TextView mRiseTime;
        TasksAdapter mAdapter;

        TaskViewHolder(TasksAdapter adapter, View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
//            mSmallBlind = (TextView) itemView.findViewById(R.id.blind_tv_blind_small);
//            mBigBlind = (TextView) itemView.findViewById(R.id.blind_tv_blind_big);
//            mRiseTime = (TextView) itemView.findViewById(R.id.blind_tv_rise_time);
            mAdapter = adapter;
        }

        @Override
        public void onClick(View v) {
//            BlindDialogHelper.showUpdate(mContext, mAdapter, mDamnTasksDbHelper, this);
        }
    }
}
