package nitnavigator.zeefive.com.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.utilities.PassObject;

public class ActivityDepartmentDetail extends ActivityCustom {
    private String id;
    private TextView textView_title;
    private TextView textView_category;
    private TextView textView_about;
    private LinearLayout linearLayout;

    AppCompatActivity activity = this;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_detail);

        PassObject passObject = (PassObject) getIntent().getExtras().getSerializable("passObject");

        textView_title = (TextView) findViewById(R.id.title);
        textView_category = (TextView) findViewById(R.id.textView_subtitle);
        textView_about = (TextView) findViewById(R.id.textView_about);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        String mTitle = passObject.getTitle();
        String mCategory = passObject.getCategory();
        String mAbout = passObject.getAbout();

        textView_title.setText(mTitle);
        textView_category.setText(mCategory);
        textView_about.setText(mAbout);

        //this.mActionBar.setBackgroundDrawable(new ColorDrawable(Data.getColor(passObject.getCategory())));
        linearLayout.setBackgroundColor(Data.getColor(mCategory));
    }

}
