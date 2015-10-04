package nitnavigator.zeefive.com.data;

import android.database.sqlite.SQLiteDatabase;

public class TableCalendar {

    public static String ID = "_id";
    public static String DATE = "date";
    public static String TITLE = "title";
    public static String CLASS = "class";
    public static String DESC = "desc";
    public static String REMARK = "remark";
    public static String MONTH = "month";

    public static final String CALENDAR_TABLE = "calendar_table";

    public static void onCreate(SQLiteDatabase db) {
    }

}
