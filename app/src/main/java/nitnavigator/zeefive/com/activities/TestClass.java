package nitnavigator.zeefive.com.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import nitnavigator.zeefive.com.main.R;

/**
 * Created by Riyas V on 4/12/2016.
 * This is a test Class.
 */
public class TestClass extends ActivityCustom{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "Hello World!", Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case 1:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
