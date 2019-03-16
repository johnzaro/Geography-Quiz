package code.core;

import code.core.OsCheck.OSType;
import code.dataStructures.*;
import code.screens.ErrorScreen;
import javafx.application.Platform;
import org.apache.commons.io.FileUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static code.core.GlobalVariables.*;

public class FilesIO
{
	static File gamesScoresFile, playersFile;
	private static File gameFolder;
	
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
	
	//	SETUP AND CREATE SAVE FILES
	static void setupFiles()
	{
		gameFolder = new File(GAME_DATA_PATH);
		
		playersFile = new File(GAME_DATA_PATH + File.separator + PLAYERS_FILE_PATH);
		gamesScoresFile = new File(GAME_DATA_PATH + File.separator + GAMES_SCORES_FILE_PATH);
		
		try
		{
			//create game data folder and data files if they don't exist
			
			if (!playersFile.getParentFile().exists()) playersFile.getParentFile().mkdirs();
			
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
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(playersFile);
			
			Element root = doc.getRootElement();
			
			List list = root.getChildren();
			
			String original, edited;
			boolean exists;
			
			for(Object aList : list)
			{
				Element player = (Element) aList;
				original = player.getChildText("originalName");
				edited = player.getChildText("editedName");
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
					tempPlayer.setHighestScorePoints(Integer.parseInt(player.getChildText("highestScore")));
					tempPlayer.setTotalCorrectAnswers(Integer.parseInt(player.getChildText("totalCorrectAnswers")));
					tempPlayer.setTotalQuestionsAnswered(Integer.parseInt(player.getChildText("totalQuestionsAnswered")));
					tempPlayer.setTotalTimePlayed(Integer.parseInt(player.getChildText("totalTimePlayed")));
					tempPlayer.setAverageAnswerTime(Double.parseDouble(player.getChildText("averageAnswerTime")));
					tempPlayer.setMaxCombo(Integer.parseInt(player.getChildText("maxCombo")));
					
					Locale locale = Locale.forLanguageTag(player.getChildText("locale"));
					int localeIndex = Integer.parseInt(player.getChildText("localeIndex"));
					int language = Integer.parseInt(player.getChildText("language"));
					int unitSystem = Integer.parseInt(player.getChildText("unitSystem"));
					int difficulty = Integer.parseInt(player.getChildText("difficultyLevel"));
					int numForClassic = Integer.parseInt(player.getChildText("numOfQuestionsInClassicMode"));
					int duration = Integer.parseInt(player.getChildText("durationForTimeAttackMode"));
					int lives = Integer.parseInt(player.getChildText("livesForEndlessMode"));
					boolean timerClassic = Boolean.parseBoolean(player.getChildText("timerForClassicMode"));
					boolean timerEndless = Boolean.parseBoolean(player.getChildText("timerForEndlessMode"));
					
					String[] categories = player.getChildText("questionCategories").split(" ");
					boolean[] selectedCategories = new boolean[NUM_ALL_CATEGORIES];
					for(int i = 0; i < categories.length; i++) selectedCategories[Integer.parseInt(categories[i])] = true;
					tempPlayer.setQuestionCategories(selectedCategories);
					
					double masterSliderVolume = Double.parseDouble(player.getChild("settings").getChildText("masterVolume"));
					double musicSliderVolume = Double.parseDouble(player.getChild("settings").getChildText("musicVolume"));
					double soundEffectsSliderVolume = Double.parseDouble(player.getChild("settings").getChildText("soundEffectsVolume"));
					
					double windowWidth = Double.parseDouble(player.getChild("settings").getChildText("windowWidth"));
					//check if width got is valid for current screen resolution
					if (!isWidthValid(windowWidth)) windowWidth = 0.75 * primaryScreenWidth;
					
					boolean startAtFullScreen = Boolean.parseBoolean(player.getChild("settings").getChildText("startAtFullScreen"));
					
					int animationsUsed = Integer.parseInt(player.getChild("settings").getChildText("animationsUsed"));
					
					if((localeIndex < 0 || localeIndex > NUM_ALL_COUNTRIES) || (language < 0 || language > 1) || (unitSystem < 0 || unitSystem > 1) || (difficulty < 0 || difficulty > 1) ||
						(numForClassic != NUM_OF_QUESTIONS_FOR_CLASSIC_10  && numForClassic != NUM_OF_QUESTIONS_FOR_CLASSIC_20  &&
						 numForClassic != NUM_OF_QUESTIONS_FOR_CLASSIC_50  && numForClassic != NUM_OF_QUESTIONS_FOR_CLASSIC_100) ||
						(duration != TIME_ATTACK_DURATION_1_MINUTE && duration != TIME_ATTACK_DURATION_2_MINUTES &&
						 duration != TIME_ATTACK_DURATION_5_MINUTES && duration != TIME_ATTACK_DURATION_10_MINUTES) ||
						(lives != ENDLESS_LIVES_1 && lives != ENDLESS_LIVES_3 && lives != ENDLESS_LIVES_5) ||
						(masterSliderVolume < 0 || masterSliderVolume > 100 ||
						 musicSliderVolume < 0 || musicSliderVolume > 100 ||
						 soundEffectsSliderVolume < 0 || soundEffectsSliderVolume > 100) ||
						 animationsUsed < 0 || animationsUsed > 2)
							tempPlayer.setDefaultPlayerSettings();
					else
					{
						LANGUAGE lan;
						if(language == 0) lan = LANGUAGE.ENGLISH;
						else lan = LANGUAGE.GREEK;
						
						UNIT_SYSTEM unS;
						if(unitSystem == 0) unS = UNIT_SYSTEM.METRIC;
						else unS = UNIT_SYSTEM.IMPERIAL;
						
						tempPlayer.setPlayerSettings(locale, localeIndex, lan, unS, difficulty, numForClassic, timerClassic,
								duration, lives, timerEndless, masterSliderVolume, musicSliderVolume, soundEffectsSliderVolume, windowWidth, animationsUsed, startAtFullScreen);
					}
					
					playersArrayList.add(tempPlayer);
				}
			}
			
			if(playersArrayList.size() == 0) setDefaultPlayerName();
			else if(list.size() != playersArrayList.size()) writePlayersFile();
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
		try
		{
			Element  namesElement = new Element("playersArrayList");
			Document doc = new Document(namesElement);
			if(playersArrayList.size() == 0)
				playersArrayList.add(new Player(System.getProperty("user.name"), getEditedOriginalName(System.getProperty("user.name"))));
			
			boolean exists;
			for(Player newPlayer : playersArrayList)
			{
				exists = false;
				for(Player existingPlayer: playersArrayList)
				{
					if(existingPlayer != newPlayer && existingPlayer.getOriginalName().equals(newPlayer.getOriginalName()))
					{
						exists = true;
						break;
					}
				}
				
				if(!exists)
				{
					Element player = new Element("player");
					player.addContent(new Element("originalName").setText(newPlayer.getOriginalName()));
					player.addContent(new Element("editedName").setText(newPlayer.getEditedName()));
					player.addContent(new Element("highestScore").setText(String.valueOf(newPlayer.getHighestScorePoints())));
					player.addContent(new Element("totalCorrectAnswers").setText(String.valueOf(newPlayer.getTotalCorrectAnswers())));
					player.addContent(new Element("totalQuestionsAnswered").setText(String.valueOf(newPlayer.getTotalQuestionsAnswered())));
					player.addContent(new Element("totalTimePlayed").setText(String.valueOf(newPlayer.getTotalTimePlayed())));
					player.addContent(new Element("averageAnswerTime").setText(decimalFormatForSaving.format(newPlayer.getAverageAnswerTime())));
					player.addContent(new Element("maxCombo").setText(String.valueOf(newPlayer.getMaxCombo())));
					
					player.addContent(new Element("locale").setText(newPlayer.getLocale().toLanguageTag()));
					player.addContent(new Element("localeIndex").setText(String.valueOf(newPlayer.getLocaleIndex())));
					
					switch(newPlayer.getLanguage())
					{
						case ENGLISH: player.addContent(new Element("language").setText("0")); break;
						case GREEK: player.addContent(new Element("language").setText("1")); break;
					}
					switch(newPlayer.getUnitSystem())
					{
						case METRIC: player.addContent(new Element("unitSystem").setText("0")); break;
						case IMPERIAL: player.addContent(new Element("unitSystem").setText("1")); break;
					}
					
					player.addContent(new Element("difficultyLevel").setText(String.valueOf(newPlayer.getDifficultyLevel())));
					player.addContent(new Element("numOfQuestionsInClassicMode").setText(String.valueOf(newPlayer.getNumberOfQuestionsInClassicalMode())));
					player.addContent(new Element("durationForTimeAttackMode").setText(String.valueOf(newPlayer.getDurationForTimeAttackMode())));
					player.addContent(new Element("livesForEndlessMode").setText(String.valueOf(newPlayer.getLivesForEndlessMode())));
					player.addContent(new Element("timerForClassicMode").setText(String.valueOf(newPlayer.isTimerForClassicMode())));
					player.addContent(new Element("timerForEndlessMode").setText(String.valueOf(newPlayer.isTimerForEndlessMode())));
					
					StringBuilder sb = new StringBuilder();
					for(int i = 0; i < newPlayer.getQuestionCategories().length; i++) if(newPlayer.getQuestionCategories()[i]) sb.append(i).append(" ");
					if(sb.length() > 0) sb.deleteCharAt(sb.length() - 1);
					player.addContent(new Element("questionCategories").setText(sb.toString()));
					
					Element settingsElement = new Element("settings");
					settingsElement.addContent(new Element("masterVolume").setText(String.valueOf((int) newPlayer.getMasterSliderVolume())));
					settingsElement.addContent(new Element("musicVolume").setText(String.valueOf((int) newPlayer.getMusicSliderVolume())));
					settingsElement.addContent(new Element("soundEffectsVolume").setText(String.valueOf((int) newPlayer.getSoundEffectsSliderVolume())));
					settingsElement.addContent(new Element("windowWidth").setText(String.valueOf((int) newPlayer.getWindowWidth())));
					settingsElement.addContent(new Element("startAtFullScreen").setText(String.valueOf(newPlayer.getStartAtFullScreen())));
					settingsElement.addContent(new Element("animationsUsed").setText(String.valueOf(newPlayer.getAnimationsUsed())));
					player.addContent(settingsElement);
					
					namesElement.addContent(player);
				}
			}
			
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat().setIndent("\t"));
			xmlOutput.output(doc, new OutputStreamWriter(new FileOutputStream(playersFile, false), "UTF-8"));
		}
		catch (Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to save player names", e));
		}
	}
	
	//	small method to empty a file
	private static void emptyTheFile(File file)
	{
		try
		{
			//opens the file with a printWriter that first empties the file by default and closes the stream immediately then
			new PrintWriter(file).close();
		}
		catch (FileNotFoundException e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to empty a file", e));
		}
	}
	
	static void readGamesScores()
	{
		try
		{
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(gamesScoresFile);
			
			Element gamesElement = doc.getRootElement();
			List gamesList = gamesElement.getChildren();
			
			for(Object aGamesList : gamesList)
			{
				Element gameElement = (Element) aGamesList;
				
				Game game = new Game();
				game.setPlayerName(gameElement.getChildText("playerName"));
				game.setGameStartedTime(LocalDateTime.parse(gameElement.getChildText("gameStartedTime"), dateTimeFormatForSaving));
				game.setGameDurationInSeconds(Integer.parseInt(gameElement.getChildText("gameDurationInSeconds")));
				
				switch(Integer.parseInt(gameElement.getChildText("gameMode")))
				{
					case 0: game.setGameMode(GAMEMODE.CLASSIC_GAMEMODE);break;
					case 1: game.setGameMode(GAMEMODE.TIME_ATTACK_GAMEMODE);break;
					case 2: game.setGameMode(GAMEMODE.ENDLESS_GAMEMODE); game.setLivesForEndless(Integer.parseInt(gameElement.getChildText("livesForEndless")));break;
				}
				
				game.setIsCountdownEnabled(Boolean.parseBoolean(gameElement.getChildText("isCountdownEnabled")));
				
				game.setDifficultyLevel(Integer.parseInt(gameElement.getChildText("difficultyLevel")));
				game.setNumberOfAllQuestions(Integer.parseInt(gameElement.getChildText("numberOfAllQuestions")));
				game.setNumberOfCorrectQuestions(Integer.parseInt(gameElement.getChildText("numberOfCorrectQuestions")));
				game.setScorePoints(Integer.parseInt(gameElement.getChildText("scorePoints")));
				game.setMaxCombo(Integer.parseInt(gameElement.getChildText("maxCombo")));
				game.setAverageAnswerTime(Double.parseDouble(gameElement.getChildText("averageAnswerTime")));
				
				String[] questionCategories = gameElement.getChildText("questionCategories").split(" ");
				int[] categories = new int[questionCategories.length];
				for(int j = 0; j < questionCategories.length; j++) categories[j] = Integer.parseInt(questionCategories[j]);
				game.setQuestionCategories(categories);
				
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
		try
		{
			Element  gamesElement = new Element("games");
			Document doc = new Document(gamesElement);
			
			for(Game game : games)
			{
				Element gameElement = new Element("game");
				
				gameElement.addContent(new Element("playerName").setText(game.getPlayerName()));
				gameElement.addContent(new Element("gameStartedTime").setText(
						game.getGameStartedTime().format(dateTimeFormatForSaving)));
				gameElement.addContent(new Element("gameDurationInSeconds").setText(
						String.valueOf(game.getGameDurationInSeconds())));
				
				switch(game.getGameMode())
				{
					case CLASSIC_GAMEMODE: gameElement.addContent(new Element("gameMode").setText("0")); break;
					case TIME_ATTACK_GAMEMODE: gameElement.addContent(new Element("gameMode").setText("1")); break;
					case ENDLESS_GAMEMODE: gameElement.addContent(new Element("gameMode").setText("2")); break;
				}
				
				if(game.getGameMode() == GAMEMODE.ENDLESS_GAMEMODE)
					gameElement.addContent(new Element("livesForEndless").setText(
							String.valueOf(game.getLivesForEndless())));
				
				gameElement.addContent(new Element("isCountdownEnabled").setText(
							String.valueOf(game.isCountdownEnabled())));
				
				gameElement.addContent(new Element("difficultyLevel").setText(
						String.valueOf(game.getDifficultyLevel())));
				
				gameElement.addContent(new Element("numberOfAllQuestions").setText(
						String.valueOf(game.getNumberOfAllQuestions())));
				
				gameElement.addContent(new Element("numberOfCorrectQuestions").setText(
						String.valueOf(game.getNumberOfCorrectQuestions())));
				
				gameElement.addContent(new Element("scorePoints").setText(
						String.valueOf(game.getScorePoints())));
				
				gameElement.addContent(new Element("maxCombo").setText(
						String.valueOf(game.getMaxCombo())));
				
				gameElement.addContent(new Element("averageAnswerTime").setText(
						String.valueOf(game.getAverageAnswerTime())));
				
				StringBuilder sb = new StringBuilder();
				for(int j = 0; j < game.getQuestionCategories().length; j++) sb.append(game.getQuestionCategories()[j]).append(" ");
				sb.deleteCharAt(sb.length() - 1);
				gameElement.addContent(new Element("questionCategories").setText(sb.toString()));
				
				gamesElement.addContent(gameElement);
			}
			
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat().setIndent("\t"));
			xmlOutput.output(doc, new OutputStreamWriter(new FileOutputStream(gamesScoresFile, false), "UTF-8"));
		}
		catch (Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to save game scores", e));
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
	
	//	method to read the countries data file
	public static void readCountriesXMLDataFile()
	{
		//		a SAXBuilder object is needed
		SAXBuilder builder = new SAXBuilder();
		
		try
		{
			//			next we create a document object and pass the result of builder.build with the correct file as parameter
			Document doc = builder.build(FilesIO.class.getResourceAsStream("/resources/dataFiles/countries.xml"));
			//			we get the root element of the file from doc object which holds all the information inside the file
			Element root = doc.getRootElement();
			
			//			we create a list of all the children inside the file
			List list = root.getChildren();
			
			//			helper list l2 and array that is needed later
			List     l2;
			String[] array;
			
			//			loop through the children of root element
			for (int i = 0; i < list.size(); i++)
			{
				//				asign each child in node object
				Element node = (Element) list.get(i);
				
				//				if the method is run for the first time then the countries array has null elements inside so
				//				it is needed to create a country object for each one, if the language is changed later in the game
				//				and the method is run again this procedure is not executed again
				if (countries[i] == null) countries[i] = new Country();
				
				countries[i].setPositionInCapitals(Short.parseShort(node.getChild("otherValues").getChildText("positionInCapitals")));
				countries[i].setAskCapital(Byte.parseByte(node.getChild("otherValues").getChildText("askCapital")));
				countries[i].setAskLargestCity(Byte.parseByte(node.getChild("otherValues").getChildText("askLargestCity")));
				countries[i].setHasEasyLocation(Boolean.parseBoolean(node.getChild("otherValues").getChildText("hasEasyLocation")));
				countries[i].setAskGeographicalCharacteristics(Byte.parseByte(node.getChild("otherValues").getChildText("askGeoChar")));
				countries[i].setAskCurrency(Byte.parseByte(node.getChild("otherValues").getChildText("askCurrency")));
				countries[i].setHasEasyFlag(Boolean.parseBoolean(node.getChild("otherValues").getChildText("hasEasyFlag")));
				countries[i].setAskForCoatOfArms(Boolean.parseBoolean(node.getChild("otherValues").getChildText("askCoatOfArms")));
				countries[i].setAskLanguage(Byte.parseByte(node.getChild("otherValues").getChildText("askLanguage")));
				countries[i].setAskContinent(Byte.parseByte(node.getChild("otherValues").getChildText("askContinent")));
				countries[i].setHasSea(Boolean.parseBoolean(node.getChildText("hasSea")));
				countries[i].setLocaleCountryCode(node.getChild("otherValues").getChildText("localeCountryCode"));
				
				String temp = node.getChild("otherValues").getChildText("localeLanguageCode");
				if(temp.equals("-")) countries[i].setLocaleLanguageCode("");
				else countries[i].setLocaleLanguageCode(temp);
				
				if (node.getChild("isIslandCountry") != null) countries[i].setIsIslandCountry(Boolean.parseBoolean(node.getChildText("isIslandCountry")));
				else countries[i].setIsIslandCountry(false);
				
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					countries[i].setNameInEnglish("");
					countries[i].setCapitalName(node.getChildText("greekCapital"));
					countries[i].setContinent(node.getChildText("continentInGreek"));
					
					countries[i].setArticleForCountry(node.getChildText("articleForCountry"));
					countries[i].setGenitiveCaseOfCountry(node.getChildText("genitiveCaseOfCountry"));
					countries[i].setArticleForCapital(node.getChildText("articleForCapital"));
					
					l2 = node.getChild("largestCitiesInGreek").getChildren();
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element city = (Element) l2.get(j);
						array[j] = city.getValue();
					}
					countries[i].setLargestCities(array);
					l2.clear();
					
					countries[i].setLanguagesString(node.getChildText("languagesStringInGreek"));
					
					if(countries[i].askLanguage() != 0)
					{
						l2 = node.getChild("languagesToAskInGreek").getChildren("language");
						array = new String[l2.size()];
						for (int j = 0; j < l2.size(); j++)
						{
							//for each child get it from l2 and assign it to lang and the value of lang to array
							Element lang = (Element) l2.get(j);
							array[j] = lang.getValue();
						}
						countries[i].setLanguagesToAsk(array);
						l2.clear();
					}
					
					Currency currency = new Currency();
					currency.setName(node.getChild("currency").getChildText("nameInGreek"));
					currency.setSymbol(node.getChild("currency").getChildText("symbol"));
					currency.setISOCode(node.getChild("currency").getChildText("ISOCode"));
					currency.setSubdivision(node.getChild("currency").getChildText("subdivisionInGreek"));
					currency.setNumberOfSubDivisions(node.getChild("currency").getChildText("numberOfSubdivisions"));
					countries[i].setCurrency(currency);
					
					if (node.getName().equals("sovereignState")) countries[i].setIsSovereignState(true);
					else
					{
						countries[i].setIsSovereignState(false);
						countries[i].setSovereignState(node.getChildText("sovereignStateInGreek"));
					}
				}
				else
				{
					countries[i].setNameInEnglish(node.getChildText("englishName"));
					countries[i].setCapitalName(node.getChildText("englishCapital"));
					countries[i].setContinent(node.getChildText("continentInEnglish"));
					
					l2 = node.getChild("largestCitiesInEnglish").getChildren();
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element city = (Element) l2.get(j);
						array[j] = city.getValue();
					}
					countries[i].setLargestCities(array);
					l2.clear();
					
					countries[i].setLanguagesString(node.getChildText("languagesStringInEnglish"));
					
					if(countries[i].askLanguage() != 0)
					{
						l2 = node.getChild("languagesToAskInEnglish").getChildren("language");
						array = new String[l2.size()];
						for (int j = 0; j < l2.size(); j++)
						{
							Element lang = (Element) l2.get(j);
							array[j] = lang.getValue();
						}
						countries[i].setLanguagesToAsk(array);
						l2.clear();
					}
					
					Currency currency = new Currency();
					currency.setName(node.getChild("currency").getChildText("nameInEnglish"));
					currency.setSymbol(node.getChild("currency").getChildText("symbol"));
					currency.setISOCode(node.getChild("currency").getChildText("ISOCode"));
					currency.setSubdivision(node.getChild("currency").getChildText("subdivisionInEnglish"));
					currency.setNumberOfSubDivisions(node.getChild("currency").getChildText("numberOfSubdivisions"));
					countries[i].setCurrency(currency);
					
					if (node.getName().equals("sovereignState")) countries[i].setIsSovereignState(true);
					else
					{
						countries[i].setIsSovereignState(false);
						countries[i].setSovereignState(node.getChildText("sovereignStateInEnglish"));
					}
				}
				countries[i].setNameInGreek(node.getChildText("greekName"));
				
				Area area = new Area();
				area.setAreaInKilometers(Float.parseFloat(node.getChild("areaData").getChildText("areaInKilometers")));
				area.setAreaInMiles(Float.parseFloat(node.getChild("areaData").getChildText("areaInMiles")));
				area.setGlobalRanking(Integer.parseInt(node.getChild("areaData").getChildText("globalRanking")));
				area.setPercentOfWater(Float.parseFloat(node.getChild("areaData").getChildText("percentOfWater")));
				if (node.getChild("areaData").getChild("coastlineInKilometers") != null)
				{
					area.setCoastlineInKilometers(Integer.parseInt(node.getChild("areaData").getChildText("coastlineInKilometers")));
					area.setCoastlineInMiles(Integer.parseInt(node.getChild("areaData").getChildText("coastlineInMiles")));
				}
				else
				{
					area.setCoastlineInKilometers(0);
					area.setCoastlineInMiles(0);
				}
				if (node.getChild("areaData").getChild("bordersInKilometers") != null)
				{
					area.setBordersInKilometers(Integer.parseInt(node.getChild("areaData").getChildText("bordersInKilometers")));
					area.setBordersInMiles(Integer.parseInt(node.getChild("areaData").getChildText("bordersInMiles")));
				}
				else
				{
					area.setBordersInKilometers(0);
					area.setBordersInMiles(0);
				}
				countries[i].setArea(area);
				
				Population population = new Population();
				population.setPopulation(Integer.parseInt(node.getChild("populationData").getChildText("population")));
				population.setPopulationDensityPerSquareKilometer(Float.parseFloat(node.getChild("populationData").getChildText("populationDensityPerSquareKilometer")));
				population.setPopulationDensityPerSquareMile(Float.parseFloat(node.getChild("populationData").getChildText("populationDensityPerSquareMile")));
				population.setGlobalRanking(Integer.parseInt(node.getChild("populationData").getChildText("globalRanking")));
				countries[i].setPopulation(population);
			}
		}
		catch (Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load data for countries", e));
		}
	}
	
	public static void readContinentsXMLDataFile()
	{
		SAXBuilder builder = new SAXBuilder();
		
		try
		{
			Document doc  = builder.build(FilesIO.class.getResourceAsStream("/resources/dataFiles/continents.xml"));
			Element  root = doc.getRootElement();
			
			List list = root.getChildren();
			
			//SPECIAL CASE: ANTARCTICA
			Element node = (Element) list.get(0);
			
			if (continents[0] == null) continents[0] = new Continent();
			if (getCurrentLanguage() == LANGUAGE.GREEK)
			{
				continents[0].setGenitiveCaseOfContinent(node.getChildText("genitiveCaseOfContinent"));
				continents[0].setHighestPoint(node.getChildText("highestPointInGreek"));
				continents[0].setLowestPoint(node.getChildText("lowestPointInGreek"));
				continents[0].setPopulationString(node.getChild("populationData").getChildText("populationInGreek"));
			}
			else
			{
				continents[0].setNameInEnglish(node.getChildText("englishName"));
				continents[0].setHighestPoint(node.getChildText("highestPointInEnglish"));
				continents[0].setLowestPoint(node.getChildText("lowestPointInEnglish"));
				continents[0].setPopulationString(node.getChild("populationData").getChildText("populationInEnglish"));
			}
			continents[0].setNameInGreek(node.getChildText("greekName"));
			continents[0].setTimeZones(node.getChildText("timeZones"));
			continents[0].setAreaInKilometers(Integer.parseInt(node.getChild("areaData").getChildText("areaInKilometers")));
			continents[0].setAreaInMiles(Integer.parseInt(node.getChild("areaData").getChildText("areaInMiles")));
			continents[0].setGlobalAreaRanking(Byte.parseByte(node.getChild("areaData").getChildText("globalRanking")));
			continents[0].setPercentOfEarth(Float.parseFloat(node.getChild("areaData").getChildText("percentOfTheEarth")));
			continents[0].setPercentOfLandOfEarth(Float.parseFloat(node.getChild("areaData").getChildText("percentOfLandOfTheEarth")));
			continents[0].setCoastlineInKilometers(Integer.parseInt(node.getChild("areaData").getChildText("coastlineInKilometers")));
			continents[0].setCoastlineInMiles(Integer.parseInt(node.getChild("areaData").getChildText("coastlineInMiles")));
			continents[0].setGlobalPopulationRanking(Byte.parseByte(node.getChild("populationData").getChildText("globalRanking")));
			
			//THE OTHER 6 CONTINENTS
			for (int i = 1; i < list.size(); i++)
			{
				node = (Element) list.get(i);
				
				if (continents[i] == null) continents[i] = new Continent();
				
				List     l2;
				String[] array;
				
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					continents[i].setGenitiveCaseOfContinent(node.getChildText("genitiveCaseOfContinent"));
					continents[i].setNameInEnglish("");
					continents[i].setNumberOfCountries(node.getChildText("numberOfCountriesInGreek"));
					
					l2 = node.getChild("largestCountriesByAreaInGreek").getChildren();
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element lang = (Element) l2.get(j);
						array[j] = lang.getValue();
					}
					continents[i].setLargestCountriesByArea(array);
					l2.clear();
					
					l2 = node.getChild("largestCountriesByPopulationInGreek").getChildren();
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element lang = (Element) l2.get(j);
						array[j] = lang.getValue();
					}
					continents[i].setLargestCountriesByPopulation(array);
					l2.clear();
					
					l2 = node.getChild("largestCitiesInGreek").getChildren();
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element lang = (Element) l2.get(j);
						array[j] = lang.getValue();
					}
					continents[i].setLargestCities(array);
					l2.clear();
					
					continents[i].setLanguages(node.getChildText("languagesInGreek"));
					continents[i].setHighestPoint(node.getChildText("highestPointInGreek"));
					continents[i].setLowestPoint(node.getChildText("lowestPointInGreek"));
					continents[i].setLongestRiver(node.getChildText("longestRiverInGreek"));
					continents[i].setLargestLake(node.getChildText("largestLakeInGreek"));
				}
				else
				{
					continents[i].setNameInEnglish(node.getChildText("englishName"));
					continents[i].setNumberOfCountries(node.getChildText("numberOfCountriesInEnglish"));
					
					l2 = node.getChild("largestCountriesByAreaInEnglish").getChildren();
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element lang = (Element) l2.get(j);
						array[j] = lang.getValue();
					}
					continents[i].setLargestCountriesByArea(array);
					l2.clear();
					
					l2 = node.getChild("largestCountriesByPopulationInEnglish").getChildren();
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element lang = (Element) l2.get(j);
						array[j] = lang.getValue();
					}
					continents[i].setLargestCountriesByPopulation(array);
					l2.clear();
					
					l2 = node.getChild("largestCitiesInEnglish").getChildren();
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element lang = (Element) l2.get(j);
						array[j] = lang.getValue();
					}
					continents[i].setLargestCities(array);
					l2.clear();
					
					continents[i].setLanguages(node.getChildText("languagesInEnglish"));
					continents[i].setHighestPoint(node.getChildText("highestPointInEnglish"));
					continents[i].setLowestPoint(node.getChildText("lowestPointInEnglish"));
					continents[i].setLongestRiver(node.getChildText("longestRiverInEnglish"));
					continents[i].setLargestLake(node.getChildText("largestLakeInEnglish"));
				}
				continents[i].setNameInGreek(node.getChildText("greekName"));
				continents[i].setTimeZones(node.getChildText("timeZones"));
				
				continents[i].setAreaInKilometers(Integer.parseInt(node.getChild("areaData").getChildText("areaInKilometers")));
				continents[i].setAreaInMiles(Integer.parseInt(node.getChild("areaData").getChildText("areaInMiles")));
				continents[i].setGlobalAreaRanking(Byte.parseByte(node.getChild("areaData").getChildText("globalRanking")));
				continents[i].setPercentOfEarth(Float.parseFloat(node.getChild("areaData").getChildText("percentOfTheEarth")));
				continents[i].setPercentOfLandOfEarth(Float.parseFloat(node.getChild("areaData").getChildText("percentOfLandOfTheEarth")));
				continents[i].setCoastlineInKilometers(Integer.parseInt(node.getChild("areaData").getChildText("coastlineInKilometers")));
				continents[i].setCoastlineInMiles(Integer.parseInt(node.getChild("areaData").getChildText("coastlineInMiles")));
				
				continents[i].setPopulation(Long.parseLong(node.getChild("populationData").getChildText("population")));
				continents[i].setPopulationDensityPerSquareKilometer(Float.parseFloat(node.getChild("populationData").getChildText("populationDensityPerSquareKilometer")));
				continents[i].setPopulationDensityPerSquareMile(Float.parseFloat(node.getChild("populationData").getChildText("populationDensityPerSquareMile")));
				continents[i].setGlobalPopulationRanking(Byte.parseByte(node.getChild("populationData").getChildText("globalRanking")));
			}
		}
		catch (Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load data for continents", e));
		}
	}
	
	public static void readStatesOfUSAXMLDataFile()
	{
		SAXBuilder builder = new SAXBuilder();
		
		try
		{
			Document doc  = builder.build(FilesIO.class.getResourceAsStream("/resources/dataFiles/statesOfUSA.xml"));
			Element  root = doc.getRootElement();
			
			List list = root.getChildren();
			
			List l2;
			String[] array;
			
			for (int i = 0; i < list.size(); i++)
			{
				Element node = (Element) list.get(i);
				
				if (statesOfUSA[i] == null) statesOfUSA[i] = new StateOfUSA();
				
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					statesOfUSA[i].setArticleForState(node.getChildText("articleForState"));
					statesOfUSA[i].setArticleForCapital(node.getChildText("articleForCapital"));
					statesOfUSA[i].setCapitalName(node.getChildText("capitalInGreek"));
					statesOfUSA[i].setSpokenLanguages(node.getChildText("spokenLanguagesInGreek"));
					statesOfUSA[i].setHighestPoint(node.getChild("areaData").getChildText("highestPointInGreek"));
					statesOfUSA[i].setLowestPoint(node.getChild("areaData").getChildText("lowestPointInGreek"));
					
					l2 = node.getChild("largestCitiesInGreek").getChildren();
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element city = (Element) l2.get(j);
						array[j] = city.getValue();
					}
					statesOfUSA[i].setLargestCities(array);
				}
				else
				{
					statesOfUSA[i].setNameInEnglish(node.getChildText("nameInEnglish"));
					statesOfUSA[i].setCapitalName(node.getChildText("capitalInEnglish"));
					statesOfUSA[i].setSpokenLanguages(node.getChildText("spokenLanguagesInEnglish"));
					statesOfUSA[i].setHighestPoint(node.getChild("areaData").getChildText("highestPointInEnglish"));
					statesOfUSA[i].setLowestPoint(node.getChild("areaData").getChildText("lowestPointInEnglish"));
					
					l2 = node.getChild("largestCitiesInEnglish").getChildren();
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element city = (Element) l2.get(j);
						array[j] = city.getValue();
					}
					statesOfUSA[i].setLargestCities(array);
				}
				
				statesOfUSA[i].setNameInGreek(node.getChildText("nameInGreek"));
				statesOfUSA[i].setAbbreviation(node.getChildText("abbreviation"));
				statesOfUSA[i].setDateEnteredUnion(LocalDate.parse(node.getChildText("dateEnteredUnion"), dateFormatForSaving));
				statesOfUSA[i].setNumberOfCounties(Short.parseShort(node.getChildText("numberOfCounties")));
				statesOfUSA[i].setHouseSeats(Byte.parseByte(node.getChildText("houseSeats")));
				statesOfUSA[i].setTimeZones(node.getChildText("timeZones"));
				
				statesOfUSA[i].setPopulation(Integer.parseInt(node.getChild("populationData").getChildText("population")));
				statesOfUSA[i].setPopulationRank(Byte.parseByte(node.getChild("populationData").getChildText("populationRank")));
				statesOfUSA[i].setPopulationDensityPerSquareKilometer(Float.parseFloat(node.getChild("populationData").getChildText("populationDensityPerSquareKilometer")));
				statesOfUSA[i].setPopulationDensityPerSquareMile(Float.parseFloat(node.getChild("populationData").getChildText("populationDensityPerSquareMile")));
				
				statesOfUSA[i].setTotalAreaInKilometers(Integer.parseInt(node.getChild("areaData").getChildText("totalAreaInKilometers")));
				statesOfUSA[i].setTotalAreaInMiles(Integer.parseInt(node.getChild("areaData").getChildText("totalAreaInMiles")));
				statesOfUSA[i].setAreaRanking(Byte.parseByte(node.getChild("areaData").getChildText("areaRank")));
				statesOfUSA[i].setLandAreaInKilometers(Integer.parseInt(node.getChild("areaData").getChildText("landAreaInKilometers")));
				statesOfUSA[i].setLandAreaInMiles(Integer.parseInt(node.getChild("areaData").getChildText("landAreaInMiles")));
				statesOfUSA[i].setLandPercent(Float.parseFloat(node.getChild("areaData").getChildText("landPercent")));
				statesOfUSA[i].setWaterAreaInKilometers(Integer.parseInt(node.getChild("areaData").getChildText("waterAreaInKilometers")));
				statesOfUSA[i].setWaterAreaInMiles(Integer.parseInt(node.getChild("areaData").getChildText("waterAreaInMiles")));
				statesOfUSA[i].setWaterPercent(Float.parseFloat(node.getChild("areaData").getChildText("waterPercent")));
				statesOfUSA[i].setCoastlineLengthInKilometers(Integer.parseInt(node.getChild("areaData").getChildText("coastlineLengthInKilometers")));
				statesOfUSA[i].setCoastlineLengthInMiles(Integer.parseInt(node.getChild("areaData").getChildText("coastlineLengthInMiles")));
				statesOfUSA[i].setHasAccessToTheOcean(Boolean.parseBoolean(node.getChild("areaData").getChildText("hasAccessToTheOcean")));
				statesOfUSA[i].setMeanPoint(node.getChild("areaData").getChildText("meanPoint"));
				
				statesOfUSA[i].setPositionInCapitals(Short.parseShort(node.getChild("otherValues").getChildText("positionInCapitals")));
			}
		}
		catch (Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load data for states of USA", e));
		}
	}
	
	public static void readGreekDecentralizedAdministrationsXMLDataFile()
	{
		SAXBuilder builder = new SAXBuilder();
		
		try
		{
			Document doc  = builder.build(FilesIO.class.getResourceAsStream("/resources/dataFiles/Greece/decentralizedAdministrations.xml"));
			Element  root = doc.getRootElement();
			
			List list = root.getChildren();
			
			for (int i = 0; i < list.size(); i++)
			{
				Element node = (Element) list.get(i);
				
				if (greekDecAdm[i] == null) greekDecAdm[i] = new GreekDecentralizedAdministration();
				
				List     l2;
				String[] array;
				
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					greekDecAdm[i].setHeadquarters(node.getChildText("headquartersInGreek"));
					greekDecAdm[i].setWikipediaLink(node.getChildText("wikipediaLinkInGreek"));
					
					l2 = node.getChild("regionsInGreek").getChildren("regionInGreek");
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element lang = (Element) l2.get(j);
						array[j] = lang.getValue();
					}
					greekDecAdm[i].setRegions(array);
					l2.clear();
					
					l2 = node.getChild("regionalUnitsInGreek").getChildren("regionalUnitInGreek");
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element lang = (Element) l2.get(j);
						array[j] = lang.getValue();
					}
					greekDecAdm[i].setRegionalUnits(array);
					l2.clear();
					
					l2 = node.getChild("municipalitiesInGreek").getChildren("municipalityInGreek");
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element lang = (Element) l2.get(j);
						array[j] = lang.getValue();
					}
					greekDecAdm[i].setMunicipalities(array);
					l2.clear();
				}
				else
				{
					greekDecAdm[i].setNameInEnglish(node.getChildText("nameInEnglish"));
					greekDecAdm[i].setHeadquarters(node.getChildText("headquartersInEnglish"));
					greekDecAdm[i].setWikipediaLink(node.getChildText("wikipediaLinkInEnglish"));
					
					l2 = node.getChild("regionsInEnglish").getChildren("regionInEnglish");
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element lang = (Element) l2.get(j);
						array[j] = lang.getValue();
					}
					greekDecAdm[i].setRegions(array);
					l2.clear();
					
					l2 = node.getChild("regionalUnitsInEnglish").getChildren("regionalUnitInEnglish");
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element lang = (Element) l2.get(j);
						array[j] = lang.getValue();
					}
					greekDecAdm[i].setRegionalUnits(array);
					l2.clear();
					
					l2 = node.getChild("municipalitiesInEnglish").getChildren("municipalityInEnglish");
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element lang = (Element) l2.get(j);
						array[j] = lang.getValue();
					}
					greekDecAdm[i].setMunicipalities(array);
					l2.clear();
				}
				
				greekDecAdm[i].setNameInGreek(node.getChildText("nameInGreek"));
				greekDecAdm[i].setYearFormed(node.getChildText("yearFormed"));
				greekDecAdm[i].setWebsite(node.getChildText("website"));
				
				greekDecAdm[i].setAreaInKilometers(Integer.parseInt(node.getChildText("areaInKilometers")));
				greekDecAdm[i].setAreaInMiles(Integer.parseInt(node.getChildText("areaInMiles")));
				
				greekDecAdm[i].setPopulation(Integer.parseInt(node.getChildText("population")));
				
				greekDecAdm[i].setNumberOfRegions(Integer.parseInt(node.getChildText("numberOfRegions")));
				greekDecAdm[i].setNumberOfRegionalUnits(Integer.parseInt(node.getChildText("numberOfRegionalUnits")));
				greekDecAdm[i].setNumberOfMunicipalities(Integer.parseInt(node.getChildText("numberOfMunicipalities")));
			}
		}
		catch (Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load data for decentralized administrations of Greece", e));
		}
	}
	
	public static void readGreekRegionsXMLDataFile()
	{
		SAXBuilder builder = new SAXBuilder();
		
		try
		{
			Document doc  = builder.build(FilesIO.class.getResourceAsStream("/resources/dataFiles/Greece/regions.xml"));
			Element  root = doc.getRootElement();
			
			List list = root.getChildren();
			
			for (int i = 0; i < list.size(); i++)
			{
				Element node = (Element) list.get(i);
				
				if (greekRegions[i] == null) greekRegions[i] = new GreekRegion();
				
				List     l2;
				String[] array;
				
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					greekRegions[i].setSeat(node.getChildText("seatInGreek"));
					greekRegions[i].setLargestCity(node.getChildText("largestCityInGreek"));
					greekRegions[i].setLargestMunicipality(node.getChildText("largestMunicipalityInGreek"));
					greekRegions[i].setHighestPoint(node.getChildText("highestPointInGreek"));
					greekRegions[i].setWikipediaLink(node.getChildText("wikipediaLinkInGreek"));
					greekRegions[i].setDecentralizedAdministration(node.getChildText("decentralizedAdministrationInGreek"));
					
					l2 = node.getChild("regionalUnitsInGreek").getChildren("regionalUnitInGreek");
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element lang = (Element) l2.get(j);
						array[j] = lang.getValue();
					}
					greekRegions[i].setRegionalUnits(array);
					l2.clear();
					
					l2 = node.getChild("municipalitiesInGreek").getChildren("municipalityInGreek");
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element lang = (Element) l2.get(j);
						array[j] = lang.getValue();
					}
					greekRegions[i].setMunicipalities(array);
					l2.clear();
				}
				else
				{
					greekRegions[i].setNameInEnglish(node.getChildText("nameInEnglish"));
					greekRegions[i].setSeat(node.getChildText("seatInEnglish"));
					greekRegions[i].setLargestCity(node.getChildText("largestCityInEnglish"));
					greekRegions[i].setLargestMunicipality(node.getChildText("largestMunicipalityInEnglish"));
					greekRegions[i].setHighestPoint(node.getChildText("highestPointInEnglish"));
					greekRegions[i].setWikipediaLink(node.getChildText("wikipediaLinkInEnglish"));
					greekRegions[i].setDecentralizedAdministration(node.getChildText("decentralizedAdministrationInEnglish"));
					
					l2 = node.getChild("regionalUnitsInEnglish").getChildren("regionalUnitInEnglish");
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element lang = (Element) l2.get(j);
						array[j] = lang.getValue();
					}
					greekRegions[i].setRegionalUnits(array);
					l2.clear();
					
					l2 = node.getChild("municipalitiesInEnglish").getChildren("municipalityInEnglish");
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element lang = (Element) l2.get(j);
						array[j] = lang.getValue();
					}
					greekRegions[i].setMunicipalities(array);
					l2.clear();
				}
				
				greekRegions[i].setNameInGreek(node.getChildText("nameInGreek"));
				greekRegions[i].setWebsite(node.getChildText("website"));
				
				greekRegions[i].setAreaInKilometers(Integer.parseInt(node.getChildText("areaInKilometers")));
				greekRegions[i].setAreaInMiles(Integer.parseInt(node.getChildText("areaInMiles")));
				greekRegions[i].setAreaRanking(Integer.parseInt(node.getChildText("areaRanking")));
				
				greekRegions[i].setPopulation(Integer.parseInt(node.getChildText("population")));
				greekRegions[i].setPopulationDensityPerSquareKilometer(Integer.parseInt(node.getChildText("populationDensityPerSquareKilometer")));
				greekRegions[i].setPopulationDensityPerSquareMile(Integer.parseInt(node.getChildText("populationDensityPerSquareMile")));
				greekRegions[i].setPopulationRanking(Integer.parseInt(node.getChildText("populationRanking")));
				
				greekRegions[i].setNumberOfRegionalUnits(Integer.parseInt(node.getChildText("numberOfRegionalUnits")));
				greekRegions[i].setNumberOfMunicipalities(Integer.parseInt(node.getChildText("numberOfMunicipalities")));
			}
		}
		catch (Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load data for regions of Greece", e));
		}
	}
	
	public static void readGreekRegionalUnitsXMLDataFile()
	{
		SAXBuilder builder = new SAXBuilder();
		
		try
		{
			Document doc  = builder.build(FilesIO.class.getResourceAsStream("/resources/dataFiles/Greece/regionalUnits.xml"));
			Element  root = doc.getRootElement();
			
			List list = root.getChildren();
			
			for (int i = 0; i < list.size(); i++)
			{
				Element node = (Element) list.get(i);
				
				if (greekRegionalUnits[i] == null) greekRegionalUnits[i] = new GreekRegionalUnit();
				
				List     l2;
				String[] array;
				
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					greekRegionalUnits[i].setCapital(node.getChildText("capitalInGreek"));
					greekRegionalUnits[i].setLargestCity(node.getChildText("largestCityInGreek"));
					greekRegionalUnits[i].setLargestMunicipality(node.getChildText("largestMunicipalityInGreek"));
					greekRegionalUnits[i].setHighestPoint(node.getChildText("highestPointInGreek"));
					greekRegionalUnits[i].setWikipediaLink(node.getChildText("wikipediaLinkInGreek"));
					greekRegionalUnits[i].setDecentralizedAdministration(node.getChildText("decentralizedAdministrationInGreek"));
					greekRegionalUnits[i].setRegion(node.getChildText("regionInGreek"));
					
					l2 = node.getChild("municipalitiesInGreek").getChildren("municipalityInGreek");
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element lang = (Element) l2.get(j);
						array[j] = lang.getValue();
					}
					greekRegionalUnits[i].setMunicipalities(array);
					l2.clear();
				}
				else
				{
					greekRegionalUnits[i].setNameInEnglish(node.getChildText("nameInEnglish"));
					greekRegionalUnits[i].setCapital(node.getChildText("capitalInEnglish"));
					greekRegionalUnits[i].setLargestCity(node.getChildText("largestCityInEnglish"));
					greekRegionalUnits[i].setLargestMunicipality(node.getChildText("largestMunicipalityInEnglish"));
					greekRegionalUnits[i].setHighestPoint(node.getChildText("highestPointInEnglish"));
					greekRegionalUnits[i].setWikipediaLink(node.getChildText("wikipediaLinkInEnglish"));
					greekRegionalUnits[i].setDecentralizedAdministration(node.getChildText("decentralizedAdministrationInEnglish"));
					greekRegionalUnits[i].setRegion(node.getChildText("regionInEnglish"));
					
					l2 = node.getChild("municipalitiesInEnglish").getChildren("municipalityInEnglish");
					array = new String[l2.size()];
					for (int j = 0; j < l2.size(); j++)
					{
						Element lang = (Element) l2.get(j);
						array[j] = lang.getValue();
					}
					greekRegionalUnits[i].setMunicipalities(array);
					l2.clear();
				}
				
				greekRegionalUnits[i].setNameInGreek(node.getChildText("nameInGreek"));
				greekRegionalUnits[i].setWebsite(node.getChildText("website"));
				
				greekRegionalUnits[i].setAreaInKilometers(Integer.parseInt(node.getChildText("areaInKilometers")));
				greekRegionalUnits[i].setAreaInMiles(Integer.parseInt(node.getChildText("areaInMiles")));
				greekRegionalUnits[i].setAreaRanking(Integer.parseInt(node.getChildText("areaRanking")));
				
				greekRegionalUnits[i].setPopulation(Integer.parseInt(node.getChildText("population")));
				greekRegionalUnits[i].setPopulationDensityPerSquareKilometer(Integer.parseInt(node.getChildText("populationDensityPerSquareKilometer")));
				greekRegionalUnits[i].setPopulationDensityPerSquareMile(Integer.parseInt(node.getChildText("populationDensityPerSquareMile")));
				greekRegionalUnits[i].setPopulationRanking(Integer.parseInt(node.getChildText("populationRanking")));
				
				greekRegionalUnits[i].setNumberOfMunicipalities(Integer.parseInt(node.getChildText("numberOfMunicipalities")));
			}
		}
		catch (Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load data for regional units of Greece", e));
		}
	}
	
	public static void readAttractionsXMLDataFile()
	{
		SAXBuilder builder = new SAXBuilder();
		
		try
		{
			Document doc  = builder.build(FilesIO.class.getResourceAsStream("/resources/dataFiles/attractions.xml"));
			Element  root = doc.getRootElement();
			
			List list = root.getChildren();
			
			for (int i = 0; i < list.size(); i++)
			{
				Element node = (Element) list.get(i);
				
				if (attractions[i] == null) attractions[i] = new Attraction();
				
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					attractions[i].setCountry(node.getChildText("countryInGreek"));
					attractions[i].setCity(node.getChildText("cityInGreek"));
					attractions[i].setBasicInfo(node.getChildText("basicInfoInGreek"));
					attractions[i].setWikipediaLink(node.getChildText("wikipediaLinkInGreek"));
					attractions[i].setGreekArticle(node.getChildText("greekArticle"));
				}
				else
				{
					attractions[i].setNameInEnglish(node.getChildText("nameInEnglish"));
					
					attractions[i].setCountry(node.getChildText("countryInEnglish"));
					attractions[i].setCity(node.getChildText("cityInEnglish"));
					attractions[i].setBasicInfo(node.getChildText("basicInfoInEnglish"));
					attractions[i].setWikipediaLink(node.getChildText("wikipediaLinkInEnglish"));
				}
				
				attractions[i].setNameInGreek(node.getChildText("nameInGreek"));
				
				attractions[i].setYearBuilt(node.getChildText("yearBuilt"));
				attractions[i].setCoordinates(node.getChildText("coordinates"));
				attractions[i].setEasy(Boolean.parseBoolean(node.getChildText("isEasy")));
			}
		}
		catch (Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load data for attractions", e));
		}
	}
}