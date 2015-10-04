package nitnavigator.zeefive.com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.fragments.FragmentCalendar;
import nitnavigator.zeefive.com.fragments.FragmentContacts;
import nitnavigator.zeefive.com.fragments.FragmentDept;
import nitnavigator.zeefive.com.fragments.FragmentHome;
import nitnavigator.zeefive.com.fragments.FragmentNews;
import nitnavigator.zeefive.com.fragments.PartThreeFragment;


public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);

    }
    @Override
    public Fragment getItem(int i) {

        switch(i){
            case 0 : return Data.PAGE_FRAGMENTS[0];
            case 1 : return Data.PAGE_FRAGMENTS[1];
            case 2 : return Data.PAGE_FRAGMENTS[2];
            case 3 : return Data.PAGE_FRAGMENTS[3];
            case 4 : return Data.PAGE_FRAGMENTS[4];
            case 5 : return Data.PAGE_FRAGMENTS[5];
        }
        return null;
    }

    @Override
    public int getCount() {
        return Data.PAGE_FRAGMENTS.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Data.TABS[position];
    }


}
