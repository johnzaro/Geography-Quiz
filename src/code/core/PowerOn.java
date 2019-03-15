package code.core;

import code.dataStructures.*;
import code.screens.*;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

import static code.core.FilesIO.*;
import static code.core.GlobalVariables.*;

//helper class that contains all methods used when the game boots up
public class PowerOn
{
	static void setupGameVariables()
	{
		clipboard = Clipboard.getSystemClipboard();
		clipboardContent = new ClipboardContent();
		shutdownHook = new ShutdownHook();
		System.setProperty("prism.lcdtext", "false");
		OS = OsCheck.getOperatingSystemType();
		GAME_DATA_PATH = getGameDataPath(OS);
		GAME_SETTINGS_FILE_PATH = "gameSettings.xml";
		PLAYERS_FILE_PATH = "players.xml";
		GAMES_SCORES_FILE_PATH = "gamesScores.xml";
		email = "johnzrgnns@gmail.com";
		
		decimalFormatForSaving = ((DecimalFormat) NumberFormat.getNumberInstance(Locale.US));
		decimalFormatForSaving.applyPattern("#.####");
		
		dateTimeFormatForSaving = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
		dateFormatForSaving = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		masterSliderVolume = new SimpleDoubleProperty();
		musicSliderVolume = new SimpleDoubleProperty();
		soundEffectsSliderVolume = new SimpleDoubleProperty();
		
		musicCalculatedVolume = (masterSliderVolume.divide(100.0)).multiply(musicSliderVolume.divide(100.0));
		soundEffectsCalculatedVolume = (masterSliderVolume.divide(100.0)).multiply(soundEffectsSliderVolume.divide(100.0));
		
		playersArrayList = new ArrayList<>();
		games = new ArrayList<>();
		
		scaleTransitionForAnchorPane = new ScaleTransition(Duration.millis(350));
	}
	
	static void loadFonts()
	{
		try
		{
			Font.loadFont(PowerOn.class.getResourceAsStream("/resources/fonts/comicSansMSRegular.ttf"), 100);
			Font.loadFont(PowerOn.class.getResourceAsStream("/resources/fonts/comicSansMSBold.ttf"), 100);
		}
		catch(Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load fonts", e));
		}
	}
	
	static void setupPrimaryScreenBounds()
	{
		primaryScreenResolution = Screen.getPrimary().getBounds();
		primaryScreenWidth = primaryScreenResolution.getWidth();
		primaryScreenHeight = primaryScreenResolution.getHeight();
	}
	
	static void setDefaultLanguage()
	{
		try
		{
			if(Locale.getDefault().getLanguage().equals("el")) languageResourceBundle = ResourceBundle.getBundle("resources.lang.GeoQuiz", new Locale("el"));
			else languageResourceBundle = ResourceBundle.getBundle("resources.lang.GeoQuiz", new Locale("en"));
		}
		catch (Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load " + Locale.getDefault().getLanguage() + " resource bundle", e));
		}
	}
	
	static void setupStage(Stage coreStage)
	{
//		set global variable "stage" = javafx stage
		stage = coreStage;
		
//		set background of stage to transparent
		stage.initStyle(StageStyle.TRANSPARENT);
		
//		disable default resize function
		stage.setResizable(false);
		
//		add all icon sizes to program
		stage.getIcons().addAll(new Image("/resources/images/icons/icon@16x16.png"), new Image("/resources/images/icons/icon@32x32.png"),
				new Image("/resources/images/icons/icon@64x64.png"), new Image("/resources/images/icons/icon@128x128.png"));
		
//		disable exit key working as "exit full screen"
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

//		set game name
		stage.setTitle(languageResourceBundle.getString("gameName"));

//		set focus and minimize functions to set game in sleep mode when not in focus
		stage.focusedProperty().addListener(e ->
		{
			if (stage.isFocused())
			{
				if (animationsUsed == ALL_ANIMATIONS)
				{
//							if it has no focus and gains it back and animations are enabled -> play them
					if(welcomeScreen != null && mainScene.getRoot() == welcomeScreen.getAnchorPane())
					{
						welcomeScreen.playGlobeAnimation();
						welcomeScreen.resumeWelcomeTextAnimation();
					}
					else if(gamePropertiesScreen != null && mainScene.getRoot() == gamePropertiesScreen.getAnchorPane())
					{
						gamePropertiesScreen.playEarthAnimation();
						gamePropertiesScreen.resumeTextAnimation();
					}
					else if(gameScreen != null && mainScene.getRoot() == gameScreen.getAnchorPane())
					{
						gameScreen.resumeTextAnimation();
					}
					else if(atlasScreen != null && mainScene.getRoot() == atlasScreen.getAnchorPane())
					{
						atlasScreen.playEarthAnimation();
						atlasScreen.resumeTextAnimation();
					}
					else if(scoreBoardScreen != null && mainScene.getRoot() == scoreBoardScreen.getAnchorPane())
					{
						scoreBoardScreen.playEarthAnimation();
						scoreBoardScreen.resumeTextAnimation();
					}
				}
				
				if(gameScreen != null && mainScene.getRoot() == gameScreen.getAnchorPane())
				{
					gameScreen.resumeTimelineFor2_5SecondsWait();
				}
				
				if (minimizedMode)
				{
					//it is in minimized state and get back its focus
					minimizedMode = false;
					restoreMinimizedWindow((Pane) stage.getScene().getRoot());
				}
			}
			else
			{
				if (animationsUsed == ALL_ANIMATIONS)
				{
//							if it has focus and loses it and uses animations -> pause them
					if(welcomeScreen != null && mainScene.getRoot() == welcomeScreen.getAnchorPane())
					{
						welcomeScreen.stopGlobeAnimation();
						welcomeScreen.pauseWelcomeTextAnimation();
					}
					else if(gamePropertiesScreen != null && mainScene.getRoot() == gamePropertiesScreen.getAnchorPane())
					{
						gamePropertiesScreen.pauseEarthAnimation();
						gamePropertiesScreen.pauseTextAnimation();
					}
					else if(gameScreen != null && mainScene.getRoot() == gameScreen.getAnchorPane())
					{
						gameScreen.pauseTextAnimation();
					}
					else if(atlasScreen != null && mainScene.getRoot() == atlasScreen.getAnchorPane())
					{
						atlasScreen.pauseEarthAnimation();
						atlasScreen.pauseTextAnimation();
					}
					else if(scoreBoardScreen != null && mainScene.getRoot() == scoreBoardScreen.getAnchorPane())
					{
						scoreBoardScreen.pauseEarthAnimation();
						scoreBoardScreen.pauseTextAnimation();
					}
				}
				
				if(gameScreen != null && mainScene.getRoot() == gameScreen.getAnchorPane())
				{
					gameScreen.pauseTimelineFor2_5SecondsWait();
				}
			}
		});
	}
	
