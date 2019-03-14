package code.screens;

import code.core.*;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

import static code.core.GlobalVariables.*;

abstract class CoreScreen
{
	AnchorPane anchorPane, nodesPane;
	Label masterVolumeLabel, musicVolumeLabel, soundEffectsVolumeLabel;
	Slider masterVolumeSlider, musicVolumeSlider, soundEffectsVolumeSlider;
	ImageView woodenFrameImage, soundIcon, minimizeIcon, moveIcon, fullScreenIcon, exitIcon;
	VBox vBoxForSound;
	CustomTooltip soundOptionsToolTip, minimizeTooltip, moveTooltip, fullScreenTooltip, exitTooltip;

	private BoxBlur boxBlur;
	
	private double xOffset = 0, yOffset = 0, dx = 0, dy = 0;
	private int positionOfResizing = 0;
	
	CoreScreen()
	{
		anchorPane = new AnchorPane();
		anchorPane.setCursor(Cursor.DEFAULT);
		anchorPane.setStyle("-fx-background-color: transparent");
		
		nodesPane = new AnchorPane();
		nodesPane.setCursor(Cursor.DEFAULT);
		nodesPane.setStyle("-fx-background-color: transparent");
		
		woodenFrameImage = new ImageView();
		woodenFrameImage.setX(0);
		woodenFrameImage.setY(0);
		woodenFrameImage.setSmooth(false);
		woodenFrameImage.setPreserveRatio(false);
		
		anchorPane.getChildren().addAll(nodesPane, woodenFrameImage);
		
		boxBlur = new BoxBlur();
		boxBlur.setWidth(30);
		boxBlur.setHeight(30);
		boxBlur.setIterations(2);
		
		soundIcon = new ImageView();
		soundIcon.setCursor(Cursor.HAND);
		soundIcon.setPreserveRatio(true);
		soundIcon.setStyle("-fx-background-color: transparent");
		soundOptionsToolTip = new CustomTooltip();
		soundOptionsToolTip.setWrapText(true);
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

		minimizeIcon = new ImageView(MINIMIZE_ICON);
		minimizeIcon.setPreserveRatio(true);
		minimizeIcon.setCursor(Cursor.HAND);
		minimizeTooltip = new CustomTooltip();
		minimizeTooltip.setWrapText(true);
		Tooltip.install(minimizeIcon, minimizeTooltip);

		moveIcon = new ImageView(MOVE_ICON);
		moveIcon.setPreserveRatio(true);
		moveIcon.setCursor(Cursor.HAND);
		moveTooltip = new CustomTooltip();
		moveTooltip.setWrapText(true);
		Tooltip.install(moveIcon, moveTooltip);

		fullScreenIcon = new ImageView(FULL_SCREEN_ICON);
		fullScreenIcon.setPreserveRatio(true);
		fullScreenIcon.setCursor(Cursor.HAND);
		fullScreenTooltip = new CustomTooltip();
		fullScreenTooltip.setWrapText(true);
		Tooltip.install(fullScreenIcon, fullScreenTooltip);

		if(!setCurrentScreenRatio(primaryScreenWidth, primaryScreenHeight)) fullScreenIcon.setDisable(true);
		
		exitIcon = new ImageView(EXIT_ICON);
		exitIcon.setPreserveRatio(true);
		exitIcon.setCursor(Cursor.HAND);
		exitTooltip = new CustomTooltip();
		exitTooltip.setWrapText(true);
		Tooltip.install(exitIcon, exitTooltip);

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

		minimizeIcon.setOnMousePressed(e -> minimizeIcon.setImage(MINIMIZE_ICON_CLICKED));

		minimizeIcon.setOnMouseClicked(e ->
				{
					minimizedMode = true;
					minimizeGame(anchorPane);
				});

		minimizeIcon.setOnMouseReleased(e -> minimizeIcon.setImage(MINIMIZE_ICON));

		fullScreenIcon.setOnMousePressed(e ->
				{
					if(fullScreenMode) fullScreenIcon.setImage(FULL_SCREEN_ICON);
					else fullScreenIcon.setImage(FULL_SCREEN_ICON_CLICKED);
				});

		fullScreenIcon.setOnMouseClicked(e ->
				{
					if (fullScreenMode) setWindowedMode();
					else setFullScreenMode();
				});

		fullScreenIcon.setOnMouseReleased(e ->
				{
					if(fullScreenMode) fullScreenIcon.setImage(FULL_SCREEN_ICON_CLICKED);
					else fullScreenIcon.setImage(FULL_SCREEN_ICON);
				});

		exitIcon.setOnMousePressed(e -> exitIcon.setImage(EXIT_ICON_CLICKED));

		exitIcon.setOnMouseReleased(e -> exitIcon.setImage(EXIT_ICON));

		moveIcon.setOnMousePressed(e ->
				{
					if (!stage.isFullScreen())
					{
						xOffset = e.getSceneX();
						yOffset = e.getSceneY();

						moveIcon.setImage(MOVE_ICON_CLICKED);
					}
					else { moveIcon.setImage(MOVE_ICON_DISABLED_CLICKED); }
				});

		moveIcon.setOnMouseReleased(e ->
				{
					if (stage.isFullScreen()) { moveIcon.setImage(MOVE_ICON_DISABLED); }
					else moveIcon.setImage(MOVE_ICON);
				});

		moveIcon.setOnMouseDragged(e ->
				{
					if (!stage.isFullScreen())
					{
						stage.setX(e.getScreenX() - xOffset);
						stage.setY(e.getScreenY() - yOffset);
					}
				});

		anchorPane.setOnMouseMoved(e ->
				{
					if (!stage.isFullScreen())
					{
						double resizeArea = 0.0052 * windowWidth;

						if ((e.getY() < resizeArea || e.getY() > stage.getHeight() - resizeArea) && e.getX() > resizeArea * 3 &&
						    e.getX() < stage.getWidth() - resizeArea * 3) { anchorPane.setCursor(Cursor.V_RESIZE); }
						else if ((e.getX() > stage.getWidth() - resizeArea || e.getX() < resizeArea) && e.getY() > resizeArea * 3 &&
						         e.getY() < stage.getHeight() - resizeArea * 3) { anchorPane.setCursor(Cursor.H_RESIZE); }
						else { anchorPane.setCursor(Cursor.DEFAULT); }
					}
				});

		anchorPane.setOnMousePressed(e ->
			{
				if (!stage.isFullScreen())
				{
					double resizeArea = 0.0052 * windowWidth;
				
					if (e.getY() < resizeArea && e.getX() > resizeArea * 3 && e.getX() < stage.getWidth() - resizeArea * 3)
					{
						dy = e.getY();
						positionOfResizing = 1;
						
						nodesPane.setEffect(boxBlur);
					}
					else if (e.getX() > stage.getWidth() - resizeArea && e.getY() > resizeArea * 3 && e.getY() < stage.getHeight() - resizeArea * 3)
					{
						dx = stage.getWidth() - e.getX();
						positionOfResizing = 2;
						nodesPane.setEffect(boxBlur);
					}
					else if (e.getY() > stage.getHeight() - resizeArea && e.getX() > resizeArea * 3 && e.getX() < stage.getWidth() - resizeArea * 3)
					{
						dy = stage.getHeight() - e.getY();
						positionOfResizing = 3;
						nodesPane.setEffect(boxBlur);
					}
					else if (e.getX() < resizeArea && e.getY() > resizeArea * 3 && e.getY() < stage.getHeight() - resizeArea * 3)
					{
						dx = e.getX();
						positionOfResizing = 4;
						nodesPane.setEffect(boxBlur);
					}
					else positionOfResizing = 0;
				}
			});
		
		anchorPane.setOnMouseDragged(e ->
			{
				if (!stage.isFullScreen())
				{
					if (positionOfResizing == 1 &&
					    (getCurrentScreenRatio() == RATIO_16_9 && (windowHeight - e.getScreenY() + stage.getY() + dy) > MIN_HEIGHT_FOR_16_9 ||
					     getCurrentScreenRatio() == RATIO_16_10 && (windowHeight - e.getScreenY() + stage.getY() + dy) > MIN_HEIGHT_FOR_16_10 ||
					     getCurrentScreenRatio() == RATIO_25_16 && (windowHeight - e.getScreenY() + stage.getY() + dy) > MIN_HEIGHT_FOR_25_16 ||
					     getCurrentScreenRatio() == RATIO_3_2 && (windowHeight - e.getScreenY() + stage.getY() + dy) > MIN_HEIGHT_FOR_3_2 ||
					     getCurrentScreenRatio() == RATIO_4_3 && (windowHeight - e.getScreenY() + stage.getY() + dy) > MIN_HEIGHT_FOR_4_3 ||
					     getCurrentScreenRatio() == RATIO_5_4 && (windowHeight - e.getScreenY() + stage.getY() + dy) > MIN_HEIGHT_FOR_5_4))
					{
						windowHeight = windowHeight - e.getScreenY() + stage.getY() + dy;
						double prevWidth = windowWidth;
						
						if(getCurrentScreenRatio() == RATIO_16_9) windowWidth = windowHeight * 16.0 / 9.0;
						else if(getCurrentScreenRatio() == RATIO_16_10) windowWidth = windowHeight * 16.0 / 10.0;
						else if(getCurrentScreenRatio() == RATIO_25_16) windowWidth = windowHeight * 25.0 / 16.0;
						else if(getCurrentScreenRatio() == RATIO_3_2) windowWidth = windowHeight * 3.0 / 2.0;
						else if(getCurrentScreenRatio() == RATIO_4_3) windowWidth = windowHeight * 4.0 / 3.0;
						else if(getCurrentScreenRatio() == RATIO_5_4) windowWidth = windowHeight * 5.0 / 4.0;
						
						recalculateBackground(windowWidth, windowHeight);
						
						stage.setHeight(windowHeight);
						stage.setWidth(windowWidth);
						stage.setY(e.getScreenY() - dy);
						stage.setX(stage.getX() - (windowWidth - prevWidth));
					}
					else if (positionOfResizing == 2 &&
					         (getCurrentScreenRatio() == RATIO_16_9 && e.getX() + dx > MIN_WIDTH_FOR_16_9 ||
					          getCurrentScreenRatio() == RATIO_16_10 && e.getX() + dx > MIN_WIDTH_FOR_16_10 ||
					          getCurrentScreenRatio() == RATIO_25_16 && e.getX() + dx > MIN_WIDTH_FOR_25_16 ||
					          getCurrentScreenRatio() == RATIO_3_2 && e.getX() + dx > MIN_WIDTH_FOR_3_2 ||
					          getCurrentScreenRatio() == RATIO_4_3 && e.getX() + dx > MIN_WIDTH_FOR_4_3 ||
					          getCurrentScreenRatio() == RATIO_5_4 && e.getX() + dx > MIN_WIDTH_FOR_5_4))
					{
						windowWidth = e.getX() + dx;
						
						if(getCurrentScreenRatio() == RATIO_16_9) windowHeight = windowWidth * 9.0 / 16.0;
						else if(getCurrentScreenRatio() == RATIO_16_10) windowHeight = windowWidth * 10.0 / 16.0;
						else if(getCurrentScreenRatio() == RATIO_25_16) windowHeight = windowWidth * 16.0 / 25.0;
						else if(getCurrentScreenRatio() == RATIO_3_2) windowHeight = windowWidth * 2.0 / 3.0;
						else if(getCurrentScreenRatio() == RATIO_4_3) windowHeight = windowWidth * 3.0 / 4.0;
						else if(getCurrentScreenRatio() == RATIO_5_4) windowHeight = windowWidth * 4.0 / 5.0;
						
						recalculateBackground(windowWidth, windowHeight);
						
						stage.setWidth(windowWidth);
						stage.setHeight(windowHeight);
					}
					else if (positionOfResizing == 3 &&
					         (getCurrentScreenRatio() == RATIO_16_9 && e.getY() + dy > MIN_HEIGHT_FOR_16_9 ||
					          getCurrentScreenRatio() == RATIO_16_10 && e.getY() + dy > MIN_HEIGHT_FOR_16_10 ||
					          getCurrentScreenRatio() == RATIO_25_16 && e.getY() + dy > MIN_HEIGHT_FOR_25_16 ||
					          getCurrentScreenRatio() == RATIO_3_2 && e.getY() + dy > MIN_HEIGHT_FOR_3_2 ||
					          getCurrentScreenRatio() == RATIO_4_3 && e.getY() + dy > MIN_HEIGHT_FOR_4_3 ||
					          getCurrentScreenRatio() == RATIO_5_4 && e.getY() + dy > MIN_HEIGHT_FOR_5_4))
					{
						windowHeight = e.getY() + dy;
						
						if(getCurrentScreenRatio() == RATIO_16_9) windowWidth = windowHeight * 16.0 / 9.0;
						else if(getCurrentScreenRatio() == RATIO_16_10) windowWidth = windowHeight * 16.0 / 10.0;
						else if(getCurrentScreenRatio() == RATIO_25_16) windowWidth = windowHeight * 25.0 / 16.0;
						else if(getCurrentScreenRatio() == RATIO_3_2) windowWidth = windowHeight * 3.0 / 2.0;
						else if(getCurrentScreenRatio() == RATIO_4_3) windowWidth = windowHeight * 4.0 / 3.0;
						else if(getCurrentScreenRatio() == RATIO_5_4) windowWidth = windowHeight * 5.0 / 4.0;
						
						recalculateBackground(windowWidth, windowHeight);
						
						stage.setHeight(windowHeight);
						stage.setWidth(windowWidth);
					}
					else if (positionOfResizing == 4 &&
					         (getCurrentScreenRatio() == RATIO_16_9 && (windowWidth - e.getScreenX() + stage.getX() + dx) > MIN_WIDTH_FOR_16_9 ||
					          getCurrentScreenRatio() == RATIO_16_10 && (windowWidth - e.getScreenX() + stage.getX() + dx) > MIN_WIDTH_FOR_16_10 ||
					          getCurrentScreenRatio() == RATIO_25_16 && (windowWidth - e.getScreenX() + stage.getX() + dx) > MIN_WIDTH_FOR_25_16 ||
					          getCurrentScreenRatio() == RATIO_3_2 && (windowWidth - e.getScreenX() + stage.getX() + dx) > MIN_WIDTH_FOR_3_2 ||
					          getCurrentScreenRatio() == RATIO_4_3 && (windowWidth - e.getScreenX() + stage.getX() + dx) > MIN_WIDTH_FOR_4_3 ||
					          getCurrentScreenRatio() == RATIO_5_4 && (windowWidth - e.getScreenX() + stage.getX() + dx) > MIN_WIDTH_FOR_5_4))
					{
						windowWidth = windowWidth - e.getScreenX() + stage.getX() + dx;
						double prevHeight = windowHeight;
						
						if(getCurrentScreenRatio() == RATIO_16_9) windowHeight = windowWidth * 9.0 / 16.0;
						else if(getCurrentScreenRatio() == RATIO_16_10) windowHeight = windowWidth * 10.0 / 16.0;
						else if(getCurrentScreenRatio() == RATIO_25_16) windowHeight = windowWidth * 16.0 / 25.0;
						else if(getCurrentScreenRatio() == RATIO_3_2) windowHeight = windowWidth * 2.0 / 3.0;
						else if(getCurrentScreenRatio() == RATIO_4_3) windowHeight = windowWidth * 3.0 / 4.0;
						else if(getCurrentScreenRatio() == RATIO_5_4) windowHeight = windowWidth * 4.0 / 5.0;
						
						recalculateBackground(windowWidth, windowHeight);
						
						stage.setWidth(windowWidth);
						stage.setHeight(windowHeight);
						
						stage.setX(e.getScreenX() - dx);
						stage.setY(stage.getY() - (windowHeight - prevHeight));
					}
				}
			});
		
		anchorPane.setOnMouseReleased(e ->
		{
			if (!stage.isFullScreen())
			{
				if(anchorPane.getCursor().equals(Cursor.H_RESIZE) || anchorPane.getCursor().equals(Cursor.V_RESIZE))
				{
					recalculateBackground(windowWidth, windowHeight);
					recalculateUI(windowWidth, windowHeight);
					nodesPane.setEffect(null);
				}
			}
		});
		
		exitIcon.setOnMouseClicked(e -> exitGame(anchorPane));
	}

