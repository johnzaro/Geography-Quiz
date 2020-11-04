package com.johnzaro.geographyquiz.core;

import com.johnzaro.geographyquiz.core.helperClasses.ShutdownHook;
import com.johnzaro.geographyquiz.dataStructures.RatioProperties;
import com.johnzaro.geographyquiz.screens.WelcomeScreen;
import com.johnzaro.geographyquiz.screens.errorScreens.ErrorScreen;
import com.johnzaro.geographyquiz.screens.errorScreens.MessageDisplayResolutionNotSupportedScreen;
import com.johnzaro.geographyquiz.screens.errorScreens.MessageGameIsAlreadyRunningScreen;
import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.ExecutionException;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;
import static com.johnzaro.geographyquiz.core.PowerOn.*;

public class BaseClass extends Application
{
	private boolean isAlreadyRunning = false;
	
	private MessageDisplayResolutionNotSupportedScreen messageDisplayResolutionNotSupportedScreen;
	private MessageGameIsAlreadyRunningScreen messageGameIsAlreadyRunningScreen;
	
	//	main method: first method that runs, only used to call start method
	public static void main(String[] args)
	{
		System.out.println(System.currentTimeMillis()%100000);
		System.setProperty("javafx.preloader", "com.johnzaro.geographyquiz.core.Splash");
		Application.launch(BaseClass.class, args);
	}
	
	public void init()
	{
		setupGameVariables();
		audioStuff = new AudioStuff();
		
		if(!getScreenStuff().isCurrentScreenRatioSupported())
			messageDisplayResolutionNotSupportedScreen = new MessageDisplayResolutionNotSupportedScreen();
		else
		{
			tryToLockGameInstance();
			
			if(isAlreadyRunning)
				messageGameIsAlreadyRunningScreen = new MessageGameIsAlreadyRunningScreen();
			else
			{
				FilesIO.setupFiles();
				loadPlayersDataAndSettings();
				
				imageStuff = new ImageStuff();
				imageStuff.registerImages();
				imageStuff.loadImages();
				imageStuff.loadAnimatedGlobeImages();
				
				powerOnStringData();
				loadQuestionsResourceBundle(getCurrentLanguage());
				audioStuff.loadAudio();
				
				ratioProperties = new RatioProperties();
				FilesIO.loadRatioProperties();
				
				welcomeScreen = new WelcomeScreen();
				powerOnGamePropertiesScreen();
				powerOnGameScreen();
				powerOnAtlasScreen();
				powerOnScoreBoardScreen();
				
				try
				{
					ImageStuff.imagesLoadedFuture.get();
				}
				catch(InterruptedException | ExecutionException e)
				{ Platform.runLater(() -> new ErrorScreen("Error occurred while waiting for images to load", e)); }
				
				getImageStuff().setMovingEarthImageViewportProperties();
				if(animationsUsed == ANIMATIONS.ALL)
				{
					gamePropertiesScreen.setupAdvancedAnimations();
					atlasScreen.setupAdvancedAnimations();
					scoreBoardScreen.setupAdvancedAnimations();
				}
			}
		}
	}

//	first method that starts the game
	public void start(Stage coreStage)
	{
		stage = coreStage;
		
		if(!getScreenStuff().isCurrentScreenRatioSupported())
			messageDisplayResolutionNotSupportedScreen.setupAndShowStage();
		else if(isAlreadyRunning)
			messageGameIsAlreadyRunningScreen.setupAndShowStage();
		else
		{
			setupStage();
			
			setWindowedModeValues();
			if(getCurrentPlayer().getStartAtFullScreen()) stage.setFullScreen(true);
			
			welcomeScreen.createScene();
			System.out.println(System.currentTimeMillis()%100000);
			welcomeScreen.showScreen();
		}
	}

