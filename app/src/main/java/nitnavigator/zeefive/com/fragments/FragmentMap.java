package nitnavigator.zeefive.com.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.view.TouchImageView;


public class FragmentMap extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TouchImageView view = new TouchImageView(getActivity());
        view.setImageResource(R.drawable.ic_search_white_24dp);
        view.setMaxZoom(10f);


        return view;
    }
}
