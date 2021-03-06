package nitnavigator.zeefive.com.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import nitnavigator.zeefive.com.main.R;

public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView mItemTextView;

    public RecyclerItemViewHolder(final View parent, TextView itemTextView) {
        super(parent);
        mItemTextView = itemTextView;
    }

    public static RecyclerItemViewHolder newInstance(View parent) {
        TextView itemTextView = (TextView) parent.findViewById(R.id.title);
        return new RecyclerItemViewHolder(parent, itemTextView);
    }

    public void setup(CharSequence text) {
        mItemTextView.setText(text);
    }

}
