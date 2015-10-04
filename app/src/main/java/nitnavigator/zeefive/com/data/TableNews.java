package nitnavigator.zeefive.com.data;

import android.database.sqlite.SQLiteDatabase;

public class TableNews {

    public static String ID = "_id";
    public static String TITLE = "title";
    public static String DESCRIPTION = "description";
    public static String IMAGE = "image";
    public static String LINK = "link";
    public static String TIMESTAMP = "timestamp";

    public static final String NEWS_TABLE = "news_table";

    private static final String DATABASE_CREATE = "CREATE TABLE if not exists " + NEWS_TABLE + " (" + ID + " integer PRIMARY KEY autoincrement," +
            TITLE + "," + DESCRIPTION + ","+ "," + IMAGE + ","+ "," + LINK + "," + TIMESTAMP  +");";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NEWS_TABLE);
        onCreate(db);
    }

}
