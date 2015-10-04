package nitnavigator.zeefive.com.misc;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nitnavigator.zeefive.com.data.TableDept;
import nitnavigator.zeefive.com.main.R;

public class SimpleCursorRecyclerAdapter extends CursorRecyclerAdapter<SimpleViewHolder> {

    private static final int CATEGORY_STARTS = 0;
    private static final int CATEGORY_CONTINUE = 1;

    private int mLayout;
    private int[] mFrom;
    private int[] mTo;
    private String[] mOriginalFrom;

    public SimpleCursorRecyclerAdapter (int layout, Cursor c, String[] from, int[] to) {
        super(c);
        mLayout = layout;
        mTo = to;
        mOriginalFrom = from;
        findColumns(c, from);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return CATEGORY_STARTS;
        }

        // For other items, decide based on current data

        Cursor cursor = getCursor();
        cursor.moveToPosition(position);
        boolean newGroup = isNewGroup(cursor, position);

        // Check item grouping

        if (newGroup) {
            return CATEGORY_STARTS;
        } else {
            return CATEGORY_CONTINUE;
        }
    }

    private boolean isNewGroup(Cursor cursor, int position) {

        int indexDept = cursor.getColumnIndex(TableDept.DEPARTMENT);
        String currentString = cursor.getString(cursor.getColumnIndex(TableDept.DEPARTMENT));

        cursor.moveToPosition(position - 1);
        String preString = cursor.getString(indexDept);
        cursor.moveToPosition(position);
        if (!currentString.equals(preString)) {
            return true;
        }

        return false;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(mLayout, parent, false);

        TextView header = (TextView) v.findViewById(R.id.textView_header);
        if(viewType == CATEGORY_STARTS){
            header.setVisibility(View.VISIBLE);
        }else {
            header.setVisibility(View.GONE);
        }
        return new SimpleViewHolder(v, mTo);
    }

    @Override
    public void onBindViewHolder (SimpleViewHolder holder, Cursor cursor) {
        final int count = mTo.length;
        final int[] from = mFrom;

        for (int i = 0; i < count; i++) {
            holder.views[i].setText(cursor.getString(from[i]));
        }
    }

    /**
     * Create a map from an array of strings to an array of column-id integers in cursor c.
     * If c is null, the array will be discarded.
     *
     * @param c the cursor to find the columns from
     * @param from the Strings naming the columns of interest
     */
    private void findColumns(Cursor c, String[] from) {
        if (c != null) {
            int i;
            int count = from.length;
            if (mFrom == null || mFrom.length != count) {
                mFrom = new int[count];
            }
            for (i = 0; i < count; i++) {
                mFrom[i] = c.getColumnIndexOrThrow(from[i]);
            }
        } else {
            mFrom = null;
        }
    }

    @Override
    public Cursor swapCursor(Cursor c) {
        findColumns(c, mOriginalFrom);
        return super.swapCursor(c);
    }
}

class SimpleViewHolder extends RecyclerView.ViewHolder
{
    public TextView[] views;

    public SimpleViewHolder (View itemView, int[] to)
    {
        super(itemView);
        views = new TextView[to.length];
        for(int i = 0 ; i < to.length ; i++) {
            views[i] = (TextView) itemView.findViewById(to[i]);
        }
    }
}