package code.screens;

import code.core.*;
import javafx.animation.*;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static code.core.GlobalVariables.*;
import static code.core.GlobalVariables.isTimerForClassic;

/**
 * Created by John on 23/8/2016.
 */

public class GamePropertiesScreen extends CoreScreenWithMovingBackground
{
	private CustomCheckBox[] extQCCategories, extQCCountries, extQCUSA, extQCGreece;
	private CustomCheckBox continentsAndCountriesCheckBox, USACheckBox, greeceCheckBox, attractionsCheckBox;
	
	private ImageView previousImage, titleImage1, titleImage2,  woodPanelFor5IconsImage,
			backArrowImage, nextArrowImage, exitExtendedCategoryQuestions;
	private Button classicGameButton, timeAttackGameButton, endlessGameButton, backButton, nextButton;
	private SwitchButton countDownToggleForClassicGameLabel, countDownToggleForEndlessGameLabel;
	private Label titleLabel1, titleLabel2, extendedQuestionCategories, numberOfQuestionsForClassicGameLabel, countDownForClassicGameLabel, descriptionForClassicGameLabel,
			gameDurationForTimeAttackGameLabel, descriptionForTimeAttackGameLabel, livesForEndlessGameLabel, countDownForEndlessGameLabel, descriptionForEndlessGameLabel;
	private ScrollPane scrollPaneForExtendedCategoryQuestionsGridPane;
	private RadioButton easyLevelRadioButton, difficultLevelRadioButton, radioButton10QuestionsForClassicGameLabel,
			radioButton20QuestionsForClassicGameLabel, radioButton50QuestionsForClassicGameLabel, radioButton100QuestionsForClassicGameLabel,
			radioButton1MinuteDurationForTimeAttackGameLabel, radioButton2MinutesDurationForTimeAttackGameLabel, radioButton5MinutesDurationForTimeAttackGameLabel,
			radioButton10MinutesDurationForTimeAttackGameLabel, radioButton1LifeForEndlessGameLabel, radioButton3LivesForEndlessGameLabel,
			radioButton5LivesForEndlessGameLabel;
	
	private ToggleGroup toggleGroupForDifficultyLevel, toggleGroupForClassicGame, toggleGroupForTimeAttackGame, toggleGroupForEndlessGame;
	private InnerShadow innerShadow;
	private DropShadow dropShadow;
	private HBox hBoxFor5Icons, hBoxForGameModes, hBoxForNumberOfQuestionsForClassicGame, hBoxForCountDownForClassicGame,
			hBoxForGameDurationForTimeAttackGame, hBoxEmpty, hBoxForLivesForEndlessGame, hBoxForCountDownForEndlessGame;
	private VBox vBoxForDifficultyLevel, vBoxForClassicGame, vBoxForTimeAttackGame, vBoxForEndlessGame;
	private GridPane gridPaneForQuestionsCategories, gridPaneForExtendedQuestionCategories;
	private Rectangle rectangleForDifficultyLevel, rectangleForQuestionCategories, rectangleForExtendedQuestionCategories;
	private TranslateTransition translateTransitionForTitleImage1, translateTransitionForTitleLabel1, translateTransitionForVBoxForSound, translateTransitionForWoodImageFor5Icons;
	private FadeTransition fadeTransitionForMovingEarthImage;
	private ScaleTransition scaleTransitionForTitleLabel1, scaleTransitionForTitleLabel2, scaleTransitionForHBoxFor5Icons, scaleTransitionForBackButton,
			scaleTransitionForNextButton, scaleTransitionForVBoxForDifficultyLevel, scaleTransitionForGridPaneForQuestionsCategories, scaleTransitionForExtendedQuestionCategoriesLabel,
			scaleTransitionForScrollPaneForExtendedQuestionCategories, scaleTransitionForRectangleForExtendedQuestionCategories, scaleTransitionForExitExtendedCategoryQuestions,
			scaleTransitionForRectangleForDifficultyLevel, scaleTransitionForRectangleForQuestionsCategories, scaleTransitionForVBoxForClassicGame,
			scaleTransitionForVBoxForTimeAttackGame, scaleTransitionForVBoxForEndlessGame;
	private ParallelTransition parallelTransitionForTitleImage2;
	private Timeline timelineToShowSoundOptions, timelineToHideSoundOptions, timeLineToShowAllStuff, timeLineToHideAllStuffFromScreen1,
			timeLineToHideAllStuffFromScreen2, timeLineFromScreen1ToScreen2, timeLineFromScreen2ToScreen1;
	
	private boolean screen1 = true, isExtendedQuestionCategoriesOpen = false, pressedOutsideExtendedQuestionCategories = false;
	
	private Handler handler = new Handler();
		
