package com.johnzaro.geographyquiz.dataStructures;

public class RatioProperties
{
	private Core core;
	private Atlas atlas;
	private GameProperties gameProperties;
	private Game game;
	private ScoreBoard scoreBoard;
	private Welcome welcome;
	
	public RatioProperties()
	{
		core = new Core();
		atlas = new Atlas();
		gameProperties = new GameProperties();
		game = new Game();
		scoreBoard = new ScoreBoard();
		welcome = new Welcome();
	}
	
	public Core getCore()
	{
		return core;
	}
	
	public Atlas getAtlas()
	{
		return atlas;
	}
	
	public GameProperties getGameProperties()
	{
		return gameProperties;
	}
	
	public Game getGame()
	{
		return game;
	}
	
	public ScoreBoard getScoreBoard()
	{
		return scoreBoard;
	}
	
	public Welcome getWelcome()
	{
		return welcome;
	}
	
	public class Core
	{
		private double vBoxForSoundPrefHeight;
		private double vBoxForSoundPrefWidth;
		
		public double getvBoxForSoundPrefHeight()
		{
			return vBoxForSoundPrefHeight;
		}
		
		public void setvBoxForSoundPrefHeight(double vBoxForSoundPrefHeight)
		{
			this.vBoxForSoundPrefHeight = vBoxForSoundPrefHeight;
		}
		
		public double getvBoxForSoundPrefWidth()
		{
			return vBoxForSoundPrefWidth;
		}
		
		public void setvBoxForSoundPrefWidth(double vBoxForSoundPrefWidth)
		{
			this.vBoxForSoundPrefWidth = vBoxForSoundPrefWidth;
		}
	}
	
	public class Atlas
	{
		private double soundIconLayoutY;
		private double hBoxForToggleButtonsLayoutY;
		private double titleImageSetY;
		private double titleLabelSetX;
		private double titleLabelSetY;
		private double vBoxForSoundLayoutY;
		private double woodPanelFor1IconImageLayoutY;
		private double woodPanelFor1IconImageLayoutX;
		
		public double getSoundIconLayoutY()
		{
			return soundIconLayoutY;
		}
		
		public void setSoundIconLayoutY(double soundIconLayoutY)
		{
			this.soundIconLayoutY = soundIconLayoutY;
		}
		
		public double gethBoxForToggleButtonsLayoutY()
		{
			return hBoxForToggleButtonsLayoutY;
		}
		
		public void sethBoxForToggleButtonsLayoutY(double hBoxForToggleButtonsLayoutY)
		{
			this.hBoxForToggleButtonsLayoutY = hBoxForToggleButtonsLayoutY;
		}
		
		public double getTitleImageSetY()
		{
			return titleImageSetY;
		}
		
		public void setTitleImageSetY(double titleImageSetY)
		{
			this.titleImageSetY = titleImageSetY;
		}
		
		public double getTitleLabelSetX()
		{
			return titleLabelSetX;
		}
		
		public void setTitleLabelSetX(double titleLabelSetX)
		{
			this.titleLabelSetX = titleLabelSetX;
		}
		
		public double getTitleLabelSetY()
		{
			return titleLabelSetY;
		}
		
		public void setTitleLabelSetY(double titleLabelSetY)
		{
			this.titleLabelSetY = titleLabelSetY;
		}
		
		public double getvBoxForSoundLayoutY()
		{
			return vBoxForSoundLayoutY;
		}
		
		public void setvBoxForSoundLayoutY(double vBoxForSoundLayoutY)
		{
			this.vBoxForSoundLayoutY = vBoxForSoundLayoutY;
		}
		
		public double getWoodPanelFor1IconImageLayoutY()
		{
			return woodPanelFor1IconImageLayoutY;
		}
		
		public double getWoodPanelFor1IconImageLayoutX()
		{
			return woodPanelFor1IconImageLayoutX;
		}
		
		public void setWoodPanelFor1IconImageLayoutX(double woodPanelFor1IconImageLayoutX)
		{
			this.woodPanelFor1IconImageLayoutX = woodPanelFor1IconImageLayoutX;
		}
		
