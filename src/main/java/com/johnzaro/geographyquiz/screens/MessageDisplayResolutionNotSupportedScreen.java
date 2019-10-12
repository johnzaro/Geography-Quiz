package com.johnzaro.geographyquiz.screens;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ResourceBundle;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;


public class MessageDisplayResolutionNotSupportedScreen
{
	private Scene scene;
	private AnchorPane anchorPane;
	private VBox vBox;
	private Label label;
	private Button closeButton;
	private ImageView backgroundChalkboardImage, frameImage, gameNameImage;
	private TextField textField;
	private DropShadow dropShadow;

	private double width, height;

	public MessageDisplayResolutionNotSupportedScreen(ResourceBundle r)
	{
		double percentage = 0.85;
		do
		{
			width = percentage * primaryScreenWidth;
			height = width * 9.0 / 16.0;
			percentage -= 0.05;
		}
		while(height > 0.9 * primaryScreenHeight);
		
		anchorPane = new AnchorPane();
		anchorPane.setStyle("-fx-background-color: transparent");
		anchorPane.setPrefWidth(width);
		anchorPane.setPrefHeight(height);

		scene = new Scene(anchorPane);
		scene.setFill(Color.TRANSPARENT);
		scene.getStylesheets().add("/css/button.css");

		frameImage = new ImageView(FRAME_IMAGE);
		frameImage.setSmooth(true);
		frameImage.setPreserveRatio(true);
		frameImage.setLayoutX(0);
		frameImage.setLayoutY(0);
		frameImage.setFitWidth(width);
		
		dropShadow = new DropShadow();
		dropShadow.setRadius(0.0104 * width);
		dropShadow.setOffsetX(-0.0052 * width);
		dropShadow.setOffsetY(-0.0052 * width);
		frameImage.setEffect(dropShadow);

		backgroundChalkboardImage = new ImageView(CHALKBOARD_BACKGROUND_IMAGE);
		backgroundChalkboardImage.setLayoutX(0);
		backgroundChalkboardImage.setLayoutY(0);
		backgroundChalkboardImage.setPreserveRatio(true);
		backgroundChalkboardImage.setFitWidth(width);
		backgroundChalkboardImage.setSmooth(true);

		gameNameImage = new ImageView();
		gameNameImage.setPreserveRatio(true);
		gameNameImage.setSmooth(true);

		gameNameImage.setFitWidth(0.6400 * width);
		gameNameImage.setLayoutX(width / 2.0 - gameNameImage.getFitWidth() / 2.0);
		gameNameImage.setLayoutY(0.0224 * height);
		gameNameImage.setImage(GAME_NAME_IMAGE);

		Font fontForLabels = Font.font("Comic Sans MS", 0.0234 * width);

		label = new Label();
		label.setTextAlignment(TextAlignment.CENTER);
		label.setTextFill(Color.WHITE);
		label.setWrapText(true);
		label.setFont(fontForLabels);

		textField = new TextField();
		textField.setFont(fontForLabels);
		textField.setStyle("-fx-background-color: transparent; -fx-background-insets: 0px; -fx-text-inner-color: white;");
		textField.setAlignment(Pos.CENTER);
		textField.setEditable(false);

		vBox = new VBox();
		vBox.setStyle(
				"-fx-background-color: #00000099; -fx-border-color: black;" +
						"-fx-background-radius:" + 0.0208 * width + ";" +
						"-fx-border-radius:" + 0.0208 * width + ";" +
						"-fx-border-width:" + 0.0042 * width + ";" +
						"-fx-padding:" + 0.0130 * width);
		vBox.setAlignment(Pos.CENTER);
		vBox.setPrefSize(0.6797 * width, 0.4500 * height);
		vBox.setLayoutX(width / 2.0 - vBox.getPrefWidth() / 2.0);
		vBox.setLayoutY(height / 2.0 - vBox.getPrefHeight() / 2.0);
		vBox.setSpacing(0.1000 * height);
		vBox.getChildren().addAll(label, textField);

		closeButton = new Button();
		closeButton.setPrefSize(0.2344 * width, 0.0900 * height);
		closeButton.setLayoutX(width / 2.0 - closeButton.getPrefWidth() / 2.0);
		closeButton.setLayoutY(0.8333 * height - closeButton.getPrefHeight() / 2.0);
		closeButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0234 * width));
		closeButton.setCursor(Cursor.HAND);

		label.setText(languageResourceBundle.getString("messageDisplayResolutionNotSupported"));
		textField.setText("e-mail: " + email + " - Resolution: " + (int)primaryScreenWidth + 'x' + (int)primaryScreenHeight);
		closeButton.setText(languageResourceBundle.getString("closeButton"));

		anchorPane.getChildren().addAll(backgroundChalkboardImage, gameNameImage, vBox, closeButton, frameImage);

		closeButton.setOnAction(e -> new Timeline(
            new KeyFrame(Duration.millis(0), ev -> playButtonClickSound()), new KeyFrame(Duration.millis(300), ev -> exitGame())).play());
		
		stage.setX(primaryScreenResolution.getMinX() + primaryScreenWidth / 2.0 - width / 2.0);
		stage.setY(primaryScreenResolution.getMinY() + primaryScreenHeight / 2.0 - height / 2.0);
		
		stage.setScene(scene);
		stage.show();
	}
}
