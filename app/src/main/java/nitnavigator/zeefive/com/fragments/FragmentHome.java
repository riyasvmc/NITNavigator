package nitnavigator.zeefive.com.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import nitnavigator.zeefive.com.activities.MainActivity;
import nitnavigator.zeefive.com.adapter.HomeMenuAdapter;
import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.main.R;


public class FragmentHome extends Fragment {

    public MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity) getActivity();
        setListViewAdapter();
    }

    public void setListViewAdapter(){
        GridView gridView = (GridView) getActivity().findViewById(R.id.gridView_home);
        gridView.setAdapter(new HomeMenuAdapter(activity, android.R.layout.simple_list_item_1, Data.HOME_MENU));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setViewPagerCurrentItem(position + 1);
            }
        });

    }

    public void setViewPagerCurrentItem(int position){
        activity.getViewPager().setCurrentItem(position);
    }
}
