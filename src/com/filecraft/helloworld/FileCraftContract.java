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

import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract to be used by any FileCraft ContentProviders.
 * 
 * Supported request formats (query uri):
 * content://<authority>/list
 * content://<authority>/list/<position>
 * content://<authority>/grid/<id>
 * content://<authority>/grid/<id>/<position>
 * content://<authority>/gallery/<id>
 * content://<authority>/gallery/<id>/<position>
 * content://<authority>/view/<id>
 * 
 * @see http://developer.android.com/guide/topics/providers/content-provider-basics.html#ContractClasses
 */
public class FileCraftContract implements BaseColumns {

	/**
	 * Include all of your authorities here. These are the strings set in the AndroidManifest for
	 * each ContentProvider.
	 */
	public enum Authority {
		CUSTOM_CURSOR_A("com.filecraft.helloworld.custom.cursor.a"),
		CUSTOM_CURSOR_B("com.filecraft.helloworld.custom.cursor.b");

		public final String name;
		public final Uri contentUri;

		private Authority(String name) {
			this.name = name;
			contentUri = Uri.parse("content://" + name);
		}
	}

	/**
	 * Custom FileCraft permission that must be included in the AndroidManifest
	 */
	public static final String READ_PERMISSION = "com.filecraft.permission.READ";

	/**
	 * Type = String (uri path)
	 * Path of the content. Supported format prefixes include "file://",
	 * "android.resource://", and http/https
	 */
	public static final String COLUMN_CONTENT_PATH = "content_path";

	/**
	 * Type = Int
	 * Type code of the content path. See ContentType for the code. If a content type is not
	 * provided, it will be guessed based off of the file extension. Providing a content type
	 * is most useful for "android.resource://" and http/https type paths because they do not
	 * always include a file extension that can be used to guess the file's mime type.
	 */
	public static final String COLUMN_CONTENT_TYPE = "content_type";

	/**
	 * Type = Int
	 * Version code returned from queries. Initial version is 0.
	 */
	public static final String COLUMN_VERSION = "version"; // Type = Int

	/**
	 * Content type used along with the "content_type" column. The "content_type" column should
	 * contain the integer code of the type of content under the column "content_path".
	 * 
	 * NOTE: Only RASTER_IMAGE works with files contained within the apk. Trying to link to apk
	 * files for the other types will show a WebView with no content.
	 */
	public enum ContentType {
		/**
		 * Png or jpg image on device (including in apk)
		 */
		RASTER_IMAGE(1),
		/**
		 * Raster, svg, or gif images found online
		 */
		WEB_IMAGE(2),
		/**
		 * Svg image on device but not in apk. Rendered within an Android WebView.
		 */
		SVG(3),
		/**
		 * Limited support for SVG Basic 1.1
		 * http://www.w3.org/TR/SVGMobile/
		 * 
		 * Unlike the above SVG, these can be provided from within the apk. Also, these will get
		 * rendered as a Bitmap instead of a WebView.
		 */
		SVG_BASIC(4),
		/**
		 * Gif image on device but not in apk. Rendered within an Android WebView.
		 */
		GIF(5);

		public final int code;

		private ContentType(int code) {
			this.code = code;
		}

		public static ContentType getType(int code) {
			for (ContentType type : ContentType.values()) {
				if (type.code == code) {
					return type;
				}
			}
			return null;
		}
	}

	/**
	 * Table for list items found within the left/start drawer of FileCraft.
	 */
	public static final class ListTable {
		public static final String TABLE_NAME = "list";

		/**
		 * Type = String
		 * Name text and text under the name in the list.
		 */
		public static final String COLUMN_LIST_NAME = "list_name";
		public static final String COLUMN_LIST_SUBTEXT = "list_subtext";

		/**
		 * Type = Integer (type id)
		 * Action code from the ListActionType enum.
		 */
		public static final String COLUMN_LIST_ACTION_TYPE = "list_action_type";

		/**
		 * Type = Integer (foreign key)
		 * Identifier for the action item. Clicking the list item will use the action type and action
		 * id to determine which item to request.
		 */
		public static final String COLUMN_LIST_ACTION_ID = "list_action_id";

		public static final String LIST_TYPE =
				ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.filecraft.list";
		public static final String LIST_ITEM_TYPE =
				ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.filecraft.list";

		/**
		 * Action to be performed on the list child when clicked.
		 */
		public enum ListActionType {
			/**
			 * Opens a grid that contains icons and text for each grid child.
			 */
			GRID(1);

			public final int code;

			private ListActionType(int code) {
				this.code = code;
			}

			public static ListActionType getType(int code) {
				for (ListActionType type : ListActionType.values()) {
					if (type.code == code) {
						return type;
					}
				}
				return null;
			}
		}
	}

	/**
	 * Table for grid items found within the center/content view of FileCraft.
	 */
	public static final class GridTable {
		public static final String TABLE_NAME = "grid";

		public static final String COLUMN_GRID_TEXT = "grid_text"; // Type = String

		/**
		 * Type = Integer (type id)
		 * Action code from the GridActionType enum.
		 */
		public static final String COLUMN_GRID_ACTION_TYPE = "grid_action_type";

		/**
		 * Type = Integer (foreign key)
		 * Identifier for the action item. Clicking the grid item will use the action type and action
		 * id to determine which item to request.
		 */
		public static final String COLUMN_GRID_ACTION_ID = "grid_action_id";

