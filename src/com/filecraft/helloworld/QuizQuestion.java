package com.filecraft.helloworld;

import java.util.ArrayList;
import java.util.HashSet;

import com.filecraft.helloworld.FileCraftContract.ContentType;
import com.filecraft.helloworld.Quiz.QuizId;
import com.filecraft.helloworld.QuizAnswer.JapaneseBasics;
import com.filecraft.helloworld.QuizAnswer.QuizAnswerSetId;

public class QuizQuestion {

	public final String iconPath;
	public final ContentType iconType;
	public final String question;
	public final String actionId;

	private QuizQuestion(String iconPath, ContentType iconType, String question, String actionId) {
		this.iconPath = iconPath;
		this.iconType = iconType;
		this.question = question;
		this.actionId = actionId;
	}

	public static int getQuizQuestionCount(String actionId) {
		QuizId id = QuizId.getId(actionId);
		switch (id) {
		case JAPANESE_VOCAB_SAMPLE:
			return 3;
		case JAPANESE_BASICS:
			return JapaneseBasics.values().length;
		}
		return 0;
	}

	public static QuizQuestion[] getQuizQuestions(String actionId, boolean inJapanese) {
		QuizId id = QuizId.getId(actionId);
		String answerActionId = null;
		switch (id) {
		case JAPANESE_VOCAB_SAMPLE:
			answerActionId = QuizAnswerSetId.JAPANESE_VOCAB.name();
			break;
		case JAPANESE_BASICS:
			answerActionId = QuizAnswerSetId.JAPANESE_BASICS.name();
			break;
		}
		ArrayList<QuizQuestion> questions = new ArrayList<QuizQuestion>();
		HashSet<String> questionsAdded = new HashSet<String>();
		while (questionsAdded.size() < getQuizQuestionCount(actionId)) {
			String[] question = QuizAnswer.getRandomQuizAnswer(answerActionId, inJapanese);
			if (questionsAdded.add(question[1])) {
				questions.add(new QuizQuestion(TutorialUtils.getResourceFilePath(R.raw.text_svg),
						ContentType.SVG_BASIC, question[0], question[1]));
			}
		}
		return questions.toArray(new QuizQuestion[questions.size()]);
	}
}
