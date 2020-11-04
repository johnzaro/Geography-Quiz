package com.johnzaro.geographyquiz.core;

import com.johnzaro.geographyquiz.core.helperClasses.OsCheck;
import com.johnzaro.geographyquiz.screens.AtlasScreen;
import com.johnzaro.geographyquiz.screens.GamePropertiesScreen;
import com.johnzaro.geographyquiz.screens.GameScreen;
import com.johnzaro.geographyquiz.screens.ScoreBoardScreen;
import com.johnzaro.geographyquiz.screens.errorScreens.ErrorScreen;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Screen;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.johnzaro.geographyquiz.core.FilesIO.*;
import static com.johnzaro.geographyquiz.core.GlobalVariables.*;

//helper class that contains all methods used when the game boots up
public class PowerOn
{
	static void setupOSType()
	{
		OS = OsCheck.getOperatingSystemType();
		GAME_DATA_PATH = getGameDataPath(OS);
	}
	
	static void setupGameVariables()
	{
		email = "johnzrgnns@gmail.com";
		
		decimalFormatForSaving = ((DecimalFormat) NumberFormat.getNumberInstance(Locale.US));
		decimalFormatForSaving.applyPattern("#.####");
		
		dateTimeFormatForSaving = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
		dateFormatForSaving = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		playersArrayList = new ArrayList<>();
		games = new ArrayList<>();
	}
	
	static void loadFonts()
	{
		try
		{
			Font.loadFont(PowerOn.class.getResourceAsStream("/fonts/comicSansMSRegular.ttf"), 100);
			Font.loadFont(PowerOn.class.getResourceAsStream("/fonts/comicSansMSBold.ttf"), 100);
		}
		catch(Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load fonts", e));
		}
	}
	
	static void setupPrimaryScreenBounds()
	{
		getScreenStuff().setPrimaryScreenResolution(Screen.getPrimary().getBounds());
		getScreenStuff().setPrimaryScreenWidth(getScreenStuff().getPrimaryScreenResolution().getWidth());
		getScreenStuff().setPrimaryScreenHeight(getScreenStuff().getPrimaryScreenResolution().getHeight());
	}
	
	static void loadPlayersDataAndSettings()
	{
		if(!isEmpty(playersFile)) readPlayersFile();
		else setDefaultPlayerName();
		
		setSettingsBasedOnCurrentPlayer();
		
		if(!isEmpty(gamesScoresFile)) readGamesScores();
	}
	
	public static void loadLanguageResourceBundle(LANGUAGE language)
	{
		if(language == LANGUAGE.GREEK) languageResourceBundle = ResourceBundle.getBundle("lang.GeoQuiz", new Locale("el"));
		else languageResourceBundle = ResourceBundle.getBundle("lang.GeoQuiz", new Locale("en"));
	}
	
	public static void loadQuestionsResourceBundle(LANGUAGE language)
	{
		if(language == LANGUAGE.GREEK) questionsResourceBundle = ResourceBundle.getBundle("lang.questions", new Locale("el"));
		else questionsResourceBundle = ResourceBundle.getBundle("lang.questions", new Locale("en"));
	}
	
	public static void setSettingsBasedOnCurrentPlayer()
	{
		setNumberAndDateFormats(getCurrentPlayer().getLocale());
		
		audioStuff.setMasterSliderVolume(getCurrentPlayer().getMasterSliderVolume());
		audioStuff.setMusicSliderVolume(getCurrentPlayer().getMusicSliderVolume());
		audioStuff.setSoundEffectsSliderVolume(getCurrentPlayer().getSoundEffectsSliderVolume());
		animationsUsed = getCurrentPlayer().getAnimationsUsed();
		
		getScreenStuff().setWindowWidth(getCurrentPlayer().getWindowWidth());
		getScreenStuff().setWindowHeight(getScreenStuff().getHeightBasedOnWidth(getScreenStuff().getWindowWidth()));
	}
	
	public static void setNumberAndDateFormats(Locale locale)
	{
		numberFormatForUI = NumberFormat.getNumberInstance(locale);
		numberFormatForUI.setGroupingUsed(true);
		numberFormatForUI.setMaximumFractionDigits(2);
		
		dateTimeForUI = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(locale);
		dateForUI = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(locale);
		
	}
	
	static void setWindowedModeValues()
	{
		stage.setX(getScreenStuff().getPrimaryScreenResolution().getMinX() + getScreenStuff().getPrimaryScreenWidth() / 2.0 - getScreenStuff().getWindowWidth() / 2.0);
		stage.setY(getScreenStuff().getPrimaryScreenResolution().getMinY() + getScreenStuff().getPrimaryScreenHeight() / 2.0 - getScreenStuff().getWindowHeight() / 2.0);
		stage.setWidth(getScreenStuff().getWindowWidth());
		stage.setHeight(getScreenStuff().getWindowHeight());
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
			readCountriesDataFile();
			readContinentsDataFile();
			readAttractionsDataFile();

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
//				input = new Scanner(PowerOn.class.getResourceAsStream(PowerOn.class.getResource("/dataFiles/temp.txt"), "UTF-8");
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
//				Document doc = b.build(FilesIO.class.getResourceAsStream(PowerOn.class.getResource("/dataFiles/countries.xml"));
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
			readStatesOfUSADataFile();
			readGreekDecentralizedAdministrationsDataFile();
			readGreekRegionsDataFile();
			readGreekRegionalUnitsDataFile();
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