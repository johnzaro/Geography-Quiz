package core;

import screens.ErrorScreen;
import javafx.application.Platform;

import java.io.IOException;
import java.net.URI;
import java.awt.Desktop;
import java.net.URISyntaxException;

/**
 * Created by John on 28/6/2016.
 */

//	helper class used to open websites in the default browser of the computer
public class UrlOpener
{
	public static void openURL(String url)
	{
		try
		{
			Desktop.getDesktop().browse(new URI(url));
		}
		catch (IOException | URISyntaxException e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to open the website: " + url, e));
		}
	}
}
