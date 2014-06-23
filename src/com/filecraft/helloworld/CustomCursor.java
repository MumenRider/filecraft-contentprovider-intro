/**
The MIT License (MIT)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and 
associated documentation files (the "Software"), to deal in the Software without restriction, 
including without limitation the rights to use, copy, modify, merge, publish, distribute, 
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is 
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or 
substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT 
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT 
OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.filecraft.helloworld;

import java.util.List;

import android.content.res.Resources;
import android.database.AbstractCursor;
import android.net.Uri;

import com.filecraft.helloworld.FileCraftContract.ContentType;
import com.filecraft.helloworld.FileCraftContract.GridTable;
import com.filecraft.helloworld.FileCraftContract.GridTable.GridActionType;
import com.filecraft.helloworld.FileCraftContract.ListTable;
import com.filecraft.helloworld.FileCraftContract.ListTable.ListActionType;
import com.filecraft.helloworld.FileCraftContract.UriMatcherEntry;
import com.filecraft.helloworld.FileCraftContract.ViewTable;
import com.filecraft.helloworld.FileCraftContract.ViewTable.ViewType;

/**
 * Custom cursor used by the ContentProvider. Does not use a sqlite database and all data is
 * static. A better Cursor might draw data from a sqlite database, from another ContentProvider
 * on the device, and/or from a remote server.
 * 
 * TODO: Make sample Cursors that draw data from a sqlite database, from another ContentProvider
 * on the device, and from a web site.
 */
public class CustomCursor extends AbstractCursor {

	private static final String PACKAGE_NAME = MyApplication.getAppContext().getPackageName();

	/**
	 * Child items found within the left/start drawer view.
	 */
	private enum ListItem {
		GRID_GALLERY(ListActionType.GRID, R.raw.gallery, ContentType.RASTER_IMAGE,
				R.string.list_custom_cursor_title_gallery,
				R.string.list_custom_cursor_subtext_gallery,
				new GridItem[] {
						GridItem.GALLERY_1, GridItem.GALLERY_2,
						GridItem.GALLERY_WEB_VIEWS, GridItem.GALLERY_RASTER_VS_SVGBASIC }),
		GRID_VIEW_WEB(ListActionType.GRID, R.raw.download, ContentType.RASTER_IMAGE,
				R.string.list_custom_cursor_title_view_web,
				R.string.list_custom_cursor_subtext_view_web,
				new GridItem[] {
						GridItem.VIEW_GOOGLE, GridItem.VIEW_4CHAN });

		/**
		 * Type of action to perform on the list item when clicked.
		 */
		public final ListActionType type;

		/**
		 * Path and content type of the icon to display in the list.
		 */
		public final String iconPath;
		public final ContentType contentType;

		/**
		 * Name/title text and subtext to display in the list item.
		 */
		public final String name;
		public final String subtext;

		/**
		 * Child items used by the list action.
		 */
		public final Object[] childItems;

		private ListItem(ListActionType type, int fileRes, ContentType contentType,
				int nameRes, int subtextRes, Object[] childItems) {
			this.type = type;
			iconPath = fileRes == -1 ? "" : "android.resource://" + PACKAGE_NAME + "/" + fileRes;
			//uriPath = "file://" + Environment.getExternalStorage() + "/some_image.png";
			//uriPath = "https://www.google.com/favicon.ico";
			this.contentType = contentType;
			Resources resources = MyApplication.getAppContext().getResources();
			name = nameRes == -1 ? "" : resources.getString(nameRes);
			subtext = subtextRes == -1 ? "" : resources.getString(subtextRes);
			this.childItems = childItems;
		}
	}

	private enum GridItem {
		GALLERY_1(R.raw.gallery, ContentType.RASTER_IMAGE,
				R.string.grid_gallery_1, GridActionType.GALLERY,
				new GalleryItem[] { GalleryItem.GALLERY,
						GalleryItem.IMAGE, GalleryItem.GOOGLE_FAVICON }),
		GALLERY_2(R.raw.gallery_svg, ContentType.SVG_BASIC,
				R.string.grid_gallery_2, GridActionType.GALLERY,
				new GalleryItem[] { GalleryItem.GOOGLE_FAVICON,
						GalleryItem.DOWNLOAD, GalleryItem.GOOGLE_FAVICON,
						GalleryItem.DOWNLOAD, GalleryItem.GOOGLE_FAVICON }),
		GALLERY_WEB_VIEWS(R.raw.image, ContentType.RASTER_IMAGE,
				R.string.grid_gallery_web_views, GridActionType.GALLERY,
				new GalleryItem[] {
						GalleryItem.GOOGLE_FAVICON, GalleryItem.GOOGLE_FAVICON,
						GalleryItem.GOOGLE_FAVICON, GalleryItem.GOOGLE_FAVICON }),
		GALLERY_RASTER_VS_SVGBASIC(R.raw.image_svg, ContentType.SVG_BASIC,
				R.string.grid_gallery_raster_vs_svgbasic, GridActionType.GALLERY,
				new GalleryItem[] {
						GalleryItem.GALLERY, GalleryItem.GALLERY_SVG,
						GalleryItem.IMAGE, GalleryItem.IMAGE_SVG,
						GalleryItem.WEB, GalleryItem.WEB_SVG,
						GalleryItem.DOWNLOAD, GalleryItem.DOWNLOAD_SVG }),
		VIEW_GOOGLE(R.raw.web, ContentType.RASTER_IMAGE,
				R.string.grid_web_google, GridActionType.VIEW,
				new ViewItem[] { ViewItem.WEB_GOOGLE }),
		VIEW_4CHAN(R.raw.web, ContentType.RASTER_IMAGE,
				R.string.grid_web_4chan, GridActionType.VIEW,
				new ViewItem[] { ViewItem.WEB_4CHAN });

