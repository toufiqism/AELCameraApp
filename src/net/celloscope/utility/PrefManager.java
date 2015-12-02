package net.celloscope.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefManager {

	public static boolean isInitialized = false;

	public static final String AGENT_ID = "agentID";
	public static final String FPS_DEVICE_ID = "fpsDeviceID";
	public static final String BANK_ID = "bankID";
	public static final String BRANCH_ID = "branchID";

	public static final String USER_NAME = "userName";
	public static final String PASSWORD = "password";
	public static final String FINGERPRINT_ENROLLMENT_ORDER = "fingerPrintEnrolmentOrder";
	public static final String FPSCAN_RETRY_VALUE = "fpscanRetryValue";

	public static String IS_FOR_BANK_OFFICER = "isForBankOfficer";
	public static String ROLE = "role";

	public static final String TO_ACCOUNT_HOLDER_NAME = "toAccountHolderName";
	public static String ACCOUNT_JSON = "AccountJSON";
	public static final String FP_DATA = "fpdata";
	public static final String IMAGE_DATA = "image_data";
	public final static String DEFAULT_FP = "defaultFP";
	public final static String DEFAULT_FP_VALUE = "defaultFPValue";
	public final static String MAG_CARD_NO = "magCardNo";
	public final static String MAG_CARD_HOLDER_NAME = "magCardHolderName";
	
	public final static String CUSTOMER_ID = "customerID";
	public final static String PRODUCT_ID = "productID";
	public final static String ACCOUNT_HOLDER_NAME = "accountHolderName";
	public final static String NID = "nationalIdNo";
	public final static String  PASSPORT_NO= "passportNO";
	public final static String DRIVING_LICENSE_NO = "drivingLicenseNo";
	public final static String EMAIL  = "email";
	public final static String MOBILE_NO  = "mobileNo";
	public final static String PRODUCT_DETAIL  = "productDetail";
	public final static String PIN  = "pin";
	public final static String ACCOUNT_LIST  = "accountList";
	public final static String FDR_AMOUNT  = "fdrAmount";
	public final static String DCODE  = "dCode";
	public final static String ACCOUNT_ID  = "accountID";
	public final static String SELECTED_ACCOUNT_ID  = "selectedAccountID";
	public final static String CASH_DEPOSIT  = "CashDeposit";
	public final static String CASH_WITHDRAW  = "CashWithdraw";
	public final static String MINI_STATEMENT  = "MiniStatement";
	public final static String BALANCE_INQUIRY  = "BalanceInquiry";
	public final static String ACC_ACTIVATION  = "AccActivation";
	public final static String PRODUCT_DETAIL_FDR  = "ProductDetailFDR";
	public final static String PRODUCT_DETAIL_DPS  = "ProductDetailDPS";
	public final static String DESTINATION_ACC_NO  = "DestinationAccNo";
	public final static String DESTINATION_ACC_DCODE  = "DestinationAccNoDCode";
	public static final String  ACCOUNT_INFO_ACCOUNT_ID = "AccountInfoAccountID";
	
	

	
		
	private static SharedPreferences pref = null;

	public static String TAG = PrefManager.class.getSimpleName();

	public static void initialize(Context context) {
		pref = PreferenceManager.getDefaultSharedPreferences(context);
		isInitialized = true;
	}

	public SharedPreferences getDefaultPref() {
		return pref;
	}

	public static void putData(String key, String data) {
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(key, data);
		editor.commit();

	
	}

	public static String getData(String key) {
		return pref.getString(key, null);
	}

	public static void clearData(String key) {		
		SharedPreferences.Editor editor = pref.edit();
		editor.remove(key);
		editor.commit();
		
	}

	public static void clearAgentData() {
		clearData(AGENT_ID);
		clearData(ROLE);
		clearData(BANK_ID);
		clearData(BRANCH_ID);
		clearData(FPS_DEVICE_ID);
		clearData(IS_FOR_BANK_OFFICER);
		clearData(USER_NAME);
		clearData(PASSWORD);
		clearData(FINGERPRINT_ENROLLMENT_ORDER);
		clearData(FPSCAN_RETRY_VALUE);
	
	}

	public static void clearCustomerData() {
		clearData(DEFAULT_FP);
		clearData(DEFAULT_FP_VALUE);
		clearData(FP_DATA);
		clearData(IMAGE_DATA);
		clearData(TO_ACCOUNT_HOLDER_NAME);
		clearData(ACCOUNT_JSON);
		clearData(MAG_CARD_NO);
		clearData(MAG_CARD_HOLDER_NAME);
		
	}

	public static void showCurrentPrefManagerData(String tag) {

		// Logger.AddLog(TAG, tag);
		Logger.AddLog(TAG, "=====Current All Preference Data=== " + tag);
		Logger.AddLog(TAG, "USER_NAME" + " : " + getData(USER_NAME));
		Logger.AddLog(TAG, "ROLE" + " : " + getData(ROLE));
		Logger.AddLog(TAG, "AGENT-ID" + " : " + getData(AGENT_ID));
		Logger.AddLog(TAG, "FPS_DEVICE_ID" + " : " + getData(FPS_DEVICE_ID));
		Logger.AddLog(TAG, "BANK_ID" + " : " + getData(BANK_ID));
		Logger.AddLog(TAG, "BRANCH_ID" + " : " + getData(BRANCH_ID));
		Logger.AddLog(TAG, "FP_DATA" + " : " + getData(FP_DATA));
		Logger.AddLog(TAG, "FingerPrintEnrollmentOrder" + " : "
				+ getData(FINGERPRINT_ENROLLMENT_ORDER));
		Logger.AddLog(TAG, "FPScanRetryValue" + " : "
				+ getData(FPSCAN_RETRY_VALUE));
		Logger.AddLog(TAG, "DefaultFP" + " : " + getData(DEFAULT_FP));
		Logger.AddLog(TAG, "DefaultFPValue" + " : " + getData(DEFAULT_FP_VALUE));
		
		Logger.AddLog(TAG, "Mag Card No" + " : " + getData(MAG_CARD_NO));
		Logger.AddLog(TAG, "Mag Card Holder Name " + " : " + getData(MAG_CARD_HOLDER_NAME));

		Logger.AddLog(TAG, "IMAGE_DATA" + " : " + getData(IMAGE_DATA));
		Logger.AddLog(TAG, "IS_FOR_BANK_OFFICER" + " : "
				+ getData(IS_FOR_BANK_OFFICER));
		Logger.AddLog(TAG, "TO_ACCOUNT_HOLDER_NAME" + " : "
				+ getData(TO_ACCOUNT_HOLDER_NAME));
		Logger.AddLog(TAG, "ACCOUNT_JSON" + " : " + getData(ACCOUNT_JSON));
		
		
		
		Logger.AddLog(TAG, "=====Current All Preference Data====\n");

	}
}
