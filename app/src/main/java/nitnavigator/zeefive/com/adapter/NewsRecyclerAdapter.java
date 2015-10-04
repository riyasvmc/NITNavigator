package nitnavigator.zeefive.com.adapter;

import android.content.Context;
import android.database.DatabaseUtils;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nitnavigator.zeefive.com.data.News;
import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.utilities.Utilities;
import nitnavigator.zeefive.com.viewholders.NewsViewHolder;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<News> mItemList;

    public NewsRecyclerAdapter(List<News> itemList) {
        mItemList = itemList;
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
        News news = mItemList.get(getItemCount() - position - 1);
        holder.setTitle(news.getTitle());
        holder.setSub(Utilities.getTimeSpanStringFromDate(news.getCreated_at()));
        holder.setTag(news.getType());
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

}
