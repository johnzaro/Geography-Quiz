package com.johnzaro.geographyquiz.core;

import javafx.application.HostServices;

/**
 * Created by John on 28/6/2016.
 */

//	helper class used to open websites in the default browser of the computer
public class UrlOpener
{
	public static void openURL(String url)
	{
		((HostServices)GlobalVariables.stage.getProperties().get("hostServices")).showDocument(url);
	}
}
