package code.core;

import code.dataStructures.*;
import code.screens.*;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.concurrent.Task;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class GlobalVariables
{
//  --------------------- BASIC GAME VARIABLES ---------------------
	public static OsCheck.OSType OS;
	
	static String GAME_DATA_PATH;
	static String GAME_SETTINGS_FILE_PATH;
	static String PLAYERS_FILE_PATH;
	static String GAMES_SCORES_FILE_PATH;
	
	public static Clipboard clipboard;
	public static ClipboardContent clipboardContent;
	static ShutdownHook shutdownHook;
	public static String email;
	public static ResourceBundle languageResourceBundle;
	public static ArrayList<Player> playersArrayList;
	public static ArrayList<Game> games;
	
	public static NumberFormat numberFormatForUI;
	public static DateTimeFormatter dateTimeForUI, dateForUI;
	
	static DecimalFormat decimalFormatForSaving;
	static DateTimeFormatter dateTimeFormatForSaving, dateFormatForSaving;
	
	//  --------------------- OTHER GAME VARIABLES ---------------------
	public static final int LANGUAGE_ENGLISH = 0, LANGUAGE_GREEK = 1;
	public static final int METRIC_SYSTEM = 0, IMPERIAL_SYSTEM = 1;
	
	public static final double TEXT_SCALE_ANIMATION_TIME = 4000.0;
	public static final byte CLASSIC_GAMEMODE = 0, TIME_ATTACK_GAMEMODE = 1, ENDLESS_GAMEMODE = 2;
	public static final int NO_ANIMATIONS = 0, LIMITED_ANIMATIONS = 1, ALL_ANIMATIONS = 2;
	
	public static final byte DIFFICULTY_EASY = 0, DIFFICULTY_DIFFICULT = 1;
	public static final int NUM_OF_QUESTIONS_FOR_CLASSIC_10 = 10, NUM_OF_QUESTIONS_FOR_CLASSIC_20 = 20, NUM_OF_QUESTIONS_FOR_CLASSIC_50 = 50, NUM_OF_QUESTIONS_FOR_CLASSIC_100 = 100;
	public static final int TIME_ATTACK_DURATION_1_MINUTE = 1, TIME_ATTACK_DURATION_2_MINUTES = 2, TIME_ATTACK_DURATION_5_MINUTES = 5, TIME_ATTACK_DURATION_10_MINUTES = 10;
	public static final int ENDLESS_LIVES_1 = 1, ENDLESS_LIVES_3 = 3, ENDLESS_LIVES_5 = 5;
	
	public static int animationsUsed;
	public static boolean fullScreenMode, startAtFullScreen, minimizedMode, hasNotInitializedWindowedMode;
	
	public static final int MAX_WIDTH_FOR_X250_IMAGES = 280, MAX_WIDTH_FOR_X500_IMAGES = 550, MAX_WIDTH_FOR_X1000_IMAGES = 1200;
	
	public static int getCurrentLanguage()
	{
		if(getCurrentPlayer().getLanguage() == LANGUAGE_GREEK) return LANGUAGE_GREEK;
		else return LANGUAGE_ENGLISH;
	}
	
	public static int getDifficultyLevel()
	{
		return getCurrentPlayer().getDifficultyLevel();
	}
	public static void setDifficultyLevel(int difficultyLevel)
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
	
	public static final short NUM_ALL_CONTINENTS = 7;
	
	public static final short NUM_ALL_USA_STATES = 50;

	public static final short NUM_ALL_GREEK_DEC_ADMIN = 7;
	public static final short NUM_ALL_GREEK_REGIONS = 13;
	public static final short NUM_ALL_GREEK_REGIONAL_UNITS = 74;
	public static final short NUM_ALL_GREEK_MUNICIPALITIES = 325;

	public static final int NUM_ALL_ATTRACTIONS = 94;
//	public static final int NUMBER_OF_GENERAL_QUESTIONS = 672;

//	--------------------- CSS ---------------------
	public static final String buttonCSS = GlobalVariables.class.getResource("/resources/css/button.css").toExternalForm();
	public static final String checkboxCSS = GlobalVariables.class.getResource("/resources/css/checkbox.css").toExternalForm();
	public static final String radioButtonCSS = GlobalVariables.class.getResource("/resources/css/radioButton.css").toExternalForm();
	public static final String toggleButtonCSS = GlobalVariables.class.getResource("/resources/css/toggleButton.css").toExternalForm();
	public static final String listViewCSS = GlobalVariables.class.getResource("/resources/css/listView.css").toExternalForm();
	public static final String labelCSS = GlobalVariables.class.getResource("/resources/css/label.css").toExternalForm();
	public static final String scrollPaneCSS = GlobalVariables.class.getResource("/resources/css/scrollPane.css").toExternalForm();
	public static final String comboBoxCSS = GlobalVariables.class.getResource("/resources/css/comboBox.css").toExternalForm();
	public static final String textAreaCSS = GlobalVariables.class.getResource("/resources/css/textArea.css").toExternalForm();
	public static final String progressBarCSS = GlobalVariables.class.getResource("/resources/css/progressBar.css").toExternalForm();
	public static final String sliderCSS = GlobalVariables.class.getResource("/resources/css/slider.css").toExternalForm();
	public static final String tableViewCSS = GlobalVariables.class.getResource("/resources/css/tableView.css").toExternalForm();
	public static final String popOverCSS = GlobalVariables.class.getResource("/resources/css/popOver.css").toExternalForm();

//  --------------------- WINDOW OBJECTS ---------------------
	public static Stage stage;
	public static Scene mainScene;

	public static SplashScreen splashScreen;
	static MessageDisplayResolutionNotSupportedScreen messageDisplayResolutionNotSupportedScreen;
	static MessageGameIsAlreadyRunningScreen messageGameIsAlreadyRunningScreen;
	public static WelcomeScreen welcomeScreen;
	public static GamePropertiesScreen gamePropertiesScreen;
	public static GameScreen gameScreen;
	public static AtlasScreen atlasScreen;
	public static ScoreBoardScreen scoreBoardScreen;

	public static double windowWidth, windowHeight;

	public static ScaleTransition scaleTransitionForAnchorPane;
	
	public static double getHeightBasedOnWidth(double width)
	{
		return width / getCurrentScreenRatioValue();
	}
	
	public static boolean isWidthValid(double width)
	{
		return width <= primaryScreenWidth &&
				(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_9 && width >= MIN_WIDTH_FOR_16_9 ||
				 getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_10 && width >= MIN_WIDTH_FOR_16_10 ||
				 getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_25_16 && width >= MIN_WIDTH_FOR_25_16 ||
				 getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_4_3 && width >= MIN_WIDTH_FOR_4_3 ||
				 getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_5_4 && width >= MIN_WIDTH_FOR_5_4 ||
				 getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_3_2 && width >= MIN_WIDTH_FOR_3_2);
	}

	public static void minimizeGame(Pane anchorPane)
	{
		playMinimizeSound();
		if(OS == OsCheck.OSType.Windows && animationsUsed != NO_ANIMATIONS)
		{
			scaleTransitionForAnchorPane.setNode(anchorPane);
			scaleTransitionForAnchorPane.setToX(0);
			scaleTransitionForAnchorPane.setToY(0);
			scaleTransitionForAnchorPane.setOnFinished(e ->
			{
				stage.setIconified(true);
				
				if(isIntroductionSoundPlaying()) pauseIntroductionSound();
				else if(isWelcomeLoopSoundPlaying()) pauseWelcomeLoopSound();
			});
			scaleTransitionForAnchorPane.playFromStart();
		}
		else
		{
			stage.setIconified(true);

			if(isIntroductionSoundPlaying()) pauseIntroductionSound();
			else if(isWelcomeLoopSoundPlaying()) pauseWelcomeLoopSound();
		}
	}

	static void restoreMinimizedWindow(Pane anchorPane)
	{
		playMaximizeSound();
		stage.setIconified(false);
		if(OS == OsCheck.OSType.Windows && animationsUsed != NO_ANIMATIONS)
		{
			scaleTransitionForAnchorPane.setNode(anchorPane);
			scaleTransitionForAnchorPane.setToX(1);
			scaleTransitionForAnchorPane.setToY(1);
			scaleTransitionForAnchorPane.setOnFinished(e ->
			{
				if(isIntroductionSoundPaused()) playIntroductionSound();
				else if(isWelcomeLoopSoundPaused()) playWelcomeLoopSoundSound();
			});
			scaleTransitionForAnchorPane.playFromStart();
		
		}
		else
		{
			if(isIntroductionSoundPaused()) playIntroductionSound();
			else if(isWelcomeLoopSoundPaused()) playWelcomeLoopSoundSound();
		}
	}

	public static void exitGame(Pane anchorPane)
	{
		if(animationsUsed != NO_ANIMATIONS)
		{
			scaleTransitionForAnchorPane.setNode(anchorPane);
			scaleTransitionForAnchorPane.setToX(0);
			scaleTransitionForAnchorPane.setToY(0);
			scaleTransitionForAnchorPane.setOnFinished(e -> Platform.exit());
			
			playMinimizeSound();
			scaleTransitionForAnchorPane.play();
		}
		else Platform.exit();
	}
	
	public static double getWorldMapLayoutX()
	{
		return worldMapLayoutX;
	}
	
	public static double getWorldMapLayoutY()
	{
		return worldMapLayoutY;
	}
	
	public static double getWorldMapFitWidth()
	{
		return worldMapFitWidth;
	}
	
	public static double getWorldMapFitHeight()
	{
		return worldMapFitHeight;
	}

//	--------------------- SCREEN STUFF ---------------------
	public enum SUPPORTED_SCREEN_RATIOS { RATIO_16_9, RATIO_16_10, RATIO_25_16, RATIO_3_2, RATIO_4_3, RATIO_5_4, RATIO_NOT_SUPPORTED }
	
	public static SUPPORTED_SCREEN_RATIOS currentScreenRatioEnum;
	public static double currentScreenRatioValue;
	
	public static double primaryScreenWidth, primaryScreenHeight;
	public static Rectangle2D primaryScreenResolution;
	
	static double worldMapLayoutX, worldMapLayoutY, worldMapFitWidth, worldMapFitHeight;
	
	public static final int MIN_WIDTH_FOR_16_9 = 1050, MIN_HEIGHT_FOR_16_9 = 590, MIN_WIDTH_FOR_16_10 = 1000, MIN_HEIGHT_FOR_16_10 = 625,
							MIN_WIDTH_FOR_25_16 = 1000, MIN_HEIGHT_FOR_25_16 = 640, MIN_WIDTH_FOR_3_2 = 990, MIN_HEIGHT_FOR_3_2 = 660,
							MIN_WIDTH_FOR_4_3 = 800, MIN_HEIGHT_FOR_4_3 = 600, MIN_WIDTH_FOR_5_4 = 900, MIN_HEIGHT_FOR_5_4 = 720;
	
	public static final double EPSILON_VALUE = 0.00001;
	
	public static void setCurrentScreenRatio(double screenWidth, double screenHeight)
	{
		currentScreenRatioValue = screenWidth / screenHeight;
		
		if(Math.abs(currentScreenRatioValue - 16.0 / 9.0) <= EPSILON_VALUE
				|| Math.abs(currentScreenRatioValue - 85.0 / 48.0) <= EPSILON_VALUE
				|| Math.abs(currentScreenRatioValue - 683.0 / 384.0) <= EPSILON_VALUE
				|| Math.abs(currentScreenRatioValue - 221.0 / 124.0) <= EPSILON_VALUE
				|| Math.abs(currentScreenRatioValue - 147.0 / 83.0) <= EPSILON_VALUE)
			currentScreenRatioEnum = SUPPORTED_SCREEN_RATIOS.RATIO_16_9;
		else if(Math.abs(currentScreenRatioValue - 16.0 / 10.0) <= EPSILON_VALUE)
			currentScreenRatioEnum = SUPPORTED_SCREEN_RATIOS.RATIO_16_10;
		else if(Math.abs(currentScreenRatioValue - 25.0 / 16.0) <= EPSILON_VALUE)
			currentScreenRatioEnum = SUPPORTED_SCREEN_RATIOS.RATIO_25_16;
		else if(Math.abs(currentScreenRatioValue - 3.0 / 2.0) <= EPSILON_VALUE)
			currentScreenRatioEnum = SUPPORTED_SCREEN_RATIOS.RATIO_3_2;
		else if(Math.abs(currentScreenRatioValue - 4.0 / 3.0) <= EPSILON_VALUE)
			currentScreenRatioEnum = SUPPORTED_SCREEN_RATIOS.RATIO_4_3;
		else if(Math.abs(currentScreenRatioValue - 5.0 / 4.0) <= EPSILON_VALUE)
			currentScreenRatioEnum = SUPPORTED_SCREEN_RATIOS.RATIO_5_4;
		else
			currentScreenRatioEnum = SUPPORTED_SCREEN_RATIOS.RATIO_NOT_SUPPORTED;
	}

	public static SUPPORTED_SCREEN_RATIOS getCurrentScreenRatioEnum()
	{
		return currentScreenRatioEnum;
	}
	
	public static double getCurrentScreenRatioValue() { return currentScreenRatioValue; }
	
	public static boolean isCurrentScreenRatioSupported()
	{
		return currentScreenRatioEnum != SUPPORTED_SCREEN_RATIOS.RATIO_NOT_SUPPORTED;
	}
	
	public static void setSoundIcon(ImageView soundIcon, boolean clicked)
	{
		if(clicked)
		{
			if (soundIcon.getImage() == SOUND_OFF_ICON) soundIcon.setImage(SOUND_OFF_ICON_CLICKED);
			else if (soundIcon.getImage() == SOUND_ON_1_BAR_ICON) soundIcon.setImage(SOUND_ON_1_BAR_ICON_CLICKED);
			else if (soundIcon.getImage() == SOUND_ON_2_BARS_ICON) soundIcon.setImage(SOUND_ON_2_BARS_ICON_CLICKED);
			else if (soundIcon.getImage() == SOUND_ON_3_BARS_ICON) soundIcon.setImage(SOUND_ON_3_BARS_ICON_CLICKED);
		}
		else
		{
			if (soundIcon.getImage() == SOUND_OFF_ICON_CLICKED) soundIcon.setImage(SOUND_OFF_ICON);
			else if (soundIcon.getImage() == SOUND_ON_1_BAR_ICON_CLICKED) soundIcon.setImage(SOUND_ON_1_BAR_ICON);
			else if (soundIcon.getImage() == SOUND_ON_2_BARS_ICON_CLICKED) soundIcon.setImage(SOUND_ON_2_BARS_ICON);
			else if (soundIcon.getImage() == SOUND_ON_3_BARS_ICON_CLICKED) soundIcon.setImage(SOUND_ON_3_BARS_ICON);
		}
	}
	
	public static Player getCurrentPlayer()
	{
		return playersArrayList.get(0);
	}
	
	public static String getReadablePlayedTime(int time)
	{
		int seconds = time % 60;
		int minutes = time / 60;
		int hours = minutes / 60;
		if(hours != 0) minutes = minutes % 60;
		
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

//	--------------------- IMAGES ---------------------
	public static Image SPLASH_BACKGROUND_IMAGE;
	public static Image SPLASH_TEXT_IMAGE;

	public static Image FRAME_IMAGE;
	public static Image EMPTY_WOOD_BACKGROUND_PANEL_SMALL_ROPE;
	public static Image EMPTY_WOOD_BACKGROUND_PANEL_BIG_ROPE;

	public static Image CHALKBOARD_BACKGROUND_IMAGE;
	public static Image WOOD_BACKGROUND_IMAGE_FOR_1_BUTTON;
//	public static Image WOOD_BACKGROUND_IMAGE_FOR_2_BUTTONS;
	public static Image WOOD_BACKGROUND_IMAGE_FOR_4_BUTTONS;
	public static Image LEFT_GLOBE_STAND_IMAGE;
	public static Image RIGHT_GLOBE_STAND_IMAGE;
	public static Image GAME_NAME_IMAGE;

	public static Image MOVING_EARTH_IMAGE_1;
	public static Image MOVING_EARTH_IMAGE_2;
	public static Image WOOD_BACKGROUND_IMAGE_FOR_5_BUTTONS;
	
	public static Image WORLD_MAP;

//	--------------------- ICONS ---------------------
	public static Image GREEK_FLAG_ICON;
	public static Image ENGLISH_FLAG_ICON;
	public static Image SOUND_ON_3_BARS_ICON;
	public static Image SOUND_ON_3_BARS_ICON_CLICKED;
	public static Image SOUND_ON_2_BARS_ICON;
	public static Image SOUND_ON_2_BARS_ICON_CLICKED;
	public static Image SOUND_ON_1_BAR_ICON;
	public static Image SOUND_ON_1_BAR_ICON_CLICKED;
	public static Image SOUND_OFF_ICON;
	public static Image SOUND_OFF_ICON_CLICKED;
	public static Image SETTINGS_ICON;
	public static Image SETTINGS_ICON_CLICKED;
	public static Image MINIMIZE_ICON;
	public static Image MINIMIZE_ICON_CLICKED;
	public static Image MOVE_ICON;
	public static Image MOVE_ICON_CLICKED;
	public static Image MOVE_ICON_DISABLED;
	public static Image MOVE_ICON_DISABLED_CLICKED;
	public static Image FULL_SCREEN_ICON;
	public static Image FULL_SCREEN_ICON_CLICKED;
	public static Image EXIT_ICON;
	public static Image EXIT_ICON_CLICKED;
	public static Image X_ICON;
	public static Image X_ICON_CLICKED;
	public static Image ZOOM_IN_ICON;
	public static Image ZOOM_IN_ICON_CLICKED;
	public static Image ZOOM_OUT_ICON;
	public static Image ZOOM_OUT_ICON_CLICKED;
	public static Image EDIT_ICON;
	public static Image EDIT_ICON_DISABLED;
	public static Image INFO_ICON;
	public static Image INFO_ICON_CLICKED;
	public static Image SINGLE_PLAYER_ICON;
	public static Image MULTI_PLAYER_ICON;
	public static Image ATLAS_ICON;
	public static Image SCORES_ICON;
	public static Image BACK_ARROW;
	public static Image PAUSE_ICON;
	public static Image PAUSE_ICON_CLICKED;
	public static Image CORRECT_ICON_SMALL;
	public static Image INCORRECT_ICON_SMALL;
	public static Image CORRECT_ICON_BIG;
	public static Image INCORRECT_ICON_BIG;
	public static Image HEART_ICON_SMALL;
	public static Image HEART_ICON_LOST_SMALL;
	public static Image HEART_ICON_BIG;
	public static Image HEART_ICON_BROKEN_BIG;
	
	public static Image PROGRESS_BAR_COLOR;

//	--------------------- AUDIO FILES ---------------------
	public static DoubleProperty masterSliderVolume;
	public static DoubleProperty musicSliderVolume;
	public static DoubleProperty soundEffectsSliderVolume;
	
	static DoubleBinding musicCalculatedVolume;
	static DoubleBinding soundEffectsCalculatedVolume;
	
	static AudioClip HOVER_SOUND;
	static AudioClip BUTTON_CLICK_SOUND;
	static AudioClip SWITCH_BUTTON_ON_SOUND;
	static AudioClip SWITCH_BUTTON_OFF_SOUND;
	static AudioClip CHECKBOX_SELECTED_SOUND;
	static AudioClip CHECKBOX_DESELECTED_SOUND;
	static AudioClip RADIOBUTTON_SELECTED_SOUND;
	
	static AudioClip POPUP_SOUND;
	static AudioClip SLIDE_SOUND;
	
	static AudioClip MINIMIZE_SOUND;
	static AudioClip MAXIMIZE_SOUND;
	
	static AudioClip REWIND_SOUND;
	static AudioClip CORRECT_ANSWER_SIMPLE_SOUND;
	static AudioClip WRONG_ANSWER_SIMPLE_SOUND;
	static AudioClip GAME_WON_SOUND;
	static AudioClip GAME_LOST_SOUND;
	static AudioClip HEART_BREAKING_SOUND;
	static AudioClip TIME_OVER_SOUND;

	static Media       CLOCK_TICKING_30S_SOUND;
	static MediaPlayer CLOCK_TICKING_30S_PLAYER;
	
	static Media introductionSound;
	static Media welcomeLoopSound;
	static MediaPlayer introductionMediaPlayer;
	static MediaPlayer welcomeLoopMediaPlayer;

	public static void playIntroductionSound()
	{
		new Thread(new Task<Void>() { protected Void call() throws Exception { introductionMediaPlayer.play(); return null; }}).start();
	}
	
	public static void pauseIntroductionSound()
	{
		introductionMediaPlayer.pause();
	}
	
	public static boolean isIntroductionSoundPlaying()
	{
		return introductionMediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
	}
	
	public static boolean isIntroductionSoundPaused()
	{
		return introductionMediaPlayer.getStatus() == MediaPlayer.Status.PAUSED;
	}
	
	public static boolean isIntroductionSoundReady()
	{
		return introductionMediaPlayer.getStatus() == MediaPlayer.Status.READY;
	}
	
	public static void playWelcomeLoopSoundSound()
	{
		new Thread(new Task<Void>() { protected Void call() throws Exception { welcomeLoopMediaPlayer.play(); return null; }}).start();
	}
	
	public static void pauseWelcomeLoopSound()
	{
		welcomeLoopMediaPlayer.pause();
	}
	
	public static boolean isWelcomeLoopSoundPlaying()
	{
		return welcomeLoopMediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
	}
	
	public static boolean isWelcomeLoopSoundPaused()
	{
		return welcomeLoopMediaPlayer.getStatus() == MediaPlayer.Status.PAUSED;
	}
	
	public static void playHoverSound()
	{
		HOVER_SOUND.play();
	}
	
	public static void playButtonClickSound()
	{
		BUTTON_CLICK_SOUND.play();
	}
	
	public static void playSwitchButtonOnSound()
	{
		SWITCH_BUTTON_ON_SOUND.play();
	}
	
	public static void playSwitchButtonOffSound()
	{
		SWITCH_BUTTON_OFF_SOUND.play();
	}
	
	public static void playCheckBoxSelectedSound()
	{
		CHECKBOX_SELECTED_SOUND.play();
	}
	
	public static void playCheckBoxDeselectedSound()
	{
		CHECKBOX_DESELECTED_SOUND.play();
	}
	
	public static void playRadioButtonSelectedSound()
	{
		RADIOBUTTON_SELECTED_SOUND.play();
	}
	
	public static void playPopUpSound()
	{
		POPUP_SOUND.play();
	}
	
	public static void playSlideSound()
	{
		SLIDE_SOUND.play();
	}
	
	public static void playMinimizeSound()
	{
		MINIMIZE_SOUND.play();
	}

	public static void playMaximizeSound()
	{
		MAXIMIZE_SOUND.play();
	}
	
	public static void playRewindSound()
	{
		REWIND_SOUND.play();
	}
	
	public static void playCorrectAnswerSimpleSound()
	{
		CORRECT_ANSWER_SIMPLE_SOUND.play();
	}
	
	public static void playWrongAnswerSimpleSound()
	{
		WRONG_ANSWER_SIMPLE_SOUND.play();
	}
	
	public static void playGameWonSound()
	{
		GAME_WON_SOUND.play();
	}
	
	public static void playGameLostSound()
	{
		GAME_LOST_SOUND.play();
	}
	
	public static void playHeartBreakingSound()
	{
		HEART_BREAKING_SOUND.play();
	}
	
	public static void playTimeOverSound()
	{
		TIME_OVER_SOUND.play();
	}
	
	public static void playClockTickingSound()
	{
		CLOCK_TICKING_30S_PLAYER.play();
	}
	
	public static void stopClockTickingSound()
	{
		CLOCK_TICKING_30S_PLAYER.stop();
	}
	
	//PLAYER NAMES
	private static String getStringWithoutTones(String a)
	{
		char[] temp = a.toCharArray();
		
		for(int i = 0; i < temp.length; i++)
		{
			if(temp[i] == 'ά') temp[i] = 'α';
			else if(temp[i] == 'έ') temp[i] = 'ε';
			else if(temp[i] == 'ή') temp[i] = 'η';
			else if(temp[i] == 'ί') temp[i] = 'ι';
			else if(temp[i] == 'ό') temp[i] = 'ο';
			else if(temp[i] == 'ύ') temp[i] = 'υ';
			else if(temp[i] == 'ώ') temp[i] = 'ω';
			else if(temp[i] == 'ς') temp[i] = 'σ';
			else if(temp[i] == 'Ά') temp[i] = 'Α';
			else if(temp[i] == 'Έ') temp[i] = 'Ε';
			else if(temp[i] == 'Ή') temp[i] = 'Η';
			else if(temp[i] == 'Ί') temp[i] = 'Ι';
			else if(temp[i] == 'Ό') temp[i] = 'Ο';
			else if(temp[i] == 'Ύ') temp[i] = 'Υ';
			else if(temp[i] == 'Ώ') temp[i] = 'Ω';
		}
		
		return String.valueOf(temp);
	}
	
	public static String getEditedOriginalName(String name)
	{
		name = name.trim();
		String originalName;
		String editedName = "";
		
		if (!name.matches("[^α-ωΑ-Ω]"))
		{
			String[] words = name.split(" ");
			for (int i = 0; i < words.length; i++)
			{
				String noTones = getStringWithoutTones(words[i]);
				if (noTones.endsWith("ησ") || noTones.endsWith("ΗΣ") || noTones.endsWith("ασ") || noTones.endsWith("ΑΣ")
						|| noTones.equalsIgnoreCase("Αλεκοσ") || noTones.equalsIgnoreCase("Γιωργοσ") || noTones.equalsIgnoreCase("Θανοσ")
						|| noTones.equalsIgnoreCase("Κυριακοσ") || noTones.equalsIgnoreCase("Λαμπροσ") || noTones.equalsIgnoreCase("Μανθοσ")
						|| noTones.equalsIgnoreCase("Μαρκοσ") || noTones.equalsIgnoreCase("Νικοσ") || noTones.equalsIgnoreCase("Παυλοσ")
						|| noTones.equalsIgnoreCase("Πετροσ") || noTones.equalsIgnoreCase("Σπυροσ") || noTones.equalsIgnoreCase("Σταυροσ")
						|| noTones.equalsIgnoreCase("Στελιοσ") || noTones.equalsIgnoreCase("Στεργιοσ") || noTones.equalsIgnoreCase("Χρηστοσ"))
					words[i] = words[i].substring(0, name.length() - 1);
				else if (words[i].endsWith("ος")) words[i] = words[i].replace("ος", "ε");
				else if (words[i].endsWith("οσ")) words[i] = words[i].replace("οσ", "ε");
				else if (words[i].endsWith("ΟΣ")) words[i] = words[i].replace("ΟΣ", "Ε");
				else if (words[i].endsWith("ός")) words[i] = words[i].replace("ός", "έ");
				else if (words[i].endsWith("όσ")) words[i] = words[i].replace("όσ", "έ");
				else if (words[i].endsWith("ΌΣ")) words[i] = words[i].replace("ΌΣ", "Έ");
			}
			editedName = Arrays.toString(words).replace("[", "").replace("]", "");
		}
		originalName = name;
		if(editedName.equals("")) editedName = originalName;
		
		return editedName;
	}
	
	
	// ------------  ROTATING GLOBE  ------------
	public static Image[] animatedGlobe;
	
	//DATA ARRAYS
	public static Country[] countries;
	public static Continent[] continents;
	public static StateOfUSA[] statesOfUSA;
	public static GreekDecentralizedAdministration[] greekDecAdm;
	public static GreekRegion[] greekRegions;
	public static GreekRegionalUnit[] greekRegionalUnits;
//	public static GreekRegionalUnit[] greekRegionalUnits;
//	public static GreekMunicipality[] greekMunicipalities;
	public static Attraction[] attractions;
	
//	static GeneralQuestion[] generalQuestions;
	
	public enum ImageType
	{
		COUNTRY_FLAG, COUNTRY_COAT_OF_ARMS, COUNTRY_LOCATION, CONTINENT_LOCATION, USA_FLAG, USA_SEAL, USA_LOCATION, GREECE_DEC_ADM_LOGO, GREECE_DEC_ADM_LOCATION,
		GREECE_REGION_LOGO, GREECE_REGION_LOCATION, GREECE_REGIONAL_UNIT_LOCATION, ATTRACTION_PHOTO, ATTRACTION_LOCATION
	}
	
	public static Image getImage(ImageType imageType, String size, String imageName, boolean backgroundLoading)
	{
		String type = "";
		if(imageType == ImageType.COUNTRY_FLAG) type = "countries/flags";
		else if(imageType == ImageType.COUNTRY_COAT_OF_ARMS)
		{
			type = "countries/coatOfArms";
			
			if(imageName.equals("Αγγλία") || imageName.equals("Ουαλία") || imageName.equals("Σκωτία") || imageName.equals("Βόρεια Ιρλανδία"))
				imageName = "Ηνωμένο Βασίλειο";
		}
		else if(imageType == ImageType.COUNTRY_LOCATION) type = "countries/location";
		else if(imageType == ImageType.CONTINENT_LOCATION) type = "continents";
		else if(imageType == ImageType.USA_FLAG) type = "usa/flags";
		else if(imageType == ImageType.USA_SEAL) type = "usa/seals";
		else if(imageType == ImageType.USA_LOCATION) type = "usa/location";
		else if(imageType == ImageType.GREECE_DEC_ADM_LOGO) type = "greece/decentralizedAdministrations/logos";
		else if(imageType == ImageType.GREECE_DEC_ADM_LOCATION) type = "greece/decentralizedAdministrations/location";
		else if(imageType == ImageType.GREECE_REGION_LOGO) type = "greece/regions/logos";
		else if(imageType == ImageType.GREECE_REGION_LOCATION) type = "greece/regions/location";
		else if(imageType == ImageType.GREECE_REGIONAL_UNIT_LOCATION) type = "greece/regionalUnits";
		else if(imageType == ImageType.ATTRACTION_PHOTO) type = "attractions/photos";
		else if(imageType == ImageType.ATTRACTION_LOCATION) type = "attractions/location";
		
		String extension;
		if(imageType == ImageType.GREECE_DEC_ADM_LOGO || imageType == ImageType.GREECE_DEC_ADM_LOCATION ||
		   imageType == ImageType.GREECE_REGION_LOGO || imageType == ImageType.GREECE_REGION_LOCATION ||
		   imageType == ImageType.GREECE_REGIONAL_UNIT_LOCATION || imageType == ImageType.ATTRACTION_PHOTO)
			extension = ".jpg";
		else extension = ".png";
		
		if(!size.equals("")) size = size + "/";
		
		return new Image("/resources/images/" + type + "/" + size + imageName + extension, backgroundLoading);
	}
	
	public static String getImagePath(ImageType imageType, String size, String imageName)
	{
		String type = "";
		if(imageType == ImageType.COUNTRY_FLAG) type = "countries/flags";
		else if(imageType == ImageType.COUNTRY_COAT_OF_ARMS)
		{
			type = "countries/coatOfArms";
			
			if(imageName.equals("Αγγλία") || imageName.equals("Ουαλία") || imageName.equals("Σκωτία") || imageName.equals("Βόρεια Ιρλανδία"))
				imageName = "Ηνωμένο Βασίλειο";
		}
		else if(imageType == ImageType.COUNTRY_LOCATION) type = "countries/location";
		else if(imageType == ImageType.CONTINENT_LOCATION) type = "continents";
		else if(imageType == ImageType.USA_FLAG) type = "usa/flags";
		else if(imageType == ImageType.USA_SEAL) type = "usa/seals";
		else if(imageType == ImageType.USA_LOCATION) type = "usa/location";
		else if(imageType == ImageType.GREECE_DEC_ADM_LOGO) type = "greece/decentralizedAdministrations/logos";
		else if(imageType == ImageType.GREECE_DEC_ADM_LOCATION) type = "greece/decentralizedAdministrations/location";
		else if(imageType == ImageType.GREECE_REGION_LOGO) type = "greece/regions/logos";
		else if(imageType == ImageType.GREECE_REGION_LOCATION) type = "greece/regions/location";
		else if(imageType == ImageType.GREECE_REGIONAL_UNIT_LOCATION) type = "greece/regionalUnits";
		else if(imageType == ImageType.ATTRACTION_PHOTO) type = "attractions/photos";
		else if(imageType == ImageType.ATTRACTION_LOCATION) type = "attractions/location";
		
		String extension;
		if(imageType == ImageType.GREECE_DEC_ADM_LOGO || imageType == ImageType.GREECE_DEC_ADM_LOCATION ||
		   imageType == ImageType.GREECE_REGION_LOGO || imageType == ImageType.GREECE_REGION_LOCATION ||
		   imageType == ImageType.GREECE_REGIONAL_UNIT_LOCATION || imageType == ImageType.ATTRACTION_PHOTO)
			extension = ".jpg";
		else extension = ".png";
		
		if(!size.equals("")) size = size + "/";
		
		return "/resources/images/" + type + "/" + size + imageName + extension;
	}
}