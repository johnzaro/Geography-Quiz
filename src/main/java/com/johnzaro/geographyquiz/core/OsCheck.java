package com.johnzaro.geographyquiz.core;

import java.util.Locale;

//helper class that is used to check in which OS the game has started
public class OsCheck
{
//	enumeration to hold all the different possible OS's
	public enum OSType
	{
		Windows, MacOS, Linux, Other
	}

	private static OSType detectedOS;

//	method to find the OS of the computer
	public static OSType getOperatingSystemType()
	{
		if (detectedOS == null)
		{
//			get system variable that holds OS name and save it to "OS" variable
			String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
//			check the string for all the possible OS names
			if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) detectedOS = OSType.MacOS;
			else if (OS.indexOf("win") >= 0) detectedOS = OSType.Windows;
			else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0)
				detectedOS = OSType.Linux;
			else detectedOS = OSType.Other;
		}
		return detectedOS;
	}
}