package nitnavigator.zeefive.com.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "nitc_data.db";
    private static final int DATABASE_VERSION = 11;

    private static final String CREATE_NEWS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + NewsTable.TABLE_NAME
                    + " ( "
                    + NewsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + NewsTable.SERVER_ID + " TEXT UNIQUE, "
                    + NewsTable.TITLE + " TEXT, "
                    + NewsTable.LINK + " TEXT, "
                    + NewsTable.MD5 + " TEXT, "
                    + NewsTable.CREATED_AT + " TEXT"
                    + ");";

    private static final String CREATE_CONTACTS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + ContactsTable.TABLE_NAME
                    + " ( "
                    + ContactsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ContactsTable.TITLE + " TEXT, "
                    + ContactsTable.CATEGORY + " TEXT, "
                    + ContactsTable.PHONE + " TEXT, "
                    + ContactsTable.MOBILE + " TEXT, "
                    + ContactsTable.CREATED_AT + " TEXT"
                    + ");";

    private static final String CREATE_CALENDAR_TABLE =
            "CREATE TABLE IF NOT EXISTS " + CalendarTable.TABLE_NAME
                    + " ( "
                    + CalendarTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CalendarTable.TITLE + " TEXT, "
                    + CalendarTable.CLASS_COUNT + " TEXT, "
                    + CalendarTable.DESCRIPTION + " TEXT, "
                    + CalendarTable.DATE + " TEXT, "
                    + CalendarTable.CREATED_AT + " TEXT"
                    + ");";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NEWS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CALENDAR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static final class NewsTable implements BaseColumns {
        private NewsTable() {}
        public static final String TABLE_NAME = "news_table_1";
        public static final String FULL_ID =  TABLE_NAME + "." + _ID;
        public static final String SERVER_ID = "server_id";
        public static final String FULL_SERVER_ID =  TABLE_NAME + "." + SERVER_ID;
        public static final String TITLE =  "title";
        public static final String FULL_TITLE =  TABLE_NAME + "." + TITLE;
        public static final String LINK =  "link";
        public static final String FULL_LINK =  TABLE_NAME + "." + LINK;
        public static final String CREATED_AT =  "created_at";
        public static final String FULL_CREATED_AT =  TABLE_NAME + "." + CREATED_AT;
        public static final String MD5 =  "md5";
        public static final String FULL_MD5 =  TABLE_NAME + "." + MD5;
    }

    public static final class ContactsTable implements BaseColumns {
        private ContactsTable() {}
        public static final String TABLE_NAME = "contacts_table_1";
        public static final String FULL_ID =  TABLE_NAME + "." + _ID;
        public static final String TITLE =  "title";
        public static final String FULL_TITLE =  TABLE_NAME + "." + TITLE;
        public static final String CATEGORY =  "category";
        public static final String FULL_CATEGORY =  TABLE_NAME + "." + CATEGORY;
        public static final String PHONE =  "phone";
        public static final String FULL_PHONE =  TABLE_NAME + "." + PHONE;
        public static final String MOBILE =  "mobile";
        public static final String FULL_MOBILE =  TABLE_NAME + "." + MOBILE;
        public static final String CREATED_AT =  "created_at";
        public static final String FULL_CREATED_AT =  TABLE_NAME + "." + CREATED_AT;
    }

    public static final class CalendarTable implements BaseColumns {
        private CalendarTable() {}
        public static final String TABLE_NAME = "calendar_table_1";
        public static final String FULL_ID =  TABLE_NAME + "." + _ID;
        public static final String TITLE =  "title";
        public static final String FULL_TITLE =  TABLE_NAME + "." + TITLE;
        public static final String DESCRIPTION =  "description";
        public static final String FULL_DESCRIPTION =  TABLE_NAME + "." + DESCRIPTION;
        public static final String CLASS_COUNT =  "class_count";
        public static final String FULL_CLASS_COUNT =  TABLE_NAME + "." + CLASS_COUNT;
        public static final String DATE =  "date";
        public static final String FULL_DATE =  TABLE_NAME + "." + DATE;
        public static final String CREATED_AT =  "created_at";
        public static final String FULL_CREATED_AT =  TABLE_NAME + "." + CREATED_AT;
    }
}