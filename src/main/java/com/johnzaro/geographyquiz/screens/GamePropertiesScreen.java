package com.johnzaro.geographyquiz.screens;

import com.johnzaro.geographyquiz.core.*;
import com.johnzaro.geographyquiz.core.ImageStuff.Images;
import com.johnzaro.geographyquiz.core.customNodes.*;
import javafx.animation.*;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;

/**
 * Created by John on 23/8/2016.
 */

public class GamePropertiesScreen extends CoreScreenWithMovingBackground
{
	private CustomCheckBox[] extQCCategories, extQCCountries, extQCUSA, extQCGreece;
	private CustomCheckBox continentsAndCountriesCheckBox, USACheckBox, greeceCheckBox, attractionsCheckBox;
	private CustomImageView previousImage, titleImage1, titleImage2,
			exitExtendedCategoryQuestions;
	private CustomButton classicGameButton, timeAttackGameButton, endlessGameButton, backButton, nextButton, extendedQuestionCategoriesButton;
	private SwitchButton countDownToggleForClassicGameLabel, countDownToggleForEndlessGameLabel;
	private CustomLabel titleLabel1, titleLabel2, numberOfQuestionsForClassicGameLabel, countDownForClassicGameLabel, descriptionForClassicGameLabel,
			gameDurationForTimeAttackGameLabel, descriptionForTimeAttackGameLabel, livesForEndlessGameLabel, countDownForEndlessGameLabel, descriptionForEndlessGameLabel;
	private CustomScrollPane scrollPaneForExtendedCategoryQuestionsGridPane;
	private CustomRadioButton easyLevelRadioButton, difficultLevelRadioButton, radioButton10QuestionsForClassicGameLabel,
			radioButton20QuestionsForClassicGameLabel, radioButton50QuestionsForClassicGameLabel, radioButton100QuestionsForClassicGameLabel,
			radioButton1MinuteDurationForTimeAttackGameLabel, radioButton2MinutesDurationForTimeAttackGameLabel, radioButton5MinutesDurationForTimeAttackGameLabel,
			radioButton10MinutesDurationForTimeAttackGameLabel, radioButton1LifeForEndlessGameLabel, radioButton3LivesForEndlessGameLabel,
			radioButton5LivesForEndlessGameLabel;
	private ToggleGroup toggleGroupForDifficultyLevel, toggleGroupForClassicGame, toggleGroupForTimeAttackGame, toggleGroupForEndlessGame;
	private CustomHBox hBoxForGameModes, hBoxForNumberOfQuestionsForClassicGame, hBoxForCountDownForClassicGame,
			hBoxForGameDurationForTimeAttackGame, hBoxEmpty, hBoxForLivesForEndlessGame, hBoxForCountDownForEndlessGame;
	private CustomVBox vBoxForDifficultyLevel, vBoxForClassicGame, vBoxForTimeAttackGame, vBoxForEndlessGame;
	private CustomGridPane gridPaneForQuestionsCategories, gridPaneForExtendedQuestionCategories;

	private TranslateTransition translateTransitionForTitleImage1, translateTransitionForTitleLabel1;
	private FadeTransition fadeTransitionForMovingEarthImage;
	private ScaleTransition scaleTransitionForTitleLabel1, scaleTransitionForTitleLabel2, scaleTransitionForBackButton,
			scaleTransitionForNextButton, scaleTransitionForVBoxForDifficultyLevel, scaleTransitionForGridPaneForQuestionsCategories, scaleTransitionForExtendedQuestionCategoriesLabel,
			scaleTransitionForScrollPaneForExtendedQuestionCategories, scaleTransitionForExitExtendedCategoryQuestions,
			scaleTransitionForVBoxForClassicGame, scaleTransitionForVBoxForTimeAttackGame, scaleTransitionForVBoxForEndlessGame;
	private ParallelTransition parallelTransitionForTitleImage2;
	private Timeline timelineToHideAllStuffFromScreen1,
			timelineToHideAllStuffFromScreen2, timelineFromScreen1ToScreen2, timelineFromScreen2ToScreen1;

	private boolean screen1 = true, isExtendedQuestionCategoriesOpen = false, pressedOutsideExtendedQuestionCategories = false;

	private Handler handler = new Handler();

	private boolean fromWelcomeScreen;

