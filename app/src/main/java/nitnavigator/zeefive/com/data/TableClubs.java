package nitnavigator.zeefive.com.data;

import android.database.sqlite.SQLiteDatabase;

public class TableClubs {

    public static String ID = "_id";
    public static String NAME = "name";
    public static String ABOUT = "about";
    public static String CATEGORY = "category";

    public static final String CLUBS_TABLE = "phone_table";

    private static final String DATABASE_CREATE = "CREATE TABLE if not exists " + CLUBS_TABLE + " (" + ID + " integer PRIMARY KEY autoincrement," +
            NAME + "," + ABOUT + "," + CATEGORY  +");";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CLUBS_TABLE);
        onCreate(db);
    }

}
