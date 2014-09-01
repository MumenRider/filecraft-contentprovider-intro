package com.filecraft.helloworld;

import com.filecraft.helloworld.FileCraftContract.ContentType;
import com.filecraft.helloworld.FileCraftContract.GridTable.GridActionType;
import com.filecraft.helloworld.GalleryItem.GalleryItemId;
import com.filecraft.helloworld.Quiz.QuizId;
import com.filecraft.helloworld.ViewItem.ViewItemId;

public class GridItem {

	public enum GridItemId {
		INTRO(3, true),
		SAMPLES(9, true),
		NESTED_GRID(1, false);

		public final int itemCount;
		public final boolean showInList;

		private GridItemId(int itemCount, boolean showInList) {
			this.itemCount = itemCount;
			this.showInList = showInList;
		}

		public static GridItemId getItemId(String actionId) {
			for (GridItemId id : GridItemId.values()) {
				if (id.name().equals(actionId)) {
					return id;
				}
			}
			return null;
		}
	}

	/**
	 * Path and content type of the icon to display in the grid.
	 */
	public final String iconPath;
	public final ContentType iconType;

	/**
	 * Text to display under the icon in the grid.
	 */
	public final String text;

	/**
	 * Type of action performed when the grid item is clicked.
	 */
	public final GridActionType actionType;

	/**
	 * Id of the action performed when the grid item is clicked.
	 */
	public final String actionId;

	private GridItem(String iconPath, ContentType iconType, String text,
			GridActionType actionType, String actionId) {
		this.iconPath = iconPath;
		this.iconType = iconType;
		this.text = text;
		this.actionType = actionType;
		this.actionId = actionId;
	}

	public static int getGridItemCount(String actionId) {
		return GridItemId.getItemId(actionId).itemCount;
	}

	public static GridItem getGridItem(String actionId, int position) {
		GridItemId id = GridItemId.getItemId(actionId);
		switch (id) {
		case INTRO:
			switch (position) {
			case 0:
				return new GridItem(TutorialUtils.getResourceFilePath(R.raw.android_svg), ContentType.SVG_BASIC,
						TutorialUtils.getString(R.string.contentprovider_grid_android_title),
						GridActionType.VIEW, ViewItemId.WEB_ANDROID_SETUP.name());
			case 1:
				return new GridItem(TutorialUtils.getResourceFilePath(R.raw.android_svg), ContentType.SVG_BASIC,
						TutorialUtils.getString(R.string.contentprovider_grid_documentation_title),
						GridActionType.VIEW, ViewItemId.WEB_CONTENTPROVIDER_DOCUMENTATION.name());
			case 2:
				return new GridItem(TutorialUtils.getResourceFilePath(R.raw.octocat_svg), ContentType.SVG_BASIC,
						TutorialUtils.getString(R.string.contentprovider_grid_github_title),
						GridActionType.VIEW, ViewItemId.WEB_GITHUB_TUTORIAL.name());
			}
			break;
		case SAMPLES:
			switch (position) {
			case 0:
				return new GridItem(TutorialUtils.getResourceFilePath(R.raw.up_arrow_svg), ContentType.SVG_BASIC,
						TutorialUtils.getString(R.string.contentprovider_grid_nested_grid),
						GridActionType.GRID, GridItemId.NESTED_GRID.name());
			case 1:
				return new GridItem(TutorialUtils.getResourceFilePath(R.raw.text_svg), ContentType.SVG_BASIC,
						TutorialUtils.getString(R.string.contentprovider_grid_quiz_japanese_vocab),
						GridActionType.QUIZ, QuizId.JAPANESE_VOCAB_SAMPLE.name());
			case 2:
				return new GridItem(TutorialUtils.getResourceFilePath(R.raw.text_svg), ContentType.SVG_BASIC,
						TutorialUtils.getString(R.string.contentprovider_grid_quiz_japanese_basics),
						GridActionType.QUIZ, QuizId.JAPANESE_BASICS.name());
			case 3:
				return new GridItem(TutorialUtils.getResourceFilePath(R.raw.web_svg), ContentType.SVG_BASIC,
						TutorialUtils.getString(R.string.contentprovider_grid_link_google_play),
						GridActionType.VIEW, ViewItemId.WEB_GOOGLE_PLAY.name());
			case 4:
				return new GridItem(TutorialUtils.getResourceFilePath(R.raw.web_svg), ContentType.SVG_BASIC,
						TutorialUtils.getString(R.string.contentprovider_grid_link_github),
						GridActionType.VIEW, ViewItemId.WEB_GITHUB.name());
			case 5:
				return new GridItem(TutorialUtils.getResourceFilePath(R.raw.gallery_svg), ContentType.SVG_BASIC,
						TutorialUtils.getString(R.string.contentprovider_grid_gallery_all),
						GridActionType.GALLERY, GalleryItemId.ALL_IMAGE_TYPES.name());
			case 6:
				return new GridItem(TutorialUtils.getResourceFilePath(R.raw.gallery_svg), ContentType.SVG_BASIC,
						TutorialUtils.getString(R.string.contentprovider_grid_gallery_svg),
						GridActionType.GALLERY, GalleryItemId.SVG_BASIC_ONLY.name());
			case 7:
				return new GridItem(TutorialUtils.getResourceFilePath(R.raw.gallery_svg), ContentType.SVG_BASIC,
						TutorialUtils.getString(R.string.contentprovider_grid_gallery_web),
						GridActionType.GALLERY, GalleryItemId.WEB_ONLY.name());
			case 8:
				return new GridItem(TutorialUtils.getResourceFilePath(R.raw.gallery_svg), ContentType.SVG_BASIC,
						TutorialUtils.getString(R.string.contentprovider_grid_gallery_png),
						GridActionType.GALLERY, GalleryItemId.PNG_ONLY.name());
			}
			break;
		case NESTED_GRID:
			switch (position) {
			case 0:
				return new GridItem(TutorialUtils.getResourceFilePath(R.raw.up_arrow_svg), ContentType.SVG_BASIC,
						TutorialUtils.getString(R.string.contentprovider_grid_nested_grid_return),
						GridActionType.GRID, GridItemId.SAMPLES.name());
			}
		}
		return null;
	}
}
