package nitnavigator.zeefive.com.activities;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import nitnavigator.zeefive.com.adapter.DemoPagerAdapter;
import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.utilities.Utilities;


public class ActivityDemo extends AppCompatActivity {

    AppCompatActivity activity = this;
    DemoPagerAdapter mDemoPagerAdapter;
    ViewPager mViewPager;
    private int currentBackgroundColor;
    private Button button_next;
    private Button button_skip;

    private int pageCount = 3;
    private final String BUTTON_TEXT_NEXT = "NEXT";
    private final String BUTTON_TEXT_DONE = "DONE";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        // Finding Views
        button_next = (Button) findViewById(R.id.button_demo_nextanddone);
        button_skip = (Button) findViewById(R.id.button_demo_skip);
        button_next.setText(BUTTON_TEXT_NEXT);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_next.getText() == BUTTON_TEXT_NEXT){
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);

                }else if (button_next.getText().equals(BUTTON_TEXT_DONE)){
                    goToNextActivity();
                }


            }
        });

        button_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextActivity();
            }
        });
        // make full screen
        Utilities.makeItFullScreen(activity);
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mDemoPagerAdapter = new DemoPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(mDemoPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //setBackgroundColor((int) (positionOffset * 255));
            }

            @Override
            public void onPageSelected(int position) {
                changeBackgroundColor(position);
                ImageView imageView = (ImageView)activity.findViewById(R.id.imageView_indicatorDemo);
                imageView.setImageResource(Data.id_drawable_viewpager_indicator[position]);


                // changing the Next_Button_text depending on the position of the page
                if(mViewPager.getCurrentItem() == (pageCount - 1)){
                    button_next.setText(BUTTON_TEXT_DONE);
                }else{
                    button_next.setText(BUTTON_TEXT_NEXT);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    private void goToNextActivity(){
        // here I should register the first start up is over in the preferences
        // intent to start new Activity
        Intent intent = new Intent(activity, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setBackgroundColor(int percentage){
        //Log.d("Riyas", String.valueOf(percentage));
        if(percentage != 0) {
            mViewPager.setBackgroundColor(Color.rgb(0,204,percentage));
        }
        //mViewPager.setBackgroundColor(Color.rgb());
    }
    private void setOffsetForThings(int percentage){
        //mViewPager.getChildAt(0).findViewById(R.id.imageview_chickenlogo).setTranslationX(percentage*2);
    }
    private void changeBackgroundColor(int position){

        int colorFrom = 0;
        int colorTo = 0;
        if(position == 0 && currentBackgroundColor == 1) {
            colorFrom = getResources().getColor(R.color.color_control_highlight);
            colorTo = getResources().getColor(R.color.blue);
        }else if(position == 1 && currentBackgroundColor == 0){
            colorFrom = getResources().getColor(R.color.blue);
            colorTo = getResources().getColor(R.color.color_control_highlight);
        }
        else if(position == 2 && currentBackgroundColor == 1) {
            colorFrom = getResources().getColor(R.color.color_control_highlight);
            colorTo = getResources().getColor(R.color.skyblue);
        }else if(position == 1 && currentBackgroundColor == 2){
            colorFrom = getResources().getColor(R.color.skyblue);
            colorTo = getResources().getColor(R.color.color_control_highlight);
        }

            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    mViewPager.setBackgroundColor((Integer)animator.getAnimatedValue());
                }

            });
            colorAnimation.setDuration(1000);
            colorAnimation.start();

        currentBackgroundColor = position;

    }

    public ViewPager getViewPager(){
        return this.mViewPager;
    }
}
