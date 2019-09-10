package nitnavigator.zeefive.com.utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.main.BuildConfig;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Utilities {

    public static final String MY_PREF = "my_pref";

    public static void makeItFullScreen(AppCompatActivity activity){

        // activity.getSupportActionBar().hide();
        /*
        if (Build.VERSION.SDK_INT < 16) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }*/
    }

    public static boolean isItFirstOpen(Context context){

        SharedPreferences sp = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        boolean hasVisited = sp.getBoolean("isItFirstOpen", false);

        if(hasVisited == false){
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("isItFirstOpen", true);
            e.commit();

            return true;
        }

        return false;

    }

    public static boolean showDemo(Context context){

        SharedPreferences sp = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
        boolean demoShown = sp.getBoolean("demoShown", false);

        if(demoShown == false){
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("demoShown", true);
            e.commit();

            return true;
        }

        return false;

    }

    public static void hideKeyboard(AppCompatActivity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(ActionBarActivity.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

    }

    public static Intent dialPhone(String phoneNo) {
        String uri = "tel:" + phoneNo;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        return intent;
    }

    public static Intent sendEmail(String to, String sub, String message){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("plain/text");
        intent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
        intent.putExtra(Intent.EXTRA_SUBJECT, sub);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        return intent;
    }


    public static boolean isItToday(String date){
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
        Calendar cal = Calendar.getInstance();
        String systemDate = dateFormat.format(cal.getTime());
        //String systemDate = "25-Jan-2015";

        Log.d("Date", "System date: " + systemDate + ", Passed date: " + date);


        if(systemDate.equals(date)){
            return true;
        }else{
            return false;
        }

    }

    public static int noDays(String date){
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MMM-yy");
        Calendar cal = Calendar.getInstance();
        String mSystemDate = myFormat.format(cal.getTime());
        long noDays = -1;
        try{
            Date date1 = myFormat.parse(date);
            Date dateSystem = myFormat.parse(mSystemDate);
            long diffMilliSeconds = dateSystem.getTime() - date1.getTime();

            noDays = TimeUnit.DAYS.convert(diffMilliSeconds, TimeUnit.MILLISECONDS);

            Log.d("RiyasVmc", "No of Days: " + String.valueOf(noDays));

        }catch (ParseException e){

        }

        return (int) noDays;
    }
    public static int getTagColor(String string){
        return 0;
    }

    public static int getColorCode(String string){
        switch(string){
            case "a" : return 0;
            case "b" : return 1;
            case "c" : return 2;
            case "d" : return 3;
            case "e" : return 4;
            case "f" : return 5;
            case "g" : return 6;
            case "h" : return 7;
            case "i" : return 8;
            case "j" : return 9;
            case "k" : return 10;
            case "l" : return 11;
            case "m" : return 12;
            case "n" : return 13;
            case "o" : return 14;
            case "p" : return 15;
            case "q" : return 16;
            case "r" : return 17;
            case "s" : return 18;
            case "t" : return 19;
            case "u" : return 20;
            case "v" : return 21;
            case "w" : return 22;
            case "x" : return 23;
            case "y" : return 24;
            case "z" : return 25;
        }
        return 0;
    }

    public static boolean isInternetAvailable(AppCompatActivity activity){
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected){
            Toast.makeText(activity, Data.noInternet, Toast.LENGTH_SHORT).show();
        }
        return isConnected;
    }

    public static boolean isConnectedToInternet(AppCompatActivity activity){
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected){
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return isConnected;
    }

    public static String getTimeSpanStringFromDate(String dateString){
        //String created_at = news.getCreated_at(); //"2011/11/12 16:05:06";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        if(date != null){
            return DateUtils.getRelativeTimeSpanString(date.getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS).toString();
        }else{
            return null;
        }
    }

    public static void makeDebugToast(Context context, String message){
        if(BuildConfig.DEBUG) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

}
