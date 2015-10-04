package nitnavigator.zeefive.com.listener;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

public class MyOnClickListenerForAlertDialog implements OnClickListener{

	Intent intent;
	Activity activity;
	public MyOnClickListenerForAlertDialog(Activity activity, Intent intent){
		this.intent = intent;
		this.activity = activity;
	}
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		activity.startActivity(intent);
	}

}
