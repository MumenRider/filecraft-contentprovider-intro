package com.filecraft.helloworld;

import com.filecraft.helloworld.FileCraftContract.ContentType;

public class Quiz {

	public enum QuizId {
		JAPANESE_VOCAB_SAMPLE,
		JAPANESE_BASICS;

		public static QuizId getId(String actionId) {
			for (QuizId id : QuizId.values()) {
				if (id.name().equals(actionId)) {
					return id;
				}
			}
			return null;
		}
	}

	public final String iconPath;
	public final ContentType iconType;
	public final String title;
	public final String description;
	public final String actionId;

	private Quiz(String iconPath, ContentType iconType, String title, String description, String actionId) {
		this.iconPath = iconPath;
		this.iconType = iconType;
		this.title = title;
		this.description = description;
		this.actionId = actionId;
	}

	public static Quiz getQuiz(String actionId) {
		QuizId id = QuizId.getId(actionId);
		switch (id) {
		case JAPANESE_VOCAB_SAMPLE:
			return new Quiz(TutorialUtils.getResourceFilePath(R.raw.text_svg), ContentType.SVG_BASIC,
					TutorialUtils.getString(R.string.contentprovider_grid_quiz_japanese_vocab),
					TutorialUtils.getString(R.string.contentprovider_grid_quiz_subtext), id.name());
		case JAPANESE_BASICS:
			return new Quiz(TutorialUtils.getResourceFilePath(R.raw.text_svg), ContentType.SVG_BASIC,
					TutorialUtils.getString(R.string.contentprovider_grid_quiz_japanese_basics),
					TutorialUtils.getString(R.string.contentprovider_grid_quiz_subtext), id.name());
		}
		return null;
	}
}