		/**
		 * Path and content type of the icon to display in the grid.
		 */
		public final String iconPath;
		public final ContentType contentType;

		/**
		 * Text to display under the icon in the grid.
		 */
		public final String text;

		/**
		 * Action performed when the grid item is clicked.
		 */
		public final GridActionType actionType;

		/**
		 * Child items used by the list action.
		 */
		public final Object[] childItems;

		private GridItem(int fileRes, ContentType contentType, int textRes,
				GridActionType actionType, Object[] childItems) {
			iconPath = fileRes == -1 ? "" : "android.resource://" + PACKAGE_NAME + "/" + fileRes;
			//uriPath = "file://" + Environment.getExternalStorage() + "/some_image.png";
			//uriPath = "https://www.google.com/favicon.ico";
			this.contentType = contentType;
			Resources resources = MyApplication.getAppContext().getResources();
			text = textRes == -1 ? "" : resources.getString(textRes);
			this.actionType = actionType;
			this.childItems = childItems;
		}
	}

	private enum GalleryItem {
		IMAGE("android.resource://" + PACKAGE_NAME + "/" + R.raw.image, ContentType.RASTER_IMAGE),
		IMAGE_SVG("android.resource://" + PACKAGE_NAME + "/" + R.raw.image_svg, ContentType.SVG_BASIC),
		GALLERY("android.resource://" + PACKAGE_NAME + "/" + R.raw.gallery, ContentType.RASTER_IMAGE),
		GALLERY_SVG("android.resource://" + PACKAGE_NAME + "/" + R.raw.gallery_svg, ContentType.SVG_BASIC),
		DOWNLOAD("android.resource://" + PACKAGE_NAME + "/" + R.raw.download, ContentType.RASTER_IMAGE),
		DOWNLOAD_SVG("android.resource://" + PACKAGE_NAME + "/" + R.raw.download_svg, ContentType.SVG_BASIC),
		WEB("android.resource://" + PACKAGE_NAME + "/" + R.raw.web, ContentType.RASTER_IMAGE),
		WEB_SVG("android.resource://" + PACKAGE_NAME + "/" + R.raw.web_svg, ContentType.SVG_BASIC),
		GOOGLE_FAVICON("https://www.google.com/favicon.ico", ContentType.WEB_IMAGE);

		/**
		 * Path and content type of a gallery page.
		 */
		public final String uri;
		public final ContentType contentType;

		private GalleryItem(String uri, ContentType contentType) {
			this.uri = uri;
			this.contentType = contentType;
		}
	}

	private enum ViewItem {
		WEB_GOOGLE("https://www.google.com/", ViewType.STANDARD),
		WEB_4CHAN("http://www.4chan.org/", ViewType.STANDARD);

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
	}

	private final UriMatcherEntry _matcherEntry;
	private final Uri _uri;
	private int _id;
	private final String[] _projection;

