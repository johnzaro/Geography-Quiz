package com.johnzaro.geographyquiz.screens;

import com.johnzaro.geographyquiz.core.ImageStuff;
import com.johnzaro.geographyquiz.core.ImageStuff.Images;
import com.johnzaro.geographyquiz.core.customNodes.CustomButton;
import com.johnzaro.geographyquiz.core.customNodes.CustomImageView;
import com.johnzaro.geographyquiz.core.customNodes.CustomVBox;
import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.Map;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;

public abstract class CoreScreen
{
	AnchorPane anchorPane, nodesPane;
	private Label masterVolumeLabel, musicVolumeLabel, soundEffectsVolumeLabel;
	private Slider masterVolumeSlider, musicVolumeSlider, soundEffectsVolumeSlider;
	CustomImageView woodenFrameImage, woodPanelFor1IconImage;
	CustomButton soundButton;
	CustomVBox vBoxForSound;

	ScaleTransition scaleTransitionForSoundIcon;
	TranslateTransition translateTransitionForVBoxForSound, translateTransitionForWoodPanelFor1IconImage;
	Timeline timelineToShowAllStuff, timelineToHideAllStuff, timelineToShowSoundOptions, timelineToHideSoundOptions;

	DropShadow dropShadow, dropShadowForText;
	BoxBlur boxBlurForNodes, boxBlurForText;
	InnerShadow innerShadow;
	
	static final double TEXT_SCALE_ANIMATION_TIME = 4000.0;
	
	static final String BLACK_BACKGROUND_DARK_TRANSPARENT = "-fx-background-color: #00000090;";
	static final String WHITE_BACKGROUND_DARK_TRANSPARENT = "-fx-background-color: #ffffff90;";
	static final String BLACK_BORDERED = "-fx-border-color: black;";
	static final String NORMAL_COLOR = "derive(#ececec, 80%)";
	static final String GREEN_COLOR = "#2ecc71";
	static final String RED_COLOR = "#e74c3c";
	static final Color LIGHT_BLACK = Color.valueOf("00000060");
	static final Color DARK_BLACK = Color.valueOf("00000090");
	static final Color BLACK = Color.BLACK;
	static final Color WHITE = Color.WHITE;
	static final Color WHITE_TRANSPARENT = Color.valueOf("ffffff90");
	static final Color DARK_BROWN = Color.valueOf("602000");
	static final Color BROWN = Color.valueOf("7A301B");
	static final String TEXT_FILL_DARK_GREY = "-fx-text-fill: #323232;";
	static final String GRID_ITEM_EVEN = "-fx-background-color: #FFEBCD;" + TEXT_FILL_DARK_GREY;
	static final String GRID_ITEM_ODD = "-fx-background-color: #EDD1A6;" + TEXT_FILL_DARK_GREY;

	private WidthChangeListener widthChangeListener;
	private HeightChangeListener heightChangeListener;

	private Timeline resizeTimeline;

	double smallIconSize, mediumIconSize, largeIconSize, veryLargeIconSize, shadowRadius, shadowOffset;
	private double boxBlurSize;
	
	Map<Images, ImageStuff.CustomImage> images;
	
	Font font18P, font25P, font30P, font30B, font35P, font40P, font45B, font50B, font55B, font65B, font70B, font80B, font85B, font95B;
	String cssFont15P, cssFont20P, cssFont25P, cssFont30P, cssBackgroundAndBorderSmall, cssBackgroundAndBorderMedium, cssBackgroundAndBorderBig, cssPadding5, cssPadding10, cssPadding20, cssPadding30;

