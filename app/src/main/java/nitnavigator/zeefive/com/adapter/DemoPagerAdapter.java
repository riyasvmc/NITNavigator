package nitnavigator.zeefive.com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import nitnavigator.zeefive.com.fragments.FragmentDemo1;
import nitnavigator.zeefive.com.fragments.FragmentDemo2;
import nitnavigator.zeefive.com.fragments.FragmentDemo3;

public class DemoPagerAdapter extends FragmentPagerAdapter {
    public DemoPagerAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }
    @Override
    public Fragment getItem(int i) {
        //Fragment fragment = new DemoFragment1();

        switch (i){
            case 0: return new FragmentDemo1();
            case 1: return new FragmentDemo2();
            case 2: return new FragmentDemo3();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}
