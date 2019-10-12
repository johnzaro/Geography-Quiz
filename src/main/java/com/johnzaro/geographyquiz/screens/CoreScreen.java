package com.johnzaro.geographyquiz.screens;

import com.johnzaro.geographyquiz.core.CustomImageView;
import com.johnzaro.geographyquiz.core.CustomTooltip;
import com.johnzaro.geographyquiz.core.PowerOn;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;

public abstract class CoreScreen
{
	AnchorPane anchorPane, nodesPane;
	Label masterVolumeLabel, musicVolumeLabel, soundEffectsVolumeLabel;
	Slider masterVolumeSlider, musicVolumeSlider, soundEffectsVolumeSlider;
	CustomImageView woodenFrameImage, soundIcon;
	VBox vBoxForSound;
	CustomTooltip soundOptionsToolTip;
	
	Font fontForTooltips;
	
	CoreScreen()
	{
		anchorPane = new AnchorPane();
		anchorPane.setCursor(Cursor.DEFAULT);
		anchorPane.setStyle("-fx-background-color: transparent");
		
		nodesPane = new AnchorPane();
		nodesPane.setCursor(Cursor.DEFAULT);
		nodesPane.setStyle("-fx-background-color: transparent");
		
		woodenFrameImage = new CustomImageView(false, false, false, false, null);
		woodenFrameImage.setX(0);
		woodenFrameImage.setY(0);
		
		anchorPane.getChildren().addAll(nodesPane, woodenFrameImage);
		
		soundIcon = new CustomImageView(false, true, false, true, CacheHint.SCALE);
		soundIcon.setCursor(Cursor.HAND);
		
		soundOptionsToolTip = new CustomTooltip();
		Tooltip.install(soundIcon, soundOptionsToolTip);

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

		vBoxForSound = new VBox();
		vBoxForSound.setAlignment(Pos.CENTER);
		vBoxForSound.getChildren().addAll(masterVolumeLabel, masterVolumeSlider, musicVolumeLabel, musicVolumeSlider, soundEffectsVolumeLabel, soundEffectsVolumeSlider);
		vBoxForSound.setVisible(false);
		
		soundIcon.setOnMousePressed(e ->
		{
           if (!vBoxForSound.isVisible())
           {
               if (soundIcon.getImage() == SOUND_OFF_ICON) { soundIcon.setImage(SOUND_OFF_ICON_CLICKED); }
               else if (soundIcon.getImage() == SOUND_ON_1_BAR_ICON) { soundIcon.setImage(SOUND_ON_1_BAR_ICON_CLICKED); }
               else if (soundIcon.getImage() == SOUND_ON_2_BARS_ICON) { soundIcon.setImage(SOUND_ON_2_BARS_ICON_CLICKED); }
               else if (soundIcon.getImage() == SOUND_ON_3_BARS_ICON) soundIcon.setImage(SOUND_ON_3_BARS_ICON_CLICKED);
           }
           else
           {
               if (soundIcon.getImage() == SOUND_OFF_ICON_CLICKED) { soundIcon.setImage(SOUND_OFF_ICON); }
               else if (soundIcon.getImage() == SOUND_ON_1_BAR_ICON_CLICKED) { soundIcon.setImage(SOUND_ON_1_BAR_ICON); }
               else if (soundIcon.getImage() == SOUND_ON_2_BARS_ICON_CLICKED) { soundIcon.setImage(SOUND_ON_2_BARS_ICON); }
               else if (soundIcon.getImage() == SOUND_ON_3_BARS_ICON_CLICKED) soundIcon.setImage(SOUND_ON_3_BARS_ICON);
           }
       });

		soundIcon.setOnMouseReleased(e ->
		{
            if (!vBoxForSound.isVisible())
            {
                if (soundIcon.getImage() == SOUND_OFF_ICON_CLICKED) { soundIcon.setImage(SOUND_OFF_ICON); }
                else if (soundIcon.getImage() == SOUND_ON_1_BAR_ICON_CLICKED) { soundIcon.setImage(SOUND_ON_1_BAR_ICON); }
                else if (soundIcon.getImage() == SOUND_ON_2_BARS_ICON_CLICKED) { soundIcon.setImage(SOUND_ON_2_BARS_ICON); }
                else if (soundIcon.getImage() == SOUND_ON_3_BARS_ICON_CLICKED) soundIcon.setImage(SOUND_ON_3_BARS_ICON);
            }
            else
            {
                if (soundIcon.getImage() == SOUND_OFF_ICON) { soundIcon.setImage(SOUND_OFF_ICON_CLICKED); }
                else if (soundIcon.getImage() == SOUND_ON_1_BAR_ICON) { soundIcon.setImage(SOUND_ON_1_BAR_ICON_CLICKED); }
                else if (soundIcon.getImage() == SOUND_ON_2_BARS_ICON) { soundIcon.setImage(SOUND_ON_2_BARS_ICON_CLICKED); }
                else if (soundIcon.getImage() == SOUND_ON_3_BARS_ICON) soundIcon.setImage(SOUND_ON_3_BARS_ICON_CLICKED);
            }
        });
		
		masterVolumeSlider.setOnMouseReleased(mouseEvent -> getCurrentPlayer().setMasterSliderVolume(masterSliderVolume.get()));
		musicVolumeSlider.setOnMouseReleased(mouseEvent -> getCurrentPlayer().setMusicSliderVolume(musicSliderVolume.get()));
		soundEffectsVolumeSlider.setOnMouseReleased(mouseEvent -> getCurrentPlayer().setSoundEffectsSliderVolume(soundEffectsSliderVolume.get()));
		
		masterVolumeSlider.valueProperty().addListener((observable, oldValue, newValue) ->
				{
					if (masterSliderVolume.get() == 0)
					{
						if (isIntroductionSoundPlaying()) pauseIntroductionSound();
						else if (isWelcomeLoopSoundPlaying()) pauseWelcomeLoopSound();
					}
					else
					{
						if ((isIntroductionSoundPaused() || isIntroductionSoundReady()) && musicSliderVolume.get() != 0) playIntroductionSound();
						else if (isWelcomeLoopSoundPaused() && musicSliderVolume.get() != 0) playWelcomeLoopSoundSound();
					}
					
					if(!vBoxForSound.isVisible())
					{
						if (newValue.doubleValue() == 0) soundIcon.setImage(SOUND_OFF_ICON);
						else if (newValue.doubleValue() < 33) soundIcon.setImage(SOUND_ON_1_BAR_ICON);
						else if (newValue.doubleValue() < 66) soundIcon.setImage(SOUND_ON_2_BARS_ICON);
						else soundIcon.setImage(SOUND_ON_3_BARS_ICON);
					}
					else
					{
						if (newValue.doubleValue() == 0) soundIcon.setImage(SOUND_OFF_ICON_CLICKED);
						else if (newValue.doubleValue() < 33) soundIcon.setImage(SOUND_ON_1_BAR_ICON_CLICKED);
						else if (newValue.doubleValue() < 66) soundIcon.setImage(SOUND_ON_2_BARS_ICON_CLICKED);
						else soundIcon.setImage(SOUND_ON_3_BARS_ICON_CLICKED);
					}
					
					updateMasterVolumeText();
				});
		
		masterVolumeSlider.valueProperty().bindBidirectional(masterSliderVolume);
		musicVolumeSlider.valueProperty().bindBidirectional(musicSliderVolume);
		soundEffectsVolumeSlider.valueProperty().bindBidirectional(soundEffectsSliderVolume);

		musicVolumeSlider.valueProperty().addListener((observable, oldValue, newValue) ->
				{
					if (musicSliderVolume.get() == 0)
					{
						if (isIntroductionSoundPlaying()) pauseIntroductionSound();
						else if (isWelcomeLoopSoundPlaying()) pauseWelcomeLoopSound();
					}
					else
					{
						if ((isIntroductionSoundPaused() || isIntroductionSoundReady()) && masterSliderVolume.get() != 0) playIntroductionSound();
						else if (isWelcomeLoopSoundPaused() && masterSliderVolume.get() != 0) playWelcomeLoopSoundSound();
					}

					updateMusicVolumeText();
				});

		soundEffectsVolumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> updateOtherVolumeText());
		