	protected void recalculateUI(double width, double height)
	{
		mediumIconSize = 0.0260 * width; // 50 -> 1920
		smallIconSize = 0.8 * mediumIconSize; // 40 -> 1920
		largeIconSize = 1.2 * mediumIconSize; // 60 -> 1920
		veryLargeIconSize = 1.4 * mediumIconSize; // 70 -> 1920

//		maxTooltipWidth = 0.2604 * width; // 500 -> 1920

		font18P = Font.font(FONT_NAME, 0.0094 * width); // 18 -> 1920
		font25P = Font.font(FONT_NAME, 0.013 * width); // 25 -> 1920
		font30P = Font.font(FONT_NAME, 0.0156 * width); // 30 -> 1920
		font30B = Font.font(FONT_NAME, FontWeight.BOLD, 0.0156 * width); // 30 -> 1920
		font35P = Font.font(FONT_NAME, 0.0182 * width); // 35 -> 1920
		font40P = Font.font(FONT_NAME, 0.0208 * width); // 40 -> 1920
		font45B = Font.font(FONT_NAME, FontWeight.BOLD, 0.0234 * width); // 45 -> 1920
		font50B = Font.font(FONT_NAME, FontWeight.BOLD, 0.0260 * width); // 50 -> 1920
		font55B = Font.font(FONT_NAME, FontWeight.BOLD, 0.0286 * width); // 55 -> 1920
		font65B = Font.font(FONT_NAME, FontWeight.BOLD, 0.0339 * width); // 65 -> 1920
		font70B = Font.font(FONT_NAME, FontWeight.BOLD, 0.0365 * width); // 70 -> 1920
		font80B = Font.font(FONT_NAME, FontWeight.BOLD, 0.0417 * width); // 80 -> 1920
		font85B = Font.font(FONT_NAME, FontWeight.BOLD, 0.0443 * width); // 85 -> 1920
		font95B = Font.font(FONT_NAME, FontWeight.BOLD, 0.0495 * width); // 95 -> 1920
		
		cssFont15P = "-fx-font:" + 0.0078 * width + "px \"" + FONT_NAME + "\";";
		cssFont20P = "-fx-font:" + 0.0104 * width + "px \"" + FONT_NAME + "\";";
		cssFont25P = "-fx-font:" + 0.0130 * width + "px \"" + FONT_NAME + "\";";
		cssFont30P = "-fx-font:" + 0.0156 * width + "px \"" + FONT_NAME + "\";";
		
		cssBackgroundAndBorderSmall = "-fx-background-radius:" + 0.0078 * width + "; -fx-border-radius:" + 0.0068 * width + "; -fx-border-width:" + 0.0026 * width + ";";
		cssBackgroundAndBorderMedium = "-fx-background-radius:" + 0.0104 * width + "; -fx-border-radius:" + 0.0091 * width + "; -fx-border-width:" + 0.0026 * width + ";";
		cssBackgroundAndBorderBig = "-fx-background-radius:" + 0.0208 * width + "; -fx-border-radius:" + 0.0182 * width + "; -fx-border-width:" + 0.0052 * width + ";";
		
		cssPadding5 = "-fx-padding:" + 0.0026 * width + ";";
		cssPadding10 = "-fx-padding:" + 0.0052 * width + ";";
		cssPadding20 = "-fx-padding:" + 0.0104 * width + ";";
		cssPadding30 = "-fx-padding:" + 0.0156 * width + ";";

//		SOUND SLIDER SIZE
		if (width < 1700)
		{
			masterVolumeSlider.setId("small");
			musicVolumeSlider.setId("small");
			soundEffectsVolumeSlider.setId("small");
		}
		else if (width < 2150)
		{
			masterVolumeSlider.setId("medium");
			musicVolumeSlider.setId("medium");
			soundEffectsVolumeSlider.setId("medium");
		}
		else if (width < 3000)
		{
			masterVolumeSlider.setId("big");
			musicVolumeSlider.setId("big");
			soundEffectsVolumeSlider.setId("big");
		}
		else
		{
			masterVolumeSlider.setId("veryBig");
			musicVolumeSlider.setId("veryBig");
			soundEffectsVolumeSlider.setId("veryBig");
		}
		
		masterVolumeLabel.setFont(font18P);
		musicVolumeLabel.setFont(font18P);
		soundEffectsVolumeLabel.setFont(font18P);
		
		woodPanelFor1IconImage.setFitWidth(0.0482 * width);
		
		soundButton.setFitWidth(largeIconSize);
		soundButton.setLayoutX(woodPanelFor1IconImage.getLayoutX() + woodPanelFor1IconImage.getFitWidth() / 2.0 - soundButton.getFitWidth() / 2.0);
		soundButton.getTooltip().setFont(font25P);
		
		vBoxForSound.setPrefSize(ratioProperties.getCore().getvBoxForSoundPrefWidth() * width, ratioProperties.getCore().getvBoxForSoundPrefHeight() * height);
		vBoxForSound.setStyle(cssBackgroundAndBorderSmall + cssPadding10);
		
		masterVolumeSlider.setPrefWidth(vBoxForSound.getPrefWidth());
		musicVolumeSlider.setPrefWidth(vBoxForSound.getPrefWidth());
		soundEffectsVolumeSlider.setPrefWidth(vBoxForSound.getPrefWidth());
		
		shadowRadius = 0.0104 * width;
		shadowOffset = -0.0026 * width;
		innerShadow.setRadius(shadowRadius);
		innerShadow.setOffsetX(shadowOffset);
		innerShadow.setOffsetY(shadowOffset);

		dropShadow.setRadius(shadowRadius);
		dropShadow.setOffsetX(shadowOffset);
		dropShadow.setOffsetY(shadowOffset);

		dropShadowForText.setRadius(0.0026 * width);
		dropShadowForText.setOffsetX(-0.0021 * width);
		dropShadowForText.setOffsetY(-0.0021 * width);

		boxBlurSize = 0.0156 * width;
		boxBlurForNodes.setWidth(boxBlurSize);
		boxBlurForNodes.setHeight(boxBlurSize);

		boxBlurForText.setWidth(boxBlurSize);
		boxBlurForText.setHeight(boxBlurSize);
	}

