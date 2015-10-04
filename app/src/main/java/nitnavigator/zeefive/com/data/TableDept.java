package nitnavigator.zeefive.com.data;

import android.database.sqlite.SQLiteDatabase;

public class TableDept {

    public static String ID = "_id";
    public static String NAME = "name";
    public static String ABOUT = "about";
    public static String DEPARTMENT = "category";

    public static final String DEPT_TABLE = "department_table";

    private static final String DATABASE_CREATE = "CREATE TABLE if not exists " + DEPT_TABLE + " (" + ID + " integer PRIMARY KEY autoincrement," +
            NAME + "," + ABOUT + "," + DEPARTMENT + ");";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DEPT_TABLE);
        onCreate(db);
    }

}
