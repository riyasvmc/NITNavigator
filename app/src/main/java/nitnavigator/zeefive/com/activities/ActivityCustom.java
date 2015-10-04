package nitnavigator.zeefive.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.utilities.Utilities;

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent mActivity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(mActivity, ActivitySettings.class);
            startActivity(intent);
            return true;
        }if (id == R.id.action_Feedback) {
            startActivity(Utilities.sendEmail("origami.feedback@gmail.com", "Feedback", "write here..." ));
            return true;
        }if (id == R.id.action_Support) {
            startActivity(Utilities.sendEmail("origami.feedback@gmail.com", "College Data", "You can send any data about our college (Phone nos, Map, Professors emails, etc...), It can be included in the next update."));
            return true;
        }if (id == R.id.action_about) {
            Intent intent = new Intent(mActivity, ActivityAbout.class);
            startActivity(intent);
            return true;
        }else if(id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
