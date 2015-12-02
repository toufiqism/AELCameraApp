package net.celloscope.utility;


import android.bluetooth.BluetoothAdapter;

//import com.evolute.mbs.Log;

public class BTUtility {

	public static final String BT_STATUS = "BT_STATUS";
	public static final String BT_SUPPORTED = "BT_SUPPORTED";
	public static final String BT_NOT_SUPPORTED = "BT_NOT_SUPPORTED";
	public static final String BT_ENABLED = "BT_ENABLED";
	public static final String BT_NOT_ENABLED = "BT_NOT_ENABLED";
	
	public static final int REQUEST_ENABLE_BT = 1001;


	
	public static final String MSG_ENABLE_BT = "Please turn on BlueTooth first!";
	
	private static BluetoothAdapter btAdapter = null;
	private static boolean isClientDeviceSupportBT = false;
	private static boolean isClientDeviceBTEnabled = false;
	
	
	public static boolean checkBTSupport() {
		
		btAdapter = BluetoothAdapter.getDefaultAdapter();
				
		if (btAdapter != null) 
		{ 
			//Logger.AddLog("BTUtility-->checkBTSupport", "\nBluetooth IS supported. Continuing"); 
			isClientDeviceSupportBT = true;
		} else 
		{
			//Logger.AddLog("BTUtility-->checkBTSupport", "\nBluetooth NOT supported. Aborting."); 
			isClientDeviceSupportBT = false;
		}
		return isClientDeviceSupportBT;
	}
	
	public static boolean checkIfBTEnabled() {
		// checks again if BT is supported on this device. 
		// this also allows us to check btAdapter has been initialized at least once
		if (checkBTSupport()) {
			
			if (btAdapter.isEnabled()){
			//	Logger.AddLog("BTUtility-->checkIfBTEnabled", "\nBluetooth is enabled...");
				isClientDeviceBTEnabled = true;
			} else {
			//	Logger.AddLog("BTUtility-->checkIfBTEnabled", "\nBluetooth is NOT enabled...");
				isClientDeviceBTEnabled = false;
			}		
		} 		
		
		return isClientDeviceBTEnabled;
	}


}
