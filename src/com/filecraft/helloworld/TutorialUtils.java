package com.filecraft.helloworld;

import android.content.res.Resources;

public class TutorialUtils {
	private static final String PACKAGE_NAME = MyApplication.getAppContext().getPackageName();
	private static final Resources RESOURCES = MyApplication.getAppContext().getResources();

	public static String getResourceFilePath(int resourceId) {
		return "android.resource://" + PACKAGE_NAME + "/" + resourceId;
	}

	public static String getString(int stringId) {
		return RESOURCES.getString(stringId);
	}
}
