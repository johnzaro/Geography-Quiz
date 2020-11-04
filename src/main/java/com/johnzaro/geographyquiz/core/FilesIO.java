package com.johnzaro.geographyquiz.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.johnzaro.geographyquiz.core.helperClasses.OsCheck.OSType;
import com.johnzaro.geographyquiz.dataStructures.*;
import com.johnzaro.geographyquiz.screens.errorScreens.ErrorScreen;
import javafx.application.Platform;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.StreamSupport;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;
import static com.johnzaro.geographyquiz.core.PowerOn.loadLanguageResourceBundle;
import static com.johnzaro.geographyquiz.core.PowerOn.loadPlayersDataAndSettings;
import static com.johnzaro.geographyquiz.core.helperClasses.GreekLanguageHelper.getEditedOriginalName;

public class FilesIO
{
	static File gamesScoresFile, playersFile, languageFile;
	private static File gameFolder;
	
	private final static String LANGUAGE_FILE_PATH = "language";
	private final static String PLAYERS_FILE_PATH = "players.json";
	private final static String GAMES_SCORES_FILE_PATH = "gamesScores.json";
	
	//	SET GAME DATA PATH FOR EACH OPERATING SYSTEM
	static String getGameDataPath(OSType ostype)
	{
		String temp;
		
		//		if OS is Windows the game path should be "c:/users/userName/Application data/Roaming/GeoQuiz/...."
		if (ostype == OSType.Windows) temp = System.getenv("APPDATA") + File.separator + "GeoQuiz";
			//		if OS is Mac the game path should be "/home/Library/Application Support/GeoQuiz/...."
		else if (ostype == OSType.MacOS) temp = System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support" + File.separator + "GeoQuiz";
			//		if OS is Linux the game path should be "/home/.GeoQuiz/...."
		else if (ostype == OSType.Linux) temp = System.getProperty("user.home") + File.separator + ".GeoQuiz";
		else temp = "";
		
		return temp;
	}
	
	static void createGameDataPathFolder()
	{
		gameFolder = new File(GAME_DATA_PATH);
		if(!gameFolder.exists())
		{
			try
			{
				gameFolder.createNewFile();
			}
			catch (IOException e)
			{
				Platform.runLater(() -> new ErrorScreen("Error occurred while trying to create game data path folder", e));
			}
		}
	}
	
	static LANGUAGE loadLanguageFile()
	{
		languageFile = new File(GAME_DATA_PATH + File.separator + LANGUAGE_FILE_PATH);
		
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		
		try
		{
			if (!languageFile.exists())
			{
				languageFile.createNewFile();
				
				bufferedWriter = new BufferedWriter(new FileWriter(languageFile));
				language = loadAndSaveDefaultLanguage(bufferedWriter);
			}
			else
			{
				bufferedReader = new BufferedReader(new FileReader(languageFile));
				
				String languageString = bufferedReader.readLine();
				if(languageString != null && (languageString.equals("greek") || languageString.equals("english")))
				{
					if(languageString.equals("greek"))
					{
						language = LANGUAGE.GREEK;
						loadLanguageResourceBundle(language);
					}
					else
					{
						language = LANGUAGE.ENGLISH;
						loadLanguageResourceBundle(language);
					}
				}
				else
				{
					bufferedWriter = new BufferedWriter(new FileWriter(languageFile));
					language = loadAndSaveDefaultLanguage(bufferedWriter);
				}
				bufferedReader.close();
			}
			
			return language;
		}
		catch (IOException e)
		{
			try
			{
				if(bufferedReader != null) bufferedReader.close();
				if(bufferedWriter != null) bufferedWriter.close();
			}
			catch(IOException ex)
			{
				Platform.runLater(() -> new ErrorScreen("Error occurred while trying to close language file", ex));
			}
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load language file", e));
			
			return LANGUAGE.ENGLISH;
		}
	}
	
	private static LANGUAGE loadAndSaveDefaultLanguage(BufferedWriter bufferedWriter) throws IOException
	{
		if(Locale.getDefault().getLanguage().equals("el"))
		{
			language = LANGUAGE.GREEK;
			loadLanguageResourceBundle(language);
			bufferedWriter.write("greek");
		}
		else
		{
			language = LANGUAGE.ENGLISH;
			loadLanguageResourceBundle(language);
			bufferedWriter.write("english");
		}
		bufferedWriter.close();
		
		return language;
	}
	
	public static void writeCurrentLanguage(LANGUAGE language)
	{
		BufferedWriter bufferedWriter = null;
		
		try
		{
			bufferedWriter = new BufferedWriter(new FileWriter(languageFile));
			
			if(language == LANGUAGE.ENGLISH) bufferedWriter.write("english");
			else bufferedWriter.write("greek");
			
			bufferedWriter.close();
		}
		catch(IOException e)
		{
			try
			{
				if(bufferedWriter != null) bufferedWriter.close();
			}
			catch(IOException ex)
			{
				Platform.runLater(() -> new ErrorScreen("Error occurred while trying to close write to language file", ex));
			}
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to write language file", e));
		}
	}
	
	//	SETUP AND CREATE SAVE FILES
	static void setupFiles()
	{
		playersFile = new File(GAME_DATA_PATH + File.separator + PLAYERS_FILE_PATH);
		gamesScoresFile = new File(GAME_DATA_PATH + File.separator + GAMES_SCORES_FILE_PATH);
		
		try
		{
			if (!playersFile.exists()) playersFile.createNewFile();
			if (!gamesScoresFile.exists()) gamesScoresFile.createNewFile();
		}
		catch (IOException e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to create data files", e));
		}
	}
	
	public static boolean deleteAllData()
	{
		try
		{
			if(gameFolder.exists()) FileUtils.forceDelete(gameFolder);
			
			playersArrayList = new ArrayList<>();
			games = new ArrayList<>();
			
			FilesIO.setupFiles();

			loadPlayersDataAndSettings();
			
			return true;
		}
		catch (Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to erase all data", e));
			
			return false;
		}
	}
	
	static void setDefaultPlayerName()
	{
		playersArrayList.clear();
		playersArrayList.add(new Player(System.getProperty("user.name"), getEditedOriginalName(System.getProperty("user.name"))));
		writePlayersFile();
	}
	
