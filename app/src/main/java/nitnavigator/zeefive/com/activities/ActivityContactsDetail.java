package nitnavigator.zeefive.com.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.utilities.PassObject;
import nitnavigator.zeefive.com.utilities.Utilities;

public class ActivityContactsDetail extends ActivityCustom {

    private TextView textView_title;
    private TextView textView_category;
    private TextView textView_phone;
    private TextView textView_email;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout_phone;
    private LinearLayout linearLayout_email;
    private LinearLayout linearLayout_container;

    private String mId;
    private String mTitle;
    private String mCategory;
    private String mPhone;
    private String mMobile;
    private String mEmail;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_detail);
        super.setActionBar();

        PassObject passObject = (PassObject) getIntent().getExtras().getSerializable("passObject");

        mId = passObject.getId();
        mTitle = passObject.getTitle();
        mCategory = passObject.getCategory();
        mPhone = passObject.getPhone();
        mMobile = passObject.getMobile();
        mEmail = passObject.getEmail();

        textView_title = (TextView) findViewById(R.id.title);
        textView_category = (TextView) findViewById(R.id.textView_category);
        textView_phone = (TextView) findViewById(R.id.textView_phone);
        textView_email = (TextView) findViewById(R.id.textView_email);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        linearLayout_phone = (LinearLayout) findViewById(R.id.linearLayout_phone);
        linearLayout_email = (LinearLayout) findViewById(R.id.linearLayout_email);
        linearLayout_container = (LinearLayout) findViewById(R.id.linearLayout_container);

        // set color for linear layout
        linearLayout.setBackgroundColor(Data.getColor(mCategory));
        //this.mActionBar.setBackgroundDrawable(new ColorDrawable(Data.getColor(mCategory)));

        linearLayout_phone.setOnClickListener(phoneListener);
        linearLayout_email.setOnClickListener(emailListener);

        textView_title.setText(mTitle);
        textView_category.setText(mCategory);

        setTextViews(linearLayout_phone,textView_phone, mPhone);
        setTextViews(linearLayout_email, textView_email, mEmail);

    }

    private void setTextViews(LinearLayout l,TextView tv, String s){
        if(s.equals("") || s.equals("null")){
            l.setVisibility(View.GONE);
            linearLayout_container.invalidate();
        }else{
            tv.setText(s);
        }
    }

    private View.OnClickListener phoneListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            startActivity(Utilities.dialPhone(mPhone));
        }
    };

    private View.OnClickListener emailListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            startActivity(Utilities.sendEmail(mEmail,"",""));
        }
    };
}
