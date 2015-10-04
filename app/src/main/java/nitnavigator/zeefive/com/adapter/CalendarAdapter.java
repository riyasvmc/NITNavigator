package nitnavigator.zeefive.com.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nitnavigator.zeefive.com.activities.MainActivity;
import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.data.TableCalendar;
import nitnavigator.zeefive.com.main.R;

public class CalendarAdapter extends SimpleCursorAdapter {

    private static final int VIEW_TYPE_GROUP_START = 0;
    private static final int VIEW_TYPE_GROUP_CONT = 1;
    private static final int VIEW_TYPE_COUNT = 2;
    private int lastPosition = -1;
    private LayoutInflater mInflater;

    private int nViewType;


    public CalendarAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int i) {
        super(context, layout, c, from, to);

        this.mContext = context;

        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {

        // There is always a group header for the first data item

        if (position == 0) {
            return VIEW_TYPE_GROUP_START;
        }

        // For other items, decide based on current data
        Cursor cursor = getCursor();
        cursor.moveToPosition(position);
        boolean newGroup = isNewGroup(cursor, position);

        // Check item grouping

        if (newGroup) {
            return VIEW_TYPE_GROUP_START;
        } else {
            return VIEW_TYPE_GROUP_CONT;
        }
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {



        String mTitle = cursor.getString(cursor.getColumnIndex(TableCalendar.TITLE));
        String mDesc = cursor.getString(cursor.getColumnIndex(TableCalendar.DESC));
        String mDate = cursor.getString(cursor.getColumnIndex(TableCalendar.DATE));
        String mClass = cursor.getString(cursor.getColumnIndex(TableCalendar.CLASS));
        String mMonth = cursor.getString(cursor.getColumnIndex(TableCalendar.MONTH));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String mDayOfWeek = "";
        try {
                Date date = dateFormat.parse(mDate);
                mDayOfWeek = sdf.format(date);

        }catch (ParseException e){

        }

        String mDayOfMonth;

        ViewHolder viewHolder = (ViewHolder) view.getTag();
/*
        Drawable drawable = FragmentCalendar.shapeDrawable;
        drawable.setColorFilter(Color.RED, PorterDuff.Mode.OVERLAY);
        viewHolder.linearLayout_tag.setBackgroundDrawable(drawable);*/

        if(mTitle == null){
            viewHolder.textView_title.setVisibility(View.GONE);
        }else {
            viewHolder.textView_title.setVisibility(View.VISIBLE);
            viewHolder.textView_title.setText(mTitle);
        }


        if(mDesc == null){
            viewHolder.textView_description.setVisibility(View.GONE);
        }else{
            viewHolder.textView_description.setVisibility(View.VISIBLE);
            viewHolder.textView_description.setText(mDesc);
        }
        if(mClass == null){
            viewHolder.textView_class.setText("");
        }else{
            viewHolder.textView_class.setText(mClass);
        }

        /*if(Integer.parseInt(mClass) != -1){
            viewHolder.textView_class.setText(mClass);
        }else{
            viewHolder.textView_class.setText("");
        }*/

        viewHolder.textView_header.setText(mMonth + " 2015");
        viewHolder.textView_header.setEnabled(false);

        /*viewHolder.linearLayout_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.getActivity(), ActivityCalendarDetail.class);
                MainActivity.getActivity().startActivity(intent);
            }
        });*/

        // set day of month for tag
        Log.d("Riyas", mDate.substring(1,2));
        if(mDate.substring(1,2).equals("-")){
            mDayOfMonth = mDate.substring(0, 1);
        }else{
            mDayOfMonth = mDate.substring(0, 2);
        }

        viewHolder.textView_dayMonth.setText(mDayOfMonth);

        // control the today indicator
        if(MainActivity.calPosition == cursor.getPosition()){
            viewHolder.linearLayout_today.setVisibility(View.VISIBLE);
        }else{
            viewHolder.linearLayout_today.setVisibility(View.GONE);
        }

        viewHolder.linearLayout_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        switch(mMonth.substring(0,1).toLowerCase()){
            case "a" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[0]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "d" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[3]), PorterDuff.Mode.MULTIPLY);
                 viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "f" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[5]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "j" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[9]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "m" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[12]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "n" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[13]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "o" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[14]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "s" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[18]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
        }

        viewHolder.textView_datestamp.setText(mDayOfWeek + ", " + mMonth + " " + mDayOfMonth);
        viewHolder.linearLayout_container.invalidate();

    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        Log.d(MainActivity.TAG, "CalendarAdapter: newView");

        int position = cursor.getPosition();
        if (position == 0) {
            nViewType = VIEW_TYPE_GROUP_START;
        } else {
            boolean newGroup = isNewGroup(cursor, position);

            if (newGroup) {
                nViewType = VIEW_TYPE_GROUP_START;

            } else {
                nViewType = VIEW_TYPE_GROUP_CONT;
            }
        }

        View v;
        v = mInflater.inflate(R.layout.listitem_calendar_header, parent, false);

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.textView_title = (TextView) v.findViewById(R.id.title);
        viewHolder.textView_description = (TextView) v.findViewById(R.id.sub);
        viewHolder.textView_class = (TextView) v.findViewById(R.id.textView_class);
        viewHolder.textView_header = (TextView) v.findViewById(R.id.textView_header);
        viewHolder.textView_dayMonth = (TextView) v.findViewById(R.id.tag);
        viewHolder.textView_datestamp = (TextView) v.findViewById(R.id.textView_sub_2);
        viewHolder.linearLayout = (LinearLayout) v.findViewById(R.id.linearLayout_subcontainer);
        viewHolder.linearLayout_today = (LinearLayout) v.findViewById(R.id.linearLayout_today);
        viewHolder.linearLayout_container = (LinearLayout) v.findViewById(R.id.linearLayout_container);
        viewHolder.linearLayout_tag = (LinearLayout) v.findViewById(R.id.linearLayout_tag);
        viewHolder.linearLayout_content = (LinearLayout) v.findViewById(R.id.linearLayout_content);
        viewHolder.shapeDrawable = mContext.getResources().getDrawable(R.drawable.bg_roundbutton);
        v.setTag(viewHolder);

        if (nViewType == VIEW_TYPE_GROUP_START) {

            viewHolder.textView_header.setVisibility(View.VISIBLE);

        } else {
            viewHolder.textView_header.setVisibility(View.GONE);
        }

        return v;
    }


    private boolean isNewGroup(Cursor cursor, int position) {

        int indexMonth = cursor.getColumnIndex(TableCalendar.MONTH);
        String currentString = cursor.getString(cursor.getColumnIndex(TableCalendar.MONTH));
        cursor.moveToPosition(position - 1);
        String preString = cursor.getString(indexMonth);
        cursor.moveToPosition(position);

        if (!currentString.equals(preString)) {
            return true;
        }

            return false;
    }

    class ViewHolder {

        TextView textView_title;
        TextView textView_description;
        TextView textView_class;
        TextView textView_header;
        TextView textView_dayMonth;
        TextView textView_datestamp;
        LinearLayout linearLayout;
        LinearLayout linearLayout_today;
        LinearLayout linearLayout_container;
        LinearLayout linearLayout_tag;
        LinearLayout linearLayout_content;
        Drawable shapeDrawable;
    }

}


