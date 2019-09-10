package nitnavigator.zeefive.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import hugo.weaving.DebugLog;
import nitnavigator.zeefive.com.activities.ActivityContactsDetail;
import nitnavigator.zeefive.com.fragments.FragmentContacts;
import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.model.Contact;
import nitnavigator.zeefive.com.view.CustomTypefaceSpan;

public class RecyclerAdapterContacts extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static Context mContext;
    private static List<Contact> mList;
    private static Typeface mTypeface_ThickFont;

    public RecyclerAdapterContacts(Context context, List<Contact> itemList) {
        mList = itemList;
        mContext = context;
        mTypeface_ThickFont = Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto/roboto_bold.ttf");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.listitem_contacts, parent, false);

        return ContactsViewHolder.newInstance(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ContactsViewHolder holder = (ContactsViewHolder) viewHolder;
        Contact item = mList.get(position);

        // highlight code for title
        holder.setTitle(getSpannableString(item.getTitle()));

        // highlight code for category
        holder.setSub(getSpannableString(item.getCategory()));
    }

    @DebugLog
    private static int getStartIndexOf(String s){
        int index = s.toLowerCase().indexOf(FragmentContacts.mQuery.toLowerCase());
        return index;
    }

    private static int getEndIndexOf(String string){
        int end = getStartIndexOf(string) + FragmentContacts.mQuery.length();
        if (end > string.length()) end = string.length();
        return end;
    }

    private static SpannableString getSpannableString(String s){
        if(!TextUtils.isEmpty(FragmentContacts.mQuery) && getStartIndexOf(s) != -1) {
            SpannableString text = new SpannableString(s);
            int start = getStartIndexOf(s);
            int end = getEndIndexOf(s);
            CustomTypefaceSpan customTypefaceSpan = new CustomTypefaceSpan("", mTypeface_ThickFont);
            text.setSpan(customTypefaceSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);        //text.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return text;
        }else{
            return new SpannableString(s);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    // View Holder
    private static class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView mTitle;
        private final TextView mSub;

        public ContactsViewHolder(final View parent, TextView title, TextView sub) {
            super(parent);
            mTitle = title;
            mSub = sub;
            parent.setOnClickListener(this);
        }

        public static ContactsViewHolder newInstance(View parent) {
            TextView title = (TextView) parent.findViewById(R.id.title);
            TextView sub = (TextView) parent.findViewById(R.id.sub_title_2);
            TextView tag = (TextView) parent.findViewById(R.id.tag);

            return new ContactsViewHolder(parent, title, sub);
        }

        public void setTitle(SpannableString text) {
            mTitle.setText(text, TextView.BufferType.SPANNABLE);
        }

        public void setSub(SpannableString text) {
            mSub.setText(text, TextView.BufferType.SPANNABLE);
        }
        @Override
        public void onClick(View v) {
            Contact contact = mList.get(getLayoutPosition());
            Intent i = new Intent(mContext, ActivityContactsDetail.class);
            i.putExtra(Contact.CONTACT, contact);
            mContext.startActivity(i);
        }
    }
}
