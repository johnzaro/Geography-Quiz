package com.johnzaro.geographyquiz.core;

//helper class to execute some last stuff needed before exiting the game that starts being executed before the game is closed
public class ShutdownHook extends Thread
{
	public void run()
	{
		FilesIO.writePlayersFile();
//		FilesIO.writeGameScores();
	}
}