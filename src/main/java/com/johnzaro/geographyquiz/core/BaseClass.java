package com.johnzaro.geographyquiz.core;

import com.johnzaro.geographyquiz.dataStructures.RatioProperties;
import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import javafx.application.Application;
import javafx.stage.Stage;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;
import static com.johnzaro.geographyquiz.core.PowerOn.*;

public class BaseClass extends Application
{
//	Boolean to store weather the game is already running
	private boolean isAlreadyRunning = false;
//	public static Thread t1, t2, t3, t4, t5;

//	main method: first method that runs, only used to call start method
	public static void main(String[] args)
	{
		launch(args);
	}

//	first method that starts the game
	public void start(Stage coreStage)
	{
		coreStage.getProperties().put("hostServices", this.getHostServices());
		
//		setup basic game variables and objects
		setupGameVariables();
		
//		load the fonts needed to correctly display texts
		loadFonts();
		
//		get the bounds of primary screen that is used in order to check if it is supported
		setupPrimaryScreenBounds();
		
//		pass the width and height of primary screen to method to set currentScreenRatioValue & enum
		setCurrentScreenRatio(primaryScreenWidth, primaryScreenHeight);
		
		if(!isCurrentScreenRatioSupported())
		{
//			follow steps to show the "display not supported" screen
//			use helping resourceBundle to get the system language
			loadLanguageResourceBundle(true);
			
//			setup the stage
			setupStage(coreStage);
			
//			setup the "display not supported" screen and show it
			powerOnMessageDisplayIsNotSupported(languageResourceBundle);
		}
		else
		{
//			enter here only if display resolution is supported

//			try to create .lock files to check if the game is already running
			tryToLockGameInstance();
			
//			setup the correct paths for all the files used by the game
			FilesIO.setupFiles();

//			use the files to load the settings or set default settings if not any
			loadPlayersDataAndSettings();
			
//			setup the stage
			setupStage(coreStage);
			
			if(isAlreadyRunning)
			{
//				setup "game is already running" screen and show it
				powerOnMessageGameIsAlreadyRunning();
			}
			else
			{
//				add shutdown hook that writes game settings before exiting game
				Runtime.getRuntime().addShutdownHook(shutdownHook);
				
//				load all data files
				powerOnStringData();

//				load basic images and sounds
				loadMedia();
				
				ratioProperties = new RatioProperties();
				FilesIO.loadRatioProperties();
				
				setWindowedModeValues();
				
				powerOnWelcomeScreen();
				
				if(welcomeScreen != null) welcomeScreen.showScreen(true);
				
				powerOnGamePropertiesScreen();
				powerOnGameScreen();
				powerOnAtlasScreen();
				powerOnScoreBoardScreen();
			}
		}
	}

	private void tryToLockGameInstance()
	{
//		try to create lock file and check if one is already there
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
}
