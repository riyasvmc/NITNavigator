package nitnavigator.zeefive.com.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nitnavigator.zeefive.com.adapter.NewsRecyclerAdapter;
import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.data.News;
import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.utilities.VolleySingleton;
import nitnavigator.zeefive.com.view.RecyclerViewWithEmptyView;

public class FragmentNews extends Fragment {

    private SimpleCursorAdapter dataAdapter;
    private NewsRecyclerAdapter recyclerAdapter;

    private ProgressBar progressBar = null;

    public static ArrayList<News> newsList = new ArrayList<News>();
    private Button button_retry;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RequestQueue queue;
    private StringRequest stringRequest;
    public static final String REQUEST_TAG = "tag";
    private static final String TYPE_PDF = "pdf";
    private static final String TYPE_URL = "url";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        button_retry = (Button) view.findViewById(R.id.button_retry);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setColorSchemeColors(Color.BLACK);
        //setHasOptionsMenu(true);
        RecyclerViewWithEmptyView recyclerView = (RecyclerViewWithEmptyView) view.findViewById(R.id.recyclerView);
        recyclerView.setEmptyView(view.findViewById(R.id.empty_view));
        setRecyclerView(recyclerView);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        refreshNews(Data.NEWS_URL);

        button_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button_retry.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                refreshNews(Data.NEWS_URL);

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNews(Data.NEWS_URL);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(queue != null){
            queue.cancelAll(REQUEST_TAG);
        }
    }

    public void refreshNews(String url){
        if(queue != null){
            queue.cancelAll(REQUEST_TAG);
        }
        queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        swipeRefreshLayout.setRefreshing(false);
                        processJsonNews(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        button_retry.setVisibility(View.VISIBLE);
                        if(swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        if(error instanceof NoConnectionError) {
                            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        stringRequest.setTag(REQUEST_TAG);
        queue.add(stringRequest);
    }

    private void processJsonNews(String string){
        newsList.clear();
        try {
            JSONObject jsonObject_response = new JSONObject(string);
            JSONArray jsonArray_news = jsonObject_response.getJSONArray("news");
            for(int i = 0; i<jsonArray_news.length(); i++){
                JSONObject singleNews = jsonArray_news.getJSONObject(i);
                String id = singleNews.getString("id");
                String title = singleNews.getString("title");
                String link = singleNews.getString("link");
                String md5 = singleNews.getString("md5");
                String type = getTypeOfLink(link);
                String created_at = singleNews.getString("created_at");
                newsList.add(new News(id, title, link, md5, created_at, type));
            }
            recyclerAdapter.notifyDataSetChanged();
        }catch (JSONException e){
        }
    }

    private String getTypeOfLink(String link) {
        if(link.toLowerCase().endsWith(".pdf")){
            return TYPE_PDF;
        }else{
            return TYPE_URL;
        }
    }

    private void setRecyclerView(RecyclerViewWithEmptyView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setEmptyView(MActivity.findViewById(R.id.empty_view));
        recyclerAdapter = new NewsRecyclerAdapter(newsList);
        recyclerView.setAdapter(recyclerAdapter);
        //recyclerView.setAdapter(new NewsListAdapter());
        /*recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = (News) parent.getAdapter().getItem(position);
                String url = news.getLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });*/
    }
}
