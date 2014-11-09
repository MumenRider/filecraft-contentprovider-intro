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

import android.database.AbstractCursor;
import android.net.Uri;

import com.filecraft.helloworld.FileCraftContract.ListTable;
import com.filecraft.helloworld.FileCraftContract.QuizAnswersTable;
import com.filecraft.helloworld.FileCraftContract.QuizQuestionsTable;
import com.filecraft.helloworld.FileCraftContract.QuizTable;
import com.filecraft.helloworld.FileCraftContract.UriMatcherEntry;
import com.filecraft.helloworld.FileCraftContract.ViewTable;

/**
 * Custom cursor used by the ContentProvider. Does not use a sqlite database and all data is
 * static.
 * 
 * TODO: Add example cursors that draw data from a sqlite database, from another ContentProvider
 * on the device, from files packaged with the apk, and from a remote server.
 */
public class CustomCursor extends AbstractCursor {

	private final UriMatcherEntry _matcherEntry;
	private final Uri _uri;
	private String _actionId;
	private final String[] _projection;

	private ListItem[] _listItems = null;
	private GridItem[] _gridItems = null;
	private GalleryItem[] _galleryItems = null;
	private ViewItem _viewItem = null;
	private Quiz _quiz = null;
	private QuizQuestion[] _quizQuestions = null;
	private QuizAnswer[] _quizAnswers = null;

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
			switch (_matcherEntry) {
			case GRID:
			case GALLERY:
			case VIEW:
			case QUIZ:
			case QUIZ_QUESTIONS:
			case QUIZ_ANSWERS:
			case GRID_ITEM:
			case GALLERY_ITEM:
			case QUIZ_QUESTIONS_ITEM:
			case QUIZ_ANSWERS_ITEM:
				_actionId = selectionArgs[0];
			default:
				// NO-OP
				break;
			}
			switch (_matcherEntry) {
			case LIST:
			case LIST_ITEM:
				_listItems = new ListItem[ListItem.getListItemCount()];
				break;
			case GRID:
			case GRID_ITEM:
				_gridItems = new GridItem[GridItem.getGridItemCount(_actionId)];
				break;
			case GALLERY:
			case GALLERY_ITEM:
				_galleryItems = new GalleryItem[GalleryItem.getGalleryItemCount(_actionId)];
				break;
			case VIEW:
				_viewItem = ViewItem.getViewItem(_actionId);
				break;
			case QUIZ:
				_quiz = Quiz.getQuiz(_actionId);
				break;
			case QUIZ_QUESTIONS:
			case QUIZ_QUESTIONS_ITEM:
				_quizQuestions = QuizQuestion.getQuizQuestions(_actionId, false);
				break;
			case QUIZ_ANSWERS:
			case QUIZ_ANSWERS_ITEM:
				_quizAnswers = QuizAnswer.getQuizAnswers(_actionId, true);
				break;
			default:
				// NO-OP
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Failed to parse table URI: " + uri);
		}
	}

	@Override
	public int getCount() {
		switch (_matcherEntry) {
		case LIST:
			return ListItem.getListItemCount();
		case GRID:
			return GridItem.getGridItemCount(_actionId);
		case GALLERY:
			return GalleryItem.getGalleryItemCount(_actionId);
		case QUIZ:
			return 1;
		case QUIZ_QUESTIONS:
			return QuizQuestion.getQuizQuestionCount(_actionId);
		case QUIZ_ANSWERS:
			return _quizAnswers.length;
		case VIEW:
			return 1;
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
				FileCraftContract.COLUMN_ACTION_TYPE.equals(columnName) ||
				QuizAnswersTable.COLUMN_IS_CORRECT_ANSWER.equals(columnName)) {
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
			if (_listItems[position] == null) {
				_listItems[position] = ListItem.getListItem(position);
			}
			if (FileCraftContract.COLUMN_CONTENT_PATH.equals(columnName)) {
				return _listItems[position].iconPath;
			} else if (FileCraftContract.COLUMN_ACTION_ID.equals(columnName)) {
				return _listItems[position].actionId;
			} else if (ListTable.COLUMN_LIST_NAME.equals(columnName)) {
				return _listItems[position].name;
			} else if (ListTable.COLUMN_LIST_SUBTEXT.equals(columnName)) {
				return _listItems[position].subtext;
			}
			break;
		case GRID:
			if (_gridItems[position] == null) {
				_gridItems[position] = GridItem.getGridItem(_actionId, position);
			}
			if (FileCraftContract.COLUMN_CONTENT_PATH.equals(columnName)) {
				return _gridItems[position].iconPath;
			} else if (FileCraftContract.COLUMN_ACTION_ID.equals(columnName)) {
				return _gridItems[position].actionId;
			} else if (FileCraftContract.COLUMN_TEXT.equals(columnName)) {
				return _gridItems[position].text;
			}
			break;
		case GALLERY:
			if (_galleryItems[position] == null) {
				_galleryItems[position] = GalleryItem.getGalleryItem(_actionId, position);
			}
			if (FileCraftContract.COLUMN_CONTENT_PATH.equals(columnName)) {
				return _galleryItems[position].imagePath;
			} else if (FileCraftContract.COLUMN_TEXT.equals(columnName)) {
				return _galleryItems[position].text;
			}
			break;
		case VIEW:
			if (ViewTable.COLUMN_VIEW_URI.equals(columnName)) {
				return _viewItem.uri;
			}
			break;
		case QUIZ:
			if (FileCraftContract.COLUMN_CONTENT_PATH.equals(columnName)) {
				return _quiz.iconPath;
			} else if (FileCraftContract.COLUMN_ACTION_ID.equals(columnName)) {
				return _quiz.actionId;
			} else if (QuizTable.COLUMN_DESCRIPTION.equals(columnName)) {
				return _quiz.description;
			} else if (QuizTable.COLUMN_TITLE.equals(columnName)) {
				return _quiz.title;
			}
			break;
		case QUIZ_QUESTIONS:
			if (FileCraftContract.COLUMN_CONTENT_PATH.equals(columnName)) {
				return _quizQuestions[position].iconPath;
			} else if (FileCraftContract.COLUMN_ACTION_ID.equals(columnName)) {
				return _quizQuestions[position].actionId;
			} else if (QuizQuestionsTable.COLUMN_QUIZ_QUESTION.equals(columnName)) {
				return _quizQuestions[position].question;
			} else if (QuizQuestionsTable.COLUMN_QUIZ_SUBTEXT.equals(columnName)) {
				return "";
			}
			break;
		case QUIZ_ANSWERS:
			if (QuizAnswersTable.COLUMN_ANSWER_TEXT.equals(columnName)) {
				return _quizAnswers[position].answer;
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
			if (_listItems[position] == null) {
				_listItems[position] = ListItem.getListItem(position);
			}
			if (FileCraftContract.COLUMN_ACTION_TYPE.equals(columnName)) {
				return _listItems[position].type.code;
			} else if (FileCraftContract.COLUMN_CONTENT_TYPE.equals(columnName)) {
				return _listItems[position].iconType.code;
			}
			break;
		case GRID:
			if (_gridItems[position] == null) {
				_gridItems[position] = GridItem.getGridItem(_actionId, position);
			}
			if (FileCraftContract.COLUMN_ACTION_TYPE.equals(columnName)) {
				return _gridItems[position].actionType.code;
			} else if (FileCraftContract.COLUMN_CONTENT_TYPE.equals(columnName)) {
				return _gridItems[position].iconType.code;
			}
			break;
		case GALLERY:
			if (_galleryItems[position] == null) {
				_galleryItems[position] = GalleryItem.getGalleryItem(_actionId, position);
			}
			if (FileCraftContract.COLUMN_CONTENT_TYPE.equals(columnName)) {
				return _galleryItems[position].imageType.code;
			}
			break;
		case VIEW:
			if (FileCraftContract.COLUMN_ACTION_TYPE.equals(columnName)) {
				return _viewItem.type.code;
			}
		case QUIZ:
			if (FileCraftContract.COLUMN_CONTENT_TYPE.equals(columnName)) {
				return _quiz.iconType.code;
			}
			break;
		case QUIZ_QUESTIONS:
			if (FileCraftContract.COLUMN_ACTION_TYPE.equals(columnName)) {
				return QuizQuestionsTable.TYPE_MULTIPLE_CHOICE;
			}
			break;
		case QUIZ_ANSWERS:
			if (QuizAnswersTable.COLUMN_IS_CORRECT_ANSWER.equals(columnName)) {
				return _quizAnswers[position].isCorrect ? 1 : 0;
			}
			break;
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
