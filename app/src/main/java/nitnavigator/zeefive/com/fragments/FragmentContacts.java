package nitnavigator.zeefive.com.fragments;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hugo.weaving.DebugLog;
import nitnavigator.zeefive.com.SyncHandler;
import nitnavigator.zeefive.com.adapter.RecyclerAdapterContacts;
import nitnavigator.zeefive.com.data.DBHelper;
import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.data.MyContentProvider;
import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.model.Contact;
import nitnavigator.zeefive.com.utility.Utilities;
import nitnavigator.zeefive.com.utility.VolleySingleton;
import nitnavigator.zeefive.com.view.RecyclerViewWithEmptyView;

public class FragmentContacts extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    //constants
    public static final String TITLE = "Contacts";
    public static final String REQUEST_TAG = "tag";
    private RecyclerAdapterContacts mAdapter;
    private CursorLoader cursorLoader;
    private ArrayList<Contact> mList;
    public static String mQuery = "";
    private RecyclerViewWithEmptyView mRecyclerView;
    private JsonObjectRequest mJsonObjectRequest;
    private RequestQueue mQueue;
    // views
    private TextView mTextView_EmptyView;
    private LinearLayout mLinearLayout_EmptyView;
    private Button mButton_Sync;
    private static ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        setHasOptionsMenu(true);

        mRecyclerView = (RecyclerViewWithEmptyView) view.findViewById(R.id.recyclerView_contacts);
        //mRecyclerView.setEmptyView(view.findViewById(R.id.empty_view));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mList = new ArrayList<>();
        mAdapter = new RecyclerAdapterContacts(getContext(), mList);
        mRecyclerView.setAdapter(mAdapter);

        mLinearLayout_EmptyView = (LinearLayout) view.findViewById(R.id.linearLayout_emptyView);
        mButton_Sync = (Button) view.findViewById(R.id.button_sync);
        mButton_Sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog = ProgressDialog.show(getActivity(), null, "Downloading...");
                syncContacts();
            }
        });
        mTextView_EmptyView = (TextView) view.findViewById(R.id.textView);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(Data.LOADER_CONTACTS, null, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().restartLoader(Data.LOADER_CONTACTS, null, this);

        // sync Contacts
        syncContacts();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mQueue != null){
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

    @DebugLog
    public void syncContacts(){

        // Todo show progress bar here.
        if(mQueue != null){
            mQueue.cancelAll(REQUEST_TAG);
        }
        mQueue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
        mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Data.URL_API_SYNC_CONTACTS, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @DebugLog
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissDialog();
                        SyncHandler.Contacts.processContacts(getActivity(), response);
                    }
                },
                new Response.ErrorListener() {
                    @DebugLog
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dismissDialog();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_contact, menu);

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
    }

    private void restartLoader(){
        getActivity().getSupportLoaderManager().restartLoader(Data.LOADER_CONTACTS, null, this);
        mRecyclerView.scrollToPosition(0);
    }

    // This is called when a new Loader needs to be created.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection =
                DBHelper.ContactsTable.TITLE + " like ? or " +
                DBHelper.ContactsTable.TITLE + " like ? or " +
                        DBHelper.ContactsTable.CATEGORY + " like ? or " +
                        DBHelper.ContactsTable.CATEGORY + " like ?";
        String[] selectionArgs = new String[] {
                mQuery + "%",
                "% " + mQuery + "%",
                mQuery + "%",
                "% " + mQuery + "%"
        };

        String order = DBHelper.ContactsTable.TITLE;
        CursorLoader cursorLoader = new CursorLoader(getContext(), MyContentProvider.CONTENT_URI_CONTACTS, null, selection, selectionArgs, order);
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

        int i_id = c.getColumnIndexOrThrow(DBHelper.ContactsTable._ID);
        int i_title = c.getColumnIndexOrThrow(DBHelper.ContactsTable.TITLE);
        int i_category = c.getColumnIndexOrThrow(DBHelper.ContactsTable.CATEGORY);
        int i_phone = c.getColumnIndexOrThrow(DBHelper.ContactsTable.PHONE);
        int i_mobile = c.getColumnIndexOrThrow(DBHelper.ContactsTable.MOBILE);
        int i_created_at = c.getColumnIndexOrThrow(DBHelper.ContactsTable.CREATED_AT);

        mList.clear();
        while(c.moveToNext()){
            // make item object
            Contact item = new Contact(c.getString(i_id), c.getString(i_title), c.getString(i_category), c.getString(i_phone), c.getString(i_mobile), c.getString(i_created_at));
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
}
