package nitnavigator.zeefive.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.model.News;
import nitnavigator.zeefive.com.utility.Utilities;

public class RecyclerAdapterNews extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static List<News> mItemList;
    private static Context mContext;
    private static int sItemCount = 0;

    public RecyclerAdapterNews(Context context, List<News> itemList) {
        mItemList = itemList;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.listitem_news, parent, false);
        return NewsViewHolder.newInstance(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        NewsViewHolder holder = (NewsViewHolder) viewHolder;
        News item = mItemList.get(getItemCount() - position - 1);
        holder.setTitle(item.getTitle());
        holder.setSub(Utilities.getTimeSpanStringFromDate(item.getCreatedAt()));
    }

    @Override
    public int getItemCount() {
        sItemCount = mItemList == null ? 0 : mItemList.size();
        return sItemCount;
    }

    private static class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView mTitle;
        private final TextView mSub;

        public NewsViewHolder(final View parent, TextView title, TextView sub) {
            super(parent);
            mTitle = title;
            mSub = sub;
            parent.setOnClickListener(this);
        }

        public static NewsViewHolder newInstance(View parent) {
            TextView title = (TextView) parent.findViewById(R.id.news_title);
            TextView sub = (TextView) parent.findViewById(R.id.sub_title);
            TextView tag = (TextView) parent.findViewById(R.id.tag);
            return new NewsViewHolder(parent, title, sub);
        }

        public void setTitle(CharSequence text) {
            mTitle.setText(text);
        }
        public void setSub(CharSequence text) {
            mSub.setText(text);
        }

        @Override
        public void onClick(View v) {
            News item = mItemList.get(sItemCount - getLayoutPosition() - 1);
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink()));
            mContext.startActivity(i);
        }
    }
}
