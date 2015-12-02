

package net.celloscope.customcameramoduleapp;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;

import net.celloscope.utility.AppUtils;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Size;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

/**
 * @author Zaki
 * 
 */
public class CameraPreview extends SurfaceView implements
		SurfaceHolder.Callback {

	// private static final String TAG = "CameraPreview";

	Context mContext;
	Camera mCamera;
	SurfaceHolder mHolder;
	AutoFocusCallback myAutoFocusCallback;

	public CameraPreview(Context context, Camera camera) {
		super(context);
		mContext = context;
		mCamera = camera;

		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		mHolder = getHolder();
		mHolder.addCallback(this);
		// deprecated setting, but required on Android versions prior to 3.0
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		// AppUtils.ShowToast(mContext, "Surface Created");
		try {
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();

			mCamera.autoFocus(new AutoFocusCallback() {

				@Override
				public void onAutoFocus(boolean arg0, Camera arg1) {
					// TODO Auto-generated method stub

				}
			}
		);

		} catch (IOException e) {
			AppUtils.PrintError("IOException: " + e.getMessage());
		} catch (Exception e) {
			AppUtils.PrintError("Exception: " + e.getMessage());
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		mCamera.stopPreview();
		String rotation = "";

		Display display = ((WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

		/*
		 * currently activity fixed to portrait mode. but later if we want to
		 * support landscape mode then we need to handle rotation and
		 * orientation in below cases
		 */
		switch (display.getRotation()) {
		case Surface.ROTATION_0:
			rotation = "0 degree";
			mCamera.setDisplayOrientation(90);
			break;
		case Surface.ROTATION_90:
			rotation = "90 degree";
			break;
		case Surface.ROTATION_180:
			rotation = "180 degree";
			break;
		case Surface.ROTATION_270:
			rotation = "270 degree";
			break;
		default:
			break;
		}

		AppUtils.PrintError("Surface Changed. height: " + height + " width: "
				+ width + " rotation: " + rotation);

		// set some parameters for better photo in portrait mode
		Camera.Parameters params = mCamera.getParameters();
		params.setRotation(90);

		// this sets the orientation of the image to portrait but DOES NOT
		// changes width and height
		// so the image is w:640,h:480. it just opens in portrait mode in file
		// manager

		List<Size> previewSizes = params.getSupportedPreviewSizes();

		List<Size> pictureSizes = params.getSupportedPreviewSizes();

		Camera.Size previewSize = previewSizes.get(0);

		Camera.Size pictureSize = pictureSizes.get(0);

		// Logger.AddLog(TAG, "Camrepa Parametes : Preview Size : Width :"
		// + previewSize.width + " height :" + previewSize.height
		// + " Picture Size : Width " + pictureSize.width + " Height :"
		// + height);
		//

		Camera.Size bestSize = null;
		List<Camera.Size> sizeList = mCamera.getParameters()
				.getSupportedPreviewSizes();
		// bestSize = sizeList.get(1);
		for (int i = 0; i < sizeList.size(); i++) {
			// select 720*1280 rsolution i,e 720*1280=921600
			if ((sizeList.get(i).width * sizeList.get(i).height) <= 921600) {
				bestSize = sizeList.get(i);
				Log.d("Camrara Preview: ", "Height:" + bestSize.height
						+ "Width: " + bestSize.width);
				break;
			}
		}

		List<Integer> supportedPreviewFormats = params
				.getSupportedPreviewFormats();
		Iterator<Integer> supportedPreviewFormatsIterator = supportedPreviewFormats
				.iterator();
		while (supportedPreviewFormatsIterator.hasNext()) {
			Integer previewFormat = supportedPreviewFormatsIterator.next();
			Log.d("Camrara Preview: ", "previewFormat" + previewFormat);
			if (previewFormat == ImageFormat.JPEG) {

				params.setPreviewFormat(previewFormat);
			}
		}

		params.setPreviewSize(bestSize.width, bestSize.height);
		params.setPictureSize(bestSize.width, bestSize.height);

		/*
		 * params.setPreviewSize(previewSize.width, previewSize.height);
		 * params.setPictureSize(pictureSize.width, pictureSize.height);
		 */

		// params.setPreviewSize(640, 480); // this works
		// params.setPictureSize(640, 480); // this works
		// camera assumes orientation landscape so width > height

		/* params.setJpegQuality(40); */
		params.setJpegQuality(60);
		mCamera.setParameters(params);

		try {
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		} catch (IOException e) {
			AppUtils.PrintError("IOException: " + e.getMessage());
		} catch (Exception e) {
			AppUtils.PrintError("Exception: " + e.getMessage());
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		AppUtils.PrintError("Surface Destroyed");
		mCamera.stopPreview();
		mCamera.release();
	}

}
