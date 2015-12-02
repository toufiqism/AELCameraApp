package net.celloscope.utility;

import android.os.Environment;

public class StorageUtils {
	public static Boolean IsSdCardReady(){
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
}
