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

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.filecraft.helloworld.FileCraftContract.GalleryTable;
import com.filecraft.helloworld.FileCraftContract.GridTable;
import com.filecraft.helloworld.FileCraftContract.ListTable;
import com.filecraft.helloworld.FileCraftContract.UriMatcherEntry;
import com.filecraft.helloworld.FileCraftContract.ViewTable;

public class CustomCursorProviderA extends ContentProvider {

	@Override
	public boolean onCreate() {
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder) {
		return new CustomCursor(uri, projection, selection, selectionArgs, sortOrder);
	}

	@Override
	public String getType(Uri uri) {
		int tableId = FileCraftContract.URI_MATCHER.match(uri);
		UriMatcherEntry entry = UriMatcherEntry.getEntryFromTableId(tableId);
		switch (entry) {
		case LIST:
			return ListTable.LIST_TYPE;
		case LIST_ITEM:
			return ListTable.LIST_ITEM_TYPE;
		case GRID:
			return GridTable.GRID_TYPE;
		case GRID_ITEM:
			return GridTable.GRID_ITEM_TYPE;
		case GALLERY:
			return GalleryTable.GALLERY_TYPE;
		case GALLERY_ITEM:
			return GalleryTable.GALLERY_ITEM_TYPE;
		case VIEW:
			return ViewTable.VIEW_TYPE;
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// NO-OP
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// NO-OP
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// NO-OP
		return 0;
	}
}
