package nitnavigator.zeefive.com.utilities;

import android.support.v4.app.Fragment;

import nitnavigator.zeefive.com.data.Data;

/**
 * Created by Riyas V on 1/5/2015.
 */
public class ItemFragment {

    private final CharSequence mTitle;
    private final int mIndicatorColor;
    private final int mDividerColor;
    private Fragment mFragment;

    public ItemFragment(CharSequence title, int indicatorColor, int dividerColor, Fragment fragment) {
        mTitle = title;
        mIndicatorColor = indicatorColor;
        mDividerColor = dividerColor;
        mFragment = fragment;
    }

    public Fragment createFragment(int id) {
        switch (id){
            case 0: return Data.PAGE_FRAGMENTS[0];
            case 1: return Data.PAGE_FRAGMENTS[1];
            case 2: return Data.PAGE_FRAGMENTS[2];
            case 3: return Data.PAGE_FRAGMENTS[3];
        }
        return null;
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

    public Fragment getFragment(){ return mFragment; }
}
