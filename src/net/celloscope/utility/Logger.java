package net.celloscope.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Environment;

import android.util.Log;

public class Logger {

	public static void AddLog(String tag, String message) {

		if (!Configuration.PRODUCTION_MODE) {
			Log.d(tag, message);
		}

	//	printLog(tag, message);

	}

	public static void printLog(String tag, String message) {

		File logFile = new File(Environment.getExternalStorageDirectory()
				+ "/OperationLog/" + getLogFileName());
		Log.d("sdsdsdsdsd", getLogFileName());
		Log.d("sdsdsdsdsd", logFile.getName());

		if (!logFile.exists()) {
			try {

				logFile.createNewFile();
				AppUtils.LOG_FILE_NAME = logFile.getName();
				Log.d("sdsd+++", AppUtils.LOG_FILE_NAME);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			if (calculateFileSize(logFile) <= 5) {
				Log.d("sdsd+++", AppUtils.LOG_FILE_NAME);
				BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,
						true));
				buf.append(tag + "-->" + message);
				buf.newLine();
				buf.close();

			}

			else {

				Log.d("sdsdsdasddsadsad", logFile.getName());
				String[] n = logFile.getName().split("-");

				Log.d("sdsdsdasddsadsad++", "N=" + n.length);

				if (n.length > 3) {
					int no = Integer.valueOf(n[3]);
					no = no + 1;

					StringBuilder fileNameBuilder = new StringBuilder("");

					fileNameBuilder.append(n[0]);
					fileNameBuilder.append("_");
					fileNameBuilder.append(n[1]);
					fileNameBuilder.append("_");
					fileNameBuilder.append(n[2]);
					fileNameBuilder.append("_");
					fileNameBuilder.append(no);

					logFile = new File(
							Environment.getExternalStorageDirectory()
									+ "/OperationLog/"
									+ fileNameBuilder.toString());

					if (!logFile.exists()) {
						try {
							logFile.createNewFile();
							AppUtils.LOG_FILE_NAME = logFile.getName();

						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String getLogFileName() {

		File logFile = new File(Environment.getExternalStorageDirectory()
				+ "/OperationLog/");
		if (logFile.isDirectory() && logFile.listFiles().length != 0) {

			if (AppUtils.LOG_FILE_NAME.length() == 0) {

				AppUtils.LOG_FILE_NAME = lastFileModified(
						Environment.getExternalStorageDirectory()
								+ "/OperationLog/").getName();

				return AppUtils.LOG_FILE_NAME;
			}

		} else {
			try {

				logFile.mkdirs();

				logFile = new File(Environment.getExternalStorageDirectory()
						+ "/OperationLog/" + "ol" + "_" + AppUtils.MOBILE_IMEI
						+ "_" + AppUtils.getSystemDateTime("yyyy-MMM-dd") + "_"
						+ "1.txt");
				// logFile.createNewFile();

				AppUtils.LOG_FILE_NAME = logFile.getName();

				return logFile.getName();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return AppUtils.LOG_FILE_NAME;

	}

	public static int calculateFileSize(File file) {
		int file_size = 0;
		file_size = Integer.parseInt(String.valueOf(file.length() / 1024));

		Log.d("kksds", "" + file_size);
		return file_size;

	}

	public static File lastFileModified(String dir) {
		File fl = new File(dir);
		File[] files = fl.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		long lastMod = Long.MIN_VALUE;
		File choice = null;
		for (File file : files) {
			if (file.lastModified() > lastMod) {
				choice = file;
				lastMod = file.lastModified();
			}
		}
		return choice;
	}

	public static boolean isFolderEmpty() {
		return false;

	}

}