	protected void recalculateBackground(double width, double height)
	{
		anchorPane.setPrefSize(width, height);
		nodesPane.setPrefSize(width, height);

		woodenFrameImage.setFitWidth(width);
		woodenFrameImage.setFitHeight(height);
	}

	CoreScreen()
	{
		widthChangeListener = new WidthChangeListener();
		heightChangeListener = new HeightChangeListener();
		
		images = getImageStuff().getImages();

		innerShadow = new InnerShadow();

		dropShadow = new DropShadow();
		dropShadowForText = new DropShadow();

		boxBlurForNodes = new BoxBlur();
		boxBlurForNodes.setIterations(2);

		boxBlurForText = new BoxBlur();
		boxBlurForText.setIterations(2);
		boxBlurForText.setInput(dropShadowForText);

		anchorPane = new AnchorPane();
		anchorPane.setCursor(Cursor.DEFAULT);

		nodesPane = new AnchorPane();
		nodesPane.setCursor(Cursor.DEFAULT);

		woodenFrameImage = new CustomImageView(false, false, false, false, null);
		woodenFrameImage.setX(0);
		woodenFrameImage.setY(0);

		anchorPane.getChildren().addAll(nodesPane, woodenFrameImage);

		woodPanelFor1IconImage = new CustomImageView(true, true, false, true, CacheHint.SPEED);
		woodPanelFor1IconImage.setEffect(dropShadow);

		soundButton = new CustomButton(null, null, null, null, null, true, true, true, true, CacheHint.SCALE);
		soundButton.setCursor(Cursor.HAND);

		masterVolumeLabel = new Label();
		masterVolumeLabel.setTextFill(Color.WHITE);

		masterVolumeSlider = new Slider();
		masterVolumeSlider.setCursor(Cursor.HAND);
		masterVolumeSlider.setFocusTraversable(false);

		musicVolumeLabel = new Label();
		musicVolumeLabel.setTextFill(Color.WHITE);

		musicVolumeSlider = new Slider();
		musicVolumeSlider.setCursor(Cursor.HAND);
		musicVolumeSlider.setFocusTraversable(false);

		soundEffectsVolumeLabel = new Label();
		soundEffectsVolumeLabel.setTextFill(Color.WHITE);

		soundEffectsVolumeSlider = new Slider();
		soundEffectsVolumeSlider.setCursor(Cursor.HAND);
		soundEffectsVolumeSlider.setFocusTraversable(false);

		vBoxForSound = new CustomVBox(false, Pos.CENTER, "black-bordered-background-light", false, null);
		vBoxForSound.getChildren().addAll(masterVolumeLabel, masterVolumeSlider, musicVolumeLabel, musicVolumeSlider, soundEffectsVolumeLabel, soundEffectsVolumeSlider);
		vBoxForSound.setVisible(false);
		vBoxForSound.setEffect(dropShadow);

		masterVolumeSlider.setOnMouseReleased(mouseEvent -> getCurrentPlayer().setMasterSliderVolume(getAudioStuff().getMasterSliderVolume()));
		musicVolumeSlider.setOnMouseReleased(mouseEvent -> getCurrentPlayer().setMusicSliderVolume(getAudioStuff().getMusicSliderVolume()));
		soundEffectsVolumeSlider.setOnMouseReleased(mouseEvent -> getCurrentPlayer().setSoundEffectsSliderVolume(getAudioStuff().getSoundEffectsSliderVolume()));

		masterVolumeSlider.valueProperty().addListener((observable, oldValue, newValue) ->
				{
					if (getAudioStuff().getMasterSliderVolume() == 0)
					{
						if (getAudioStuff().isIntroductionSoundPlaying()) getAudioStuff().pauseIntroductionSound();
						else if (getAudioStuff().isWelcomeLoopSoundPlaying()) getAudioStuff().pauseWelcomeLoopSound();
					}
					else
					{
						if ((getAudioStuff().isIntroductionSoundPaused() || getAudioStuff().isIntroductionSoundReady()) && getAudioStuff().getMusicSliderVolume() != 0) getAudioStuff().playIntroductionSound();
						else if (getAudioStuff().isWelcomeLoopSoundPaused() && getAudioStuff().getMusicSliderVolume() != 0) getAudioStuff().playWelcomeLoopSoundSound();
					}

					if(!vBoxForSound.isVisible())
					{
						if (newValue.doubleValue() == 0) soundButton.setImage(images.get(Images.SOUND_OFF));
						else if (newValue.doubleValue() < 33) soundButton.setImage(images.get(Images.SOUND_ON_1));
						else if (newValue.doubleValue() < 66) soundButton.setImage(images.get(Images.SOUND_ON_2));
						else soundButton.setImage(images.get(Images.SOUND_ON_3));
					}
					else
					{
						if (newValue.doubleValue() == 0) soundButton.setImage(images.get(Images.SOUND_OFF_CLICKED));
						else if (newValue.doubleValue() < 33) soundButton.setImage(images.get(Images.SOUND_ON_1_CLICKED));
						else if (newValue.doubleValue() < 66) soundButton.setImage(images.get(Images.SOUND_ON_2_CLICKED));
						else soundButton.setImage(images.get(Images.SOUND_ON_3_CLICKED));
					}

					updateMasterVolumeText();
				});

		masterVolumeSlider.valueProperty().bindBidirectional(getAudioStuff().masterSliderVolumeProperty());
		musicVolumeSlider.valueProperty().bindBidirectional(getAudioStuff().musicSliderVolumeProperty());
		soundEffectsVolumeSlider.valueProperty().bindBidirectional(getAudioStuff().soundEffectsSliderVolumeProperty());

		musicVolumeSlider.valueProperty().addListener((observable, oldValue, newValue) ->
				{
					if (getAudioStuff().getMusicSliderVolume() == 0)
					{
						if (getAudioStuff().isIntroductionSoundPlaying()) getAudioStuff().pauseIntroductionSound();
						else if (getAudioStuff().isWelcomeLoopSoundPlaying()) getAudioStuff().pauseWelcomeLoopSound();
					}
					else
					{
						if ((getAudioStuff().isIntroductionSoundPaused() || getAudioStuff().isIntroductionSoundReady()) && getAudioStuff().getMasterSliderVolume() != 0) getAudioStuff().playIntroductionSound();
						else if (getAudioStuff().isWelcomeLoopSoundPaused() && getAudioStuff().getMasterSliderVolume() != 0) getAudioStuff().playWelcomeLoopSoundSound();
					}

					updateMusicVolumeText();
				});

		soundEffectsVolumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> updateOtherVolumeText());