		public void setWoodPanelFor1IconImageLayoutY(double woodPanelFor1IconImageLayoutY)
		{
			this.woodPanelFor1IconImageLayoutY = woodPanelFor1IconImageLayoutY;
		}
	}
	
	public class GameProperties
	{
		private double titleImage1LayoutY_1;
		private double titleImage1LayoutY_2;
		private double titleLabel1LayoutX_1;
		private double titleLabel1LayoutX_2;
		private double titleLabel1LayoutY_1;
		private double titleLabel1LayoutY_2;
		private double titleImage2LayoutY;
		private double titleLabel2LayoutX;
		private double titleLabel2LayoutY;
		private double vBoxForSoundLayoutY;
		private double rectangleForDifficultyLevelWidth;
		private double rectangleForDifficultyLevelHeight;
		private double rectangleForDifficultyLevelLayoutY;
		private double rectangleForQuestionCategoriesWidth;
		private double rectangleForQuestionCategoriesHeight;
		private double rectangleForQuestionCategoriesLayoutY;
		private double vBoxForDifficultyLevelWidth;
		private double vBoxForDifficultyLevelHeight;
		private double vBoxForDifficultyLevelLayoutY;
		private double extendedQuestionCategoriesWidth;
		private double extendedQuestionCategoriesHeight;
		private double rectangleForExtendedQuestionCategoriesHeight;
		private double hBoxForGameModesWidth;
		private double hBoxForGameModesHeight;
		private double hBoxForGameModesLayoutY;
		private double buttonHeight;
		private double heightOfDescriptions;
		private double backButtonLayoutY;
		private double soundIconLayoutY;
		private double nextButtonLayoutY;
		private double woodPanelFor1IconImageLayoutX;
		private double woodPanelFor1IconImageLayoutY;
		
		public double getTitleImage1LayoutY_1()
		{
			return titleImage1LayoutY_1;
		}
		
		public void setTitleImage1LayoutY_1(double titleImage1LayoutY_1)
		{
			this.titleImage1LayoutY_1 = titleImage1LayoutY_1;
		}
		
		public double getTitleImage1LayoutY_2()
		{
			return titleImage1LayoutY_2;
		}
		
		public void setTitleImage1LayoutY_2(double titleImage1LayoutY_2)
		{
			this.titleImage1LayoutY_2 = titleImage1LayoutY_2;
		}
		
		public double getTitleLabel1LayoutX_1()
		{
			return titleLabel1LayoutX_1;
		}
		
		public void setTitleLabel1LayoutX_1(double titleLabel1LayoutX_1)
		{
			this.titleLabel1LayoutX_1 = titleLabel1LayoutX_1;
		}
		
		public double getTitleLabel1LayoutX_2()
		{
			return titleLabel1LayoutX_2;
		}
		
		public void setTitleLabel1LayoutX_2(double titleLabel1LayoutX_2)
		{
			this.titleLabel1LayoutX_2 = titleLabel1LayoutX_2;
		}
		
		public double getTitleLabel1LayoutY_1()
		{
			return titleLabel1LayoutY_1;
		}
		
		public void setTitleLabel1LayoutY_1(double titleLabel1LayoutY_1)
		{
			this.titleLabel1LayoutY_1 = titleLabel1LayoutY_1;
		}
		
		public double getTitleLabel1LayoutY_2()
		{
			return titleLabel1LayoutY_2;
		}
		
		public void setTitleLabel1LayoutY_2(double titleLabel1LayoutY_2)
		{
			this.titleLabel1LayoutY_2 = titleLabel1LayoutY_2;
		}
		
		public double getTitleImage2LayoutY()
		{
			return titleImage2LayoutY;
		}
		
		public void setTitleImage2LayoutY(double titleImage2LayoutY)
		{
			this.titleImage2LayoutY = titleImage2LayoutY;
		}
		
		public double getTitleLabel2LayoutX()
		{
			return titleLabel2LayoutX;
		}
		
		public void setTitleLabel2LayoutX(double titleLabel2LayoutX)
		{
			this.titleLabel2LayoutX = titleLabel2LayoutX;
		}
		
