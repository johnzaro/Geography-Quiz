package com.johnzaro.geographyquiz.screens;

import com.johnzaro.geographyquiz.core.ScreenStuff;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;

/**
 * Created by John on 28/2/2017.
 */

abstract class CoreScreenWithMovingBackground extends CoreScreen
{
	Timeline movingEarthTimeline;
	ImageView movingEarthImage;
	private int viewPortIndex; // starting X of moving image viewPort
	int earthTransitionStatus; // 1 = toRight, -1 = toLeft, 0 = stopped
	boolean previousEarthTransitionWasToLeft;
	
	protected void recalculateBackground(double width, double height)
	{
		super.recalculateBackground(width, height);
		
		if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_16_9)
		{
			movingEarthImage.setFitWidth(0.9500 * width);
			movingEarthImage.setLayoutY(0.0500 * height);
		}
		else if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_16_10 ||
				getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_25_16 ||
				getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_3_2)
		{
			movingEarthImage.setFitWidth(0.9590 * width);
			movingEarthImage.setLayoutY(0.0519 * height);
		}
		else if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_4_3)
		{
			movingEarthImage.setFitWidth(0.9548 * width);
			movingEarthImage.setLayoutY(0.0322 * height);
		}
		else if(getScreenStuff().getCurrentScreenRatioEnum() == ScreenStuff.SUPPORTED_SCREEN_RATIOS.RATIO_5_4)
		{
			movingEarthImage.setFitWidth(0.9500 * width);
			movingEarthImage.setLayoutY(0.0332 * height);
		}
		
		movingEarthImage.setLayoutX(width / 2.0 - movingEarthImage.getFitWidth() / 2.0);
	}

	public void setupAdvancedAnimations()
	{
		earthTransitionStatus = 1;
		previousEarthTransitionWasToLeft = true;

		Rectangle2D[] viewportArray = getImageStuff().getMovingEarthImageViewports();
		int viewPortArrayLength = viewportArray.length - 1;
		
		viewPortIndex = 0;

		movingEarthTimeline = new Timeline(
			new KeyFrame(Duration.millis(getImageStuff().MILLIS_FOR_24FPS_ANIMATION), e ->
			{
				if (earthTransitionStatus == 1)
				{
					viewPortIndex--;
					if (viewPortIndex < 0) viewPortIndex = viewPortArrayLength;
				}
				else if (earthTransitionStatus == -1)
				{
					viewPortIndex++;
					if (viewPortIndex > viewPortArrayLength) viewPortIndex = 0;
				}
				else movingEarthTimeline.stop();
				movingEarthImage.setViewport(viewportArray[viewPortIndex]);
			}));
		movingEarthTimeline.setCycleCount(Timeline.INDEFINITE);
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