		resizeTimeline = new Timeline(
				new KeyFrame(Duration.millis(100), e ->
				{
					if(Math.abs(stage.getWidth() - getScreenStuff().getPreviousStageWidth()) <= 5.0)
					{
						recalculateBackground(getScreenStuff().getWindowWidth(), getScreenStuff().getWindowHeight());
						recalculateUI(getScreenStuff().getWindowWidth(), getScreenStuff().getWindowHeight());
						getCurrentPlayer().setWindowWidth(getScreenStuff().getWindowWidth());

						nodesPane.setEffect(null);
						resizeTimeline.stop();
					}
					getScreenStuff().setPreviousStageWidth(stage.getWidth());
				}));
		resizeTimeline.setCycleCount(Animation.INDEFINITE);
	}

	protected abstract void setupListeners();

	protected abstract void setupLimitedAnimations();

	protected abstract void setupAdvancedAnimations();

	protected abstract void setInitialStateForAllNodes();

	protected void showScreen()
	{
		mainScene.setRoot(anchorPane);
		addSceneListeners();

		if(stage.isFullScreen())
		{
			recalculateBackground(getScreenStuff().getPrimaryScreenWidth(), getScreenStuff().getPrimaryScreenHeight());

			recalculateUI(getScreenStuff().getPrimaryScreenWidth(), getScreenStuff().getPrimaryScreenHeight());
			setInitialStateForAllNodes();
			recalculateUI(getScreenStuff().getPrimaryScreenWidth(), getScreenStuff().getPrimaryScreenHeight());
		}
		else
		{
			recalculateBackground(getScreenStuff().getWindowWidth(), getScreenStuff().getWindowHeight());

			recalculateUI(getScreenStuff().getWindowWidth(), getScreenStuff().getWindowHeight());
			setInitialStateForAllNodes();
			recalculateUI(getScreenStuff().getWindowWidth(), getScreenStuff().getWindowHeight());
		}
	}

	void showOtherScreen(CoreScreen screen)
	{
		removeSceneListeners();

		screen.showScreen();
	}

	void updateMasterVolumeText()
	{
		masterVolumeLabel.setText(languageResourceBundle.getString("masterVolumeLabel") + Math.round(masterVolumeSlider.getValue()) + '%');
	}

	void updateMusicVolumeText()
	{
		musicVolumeLabel.setText(languageResourceBundle.getString("musicVolumeLabel") + Math.round(musicVolumeSlider.getValue()) + '%');
	}

	void updateOtherVolumeText()
	{
		soundEffectsVolumeLabel.setText(languageResourceBundle.getString("otherVolumeLabel") + Math.round(soundEffectsVolumeSlider.getValue()) + '%');
	}

	private void addSceneListeners()
	{
		mainScene.widthProperty().addListener(widthChangeListener);
		mainScene.heightProperty().addListener(heightChangeListener);
	}

	private void removeSceneListeners()
	{
		mainScene.widthProperty().removeListener(widthChangeListener);
		mainScene.heightProperty().removeListener(heightChangeListener);
	}

	public void setCorrectSoundIcon(boolean clicked)
	{
		if(clicked)
		{
			if (soundButton.getImage() == images.get(Images.SOUND_OFF)) soundButton.setImage(images.get(Images.SOUND_OFF_CLICKED));
			else if (soundButton.getImage() == images.get(Images.SOUND_ON_1)) soundButton.setImage(images.get(Images.SOUND_ON_1_CLICKED));
			else if (soundButton.getImage() == images.get(Images.SOUND_ON_2)) soundButton.setImage(images.get(Images.SOUND_ON_2_CLICKED));
			else if (soundButton.getImage() == images.get(Images.SOUND_ON_3)) soundButton.setImage(images.get(Images.SOUND_ON_3_CLICKED));
		}
		else
		{
			if (soundButton.getImage() == images.get(Images.SOUND_OFF_CLICKED)) soundButton.setImage(images.get(Images.SOUND_OFF));
			else if (soundButton.getImage() == images.get(Images.SOUND_ON_1_CLICKED)) soundButton.setImage(images.get(Images.SOUND_ON_1));
			else if (soundButton.getImage() == images.get(Images.SOUND_ON_2_CLICKED)) soundButton.setImage(images.get(Images.SOUND_ON_2));
			else if (soundButton.getImage() == images.get(Images.SOUND_ON_3_CLICKED)) soundButton.setImage(images.get(Images.SOUND_ON_3));
		}
	}

	public AnchorPane getAnchorPane()
	{
		return anchorPane;
	}

	private class WidthChangeListener implements ChangeListener<Number>
	{
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
		{
			getScreenStuff().setWindowWidth(newValue.doubleValue());
		}
	}

	private class HeightChangeListener implements ChangeListener<Number>
	{
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
		{
			getScreenStuff().setWindowHeight(newValue.doubleValue());

			if(resizeTimeline.getStatus() != Animation.Status.RUNNING)
			{
				nodesPane.setEffect(boxBlurForNodes);
				resizeTimeline.playFromStart();
			}

			recalculateBackground(getScreenStuff().getWindowWidth(), getScreenStuff().getWindowHeight());
		}
	}
}
