package net.celloscope.utility;

import java.math.BigDecimal;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

public class Utils {
	public static ArrayAdapter<String> spinnerAdapter ;
	public static ArrayAdapter<String> signatoryAdapter; 
	public static boolean isAppSentToBackground(final Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (!topActivity.getPackageName().equals(context.getPackageName())) {
				Log.w(" YES TRUE ", "isAppSentToBackground ");
				return true;
			}
		}
		
		
		
		
		return false;
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}

}
