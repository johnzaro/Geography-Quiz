package code.dataStructures;

public class RatioProperties
{
	private Atlas atlas;
	private GameProperties gameProperties;
	private Game game;
	private ScoreBoard scoreBoard;
	private Welcome welcome;
	
	public RatioProperties()
	{
		atlas = new Atlas();
		gameProperties = new GameProperties();
		game = new Game();
		scoreBoard = new ScoreBoard();
		welcome = new Welcome();
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
	
	public class Atlas
	{
		private double hBoxFor5IconsLayoutY;
		private double hBoxForToggleButtonsLayoutY;
		private double titleImageSetY;
		private double titleLabelSetX;
		private double titleLabelSetY;
		private double vBoxForSoundLayoutY;
		private double vBoxForSoundPrefHeight;
		private double vBoxForSoundPrefWidth;
		private double woodPanelFor5IconsImageLayoutY;
		
		public double gethBoxFor5IconsLayoutY()
		{
			return hBoxFor5IconsLayoutY;
		}
		
		public void sethBoxFor5IconsLayoutY(double hBoxFor5IconsLayoutY)
		{
			this.hBoxFor5IconsLayoutY = hBoxFor5IconsLayoutY;
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
		
		public double getWoodPanelFor5IconsImageLayoutY()
		{
			return woodPanelFor5IconsImageLayoutY;
		}
		
		public void setWoodPanelFor5IconsImageLayoutY(double woodPanelFor5IconsImageLayoutY)
		{
			this.woodPanelFor5IconsImageLayoutY = woodPanelFor5IconsImageLayoutY;
		}
	}
	
	public class GameProperties
	{
		private double backButtonLayoutY;
		private double hBoxFor5IconsLayoutY;
		private double nextButtonLayoutY;
		private double woodPanelFor5IconsImageLayoutY;
		
		public double getBackButtonLayoutY()
		{
			return backButtonLayoutY;
		}
		
		public void setBackButtonLayoutY(double backButtonLayoutY)
		{
			this.backButtonLayoutY = backButtonLayoutY;
		}
		
		public double gethBoxFor5IconsLayoutY()
		{
			return hBoxFor5IconsLayoutY;
		}
		
		public void sethBoxFor5IconsLayoutY(double hBoxFor5IconsLayoutY)
		{
			this.hBoxFor5IconsLayoutY = hBoxFor5IconsLayoutY;
		}
		
		public double getNextButtonLayoutY()
		{
			return nextButtonLayoutY;
		}
		
		public void setNextButtonLayoutY(double nextButtonLayoutY)
		{
			this.nextButtonLayoutY = nextButtonLayoutY;
		}
		
		public double getWoodPanelFor5IconsImageLayoutY()
		{
			return woodPanelFor5IconsImageLayoutY;
		}
		
		public void setWoodPanelFor5IconsImageLayoutY(double woodPanelFor5IconsImageLayoutY)
		{
			this.woodPanelFor5IconsImageLayoutY = woodPanelFor5IconsImageLayoutY;
		}
	}
	
	public class Game
	{
		private double hBoxFor5IconsLayoutY;
		private double vBoxForSoundLayoutY;
		private double vBoxForSoundPrefHeight;
		private double vBoxForSoundPrefWidth;
		private double woodPanelFor5IconsImageLayoutY;
		
		public double gethBoxFor5IconsLayoutY()
		{
			return hBoxFor5IconsLayoutY;
		}
		
		public void sethBoxFor5IconsLayoutY(double hBoxFor5IconsLayoutY)
		{
			this.hBoxFor5IconsLayoutY = hBoxFor5IconsLayoutY;
		}
		
		public double getvBoxForSoundLayoutY()
		{
			return vBoxForSoundLayoutY;
		}
		
		public void setvBoxForSoundLayoutY(double vBoxForSoundLayoutY)
		{
			this.vBoxForSoundLayoutY = vBoxForSoundLayoutY;
		}
		
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
		
		public double getWoodPanelFor5IconsImageLayoutY()
		{
			return woodPanelFor5IconsImageLayoutY;
		}
		
		public void setWoodPanelFor5IconsImageLayoutY(double woodPanelFor5IconsImageLayoutY)
		{
			this.woodPanelFor5IconsImageLayoutY = woodPanelFor5IconsImageLayoutY;
		}
	}
	
	public class ScoreBoard
	{
		private double hBoxFor5IconsLayoutY;
		private double titleImageSetY;
		private double titleLabelSetX;
		private double titleLabelSetY;
		private double vBoxForSoundLayoutY;
		private double vBoxForSoundPrefHeight;
		private double vBoxForSoundPrefWidth;
		private double woodPanelFor5IconsImageLayoutY;
		
		public double gethBoxFor5IconsLayoutY()
		{
			return hBoxFor5IconsLayoutY;
		}
		
		public void sethBoxFor5IconsLayoutY(double hBoxFor5IconsLayoutY)
		{
			this.hBoxFor5IconsLayoutY = hBoxFor5IconsLayoutY;
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
		
		public double getWoodPanelFor5IconsImageLayoutY()
		{
			return woodPanelFor5IconsImageLayoutY;
		}
		
		public void setWoodPanelFor5IconsImageLayoutY(double woodPanelFor5IconsImageLayoutY)
		{
			this.woodPanelFor5IconsImageLayoutY = woodPanelFor5IconsImageLayoutY;
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
		private double hBoxFor4SettingsButtonsLayoutY;
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
		private double vBoxForSoundPrefHeight;
		private double welcomeImageLayoutY;
		private double welcomeLabelLayoutY;
		private double woodPanelFor1IconImageLayoutX;
		private double woodPanelFor1IconImageLayoutY;
		private double woodPanelFor4IconsImageLayoutX;
		private double woodPanelFor4IconsImageLayoutY;
		
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
		
		public double gethBoxFor4SettingsButtonsLayoutY()
		{
			return hBoxFor4SettingsButtonsLayoutY;
		}
		
		public void sethBoxFor4SettingsButtonsLayoutY(double hBoxFor4SettingsButtonsLayoutY)
		{
			this.hBoxFor4SettingsButtonsLayoutY = hBoxFor4SettingsButtonsLayoutY;
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
		
		public double getvBoxForSoundPrefHeight()
		{
			return vBoxForSoundPrefHeight;
		}
		
		public void setvBoxForSoundPrefHeight(double vBoxForSoundPrefHeight)
		{
			this.vBoxForSoundPrefHeight = vBoxForSoundPrefHeight;
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
		
		public double getWoodPanelFor4IconsImageLayoutX()
		{
			return woodPanelFor4IconsImageLayoutX;
		}
		
		public void setWoodPanelFor4IconsImageLayoutX(double woodPanelFor4IconsImageLayoutX)
		{
			this.woodPanelFor4IconsImageLayoutX = woodPanelFor4IconsImageLayoutX;
		}
		
		public double getWoodPanelFor4IconsImageLayoutY()
		{
			return woodPanelFor4IconsImageLayoutY;
		}
		
		public void setWoodPanelFor4IconsImageLayoutY(double woodPanelFor4IconsImageLayoutY)
		{
			this.woodPanelFor4IconsImageLayoutY = woodPanelFor4IconsImageLayoutY;
		}
	}
}
