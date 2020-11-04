package com.johnzaro.geographyquiz.screens.errorScreens;

import com.johnzaro.geographyquiz.core.PowerOn;
import com.johnzaro.geographyquiz.core.ScreenStuff;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;

public class MessageGameIsAlreadyRunningScreen
{
	private Image FRAME_IMAGE, CHALKBOARD_BACKGROUND_IMAGE, GAME_NAME_IMAGE;
	private AudioClip BUTTON_CLICK_SOUND;
	private double windowWidth, windowHeight, primaryScreenWidth, primaryScreenHeight;
	
	public MessageGameIsAlreadyRunningScreen()
	{
		primaryScreenWidth = getScreenStuff().getPrimaryScreenWidth();
		primaryScreenHeight = getScreenStuff().getPrimaryScreenHeight();
		
		if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_16_9 ||
				getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_16_10) windowWidth = 0.75 * primaryScreenWidth;
		else if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_25_16 ||
				getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_3_2) windowWidth = 0.8 * primaryScreenWidth;
		else if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_4_3 ||
				getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_5_4) windowWidth = 0.9 * primaryScreenWidth;
		windowHeight = windowWidth * 9.0 / 16.0;

		setupMedia();

		AnchorPane anchorPane = new AnchorPane();
		anchorPane.setPrefWidth(windowWidth);
		anchorPane.setPrefHeight(windowHeight);

		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(0.0104 * windowWidth);
		dropShadow.setOffsetX(-0.0026 * windowWidth);
		dropShadow.setOffsetY(-0.0026 * windowWidth);

		ImageView frameImage = new ImageView(FRAME_IMAGE);
		frameImage.setSmooth(true);
		frameImage.setPreserveRatio(true);
		frameImage.setLayoutX(0);
		frameImage.setLayoutY(0);
		frameImage.setFitWidth(windowWidth);

		ImageView backgroundChalkboardImage = new ImageView(CHALKBOARD_BACKGROUND_IMAGE);
		backgroundChalkboardImage.setLayoutX(0);
		backgroundChalkboardImage.setLayoutY(0);
		backgroundChalkboardImage.setPreserveRatio(true);
		backgroundChalkboardImage.setFitWidth(windowWidth);
		backgroundChalkboardImage.setSmooth(true);

		ImageView gameNameImage = new ImageView();
		gameNameImage.setPreserveRatio(true);
		gameNameImage.setSmooth(true);

		gameNameImage.setFitWidth(0.6400 * windowWidth);
		gameNameImage.setLayoutX(windowWidth / 2.0 - gameNameImage.getFitWidth() / 2.0);
		gameNameImage.setLayoutY(0.0224 * windowHeight);
		gameNameImage.setImage(GAME_NAME_IMAGE);

		Label label = new Label();
		label.setStyle(
			"-fx-background-color: #00000060; -fx-border-color: black;" +
			"-fx-background-radius:" + 0.0208 * windowWidth + ";" +
			"-fx-border-radius:" + 0.0208 * windowWidth + ";" +
			"-fx-border-width:" + 0.0042 * windowWidth + ";" +
			"-fx-padding:" + 0.0130 * windowWidth);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setTextFill(Color.WHITE);
		label.setWrapText(true);
		label.setPrefSize(0.5977 * windowWidth, 0.4000 * windowHeight);
		label.setLayoutX(windowWidth / 2.0 - label.getPrefWidth() / 2.0);
		label.setLayoutY(windowHeight / 2.0 - label.getPrefHeight() / 2.0);
		label.setFont(Font.font("Comic Sans MS", 0.0273 * windowWidth));

		Button closeButton = new Button();
		closeButton.setPrefSize(0.2344 * windowWidth, 0.0900 * windowHeight);
		closeButton.setLayoutX(0.3828 * windowWidth);
		closeButton.setLayoutY(0.8148 * windowHeight - closeButton.getPrefHeight() / 2.0);
		closeButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0234 * windowWidth));
		closeButton.setCursor(Cursor.HAND);
		closeButton.setId("normal");

		label.setText(languageResourceBundle.getString("messageGameIsAlreadyRunningText"));
		closeButton.setText(languageResourceBundle.getString("closeButton"));

		frameImage.setEffect(dropShadow);
		gameNameImage.setEffect(dropShadow);
		label.setEffect(dropShadow);
		closeButton.setEffect(dropShadow);

		anchorPane.getChildren().addAll(backgroundChalkboardImage, gameNameImage, label, closeButton, frameImage);

		mainScene = new Scene(anchorPane);
		mainScene.getStylesheets().add("/css/button.css");

		Timeline closeWindowTimeline = new Timeline(new KeyFrame(Duration.millis(200), e -> exitGame()));

		closeButton.setOnAction(e ->
		{
			BUTTON_CLICK_SOUND.play();
			closeWindowTimeline.playFromStart();
		});
	}
	
	private static void exitGame()
	{
		Platform.exit();
		System.exit(0);
	}

	private void setupMedia()
	{
		if(primaryScreenWidth <= 1920)
		{
			FRAME_IMAGE = new Image(PowerOn.class.getResource("/images/backgrounds/frame_16-9@1920x1080.png").toExternalForm(), windowWidth, 0, true, false);
			CHALKBOARD_BACKGROUND_IMAGE = new Image(PowerOn.class.getResource("/images/backgrounds/chalkboardBackground_16-9@1920x1080.jpg").toExternalForm(), windowWidth, 0, true, false);
		}
		else if(primaryScreenWidth <= 2560)
		{
			FRAME_IMAGE = new Image(PowerOn.class.getResource("/images/backgrounds/frame_16-9@2560x1440.png").toExternalForm(), windowWidth, 0, true, false);
			CHALKBOARD_BACKGROUND_IMAGE = new Image(PowerOn.class.getResource("/images/backgrounds/chalkboardBackground_16-9@2560x1440.jpg").toExternalForm(), windowWidth, 0, true, false);
		}
		else
		{
			FRAME_IMAGE = new Image(PowerOn.class.getResource("/images/backgrounds/frame_16-9@5120x2880.png").toExternalForm(), windowWidth, 0, true, false);
			CHALKBOARD_BACKGROUND_IMAGE = new Image(PowerOn.class.getResource("/images/backgrounds/chalkboardBackground_16-9@5120x2880.jpg").toExternalForm(), windowWidth, 0, true, false);
		}

		if(getCurrentLanguage() == LANGUAGE.GREEK)
			GAME_NAME_IMAGE = new Image(PowerOn.class.getResource("/images/backgrounds/gameNameGreek.png").toExternalForm(), 0.7 * primaryScreenWidth, 0, true, false);
		else
			GAME_NAME_IMAGE  = new Image(PowerOn.class.getResource("/images/backgrounds/gameNameEnglish.png").toExternalForm(), 0.7 * primaryScreenWidth, 0, true, false);

		BUTTON_CLICK_SOUND = new AudioClip(PowerOn.class.getResource("/audio/buttonClick.wav").toExternalForm());
		BUTTON_CLICK_SOUND.setVolume(0.7);

	}

	public void setupAndShowStage()
	{
		stage.setTitle(languageResourceBundle.getString("gameName"));
		stage.setResizable(false);
		stage.setX(getScreenStuff().getPrimaryScreenResolution().getMinX() + primaryScreenWidth / 2.0 - windowWidth / 2.0);
		stage.setY(getScreenStuff().getPrimaryScreenResolution().getMinY() + primaryScreenHeight / 2.0 - windowHeight / 2.0);

		stage.setScene(mainScene);
		stage.show();
	}
}
