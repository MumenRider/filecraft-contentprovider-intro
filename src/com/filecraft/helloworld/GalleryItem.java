package com.filecraft.helloworld;

import com.filecraft.helloworld.FileCraftContract.ContentType;

public class GalleryItem {

	public enum GalleryItemId {
		ALL_IMAGE_TYPES(3),
		SVG_BASIC_ONLY(4),
		PNG_ONLY(5),
		WEB_ONLY(4),
		IMAGE_TEXT_MIX(4);

		public final int itemCount;

		private GalleryItemId(int itemCount) {
			this.itemCount = itemCount;
		}

		public static GalleryItemId getItemId(String actionId) {
			for (GalleryItemId id : GalleryItemId.values()) {
				if (id.name().equals(actionId)) {
					return id;
				}
			}
			return null;
		}
	}

	public final String imagePath;
	public final ContentType imageType;
	public final String text;

	private GalleryItem(String imagePath, ContentType imageType, String text) {
		this.imagePath = imagePath;
		this.imageType = imageType;
		this.text = text;
	}

	private GalleryItem(String imagePath, ContentType imageType) {
		this.imagePath = imagePath;
		this.imageType = imageType;
		text = null;
	}

	public static int getGalleryItemCount(String actionId) {
		return GalleryItemId.getItemId(actionId).itemCount;
	}

	public static GalleryItem getGalleryItem(String actionId, int position) {
		GalleryItemId id = GalleryItemId.getItemId(actionId);
		switch (id) {
		case ALL_IMAGE_TYPES:
			switch (position) {
			case 0:
				return new GalleryItem(TutorialUtils.getResourceFilePath(R.raw.image_svg), ContentType.SVG_BASIC);
			case 1:
				return new GalleryItem(TutorialUtils.getResourceFilePath(R.raw.image), ContentType.RASTER_IMAGE);
			case 2:
				return new GalleryItem("https://www.google.com/favicon.ico", ContentType.WEB_IMAGE);
			}
			break;
		case SVG_BASIC_ONLY:
			switch (position) {
			case 0:
				return new GalleryItem(TutorialUtils.getResourceFilePath(R.raw.download_svg), ContentType.SVG_BASIC);
			case 1:
				return new GalleryItem(TutorialUtils.getResourceFilePath(R.raw.gallery_svg), ContentType.SVG_BASIC);
			case 2:
				return new GalleryItem(TutorialUtils.getResourceFilePath(R.raw.image_svg), ContentType.SVG_BASIC);
			case 3:
				return new GalleryItem(TutorialUtils.getResourceFilePath(R.raw.web_svg), ContentType.SVG_BASIC);
			}
			break;
		case PNG_ONLY:
			switch (position) {
			case 0:
				return new GalleryItem(TutorialUtils.getResourceFilePath(R.drawable.ic_launcher), ContentType.RASTER_IMAGE);
			case 1:
				return new GalleryItem(TutorialUtils.getResourceFilePath(R.raw.download), ContentType.RASTER_IMAGE);
			case 2:
				return new GalleryItem(TutorialUtils.getResourceFilePath(R.raw.gallery), ContentType.RASTER_IMAGE);
			case 3:
				return new GalleryItem(TutorialUtils.getResourceFilePath(R.raw.image), ContentType.RASTER_IMAGE);
			case 4:
				return new GalleryItem(TutorialUtils.getResourceFilePath(R.raw.web), ContentType.RASTER_IMAGE);
			}
			break;
		case WEB_ONLY:
			switch (position) {
			case 0:
				return new GalleryItem("https://www.google.com/favicon.ico", ContentType.WEB_IMAGE);
			case 1:
				return new GalleryItem("https://www.google.com/favicon.ico", ContentType.WEB_IMAGE);
			case 2:
				return new GalleryItem("https://www.google.com/favicon.ico", ContentType.WEB_IMAGE);
			case 3:
				return new GalleryItem("https://www.google.com/favicon.ico", ContentType.WEB_IMAGE);
			}
			break;
		case IMAGE_TEXT_MIX:
			switch (position) {
			case 0:
				return new GalleryItem(TutorialUtils.getResourceFilePath(R.raw.image_svg), ContentType.SVG_BASIC,
						MyApplication.getAppContext().getResources().getString(R.string.contentprovider_gallery_split_text));
			case 1:
				return new GalleryItem(TutorialUtils.getResourceFilePath(R.raw.gallery_svg), ContentType.SVG_BASIC);
			case 2:
				return new GalleryItem(TutorialUtils.getResourceFilePath(R.raw.download_svg), ContentType.SVG_BASIC,
						MyApplication.getAppContext().getResources().getString(R.string.contentprovider_gallery_split_text));
			case 3:
				return new GalleryItem(TutorialUtils.getResourceFilePath(R.raw.web_svg), ContentType.SVG_BASIC);
			}
			break;
		}
		return null;
	}
}
