package nitnavigator.zeefive.com.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import nitnavigator.zeefive.com.SyncHandler;

public class MyContentProvider extends ContentProvider{

    public static DBHelper dbHelper;

    private static final int CONTACTS_ALL = 1;
    private static final int CONTACTS_SINGLE = 2;
    private static final int CALENDAR_ALL = 3;
    private static final int CALENDAR_SINGLE = 4;
    private static final int NEWS_ALL = 5;
    private static final int NEWS_SINGLE = 6;

    // authority is the symbolic name of your provider
    // To avoid conflicts with other providers, you should use
    // Internet domain ownership (in reverse) as the basis of your provider authority.
    private static String PACKAGE_NAME = "nitnavigator.zeefive.com.nitnavigator";
    private static String AUTHORITY =  "com.zeefive.nitnavigator.provider";

    private static String PATH_CONTACTS = "contacts";
    private static String PATH_CALENDAR = "calendar";
    private static String PATH_NEWS = "news";

    // create content URIs from the authority by appending path to database table
    public static final Uri CONTENT_URI_CONTACTS = Uri.parse("content://" + AUTHORITY + "/" + PATH_CONTACTS);
    public static final Uri CONTENT_URI_CALENDAR = Uri.parse("content://" + AUTHORITY + "/" + PATH_CALENDAR);
    public static final Uri CONTENT_URI_NEWS = Uri.parse("content://" + AUTHORITY + "/" + PATH_NEWS);

    // a content URI pattern matches content URIs using wildcard characters:
    // *: Matches a string of any valid characters of any length.
    // #: Matches a string of numeric characters of any length.
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, PATH_CONTACTS, CONTACTS_ALL);
        uriMatcher.addURI(AUTHORITY, PATH_CONTACTS + "/#", CONTACTS_SINGLE);
        uriMatcher.addURI(AUTHORITY, PATH_CALENDAR, CALENDAR_ALL);
        uriMatcher.addURI(AUTHORITY, PATH_CALENDAR + "/#", CALENDAR_SINGLE);
        uriMatcher.addURI(AUTHORITY, PATH_NEWS, NEWS_ALL);
        uriMatcher.addURI(AUTHORITY, PATH_NEWS + "/#", NEWS_SINGLE);
    }

    // system calls onCreate() when it starts up the provider.
    @Override
    public boolean onCreate() {
        // get access to the database helper
        dbHelper = new DBHelper(getContext());
        return true;
    }

    //Return the MIME type corresponding to a content URI
    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)) {

            case CONTACTS_ALL: return "vnd.android.cursor.dir/vnd." + PACKAGE_NAME + "." + PATH_CONTACTS;
            case CONTACTS_SINGLE: return "vnd.android.cursor.item/vnd."+ PACKAGE_NAME + "." + PATH_CONTACTS;
            case CALENDAR_ALL: return "vnd.android.cursor.dir/vnd." + PACKAGE_NAME + "." + PATH_CALENDAR;
            case CALENDAR_SINGLE: return "vnd.android.cursor.item/vnd."+ PACKAGE_NAME + "." + PATH_CALENDAR;
            case NEWS_ALL: return "vnd.android.cursor.dir/vnd." + PACKAGE_NAME + "." + PATH_NEWS;
            case NEWS_SINGLE: return "vnd.android.cursor.item/vnd."+ PACKAGE_NAME + "." + PATH_NEWS;
            default: throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    // The insert() method adds a new row to the appropriate table, using the values
    // in the ContentValues argument. If a column name is not in the ContentValues argument,
    // you may want to provide a default value for it either in your provider code or in
    // your database schema.
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        return null;

    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int insertCount = 0;
        switch (uriMatcher.match(uri)){
            case NEWS_ALL:
                insertCount = SyncHandler.News.insertContentValuesToDatabase(db, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return insertCount;
            case CONTACTS_ALL:
                insertCount = SyncHandler.Contacts.insertContentValuesToDatabase(db, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return insertCount;
            case CALENDAR_ALL:
                insertCount = SyncHandler.Calendar.insertContentValuesToDatabase(db, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return insertCount;
        }
        return 0;
    }

    // The query() method must return a Cursor object, or if it fails,
    // throw an Exception. If you are using an SQLite database as your data storage,
    // you can simply return the Cursor returned by one of the query() methods of the
    // SQLiteDatabase class. If the query does not match any rows, you should return a
    // Cursor instance whose getCount() method returns 0. You should return null only
    // if an internal error occurred during the query process.
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String id;
        switch (uriMatcher.match(uri)) {
            case CONTACTS_ALL:
                queryBuilder.setTables(DBHelper.ContactsTable.TABLE_NAME);
                break;

            case CONTACTS_SINGLE:
                queryBuilder.setTables(DBHelper.ContactsTable.TABLE_NAME);
                id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(DBHelper.ContactsTable._ID + "=" + id);
                break;

            case CALENDAR_ALL:
                queryBuilder.setTables(DBHelper.CalendarTable.TABLE_NAME);
                //do nothing
                break;

            case CALENDAR_SINGLE:
                queryBuilder.setTables(DBHelper.CalendarTable.TABLE_NAME);
                id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(DBHelper.CalendarTable._ID + "=" + id);
                break;

            case NEWS_ALL:
                queryBuilder.setTables(DBHelper.NewsTable.TABLE_NAME);
                //do nothing
                break;

            case NEWS_SINGLE:
                queryBuilder.setTables(DBHelper.NewsTable.TABLE_NAME);
                id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(DBHelper.NewsTable._ID + "=" + id);
                break;

            default: throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    // The delete() method deletes rows based on the seletion or if an id is
    // provided then it deleted a single row. The methods returns the numbers
    // of records delete from the database. If you choose not to delete the data
    // physically then just update a flag here.
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d("Riyas", "Selection: " + selection);
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String id;
        switch (uriMatcher.match(uri)) {
            case NEWS_ALL:
                try{
                    int count = db.delete(DBHelper.NewsTable.TABLE_NAME, selection, selectionArgs);
                    getContext().getContentResolver().notifyChange(uri, null);
                    Log.d("Riyas", "Delete count in News table: " + count);
                    return count;
                }catch (Exception e){
                    e.printStackTrace();
                }
            case CALENDAR_ALL:
                try{
                    int count = db.delete(DBHelper.CalendarTable.TABLE_NAME, selection, selectionArgs);
                    getContext().getContentResolver().notifyChange(uri, null);
                    Log.d("Riyas", "Delete count in Calendar table: " + count);
                    return count;
                }catch (Exception e){
                    e.printStackTrace();
                }
            case CONTACTS_ALL:
                try{
                    int count = db.delete(DBHelper.ContactsTable.TABLE_NAME, selection, selectionArgs);
                    getContext().getContentResolver().notifyChange(uri, null);
                    Log.d("Riyas", "Delete count in Contacts table: " + count);
                    return count;
                }catch (Exception e){
                    e.printStackTrace();
                }
            default:
                return 0;
        }
    }

    // The update method() is same as delete() which updates multiple rows
    // based on the selection or a single row if the row id is provided. The
    // update method returns the number of updated rows.
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}