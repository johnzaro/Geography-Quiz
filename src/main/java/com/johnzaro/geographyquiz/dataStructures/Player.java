package com.johnzaro.geographyquiz.dataStructures;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Locale;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;

/**
 * Created by John on 18/11/2016.
 */
public class Player
{
	//NAME
	private String originalName;
	public void setOriginalName(String name)
	{
		originalName = name;
	}
	public String getOriginalName()
	{
		return originalName;
	}
	
	private String editedName;
	public void setEditedName(String name)
	{
		editedName = name;
	}
	public String getEditedName()
	{
		return editedName;
	}
	
	//STATISTICS
	private int highestScorePoints;
	public void setHighestScorePoints(int points)
	{
		highestScorePoints = points;
	}
	public int getHighestScorePoints()
	{
		return highestScorePoints;
	}
	
	private int totalCorrectAnswers;
	public void setTotalCorrectAnswers(int correct)
	{
		totalCorrectAnswers = correct;
	}
	public int getTotalCorrectAnswers()
	{
		return totalCorrectAnswers;
	}
	
	private int totalQuestionsAnswered;
	public void setTotalQuestionsAnswered(int questions)
	{
		totalQuestionsAnswered = questions;
	}
	public int getTotalQuestionsAnswered()
	{
		return totalQuestionsAnswered;
	}
	
	private int totalTimePlayed;
	public void setTotalTimePlayed(int seconds)
	{
		totalTimePlayed = seconds;
	}
	public int getTotalTimePlayed()
	{
		return totalTimePlayed;
	}
	
	private double averageAnswerTime;
	public void setAverageAnswerTime(double time)
	{
		averageAnswerTime = time;
	}
	public double getAverageAnswerTime()
	{
		return averageAnswerTime;
	}
	
	private int maxCombo;
	public void setMaxCombo(int combo)
	{
		maxCombo = combo;
	}
	public int getMaxCombo()
	{
		return maxCombo;
	}
	
	//SETTINGS
	private Locale locale;
	public Locale getLocale()
	{
		return locale;
	}
	public void setLocale(Locale locale)
	{
		this.locale = locale;
	}
	
	private int localeIndex;
	public int getLocaleIndex()
	{
		return localeIndex;
	}
	public void setLocaleIndex(int localeIndex)
	{
		this.localeIndex = localeIndex;
	}
	
	private LANGUAGE language;
	public LANGUAGE getLanguage()
	{
		return language;
	}
	public void setLanguage(LANGUAGE language)
	{
		this.language = language;
	}
	
	private UNIT_SYSTEM unitSystem;
	public UNIT_SYSTEM getUnitSystem()
	{
		return unitSystem;
	}
	public void setUnitSystem(UNIT_SYSTEM unitSystem)
	{
		this.unitSystem = unitSystem;
	}
	
	private DIFFICULTY difficultyLevel;
	public DIFFICULTY getDifficultyLevel()
	{
		return difficultyLevel;
	}
	public void setDifficultyLevel(DIFFICULTY difficultyLevel)
	{
		this.difficultyLevel = difficultyLevel;
	}
	
	private int numberOfQuestionsInClassicalMode;
	public int getNumberOfQuestionsInClassicalMode()
	{
		return numberOfQuestionsInClassicalMode;
	}
	public void setNumberOfQuestionsInClassicalMode(int numberOfQuestionsInClassicalMode)
	{
		this.numberOfQuestionsInClassicalMode = numberOfQuestionsInClassicalMode;
	}
	
	private BooleanProperty timerForClassicMode;
	public boolean isTimerForClassicMode()
	{
		return timerForClassicMode.get();
	}
	public void setTimerForClassicMode(boolean timerForClassicMode)
	{
		this.timerForClassicMode.set(timerForClassicMode);
	}
	
	private int durationForTimeAttackMode;
	public int getDurationForTimeAttackMode()
	{
		return durationForTimeAttackMode;
	}
	public void setDurationForTimeAttackMode(int durationForTimeAttackMode)
	{
		this.durationForTimeAttackMode = durationForTimeAttackMode;
	}
	
	private int livesForEndlessMode;
	public int getLivesForEndlessMode()
	{
		return livesForEndlessMode;
	}
	public void setLivesForEndlessMode(int livesForEndlessMode)
	{
		this.livesForEndlessMode = livesForEndlessMode;
	}
	
	private BooleanProperty timerForEndlessMode;
	public boolean isTimerForEndlessMode()
	{
		return timerForEndlessMode.get();
	}
	public void setTimerForEndlessMode(boolean timerForEndlessMode)
	{
		this.timerForEndlessMode.set(timerForEndlessMode);
	}
	
	private Boolean[] questionCategories;
	public void setQuestionCategories(Boolean[] categories)
	{
		questionCategories = categories;
	}
	public Boolean[] getQuestionCategories()
	{
		return questionCategories;
	}
	
	private double masterSliderVolume;
	public void setMasterSliderVolume(double masterSliderVolume)
	{
		this.masterSliderVolume = masterSliderVolume;
	}
	public double getMasterSliderVolume()
	{
		return masterSliderVolume;
	}
	
