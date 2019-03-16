package code.screens;

import code.core.BaseClass;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import static code.core.GlobalVariables.*;
import static code.core.PowerOn.setWindowedModeValues;

public class SplashScreen
{
	private static String[] loadingDots = new String[4];
	private static String loadingText;

	private AnchorPane anchorPane;
	private VBox progressBox;
	private ImageView splashImage, splashImageName;
	private ProgressBar progressBar;
	private Label progressText;
	private Thread progressThread;
	private Task task;

	private FadeTransition fadeOutTransition;

	private double splashWidth, splashHeight;

	public SplashScreen()
	{
		if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_9 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_10) splashWidth = 0.75 * primaryScreenWidth;
		else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_25_16 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_3_2) splashWidth = 0.8 * primaryScreenWidth;
		else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_4_3 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_5_4) splashWidth = 0.9 * primaryScreenWidth;
		splashHeight = splashWidth * 9.0 / 16.0;

		task = new Task<Void>()
		{
			int counter = 0;

			public Void call() throws InterruptedException
			{
				final int max = 400;
				for (int i = 1; i <= max; i++)
				{
					updateProgress(i, max);

					if ((i == 1 || i % 50 == 0) && i != 400)
					{
						Platform.runLater(() ->
								{
									progressText.setText(loadingText + loadingDots[counter]);
									counter++;
									if(counter == 4) counter = 0;
								});
					}
//					progressThread.sleep(10);
				}
				return null;
			}
		};
		
		task.setOnSucceeded(e ->
		{
			try
			{
				BaseClass.t1.join();
				BaseClass.t2.join();
				BaseClass.t3.join();
				BaseClass.t4.join();
				BaseClass.t5.join();
			}
			catch(Exception ex){}
			
			fadeOutTransition.play();
		});

		anchorPane = new AnchorPane();
		anchorPane.setPrefWidth(splashWidth);
		anchorPane.setPrefHeight(splashHeight);
		anchorPane.setStyle("-fx-background-color: transparent;");

		splashImage = new ImageView(SPLASH_BACKGROUND_IMAGE);
		splashImage.setX(0);
		splashImage.setY(0);
		splashImage.setFitWidth(splashWidth);
		splashImage.setPreserveRatio(true);
		splashImage.setSmooth(true);

		splashImageName = new ImageView(SPLASH_TEXT_IMAGE);
		splashImageName.setSmooth(true);
		splashImageName.setPreserveRatio(true);

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
		progressBar.progressProperty().bind(task.progressProperty());

		progressText = new Label();
		progressText.setPrefWidth(0.3139 * splashWidth);
		progressText.setPrefHeight(0.0429 * splashHeight);
		progressText.setAlignment(Pos.CENTER);
		progressText.setFont(Font.font("Comic Sans MS", 0.0125 * splashWidth));

		progressBox.getChildren().addAll(progressBar, progressText);

		progressThread = new Thread(task);

		fadeOutTransition = new FadeTransition(Duration.millis(400), anchorPane);
		fadeOutTransition.setToValue(0.0);
		fadeOutTransition.setOnFinished(e ->
				{
					disposeSplashScreen();
					setWindowedModeValues();
					mainScene.setCursor(Cursor.DEFAULT);
					if(welcomeScreen != null) welcomeScreen.showScreen(true);
				});
		
		loadingDots[0] = "";
		loadingDots[1] = ".";
		loadingDots[2] = "..";
		loadingDots[3] = "...";
		
		if(getCurrentLanguage() == LANGUAGE.GREEK)
		{
			loadingText = "Φόρτωση";
			
			splashImageName.setFitWidth(0.6875 * splashWidth);
			splashImageName.setY(0.0778 * splashHeight);
		}
		else
		{
			loadingText = "Loading";
			
			splashImageName.setFitWidth(0.6750 * splashWidth);
			splashImageName.setY(0.1111 * splashHeight);
		}
		splashImageName.setX(splashWidth / 2.0 - splashImageName.getFitWidth() / 2.0);

		anchorPane.getChildren().addAll(splashImage, splashImageName, progressBox);

		stage.setX(
				primaryScreenResolution.getMinX() + primaryScreenWidth / 2.0 - splashWidth / 2.0);
		stage.setY(
				primaryScreenResolution.getMinY() + primaryScreenHeight / 2.0 - splashHeight / 2.0);
		
		mainScene = new Scene(anchorPane);
		mainScene.setFill(Color.TRANSPARENT);
		mainScene.setCursor(Cursor.WAIT);
		mainScene.getStylesheets().addAll(buttonCSS, checkboxCSS, radioButtonCSS, sliderCSS,
				comboBoxCSS, toggleButtonCSS, listViewCSS, labelCSS, scrollPaneCSS, textAreaCSS,
				progressBarCSS, tableViewCSS);
		
		stage.setScene(mainScene);
		stage.show();

		new Timeline(new KeyFrame(Duration.millis(50), e -> progressThread.start())).play();
	}
	
	private void disposeSplashScreen()
	{
		anchorPane.setOpacity(0);
		
		progressBar = null;
		progressText = null;
		progressBox = null;
		
		splashImageName = null;
		splashImage = null;
		
		progressThread = null;
		task = null;
		
		SPLASH_BACKGROUND_IMAGE = null;
		SPLASH_TEXT_IMAGE = null;
		
		loadingDots = null;
		
		fadeOutTransition = null;
		
		anchorPane = null;
		splashScreen = null;
	}
}