	protected void recalculateUI(double width, double height)
	{
		double iconSize  = 0.0260 * width;
		
		Font fontForButtons  = Font.font("Comic Sans MS", FontWeight.BOLD, 0.0234 * width);
		Font fontForTooltips = Font.font("Comic Sans MS", 0.0130 * width);
		Font fontForArrowButtons = Font.font("Comic Sans MS", FontWeight.BOLD, 0.0156 * width);
		
		if (width < 1200)
		{
			easyLevelRadioButton.setId("small");
			difficultLevelRadioButton.setId("small");
			radioButton10QuestionsForClassicGameLabel.setId("small");
			radioButton20QuestionsForClassicGameLabel.setId("small");
			radioButton50QuestionsForClassicGameLabel.setId("small");
			radioButton100QuestionsForClassicGameLabel.setId("small");
			radioButton1MinuteDurationForTimeAttackGameLabel.setId("small");
			radioButton2MinutesDurationForTimeAttackGameLabel.setId("small");
			radioButton5MinutesDurationForTimeAttackGameLabel.setId("small");
			radioButton10MinutesDurationForTimeAttackGameLabel.setId("small");
			radioButton1LifeForEndlessGameLabel.setId("small");
			radioButton3LivesForEndlessGameLabel.setId("small");
			radioButton5LivesForEndlessGameLabel.setId("small");
		}
		else
		{
			easyLevelRadioButton.setId("big");
			difficultLevelRadioButton.setId("big");
			radioButton10QuestionsForClassicGameLabel.setId("big");
			radioButton20QuestionsForClassicGameLabel.setId("big");
			radioButton50QuestionsForClassicGameLabel.setId("big");
			radioButton100QuestionsForClassicGameLabel.setId("big");
			radioButton1MinuteDurationForTimeAttackGameLabel.setId("big");
			radioButton2MinutesDurationForTimeAttackGameLabel.setId("big");
			radioButton5MinutesDurationForTimeAttackGameLabel.setId("big");
			radioButton10MinutesDurationForTimeAttackGameLabel.setId("big");
			radioButton1LifeForEndlessGameLabel.setId("big");
			radioButton3LivesForEndlessGameLabel.setId("big");
			radioButton5LivesForEndlessGameLabel.setId("big");
		}
		
		if(screen1)
		{
			if (width < 1100)
			{
				masterVolumeSlider.setId("small");
				musicVolumeSlider.setId("small");
				soundEffectsVolumeSlider.setId("small");
			}
			else if (width < 1700)
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
			
			Font fontForLabels = Font.font("Comic Sans MS", 0.0182 * width);
			
			if (getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_9)
			{
				titleImage1.setLayoutY(0.0574 * height);
				titleLabel1.setLayoutX(0.2479 * width);
				titleLabel1.setLayoutY(0.0852 * height);
				
				titleImage2.setLayoutY(0.2040 * height);
				titleLabel2.setLayoutX(0.2479 * width);
				titleLabel2.setLayoutY(0.4550 * height);
				
				vBoxForSound.setLayoutY(0.1940 * height);
				
				rectangleForDifficultyLevel.setWidth(0.2343 * width);
				rectangleForDifficultyLevel.setHeight(0.2030 * height);
				rectangleForDifficultyLevel.setLayoutY(0.2320 * height);
				
				rectangleForQuestionCategories.setWidth(0.3950 * width);
				rectangleForQuestionCategories.setHeight(0.2590 * height);
				rectangleForQuestionCategories.setLayoutY(0.5980 * height);
				
				vBoxForDifficultyLevel.setPrefSize(0.1170 * width, 0.1666 * height);
				if (width < 1050) vBoxForDifficultyLevel.setLayoutY(0.2410 * height);
				else if (width < 1200) vBoxForDifficultyLevel.setLayoutY(0.2450 * height);
				else vBoxForDifficultyLevel.setLayoutY(0.2500 * height);
				
				extendedQuestionCategories.setPrefSize(0.2604 * width, 0.0463 * height);
				
				rectangleForExtendedQuestionCategories.setHeight(0.6926 * height);
			}
			else if (getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_10)
			{
				titleImage1.setLayoutY(0.0574 * height);
				titleLabel1.setLayoutX(0.2470 * width);
				titleLabel1.setLayoutY(0.0838 * height);
				
				titleImage2.setLayoutY(0.1940 * height);
				titleLabel2.setLayoutX(0.2479 * width);
				titleLabel2.setLayoutY(0.4140 * height);
				
				vBoxForSound.setLayoutY(0.1723 * height);
				
				rectangleForDifficultyLevel.setWidth(0.2343 * width);
				rectangleForDifficultyLevel.setHeight(0.1930 * height);
				rectangleForDifficultyLevel.setLayoutY(0.2120 * height);
				
				rectangleForQuestionCategories.setWidth(0.4000 * width);
				rectangleForQuestionCategories.setHeight(0.2590 * height);
				rectangleForQuestionCategories.setLayoutY(0.546 * height);
				
				vBoxForDifficultyLevel.setPrefSize(0.1181 * width, 0.1666 * height);
				vBoxForDifficultyLevel.setLayoutY(0.2250 * height);
				
				extendedQuestionCategories.setPrefSize(0.2604 * width, 0.0463 * height);
				
				rectangleForExtendedQuestionCategories.setHeight(0.7121 * height);
			}
			else if (getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_25_16 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_3_2)
			{
				if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_25_16)
				{
					titleImage2.setLayoutY(0.1904 * height);
					titleLabel2.setLayoutY(0.4053 * height);
						
					rectangleForDifficultyLevel.setLayoutY(0.2070 * height);
					vBoxForDifficultyLevel.setLayoutY(0.2168 * height);
					
					rectangleForQuestionCategories.setLayoutY(0.5342 * height);
					
					rectangleForExtendedQuestionCategories.setHeight(0.7157 * height);
				}
				else
				{
					titleImage2.setLayoutY(0.1887 * height);
					titleLabel2.setLayoutY(0.3904 * height);
					
					rectangleForDifficultyLevel.setLayoutY(0.1983 * height);
					vBoxForDifficultyLevel.setLayoutY(0.2087 * height);
					
					rectangleForQuestionCategories.setLayoutY(0.5168 * height);
					
					rectangleForExtendedQuestionCategories.setHeight(0.7261 * height);
				}
				
				titleImage1.setLayoutY(0.0574 * height);
				titleLabel1.setLayoutX(0.2479 * width);
				titleLabel1.setLayoutY(0.0801 * height);
				
				titleLabel2.setLayoutX(0.2479 * width);
				
				vBoxForSound.setLayoutY(0.1660 * height);
				
				rectangleForDifficultyLevel.setWidth(0.2343 * width);
				rectangleForDifficultyLevel.setHeight(0.1904 * height);
				
				rectangleForQuestionCategories.setWidth(0.4200 * width);
				rectangleForQuestionCategories.setHeight(0.2590 * height);
				
				vBoxForDifficultyLevel.setPrefSize(0.1155 * width, 0.1666 * height);
				
				extendedQuestionCategories.setPrefSize(0.2604 * width, 0.0463 * height);
			}
			else if (getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_4_3 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_5_4)
			{
				if (getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_4_3)
				{
					titleLabel1.setLayoutY(0.0450 * height);
					
					titleImage2.setLayoutY(0.1520 * height);
					titleLabel2.setLayoutY(0.3280 * height);
					
					rectangleForDifficultyLevel.setWidth(0.2343 * width);
					rectangleForDifficultyLevel.setHeight(0.1570 * height);
					rectangleForDifficultyLevel.setLayoutY(0.1690 * height);
					
					rectangleForQuestionCategories.setWidth(0.4200 * width);
					rectangleForQuestionCategories.setHeight(0.2190 * height);
					rectangleForQuestionCategories.setLayoutY(0.4470 * height);
					
					vBoxForDifficultyLevel.setPrefSize(0.1180 * width, 0.1666 * height);
					vBoxForDifficultyLevel.setLayoutY(0.1630 * height);
					
					extendedQuestionCategories.setPrefSize(0.2604 * width, 0.0463 * height);
					
					rectangleForExtendedQuestionCategories.setHeight(0.7731 * height);
				}
				else if (getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_5_4)
				{
					titleLabel1.setLayoutY(0.0439 * height);
					
					titleImage2.setLayoutY(0.1465 * height);
					titleLabel2.setLayoutY(0.3076 * height);
					
					rectangleForDifficultyLevel.setWidth(0.2343 * width);
					rectangleForDifficultyLevel.setHeight(0.1563 * height);
					rectangleForDifficultyLevel.setLayoutY(0.1563 * height);
					
					rectangleForQuestionCategories.setWidth(0.4200 * width);
					rectangleForQuestionCategories.setHeight(0.2148 * height);
					rectangleForQuestionCategories.setLayoutY(0.4199 * height);
					
					vBoxForDifficultyLevel.setPrefSize(0.1140 * width, 0.1666 * height);
					vBoxForDifficultyLevel.setLayoutY(0.1523 * height);
					
					extendedQuestionCategories.setPrefSize(0.2604 * width, 0.0463 * height);
					
					rectangleForExtendedQuestionCategories.setHeight(0.7886 * height);
				}
				titleImage1.setLayoutY(0.0381 * height);
				titleLabel1.setLayoutX(0.2479 * width);
				
				titleLabel2.setLayoutX(0.2479 * width);
				
				vBoxForSound.setLayoutY(0.1328 * height);
			}
			
			gridPaneForQuestionsCategories.setPrefSize(0.3650 * width, 0.2220 * height);
			
			extendedQuestionCategories.setLayoutX(width / 2.0 - extendedQuestionCategories.getPrefWidth() / 2.0);
			extendedQuestionCategories.setLayoutY(rectangleForQuestionCategories.getLayoutY() + rectangleForQuestionCategories.getHeight());
			
			vBoxForDifficultyLevel.setLayoutX(width / 2.0 - vBoxForDifficultyLevel.getPrefWidth() / 2.0);
			
			rectangleForDifficultyLevel.setLayoutX(width / 2.0 - rectangleForDifficultyLevel.getWidth() / 2.0);
			
			rectangleForQuestionCategories.setLayoutX(width / 2.0 - rectangleForQuestionCategories.getWidth() / 2.0);
			
			gridPaneForQuestionsCategories.setLayoutX(width / 2.0 - gridPaneForQuestionsCategories.getPrefWidth() / 2.0);
			gridPaneForQuestionsCategories.setLayoutY(rectangleForQuestionCategories.getLayoutY() + rectangleForQuestionCategories.getHeight() / 2.0 - gridPaneForQuestionsCategories.getPrefHeight() / 2.0);
			
			titleImage2.setFitWidth(0.5156 * width);
			titleImage2.setLayoutX(width / 2.0 - titleImage2.getFitWidth() / 2.0);
			titleLabel2.setPrefSize(0.5052 * width, 0.1111 * height);
			
			if (getCurrentLanguage() == LANGUAGE_GREEK)
			{
				if(isExtendedQuestionCategoriesOpen) titleLabel1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0302 * width));
				else titleLabel1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0339 * width));
				
				titleLabel2.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0417 * width));
			}
			else
			{
				if(isExtendedQuestionCategoriesOpen) titleLabel1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0401 * width));
				else titleLabel1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0354 * width));
				
				titleLabel2.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0450 * width));
			}
			
			rectangleForDifficultyLevel.setArcWidth(0.0208 * width);
			rectangleForDifficultyLevel.setArcHeight(0.0208 * width);
			rectangleForDifficultyLevel.setStrokeWidth(0.0042 * width);
			
			vBoxForDifficultyLevel.setSpacing(0.0130 * width);
			
			gridPaneForQuestionsCategories.setVgap(0.0740 * height);
			
			rectangleForQuestionCategories.setArcWidth(0.0208 * width);
			rectangleForQuestionCategories.setArcHeight(0.0208 * width);
			rectangleForQuestionCategories.setStrokeWidth(0.0042 * width);
			
			continentsAndCountriesCheckBox.getTooltip().setMaxWidth(0.3646 * width);
			USACheckBox.getTooltip().setMaxWidth(0.3646 * width);
			greeceCheckBox.getTooltip().setMaxWidth(0.3646 * width);
			attractionsCheckBox.getTooltip().setMaxWidth(0.3646 * width);
			
			nextButton.setLayoutX(0.8670 * width);
			nextArrowImage.setFitWidth(0.0703 * width);
			nextButton.getTooltip().setMaxWidth(0.3646 * width);
			nextButton.setFont(fontForArrowButtons);
			nextButton.getTooltip().setFont(fontForTooltips);
			
			easyLevelRadioButton.setFont(fontForLabels);
			difficultLevelRadioButton.setFont(fontForLabels);
			
			if(getCurrentLanguage() == LANGUAGE_GREEK)
			{
				gridPaneForQuestionsCategories.setHgap(0.0390 * width);
				
				continentsAndCountriesCheckBox.setFont(fontForLabels);
				USACheckBox.setFont(fontForLabels);
				greeceCheckBox.setFont(fontForLabels);
				attractionsCheckBox.setFont(fontForLabels);
			}
			else
			{
				gridPaneForQuestionsCategories.setHgap(0.0208 * width);
				
				Font fontForCheckboxes = Font.font("Comic Sans MS", 0.0156 * width);
				
				continentsAndCountriesCheckBox.setFont(fontForCheckboxes);
				USACheckBox.setFont(fontForCheckboxes);
				greeceCheckBox.setFont(fontForCheckboxes);
				attractionsCheckBox.setFont(fontForCheckboxes);
			}
			
			extendedQuestionCategories.setFont(Font.font("Comic Sans MS", 0.0156 * width));
			
			continentsAndCountriesCheckBox.getTooltip().setFont(fontForTooltips);
			USACheckBox.getTooltip().setFont(fontForTooltips);
			greeceCheckBox.getTooltip().setFont(fontForTooltips);
			attractionsCheckBox.getTooltip().setFont(fontForTooltips);
			
			//EXTENDED CATEGORIES
			rectangleForExtendedQuestionCategories.setWidth(0.8958 * width);
			rectangleForExtendedQuestionCategories.setArcWidth(0.0208 * width);
			rectangleForExtendedQuestionCategories.setArcHeight(0.0208 * width);
			rectangleForExtendedQuestionCategories.setStrokeWidth(0.0042 * width);
			rectangleForExtendedQuestionCategories.setLayoutX(width / 2.0 - rectangleForExtendedQuestionCategories.getBoundsInLocal().getWidth() / 2.0);
			rectangleForExtendedQuestionCategories.setLayoutY(rectangleForDifficultyLevel.getLayoutY());
			
			scrollPaneForExtendedCategoryQuestionsGridPane.setLayoutX(rectangleForExtendedQuestionCategories.getLayoutX() + 0.0104 * width);
			scrollPaneForExtendedCategoryQuestionsGridPane.setLayoutY(rectangleForExtendedQuestionCategories.getLayoutY() + 0.0185 * height);
			scrollPaneForExtendedCategoryQuestionsGridPane.setPrefSize(
					rectangleForExtendedQuestionCategories.getWidth() - 0.0260 * width,
					rectangleForExtendedQuestionCategories.getHeight() - 0.0463 * height);
			
			gridPaneForExtendedQuestionCategories.setHgap(0.0026 * width);
			gridPaneForExtendedQuestionCategories.setVgap(0.0111 * height);
			
			exitExtendedCategoryQuestions.setFitWidth(0.0313 * width);
			exitExtendedCategoryQuestions.setLayoutX(rectangleForExtendedQuestionCategories.getLayoutX()
			                                         + rectangleForExtendedQuestionCategories.getWidth()
			                                         - exitExtendedCategoryQuestions.getFitWidth()
			                                         - 0.0156 * width);
			
			if(width > 1200) exitExtendedCategoryQuestions.setLayoutY(rectangleForExtendedQuestionCategories.getLayoutY()
			                                                          + rectangleForExtendedQuestionCategories.getHeight()
			                                                          - exitExtendedCategoryQuestions.getFitWidth()
			                                                          - 0.0694 * height);
			else exitExtendedCategoryQuestions.setLayoutY(rectangleForExtendedQuestionCategories.getLayoutY()
			                                              + rectangleForExtendedQuestionCategories.getHeight()
			                                              - exitExtendedCategoryQuestions.getFitWidth()
			                                              - 0.0787 * height);
			
			Insets pos1 = new Insets(0, 0, 0, 0.0026 * width);
			Insets pos2 = new Insets(0, 0, 0, 0.0198 * width);
			
			Font fontForExtCat = Font.font("Comic Sans MS", 0.0115 * width);

			for(int i = 0; i < extQCCountries.length; i++)
			{
				if(i < extQCCategories.length)
				{
					extQCCategories[i].setFont(fontForExtCat);
					gridPaneForExtendedQuestionCategories.setMargin(extQCCategories[i], pos1);
				}
				
				extQCCountries[i].setFont(fontForExtCat);
				gridPaneForExtendedQuestionCategories.setMargin(extQCCountries[i], pos2);
				
				if(i < extQCUSA.length)
				{
					extQCUSA[i].setFont(fontForExtCat);
					gridPaneForExtendedQuestionCategories.setMargin(extQCUSA[i], pos2);
					
				}
				if(i < extQCGreece.length)
				{
					extQCGreece[i].setFont(fontForExtCat);
					gridPaneForExtendedQuestionCategories.setMargin(extQCGreece[i], pos2);
				}
			}
			
		}
		else
		{
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
			
			Font fontForLabels = Font.font("Comic Sans MS", 0.0156 * width);
			
			double widthOfNodesInVBox, heightOfNodesInVBox, heightOfDescriptions = 0, buttonHeight = 0;
			
			if(getCurrentLanguage() == LANGUAGE_GREEK) titleLabel1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0469 * width));
			else titleLabel1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0521 * width));
			
			if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_9)
			{
				titleImage1.setLayoutY(0.0574 * height);
				
				titleLabel1.setLayoutX(0.2479 * width);
				titleLabel1.setLayoutY(0.0807 * height);
				
				vBoxForSound.setLayoutY(0.0722 * height);
				
				hBoxForGameModes.setPrefSize(0.7688 * width, 0.6435 * height);
				hBoxForGameModes.setLayoutY(0.2546 * height);
				
				buttonHeight = 0.1019 * height;
				heightOfDescriptions = 0.3000 * height;
			}
			else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_10)
			{
				titleImage1.setLayoutY(0.0574 * height);
				
				titleLabel1.setLayoutX(0.2470 * width);
				titleLabel1.setLayoutY(0.0838 * height);
				
				vBoxForSound.setLayoutY(0.0722 * height);
				
				hBoxForGameModes.setPrefSize(0.7688 * width, 0.6380 * height);
				hBoxForGameModes.setLayoutY(0.2381 * height);
				
				buttonHeight = 0.0952 * height;
				heightOfDescriptions = 0.2667 * height;
			}
			else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_25_16 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_3_2)
			{
				if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_25_16)
				{
					hBoxForGameModes.setPrefSize(0.7688 * width, 0.6348 * height);
					hBoxForGameModes.setLayoutY(0.2344 * height);
				}
				else
				{
					hBoxForGameModes.setPrefSize(0.7688 * width, 0.5965 * height);
					hBoxForGameModes.setLayoutY(0.2519 * height);
				}
				
				titleImage1.setLayoutY(0.0574 * height);
				
				titleLabel1.setLayoutX(0.2479 * width);
				titleLabel1.setLayoutY(0.0801 * height);
				
				vBoxForSound.setLayoutY(0.0722 * height);
				
				buttonHeight = 0.0938 * height;
				heightOfDescriptions = 0.2539 * height;
			}
			else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_4_3 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_5_4)
			{
				if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_4_3)
				{
					hBoxForGameModes.setPrefSize(0.8214 * width, 0.5714 * height);
					hBoxForGameModes.setLayoutY(0.2619 * height);
				}
				else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_5_4)
				{
					hBoxForGameModes.setPrefSize(0.8203 * width, 0.5469 * height);
					hBoxForGameModes.setLayoutY(0.2766 * height);
				}
				
				titleImage1.setLayoutY(0.0381 * height);
				
				titleLabel1.setLayoutX(0.2479 * width);
				titleLabel1.setLayoutY(0.0439 * height);
				
				vBoxForSound.setLayoutY(0.0560 * height);
				
				buttonHeight = 0.0857 * height;
				
				heightOfDescriptions = 0.2246 * height;
			}
			
			if(titleImage1.getTranslateX() != 0)
			{
				if (OS == OsCheck.OSType.Windows)
				{
					titleImage1.setTranslateX(-0.1484 * width);
					titleLabel1.setTranslateX(-0.1484 * width);
				}
				else
				{
					titleImage1.setTranslateX(0.1484 * width);
					titleLabel1.setTranslateX(0.1484 * width);
				}
			}
			
			double vBoxSpacing = 0.0074 * height;
			vBoxForClassicGame.setSpacing(vBoxSpacing);
			vBoxForTimeAttackGame.setSpacing(vBoxSpacing);
			vBoxForEndlessGame.setSpacing(vBoxSpacing);
			
			double buttonWidth = 0.1927 * width;
			classicGameButton.setPrefSize(buttonWidth, buttonHeight);
			timeAttackGameButton.setPrefSize(buttonWidth, buttonHeight);
			endlessGameButton.setPrefSize(buttonWidth, buttonHeight);
			
			hBoxForGameModes.setLayoutX(width / 2.0 - hBoxForGameModes.getPrefWidth() / 2.0);
			hBoxForGameModes.setSpacing(0.0078 * width);
			
			vBoxForClassicGame.setPrefWidth((hBoxForGameModes.getPrefWidth() - hBoxForGameModes.getSpacing() * 2.0) / 3.0);
			vBoxForTimeAttackGame.setPrefWidth((hBoxForGameModes.getPrefWidth() - hBoxForGameModes.getSpacing() * 2.0) / 3.0);
			vBoxForEndlessGame.setPrefWidth((hBoxForGameModes.getPrefWidth() - hBoxForGameModes.getSpacing() * 2.0) / 3.0);
			
			vBoxForClassicGame.setStyle(
					"-fx-background-color: #00000099; -fx-border-color: black;" +
					"-fx-background-radius:" + 0.0208 * width + ";" +
					"-fx-border-radius:" + 0.0208 * width + ";" +
					"-fx-border-width:" + 0.0042 * width);
			
			vBoxForTimeAttackGame.setStyle(
					"-fx-background-color: #00000099; -fx-border-color: black;" +
					"-fx-background-radius:" + 0.0208 * width + ";" +
					"-fx-border-radius:" + 0.0208 * width + ";" +
					"-fx-border-width:" + 0.0042 * width);
			
			vBoxForEndlessGame.setStyle(
					"-fx-background-color: #00000099; -fx-border-color: black;" +
					"-fx-background-radius:" + 0.0208 * width + ";" +
					"-fx-border-radius:" + 0.0208 * width + ";" +
					"-fx-border-width:" + 0.0042 * width);
			
			widthOfNodesInVBox = 0.2266 * width;
			heightOfNodesInVBox = 0.0509 * height;
			
			numberOfQuestionsForClassicGameLabel.setPrefSize(widthOfNodesInVBox, heightOfNodesInVBox);
			hBoxForNumberOfQuestionsForClassicGame.setPrefSize(widthOfNodesInVBox, heightOfNodesInVBox);
			hBoxForNumberOfQuestionsForClassicGame.setSpacing(0.0052 * width);
			hBoxForCountDownForClassicGame.setPrefSize(widthOfNodesInVBox, heightOfNodesInVBox);
			hBoxForCountDownForClassicGame.setSpacing(0.0052 * width);
			descriptionForClassicGameLabel.setPrefSize(widthOfNodesInVBox, heightOfDescriptions);
			
			gameDurationForTimeAttackGameLabel.setPrefSize(widthOfNodesInVBox, heightOfNodesInVBox);
			hBoxForGameDurationForTimeAttackGame.setPrefSize(widthOfNodesInVBox, heightOfNodesInVBox);
			hBoxEmpty.setPrefSize(widthOfNodesInVBox, heightOfNodesInVBox);
			hBoxForGameDurationForTimeAttackGame.setSpacing(0.0052 * width);
			descriptionForTimeAttackGameLabel.setPrefSize(widthOfNodesInVBox, heightOfDescriptions);
			
			livesForEndlessGameLabel.setPrefSize(widthOfNodesInVBox, heightOfNodesInVBox);
			hBoxForLivesForEndlessGame.setPrefSize(widthOfNodesInVBox, heightOfNodesInVBox);
			hBoxForLivesForEndlessGame.setSpacing(0.0052 * width);
			hBoxForCountDownForEndlessGame.setPrefSize(widthOfNodesInVBox, heightOfNodesInVBox);
			hBoxForCountDownForEndlessGame.setSpacing(0.0052 * width);
			descriptionForEndlessGameLabel.setPrefSize(widthOfNodesInVBox, heightOfDescriptions);
			
			countDownToggleForClassicGameLabel.recalculateUI(0.0208 * width, 0.0208 * width, 0.0115 * width);
			countDownToggleForEndlessGameLabel.recalculateUI(0.0208 * width, 0.0208 * width, 0.0115 * width);
			
			countDownToggleForClassicGameLabel.setPrefWidth(0.0521 * width);
			countDownToggleForEndlessGameLabel.setPrefWidth(0.0521 * width);
			
			countDownToggleForClassicGameLabel.setPrefHeight(0.0208 * width);
			countDownToggleForEndlessGameLabel.setPrefHeight(0.0208 * width);
			
			backArrowImage.setFitWidth(0.0703 * width);
			
			classicGameButton.setFont(fontForButtons);
			timeAttackGameButton.setFont(fontForButtons);
			endlessGameButton.setFont(fontForButtons);
			
			numberOfQuestionsForClassicGameLabel.setFont(fontForLabels);
			radioButton10QuestionsForClassicGameLabel.setFont(fontForLabels);
			radioButton20QuestionsForClassicGameLabel.setFont(fontForLabels);
			radioButton50QuestionsForClassicGameLabel.setFont(fontForLabels);
			radioButton100QuestionsForClassicGameLabel.setFont(fontForLabels);
			countDownForClassicGameLabel.setFont(fontForLabels);
			descriptionForClassicGameLabel.setFont(fontForLabels);
			gameDurationForTimeAttackGameLabel.setFont(fontForLabels);
			radioButton1MinuteDurationForTimeAttackGameLabel.setFont(fontForLabels);
			radioButton2MinutesDurationForTimeAttackGameLabel.setFont(fontForLabels);
			radioButton5MinutesDurationForTimeAttackGameLabel.setFont(fontForLabels);
			radioButton10MinutesDurationForTimeAttackGameLabel.setFont(fontForLabels);
			descriptionForTimeAttackGameLabel.setFont(fontForLabels);
			livesForEndlessGameLabel.setFont(fontForLabels);
			radioButton1LifeForEndlessGameLabel.setFont(fontForLabels);
			radioButton3LivesForEndlessGameLabel.setFont(fontForLabels);
			radioButton5LivesForEndlessGameLabel.setFont(fontForLabels);
			countDownForEndlessGameLabel.setFont(fontForLabels);
			descriptionForEndlessGameLabel.setFont(fontForLabels);
		}
		
		if (getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_9)
		{
			woodPanelFor5IconsImage.setLayoutY(0.0454 * height);
			hBoxFor5Icons.setLayoutY(0.1009 * height);
			
			backButton.setLayoutY(0.8056 * height);
			nextButton.setLayoutY(0.8056 * height);
		}
		else if (getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_10)
		{
			woodPanelFor5IconsImage.setLayoutY(0.0467 * height);
			hBoxFor5Icons.setLayoutY(0.0952 * height);
			
			backButton.setLayoutY(0.8120 * height);
			nextButton.setLayoutY(0.8120 * height);
		}
		else if (getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_25_16 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_3_2)
		{
			if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_25_16) woodPanelFor5IconsImage.setLayoutY(0.0461 * height);
			else woodPanelFor5IconsImage.setLayoutY(0.0478 * height);
			
			hBoxFor5Icons.setLayoutY(0.0908 * height);
			
			backButton.setLayoutY(0.8200 * height);
			nextButton.setLayoutY(0.8200 * height);
		}
		else if (getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_4_3 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_5_4)
		{
			if (getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_4_3)
			{
				hBoxFor5Icons.setLayoutY(0.0638 * height);
				
				backButton.setLayoutY(0.8429 * height);
				nextButton.setLayoutY(0.8429 * height);
			}
			else if (getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_5_4)
			{
				hBoxFor5Icons.setLayoutY(0.0600 * height);
				
				backButton.setLayoutY(0.8516 * height);
				nextButton.setLayoutY(0.8516 * height);
			}
			
			woodPanelFor5IconsImage.setLayoutY(0.0286 * height);
		}
		
		vBoxForSound.setPrefSize(0.1400 * width, 0.1389 * height);
		
		woodPanelFor5IconsImage.setFitWidth(0.1667 * width);
		
		hBoxFor5Icons.setPrefSize(0.1667 * width, 0.0509 * height);
		hBoxFor5Icons.setSpacing(0.0052 * width);
		
		if (OS == OsCheck.OSType.Windows)
		{
			woodPanelFor5IconsImage.setLayoutX(0.7792 * width);
			
			if(screen1) vBoxForSound.setLayoutX(woodPanelFor5IconsImage.getLayoutX() + woodPanelFor5IconsImage.getFitWidth() / 2.0 - vBoxForSound.getPrefWidth() / 2.0);
			else vBoxForSound.setLayoutX(woodPanelFor5IconsImage.getLayoutX() - vBoxForSound.getPrefWidth() - 0.0156 * width);
		}
		else
		{
			woodPanelFor5IconsImage.setLayoutX(0.0542 * width);
			
			if(screen1) vBoxForSound.setLayoutX(woodPanelFor5IconsImage.getLayoutX() + woodPanelFor5IconsImage.getFitWidth() / 2.0 - vBoxForSound.getPrefWidth() / 2.0);
			else vBoxForSound.setLayoutX(woodPanelFor5IconsImage.getLayoutX() + vBoxForSound.getPrefWidth() + 0.0156 * width);
		}
		
		hBoxFor5Icons.setLayoutX(woodPanelFor5IconsImage.getLayoutX());
		
		titleImage1.setFitWidth(0.5156 * width);
		titleImage1.setLayoutX(width / 2.0 - titleImage1.getFitWidth() / 2.0);
		
		titleLabel1.setPrefSize(0.5052 * width, 0.1111 * height);
		
		innerShadow.setOffsetX(0.0041 * width);
		innerShadow.setOffsetY(0.0041 * width);
		
		dropShadow.setRadius(0.0104 * width);
		dropShadow.setOffsetX(-0.0052 * width);
		dropShadow.setOffsetY(-0.0052 * width);
		
		titleLabel1.setEffect(innerShadow);
		titleLabel2.setEffect(innerShadow);
		
		vBoxForSound.setStyle("-fx-background-color: #00000099; " +
		                      "-fx-border-color: black; " +
		                      "-fx-background-radius:" + 0.0078 * width + "; " +
		                      "-fx-border-radius:" + 0.0078 * width + "; " +
		                      "-fx-border-width:" + 0.0026 * width + "; " +
		                      "-fx-padding:" + 0.0052 * width + ";");
		
		soundIcon.setFitWidth(iconSize);
		minimizeIcon.setFitWidth(iconSize);
		moveIcon.setFitWidth(iconSize);
		fullScreenIcon.setFitWidth(iconSize);
		exitIcon.setFitWidth(iconSize);
		
		if (width < 1200) backButton.setLayoutX(0.0240 * width);
		else if (width < 2000) backButton.setLayoutX(0.0286 * width);
		else backButton.setLayoutX(0.0339 * width);
		
		backArrowImage.setFitWidth(0.0703 * width);
		backButton.getTooltip().setMaxWidth(0.3646 * width);
		backButton.getTooltip().setFont(fontForTooltips);
		
		backButton.setFont(fontForArrowButtons);
		
		Font fontForSound = Font.font("Comic Sans MS", 0.0104 * width);
		masterVolumeLabel.setFont(fontForSound);
		musicVolumeLabel.setFont(fontForSound);
		soundEffectsVolumeLabel.setFont(fontForSound);
		
		soundOptionsToolTip.setFont(fontForTooltips);
		minimizeTooltip.setFont(fontForTooltips);
		moveTooltip.setFont(fontForTooltips);
		fullScreenTooltip.setFont(fontForTooltips);
		exitTooltip.setFont(fontForTooltips);
	}
	
	protected void recalculateBackground(double width, double height)
	{
		super.recalculateBackground(width, height);
		
		if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_9)
		{
			movingEarthImage.setFitWidth(0.9500 * width);
			movingEarthImage.setLayoutY(0.0500 * height);
		}
		else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_10  || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_25_16 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_3_2)
		{
			movingEarthImage.setFitWidth(0.9590 * width);
			movingEarthImage.setLayoutY(0.0519 * height);
		}
		else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_4_3)
		{
			movingEarthImage.setFitWidth(0.9548 * width);
			movingEarthImage.setLayoutY(0.0322 * height);
		}
		else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_5_4)
		{
			movingEarthImage.setFitWidth(0.9500 * width);
			movingEarthImage.setLayoutY(0.0332 * height);
		}
		
		movingEarthImage.setLayoutX(width / 2.0 - movingEarthImage.getFitWidth() / 2.0);
		
		if(previousImage.getImage() != null && previousImage.getImage().equals(CHALKBOARD_BACKGROUND_IMAGE))
		{
			previousImage.setLayoutX(0);
			previousImage.setLayoutY(0);
			previousImage.setFitWidth(width);
			previousImage.setFitHeight(height);
		}
		else
		{
			previousImage.setLayoutX(getWorldMapLayoutX() * width);
			previousImage.setLayoutY(getWorldMapLayoutY() * height);
			previousImage.setFitWidth(getWorldMapFitWidth() * width);
			previousImage.setFitHeight(getWorldMapFitHeight() * height);
		}
	}
	
	protected void setScreenRatioDependentImages()
	{
		woodenFrameImage.setImage(FRAME_IMAGE);
		movingEarthImage.setImage(MOVING_EARTH_IMAGE_1);
		setViewPortProperties();
	}
	
	public GamePropertiesScreen()
	{
		previousImage = new ImageView();
		previousImage.setPreserveRatio(false);
		previousImage.setLayoutX(0);
		previousImage.setLayoutY(0);
		previousImage.setSmooth(false);
		previousImage.setCache(true);
		previousImage.setCacheHint(CacheHint.SPEED);
		
		movingEarthImage = new ImageView();
		movingEarthImage.setSmooth(false);
		movingEarthImage.setPreserveRatio(true);
		
		woodPanelFor5IconsImage = new ImageView();
		woodPanelFor5IconsImage.setSmooth(true);
		woodPanelFor5IconsImage.setPreserveRatio(true);
		woodPanelFor5IconsImage.setCache(true);
		woodPanelFor5IconsImage.setCacheHint(CacheHint.SPEED);
		
		hBoxFor5Icons = new HBox();
		hBoxFor5Icons.setAlignment(Pos.CENTER);
		hBoxFor5Icons.setCache(true);
		hBoxFor5Icons.setCacheHint(CacheHint.SCALE);
		
		if (OS == OsCheck.OSType.Windows) hBoxFor5Icons.getChildren().addAll(soundIcon, minimizeIcon, moveIcon, fullScreenIcon, exitIcon);
		else hBoxFor5Icons.getChildren().addAll(exitIcon, fullScreenIcon, moveIcon, minimizeIcon, soundIcon);
		
		backArrowImage = new ImageView();
		backArrowImage.setPreserveRatio(true);
		backArrowImage.setSmooth(true);
		backArrowImage.setPickOnBounds(true);
		
		backButton = new Button();
		backButton.setStyle("-fx-background-color: transparent");
		backButton.setGraphic(backArrowImage);
		backButton.setContentDisplay(ContentDisplay.TOP);
		backButton.setTextAlignment(TextAlignment.CENTER);
		backButton.setTextFill(Color.valueOf("#7A301B"));
		backButton.setCursor(Cursor.HAND);
		backButton.setTooltip(new CustomTooltip());
		backButton.getTooltip().setWrapText(true);
		backButton.setCache(true);
		backButton.setCacheHint(CacheHint.SCALE);
		
//		Difficulty and categories selection screen
		nextArrowImage = new ImageView();
		nextArrowImage.setRotate(180);
		nextArrowImage.setPreserveRatio(true);
		nextArrowImage.setSmooth(true);
		nextArrowImage.setPickOnBounds(true);
		
		nextButton = new Button();
		nextButton.setStyle("-fx-background-color: transparent");
		nextButton.setGraphic(nextArrowImage);
		nextButton.setContentDisplay(ContentDisplay.TOP);
		nextButton.setTextAlignment(TextAlignment.CENTER);
		nextButton.setTextFill(Color.valueOf("#7A301B"));
		nextButton.setCursor(Cursor.HAND);
		nextButton.setTooltip(new CustomTooltip());
		nextButton.getTooltip().setWrapText(true);
		nextButton.setCache(true);
		nextButton.setCacheHint(CacheHint.SCALE);
		
		titleImage1 = new ImageView();
		titleImage1.setSmooth(true);
		titleImage1.setPreserveRatio(true);
		titleImage1.setCache(true);
		titleImage1.setCacheHint(CacheHint.SPEED);
		
		titleImage2 = new ImageView();
		titleImage2.setSmooth(true);
		titleImage2.setPreserveRatio(true);
		titleImage2.setCache(true);
		titleImage2.setCacheHint(CacheHint.SCALE);
		
		innerShadow = new InnerShadow();
		
		titleLabel1 = new Label();
		titleLabel1.setTextFill(Color.valueOf("#602000"));
		titleLabel1.setTextAlignment(TextAlignment.CENTER);
		titleLabel1.setAlignment(Pos.CENTER);
		titleLabel1.setCache(true);
		titleLabel1.setCacheHint(CacheHint.SCALE);
		
		titleLabel2 = new Label();
		titleLabel2.setTextFill(Color.valueOf("#602000"));
		titleLabel2.setTextAlignment(TextAlignment.CENTER);
		titleLabel2.setAlignment(Pos.CENTER);
		titleLabel2.setCache(true);
		titleLabel2.setCacheHint(CacheHint.SCALE);
		
		easyLevelRadioButton = new RadioButton();
		easyLevelRadioButton.setFocusTraversable(false);
		easyLevelRadioButton.setCursor(Cursor.HAND);
		easyLevelRadioButton.setTextFill(Color.WHITE);
		
		difficultLevelRadioButton = new RadioButton();
		difficultLevelRadioButton.setFocusTraversable(false);
		difficultLevelRadioButton.setCursor(Cursor.HAND);
		difficultLevelRadioButton.setTextFill(Color.WHITE);
		
		toggleGroupForDifficultyLevel = new ToggleGroup();
		toggleGroupForDifficultyLevel.getToggles().addAll(easyLevelRadioButton, difficultLevelRadioButton);
		
		rectangleForDifficultyLevel = new Rectangle();
		rectangleForDifficultyLevel.setSmooth(true);
		rectangleForDifficultyLevel.setStroke(Color.BLACK);
		rectangleForDifficultyLevel.setFill(Color.valueOf("00000099"));
		
		rectangleForQuestionCategories = new Rectangle();
		rectangleForQuestionCategories.setSmooth(true);
		rectangleForQuestionCategories.setStroke(Color.BLACK);
		rectangleForQuestionCategories.setFill(Color.valueOf("00000099"));
		rectangleForQuestionCategories.setCache(true);
		rectangleForQuestionCategories.setCacheHint(CacheHint.SCALE);
		
		vBoxForDifficultyLevel = new VBox();
		vBoxForDifficultyLevel.setAlignment(Pos.CENTER_LEFT);
		vBoxForDifficultyLevel.getChildren().addAll(easyLevelRadioButton, difficultLevelRadioButton);
		vBoxForDifficultyLevel.setCache(true);
		vBoxForDifficultyLevel.setCacheHint(CacheHint.SCALE);
		
		continentsAndCountriesCheckBox = new CustomCheckBox();
		continentsAndCountriesCheckBox.setCursor(Cursor.HAND);
		continentsAndCountriesCheckBox.setTextFill(Color.WHITE);
		continentsAndCountriesCheckBox.setTooltip(new CustomTooltip());
		continentsAndCountriesCheckBox.getTooltip().setWrapText(true);
		
		USACheckBox = new CustomCheckBox();
		USACheckBox.setTooltip(new CustomTooltip());
		USACheckBox.getTooltip().setWrapText(true);
		
		greeceCheckBox = new CustomCheckBox();
		greeceCheckBox.setTooltip(new CustomTooltip());
		greeceCheckBox.getTooltip().setWrapText(true);
		
		attractionsCheckBox = new CustomCheckBox();
		attractionsCheckBox.setTooltip(new CustomTooltip());
		attractionsCheckBox.getTooltip().setWrapText(true);
		
		gridPaneForQuestionsCategories = new GridPane();
		gridPaneForQuestionsCategories.setAlignment(Pos.CENTER);
		
		gridPaneForQuestionsCategories.add(continentsAndCountriesCheckBox, 0, 0);
		gridPaneForQuestionsCategories.add(USACheckBox, 1, 0);
		gridPaneForQuestionsCategories.add(greeceCheckBox, 0, 1);
		gridPaneForQuestionsCategories.add(attractionsCheckBox, 1, 1);
		gridPaneForQuestionsCategories.setCache(true);
		gridPaneForQuestionsCategories.setCacheHint(CacheHint.SCALE);
		
		extendedQuestionCategories = new Label();
		extendedQuestionCategories.setTextFill(Color.BLACK);
		extendedQuestionCategories.setTextAlignment(TextAlignment.CENTER);
		extendedQuestionCategories.setAlignment(Pos.CENTER);
		extendedQuestionCategories.setUnderline(true);
		extendedQuestionCategories.setPickOnBounds(true);
		extendedQuestionCategories.setCursor(Cursor.HAND);
		
		gridPaneForExtendedQuestionCategories = new GridPane();
		gridPaneForExtendedQuestionCategories.setAlignment(Pos.TOP_CENTER);
		
		scrollPaneForExtendedCategoryQuestionsGridPane = new ScrollPane();
		scrollPaneForExtendedCategoryQuestionsGridPane.setFitToHeight(true);
		scrollPaneForExtendedCategoryQuestionsGridPane.setPannable(true);
		scrollPaneForExtendedCategoryQuestionsGridPane.setCursor(Cursor.MOVE);
		scrollPaneForExtendedCategoryQuestionsGridPane.setContent(gridPaneForExtendedQuestionCategories);
		scrollPaneForExtendedCategoryQuestionsGridPane.setCache(true);
		scrollPaneForExtendedCategoryQuestionsGridPane.setCacheHint(CacheHint.SCALE);
		                                      
		rectangleForExtendedQuestionCategories = new Rectangle();
		rectangleForExtendedQuestionCategories.setSmooth(true);
		rectangleForExtendedQuestionCategories.setStroke(Color.BLACK);
		rectangleForExtendedQuestionCategories.setFill(Color.valueOf("000000de"));
		rectangleForExtendedQuestionCategories.setCache(true);
		rectangleForExtendedQuestionCategories.setCacheHint(CacheHint.SCALE);
		
		exitExtendedCategoryQuestions = new ImageView();
		exitExtendedCategoryQuestions.setPreserveRatio(true);
		exitExtendedCategoryQuestions.setSmooth(true);
		exitExtendedCategoryQuestions.setCursor(Cursor.HAND);
		
		extQCCategories = new CustomCheckBox[4];
		extQCCountries = new CustomCheckBox[11];
		extQCUSA = new CustomCheckBox[9];
		extQCGreece = new CustomCheckBox[7];
		
		for(int i = 0; i < extQCCountries.length; i++)
		{
			if(i < extQCCategories.length) extQCCategories[i] = new CustomCheckBox();
			extQCCountries[i] = new CustomCheckBox();
			if(i < extQCUSA.length) extQCUSA[i] = new CustomCheckBox();
			if(i < extQCGreece.length) extQCGreece[i] = new CustomCheckBox();
		}
		
		gridPaneForExtendedQuestionCategories.add(extQCCategories[0], 0, 0);
		for(int i = 0; i < extQCCountries.length; i++) gridPaneForExtendedQuestionCategories.add(extQCCountries[i], 0, i + 1);
		
		gridPaneForExtendedQuestionCategories.add(extQCCategories[1], 1, 0);
		for(int i = 0; i < extQCUSA.length; i++) gridPaneForExtendedQuestionCategories.add(extQCUSA[i], 1, i + 1);
		
		gridPaneForExtendedQuestionCategories.add(extQCCategories[2], 2, 0);
		for(int i = 0; i < extQCGreece.length; i++) gridPaneForExtendedQuestionCategories.add(extQCGreece[i], 2, i + 1);

		gridPaneForExtendedQuestionCategories.add(extQCCategories[3], 3, 0);
		
//		Game mode selection screen
		
		classicGameButton = new Button("Classic");
		classicGameButton.setCursor(Cursor.HAND);
		
		timeAttackGameButton = new Button("Time attack");
		timeAttackGameButton.setCursor(Cursor.HAND);
		
		endlessGameButton = new Button("Endless");
		endlessGameButton.setCursor(Cursor.HAND);
		
		numberOfQuestionsForClassicGameLabel = new Label();
		numberOfQuestionsForClassicGameLabel.setUnderline(true);
		numberOfQuestionsForClassicGameLabel.setTextFill(Color.WHITE);
		numberOfQuestionsForClassicGameLabel.setAlignment(Pos.CENTER);
		
		gameDurationForTimeAttackGameLabel = new Label();
		gameDurationForTimeAttackGameLabel.setUnderline(true);
		gameDurationForTimeAttackGameLabel.setTextFill(Color.WHITE);
		gameDurationForTimeAttackGameLabel.setAlignment(Pos.CENTER);
		
		livesForEndlessGameLabel = new Label();
		livesForEndlessGameLabel.setUnderline(true);
		livesForEndlessGameLabel.setTextFill(Color.WHITE);
		livesForEndlessGameLabel.setAlignment(Pos.CENTER);
		
		countDownForClassicGameLabel = new Label();
		countDownForClassicGameLabel.setUnderline(true);
		countDownForClassicGameLabel.setTextFill(Color.WHITE);
		countDownForClassicGameLabel.setAlignment(Pos.CENTER);
		
		countDownForEndlessGameLabel = new Label();
		countDownForEndlessGameLabel.setUnderline(true);
		countDownForEndlessGameLabel.setTextFill(Color.WHITE);
		countDownForEndlessGameLabel.setAlignment(Pos.CENTER);
		
		descriptionForClassicGameLabel = new Label();
		descriptionForClassicGameLabel.setTextFill(Color.WHITE);
		descriptionForClassicGameLabel.setAlignment(Pos.TOP_CENTER);
		descriptionForClassicGameLabel.setWrapText(true);
		
		descriptionForTimeAttackGameLabel = new Label();
		descriptionForTimeAttackGameLabel.setTextFill(Color.WHITE);
		descriptionForTimeAttackGameLabel.setAlignment(Pos.TOP_CENTER);
		descriptionForTimeAttackGameLabel.setWrapText(true);
		
		descriptionForEndlessGameLabel = new Label();
		descriptionForEndlessGameLabel.setTextFill(Color.WHITE);
		descriptionForEndlessGameLabel.setAlignment(Pos.TOP_CENTER);
		descriptionForEndlessGameLabel.setWrapText(true);
		
		radioButton10QuestionsForClassicGameLabel = new RadioButton("10");
		radioButton10QuestionsForClassicGameLabel.setFocusTraversable(false);
		radioButton10QuestionsForClassicGameLabel.setCursor(Cursor.HAND);
		radioButton10QuestionsForClassicGameLabel.setTextFill(Color.WHITE);
		
		radioButton20QuestionsForClassicGameLabel = new RadioButton("20");
		radioButton20QuestionsForClassicGameLabel.setFocusTraversable(false);
		radioButton20QuestionsForClassicGameLabel.setCursor(Cursor.HAND);
		radioButton20QuestionsForClassicGameLabel.setTextFill(Color.WHITE);
		
		radioButton50QuestionsForClassicGameLabel = new RadioButton("50");
		radioButton50QuestionsForClassicGameLabel.setFocusTraversable(false);
		radioButton50QuestionsForClassicGameLabel.setCursor(Cursor.HAND);
		radioButton50QuestionsForClassicGameLabel.setTextFill(Color.WHITE);
		
		radioButton100QuestionsForClassicGameLabel = new RadioButton("100");
		radioButton100QuestionsForClassicGameLabel.setFocusTraversable(false);
		radioButton100QuestionsForClassicGameLabel.setCursor(Cursor.HAND);
		radioButton100QuestionsForClassicGameLabel.setTextFill(Color.WHITE);
		
		toggleGroupForClassicGame = new ToggleGroup();
		toggleGroupForClassicGame.getToggles().addAll(radioButton10QuestionsForClassicGameLabel,
				radioButton20QuestionsForClassicGameLabel,
				radioButton50QuestionsForClassicGameLabel,
				radioButton100QuestionsForClassicGameLabel);
		
		countDownToggleForClassicGameLabel = new SwitchButton();
		
		hBoxForNumberOfQuestionsForClassicGame = new HBox();
		hBoxForNumberOfQuestionsForClassicGame.setFillHeight(true);
		hBoxForNumberOfQuestionsForClassicGame.setAlignment(Pos.CENTER);
		hBoxForNumberOfQuestionsForClassicGame.getChildren().addAll(radioButton10QuestionsForClassicGameLabel,
				radioButton20QuestionsForClassicGameLabel,
				radioButton50QuestionsForClassicGameLabel,
				radioButton100QuestionsForClassicGameLabel);
		
		hBoxForCountDownForClassicGame = new HBox();
		hBoxForCountDownForClassicGame.setAlignment(Pos.CENTER);
		hBoxForCountDownForClassicGame.getChildren().addAll(countDownForClassicGameLabel, countDownToggleForClassicGameLabel);
		
		radioButton1MinuteDurationForTimeAttackGameLabel = new RadioButton("1");
		radioButton1MinuteDurationForTimeAttackGameLabel.setFocusTraversable(false);
		radioButton1MinuteDurationForTimeAttackGameLabel.setCursor(Cursor.HAND);
		radioButton1MinuteDurationForTimeAttackGameLabel.setTextFill(Color.WHITE);
		
		radioButton2MinutesDurationForTimeAttackGameLabel = new RadioButton("2");
		radioButton2MinutesDurationForTimeAttackGameLabel.setFocusTraversable(false);
		radioButton2MinutesDurationForTimeAttackGameLabel.setCursor(Cursor.HAND);
		radioButton2MinutesDurationForTimeAttackGameLabel.setTextFill(Color.WHITE);
		
		radioButton5MinutesDurationForTimeAttackGameLabel = new RadioButton("5");
		radioButton5MinutesDurationForTimeAttackGameLabel.setFocusTraversable(false);
		radioButton5MinutesDurationForTimeAttackGameLabel.setCursor(Cursor.HAND);
		radioButton5MinutesDurationForTimeAttackGameLabel.setTextFill(Color.WHITE);
		
		radioButton10MinutesDurationForTimeAttackGameLabel = new RadioButton("10");
		radioButton10MinutesDurationForTimeAttackGameLabel.setFocusTraversable(false);
		radioButton10MinutesDurationForTimeAttackGameLabel.setCursor(Cursor.HAND);
		radioButton10MinutesDurationForTimeAttackGameLabel.setTextFill(Color.WHITE);
		
		toggleGroupForTimeAttackGame = new ToggleGroup();
		toggleGroupForTimeAttackGame.getToggles().addAll(radioButton1MinuteDurationForTimeAttackGameLabel,
				radioButton2MinutesDurationForTimeAttackGameLabel,
				radioButton5MinutesDurationForTimeAttackGameLabel,
				radioButton10MinutesDurationForTimeAttackGameLabel);
		
		hBoxForGameDurationForTimeAttackGame = new HBox();
		hBoxForGameDurationForTimeAttackGame.setFillHeight(true);
		hBoxForGameDurationForTimeAttackGame.setAlignment(Pos.CENTER);
		hBoxForGameDurationForTimeAttackGame.getChildren().addAll(radioButton1MinuteDurationForTimeAttackGameLabel,
				radioButton2MinutesDurationForTimeAttackGameLabel,
				radioButton5MinutesDurationForTimeAttackGameLabel,
				radioButton10MinutesDurationForTimeAttackGameLabel);
		
		hBoxEmpty = new HBox();
		
		radioButton1LifeForEndlessGameLabel = new RadioButton("1");
		radioButton1LifeForEndlessGameLabel.setFocusTraversable(false);
		radioButton1LifeForEndlessGameLabel.setCursor(Cursor.HAND);
		radioButton1LifeForEndlessGameLabel.setTextFill(Color.WHITE);
		
		radioButton3LivesForEndlessGameLabel = new RadioButton("3");
		radioButton3LivesForEndlessGameLabel.setFocusTraversable(false);
		radioButton3LivesForEndlessGameLabel.setCursor(Cursor.HAND);
		radioButton3LivesForEndlessGameLabel.setTextFill(Color.WHITE);
		
		radioButton5LivesForEndlessGameLabel = new RadioButton("5");
		radioButton5LivesForEndlessGameLabel.setFocusTraversable(false);
		radioButton5LivesForEndlessGameLabel.setCursor(Cursor.HAND);
		radioButton5LivesForEndlessGameLabel.setTextFill(Color.WHITE);
		
		toggleGroupForEndlessGame = new ToggleGroup();
		toggleGroupForEndlessGame.getToggles().addAll(radioButton1LifeForEndlessGameLabel,
				radioButton3LivesForEndlessGameLabel,
				radioButton5LivesForEndlessGameLabel);
		
		countDownToggleForEndlessGameLabel = new SwitchButton();
		
		hBoxForLivesForEndlessGame = new HBox();
		hBoxForLivesForEndlessGame.setFillHeight(true);
		hBoxForLivesForEndlessGame.setAlignment(Pos.CENTER);
		hBoxForLivesForEndlessGame.getChildren().addAll(radioButton1LifeForEndlessGameLabel,
				radioButton3LivesForEndlessGameLabel,
				radioButton5LivesForEndlessGameLabel);
		
		hBoxForCountDownForEndlessGame = new HBox();
		hBoxForCountDownForEndlessGame.setAlignment(Pos.CENTER);
		hBoxForCountDownForEndlessGame.getChildren().addAll(countDownForEndlessGameLabel, countDownToggleForEndlessGameLabel);
		
		vBoxForClassicGame = new VBox();
		vBoxForClassicGame.setAlignment(Pos.CENTER);
		vBoxForClassicGame.getChildren().addAll(classicGameButton, numberOfQuestionsForClassicGameLabel, hBoxForNumberOfQuestionsForClassicGame,
				hBoxForCountDownForClassicGame, descriptionForClassicGameLabel);
		vBoxForClassicGame.setCache(true);
		vBoxForClassicGame.setCacheHint(CacheHint.SCALE);
		
		vBoxForTimeAttackGame = new VBox();
		vBoxForTimeAttackGame.setAlignment(Pos.CENTER);
		vBoxForTimeAttackGame.getChildren().addAll(timeAttackGameButton, gameDurationForTimeAttackGameLabel,
				hBoxForGameDurationForTimeAttackGame, hBoxEmpty, descriptionForTimeAttackGameLabel);
		vBoxForTimeAttackGame.setCache(true);
		vBoxForTimeAttackGame.setCacheHint(CacheHint.SCALE);
		
		vBoxForEndlessGame = new VBox();
		vBoxForEndlessGame.setAlignment(Pos.CENTER);
		vBoxForEndlessGame.getChildren().addAll(endlessGameButton, livesForEndlessGameLabel, hBoxForLivesForEndlessGame, hBoxForCountDownForEndlessGame, descriptionForEndlessGameLabel);
		vBoxForEndlessGame.setCache(true);
		vBoxForEndlessGame.setCacheHint(CacheHint.SCALE);
		
		hBoxForGameModes = new HBox();
		hBoxForGameModes.setFillHeight(true);
		hBoxForGameModes.setAlignment(Pos.CENTER);
		hBoxForGameModes.getChildren().addAll(vBoxForClassicGame, vBoxForTimeAttackGame, vBoxForEndlessGame);
		
		dropShadow = new DropShadow();
		woodenFrameImage.setEffect(dropShadow);
		titleImage1.setEffect(dropShadow);
		titleImage2.setEffect(dropShadow);
		woodPanelFor5IconsImage.setEffect(dropShadow);
		
		nodesPane.getChildren().addAll(previousImage, movingEarthImage, titleImage2, titleImage1, titleLabel1,
				titleLabel2, woodPanelFor5IconsImage, vBoxForSound, backButton, nextButton, hBoxFor5Icons,
				rectangleForDifficultyLevel, vBoxForDifficultyLevel, rectangleForQuestionCategories, gridPaneForQuestionsCategories,
				extendedQuestionCategories, rectangleForExtendedQuestionCategories, scrollPaneForExtendedCategoryQuestionsGridPane, exitExtendedCategoryQuestions);
		
		if(animationsUsed != NO_ANIMATIONS)
		{
			setupLimitedAnimations();
			if(animationsUsed == ALL_ANIMATIONS) setupAdvancedAnimations();
		}
		
		setupListeners();
	}
	
	protected void setupListeners()
	{
		for(int i = 0; i < extQCCountries.length; i++)
		{
			if(i < extQCCategories.length) extQCCategories[i].setOnAction(handler);
			extQCCountries[i].setOnAction(handler);
			if(i < extQCUSA.length) extQCUSA[i].setOnAction(handler);
			if(i < extQCGreece.length) extQCGreece[i].setOnAction(handler);
		}
		
		continentsAndCountriesCheckBox.setOnAction(handler);
		USACheckBox.setOnAction(handler);
		greeceCheckBox.setOnAction(handler);
		attractionsCheckBox.setOnAction(handler);

		toggleGroupForDifficultyLevel.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) ->
			{
				if(mainScene.getRoot() == anchorPane) playRadioButtonSelectedSound();
				
				if(new_toggle == easyLevelRadioButton) setDifficultyLevel(DIFFICULTY_EASY);
				else setDifficultyLevel(DIFFICULTY_DIFFICULT);
				
				handler.updateCheckboxesState(false);
			});
		
		extendedQuestionCategories.setOnMouseClicked(e -> openExtendedQuestionCategories());
		
		exitExtendedCategoryQuestions.setOnMousePressed(e -> exitExtendedCategoryQuestions.setImage(X_ICON_CLICKED));
		
		exitExtendedCategoryQuestions.setOnMouseClicked(e -> closeExtendedQuestionCategories());
		
		exitExtendedCategoryQuestions.setOnMouseReleased(e -> exitExtendedCategoryQuestions.setImage(X_ICON));
		
		scrollPaneForExtendedCategoryQuestionsGridPane.setOnScroll(e -> scrollPaneForExtendedCategoryQuestionsGridPane.setHvalue(-e.getDeltaY()));
		
		classicGameButton.setOnMouseEntered(e -> playHoverSound());
		classicGameButton.setOnAction(e ->
		{
			playButtonClickSound();
			
			startGame(CLASSIC_GAMEMODE);
		});
		
		timeAttackGameButton.setOnMouseEntered(e -> playHoverSound());
		timeAttackGameButton.setOnAction(e ->
		{
			playButtonClickSound();
			
			startGame(TIME_ATTACK_GAMEMODE);
		});
		
		endlessGameButton.setOnMouseEntered(e -> playHoverSound());
		endlessGameButton.setOnAction(e ->
		{
			playButtonClickSound();
			
			startGame(ENDLESS_GAMEMODE);
		});
		
		toggleGroupForClassicGame.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) ->
		{
			if(old_toggle != null) playRadioButtonSelectedSound();
			
			if(new_toggle == radioButton10QuestionsForClassicGameLabel) setNumberOfQuestionsForClassic(NUM_OF_QUESTIONS_FOR_CLASSIC_10);
			else if(new_toggle == radioButton20QuestionsForClassicGameLabel) setNumberOfQuestionsForClassic(NUM_OF_QUESTIONS_FOR_CLASSIC_20);
			else if(new_toggle == radioButton50QuestionsForClassicGameLabel) setNumberOfQuestionsForClassic(NUM_OF_QUESTIONS_FOR_CLASSIC_50);
			else setNumberOfQuestionsForClassic(NUM_OF_QUESTIONS_FOR_CLASSIC_100);
			
			descriptionForClassicGameLabel.setText(getDescriptionForClassicGameMode());
		});
		
		countDownToggleForClassicGameLabel.setOnMouseClicked(e ->
		{
			countDownToggleForClassicGameLabel.setValue(!countDownToggleForClassicGameLabel.getValue());
			
			if(countDownToggleForClassicGameLabel.getValue()) playSwitchButtonOnSound();
			else playSwitchButtonOffSound();
			
			setTimerForClassic(countDownToggleForClassicGameLabel.getValue());
			
			descriptionForClassicGameLabel.setText(getDescriptionForClassicGameMode());
		});
		
		countDownToggleForClassicGameLabel.getButton().setOnAction(e ->
		{
			countDownToggleForClassicGameLabel.setValue(!countDownToggleForClassicGameLabel.getValue());
			
			if(countDownToggleForClassicGameLabel.getValue()) playSwitchButtonOnSound();
			else playSwitchButtonOffSound();
			
			setTimerForClassic(countDownToggleForClassicGameLabel.getValue());
			
			descriptionForClassicGameLabel.setText(getDescriptionForClassicGameMode());
		});
		
		toggleGroupForTimeAttackGame.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) ->
		{
			if(mainScene.getRoot() == anchorPane) playRadioButtonSelectedSound();
			
			if(new_toggle == radioButton1MinuteDurationForTimeAttackGameLabel) setDurationInMinutesForTimeAttack(TIME_ATTACK_DURATION_1_MINUTE);
			else if(new_toggle == radioButton2MinutesDurationForTimeAttackGameLabel) setDurationInMinutesForTimeAttack(TIME_ATTACK_DURATION_2_MINUTES);
			else if(new_toggle == radioButton5MinutesDurationForTimeAttackGameLabel) setDurationInMinutesForTimeAttack(TIME_ATTACK_DURATION_5_MINUTES);
			else setDurationInMinutesForTimeAttack(TIME_ATTACK_DURATION_10_MINUTES);
			
			descriptionForTimeAttackGameLabel.setText(getDescriptionForTimeAttackGameMode());
		});
		
		toggleGroupForEndlessGame.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) ->
		{
			if(mainScene.getRoot() == anchorPane) playRadioButtonSelectedSound();
			
			if(new_toggle == radioButton1LifeForEndlessGameLabel) setLivesForEndless(ENDLESS_LIVES_1);
			else if(new_toggle == radioButton3LivesForEndlessGameLabel) setLivesForEndless(ENDLESS_LIVES_3);
			else setLivesForEndless(ENDLESS_LIVES_5);
			
			descriptionForEndlessGameLabel.setText(getDescriptionForEndlessGameMode());
		});
		
		countDownToggleForEndlessGameLabel.setOnMouseClicked(e ->
		{
			countDownToggleForEndlessGameLabel.setValue(!countDownToggleForEndlessGameLabel.getValue());
			
			if(countDownToggleForEndlessGameLabel.getValue()) playSwitchButtonOnSound();
			else playSwitchButtonOffSound();
			
			setTimerForEndless(countDownToggleForEndlessGameLabel.getValue());
			
			descriptionForEndlessGameLabel.setText(getDescriptionForEndlessGameMode());
		});
		countDownToggleForEndlessGameLabel.getButton().setOnAction(e ->
		{
			countDownToggleForEndlessGameLabel.setValue(!countDownToggleForEndlessGameLabel.getValue());
			
			if(countDownToggleForEndlessGameLabel.getValue()) playSwitchButtonOnSound();
			else playSwitchButtonOffSound();
			
			setTimerForEndless(countDownToggleForEndlessGameLabel.getValue());
			
			descriptionForEndlessGameLabel.setText(getDescriptionForEndlessGameMode());
		});
		
		backButton.setOnAction(e ->
			{
				playButtonClickSound();
				
				if(screen1)
				{
					returnToWelcomeScreen();
				}
				else
				{
					if(animationsUsed != NO_ANIMATIONS) timeLineFromScreen2ToScreen1.playFromStart();
					else
					{
						if(vBoxForSound.isVisible())
						{
							vBoxForSound.setVisible(false);
							
							setSoundIcon(soundIcon, false);
							
							titleImage1.setTranslateX(0);
							titleLabel1.setTranslateX(0);
						}
						
						nodesPane.getChildren().clear();
						
						screen1 = true;
						
						updateStrings();
						
						nodesPane.getChildren().addAll(previousImage, movingEarthImage, titleImage2, titleImage1, titleLabel1,
								titleLabel2, woodPanelFor5IconsImage, vBoxForSound, backButton, nextButton, hBoxFor5Icons,
								rectangleForDifficultyLevel, vBoxForDifficultyLevel, rectangleForQuestionCategories, gridPaneForQuestionsCategories,
								extendedQuestionCategories, rectangleForExtendedQuestionCategories, scrollPaneForExtendedCategoryQuestionsGridPane, exitExtendedCategoryQuestions);
						
						recalculateBackground(stage.getWidth(), stage.getHeight());
						recalculateUI(stage.getWidth(), stage.getHeight());
						
						if (OS == OsCheck.OSType.Windows) vBoxForSound.setTranslateX(stage.getWidth() - vBoxForSound.getLayoutX() + 20);
						else vBoxForSound.setTranslateX(-1.0 * (vBoxForSound.getLayoutX() + vBoxForSound.getPrefWidth() + 20));
						vBoxForSound.setTranslateY(0);
					}
				}
				
			});
		
		backButton.setOnMouseEntered(e -> playHoverSound());
		
		nextButton.setOnAction(e ->
			{
				if(!isNextButtonDisabled())
				{
					playButtonClickSound();
					
					if(animationsUsed != NO_ANIMATIONS) timeLineFromScreen1ToScreen2.playFromStart();
					else
					{
						if(vBoxForSound.isVisible())
						{
							vBoxForSound.setVisible(false);
							
							setSoundIcon(soundIcon, false);
						}
						
						nodesPane.getChildren().clear();
						
						screen1 = false;
						
						updateStrings();
						
						nodesPane.getChildren().addAll(movingEarthImage, titleImage1, titleLabel1, hBoxForGameModes,
								woodPanelFor5IconsImage, backButton, vBoxForSound, hBoxFor5Icons);
						
						recalculateBackground(stage.getWidth(), stage.getHeight());
						recalculateUI(stage.getWidth(), stage.getHeight());
						
						vBoxForSound.setTranslateY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
						vBoxForSound.setTranslateX(0);
					}
				}
			});
		
		nextButton.setOnMouseEntered(e -> playHoverSound());
		
		soundIcon.setOnMouseClicked(e ->
			{
				soundIcon.setDisable(true);
				
				if (!vBoxForSound.isVisible())
				{
					setSoundIcon(soundIcon, true);
					
					if(animationsUsed != NO_ANIMATIONS) timelineToShowSoundOptions.play();
					else
					{
						if(screen1) vBoxForSound.setTranslateX(0);
						else
						{
							if(OS == OsCheck.OSType.Windows)
							{
								titleImage1.setTranslateX(-0.1484 * stage.getWidth());
								titleLabel1.setTranslateX(-0.1484 * stage.getWidth());
							}
							else
							{
								titleImage1.setTranslateX(0.1484 * stage.getWidth());
								titleLabel1.setTranslateX(0.1484 * stage.getWidth());
							}
							vBoxForSound.setTranslateY(0);
						}
						vBoxForSound.setVisible(true);
					}
				}
				else
				{
					setSoundIcon(soundIcon, false);
					
					if(animationsUsed != NO_ANIMATIONS) timelineToHideSoundOptions.play();
					else
					{
						if(screen1)
						{
							if (OS == OsCheck.OSType.Windows) vBoxForSound.setTranslateX(stage.getWidth() - vBoxForSound.getLayoutX() + 20);
							else vBoxForSound.setTranslateX(-1.0 * (vBoxForSound.getLayoutX() + vBoxForSound.getPrefWidth() + 20));
						}
						else
						{
							vBoxForSound.setTranslateY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
							titleImage1.setTranslateX(0);
							titleLabel1.setTranslateX(0);
						}
						vBoxForSound.setVisible(false);
					}
				}
				soundIcon.setDisable(false);
			});
		
		soundIcon.setOnMousePressed(e ->
		{
			if (!vBoxForSound.isVisible()) setSoundIcon(soundIcon, true);
			else setSoundIcon(soundIcon, false);
		});
		
		soundIcon.setOnMouseReleased(e ->
		{
			if (!vBoxForSound.isVisible()) setSoundIcon(soundIcon, false);
			else setSoundIcon(soundIcon, true);
		});
		
		movingEarthImage.setOnMouseClicked(e ->
			{
				if (animationsUsed == ALL_ANIMATIONS)
				{
					if (earthTransitionStatus == 1)
					{
						movingEarthTimeline.pause();
						previousEarthTransitionWasToLeft = true;
						earthTransitionStatus = 0;
					}
					else if (earthTransitionStatus == 0)
					{
						if (previousEarthTransitionWasToLeft) { earthTransitionStatus = -1; }
						else { earthTransitionStatus = 1; }
						movingEarthTimeline.play();
					}
					else
					{
						movingEarthTimeline.pause();
						previousEarthTransitionWasToLeft = false;
						earthTransitionStatus = 0;
					}
				}
			});
		
		anchorPane.addEventFilter(MouseEvent.MOUSE_PRESSED, e ->
		{
			if(isExtendedQuestionCategoriesOpen)
			{
				if((e.getSceneX() < scrollPaneForExtendedCategoryQuestionsGridPane.getLayoutX() || e.getSceneX() > scrollPaneForExtendedCategoryQuestionsGridPane.getLayoutX() + scrollPaneForExtendedCategoryQuestionsGridPane.getWidth()
				    || e.getSceneY() < scrollPaneForExtendedCategoryQuestionsGridPane.getLayoutY() || e.getSceneY() > scrollPaneForExtendedCategoryQuestionsGridPane.getLayoutY() + scrollPaneForExtendedCategoryQuestionsGridPane.getHeight())
				   && !anchorPane.getCursor().equals(Cursor.H_RESIZE) && !anchorPane.getCursor().equals(Cursor.V_RESIZE)
				   && !minimizeIcon.isHover() && !moveIcon.isHover() && !fullScreenIcon.isHover() && !exitIcon.isHover())
					pressedOutsideExtendedQuestionCategories = true;
				else pressedOutsideExtendedQuestionCategories = false;
			}
		});
		
		anchorPane.addEventFilter(MouseEvent.MOUSE_RELEASED, e ->
		{
			if(isExtendedQuestionCategoriesOpen)
			{
				if(e.getSceneX() < scrollPaneForExtendedCategoryQuestionsGridPane.getLayoutX() || e.getSceneX() > scrollPaneForExtendedCategoryQuestionsGridPane.getLayoutX() + scrollPaneForExtendedCategoryQuestionsGridPane.getWidth()
				   || e.getSceneY() < scrollPaneForExtendedCategoryQuestionsGridPane.getLayoutY() || e.getSceneY() > scrollPaneForExtendedCategoryQuestionsGridPane.getLayoutY() + scrollPaneForExtendedCategoryQuestionsGridPane.getHeight())
					if(pressedOutsideExtendedQuestionCategories) closeExtendedQuestionCategories();
			}
		});
		
		anchorPane.setOnKeyPressed(e ->
			{
				if (e.getCode() == KeyCode.ESCAPE)
				{
					if(isExtendedQuestionCategoriesOpen) closeExtendedQuestionCategories();
					else if(fullScreenMode) setWindowedMode();
				}
			});
	}
	
	protected void setupLimitedAnimations()
	{
		fadeTransitionForMovingEarthImage = new FadeTransition(Duration.millis(400), movingEarthImage);
		
		scaleTransitionForTitleLabel1 = new ScaleTransition(Duration.millis(300), titleLabel1);

		scaleTransitionForTitleLabel2 = new ScaleTransition(Duration.millis(300), titleLabel2);
		
		scaleTransitionForHBoxFor5Icons = new ScaleTransition(Duration.millis(300), hBoxFor5Icons);
		
		scaleTransitionForBackButton = new ScaleTransition(Duration.millis(300), backButton);

		scaleTransitionForNextButton = new ScaleTransition(Duration.millis(300), nextButton);

		scaleTransitionForVBoxForDifficultyLevel = new ScaleTransition(Duration.millis(300), vBoxForDifficultyLevel);

		scaleTransitionForGridPaneForQuestionsCategories = new ScaleTransition(Duration.millis(300), gridPaneForQuestionsCategories);

		scaleTransitionForRectangleForDifficultyLevel = new ScaleTransition(Duration.millis(300), rectangleForDifficultyLevel);

		scaleTransitionForRectangleForQuestionsCategories = new ScaleTransition(Duration.millis(300), rectangleForQuestionCategories);
		
		scaleTransitionForExtendedQuestionCategoriesLabel = new ScaleTransition(Duration.millis(300), extendedQuestionCategories);
		scaleTransitionForScrollPaneForExtendedQuestionCategories = new ScaleTransition(Duration.millis(300), scrollPaneForExtendedCategoryQuestionsGridPane);
		scaleTransitionForRectangleForExtendedQuestionCategories = new ScaleTransition(Duration.millis(300), rectangleForExtendedQuestionCategories);
		scaleTransitionForExitExtendedCategoryQuestions = new ScaleTransition(Duration.millis(100), exitExtendedCategoryQuestions);
		
		translateTransitionForTitleImage1 = new TranslateTransition(Duration.millis(300), titleImage1);
		
		translateTransitionForTitleLabel1 = new TranslateTransition(Duration.millis(300), titleLabel1);
		
		
		TranslateTransition translateTransitionForTitleImage2 = new TranslateTransition(Duration.millis(300), titleImage2);
		ScaleTransition scaleTransitionForTitleImage2 = new ScaleTransition(Duration.millis(300), titleImage2);
		parallelTransitionForTitleImage2 = new ParallelTransition(translateTransitionForTitleImage2, scaleTransitionForTitleImage2);
		
		translateTransitionForVBoxForSound = new TranslateTransition(Duration.millis(300), vBoxForSound);
		
		translateTransitionForWoodImageFor5Icons = new TranslateTransition(Duration.millis(300), woodPanelFor5IconsImage);
		
		scaleTransitionForVBoxForClassicGame = new ScaleTransition(Duration.millis(300), vBoxForClassicGame);
		
		scaleTransitionForVBoxForTimeAttackGame = new ScaleTransition(Duration.millis(300), vBoxForTimeAttackGame);
		
		scaleTransitionForVBoxForEndlessGame = new ScaleTransition(Duration.millis(300), vBoxForEndlessGame);
		
		timeLineToShowAllStuff = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					backButton.setDisable(true);
					nextButton.setDisable(true);
					hBoxFor5Icons.setDisable(true);
					
					fadeTransitionForMovingEarthImage.setToValue(1);
					fadeTransitionForMovingEarthImage.playFromStart();
				}),
				new KeyFrame(Duration.millis(400), e ->
				{
					translateTransitionForTitleImage1.setToX(0);
					translateTransitionForTitleImage1.setToY(0);
					translateTransitionForWoodImageFor5Icons.setToY(0);
					
					playSlideSound();
					translateTransitionForTitleImage1.play();
					translateTransitionForWoodImageFor5Icons.playFromStart();
				}),
				new KeyFrame(Duration.millis(700), e ->
				{
					translateTransitionForTitleImage2.setToY(0);
					scaleTransitionForTitleImage2.setToY(1);
					
					playSlideSound();
					parallelTransitionForTitleImage2.playFromStart();
				}),
				new KeyFrame(Duration.millis(1000), e ->
				{
					scaleTransitionForTitleLabel1.setDuration(Duration.millis(300));
					scaleTransitionForTitleLabel1.setFromX(0);
					scaleTransitionForTitleLabel1.setFromY(0);
					scaleTransitionForTitleLabel1.setToX(0.95);
					scaleTransitionForTitleLabel1.setToY(0.95);
					
					scaleTransitionForTitleLabel2.setDuration(Duration.millis(300));
					scaleTransitionForTitleLabel2.setFromX(0);
					scaleTransitionForTitleLabel2.setFromY(0);
					scaleTransitionForTitleLabel2.setToX(0.95);
					scaleTransitionForTitleLabel2.setToY(0.95);
					
					scaleTransitionForHBoxFor5Icons.setToX(1);
					scaleTransitionForHBoxFor5Icons.setToY(1);
					scaleTransitionForBackButton.setToX(1);
					scaleTransitionForBackButton.setToY(1);
					scaleTransitionForNextButton.setToX(1);
					scaleTransitionForNextButton.setToY(1);
					scaleTransitionForRectangleForDifficultyLevel.setToX(1);
					scaleTransitionForRectangleForDifficultyLevel.setToY(1);
					scaleTransitionForRectangleForQuestionsCategories.setToX(1);
					scaleTransitionForRectangleForQuestionsCategories.setToY(1);
					scaleTransitionForVBoxForDifficultyLevel.setToX(1);
					scaleTransitionForVBoxForDifficultyLevel.setToY(1);
					scaleTransitionForGridPaneForQuestionsCategories.setToX(1);
					scaleTransitionForGridPaneForQuestionsCategories.setToY(1);
					scaleTransitionForExtendedQuestionCategoriesLabel.setToX(1);
					scaleTransitionForExtendedQuestionCategoriesLabel.setToY(1);
					
					if(animationsUsed == ALL_ANIMATIONS)
					{
						scaleTransitionForTitleLabel1.setOnFinished(ev ->
						{
							scaleTransitionForTitleLabel1.setOnFinished(eve -> {});
							startTextAnimationForTitleLabel1();
							startTextAnimationForTitleLabel2();
						});
					}

					playPopUpSound();
					scaleTransitionForTitleLabel1.playFromStart();
					scaleTransitionForTitleLabel2.playFromStart();
					scaleTransitionForHBoxFor5Icons.playFromStart();
					scaleTransitionForBackButton.playFromStart();
					scaleTransitionForNextButton.playFromStart();
					scaleTransitionForRectangleForDifficultyLevel.playFromStart();
					scaleTransitionForRectangleForQuestionsCategories.playFromStart();
					scaleTransitionForVBoxForDifficultyLevel.playFromStart();
					scaleTransitionForGridPaneForQuestionsCategories.playFromStart();
					scaleTransitionForExtendedQuestionCategoriesLabel.playFromStart();
				}),
				new KeyFrame(Duration.millis(1300), e ->
				{
					backButton.setDisable(false);
					nextButton.setDisable(false);
					hBoxFor5Icons.setDisable(false);
				})
		);
		
		timeLineToHideAllStuffFromScreen1 = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					backButton.setDisable(true);
					nextButton.setDisable(true);
					hBoxFor5Icons.setDisable(true);
					
					scaleTransitionForTitleLabel1.setDuration(Duration.millis(300));
					scaleTransitionForTitleLabel1.setFromX(titleLabel1.getScaleX());
					scaleTransitionForTitleLabel1.setFromY(titleLabel1.getScaleY());
					scaleTransitionForTitleLabel1.setToX(0);
					scaleTransitionForTitleLabel1.setToY(0);
					scaleTransitionForTitleLabel1.setAutoReverse(false);
					scaleTransitionForTitleLabel1.setCycleCount(1);
					
					scaleTransitionForTitleLabel2.setDuration(Duration.millis(300));
					scaleTransitionForTitleLabel2.setFromX(titleLabel2.getScaleX());
					scaleTransitionForTitleLabel2.setFromY(titleLabel2.getScaleY());
					scaleTransitionForTitleLabel2.setToX(0);
					scaleTransitionForTitleLabel2.setToY(0);
					scaleTransitionForTitleLabel2.setAutoReverse(false);
					scaleTransitionForTitleLabel2.setCycleCount(1);
					
					scaleTransitionForTitleLabel1.setOnFinished(ev -> {});
					scaleTransitionForTitleLabel2.setOnFinished(ev -> {});
					
					scaleTransitionForRectangleForDifficultyLevel.setToX(0);
					scaleTransitionForRectangleForDifficultyLevel.setToY(0);
					scaleTransitionForRectangleForQuestionsCategories.setToX(0);
					scaleTransitionForRectangleForQuestionsCategories.setToY(0);
					scaleTransitionForVBoxForDifficultyLevel.setToX(0);
					scaleTransitionForVBoxForDifficultyLevel.setToY(0);
					scaleTransitionForGridPaneForQuestionsCategories.setToX(0);
					scaleTransitionForGridPaneForQuestionsCategories.setToY(0);
					scaleTransitionForExtendedQuestionCategoriesLabel.setToX(0);
					scaleTransitionForExtendedQuestionCategoriesLabel.setToY(0);
					scaleTransitionForHBoxFor5Icons.setToX(0);
					scaleTransitionForHBoxFor5Icons.setToY(0);
					scaleTransitionForBackButton.setToX(0);
					scaleTransitionForBackButton.setToY(0);
					scaleTransitionForNextButton.setToX(0);
					scaleTransitionForNextButton.setToY(0);

					playMinimizeSound();
					scaleTransitionForRectangleForDifficultyLevel.playFromStart();
					scaleTransitionForRectangleForQuestionsCategories.playFromStart();
					scaleTransitionForVBoxForDifficultyLevel.playFromStart();
					scaleTransitionForGridPaneForQuestionsCategories.playFromStart();
					scaleTransitionForExtendedQuestionCategoriesLabel.playFromStart();
					scaleTransitionForTitleLabel1.playFromStart();
					scaleTransitionForTitleLabel2.playFromStart();
					scaleTransitionForHBoxFor5Icons.playFromStart();
					scaleTransitionForBackButton.playFromStart();
					scaleTransitionForNextButton.playFromStart();
				}),
				new KeyFrame(Duration.millis(300), e ->
				{
					translateTransitionForTitleImage2.setToY(-1.0 * (0.231) * stage.getHeight());
					scaleTransitionForTitleImage2.setToY(0);
					
					playSlideSound();
					parallelTransitionForTitleImage2.playFromStart();
				}),
				new KeyFrame(Duration.millis(600), e ->
				{
					translateTransitionForTitleImage1.setToY(-1.0 * (titleImage1.getLayoutY() + titleImage1.getBoundsInParent().getHeight() + 20));
					translateTransitionForWoodImageFor5Icons.setToY(-1.0 * (woodPanelFor5IconsImage.getLayoutY() + woodPanelFor5IconsImage.getBoundsInParent().getHeight() + 20));
					
					playSlideSound();
					translateTransitionForTitleImage1.playFromStart();
					translateTransitionForWoodImageFor5Icons.playFromStart();
					if(vBoxForSound.isVisible()) timelineToHideSoundOptions.playFromStart();
				}),
				new KeyFrame(Duration.millis(900), e ->
				{
					fadeTransitionForMovingEarthImage.setToValue(0);
					fadeTransitionForMovingEarthImage.playFromStart();
				}),
				new KeyFrame(Duration.millis(1300), e ->
				{
					if(animationsUsed == ALL_ANIMATIONS)
					{
						pauseEarthAnimation();
						pauseTextAnimation();
					}
					
					backButton.setDisable(false);
					nextButton.setDisable(false);
					hBoxFor5Icons.setDisable(false);
				})
		);
		
		timeLineToHideAllStuffFromScreen2 = new Timeline(
			new KeyFrame(Duration.millis(200), e ->
			{
				backButton.setDisable(true);
				hBoxFor5Icons.setDisable(true);
				classicGameButton.setDisable(true);
				timeAttackGameButton.setDisable(true);
				endlessGameButton.setDisable(true);
				
				scaleTransitionForVBoxForClassicGame.setToY(0);
				scaleTransitionForVBoxForTimeAttackGame.setToY(0);
				scaleTransitionForVBoxForEndlessGame.setToY(0);
				
				playPopUpSound();
				scaleTransitionForVBoxForClassicGame.playFromStart();
			}),
			new KeyFrame(Duration.millis(400), e ->
			{
				playPopUpSound();
				scaleTransitionForVBoxForTimeAttackGame.playFromStart();
			}),
			new KeyFrame(Duration.millis(600), e ->
			{
				playPopUpSound();
				scaleTransitionForVBoxForEndlessGame.playFromStart();
			}),
			new KeyFrame(Duration.millis(900), e ->
			{
				scaleTransitionForTitleLabel1.setDuration(Duration.millis(300));
				scaleTransitionForTitleLabel1.setFromX(titleLabel1.getScaleX());
				scaleTransitionForTitleLabel1.setFromY(titleLabel1.getScaleY());
				scaleTransitionForTitleLabel1.setToX(0);
				scaleTransitionForTitleLabel1.setToY(0);
				scaleTransitionForTitleLabel1.setAutoReverse(false);
				scaleTransitionForTitleLabel1.setCycleCount(1);
				
				scaleTransitionForTitleLabel1.setOnFinished(ev -> {});
				
				scaleTransitionForBackButton.setToX(0);
				scaleTransitionForBackButton.setToY(0);
				
				scaleTransitionForHBoxFor5Icons.setToX(0);
				scaleTransitionForHBoxFor5Icons.setToY(0);
				
				playMinimizeSound();
				scaleTransitionForTitleLabel1.playFromStart();
				scaleTransitionForBackButton.playFromStart();
				scaleTransitionForHBoxFor5Icons.playFromStart();
			}),
			new KeyFrame(Duration.millis(1200), e ->
			{
				translateTransitionForTitleImage1.setToY(-1.0 * (titleImage1.getLayoutY() + titleImage1.getBoundsInParent().getHeight() + 20));
				translateTransitionForTitleImage1.setToX(titleImage1.getTranslateX());
				
				translateTransitionForWoodImageFor5Icons.setToY(-1.0 * (woodPanelFor5IconsImage.getLayoutY() + woodPanelFor5IconsImage.getBoundsInParent().getHeight() + 20));
				
				playSlideSound();
				translateTransitionForTitleImage1.playFromStart();
				translateTransitionForWoodImageFor5Icons.playFromStart();
				if(vBoxForSound.isVisible())
				{
					setSoundIcon(soundIcon, false);
					
					translateTransitionForVBoxForSound.setToY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
					translateTransitionForVBoxForSound.setToX(0);
					translateTransitionForVBoxForSound.playFromStart();
				}
			}),
			new KeyFrame(Duration.millis(1500), e ->
			{
				fadeTransitionForMovingEarthImage.setToValue(0);
				fadeTransitionForMovingEarthImage.playFromStart();
			}),
			new KeyFrame(Duration.millis(1900), e ->
			{
				if(animationsUsed == ALL_ANIMATIONS)
				{
					pauseEarthAnimation();
					pauseTextAnimation();
				}
				
				backButton.setDisable(false);
				hBoxFor5Icons.setDisable(false);
				classicGameButton.setDisable(false);
				timeAttackGameButton.setDisable(false);
				endlessGameButton.setDisable(false);
			}));
		
		timeLineFromScreen1ToScreen2 = new Timeline(
				new KeyFrame(Duration.millis(200), e ->
				{
					backButton.setDisable(true);
					nextButton.setDisable(true);
					hBoxFor5Icons.setDisable(true);
					classicGameButton.setDisable(true);
					timeAttackGameButton.setDisable(true);
					endlessGameButton.setDisable(true);
					
					scaleTransitionForTitleLabel1.setDuration(Duration.millis(300));
					scaleTransitionForTitleLabel1.setFromX(titleLabel1.getScaleX());
					scaleTransitionForTitleLabel1.setFromY(titleLabel1.getScaleY());
					scaleTransitionForTitleLabel1.setToX(0);
					scaleTransitionForTitleLabel1.setToY(0);
					scaleTransitionForTitleLabel1.setAutoReverse(false);
					scaleTransitionForTitleLabel1.setCycleCount(1);
					
					scaleTransitionForTitleLabel2.setDuration(Duration.millis(300));
					scaleTransitionForTitleLabel2.setFromX(titleLabel2.getScaleX());
					scaleTransitionForTitleLabel2.setFromY(titleLabel2.getScaleY());
					scaleTransitionForTitleLabel2.setToX(0);
					scaleTransitionForTitleLabel2.setToY(0);
					scaleTransitionForTitleLabel2.setAutoReverse(false);
					scaleTransitionForTitleLabel2.setCycleCount(1);
					
					scaleTransitionForTitleLabel1.setOnFinished(ev -> {});
					scaleTransitionForTitleLabel2.setOnFinished(ev -> {});
					
					scaleTransitionForRectangleForDifficultyLevel.setToX(0);
					scaleTransitionForRectangleForDifficultyLevel.setToY(0);
					scaleTransitionForRectangleForQuestionsCategories.setToX(0);
					scaleTransitionForRectangleForQuestionsCategories.setToY(0);
					scaleTransitionForVBoxForDifficultyLevel.setToX(0);
					scaleTransitionForVBoxForDifficultyLevel.setToY(0);
					scaleTransitionForGridPaneForQuestionsCategories.setToX(0);
					scaleTransitionForGridPaneForQuestionsCategories.setToY(0);
					scaleTransitionForExtendedQuestionCategoriesLabel.setToX(0);
					scaleTransitionForExtendedQuestionCategoriesLabel.setToY(0);
					scaleTransitionForBackButton.setToX(0);
					scaleTransitionForBackButton.setToY(0);
					scaleTransitionForNextButton.setToX(0);
					scaleTransitionForNextButton.setToY(0);

					playMinimizeSound();
					scaleTransitionForRectangleForDifficultyLevel.playFromStart();
					scaleTransitionForRectangleForQuestionsCategories.playFromStart();
					scaleTransitionForVBoxForDifficultyLevel.playFromStart();
					scaleTransitionForGridPaneForQuestionsCategories.playFromStart();
					scaleTransitionForExtendedQuestionCategoriesLabel.playFromStart();
					scaleTransitionForTitleLabel1.playFromStart();
					scaleTransitionForTitleLabel2.playFromStart();
					scaleTransitionForNextButton.playFromStart();
					scaleTransitionForBackButton.playFromStart();
				}),
				new KeyFrame(Duration.millis(500), e ->
				{
					translateTransitionForTitleImage2.setToY(-1.0 * (0.231) * stage.getHeight());
					scaleTransitionForTitleImage2.setToY(0);
					
					playSlideSound();
					parallelTransitionForTitleImage2.playFromStart();
					
					if(vBoxForSound.isVisible())
					{
						setSoundIcon(soundIcon, false);
						
						if (OS == OsCheck.OSType.Windows) translateTransitionForVBoxForSound.setToX(stage.getWidth() - vBoxForSound.getLayoutX() + 20);
						else translateTransitionForVBoxForSound.setToX(-1.0 * (vBoxForSound.getLayoutX() + vBoxForSound.getPrefWidth() + 20));
						
						translateTransitionForVBoxForSound.setToY(0);
						
						translateTransitionForVBoxForSound.playFromStart();
					}
				}),
				new KeyFrame(Duration.millis(800), e ->
				{
					vBoxForSound.setVisible(false);
					
					translateTransitionForTitleImage1.setToY(-1.0 * (titleImage1.getLayoutY() + titleImage1.getBoundsInParent().getHeight() + 20));
					
					playSlideSound();
					translateTransitionForTitleImage1.playFromStart();
				}),
				new KeyFrame(Duration.millis(1150), e ->
				{
					vBoxForSound.setTranslateY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
					vBoxForSound.setTranslateX(0);
					
					nodesPane.getChildren().clear();
					
					screen1 = false;
					
					updateStrings();
					
					nodesPane.getChildren().addAll(previousImage, movingEarthImage, titleImage1, titleLabel1, hBoxForGameModes,
							woodPanelFor5IconsImage, backButton, vBoxForSound, hBoxFor5Icons);
					
					recalculateBackground(stage.getWidth(), stage.getHeight());
					recalculateUI(stage.getWidth(), stage.getHeight());
				}),
                new KeyFrame(Duration.millis(1200), e ->
                {
                    translateTransitionForTitleImage1.setToX(0);
                    translateTransitionForTitleImage1.setToY(0);
	
	                playSlideSound();
                    translateTransitionForTitleImage1.playFromStart();
                }),
                new KeyFrame(Duration.millis(1500), e ->
                {
	                titleLabel1.setTranslateX(0);
	                scaleTransitionForTitleLabel1.setFromX(0);
	                scaleTransitionForTitleLabel1.setFromY(0);
	                scaleTransitionForTitleLabel1.setToX(0.95);
	                scaleTransitionForTitleLabel1.setToY(0.95);
	
	                scaleTransitionForBackButton.setToX(1);
	                scaleTransitionForBackButton.setToY(1);
	
	                if(animationsUsed == ALL_ANIMATIONS)
	                {
		                scaleTransitionForTitleLabel1.setOnFinished(ev ->
		                {
			                scaleTransitionForTitleLabel1.setOnFinished(eve -> {});
			                startTextAnimationForTitleLabel1();
		                });
	                }
	                
	                scaleTransitionForTitleLabel1.playFromStart();
	                scaleTransitionForBackButton.playFromStart();
                }),
                new KeyFrame(Duration.millis(1800), e ->
                {
	                scaleTransitionForVBoxForClassicGame.setToY(1);
	                scaleTransitionForVBoxForTimeAttackGame.setToY(1);
	                scaleTransitionForVBoxForEndlessGame.setToY(1);
	
	                playPopUpSound();
	                scaleTransitionForVBoxForClassicGame.playFromStart();
                }),
                new KeyFrame(Duration.millis(2000), e ->
                {
	                playPopUpSound();
	                scaleTransitionForVBoxForTimeAttackGame.playFromStart();
                }),
                new KeyFrame(Duration.millis(2200), e ->
                {
	                playPopUpSound();
                    scaleTransitionForVBoxForEndlessGame.playFromStart();
                }),
                new KeyFrame(Duration.millis(2500), e ->
                {
	                backButton.setDisable(false);
	                nextButton.setDisable(false);
	                hBoxFor5Icons.setDisable(false);
	                classicGameButton.setDisable(false);
	                timeAttackGameButton.setDisable(false);
	                endlessGameButton.setDisable(false);
                })
		);
		
		timeLineFromScreen2ToScreen1 = new Timeline(
				new KeyFrame(Duration.millis(200), e ->
				{
					backButton.setDisable(true);
					nextButton.setDisable(true);
					hBoxFor5Icons.setDisable(true);
					classicGameButton.setDisable(true);
					timeAttackGameButton.setDisable(true);
					endlessGameButton.setDisable(true);
					
					scaleTransitionForVBoxForClassicGame.setToY(0);
					scaleTransitionForVBoxForTimeAttackGame.setToY(0);
					scaleTransitionForVBoxForEndlessGame.setToY(0);
					
					playPopUpSound();
					scaleTransitionForVBoxForClassicGame.playFromStart();
				}),
				new KeyFrame(Duration.millis(400), e ->
				{
					playPopUpSound();
					scaleTransitionForVBoxForTimeAttackGame.playFromStart();
				}),
				new KeyFrame(Duration.millis(600), e ->
				{
					playPopUpSound();
					scaleTransitionForVBoxForEndlessGame.playFromStart();
				}),
				new KeyFrame(Duration.millis(900), e ->
				{
					scaleTransitionForTitleLabel1.setDuration(Duration.millis(300));
					scaleTransitionForTitleLabel1.setFromX(titleLabel1.getScaleX());
					scaleTransitionForTitleLabel1.setFromY(titleLabel1.getScaleY());
					scaleTransitionForTitleLabel1.setToX(0);
					scaleTransitionForTitleLabel1.setToY(0);
					scaleTransitionForTitleLabel1.setAutoReverse(false);
					scaleTransitionForTitleLabel1.setCycleCount(1);
					
					scaleTransitionForTitleLabel1.setOnFinished(ev -> {});
					
					scaleTransitionForBackButton.setToX(0);
					scaleTransitionForBackButton.setToY(0);
					
					playMinimizeSound();
					scaleTransitionForTitleLabel1.playFromStart();
					scaleTransitionForBackButton.playFromStart();
				}),
				new KeyFrame(Duration.millis(1200), e ->
				{
					translateTransitionForTitleImage1.setToY(-1.0 * (titleImage1.getLayoutY() + titleImage1.getBoundsInParent().getHeight() + 20));
					translateTransitionForTitleImage1.setToX(titleImage1.getTranslateX());
					
					playSlideSound();
					translateTransitionForTitleImage1.playFromStart();
					if(vBoxForSound.isVisible())
					{
						setSoundIcon(soundIcon, false);
						
						translateTransitionForVBoxForSound.setToY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
						translateTransitionForVBoxForSound.setToX(0);
						translateTransitionForVBoxForSound.playFromStart();
					}
				}),
				new KeyFrame(Duration.millis(1550), e ->
				{
					vBoxForSound.setVisible(false);
					
					if (OS == OsCheck.OSType.Windows) vBoxForSound.setTranslateX(stage.getWidth() - vBoxForSound.getLayoutX() + 20);
					else vBoxForSound.setTranslateX(-1.0 * (vBoxForSound.getLayoutX() + vBoxForSound.getPrefWidth() + 20));
					vBoxForSound.setTranslateY(0);
					
					nodesPane.getChildren().clear();
					
					screen1 = true;
					
					updateStrings();
					
					nodesPane.getChildren().addAll(previousImage, movingEarthImage, titleImage2, titleImage1, titleLabel1,
							titleLabel2, woodPanelFor5IconsImage, vBoxForSound, backButton, nextButton, hBoxFor5Icons,
							rectangleForDifficultyLevel, vBoxForDifficultyLevel, rectangleForQuestionCategories, gridPaneForQuestionsCategories,
							extendedQuestionCategories, rectangleForExtendedQuestionCategories, scrollPaneForExtendedCategoryQuestionsGridPane, exitExtendedCategoryQuestions);
					
					recalculateBackground(stage.getWidth(), stage.getHeight());
					recalculateUI(stage.getWidth(), stage.getHeight());
				}),
				new KeyFrame(Duration.millis(1600), e ->
				{
					titleImage1.setTranslateX(0);
					titleLabel1.setTranslateX(0);
					
					translateTransitionForTitleImage1.setToX(0);
					translateTransitionForTitleImage1.setToY(0);
					
					playSlideSound();
					translateTransitionForTitleImage1.playFromStart();
				}),
				new KeyFrame(Duration.millis(1900), e ->
				{
					translateTransitionForTitleImage2.setToY(0);
					scaleTransitionForTitleImage2.setToY(1);
					
					playSlideSound();
					parallelTransitionForTitleImage2.playFromStart();
				}),
				new KeyFrame(Duration.millis(2200), e ->
				{
					scaleTransitionForTitleLabel1.setFromX(0);
					scaleTransitionForTitleLabel1.setFromY(0);
					scaleTransitionForTitleLabel1.setToX(0.95);
					scaleTransitionForTitleLabel1.setToY(0.95);
					
					scaleTransitionForTitleLabel2.setFromX(0);
					scaleTransitionForTitleLabel2.setFromY(0);
					scaleTransitionForTitleLabel2.setToX(0.95);
					scaleTransitionForTitleLabel2.setToY(0.95);
					
					scaleTransitionForBackButton.setToX(1);
					scaleTransitionForBackButton.setToY(1);
					scaleTransitionForNextButton.setToX(1);
					scaleTransitionForNextButton.setToY(1);
					scaleTransitionForRectangleForDifficultyLevel.setToX(1);
					scaleTransitionForRectangleForDifficultyLevel.setToY(1);
					scaleTransitionForRectangleForQuestionsCategories.setToX(1);
					scaleTransitionForRectangleForQuestionsCategories.setToY(1);
					scaleTransitionForVBoxForDifficultyLevel.setToX(1);
					scaleTransitionForVBoxForDifficultyLevel.setToY(1);
					scaleTransitionForGridPaneForQuestionsCategories.setToX(1);
					scaleTransitionForGridPaneForQuestionsCategories.setToY(1);
					scaleTransitionForExtendedQuestionCategoriesLabel.setToX(1);
					scaleTransitionForExtendedQuestionCategoriesLabel.setToY(1);
					
					if(animationsUsed == ALL_ANIMATIONS)
					{
						scaleTransitionForTitleLabel1.setOnFinished(ev ->
						{
							scaleTransitionForTitleLabel1.setOnFinished(eve -> {});
							startTextAnimationForTitleLabel1();
							startTextAnimationForTitleLabel2();
						});
					}
					
					playPopUpSound();
					scaleTransitionForTitleLabel1.playFromStart();
					scaleTransitionForTitleLabel2.playFromStart();
					scaleTransitionForHBoxFor5Icons.playFromStart();
					scaleTransitionForBackButton.playFromStart();
					scaleTransitionForNextButton.playFromStart();
					scaleTransitionForRectangleForDifficultyLevel.playFromStart();
					scaleTransitionForRectangleForQuestionsCategories.playFromStart();
					scaleTransitionForVBoxForDifficultyLevel.playFromStart();
					scaleTransitionForGridPaneForQuestionsCategories.playFromStart();
					scaleTransitionForExtendedQuestionCategoriesLabel.playFromStart();
				}),
				new KeyFrame(Duration.millis(2500), e ->
				{
					backButton.setDisable(false);
					nextButton.setDisable(false);
					hBoxFor5Icons.setDisable(false);
					classicGameButton.setDisable(false);
					timeAttackGameButton.setDisable(false);
					endlessGameButton.setDisable(false);
				})
		);
		
		timelineToShowSoundOptions = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					soundIcon.setDisable(true);
					
					if(screen1)
					{
						vBoxForSound.setVisible(true);
						translateTransitionForVBoxForSound.setToX(0);
						translateTransitionForVBoxForSound.setToY(0);
						
						playSlideSound();
						translateTransitionForVBoxForSound.playFromStart();
					}
					else
					{
						if(OS == OsCheck.OSType.Windows)
						{
							translateTransitionForTitleImage1.setToX(-0.1484 * stage.getWidth());
							translateTransitionForTitleLabel1.setToX(-0.1484 * stage.getWidth());
						}
						else
						{
							translateTransitionForTitleImage1.setToX(0.1484 * stage.getWidth());
							translateTransitionForTitleLabel1.setToX(0.1484 * stage.getWidth());
						}
						translateTransitionForTitleImage1.setToY(0);
						translateTransitionForTitleLabel1.setToY(0);
						
						playSlideSound();
						translateTransitionForTitleImage1.playFromStart();
						translateTransitionForTitleLabel1.playFromStart();
					}
				}),
				new KeyFrame(Duration.millis(150), e ->
				{
					if(!screen1)
					{
						vBoxForSound.setVisible(true);
						translateTransitionForVBoxForSound.setToX(0);
						translateTransitionForVBoxForSound.setToY(0);
						
						playSlideSound();
						translateTransitionForVBoxForSound.playFromStart();
					}
					else
					{
						soundIcon.setDisable(false);
					}
				}),
				new KeyFrame(Duration.millis(450), e ->
				{
					if(!screen1) soundIcon.setDisable(false);
				})
		);
		
		timelineToHideSoundOptions = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					soundIcon.setDisable(true);
					
					if(screen1)
					{
						if (OS == OsCheck.OSType.Windows) translateTransitionForVBoxForSound.setToX(stage.getWidth() - vBoxForSound.getLayoutX() + 20);
						else translateTransitionForVBoxForSound.setToX(-1.0 * (vBoxForSound.getLayoutX() + vBoxForSound.getPrefWidth() + 20));
						
						translateTransitionForVBoxForSound.setToY(0);
					}
					else
					{
						translateTransitionForVBoxForSound.setToY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
						translateTransitionForVBoxForSound.setToX(0);
					}
					
					playSlideSound();
					translateTransitionForVBoxForSound.playFromStart();
				}),
				new KeyFrame(Duration.millis(150), e ->
				{
					if(!screen1)
					{
						translateTransitionForTitleImage1.setToY(0);
						translateTransitionForTitleImage1.setToX(0);
						translateTransitionForTitleLabel1.setToY(0);
						translateTransitionForTitleLabel1.setToX(0);
						
						playSlideSound();
						translateTransitionForTitleImage1.playFromStart();
						translateTransitionForTitleLabel1.playFromStart();
					}
					else
					{
						soundIcon.setDisable(false);
						vBoxForSound.setVisible(false);
					}
				}),
				new KeyFrame(Duration.millis(450), e ->
				{
					if(!screen1)
					{
						soundIcon.setDisable(false);
						vBoxForSound.setVisible(false);
					}
				})
		);
	}
	
	void showScreen(boolean fromWelcomeScreen)
	{
		setInitialStateForAllNodes(fromWelcomeScreen);
		
		if (fullScreenMode) setFullScreenMode();
		else setWindowedMode();
		
		mainScene.setRoot(anchorPane);
		
		if(animationsUsed != NO_ANIMATIONS)
		{
			movingEarthImage.setOpacity(0);
			timeLineToShowAllStuff.playFromStart();
		}
	}
	
	private void setInitialStateForAllNodes(boolean fromWelcomeScreen)
	{
//		--------------- Load images if not already set ---------------
		if(woodenFrameImage.getImage() == null || !woodenFrameImage.getImage().equals(FRAME_IMAGE))
		{
			woodenFrameImage.setImage(FRAME_IMAGE);
			movingEarthImage.setImage(MOVING_EARTH_IMAGE_2);
			woodPanelFor5IconsImage.setImage(WOOD_BACKGROUND_IMAGE_FOR_5_BUTTONS);
			
			titleImage1.setImage(EMPTY_WOOD_BACKGROUND_PANEL_SMALL_ROPE);
			titleImage2.setImage(EMPTY_WOOD_BACKGROUND_PANEL_BIG_ROPE);
			
			if(fromWelcomeScreen) previousImage.setImage(CHALKBOARD_BACKGROUND_IMAGE);
			else previousImage.setImage(WORLD_MAP);
			
			backArrowImage.setImage(BACK_ARROW);
			nextArrowImage.setImage(BACK_ARROW);
			
			exitExtendedCategoryQuestions.setImage(X_ICON);
		}
		
		if(!screen1)
		{
			if(fromWelcomeScreen) previousImage.setImage(CHALKBOARD_BACKGROUND_IMAGE);
			else previousImage.setImage(WORLD_MAP);
			
			nodesPane.getChildren().clear();
			
			screen1 = true;
			
			updateStrings();
			
			nodesPane.getChildren().addAll(previousImage, movingEarthImage, titleImage2, titleImage1, titleLabel1,
					titleLabel2, woodPanelFor5IconsImage, vBoxForSound, backButton, nextButton, hBoxFor5Icons,
					rectangleForDifficultyLevel, vBoxForDifficultyLevel, rectangleForQuestionCategories, gridPaneForQuestionsCategories,
					extendedQuestionCategories, rectangleForExtendedQuestionCategories, scrollPaneForExtendedCategoryQuestionsGridPane, exitExtendedCategoryQuestions);
		}
		
//		--------------- SET POSITIONS ---------------
		if (OS == OsCheck.OSType.Windows) vBoxForSound.setTranslateX(stage.getWidth() - vBoxForSound.getLayoutX() + 20);
		else vBoxForSound.setTranslateX(-1.0 * (vBoxForSound.getLayoutX() + vBoxForSound.getPrefWidth() + 20));
		
		vBoxForSound.setTranslateY(0);
		
		vBoxForSound.setVisible(false);
		
		rectangleForExtendedQuestionCategories.setScaleX(0);
		rectangleForExtendedQuestionCategories.setScaleY(0);
		scrollPaneForExtendedCategoryQuestionsGridPane.setScaleX(0);
		scrollPaneForExtendedCategoryQuestionsGridPane.setScaleY(0);
		exitExtendedCategoryQuestions.setScaleX(0);
		exitExtendedCategoryQuestions.setScaleY(0);
		
		if(animationsUsed != NO_ANIMATIONS)
		{
			titleImage1.setTranslateY(-1.0 * (titleImage1.getLayoutY() + titleImage1.getBoundsInParent().getHeight() + 20));
			titleImage2.setTranslateY(-1.0 * (0.231) * stage.getHeight());
			woodPanelFor5IconsImage.setTranslateY(-1.0 * (woodPanelFor5IconsImage.getLayoutY() + woodPanelFor5IconsImage.getBoundsInParent().getHeight() + 20));
			
			titleImage2.setScaleY(0);
			
			hBoxFor5Icons.setScaleX(0);
			hBoxFor5Icons.setScaleY(0);
			
			titleLabel1.setScaleX(0);
			titleLabel1.setScaleY(0);
			
			titleLabel2.setScaleX(0);
			titleLabel2.setScaleY(0);
			
			rectangleForDifficultyLevel.setScaleX(0);
			rectangleForDifficultyLevel.setScaleY(0);
			
			rectangleForQuestionCategories.setScaleX(0);
			rectangleForQuestionCategories.setScaleY(0);
			
			vBoxForDifficultyLevel.setScaleX(0);
			vBoxForDifficultyLevel.setScaleY(0);
			
			gridPaneForQuestionsCategories.setScaleX(0);
			gridPaneForQuestionsCategories.setScaleY(0);
			
			extendedQuestionCategories.setScaleX(0);
			extendedQuestionCategories.setScaleY(0);
			
			vBoxForClassicGame.setScaleY(0);
			vBoxForTimeAttackGame.setScaleY(0);
			vBoxForEndlessGame.setScaleY(0);
			
			backButton.setScaleX(0);
			backButton.setScaleY(0);
			
			nextButton.setScaleX(0);
			nextButton.setScaleY(0);
		}
		else
		{
			movingEarthImage.setOpacity(1);
			
			titleImage1.setTranslateY(0);
			titleImage2.setTranslateY(0);
			woodPanelFor5IconsImage.setTranslateY(0);
			
			titleImage2.setScaleY(1);
			
			hBoxFor5Icons.setScaleX(1);
			hBoxFor5Icons.setScaleY(1);
			
			titleLabel1.setScaleX(1);
			titleLabel1.setScaleY(1);
			
			titleLabel2.setScaleX(1);
			titleLabel2.setScaleY(1);
			
			rectangleForDifficultyLevel.setScaleX(1);
			rectangleForDifficultyLevel.setScaleY(1);
			
			rectangleForQuestionCategories.setScaleX(1);
			rectangleForQuestionCategories.setScaleY(1);
			
			vBoxForDifficultyLevel.setScaleX(1);
			vBoxForDifficultyLevel.setScaleY(1);
			
			gridPaneForQuestionsCategories.setScaleX(1);
			gridPaneForQuestionsCategories.setScaleY(1);
			
			extendedQuestionCategories.setScaleX(1);
			extendedQuestionCategories.setScaleY(1);
			
			vBoxForClassicGame.setScaleY(1);
			vBoxForTimeAttackGame.setScaleY(1);
			vBoxForEndlessGame.setScaleY(1);
			
			backButton.setScaleX(1);
			backButton.setScaleY(1);
			
			nextButton.setScaleX(1);
			nextButton.setScaleY(1);
			
			backButton.setDisable(false);
			nextButton.setDisable(false);
			soundIcon.setDisable(false);
			minimizeIcon.setDisable(false);
			fullScreenIcon.setDisable(false);
			exitIcon.setDisable(false);
			moveIcon.setDisable(false);
		}
		
//		--------------- SET VARIABLES AND TEXT ---------------
		if (getDifficultyLevel() == DIFFICULTY_EASY) easyLevelRadioButton.setSelected(true);
		else difficultLevelRadioButton.setSelected(true);

		for(int i = 0; i < NUM_ALL_CATEGORIES; i++)
		{
			if(i < NUM_CAT_COUNTRIES_ALL)
				extQCCountries[i].setSelected(isCategorySelected(i));
			else if(i < NUM_CAT_COUNTRIES_ALL + NUM_CAT_USA_ALL)
				extQCUSA[i - NUM_CAT_COUNTRIES_ALL].setSelected(isCategorySelected(i));
			else if(i < NUM_CAT_COUNTRIES_ALL + NUM_CAT_USA_ALL + NUM_CAT_GREECE_ALL)
				extQCGreece[i - (NUM_CAT_COUNTRIES_ALL + NUM_CAT_USA_ALL)].setSelected(isCategorySelected(i));
			else
				extQCCategories[3].setSelected(isCategorySelected(i));
		}
		handler.updateCheckboxesState(true);
		
		updateNextButtonState();
		
		setViewPortProperties();
		
		if (animationsUsed == ALL_ANIMATIONS)
		{
			movingEarthImage.setCursor(Cursor.HAND);
			playEarthAnimation();
		}
		else movingEarthImage.setCursor(Cursor.DEFAULT);
		
		countDownToggleForClassicGameLabel.setValue(isTimerForClassic());
		countDownToggleForEndlessGameLabel.setValue(isTimerForEndless());
		
		if(getNumberOfQuestionsForClassic() == 10) radioButton10QuestionsForClassicGameLabel.setSelected(true);
		else if(getNumberOfQuestionsForClassic() == 20) radioButton20QuestionsForClassicGameLabel.setSelected(true);
		else if(getNumberOfQuestionsForClassic() == 50) radioButton50QuestionsForClassicGameLabel.setSelected(true);
		else radioButton100QuestionsForClassicGameLabel.setSelected(true);
		
		if(getDurationInMinutesForTimeAttack() == 1) radioButton1MinuteDurationForTimeAttackGameLabel.setSelected(true);
		else if(getDurationInMinutesForTimeAttack() == 2) radioButton2MinutesDurationForTimeAttackGameLabel.setSelected(true);
		else if(getDurationInMinutesForTimeAttack() == 5) radioButton5MinutesDurationForTimeAttackGameLabel.setSelected(true);
		else radioButton10MinutesDurationForTimeAttackGameLabel.setSelected(true);
		
		if(getLivesForEndless() == 1) radioButton1LifeForEndlessGameLabel.setSelected(true);
		else if(getLivesForEndless() == 3) radioButton3LivesForEndlessGameLabel.setSelected(true);
		else radioButton5LivesForEndlessGameLabel.setSelected(true);
		
		updateStrings();
	}
	
	private void openExtendedQuestionCategories()
	{
		setExtendedQuestionCategoriesStrings();
		
		if(animationsUsed != NO_ANIMATIONS)
		{
			scaleTransitionForTitleLabel1.setDuration(Duration.millis(300));
			scaleTransitionForTitleLabel1.setAutoReverse(false);
			scaleTransitionForTitleLabel1.setCycleCount(1);
			scaleTransitionForTitleLabel1.setFromX(titleLabel1.getScaleX());
			scaleTransitionForTitleLabel1.setFromY(titleLabel1.getScaleY());
			scaleTransitionForTitleLabel1.setToX(0);
			scaleTransitionForTitleLabel1.setToY(0);
			scaleTransitionForTitleLabel1.setOnFinished(e ->
			{
				titleLabel1.setText(languageResourceBundle.getString("extendedQuestionCategories"));
				
				if (getCurrentLanguage() == LANGUAGE_GREEK) titleLabel1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0302 * stage.getWidth()));
				else titleLabel1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0401 * stage.getWidth()));
				
				pauseTextAnimation();
				
				scaleTransitionForTitleLabel1.setFromX(0);
				scaleTransitionForTitleLabel1.setFromY(0);
				scaleTransitionForTitleLabel1.setToX(titleLabel2.getScaleX());
				scaleTransitionForTitleLabel1.setToY(titleLabel2.getScaleY());
				
				if(animationsUsed == ALL_ANIMATIONS)
				{
					scaleTransitionForTitleLabel1.setOnFinished(ev ->
					{
						scaleTransitionForTitleLabel1.setOnFinished(eve -> {});
						startTextAnimationForTitleLabel1();
						startTextAnimationForTitleLabel2();
					});
				}
				else scaleTransitionForTitleLabel1.setOnFinished(ev -> {});
				
				scaleTransitionForTitleLabel1.playFromStart();
			});
			
			scaleTransitionForScrollPaneForExtendedQuestionCategories.setToX(1);
			scaleTransitionForScrollPaneForExtendedQuestionCategories.setToY(1);
			scaleTransitionForRectangleForExtendedQuestionCategories.setToX(1);
			scaleTransitionForRectangleForExtendedQuestionCategories.setToY(1);
			scaleTransitionForExitExtendedCategoryQuestions.setToX(1);
			scaleTransitionForExitExtendedCategoryQuestions.setToY(1);
			
			scaleTransitionForRectangleForExtendedQuestionCategories.setOnFinished(e ->
			{
				scaleTransitionForRectangleForExtendedQuestionCategories.setOnFinished(ev -> {});
				scaleTransitionForExitExtendedCategoryQuestions.playFromStart();
			});
			
			playMaximizeSound();
			scaleTransitionForScrollPaneForExtendedQuestionCategories.playFromStart();
			scaleTransitionForRectangleForExtendedQuestionCategories.playFromStart();
			scaleTransitionForTitleLabel1.playFromStart();
		}
		else
		{
			titleLabel1.setText(languageResourceBundle.getString("extendedQuestionCategories"));
			
			if (getCurrentLanguage() == LANGUAGE_GREEK) titleLabel1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0302 * stage.getWidth()));
			else titleLabel1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0401 * stage.getWidth()));
			
			scrollPaneForExtendedCategoryQuestionsGridPane.setScaleX(1);
			scrollPaneForExtendedCategoryQuestionsGridPane.setScaleY(1);
			rectangleForExtendedQuestionCategories.setScaleX(1);
			rectangleForExtendedQuestionCategories.setScaleY(1);
			exitExtendedCategoryQuestions.setScaleX(1);
			exitExtendedCategoryQuestions.setScaleY(1);
		}
		isExtendedQuestionCategoriesOpen = true;
		
		backButton.setDisable(true);
		nextButton.setDisable(true);
		vBoxForDifficultyLevel.setDisable(true);
		extendedQuestionCategories.setDisable(true);
		soundIcon.setDisable(true);
	}
	
	private void closeExtendedQuestionCategories()
	{
		if(animationsUsed != NO_ANIMATIONS)
		{
			scaleTransitionForTitleLabel1.setDuration(Duration.millis(300));
			scaleTransitionForTitleLabel1.setAutoReverse(false);
			scaleTransitionForTitleLabel1.setCycleCount(1);
			scaleTransitionForTitleLabel1.setFromX(titleLabel1.getScaleX());
			scaleTransitionForTitleLabel1.setFromY(titleLabel1.getScaleY());
			scaleTransitionForTitleLabel1.setToX(0);
			scaleTransitionForTitleLabel1.setToY(0);
			scaleTransitionForTitleLabel1.setOnFinished(e ->
			{
				titleLabel1.setText(languageResourceBundle.getString("titleForDifficultySelectionLabel"));
				
				if (getCurrentLanguage() == LANGUAGE_GREEK) titleLabel1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0339 * stage.getWidth()));
				else titleLabel1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0354 * stage.getWidth()));
				
				pauseTextAnimation();
				
				scaleTransitionForTitleLabel1.setFromX(0);
				scaleTransitionForTitleLabel1.setFromY(0);
				scaleTransitionForTitleLabel1.setToX(titleLabel2.getScaleX());
				scaleTransitionForTitleLabel1.setToY(titleLabel2.getScaleY());
				
				if(animationsUsed == ALL_ANIMATIONS)
				{
					scaleTransitionForTitleLabel1.setOnFinished(ev ->
					{
						scaleTransitionForTitleLabel1.setOnFinished(eve -> {});
						startTextAnimationForTitleLabel1();
						startTextAnimationForTitleLabel2();
					});
				}
				else scaleTransitionForTitleLabel1.setOnFinished(ev -> {});
				
				scaleTransitionForTitleLabel1.playFromStart();
			});
			
			scaleTransitionForScrollPaneForExtendedQuestionCategories.setToX(0);
			scaleTransitionForScrollPaneForExtendedQuestionCategories.setToY(0);
			scaleTransitionForRectangleForExtendedQuestionCategories.setToX(0);
			scaleTransitionForRectangleForExtendedQuestionCategories.setToY(0);
			scaleTransitionForExitExtendedCategoryQuestions.setToX(0);
			scaleTransitionForExitExtendedCategoryQuestions.setToY(0);
			
			scaleTransitionForExitExtendedCategoryQuestions.setOnFinished(e ->
			{
				scaleTransitionForExitExtendedCategoryQuestions.setOnFinished(ev -> {});
				
				playMinimizeSound();
				scaleTransitionForScrollPaneForExtendedQuestionCategories.playFromStart();
				scaleTransitionForRectangleForExtendedQuestionCategories.playFromStart();
			});
			scaleTransitionForExitExtendedCategoryQuestions.playFromStart();
			scaleTransitionForTitleLabel1.playFromStart();
		}
		else
		{
			titleLabel1.setText(languageResourceBundle.getString("titleForDifficultySelectionLabel"));
			
			if (getCurrentLanguage() == LANGUAGE_GREEK) titleLabel1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0339 * stage.getWidth()));
			else titleLabel1.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0354 * stage.getWidth()));
			
			scrollPaneForExtendedCategoryQuestionsGridPane.setScaleX(0);
			scrollPaneForExtendedCategoryQuestionsGridPane.setScaleY(0);
			rectangleForExtendedQuestionCategories.setScaleX(0);
			rectangleForExtendedQuestionCategories.setScaleY(0);
			exitExtendedCategoryQuestions.setScaleX(0);
			exitExtendedCategoryQuestions.setScaleY(0);
		}
		isExtendedQuestionCategoriesOpen = false;
		
		backButton.setDisable(false);
		nextButton.setDisable(false);
		vBoxForDifficultyLevel.setDisable(false);
		extendedQuestionCategories.setDisable(false);
		soundIcon.setDisable(false);
	}
	
	private void setExtendedQuestionCategoriesStrings()
	{
		extQCCategories[0].setText(languageResourceBundle.getString("extContinentsAndCountries"));
		extQCCategories[1].setText(languageResourceBundle.getString("extUSA"));
		extQCCategories[2].setText(languageResourceBundle.getString("extGreece"));
		extQCCategories[3].setText(languageResourceBundle.getString("extAttractions"));
		
		extQCCountries[0].setText(languageResourceBundle.getString("extCapital"));
		extQCCountries[1].setText(languageResourceBundle.getString("extLargestCities"));
		extQCCountries[2].setText(languageResourceBundle.getString("extOfficialLanguages"));
		extQCCountries[3].setText(languageResourceBundle.getString("extCurrency"));
		extQCCountries[4].setText(languageResourceBundle.getString("extPopulation"));
		extQCCountries[5].setText(languageResourceBundle.getString("extArea"));
		extQCCountries[6].setText(languageResourceBundle.getString("extContinents_Countries"));
		extQCCountries[7].setText(languageResourceBundle.getString("extSovereignStates"));
		extQCCountries[8].setText(languageResourceBundle.getString("extFlag"));
		extQCCountries[9].setText(languageResourceBundle.getString("extCoatOfArms"));
		extQCCountries[10].setText(languageResourceBundle.getString("extLocation"));
		
		extQCUSA[0].setText(languageResourceBundle.getString("extCapital"));
		extQCUSA[1].setText(languageResourceBundle.getString("extLargestCities"));
		extQCUSA[2].setText(languageResourceBundle.getString("extUSAYearEnteredUnion"));
		extQCUSA[3].setText(languageResourceBundle.getString("extUSANumberOfSeats"));
		extQCUSA[4].setText(languageResourceBundle.getString("extPopulation"));
		extQCUSA[5].setText(languageResourceBundle.getString("extArea"));
		extQCUSA[6].setText(languageResourceBundle.getString("extGeographicalCharacteristics"));
		extQCUSA[7].setText(languageResourceBundle.getString("extFlag"));
		extQCUSA[8].setText(languageResourceBundle.getString("extLocation"));

		extQCGreece[0].setText(languageResourceBundle.getString("extAdministrativeDivision"));
		extQCGreece[1].setText(languageResourceBundle.getString("extLargestCities"));
		extQCGreece[2].setText(languageResourceBundle.getString("extLargestMunicipalities"));
		extQCGreece[3].setText(languageResourceBundle.getString("extGeographicalCharacteristics"));
		extQCGreece[4].setText(languageResourceBundle.getString("extPopulation"));
		extQCGreece[5].setText(languageResourceBundle.getString("extArea"));
		extQCGreece[6].setText(languageResourceBundle.getString("extLocation"));
	}
	
	private void updateNextButtonState()
	{
		if(continentsAndCountriesCheckBox.isSelected() || USACheckBox.isSelected() || greeceCheckBox.isSelected() || attractionsCheckBox.isSelected() ||
		   continentsAndCountriesCheckBox.isIndeterminate() || USACheckBox.isIndeterminate() || greeceCheckBox.isIndeterminate() || attractionsCheckBox.isIndeterminate())
		{
			nextButton.getTooltip().setText(languageResourceBundle.getString("nextButtonTooltipInDifficultySelectionScreenEnabled"));
			nextButton.setCursor(Cursor.HAND);
		}
		else
		{
			nextButton.getTooltip().setText(languageResourceBundle.getString("nextButtonTooltipInDifficultySelectionScreenDisabled"));
			nextButton.setCursor(Cursor.DEFAULT);
		}
	}
	
	private boolean isNextButtonDisabled()
	{
		if(continentsAndCountriesCheckBox.isSelected() || USACheckBox.isSelected() || greeceCheckBox.isSelected() || attractionsCheckBox.isSelected() ||
		   continentsAndCountriesCheckBox.isIndeterminate() || USACheckBox.isIndeterminate() || greeceCheckBox.isIndeterminate() || attractionsCheckBox.isIndeterminate())
		return false;
		else return true;
	}
	
	private void updateStrings()
	{
		if(screen1)
		{
			titleLabel1.setText(languageResourceBundle.getString("titleForDifficultySelectionLabel"));
			titleLabel2.setText(languageResourceBundle.getString("titleForCategoriesSelectionLabel"));
			
			nextButton.setText(languageResourceBundle.getString("nextButton"));
			nextButton.getTooltip().setText(languageResourceBundle.getString("nextButtonTooltipInDifficultySelectionScreenEnabled"));
			
			easyLevelRadioButton.setText(languageResourceBundle.getString("easyLabel"));
			difficultLevelRadioButton.setText(languageResourceBundle.getString("difficultLabel"));
			
			continentsAndCountriesCheckBox.setText(languageResourceBundle.getString("continentsAndCountries"));
			USACheckBox.setText(languageResourceBundle.getString("usa"));
			greeceCheckBox.setText(languageResourceBundle.getString("greece"));
			attractionsCheckBox.setText(languageResourceBundle.getString("attractions"));
			
			continentsAndCountriesCheckBox.getTooltip().setText(languageResourceBundle.getString("continentsAndCountriesCheckBoxTooltip"));
			USACheckBox.getTooltip().setText(languageResourceBundle.getString("usaCheckBoxTooltip"));
			greeceCheckBox.getTooltip().setText(languageResourceBundle.getString("greeceCheckBoxTooltip"));
			attractionsCheckBox.getTooltip().setText(languageResourceBundle.getString("attractionsCheckBoxTooltip"));
			
			extendedQuestionCategories.setText(languageResourceBundle.getString("extendedQuestionCategories"));
		}
		else
		{
			titleLabel1.setText(languageResourceBundle.getString("titleForGameModeSelectionLabel"));
			
			numberOfQuestionsForClassicGameLabel.setText(languageResourceBundle.getString("numberOfQuestionsLabel"));
			gameDurationForTimeAttackGameLabel.setText(languageResourceBundle.getString("gameDurationLabel"));
			livesForEndlessGameLabel.setText(languageResourceBundle.getString("livesLabel"));
			countDownForClassicGameLabel.setText(languageResourceBundle.getString("countDownLabel"));
			countDownForEndlessGameLabel.setText(languageResourceBundle.getString("countDownLabel"));
			
			descriptionForClassicGameLabel.setText(getDescriptionForClassicGameMode());
			descriptionForTimeAttackGameLabel.setText(getDescriptionForTimeAttackGameMode());
			descriptionForEndlessGameLabel.setText(getDescriptionForEndlessGameMode());
		}
		
		soundOptionsToolTip.setText(languageResourceBundle.getString("soundOptionsTooltip"));
		minimizeTooltip.setText(languageResourceBundle.getString("minimizeTooltip"));
		exitTooltip.setText(languageResourceBundle.getString("exitTooltip"));
		
		backButton.setText(languageResourceBundle.getString("backButton"));
		backButton.getTooltip().setText(languageResourceBundle.getString("backButtonTooltipInDifficultySelectionScreen"));
		
		updateMasterVolumeText();
		updateMusicVolumeText();
		updateOtherVolumeText();
	}
	
	private void startTextAnimationForTitleLabel2()
	{
		if(titleLabel2.getScaleX() != 0.95)
		{
			double time = (Math.abs(titleLabel2.getScaleX() - 0.95) / 0.1) * TEXT_SCALE_ANIMATION_TIME;
			
			scaleTransitionForTitleLabel2.setDuration(Duration.millis(time));
			scaleTransitionForTitleLabel2.setFromX(titleLabel1.getScaleX());
			scaleTransitionForTitleLabel2.setFromY(titleLabel1.getScaleY());
			scaleTransitionForTitleLabel2.setToX(0.95);
			scaleTransitionForTitleLabel2.setToY(0.95);
			scaleTransitionForTitleLabel2.setCycleCount(1);
			scaleTransitionForTitleLabel2.setAutoReverse(false);
			
			scaleTransitionForTitleLabel2.setOnFinished(e ->
			{
				scaleTransitionForTitleLabel2.setDuration(Duration.millis(TEXT_SCALE_ANIMATION_TIME));
				scaleTransitionForTitleLabel2.setFromX(0.95);
				scaleTransitionForTitleLabel2.setFromY(0.95);
				scaleTransitionForTitleLabel2.setToX(1.03);
				scaleTransitionForTitleLabel2.setToY(1.03);
				scaleTransitionForTitleLabel2.setCycleCount(Animation.INDEFINITE);
				scaleTransitionForTitleLabel2.setAutoReverse(true);
				scaleTransitionForTitleLabel2.setOnFinished(null);
				
				scaleTransitionForTitleLabel2.playFromStart();
			});
			
			scaleTransitionForTitleLabel2.playFromStart();
		}
		else
		{
			scaleTransitionForTitleLabel2.setDuration(Duration.millis(TEXT_SCALE_ANIMATION_TIME));
			scaleTransitionForTitleLabel2.setFromX(0.95);
			scaleTransitionForTitleLabel2.setFromY(0.95);
			scaleTransitionForTitleLabel2.setToX(1.03);
			scaleTransitionForTitleLabel2.setToY(1.03);
			scaleTransitionForTitleLabel2.setCycleCount(Animation.INDEFINITE);
			scaleTransitionForTitleLabel2.setAutoReverse(true);
			scaleTransitionForTitleLabel2.setOnFinished(null);
			
			scaleTransitionForTitleLabel2.playFromStart();
		}
	}
	
	private void startTextAnimationForTitleLabel1()
	{
		if(titleLabel1.getScaleX() != 0.95)
		{
			double time = (Math.abs(titleLabel1.getScaleX() - 0.95) / 0.1) * TEXT_SCALE_ANIMATION_TIME;
			
			scaleTransitionForTitleLabel1.setDuration(Duration.millis(time));
			scaleTransitionForTitleLabel1.setFromX(titleLabel1.getScaleX());
			scaleTransitionForTitleLabel1.setFromY(titleLabel1.getScaleY());
			scaleTransitionForTitleLabel1.setToX(0.95);
			scaleTransitionForTitleLabel1.setToY(0.95);
			scaleTransitionForTitleLabel1.setCycleCount(1);
			scaleTransitionForTitleLabel1.setAutoReverse(false);
			
			scaleTransitionForTitleLabel1.setOnFinished(e ->
			{
				scaleTransitionForTitleLabel1.setDuration(Duration.millis(TEXT_SCALE_ANIMATION_TIME));
				scaleTransitionForTitleLabel1.setFromX(0.95);
				scaleTransitionForTitleLabel1.setFromY(0.95);
				scaleTransitionForTitleLabel1.setToX(1.03);
				scaleTransitionForTitleLabel1.setToY(1.03);
				scaleTransitionForTitleLabel1.setCycleCount(Animation.INDEFINITE);
				scaleTransitionForTitleLabel1.setAutoReverse(true);
				scaleTransitionForTitleLabel1.setOnFinished(null);
				
				scaleTransitionForTitleLabel1.playFromStart();
			});
			
			scaleTransitionForTitleLabel1.playFromStart();
		}
		else
		{
			scaleTransitionForTitleLabel1.setDuration(Duration.millis(TEXT_SCALE_ANIMATION_TIME));
			scaleTransitionForTitleLabel1.setFromX(0.95);
			scaleTransitionForTitleLabel1.setFromY(0.95);
			scaleTransitionForTitleLabel1.setToX(1.03);
			scaleTransitionForTitleLabel1.setToY(1.03);
			scaleTransitionForTitleLabel1.setCycleCount(Animation.INDEFINITE);
			scaleTransitionForTitleLabel1.setAutoReverse(true);
			scaleTransitionForTitleLabel1.setOnFinished(null);
			
			scaleTransitionForTitleLabel1.playFromStart();
		}
	}
	
	public void resumeTextAnimation()
	{
		scaleTransitionForTitleLabel1.play();
		if(screen1) scaleTransitionForTitleLabel2.play();
	}
	
	public void pauseTextAnimation()
	{
		scaleTransitionForTitleLabel1.pause();
		if(screen1) scaleTransitionForTitleLabel2.pause();
	}
	
	private void startGame(byte gameMode)
	{
		FilesIO.writePlayersFile();
		
		if(animationsUsed != NO_ANIMATIONS)
		{
			previousImage.setImage(WORLD_MAP);
			previousImage.setLayoutX(getWorldMapLayoutX() * stage.getWidth());
			previousImage.setLayoutY(getWorldMapLayoutY() * stage.getHeight());
			previousImage.setFitWidth(getWorldMapFitWidth() * stage.getWidth());
			previousImage.setFitHeight(getWorldMapFitHeight() * stage.getHeight());
			
			timeLineToHideAllStuffFromScreen2.setOnFinished(ev -> gameScreen.showScreen(gameMode));
			timeLineToHideAllStuffFromScreen2.playFromStart();
		}
		else gameScreen.showScreen(gameMode);
	}
	
	private void returnToWelcomeScreen()
	{
		FilesIO.writePlayersFile();
		
		if(animationsUsed != NO_ANIMATIONS)
		{
			previousImage.setImage(CHALKBOARD_BACKGROUND_IMAGE);
			previousImage.setLayoutX(0);
			previousImage.setLayoutY(0);
			previousImage.setFitWidth(stage.getWidth());
			previousImage.setFitHeight(stage.getHeight());
			
			timeLineToHideAllStuffFromScreen1.setOnFinished(ev -> welcomeScreen.showScreen(false));
			timeLineToHideAllStuffFromScreen1.playFromStart();
		}
		else welcomeScreen.showScreen(false);
	}
	
	private String getDescriptionForClassicGameMode()
	{
		StringBuilder s = new StringBuilder();
		
		if(getCurrentLanguage() == LANGUAGE_GREEK)
		{
			s.append("Γίνονται " + getNumberOfQuestionsForClassic() + " ερωτήσεις από τις κατηγορίες που θα επιλέξεις.");
			if(isTimerForClassic()) s.append(" Έχεις 10 δευτερόλεπτα για να απαντήσεις σε κάθε ερώτηση.");
			else s.append(" Δεν έχεις κανένα χρονικό περιορισμό.");
			s.append(" Κάθε λάθος απάντηση βαθμολογείται αρνητικά.");
		}
		else
		{
			s.append("You are going to be asked " + getNumberOfQuestionsForClassic() + " questions from the categories you'll choose. ");
			if(isTimerForClassic()) s.append("You'll have 10 seconds to answer each question.");
			else s.append("You won't have any time limitation.");
			s.append(" Each wrong answer is scored negatively.");
		}
		return s.toString();
	}
	
	private String getDescriptionForTimeAttackGameMode()
	{
		StringBuilder s = new StringBuilder();
		
		if(getCurrentLanguage() == LANGUAGE_GREEK)
		{
			s.append("Έχεις " + getDurationInMinutesForTimeAttack());
			
			if(getDurationInMinutesForTimeAttack() == 1) s.append(" λεπτό ");
			else s.append(" λεπτά ");
			
			s.append("για να απαντήσεις σωστά σε όσες περισσότερες ερωτήσεις μπορείς από τις κατηγορίες που θα επιλέξεις. Κάθε λάθος απάντηση βαθμολογείται αρνητικά.");
		}
		else
		{
			s.append("You'll have " + getDurationInMinutesForTimeAttack());
			
			if(getDurationInMinutesForTimeAttack() == 1) s.append(" minute ");
			else s.append(" minutes ");
			
			s.append("to correctly answer as many questions as you can from the categories you'll choose. Each wrong answer is scored negatively.");
		}
		return s.toString();
	}
	
	private String getDescriptionForEndlessGameMode()
	{
		StringBuilder s = new StringBuilder();
		
		if(getCurrentLanguage() == LANGUAGE_GREEK)
		{
			s.append("Πρέπει να απαντήσεις σωστά σε όσες περισσότερες ερωτήσεις μπορείς. Έχεις " + getLivesForEndless());
			
			if(getLivesForEndless() == 1) s.append(" ευκαιρία ");
			else s.append(" ευκαιρίες ");
			
			s.append("για λάθος απάντηση ");
			
			if(isTimerForEndless()) s.append("και 10 δευτερόλεπτα για να απαντήσεις σε κάθε ερώτηση.");
			else s.append("χωρίς περιορισμό στο χρόνο απάντησης κάθε ερώτησης.");
		}
		else
		{
			s.append("You must correctly answer as many questions as you can. You have " + getLivesForEndless());
			
			if(getLivesForEndless() == 1) s.append(" chance ");
			else s.append(" chances ");
			
			s.append("for a wrong answer ");
			
			if(isTimerForEndless()) s.append("and 10 seconds to answer each question.");
			else s.append("without limitation to the answering time of each question.");
		}
		return s.toString();
	}
	
	private class Handler implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			if(((CheckBox) e.getSource()).isSelected()) playCheckBoxSelectedSound();
			else playCheckBoxDeselectedSound();
			
