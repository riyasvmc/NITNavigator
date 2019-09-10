package nitnavigator.zeefive.com;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hugo.weaving.DebugLog;
import nitnavigator.zeefive.com.activities.MainActivity;
import nitnavigator.zeefive.com.data.DBHelper;
import nitnavigator.zeefive.com.data.MyContentProvider;
import nitnavigator.zeefive.com.utility.VolleySingleton;

public class SyncHandler {

    public static class News {
        public static void processResponse(Context context, JSONObject response) {

            ContentValues[] values = null;
            JSONArray jsonArrayNews = null;

            if (response != null) {
                jsonArrayNews = response.optJSONArray("news");
                if (jsonArrayNews != null) {
                    int len = jsonArrayNews.length();
                    values = new ContentValues[len];
                    for (int i = 0; i < len; i++) {
                        JSONObject item = jsonArrayNews.optJSONObject(i);
                        ContentValues cv = new ContentValues();
                        cv.put(DBHelper.NewsTable.SERVER_ID, item.optString("id"));
                        cv.put(DBHelper.NewsTable.TITLE, item.optString("title"));
                        cv.put(DBHelper.NewsTable.LINK, item.optString("link"));
                        cv.put(DBHelper.NewsTable.CREATED_AT, item.optString("created_at"));
                        values[i] = cv;
                    }

                    // bulk insert
                    ContentResolver contentResolver = context.getContentResolver();
                    contentResolver.bulkInsert(MyContentProvider.CONTENT_URI_NEWS, values);

                }

                String ids = response.optString("ids");

                // bulk delete
                ContentResolver contentResolver = context.getContentResolver();
                String where = DBHelper.NewsTable.SERVER_ID + " NOT IN " + ids;
                contentResolver.delete(MyContentProvider.CONTENT_URI_NEWS, where, null);
            }
        }

        public static int insertContentValuesToDatabase(SQLiteDatabase db, ContentValues[] values) {
            int numInserted = 0;
            db.beginTransaction();
            try {
                String sql = "INSERT OR REPLACE INTO " + DBHelper.NewsTable.TABLE_NAME + " (server_id, title, link, created_at) VALUES (?,?,?,?);";
                SQLiteStatement insert = db.compileStatement(sql);
                for (ContentValues cv : values) {
                    insert.bindString(1, cv.getAsString(DBHelper.NewsTable.SERVER_ID));
                    insert.bindString(2, cv.getAsString(DBHelper.NewsTable.TITLE));
                    insert.bindString(3, cv.getAsString(DBHelper.NewsTable.LINK));
                    insert.bindString(4, cv.getAsString(DBHelper.NewsTable.CREATED_AT));
                    insert.execute();
                }
                db.setTransactionSuccessful();
                numInserted = values.length;
            } finally {
                db.endTransaction();
            }
            return numInserted;
        }

    }


    public static class Contacts {
        public static void processContacts(Context context, JSONObject jsonResponse) {

            ContentValues[] values;
            JSONArray jsonArrayContacts;

            if (jsonResponse != null) {
                jsonArrayContacts = jsonResponse.optJSONArray("contacts");
                if (jsonArrayContacts != null) {
                    int len = jsonArrayContacts.length();
                    values = new ContentValues[len];
                    for (int i = 0; i < len; i++) {
                        JSONObject jsonItem = jsonArrayContacts.optJSONObject(i);
                        ContentValues cv = new ContentValues();
                        cv.put(DBHelper.ContactsTable._ID, jsonItem.optString("id"));
                        cv.put(DBHelper.ContactsTable.TITLE, jsonItem.optString("title"));
                        cv.put(DBHelper.ContactsTable.CATEGORY, jsonItem.optString("category"));
                        cv.put(DBHelper.ContactsTable.PHONE, jsonItem.optString("phone"));
                        cv.put(DBHelper.ContactsTable.MOBILE, jsonItem.optString("mobile"));
                        cv.put(DBHelper.ContactsTable.CREATED_AT, jsonItem.optString("created_at"));
                        values[i] = cv;
                    }
                    bulkInsertContactsToDatabase(context, values);
                }
            }
        }

