package com.johnzaro.geographyquiz.core;

import com.johnzaro.geographyquiz.core.helperClasses.OsCheck;
import com.johnzaro.geographyquiz.dataStructures.*;
import com.johnzaro.geographyquiz.screens.*;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class GlobalVariables
{
//  --------------------- BASIC GAME VARIABLES ---------------------
	static OsCheck.OSType OS;
	
	static String GAME_DATA_PATH;
	
	public static final double EPSILON_VALUE = 0.00001;
	public static Clipboard clipboard;
	public static ClipboardContent clipboardContent;
	public static String email;
	public static ResourceBundle languageResourceBundle, questionsResourceBundle;
	public static ArrayList<Player> playersArrayList;
	public static ArrayList<Game> games;
	public static final String FONT_NAME = "Comic Sans MS";
	
	public static NumberFormat numberFormatForUI;
	public static DateTimeFormatter dateTimeForUI, dateForUI;
	
	static DecimalFormat decimalFormatForSaving;
	static DateTimeFormatter dateTimeFormatForSaving, dateFormatForSaving;
	
	static ImageStuff imageStuff;
	public static ImageStuff getImageStuff()
	{
		return imageStuff;
	}
	
	static AudioStuff audioStuff;
	public static AudioStuff getAudioStuff()
	{
		return audioStuff;
	}
	
	static ScreenStuff screenStuff;
	public static ScreenStuff getScreenStuff()
	{
		return screenStuff;
	}
	
	//  --------------------- OTHER GAME VARIABLES ---------------------
	public enum LANGUAGE { ENGLISH, GREEK }
	
	public enum UNIT_SYSTEM { METRIC, IMPERIAL }
	
	public enum GAMEMODE { CLASSIC_GAMEMODE, TIME_ATTACK_GAMEMODE, ENDLESS_GAMEMODE }
	
	public enum ANIMATIONS { NO, LIMITED, ALL }
	public static ANIMATIONS animationsUsed;
	
	public enum DIFFICULTY { EASY, DIFFICULT }
	
	public static final int NUM_OF_QUESTIONS_FOR_CLASSIC_10 = 10, NUM_OF_QUESTIONS_FOR_CLASSIC_20 = 20, NUM_OF_QUESTIONS_FOR_CLASSIC_50 = 50, NUM_OF_QUESTIONS_FOR_CLASSIC_100 = 100;
	public static final int TIME_ATTACK_DURATION_1_MINUTE = 1, TIME_ATTACK_DURATION_2_MINUTES = 2, TIME_ATTACK_DURATION_5_MINUTES = 5, TIME_ATTACK_DURATION_10_MINUTES = 10;
	public static final int ENDLESS_LIVES_1 = 1, ENDLESS_LIVES_3 = 3, ENDLESS_LIVES_5 = 5;
	
	public static LANGUAGE getCurrentLanguage()
	{
		if(getCurrentPlayer() == null)
			return language;
		else
		{
			if(getCurrentPlayer().getLanguage() == LANGUAGE.GREEK) return LANGUAGE.GREEK;
			else return LANGUAGE.ENGLISH;
		}
	}
	static LANGUAGE language;
	
	public static DIFFICULTY getDifficultyLevel()
	{
		return getCurrentPlayer().getDifficultyLevel();
	}
	public static void setDifficultyLevel(DIFFICULTY difficultyLevel)
	{
		getCurrentPlayer().setDifficultyLevel(difficultyLevel);
	}
	
	public static int getNumberOfQuestionsForClassic()
	{
		return getCurrentPlayer().getNumberOfQuestionsInClassicalMode();
	}
	public static void setNumberOfQuestionsForClassic(int numOfQuestionsForClassic)
	{
		getCurrentPlayer().setNumberOfQuestionsInClassicalMode(numOfQuestionsForClassic);
	}
	
	public static void setTimerForClassic(boolean timerForClassic)
	{
		getCurrentPlayer().setTimerForClassicMode(timerForClassic);
	}
	public static boolean isTimerForClassic()
	{
		return getCurrentPlayer().isTimerForClassicMode();
	}
	
	public static int getDurationInMinutesForTimeAttack()
	{
		return getCurrentPlayer().getDurationForTimeAttackMode();
	}
	public static void setDurationInMinutesForTimeAttack(int durationForTimeAttack)
	{
		getCurrentPlayer().setDurationForTimeAttackMode(durationForTimeAttack);
	}
	public static int getDurationInSecondsForTimeAttack()
	{
		return getCurrentPlayer().getDurationForTimeAttackMode() * 60;
	}
	
	public static int getLivesForEndless()
	{
		return getCurrentPlayer().getLivesForEndlessMode();
	}
	public static void setLivesForEndless(int livesForEndless)
	{
		getCurrentPlayer().setLivesForEndlessMode(livesForEndless);
	}
	
	public static void setTimerForEndless(boolean timerForEndless)
	{
		getCurrentPlayer().setTimerForEndlessMode(timerForEndless);
	}
	public static boolean isTimerForEndless()
	{
		return getCurrentPlayer().isTimerForEndlessMode();
	}
	
	public static void setIsCategorySelected(int index, boolean selected)
	{
		getCurrentPlayer().getQuestionCategories()[index] = selected;
	}
	public static boolean isCategorySelected(int index)
	{
		return getCurrentPlayer().getQuestionCategories()[index];
	}
	
//	--------------------- GAME QUESTION CATEGORIES ---------------------
	public static final byte NUM_CAT_COUNTRIES_ALL = 11;
	public static final int  CAT_COUNTRY_CAPITALS  = 0, CAT_CONTINENT_COUNTRY_LARGEST_CITIES = 1,
			CAT_COUNTRY_LANGUAGES = 2, CAT_COUNTRY_CURRENCY = 3, CAT_CONTINENT_COUNTRY_POPULATION = 4,
			CAT_CONTINENT_COUNTRY_AREA = 5, CAT_CONTINENTS_COUNTRIES = 6,  CAT_SOVEREIGN_DEPENDENT_COUNTRIES = 7,
			CAT_COUNTRY_FLAGS = 8, CAT_COUNTRY_COAT_OF_ARMS = 9, CAT_CONTINENT_COUNTRY_LOCATION = 10;

	public static final byte NUM_CAT_USA_ALL = 9;
	public static final int CAT_USA_CAPITAL = 11, CAT_USA_LARGEST_CITIES = 12, CAT_USA_YEAR_ENTERED_UNION = 13,
			CAT_USA_NUM_SEATS = 14, CAT_USA_POPULATION = 15, CAT_USA_AREA = 16,
			CAT_USA_GEOGRAPHICAL_CHARACTERISTICS = 17, CAT_USA_FLAG = 18, CAT_USA_LOCATION = 19;

	public static final byte NUM_CAT_GREECE_ALL = 7;
	public static final int CAT_GREECE_ADMINISTRATIVE_DIVISION = 20, CAT_GREECE_LARGEST_CITIES = 21,
			CAT_GREECE_LARGEST_MUNICIPALITIES = 22, CAT_GREECE_GEOGRAPHICAL_CHARACTERISTICS = 23,
			CAT_GREECE_POPULATION = 24, CAT_GREECE_AREA = 25, CAT_GREECE_LOCATION = 26;
	
	public static final byte NUM_CAT_ATTRACTIONS_ALL = 1;

	public static final byte NUM_ALL_CATEGORIES = NUM_CAT_COUNTRIES_ALL + NUM_CAT_USA_ALL + NUM_CAT_GREECE_ALL + NUM_CAT_ATTRACTIONS_ALL;
	
	//	--------------------- NUMBER OF VARIOUS QUESTIONS ---------------------
	public static final short NUM_INDEPENDENT_COUNTRIES = 195;
	public static final short NUM_DEPENDENT_COUNTRIES = 36;
	public static final short NUM_ALL_COUNTRIES = NUM_INDEPENDENT_COUNTRIES + NUM_DEPENDENT_COUNTRIES;

	public static final short NUM_ASK_DEPENDENT_STATE = 28;

	public static final short NUM_COUNTRIES_EASY_CAPITAL = 52;
	public static final short NUM_COUNTRIES_NO_ASK_CAPITAL = 16;
	public static final short NUM_COUNTRIES_DIFFICULT_CAPITAL = NUM_ALL_COUNTRIES - NUM_COUNTRIES_NO_ASK_CAPITAL - NUM_COUNTRIES_EASY_CAPITAL;
	
	public static final short NUM_COUNTRIES_EASY_LARGEST_CITY = 45;
	public static final short NUM_COUNTRIES_NO_ASK_LARGEST_CITY = 14;
	public static final short NUM_COUNTRIES_DIFFICULT_LARGEST_CITY = NUM_ALL_COUNTRIES - NUM_COUNTRIES_NO_ASK_LARGEST_CITY - NUM_COUNTRIES_EASY_LARGEST_CITY;
	
	public static final short NUM_COUNTRIES_ASK_LANGUAGE = 174;
	public static final short NUM_COUNTRIES_INDIVIDUAL_LANGUAGE = 70;
	
	public static final short NUM_COUNTRIES_EASY_GEOGRAPHICAL_CHARACTERISTICS      = 58;
	public static final short NUM_COUNTRIES_NO_ASK_GEOGRAPHICAL_CHARACTERISTICS    = 15;
	public static final short NUM_COUNTRIES_DIFFICULT_GEOGRAPHICAL_CHARACTERISTICS = NUM_ALL_COUNTRIES - NUM_COUNTRIES_NO_ASK_GEOGRAPHICAL_CHARACTERISTICS - NUM_COUNTRIES_EASY_GEOGRAPHICAL_CHARACTERISTICS;
	
	public static final short NUM_COUNTRIES_EASY_CURRENCY = 30;
	public static final short NUM_COUNTRIES_NO_ASK_CURRENCY = 104;
	public static final short NUM_COUNTRIES_DIFFICULT_CURRENCY = NUM_ALL_COUNTRIES - NUM_COUNTRIES_NO_ASK_CURRENCY - NUM_COUNTRIES_EASY_CURRENCY;
	public static final short NUM_INDIVIDUAL_CURRENCIES = 59;
	
	public static final short NUM_COUNTRIES_EASY_FLAG = 47;
	public static final short NUM_COUNTRIES_DIFFICULT_FLAG = NUM_ALL_COUNTRIES - NUM_COUNTRIES_EASY_FLAG;
	
	public static final short NUM_COUNTRIES_ASK_COAT_OF_ARMS = 178;
	
	public static final short NUM_COUNTRIES_EASY_LOCATION = 58;
	public static final short NUM_COUNTRIES_DIFFICULT_LOCATION = NUM_ALL_COUNTRIES - NUM_COUNTRIES_EASY_LOCATION;
	
	public static final short NUM_ALL_GREEK_MUNICIPALITIES = 325;
	
//	public static final int NUMBER_OF_GENERAL_QUESTIONS = 672;

//	--------------------- CSS ---------------------
	public static final String[] cssFiles =
		{"button", "checkbox", "radioButton", "toggleButton", "listView", "label", "scrollPane", "comboBox", "textArea", "progressBar", "slider", "tableView", "various"};
	public static final String popOverCSS = GlobalVariables.class.getResource("/css/popOver.css").toExternalForm();

//  --------------------- WINDOW OBJECTS ---------------------
	public static Stage stage;
	public static Scene mainScene;
	
	public static RatioProperties ratioProperties;

	public static WelcomeScreen welcomeScreen;
	public static GamePropertiesScreen gamePropertiesScreen;
	public static GameScreen gameScreen;
	public static AtlasScreen atlasScreen;
	public static ScoreBoardScreen scoreBoardScreen;
	
	public static Player getCurrentPlayer()
	{
		if(playersArrayList != null && !playersArrayList.isEmpty())
			return playersArrayList.get(0);
		else return null;
	}
	
	public static String getReadablePlayedTime(int time)
	{
		int seconds = time % 60;
		int minutes = time / 60;
		int hours = minutes / 60;
		if(hours != 0) minutes = minutes % 60;
		
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
	
	public static int getLocaleIndexBasedOnLocale(Locale locale)
	{
		for(int i = 0; i < countries.length; i++)
		{
			if(countries[i].getLocaleCountryCode().equals(locale.getCountry()))
			{
				return i;
			}
		}
		return -1;
	}
	
	//DATA ARRAYS
	public static Country[] countries;
	public static Continent[] continents;
	public static StateOfUSA[] statesOfUSA;
	public static GreekDecentralizedAdministration[] greekDecAdm;
	public static GreekRegion[] greekRegions;
	public static GreekRegionalUnit[] greekRegionalUnits;
//	public static GreekMunicipality[] greekMunicipalities;
	public static Attraction[] attractions;
	
//	static GeneralQuestion[] generalQuestions;
}