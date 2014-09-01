package com.filecraft.helloworld;

import com.filecraft.helloworld.FileCraftContract.ContentType;
import com.filecraft.helloworld.FileCraftContract.ListTable.ListActionType;
import com.filecraft.helloworld.GridItem.GridItemId;

/**
 * Child items found within the left/start drawer view.
 */
public class ListItem {

	/**
	 * Type of action to perform on the list item when clicked.
	 */
	public final ListActionType type;

	/**
	 * Path and content type of the icon to display in the list.
	 */
	public final String iconPath;
	public final ContentType iconType;

	/**
	 * Name/title text and subtext to display in the list item.
	 */
	public final String name;
	public final String subtext;

	public final String actionId;

	private ListItem(ListActionType type, String iconPath, ContentType iconType, String name, String subtext, String actionId) {
		this.type = type;
		this.iconPath = iconPath;
		this.iconType = iconType;
		this.name = name;
		this.subtext = subtext;
		this.actionId = actionId;
	}

	public static int getListItemCount() {
		int count = 0;
		for (GridItemId id : GridItemId.values()) {
			if (id.showInList) {
				count++;
			}
		}
		return count;
	}

	public static ListItem getListItem(int position) {
		GridItemId id = GridItemId.values()[position];
		switch (id) {
		case INTRO:
			return new ListItem(ListActionType.GRID, TutorialUtils.getResourceFilePath(R.raw.android_svg),
					ContentType.SVG_BASIC, TutorialUtils.getString(R.string.contentprovider_tutorial_title),
					TutorialUtils.getString(R.string.contentprovider_tutorial_subtext), id.name());
		case SAMPLES:
			return new ListItem(ListActionType.GRID, TutorialUtils.getResourceFilePath(R.drawable.ic_launcher),
					ContentType.RASTER_IMAGE, TutorialUtils.getString(R.string.contentprovider_examples_title),
					TutorialUtils.getString(R.string.contentprovider_examples_subtext), id.name());
		case NESTED_GRID:
			return null;
		}
		return null;
	}
}
