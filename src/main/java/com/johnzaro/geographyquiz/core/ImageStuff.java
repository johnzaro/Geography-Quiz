package com.johnzaro.geographyquiz.core;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;
import static com.johnzaro.geographyquiz.core.GlobalVariables.animationsUsed;

public class ImageStuff
{
	private String[] requireWhiteBackground = {"India", "American Samoa", "Austria", "Iran", "Cameroon", "Gabon", "Kenya",
			"Comoros", "Cyprus", "Lesotho", "Latvia", "New Caledonia", "Pakistan", "Palau", "Swaziland", "Sudan", "South Africa"};
	
	public enum Images {ADD_PLAYER, GLOBE, BACK_ARROW, CHALKBOARD_BACKGROUND, CORRECT_BIG, CORRECT_SMALL,
		EDIT_PLAYER_NAME, EMPTY_WOOD_BACKGROUND_BIG_ROPE, EMPTY_WOOD_BACKGROUND_SMALL_ROPE, ENGLISH_FLAG,
		FRAME, GAME_NAME, GLOBE_STAND, GREEK_FLAG, HEART_BIG, HEART_BROKEN_BIG, HEART_LOST_SMALL,
		HEART_SMALL, INCORRECT_BIG, INCORRECT_SMALL, INFO_CLICKED, INFO, MOVING_EARTH_1, MOVING_EARTH_2,
		MULTI_PLAYER, PAUSE_CLICKED, PAUSE, PENCIL_DISABLED, PENCIL, PROGRESS_COLOR, SCORES,
		SETTINGS_CLICKED, SETTINGS, SINGLE_PLAYER, SOUND_OFF_CLICKED, SOUND_OFF, SOUND_ON_1_CLICKED,
		SOUND_ON_1, SOUND_ON_2_CLICKED, SOUND_ON_2, SOUND_ON_3_CLICKED, SOUND_ON_3,
		SWITCH_PLAYER, EMPTY_WOOD_BACKGROUND_1_ICON, WORLD_MAP, X_CLICKED, X, ZOOM_IN_CLICKED, ZOOM_IN,
		ZOOM_OUT_CLICKED, ZOOM_OUT}
	
	public enum ImageType
	{
		COUNTRY_FLAG, COUNTRY_COAT_OF_ARMS, COUNTRY_LOCATION, CONTINENT_LOCATION, USA_FLAG, USA_SEAL, USA_LOCATION, GREECE_DEC_ADM_LOGO, GREECE_DEC_ADM_LOCATION,
		GREECE_REGION_LOGO, GREECE_REGION_LOCATION, GREECE_REGIONAL_UNIT_LOCATION, ATTRACTION_PHOTO, ATTRACTION_LOCATION
	}
	
	public final int MILLIS_FOR_24FPS_ANIMATION = 42;
	public final int NUMBER_OF_ANIMATED_GLOBES = 359;
	
	public final int MAX_WIDTH_FOR_X250_IMAGES = 275, MAX_WIDTH_FOR_X500_IMAGES = 550, MAX_WIDTH_FOR_X1000_IMAGES = 1100;
	
	private double worldMapLayoutX, worldMapLayoutY, worldMapFitWidth, worldMapFitHeight;
	private Rectangle2D[] movingEarthImageViewports;
	private double viewPortWidth, viewPortHeight, movingImageHalfWidth;
	
	private Map<Images, CustomImage> images;
	private CustomImage[] animatedGlobe;
	
	private static int registeredImages;
	private static int readyImages;
	
	static CompletableFuture<String> imagesLoadedFuture;
	