		public static final String GRID_TYPE =
				ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.filecraft.grid";
		public static final String GRID_ITEM_TYPE =
				ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.filecraft.grid";

		/**
		 * Action to be performed on a grid item when clicked.
		 */
		public enum GridActionType {
			/**
			 * Opens a gallery. Each page of the gallery is a different image.
			 */
			GALLERY(1),
			/**
			 * Launches a VIEW Intent for a provided uri. This could be for viewing a web address,
			 * file, or any other uri provided.
			 * 
			 * @see http://developer.android.com/reference/android/content/Intent.html#ACTION_VIEW
			 */
			VIEW(2),
			/**
			 * Opens a different grid.
			 */
			GRID(3);

			public final int code;

			private GridActionType(int code) {
				this.code = code;
			}

			public static GridActionType getType(int code) {
				for (GridActionType type : GridActionType.values()) {
					if (code == type.code) {
						return type;
					}
				}
				return null;
			}
		}

		public static Uri getUri(String authority, int id) {
			Uri contentUri = Uri.parse("content://" + authority);
			Uri tableUri = Uri.withAppendedPath(contentUri,
					GridTable.TABLE_NAME + "/" + id);
			return tableUri;
		}
	}

	/**
	 * Table for gallery items of a gallery action.
	 */
	public static final class GalleryTable {
		public static final String TABLE_NAME = "gallery";

		public static final String COLUMN_GALLERY_TYPE = "gallery_type"; // Type = Integer (type id)
		public static final String COLUMN_GALLERY_IMAGE = "gallery_image"; // Type = String (uri path)

		public static final String GALLERY_TYPE =
				ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.filecraft.gallery";
		public static final String GALLERY_ITEM_TYPE =
				ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.filecraft.gallery";

		/**
		 * Type of gallery. Defines what kind of display rules will be used in the gallery view.
		 */
		public enum GalleryType {
			/**
			 * Display images with no other information.
			 */
			IMAGE_ONLY(1);

			public final int code;

			private GalleryType(int code) {
				this.code = code;
			}
		}

		public static Uri getUri(String authority, int id) {
			Uri contentUri = Uri.parse("content://" + authority);
			Uri tableUri = Uri.withAppendedPath(contentUri, TABLE_NAME + "/" + id);
			return tableUri;
		}
	}

	/**
	 * Table for VIEW Intent actions.
	 * 
	 * @see http://developer.android.com/reference/android/content/Intent.html#ACTION_VIEW
	 */
	public static final class ViewTable {
		public static final String TABLE_NAME = "view";

		public static final String COLUMN_VIEW_TYPE = "view_type"; // Type = Integer (type id)
		public static final String COLUMN_VIEW_URI = "view_uri"; // Type = String (uri)

		public static final String VIEW_TYPE =
				ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.filecraft.view";
		public static final String VIEW_ITEM_TYPE =
				ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.filecraft.view";

		/**
		 * Specifies what kind of view action will be performed.
		 */
		public enum ViewType {
			/**
			 * Standard view Intent with no special arguments.
			 */
			STANDARD(1);

			public final int code;

			private ViewType(int code) {
				this.code = code;
			}

			public static ViewType getType(int code) {
				for (ViewType type : ViewType.values()) {
					if (type.code == code) {
						return type;
					}
				}
				return null;
			}
		}

		public static Uri getUri(String authority, int id) {
			Uri contentUri = Uri.parse("content://" + authority);
			Uri tableUri = Uri.withAppendedPath(contentUri, TABLE_NAME + "/" + id);
			return tableUri;
		}
	}

	/**
	 * Enum for use by the UriMatcher.
	 */
	public static enum UriMatcherEntry {
		LIST(ListTable.TABLE_NAME, 42),
		LIST_ITEM(ListTable.TABLE_NAME + "/#", 314),

		GRID(GridTable.TABLE_NAME + "/#", 9000),
		GRID_ITEM(GridTable.TABLE_NAME + "/#/#", 13),

		GALLERY(GalleryTable.TABLE_NAME + "/#", 360),
		GALLERY_ITEM(GalleryTable.TABLE_NAME + "/#/#", 1337),

		VIEW(ViewTable.TABLE_NAME + "/#", 404);

		public final String path;
		public final int tableId;

		private UriMatcherEntry(String path, int tableId) {
			this.path = path;
			this.tableId = tableId;
		}

		public static UriMatcherEntry getEntryFromTableId(int tableId) {
			if (tableId == LIST.tableId) {
				return LIST;
			} else if (tableId == LIST_ITEM.tableId) {
				return LIST_ITEM;
			} else if (tableId == GRID.tableId) {
				return GRID;
			} else if (tableId == GRID_ITEM.tableId) {
				return GRID_ITEM;
			} else if (tableId == GALLERY.tableId) {
				return GALLERY;
			} else if (tableId == GALLERY_ITEM.tableId) {
				return GALLERY_ITEM;
			} else if (tableId == VIEW.tableId) {
				return VIEW;
			} else {
				return null;
			}
		}
	}

	/**
	 * UriMatcher used by the ContentProvider for determining which table was requested in the query.
	 */
	public static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		for (UriMatcherEntry entry : UriMatcherEntry.values()) {
			for (Authority authority : Authority.values()) {
				URI_MATCHER.addURI(authority.name, entry.path, entry.tableId);
			}
		}
	}
}