//			----------------------------------------------------------------------------
			if(e.getSource() == extQCCategories[0] || e.getSource() == continentsAndCountriesCheckBox)
			{
				if(e.getSource() == extQCCategories[0])
				{
					if(extQCCategories[0].isSelected())
					{
						continentsAndCountriesCheckBox.setSelected(true);
						continentsAndCountriesCheckBox.setIndeterminate(false);
					}
					else if(extQCCategories[0].isIndeterminate())
					{
						continentsAndCountriesCheckBox.setSelected(false);
						continentsAndCountriesCheckBox.setIndeterminate(true);
					}
					else
					{
						continentsAndCountriesCheckBox.setSelected(false);
						continentsAndCountriesCheckBox.setIndeterminate(false);
					}
				}
				else
				{
					if(continentsAndCountriesCheckBox.isSelected())
					{
						extQCCategories[0].setSelected(true);
						extQCCategories[0].setIndeterminate(false);
					}
					else
					{
						extQCCategories[0].setSelected(false);
						extQCCategories[0].setIndeterminate(false);
					}
				}
				
				if(extQCCategories[0].isSelected()) for(CustomCheckBox extQCCountry : extQCCountries) extQCCountry.setSelected(true);
				else for(CustomCheckBox extQCCountry : extQCCountries) extQCCountry.setSelected(false);
			}
			else if(e.getSource() == extQCCategories[1] || e.getSource() == USACheckBox)
			{
				if(e.getSource() == extQCCategories[1])
				{
					if(extQCCategories[1].isSelected())
					{
						USACheckBox.setSelected(true);
						USACheckBox.setIndeterminate(false);
					}
					else
					{
						USACheckBox.setSelected(false);
						USACheckBox.setIndeterminate(false);
					}
				}
				else
				{
					if(USACheckBox.isSelected())
					{
						extQCCategories[1].setSelected(true);
						extQCCategories[1].setIndeterminate(false);
					}
					else
					{
						extQCCategories[1].setSelected(false);
						extQCCategories[1].setIndeterminate(false);
					}
				}
				
				if(extQCCategories[1].isSelected()) for(CustomCheckBox anExtQCUSA1 : extQCUSA) anExtQCUSA1.setSelected(true);
				else for(CustomCheckBox anExtQCUSA : extQCUSA) anExtQCUSA.setSelected(false);
			}
			else if(e.getSource() == extQCCategories[2] || e.getSource() == greeceCheckBox)
			{
				if(e.getSource() == extQCCategories[2])
				{
					if(extQCCategories[2].isSelected())
					{
						greeceCheckBox.setSelected(true);
						greeceCheckBox.setIndeterminate(false);
					}
					else
					{
						greeceCheckBox.setSelected(false);
						greeceCheckBox.setIndeterminate(false);
					}
				}
				else
				{
					if(greeceCheckBox.isSelected())
					{
						extQCCategories[2].setSelected(true);
						extQCCategories[2].setIndeterminate(false);
					}
					else
					{
						extQCCategories[2].setSelected(false);
						extQCCategories[2].setIndeterminate(false);
					}
				}
				
				if(extQCCategories[2].isSelected()) for(CustomCheckBox anExtQCGreece : extQCGreece) anExtQCGreece.setSelected(true);
				else for(CustomCheckBox anExtQCGreece : extQCGreece) anExtQCGreece.setSelected(false);
			}
			else if(e.getSource() == extQCCategories[3] || e.getSource() == attractionsCheckBox)
			{
				if(e.getSource() == extQCCategories[3])
				{
					if(extQCCategories[3].isSelected())
					{
						attractionsCheckBox.setSelected(true);
						attractionsCheckBox.setIndeterminate(false);
					}
					else
					{
						attractionsCheckBox.setSelected(false);
						attractionsCheckBox.setIndeterminate(false);
					}
				}
				else
				{
					if(attractionsCheckBox.isSelected())
					{
						extQCCategories[3].setSelected(true);
						extQCCategories[3].setIndeterminate(false);
					}
					else
					{
						extQCCategories[3].setSelected(false);
						extQCCategories[3].setIndeterminate(false);
					}
				}
			}