	private void tryToLockGameInstance()
	{
		try
		{
			JUnique.acquireLock("com.johnzaro.geographyquiz.core.BaseClass");
			isAlreadyRunning = false;
		}
		catch(AlreadyLockedException e)
		{
			isAlreadyRunning = true;
		}
	}
	
	private void setupStage()
	{
		stage.setMinWidth(getScreenStuff().getMinStageWidth());
		
		stage.setTitle(languageResourceBundle.getString("gameName"));
		
		stage.getProperties().put("hostServices", this.getHostServices());
		clipboard = Clipboard.getSystemClipboard();
		clipboardContent = new ClipboardContent();
		Runtime.getRuntime().addShutdownHook(new ShutdownHook());
		
		stage.fullScreenProperty().addListener((observable, oldValue, newValue) ->
		{
			if(newValue != oldValue && newValue)
			{
				double newWidth, newHeight;
				ScreenStuff.SUPPORTED_SCREEN_RATIOS previousScreenRatio;
				ObservableList<Screen> screens = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
				
				newWidth = screens.get(0).getBounds().getWidth();
				newHeight = screens.get(0).getBounds().getHeight();
				
				previousScreenRatio = getScreenStuff().getCurrentScreenRatioEnum();
				getScreenStuff().setCurrentScreenRatio(newWidth, newHeight);
				if(previousScreenRatio != getScreenStuff().getCurrentScreenRatioEnum())
				{
					getScreenStuff().setPrimaryScreenWidth(newWidth);
					getScreenStuff().setPrimaryScreenHeight(newHeight);
					
					stage.setMinWidth(getScreenStuff().getMinStageWidth());
					
					imageStuff.registerImages();
					imageStuff.loadImages();
					imageStuff.loadAnimatedGlobeImages();
					
					try { getImageStuff().imagesLoadedFuture.get(); }
					catch(InterruptedException | ExecutionException e)
					{ Platform.runLater(() -> new ErrorScreen("Error occurred while waiting for images to load", e)); }
					
					if(animationsUsed == ANIMATIONS.ALL)
					{
						getImageStuff().setMovingEarthImageViewportProperties();
						gamePropertiesScreen.setupAdvancedAnimations();
						atlasScreen.setupAdvancedAnimations();
						scoreBoardScreen.setupAdvancedAnimations();
					}
				}
			}
		});
		
		stage.focusedProperty().addListener((observable, oldValue, newValue) ->
		{
			if(newValue)
			{
				if (animationsUsed == ANIMATIONS.ALL)
				{
//					if it has no focus and gains it back and animations are enabled -> play them
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
					gameScreen.resumeComboAnimation();
					gameScreen.resumeTimelineFor2_5SecondsWait();
				}
			}
			else
			{
				if (animationsUsed == ANIMATIONS.ALL)
				{
//					if it has focus and loses it and uses animations -> pause them
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
					gameScreen.pauseComboAnimation();
					gameScreen.pauseTimelineFor2_5SecondsWait();
				}
			}
		});
		
		stage.iconifiedProperty().addListener((observable, oldValue, newValue) ->
		{
			if(newValue)
			{
				if(getAudioStuff().isIntroductionSoundPlaying()) getAudioStuff().pauseIntroductionSound();
				else if(getAudioStuff().isWelcomeLoopSoundPlaying()) getAudioStuff().pauseWelcomeLoopSound();
			}
			else
			{
				if(getAudioStuff().isIntroductionSoundPaused()) getAudioStuff().playIntroductionSound();
				else if(getAudioStuff().isWelcomeLoopSoundPaused()) getAudioStuff().playWelcomeLoopSoundSound();
			}
		});
		
		stage.setOnCloseRequest(e ->
		{
			if(scoreBoardScreen != null &&
				mainScene.getRoot() == scoreBoardScreen.getAnchorPane() &&
				scoreBoardScreen.getPopOver() != null &&
				scoreBoardScreen.getPopOver().isShowing())
					scoreBoardScreen.getPopOver().hide(Duration.millis(0));
		});
	}
}
