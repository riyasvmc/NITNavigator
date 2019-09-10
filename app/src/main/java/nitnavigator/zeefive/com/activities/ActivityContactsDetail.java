package nitnavigator.zeefive.com.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.model.Contact;
import nitnavigator.zeefive.com.utility.Utilities;

public class ActivityContactsDetail extends ActivityCustom {

    private TextView textView_phone;
    private TextView textView_email;
    private LinearLayout linearLayout_phone;
    private LinearLayout linearLayout_email;
    private LinearLayout linearLayout_container;

    Contact mContact = new Contact();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_detail);
        super.setActionBar();

        mContact = (Contact) getIntent().getExtras().getSerializable(Contact.CONTACT);

        // set title
        getSupportActionBar().setTitle(mContact.getTitle());
        getSupportActionBar().setSubtitle(mContact.getCategory());

        textView_phone = (TextView) findViewById(R.id.textView_phone);
        textView_email = (TextView) findViewById(R.id.textView_mobile);
        linearLayout_phone = (LinearLayout) findViewById(R.id.linearLayout_phone);
        linearLayout_email = (LinearLayout) findViewById(R.id.linearLayout_mobile);
        linearLayout_container = (LinearLayout) findViewById(R.id.linearLayout_container);


        linearLayout_phone.setOnClickListener(phoneListener);
        linearLayout_email.setOnClickListener(mobileListener);

        setTextViews(linearLayout_phone,textView_phone, mContact.getPhone());
        setTextViews(linearLayout_email, textView_email, mContact.getMobile());
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
            startActivity(Utilities.dialPhone(mContact.getPhone()));
        }
    };

    private View.OnClickListener mobileListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            startActivity(Utilities.dialPhone(mContact.getMobile()));
        }
    };
}
