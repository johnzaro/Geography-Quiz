package com.johnzaro.geographyquiz.core;

import javafx.geometry.Rectangle2D;

import static com.johnzaro.geographyquiz.core.GlobalVariables.EPSILON_VALUE;

public class ScreenStuff
{
	public enum SUPPORTED_SCREEN_RATIOS { RATIO_16_9, RATIO_16_10, RATIO_25_16, RATIO_3_2, RATIO_4_3, RATIO_5_4, RATIO_NOT_SUPPORTED }
	
	private SUPPORTED_SCREEN_RATIOS currentScreenRatioEnum;
	private double currentScreenRatioValue, primaryScreenWidth, primaryScreenHeight, windowWidth, windowHeight, previousStageWidth, stageSceneHeightDifference;
	private int minStageWidth, minStageHeight;
	private Rectangle2D primaryScreenResolution;
	
	private final int MIN_WIDTH_FOR_16_9 = 1050, MIN_HEIGHT_FOR_16_9 = 590, MIN_WIDTH_FOR_16_10 = 1000, MIN_HEIGHT_FOR_16_10 = 625,
	MIN_WIDTH_FOR_25_16 = 1000, MIN_HEIGHT_FOR_25_16 = 640, MIN_WIDTH_FOR_3_2 = 990, MIN_HEIGHT_FOR_3_2 = 660,
	MIN_WIDTH_FOR_4_3 = 800, MIN_HEIGHT_FOR_4_3 = 600, MIN_WIDTH_FOR_5_4 = 900, MIN_HEIGHT_FOR_5_4 = 720;
	
	public void setCurrentScreenRatio(double screenWidth, double screenHeight)
	{
		currentScreenRatioValue = screenWidth / screenHeight;
		
		if(Math.abs(currentScreenRatioValue - 16.0 / 9.0) <= EPSILON_VALUE
				|| Math.abs(currentScreenRatioValue - 85.0 / 48.0) <= EPSILON_VALUE
				|| Math.abs(currentScreenRatioValue - 683.0 / 384.0) <= EPSILON_VALUE
				|| Math.abs(currentScreenRatioValue - 221.0 / 124.0) <= EPSILON_VALUE
				|| Math.abs(currentScreenRatioValue - 147.0 / 83.0) <= EPSILON_VALUE)
		{
			currentScreenRatioEnum = SUPPORTED_SCREEN_RATIOS.RATIO_16_9;
			minStageWidth = MIN_WIDTH_FOR_16_9;
			minStageHeight = MIN_HEIGHT_FOR_16_9;
		}
		else if(Math.abs(currentScreenRatioValue - 16.0 / 10.0) <= EPSILON_VALUE)
		{
			currentScreenRatioEnum = SUPPORTED_SCREEN_RATIOS.RATIO_16_10;
			minStageWidth = MIN_WIDTH_FOR_16_10;
			minStageHeight = MIN_HEIGHT_FOR_16_10;
		}
		else if(Math.abs(currentScreenRatioValue - 25.0 / 16.0) <= EPSILON_VALUE)
		{
			currentScreenRatioEnum = SUPPORTED_SCREEN_RATIOS.RATIO_25_16;
			minStageWidth = MIN_WIDTH_FOR_25_16;
			minStageHeight = MIN_HEIGHT_FOR_25_16;
		}
		else if(Math.abs(currentScreenRatioValue - 3.0 / 2.0) <= EPSILON_VALUE)
		{
			currentScreenRatioEnum = SUPPORTED_SCREEN_RATIOS.RATIO_3_2;
			minStageWidth = MIN_WIDTH_FOR_3_2;
			minStageHeight = MIN_HEIGHT_FOR_3_2;
		}
		else if(Math.abs(currentScreenRatioValue - 4.0 / 3.0) <= EPSILON_VALUE)
		{
			currentScreenRatioEnum = SUPPORTED_SCREEN_RATIOS.RATIO_4_3;
			minStageWidth = MIN_WIDTH_FOR_4_3;
			minStageHeight = MIN_HEIGHT_FOR_4_3;
		}
		else if(Math.abs(currentScreenRatioValue - 5.0 / 4.0) <= EPSILON_VALUE)
		{
			currentScreenRatioEnum = SUPPORTED_SCREEN_RATIOS.RATIO_5_4;
			minStageWidth = MIN_WIDTH_FOR_5_4;
			minStageHeight = MIN_HEIGHT_FOR_5_4;
		}
		else
			currentScreenRatioEnum = SUPPORTED_SCREEN_RATIOS.RATIO_NOT_SUPPORTED;
	}
	
	public SUPPORTED_SCREEN_RATIOS getCurrentScreenRatioEnum()
	{
		return currentScreenRatioEnum;
	}
	
	public double getCurrentScreenRatioValue() { return currentScreenRatioValue; }
	
	public boolean isCurrentScreenRatioSupported()
	{
		return currentScreenRatioEnum != SUPPORTED_SCREEN_RATIOS.RATIO_NOT_SUPPORTED;
	}
	
	public double getHeightBasedOnWidth(double width)
	{
		return width / getCurrentScreenRatioValue();
	}
	
	public boolean isWidthValid(double width)
	{
		return width <= primaryScreenWidth && width >= minStageWidth;
	}
	
	public double getPrimaryScreenWidth()
	{
		return primaryScreenWidth;
	}
	
	public void setPrimaryScreenWidth(double primaryScreenWidth)
	{
		this.primaryScreenWidth = primaryScreenWidth;
	}
	
	public double getPrimaryScreenHeight()
	{
		return primaryScreenHeight;
	}
	
	public void setPrimaryScreenHeight(double primaryScreenHeight)
	{
		this.primaryScreenHeight = primaryScreenHeight;
	}
	
	public double getWindowWidth()
	{
		return windowWidth;
	}
	
	public void setWindowWidth(double windowWidth)
	{
		this.windowWidth = windowWidth;
	}
	
	public double getWindowHeight()
	{
		return windowHeight;
	}
	
	public void setWindowHeight(double windowHeight)
	{
		this.windowHeight = windowHeight;
	}
	
	public double getPreviousStageWidth()
	{
		return previousStageWidth;
	}
	
	public void setPreviousStageWidth(double previousStageWidth)
	{
		this.previousStageWidth = previousStageWidth;
	}
	
	public double getStageSceneHeightDifference()
	{
		return stageSceneHeightDifference;
	}
	
	public void setStageSceneHeightDifference(double stageSceneHeightDifference)
	{
		this.stageSceneHeightDifference = stageSceneHeightDifference;
	}
	
	public Rectangle2D getPrimaryScreenResolution()
	{
		return primaryScreenResolution;
	}
	
	public void setPrimaryScreenResolution(Rectangle2D primaryScreenResolution)
	{
		this.primaryScreenResolution = primaryScreenResolution;
	}
	
	public int getMinStageWidth()
	{
		return minStageWidth;
	}
	
	public void setMinStageWidth(int minStageWidth)
	{
		this.minStageWidth = minStageWidth;
	}
	
	public int getMinStageHeight()
	{
		return minStageHeight;
	}
	
	public void setMinStageHeight(int minStageHeight)
	{
		this.minStageHeight = minStageHeight;
	}
}
