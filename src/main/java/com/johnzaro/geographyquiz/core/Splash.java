package com.johnzaro.geographyquiz.core;

import com.johnzaro.geographyquiz.core.customNodes.CustomImageView;
import javafx.application.Preloader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;
import static com.johnzaro.geographyquiz.core.PowerOn.*;

public class Splash extends Preloader
{
	private Stage splashStage;
	private Scene splashScene;

	private AnchorPane anchorPane;
	private VBox progressBox;
	private CustomImageView splashImage, splashImageName;
	private ProgressBar progressBar;
	private Label progressText;
	
	private Image SPLASH_BACKGROUND_IMAGE;
	private Image SPLASH_TEXT_IMAGE;
	
	private double splashWidth, splashHeight;
	
	public void init()
	{
		System.setProperty("prism.lcdtext", "false");
	
		setupOSType();
		FilesIO.createGameDataPathFolder();
		language = FilesIO.loadLanguageFile();
		
		screenStuff = new ScreenStuff();
		
		loadFonts();
		setupPrimaryScreenBounds();
		screenStuff.setCurrentScreenRatio(screenStuff.getPrimaryScreenWidth(), screenStuff.getPrimaryScreenHeight());
		
		setSplashSize();
		loadSplashImages();
		
		anchorPane = new AnchorPane();
		anchorPane.setPrefWidth(splashWidth);
		anchorPane.setPrefHeight(splashHeight);
		anchorPane.setStyle("-fx-background-color: transparent;");
		
		splashImage = new CustomImageView(SPLASH_BACKGROUND_IMAGE, true, true, false, false, null);
		splashImage.setX(0);
		splashImage.setY(0);
		splashImage.setFitWidth(splashWidth);
		
		splashImageName = new CustomImageView(SPLASH_TEXT_IMAGE, true, true, false, false, null);
		
		progressBox = new VBox();
		progressBox.setStyle("-fx-background-color: transparent;");
		progressBox.setLayoutX(0.3431 * splashWidth);
		progressBox.setLayoutY(0.9074 * splashHeight);
		progressBox.setPrefWidth(0.3139 * splashWidth);
		progressBox.setPrefHeight(0.0857 * splashHeight);
		
		progressBar = new ProgressBar();
		progressBar.setId("normal");
		progressBar.setPrefWidth(0.3139 * splashWidth);
		progressBar.setPrefHeight(0.0429 * splashHeight);
		progressBar.setProgress(0);
		progressBar.setStyle("-fx-accent: #2ecc71;");
		progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
		
		progressText = new Label();
		progressText.setPrefWidth(0.3139 * splashWidth);
		progressText.setPrefHeight(0.0429 * splashHeight);
		progressText.setAlignment(Pos.CENTER);
		progressText.setFont(Font.font("Comic Sans MS", 0.0125 * splashWidth));
		
		if(language == LANGUAGE.GREEK)
		{
			progressText.setText("Φόρτωση...");
			
			splashImageName.setFitWidth(0.6875 * splashWidth);
			splashImageName.setY(0.0778 * splashHeight);
		}
		else
		{
			progressText.setText("Loading...");
			
			splashImageName.setFitWidth(0.6750 * splashWidth);
			splashImageName.setY(0.1111 * splashHeight);
		}
		splashImageName.setX(splashWidth / 2.0 - splashImageName.getFitWidth() / 2.0);
		
		progressBox.getChildren().addAll(progressBar, progressText);
		
		anchorPane.getChildren().addAll(splashImage, splashImageName, progressBox);
		
		splashScene = new Scene(anchorPane);
		splashScene.setFill(Color.TRANSPARENT);
		splashScene.setCursor(Cursor.WAIT);
	}
	
	public void start(Stage primaryStage)
	{
		splashStage = primaryStage;
		
		splashStage.initStyle(StageStyle.TRANSPARENT);
		splashStage.setResizable(false);
		
		splashStage.setX(screenStuff.getPrimaryScreenResolution().getMinX() + screenStuff.getPrimaryScreenWidth() / 2.0 - splashWidth / 2.0);
		splashStage.setY(screenStuff.getPrimaryScreenResolution().getMinY() + screenStuff.getPrimaryScreenHeight() / 2.0 - splashHeight / 2.0);
		
		splashStage.setScene(splashScene);
		splashStage.show();
	}
	
	public void handleStateChangeNotification(StateChangeNotification info)
	{
		if(info.getType() == StateChangeNotification.Type.BEFORE_START)
		{
			splashStage.hide();
			disposeSplashScreen();
		}
	}
	
	private void setSplashSize()
	{
		if(screenStuff.getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_16_9
				|| screenStuff.getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_16_10)
			splashWidth = 0.75 * screenStuff.getPrimaryScreenWidth();
		else if(screenStuff.getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_25_16
				|| screenStuff.getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_3_2)
			splashWidth = 0.8 * screenStuff.getPrimaryScreenWidth();
		else if(screenStuff.getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_4_3
				|| screenStuff.getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_5_4)
			splashWidth = 0.9 * screenStuff.getPrimaryScreenWidth();
		splashHeight = splashWidth * 9.0 / 16.0;
	}
	
	private void loadSplashImages()
	{
		if(screenStuff.getPrimaryScreenWidth() <= 1920)
			SPLASH_BACKGROUND_IMAGE = new Image(PowerOn.class.getResource("/images/backgrounds/splashImage@1920x1080.png").toExternalForm(), splashWidth, 0, true, false);
		else if(screenStuff.getPrimaryScreenWidth() <= 2560)
			SPLASH_BACKGROUND_IMAGE = new Image(PowerOn.class.getResource("/images/backgrounds/splashImage@2560x1440.png").toExternalForm(), splashWidth, 0, true, false);
		else
			SPLASH_BACKGROUND_IMAGE = new Image(PowerOn.class.getResource("/images/backgrounds/splashImage@3072x1728.png").toExternalForm(), splashWidth, 0, true, false);
		
		if(language == LANGUAGE.GREEK)
			SPLASH_TEXT_IMAGE = new Image(PowerOn.class.getResource("/images/backgrounds/splashImageGreekName.png").toExternalForm(), 0.7 * splashWidth, 0, true, false);
		else
			SPLASH_TEXT_IMAGE = new Image(PowerOn.class.getResource("/images/backgrounds/splashImageEnglishName.png").toExternalForm(), 0.7 * splashWidth, 0, true, false);
	}
	
	private void disposeSplashScreen()
	{
		anchorPane.setOpacity(0);
		
		progressBar = null;
		progressText = null;
		progressBox = null;
		
		splashImageName = null;
		splashImage = null;
		
		SPLASH_BACKGROUND_IMAGE = null;
		SPLASH_TEXT_IMAGE = null;
		
		anchorPane = null;
		
		splashScene = null;
		splashStage = null;
	}
}
