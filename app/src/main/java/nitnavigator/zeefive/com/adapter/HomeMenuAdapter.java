package nitnavigator.zeefive.com.adapter;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import nitnavigator.zeefive.com.data.Data;
import nitnavigator.zeefive.com.main.R;

//CLASS: ArrayAdapter for MenuItems.
public class HomeMenuAdapter extends ArrayAdapter<String> {

    private AppCompatActivity activity;
    private View view;
    private String[] list;

    //Constructor
    public HomeMenuAdapter(AppCompatActivity activity, int resource, String[] list) {
        super(activity, resource, list);
        this.activity = activity;
        this.list = list;
    }

    //View Generator
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        view = convertView;

        if(view == null){
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.listitem_homeicon, null);
        }

        TextView textView_title = (TextView) view.findViewById(R.id.title);
        ImageView imageView_icon = (ImageView) view.findViewById(R.id.imageView_icon);

        String title = list[position];
        textView_title.setText(title);
        imageView_icon.setImageResource(Data.id_home_icons[position]);



        return view;
    }



}