		public double getTitleLabel2LayoutY()
		{
			return titleLabel2LayoutY;
		}
		
		public void setTitleLabel2LayoutY(double titleLabel2LayoutY)
		{
			this.titleLabel2LayoutY = titleLabel2LayoutY;
		}
		
		public double getvBoxForSoundLayoutY()
		{
			return vBoxForSoundLayoutY;
		}
		
		public void setvBoxForSoundLayoutY(double vBoxForSoundLayoutY)
		{
			this.vBoxForSoundLayoutY = vBoxForSoundLayoutY;
		}
		
		public double getRectangleForDifficultyLevelWidth()
		{
			return rectangleForDifficultyLevelWidth;
		}
		
		public void setRectangleForDifficultyLevelWidth(double rectangleForDifficultyLevelWidth)
		{
			this.rectangleForDifficultyLevelWidth = rectangleForDifficultyLevelWidth;
		}
		
		public double getRectangleForDifficultyLevelHeight()
		{
			return rectangleForDifficultyLevelHeight;
		}
		
		public void setRectangleForDifficultyLevelHeight(double rectangleForDifficultyLevelHeight)
		{
			this.rectangleForDifficultyLevelHeight = rectangleForDifficultyLevelHeight;
		}
		
		public double getRectangleForDifficultyLevelLayoutY()
		{
			return rectangleForDifficultyLevelLayoutY;
		}
		
		public void setRectangleForDifficultyLevelLayoutY(double rectangleForDifficultyLevelLayoutY)
		{
			this.rectangleForDifficultyLevelLayoutY = rectangleForDifficultyLevelLayoutY;
		}
		
		public double getRectangleForQuestionCategoriesWidth()
		{
			return rectangleForQuestionCategoriesWidth;
		}
		
		public void setRectangleForQuestionCategoriesWidth(double rectangleForQuestionCategoriesWidth)
		{
			this.rectangleForQuestionCategoriesWidth = rectangleForQuestionCategoriesWidth;
		}
		
		public double getRectangleForQuestionCategoriesHeight()
		{
			return rectangleForQuestionCategoriesHeight;
		}
		
		public void setRectangleForQuestionCategoriesHeight(double rectangleForQuestionCategoriesHeight)
		{
			this.rectangleForQuestionCategoriesHeight = rectangleForQuestionCategoriesHeight;
		}
		
		public double getRectangleForQuestionCategoriesLayoutY()
		{
			return rectangleForQuestionCategoriesLayoutY;
		}
		
		public void setRectangleForQuestionCategoriesLayoutY(double rectangleForQuestionCategoriesLayoutY)
		{
			this.rectangleForQuestionCategoriesLayoutY = rectangleForQuestionCategoriesLayoutY;
		}
		
		public double getvBoxForDifficultyLevelWidth()
		{
			return vBoxForDifficultyLevelWidth;
		}
		
		public void setvBoxForDifficultyLevelWidth(double vBoxForDifficultyLevelWidth)
		{
			this.vBoxForDifficultyLevelWidth = vBoxForDifficultyLevelWidth;
		}
		
		public double getvBoxForDifficultyLevelHeight()
		{
			return vBoxForDifficultyLevelHeight;
		}
		
		public void setvBoxForDifficultyLevelHeight(double vBoxForDifficultyLevelHeight)
		{
			this.vBoxForDifficultyLevelHeight = vBoxForDifficultyLevelHeight;
		}
		
		public double getvBoxForDifficultyLevelLayoutY()
		{
			return vBoxForDifficultyLevelLayoutY;
		}
		
		public void setvBoxForDifficultyLevelLayoutY(double vBoxForDifficultyLevelLayoutY)
		{
			this.vBoxForDifficultyLevelLayoutY = vBoxForDifficultyLevelLayoutY;
		}
		
		public double getExtendedQuestionCategoriesWidth()
		{
			return extendedQuestionCategoriesWidth;
		}
		
		public void setExtendedQuestionCategoriesWidth(double extendedQuestionCategoriesWidth)
		{
			this.extendedQuestionCategoriesWidth = extendedQuestionCategoriesWidth;
		}
		