        private static void bulkInsertContactsToDatabase(Context context, ContentValues[] values) {
            ContentResolver contentResolver = context.getContentResolver();
            int count = contentResolver.bulkInsert(MyContentProvider.CONTENT_URI_CONTACTS, values);
        }

        public static int insertContentValuesToDatabase(SQLiteDatabase db, ContentValues[] values) {
            int numInserted = 0;
            db.beginTransaction();
            try {
                String sql = "INSERT OR REPLACE INTO " + DBHelper.ContactsTable.TABLE_NAME + " (_id, title, category, phone, mobile, created_at) VALUES (?,?,?,?,?,?);";
                SQLiteStatement insert = db.compileStatement(sql);
                for (ContentValues cv : values) {
                    insert.bindString(1, cv.getAsString(DBHelper.ContactsTable._ID));
                    insert.bindString(2, cv.getAsString(DBHelper.ContactsTable.TITLE));
                    insert.bindString(3, cv.getAsString(DBHelper.ContactsTable.CATEGORY));
                    insert.bindString(4, cv.getAsString(DBHelper.ContactsTable.PHONE));
                    insert.bindString(5, cv.getAsString(DBHelper.ContactsTable.MOBILE));
                    insert.bindString(6, cv.getAsString(DBHelper.ContactsTable.CREATED_AT));
                    insert.execute();
                }
                db.setTransactionSuccessful();
                numInserted = values.length;
            } finally {
                db.endTransaction();
            }
            return numInserted;
        }
    }

    public static class Calendar {
        @DebugLog
        public static void processCalendar(Context context, JSONObject jsonResponse) {

            ContentValues[] values;
            JSONArray jsonArrayCalendar;

            if (jsonResponse != null) {
                jsonArrayCalendar = jsonResponse.optJSONArray("entries");
                if (jsonArrayCalendar != null) {
                    int len = jsonArrayCalendar.length();
                    values = new ContentValues[len];
                    for (int i = 0; i < len; i++) {
                        JSONObject jsonItem = jsonArrayCalendar.optJSONObject(i);
                        ContentValues cv = new ContentValues();
                        cv.put(DBHelper.CalendarTable._ID, jsonItem.optString("id"));
                        cv.put(DBHelper.CalendarTable.DATE, jsonItem.optString("date"));
                        cv.put(DBHelper.CalendarTable.TITLE, jsonItem.optString("title"));
                        cv.put(DBHelper.CalendarTable.CLASS_COUNT, jsonItem.optString("class"));
                        cv.put(DBHelper.CalendarTable.DESCRIPTION, jsonItem.optString("description"));
                        cv.put(DBHelper.CalendarTable.CREATED_AT, jsonItem.optString("created_at"));
                        values[i] = cv;
                    }
                    bulkInsertCalendarToDatabase(context, values);
                }
            }
        }

        private static void bulkInsertCalendarToDatabase(Context context, ContentValues[] values) {
            ContentResolver contentResolver = context.getContentResolver();
            int count = contentResolver.bulkInsert(MyContentProvider.CONTENT_URI_CALENDAR, values);
        }

        public static int insertContentValuesToDatabase(SQLiteDatabase db, ContentValues[] values) {
            int numInserted = 0;
            db.beginTransaction();
            try {
                String sql = "INSERT OR REPLACE INTO " + DBHelper.CalendarTable.TABLE_NAME + " (_id, title, class_count, description, date, created_at) VALUES (?,?,?,?,?,?);";
                SQLiteStatement insert = db.compileStatement(sql);
                for (ContentValues cv : values) {
                    insert.bindString(1, cv.getAsString(DBHelper.CalendarTable._ID));
                    insert.bindString(2, cv.getAsString(DBHelper.CalendarTable.TITLE));
                    insert.bindString(3, cv.getAsString(DBHelper.CalendarTable.CLASS_COUNT));
                    insert.bindString(4, cv.getAsString(DBHelper.CalendarTable.DESCRIPTION));
                    insert.bindString(5, cv.getAsString(DBHelper.CalendarTable.DATE));
                    insert.bindString(6, cv.getAsString(DBHelper.CalendarTable.CREATED_AT));
                    insert.execute();
                }
                db.setTransactionSuccessful();
                numInserted = values.length;
            } finally {
                db.endTransaction();
            }
            return numInserted;
        }
    }
}
