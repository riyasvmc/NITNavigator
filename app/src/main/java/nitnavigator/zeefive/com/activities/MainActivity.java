package nitnavigator.zeefive.com.activities;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nitnavigator.zeefive.com.adapter.PagerAdapter;
import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.data.DatabaseHelper;
import nitnavigator.zeefive.com.data.TableCalendar;
import nitnavigator.zeefive.com.fragments.FragmentCalendar;
import nitnavigator.zeefive.com.fragments.FragmentContacts;
import nitnavigator.zeefive.com.fragments.FragmentDept;
import nitnavigator.zeefive.com.fragments.FragmentHome;
import nitnavigator.zeefive.com.fragments.FragmentNews;
import nitnavigator.zeefive.com.fragments.PartThreeFragment;
import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.utilities.Utilities;
import nitnavigator.zeefive.com.view.slidingtabs.SlidingTabLayout;
import nitnavigator.zeefive.com.view.slidingtabs.SlidingTabStrip;

public class MainActivity extends ActivityCustom{

    public static final String MY_PREF = "my_pref";
    public static final String TAG = "Riyas";
    private static AppCompatActivity activity;
    private PagerAdapter mPagerAdapter;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    public static FloatingActionButton fab;
    private DatabaseHelper dbh = new DatabaseHelper(this);
    private PagerTitleStrip titleStrip;
    private static Handler handler;
    private static Thread thread = null;
    public static List<FragmentProducer> mTabs = new ArrayList<FragmentProducer>();
    public static int calPosition = 0;
    private SlidingTabStrip strip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);


        handler = new Handler();
        activity = this;

        if(Utilities.isItFirstOpen(getBaseContext()) == true){
            // Swap database
            dbh.open();
            try {
                dbh.copyDatabase();
            } catch (IOException e) {
            }
        }

        calPosition = checkTodayMarking();

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(5);

        addPagesToList();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                mViewPager.setCurrentItem(pos);
                if(pos == 0){
                    fab.setVisibility(View.GONE);
                }else{
                    fab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public static Context getActivity(){
        return activity;
    }

    private void addPagesToList(){

        int color = getResources().getColor(android.R.color.white);
        for(int i = 0; i<5; i++){
            mTabs.add(new FragmentProducer(Data.TABS[0], new FragmentHome(), color, Color.BLUE));
            mTabs.add(new FragmentProducer(Data.TABS[1], new FragmentNews(), color, Color.BLUE));
            mTabs.add(new FragmentProducer(Data.TABS[2], new FragmentContacts(), color, Color.BLUE));
            mTabs.add(new FragmentProducer(Data.TABS[3], new FragmentCalendar(), color, Color.BLUE));
            mTabs.add(new FragmentProducer(Data.TABS[4], new FragmentDept(), color, Color.BLUE));
            mTabs.add(new FragmentProducer(Data.TABS[5], new PartThreeFragment(), color, Color.BLUE));
        }
    }

    public ViewPager getViewPager(){
        return mViewPager;
    }

    public static class FragmentProducer {
        private final CharSequence mTitle;
        private final int mIndicatorColor;
        private final int mDividerColor;
        private Fragment mFragment;

        FragmentProducer(CharSequence title, Fragment fragment,int indicatorColor, int dividerColor) {
            mTitle = title;
            mFragment = fragment;
            mIndicatorColor = indicatorColor;
            mDividerColor = dividerColor;
        }

        public void setFragment(Fragment fragment){
            this.mFragment = fragment;
        }
        public Fragment getFragment(int id) {
            return mFragment;
        }

        public CharSequence getTitle() {
            return mTitle;
        }

        public int getIndicatorColor() {
            return mIndicatorColor;
        }

        public int getDividerColor() {
            return mDividerColor;
        }
    }


    private int checkTodayMarking() {

        Date date_db = null;
        Date date_system = null;
        Cursor cursor = null;

        String date_formatted = null;

        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd-MMM-yy");
        String dateString_system = df.format(cal.getTime());

        try {date_system = df.parse(dateString_system); } catch (ParseException e){e.printStackTrace();}

        cursor = dbh.getCalendarData();

        while(cursor.moveToNext()){
            String dateString_db = cursor.getString(cursor.getColumnIndex(TableCalendar.DATE));
            try {
                date_db = df.parse(dateString_db);
                if(date_db.compareTo(date_system) == 0) {
                    return cursor.getPosition();
                }
            }
            catch (ParseException e) { e.printStackTrace(); }

        }

        return 0;
    }



}
