package net.celloscope.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class Validator {

	static final String REGEX_EMAIL = android.util.Patterns.EMAIL_ADDRESS
			.pattern();
	static final String REGEX_NATIONAL_ID = "[0-9]+";
	// static final String REGEX_PHONE_NO = "[0-9]{11}";
	static final String REGEX_PHONE_NO = "[+][0-9]{13}";
	static final String REGEX_PASSPORT = "[a-zA-Z]{1,2}[0-9]{7}";

	public static boolean isEmailValid(String email) {
		// if (email.length() == 0
		// || email.compareToIgnoreCase(Constants.NOT_AVAILABLE) == 0) {
		// return true;
		// } else {

		return isValid(email, REGEX_EMAIL);
		// }
	}

	public static boolean IsMobileNumberValid(String mobileNumber) {

		return isValid(mobileNumber, REGEX_PHONE_NO);
	}

	/**
	 * nationalId is either 13 or 17 digits long
	 * 
	 * @param nationalID
	 * @return true or false
	 */
	public static boolean isNationalIDValid(String nationalID) {

		return (nationalID.length() == 13 || nationalID.length() == 17)
				&& isValid(nationalID, REGEX_NATIONAL_ID);
	}

	/**
	 * passport must have 1 or 2 character at the beginning and 7 digits
	 * 
	 * @param PassportNo
	 * @return
	 */
	public static boolean isPassportNoValid(String PassportNo) {

		return isValid(PassportNo, REGEX_PASSPORT);

	}

	public static boolean isInputValid(String Text) {

		String patternForSqlInjection = "\\b(ALTER|CREATE|DELETE|DROP|EXEC(UTE){0,1}|INSERT( +INTO){0,1}|MERGE|SELECT|UPDATE|UNION( +ALL){0,1})\\b";

		Pattern pattern = Pattern.compile(patternForSqlInjection);
		Matcher matcher = pattern.matcher(Text);

		return !matcher.matches();

	}

	public static boolean isTINValid(String TIN) {

		if (TIN.trim().length() > 0)
			return true;
		else
			return false;

	}

	public static boolean isBirthRegNoValid(String birthRegNo) {

		if (birthRegNo.trim().length() > 0)
			return true;
		else
			return false;

	}

	public static boolean isDrivingLicenseValid(String drivingLicense) {

		return true;

	}

	private static boolean isValid(String value, String regEx) {

		Pattern pattern;
		Matcher matcher;

		pattern = Pattern.compile(regEx);
		matcher = pattern.matcher(value);

		return matcher.matches();
	}

	public static boolean validateQRcode(String qrCode) {

		if (qrCode.trim().length() > 0)
			return true;
		else
			return false;

	}

	public static boolean validateAccountNo(String accountNo) {

		if (accountNo.length() == 15)
			return true;
		else
			return false;

	}
	
	public static boolean validateChequeTrackingID(String trackingID) {

		if (trackingID.length() == 12)
			return true;
		else
			return false;

	}


}