	protected void recalculateUI(double width, double height)
	{
		super.recalculateUI(width, height);

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
		
		titleImage1.setFitWidth(0.5156 * width);
		titleImage1.setLayoutX(width / 2.0 - titleImage1.getFitWidth() / 2.0);
		
		titleLabel1.setPrefSize(0.5052 * width, 0.1111 * height);
		
		woodPanelFor1IconImage.setLayoutX(ratioProperties.getGameProperties().getWoodPanelFor1IconImageLayoutX() * width - woodPanelFor1IconImage.getFitWidth() / 2.0);
		woodPanelFor1IconImage.setLayoutY(ratioProperties.getGameProperties().getWoodPanelFor1IconImageLayoutY() * height);
		
		vBoxForSound.setLayoutX(0.7031 * width);
		vBoxForSound.setLayoutY(ratioProperties.getGameProperties().getvBoxForSoundLayoutY() * height);
		if(vBoxForSound.getTranslateY() != 0) vBoxForSound.setTranslateY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
		
		soundButton.setLayoutY(ratioProperties.getGameProperties().getSoundIconLayoutY() * height - soundButton.getFitWidth() / 2.0);

		backButton.setFitWidth(0.0703 * width);
		backButton.setLayoutX(0.0420 * width);
		backButton.setLayoutY(ratioProperties.getGameProperties().getBackButtonLayoutY() * height);
		backButton.setFont(font25P);
		backButton.getTooltip().setFont(font25P);

		if(screen1)
		{
			titleImage1.setLayoutY(ratioProperties.getGameProperties().getTitleImage1LayoutY_1() * height);
			titleLabel1.setLayoutX(ratioProperties.getGameProperties().getTitleLabel1LayoutX_1() * width);
			titleLabel1.setLayoutY(ratioProperties.getGameProperties().getTitleLabel1LayoutY_1() * height);
			
			titleImage2.setFitWidth(0.5156 * width);
			titleImage2.setLayoutX(width / 2.0 - titleImage2.getFitWidth() / 2.0);
			titleImage2.setLayoutY(ratioProperties.getGameProperties().getTitleImage2LayoutY() * height);
			
			titleLabel2.setPrefSize(0.5052 * width, 0.1111 * height);
			titleLabel2.setLayoutX(ratioProperties.getGameProperties().getTitleLabel2LayoutX() * width);
			titleLabel2.setLayoutY(ratioProperties.getGameProperties().getTitleLabel2LayoutY() * height);
			
			if (getCurrentLanguage() == LANGUAGE.GREEK)
			{
				if(isExtendedQuestionCategoriesOpen) titleLabel1.setFont(font55B);
				else titleLabel1.setFont(font65B);
				titleLabel2.setFont(font80B);
			}
			else
			{
				titleLabel1.setFont(font70B);
				titleLabel2.setFont(font85B);
			}

			vBoxForDifficultyLevel.setPrefSize(ratioProperties.getGameProperties().getvBoxForDifficultyLevelWidth() * width, ratioProperties.getGameProperties().getvBoxForDifficultyLevelHeight() * height);
			vBoxForDifficultyLevel.setLayoutX(width / 2.0 - vBoxForDifficultyLevel.getPrefWidth() / 2.0);
			vBoxForDifficultyLevel.setLayoutY(ratioProperties.getGameProperties().getvBoxForDifficultyLevelLayoutY() * height);
			vBoxForDifficultyLevel.setSpacing(0.0130 * width);
			vBoxForDifficultyLevel.setStyle(cssBackgroundAndBorderMedium + "-fx-padding:" + 0.0156 * width + "px " + 0.0525 * width + "px;");
			
			easyLevelRadioButton.setFont(font35P);
			difficultLevelRadioButton.setFont(font35P);

			gridPaneForQuestionsCategories.setPrefSize(ratioProperties.getGameProperties().getGridPaneForQuestionsCategoriesWidth() * width,
					ratioProperties.getGameProperties().getGridPaneForQuestionsCategoriesHeight() * height);
			gridPaneForQuestionsCategories.setLayoutX(width / 2.0 - gridPaneForQuestionsCategories.getPrefWidth() / 2.0);
			gridPaneForQuestionsCategories.setLayoutY(ratioProperties.getGameProperties().getGridPaneForQuestionsCategoriesLayoutY() * height);
			gridPaneForQuestionsCategories.setVgap(0.0740 * height);
			gridPaneForQuestionsCategories.setStyle(cssBackgroundAndBorderMedium + cssPadding30);
			
			continentsAndCountriesCheckBox.getTooltip().setFont(font25P);
			USACheckBox.getTooltip().setFont(font25P);
			greeceCheckBox.getTooltip().setFont(font25P);
			attractionsCheckBox.getTooltip().setFont(font25P);
			
			if(getCurrentLanguage() == LANGUAGE.GREEK)
			{
				gridPaneForQuestionsCategories.setHgap(0.0390 * width);

				continentsAndCountriesCheckBox.setFont(font35P);
				USACheckBox.setFont(font35P);
				greeceCheckBox.setFont(font35P);
				attractionsCheckBox.setFont(font35P);
			}
			else
			{
				gridPaneForQuestionsCategories.setHgap(0.0208 * width);

				continentsAndCountriesCheckBox.setFont(font30P);
				USACheckBox.setFont(font30P);
				greeceCheckBox.setFont(font30P);
				attractionsCheckBox.setFont(font30P);
			}
			
			extendedQuestionCategoriesButton.setPrefSize(ratioProperties.getGameProperties().getExtendedQuestionCategoriesWidth() * width, ratioProperties.getGameProperties().getExtendedQuestionCategoriesHeight() * height);
			extendedQuestionCategoriesButton.setLayoutX(width / 2.0 - extendedQuestionCategoriesButton.getPrefWidth() / 2.0);
			extendedQuestionCategoriesButton.setLayoutY(gridPaneForQuestionsCategories.getLayoutY() + gridPaneForQuestionsCategories.getPrefHeight() + 0.0093 * height);
			extendedQuestionCategoriesButton.setFont(font30P);
			
			nextButton.setFitWidth(backButton.getFitWidth());
			nextButton.setLayoutX(width - 0.1110 * width);
			nextButton.setLayoutY(backButton.getLayoutY());
			nextButton.setFont(font25P);
			nextButton.getTooltip().setFont(font25P);

			scrollPaneForExtendedCategoryQuestionsGridPane.setPrefSize(0.8958 * width, ratioProperties.getGameProperties().getScrollPaneForExtendedCategoryQuestionsGridPaneHeight() * height);
			scrollPaneForExtendedCategoryQuestionsGridPane.setLayoutX(width / 2.0 - scrollPaneForExtendedCategoryQuestionsGridPane.getPrefWidth() / 2.0);
			scrollPaneForExtendedCategoryQuestionsGridPane.setLayoutY(vBoxForDifficultyLevel.getLayoutY());
			scrollPaneForExtendedCategoryQuestionsGridPane.setStyle(cssBackgroundAndBorderMedium + cssPadding30);

			gridPaneForExtendedQuestionCategories.setHgap(0.0026 * width);
			gridPaneForExtendedQuestionCategories.setVgap(0.0111 * height);

			exitExtendedCategoryQuestions.setFitWidth(0.0313 * width);
			exitExtendedCategoryQuestions.setLayoutX(scrollPaneForExtendedCategoryQuestionsGridPane.getLayoutX() + scrollPaneForExtendedCategoryQuestionsGridPane.getPrefWidth()
					- exitExtendedCategoryQuestions.getFitWidth() - 0.0156 * width);
			exitExtendedCategoryQuestions.setLayoutY(scrollPaneForExtendedCategoryQuestionsGridPane.getLayoutY() + scrollPaneForExtendedCategoryQuestionsGridPane.getPrefHeight()
					- exitExtendedCategoryQuestions.getFitWidth() - 0.0156 * width);

			Insets pos1 = new Insets(0, 0, 0, 0.0026 * width);
			Insets pos2 = new Insets(0, 0, 0, 0.0198 * width);
			for(int i = 0; i < extQCCountries.length; i++)
			{
				if(i < extQCCategories.length)
				{
					extQCCategories[i].setFont(font25P);
					GridPane.setMargin(extQCCategories[i], pos1);
				}

				extQCCountries[i].setFont(font25P);
				GridPane.setMargin(extQCCountries[i], pos2);

				if(i < extQCUSA.length)
				{
					extQCUSA[i].setFont(font25P);
					GridPane.setMargin(extQCUSA[i], pos2);

				}
				if(i < extQCGreece.length)
				{
					extQCGreece[i].setFont(font25P);
					GridPane.setMargin(extQCGreece[i], pos2);
				}
			}
		}
		else
		{
			double buttonWidth = 0.1927 * width;
			double buttonHeight = ratioProperties.getGameProperties().getButtonHeight() * height;
			double vBoxSpacing = 0.0074 * height;
			double widthOfNodesInVBox = 0.2266 * width;
			double heightOfNodesInVBox = 0.0509 * height;
			double heightOfDescriptions = ratioProperties.getGameProperties().getHeightOfDescriptions() * height;

			titleImage1.setLayoutY(ratioProperties.getGameProperties().getTitleImage1LayoutY_2() * height);
			if(titleImage1.getTranslateX() != 0)
			{
				titleImage1.setTranslateX(-0.1484 * width);
				titleLabel1.setTranslateX(-0.1484 * width);
			}

			titleLabel1.setLayoutX(ratioProperties.getGameProperties().getTitleLabel1LayoutX_2() * width);
			titleLabel1.setLayoutY(ratioProperties.getGameProperties().getTitleLabel1LayoutY_2() * height);
			if(getCurrentLanguage() == LANGUAGE.GREEK) titleLabel1.setFont(font85B);
			else titleLabel1.setFont(font95B);

			hBoxForGameModes.setPrefSize(ratioProperties.getGameProperties().gethBoxForGameModesWidth() * width, ratioProperties.getGameProperties().gethBoxForGameModesHeight() * height);
			hBoxForGameModes.setLayoutX(width / 2.0 - hBoxForGameModes.getPrefWidth() / 2.0);
			hBoxForGameModes.setLayoutY(ratioProperties.getGameProperties().gethBoxForGameModesLayoutY() * height);
			hBoxForGameModes.setSpacing(0.0078 * width);
			
			vBoxForClassicGame.setPrefWidth((hBoxForGameModes.getPrefWidth() - hBoxForGameModes.getSpacing() * 2.0) / 3.0);
			vBoxForClassicGame.setSpacing(vBoxSpacing);
			vBoxForClassicGame.setStyle(cssBackgroundAndBorderBig);
			classicGameButton.setPrefSize(buttonWidth, buttonHeight);
			classicGameButton.setFont(font45B);
			hBoxForNumberOfQuestionsForClassicGame.setPrefSize(widthOfNodesInVBox, heightOfNodesInVBox);
			hBoxForNumberOfQuestionsForClassicGame.setSpacing(0.0052 * width);
			numberOfQuestionsForClassicGameLabel.setPrefSize(widthOfNodesInVBox, heightOfNodesInVBox);
			numberOfQuestionsForClassicGameLabel.setFont(font30P);
			radioButton10QuestionsForClassicGameLabel.setFont(font30P);
			radioButton20QuestionsForClassicGameLabel.setFont(font30P);
			radioButton50QuestionsForClassicGameLabel.setFont(font30P);
			radioButton100QuestionsForClassicGameLabel.setFont(font30P);
			hBoxForCountDownForClassicGame.setPrefSize(widthOfNodesInVBox, heightOfNodesInVBox);
			hBoxForCountDownForClassicGame.setSpacing(0.0104 * width);
			countDownForClassicGameLabel.setFont(font30P);
			countDownToggleForClassicGameLabel.setPrefSize(0.0521 * width, 0.0370 * height);
			countDownToggleForClassicGameLabel.recalculateUI(0.0208 * width, 0.0208 * width, font25P);
			descriptionForClassicGameLabel.setPrefSize(widthOfNodesInVBox, heightOfDescriptions);
			descriptionForClassicGameLabel.setFont(font30P);
			
			vBoxForTimeAttackGame.setPrefWidth((hBoxForGameModes.getPrefWidth() - hBoxForGameModes.getSpacing() * 2.0) / 3.0);
			vBoxForTimeAttackGame.setSpacing(vBoxSpacing);
			vBoxForTimeAttackGame.setStyle(cssBackgroundAndBorderBig);
			timeAttackGameButton.setPrefSize(buttonWidth, buttonHeight);
			timeAttackGameButton.setFont(font45B);
			hBoxForGameDurationForTimeAttackGame.setPrefSize(widthOfNodesInVBox, heightOfNodesInVBox);
			hBoxForGameDurationForTimeAttackGame.setSpacing(0.0052 * width);
			gameDurationForTimeAttackGameLabel.setPrefSize(widthOfNodesInVBox, heightOfNodesInVBox);
			gameDurationForTimeAttackGameLabel.setFont(font30P);
			radioButton1MinuteDurationForTimeAttackGameLabel.setFont(font30P);
			radioButton2MinutesDurationForTimeAttackGameLabel.setFont(font30P);
			radioButton5MinutesDurationForTimeAttackGameLabel.setFont(font30P);
			radioButton10MinutesDurationForTimeAttackGameLabel.setFont(font30P);
			hBoxEmpty.setPrefSize(widthOfNodesInVBox, heightOfNodesInVBox);
			descriptionForTimeAttackGameLabel.setPrefSize(widthOfNodesInVBox, heightOfDescriptions);
			descriptionForTimeAttackGameLabel.setFont(font30P);
			
			vBoxForEndlessGame.setPrefWidth((hBoxForGameModes.getPrefWidth() - hBoxForGameModes.getSpacing() * 2.0) / 3.0);
			vBoxForEndlessGame.setSpacing(vBoxSpacing);
			vBoxForEndlessGame.setStyle(cssBackgroundAndBorderBig);
			endlessGameButton.setPrefSize(buttonWidth, buttonHeight);
			endlessGameButton.setFont(font45B);
			hBoxForLivesForEndlessGame.setPrefSize(widthOfNodesInVBox, heightOfNodesInVBox);
			hBoxForLivesForEndlessGame.setSpacing(0.0052 * width);
			livesForEndlessGameLabel.setPrefSize(widthOfNodesInVBox, heightOfNodesInVBox);
			livesForEndlessGameLabel.setFont(font30P);
			radioButton1LifeForEndlessGameLabel.setFont(font30P);
			radioButton3LivesForEndlessGameLabel.setFont(font30P);
			radioButton5LivesForEndlessGameLabel.setFont(font30P);
			hBoxForCountDownForEndlessGame.setPrefSize(widthOfNodesInVBox, heightOfNodesInVBox);
			hBoxForCountDownForEndlessGame.setSpacing(0.0104 * width);
			countDownForEndlessGameLabel.setFont(font30P);
			countDownToggleForEndlessGameLabel.setPrefSize(0.0521 * width, 0.0370 * height);
			countDownToggleForEndlessGameLabel.recalculateUI(0.0208 * width, 0.0208 * width, font25P);
			descriptionForEndlessGameLabel.setPrefSize(widthOfNodesInVBox, heightOfDescriptions);
			descriptionForEndlessGameLabel.setFont(font30P);
		}
	}

	protected void recalculateBackground(double width, double height)
	{
		super.recalculateBackground(width, height);

		if(fromWelcomeScreen)
		{
			previousImage.setLayoutX(0);
			previousImage.setLayoutY(0);
			previousImage.setFitWidth(width);
			previousImage.setFitHeight(height);
		}
		else
		{
			previousImage.setLayoutX(getImageStuff().getWorldMapLayoutX() * width);
			previousImage.setLayoutY(getImageStuff().getWorldMapLayoutY() * height);
			previousImage.setFitWidth(getImageStuff().getWorldMapFitWidth() * width);
			previousImage.setFitHeight(getImageStuff().getWorldMapFitHeight() * height);
		}
	}