	public AnchorPane getAnchorPane()
	{
		return anchorPane;
	}
	
	void setFullScreenMode()
	{
		double newWidth, newHeight, previousScreenRatio;
		ObservableList<Screen> screens = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
		
		newWidth = screens.get(0).getBounds().getWidth();
		newHeight = screens.get(0).getBounds().getHeight();
		
		previousScreenRatio = getCurrentScreenRatio();
		setCurrentScreenRatio(newWidth, newHeight);
		if (previousScreenRatio != getCurrentScreenRatio())
		{
			primaryScreenWidth = newWidth;
			primaryScreenHeight = newHeight;
			
			PowerOn.powerOnScreenDependentImages(primaryScreenWidth);
			setScreenRatioDependentImages();
		}
		
		recalculateBackground(primaryScreenWidth, primaryScreenHeight);
		recalculateUI(primaryScreenWidth, primaryScreenHeight);
		
		fullScreenMode = true;
		if(!stage.isFullScreen()) stage.setFullScreen(true);
		
		if(moveIcon.getImage() != MOVE_ICON_DISABLED) moveIcon.setImage(MOVE_ICON_DISABLED);
		if(fullScreenIcon.getImage() != FULL_SCREEN_ICON_CLICKED) fullScreenIcon.setImage(FULL_SCREEN_ICON_CLICKED);
		
		moveTooltip.setText(languageResourceBundle.getString("moveDisabledTooltip"));
		fullScreenTooltip.setText(languageResourceBundle.getString("exitFullScreenTooltip"));
	}

	void setWindowedMode()
	{
		fullScreenMode = false;
		if (stage.isFullScreen()) stage.setFullScreen(false);
		
		if(moveIcon.getImage() != MOVE_ICON) moveIcon.setImage(MOVE_ICON);
		if(fullScreenIcon.getImage() != FULL_SCREEN_ICON) fullScreenIcon.setImage(FULL_SCREEN_ICON);
		
		moveTooltip.setText(languageResourceBundle.getString("moveTooltip"));
		fullScreenTooltip.setText(languageResourceBundle.getString("enterFullScreenTooltip"));
		
		recalculateBackground(windowWidth, windowHeight);
		recalculateUI(windowWidth, windowHeight);
	}
	
	protected abstract void setScreenRatioDependentImages();

	protected abstract void recalculateUI(double width, double height);
	
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
