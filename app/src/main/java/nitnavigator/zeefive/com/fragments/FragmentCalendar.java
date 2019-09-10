package nitnavigator.zeefive.com.fragments;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import hugo.weaving.DebugLog;
import nitnavigator.zeefive.com.SyncHandler;
import nitnavigator.zeefive.com.adapter.RecyclerAdapterCalendar;
import nitnavigator.zeefive.com.data.DBHelper;
import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.data.MyContentProvider;
import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.model.Calendar;
import nitnavigator.zeefive.com.utility.Utilities;
import nitnavigator.zeefive.com.utility.VolleySingleton;
import nitnavigator.zeefive.com.view.RecyclerViewWithEmptyView;

public class FragmentCalendar extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String TITLE = "Calendar";
    public static final String REQUEST_TAG = "tag";
    private RecyclerAdapterCalendar mAdapter;
    private CursorLoader cursorLoader;
    private ArrayList<Calendar> mList;
    public static String mQuery = "";
    private static RecyclerViewWithEmptyView mRecyclerView;
    private RequestQueue mQueue;
    private JsonObjectRequest mJsonObjectRequest;
    public static int sCalendarTodayPosition = -1;
    // views
    private TextView mTextView_EmptyView;
    private LinearLayout mLinearLayout_EmptyView;
    private Button mButton_Sync;
    private static ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        mLinearLayout_EmptyView = (LinearLayout) view.findViewById(R.id.linearLayout_emptyView);
        mButton_Sync = (Button) view.findViewById(R.id.button_sync);
        mButton_Sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog = ProgressDialog.show(getActivity(), null, "Downloading...");
                syncCalendar();
            }
        });
        mTextView_EmptyView = (TextView) view.findViewById(R.id.textView);

        // setHasOptionsMenu(true);
        setRecyclerView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(Data.LOADER_CALENDAR, null, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().restartLoader(Data.LOADER_CALENDAR, null, this);

        // sync calendar
        syncCalendar();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mQueue != null){
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

    @DebugLog
    public void syncCalendar(){

        if(mQueue != null){
            mQueue.cancelAll(REQUEST_TAG);
        }
        mQueue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
        mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Data.URL_API_SYNC_CALENDAR, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissDialog();
                        SyncHandler.Calendar.processCalendar(getActivity(), response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dismissDialog();
                        Utilities.makeDebugToast(getActivity(), error.getMessage() + error.toString());
                        if(error instanceof NoConnectionError) {
                            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        mJsonObjectRequest.setTag(REQUEST_TAG);
        mJsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10*1000, 3, 1.2f));
        mQueue.add(mJsonObjectRequest);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            setScrollPostionToToday();
            setScrollPosition();
        }
    }

    public static void setScrollPosition(){
        if(mRecyclerView != null && sCalendarTodayPosition != -1) {
            mRecyclerView.scrollToPosition(sCalendarTodayPosition);
        }
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_calendar, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        if(searchView != null){
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getActivity().getComponentName()));

            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH){
                if (!TextUtils.isEmpty(mQuery)) {
                    searchItem.expandActionView();
                    searchView.setQuery(mQuery, true);
                    searchView.clearFocus();
                }
            }
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String query) {

                if(query.toString() != ""){
                    mQuery = query;
                    restartLoader();
                }else{
                    mQuery = null;
                    restartLoader();
                }
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Utilities.hideKeyboard((AppCompatActivity) getActivity());
                return true;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);
    }*/

    private void restartLoader(){
        getActivity().getSupportLoaderManager().restartLoader(Data.LOADER_CALENDAR, null, this);
        mRecyclerView.scrollToPosition(0);
    }

    private void setRecyclerView(View view) {

        mRecyclerView = (RecyclerViewWithEmptyView) view.findViewById(R.id.recyclerView_calendar);
        mList = new ArrayList<>();
        mAdapter = new RecyclerAdapterCalendar(getContext(), mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

    }

    // This is called when a new Loader needs to be created.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = DBHelper.CalendarTable.TITLE + " like ? or " + DBHelper.CalendarTable.TITLE + " like ? ";
        String[] selectionArgs = new String[] { mQuery+"%", "% " +mQuery + "%"};

        String order = DBHelper.CalendarTable._ID;
        CursorLoader cursorLoader = new CursorLoader(getContext(), MyContentProvider.CONTENT_URI_CALENDAR, null, selection, selectionArgs, order);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
        // check for empty counter and toggle visibility of recyclerview
        if(c.getCount() == 0){
            mLinearLayout_EmptyView.setVisibility(View.VISIBLE);
            if(mQuery.equals("")) {
                mButton_Sync.setVisibility(View.VISIBLE);
                mTextView_EmptyView.setText("No Data Found!");
            }else{
                mButton_Sync.setVisibility(View.GONE);
                mTextView_EmptyView.setText("No Results Matching Your Search \"" + mQuery + "\"");
            }
            mRecyclerView.setVisibility(View.GONE);
        }else{
            mLinearLayout_EmptyView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }

        int i_id = c.getColumnIndexOrThrow(DBHelper.CalendarTable._ID);
        int i_title = c.getColumnIndexOrThrow(DBHelper.CalendarTable.TITLE);
        int i_date = c.getColumnIndexOrThrow(DBHelper.CalendarTable.DATE);
        int i_class_count = c.getColumnIndexOrThrow(DBHelper.CalendarTable.CLASS_COUNT);
        int i_description = c.getColumnIndexOrThrow(DBHelper.CalendarTable.DESCRIPTION);
        int i_created_at = c.getColumnIndexOrThrow(DBHelper.CalendarTable.CREATED_AT);

        mList.clear();
        while(c.moveToNext()){
            // make item object
            Calendar item = new Calendar(c.getString(i_id), c.getString(i_title),
                    c.getString(i_description), c.getString(i_class_count), c.getString(i_date), c.getString(i_created_at));

            // adding item to array list
            mList.add(item);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public static void dismissDialog(){
        if(mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }

    @DebugLog
    public int setScrollPostionToToday() {

        Date date_db = null;
        Date date_system = null;
        Cursor cursor = null;

        String date_formatted = null;

        java.util.Calendar cal = java.util.Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateString_system = df.format(cal.getTime());

        try {date_system = df.parse(dateString_system); } catch (ParseException e){e.printStackTrace();}
        cursor = getActivity().getContentResolver().query(MyContentProvider.CONTENT_URI_CALENDAR, null, null, null, null, null);

        while(cursor.moveToNext()){
            String dateString_db = cursor.getString(cursor.getColumnIndex(DBHelper.CalendarTable.DATE));
            try {
                date_db = df.parse(dateString_db);
                if(date_db.compareTo(date_system) == 0) {
                    sCalendarTodayPosition = cursor.getPosition();
                }
            }
            catch (ParseException e) { e.printStackTrace(); }

        }

        return 0;
    }
}