package nitnavigator.zeefive.com.data;

import android.database.sqlite.SQLiteDatabase;

public class TableContacts {

    public static String ID = "_id";
    public static String NAME = "name";
    public static String DEPARTMENT = "department";
    public static String PHONE = "phone";
    public static String MOBILE = "mobile";
    public static String EMAIL = "email";

    public static final String CONTACTS_TABLE = "phone_table";

    private static final String DATABASE_CREATE = "CREATE TABLE if not exists " + CONTACTS_TABLE + " (" + ID + " integer PRIMARY KEY autoincrement," +
            NAME + "," + DEPARTMENT + "," + PHONE + "," + MOBILE + "," + EMAIL +");";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE);
        onCreate(db);
    }

}