		stage.fullScreenProperty().addListener((observable, oldValue, newValue) ->
		{
			if(newValue != oldValue && newValue) checkIfScreenRatioChanged();
		});
	}

	public AnchorPane getAnchorPane()
	{
		return anchorPane;
	}
	
	private void checkIfScreenRatioChanged()
	{
		double newWidth, newHeight;
		SUPPORTED_SCREEN_RATIOS previousScreenRatio;
		ObservableList<Screen> screens = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
		
		newWidth = screens.get(0).getBounds().getWidth();
		newHeight = screens.get(0).getBounds().getHeight();
		
		previousScreenRatio = getCurrentScreenRatioEnum();
		setCurrentScreenRatio(newWidth, newHeight);
		if (previousScreenRatio != getCurrentScreenRatioEnum())
		{
			primaryScreenWidth = newWidth;
			primaryScreenHeight = newHeight;
			
			PowerOn.powerOnScreenDependentImages(primaryScreenWidth);
			setScreenRatioDependentImages();
		}
		
		recalculateBackground(primaryScreenWidth, primaryScreenHeight);
		recalculateUI(primaryScreenWidth, primaryScreenHeight);
	}
	
	protected abstract void setScreenRatioDependentImages();

	protected void recalculateUI(double width, double height)
	{
		fontForTooltips = Font.font("Comic Sans MS", 0.013 * width); // 30 -> 1920
		
		if (width < 1500)
		{
			masterVolumeSlider.setId("small");
			musicVolumeSlider.setId("small");
			soundEffectsVolumeSlider.setId("small");
		}
		else if (width < 2000)
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
		
		Font fontForSound = Font.font("Comic Sans MS", 0.0104 * width);
		masterVolumeLabel.setFont(fontForSound);
		musicVolumeLabel.setFont(fontForSound);
		soundEffectsVolumeLabel.setFont(fontForSound);
		
		vBoxForSound.setPrefSize(ratioProperties.getCore().getvBoxForSoundPrefWidth() * width, ratioProperties.getCore().getvBoxForSoundPrefHeight() * height);
		
		vBoxForSound.setStyle(
				"-fx-background-color: #00000099; -fx-border-color: black;" +
				"-fx-background-radius:" + 0.0078 * width + ";" +
				"-fx-border-radius:" + 0.0078 * width + ";" +
				"-fx-border-width:" + 0.0026 * width + ";" +
				"-fx-padding:" + 0.0052 * width + ";");
	}
	
	protected void recalculateBackground(double width, double height)
	{
		anchorPane.setPrefSize(width, height);
		nodesPane.setPrefSize(width, height);
		
		woodenFrameImage.setFitWidth(width);
		woodenFrameImage.setFitHeight(height);
	}

	protected abstract void setupListeners();

	protected abstract void setupLimitedAnimations();
	
	protected abstract void setupAdvancedAnimations();

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

}
