package nitnavigator.zeefive.com.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import nitnavigator.zeefive.com.main.R;

public class ActivityCalendarDetail extends ActivityCustom {

    AppCompatActivity activity = this;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_detail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

}
