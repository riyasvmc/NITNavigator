package nitnavigator.zeefive.com.activities;

import android.content.Intent;
import android.os.Bundle;

import nitnavigator.zeefive.com.main.R;
import nitnavigator.zeefive.com.utilities.Utilities;

public class ActivitySplash extends ActivityCustom {

    private ActivityCustom activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Utilities.makeItFullScreen(activity);

        Thread thread = new Thread(){
            public void run(){
                try{
                    Thread.sleep(700);

                    // Check it is first run or not.
                    if(Utilities.showDemo(activity)) {
                        Intent intent = new Intent(getBaseContext(), ActivityDemo.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }

                    finish();

                }catch (Exception e){

                }
            }
        };
        thread.start();
    }
}
