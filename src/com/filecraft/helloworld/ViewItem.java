package com.filecraft.helloworld;

import com.filecraft.helloworld.FileCraftContract.ViewTable.ViewType;

public class ViewItem {

	public enum ViewItemId {
		WEB_GITHUB_TUTORIAL,
		WEB_ANDROID_SETUP,
		WEB_CONTENTPROVIDER_DOCUMENTATION,
		WEB_GOOGLE_PLAY,
		WEB_GITHUB;

		public static ViewItemId getItemId(String actionId) {
			for (ViewItemId id : ViewItemId.values()) {
				if (id.name().equals(actionId)) {
					return id;
				}
			}
			return null;
		}
	}

	/**
	 * Uri to VIEW
	 */
	public final String uri;

	/**
	 * Type of view action to perform.
	 */
	public final ViewType type;

	private ViewItem(String uri, ViewType type) {
		this.uri = uri;
		this.type = type;
	}

	public static ViewItem getViewItem(String actionId) {
		ViewItemId id = ViewItemId.getItemId(actionId);
		switch (id) {
		case WEB_GITHUB_TUTORIAL:
			return new ViewItem("https://github.com/b3ntt1nc4n/filecraft-contentprovider-intro", ViewType.STANDARD);
		case WEB_ANDROID_SETUP:
			return new ViewItem("http://developer.android.com/training/basics/firstapp/index.html", ViewType.STANDARD);
		case WEB_CONTENTPROVIDER_DOCUMENTATION:
			return new ViewItem("http://developer.android.com/guide/topics/providers/content-providers.html", ViewType.STANDARD);
		case WEB_GOOGLE_PLAY:
			return new ViewItem("https://play.google.com/store", ViewType.STANDARD);
		case WEB_GITHUB:
			return new ViewItem("https://github.com/", ViewType.STANDARD);
		}
		return null;
	}
}