		public double getExtendedQuestionCategoriesHeight()
		{
			return extendedQuestionCategoriesHeight;
		}
		
		public void setExtendedQuestionCategoriesHeight(double extendedQuestionCategoriesHeight)
		{
			this.extendedQuestionCategoriesHeight = extendedQuestionCategoriesHeight;
		}
		
		public double getRectangleForExtendedQuestionCategoriesHeight()
		{
			return rectangleForExtendedQuestionCategoriesHeight;
		}
		
		public void setRectangleForExtendedQuestionCategoriesHeight(double rectangleForExtendedQuestionCategoriesHeight)
		{
			this.rectangleForExtendedQuestionCategoriesHeight = rectangleForExtendedQuestionCategoriesHeight;
		}
		
		public double gethBoxForGameModesWidth()
		{
			return hBoxForGameModesWidth;
		}
		
		public void sethBoxForGameModesWidth(double hBoxForGameModesWidth)
		{
			this.hBoxForGameModesWidth = hBoxForGameModesWidth;
		}
		
		public double gethBoxForGameModesHeight()
		{
			return hBoxForGameModesHeight;
		}
		
		public void sethBoxForGameModesHeight(double hBoxForGameModesHeight)
		{
			this.hBoxForGameModesHeight = hBoxForGameModesHeight;
		}
		
		public double gethBoxForGameModesLayoutY()
		{
			return hBoxForGameModesLayoutY;
		}
		
		public void sethBoxForGameModesLayoutY(double hBoxForGameModesLayoutY)
		{
			this.hBoxForGameModesLayoutY = hBoxForGameModesLayoutY;
		}
		
		public double getButtonHeight()
		{
			return buttonHeight;
		}
		
		public void setButtonHeight(double buttonHeight)
		{
			this.buttonHeight = buttonHeight;
		}
		
		public double getHeightOfDescriptions()
		{
			return heightOfDescriptions;
		}
		
		public void setHeightOfDescriptions(double heightOfDescriptions)
		{
			this.heightOfDescriptions = heightOfDescriptions;
		}
		
		public double getBackButtonLayoutY()
		{
			return backButtonLayoutY;
		}
		
		public void setBackButtonLayoutY(double backButtonLayoutY)
		{
			this.backButtonLayoutY = backButtonLayoutY;
		}
		
		public double getSoundIconLayoutY()
		{
			return soundIconLayoutY;
		}
		
		public void setSoundIconLayoutY(double soundIconLayoutY)
		{
			this.soundIconLayoutY = soundIconLayoutY;
		}
		
		public double getNextButtonLayoutY()
		{
			return nextButtonLayoutY;
		}
		
		public void setNextButtonLayoutY(double nextButtonLayoutY)
		{
			this.nextButtonLayoutY = nextButtonLayoutY;
		}
		
		public double getWoodPanelFor1IconImageLayoutX()
		{
			return woodPanelFor1IconImageLayoutX;
		}
		
		public void setWoodPanelFor1IconImageLayoutX(double woodPanelFor1IconImageLayoutX)
		{
			this.woodPanelFor1IconImageLayoutX = woodPanelFor1IconImageLayoutX;
		}
		
		public double getWoodPanelFor1IconImageLayoutY()
		{
			return woodPanelFor1IconImageLayoutY;
		}
		
		public void setWoodPanelFor1IconImageLayoutY(double woodPanelFor1IconImageLayoutY)
		{
			this.woodPanelFor1IconImageLayoutY = woodPanelFor1IconImageLayoutY;
		}
	}
	
	public class Game
	{
		private double soundIconLayoutY;
		private double vBoxForSoundLayoutY;
		private double woodPanelFor1IconImageLayoutX;
		private double woodPanelFor1IconImageLayoutY;
		
		public double getSoundIconLayoutY()
		{
			return soundIconLayoutY;
		}
		
		public void setSoundIconLayoutY(double soundIconLayoutY)
		{
			this.soundIconLayoutY = soundIconLayoutY;
		}
		