	private double musicSliderVolume;
	public void setMusicSliderVolume(double musicSliderVolume)
	{
		this.musicSliderVolume = musicSliderVolume;
	}
	public double getMusicSliderVolume()
	{
		return musicSliderVolume;
	}
	
	private double soundEffectsSliderVolume;
	public void setSoundEffectsSliderVolume(double soundEffectsSliderVolume)
	{
		this.soundEffectsSliderVolume = soundEffectsSliderVolume;
	}
	public double getSoundEffectsSliderVolume()
	{
		return soundEffectsSliderVolume;
	}
	
	private double windowWidth;
	public void setWindowWidth(double windowWidth)
	{
		this.windowWidth = windowWidth;
	}
	public double getWindowWidth()
	{
		return windowWidth;
	}
	
	private ANIMATIONS animationsUsed;
	public ANIMATIONS getAnimationsUsed()
	{
		return animationsUsed;
	}
	public void setAnimationsUsed(ANIMATIONS animationsUsed)
	{
		this.animationsUsed = animationsUsed;
	}
	
	private boolean startAtFullScreen;
	public void setStartAtFullScreen(boolean startAtFullScreen)
	{
		this.startAtFullScreen = startAtFullScreen;
	}
	public boolean getStartAtFullScreen()
	{
		return startAtFullScreen;
	}
	
	public Player(String originalName, String editedName)
	{
		timerForClassicMode = new SimpleBooleanProperty();
		timerForEndlessMode = new SimpleBooleanProperty();
		questionCategories = new Boolean[NUM_ALL_CATEGORIES];
		
		setOriginalName(originalName);
		setEditedName(editedName);
		
		resetPlayerStatistics();
		
		setDefaultPlayerSettings();
		setDefaultQuestionCategories();
	}
	
	public void setPlayerSettings(Locale locale, int localeIndex, LANGUAGE language, UNIT_SYSTEM unitSystem, DIFFICULTY difficultyLevel, int numberOfQuestionsInClassicalMode, boolean timerForClassicMode,
								  int durationForTimeAttackMode, int livesForEndlessMode, boolean timerForEndlessMode, double masterSliderVolume, double musicSliderVolume, double soundEffectsSliderVolume,
								  double windowWidth, ANIMATIONS animationsUsed, boolean startAtFullScreen)
	{
		setLocale(locale);
		setLocaleIndex(localeIndex);
		setLanguage(language);
		setUnitSystem(unitSystem);
		
		setDifficultyLevel(difficultyLevel);
		
		setNumberOfQuestionsInClassicalMode(numberOfQuestionsInClassicalMode);
		setTimerForClassicMode(timerForClassicMode);
		setDurationForTimeAttackMode(durationForTimeAttackMode);
		setLivesForEndlessMode(livesForEndlessMode);
		setTimerForEndlessMode(timerForEndlessMode);
		
		setMasterSliderVolume(masterSliderVolume);
		setMusicSliderVolume(musicSliderVolume);
		setSoundEffectsSliderVolume(soundEffectsSliderVolume);
		setWindowWidth(windowWidth);
		setAnimationsUsed(animationsUsed);
		setStartAtFullScreen(startAtFullScreen);
	}
	
	public void setDefaultQuestionCategories()
	{
		for(int i = 0; i < NUM_CAT_COUNTRIES_ALL; i++) questionCategories[i] = true;
		for(int i = NUM_CAT_COUNTRIES_ALL; i < NUM_ALL_CATEGORIES; i++) questionCategories[i] = false;
	}
	
	public void setDefaultPlayerSettings()
	{
		setLocale(Locale.getDefault());
		setLocaleIndex(-1);
		
		if(getLocale().getLanguage().equals("el")) setLanguage(LANGUAGE.GREEK);
		else setLanguage(LANGUAGE.ENGLISH);
		
		switch(getLocale().getCountry())
		{
			case "GB":case "US": setUnitSystem(UNIT_SYSTEM.IMPERIAL);break;
			default: setUnitSystem(UNIT_SYSTEM.METRIC);break;
		}
		
		setDifficultyLevel(DIFFICULTY.EASY);
		
		setNumberOfQuestionsInClassicalMode(NUM_OF_QUESTIONS_FOR_CLASSIC_20);
		setTimerForClassicMode(true);
		setDurationForTimeAttackMode(TIME_ATTACK_DURATION_2_MINUTES);
		setLivesForEndlessMode(ENDLESS_LIVES_3);
		setTimerForEndlessMode(false);
		
		setMasterSliderVolume(100);
		setMusicSliderVolume(50);
		setSoundEffectsSliderVolume(50);
		
		setWindowWidth(0.75 * getScreenStuff().getPrimaryScreenWidth());
		
		setAnimationsUsed(ANIMATIONS.ALL);
		setStartAtFullScreen(false);
	}
	
	public void resetPlayerStatistics()
	{
		setHighestScorePoints(0);
		setTotalQuestionsAnswered(0);
		setTotalCorrectAnswers(0);
		setTotalTimePlayed(0);
		setAverageAnswerTime(0);
		setMaxCombo(0);
	}
}
