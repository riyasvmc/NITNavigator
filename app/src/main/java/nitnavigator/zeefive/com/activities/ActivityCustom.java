package nitnavigator.zeefive.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.utility.Utilities;

public class ActivityCustom extends AppCompatActivity {

    private AppCompatActivity mActivity = this;
    private ActionBar mActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setActionBar(){
        mActionBar = this.getSupportActionBar();
        mActionBar.setElevation(4f);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                intent = new Intent(mActivity, ActivitySettings.class);
                startActivity(intent);
                return true;

            case R.id.action_Feedback:
                startActivity(Utilities.sendEmail(Data.support_email, "Feedback", "write here..." ));
                return true;

            case R.id.action_about:
                intent = new Intent(mActivity, ActivityAbout.class);
                startActivity(intent);
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
