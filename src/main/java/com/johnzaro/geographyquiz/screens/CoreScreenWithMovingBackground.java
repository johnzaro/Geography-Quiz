package com.johnzaro.geographyquiz.screens;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;
import static com.johnzaro.geographyquiz.core.GlobalVariables.MOVING_EARTH_IMAGE_1;

/**
 * Created by John on 28/2/2017.
 */
abstract class CoreScreenWithMovingBackground extends CoreScreen
{
	Timeline movingEarthTimeline;
	ImageView movingEarthImage;
	double viewPortWidth, viewPortHeight;
	double movingImageHalfWidth;
	int movingEarthStartX; // starting X of moving image viewPort
	int earthTransitionStatus; // 1 = toRight, -1 = toLeft, 0 = stopped
	boolean previousEarthTransitionWasToLeft;
	double viewPortX, viewPortY;
	
	protected void setupAdvancedAnimations()
	{
		movingEarthStartX = 0;
		earthTransitionStatus = 1;
		previousEarthTransitionWasToLeft = true;
		
		movingEarthTimeline = new Timeline(
				new KeyFrame(Duration.millis(13), e ->
				{
					if (earthTransitionStatus == 1)
					{
						movingEarthStartX--;
						if (movingEarthStartX < 0) movingEarthStartX = (int) movingImageHalfWidth;
					}
					else if (earthTransitionStatus == -1)
					{
						movingEarthStartX++;
						if (movingEarthStartX > movingImageHalfWidth) movingEarthStartX = 0;
					}
					else movingEarthTimeline.stop();
					movingEarthImage.setViewport(new Rectangle2D(movingEarthStartX, 0, viewPortWidth, viewPortHeight));
				}));
		movingEarthTimeline.setCycleCount(Timeline.INDEFINITE);
	}
	
	void setViewPortProperties()
	{
		if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_9) viewPortWidth = MOVING_EARTH_IMAGE_1.getWidth() * 0.4850;
		else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_10) viewPortWidth = MOVING_EARTH_IMAGE_1.getWidth() * 0.4450;
		else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_25_16) viewPortWidth = MOVING_EARTH_IMAGE_1.getWidth() * 0.4350;
		else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_3_2) viewPortWidth = MOVING_EARTH_IMAGE_1.getWidth() * 0.4150;
		else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_4_3) viewPortWidth = MOVING_EARTH_IMAGE_1.getWidth() * 0.3538;
		else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_5_4) viewPortWidth = MOVING_EARTH_IMAGE_1.getWidth() * 0.3312;
		viewPortHeight = (int) MOVING_EARTH_IMAGE_1.getHeight();
		movingImageHalfWidth = MOVING_EARTH_IMAGE_1.getWidth() / 2.0;
		movingEarthImage.setViewport(new Rectangle2D(movingEarthStartX, 0, viewPortWidth, viewPortHeight));
	}
	
	public void playEarthAnimation()
	{
		movingEarthTimeline.play();
		
		movingEarthImage.setCursor(Cursor.HAND);
	}
	
	public void pauseEarthAnimation()
	{
		movingEarthTimeline.stop();
		
		movingEarthImage.setCursor(Cursor.DEFAULT);
	}
}
