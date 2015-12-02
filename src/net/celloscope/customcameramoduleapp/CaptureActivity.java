package net.celloscope.customcameramoduleapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.example.aelcameraapp.R;

import net.celloscope.utility.AppUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.RelativeLayout;

public class CaptureActivity extends Activity {

	public static final String TAG = "CaptureActivity";
	public static final String IMAGE_PATH = "imagepath";

	Context mContext;
	Camera mCamera;
	CameraPreview mCameraPreview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture);

		mContext = this;
		mCamera = getCamera();
		mCameraPreview = new CameraPreview(mContext, mCamera);

		RelativeLayout previewFrame = (RelativeLayout) findViewById(R.id.camera_preview);
		previewFrame.addView(mCameraPreview);

		// need to bring the child views infront of preview surface view
		ImageView photoGuide = ((ImageView) previewFrame.findViewById(R.id.image_grid));
		photoGuide.bringToFront();

		RelativeLayout rlBottomPanel = (RelativeLayout) findViewById(R.id.rlBottomPanel);
		rlBottomPanel.bringToFront();

		Button btnCapture = (Button) findViewById(R.id.btnCapture);
		btnCapture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCamera.takePicture(null, null, pictureCallback);
			}
		});

		Button btnCancel = (Button) findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	Camera getCamera() {
		Camera camera = null;
		try {
			camera = Camera.open();
		} catch (Exception e) {
			// cannot get camera or does not exist
			Log.d(TAG, "Could not access camera");
		}
		return camera;
	}

	PictureCallback pictureCallback = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			Log.d(TAG, "picture Taken: " + data.toString());
			File pictureFile = AppUtils.getImageFile();
			if (pictureFile == null) {
				return;
			}

			try {
				// save image
				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();

				// crop the image and return crop image location to the caller
				// activity
				ReturnActivityResult(cropImage(pictureFile.getAbsolutePath()));
				// ReturnActivityResult(pictureFile.getAbsolutePath());

			} catch (FileNotFoundException e) {
				AppUtils.PrintError("FileNotFoundException: " + e.getMessage());
			} catch (IOException e) {
				AppUtils.PrintError("IOException: " + e.getMessage());
			}

		}
	};

	private String cropImage(String path) {
		Bitmap originalImage = BitmapFactory.decodeFile(path);
		Bitmap scaledImage = null;
		Bitmap cropImage = null;

		int oWidth = originalImage.getWidth();
		int oHeight = originalImage.getHeight();
		Log.d(TAG, "Image Height and Weight: " + oHeight + " " + oWidth);

		int dim = calculateScalingDimension(oWidth, oHeight);
		Log.d(TAG, "DIM: " + dim);

		List<Camera.Size> sizeList = mCamera.getParameters().getSupportedPreviewSizes();
		Log.d(TAG, "Height=: " + sizeList.get(0).height + "Weight=" + sizeList.get(0).width);
		if ((sizeList.get(0).height) * (sizeList.get(0).width) == 921600) {
			// dim-90 this code added for remove scalling problem
			// select 720*1280 rsolution i,e 720*1280=921600
			Log.d(TAG, "Inside dim check 921600");
			dim = dim - 90;
		} else if ((sizeList.get(0).height) * (sizeList.get(0).width) == 384000) {
			// dim-50 this code added for remove scalling problem
			// select 480*800 rsolution i,e 720*1280=921600
			Log.d(TAG, "Inside dim check 384000");
			dim = dim - 50;
		}

		if (oWidth > oHeight) {
			// land//1440 x 2560

			scaledImage = Bitmap.createScaledBitmap(originalImage, 2560, 1440, false);
			// scaledImage = Bitmap.createScaledBitmap(originalImage,
			// AppUtils.PHOTO_DIMENSION,AppUtils.PHOTO_DIMENSION, false);
			Log.d(TAG, "Scalled Image Height and Weight: " + scaledImage.getHeight() + " " + scaledImage.getWidth());

		} else {
			// portrait
			scaledImage = Bitmap.createScaledBitmap(originalImage, 1440, 2560, false);
			Log.d(TAG, "Scalled Image Height and Weight: " + scaledImage.getHeight() + " " + scaledImage.getWidth());
		}

		// scale down

		// rotate and crop
		Matrix matrix = new Matrix();
		if (oWidth < oHeight)
			matrix.postRotate(0);
		else
			matrix.postRotate(90);

		if (oWidth > oHeight) {
			cropImage = Bitmap.createBitmap(scaledImage, 0, 0, 2560, 1440, matrix, false);
			Log.d(TAG, "cropImage Image Height and Weight: " + cropImage.getHeight() + " " + cropImage.getWidth());
		} else {
			cropImage = Bitmap.createBitmap(scaledImage, 0, 0, 1440, 2560, matrix, false);
			Log.d(TAG, "cropImage Image Height and Weight: " + cropImage.getHeight() + " " + cropImage.getWidth());
		}

		FileOutputStream fos = null;
		try {
			File cImage = AppUtils.getCroppedImageFile();
			fos = new FileOutputStream(cImage);
			cropImage.compress(CompressFormat.JPEG, 50, fos);

			return cImage.getAbsolutePath();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}
	}

	void ReturnActivityResult(String imagePath) {
		Intent data = new Intent();
		data.putExtra(IMAGE_PATH, imagePath);
		if (getParent() == null) {
			setResult(Activity.RESULT_OK, data);
		} else {
			getParent().setResult(Activity.RESULT_OK, data);
		}
		finish();
	}

	private int calculateScalingDimension(int width, int height) {

		int dim = AppUtils.PHOTO_DIMENSION;

		if (width > height) {
			// this should always be the case since we have set the
			// (width,height) to (640, 480)
			dim = (int) Math.floor((width * AppUtils.PHOTO_DIMENSION) / height);

		} else {
			dim = (int) Math.floor((height * AppUtils.PHOTO_DIMENSION) / width);
		}

		return dim;
	}
}
