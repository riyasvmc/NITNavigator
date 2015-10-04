package nitnavigator.zeefive.com.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import nitnavigator.zeefive.com.main.R;

public class FragmentDemo1 extends Fragment {

    public static final String ARG_OBJECT = "object";
    private ActionBarActivity activity;
    private Button button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(R.layout.fragment_demo_1, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
/*

        final ValueAnimator anim = ValueAnimator.ofInt(0, 10);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ((TextView) getActivity().findViewById(R.id.app_title)).setText(String.valueOf((int)animation.getAnimatedValue()));
            }
        });
        anim.setDuration(10000);
        anim.start();
*/

    }
}