		public double getvBoxForSoundLayoutY()
		{
			return vBoxForSoundLayoutY;
		}
		
		public void setvBoxForSoundLayoutY(double vBoxForSoundLayoutY)
		{
			this.vBoxForSoundLayoutY = vBoxForSoundLayoutY;
		}
		
		public double getWoodPanelFor1IconImageLayoutX()
		{
			return woodPanelFor1IconImageLayoutX;
		}
		
		public void setWoodPanelFor1IconImageLayoutX(double woodPanelFor1IconImageLayoutX)
		{
			this.woodPanelFor1IconImageLayoutX = woodPanelFor1IconImageLayoutX;
		}
		
		public double getWoodPanelFor1IconImageLayoutY()
		{
			return woodPanelFor1IconImageLayoutY;
		}
		
		public void setWoodPanelFor1IconImageLayoutY(double woodPanelFor1IconImageLayoutY)
		{
			this.woodPanelFor1IconImageLayoutY = woodPanelFor1IconImageLayoutY;
		}
	}
	
	public class ScoreBoard
	{
		private double soundIconLayoutY;
		private double titleImageSetY;
		private double titleLabelSetX;
		private double titleLabelSetY;
		private double vBoxForSoundLayoutY;
		private double woodPanelFor1IconImageLayoutX;
		private double woodPanelFor1IconImageLayoutY;
		
		public double getSoundIconLayoutY()
		{
			return soundIconLayoutY;
		}
		
		public void setSoundIconLayoutY(double soundIconLayoutY)
		{
			this.soundIconLayoutY = soundIconLayoutY;
		}
		
		public double getTitleImageSetY()
		{
			return titleImageSetY;
		}
		
		public void setTitleImageSetY(double titleImageSetY)
		{
			this.titleImageSetY = titleImageSetY;
		}
		
		public double getTitleLabelSetX()
		{
			return titleLabelSetX;
		}
		
		public void setTitleLabelSetX(double titleLabelSetX)
		{
			this.titleLabelSetX = titleLabelSetX;
		}
		
		public double getTitleLabelSetY()
		{
			return titleLabelSetY;
		}
		
		public void setTitleLabelSetY(double titleLabelSetY)
		{
			this.titleLabelSetY = titleLabelSetY;
		}
		
		public double getvBoxForSoundLayoutY()
		{
			return vBoxForSoundLayoutY;
		}
		
		public void setvBoxForSoundLayoutY(double vBoxForSoundLayoutY)
		{
			this.vBoxForSoundLayoutY = vBoxForSoundLayoutY;
		}
		
		public double getWoodPanelFor1IconImageLayoutX()
		{
			return woodPanelFor1IconImageLayoutX;
		}
		
		public void setWoodPanelFor1IconImageLayoutX(double woodPanelFor1IconImageLayoutX)
		{
			this.woodPanelFor1IconImageLayoutX = woodPanelFor1IconImageLayoutX;
		}
		
		public double getWoodPanelFor1IconImageLayoutY()
		{
			return woodPanelFor1IconImageLayoutY;
		}
		
		public void setWoodPanelFor1IconImageLayoutY(double woodPanelFor1IconImageLayoutY)
		{
			this.woodPanelFor1IconImageLayoutY = woodPanelFor1IconImageLayoutY;
		}
	}
	
	public class Welcome
	{
		private double editNameIconLayoutY;
		private double gameNameImageLayoutY;
		private double globeImageFitWidth;
		private double globeImageLayoutY;
		private double globeStandFitWidth;
		private double globeStandLayoutY;
		private double hBoxForSettingsAndInfoIconsLayoutY;
		private double leftGlobeImageLayoutX;
		private double leftGlobeStandLayoutX;
		private double rectangleForInfoAboutGameLayoutY;
		private double rightGlobeImageLayoutX;
		private double soundIconLayoutY;
		private double usersEditSegmentedButtonLayoutY;
		private double vBoxesForEditUsersLayoutY;
		private double vBoxForMainButtonsLayoutY;
		private double vBoxForMainButtonsPrefHeight;
		private double vBoxForMainButtonsPrefWidth;
		private double vBoxForMainButtonsSpacing;
		private double vBoxForSettingsLayoutY;
		private double vBoxForSoundLayoutX;
		private double vBoxForSoundLayoutY;
		private double welcomeImageLayoutY;
		private double welcomeLabelLayoutY;
		private double woodPanelFor1IconImageLayoutX;
		private double woodPanelFor1IconImageLayoutY;
		
