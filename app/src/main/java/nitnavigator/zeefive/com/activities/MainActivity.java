package nitnavigator.zeefive.com.activities;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import hugo.weaving.DebugLog;
import nitnavigator.zeefive.com.PageTransformerPopUp;
import nitnavigator.zeefive.com.QuickstartPreferences;
import nitnavigator.zeefive.com.adapter.PagerAdapter;
import nitnavigator.zeefive.com.fragments.FragmentCalendar;
import nitnavigator.zeefive.com.gcm.RegistrationIntentService;
import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.utility.Utilities;
import nitnavigator.zeefive.com.view.TypeWriter;

public class MainActivity extends ActivityCustom{

    public static final String TAG = "Riyas";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final int NOTIFICATION_ID = 11;

    // views
    private static AppCompatActivity mActivity;
    public static FloatingActionButton mFab;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ActionBar mActionBar;
    private TypeWriter mTextView;

    private PagerAdapter mPagerAdapter;
    public static final String REQUEST_TAG = "tag";

    private RequestQueue mQueue;
    private JsonObjectRequest mJsonObjectRequest;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private SharedPreferences mSharedPreferences;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        ActionBar actionBar = getSupportActionBar();
        LinearLayout linearLayout = (LinearLayout) actionBar.getCustomView();
        mTextView = (TypeWriter) linearLayout.findViewById(R.id.sub);
        mTextView.setText("News");

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                /*mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                mLinearLayout.setVisibility(View.VISIBLE);*/
            }
        };

        mFragmentManager = getSupportFragmentManager();

        setUpWithFragmentManager(mFragmentManager);

        registerAppOnGCMServer();

        // dismiss notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(MainActivity.NOTIFICATION_ID);

    }

    @DebugLog
    private void setUpWithFragmentManager(FragmentManager manager){
        mPagerAdapter = new PagerAdapter(manager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setPageTransformer(true, new PageTransformerPopUp(PageTransformerPopUp.TransformType.FLOW));

        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(3);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentCalendar.setScrollPosition();
            }
        });
        mFab.setVisibility(View.GONE);

        mTabLayout.setOnTabSelectedListener(mOnTabSelectedListener);

        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_message_accent_24dp);
        mTabLayout.getTabAt(0).setText("");
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_perm_contact_calendar_grey_400_24dp);
        mTabLayout.getTabAt(1).setText("");
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_event_note_grey_400_24dp);
        mTabLayout.getTabAt(2).setText("");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mQueue != null){
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

    private void initViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();

        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.activity_main_actionbar_title);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
    }

    private void registerAppOnGCMServer(){

        /*mButtonRetry.setVisibility(View.GONE);
        mRegistrationProgressBar.setVisibility(View.VISIBLE);*/

        boolean isRegistrationComplete = mSharedPreferences.getBoolean(QuickstartPreferences.REGISTRATION_COMPLETE, false);

        // check weather the app is registered with GCM Server
        if(!isRegistrationComplete){

            // check Internet & Google play services available
            if(Utilities.isConnectedToInternet(this) && checkPlayServices()){
                Log.d("Riyas", "starting Service");
                // Start IntentService to register App with GCM Server
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }else {
                /*mButtonRetry.setVisibility(View.VISIBLE);
                mRegistrationProgressBar.setVisibility(View.GONE);*/
            }
        }else{
            /*mRegistrationProgressBar.setVisibility(View.GONE);
            mLinearLayout.setVisibility(View.VISIBLE);*/
        }
        int i =0;
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }

    public static Context getActivity(){
        return mActivity;
    }

    private TabLayout.OnTabSelectedListener mOnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mViewPager.setCurrentItem(tab.getPosition());
            setFABVisibility(tab.getPosition());

            // Todo improve below code, declare tab position values to Data class.
            // heighlight tab icon
            switch (tab.getPosition()){
                case 0 : tab.setIcon(R.drawable.ic_message_accent_24dp);
                    mActionBar.setTitle("News");
                    mTextView.setText("News");
                    break;
                case 1 : tab.setIcon(R.drawable.ic_perm_contact_calendar_accent_24dp);
                    mActionBar.setTitle("Contacts");
                    mTextView.setText("Contacts");
                    break;
                case 2 : tab.setIcon(R.drawable.ic_event_note_accent_24dp);
                    mActionBar.setTitle("Calendar");
                    mTextView.setText("Calendar");
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            switch (tab.getPosition()){
                case 0 : tab.setIcon(R.drawable.ic_message_grey_400_24dp);
                    break;
                case 1 : tab.setIcon(R.drawable.ic_perm_contact_calendar_grey_400_24dp);
                    break;
                case 2 : tab.setIcon(R.drawable.ic_event_note_grey_400_24dp);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private void setFABVisibility(int position){
        if(position == 2 && FragmentCalendar.sCalendarTodayPosition != -1){
            mFab.setVisibility(View.VISIBLE);
        }else{
            mFab.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        int position = mViewPager.getCurrentItem();
        if(position == 0){
            super.onBackPressed();
        }else{
            mViewPager.setCurrentItem(position - 1);
        }

    }

}
