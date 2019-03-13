package code.screens;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static code.core.GlobalVariables.*;


public class MessageGameIsAlreadyRunningScreen
{
	private Scene scene;
	private AnchorPane anchorPane;
	private Label label;
	private Button closeButton;
	private ImageView backgroundChalkboardImage, frameImage, gameNameImage;
	private DropShadow dropShadow;
	
	private double width, height;

	public MessageGameIsAlreadyRunningScreen()
	{
		if(getCurrentScreenRatio() == RATIO_16_9 || getCurrentScreenRatio() == RATIO_16_10) width = 0.75 * primaryScreenWidth;
		else if(getCurrentScreenRatio() == RATIO_25_16 || getCurrentScreenRatio() == RATIO_3_2) width = 0.8 * primaryScreenWidth;
		else if(getCurrentScreenRatio() == RATIO_4_3 || getCurrentScreenRatio() == RATIO_5_4) width = 0.9 * primaryScreenWidth;
		height = width * 9.0 / 16.0;
		
		anchorPane = new AnchorPane();
		anchorPane.setStyle("-fx-background-color: transparent");
		anchorPane.setPrefWidth(width);
		anchorPane.setPrefHeight(height);

		scene = new Scene(anchorPane);
		scene.setFill(Color.TRANSPARENT);
		scene.getStylesheets().add("/resources/css/button.css");

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
		
		label = new Label();
		label.setStyle(
				"-fx-background-color: #00000099; -fx-border-color: black;" +
						"-fx-background-radius:" + 0.0208 * width + ";" +
						"-fx-border-radius:" + 0.0208 * width + ";" +
						"-fx-border-width:" + 0.0042 * width + ";" +
						"-fx-padding:" + 0.0130 * width);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setTextFill(Color.WHITE);
		label.setWrapText(true);
		label.setPrefSize(0.5977 * width, 0.4000 * height);
		label.setLayoutX(width / 2.0 - label.getPrefWidth() / 2.0);
		label.setLayoutY(height / 2.0 - label.getPrefHeight() / 2.0);
		label.setFont(Font.font("Comic Sans MS", 0.0273 * width));

		closeButton = new Button();
		closeButton.setPrefSize(0.2344 * width, 0.0900 * height);
		closeButton.setLayoutX(0.3828 * width);
		closeButton.setLayoutY(0.8148 * height - closeButton.getPrefHeight() / 2.0);
		closeButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0234 * width));
		closeButton.setCursor(Cursor.HAND);

		label.setText(languageResourceBundle.getString("messageGameIsAlreadyRunningText"));
		closeButton.setText(languageResourceBundle.getString("closeButton"));

		anchorPane.getChildren().addAll(backgroundChalkboardImage, gameNameImage, label, closeButton, frameImage);

		closeButton.setOnAction(e ->
			new Timeline(
					            new KeyFrame(Duration.millis(0), ev -> playButtonClickSound()),
					            new KeyFrame(Duration.millis(300), ev -> exitGame(anchorPane))
		).play());
		
		stage.setX(primaryScreenResolution.getMinX() + primaryScreenWidth / 2.0 - width / 2.0);
		stage.setY(primaryScreenResolution.getMinY() + primaryScreenHeight / 2.0 - height / 2.0);
		
		stage.setScene(scene);
		stage.show();
	}
}
