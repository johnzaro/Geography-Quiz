package com.johnzaro.geographyquiz.dataStructures;

import com.johnzaro.geographyquiz.core.GlobalVariables;
import javafx.beans.property.*;

import java.time.*;

/**
 * Created by John on 1/2/2017.
 */

public class Game
{
	private StringProperty playerName;
	public void setPlayerName(String name)
	{
		playerNameProperty().set(name);
	}
	public String getPlayerName()
	{
		return playerNameProperty().get();
	}
	public StringProperty playerNameProperty()
	{
		return playerName;
	}
	
	private ObjectProperty<LocalDateTime> gameStartedTime;
	public void setGameStartedTime(LocalDateTime instant)
	{
		gameStartedTimeProperty().set(instant);
	}
	public LocalDateTime getGameStartedTime()
	{
		return gameStartedTimeProperty().get();
	}
	public ObjectProperty<LocalDateTime> gameStartedTimeProperty()
	{
		return gameStartedTime;
	}
	
	private ObjectProperty<Integer> gameDurationInSeconds;
	public void setGameDurationInSeconds(int seconds)
	{
		gameDurationInSecondsProperty().set(seconds);
	}
	public int getGameDurationInSeconds()
	{
		return gameDurationInSecondsProperty().get();
	}
	public ObjectProperty<Integer> gameDurationInSecondsProperty()
	{
		return gameDurationInSeconds;
	}
	
	private ObjectProperty<GlobalVariables.GAMEMODE> gameMode;
	public void setGameMode(GlobalVariables.GAMEMODE mode)
	{
		gameModeProperty().set(mode);
	}
	public GlobalVariables.GAMEMODE getGameMode()
	{
		return gameModeProperty().get();
	}
	public ObjectProperty<GlobalVariables.GAMEMODE> gameModeProperty()
	{
		return gameMode;
	}
	
	private int livesForEndless;
	public void setLivesForEndless(int lives)
	{
		livesForEndless = lives;
	}
	public int getLivesForEndless()
	{
		return livesForEndless;
	}
	
	private ObjectProperty<Boolean> isCountdownEnabled;
	public void setIsCountdownEnabled(boolean countdown)
	{
		isCountdownEnabledProperty().set(countdown);
	}
	public boolean isCountdownEnabled()
	{
		return isCountdownEnabledProperty().get();
	}
	public ObjectProperty<Boolean> isCountdownEnabledProperty()
	{
		return isCountdownEnabled;
	}
	
	private ObjectProperty<GlobalVariables.DIFFICULTY> difficultyLevel;
	public void setDifficultyLevel(GlobalVariables.DIFFICULTY difficulty)
	{
		difficultyLevelProperty().set(difficulty);
	}
	public GlobalVariables.DIFFICULTY getDifficultyLevel()
	{
		return difficultyLevelProperty().get();
	}
	public ObjectProperty<GlobalVariables.DIFFICULTY> difficultyLevelProperty()
	{
		return difficultyLevel;
	}
	
	private ObjectProperty<Integer> numberOfAllQuestions;
	public void setNumberOfAllQuestions(int questions)
	{
		numberOfAllQuestionsProperty().set(questions);
	}
	public int getNumberOfAllQuestions()
	{
		return numberOfAllQuestionsProperty().get();
	}
	public ObjectProperty<Integer> numberOfAllQuestionsProperty()
	{
		return numberOfAllQuestions;
	}
	
	private ObjectProperty<Integer> numberOfCorrectQuestions;
	public void setNumberOfCorrectQuestions(int correct)
	{
		numberOfCorrectQuestionsProperty().set(correct);
	}
	public int getNumberOfCorrectQuestions()
	{
		return numberOfCorrectQuestionsProperty().get();
	}
	public ObjectProperty<Integer> numberOfCorrectQuestionsProperty()
	{
		return numberOfCorrectQuestions;
	}
	
	private ObjectProperty<Integer> scorePoints;
	public void setScorePoints(int correct)
	{
		scorePointsProperty().set(correct);
	}
	public int getScorePoints()
	{
		return scorePointsProperty().get();
	}
	public ObjectProperty<Integer> scorePointsProperty()
	{
		return scorePoints;
	}
	
	private ObjectProperty<Integer> maxCombo;
	public void setMaxCombo(int correct)
	{
		maxComboProperty().set(correct);
	}
	public int getMaxCombo()
	{
		return maxComboProperty().get();
	}
	public ObjectProperty<Integer> maxComboProperty()
	{
		return maxCombo;
	}
	
	private ObjectProperty<Double> averageAnswerTime;
	public void setAverageAnswerTime(double time)
	{
		averageAnswerTimeProperty().set(time);
	}
	public double getAverageAnswerTime()
	{
		return averageAnswerTimeProperty().get();
	}
	public ObjectProperty<Double> averageAnswerTimeProperty()
	{
		return averageAnswerTime;
	}
	
	private Integer[] questionCategories;
	public void setQuestionCategories(Integer[] categories)
	{
		questionCategories = categories;
	}
	public Integer[] getQuestionCategories()
	{
		return questionCategories;
	}
	
	public Game()
	{
		playerName = new SimpleStringProperty(this, "playerName");
		gameStartedTime = new SimpleObjectProperty<>(this, "gameStartedTime");
		gameDurationInSeconds = new SimpleObjectProperty<>(this, "gameDurationInSeconds");
		gameMode = new SimpleObjectProperty<>(this, "gameMode");
		isCountdownEnabled = new SimpleObjectProperty<>(this, "isCountdownEnabled");
		difficultyLevel = new SimpleObjectProperty<>(this, "difficultyLevel");
		numberOfAllQuestions = new SimpleObjectProperty<>(this, "numberOfAllQuestions");
		numberOfCorrectQuestions = new SimpleObjectProperty<>(this, "numberOfCorrectQuestions");
		scorePoints = new SimpleObjectProperty<>(this, "scorePoints");
		maxCombo = new SimpleObjectProperty<>(this, "maxCombo");
		averageAnswerTime = new SimpleObjectProperty<>(this, "averageAnswerTime");
	}
}