	public ImageStuff()
	{
		new Thread(() ->
		{
			for(int i = 0; i < 5; i++)
			{
				if(images != null)
				{
					for(Images image: images.keySet())
					{
						System.out.println(images.get(image).getProgress() + "\t" + image.toString());
					}
				}
				if(animatedGlobe != null) System.out.println(animatedGlobe[0].getProgress());
				System.out.println("----------------------------------------");
				
				try
				{
					Thread.sleep(1000);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	void registerImages()
	{
		initRegisteredImages();
		
		registerImage(Images.values().length);
		if(animationsUsed == ANIMATIONS.ALL) registerImage(NUMBER_OF_ANIMATED_GLOBES);
		else registerImage(1);
	}
	
	void loadImages()
	{
		images = new EnumMap<>(Images.class);
		
		initReadyImages();
		imagesLoadedFuture = new CompletableFuture<>();
		
		double primaryScreenWidth = getScreenStuff().getPrimaryScreenWidth();
		double iconSize = Math.min(200, 0.04 * primaryScreenWidth);
		double heightForBigImage = Math.min(2000, 0.6 * getScreenStuff().getPrimaryScreenHeight());
		
		StringBuilder sb = new StringBuilder();
		if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_16_9)
		{
			sb.append("_16-9@");
			if(primaryScreenWidth <= 1920)
			{
				sb.append("1920x1080");
				worldMapLayoutX = 0.0271; worldMapLayoutY = 0.0481; worldMapFitWidth = 0.9469; worldMapFitHeight = 0.9037;
			}
			else if(primaryScreenWidth <= 2560)
			{
				sb.append("2560x1440");
				worldMapLayoutX = 0.0266; worldMapLayoutY = 0.0444; worldMapFitWidth = 0.9510; worldMapFitHeight = 0.9120;
			}
			else
			{
				sb.append("5120x2880");
				worldMapLayoutX = 0.0250; worldMapLayoutY = 0.0444; worldMapFitWidth = 0.9505; worldMapFitHeight = 0.9167;
			}
		}
		else if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_16_10 ||
				getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_25_16 ||
				getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_3_2)
		{
			sb.append("_16-10@");
			if(primaryScreenWidth <= 1440)
			{
				sb.append("1440x900");
				worldMapLayoutX = 0.0281; worldMapLayoutY = 0.0508; worldMapFitWidth = 0.9458; worldMapFitHeight = 0.9000;
			}
			else if(primaryScreenWidth <= 1920)
			{
				sb.append("1920x1200");
				worldMapLayoutX = 0.0271; worldMapLayoutY = 0.0500; worldMapFitWidth = 0.9479; worldMapFitHeight = 0.9025;
			}
			else
			{
				sb.append("2880x1800");
				worldMapLayoutX = 0.0240; worldMapLayoutY = 0.0442; worldMapFitWidth = 0.9531; worldMapFitHeight = 0.9150;
			}
		}
		else if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_4_3 ||
				getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_5_4)
		{
			if(new Random().nextBoolean())
			{
				sb.append("1");
				worldMapLayoutX = 0.0240; worldMapLayoutY = 0.0352; worldMapFitWidth = 0.9507; worldMapFitHeight = 0.9343;
			}
			else
			{
				sb.append("2");
				worldMapLayoutX = 0.0250; worldMapLayoutY = 0.0324; worldMapFitWidth = 0.9521; worldMapFitHeight = 0.9371;
			}
			sb.append("_4-3@");
			if(primaryScreenWidth <= 1400) sb.append("1400x1050");
			else if(primaryScreenWidth <= 2048) sb.append("2048x1536");
			else sb.append("4000x3000");
		}
		
		images.put(Images.FRAME, new CustomImage(getClass().getResource("/images/backgrounds/frame" + sb.toString() + ".png").toExternalForm(), primaryScreenWidth, 0));
		images.put(Images.CHALKBOARD_BACKGROUND, new CustomImage(getClass().getResource("/images/backgrounds/chalkboardBackground" + sb.toString() + ".jpg").toExternalForm(), primaryScreenWidth, 0));
		images.put(Images.WORLD_MAP, new CustomImage(getClass().getResource("/images/backgrounds/worldMap" + sb.toString() + ".jpg").toExternalForm(), primaryScreenWidth, 0));
		
		sb = new StringBuilder();
		if(getScreenStuff().getPrimaryScreenHeight() <= 950) sb.append("3461x900");
		else if(getScreenStuff().getPrimaryScreenHeight() <= 1500) sb.append("5538x1440");
		else sb.append("10000x2600");
		
		images.put(Images.MOVING_EARTH_1, new CustomImage(getClass().getResource("/images/backgrounds/doubleEarthMap1@" + sb.toString() + ".jpg").toExternalForm(), 0, getScreenStuff().getPrimaryScreenHeight()));
		images.put(Images.MOVING_EARTH_2, new CustomImage(getClass().getResource("/images/backgrounds/doubleEarthMap2@" + sb.toString() + ".jpg").toExternalForm(), 0, getScreenStuff().getPrimaryScreenHeight()));
		
		loadGameNameImage();
		
		images.put(Images.EMPTY_WOOD_BACKGROUND_SMALL_ROPE, new CustomImage(getClass().getResource("/images/backgrounds/emptyWoodBackgroundSmallRope.png").toExternalForm(), 0.55 * primaryScreenWidth, 0));
		images.put(Images.EMPTY_WOOD_BACKGROUND_BIG_ROPE, new CustomImage(getClass().getResource("/images/backgrounds/emptyWoodBackgroundBigRope.png").toExternalForm(), 0.55 * primaryScreenWidth, 0));
		images.put(Images.EMPTY_WOOD_BACKGROUND_1_ICON, new CustomImage(getClass().getResource("/images/backgrounds/emptyWoodBackground1Icon.png").toExternalForm(), 0.05 * primaryScreenWidth, 0));
		images.put(Images.GLOBE_STAND, new CustomImage(getClass().getResource("/images/backgrounds/globeStand.png").toExternalForm(), 0.18 * primaryScreenWidth, 0));
		images.put(Images.BACK_ARROW, new CustomImage(getClass().getResource("/images/icons/backArrow.png").toExternalForm(), 0.09 * primaryScreenWidth, 0));
		images.put(Images.GREEK_FLAG, new CustomImage(getClass().getResource("/images/icons/el.png").toExternalForm(), 0.03 * primaryScreenWidth, 0));
		images.put(Images.ENGLISH_FLAG, new CustomImage(getClass().getResource("/images/icons/en.png").toExternalForm(), 0.03 * primaryScreenWidth, 0));
		images.put(Images.SOUND_ON_3, new CustomImage(getClass().getResource("/images/icons/soundOn_3bars.png").toExternalForm(), iconSize, 0));
		images.put(Images.SOUND_ON_3_CLICKED, new CustomImage(getClass().getResource("/images/icons/soundOnClicked_3bars.png").toExternalForm(), iconSize, 0));
		images.put(Images.SOUND_ON_2, new CustomImage(getClass().getResource("/images/icons/soundOn_2bars.png").toExternalForm(), iconSize, 0));
		images.put(Images.SOUND_ON_2_CLICKED, new CustomImage(getClass().getResource("/images/icons/soundOnClicked_2bars.png").toExternalForm(), iconSize, 0));
		images.put(Images.SOUND_ON_1, new CustomImage(getClass().getResource("/images/icons/soundOn_1bar.png").toExternalForm(), iconSize, 0));
		images.put(Images.SOUND_ON_1_CLICKED, new CustomImage(getClass().getResource("/images/icons/soundOnClicked_1bar.png").toExternalForm(), iconSize, 0));
		images.put(Images.SOUND_OFF, new CustomImage(getClass().getResource("/images/icons/soundOff.png").toExternalForm(), iconSize, 0));
		images.put(Images.SOUND_OFF_CLICKED, new CustomImage(getClass().getResource("/images/icons/soundOffClicked.png").toExternalForm(), iconSize, 0));
		images.put(Images.SETTINGS, new CustomImage(getClass().getResource("/images/icons/settings.png").toExternalForm(), iconSize, 0));
		images.put(Images.SETTINGS_CLICKED, new CustomImage(getClass().getResource("/images/icons/settingsClicked.png").toExternalForm(), iconSize, 0));
		images.put(Images.X, new CustomImage(getClass().getResource("/images/icons/x.png").toExternalForm(), iconSize, 0));
		images.put(Images.X_CLICKED, new CustomImage(getClass().getResource("/images/icons/xClicked.png").toExternalForm(), iconSize, 0));
		images.put(Images.ZOOM_IN, new CustomImage(getClass().getResource("/images/icons/zoomIn.png").toExternalForm(), iconSize, 0));
		images.put(Images.ZOOM_IN_CLICKED, new CustomImage(getClass().getResource("/images/icons/zoomInClicked.png").toExternalForm(), iconSize, 0));
		images.put(Images.ZOOM_OUT, new CustomImage(getClass().getResource("/images/icons/zoomOut.png").toExternalForm(), iconSize, 0));
		images.put(Images.ZOOM_OUT_CLICKED, new CustomImage(getClass().getResource("/images/icons/zoomOutClicked.png").toExternalForm(), iconSize, 0));
		images.put(Images.PENCIL, new CustomImage(getClass().getResource("/images/icons/pencil.png").toExternalForm(), iconSize, 0));
		images.put(Images.PENCIL_DISABLED, new CustomImage(getClass().getResource("/images/icons/pencilDisabled.png").toExternalForm(), iconSize, 0));
		images.put(Images.EDIT_PLAYER_NAME, new CustomImage(getClass().getResource("/images/icons/editPlayerName.png").toExternalForm(), iconSize, 0));
		images.put(Images.SWITCH_PLAYER, new CustomImage(getClass().getResource("/images/icons/switchPlayer.png").toExternalForm(), iconSize, 0));
		images.put(Images.ADD_PLAYER, new CustomImage(getClass().getResource("/images/icons/addPlayer.png").toExternalForm(), iconSize, 0));
		images.put(Images.INFO, new CustomImage(getClass().getResource("/images/icons/info.png").toExternalForm(), iconSize, 0));
		images.put(Images.INFO_CLICKED, new CustomImage(getClass().getResource("/images/icons/infoClicked.png").toExternalForm(), iconSize, 0));
		images.put(Images.SINGLE_PLAYER, new CustomImage(getClass().getResource("/images/icons/singlePlayer.png").toExternalForm(), iconSize, 0));
		images.put(Images.MULTI_PLAYER, new CustomImage(getClass().getResource("/images/icons/multiPlayer.png").toExternalForm(), iconSize, 0));
		images.put(Images.GLOBE, new CustomImage(getClass().getResource("/images/icons/globe.png").toExternalForm(), iconSize, 0));
		images.put(Images.SCORES, new CustomImage(getClass().getResource("/images/icons/scores.png").toExternalForm(), iconSize, 0));
		images.put(Images.PAUSE, new CustomImage(getClass().getResource("/images/icons/pause.png").toExternalForm(), iconSize, 0));
		images.put(Images.PAUSE_CLICKED, new CustomImage(getClass().getResource("/images/icons/pauseClicked.png").toExternalForm(), iconSize, 0));
		images.put(Images.CORRECT_SMALL, new CustomImage(getClass().getResource("/images/icons/correctSmall.png").toExternalForm(), iconSize, 0));
		images.put(Images.INCORRECT_SMALL, new CustomImage(getClass().getResource("/images/icons/incorrectSmall.png").toExternalForm(), iconSize, 0));
		images.put(Images.HEART_SMALL, new CustomImage(getClass().getResource("/images/icons/heartSmall.png").toExternalForm(), iconSize, 0));
		images.put(Images.HEART_LOST_SMALL, new CustomImage(getClass().getResource("/images/icons/heartLostSmall.png").toExternalForm(), iconSize, 0));
		images.put(Images.PROGRESS_COLOR, new CustomImage(getClass().getResource("/images/icons/progressBarColor.jpg").toExternalForm(), true));
		images.put(Images.CORRECT_BIG, new CustomImage(getClass().getResource("/images/icons/correctBig.png").toExternalForm(), 0, heightForBigImage));
		images.put(Images.INCORRECT_BIG, new CustomImage(getClass().getResource("/images/icons/incorrectBig.png").toExternalForm(), 0, heightForBigImage));
		images.put(Images.HEART_BIG, new CustomImage(getClass().getResource("/images/icons/heartBig.png").toExternalForm(), 0, heightForBigImage));
		images.put(Images.HEART_BROKEN_BIG, new CustomImage(getClass().getResource("/images/icons/heartBrokenBig.png").toExternalForm(), 0, heightForBigImage));
	}
	
	public void loadGameNameImage()
	{
		if (getCurrentLanguage() == GlobalVariables.LANGUAGE.GREEK)
			images.put(Images.GAME_NAME, new CustomImage(getClass().getResource("/images/backgrounds/gameNameGreek.png").toExternalForm(), 0.65 * getScreenStuff().getPrimaryScreenWidth(), 0));
		else
			images.put(Images.GAME_NAME, new CustomImage(getClass().getResource("/images/backgrounds/gameNameEnglish.png").toExternalForm(), 0.65 * getScreenStuff().getPrimaryScreenWidth(), 0));
	}
	
	public void loadAnimatedGlobeImages()
	{
		int ANIMATED_GLOBE_WIDTH = 600;
		final double requestedWidth = Math.min(ANIMATED_GLOBE_WIDTH, getScreenStuff().getPrimaryScreenWidth() * 0.18);
		if(animationsUsed == ANIMATIONS.ALL)
		{
			animatedGlobe = new CustomImage[NUMBER_OF_ANIMATED_GLOBES];
			for(int i = 1; i <= NUMBER_OF_ANIMATED_GLOBES; i++)
				animatedGlobe[i - 1] = new CustomImage(getClass().getResource("/images/globes/x" + ANIMATED_GLOBE_WIDTH + "/" + i + ".png").toExternalForm(), requestedWidth, false);
		}
		else
		{
			animatedGlobe = new CustomImage[1];
			animatedGlobe[0] =
					new CustomImage(getClass().getResource("/images/globes/x" + ANIMATED_GLOBE_WIDTH + "/" +
							(new Random().nextInt(NUMBER_OF_ANIMATED_GLOBES) + 1) + ".png").toExternalForm(), requestedWidth, false);
		}
	}
	
	void setMovingEarthImageViewportProperties()
	{
		double imageWidth = images.get(Images.MOVING_EARTH_1).getWidth();
		if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_16_9) viewPortWidth = imageWidth * 0.4850;
		else if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_16_10) viewPortWidth = imageWidth * 0.4450;
		else if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_25_16) viewPortWidth = imageWidth * 0.4350;
		else if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_3_2) viewPortWidth = imageWidth * 0.4150;
		else if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_4_3) viewPortWidth = imageWidth * 0.3538;
		else if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_5_4) viewPortWidth = imageWidth * 0.3312;
		viewPortHeight = (int) images.get(Images.MOVING_EARTH_1).getHeight();
		movingImageHalfWidth = imageWidth / 2.0;
		
		createViewports();
	}
	
	public void createViewports()
	{
		if(animationsUsed == ANIMATIONS.ALL)
		{
			if(movingEarthImageViewports == null || movingEarthImageViewports.length == 1)
			{
				movingEarthImageViewports = new Rectangle2D[(int) movingImageHalfWidth / 2];
				for(int i = 0, startX = 0; i < movingEarthImageViewports.length; i++, startX += 2)
				{
					movingEarthImageViewports[i] = new Rectangle2D(startX, 0, viewPortWidth, viewPortHeight);
				}
			}
		}
		else
		{
			if(movingEarthImageViewports == null) movingEarthImageViewports = new Rectangle2D[1];
			if(movingEarthImageViewports.length == 1) movingEarthImageViewports[0] = new Rectangle2D(new Random().nextInt((int) movingImageHalfWidth / 2), 0, viewPortWidth, viewPortHeight);
		}
	}
	
	public Image getImage(ImageType imageType, String size, String imageName, boolean backgroundLoading)
	{
		String type = "";
		if(imageType == ImageType.COUNTRY_FLAG) type = "countries/flags";
		else if(imageType == ImageType.COUNTRY_COAT_OF_ARMS)
		{
			type = "countries/coatOfArms";
			
			if(imageName.equals("England") || imageName.equals("Wales") || imageName.equals("Scotland") || imageName.equals("Northern Ireland"))
				imageName = "United Kingdom";
		}
		else if(imageType == ImageType.COUNTRY_LOCATION) type = "countries/location";
		else if(imageType == ImageType.CONTINENT_LOCATION) type = "continents";
		else if(imageType == ImageType.USA_FLAG) type = "usa/flags";
		else if(imageType == ImageType.USA_SEAL) type = "usa/seals";
		else if(imageType == ImageType.USA_LOCATION) type = "usa/location";
		else if(imageType == ImageType.GREECE_DEC_ADM_LOGO) type = "greece/decentralizedAdministrations/logos";
		else if(imageType == ImageType.GREECE_DEC_ADM_LOCATION) type = "greece/decentralizedAdministrations/location";
		else if(imageType == ImageType.GREECE_REGION_LOGO) type = "greece/regions/logos";
		else if(imageType == ImageType.GREECE_REGION_LOCATION) type = "greece/regions/location";
		else if(imageType == ImageType.GREECE_REGIONAL_UNIT_LOCATION) type = "greece/regionalUnits";
		else if(imageType == ImageType.ATTRACTION_PHOTO) type = "attractions/photos";
		else if(imageType == ImageType.ATTRACTION_LOCATION) type = "attractions/location";
		
		String extension;
		if(imageType == ImageType.GREECE_DEC_ADM_LOGO || imageType == ImageType.GREECE_DEC_ADM_LOCATION ||
				imageType == ImageType.GREECE_REGION_LOGO || imageType == ImageType.GREECE_REGION_LOCATION ||
				imageType == ImageType.GREECE_REGIONAL_UNIT_LOCATION || imageType == ImageType.ATTRACTION_PHOTO)
			extension = ".jpg";
		else extension = ".png";
		
		if(!size.equals("")) size = size + "/";
		
		imageName = imageName.replaceAll(" ", "_");
		
		return new Image(getClass().getResource("/images/" + type + "/" + size + imageName + extension).toExternalForm(), backgroundLoading);
	}
	
	public String getImagePath(ImageType imageType, String size, String imageName)
	{
		String type = "";
		if(imageType == ImageType.COUNTRY_FLAG) type = "countries/flags";
		else if(imageType == ImageType.COUNTRY_COAT_OF_ARMS)
		{
			type = "countries/coatOfArms";
			
			if(imageName.equals("England") || imageName.equals("Wales") || imageName.equals("Scotland") || imageName.equals("Northern Ireland"))
				imageName = "United Kingdom";
		}
		else if(imageType == ImageType.COUNTRY_LOCATION) type = "countries/location";
		else if(imageType == ImageType.CONTINENT_LOCATION) type = "continents";
		else if(imageType == ImageType.USA_FLAG) type = "usa/flags";
		else if(imageType == ImageType.USA_SEAL) type = "usa/seals";
		else if(imageType == ImageType.USA_LOCATION) type = "usa/location";
		else if(imageType == ImageType.GREECE_DEC_ADM_LOGO) type = "greece/decentralizedAdministrations/logos";
		else if(imageType == ImageType.GREECE_DEC_ADM_LOCATION) type = "greece/decentralizedAdministrations/location";
		else if(imageType == ImageType.GREECE_REGION_LOGO) type = "greece/regions/logos";
		else if(imageType == ImageType.GREECE_REGION_LOCATION) type = "greece/regions/location";
		else if(imageType == ImageType.GREECE_REGIONAL_UNIT_LOCATION) type = "greece/regionalUnits";
		else if(imageType == ImageType.ATTRACTION_PHOTO) type = "attractions/photos";
		else if(imageType == ImageType.ATTRACTION_LOCATION) type = "attractions/location";
		
		String extension;
		if(imageType == ImageType.GREECE_DEC_ADM_LOGO || imageType == ImageType.GREECE_DEC_ADM_LOCATION ||
				imageType == ImageType.GREECE_REGION_LOGO || imageType == ImageType.GREECE_REGION_LOCATION ||
				imageType == ImageType.GREECE_REGIONAL_UNIT_LOCATION || imageType == ImageType.ATTRACTION_PHOTO)
			extension = ".jpg";
		else extension = ".png";
		
		if(!size.equals("")) size = size + "/";
		
		imageName = imageName.replaceAll(" ", "_");
		
		return getClass().getResource("/images/" + type + "/" + size + imageName + extension).toExternalForm();
	}
	
	public boolean requiresWhiteBackground(String name)
	{
		for(String requireWhiteBg: requireWhiteBackground)
			if(name.replaceAll("_", " ").contains(requireWhiteBg)) return true;
		return false;
	}
	
	public double getWorldMapLayoutX()
	{
		return worldMapLayoutX;
	}
	
	public double getWorldMapLayoutY()
	{
		return worldMapLayoutY;
	}
	
	public double getWorldMapFitWidth()
	{
		return worldMapFitWidth;
	}
	
	public double getWorldMapFitHeight()
	{
		return worldMapFitHeight;
	}
	
	public Rectangle2D[] getMovingEarthImageViewports()
	{
		return movingEarthImageViewports;
	}
	public Map<Images, CustomImage> getImages()
	{
		return images;
	}
	public CustomImage[] getAnimatedGlobes()
	{
		return animatedGlobe;
	}
	
	private static synchronized void initRegisteredImages()
	{
		registeredImages = 0;
	}
	private static synchronized void registerImage(int numOfImages)
	{
		registeredImages += numOfImages;
	}
	private static synchronized void initReadyImages()
	{
		readyImages = 0;
	}
	private static synchronized void imageIsReady()
	{
		if(++readyImages == registeredImages) imagesLoadedFuture.complete("ok");
	}
	
	public static class CustomImage extends Image
	{
		CustomImage(String url, boolean backgroundLoading)
		{
			super(url, backgroundLoading);
			
			progressProperty().addListener(new ImageReadyChangeListener());
		}
		
		CustomImage(String url, double requestedWidth, double requestedHeight)
		{
			super(url, requestedWidth, requestedHeight, true, true, true);
			
			progressProperty().addListener(new ImageReadyChangeListener());
		}
		
		CustomImage(String url, double requestedWidth, boolean smooth)
		{
			super(url, requestedWidth, 0, true, smooth, true);
			
			progressProperty().addListener(new ImageReadyChangeListener());
		}
		
		public static class ImageReadyChangeListener implements ChangeListener<Number>
		{
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
			{
				if(newValue.doubleValue() == 1.0) imageIsReady();
			}
		}
	}
}
