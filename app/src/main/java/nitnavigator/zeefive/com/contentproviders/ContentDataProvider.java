package nitnavigator.zeefive.com.contentproviders;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.data.DatabaseHelper;
import nitnavigator.zeefive.com.data.TableCalendar;
import nitnavigator.zeefive.com.data.TableContacts;
import nitnavigator.zeefive.com.data.TableDept;
import nitnavigator.zeefive.com.data.TableNews;

public class ContentDataProvider extends ContentProvider{

    public static DatabaseHelper dbHelper;

    private static final int CONTACTS_ALL = 1;
    private static final int CONTACTS_SINGLE = 2;
    private static final int CALENDAR_ALL = 3;
    private static final int CALENDAR_SINGLE = 4;
    private static final int NEWS_ALL = 5;
    private static final int NEWS_SINGLE = 6;
    private static final int DEPT_ALL = 7;
    private static final int DEPT_SINGLE = 8;

    // authority is the symbolic name of your provider
    // To avoid conflicts with other providers, you should use
    // Internet domain ownership (in reverse) as the basis of your provider authority.
    private static String PACKAGE_NAME = Data.PACKAGE_NAME;
    private static String AUTHORITY =  "com.zeefive.nitnavigator" +".contentproviders";

    private static String PATH_CONTACTS = "contacts_table";
    private static String PATH_CALENDAR = "calendar_table";
    private static String PATH_NEWS = "news_table";
    private static String PATH_DEPT = "department_table";

    // create content URIs from the authority by appending path to database table
    public static final Uri CONTENT_URI_CONTACTS = Uri.parse("content://" + AUTHORITY + "/" + PATH_CONTACTS);
    public static final Uri CONTENT_URI_CALENDAR = Uri.parse("content://" + AUTHORITY + "/" + PATH_CALENDAR);
    public static final Uri CONTENT_URI_NEWS = Uri.parse("content://" + AUTHORITY + "/" + PATH_NEWS);
    public static final Uri CONTENT_URI_DEPT = Uri.parse("content://" + AUTHORITY + "/" + PATH_DEPT);

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

        uriMatcher.addURI(AUTHORITY, PATH_DEPT, DEPT_ALL);
        uriMatcher.addURI(AUTHORITY, PATH_DEPT + "/#", DEPT_SINGLE);
    }

    // system calls onCreate() when it starts up the provider.
    @Override
    public boolean onCreate() {
        // get access to the database helper
        dbHelper = new DatabaseHelper(getContext());
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

            case DEPT_ALL: return "vnd.android.cursor.dir/vnd." + PACKAGE_NAME + "." + PATH_DEPT;
            case DEPT_SINGLE: return "vnd.android.cursor.item/vnd."+ PACKAGE_NAME + "." + PATH_DEPT;

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
                queryBuilder.setTables(TableContacts.CONTACTS_TABLE);
                break;

            case CONTACTS_SINGLE:
                queryBuilder.setTables(TableContacts.CONTACTS_TABLE);
                id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(TableContacts.ID + "=" + id);
                break;

            case CALENDAR_ALL:
                queryBuilder.setTables(TableCalendar.CALENDAR_TABLE);
                //do nothing
                break;

            case CALENDAR_SINGLE:
                queryBuilder.setTables(TableCalendar.CALENDAR_TABLE);
                id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(TableCalendar.ID + "=" + id);
                break;

            case NEWS_ALL:
                queryBuilder.setTables(TableNews.NEWS_TABLE);
                //do nothing
                break;

            case NEWS_SINGLE:
                queryBuilder.setTables(TableNews.NEWS_TABLE);
                id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(TableNews.ID + "=" + id);
                break;

            case DEPT_ALL:
                queryBuilder.setTables(TableDept.DEPT_TABLE);
                //do nothing
                break;

            case DEPT_SINGLE:
                queryBuilder.setTables(TableDept.DEPT_TABLE);
                id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(TableDept.ID + "=" + id);
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
        return 0;
    }

    // The update method() is same as delete() which updates multiple rows
    // based on the selection or a single row if the row id is provided. The
    // update method returns the number of updated rows.
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}