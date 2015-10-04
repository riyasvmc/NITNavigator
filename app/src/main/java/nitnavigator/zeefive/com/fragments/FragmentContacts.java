package nitnavigator.zeefive.com.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import nitnavigator.zeefive.com.adapter.ContactsAdapter;
import nitnavigator.zeefive.com.contentproviders.ContentDataProvider;
import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.data.TableContacts;
import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.utilities.Utilities;

public class FragmentContacts extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private SimpleCursorAdapter mAdapter;
    private CursorLoader cursorLoader;
    public static String mQuery = "";
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        setHasOptionsMenu(true);
        getActivity().getSupportLoaderManager().initLoader(Data.LOADER_CONTACTS, null,this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        displayListView();
    }


    @Override
    public void onResume() {
        super.onResume();

        //Log.d(MainActivity.TAG, "FragmentContacts: onResume");
        //Starts a new or restarts an existing Loader in this manager

        getActivity().getSupportLoaderManager().initLoader(Data.LOADER_CONTACTS, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        //Log.d(MainActivity.TAG, "FragmentContacts: onCreateOptionMenu");

        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_contact, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        if(searchView != null){
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            //TextView textView = (TextView) searchView.findViewById(searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null));
            //textView = UtilityClass.setStyleToTextView(getBaseContext(), textView);

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
    }

    private void displayListView() {

        //Log.d(MainActivity.TAG, "FragmentContacts: displayListView");

        // The desired columns to be bound
        String[] columns = new String[] {TableContacts.PHONE, TableContacts.NAME, TableContacts.DEPARTMENT};
        //int[] to = new int[] {R.id.textview_phone, R.id.textview_name, R.id.textview_category};
        mAdapter = new ContactsAdapter(getActivity(), R.layout.listitem_contacts_header, null, columns, null, 0);
        mAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(0);
            }
        });

        listView = (ListView) getActivity().findViewById(R.id.listView_contacts);
        listView.setAdapter(mAdapter);
        listView.setDividerHeight(0);

    }

    // This is called when a new Loader needs to be created.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String select = TableContacts.NAME + " like \'%" + mQuery + "%\' or " + TableContacts.DEPARTMENT + " like \'%" + mQuery + "%\'";
        String[] selectionArgs = new String[]{mQuery};
        String order = TableContacts.DEPARTMENT;
        String[] projection = {TableContacts.ID, TableContacts.PHONE, TableContacts.NAME, TableContacts.DEPARTMENT,TableContacts.MOBILE, TableContacts.EMAIL};

        if(mQuery != null || mQuery !=""){

            cursorLoader = new CursorLoader(getActivity(), ContentDataProvider.CONTENT_URI_CONTACTS, projection, select, null, order);
            return cursorLoader;
        }else if(mQuery == null){
            cursorLoader = new CursorLoader(getActivity(), ContentDataProvider.CONTENT_URI_CONTACTS, projection, null, null, order);
            return cursorLoader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        //Log.d(MainActivity.TAG, "FragmentContacts: onLoadFinished");

        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        //Log.d(MainActivity.TAG, "FragmentContacts: onLoaderReset");

        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null);
    }
}