	static void powerOnMessageDisplayIsNotSupported(ResourceBundle r)
	{
		try
		{
			if(primaryScreenWidth <= 1920)
			{
				FRAME_IMAGE = new Image("/resources/images/backgrounds/frame_16-9@1920x1080.png", 0.85 * primaryScreenWidth, 0, true, false);
				CHALKBOARD_BACKGROUND_IMAGE = new Image("/resources/images/backgrounds/chalkboardBackground_16-9@1920x1080.jpg", 0.85 * primaryScreenWidth, 0, true, false);
			}
			else if(primaryScreenWidth <= 2560)
			{
				FRAME_IMAGE = new Image("/resources/images/backgrounds/frame_16-9@2560x1440.png", 0.85 * primaryScreenWidth, 0, true, false);
				CHALKBOARD_BACKGROUND_IMAGE = new Image("/resources/images/backgrounds/chalkboardBackground_16-9@2560x1440.jpg", 0.85 * primaryScreenWidth, 0, true, false);
			}
			else
			{
				FRAME_IMAGE = new Image("/resources/images/backgrounds/frame_16-9@5120x2880.png", 0.85 * primaryScreenWidth, 0, true, false);
				CHALKBOARD_BACKGROUND_IMAGE = new Image("/resources/images/backgrounds/chalkboardBackground_16-9@5120x2880.jpg", 0.85 * primaryScreenWidth, 0, true, false);
			}
			
			if(getCurrentLanguage() == LANGUAGE_GREEK) GAME_NAME_IMAGE = new Image("/resources/images/backgrounds/gameNameGreek.png", 0.7 * primaryScreenWidth, 0, true, false);
			else GAME_NAME_IMAGE  = new Image("/resources/images/backgrounds/gameNameEnglish.png", 0.7 * primaryScreenWidth, 0, true, false);
			
			BUTTON_CLICK_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/buttonClick.mp3").toExternalForm());
			MINIMIZE_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/minimize.mp3").toExternalForm());
			
			BUTTON_CLICK_SOUND.setVolume(0.7);
			MINIMIZE_SOUND.setVolume(0.7);
			
			messageDisplayResolutionNotSupportedScreen = new MessageDisplayResolutionNotSupportedScreen(r);
		}
		catch(Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to display the \"Resolution not supported\" message", e));
		}
	}
	
	static void loadSettings()
	{
		if(!isEmpty(playersFile)) readPlayersFile();
		else setDefaultPlayerName();
		
		loadLanguageResourceBundle();
		setNumberAndDateFormats(getCurrentPlayer().getLocale());
		
		if(!isEmpty(gamesScoresFile)) readGamesScores();
		
		if(isEmpty(savedSettingsFile))
		{
			setDefaultSettings();
			
			writeGameSettings();
		}
		else readGameSettings();
	}
	
	public static void loadLanguageResourceBundle()
	{
		if (getCurrentLanguage() == LANGUAGE_GREEK) languageResourceBundle = ResourceBundle.getBundle("resources.lang.GeoQuiz", new Locale("el"));
		else languageResourceBundle = ResourceBundle.getBundle("resources.lang.GeoQuiz", new Locale("en"));
	}
	
	public static void setNumberAndDateFormats(Locale locale)
	{
		numberFormatForUI = NumberFormat.getNumberInstance(locale);
		numberFormatForUI.setGroupingUsed(true);
		numberFormatForUI.setMaximumFractionDigits(2);
		
		dateTimeForUI = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(locale);
		dateForUI = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(locale);
		
	}
	
	public static void setWindowedModeValues()
	{
		stage.setX(primaryScreenResolution.getMinX() + primaryScreenWidth / 2.0 - windowWidth / 2.0);
		stage.setY(primaryScreenResolution.getMinY() + primaryScreenHeight / 2.0 - windowHeight / 2.0);
		stage.setWidth(windowWidth);
		stage.setHeight(windowHeight);
	}
	
	private static void setDefaultSettings()
	{
		masterSliderVolume.set(100);
		musicSliderVolume.set(50);
		soundEffectsSliderVolume.set(50);
		
		windowWidth = 0.75 * primaryScreenWidth;
		
		animationsUsed = ALL_ANIMATIONS;
		startAtFullScreen = false;
	}
	
	static void powerOnMessageGameIsAlreadyRunning()
	{
		try
		{
			double width;
			if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_9 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_10)
				width = 0.75 * primaryScreenWidth;
			else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_25_16 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_3_2) width = 0.8 * primaryScreenWidth;
			else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_4_3 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_5_4)
				width = 0.9 * primaryScreenWidth;
			else width = 0.85 * primaryScreenWidth;
			
			if(primaryScreenWidth <= 1920)
			{
				FRAME_IMAGE = new Image("/resources/images/backgrounds/frame_16-9@1920x1080.png", width, 0, true, false);
				CHALKBOARD_BACKGROUND_IMAGE = new Image("/resources/images/backgrounds/chalkboardBackground_16-9@1920x1080.jpg", width, 0, true, false);
			} else if(primaryScreenWidth <= 2560)
			{
				FRAME_IMAGE = new Image("/resources/images/backgrounds/frame_16-9@2560x1440.png", width, 0, true, false);
				CHALKBOARD_BACKGROUND_IMAGE = new Image("/resources/images/backgrounds/chalkboardBackground_16-9@2560x1440.jpg", width, 0, true, false);
			} else
			{
				FRAME_IMAGE = new Image("/resources/images/backgrounds/frame_16-9@5120x2880.png", width, 0, true, false);
				CHALKBOARD_BACKGROUND_IMAGE = new Image("/resources/images/backgrounds/chalkboardBackground_16-9@5120x2880.jpg", width, 0, true, false);
			}
			
			if(getCurrentLanguage() == LANGUAGE_GREEK) GAME_NAME_IMAGE = new Image("/resources/images/backgrounds/gameNameGreek.png", 0.7 * primaryScreenWidth, 0, true, false);
			else GAME_NAME_IMAGE  = new Image("/resources/images/backgrounds/gameNameEnglish.png", 0.7 * primaryScreenWidth, 0, true, false);
			
			BUTTON_CLICK_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/buttonClick.mp3").toExternalForm());
			MINIMIZE_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/minimize.mp3").toExternalForm());
			
			messageGameIsAlreadyRunningScreen = new MessageGameIsAlreadyRunningScreen();
		}
		catch(Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to display the \"Game is already running\" message", e));
		}
	}
	
	static void powerOnMedia()
	{
		try
		{
			Thread t1 = new Thread(() -> powerOnAudio());
			Thread t2 = new Thread(() -> powerOnImages());
			Thread t3 = new Thread(() -> powerOnAnimatedGlobeImages());
			Thread t4 = new Thread(() -> powerOnScreenDependentImages(primaryScreenWidth));
			
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			
			t1.join();
			t2.join();
			t3.join();
			t4.join();
			
			HOVER_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
			BUTTON_CLICK_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
			SWITCH_BUTTON_ON_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
			SWITCH_BUTTON_OFF_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
			CHECKBOX_SELECTED_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
			CHECKBOX_DESELECTED_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
			RADIOBUTTON_SELECTED_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
			POPUP_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
			SLIDE_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
			MINIMIZE_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
			MAXIMIZE_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
			REWIND_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
			CORRECT_ANSWER_SIMPLE_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
			WRONG_ANSWER_SIMPLE_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
			GAME_WON_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
			GAME_LOST_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
			HEART_BREAKING_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
			TIME_OVER_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
			CLOCK_TICKING_30S_PLAYER.volumeProperty().bind(soundEffectsCalculatedVolume);
			
			introductionMediaPlayer.volumeProperty().bind(musicCalculatedVolume);
			welcomeLoopMediaPlayer.volumeProperty().bind(musicCalculatedVolume);
		}
		catch(Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load media", e));
		}
	}
	
	private static void powerOnAudio()
	{
		HOVER_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/hover.wav").toExternalForm());
		BUTTON_CLICK_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/buttonClick.wav").toExternalForm());
		SWITCH_BUTTON_ON_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/switchButtonOn.wav").toExternalForm());
		SWITCH_BUTTON_OFF_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/switchButtonOff.wav").toExternalForm());
		CHECKBOX_SELECTED_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/checkboxSelected.wav").toExternalForm());
		CHECKBOX_DESELECTED_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/checkboxDeselected.wav").toExternalForm());
		RADIOBUTTON_SELECTED_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/radioButtonSelected.wav").toExternalForm());
		
		POPUP_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/popUp.wav").toExternalForm());
		SLIDE_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/slide.wav").toExternalForm());
		
		MINIMIZE_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/minimize.wav").toExternalForm());
		MAXIMIZE_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/maximize.wav").toExternalForm());
		
		REWIND_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/rewind.wav").toExternalForm());
		CORRECT_ANSWER_SIMPLE_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/correctAnswerSimple.wav").toExternalForm());
		WRONG_ANSWER_SIMPLE_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/wrongAnswerSimple.wav").toExternalForm());
		GAME_WON_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/gameWon.wav").toExternalForm());
		GAME_LOST_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/gameLost.wav").toExternalForm());
		HEART_BREAKING_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/heartBreaking.wav").toExternalForm());
		TIME_OVER_SOUND = new AudioClip(GlobalVariables.class.getResource("/resources/audio/timeOver.wav").toExternalForm());
		
		CLOCK_TICKING_30S_SOUND = new Media(GlobalVariables.class.getResource("/resources/audio/clockTicking30S.mp3").toExternalForm());
		CLOCK_TICKING_30S_PLAYER = new MediaPlayer(CLOCK_TICKING_30S_SOUND);
		CLOCK_TICKING_30S_PLAYER.setCycleCount(MediaPlayer.INDEFINITE);
		
		introductionSound = new Media(GlobalVariables.class.getResource("/resources/audio/introductionSound.mp3").toExternalForm());
		welcomeLoopSound = new Media(GlobalVariables.class.getResource("/resources/audio/welcomeLoopSound.mp3").toExternalForm());
		introductionMediaPlayer = new MediaPlayer(introductionSound);
		welcomeLoopMediaPlayer = new MediaPlayer(welcomeLoopSound);
		
		welcomeLoopMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		
		introductionMediaPlayer.setOnEndOfMedia(() ->
		{
			playWelcomeLoopSoundSound();
			introductionMediaPlayer.dispose();
		});
	}
	
	private static void powerOnImages()
	{
		if (getCurrentLanguage() == LANGUAGE_GREEK) GAME_NAME_IMAGE = new Image("/resources/images/backgrounds/gameNameGreek.png", 0.7 * primaryScreenWidth, 0, true, false);
		else GAME_NAME_IMAGE = new Image("/resources/images/backgrounds/gameNameEnglish.png", 0.7 * primaryScreenWidth, 0, true, false);
		
		EMPTY_WOOD_BACKGROUND_PANEL_SMALL_ROPE = new Image("/resources/images/backgrounds/emptyWoodBackgroundPanelSmallRope.png", 0.7 * primaryScreenWidth, 0, true, true);
		EMPTY_WOOD_BACKGROUND_PANEL_BIG_ROPE = new Image("/resources/images/backgrounds/emptyWoodBackgroundPanelBigRope.png", 0.7 * primaryScreenWidth, 0, true, true);
		
		WOOD_BACKGROUND_IMAGE_FOR_1_BUTTON = new Image("/resources/images/backgrounds/backgroundFor1Icon.png", 0.15 * primaryScreenWidth, 0, true, true);
//		WOOD_BACKGROUND_IMAGE_FOR_2_BUTTONS = new Image("/resources/images/backgrounds/backgroundFor2Icons.png", 0.2 * primaryScreenWidth, 0, true, true);
		WOOD_BACKGROUND_IMAGE_FOR_4_BUTTONS = new Image("/resources/images/backgrounds/backgroundFor4Icons.png", 0.3 * primaryScreenWidth, 0, true, true);
		WOOD_BACKGROUND_IMAGE_FOR_5_BUTTONS = new Image("/resources/images/backgrounds/backgroundFor5Icons.png", 0.4 * primaryScreenWidth, 0, true, true);
		
		LEFT_GLOBE_STAND_IMAGE = new Image("/resources/images/backgrounds/leftGlobeStand.png", 0.2 * primaryScreenWidth, 0, true, true);
		RIGHT_GLOBE_STAND_IMAGE = new Image("/resources/images/backgrounds/rightGlobeStand.png", 0.2 * primaryScreenWidth, 0, true, true);
		
		BACK_ARROW = new Image("/resources/images/icons/backArrow.png", 0.15 * primaryScreenWidth, 0, true, false);
		
		GREEK_FLAG_ICON = new Image("/resources/images/icons/el.png", 0.05 * primaryScreenWidth, 0, true, true);
		ENGLISH_FLAG_ICON = new Image("/resources/images/icons/en.png", 0.05 * primaryScreenWidth, 0, true, true);
		SOUND_ON_3_BARS_ICON = new Image("/resources/images/icons/soundOn_3bars.png", 0.05 * primaryScreenWidth, 0, true, true);
		SOUND_ON_3_BARS_ICON_CLICKED = new Image("/resources/images/icons/soundOnClicked_3bars.png", 0.05 * primaryScreenWidth, 0, true, true);
		SOUND_ON_2_BARS_ICON = new Image("/resources/images/icons/soundOn_2bars.png", 0.05 * primaryScreenWidth, 0, true, true);
		SOUND_ON_2_BARS_ICON_CLICKED = new Image("/resources/images/icons/soundOnClicked_2bars.png", 0.05 * primaryScreenWidth, 0, true, true);
		SOUND_ON_1_BAR_ICON = new Image("/resources/images/icons/soundOn_1bar.png", 0.05 * primaryScreenWidth, 0, true, true);
		SOUND_ON_1_BAR_ICON_CLICKED = new Image("/resources/images/icons/soundOnClicked_1bar.png", 0.05 * primaryScreenWidth, 0, true, true);
		SOUND_OFF_ICON = new Image("/resources/images/icons/soundOff.png", 0.05 * primaryScreenWidth, 0, true, true);
		SOUND_OFF_ICON_CLICKED = new Image("/resources/images/icons/soundOffClicked.png", 0.05 * primaryScreenWidth, 0, true, true);
		SETTINGS_ICON = new Image("/resources/images/icons/settings.png", 0.05 * primaryScreenWidth, 0, true, true);
		SETTINGS_ICON_CLICKED = new Image("/resources/images/icons/settingsClicked.png", 0.05 * primaryScreenWidth, 0, true, true);
		MINIMIZE_ICON = new Image("/resources/images/icons/minimize.png", 0.05 * primaryScreenWidth, 0, true, true);
		MINIMIZE_ICON_CLICKED = new Image("/resources/images/icons/minimizeClicked.png", 0.05 * primaryScreenWidth, 0, true, true);
		MOVE_ICON = new Image("/resources/images/icons/move.png", 0.05 * primaryScreenWidth, 0, true, true);
		MOVE_ICON_CLICKED = new Image("/resources/images/icons/moveClicked.png", 0.05 * primaryScreenWidth, 0, true, true);
		MOVE_ICON_DISABLED = new Image("/resources/images/icons/moveDisabled.png", 0.05 * primaryScreenWidth, 0, true, true);
		MOVE_ICON_DISABLED_CLICKED = new Image("/resources/images/icons/moveDisabledClicked.png", 0.05 * primaryScreenWidth, 0, true, true);
		FULL_SCREEN_ICON = new Image("/resources/images/icons/fullScreen.png", 0.05 * primaryScreenWidth, 0, true, true);
		FULL_SCREEN_ICON_CLICKED = new Image("/resources/images/icons/fullScreenClicked.png", 0.05 * primaryScreenWidth, 0, true, true);
		EXIT_ICON = new Image("/resources/images/icons/exit.png", 0.05 * primaryScreenWidth, 0, true, true);
		EXIT_ICON_CLICKED = new Image("/resources/images/icons/exitClicked.png", 0.05 * primaryScreenWidth, 0, true, true);
		X_ICON = new Image("/resources/images/icons/x.png", 0.05 * primaryScreenWidth, 0, true, true);
		X_ICON_CLICKED = new Image("/resources/images/icons/xClicked.png", 0.05 * primaryScreenWidth, 0, true, true);
		ZOOM_IN_ICON = new Image("/resources/images/icons/zoomIn.png", 0.05 * primaryScreenWidth, 0, true, true);
		ZOOM_IN_ICON_CLICKED = new Image("/resources/images/icons/zoomInClicked.png", 0.05 * primaryScreenWidth, 0, true, true);
		ZOOM_OUT_ICON = new Image("/resources/images/icons/zoomOut.png", 0.05 * primaryScreenWidth, 0, true, true);
		ZOOM_OUT_ICON_CLICKED = new Image("/resources/images/icons/zoomOutClicked.png", 0.05 * primaryScreenWidth, 0, true, true);
		EDIT_ICON = new Image("/resources/images/icons/editName.png", 0.05 * primaryScreenWidth, 0, true, true);
		EDIT_ICON_DISABLED = new Image("/resources/images/icons/editNameDisabled.png", 0.05 * primaryScreenWidth, 0, true, true);
		INFO_ICON = new Image("/resources/images/icons/info.png", 0.05 * primaryScreenWidth, 0, true, true);
		INFO_ICON_CLICKED = new Image("/resources/images/icons/infoClicked.png", 0.05 * primaryScreenWidth, 0, true, true);
		SINGLE_PLAYER_ICON = new Image("/resources/images/icons/singlePlayer.png", 0.05 * primaryScreenWidth, 0, true, true);
		MULTI_PLAYER_ICON = new Image("/resources/images/icons/multiPlayer.png", 0.05 * primaryScreenWidth, 0, true, true);
		ATLAS_ICON = new Image("/resources/images/icons/globe.png", 0.05 * primaryScreenWidth, 0, true, true);
		SCORES_ICON = new Image("/resources/images/icons/scores.png", 0.05 * primaryScreenWidth, 0, true, true);
		PAUSE_ICON = new Image("/resources/images/icons/pause.png", 0.05 * primaryScreenWidth, 0, true, true);
		PAUSE_ICON_CLICKED = new Image("/resources/images/icons/pauseClicked.png", 0.05 * primaryScreenWidth, 0, true, true);
		CORRECT_ICON_SMALL = new Image("/resources/images/icons/correctSmall.png", 0.05 * primaryScreenWidth, 0, true, true);
		INCORRECT_ICON_SMALL = new Image("/resources/images/icons/incorrectSmall.png", 0.05 * primaryScreenWidth, 0, true, true);
		HEART_ICON_SMALL = new Image("/resources/images/icons/heartSmall.png", 0.05 * primaryScreenWidth, 0, true, true);
		HEART_ICON_LOST_SMALL = new Image("/resources/images/icons/heartLostSmall.png", 0.05 * primaryScreenWidth, 0, true, true);
		
		PROGRESS_BAR_COLOR = new Image("/resources/images/icons/progressBarColor.jpg");
		
		if(primaryScreenHeight < 2800)
		{
			CORRECT_ICON_BIG = new Image("/resources/images/icons/correctBig.png", 0.7 * primaryScreenHeight, 0, true, true);
			INCORRECT_ICON_BIG = new Image("/resources/images/icons/incorrectBig.png", 0.7 * primaryScreenHeight, 0, true, true);
			HEART_ICON_BIG = new Image("/resources/images/icons/heartBig.png", 0.7 * primaryScreenHeight, 0, true, true);
			HEART_ICON_BROKEN_BIG = new Image("/resources/images/icons/heartBrokenBig.png", 0.7 * primaryScreenHeight, 0, true, true);
		}
		else
		{
			CORRECT_ICON_BIG = new Image("/resources/images/icons/correctBig.png");
			INCORRECT_ICON_BIG = new Image("/resources/images/icons/incorrectBig.png");
			HEART_ICON_BIG = new Image("/resources/images/icons/heartBig.png");
			HEART_ICON_BROKEN_BIG = new Image("/resources/images/icons/heartBrokenBig.png");
		}
	}
	
	public static void powerOnScreenDependentImages(double width)
	{
		try
		{
			if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_9)
			{
				if(primaryScreenWidth <= 1920)
				{
					FRAME_IMAGE = new Image("/resources/images/backgrounds/frame_16-9@1920x1080.png", width, 0, true, false);
					CHALKBOARD_BACKGROUND_IMAGE = new Image("/resources/images/backgrounds/chalkboardBackground_16-9@1920x1080.jpg", width, 0, true, false);
					WORLD_MAP = new Image("/resources/images/backgrounds/worldMap_16-9@1920x1080.jpg", width, 0, true, false);
					worldMapLayoutX = 0.0271; worldMapLayoutY = 0.0481; worldMapFitWidth = 0.9469; worldMapFitHeight = 0.9037;
				}
				else if(primaryScreenWidth <= 2560)
				{
					FRAME_IMAGE = new Image("/resources/images/backgrounds/frame_16-9@2560x1440.png", width, 0, true, false);
					CHALKBOARD_BACKGROUND_IMAGE = new Image("/resources/images/backgrounds/chalkboardBackground_16-9@2560x1440.jpg", width, 0, true, false);
					WORLD_MAP = new Image("/resources/images/backgrounds/worldMap_16-9@2560x1440.jpg", width, 0, true, false);
					worldMapLayoutX = 0.0266; worldMapLayoutY = 0.0444; worldMapFitWidth = 0.9510; worldMapFitHeight = 0.9120;
				}
				else
				{
					FRAME_IMAGE = new Image("/resources/images/backgrounds/frame_16-9@5120x2880.png", width, 0, true, false);
					CHALKBOARD_BACKGROUND_IMAGE = new Image("/resources/images/backgrounds/chalkboardBackground_16-9@5120x2880.jpg", width, 0, true, false);
					WORLD_MAP = new Image("/resources/images/backgrounds/worldMap_16-9@5120x2880.jpg", width, 0, true, false);
					worldMapLayoutX = 0.0250; worldMapLayoutY = 0.0444; worldMapFitWidth = 0.9505; worldMapFitHeight = 0.9167;
				}
			}
			else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_10 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_25_16 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_3_2)
			{
				if(primaryScreenWidth <= 1440)
				{
					FRAME_IMAGE = new Image("/resources/images/backgrounds/frame_16-10@1440x900.png", width, 0, true, false);
					CHALKBOARD_BACKGROUND_IMAGE = new Image("/resources/images/backgrounds/chalkboardBackground_16-10@1440x900.jpg", width, 0, true, false);
					WORLD_MAP = new Image("/resources/images/backgrounds/worldMap_16-10@1440x900.jpg", width, 0, true, false);
					worldMapLayoutX = 0.0281; worldMapLayoutY = 0.0508; worldMapFitWidth = 0.9458; worldMapFitHeight = 0.9000;
				}
				else if(primaryScreenWidth <= 1920)
				{
					FRAME_IMAGE = new Image("/resources/images/backgrounds/frame_16-10@1920x1200.png", width, 0, true, false);
					CHALKBOARD_BACKGROUND_IMAGE = new Image("/resources/images/backgrounds/chalkboardBackground_16-10@1920x1200.jpg", width, 0, true, false);
					WORLD_MAP = new Image("/resources/images/backgrounds/worldMap_16-10@1920x1200.jpg", width, 0, true, false);
					worldMapLayoutX = 0.0271; worldMapLayoutY = 0.0500; worldMapFitWidth = 0.9479; worldMapFitHeight = 0.9025;
				}
				else
				{
					FRAME_IMAGE = new Image("/resources/images/backgrounds/frame_16-10@2880x1800.png", width, 0, true, false);
					CHALKBOARD_BACKGROUND_IMAGE = new Image("/resources/images/backgrounds/chalkboardBackground_16-10@2880x1800.jpg", width, 0, true, false);
					WORLD_MAP = new Image("/resources/images/backgrounds/worldMap_16-10@2880x1800.jpg", width, 0, true, false);
					worldMapLayoutX = 0.0240; worldMapLayoutY = 0.0442; worldMapFitWidth = 0.9531; worldMapFitHeight = 0.9150;
				}
			}
			else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_4_3 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_5_4)
			{
				if(primaryScreenWidth <= 1400)
				{
					FRAME_IMAGE = new Image("/resources/images/backgrounds/frame_4-3@1400x1050.png", width, 0, true, false);
					CHALKBOARD_BACKGROUND_IMAGE = new Image("/resources/images/backgrounds/chalkboardBackground_4-3@1400x1050.jpg", width, 0, true, false);
					if(new Random().nextBoolean())
					{
						WORLD_MAP = new Image("/resources/images/backgrounds/worldMap1_4-3@1400x1050.jpg", width, 0, true, false);
						worldMapLayoutX = 0.0240; worldMapLayoutY = 0.0352; worldMapFitWidth = 0.9507; worldMapFitHeight = 0.9343;
					}
					else
					{
						WORLD_MAP = new Image("/resources/images/backgrounds/worldMap2_4-3@1400x1050.jpg", width, 0, true, false);
						worldMapLayoutX = 0.0250; worldMapLayoutY = 0.0324; worldMapFitWidth = 0.9521; worldMapFitHeight = 0.9371;
					}
				}
				else if(primaryScreenWidth <= 2048)
				{
					FRAME_IMAGE = new Image("/resources/images/backgrounds/frame_4-3@2048x1536.png", width, 0, true, false);
					CHALKBOARD_BACKGROUND_IMAGE = new Image("/resources/images/backgrounds/chalkboardBackground_4-3@2048x1536.jpg", width, 0, true, false);
					if(new Random().nextBoolean())
					{
						WORLD_MAP = new Image("/resources/images/backgrounds/worldMap1_4-3@2048x1536.jpg", width, 0, true, false);
						worldMapLayoutX = 0.0240; worldMapLayoutY = 0.0352; worldMapFitWidth = 0.9507; worldMapFitHeight = 0.9343;
					}
					else
					{
						WORLD_MAP = new Image("/resources/images/backgrounds/worldMap2_4-3@2048x1536.jpg", width, 0, true, false);
						worldMapLayoutX = 0.0250; worldMapLayoutY = 0.0324; worldMapFitWidth = 0.9521; worldMapFitHeight = 0.9371;
					}
				}
				else
				{
					FRAME_IMAGE = new Image("/resources/images/backgrounds/frame_4-3@4000x3000.png", width, 0, true, false);
					CHALKBOARD_BACKGROUND_IMAGE = new Image("/resources/images/backgrounds/chalkboardBackground_4-3@4000x3000.jpg", width, 0, true, false);
					if(new Random().nextBoolean())
					{
						WORLD_MAP = new Image("/resources/images/backgrounds/worldMap1_4-3@4000x3000.jpg", width, 0, true, false);
						worldMapLayoutX = 0.0240; worldMapLayoutY = 0.0352; worldMapFitWidth = 0.9507; worldMapFitHeight = 0.9343;
					}
					else
					{
						WORLD_MAP = new Image("/resources/images/backgrounds/worldMap2_4-3@4000x3000.jpg", width, 0, true, false);
						worldMapLayoutX = 0.0250; worldMapLayoutY = 0.0324; worldMapFitWidth = 0.9521; worldMapFitHeight = 0.9371;
					}
				}
			}
			
			if(primaryScreenHeight <= 950)
			{
				MOVING_EARTH_IMAGE_1 = new Image("/resources/images/backgrounds/doubleEarthMap1@3461x900.jpg", 0, primaryScreenHeight, true, false);
				MOVING_EARTH_IMAGE_2 = new Image("/resources/images/backgrounds/doubleEarthMap2@3461x900.jpg", 0, primaryScreenHeight, true, false);
			}
			else if(primaryScreenHeight <= 1500)
			{
				MOVING_EARTH_IMAGE_1 = new Image("/resources/images/backgrounds/doubleEarthMap1@5538x1440.jpg", 0, primaryScreenHeight, true, false);
				MOVING_EARTH_IMAGE_2 = new Image("/resources/images/backgrounds/doubleEarthMap2@5538x1440.jpg", 0, primaryScreenHeight, true, false);
			}
			else
			{
				MOVING_EARTH_IMAGE_1 = new Image("/resources/images/backgrounds/doubleEarthMap1@10000x2600.jpg", 0, primaryScreenHeight, true, false);
				MOVING_EARTH_IMAGE_2 = new Image("/resources/images/backgrounds/doubleEarthMap2@10000x2600.jpg", 0, primaryScreenHeight, true, false);
			}
		}
		catch(Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load screen dependent images", e));
		}
	}
	
	private static void powerOnAnimatedGlobeImages()
	{
		if(animationsUsed == ALL_ANIMATIONS)
		{
			animatedGlobe = new Image[80];
			
			if(primaryScreenWidth < 2250) for(int i = 1; i <= 80; i++) animatedGlobe[i - 1] = new Image("/resources/images/globes/x400/" + i + ".png", 0.18 * primaryScreenWidth, 0, true, false);
			else for(int i = 1; i <= 80; i++) animatedGlobe[i - 1] = new Image("/resources/images/globes/x850/" + i + ".png", 0.18 * primaryScreenWidth, 0, true, false);
		}
		else
		{
			animatedGlobe = new Image[1];
			if(primaryScreenWidth < 2250) animatedGlobe[0] = new Image("/resources/images/globes/x400/1.png", 0.18 * primaryScreenWidth, 0, true, false);
			else animatedGlobe[0] = new Image("/resources/images/globes/x850/1.png", 0.18 * primaryScreenWidth, 0, true, false);
		}
	}
	
	static void powerOnSplashScreen()
	{
		try
		{
			double width;
			if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_9 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_10)
				width = 0.75 * primaryScreenWidth;
			else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_25_16 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_3_2) width = 0.8 * primaryScreenWidth;
			else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_4_3 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_5_4)
				width = 0.9 * primaryScreenWidth;
			else width = 0.85 * primaryScreenWidth;
			
			if(primaryScreenWidth <= 1920) SPLASH_BACKGROUND_IMAGE = new Image("/resources/images/backgrounds/splashImage@1920x1080.png", width, 0, true, false);
			else if(primaryScreenWidth <= 2560) SPLASH_BACKGROUND_IMAGE = new Image("/resources/images/backgrounds/splashImage@2560x1440.png", width, 0, true, false);
			else SPLASH_BACKGROUND_IMAGE = new Image("/resources/images/backgrounds/splashImage@3072x1728.png", width, 0, true, false);
			
			if(getCurrentLanguage() == LANGUAGE_GREEK) SPLASH_TEXT_IMAGE = new Image("/resources/images/backgrounds/splashImageGreekName.png", 0.7 * width, 0, true, false);
			else SPLASH_TEXT_IMAGE = new Image("/resources/images/backgrounds/splashImageEnglishName.png", 0.7 * width, 0, true, false);
			
			splashScreen = new SplashScreen();
		}
		catch(Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to display the Splash Screen", e));
		}
	}
	
	static void powerOnWelcomeScreen()
	{
		try
		{
			welcomeScreen = new WelcomeScreen();
		}
		catch(Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load the Welcome Screen", e));
		}
	}
	
	static void powerOnGamePropertiesScreen()
	{
		try
		{
			gamePropertiesScreen = new GamePropertiesScreen();
		}
		catch(Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load the \"Difficulty and Question Categories Selection\" Screen", e));
		}
	}
	
	static void powerOnGameScreen()
	{
		try
		{
			gameScreen = new GameScreen();
		}
		catch(Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load the Game Screen", e));
		}
	}
	
	static void powerOnAtlasScreen()
	{
		try
		{
			atlasScreen = new AtlasScreen();
		}
		catch(Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load Atlas Screen", e));
		}
	}
	
	static void powerOnScoreBoardScreen()
	{
		try
		{
			scoreBoardScreen = new ScoreBoardScreen();
		}
		catch(Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load ScoreBoard Screen", e));
		}
	}
	
	static void powerOnStringData()
	{
		Thread t1 = new Thread(() ->
		{
			countries = new Country[NUM_ALL_COUNTRIES];
			readCountriesXMLDataFile();
			
			continents = new Continent[NUM_ALL_CONTINENTS];
			readContinentsXMLDataFile();
			
			attractions = new Attraction[GlobalVariables.NUM_ALL_ATTRACTIONS];
			readAttractionsXMLDataFile();

//			int c= 0;
//			ArrayList<Integer> index = new ArrayList<>();
//			ArrayList<Float> area = new ArrayList<>();
//
//			Locale[] tempLocale = Locale.getAvailableLocales();
//
//			for(int i = 0; i < tempLocale.length; i++)
//			{
//				System.out.println(tempLocale[i].getDisplayCountry() + "\t" + tempLocale[i].getLanguage());
//			}
//
//			int j;
//			boolean flag = true;   // set flag to true to begin first pass
//			float temp;
//			int temp1;
//
//			while ( flag )
//			{
//				flag = false;    //set flag to false awaiting a possible swap
//				for( j=0;  j < area.size() - 1;  j++ )
//				{
//					if ( area.get(j) < area.get(j + 1) )   // change to > for ascending sort
//					{
//						temp = area.get(j);
//						area.set(j, area.get(j + 1));
//						area.set(j + 1, temp);
//
//						temp1 = index.get(j);
//						index.set(j, index.get(j + 1));
//						index.set(j + 1, temp1);
//
//						flag = true;
//					}
//				}
//			}
//
//			for(int i = 0; i < index.size(); i++)
//			{
//				System.out.println(index.get(i));
//			}
//
//			for(int m = 0; m < countries.length; m++)
//			{
//				if(countries[m].isSovereignState())
//				{
//					for(int k = 0; k < index.size(); k++)
//					{
//						if(index.get(k) == m)
//						{
//							countries[m].getPopulation().setGlobalRanking(k + 1);
//							break;
//						}
//					}
//				}
//				else countries[m].getPopulation().setGlobalRanking(0);
//			}
//
//			System.out.println(c);
//			Scanner input = null;
//			int counter = 0;
//			String[] attr = new String[attractions.length];
//			try
//			{
//				input = new Scanner(PowerOn.class.getResourceAsStream("/resources/dataFiles/temp.txt"), "UTF-8");
//
//				while(input.hasNext())
//				{
//					attr[counter] = input.nextLine();
//					counter++;
//				}
//				input.close();
//			}
//			catch(Exception e)
//			{
//				if(input != null) input.close();
//
//				Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load data for dependent states", e));
//			}
////
//			try
//			{
//				SAXBuilder b   = new SAXBuilder();
//				Document doc = b.build(FilesIO.class.getResourceAsStream("/resources/dataFiles/countries.xml"));
//
//				Element root = doc.getRootElement();
//
//				List list = root.getChildren();
//
//				for(int i = 0; i < list.size(); i++)
//				{
//					Element country = ((Element) list.get(i));
//
//					Element otherValues = country.getChild("otherValues");
//
//					otherValues.addContent(new Element("localeLanguageCode").setText("0"));
//				}
//
//				XMLOutputter xmlOutput = new XMLOutputter();
//				xmlOutput.setFormat(Format.getPrettyFormat().setIndent("\t"));
//				xmlOutput.output(doc, new OutputStreamWriter(new FileOutputStream(new File("countries.xml"), false), "UTF-8"));
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
		});
		
		Thread t2 = new Thread(() ->
		{
			statesOfUSA = new StateOfUSA[NUM_ALL_USA_STATES];
			readStatesOfUSAXMLDataFile();
			
			greekDecAdm = new GreekDecentralizedAdministration[NUM_ALL_GREEK_DEC_ADMIN];
			readGreekDecentralizedAdministrationsXMLDataFile();
			
			greekRegions = new GreekRegion[NUM_ALL_GREEK_REGIONS];
			readGreekRegionsXMLDataFile();
			
			greekRegionalUnits = new GreekRegionalUnit[NUM_ALL_GREEK_REGIONAL_UNITS];
			readGreekRegionalUnitsXMLDataFile();

//			for(int i = 0; i < statesOfUSA.length; i++)
//			{
//				System.out.println(statesOfUSA[i].getNameInGreek());
//			}
//
//			try
//			{
//				SAXBuilder b   = new SAXBuilder();
//				Document   doc = b.build(FilesIO.class.getResourceAsStream("/resources/dataFiles/statesOfUSA.xml"));
//
//				Element root = doc.getRootElement();
//
//				List list = root.getChildren();
//
//				for(int i = 0; i < list.size(); i++)
//				{
//					Element state = ((Element) list.get(i));
//
//					state.addContent(12, new Element("articleForCapital").setText("Το"));
//				}
//
//				XMLOutputter xmlOutput = new XMLOutputter();
//				xmlOutput.setFormat(Format.getPrettyFormat().setIndent("\t"));
//				xmlOutput.output(doc, new OutputStreamWriter(new FileOutputStream(new File("statesOfUSA.xml"), false), "UTF-8"));
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
		});
		
		t1.start();
		t2.start();
		
		try
		{
			t1.join();
			t2.join();
		}
		catch (Exception e){}
	}
}