		public double getEditNameIconLayoutY()
		{
			return editNameIconLayoutY;
		}
		
		public void setEditNameIconLayoutY(double editNameIconLayoutY)
		{
			this.editNameIconLayoutY = editNameIconLayoutY;
		}
		
		public double getGameNameImageLayoutY()
		{
			return gameNameImageLayoutY;
		}
		
		public void setGameNameImageLayoutY(double gameNameImageLayoutY)
		{
			this.gameNameImageLayoutY = gameNameImageLayoutY;
		}
		
		public double getGlobeImageFitWidth()
		{
			return globeImageFitWidth;
		}
		
		public void setGlobeImageFitWidth(double globeImageFitWidth)
		{
			this.globeImageFitWidth = globeImageFitWidth;
		}
		
		public double getGlobeImageLayoutY()
		{
			return globeImageLayoutY;
		}
		
		public void setGlobeImageLayoutY(double globeImageLayoutY)
		{
			this.globeImageLayoutY = globeImageLayoutY;
		}
		
		public double getGlobeStandFitWidth()
		{
			return globeStandFitWidth;
		}
		
		public void setGlobeStandFitWidth(double globeStandFitWidth)
		{
			this.globeStandFitWidth = globeStandFitWidth;
		}
		
		public double getGlobeStandLayoutY()
		{
			return globeStandLayoutY;
		}
		
		public void setGlobeStandLayoutY(double globeStandLayoutY)
		{
			this.globeStandLayoutY = globeStandLayoutY;
		}
		
		public double gethBoxForSettingsAndInfoIconsLayoutY()
		{
			return hBoxForSettingsAndInfoIconsLayoutY;
		}
		
		public void sethBoxForSettingsAndInfoIconsLayoutY(double hBoxForSettingsAndInfoIconsLayoutY)
		{
			this.hBoxForSettingsAndInfoIconsLayoutY = hBoxForSettingsAndInfoIconsLayoutY;
		}
		
		public double getLeftGlobeImageLayoutX()
		{
			return leftGlobeImageLayoutX;
		}
		
		public void setLeftGlobeImageLayoutX(double leftGlobeImageLayoutX)
		{
			this.leftGlobeImageLayoutX = leftGlobeImageLayoutX;
		}
		
		public double getLeftGlobeStandLayoutX()
		{
			return leftGlobeStandLayoutX;
		}
		
		public void setLeftGlobeStandLayoutX(double leftGlobeStandLayoutX)
		{
			this.leftGlobeStandLayoutX = leftGlobeStandLayoutX;
		}
		
		public double getRectangleForInfoAboutGameLayoutY()
		{
			return rectangleForInfoAboutGameLayoutY;
		}
		
		public void setRectangleForInfoAboutGameLayoutY(double rectangleForInfoAboutGameLayoutY)
		{
			this.rectangleForInfoAboutGameLayoutY = rectangleForInfoAboutGameLayoutY;
		}
		
		public double getRightGlobeImageLayoutX()
		{
			return rightGlobeImageLayoutX;
		}
		
		public void setRightGlobeImageLayoutX(double rightGlobeImageLayoutX)
		{
			this.rightGlobeImageLayoutX = rightGlobeImageLayoutX;
		}
		
		public double getSoundIconLayoutY()
		{
			return soundIconLayoutY;
		}
		
		public void setSoundIconLayoutY(double soundIconLayoutY)
		{
			this.soundIconLayoutY = soundIconLayoutY;
		}
		
		public double getUsersEditSegmentedButtonLayoutY()
		{
			return usersEditSegmentedButtonLayoutY;
		}
		