	public GamePropertiesScreen()
	{
		previousImage = new CustomImageView(false, false, false, true, CacheHint.SPEED);

		movingEarthImage = new CustomImageView(false, true, false, false, null);

		backButton = new CustomButton(null, images.get(Images.BACK_ARROW), ContentDisplay.TOP, null, CustomButton.COLOR, true, true, true, true, CacheHint.SCALE);

//		Difficulty and categories selection screen
		nextButton = new CustomButton(null, images.get(Images.BACK_ARROW), ContentDisplay.TOP, null, CustomButton.COLOR, true, true, true, true, CacheHint.SCALE);
		nextButton.getGraphic().setRotate(180);

		titleImage1 = new CustomImageView(true, true, false, true, CacheHint.SPEED);
		titleImage2 = new CustomImageView(true, true, false, true, CacheHint.SCALE);

		titleLabel1 = new CustomLabel(Pos.CENTER, TextAlignment.CENTER, DARK_BROWN, null, null, null, false, false, false, false, true, CacheHint.SCALE);
		titleLabel2 = new CustomLabel(Pos.CENTER, TextAlignment.CENTER, DARK_BROWN, null, null, null, false, false, false, false, true, CacheHint.SCALE);

		easyLevelRadioButton = new CustomRadioButton(null, true);
		difficultLevelRadioButton = new CustomRadioButton(null, true);

		toggleGroupForDifficultyLevel = new ToggleGroup();
		toggleGroupForDifficultyLevel.getToggles().addAll(easyLevelRadioButton, difficultLevelRadioButton);

		vBoxForDifficultyLevel = new CustomVBox(false, Pos.CENTER_LEFT, "black-bordered-background-light", true, CacheHint.SCALE);
		vBoxForDifficultyLevel.getChildren().addAll(easyLevelRadioButton, difficultLevelRadioButton);

		continentsAndCountriesCheckBox = new CustomCheckBox();
		continentsAndCountriesCheckBox.setCursor(Cursor.HAND);
		continentsAndCountriesCheckBox.setTextFill(WHITE);
		continentsAndCountriesCheckBox.setTooltip(new CustomTooltip());

		USACheckBox = new CustomCheckBox();
		USACheckBox.setTooltip(new CustomTooltip());

		greeceCheckBox = new CustomCheckBox();
		greeceCheckBox.setTooltip(new CustomTooltip());

		attractionsCheckBox = new CustomCheckBox();
		attractionsCheckBox.setTooltip(new CustomTooltip());

		gridPaneForQuestionsCategories = new CustomGridPane(Pos.CENTER, "black-bordered-background-light", true, CacheHint.SCALE);
		gridPaneForQuestionsCategories.add(continentsAndCountriesCheckBox, 0, 0);
		gridPaneForQuestionsCategories.add(USACheckBox, 1, 0);
		gridPaneForQuestionsCategories.add(greeceCheckBox, 0, 1);
		gridPaneForQuestionsCategories.add(attractionsCheckBox, 1, 1);

		extendedQuestionCategoriesButton = new CustomButton(null, null, null, null, BLACK, true, true, false, true, CacheHint.SCALE);

		gridPaneForExtendedQuestionCategories = new CustomGridPane(Pos.TOP_CENTER, null, false, null);

		scrollPaneForExtendedCategoryQuestionsGridPane =
				new CustomScrollPane(true, false, true, Cursor.MOVE, gridPaneForExtendedQuestionCategories, "black-bordered-background-dark", true, CacheHint.SCALE);

		exitExtendedCategoryQuestions = new CustomImageView(true, true, false, false, null);
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

		classicGameButton = new CustomButton("Classic", null, null, null, null, false, true, false, true, CacheHint.SCALE);
		timeAttackGameButton = new CustomButton("Time attack", null, null, null, null, false, true, false, true, CacheHint.SCALE);
		endlessGameButton = new CustomButton("Endless", null, null, null, null, false, true, false, true, CacheHint.SCALE);

		numberOfQuestionsForClassicGameLabel = new CustomLabel(Pos.CENTER, null, WHITE, null, null, null, false, true, false, false, false, null);
		gameDurationForTimeAttackGameLabel = new CustomLabel(Pos.CENTER, null, WHITE, null, null, null, false, true, false, false, false, null);
		livesForEndlessGameLabel = new CustomLabel(Pos.CENTER, null, WHITE, null, null, null, false, true, false, false, false, null);
		countDownForClassicGameLabel = new CustomLabel(Pos.CENTER, null, WHITE, null, null, null, false, true, false, false, false, null);
		countDownForEndlessGameLabel = new CustomLabel(Pos.CENTER, null, WHITE, null, null, null, false, true, false, false, false, null);
		descriptionForClassicGameLabel = new CustomLabel(Pos.TOP_CENTER, null, WHITE, null, null, null, false, false, true, false, false, null);
		descriptionForTimeAttackGameLabel = new CustomLabel(Pos.TOP_CENTER, null, WHITE, null, null, null, false, false, true, false, false, null);
		descriptionForEndlessGameLabel = new CustomLabel(Pos.TOP_CENTER, null, WHITE, null, null, null, false, false, true, false, false, null);

		radioButton10QuestionsForClassicGameLabel = new CustomRadioButton("10", true);
		radioButton20QuestionsForClassicGameLabel = new CustomRadioButton("20", true);
		radioButton50QuestionsForClassicGameLabel = new CustomRadioButton("50", true);
		radioButton100QuestionsForClassicGameLabel = new CustomRadioButton("100", true);

		toggleGroupForClassicGame = new ToggleGroup();
		toggleGroupForClassicGame.getToggles().addAll(radioButton10QuestionsForClassicGameLabel,
				radioButton20QuestionsForClassicGameLabel,
				radioButton50QuestionsForClassicGameLabel,
				radioButton100QuestionsForClassicGameLabel);

		countDownToggleForClassicGameLabel = new SwitchButton();

		hBoxForNumberOfQuestionsForClassicGame = new CustomHBox(true, Pos.CENTER, false, null);
		hBoxForNumberOfQuestionsForClassicGame.getChildren().addAll(radioButton10QuestionsForClassicGameLabel,
				radioButton20QuestionsForClassicGameLabel,
				radioButton50QuestionsForClassicGameLabel,
				radioButton100QuestionsForClassicGameLabel);

		hBoxForCountDownForClassicGame = new CustomHBox(false, Pos.CENTER, false, null);
		hBoxForCountDownForClassicGame.getChildren().addAll(countDownForClassicGameLabel, countDownToggleForClassicGameLabel);

		radioButton1MinuteDurationForTimeAttackGameLabel = new CustomRadioButton("1", true);
		radioButton2MinutesDurationForTimeAttackGameLabel = new CustomRadioButton("2", true);
		radioButton5MinutesDurationForTimeAttackGameLabel = new CustomRadioButton("5", true);
		radioButton10MinutesDurationForTimeAttackGameLabel = new CustomRadioButton("10", true);

		toggleGroupForTimeAttackGame = new ToggleGroup();
		toggleGroupForTimeAttackGame.getToggles().addAll(radioButton1MinuteDurationForTimeAttackGameLabel,
				radioButton2MinutesDurationForTimeAttackGameLabel,
				radioButton5MinutesDurationForTimeAttackGameLabel,
				radioButton10MinutesDurationForTimeAttackGameLabel);

		hBoxForGameDurationForTimeAttackGame = new CustomHBox(true, Pos.CENTER, false, null);
		hBoxForGameDurationForTimeAttackGame.getChildren().addAll(radioButton1MinuteDurationForTimeAttackGameLabel,
				radioButton2MinutesDurationForTimeAttackGameLabel,
				radioButton5MinutesDurationForTimeAttackGameLabel,
				radioButton10MinutesDurationForTimeAttackGameLabel);

		hBoxEmpty = new CustomHBox(false, null, false, null);

		radioButton1LifeForEndlessGameLabel = new CustomRadioButton("1", true);
		radioButton3LivesForEndlessGameLabel = new CustomRadioButton("3", true);
		radioButton5LivesForEndlessGameLabel = new CustomRadioButton("5", true);

		toggleGroupForEndlessGame = new ToggleGroup();
		toggleGroupForEndlessGame.getToggles().addAll(radioButton1LifeForEndlessGameLabel,
				radioButton3LivesForEndlessGameLabel,
				radioButton5LivesForEndlessGameLabel);

		countDownToggleForEndlessGameLabel = new SwitchButton();

		hBoxForLivesForEndlessGame = new CustomHBox(true, Pos.CENTER, false, null);
		hBoxForLivesForEndlessGame.getChildren().addAll(radioButton1LifeForEndlessGameLabel,
				radioButton3LivesForEndlessGameLabel,
				radioButton5LivesForEndlessGameLabel);

		hBoxForCountDownForEndlessGame = new CustomHBox(false, Pos.CENTER, false, null);
		hBoxForCountDownForEndlessGame.getChildren().addAll(countDownForEndlessGameLabel, countDownToggleForEndlessGameLabel);

		vBoxForClassicGame = new CustomVBox(false, Pos.CENTER, "black-bordered-background-light", true, CacheHint.SCALE);
		vBoxForClassicGame.getChildren().addAll(classicGameButton, numberOfQuestionsForClassicGameLabel, hBoxForNumberOfQuestionsForClassicGame,
				hBoxForCountDownForClassicGame, descriptionForClassicGameLabel);

		vBoxForTimeAttackGame = new CustomVBox(false, Pos.CENTER, "black-bordered-background-light", true, CacheHint.SCALE);
		vBoxForTimeAttackGame.getChildren().addAll(timeAttackGameButton, gameDurationForTimeAttackGameLabel,
				hBoxForGameDurationForTimeAttackGame, hBoxEmpty, descriptionForTimeAttackGameLabel);

		vBoxForEndlessGame = new CustomVBox(false, Pos.CENTER, "black-bordered-background-light", true, CacheHint.SCALE);
		vBoxForEndlessGame.getChildren().addAll(endlessGameButton, livesForEndlessGameLabel, hBoxForLivesForEndlessGame, hBoxForCountDownForEndlessGame, descriptionForEndlessGameLabel);

		hBoxForGameModes = new CustomHBox(true, Pos.CENTER, false, null);
		hBoxForGameModes.getChildren().addAll(vBoxForClassicGame, vBoxForTimeAttackGame, vBoxForEndlessGame);

		titleLabel1.setEffect(innerShadow);
		titleLabel2.setEffect(innerShadow);

		woodenFrameImage.setEffect(dropShadow);
		titleImage1.setEffect(dropShadow);
		titleImage2.setEffect(dropShadow);
		backButton.setEffect(dropShadow);
		nextButton.setEffect(dropShadow);
		titleImage2.setEffect(dropShadow);
		vBoxForDifficultyLevel.setEffect(dropShadow);
		gridPaneForQuestionsCategories.setEffect(dropShadow);
		scrollPaneForExtendedCategoryQuestionsGridPane.setEffect(dropShadow);
		vBoxForClassicGame.setEffect(dropShadow);
		vBoxForTimeAttackGame.setEffect(dropShadow);
		vBoxForEndlessGame.setEffect(dropShadow);

		nodesPane.getChildren().addAll(previousImage, movingEarthImage, woodPanelFor1IconImage, titleImage2, titleImage1, titleLabel1,
				titleLabel2, vBoxForSound, soundButton, backButton, nextButton, vBoxForDifficultyLevel, gridPaneForQuestionsCategories,
				extendedQuestionCategoriesButton, scrollPaneForExtendedCategoryQuestionsGridPane, exitExtendedCategoryQuestions);
		
		if(animationsUsed != ANIMATIONS.NO) setupLimitedAnimations();

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
				if(new_toggle == easyLevelRadioButton) setDifficultyLevel(DIFFICULTY.EASY);
				else setDifficultyLevel(DIFFICULTY.DIFFICULT);

				handler.updateCheckboxesState(false);
			});

		extendedQuestionCategoriesButton.setOnAction(e -> openExtendedQuestionCategories());

		exitExtendedCategoryQuestions.setOnMousePressed(e -> exitExtendedCategoryQuestions.setImage(images.get(Images.X_CLICKED)));
		exitExtendedCategoryQuestions.setOnMouseClicked(e -> closeExtendedQuestionCategories());
		exitExtendedCategoryQuestions.setOnMouseReleased(e -> exitExtendedCategoryQuestions.setImage(images.get(Images.X)));

		scrollPaneForExtendedCategoryQuestionsGridPane.setOnScroll(e -> scrollPaneForExtendedCategoryQuestionsGridPane.setHvalue(-e.getDeltaY()));

		classicGameButton.setOnAction(e -> startGame(GAMEMODE.CLASSIC_GAMEMODE));
		timeAttackGameButton.setOnAction(e -> startGame(GAMEMODE.TIME_ATTACK_GAMEMODE));
		endlessGameButton.setOnAction(e -> startGame(GAMEMODE.ENDLESS_GAMEMODE));

		toggleGroupForClassicGame.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) ->
		{
			if(new_toggle == radioButton10QuestionsForClassicGameLabel) setNumberOfQuestionsForClassic(NUM_OF_QUESTIONS_FOR_CLASSIC_10);
			else if(new_toggle == radioButton20QuestionsForClassicGameLabel) setNumberOfQuestionsForClassic(NUM_OF_QUESTIONS_FOR_CLASSIC_20);
			else if(new_toggle == radioButton50QuestionsForClassicGameLabel) setNumberOfQuestionsForClassic(NUM_OF_QUESTIONS_FOR_CLASSIC_50);
			else setNumberOfQuestionsForClassic(NUM_OF_QUESTIONS_FOR_CLASSIC_100);

			descriptionForClassicGameLabel.setText(getDescriptionForClassicGameMode());
		});

		countDownToggleForClassicGameLabel.setOnMouseClicked(e ->
		{
			countDownToggleForClassicGameLabel.setValue(!countDownToggleForClassicGameLabel.getValue());

			if(countDownToggleForClassicGameLabel.getValue()) getAudioStuff().playSwitchButtonOnSound();
			else getAudioStuff().playSwitchButtonOffSound();

			setTimerForClassic(countDownToggleForClassicGameLabel.getValue());

			descriptionForClassicGameLabel.setText(getDescriptionForClassicGameMode());
		});

		countDownToggleForClassicGameLabel.getButton().setOnAction(e ->
		{
			countDownToggleForClassicGameLabel.setValue(!countDownToggleForClassicGameLabel.getValue());

			if(countDownToggleForClassicGameLabel.getValue()) getAudioStuff().playSwitchButtonOnSound();
			else getAudioStuff().playSwitchButtonOffSound();

			setTimerForClassic(countDownToggleForClassicGameLabel.getValue());

			descriptionForClassicGameLabel.setText(getDescriptionForClassicGameMode());
		});

		toggleGroupForTimeAttackGame.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) ->
		{
			if(new_toggle == radioButton1MinuteDurationForTimeAttackGameLabel) setDurationInMinutesForTimeAttack(TIME_ATTACK_DURATION_1_MINUTE);
			else if(new_toggle == radioButton2MinutesDurationForTimeAttackGameLabel) setDurationInMinutesForTimeAttack(TIME_ATTACK_DURATION_2_MINUTES);
			else if(new_toggle == radioButton5MinutesDurationForTimeAttackGameLabel) setDurationInMinutesForTimeAttack(TIME_ATTACK_DURATION_5_MINUTES);
			else setDurationInMinutesForTimeAttack(TIME_ATTACK_DURATION_10_MINUTES);

			descriptionForTimeAttackGameLabel.setText(getDescriptionForTimeAttackGameMode());
		});

		toggleGroupForEndlessGame.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) ->
		{
			if(new_toggle == radioButton1LifeForEndlessGameLabel) setLivesForEndless(ENDLESS_LIVES_1);
			else if(new_toggle == radioButton3LivesForEndlessGameLabel) setLivesForEndless(ENDLESS_LIVES_3);
			else setLivesForEndless(ENDLESS_LIVES_5);

			descriptionForEndlessGameLabel.setText(getDescriptionForEndlessGameMode());
		});

		countDownToggleForEndlessGameLabel.setOnMouseClicked(e ->
		{
			countDownToggleForEndlessGameLabel.setValue(!countDownToggleForEndlessGameLabel.getValue());

			if(countDownToggleForEndlessGameLabel.getValue()) getAudioStuff().playSwitchButtonOnSound();
			else getAudioStuff().playSwitchButtonOffSound();

			setTimerForEndless(countDownToggleForEndlessGameLabel.getValue());

			descriptionForEndlessGameLabel.setText(getDescriptionForEndlessGameMode());
		});
		countDownToggleForEndlessGameLabel.getButton().setOnAction(e ->
		{
			countDownToggleForEndlessGameLabel.setValue(!countDownToggleForEndlessGameLabel.getValue());

			if(countDownToggleForEndlessGameLabel.getValue()) getAudioStuff().playSwitchButtonOnSound();
			else getAudioStuff().playSwitchButtonOffSound();

			setTimerForEndless(countDownToggleForEndlessGameLabel.getValue());

			descriptionForEndlessGameLabel.setText(getDescriptionForEndlessGameMode());
		});
		
		backButton.setOnMouseClicked(e -> {});
		backButton.setOnAction(e ->
			{
				getAudioStuff().playButtonClickSound();
				
				if(screen1)
				{
					returnToWelcomeScreen();
				}
				else
				{
					if(animationsUsed != ANIMATIONS.NO) timelineFromScreen2ToScreen1.playFromStart();
					else
					{
						if(vBoxForSound.isVisible())
						{
							vBoxForSound.setVisible(false);
							setCorrectSoundIcon(false);

							vBoxForSound.setTranslateY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));

							titleImage1.setTranslateX(0);
							titleLabel1.setTranslateX(0);
						}

						nodesPane.getChildren().clear();

						screen1 = true;

						updateStrings();

						nodesPane.getChildren().addAll(previousImage, movingEarthImage, woodPanelFor1IconImage,
								titleImage2, titleImage1, titleLabel1, titleLabel2, vBoxForSound, soundButton,
								backButton, nextButton, vBoxForDifficultyLevel, gridPaneForQuestionsCategories,
								extendedQuestionCategoriesButton, scrollPaneForExtendedCategoryQuestionsGridPane,
								exitExtendedCategoryQuestions);

						recalculateBackground(mainScene.getWidth(), mainScene.getHeight());
						recalculateUI(mainScene.getWidth(), mainScene.getHeight());
					}
				}
			});
		
		nextButton.setOnMouseClicked(e -> {});
		nextButton.setOnAction(e ->
			{
				getAudioStuff().playButtonClickSound();
				
				if(!isNextButtonDisabled())
				{
					if(animationsUsed != ANIMATIONS.NO) timelineFromScreen1ToScreen2.playFromStart();
					else
					{
						if(vBoxForSound.isVisible())
						{
							vBoxForSound.setVisible(false);
							setCorrectSoundIcon(false);

							vBoxForSound.setTranslateY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));

							titleImage1.setTranslateX(0);
							titleLabel1.setTranslateX(0);
						}

						nodesPane.getChildren().clear();

						screen1 = false;

						updateStrings();

						nodesPane.getChildren().addAll(movingEarthImage, woodPanelFor1IconImage, titleImage1,
								titleLabel1, hBoxForGameModes, backButton, vBoxForSound, soundButton);

						recalculateBackground(mainScene.getWidth(), mainScene.getHeight());
						recalculateUI(mainScene.getWidth(), mainScene.getHeight());
					}
				}
			});

		soundButton.setOnAction(e ->
			{
				soundButton.setDisable(true);

				if (!vBoxForSound.isVisible())
				{
					if(animationsUsed != ANIMATIONS.NO) timelineToShowSoundOptions.play();
					else
					{
						vBoxForSound.setTranslateY(0);

						titleImage1.setTranslateX(-0.0651 * mainScene.getWidth());
						titleLabel1.setTranslateX(-0.0651 * mainScene.getWidth());

						vBoxForSound.setVisible(true);
					}
				}
				else
				{
					if(animationsUsed != ANIMATIONS.NO) timelineToHideSoundOptions.play();
					else
					{
						vBoxForSound.setTranslateY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));

						titleImage1.setTranslateX(0);
						titleLabel1.setTranslateX(0);

						vBoxForSound.setVisible(false);
					}
				}
				soundButton.setDisable(false);
			});

		movingEarthImage.setOnMouseClicked(e ->
			{
				if (animationsUsed == ANIMATIONS.ALL)
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
				   && !anchorPane.getCursor().equals(Cursor.H_RESIZE) && !anchorPane.getCursor().equals(Cursor.V_RESIZE))
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
				}
			});
	}

	protected void setupLimitedAnimations()
	{
		fadeTransitionForMovingEarthImage = new FadeTransition(Duration.millis(300), movingEarthImage);

		translateTransitionForWoodPanelFor1IconImage = new TranslateTransition(Duration.millis(200), woodPanelFor1IconImage);

		translateTransitionForTitleImage1 = new TranslateTransition(Duration.millis(200), titleImage1);
		translateTransitionForTitleLabel1 = new TranslateTransition(Duration.millis(200), titleLabel1);

		scaleTransitionForTitleLabel1 = new ScaleTransition(Duration.millis(200), titleLabel1);
		scaleTransitionForTitleLabel2 = new ScaleTransition(Duration.millis(200), titleLabel2);

		TranslateTransition translateTransitionForTitleImage2 = new TranslateTransition(Duration.millis(200), titleImage2);
		ScaleTransition scaleTransitionForTitleImage2 = new ScaleTransition(Duration.millis(200), titleImage2);
		parallelTransitionForTitleImage2 = new ParallelTransition(translateTransitionForTitleImage2, scaleTransitionForTitleImage2);

		scaleTransitionForBackButton = new ScaleTransition(Duration.millis(200), backButton);
		scaleTransitionForNextButton = new ScaleTransition(Duration.millis(200), nextButton);

		scaleTransitionForVBoxForDifficultyLevel = new ScaleTransition(Duration.millis(200), vBoxForDifficultyLevel);

		scaleTransitionForGridPaneForQuestionsCategories = new ScaleTransition(Duration.millis(200), gridPaneForQuestionsCategories);

		scaleTransitionForExtendedQuestionCategoriesLabel = new ScaleTransition(Duration.millis(200), extendedQuestionCategoriesButton);
		scaleTransitionForScrollPaneForExtendedQuestionCategories = new ScaleTransition(Duration.millis(200), scrollPaneForExtendedCategoryQuestionsGridPane);
		scaleTransitionForExitExtendedCategoryQuestions = new ScaleTransition(Duration.millis(200), exitExtendedCategoryQuestions);

		translateTransitionForVBoxForSound = new TranslateTransition(Duration.millis(200), vBoxForSound);
		scaleTransitionForSoundIcon = new ScaleTransition(Duration.millis(200), soundButton);

		scaleTransitionForVBoxForClassicGame = new ScaleTransition(Duration.millis(200), vBoxForClassicGame);
		scaleTransitionForVBoxForTimeAttackGame = new ScaleTransition(Duration.millis(200), vBoxForTimeAttackGame);
		scaleTransitionForVBoxForEndlessGame = new ScaleTransition(Duration.millis(200), vBoxForEndlessGame);

		timelineToShowAllStuff = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					backButton.setDisable(true);
					nextButton.setDisable(true);

					fadeTransitionForMovingEarthImage.setToValue(1);
					fadeTransitionForMovingEarthImage.playFromStart();
				}),
				new KeyFrame(Duration.millis(300), e ->
				{
					translateTransitionForTitleImage1.setToX(0);
					translateTransitionForTitleImage1.setToY(0);

					translateTransitionForWoodPanelFor1IconImage.setToY(0);

					getAudioStuff().playSlideSound();
					translateTransitionForTitleImage1.play();
					translateTransitionForWoodPanelFor1IconImage.playFromStart();
				}),
				new KeyFrame(Duration.millis(500), e ->
				{
					translateTransitionForTitleImage2.setToY(0);
					scaleTransitionForTitleImage2.setToY(1);

					getAudioStuff().playSlideSound();
					parallelTransitionForTitleImage2.playFromStart();
				}),
				new KeyFrame(Duration.millis(700), e ->
				{
					scaleTransitionForSoundIcon.setToX(1);
					scaleTransitionForSoundIcon.setToY(1);

					scaleTransitionForTitleLabel1.setDuration(Duration.millis(200));
					scaleTransitionForTitleLabel1.setFromX(0);
					scaleTransitionForTitleLabel1.setFromY(0);
					scaleTransitionForTitleLabel1.setToX(0.95);
					scaleTransitionForTitleLabel1.setToY(0.95);

					scaleTransitionForTitleLabel2.setDuration(Duration.millis(200));
					scaleTransitionForTitleLabel2.setFromX(0);
					scaleTransitionForTitleLabel2.setFromY(0);
					scaleTransitionForTitleLabel2.setToX(0.95);
					scaleTransitionForTitleLabel2.setToY(0.95);

					scaleTransitionForBackButton.setToX(1);
					scaleTransitionForBackButton.setToY(1);
					scaleTransitionForNextButton.setToX(1);
					scaleTransitionForNextButton.setToY(1);
					scaleTransitionForVBoxForDifficultyLevel.setToX(1);
					scaleTransitionForVBoxForDifficultyLevel.setToY(1);
					scaleTransitionForGridPaneForQuestionsCategories.setToX(1);
					scaleTransitionForGridPaneForQuestionsCategories.setToY(1);
					scaleTransitionForExtendedQuestionCategoriesLabel.setToX(1);
					scaleTransitionForExtendedQuestionCategoriesLabel.setToY(1);

					if(animationsUsed == ANIMATIONS.ALL)
					{
						scaleTransitionForTitleLabel1.setOnFinished(ev ->
						{
							scaleTransitionForTitleLabel1.setOnFinished(eve -> {});
							startTextAnimationForTitleLabel1();
							startTextAnimationForTitleLabel2();
						});
					}

					getAudioStuff().playPopUpSound();
					scaleTransitionForTitleLabel1.playFromStart();
					scaleTransitionForTitleLabel2.playFromStart();
					scaleTransitionForBackButton.playFromStart();
					scaleTransitionForNextButton.playFromStart();
					scaleTransitionForVBoxForDifficultyLevel.playFromStart();
					scaleTransitionForGridPaneForQuestionsCategories.playFromStart();
					scaleTransitionForExtendedQuestionCategoriesLabel.playFromStart();
					scaleTransitionForSoundIcon.playFromStart();
				}),
				new KeyFrame(Duration.millis(900), e ->
				{
					backButton.setDisable(false);
					nextButton.setDisable(false);
				})
		);

		timelineToHideAllStuffFromScreen1 = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					backButton.setDisable(true);
					nextButton.setDisable(true);

					scaleTransitionForSoundIcon.setToX(0);
					scaleTransitionForSoundIcon.setToY(0);

					scaleTransitionForTitleLabel1.setDuration(Duration.millis(200));
					scaleTransitionForTitleLabel1.setFromX(titleLabel1.getScaleX());
					scaleTransitionForTitleLabel1.setFromY(titleLabel1.getScaleY());
					scaleTransitionForTitleLabel1.setToX(0);
					scaleTransitionForTitleLabel1.setToY(0);
					scaleTransitionForTitleLabel1.setAutoReverse(false);
					scaleTransitionForTitleLabel1.setCycleCount(1);

					scaleTransitionForTitleLabel2.setDuration(Duration.millis(200));
					scaleTransitionForTitleLabel2.setFromX(titleLabel2.getScaleX());
					scaleTransitionForTitleLabel2.setFromY(titleLabel2.getScaleY());
					scaleTransitionForTitleLabel2.setToX(0);
					scaleTransitionForTitleLabel2.setToY(0);
					scaleTransitionForTitleLabel2.setAutoReverse(false);
					scaleTransitionForTitleLabel2.setCycleCount(1);

					scaleTransitionForTitleLabel1.setOnFinished(ev -> {});
					scaleTransitionForTitleLabel2.setOnFinished(ev -> {});

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

					getAudioStuff().playMinimizeSound();
					scaleTransitionForVBoxForDifficultyLevel.playFromStart();
					scaleTransitionForGridPaneForQuestionsCategories.playFromStart();
					scaleTransitionForExtendedQuestionCategoriesLabel.playFromStart();
					scaleTransitionForTitleLabel1.playFromStart();
					scaleTransitionForTitleLabel2.playFromStart();
					scaleTransitionForBackButton.playFromStart();
					scaleTransitionForNextButton.playFromStart();
					scaleTransitionForSoundIcon.playFromStart();
				}),
				new KeyFrame(Duration.millis(200), e ->
				{
					translateTransitionForTitleImage2.setToY(-1.0 * (0.231) * mainScene.getHeight());
					scaleTransitionForTitleImage2.setToY(0);

					getAudioStuff().playSlideSound();
					parallelTransitionForTitleImage2.playFromStart();
				}),
				new KeyFrame(Duration.millis(400), e ->
				{
					translateTransitionForTitleImage1.setToY(-1.0 * (titleImage1.getLayoutY() + titleImage1.getBoundsInParent().getHeight() + 20));

					translateTransitionForWoodPanelFor1IconImage.setToY(-1.0 * (woodPanelFor1IconImage.getLayoutY() + woodPanelFor1IconImage.getBoundsInParent().getHeight()));

					getAudioStuff().playSlideSound();
					translateTransitionForTitleImage1.playFromStart();
					translateTransitionForWoodPanelFor1IconImage.playFromStart();
					if(vBoxForSound.isVisible())
					{
						setCorrectSoundIcon(false);

						translateTransitionForVBoxForSound.setToY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
						translateTransitionForVBoxForSound.playFromStart();
					}
				}),
				new KeyFrame(Duration.millis(600), e ->
				{
					vBoxForSound.setVisible(false);

					fadeTransitionForMovingEarthImage.setToValue(0);
					fadeTransitionForMovingEarthImage.playFromStart();
				}),
				new KeyFrame(Duration.millis(900), e ->
				{
					if(animationsUsed == ANIMATIONS.ALL)
					{
						pauseEarthAnimation();
						pauseTextAnimation();
					}

					backButton.setDisable(false);
					nextButton.setDisable(false);
				})
		);

		timelineToHideAllStuffFromScreen2 = new Timeline(
			new KeyFrame(Duration.millis(200), e ->
			{
				backButton.setDisable(true);
				classicGameButton.setDisable(true);
				timeAttackGameButton.setDisable(true);
				endlessGameButton.setDisable(true);

				getAudioStuff().playPopUpSound();

				scaleTransitionForVBoxForClassicGame.setToY(0);
				scaleTransitionForVBoxForClassicGame.playFromStart();
			}),
			new KeyFrame(Duration.millis(350), e ->
			{
				getAudioStuff().playPopUpSound();

				scaleTransitionForVBoxForTimeAttackGame.setToY(0);
				scaleTransitionForVBoxForTimeAttackGame.playFromStart();
			}),
			new KeyFrame(Duration.millis(500), e ->
			{
				getAudioStuff().playPopUpSound();

				scaleTransitionForVBoxForEndlessGame.setToY(0);
				scaleTransitionForVBoxForEndlessGame.playFromStart();
			}),
			new KeyFrame(Duration.millis(700), e ->
			{
				scaleTransitionForSoundIcon.setToX(0);
				scaleTransitionForSoundIcon.setToY(0);

				scaleTransitionForTitleLabel1.setDuration(Duration.millis(200));
				scaleTransitionForTitleLabel1.setFromX(titleLabel1.getScaleX());
				scaleTransitionForTitleLabel1.setFromY(titleLabel1.getScaleY());
				scaleTransitionForTitleLabel1.setToX(0);
				scaleTransitionForTitleLabel1.setToY(0);
				scaleTransitionForTitleLabel1.setAutoReverse(false);
				scaleTransitionForTitleLabel1.setCycleCount(1);

				scaleTransitionForTitleLabel1.setOnFinished(ev -> {});

				scaleTransitionForBackButton.setToX(0);
				scaleTransitionForBackButton.setToY(0);

				getAudioStuff().playMinimizeSound();
				scaleTransitionForTitleLabel1.playFromStart();
				scaleTransitionForBackButton.playFromStart();
				scaleTransitionForSoundIcon.playFromStart();
			}),
			new KeyFrame(Duration.millis(900), e ->
			{
				translateTransitionForTitleImage1.setToY(-1.0 * (titleImage1.getLayoutY() + titleImage1.getBoundsInParent().getHeight() + 20));

				translateTransitionForWoodPanelFor1IconImage.setToY(-1.0 * (woodPanelFor1IconImage.getLayoutY() + woodPanelFor1IconImage.getBoundsInParent().getHeight()));

				getAudioStuff().playSlideSound();
				translateTransitionForTitleImage1.playFromStart();
				translateTransitionForWoodPanelFor1IconImage.playFromStart();
				if(vBoxForSound.isVisible())
				{
					setCorrectSoundIcon(false);

					translateTransitionForVBoxForSound.setToY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
					translateTransitionForVBoxForSound.playFromStart();
				}
			}),
			new KeyFrame(Duration.millis(1100), e ->
			{
				vBoxForSound.setVisible(false);

				fadeTransitionForMovingEarthImage.setToValue(0);
				fadeTransitionForMovingEarthImage.playFromStart();
			}),
			new KeyFrame(Duration.millis(1300), e ->
			{
				if(animationsUsed == ANIMATIONS.ALL)
				{
					pauseEarthAnimation();
					pauseTextAnimation();
				}

				backButton.setDisable(false);
				classicGameButton.setDisable(false);
				timeAttackGameButton.setDisable(false);
				endlessGameButton.setDisable(false);
			}));

		timelineFromScreen1ToScreen2 = new Timeline(
				new KeyFrame(Duration.millis(200), e ->
				{
					backButton.setDisable(true);
					nextButton.setDisable(true);
					classicGameButton.setDisable(true);
					timeAttackGameButton.setDisable(true);
					endlessGameButton.setDisable(true);

					scaleTransitionForTitleLabel1.setDuration(Duration.millis(200));
					scaleTransitionForTitleLabel1.setFromX(titleLabel1.getScaleX());
					scaleTransitionForTitleLabel1.setFromY(titleLabel1.getScaleY());
					scaleTransitionForTitleLabel1.setToX(0);
					scaleTransitionForTitleLabel1.setToY(0);
					scaleTransitionForTitleLabel1.setAutoReverse(false);
					scaleTransitionForTitleLabel1.setCycleCount(1);

					scaleTransitionForTitleLabel2.setDuration(Duration.millis(200));
					scaleTransitionForTitleLabel2.setFromX(titleLabel2.getScaleX());
					scaleTransitionForTitleLabel2.setFromY(titleLabel2.getScaleY());
					scaleTransitionForTitleLabel2.setToX(0);
					scaleTransitionForTitleLabel2.setToY(0);
					scaleTransitionForTitleLabel2.setAutoReverse(false);
					scaleTransitionForTitleLabel2.setCycleCount(1);

					scaleTransitionForTitleLabel1.setOnFinished(ev -> {});
					scaleTransitionForTitleLabel2.setOnFinished(ev -> {});

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

					getAudioStuff().playMinimizeSound();
					scaleTransitionForVBoxForDifficultyLevel.playFromStart();
					scaleTransitionForGridPaneForQuestionsCategories.playFromStart();
					scaleTransitionForExtendedQuestionCategoriesLabel.playFromStart();
					scaleTransitionForTitleLabel1.playFromStart();
					scaleTransitionForTitleLabel2.playFromStart();
					scaleTransitionForNextButton.playFromStart();
					scaleTransitionForBackButton.playFromStart();
				}),
				new KeyFrame(Duration.millis(400), e ->
				{
					translateTransitionForTitleImage2.setToY(-1.0 * (0.231) * mainScene.getHeight());
					scaleTransitionForTitleImage2.setToY(0);

					getAudioStuff().playSlideSound();
					parallelTransitionForTitleImage2.playFromStart();

					if(vBoxForSound.isVisible())
					{
						setCorrectSoundIcon(false);

						translateTransitionForVBoxForSound.setToY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
						translateTransitionForVBoxForSound.playFromStart();
					}
				}),
				new KeyFrame(Duration.millis(600), e ->
				{
					vBoxForSound.setVisible(false);

					translateTransitionForTitleImage1.setToY(-1.0 * (titleImage1.getLayoutY() + titleImage1.getBoundsInParent().getHeight() + 20));

					getAudioStuff().playSlideSound();
					translateTransitionForTitleImage1.playFromStart();
				}),
				new KeyFrame(Duration.millis(800), e ->
				{
					nodesPane.getChildren().clear();

					screen1 = false;

					updateStrings();

					nodesPane.getChildren().addAll(previousImage, movingEarthImage, woodPanelFor1IconImage,
							titleImage1, titleLabel1, hBoxForGameModes, backButton, vBoxForSound, soundButton);

					recalculateBackground(mainScene.getWidth(), mainScene.getHeight());
					recalculateUI(mainScene.getWidth(), mainScene.getHeight());
				}),
                new KeyFrame(Duration.millis(850), e ->
                {
                	titleImage1.setTranslateX(0);
					titleLabel1.setTranslateX(0);

                    translateTransitionForTitleImage1.setToX(0);
                    translateTransitionForTitleImage1.setToY(0);

	                getAudioStuff().playSlideSound();
                    translateTransitionForTitleImage1.playFromStart();
                }),
                new KeyFrame(Duration.millis(1050), e ->
                {
	                titleLabel1.setTranslateX(0);
	                scaleTransitionForTitleLabel1.setFromX(0);
	                scaleTransitionForTitleLabel1.setFromY(0);
	                scaleTransitionForTitleLabel1.setToX(0.95);
	                scaleTransitionForTitleLabel1.setToY(0.95);

	                scaleTransitionForBackButton.setToX(1);
	                scaleTransitionForBackButton.setToY(1);

	                if(animationsUsed == ANIMATIONS.ALL)
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
                new KeyFrame(Duration.millis(1250), e ->
                {
                	getAudioStuff().playPopUpSound();

					scaleTransitionForVBoxForClassicGame.setToY(1);
					scaleTransitionForVBoxForClassicGame.playFromStart();
                }),
                new KeyFrame(Duration.millis(1400), e ->
                {
	                getAudioStuff().playPopUpSound();

					scaleTransitionForVBoxForTimeAttackGame.setToY(1);
	                scaleTransitionForVBoxForTimeAttackGame.playFromStart();
                }),
                new KeyFrame(Duration.millis(1550), e ->
                {
	                getAudioStuff().playPopUpSound();

					scaleTransitionForVBoxForEndlessGame.setToY(1);
                    scaleTransitionForVBoxForEndlessGame.playFromStart();
                }),
                new KeyFrame(Duration.millis(1750), e ->
                {
	                backButton.setDisable(false);
	                nextButton.setDisable(false);
	                classicGameButton.setDisable(false);
	                timeAttackGameButton.setDisable(false);
	                endlessGameButton.setDisable(false);
                })
		);

		timelineFromScreen2ToScreen1 = new Timeline(
				new KeyFrame(Duration.millis(200), e ->
				{
					backButton.setDisable(true);
					nextButton.setDisable(true);
					classicGameButton.setDisable(true);
					timeAttackGameButton.setDisable(true);
					endlessGameButton.setDisable(true);

					getAudioStuff().playPopUpSound();

					scaleTransitionForVBoxForClassicGame.setToY(0);
					scaleTransitionForVBoxForClassicGame.playFromStart();
				}),
				new KeyFrame(Duration.millis(350), e ->
				{
					getAudioStuff().playPopUpSound();

					scaleTransitionForVBoxForTimeAttackGame.setToY(0);
					scaleTransitionForVBoxForTimeAttackGame.playFromStart();
				}),
				new KeyFrame(Duration.millis(500), e ->
				{
					getAudioStuff().playPopUpSound();

					scaleTransitionForVBoxForEndlessGame.setToY(0);
					scaleTransitionForVBoxForEndlessGame.playFromStart();
				}),
				new KeyFrame(Duration.millis(700), e ->
				{
					scaleTransitionForTitleLabel1.setDuration(Duration.millis(200));
					scaleTransitionForTitleLabel1.setFromX(titleLabel1.getScaleX());
					scaleTransitionForTitleLabel1.setFromY(titleLabel1.getScaleY());
					scaleTransitionForTitleLabel1.setToX(0);
					scaleTransitionForTitleLabel1.setToY(0);
					scaleTransitionForTitleLabel1.setAutoReverse(false);
					scaleTransitionForTitleLabel1.setCycleCount(1);

					scaleTransitionForTitleLabel1.setOnFinished(ev -> {});

					scaleTransitionForBackButton.setToX(0);
					scaleTransitionForBackButton.setToY(0);

					getAudioStuff().playMinimizeSound();
					scaleTransitionForTitleLabel1.playFromStart();
					scaleTransitionForBackButton.playFromStart();
				}),
				new KeyFrame(Duration.millis(900), e ->
				{
					translateTransitionForTitleImage1.setToY(-1.0 * (titleImage1.getLayoutY() + titleImage1.getBoundsInParent().getHeight() + 20));
					translateTransitionForTitleImage1.setToX(titleImage1.getTranslateX());

					getAudioStuff().playSlideSound();
					translateTransitionForTitleImage1.playFromStart();
					if(vBoxForSound.isVisible())
					{
						setCorrectSoundIcon(false);

						translateTransitionForVBoxForSound.setToY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
						translateTransitionForVBoxForSound.playFromStart();
					}
				}),
				new KeyFrame(Duration.millis(1100), e ->
				{
					vBoxForSound.setVisible(false);

					nodesPane.getChildren().clear();

					screen1 = true;

					updateStrings();

					nodesPane.getChildren().addAll(previousImage, movingEarthImage, woodPanelFor1IconImage,
							titleImage2, titleImage1, titleLabel1, titleLabel2, vBoxForSound, soundButton,
							backButton, nextButton, vBoxForDifficultyLevel, gridPaneForQuestionsCategories,
							extendedQuestionCategoriesButton, scrollPaneForExtendedCategoryQuestionsGridPane,
							exitExtendedCategoryQuestions);

					recalculateBackground(mainScene.getWidth(), mainScene.getHeight());
					recalculateUI(mainScene.getWidth(), mainScene.getHeight());
				}),
				new KeyFrame(Duration.millis(1150), e ->
				{
					titleImage1.setTranslateX(0);
					titleLabel1.setTranslateX(0);

					translateTransitionForTitleImage1.setToX(0);
					translateTransitionForTitleImage1.setToY(0);

					getAudioStuff().playSlideSound();
					translateTransitionForTitleImage1.playFromStart();
				}),
				new KeyFrame(Duration.millis(1350), e ->
				{
					translateTransitionForTitleImage2.setToY(0);
					scaleTransitionForTitleImage2.setToY(1);

					getAudioStuff().playSlideSound();
					parallelTransitionForTitleImage2.playFromStart();
				}),
				new KeyFrame(Duration.millis(1550), e ->
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
					scaleTransitionForVBoxForDifficultyLevel.setToX(1);
					scaleTransitionForVBoxForDifficultyLevel.setToY(1);
					scaleTransitionForGridPaneForQuestionsCategories.setToX(1);
					scaleTransitionForGridPaneForQuestionsCategories.setToY(1);
					scaleTransitionForExtendedQuestionCategoriesLabel.setToX(1);
					scaleTransitionForExtendedQuestionCategoriesLabel.setToY(1);

					if(animationsUsed == ANIMATIONS.ALL)
					{
						scaleTransitionForTitleLabel1.setOnFinished(ev ->
						{
							scaleTransitionForTitleLabel1.setOnFinished(eve -> {});
							startTextAnimationForTitleLabel1();
							startTextAnimationForTitleLabel2();
						});
					}

					getAudioStuff().playPopUpSound();
					scaleTransitionForTitleLabel1.playFromStart();
					scaleTransitionForTitleLabel2.playFromStart();
					scaleTransitionForBackButton.playFromStart();
					scaleTransitionForNextButton.playFromStart();
					scaleTransitionForVBoxForDifficultyLevel.playFromStart();
					scaleTransitionForGridPaneForQuestionsCategories.playFromStart();
					scaleTransitionForExtendedQuestionCategoriesLabel.playFromStart();
				}),
				new KeyFrame(Duration.millis(1750), e ->
				{
					backButton.setDisable(false);
					nextButton.setDisable(false);
					classicGameButton.setDisable(false);
					timeAttackGameButton.setDisable(false);
					endlessGameButton.setDisable(false);
				})
		);

		timelineToShowSoundOptions = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					soundButton.setDisable(true);

					translateTransitionForTitleImage1.setToX(-0.0651 * mainScene.getWidth());
					translateTransitionForTitleLabel1.setToX(-0.0651 * mainScene.getWidth());

					translateTransitionForTitleImage1.setToY(0);
					translateTransitionForTitleLabel1.setToY(0);

					getAudioStuff().playSlideSound();
					translateTransitionForTitleImage1.playFromStart();
					translateTransitionForTitleLabel1.playFromStart();
				}),
				new KeyFrame(Duration.millis(100), e ->
				{
					vBoxForSound.setVisible(true);
					translateTransitionForVBoxForSound.setToY(0);

					translateTransitionForVBoxForSound.playFromStart();
				}),
				new KeyFrame(Duration.millis(300), e -> soundButton.setDisable(false)));

		timelineToHideSoundOptions = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					soundButton.setDisable(true);

					translateTransitionForVBoxForSound.setToY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));

					getAudioStuff().playSlideSound();
					translateTransitionForVBoxForSound.playFromStart();
				}),
				new KeyFrame(Duration.millis(100), e ->
				{
					translateTransitionForTitleImage1.setToY(0);
					translateTransitionForTitleImage1.setToX(0);
					translateTransitionForTitleLabel1.setToY(0);
					translateTransitionForTitleLabel1.setToX(0);

					translateTransitionForTitleImage1.playFromStart();
					translateTransitionForTitleLabel1.playFromStart();
				}),
				new KeyFrame(Duration.millis(300), e ->
				{
					soundButton.setDisable(false);
					vBoxForSound.setVisible(false);
				}));
	}

	protected void showScreen()
	{
		super.showScreen();

		if(animationsUsed != ANIMATIONS.NO)
			timelineToShowAllStuff.playFromStart();
	}

	protected void setInitialStateForAllNodes()
	{
//		--------------- Load images if not already set ---------------
		if(previousImage.getImage() == null || !woodenFrameImage.getImage().equals(images.get(Images.FRAME)))
		{
			woodenFrameImage.setImage(images.get(Images.FRAME));
			movingEarthImage.setImage(images.get(Images.MOVING_EARTH_2));

			titleImage1.setImage(images.get(Images.EMPTY_WOOD_BACKGROUND_SMALL_ROPE));
			titleImage2.setImage(images.get(Images.EMPTY_WOOD_BACKGROUND_BIG_ROPE));

			woodPanelFor1IconImage.setImage(images.get(Images.EMPTY_WOOD_BACKGROUND_1_ICON));

			if(fromWelcomeScreen) previousImage.setImage(images.get(Images.CHALKBOARD_BACKGROUND));
			else previousImage.setImage(images.get(Images.WORLD_MAP));

			exitExtendedCategoryQuestions.setImage(images.get(Images.X));
		}

		if(!screen1)
		{
			if(fromWelcomeScreen) previousImage.setImage(images.get(Images.CHALKBOARD_BACKGROUND));
			else previousImage.setImage(images.get(Images.WORLD_MAP));

			nodesPane.getChildren().clear();

			screen1 = true;

			updateStrings();

			nodesPane.getChildren().addAll(previousImage, movingEarthImage, woodPanelFor1IconImage, titleImage2, titleImage1,
					titleLabel1, titleLabel2, vBoxForSound, soundButton, backButton, nextButton, vBoxForDifficultyLevel,
					gridPaneForQuestionsCategories, extendedQuestionCategoriesButton, scrollPaneForExtendedCategoryQuestionsGridPane,
					exitExtendedCategoryQuestions);
		}

//		--------------- SET POSITIONS ---------------
		setCorrectSoundIcon(false);
		vBoxForSound.setTranslateY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
		vBoxForSound.setVisible(false);

		titleImage1.setTranslateX(0);
		titleLabel1.setTranslateX(0);

		scrollPaneForExtendedCategoryQuestionsGridPane.setScaleX(0);
		scrollPaneForExtendedCategoryQuestionsGridPane.setScaleY(0);
		exitExtendedCategoryQuestions.setScaleX(0);
		exitExtendedCategoryQuestions.setScaleY(0);

		if(animationsUsed != ANIMATIONS.NO)
		{
			woodPanelFor1IconImage.setTranslateY(-1.0 * (woodPanelFor1IconImage.getLayoutY() + woodPanelFor1IconImage.getBoundsInParent().getHeight()));

			titleImage1.setTranslateY(-1.0 * (titleImage1.getLayoutY() + titleImage1.getBoundsInParent().getHeight() + 20));
			
			movingEarthImage.setOpacity(0);

			titleImage2.setTranslateY(-1.0 * (0.231) * mainScene.getHeight());
			titleImage2.setScaleY(0);

			titleLabel1.setScaleX(0);
			titleLabel1.setScaleY(0);

			titleLabel2.setScaleX(0);
			titleLabel2.setScaleY(0);

			soundButton.setScaleX(0);
			soundButton.setScaleY(0);

			vBoxForDifficultyLevel.setScaleX(0);
			vBoxForDifficultyLevel.setScaleY(0);

			gridPaneForQuestionsCategories.setScaleX(0);
			gridPaneForQuestionsCategories.setScaleY(0);

			extendedQuestionCategoriesButton.setScaleX(0);
			extendedQuestionCategoriesButton.setScaleY(0);

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
			woodPanelFor1IconImage.setTranslateY(0);

			soundButton.setScaleX(1);
			soundButton.setScaleY(1);

			movingEarthImage.setOpacity(1);

			titleImage1.setTranslateY(0);
			titleImage2.setTranslateY(0);

			titleImage2.setScaleY(1);

			titleLabel1.setScaleX(1);
			titleLabel1.setScaleY(1);

			titleLabel2.setScaleX(1);
			titleLabel2.setScaleY(1);

			vBoxForDifficultyLevel.setScaleX(1);
			vBoxForDifficultyLevel.setScaleY(1);

			gridPaneForQuestionsCategories.setScaleX(1);
			gridPaneForQuestionsCategories.setScaleY(1);

			extendedQuestionCategoriesButton.setScaleX(1);
			extendedQuestionCategoriesButton.setScaleY(1);

			vBoxForClassicGame.setScaleY(1);
			vBoxForTimeAttackGame.setScaleY(1);
			vBoxForEndlessGame.setScaleY(1);

			backButton.setScaleX(1);
			backButton.setScaleY(1);

			nextButton.setScaleX(1);
			nextButton.setScaleY(1);

			backButton.setDisable(false);
			nextButton.setDisable(false);
			soundButton.setDisable(false);
		}
		
		if(animationsUsed == ANIMATIONS.ALL)
		{
			movingEarthImage.setCursor(Cursor.HAND);
			playEarthAnimation();
		}
		else
		{
			movingEarthImage.setCursor(Cursor.DEFAULT);
			movingEarthImage.setViewport(getImageStuff().getMovingEarthImageViewports()[0]);
		}

//		--------------- SET VARIABLES AND TEXT ---------------
		if (getDifficultyLevel() == DIFFICULTY.EASY) easyLevelRadioButton.setSelected(true);
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

		if(animationsUsed != ANIMATIONS.NO)
		{
			scaleTransitionForTitleLabel1.setDuration(Duration.millis(200));
			scaleTransitionForTitleLabel1.setAutoReverse(false);
			scaleTransitionForTitleLabel1.setCycleCount(1);
			scaleTransitionForTitleLabel1.setFromX(titleLabel1.getScaleX());
			scaleTransitionForTitleLabel1.setFromY(titleLabel1.getScaleY());
			scaleTransitionForTitleLabel1.setToX(0);
			scaleTransitionForTitleLabel1.setToY(0);
			scaleTransitionForTitleLabel1.setOnFinished(e ->
			{
				titleLabel1.setText(languageResourceBundle.getString("extendedQuestionCategories"));

				if (getCurrentLanguage() == LANGUAGE.GREEK) titleLabel1.setFont(font55B);
				else titleLabel1.setFont(font70B);

				pauseTextAnimation();

				scaleTransitionForTitleLabel1.setFromX(0);
				scaleTransitionForTitleLabel1.setFromY(0);
				scaleTransitionForTitleLabel1.setToX(titleLabel2.getScaleX());
				scaleTransitionForTitleLabel1.setToY(titleLabel2.getScaleY());

				if(animationsUsed == ANIMATIONS.ALL)
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
			scaleTransitionForExitExtendedCategoryQuestions.setToX(1);
			scaleTransitionForExitExtendedCategoryQuestions.setToY(1);

			scaleTransitionForScrollPaneForExtendedQuestionCategories.setOnFinished(e ->
			{
				scaleTransitionForScrollPaneForExtendedQuestionCategories.setOnFinished(ev -> {});
				scaleTransitionForExitExtendedCategoryQuestions.playFromStart();
			});

			getAudioStuff().playMaximizeSound();
			scaleTransitionForScrollPaneForExtendedQuestionCategories.playFromStart();
			scaleTransitionForTitleLabel1.playFromStart();
		}
		else
		{
			titleLabel1.setText(languageResourceBundle.getString("extendedQuestionCategories"));

			if (getCurrentLanguage() == LANGUAGE.GREEK) titleLabel1.setFont(font55B);
			else titleLabel1.setFont(font70B);

			scrollPaneForExtendedCategoryQuestionsGridPane.setScaleX(1);
			scrollPaneForExtendedCategoryQuestionsGridPane.setScaleY(1);
			exitExtendedCategoryQuestions.setScaleX(1);
			exitExtendedCategoryQuestions.setScaleY(1);
		}
		isExtendedQuestionCategoriesOpen = true;

		backButton.setDisable(true);
		nextButton.setDisable(true);
		vBoxForDifficultyLevel.setDisable(true);
		extendedQuestionCategoriesButton.setDisable(true);
		soundButton.setDisable(true);
	}

	private void closeExtendedQuestionCategories()
	{
		if(animationsUsed != ANIMATIONS.NO)
		{
			scaleTransitionForTitleLabel1.setDuration(Duration.millis(200));
			scaleTransitionForTitleLabel1.setAutoReverse(false);
			scaleTransitionForTitleLabel1.setCycleCount(1);
			scaleTransitionForTitleLabel1.setFromX(titleLabel1.getScaleX());
			scaleTransitionForTitleLabel1.setFromY(titleLabel1.getScaleY());
			scaleTransitionForTitleLabel1.setToX(0);
			scaleTransitionForTitleLabel1.setToY(0);
			scaleTransitionForTitleLabel1.setOnFinished(e ->
			{
				titleLabel1.setText(languageResourceBundle.getString("titleForDifficultySelectionLabel"));

				if (getCurrentLanguage() == LANGUAGE.GREEK) titleLabel1.setFont(font65B);
				else titleLabel1.setFont(font70B);

				pauseTextAnimation();

				scaleTransitionForTitleLabel1.setFromX(0);
				scaleTransitionForTitleLabel1.setFromY(0);
				scaleTransitionForTitleLabel1.setToX(titleLabel2.getScaleX());
				scaleTransitionForTitleLabel1.setToY(titleLabel2.getScaleY());

				if(animationsUsed == ANIMATIONS.ALL)
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
			scaleTransitionForExitExtendedCategoryQuestions.setToX(0);
			scaleTransitionForExitExtendedCategoryQuestions.setToY(0);

			scaleTransitionForExitExtendedCategoryQuestions.setOnFinished(e ->
			{
				scaleTransitionForExitExtendedCategoryQuestions.setOnFinished(ev -> {});

				getAudioStuff().playMinimizeSound();
				scaleTransitionForScrollPaneForExtendedQuestionCategories.playFromStart();
			});
			scaleTransitionForExitExtendedCategoryQuestions.playFromStart();
			scaleTransitionForTitleLabel1.playFromStart();
		}
		else
		{
			titleLabel1.setText(languageResourceBundle.getString("titleForDifficultySelectionLabel"));

			if (getCurrentLanguage() == LANGUAGE.GREEK) titleLabel1.setFont(font65B);
			else titleLabel1.setFont(font70B);

			scrollPaneForExtendedCategoryQuestionsGridPane.setScaleX(0);
			scrollPaneForExtendedCategoryQuestionsGridPane.setScaleY(0);
			exitExtendedCategoryQuestions.setScaleX(0);
			exitExtendedCategoryQuestions.setScaleY(0);
		}
		isExtendedQuestionCategoriesOpen = false;

		backButton.setDisable(false);
		nextButton.setDisable(false);
		vBoxForDifficultyLevel.setDisable(false);
		extendedQuestionCategoriesButton.setDisable(false);
		soundButton.setDisable(false);
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

			extendedQuestionCategoriesButton.setText(languageResourceBundle.getString("extendedQuestionCategories"));
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

		soundButton.getTooltip().setText(languageResourceBundle.getString("soundOptionsTooltip"));

		backButton.setText(languageResourceBundle.getString("backButton"));
		backButton.getTooltip().setText(languageResourceBundle.getString("backButtonTooltipReturnToPreviousScreen"));

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

	private void startGame(GAMEMODE gameMode)
	{
		FilesIO.writePlayersFile();

		if(animationsUsed != ANIMATIONS.NO)
		{
			previousImage.setImage(images.get(Images.WORLD_MAP));
			previousImage.setLayoutX(getImageStuff().getWorldMapLayoutX() * mainScene.getWidth());
			previousImage.setLayoutY(getImageStuff().getWorldMapLayoutY() * mainScene.getHeight());
			previousImage.setFitWidth(getImageStuff().getWorldMapFitWidth() * mainScene.getWidth());
			previousImage.setFitHeight(getImageStuff().getWorldMapFitHeight() * mainScene.getHeight());

			timelineToHideAllStuffFromScreen2.setOnFinished(ev ->
			{
				gameScreen.setGameMode(gameMode);
				showOtherScreen(gameScreen);
			});
			timelineToHideAllStuffFromScreen2.playFromStart();
		}
		else
		{
			gameScreen.setGameMode(gameMode);
			showOtherScreen(gameScreen);
		}
	}

	private void returnToWelcomeScreen()
	{
		FilesIO.writePlayersFile();

		if(animationsUsed != ANIMATIONS.NO)
		{
			previousImage.setImage(images.get(Images.CHALKBOARD_BACKGROUND));
			previousImage.setLayoutX(0);
			previousImage.setLayoutY(0);
			previousImage.setFitWidth(mainScene.getWidth());
			previousImage.setFitHeight(mainScene.getHeight());

			timelineToHideAllStuffFromScreen1.setOnFinished(ev -> showOtherScreen(welcomeScreen));
			timelineToHideAllStuffFromScreen1.playFromStart();
		}
		else showOtherScreen(welcomeScreen);
	}

	private String getDescriptionForClassicGameMode()
	{
		StringBuilder s = new StringBuilder();

		if(getCurrentLanguage() == LANGUAGE.GREEK)
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

		if(getCurrentLanguage() == LANGUAGE.GREEK)
		{
			s.append("Έχεις ").append(getDurationInMinutesForTimeAttack());

			if(getDurationInMinutesForTimeAttack() == 1) s.append(" λεπτό ");
			else s.append(" λεπτά ");

			s.append("για να απαντήσεις σωστά σε όσες περισσότερες ερωτήσεις μπορείς από τις κατηγορίες που θα επιλέξεις. Κάθε λάθος απάντηση βαθμολογείται αρνητικά.");
		}
		else
		{
			s.append("You'll have ").append(getDurationInMinutesForTimeAttack());

			if(getDurationInMinutesForTimeAttack() == 1) s.append(" minute ");
			else s.append(" minutes ");

			s.append("to correctly answer as many questions as you can from the categories you'll choose. Each wrong answer is scored negatively.");
		}
		return s.toString();
	}

	private String getDescriptionForEndlessGameMode()
	{
		StringBuilder s = new StringBuilder();

		if(getCurrentLanguage() == LANGUAGE.GREEK)
		{
			s.append("Πρέπει να απαντήσεις σωστά σε όσες περισσότερες ερωτήσεις μπορείς. Έχεις ").append(getLivesForEndless());

			if(getLivesForEndless() == 1) s.append(" ευκαιρία ");
			else s.append(" ευκαιρίες ");

			s.append("για λάθος απάντηση ");

			if(isTimerForEndless()) s.append("και 10 δευτερόλεπτα για να απαντήσεις σε κάθε ερώτηση.");
			else s.append("χωρίς περιορισμό στο χρόνο απάντησης κάθε ερώτησης.");
		}
		else
		{
			s.append("You must correctly answer as many questions as you can. You have ").append(getLivesForEndless());

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
			if(((CheckBox) e.getSource()).isSelected()) getAudioStuff().playCheckBoxSelectedSound();
			else getAudioStuff().playCheckBoxDeselectedSound();

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
			if(getDifficultyLevel() == DIFFICULTY.EASY)
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
			else if(getDifficultyLevel() == DIFFICULTY.DIFFICULT)
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

	public void setFromWelcomeScreen(boolean fromWelcomeScreen)
	{
		this.fromWelcomeScreen = fromWelcomeScreen;
	}
}
