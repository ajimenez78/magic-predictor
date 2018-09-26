package it.feio.android.omninotes.helpers;

import android.app.Activity;
import android.app.Application;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.Log;
import it.feio.android.omninotes.utils.Constants;
import org.piwik.sdk.Piwik;
import org.piwik.sdk.Tracker;

import java.net.MalformedURLException;


public class AnalyticsHelper {


	private final static String ANALYTICS_URL = "http://www.iosue.it/federico/analytics/piwik.php";

	private static Tracker tracker;

	public enum CATEGORIES {ACTION, SETTING, UPDATE}


	public static void init(Application application) {
		if (tracker == null) {
			try {
				tracker = Piwik.getInstance(application).newTracker(ANALYTICS_URL, 1);
				tracker.setUserId(Settings.Secure.getString(application.getContentResolver(), Settings.Secure
						.ANDROID_ID));
				tracker.trackAppDownload();
			} catch (MalformedURLException e) {
				Log.e(Constants.TAG, "Malformed url to get analytics tracker", e);
			}
		}
	}


	public static Tracker getTracker() {
		return tracker;
	}


	public static void trackScreenView(String screenName) {
		checkInit();
		tracker.trackScreenView(screenName);
	}


	public static void trackEvent(CATEGORIES category, String action) {
		checkInit();
		tracker.trackEvent(category.name(), action);
	}


	public static void trackActionFromResourceId(Activity activity, int resourceId) {
		checkInit();
		try {
			tracker.trackEvent(CATEGORIES.ACTION.name(), activity.getResources().getResourceEntryName(resourceId));
		} catch (Resources.NotFoundException e) {
			Log.w(Constants.TAG, "No resource name found for request id");
		}
	}


	private static void checkInit() {
		if (tracker == null) {
			throw new NullPointerException("Call AnalyticsHelper.init() before using analytics tracker");
		}
	}
}