	static void readPlayersFile()
	{
		Player tempPlayer;
		
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(playersFile);
			
			String original, edited;
			boolean exists;
			
			for(JsonNode playerNode : root)
			{
				original = playerNode.path("originalName").asText();
				edited = playerNode.path("editedName").asText();
				exists = false;
				
				for(Player originalName: playersArrayList)
				{
					if(originalName.getOriginalName().equals(original))
					{
						exists = true;
						break;
					}
				}
				
				if(!exists)
				{
					tempPlayer = new Player(original, edited);
					tempPlayer.setHighestScorePoints(playerNode.path("highestScorePoints").asInt());
					tempPlayer.setTotalCorrectAnswers(playerNode.path("totalCorrectAnswers").asInt());
					tempPlayer.setTotalQuestionsAnswered(playerNode.path("totalQuestionsAnswered").asInt());
					tempPlayer.setTotalTimePlayed(playerNode.path("totalTimePlayed").asInt());
					tempPlayer.setAverageAnswerTime(playerNode.path("averageAnswerTime").asDouble());
					tempPlayer.setMaxCombo(playerNode.path("maxCombo").asInt());
					
					Locale locale = Locale.forLanguageTag(playerNode.path("locale").asText());
					int localeIndex = playerNode.path("localeIndex").asInt();
					int languageInt = playerNode.path("language").asText().equals("ENGLISH")? 0 : 1;
					int unitSystem = playerNode.path("unitSystem").asText().equals("METRIC")? 0 : 1;
					int difficulty = playerNode.path("difficultyLevel").asText().equals("EASY")? 0 : 1;
					int numForClassic = playerNode.path("numberOfQuestionsInClassicalMode").asInt();
					int duration = playerNode.path("durationForTimeAttackMode").asInt();
					int lives = playerNode.path("livesForEndlessMode").asInt();
					boolean timerClassic = playerNode.path("timerForClassicMode").asBoolean();
					boolean timerEndless = playerNode.path("timerForEndlessMode").asBoolean();
					
					tempPlayer.setQuestionCategories(StreamSupport.stream(playerNode.path("questionCategories").spliterator(), false).map(JsonNode::asBoolean).toArray(Boolean[]::new));
					
					double masterSliderVolume = playerNode.path("masterSliderVolume").asDouble();
					double musicSliderVolume = playerNode.path("musicSliderVolume").asDouble();
					double soundEffectsSliderVolume = playerNode.path("soundEffectsSliderVolume").asDouble();
					
					double windowWidth = playerNode.path("windowWidth").asDouble();
					//check if width got is valid for current screen resolution
					if (!getScreenStuff().isWidthValid(windowWidth)) windowWidth = 0.75 * getScreenStuff().getPrimaryScreenWidth();
					
					boolean startAtFullScreen = playerNode.path("startAtFullScreen").asBoolean();
					
					int animationsUsed = 0;
					switch(playerNode.path("animationsUsed").asText())
					{
						case "NO": animationsUsed = 0;break;
						case "LIMITED": animationsUsed = 1; break;
						case "ALL": animationsUsed = 2; break;
					}
					
					if((localeIndex < 0 || localeIndex > NUM_ALL_COUNTRIES) || (languageInt < 0 || languageInt > 1) || (unitSystem < 0 || unitSystem > 1) || (difficulty < 0 || difficulty > 1) ||
						(numForClassic != NUM_OF_QUESTIONS_FOR_CLASSIC_10  && numForClassic != NUM_OF_QUESTIONS_FOR_CLASSIC_20  &&
						 numForClassic != NUM_OF_QUESTIONS_FOR_CLASSIC_50  && numForClassic != NUM_OF_QUESTIONS_FOR_CLASSIC_100) ||
						(duration != TIME_ATTACK_DURATION_1_MINUTE && duration != TIME_ATTACK_DURATION_2_MINUTES &&
						 duration != TIME_ATTACK_DURATION_5_MINUTES && duration != TIME_ATTACK_DURATION_10_MINUTES) ||
						(lives != ENDLESS_LIVES_1 && lives != ENDLESS_LIVES_3 && lives != ENDLESS_LIVES_5) ||
						(masterSliderVolume < 0.0 || masterSliderVolume > 100.0 ||
						 musicSliderVolume < 0.0 || musicSliderVolume > 100.0 ||
						 soundEffectsSliderVolume < 0.0 || soundEffectsSliderVolume > 100.0) ||
						 animationsUsed < 0 || animationsUsed > 2)
							tempPlayer.setDefaultPlayerSettings();
					else
					{
						if(languageInt == 0) language = LANGUAGE.ENGLISH;
						else language = LANGUAGE.GREEK;
						
						UNIT_SYSTEM unS;
						if(unitSystem == 0) unS = UNIT_SYSTEM.METRIC;
						else unS = UNIT_SYSTEM.IMPERIAL;
						
						ANIMATIONS an;
						if(animationsUsed == 0) an = ANIMATIONS.NO;
						else if(animationsUsed == 1) an = ANIMATIONS.LIMITED;
						else an = ANIMATIONS.ALL;
						
						DIFFICULTY df;
						if(difficulty == 0) df = DIFFICULTY.EASY;
						else df = DIFFICULTY.DIFFICULT;
						
						tempPlayer.setPlayerSettings(locale, localeIndex, language, unS, df, numForClassic, timerClassic,
								duration, lives, timerEndless, masterSliderVolume, musicSliderVolume, soundEffectsSliderVolume, windowWidth, an, startAtFullScreen);
					}
					playersArrayList.add(tempPlayer);
				}
			}
			
			if(playersArrayList.size() == 0) setDefaultPlayerName();
			else if(root.size() != playersArrayList.size()) writePlayersFile();
		}
		catch (Exception e)
		{
			try
			{
				setDefaultPlayerName();
			}
			catch(Exception ev)
			{
				Platform.runLater(() -> new ErrorScreen("Error occurred while trying to read players' data", ev));
			}
		}
	}
	
	//	method to write user name in playersFile
	public static void writePlayersFile()
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			mapper.writerWithDefaultPrettyPrinter().writeValue(playersFile, playersArrayList);
		}
		catch (IOException e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to save player names", e));
		}
	}
	
	static void readGamesScores()
	{
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule().addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatForSaving)));
			mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			JsonNode root = mapper.readTree(gamesScoresFile);
			
			for(JsonNode gameScoreNode : root)
			{
				Game game = new Game();
				game.setPlayerName(gameScoreNode.path("playerName").asText());
				game.setGameStartedTime(LocalDateTime.parse(gameScoreNode.path("gameStartedTime").asText(), dateTimeFormatForSaving));
				game.setGameDurationInSeconds(gameScoreNode.path("gameDurationInSeconds").asInt());
				
				switch(gameScoreNode.path("gameMode").asText())
				{
					case "CLASSIC_GAMEMODE": game.setGameMode(GAMEMODE.CLASSIC_GAMEMODE);break;
					case "TIME_ATTACK_GAMEMODE": game.setGameMode(GAMEMODE.TIME_ATTACK_GAMEMODE);break;
					case "ENDLESS_GAMEMODE": game.setGameMode(GAMEMODE.ENDLESS_GAMEMODE); game.setLivesForEndless(gameScoreNode.path("livesForEndless").asInt());break;
				}
				
				game.setIsCountdownEnabled(gameScoreNode.path("isCountdownEnabled").asBoolean());
				
				switch(gameScoreNode.path("difficultyLevel").asText())
				{
					case "EASY": game.setDifficultyLevel(DIFFICULTY.EASY);break;
					case "DIFFICULT": game.setDifficultyLevel(DIFFICULTY.DIFFICULT);break;
				}
				
				game.setNumberOfAllQuestions(gameScoreNode.path("numberOfAllQuestions").asInt());
				game.setNumberOfCorrectQuestions(gameScoreNode.path("numberOfCorrectQuestions").asInt());
				game.setScorePoints(gameScoreNode.path("scorePoints").asInt());
				game.setMaxCombo(gameScoreNode.path("maxCombo").asInt());
				game.setAverageAnswerTime(gameScoreNode.path("averageAnswerTime").asDouble());
				
				game.setQuestionCategories(StreamSupport.stream(gameScoreNode.path("questionCategories").spliterator(), false).map(JsonNode::asInt).toArray(Integer[]::new));
				
				games.add(game);
			}
		}
		catch (Exception e)
		{
			try
			{
				e.printStackTrace();
				gamesScoresFile.delete();
				gamesScoresFile.createNewFile();
			}
			catch (Exception ex)
			{
				Platform.runLater(() -> new ErrorScreen("Error occurred while trying to read games scores", e));
			}
		}
	}
	
	public static void writeGameScores()
	{
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule().addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatForSaving)));
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		
		try
		{
			mapper.writerWithDefaultPrettyPrinter().writeValue(gamesScoresFile, games);
		}
		catch (IOException e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to save game scores", e));
		}
		
