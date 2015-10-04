package nitnavigator.zeefive.com.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ListView;

import nitnavigator.zeefive.com.main.R;


public class ActivitySettings extends ActivityCustom {
    private AppCompatActivity activity = this;
    private ActionBar actionBar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