	public CustomCursor(Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder) {
		_uri = uri;
		_projection = projection;
		int tableId = FileCraftContract.URI_MATCHER.match(uri);
		_matcherEntry = UriMatcherEntry.getEntryFromTableId(tableId);
		if (_matcherEntry == null) {
			throw new IllegalArgumentException("Unknown table URI: " + uri);
		}
		try {
			List<String> pathSegs = uri.getPathSegments();
			switch (_matcherEntry) {
			case GRID:
			case GALLERY:
				_id = Integer.parseInt(pathSegs.get(pathSegs.size() - 1));
				break;
			case GRID_ITEM:
			case GALLERY_ITEM:
				_id = Integer.parseInt(pathSegs.get(pathSegs.size() - 2));
				break;
			default:
				// NO-OP
				break;
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to parse table URI: " + uri);
		}
	}

	@Override
	public int getCount() {
		switch (_matcherEntry) {
		case LIST:
			return ListItem.values().length;
		case GRID:
			return ListItem.values()[_id].childItems.length;
		case GALLERY:
			return GridItem.values()[_id].childItems.length;
		case VIEW:
			return ViewItem.values().length;
		default:
			return 0;
		}
	}

	@Override
	public String[] getColumnNames() {
		return _projection;
	}

	@Override
	public int getType(int column) {
		String columnName = getColumnName(column);
		if (FileCraftContract.COLUMN_CONTENT_TYPE.equals(columnName) ||
				FileCraftContract.COLUMN_VERSION.equals(columnName) ||
				ListTable.COLUMN_LIST_ACTION_TYPE.equals(columnName) ||
				ListTable.COLUMN_LIST_ACTION_ID.equals(columnName) ||
				GridTable.COLUMN_GRID_ACTION_TYPE.equals(columnName) ||
				GridTable.COLUMN_GRID_ACTION_ID.equals(columnName) ||
				ViewTable.COLUMN_VIEW_TYPE.equals(columnName)) {
			return FIELD_TYPE_INTEGER;
		} else {
			return FIELD_TYPE_STRING;
		}
	}

	@Override
	public String getString(int column) {
		String columnName = getColumnName(column);
		if (columnName == null) {
			return null;
		}
		int position = getPosition();
		if (FileCraftContract._ID.equals(columnName)) {
			return String.valueOf(position);
		}
		switch (_matcherEntry) {
		case LIST:
			ListItem listItem = ListItem.values()[position];
			if (FileCraftContract.COLUMN_CONTENT_PATH.equals(columnName)) {
				return listItem.iconPath;
			} else if (ListTable.COLUMN_LIST_NAME.equals(columnName)) {
				return listItem.name;
			} else if (ListTable.COLUMN_LIST_SUBTEXT.equals(columnName)) {
				return listItem.subtext;
			}
			break;
		case GRID:
			GridItem gridItem = (GridItem) ListItem.values()[_id].childItems[position];
			if (FileCraftContract.COLUMN_CONTENT_PATH.equals(columnName)) {
				return gridItem.iconPath;
			} else if (GridTable.COLUMN_GRID_TEXT.equals(columnName)) {
				return gridItem.text;
			}
			break;
		case GALLERY:
			GalleryItem galleryItem = (GalleryItem) GridItem.values()[_id].childItems[position];
			if (FileCraftContract.COLUMN_CONTENT_PATH.equals(columnName)) {
				return galleryItem.uri;
			}
			break;
		case VIEW:
			ViewItem viewItem = ViewItem.values()[position];
			if (ViewTable.COLUMN_VIEW_URI.equals(columnName)) {
				return viewItem.uri;
			}
			break;
		default:
			// NO-OP
			break;
		}
		throw new IllegalArgumentException("Unhandled column name: " + columnName + ", uri=" + _uri);
	}

	@Override
	public short getShort(int column) {
		return -1;
	}

	@Override
	public int getInt(int column) {
		// NOTE: Android's CursorWindow.getInt() calls getLong() from getInt(). This means that we
		// must implement getLong() instead of getInt().
		return (int) getLong(column);
	}

	@Override
	public long getLong(int column) {
		String columnName = getColumnName(column);
		if (columnName == null) {
			return -1;
		}
		if (FileCraftContract.COLUMN_VERSION.equals(columnName)) {
			return 0;
		}
		int position = getPosition();
		switch (_matcherEntry) {
		case LIST:
			ListItem listItem = ListItem.values()[position];
			if (ListTable.COLUMN_LIST_ACTION_TYPE.equals(columnName)) {
				return listItem.type.code;
			} else if (ListTable.COLUMN_LIST_ACTION_ID.equals(columnName)) {
				return position;
			} else if (FileCraftContract.COLUMN_CONTENT_TYPE.equals(columnName)) {
				return listItem.contentType.code;
			}
			break;
		case GRID:
			GridItem gridItem = (GridItem) ListItem.values()[_id].childItems[position];
			if (GridTable.COLUMN_GRID_ACTION_TYPE.equals(columnName)) {
				return gridItem.actionType.code;
			} else if (GridTable.COLUMN_GRID_ACTION_ID.equals(columnName)) {
				return position;
			} else if (FileCraftContract.COLUMN_CONTENT_TYPE.equals(columnName)) {
				return gridItem.contentType.code;
			}
			break;
		case GALLERY:
			GalleryItem galleryItem = (GalleryItem) GridItem.values()[_id].childItems[position];
			if (FileCraftContract.COLUMN_CONTENT_TYPE.equals(columnName)) {
				return galleryItem.contentType.code;
			}
			break;
		case VIEW:
			ViewItem viewItem = ViewItem.values()[position];
			if (ViewTable.COLUMN_VIEW_TYPE.equals(columnName)) {
				return viewItem.type.code;
			}
		default:
			// NO-OP
			break;
		}
		return -1;
	}

	@Override
	public float getFloat(int column) {
		return -1;
	}

	@Override
	public double getDouble(int column) {
		return -1;
	}

	@Override
	public boolean isNull(int column) {
		return false;
	}
}
