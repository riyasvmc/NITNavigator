package nitnavigator.zeefive.com.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

;import nitnavigator.zeefive.com.main.R;


public class FragmentDemo3 extends Fragment {

    private ActionBarActivity activity;
    private Button button;

    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_demo_3, container, false);
        return rootView;
    }
}
