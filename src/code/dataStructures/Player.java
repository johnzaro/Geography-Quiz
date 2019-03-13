package code.dataStructures;

import javafx.beans.property.*;

import java.util.Locale;

import static code.core.GlobalVariables.*;

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
	
	private int language;
	public int getLanguage()
	{
		return language;
	}
	public void setLanguage(int language)
	{
		this.language = language;
	}
	
	private int unitSystem;
	public int getUnitSystem()
	{
		return unitSystem;
	}
	public void setUnitSystem(int unitSystem)
	{
		this.unitSystem = unitSystem;
	}
	
	private int difficultyLevel;
	public int getDifficultyLevel()
	{
		return difficultyLevel;
	}
	public void setDifficultyLevel(int difficultyLevel)
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
	
	private boolean[] questionCategories;
	public void setQuestionCategories(boolean[] categories)
	{
		questionCategories = categories;
	}
	public boolean[] getQuestionCategories()
	{
		return questionCategories;
	}
	
	public Player(String originalName, String editedName)
	{
		timerForClassicMode = new SimpleBooleanProperty();
		timerForEndlessMode = new SimpleBooleanProperty();
		questionCategories = new boolean[NUM_ALL_CATEGORIES];
		
		setOriginalName(originalName);
		setEditedName(editedName);
		
		resetPlayerStatistics();
		
		setDefaultPlayerSettings();
		setDefaultQuestionCategories();
	}
	
	public void setPlayerSettings(Locale locale, int localeIndex, int language, int unitsOfMeasurement, int difficultyLevel, int numberOfQuestionsInClassicalMode, boolean timerForClassicMode,
								  int durationForTimeAttackMode, int livesForEndlessMode, boolean timerForEndlessMode)
	{
		setLocale(locale);
		setLocaleIndex(localeIndex);
		setLanguage(language);
		setUnitSystem(unitsOfMeasurement);
		setDifficultyLevel(difficultyLevel);
		setNumberOfQuestionsInClassicalMode(numberOfQuestionsInClassicalMode);
		setTimerForClassicMode(timerForClassicMode);
		setDurationForTimeAttackMode(durationForTimeAttackMode);
		setLivesForEndlessMode(livesForEndlessMode);
		setTimerForEndlessMode(timerForEndlessMode);
	}
	
	public void setDefaultQuestionCategories()
	{
		for(int i = 0; i < NUM_CAT_COUNTRIES_ALL; i++) questionCategories[i] = true;
	}
	
	public void setDefaultPlayerSettings()
	{
		setLocale(Locale.getDefault());
		setLocaleIndex(-1);
		
		if(getLocale().getLanguage().equals("el")) setLanguage(LANGUAGE_GREEK);
		else setLanguage(LANGUAGE_ENGLISH);
		
		switch(getLocale().getCountry())
		{
			case "GB":case "US": setUnitSystem(IMPERIAL_SYSTEM);break;
			default: setUnitSystem(METRIC_SYSTEM);break;
		}
		
		setDifficultyLevel(DIFFICULTY_EASY);
		
		setNumberOfQuestionsInClassicalMode(NUM_OF_QUESTIONS_FOR_CLASSIC_20);
		setTimerForClassicMode(true);
		setDurationForTimeAttackMode(TIME_ATTACK_DURATION_2_MINUTES);
		setLivesForEndlessMode(ENDLESS_LIVES_3);
		setTimerForEndlessMode(false);
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
