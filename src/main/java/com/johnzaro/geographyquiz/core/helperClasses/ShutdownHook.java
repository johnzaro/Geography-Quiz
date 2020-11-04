package com.johnzaro.geographyquiz.core.helperClasses;

import com.johnzaro.geographyquiz.core.FilesIO;
import com.johnzaro.geographyquiz.core.GlobalVariables;
import javafx.util.Duration;

//helper class to execute some last stuff needed before exiting the game that starts being executed before the game is closed
public class ShutdownHook extends Thread
{
	public void run()
	{
		FilesIO.writePlayersFile();
		FilesIO.writeCurrentLanguage(GlobalVariables.getCurrentLanguage());
	}
}