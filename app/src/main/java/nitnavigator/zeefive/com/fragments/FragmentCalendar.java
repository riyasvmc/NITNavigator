package nitnavigator.zeefive.com.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import nitnavigator.zeefive.com.activities.MainActivity;
import nitnavigator.zeefive.com.adapter.CalendarAdapter;
import nitnavigator.zeefive.com.contentproviders.ContentDataProvider;
import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.data.TableCalendar;
import nitnavigator.zeefive.com.main.R;

public class FragmentCalendar extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private SimpleCursorAdapter dataAdapter;
    private ListView listView;
    private MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        getActivity().getSupportLoaderManager().initLoader(Data.LOADER_CALENDAR, null, this);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity) getActivity();
        displayListView();

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().initLoader(Data.LOADER_CALENDAR, null, this);
        listView.setSelection(MainActivity.calPosition);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            listView.setSelection(MainActivity.calPosition);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_calendar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        listView.setSelection(MainActivity.calPosition);
        return true;
    }

    private void displayListView() {

        // The desired columns to be bound
        String[] columns = new String[] {TableCalendar.TITLE, TableCalendar.DESC, TableCalendar.CLASS};
        int[] to = new int[] {R.id.title, R.id.sub, R.id.textView_class};
        dataAdapter = new CalendarAdapter(getActivity(), R.layout.listitem_calendar_header, null, columns, to, 0);

        // get reference to the ListView
        listView = (ListView) getActivity().findViewById(R.id.listView_calendar);
        listView.setDividerHeight(0);
        listView.setAdapter(dataAdapter);
        listView.setSelection(MainActivity.calPosition);
    }

    // This is called when a new Loader needs to be created.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        //Log.d(MainActivity.TAG, "FragmentCalendar: onCreateLoader");

        String order = TableCalendar.ID;
        CursorLoader cursorLoader = new CursorLoader(getActivity(), ContentDataProvider.CONTENT_URI_CALENDAR, null, null, null, order);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        dataAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        dataAdapter.swapCursor(null);
    }

}
