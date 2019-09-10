package nitnavigator.zeefive.com.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import nitnavigator.zeefive.com.fragments.FragmentCalendar;
import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.model.Calendar;

public class RecyclerAdapterCalendar extends RecyclerView.Adapter<RecyclerAdapterCalendar.MyViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Calendar> mList;
    private Typeface mTypeface_ThickFont;

    public RecyclerAdapterCalendar(Context context, List<Calendar> list) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mList = list;
        mTypeface_ThickFont = Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto/roboto_bold.ttf");
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.listitem_calendar, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        viewHolder.setup(mList.get(position), position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        View mainView;
        TextView textView_day;
        TextView textView_month;
        TextView textView_title;
        TextView textView_today;
        TextView sub_1;
        TextView textView_timestamp;

        public MyViewHolder(View itemView) {
            super(itemView);
            mainView = itemView;
            textView_day = (TextView) itemView.findViewById(R.id.day);
            textView_month = (TextView) itemView.findViewById(R.id.month);
            textView_title = (TextView) itemView.findViewById(R.id.title);
            textView_today = (TextView) itemView.findViewById(R.id.textView_today);
            sub_1 = (TextView) itemView.findViewById(R.id.sub_title_1);
            textView_timestamp = (TextView) itemView.findViewById(R.id.sub_title_2);
            itemView.setOnClickListener(this);
        }

        public void setup(Calendar item, int position){

            // set title
            if(TextUtils.isEmpty(item.getTitle())){
                textView_title.setVisibility(View.GONE);
            }else{
                textView_title.setVisibility(View.VISIBLE);
                textView_title.setText(item.getTitle());
            }

            // set sub title 1
            if(TextUtils.isEmpty(item.getDescription())){
                sub_1.setVisibility(View.GONE);
            }else{
                sub_1.setVisibility(View.VISIBLE);
                sub_1.setText(item.getDescription());
            }

            // format date to human readable
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); // yyyy-MM-dd
            try{
                Date date = simpleDateFormat.parse(item.getDate());
                textView_timestamp.setText(DateUtils.formatDateTime(mContext, date.getTime(),
                        DateUtils.FORMAT_SHOW_DATE |
                        DateUtils.FORMAT_SHOW_WEEKDAY |
                        DateUtils.FORMAT_ABBREV_MONTH |
                        DateUtils.FORMAT_SHOW_YEAR)
                );

                // set day
                String day = new SimpleDateFormat("dd").format(date.getTime());
                textView_day.setText(day);

                // set month
                String month = new SimpleDateFormat("MMM").format(date.getTime());
                textView_month.setText(month);

            }catch (Exception e){
                e.printStackTrace();
            }

            // check today
            if(FragmentCalendar.sCalendarTodayPosition == position){
                textView_today.setVisibility(View.VISIBLE);
            }else{
                textView_today.setVisibility(View.GONE);
            }

        }

        @Override
        public void onClick(View v) {
            /*GlobalVariables.getInstance().setSelectedOrders(mList.get(getLayoutPosition()));
            Intent i = new Intent(mContext, ActivityOrderDetail.class);
            mContext.startActivity(i);*/
        }
    }
}