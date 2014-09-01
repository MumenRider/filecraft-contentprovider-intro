package com.filecraft.helloworld;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class QuizAnswer {

	public enum QuizAnswerSetId {
		JAPANESE_VOCAB,
		JAPANESE_BASICS;

		public static QuizAnswerSetId getId(String actionId) {
			for (QuizAnswerSetId id : QuizAnswerSetId.values()) {
				if (id.name().equals(actionId)) {
					return id;
				}
			}
			return null;
		}
	}

	public final String answer;
	public final boolean isCorrect;

	private QuizAnswer(String answer, boolean isCorrect) {
		this.answer = answer;
		this.isCorrect = isCorrect;
	}

	public static int getQuizAnswerCount(String actionId) {
		// TODO: This is ugly.
		QuizAnswerSetId id = null;
		for (int i = 0; i < JapaneseVocab.values().length; i++) {
			if (JapaneseVocab.values()[i].name().equals(actionId)) {
				id = QuizAnswerSetId.JAPANESE_VOCAB;
			}
		}
		for (int i = 0; i < JapaneseBasics.values().length; i++) {
			if (JapaneseBasics.values()[i].name().equals(actionId)) {
				id = QuizAnswerSetId.JAPANESE_BASICS;
			}
		}
		switch (id) {
		case JAPANESE_VOCAB:
			return 10;
		case JAPANESE_BASICS:
			return 6;
		}
		return 0;
	}

	public static QuizAnswer[] getQuizAnswers(String actionId, boolean inJapanese) {
		ArrayList<QuizAnswer> answers = new ArrayList<QuizAnswer>();
		String[] correctAnswer = getAnswer(actionId, inJapanese);
		answers.add(new QuizAnswer(correctAnswer[0], true));
		HashSet<String> answersAdded = new HashSet<String>();
		answersAdded.add(correctAnswer[1]);
		while (answersAdded.size() < getQuizAnswerCount(actionId)) {
			String[] answer = getRandomQuizAnswer(correctAnswer[2], inJapanese);
			if (answersAdded.add(answer[1])) {
				answers.add(new QuizAnswer(answer[0], false));
			}
		}
		return answers.toArray(new QuizAnswer[answers.size()]);
	}

	public static String[] getRandomQuizAnswer(String actionId, boolean inJapanese) {
		Random random = new Random();
		QuizAnswerSetId id = QuizAnswerSetId.getId(actionId);
		switch (id) {
		case JAPANESE_VOCAB:
			return getJapaneseVocabAnswer(random.nextInt(JapaneseVocab.values().length), inJapanese);
		case JAPANESE_BASICS:
			return getJapaneseBasicsAnswer(random.nextInt(JapaneseBasics.values().length), inJapanese);
		}
		return null;
	}

	private static String[] getAnswer(String actionId, boolean inJapanese) {
		// TODO: This is ugly.
		for (int i = 0; i < JapaneseVocab.values().length; i++) {
			if (JapaneseVocab.values()[i].name().equals(actionId)) {
				return getJapaneseVocabAnswer(i, inJapanese);
			}
		}
		for (int i = 0; i < JapaneseBasics.values().length; i++) {
			if (JapaneseBasics.values()[i].name().equals(actionId)) {
				return getJapaneseBasicsAnswer(i, inJapanese);
			}
		}
		return null;
	}

	private static String[] getJapaneseVocabAnswer(int position, boolean inJapanese) {
		JapaneseVocab vocab = JapaneseVocab.values()[position];
		return new String[] { inJapanese ? vocab.japanese : vocab.english, vocab.name(), QuizAnswerSetId.JAPANESE_VOCAB.name() };
	}

	private static String[] getJapaneseBasicsAnswer(int position, boolean inJapanese) {
		JapaneseBasics basics = JapaneseBasics.values()[position];
		return new String[] { inJapanese ? basics.japanese : basics.english, basics.name(), QuizAnswerSetId.JAPANESE_BASICS.name() };
	}

	public enum JapaneseVocab {
		YES("yes", "はい"),
		NO("no", "いいえ"),
		COMPUTER("computer", "コンピュータ"),
		CAT("cat", "ねこ"),
		DOG("dog", "いぬ"),
		FOOD("food", "たべもの"),
		DRINK("drink", "のみもの"),
		TEACHER("teacher", "せんせい"),
		THANK_YOU("thank you", "ありがと"),
		SORRY("I am sorry / excuse me", "すみません"),
		EXCUSE_ME("(eh,) excuse me", "あのう, すみません"),
		YOU_ARE_WELCOME("you are welcome", "どういたしまして"),
		GOOD_MORNING("good morning", "おはよう"),
		GOOD_AFTERNOON("good afternoon", "こんにちわ"),
		GOOD_EVENING("good evening", "こんばんわ"),
		OH("oh", "ああ"),
		WELCOME_HOME("welcome back / welcome home", "おかえりなさい"),
		THANK_YOU_ALLOWING_ME_IN("Excuse me for disturbing you / Greeting when entering someone's home", "おじゃまします"),
		IM_BACK("I'm back! / I'm home!", "ただいま"),
		RED("red", "あか"),
		GREEN("green", "みどり"),
		BLUE("blue", "あお"),
		PURPLE("purple", "むらさき"),
		BROWN("brown", "ちゃいろ"),
		BLACK("black", "くろ"),
		WHITE("white", "しろ"),
		GOLD("gold", "きん"),
		SILVER("silver", "ぎん"),
		ORANGE("orange", "だいだいいろ"),
		GREY("grey", "はいいろ");

		public final String english;
		public final String japanese;

		private JapaneseVocab(String english, String japanese) {
			this.english = english;
			this.japanese = japanese;
		}
	}

	public enum JapaneseBasics {
		PLEASE_GIVE_ME("Please give me water.", "みず おねがいします"),
		IS_THERE("Is there food?", "たべもの が ありますか"),
		HOW_MUCH("How much does the beer cost?", "ビール は いくらですか"),
		WHERE_IS("Where is the toilet?", "トイレ は どこ ですか"),
		WHAT_IS_YOUR_MAJOR("What is your [school] major?", "せんこう は なんですか"),
		WHAT_YEAR_ARE_YOU("What is your year [in school]?", "なん ねんせい ですか"),
		WHAT_IS_YOUR_JOB("What is your job?", "しごと は なん ですか"),
		HOW_OLD_ARE_YOU("How old are you?", "なんさい ですか"),
		HOW_MUCH_IS_THIS("How much does this beer cost?", "この ビール は いくら ですか"),
		WHICH_DIRECTION_IS("Which direction is the train station?", "えき は どちら ですか"),
		WHAT_TIME_DOES_START("What time does the movie start?", "えいが は なんじ に はじまりますか"),
		PLEASE_REPEAT("Could you please repeat [what you said]?", "もいちど いってください"),
		DID_NOT_UNDERSTAND("Sorry, I did not understand [what you said].", "すみません, わかりません でした"),
		HOW_DO_YOU_SAY("How do you say 'water' in Japanese?", "water わ にほんご で なんといますか"),
		WHAT_DO_YOU_DO_FOR_FUN("What do you do for fun?", "ひまのとき は なに お しますか");

		public final String english;
		public final String japanese;

		private JapaneseBasics(String english, String japanese) {
			this.english = english;
			this.japanese = japanese;
		}
	}
}
