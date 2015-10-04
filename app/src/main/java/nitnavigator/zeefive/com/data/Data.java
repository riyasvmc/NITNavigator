package nitnavigator.zeefive.com.data;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.ArrayList;

import nitnavigator.zeefive.com.fragments.FragmentCalendar;
import nitnavigator.zeefive.com.fragments.FragmentContacts;
import nitnavigator.zeefive.com.fragments.FragmentDept;
import nitnavigator.zeefive.com.fragments.FragmentHome;
import nitnavigator.zeefive.com.fragments.FragmentNews;
import nitnavigator.zeefive.com.fragments.PartThreeFragment;
import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.utilities.ItemFragment;

public class Data {

    public static final int LOADER_CONTACTS = 1;
    public static final int LOADER_NEWS = 2;
    public static final int LOADER_CALENDAR = 3;
    public static final int LOADER_DEPT = 4;
    public static final int LOADER_RECYCLER = 5;

    // Database Path
    public static String PACKAGE_NAME = "nitnavigator.zeefive.com.nitnavigator";
    public static final String DB_PATH = "/data/data/"+ PACKAGE_NAME + "/databases/";
    public static final String DATABASE_NAME = "nitc_data.db";

    public static String noInternet = "No Internet";
    public static final String NEWS_URL = "http://www.api.zeefive.com/origami/v1/news";

    // Resource IDs
    // Activity Demo
    public static final int[] id_drawable_viewpager_indicator = {R.drawable.indicator_viewpager_1, R.drawable.indicator_viewpager_2,
            R.drawable.indicator_viewpager_3};

    //public static final String[] HOME_MENU = {"Home","News", "Contacts", "Calendar", "Map", "Clubs"};
    public static final String[] HOME_MENU = {"News", "Contacts", "Calendar","Department"};
    public static final String[] TABS = {"Home","News", "Contacts", "Calendar","Department", "Recycler"};
    public static final Fragment[] PAGE_FRAGMENTS = {
            new FragmentHome(),
            new FragmentNews(),
            new FragmentContacts(),
            new FragmentCalendar(),
            new FragmentDept(),
            new PartThreeFragment().createInstance(20)
    };

    public static String[] TagColor = new String[] {"#568d83", "#00a8ff", "#ead98b", "#9b87ae", "#d32811"
            , "#54d396", "#ce3156", "#727605", "#1d7605", "#546276", "#0a91c9", "#4d6893", "#cc8dd4", "#0f9c4b", "#210064"
            , "#384638", "#2dd879", "#1a7865", "#c28791", "#152f3d", "#ff3485", "#ff344c", "#cc2020", "#3c0066", "#3f3646"
            , "#2dd879"};

    public static int getColor(String string){
        String startLetter = string.substring(0,1).toLowerCase();

        switch(startLetter){
            case "a" : return Color.parseColor(TagColor[0]); 
            case "b" : return Color.parseColor(TagColor[1]); 
            case "c" : return Color.parseColor(TagColor[2]);
            case "d" : return Color.parseColor(TagColor[3]);
            case "e" : return Color.parseColor(TagColor[4]);
            case "f" : return Color.parseColor(TagColor[5]);
            case "g" : return Color.parseColor(TagColor[6]); 
            case "h" : return Color.parseColor(TagColor[7]); 
            case "i" : return Color.parseColor(TagColor[8]); 
            case "j" : return Color.parseColor(TagColor[9]); 
            case "k" : return Color.parseColor(TagColor[10]); 
            case "l" : return Color.parseColor(TagColor[11]); 
            case "m" : return Color.parseColor(TagColor[12]); 
            case "n" : return Color.parseColor(TagColor[13]); 
            case "o" : return Color.parseColor(TagColor[14]); 
            case "p" : return Color.parseColor(TagColor[15]); 
            case "q" : return Color.parseColor(TagColor[16]); 
            case "r" : return Color.parseColor(TagColor[17]); 
            case "s" : return Color.parseColor(TagColor[18]); 
            case "t" : return Color.parseColor(TagColor[19]); 
            case "u" : return Color.parseColor(TagColor[20]); 
            case "v" : return Color.parseColor(TagColor[21]); 
            case "w" : return Color.parseColor(TagColor[22]); 
            case "x" : return Color.parseColor(TagColor[23]); 
            case "y" : return Color.parseColor(TagColor[24]); 
            case "z" : return Color.parseColor(TagColor[25]); 
        }

        return 0xFF000000;
    }

    // Typefaces
    //public static final String TYPEFACE_TABS = "fonts/roboto/roboto_regular.ttf";
    //public static final String TYPEFACE_TABS = "fonts/roboto/roboto_medium.ttf";
    public static final String TYPEFACE_TABS = "fonts/roboto/roboto_bold.ttf";


    // resources IDs
    // Home icons
    public static final int[] id_home_icons= new int[] {
        R.drawable.ic_new, R.drawable.ic_con, R.drawable.ic_cal, R.drawable.ic_dep
    };
}
