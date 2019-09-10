package nitnavigator.zeefive.com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import nitnavigator.zeefive.com.activities.MainActivity;
import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.fragments.FragmentCalendar;
import nitnavigator.zeefive.com.fragments.FragmentContacts;
import nitnavigator.zeefive.com.fragments.FragmentNews;


public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0 :
                return new FragmentNews();
            case 1 :
                return new FragmentContacts();
            case 2 :
                return new FragmentCalendar();
            default:
                new Fragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0 : return FragmentNews.TITLE;
            case 1 : return FragmentContacts.TITLE;
            case 2 : return FragmentCalendar.TITLE;
            default: return null;
        }
    }


}
