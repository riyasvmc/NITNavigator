package nitnavigator.zeefive.com.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import nitnavigator.zeefive.com.activities.MainActivity;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 10;
    private Context mContext;
    private SQLiteDatabase sqdb;

    public DatabaseHelper(Context context) {
        super(context, Data.DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TableContacts.onCreate(db);
        try {
            copyDatabase();
        } catch (IOException e) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            copyDatabase();
        } catch (IOException e) {
        }
    }

    public void copyDatabase() throws IOException {

        String path = Data.DB_PATH + Data.DATABASE_NAME;
        InputStream is = mContext.getAssets().open(Data.DATABASE_NAME);
        OutputStream os = new FileOutputStream(path);
        byte[] buffer = new byte[1024];
        int length;
        while((length = is.read(buffer))>0){
            os.write(buffer, 0, length);
        }
        os.flush();
        os.close();
        is.close();
    }

    public SQLiteDatabase open(){

        Log.d(MainActivity.TAG, "DatabaseHelper: open");

        return this.getWritableDatabase();
    }

    //GET CALENDER DATA FROM DATABASE
    public Cursor getCalendarData(){

        sqdb = this.open();
        Cursor c = (sqdb.query(TableCalendar.CALENDAR_TABLE, null, null, null, null, null, null));
        return c;
    }


}