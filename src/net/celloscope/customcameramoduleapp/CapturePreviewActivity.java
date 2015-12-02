package net.celloscope.customcameramoduleapp;

import net.celloscope.utility.AppUtils;
import net.celloscope.utility.BTUtility;
import net.celloscope.utility.Constants;
import net.celloscope.utility.StorageUtils;
import net.celloscope.utility.Utils;

import com.example.aelcameraapp.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class CapturePreviewActivity extends Activity {

	Context mContext;
	Button btnCaptureActivity, btnOK, btnConnectionInCapturePreview, btnLogOutInCapturePreview;
	ImageView imgPhoto;
	BroadcastReceiver receiver;

	private static String ImagePath = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture_preview);

		btnConnectionInCapturePreview = (Button) findViewById(R.id.btnConnectionInCapturePreview);
		mContext = getApplicationContext();

		btnLogOutInCapturePreview = (Button) findViewById(R.id.btnLogOutInCapturePreview);

		btnLogOutInCapturePreview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});

		btnConnectionInCapturePreview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		imgPhoto = (ImageView) findViewById(R.id.imgPhoto);

		String currentImage = getIntent().getStringExtra(CaptureActivity.IMAGE_PATH);

		if (currentImage != null) {

			imgPhoto.setImageBitmap(BitmapFactory.decodeFile(currentImage));
		} else {

			imgPhoto.setImageDrawable(getResources().getDrawable(R.drawable.noimageavailable));
		}

		btnCaptureActivity = (Button) findViewById(R.id.btnCaptureActivity);
		btnCaptureActivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (StorageUtils.IsSdCardReady()) {
					// CameraUtils.ShowToast(getApplicationContext(),
					// "Sd card ready!");

					Intent captureActivity = new Intent(mContext, CaptureActivity.class);
					startActivityForResult(captureActivity, Constants.REQUEST_CAPTURE_IMAGE);
				} else {
					AppUtils.ShowToast(mContext, "Sd card not ready!");
				}
			}
		});

		btnOK = (Button) findViewById(R.id.btnOk);
		btnOK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ReturnActivityResult(ImagePath);
				// AppUtils.ShowToast(mContext,
				// "Returning to caller with image: "
				// + ImagePath);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constants.REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {

			ImagePath = data.getStringExtra(CaptureActivity.IMAGE_PATH);
			// CameraUtils.ShowToast(mContext, "ImagePath: " + imagePath);

			if (ImagePath == null) {
				AppUtils.ShowToast(mContext, "Picture not taken. please try again");
			} else {
				imgPhoto.setImageBitmap(BitmapFactory.decodeFile(ImagePath));

				System.gc();
				// imgPhoto.setImageURI(Uri.parse(new
				// File(ImagePath).toString()));

			}
		}
	}

	void ReturnActivityResult(String imagePath) {
		Intent data = new Intent();
		data.putExtra(CaptureActivity.IMAGE_PATH, imagePath);
		if (getParent() == null) {
			setResult(Activity.RESULT_OK, data);
		} else {
			getParent().setResult(Activity.RESULT_OK, data);
		}
		finish();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		// FPSManager.checkDeviceStatus(FingerPrintCaptureActivity.this);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		if (isFinishing()) {
			finish();
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// /super.onBackPressed();
	}

}