		public void setUsersEditSegmentedButtonLayoutY(double usersEditSegmentedButtonLayoutY)
		{
			this.usersEditSegmentedButtonLayoutY = usersEditSegmentedButtonLayoutY;
		}
		
		public double getvBoxesForEditUsersLayoutY()
		{
			return vBoxesForEditUsersLayoutY;
		}
		
		public void setvBoxesForEditUsersLayoutY(double vBoxesForEditUsersLayoutY)
		{
			this.vBoxesForEditUsersLayoutY = vBoxesForEditUsersLayoutY;
		}
		
		public double getvBoxForMainButtonsLayoutY()
		{
			return vBoxForMainButtonsLayoutY;
		}
		
		public void setvBoxForMainButtonsLayoutY(double vBoxForMainButtonsLayoutY)
		{
			this.vBoxForMainButtonsLayoutY = vBoxForMainButtonsLayoutY;
		}
		
		public double getvBoxForMainButtonsPrefHeight()
		{
			return vBoxForMainButtonsPrefHeight;
		}
		
		public void setvBoxForMainButtonsPrefHeight(double vBoxForMainButtonsPrefHeight)
		{
			this.vBoxForMainButtonsPrefHeight = vBoxForMainButtonsPrefHeight;
		}
		
		public double getvBoxForMainButtonsPrefWidth()
		{
			return vBoxForMainButtonsPrefWidth;
		}
		
		public void setvBoxForMainButtonsPrefWidth(double vBoxForMainButtonsPrefWidth)
		{
			this.vBoxForMainButtonsPrefWidth = vBoxForMainButtonsPrefWidth;
		}
		
		public double getvBoxForMainButtonsSpacing()
		{
			return vBoxForMainButtonsSpacing;
		}
		
		public void setvBoxForMainButtonsSpacing(double vBoxForMainButtonsSpacing)
		{
			this.vBoxForMainButtonsSpacing = vBoxForMainButtonsSpacing;
		}
		
		public double getvBoxForSettingsLayoutY()
		{
			return vBoxForSettingsLayoutY;
		}
		
		public void setvBoxForSettingsLayoutY(double vBoxForSettingsLayoutY)
		{
			this.vBoxForSettingsLayoutY = vBoxForSettingsLayoutY;
		}
		
		public double getvBoxForSoundLayoutX()
		{
			return vBoxForSoundLayoutX;
		}
		
		public void setvBoxForSoundLayoutX(double vBoxForSoundLayoutX)
		{
			this.vBoxForSoundLayoutX = vBoxForSoundLayoutX;
		}
		
		public double getvBoxForSoundLayoutY()
		{
			return vBoxForSoundLayoutY;
		}
		
		public void setvBoxForSoundLayoutY(double vBoxForSoundLayoutY)
		{
			this.vBoxForSoundLayoutY = vBoxForSoundLayoutY;
		}
		
		public double getWelcomeImageLayoutY()
		{
			return welcomeImageLayoutY;
		}
		
		public void setWelcomeImageLayoutY(double welcomeImageLayoutY)
		{
			this.welcomeImageLayoutY = welcomeImageLayoutY;
		}
		
		public double getWelcomeLabelLayoutY()
		{
			return welcomeLabelLayoutY;
		}
		
		public void setWelcomeLabelLayoutY(double welcomeLabelLayoutY)
		{
			this.welcomeLabelLayoutY = welcomeLabelLayoutY;
		}
		
		public double getWoodPanelFor1IconImageLayoutX()
		{
			return woodPanelFor1IconImageLayoutX;
		}
		
		public void setWoodPanelFor1IconImageLayoutX(double woodPanelFor1IconImageLayoutX)
		{
			this.woodPanelFor1IconImageLayoutX = woodPanelFor1IconImageLayoutX;
		}
		
		public double getWoodPanelFor1IconImageLayoutY()
		{
			return woodPanelFor1IconImageLayoutY;
		}
		
		public void setWoodPanelFor1IconImageLayoutY(double woodPanelFor1IconImageLayoutY)
		{
			this.woodPanelFor1IconImageLayoutY = woodPanelFor1IconImageLayoutY;
		}
	}
}
