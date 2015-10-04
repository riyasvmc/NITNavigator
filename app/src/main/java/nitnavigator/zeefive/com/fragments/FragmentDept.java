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

import nitnavigator.zeefive.com.adapter.DepartmentAdapter;
import nitnavigator.zeefive.com.contentproviders.ContentDataProvider;
import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.data.TableDept;
import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.utilities.Utilities;

public class FragmentDept extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private SimpleCursorAdapter dataAdapter;
    private CursorLoader cursorLoader;
    private String mCurFilter = "";
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_dept, container, false);
        setHasOptionsMenu(true);
        getActivity().getSupportLoaderManager().initLoader(Data.LOADER_DEPT, null,this);
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

        getActivity().getSupportLoaderManager().initLoader(Data.LOADER_DEPT, null, this);
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

                if(query != ""){
                    mCurFilter = query;
                    restartLoader();
                    return true;
                }else{
                    mCurFilter = null;
                    restartLoader();
                    return true;
                }

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
        getActivity().getSupportLoaderManager().restartLoader(Data.LOADER_DEPT, null, this);
    }

    private void displayListView() {

        //Log.d(MainActivity.TAG, "FragmentContacts: displayListView");

        // The desired columns to be bound
        String[] columns = new String[] {TableDept.NAME, TableDept.DEPARTMENT, TableDept.ABOUT};
        //int[] to = new int[] {R.id.textview_phone, R.id.textview_name, R.id.textview_category};
        dataAdapter = new DepartmentAdapter(getActivity(), R.layout.listitem_dept_header, null, columns, null, 0);
        dataAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(0);
            }
        });

        // get reference to the ListView
        listView = (ListView) getActivity().findViewById(R.id.listView);
        listView.setAdapter(dataAdapter);
        listView.setDividerHeight(0);

        //Ensures a loader is initialized and active.


    }

    // This is called when a new Loader needs to be created.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String select = TableDept.NAME + " like \'%" + mCurFilter + "%\' or " + TableDept.DEPARTMENT + " like \'%" + mCurFilter + "%\'";
        String[] selectionArgs = new String[]{mCurFilter};
        String order = TableDept.DEPARTMENT;
        String[] projection = {TableDept.ID, TableDept.NAME, TableDept.DEPARTMENT, TableDept.ABOUT};

        if(mCurFilter != null || mCurFilter !=""){

            cursorLoader = new CursorLoader(getActivity(), ContentDataProvider.CONTENT_URI_DEPT, projection, select, null, null);
            return cursorLoader;
        }else if(mCurFilter == null){
            cursorLoader = new CursorLoader(getActivity(), ContentDataProvider.CONTENT_URI_DEPT, projection, null, null, order);
            return cursorLoader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        //Log.d(MainActivity.TAG, "FragmentContacts: onLoadFinished");

        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        dataAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        //Log.d(MainActivity.TAG, "FragmentContacts: onLoaderReset");

        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        dataAdapter.swapCursor(null);
    }
}