//		try
//		{
//			Element  gamesElement = new Element("games");
//			Document doc = new Document(gamesElement);
//
//			for(Game game : games)
//			{
//				Element gameElement = new Element("game");
//
//				gameElement.addContent(new Element("playerName").setText(game.getPlayerName()));
//				gameElement.addContent(new Element("gameStartedTime").setText(game.getGameStartedTime().format(dateTimeFormatForSaving)));
//				gameElement.addContent(new Element("gameDurationInSeconds").setText(String.valueOf(game.getGameDurationInSeconds())));
//
//				switch(game.getGameMode())
//				{
//					case CLASSIC_GAMEMODE: gameElement.addContent(new Element("gameMode").setText("0")); break;
//					case TIME_ATTACK_GAMEMODE: gameElement.addContent(new Element("gameMode").setText("1")); break;
//					case ENDLESS_GAMEMODE: gameElement.addContent(new Element("gameMode").setText("2")); break;
//				}
//
//				if(game.getGameMode() == GAMEMODE.ENDLESS_GAMEMODE)
//					gameElement.addContent(new Element("livesForEndless").setText(String.valueOf(game.getLivesForEndless())));
//
//				gameElement.addContent(new Element("isCountdownEnabled").setText(String.valueOf(game.isCountdownEnabled())));
//
//				switch(game.getDifficultyLevel())
//				{
//					case EASY: gameElement.addContent(new Element("difficultyLevel").setText("0")); break;
//					case DIFFICULT: gameElement.addContent(new Element("difficultyLevel").setText("1")); break;
//				}
//
//				gameElement.addContent(new Element("numberOfAllQuestions").setText(String.valueOf(game.getNumberOfAllQuestions())));
//				gameElement.addContent(new Element("numberOfCorrectQuestions").setText(String.valueOf(game.getNumberOfCorrectQuestions())));
//				gameElement.addContent(new Element("scorePoints").setText(String.valueOf(game.getScorePoints())));
//				gameElement.addContent(new Element("maxCombo").setText(String.valueOf(game.getMaxCombo())));
//				gameElement.addContent(new Element("averageAnswerTime").setText(String.valueOf(game.getAverageAnswerTime())));
//
//				StringBuilder sb = new StringBuilder();
//				for(int j = 0; j < game.getQuestionCategories().length; j++) sb.append(game.getQuestionCategories()[j]).append(" ");
//				sb.deleteCharAt(sb.length() - 1);
//				gameElement.addContent(new Element("questionCategories").setText(sb.toString()));
//
//				gamesElement.addContent(gameElement);
//			}
//
//			XMLOutputter xmlOutput = new XMLOutputter();
//			xmlOutput.setFormat(Format.getPrettyFormat().setIndent("\t"));
//			xmlOutput.output(doc, new OutputStreamWriter(new FileOutputStream(gamesScoresFile, false), "UTF-8"));
//		}
//		catch (Exception e)
//		{
//			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to save game scores", e));
//		}
	}
	
	public static void loadRatioProperties()
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			JsonNode root = mapper.readTree(FilesIO.class.getResourceAsStream("/dataFiles/ratioProperties.json"));
			JsonNode screensNode = root.path("ratioProperties");
			
			String ratioName;
			if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_16_9) ratioName = "ratio_16_9";
			else if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_16_10) ratioName = "ratio_16_10";
			else if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_25_16) ratioName = "ratio_25_16";
			else if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_3_2) ratioName = "ratio_3_2";
			else if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_4_3) ratioName = "ratio_4_3";
			else if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_5_4) ratioName = "ratio_5_4";
			else ratioName = null;
			
			for (JsonNode screenNode: screensNode)
			{
				JsonNode ratio = screenNode.path(ratioName);
				
				switch(screenNode.path("name").asText())
				{
					case "core":
						ratioProperties.getCore().setvBoxForSoundPrefHeight(ratio.path("vBoxForSoundPrefHeight").asDouble());
						ratioProperties.getCore().setvBoxForSoundPrefWidth(ratio.path("vBoxForSoundPrefWidth").asDouble());
						break;
					case "atlas":
						ratioProperties.getAtlas().sethBoxForToggleButtonsLayoutY(ratio.path("hBoxForToggleButtonsLayoutY").asDouble());
						ratioProperties.getAtlas().setTitleImageSetY(ratio.path("titleImageSetY").asDouble());
						ratioProperties.getAtlas().setTitleLabelSetX(ratio.path("titleLabelSetX").asDouble());
						ratioProperties.getAtlas().setTitleLabelSetY(ratio.path("titleLabelSetY").asDouble());
						ratioProperties.getAtlas().setvBoxForSoundLayoutY(ratio.path("vBoxForSoundLayoutY").asDouble());
						ratioProperties.getAtlas().setWoodPanelFor1IconImageLayoutX(ratio.path("woodPanelFor1IconImageLayoutX").asDouble());
						ratioProperties.getAtlas().setWoodPanelFor1IconImageLayoutY(ratio.path("woodPanelFor1IconImageLayoutY").asDouble());
						ratioProperties.getAtlas().setSoundIconLayoutY(ratio.path("soundIconLayoutY").asDouble());
						break;
					case "gameProperties":
						ratioProperties.getGameProperties().setTitleImage1LayoutY_1(ratio.path("titleImage1LayoutY_1").asDouble());
						ratioProperties.getGameProperties().setTitleImage1LayoutY_2(ratio.path("titleImage1LayoutY_2").asDouble());
						ratioProperties.getGameProperties().setTitleLabel1LayoutX_1(ratio.path("titleLabel1LayoutX_1").asDouble());
						ratioProperties.getGameProperties().setTitleLabel1LayoutX_2(ratio.path("titleLabel1LayoutX_2").asDouble());
						ratioProperties.getGameProperties().setTitleLabel1LayoutY_1(ratio.path("titleLabel1LayoutY_1").asDouble());
						ratioProperties.getGameProperties().setTitleLabel1LayoutY_2(ratio.path("titleLabel1LayoutY_2").asDouble());
						ratioProperties.getGameProperties().setTitleImage2LayoutY(ratio.path("titleImage2LayoutY").asDouble());
						ratioProperties.getGameProperties().setTitleLabel2LayoutX(ratio.path("titleLabel2LayoutX").asDouble());
						ratioProperties.getGameProperties().setTitleLabel2LayoutY(ratio.path("titleLabel2LayoutY").asDouble());
						ratioProperties.getGameProperties().setGridPaneForQuestionsCategoriesWidth(ratio.path("gridPaneForQuestionsCategoriesWidth").asDouble());
						ratioProperties.getGameProperties().setGridPaneForQuestionsCategoriesHeight(ratio.path("gridPaneForQuestionsCategoriesHeight").asDouble());
						ratioProperties.getGameProperties().setGridPaneForQuestionsCategoriesLayoutY(ratio.path("gridPaneForQuestionsCategoriesLayoutY").asDouble());
						ratioProperties.getGameProperties().setvBoxForDifficultyLevelWidth(ratio.path("vBoxForDifficultyLevelWidth").asDouble());
						ratioProperties.getGameProperties().setvBoxForDifficultyLevelHeight(ratio.path("vBoxForDifficultyLevelHeight").asDouble());
						ratioProperties.getGameProperties().setvBoxForDifficultyLevelLayoutY(ratio.path("vBoxForDifficultyLevelLayoutY").asDouble());
						ratioProperties.getGameProperties().setExtendedQuestionCategoriesWidth(ratio.path("extendedQuestionCategoriesWidth").asDouble());
						ratioProperties.getGameProperties().setExtendedQuestionCategoriesHeight(ratio.path("extendedQuestionCategoriesHeight").asDouble());
						ratioProperties.getGameProperties().setScrollPaneForExtendedCategoryQuestionsGridPaneHeight(ratio.path("scrollPaneForExtendedCategoryQuestionsGridPaneHeight").asDouble());
						ratioProperties.getGameProperties().sethBoxForGameModesWidth(ratio.path("hBoxForGameModesWidth").asDouble());
						ratioProperties.getGameProperties().sethBoxForGameModesHeight(ratio.path("hBoxForGameModesHeight").asDouble());
						ratioProperties.getGameProperties().sethBoxForGameModesLayoutY(ratio.path("hBoxForGameModesLayoutY").asDouble());
						ratioProperties.getGameProperties().setButtonHeight(ratio.path("buttonHeight").asDouble());
						ratioProperties.getGameProperties().setHeightOfDescriptions(ratio.path("heightOfDescriptions").asDouble());
						ratioProperties.getGameProperties().setBackButtonLayoutY(ratio.path("backButtonLayoutY").asDouble());
						ratioProperties.getGameProperties().setWoodPanelFor1IconImageLayoutX(ratio.path("woodPanelFor1IconImageLayoutX").asDouble());
						ratioProperties.getGameProperties().setWoodPanelFor1IconImageLayoutY(ratio.path("woodPanelFor1IconImageLayoutY").asDouble());
						ratioProperties.getGameProperties().setSoundIconLayoutY(ratio.path("soundIconLayoutY").asDouble());
						ratioProperties.getGameProperties().setvBoxForSoundLayoutY(ratio.path("vBoxForSoundLayoutY").asDouble());
						break;
					case "game":
						ratioProperties.getGame().setvBoxForSoundLayoutY(ratio.path("vBoxForSoundLayoutY").asDouble());
						ratioProperties.getGame().setWoodPanelFor1IconImageLayoutX(ratio.path("woodPanelFor1IconImageLayoutX").asDouble());
						ratioProperties.getGame().setWoodPanelFor1IconImageLayoutY(ratio.path("woodPanelFor1IconImageLayoutY").asDouble());
						ratioProperties.getGame().setSoundIconLayoutY(ratio.path("soundIconLayoutY").asDouble());
						break;
					case "scoreBoard":
						ratioProperties.getScoreBoard().setTitleImageSetY(ratio.path("titleImageSetY").asDouble());
						ratioProperties.getScoreBoard().setTitleLabelSetX(ratio.path("titleLabelSetX").asDouble());
						ratioProperties.getScoreBoard().setTitleLabelSetY(ratio.path("titleLabelSetY").asDouble());
						ratioProperties.getScoreBoard().setvBoxForSoundLayoutY(ratio.path("vBoxForSoundLayoutY").asDouble());
						ratioProperties.getScoreBoard().setWoodPanelFor1IconImageLayoutX(ratio.path("woodPanelFor1IconImageLayoutX").asDouble());
						ratioProperties.getScoreBoard().setWoodPanelFor1IconImageLayoutY(ratio.path("woodPanelFor1IconImageLayoutY").asDouble());
						ratioProperties.getScoreBoard().setSoundIconLayoutY(ratio.path("soundIconLayoutY").asDouble());
						break;
					case "welcome":
						ratioProperties.getWelcome().setEditNameIconLayoutY(ratio.path("editNameIconLayoutY").asDouble());
						ratioProperties.getWelcome().setGameNameImageLayoutY(ratio.path("gameNameImageLayoutY").asDouble());
						ratioProperties.getWelcome().setGlobeImageFitWidth(ratio.path("globeImageFitWidth").asDouble());
						ratioProperties.getWelcome().setGlobeImageLayoutY(ratio.path("globeImageLayoutY").asDouble());
						ratioProperties.getWelcome().setGlobeStandFitWidth(ratio.path("globeStandFitWidth").asDouble());
						ratioProperties.getWelcome().setGlobeStandLayoutY(ratio.path("globeStandLayoutY").asDouble());
						ratioProperties.getWelcome().sethBoxForSettingsAndInfoIconsLayoutY(ratio.path("hBoxForSettingsAndInfoIconsLayoutY").asDouble());
						ratioProperties.getWelcome().setLeftGlobeImageLayoutX(ratio.path("leftGlobeImageLayoutX").asDouble());
						ratioProperties.getWelcome().setLeftGlobeStandLayoutX(ratio.path("leftGlobeStandLayoutX").asDouble());
						ratioProperties.getWelcome().setRectangleForInfoAboutGameLayoutY(ratio.path("rectangleForInfoAboutGameLayoutY").asDouble());
						ratioProperties.getWelcome().setRightGlobeImageLayoutX(ratio.path("rightGlobeImageLayoutX").asDouble());
						ratioProperties.getWelcome().setSoundIconLayoutY(ratio.path("soundIconLayoutY").asDouble());
						ratioProperties.getWelcome().setUsersEditSegmentedButtonLayoutY(ratio.path("usersEditSegmentedButtonLayoutY").asDouble());
						ratioProperties.getWelcome().setvBoxesForEditUsersLayoutY(ratio.path("vBoxesForEditUsersLayoutY").asDouble());
						ratioProperties.getWelcome().setvBoxForMainButtonsLayoutY(ratio.path("vBoxForMainButtonsLayoutY").asDouble());
						ratioProperties.getWelcome().setvBoxForMainButtonsPrefHeight(ratio.path("vBoxForMainButtonsPrefHeight").asDouble());
						ratioProperties.getWelcome().setvBoxForMainButtonsPrefWidth(ratio.path("vBoxForMainButtonsPrefWidth").asDouble());
						ratioProperties.getWelcome().setvBoxForMainButtonsSpacing(ratio.path("vBoxForMainButtonsSpacing").asDouble());
						ratioProperties.getWelcome().setvBoxForSettingsLayoutY(ratio.path("vBoxForSettingsLayoutY").asDouble());
						ratioProperties.getWelcome().setWelcomeImageLayoutY(ratio.path("welcomeImageLayoutY").asDouble());
						ratioProperties.getWelcome().setWelcomeLabelLayoutY(ratio.path("welcomeLabelLayoutY").asDouble());
						ratioProperties.getWelcome().setvBoxForSoundLayoutX(ratio.path("vBoxForSoundLayoutX").asDouble());
						ratioProperties.getWelcome().setWoodPanelFor1IconImageLayoutX(ratio.path("woodPanelFor1IconImageLayoutX").asDouble());
						ratioProperties.getWelcome().setWoodPanelFor1IconImageLayoutY(ratio.path("woodPanelFor1IconImageLayoutY").asDouble());
						ratioProperties.getWelcome().setvBoxForSoundLayoutY(ratio.path("vBoxForSoundLayoutY").asDouble());
						break;
				}
			}
		}
		catch (Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load ratio properties", e));
		}
	}
	
	//	helper method to check if a file exists and has content inside
	static boolean isEmpty(File file)
	{
		try
		{
			//first check if the file exists
			if (file.exists())
			{
				//if the file exists create a scanner that tries to read the first word the file has with utf-8 encoding
				Scanner input = new Scanner(new FileInputStream(file), "UTF-8");
				
				//if the file has text inside then return false to isEmpty else return true
				if (input.hasNext())
				{
					input.close();
					return false;
				}
				else
				{
					input.close();
					return true;
				}
			}
			//if the file does not exist return true to isEmpty
			else return true;
		}
		catch (FileNotFoundException e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to check if the file: " + file.getName() + " was empty", e));
		}
		return true;
	}
	
	public static void readCountriesDataFile()
	{
		ObjectMapper mapper = new ObjectMapper();

		try
		{
			JsonNode root = mapper.readTree(FilesIO.class.getResourceAsStream("/dataFiles/countries.json"));
			JsonNode countriesNode = root.path("countries");

			countries = new Country[NUM_ALL_COUNTRIES];

			int i = 0;
			for(JsonNode countryNode : countriesNode)
			{
				if(countries[i] == null) countries[i] = new Country();

				countries[i].setPositionInCapitals(countryNode.path("otherValues").path("positionInCapitals").asInt());
				countries[i].setAskCapital(countryNode.path("otherValues").path("askCapital").asInt());
				countries[i].setAskLargestCity(countryNode.path("otherValues").path("askLargestCity").asInt());
				countries[i].setHasEasyLocation(countryNode.path("otherValues").path("hasEasyLocation").asBoolean());
				countries[i].setAskGeographicalCharacteristics(countryNode.path("otherValues").path("askGeoChar").asInt());
				countries[i].setAskCurrency(countryNode.path("otherValues").path("askCurrency").asInt());
				countries[i].setHasEasyFlag(countryNode.path("otherValues").path("hasEasyFlag").asBoolean());
				countries[i].setAskForCoatOfArms(countryNode.path("otherValues").path("askCoatOfArms").asBoolean());
				countries[i].setAskLanguage(countryNode.path("otherValues").path("askLanguage").asInt());
				countries[i].setAskContinent(countryNode.path("otherValues").path("askContinent").asInt());
				countries[i].setHasSea(countryNode.path("hasSea").asBoolean());
				countries[i].setLocaleCountryCode(countryNode.path("otherValues").path("localeCountryCode").asText());

				String temp = countryNode.path("otherValues").path("localeLanguageCode").asText();
				if(temp.equals("-")) countries[i].setLocaleLanguageCode("");
				else countries[i].setLocaleLanguageCode(temp);

				if(countryNode.path("isIslandCountry") != null) countries[i].setIsIslandCountry(countryNode.path("isIslandCountry").asBoolean());
				else countries[i].setIsIslandCountry(false);

				countries[i].setNameInGreek(countryNode.path("greekName").asText());
				countries[i].setNameInEnglish(countryNode.path("englishName").asText());
				
				countries[i].setIsSovereignState(countryNode.path("isSovereignState").asBoolean());

				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					countries[i].setCapitalName(countryNode.path("greekCapital").asText());
					countries[i].setContinent(countryNode.path("continentInGreek").asText());

					countries[i].setArticleForCountry(countryNode.path("articleForCountry").asText());
					countries[i].setGenitiveCaseOfCountry(countryNode.path("genitiveCaseOfCountry").asText());
					countries[i].setArticleForCapital(countryNode.path("articleForCapital").asText());

					countries[i].setLargestCities(StreamSupport.stream(countryNode.path("largestCitiesInGreek").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));

					countries[i].setLanguagesString(countryNode.path("languagesStringInGreek").asText());

					if(countries[i].askLanguage() != 0)
						countries[i].setLanguagesToAsk(StreamSupport.stream(countryNode.path("languagesToAskInGreek").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));

					Currency currency = new Currency();
					currency.setName(countryNode.path("currency").path("nameInGreek").asText());
					currency.setSymbol(countryNode.path("currency").path("symbol").asText());
					currency.setISOCode(countryNode.path("currency").path("ISOCode").asText());
					currency.setSubdivision(countryNode.path("currency").path("subdivisionInGreek").asText());
					currency.setNumberOfSubDivisions(countryNode.path("currency").path("numberOfSubdivisions").asText());
					countries[i].setCurrency(currency);
					
					if(!countries[i].isSovereignState()) countries[i].setSovereignState(countryNode.path("sovereignStateInGreek").asText());
				}
				else
				{
					countries[i].setCapitalName(countryNode.path("englishCapital").asText());
					countries[i].setContinent(countryNode.path("continentInEnglish").asText());

					countries[i].setLargestCities(StreamSupport.stream(countryNode.path("largestCitiesInEnglish").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));

					countries[i].setLanguagesString(countryNode.path("languagesStringInEnglish").asText());

					if(countries[i].askLanguage() != 0)
						countries[i].setLanguagesToAsk(StreamSupport.stream(countryNode.path("languagesToAskInEnglish").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));

					Currency currency = new Currency();
					currency.setName(countryNode.path("currency").path("nameInEnglish").asText());
					currency.setSymbol(countryNode.path("currency").path("symbol").asText());
					currency.setISOCode(countryNode.path("currency").path("ISOCode").asText());
					currency.setSubdivision(countryNode.path("currency").path("subdivisionInEnglish").asText());
					currency.setNumberOfSubDivisions(countryNode.path("currency").path("numberOfSubdivisions").asText());
					countries[i].setCurrency(currency);
					
					if(!countries[i].isSovereignState()) countries[i].setSovereignState(countryNode.path("sovereignStateInEnglish").asText());
				}

				Area area = new Area();
				area.setAreaInKilometers(countryNode.path("areaData").path("areaInKilometers").asDouble());
				area.setAreaInMiles(countryNode.path("areaData").path("areaInMiles").asDouble());
				area.setGlobalRanking(countryNode.path("areaData").path("globalRanking").asInt());
				area.setPercentOfWater(countryNode.path("areaData").path("percentOfWater").asDouble());
				if(countryNode.path("areaData").path("coastlineInKilometers") != null)
				{
					area.setCoastlineInKilometers(countryNode.path("areaData").path("coastlineInKilometers").asInt());
					area.setCoastlineInMiles(countryNode.path("areaData").path("coastlineInMiles").asInt());
				}
				else
				{
					area.setCoastlineInKilometers(0);
					area.setCoastlineInMiles(0);
				}
				if(countryNode.path("areaData").path("bordersInKilometers") != null)
				{
					area.setBordersInKilometers(countryNode.path("areaData").path("bordersInKilometers").asInt());
					area.setBordersInMiles(countryNode.path("areaData").path("bordersInMiles").asInt());
				}
				else
				{
					area.setBordersInKilometers(0);
					area.setBordersInMiles(0);
				}
				countries[i].setArea(area);

				Population population = new Population();
				population.setPopulation(countryNode.path("populationData").path("population").asInt());
				population.setPopulationDensityPerSquareKilometer(countryNode.path("populationData").path("populationDensityPerSquareKilometer").asDouble());
				population.setPopulationDensityPerSquareMile(countryNode.path("populationData").path("populationDensityPerSquareMile").asDouble());
				population.setGlobalRanking(countryNode.path("populationData").path("globalRanking").asInt());
				countries[i].setPopulation(population);
				
				i++;
			}
		}
		catch (Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load data for countries", e));
		}
	}
	
	public static void readContinentsDataFile()
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			JsonNode root = mapper.readTree(FilesIO.class.getResourceAsStream("/dataFiles/continents.json"));
			JsonNode continentsNode = root.path("continents");
			
			continents = new Continent[Continent.NUMBER_OF_CONTINENTS];
			
			int i = 0;
			for(JsonNode continentNode : continentsNode)
			{
				if (continents[i] == null) continents[i] = new Continent();
				
				continents[i].setNameInGreek(continentNode.path("greekName").asText());
				continents[i].setNameInEnglish(continentNode.path("englishName").asText());
				
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					continents[i].setGenitiveCaseOfContinent(continentNode.path("genitiveCaseOfContinent").asText());
					continents[i].setNumberOfCountries(continentNode.path("numberOfCountriesInGreek").asText());
					
					continents[i].setLargestCountriesByArea(StreamSupport.stream(continentNode.path("largestCountriesByAreaInGreek").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));
					continents[i].setLargestCountriesByPopulation(StreamSupport.stream(continentNode.path("largestCountriesByPopulationInGreek").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));
					continents[i].setLargestCities(StreamSupport.stream(continentNode.path("largestCitiesInGreek").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));
					
					continents[i].setLanguages(continentNode.path("languagesInGreek").asText());
					continents[i].setHighestPoint(continentNode.path("highestPointInGreek").asText());
					continents[i].setLowestPoint(continentNode.path("lowestPointInGreek").asText());
					continents[i].setLongestRiver(continentNode.path("longestRiverInGreek").asText());
					continents[i].setLargestLake(continentNode.path("largestLakeInGreek").asText());
					
					continents[i].setPopulationString(continentNode.path("populationData").path("populationInGreek").asText());
				}
				else
				{
					continents[i].setNumberOfCountries(continentNode.path("numberOfCountriesInEnglish").asText());
					
					continents[i].setLargestCountriesByArea(StreamSupport.stream(continentNode.path("largestCountriesByAreaInEnglish").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));
					continents[i].setLargestCountriesByPopulation(StreamSupport.stream(continentNode.path("largestCountriesByPopulationInEnglish").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));
					continents[i].setLargestCities(StreamSupport.stream(continentNode.path("largestCitiesInEnglish").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));
					
					continents[i].setLanguages(continentNode.path("languagesInEnglish").asText());
					continents[i].setHighestPoint(continentNode.path("highestPointInEnglish").asText());
					continents[i].setLowestPoint(continentNode.path("lowestPointInEnglish").asText());
					continents[i].setLongestRiver(continentNode.path("longestRiverInEnglish").asText());
					continents[i].setLargestLake(continentNode.path("largestLakeInEnglish").asText());
					
					continents[i].setPopulationString(continentNode.path("populationData").path("populationInEnglish").asText());
				}
				continents[i].setTimeZones(continentNode.path("timeZones").asText());
				
				continents[i].setAreaInKilometers(continentNode.path("areaData").path("areaInKilometers").asInt());
				continents[i].setAreaInMiles(continentNode.path("areaData").path("areaInMiles").asInt());
				continents[i].setGlobalAreaRanking(continentNode.path("areaData").path("globalRanking").asInt());
				continents[i].setPercentOfEarth(continentNode.path("areaData").path("percentOfTheEarth").asInt());
				continents[i].setPercentOfLandOfEarth(continentNode.path("areaData").path("percentOfLandOfTheEarth").asInt());
				continents[i].setCoastlineInKilometers(continentNode.path("areaData").path("coastlineInKilometers").asInt());
				continents[i].setCoastlineInMiles(continentNode.path("areaData").path("coastlineInMiles").asInt());
				
				continents[i].setPopulation(continentNode.path("populationData").path("population").asLong());
				continents[i].setPopulationDensityPerSquareKilometer(continentNode.path("populationData").path("populationDensityPerSquareKilometer").asInt());
				continents[i].setPopulationDensityPerSquareMile(continentNode.path("populationData").path("populationDensityPerSquareMile").asDouble());
				continents[i].setGlobalPopulationRanking(continentNode.path("populationData").path("globalRanking").asInt());
				
				i++;
			}
		}
		catch (Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load data for continents", e));
		}
	}
	
	public static void readStatesOfUSADataFile()
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			JsonNode root = mapper.readTree(FilesIO.class.getResourceAsStream("/dataFiles/statesOfUSA.json"));
			JsonNode statesOfUSANode = root.path("statesOfUSA");
			
			statesOfUSA = new StateOfUSA[StateOfUSA.NUMBER_OF_USA_STATES];
			
			int i = 0;
			for (JsonNode stateOfUSANode : statesOfUSANode)
			{
				if(statesOfUSA[i] == null) statesOfUSA[i] = new StateOfUSA();
				
				statesOfUSA[i].setNameInGreek(stateOfUSANode.path("nameInGreek").asText());
				statesOfUSA[i].setNameInEnglish(stateOfUSANode.path("nameInEnglish").asText());
			
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					statesOfUSA[i].setArticleForState(stateOfUSANode.path("articleForState").asText());
					statesOfUSA[i].setArticleForCapital(stateOfUSANode.path("articleForCapital").asText());
					statesOfUSA[i].setCapitalName(stateOfUSANode.path("capitalInGreek").asText());
					statesOfUSA[i].setSpokenLanguages(stateOfUSANode.path("spokenLanguagesInGreek").asText());
					statesOfUSA[i].setHighestPoint(stateOfUSANode.path("areaData").path("highestPointInGreek").asText());
					statesOfUSA[i].setLowestPoint(stateOfUSANode.path("areaData").path("lowestPointInGreek").asText());
					
					statesOfUSA[i].setLargestCities(StreamSupport.stream(stateOfUSANode.path("largestCitiesInGreek").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));
				}
				else
				{
					statesOfUSA[i].setCapitalName(stateOfUSANode.path("capitalInEnglish").asText());
					statesOfUSA[i].setSpokenLanguages(stateOfUSANode.path("spokenLanguagesInEnglish").asText());
					statesOfUSA[i].setHighestPoint(stateOfUSANode.path("areaData").path("highestPointInEnglish").asText());
					statesOfUSA[i].setLowestPoint(stateOfUSANode.path("areaData").path("lowestPointInEnglish").asText());
					
					statesOfUSA[i].setLargestCities(StreamSupport.stream(stateOfUSANode.path("largestCitiesInEnglish").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));
				}
				
				statesOfUSA[i].setAbbreviation(stateOfUSANode.path("abbreviation").asText());
				statesOfUSA[i].setDateEnteredUnion(LocalDate.parse(stateOfUSANode.path("dateEnteredUnion").asText(), dateFormatForSaving));
				statesOfUSA[i].setNumberOfCounties(stateOfUSANode.path("numberOfCounties").asInt());
				statesOfUSA[i].setHouseSeats(stateOfUSANode.path("houseSeats").asInt());
				statesOfUSA[i].setTimeZones(stateOfUSANode.path("timeZones").asText());
				
				statesOfUSA[i].setPopulation(stateOfUSANode.path("populationData").path("population").asInt());
				statesOfUSA[i].setPopulationRank(stateOfUSANode.path("populationData").path("populationRank").asInt());
				statesOfUSA[i].setPopulationDensityPerSquareKilometer(stateOfUSANode.path("populationData").path("populationDensityPerSquareKilometer").asDouble());
				statesOfUSA[i].setPopulationDensityPerSquareMile(stateOfUSANode.path("populationData").path("populationDensityPerSquareMile").asDouble());
				
				statesOfUSA[i].setTotalAreaInKilometers(stateOfUSANode.path("areaData").path("totalAreaInKilometers").asInt());
				statesOfUSA[i].setTotalAreaInMiles(stateOfUSANode.path("areaData").path("totalAreaInMiles").asInt());
				statesOfUSA[i].setAreaRanking(stateOfUSANode.path("areaData").path("areaRank").asInt());
				statesOfUSA[i].setLandAreaInKilometers(stateOfUSANode.path("areaData").path("landAreaInKilometers").asInt());
				statesOfUSA[i].setLandAreaInMiles(stateOfUSANode.path("areaData").path("landAreaInMiles").asInt());
				statesOfUSA[i].setLandPercent(stateOfUSANode.path("areaData").path("landPercent").asDouble());
				statesOfUSA[i].setWaterAreaInKilometers(stateOfUSANode.path("areaData").path("waterAreaInKilometers").asInt());
				statesOfUSA[i].setWaterAreaInMiles(stateOfUSANode.path("areaData").path("waterAreaInMiles").asInt());
				statesOfUSA[i].setWaterPercent(stateOfUSANode.path("areaData").path("waterPercent").asDouble());
				statesOfUSA[i].setCoastlineLengthInKilometers(stateOfUSANode.path("areaData").path("coastlineLengthInKilometers").asInt());
				statesOfUSA[i].setCoastlineLengthInMiles(stateOfUSANode.path("areaData").path("coastlineLengthInMiles").asInt());
				statesOfUSA[i].setHasAccessToTheOcean(stateOfUSANode.path("areaData").path("hasAccessToTheOcean").asBoolean());
				statesOfUSA[i].setMeanPoint(stateOfUSANode.path("areaData").path("meanPoint").asText());
				
				statesOfUSA[i].setPositionInCapitals(stateOfUSANode.path("otherValues").path("positionInCapitals").asInt());
				
				i++;
			}
		}
		catch (Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load data for states of USA", e));
		}
	}
	
	public static void readGreekDecentralizedAdministrationsDataFile()
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			JsonNode root = mapper.readTree(FilesIO.class.getResourceAsStream("/dataFiles/Greece/decentralizedAdministrations.json"));
			JsonNode decAdminsNode = root.path("decentralizedAdministrations");
			
			greekDecAdm = new GreekDecentralizedAdministration[GreekDecentralizedAdministration.NUMBER_OF_GREEK_DECENTRALIZED_ADMINISTRATIONS];
			
			int i = 0;
			for (JsonNode decAdminNode : decAdminsNode)
			{
				if (greekDecAdm[i] == null) greekDecAdm[i] = new GreekDecentralizedAdministration();
				
				greekDecAdm[i].setNameInGreek(decAdminNode.path("nameInGreek").asText());
				greekDecAdm[i].setNameInEnglish(decAdminNode.path("nameInEnglish").asText());
				
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					greekDecAdm[i].setHeadquarters(decAdminNode.path("headquartersInGreek").asText());
					greekDecAdm[i].setWikipediaLink(decAdminNode.path("wikipediaLinkInGreek").asText());
					
					greekDecAdm[i].setRegions(StreamSupport.stream(decAdminNode.path("regionsInGreek").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));
					greekDecAdm[i].setRegionalUnits(StreamSupport.stream(decAdminNode.path("regionalUnitsInGreek").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));
					greekDecAdm[i].setMunicipalities(StreamSupport.stream(decAdminNode.path("municipalitiesInGreek").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));
				}
				else
				{
					greekDecAdm[i].setHeadquarters(decAdminNode.path("headquartersInEnglish").asText());
					greekDecAdm[i].setWikipediaLink(decAdminNode.path("wikipediaLinkInEnglish").asText());
					
					greekDecAdm[i].setRegions(StreamSupport.stream(decAdminNode.path("regionsInEnglish").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));
					greekDecAdm[i].setRegionalUnits(StreamSupport.stream(decAdminNode.path("regionalUnitsInEnglish").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));
					greekDecAdm[i].setMunicipalities(StreamSupport.stream(decAdminNode.path("municipalitiesInEnglish").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));
				}
				
				greekDecAdm[i].setYearFormed(decAdminNode.path("yearFormed").asText());
				greekDecAdm[i].setWebsite(decAdminNode.path("website").asText());
				
				greekDecAdm[i].setAreaInKilometers(decAdminNode.path("areaInKilometers").asInt());
				greekDecAdm[i].setAreaInMiles(decAdminNode.path("areaInMiles").asInt());
				
				greekDecAdm[i].setPopulation(decAdminNode.path("population").asInt());
				
				greekDecAdm[i].setNumberOfRegions(decAdminNode.path("numberOfRegions").asInt());
				greekDecAdm[i].setNumberOfRegionalUnits(decAdminNode.path("numberOfRegionalUnits").asInt());
				greekDecAdm[i].setNumberOfMunicipalities(decAdminNode.path("numberOfMunicipalities").asInt());
				
				i++;
			}
		}
		catch (Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load data for decentralized administrations of Greece", e));
		}
	}
	
	public static void readGreekRegionsDataFile()
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			JsonNode root = mapper.readTree(FilesIO.class.getResourceAsStream("/dataFiles/Greece/regions.json"));
			JsonNode regionsNode = root.path("regions");
			
			greekRegions = new GreekRegion[GreekRegion.NUMBER_OF_GREEK_REGIONS];
			
			int i = 0;
			for(JsonNode regionNode : regionsNode)
			{
				if (greekRegions[i] == null) greekRegions[i] = new GreekRegion();
				
				greekRegions[i].setNameInGreek(regionNode.path("nameInGreek").asText());
				greekRegions[i].setNameInEnglish(regionNode.path("nameInEnglish").asText());
				
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					greekRegions[i].setSeat(regionNode.path("seatInGreek").asText());
					greekRegions[i].setLargestCity(regionNode.path("largestCityInGreek").asText());
					greekRegions[i].setLargestMunicipality(regionNode.path("largestMunicipalityInGreek").asText());
					greekRegions[i].setHighestPoint(regionNode.path("highestPointInGreek").asText());
					greekRegions[i].setWikipediaLink(regionNode.path("wikipediaLinkInGreek").asText());
					greekRegions[i].setDecentralizedAdministration(regionNode.path("decentralizedAdministrationInGreek").asText());
					
					greekRegions[i].setRegionalUnits(StreamSupport.stream(regionNode.path("regionalUnitsInGreek").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));
					greekRegions[i].setMunicipalities(StreamSupport.stream(regionNode.path("municipalitiesInGreek").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));
				}
				else
				{
					greekRegions[i].setSeat(regionNode.path("seatInEnglish").asText());
					greekRegions[i].setLargestCity(regionNode.path("largestCityInEnglish").asText());
					greekRegions[i].setLargestMunicipality(regionNode.path("largestMunicipalityInEnglish").asText());
					greekRegions[i].setHighestPoint(regionNode.path("highestPointInEnglish").asText());
					greekRegions[i].setWikipediaLink(regionNode.path("wikipediaLinkInEnglish").asText());
					greekRegions[i].setDecentralizedAdministration(regionNode.path("decentralizedAdministrationInEnglish").asText());
					
					greekRegions[i].setRegionalUnits(StreamSupport.stream(regionNode.path("regionalUnitsInEnglish").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));
					greekRegions[i].setMunicipalities(StreamSupport.stream(regionNode.path("municipalitiesInEnglish").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));
				}
				
				greekRegions[i].setWebsite(regionNode.path("website").asText());
				
				greekRegions[i].setAreaInKilometers(regionNode.path("areaInKilometers").asInt());
				greekRegions[i].setAreaInMiles(regionNode.path("areaInMiles").asInt());
				greekRegions[i].setAreaRanking(regionNode.path("areaRanking").asInt());
				
				greekRegions[i].setPopulation(regionNode.path("population").asInt());
				greekRegions[i].setPopulationDensityPerSquareKilometer(regionNode.path("populationDensityPerSquareKilometer").asInt());
				greekRegions[i].setPopulationDensityPerSquareMile(regionNode.path("populationDensityPerSquareMile").asInt());
				greekRegions[i].setPopulationRanking(regionNode.path("populationRanking").asInt());
				
				greekRegions[i].setNumberOfRegionalUnits(regionNode.path("numberOfRegionalUnits").asInt());
				greekRegions[i].setNumberOfMunicipalities(regionNode.path("numberOfMunicipalities").asInt());
				
				i++;
			}
		}
		catch (Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load data for regions of Greece", e));
		}
	}
	
	public static void readGreekRegionalUnitsDataFile()
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			JsonNode root = mapper.readTree(FilesIO.class.getResourceAsStream("/dataFiles/Greece/regionalUnits.json"));
			JsonNode regionalUnitsNode = root.path("regionalUnits");
			
			greekRegionalUnits = new GreekRegionalUnit[GreekRegionalUnit.NUMBER_OF_GREEK_REGIONAL_UNITS];
			
			int i = 0;
			for(JsonNode regionalUnitNode : regionalUnitsNode)
			{
				if (greekRegionalUnits[i] == null) greekRegionalUnits[i] = new GreekRegionalUnit();
				
				greekRegionalUnits[i].setNameInGreek(regionalUnitNode.path("nameInGreek").asText());
				greekRegionalUnits[i].setNameInEnglish(regionalUnitNode.path("nameInEnglish").asText());
				
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					greekRegionalUnits[i].setCapital(regionalUnitNode.path("capitalInGreek").asText());
					greekRegionalUnits[i].setLargestCity(regionalUnitNode.path("largestCityInGreek").asText());
					greekRegionalUnits[i].setLargestMunicipality(regionalUnitNode.path("largestMunicipalityInGreek").asText());
					greekRegionalUnits[i].setHighestPoint(regionalUnitNode.path("highestPointInGreek").asText());
					greekRegionalUnits[i].setWikipediaLink(regionalUnitNode.path("wikipediaLinkInGreek").asText());
					greekRegionalUnits[i].setDecentralizedAdministration(regionalUnitNode.path("decentralizedAdministrationInGreek").asText());
					greekRegionalUnits[i].setRegion(regionalUnitNode.path("regionInGreek").asText());
					
					greekRegionalUnits[i].setMunicipalities(StreamSupport.stream(regionalUnitNode.path("municipalitiesInGreek").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));
				}
				else
				{
					greekRegionalUnits[i].setCapital(regionalUnitNode.path("capitalInEnglish").asText());
					greekRegionalUnits[i].setLargestCity(regionalUnitNode.path("largestCityInEnglish").asText());
					greekRegionalUnits[i].setLargestMunicipality(regionalUnitNode.path("largestMunicipalityInEnglish").asText());
					greekRegionalUnits[i].setHighestPoint(regionalUnitNode.path("highestPointInEnglish").asText());
					greekRegionalUnits[i].setWikipediaLink(regionalUnitNode.path("wikipediaLinkInEnglish").asText());
					greekRegionalUnits[i].setDecentralizedAdministration(regionalUnitNode.path("decentralizedAdministrationInEnglish").asText());
					greekRegionalUnits[i].setRegion(regionalUnitNode.path("regionInEnglish").asText());
					
					greekRegionalUnits[i].setMunicipalities(StreamSupport.stream(regionalUnitNode.path("municipalitiesInEnglish").spliterator(), false).map(JsonNode::asText).toArray(String[]::new));
				}
				
				greekRegionalUnits[i].setWebsite(regionalUnitNode.path("website").asText());
				
				greekRegionalUnits[i].setAreaInKilometers(regionalUnitNode.path("areaInKilometers").asInt());
				greekRegionalUnits[i].setAreaInMiles(regionalUnitNode.path("areaInMiles").asInt());
				greekRegionalUnits[i].setAreaRanking(regionalUnitNode.path("areaRanking").asInt());
				
				greekRegionalUnits[i].setPopulation(regionalUnitNode.path("population").asInt());
				greekRegionalUnits[i].setPopulationDensityPerSquareKilometer(regionalUnitNode.path("populationDensityPerSquareKilometer").asInt());
				greekRegionalUnits[i].setPopulationDensityPerSquareMile(regionalUnitNode.path("populationDensityPerSquareMile").asInt());
				greekRegionalUnits[i].setPopulationRanking(regionalUnitNode.path("populationRanking").asInt());
				
				greekRegionalUnits[i].setNumberOfMunicipalities(regionalUnitNode.path("numberOfMunicipalities").asInt());
				
				i++;
			}
		}
		catch (Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load data for regional units of Greece", e));
		}
	}
	
	public static void readAttractionsDataFile()
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			JsonNode root = mapper.readTree(FilesIO.class.getResourceAsStream("/dataFiles/attractions.json"));
			JsonNode attractionsNode = root.path("attractions");
			
			Attraction.setNumberOfAttractions(attractionsNode.size());
			attractions = new Attraction[Attraction.getNumberOfAttractions()];
			
			int i = 0;
			for (JsonNode attractionNode : attractionsNode)
			{
				if (attractions[i] == null) attractions[i] = new Attraction();
				
				attractions[i].setNameInGreek(attractionNode.path("nameInGreek").asText());
				attractions[i].setNameInEnglish(attractionNode.path("nameInEnglish").asText());
				
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					attractions[i].setCountry(attractionNode.path("countryInGreek").asText());
					attractions[i].setCity(attractionNode.path("cityInGreek").asText());
					attractions[i].setBasicInfo(attractionNode.path("basicInfoInGreek").asText());
					attractions[i].setWikipediaLink(attractionNode.path("wikipediaLinkInGreek").asText());
					attractions[i].setGreekArticle(attractionNode.path("greekArticle").asText());
				}
				else
				{
					attractions[i].setCountry(attractionNode.path("countryInEnglish").asText());
					attractions[i].setCity(attractionNode.path("cityInEnglish").asText());
					attractions[i].setBasicInfo(attractionNode.path("basicInfoInEnglish").asText());
					attractions[i].setWikipediaLink(attractionNode.path("wikipediaLinkInEnglish").asText());
				}
				
				attractions[i].setYearBuilt(attractionNode.path("yearBuilt").asText());
				attractions[i].setCoordinates(attractionNode.path("coordinates").asText());
				attractions[i].setEasy(attractionNode.path("isEasy").asBoolean());
				
				i++;
			}
		}
		catch (Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load data for attractions", e));
		}
	}
}