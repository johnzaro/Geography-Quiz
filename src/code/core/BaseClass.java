package code.core;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import javafx.application.Application;
import javafx.stage.Stage;

import static code.core.GlobalVariables.*;
import static code.core.PowerOn.*;

public class BaseClass extends Application
{
//	Boolean to store weather the game is already running
	private boolean isAlreadyRunning = false;
	public static Thread t1, t2, t3, t4, t5;

//	main method: first method that runs, only used to call start method
	public static void main(String[] args)
	{
		launch(args);
	}

//	first method that starts the game
	public void start(Stage coreStage) throws InterruptedException
	{
//		setup basic game variables and objects
		setupGameVariables();
		
//		load the fonts needed to correctly display texts
		loadFonts();
		
//		get the bounds of primary screen that is used in order to check if it is supported
		setupPrimaryScreenBounds();
		
//		pass the width and height of primary screen to method that returns boolean if it is supported
		boolean displayResolutionIsSupported = setCurrentScreenRatio(primaryScreenWidth, primaryScreenHeight);
//		if(!displayResolutionIsSupported)
		if(false)
		{
//			follow steps to show the "display not supported" screen
//			use helping resourceBundle to get the system language
			setDefaultLanguage();
			
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
			loadSettings();

//			setup the stage
			setupStage(coreStage);
			
			if(isAlreadyRunning)
			{
//				setup "game is already running" screen and show it
				powerOnMessageGameIsAlreadyRunning();
			}
			else
			{
//				power on and show splash screen
				powerOnSplashScreen();
				
//				add shutdown hook that writes game settings before exiting game
				Runtime.getRuntime().addShutdownHook(shutdownHook);

//				load basic images and sounds
				powerOnMedia();

//				setup various screens
				t1 = new Thread(() -> powerOnWelcomeScreen());
				t2 = new Thread(() -> powerOnGamePropertiesScreen());
				t3 = new Thread(() -> powerOnGameScreen());
				t4 = new Thread(() -> powerOnAtlasScreen());
				t5 = new Thread(() -> powerOnScoreBoardScreen());
				
				t1.start();
				t2.start();
				t3.start();
				t4.start();
				t5.start();
				
//				load all data files
				powerOnStringData();
			}
		}
	}

	private void tryToLockGameInstance()
	{
//		try to create lock file and check if one is already there
		try
		{
			JUnique.acquireLock("code.core.BaseClass");
			isAlreadyRunning = false;
		}
		catch(AlreadyLockedException e)
		{
			isAlreadyRunning = true;
		}
	}
}
