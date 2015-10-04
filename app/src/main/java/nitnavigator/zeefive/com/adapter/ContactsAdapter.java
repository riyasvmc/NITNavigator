package nitnavigator.zeefive.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import nitnavigator.zeefive.com.activities.ActivityContactsDetail;
import nitnavigator.zeefive.com.activities.MainActivity;
import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.data.TableContacts;
import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.utilities.PassObject;

public class ContactsAdapter extends SimpleCursorAdapter {

    private Context mContext;
    private int layout;
    private View view;
    private ViewHolder viewHolder;
    private ShapeDrawable shapeDrawable;

    private static final int CATEGORY_STARTS = 0;
    private static final int CATEGORY_CONTINUE = 1;
    private static final int VIEW_COUNT = 2;
    private LayoutInflater mInflater;
    private Typeface mTypeface_RobotoBold;


    public ContactsAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int i) {
        super(context, layout, c, from, to);
        this.mContext = context;
        this.layout = layout;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_COUNT;
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

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        final String mId = cursor.getString(cursor.getColumnIndex(TableContacts.ID));
        final String mName = cursor.getString(cursor.getColumnIndex(TableContacts.NAME));
        final String mDept = cursor.getString(cursor.getColumnIndex(TableContacts.DEPARTMENT));
        final String mPhone = cursor.getString(cursor.getColumnIndex(TableContacts.PHONE));
        final String mMobile = cursor.getString(cursor.getColumnIndex(TableContacts.MOBILE));
        final String mEmail = cursor.getString(cursor.getColumnIndex(TableContacts.EMAIL));

        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.textView_subtitle_2.setText(mPhone);
        viewHolder.textView_header.setText(mDept);
        viewHolder.textView_header.setEnabled(false);
        viewHolder.textView_icon.setText(mDept.substring(0,1));
        viewHolder.textView_title.setText(mName);

        viewHolder.linearLayout_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PassObject passObject = new PassObject();
                passObject.setId(mId);
                passObject.setTitle(mName);
                passObject.setCategory(mDept);
                passObject.setPhone(mPhone);
                passObject.setMobile(mMobile);
                passObject.setEmail(mEmail);

                Intent intent = new Intent(MainActivity.getActivity(), ActivityContactsDetail.class);
                intent.putExtra("passObject", passObject);
                MainActivity.getActivity().startActivity(intent);
            }
        });

        switch(mDept.substring(0,1).toLowerCase()){
            case "a" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[0]), PorterDuff.Mode.MULTIPLY);
                       viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                       break;
            case "b" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[1]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "c" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[2]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "d" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[3]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "e" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[4]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "f" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[5]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "g" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[6]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "h" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[7]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "i" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[8]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "j" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[9]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "k" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[10]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "l" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[11]), PorterDuff.Mode.MULTIPLY);
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
            case "p" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[15]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "q" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[16]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "r" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[17]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "s" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[18]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "t" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[19]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "u" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[20]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "v" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[21]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "w" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[22]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "x" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[23]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "y" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[24]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
            case "z" : viewHolder.shapeDrawable.setColorFilter(Color.parseColor(Data.TagColor[25]), PorterDuff.Mode.MULTIPLY);
                viewHolder.linearLayout_tag.setBackgroundDrawable(viewHolder.shapeDrawable);
                break;
        }
        viewHolder.linearLayout_container.invalidate();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view;
        view = mInflater.inflate(R.layout.listitem_contacts_header, parent, false);

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.textView_title = (TextView) view.findViewById(R.id.title);
        viewHolder.textView_subtitle_2 = (TextView) view.findViewById(R.id.textView_sub_2);
        viewHolder.textView_header = (TextView) view.findViewById(R.id.textView_header);
        viewHolder.textView_icon = (TextView) view.findViewById(R.id.tag);
        viewHolder.linearLayout_container = (LinearLayout) view.findViewById(R.id.linearLayout_container);
        viewHolder.linearLayout_tag = (LinearLayout) view.findViewById(R.id.linearLayout_tag);
        viewHolder.linearLayout_content = (LinearLayout) view.findViewById(R.id.linearLayout_content);
        viewHolder.shapeDrawable = mContext.getResources().getDrawable(R.drawable.bg_roundbutton);
        view.setTag(viewHolder);

        int position = cursor.getPosition();
        if (position == 0) {
            viewHolder.textView_header.setVisibility(View.VISIBLE);
        } else {

            if (isNewGroup(cursor, position)) {
                viewHolder.textView_header.setVisibility(View.VISIBLE);
            } else {
                viewHolder.textView_header.setVisibility(View.GONE);
            }
        }

        return view;
    }

    private boolean isNewGroup(Cursor cursor, int position) {

        int indexDept = cursor.getColumnIndex(TableContacts.DEPARTMENT);
        String currentString = cursor.getString(cursor.getColumnIndex(TableContacts.DEPARTMENT));

        cursor.moveToPosition(position - 1);
        String preString = cursor.getString(indexDept);
        cursor.moveToPosition(position);
        if (!currentString.equals(preString)) {
            return true;
        }

        return false;
    }

    class ViewHolder {
        TextView textView_title;
        TextView textView_subtitle_2;
        TextView textView_header;
        TextView textView_icon;
        LinearLayout linearLayout_container;
        LinearLayout linearLayout_tag;
        LinearLayout linearLayout_content;
        Drawable shapeDrawable;
    }

}


