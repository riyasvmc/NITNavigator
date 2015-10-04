package nitnavigator.zeefive.com.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nitnavigator.zeefive.com.contentproviders.ContentDataProvider;
import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.data.TableDept;
import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.misc.SimpleCursorRecyclerAdapter;

public class PartThreeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public final static String ITEMS_COUNT_KEY = "PartThreeFragment$ItemsCount";
    SimpleCursorRecyclerAdapter dataAdapter;

    public static PartThreeFragment createInstance(int itemsCount) {
        PartThreeFragment partThreeFragment = new PartThreeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        partThreeFragment.setArguments(bundle);
        return partThreeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recycler_view, container, false);
        setupRecyclerView(recyclerView);
        getActivity().getSupportLoaderManager().initLoader(Data.LOADER_RECYCLER, null, this);
        return recyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // RecyclerAdapter recyclerAdapter = new RecyclerAdapter(createItemList());
        String[] from = new String[]{TableDept.DEPARTMENT, TableDept.NAME, TableDept.DEPARTMENT};
        int[] to = new int[]{R.id.textView_header, R.id.title, R.id.sub};
        dataAdapter = new SimpleCursorRecyclerAdapter(R.layout.listitem_dept_header, null, from, to);
        recyclerView.setAdapter(dataAdapter);
    }

    private List<String> createItemList() {
        List<String> itemList = new ArrayList<>();
        Bundle bundle = getArguments();
        if(bundle!=null) {
            int itemsCount = bundle.getInt(ITEMS_COUNT_KEY);
            for (int i = 0; i < itemsCount; i++) {
                itemList.add("Item " + i);
            }
        }
        return itemList;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), ContentDataProvider.CONTENT_URI_DEPT, null, null, null, null);
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
