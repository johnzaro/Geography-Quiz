package com.johnzaro.geographyquiz.core.helperClasses;

import com.johnzaro.geographyquiz.core.GlobalVariables;
import com.johnzaro.geographyquiz.screens.errorScreens.ErrorScreen;
import javafx.application.HostServices;
import javafx.application.Platform;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by John on 28/6/2016.
 */

//	helper class used to open websites in the default browser of the computer
public class UrlOpener
{
	public static void openURL(String url)
	{
		((HostServices) GlobalVariables.stage.getProperties().get("hostServices")).showDocument(url);
	}
	
	public static void openGreekWikiURL(String url)
	{
		try
		{
			String first = url.substring(0, 29);
			String last = url.substring(29);
			
			((HostServices)GlobalVariables.stage.getProperties().get("hostServices")).showDocument(first + URLEncoder.encode(last, "UTF-8"));
		}
		catch(UnsupportedEncodingException e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to open greek wikipedia URL", e));
		}
	}
	
	public static void openGoogleMapsLink(String coordinates)
	{
		String[] latLong = coordinates.split(" ");
		
		if(latLong[0].endsWith("S")) latLong[0] = "-" + latLong[0];
		if(latLong[1].endsWith("W")) latLong[1] = "-" + latLong[1];
		
		latLong[0] = latLong[0].substring(0, latLong[0].length() - 3);
		latLong[1] = latLong[1].substring(0, latLong[1].length() - 3);
		
		((HostServices)GlobalVariables.stage.getProperties().get("hostServices")).showDocument(String.format("http://www.google.com/maps/search/?api=1&query=%s,%s", latLong[0], latLong[1]));
	}
}
