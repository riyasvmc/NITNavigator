package nitnavigator.zeefive.com;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class toDo {
    // never forget to update db version then only update is going to update the database

    private String getTimeSpanStringFromDate(String dateString){
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
}