//			----------------------------------------------------------------------------
			updateCheckboxesState(true);
			
			updateNextButtonState();

			for(int i = 0; i < NUM_ALL_CATEGORIES; i++)
			{
				if(i < NUM_CAT_COUNTRIES_ALL)
					setIsCategorySelected(i, extQCCountries[i].isSelected());
				else if(i < NUM_CAT_COUNTRIES_ALL + NUM_CAT_USA_ALL)
					setIsCategorySelected(i, extQCUSA[i - NUM_CAT_COUNTRIES_ALL].isSelected());
				else if(i < NUM_CAT_COUNTRIES_ALL + NUM_CAT_USA_ALL + NUM_CAT_GREECE_ALL)
					setIsCategorySelected(i, extQCGreece[i - (NUM_CAT_COUNTRIES_ALL + NUM_CAT_USA_ALL)].isSelected());
				else setIsCategorySelected(i, extQCCategories[3].isSelected());
			}
		}
		
		private void updateCheckboxesState(boolean checkboxChanged)
		{
			if(getDifficultyLevel() == DIFFICULTY_EASY)
			{
				extQCCountries[2].setSelected(false);
				extQCCountries[2].setDisable(true);
				extQCCountries[7].setSelected(false);
				extQCCountries[7].setDisable(true);
				extQCCountries[9].setSelected(false);
				extQCCountries[9].setDisable(true);
				
//				switch(userLocale.getCountry())
//				{
//					case "GR":
//						extQCCategories[1].setSelected(false);
//						extQCCategories[1].setDisable(true);
//
//						USACheckBox.setSelected(false);
//						USACheckBox.setDisable(true);
//
//						for(CustomCheckBox anExtQCUSA : extQCUSA)
//						{
//							anExtQCUSA.setSelected(false);
//							anExtQCUSA.setDisable(true);
//						}
//
//						extQCCategories[2].setDisable(false);
//						greeceCheckBox.setDisable(false);
//						break;
//					case "US":
//						extQCCategories[1].setDisable(false);
//						USACheckBox.setDisable(false);
//
//						extQCUSA[0].setDisable(false);
//						extQCUSA[1].setDisable(false);
//						extQCUSA[2].setSelected(false);
//						extQCUSA[2].setDisable(true);
//						extQCUSA[3].setSelected(false);
//						extQCUSA[3].setDisable(true);
//						extQCUSA[4].setSelected(false);
//						extQCUSA[4].setDisable(true);
//						extQCUSA[5].setDisable(false);
//						extQCUSA[6].setDisable(false);
//						extQCUSA[7].setDisable(false);
//						extQCUSA[8].setDisable(false);
//						if(!checkboxChanged && extQCCategories[1].isSelected())
//						{
//							extQCUSA[0].setSelected(true);
//							extQCUSA[1].setSelected(true);
//							extQCUSA[5].setSelected(true);
//							extQCUSA[6].setSelected(true);
//							extQCUSA[7].setSelected(true);
//							extQCUSA[8].setSelected(true);
//						}
//
//						extQCCategories[2].setSelected(false);
//						extQCCategories[2].setDisable(true);
//
//						greeceCheckBox.setSelected(false);
//						greeceCheckBox.setDisable(true);
//
//						for(CustomCheckBox anExtQCGreece : extQCGreece)
//						{
//							anExtQCGreece.setSelected(false);
//							anExtQCGreece.setDisable(true);
//						}
//						break;
//					default:
//						extQCCategories[1].setSelected(false);
//						extQCCategories[1].setDisable(true);
//
//						USACheckBox.setSelected(false);
//						USACheckBox.setDisable(false);
//
//						for(CustomCheckBox anExtQCUSA : extQCUSA)
//						{
//							anExtQCUSA.setSelected(false);
//							anExtQCUSA.setDisable(true);
//						}
//
//						extQCCategories[2].setSelected(false);
//						extQCCategories[2].setDisable(true);
//
//						greeceCheckBox.setSelected(false);
//						greeceCheckBox.setDisable(true);
//
//						for(CustomCheckBox anExtQCGreece : extQCGreece)
//						{
//							anExtQCGreece.setSelected(false);
//							anExtQCGreece.setDisable(true);
//						}
//						break;
//				}
			}
			else if(getDifficultyLevel() == DIFFICULTY_DIFFICULT)
			{
				extQCCountries[2].setDisable(false);
				extQCCountries[7].setDisable(false);
				extQCCountries[9].setDisable(false);
				if(!checkboxChanged && extQCCategories[0].isSelected())
				{
					extQCCountries[2].setSelected(true);
					extQCCountries[7].setSelected(true);
					extQCCountries[9].setSelected(true);
				}
				
//				switch(userLocale.getCountry())
//				{
//					case "GR":
//						extQCCategories[1].setDisable(false);
//						USACheckBox.setDisable(false);
//
//						for(CustomCheckBox anExtQCUSA : extQCUSA) anExtQCUSA.setDisable(false);
//
//						extQCCategories[2].setDisable(false);
//						greeceCheckBox.setDisable(false);
//						break;
//					case "US":
//						extQCCategories[1].setDisable(false);
//						USACheckBox.setDisable(false);
//
//						extQCUSA[0].setSelected(false);
//						extQCUSA[0].setDisable(true);
//						extQCUSA[1].setSelected(false);
//						extQCUSA[1].setDisable(true);
//						extQCUSA[2].setDisable(false);
//						extQCUSA[3].setDisable(false);
//						extQCUSA[4].setDisable(false);
//						extQCUSA[5].setSelected(false);
//						extQCUSA[5].setDisable(true);
//						extQCUSA[6].setSelected(false);
//						extQCUSA[6].setDisable(true);
//						extQCUSA[7].setSelected(false);
//						extQCUSA[7].setDisable(true);
//						extQCUSA[8].setSelected(false);
//						extQCUSA[8].setDisable(true);
//						if(!checkboxChanged && extQCCategories[1].isSelected())
//						{
//							extQCUSA[2].setSelected(true);
//							extQCUSA[3].setSelected(true);
//							extQCUSA[4].setSelected(true);
//						}
//
//						extQCCategories[2].setDisable(false);
//						greeceCheckBox.setDisable(false);
//
//						for(CustomCheckBox anExtQCGreeceGlobal : extQCGreece) anExtQCGreeceGlobal.setDisable(false);
//						break;
//					default:
//						extQCCategories[1].setSelected(false);
//						extQCCategories[1].setDisable(true);
//
//						USACheckBox.setSelected(false);
//						USACheckBox.setDisable(false);
//
//						for(CustomCheckBox anExtQCUSA : extQCUSA) anExtQCUSA.setDisable(false);
//
//						extQCCategories[2].setDisable(false);
//						greeceCheckBox.setDisable(false);
//
//						for(CustomCheckBox anExtQCGreeceGlobal : extQCGreece) anExtQCGreeceGlobal.setDisable(false);
//						break;
//				}
			}
			
			int cSel = 0, cDis = 0;
			for (CustomCheckBox extQCCountry : extQCCountries)
			{
				if (extQCCountry.isSelected()) cSel++;
				if (extQCCountry.isDisabled()) cDis++;
			}
			
			if(cSel + cDis == extQCCountries.length)
			{
				extQCCategories[0].setSelected(true);
				extQCCategories[0].setIndeterminate(false);
				
				continentsAndCountriesCheckBox.setSelected(true);
				continentsAndCountriesCheckBox.setIndeterminate(false);
			}
			else if(cSel > 0)
			{
				extQCCategories[0].setSelected(false);
				extQCCategories[0].setIndeterminate(true);
				
				continentsAndCountriesCheckBox.setSelected(false);
				continentsAndCountriesCheckBox.setIndeterminate(true);
			}
			else
			{
				extQCCategories[0].setSelected(false);
				extQCCategories[0].setIndeterminate(false);
				
				continentsAndCountriesCheckBox.setSelected(false);
				continentsAndCountriesCheckBox.setIndeterminate(false);
			}
			//----------------------------------------------------------------------------
			cSel = 0;
			cDis = 0;
			for (CustomCheckBox anExtQCUSA : extQCUSA)
			{
				if (anExtQCUSA.isSelected()) cSel++;
				if (anExtQCUSA.isDisabled()) cDis++;
			}
			
			if(cSel + cDis == extQCUSA.length && cDis != extQCUSA.length)
			{
				extQCCategories[1].setSelected(true);
				extQCCategories[1].setIndeterminate(false);
				
				USACheckBox.setSelected(true);
				USACheckBox.setIndeterminate(false);
			}
			else if(cSel > 0)
			{
				extQCCategories[1].setSelected(false);
				extQCCategories[1].setIndeterminate(true);
				
				USACheckBox.setSelected(false);
				USACheckBox.setIndeterminate(true);
			}
			else
			{
				extQCCategories[1].setSelected(false);
				extQCCategories[1].setIndeterminate(false);
				
				USACheckBox.setSelected(false);
				USACheckBox.setIndeterminate(false);
			}
			//----------------------------------------------------------------------------
			cSel = 0;
			cDis = 0;
			for (CustomCheckBox anExtQCGreece: extQCGreece)
			{
				if (anExtQCGreece.isSelected()) cSel++;
				if (anExtQCGreece.isDisabled()) cDis++;
			}
			
			if(cSel + cDis == extQCGreece.length && cDis != extQCGreece.length)
			{
				extQCCategories[2].setSelected(true);
				extQCCategories[2].setIndeterminate(false);
				
				greeceCheckBox.setSelected(true);
				greeceCheckBox.setIndeterminate(false);
			}
			else if(cSel > 0)
			{
				extQCCategories[2].setSelected(false);
				extQCCategories[2].setIndeterminate(true);
				
				greeceCheckBox.setSelected(false);
				greeceCheckBox.setIndeterminate(true);
			}
			else
			{
				extQCCategories[2].setSelected(false);
				extQCCategories[2].setIndeterminate(false);
				
				greeceCheckBox.setSelected(false);
				greeceCheckBox.setIndeterminate(false);
			}
		}
	}
}
