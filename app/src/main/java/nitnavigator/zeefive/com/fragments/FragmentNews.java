package nitnavigator.zeefive.com.fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
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
import nitnavigator.zeefive.com.adapter.RecyclerAdapterNews;
import nitnavigator.zeefive.com.data.DBHelper;
import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.data.MyContentProvider;
import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.model.News;
import nitnavigator.zeefive.com.utility.VolleySingleton;
import nitnavigator.zeefive.com.view.RecyclerViewWithEmptyView;

public class FragmentNews extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String TITLE = "News";
    private RecyclerAdapterNews mAdapter;
    public static ArrayList<News> mNewsList = new ArrayList<>();
    private CardView mCardView;
    private FrameLayout mFrameLayout;
    // views
    private Button mButton_Retry;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerViewWithEmptyView mRecyclerView;
    private RequestQueue mQueue;
    private JsonObjectRequest mJsonObjectRequest;

    public static final String REQUEST_TAG = "tag";
    private static final String TYPE_PDF = "pdf";
    private static final String TYPE_URL = "url";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        mCardView = (CardView) view.findViewById(R.id.cardView);
        mFrameLayout = (FrameLayout) view.findViewById(R.id.empty_view);
        mButton_Retry = (Button) view.findViewById(R.id.button_retry);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLACK);
        //setHasOptionsMenu(true);

        mRecyclerView = (RecyclerViewWithEmptyView) view.findViewById(R.id.recyclerView_news);
        mRecyclerView.setEmptyView(view.findViewById(R.id.empty_view));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setEmptyView(MActivity.findViewById(R.id.empty_view));
        mAdapter = new RecyclerAdapterNews(getContext(), mNewsList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        syncNews();

        mButton_Retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mButton_Retry.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                syncNews();

            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                syncNews();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().restartLoader(Data.LOADER_NEWS, null, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mQueue != null){
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

    public void syncNews(){
        JSONObject json = null;
        try {
            json = new JSONObject("{\"ids\":\"\"}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(mQueue != null){
            mQueue.cancelAll(REQUEST_TAG);
        }
        mQueue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
        mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Data.URL_API_SYNC_NEWS, json,
                new Response.Listener<JSONObject>() {
                    @DebugLog
                    @Override
                    public void onResponse(JSONObject response) {
                        mProgressBar.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setRefreshing(false);
                        SyncHandler.News.processResponse(getActivity(), response);
                    }
                },
                new Response.ErrorListener() {
                    @DebugLog
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressBar.setVisibility(View.GONE);
                        mButton_Retry.setVisibility(View.VISIBLE);
                        if(mSwipeRefreshLayout.isRefreshing()){
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
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
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String order = DBHelper.NewsTable.CREATED_AT;
        CursorLoader cursorLoader = new CursorLoader(getContext(), MyContentProvider.CONTENT_URI_NEWS, null, null, null, order);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
        // check for empty cursor and toggle visibility of list
        if(c.getCount() == 0){
            mSwipeRefreshLayout.setVisibility(View.GONE);
            mFrameLayout.setVisibility(View.VISIBLE);
        }else{
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            mFrameLayout.setVisibility(View.GONE);
        }

        int i_id = c.getColumnIndexOrThrow(DBHelper.NewsTable._ID);
        int i_title = c.getColumnIndexOrThrow(DBHelper.NewsTable.TITLE);
        int i_link = c.getColumnIndexOrThrow(DBHelper.NewsTable.LINK);
        int i_created_at = c.getColumnIndexOrThrow(DBHelper.NewsTable.CREATED_AT);

        mNewsList.clear();
        while(c.moveToNext()){
            // make item object
            News item = new News(c.getString(i_id), c.getString(i_title), c.getString(i_link), null, c.getString(i_created_at));

            // adding item to array list
            mNewsList.add(item);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
