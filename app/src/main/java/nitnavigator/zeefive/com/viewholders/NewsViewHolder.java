package nitnavigator.zeefive.com.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import nitnavigator.zeefive.com.main.R;

public class NewsViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTitle;
    private final TextView mSub;
    private final TextView mTag;

    public NewsViewHolder(final View parent, TextView title, TextView sub, TextView tag) {
        super(parent);
        mTitle = title;
        mSub = sub;
        mTag = tag;
    }

    public static NewsViewHolder newInstance(View parent) {
        TextView title = (TextView) parent.findViewById(R.id.title);
        TextView sub = (TextView) parent.findViewById(R.id.sub);
        TextView tag = (TextView) parent.findViewById(R.id.tag);
        return new NewsViewHolder(parent, title, sub, tag);
    }

    public void setTitle(CharSequence text) {
        mTitle.setText(text);
    }
    public void setSub(CharSequence text) {
        mSub.setText(text);
    }
    public void setTag(CharSequence text) {
        mTag.setText(text);
    }
}
