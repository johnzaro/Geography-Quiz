package com.johnzaro.geographyquiz.screens;

import com.johnzaro.geographyquiz.core.*;
import com.johnzaro.geographyquiz.dataStructures.Game;
import com.johnzaro.geographyquiz.dataStructures.Player;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;

/**
 * Created by John on 26/11/2016.
 */

public class GameScreen extends CoreScreen
{
	private HBox hBoxFor5Icons, hBoxForLives;
	private VBox vBoxForPausedGame, vBoxForNextQuestionButtonAndProgressBar;
	private GridPane gridPaneFor4TextAnswers, gridPaneFor2TextAnswers, gridPaneFor4ImageAnswers, gridPaneForAnswerIconsFor4ImageAnswers;
	private StackPane stackPaneFor4ImageAnswers, stackPaneForBigImage;
	
	private CustomImageView welcomeScreenImage, worldMapBackground, titleImageForFinishedGame, woodPanelFor5IconsImage, pauseGameIcon,
			nextQuestionArrowImage, imageViewForLargeHeart, imageViewForLargeAnswerIcon, imageViewForQuestionImage, imageViewForBigImage;
	private CustomImageView[] answerIconsFor2TextAnswers, answerIconsFor4TextAnswers, answerIconsFor4ImageAnswers, imageViewForLives, imageViewFor4ImageAnswers;
	
	private Label titleLabelForFinishedGame, popUpMessage;
	private CustomButton returnToGameButton, restartGameButton, returnToGamePropertiesButton, returnToWelcomeScreenButton, nextQuestionButton,
			restartGameFromFinishedScreenButton, returnToWelcomeScreenFromFinishedGameScreenButton;
	private ProgressBar progressBarForCountdown, progressBarFor2_5SecondsWaitForNextQuestion;
	private Text textForQuestionNumber, textForCountdown, textForScore, textForQuestion, textForTimePassed, textForResultsDuration, textForResults, textForResults2, textForCombo;
	private Text numberOfQuestionAnimated, comboAnimated, scoreAnimated;
	private RadioButton[] radioButtonsFor2TextAnswers, radioButtonsFor4TextAnswers;
	
	private ToggleGroup toggleGroupFor4PossibleAnswers, toggleGroupFor2PossibleAnswers;
	
	private CustomTooltip pauseGameTooltip;
	private CustomTooltip[] tooltipsForRadioButtonsFor4TextAnswers;
	
	private FadeTransition fadeTransitionForWorldMapImage;
	private ScaleTransition scaleTransitionForTitleLabelForFinishedGame, scaleTransitionForHBoxFor5Icons,
			scaleTransitionForPauseGameIcon, scaleTransitionForTextForTimePassed, scaleTransitionForGridPaneFor2TextAnswers, scaleTransitionForProgressBarForCountdown,
			scaleTransitionForTextForCountdown, scaleTransitionForTextForQuestionNumber, scaleTransitionForTextForScore, scaleTransitionForVBoxForNextQuestion,
			scaleTransitionForTextForQuestion, scaleTransitionForGridPaneFor4TextAnswers, scaleTransitionForStackPaneFor4ImageAnswers, scaleTransitionForQuestionImage,
			scaleTransitionForTextForResultsDuration, scaleTransitionForTextForResults, scaleTransitionForTextForResults2, scaleTransitionForRestartGameButton, scaleTransitionForReturnToWelcomeScreenButton,
			scaleTransitionForHBoxForLives, scaleTransitionForNumberOfQuestionAnimated, scaleTransitionForImageViewForLargeHeart,
			scaleTransitionForLargeAnswerIcon, scaleTransitionForTextForCombo, scaleTransitionForComboAnimated, scaleTransitionForScoreAnimated, scaleTransitionForPopUpMessage;
	private TranslateTransition translateTransitionForTitleImageForFinishedGame, translateTransitionForVBoxForSound, translateTransitionFoVBoxForPausedGame,
			translateTransitionForWoodPanelFor5IconsImage, translateTransitionForScoreText, translateTransitionForNumberOfQuestionAnimated,
			translateTransitionForImageViewForLargeHeart, translateTransitionForLargeAnswerIcon, translateTransitionForComboAnimated, translateTransitionForTextForCombo,
			translateTransitionForScoreAnimated;
	private ParallelTransition parallelTransitionForNumberOfQuestionAnimated, parallelTransitionForLifeLostAnimation, parallelTransitionForLargeAnswerIcon,
			parallelTransitionForComboAnimated, parallelTransitionForScoreAnimated;
	
	private ScaleTransition[] scaleTransitionFor2TextAnswers, scaleTransitionFor4TextAnswers, scaleTransitionFor4ImageAnswers,
			scaleTransitionForAnswerIconsFor2TextAnswers, scaleTransitionForAnswerIconsFor4TextAnswers, scaleTransitionForAnswerIconsFor4ImageAnswers;
	private SequentialTransition[] sequentialTransitionsFor2TextAnswers, sequentialTransitionsFor4TextAnswers, sequentialTransitionsFor4ImageAnswers;
	private Timeline timelineToShow2TextAnswers, timelineToShow4TextAnswers, timelineToShow4ImageAnswers;
	
	private Timeline timelineToShowSoundOptions, timelineToHideSoundOptions, timelineToShowAllStuff, timelineToHideAllStuffFromGameScreen;
	private Timeline timelineForTimePassedCount, timelineForQuestionCountdown, timelineFor2_5SecondsWaitForNextQuestion,
			timelineToFinishGame, timelineToHideAllStuffFromFinishedGameScreen, timelineToCountSecondsForQuestionCountdown, timelineForLifeLostAnimation,
			timelineForLargeAnswerIcon, timelineToCloseTextForCountdown, timelineForComboAnimated, timelineForComboNoAnimated, timelineForScoreAnimated,
			timelineForScoreNoAnimated;
	private Timeline timelineFor1MinuteProgressbarForTimeAttack, timelineFor2MinutesProgressbarForTimeAttack,
			timelineFor5MinutesProgressbarForTimeAttack, timelineFor10MinutesProgressbarForTimeAttack, timelineToCountSecondsForTimeAttack,
			timelineToShowNumberOfQuestion, timelineToCountMilliseconds, timelineToShowPopUpMessage;
	
	private InnerShadow innerShadow;
	private DropShadow dropShadowForNodes, dropShadowForText;
	private BoxBlur boxBlurForNodes, boxBlurForText;
	
	private QuestionMaker questionMaker;
	private AnswerEvaluator answerEvaluator;
	private PixelReader pr;
	private Random random;
	
	private ArrayList<Integer> availableCategories;
	
	private Game currentGame;
	private LocalDateTime gameStartedTime;
	
	//GENERAL VARIABLES
	private boolean returningToWelcomeScreen;
	private GAMEMODE gameMode;
	private int currentQuestionNumber, correctAnswers, scorePoints, combo, currentProgressForProgressbar;
	private int progressFor4SecondsWait;
	private int maxCombo;
	private long millisecondsPassed;
	private boolean openPauseMenu;
	
	//VARIABLES FOR GAME MODES
	private boolean questionCountdownEnabled;
	private int secondsPassed;
	private int remainingSecondsForTimeAttack;
	private int remainingLivesForEndless;
	
	protected void recalculateUI(double width, double height)
	{
		super.recalculateUI(width, height);
		
		double iconSize  = 0.0260 * width;
		
		Font fontForButtons        = Font.font("Comic Sans MS", FontWeight.BOLD, 0.0234 * width);
		Font fontForArrowButtons   = Font.font("Comic Sans MS", FontWeight.BOLD, 0.0156 * width);
		Font fontForLabels         = Font.font("Comic Sans MS", 0.0208 * width);
		double maxWidthForTooltips = (0.2604) * width;
		
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
		
		if (width < 1200)
		{
			radioButtonsFor4TextAnswers[0].setId("small");
			radioButtonsFor4TextAnswers[1].setId("small");
			radioButtonsFor4TextAnswers[2].setId("small");
			radioButtonsFor4TextAnswers[3].setId("small");
			
			radioButtonsFor2TextAnswers[0].setId("small");
			radioButtonsFor2TextAnswers[1].setId("small");
		}
		else
		{
			radioButtonsFor4TextAnswers[0].setId("big");
			radioButtonsFor4TextAnswers[1].setId("big");
			radioButtonsFor4TextAnswers[2].setId("big");
			radioButtonsFor4TextAnswers[3].setId("big");
			
			radioButtonsFor2TextAnswers[0].setId("big");
			radioButtonsFor2TextAnswers[1].setId("big");
		}
		
		//SCREEN RATIO DEPENDENT----------------------------------------------------------------------
		woodPanelFor5IconsImage.setLayoutY(ratioProperties.getGame().getWoodPanelFor5IconsImageLayoutY() * height);
		hBoxFor5Icons.setLayoutY(ratioProperties.getGame().gethBoxFor5IconsLayoutY() * height);
		
		vBoxForSound.setLayoutY(ratioProperties.getGame().getvBoxForSoundLayoutY() * height);
		vBoxForSound.setPrefSize(ratioProperties.getGame().getvBoxForSoundPrefWidth() * width, ratioProperties.getGame().getvBoxForSoundPrefHeight() * height);
		
		woodPanelFor5IconsImage.setFitWidth(0.1667 * width);
		
		//HBOX FOR LIVES
		if(gameMode == GAMEMODE.ENDLESS_GAMEMODE)
		{
			hBoxForLives.setLayoutY(0.1435 * height);
			hBoxForLives.setSpacing(0.0026 * width);
			
			for (int i = 0; i < hBoxForLives.getChildren().size(); i++) imageViewForLives[i].setFitWidth(1.2 * iconSize);
			
			imageViewForLargeHeart.setFitWidth(1.2 * iconSize);
			imageViewForLargeHeart.setLayoutX(width / 2.0 - imageViewForLargeHeart.getFitWidth() / 2.0);
			imageViewForLargeHeart.setLayoutY(height / 2.0 - imageViewForLargeHeart.getFitWidth() / 2.0);
		}
		
		textForQuestionNumber.setStyle("-fx-stroke: white;" +
		                               "-fx-stroke-width: " + 0.0010 * width + ";");
		
		if(progressBarForCountdown.isVisible())
		{
			numberOfQuestionAnimated.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0234 * width));
			textForQuestionNumber.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0234 * width));
			textForQuestionNumber.setLayoutY(0.1204 * height);
		}
		else
		{
			numberOfQuestionAnimated.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0286 * width));
			textForQuestionNumber.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0286 * width));
			textForQuestionNumber.setLayoutY(0.1250 * height);
		}
		
		//OS DEPENDENT STUFF----------------------------------------------------------------------
		pauseGameIcon.setFitWidth(1.4 * iconSize);
		
		if (OS == OsCheck.OSType.Windows)
		{
			woodPanelFor5IconsImage.setLayoutX(0.7792 * width);
			
			pauseGameIcon.setLayoutX(woodPanelFor5IconsImage.getLayoutX() - pauseGameIcon.getFitWidth() - 0.0156 * width);
			
			textForQuestionNumber.setLayoutX(0.0469 * width);
			
			hBoxForLives.setLayoutX(0.0469 * width);
		}
		else
		{
			woodPanelFor5IconsImage.setLayoutX(0.0542 * width);
			
			pauseGameIcon.setLayoutX(woodPanelFor5IconsImage.getLayoutX() + woodPanelFor5IconsImage.getFitWidth() + 0.0156 * width);
			
			textForQuestionNumber.setLayoutX(width - textForQuestionNumber.getBoundsInLocal().getWidth() - 0.0469 * width);
			
			hBoxForLives.setLayoutX(width - (hBoxForLives.getChildren().size() - 1) * hBoxForLives.getSpacing() - hBoxForLives.getChildren().size() * 1.2 * iconSize - 0.0469 * width);
		}
		
		//HBOX FOR 5 BUTTONS
		hBoxFor5Icons.setPrefSize(0.1667 * width, 0.0509 * height);
		hBoxFor5Icons.setSpacing(0.0052 * width);
		hBoxFor5Icons.setLayoutX(woodPanelFor5IconsImage.getLayoutX());
		
		soundIcon.setFitWidth(iconSize);
		minimizeIcon.setFitWidth(iconSize);
		moveIcon.setFitWidth(iconSize);
		fullScreenIcon.setFitWidth(iconSize);
		exitIcon.setFitWidth(iconSize);
		
		soundOptionsToolTip.setFont(fontForTooltips);
		minimizeTooltip.setFont(fontForTooltips);
		moveTooltip.setFont(fontForTooltips);
		fullScreenTooltip.setFont(fontForTooltips);
		exitTooltip.setFont(fontForTooltips);
		
		soundOptionsToolTip.setMaxWidth(maxWidthForTooltips);
		minimizeTooltip.setMaxWidth(maxWidthForTooltips);
		moveTooltip.setMaxWidth(maxWidthForTooltips);
		fullScreenTooltip.setMaxWidth(maxWidthForTooltips);
		exitTooltip.setMaxWidth(maxWidthForTooltips);
		
		//SOUND STUFF
		vBoxForSound.setLayoutX(woodPanelFor5IconsImage.getLayoutX() + woodPanelFor5IconsImage.getFitWidth() / 2.0 - vBoxForSound.getPrefWidth() / 2.0);
		vBoxForSound.setStyle("-fx-background-color: #00000099; " +
		                      "-fx-border-color: black; " +
		                      "-fx-background-radius:" + 0.0078 * width + "; " +
		                      "-fx-border-radius:" + 0.0078 * width + "; " +
		                      "-fx-border-width:" + 0.0026 * width + "; " +
		                      "-fx-padding:" + 0.0052 * width + ";");
		
		if(vBoxForSound.getTranslateX() != 0)
		{
			if (OS == OsCheck.OSType.Windows) vBoxForSound.setTranslateX(width - vBoxForSound.getLayoutX() + 20);
			else vBoxForSound.setTranslateX(-1.0 * (vBoxForSound.getLayoutX() + vBoxForSound.getPrefWidth() + 20));
		}
		
		Font fontForSound = Font.font("Comic Sans MS", 0.0104 * width);
		masterVolumeLabel.setFont(fontForSound);
		musicVolumeLabel.setFont(fontForSound);
		soundEffectsVolumeLabel.setFont(fontForSound);
		
		//QUESTION NUMBER
		numberOfQuestionAnimated.setLayoutY(height / 2.0);
		numberOfQuestionAnimated.setLayoutX(width / 2.0 - numberOfQuestionAnimated.getLayoutBounds().getWidth() / 2.0);
		numberOfQuestionAnimated.setStyle("-fx-stroke: white;" +
		                                  "-fx-stroke-width: " + 0.0010 * width + ";");
		
		//COUNTDOWN
		progressBarForCountdown.setPrefSize(0.2865 * width, 0.0463 * height);
		progressBarForCountdown.setLayoutX(width / 2.0 - progressBarForCountdown.getPrefWidth() / 2.0);
		
		textForCountdown.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0417 * width));
		textForCountdown.setStyle("-fx-stroke: white;" +
		                          "-fx-stroke-width: " + 0.0016 * width + ";");
		textForCountdown.setLayoutX(width / 2.0 - textForCountdown.getBoundsInLocal().getWidth() / 2.0);
		
		//TEXT FOR QUESTION
		textForQuestion.setLayoutX(0.0521 * width);
		textForQuestion.setWrappingWidth(0.7292 * width);
		textForQuestion.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0339 * width));
		textForQuestion.setStyle("-fx-stroke: white;" +
		                      "-fx-stroke-width: " + 0.0016 * width + ";");
		
		if(textForQuestion.getTranslateX() != 0) textForQuestion.setTranslateX(0.1771 * width);
		
		//SCORE TEXT
		textForScore.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0234 * width));
		textForScore.setStyle("-fx-stroke: white;" +
		                      "-fx-stroke-width: " + 0.0010 * width + ";");
		textForScore.setLayoutX(0.8625 * width - textForScore.getBoundsInLocal().getWidth() / 2.0);
		
		if(textForScore.getTranslateY() != 0) textForScore.setTranslateY(0.2065 * height);
		
		scoreAnimated.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0365 * width));
		scoreAnimated.setStyle("-fx-stroke: white;" +
		                       "-fx-stroke-width: " + 0.0016 * width + ";");
		
		//COMBO STUFF
		textForCombo.setLayoutX(textForScore.getLayoutX() + textForScore.getBoundsInLocal().getWidth() / 2.0 - textForCombo.getBoundsInLocal().getWidth() / 2.0);
		textForCombo.setLayoutY(0.3796 * height);
		textForCombo.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0365 * width));
		textForCombo.setStyle("-fx-stroke: white;" +
		                      "-fx-stroke-width: " + 0.0016 * width + ";");
		
		if(textForCombo.getTranslateY() != 0) textForCombo.setTranslateY(0.2065 * height);
		
		comboAnimated.setLayoutY(0.6018 * height);
		comboAnimated.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0365 * width));
		comboAnimated.setStyle("-fx-stroke: white;" +
		                       "-fx-stroke-width: " + 0.0016 * width + ";");
		
		//POSITION BASED ON EXISTENCE OF TIME PASSED TEXT
		if(textForTimePassed.isVisible())
		{
			if(progressBarForCountdown.isVisible())
			{
				textForTimePassed.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0234 * width));
				textForTimePassed.setLayoutY(0.1204 * height);
				
				progressBarForCountdown.setLayoutY(0.1343 * height);
				textForCountdown.setLayoutY(0.1852 * height);
			}
			else
			{
				textForTimePassed.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0286 * width));
				textForTimePassed.setLayoutY(0.1296 * height);
			}
			
			textForQuestion.setLayoutY(0.2593 * height);
			textForScore.setLayoutY(0.2315 * height);
			imageViewForQuestionImage.setFitHeight(0.3056 * height);
			
			if(gridPaneFor2TextAnswers.isVisible()) imageViewForQuestionImage.setLayoutY(0.3750 * height);
			else imageViewForQuestionImage.setLayoutY(0.2824 * height);
		}
		else
		{
			if(progressBarForCountdown.isVisible())
			{
				progressBarForCountdown.setLayoutY(0.1037 * height);
				textForCountdown.setLayoutY(0.1546 * height);
			}
			
			textForQuestion.setLayoutY(0.2407 * height);
			textForScore.setLayoutY(0.2130 * height);
			imageViewForQuestionImage.setFitHeight(0.3241 * height);
			
			if(gridPaneFor2TextAnswers.isVisible()) imageViewForQuestionImage.setLayoutY(0.3565 * height);
			else imageViewForQuestionImage.setLayoutY(0.2639 * height);
		}
		
		if(OS != OsCheck.OSType.Windows)
		{
			if(gameMode == GAMEMODE.ENDLESS_GAMEMODE)
			{
				textForScore.setLayoutY(0.2500 * height);
				textForCombo.setLayoutY(0.3889 * height);
			}
			else
			{
				textForScore.setLayoutY(0.2037 * height);
				textForCombo.setLayoutY(0.3426 * height);
			}
		}
		
		//IMAGE FOR QUESTION
		imageViewForQuestionImage.setLayoutX(width / 2.0 - imageViewForQuestionImage.getFitHeight() / 2.0);
		
		//TIME PASSED
		textForTimePassed.setLayoutX(width / 2.0 - textForTimePassed.getBoundsInLocal().getWidth() / 2.0);
		textForTimePassed.setStyle("-fx-stroke: white;" +
		                           "-fx-stroke-width: " + 0.0010 * width + ";");
		
		//IMAGE VIEW FOR LARGE ANSWER ICON
		imageViewForLargeAnswerIcon.setFitWidth(1.2 * iconSize);
		imageViewForLargeAnswerIcon.setLayoutX(width / 2.0 - imageViewForLargeAnswerIcon.getFitWidth() / 2.0);
		imageViewForLargeAnswerIcon.setLayoutY(height / 2.0 - imageViewForLargeAnswerIcon.getFitWidth() / 2.0);
		
		
		//GRID PANE FOR 2 TEXT ANSWERS
		gridPaneFor2TextAnswers.setPrefSize(0.2083 * width, 0.2130 * height);
		gridPaneFor2TextAnswers.setLayoutX(width / 2.0 - gridPaneFor2TextAnswers.getPrefWidth() / 2.0);
		gridPaneFor2TextAnswers.setLayoutY(0.6991 * height);
		gridPaneFor2TextAnswers.setHgap(0.0052 * width);
		gridPaneFor2TextAnswers.setVgap(0.0130 * width);
		gridPaneFor2TextAnswers.setStyle(
				"-fx-background-color: #000000be; -fx-border-color: black;" +
				"-fx-background-radius:" + 0.0208 * width + ";" +
				"-fx-border-radius:" + 0.0208 * width + ";" +
				"-fx-border-width:" + 0.0042 * width + ";" +
				"-fx-padding:" + 0.0104 * width);
		
		answerIconsFor2TextAnswers[0].setFitWidth(1.2 * iconSize);
		answerIconsFor2TextAnswers[1].setFitWidth(1.2 * iconSize);
		
		radioButtonsFor2TextAnswers[0].setFont(fontForLabels);
		radioButtonsFor2TextAnswers[1].setFont(fontForLabels);
		
		//GRID PANE FOR 4 TEXT ANSWERS
		gridPaneFor4TextAnswers.setPrefSize(0.4427 * width, 0.3148 * height);
		gridPaneFor4TextAnswers.setLayoutX(width / 2.0 - gridPaneFor4TextAnswers.getPrefWidth() / 2.0);
		gridPaneFor4TextAnswers.setLayoutY(0.5972 * height);
		gridPaneFor4TextAnswers.setHgap(0.0052 * width);
		gridPaneFor4TextAnswers.setVgap(0.0078 * width);
		gridPaneFor4TextAnswers.setStyle(
				"-fx-background-color: #000000be; -fx-border-color: black;" +
				"-fx-background-radius:" + 0.0208 * width + ";" +
				"-fx-border-radius:" + 0.0208 * width + ";" +
				"-fx-border-width:" + 0.0042 * width + ";" +
				"-fx-padding:" + 0.0104 * width);
		
		for(int i = 0; i < 4; i++)
		{
			answerIconsFor4TextAnswers[i].setFitWidth(1.2 * iconSize);
			
			radioButtonsFor4TextAnswers[i].setFont(fontForLabels);
			radioButtonsFor4TextAnswers[i].setPrefWidth(0.8730 * gridPaneFor4TextAnswers.getPrefWidth());
			
			tooltipsForRadioButtonsFor4TextAnswers[i].setFont(fontForTooltips);
			tooltipsForRadioButtonsFor4TextAnswers[i].setMaxWidth(maxWidthForTooltips);
		}
		
		//GRID PANE FOR 4 IMAGE ANSWERS
		stackPaneFor4ImageAnswers.setPrefSize(0.4167 * width, 0.5370 * height);
		stackPaneFor4ImageAnswers.setLayoutX(width / 2.0 - stackPaneFor4ImageAnswers.getPrefWidth() / 2.0);
		stackPaneFor4ImageAnswers.setLayoutY(0.3750 * height);
		stackPaneFor4ImageAnswers.setStyle(
				"-fx-background-color: #000000be; -fx-border-color: black;" +
				"-fx-background-radius:" + 0.0208 * width + ";" +
				"-fx-border-radius:" + 0.0208 * width + ";" +
				"-fx-border-width:" + 0.0042 * width + ";" +
				"-fx-padding:" + 0.0052 * width);
		
		gridPaneFor4ImageAnswers.setPrefSize(stackPaneFor4ImageAnswers.getPrefWidth(), stackPaneFor4ImageAnswers.getPrefHeight());
		gridPaneFor4ImageAnswers.setHgap(0.0521 * width);
		gridPaneFor4ImageAnswers.setVgap(0.0185 * height);
		
		gridPaneForAnswerIconsFor4ImageAnswers.setPrefSize(stackPaneFor4ImageAnswers.getPrefWidth(), stackPaneFor4ImageAnswers.getPrefHeight());
		gridPaneForAnswerIconsFor4ImageAnswers.setHgap(0.0521 * width);
		gridPaneForAnswerIconsFor4ImageAnswers.setVgap(0.0185 * height);
		
		for(int i = 0; i < 4; i++)
		{
			imageViewFor4ImageAnswers[i].setFitWidth(0.4310 * stackPaneFor4ImageAnswers.getPrefHeight());
			
			answerIconsFor4ImageAnswers[i].setFitWidth(0.4310 * stackPaneFor4ImageAnswers.getPrefHeight());
			answerIconsFor4ImageAnswers[i].setFitHeight(0.4310 * stackPaneFor4ImageAnswers.getPrefHeight());
		}
		
		//BIG IMAGE
		imageViewForBigImage.setFitWidth(0.3125 * width);
		
		//NEXT QUESTION BUTTON + PROGRESS BAR
		vBoxForNextQuestionButtonAndProgressBar.setPrefSize(0.1563 * width, 0.1667 * height);
		vBoxForNextQuestionButtonAndProgressBar.setLayoutX(0.8021 * width);
		vBoxForNextQuestionButtonAndProgressBar.setLayoutY(0.7500 * height);
		
		nextQuestionButton.setPrefWidth(vBoxForNextQuestionButtonAndProgressBar.getPrefWidth());
		nextQuestionArrowImage.setFitWidth(0.0703 * width);
		nextQuestionButton.setFont(fontForArrowButtons);
		
		progressBarFor2_5SecondsWaitForNextQuestion.setPrefSize(0.9 * vBoxForNextQuestionButtonAndProgressBar.getPrefWidth(), 0.0139 * height);
		progressBarFor2_5SecondsWaitForNextQuestion.setMinHeight(0.0139 * height);
		progressBarFor2_5SecondsWaitForNextQuestion.setMaxHeight(0.0139 * height);
		
		//PAUSE GAME STUFF
		pauseGameIcon.setLayoutY(0.0944 * height);
		
		popUpMessage.setPrefSize(0.3333 * width, 0.1852 * height);
		popUpMessage.setLayoutX(width / 2.0 - popUpMessage.getPrefWidth() / 2.0);
		popUpMessage.setLayoutY(height / 2.0 - popUpMessage.getPrefHeight() / 2.0);
		popUpMessage.setStyle(
				"-fx-background-color: #000000ce; -fx-border-color: black;" +
						"-fx-background-radius:" + 0.0208 * width + ";" +
						"-fx-border-radius:" + 0.0208 * width + ";" +
						"-fx-border-width:" + 0.0050 * width + ";" +
						"-fx-padding:" + 0.0052 * width + ";"
		);
		popUpMessage.setFont(fontForTooltips);
		
		vBoxForPausedGame.setPrefSize(0.4688 * width, 0.6019 * height);
		vBoxForPausedGame.setLayoutX(width / 2.0 - vBoxForPausedGame.getPrefWidth() / 2.0);
		vBoxForPausedGame.setLayoutY(height / 2.0 - vBoxForPausedGame.getPrefHeight() / 2.0);
		vBoxForPausedGame.setSpacing(0.0463 * height);
		
		vBoxForPausedGame.setStyle(
				"-fx-background-color: #00000099; -fx-border-color: black;" +
				"-fx-background-radius:" + 0.0208 * width + ";" +
				"-fx-border-radius:" + 0.0208 * width + ";" +
				"-fx-border-width:" + 0.0050 * width + ";" +
				"-fx-padding:" + 0.0130 * width);
		
		if(vBoxForPausedGame.getTranslateY() != 0) vBoxForPausedGame.setTranslateY(-1.0 * (vBoxForPausedGame.getLayoutY() + vBoxForPausedGame.getPrefHeight() + 20));
		
		returnToGameButton.setPrefWidth(vBoxForPausedGame.getPrefWidth());
		restartGameButton.setPrefWidth(vBoxForPausedGame.getPrefWidth());
		returnToGamePropertiesButton.setPrefWidth(vBoxForPausedGame.getPrefWidth());
		returnToWelcomeScreenButton.setPrefWidth(vBoxForPausedGame.getPrefWidth());
		
		returnToGameButton.setPrefHeight(0.0926 * height);
		restartGameButton.setPrefHeight(0.0926 * height);
		returnToGamePropertiesButton.setPrefHeight(0.0926 * height);
		returnToWelcomeScreenButton.setPrefHeight(0.0926 * height);
		
		returnToGameButton.setFont(fontForButtons);
		restartGameButton.setFont(fontForButtons);
		returnToGamePropertiesButton.setFont(fontForButtons);
		returnToWelcomeScreenButton.setFont(fontForButtons);
		
		pauseGameTooltip.setFont(fontForTooltips);
		returnToGameButton.getTooltip().setFont(fontForTooltips);
		restartGameButton.getTooltip().setFont(fontForTooltips);
		returnToGamePropertiesButton.getTooltip().setFont(fontForTooltips);
		returnToWelcomeScreenButton.getTooltip().setFont(fontForTooltips);
		
		pauseGameTooltip.setMaxWidth(maxWidthForTooltips);
		returnToGameButton.getTooltip().setMaxWidth(maxWidthForTooltips);
		restartGameButton.getTooltip().setMaxWidth(maxWidthForTooltips);
		returnToGamePropertiesButton.getTooltip().setMaxWidth(maxWidthForTooltips);
		returnToWelcomeScreenButton.getTooltip().setMaxWidth(maxWidthForTooltips);
		
		//FINISHED GAME STUFF
		titleImageForFinishedGame.setFitWidth(0.5156 * width);
		titleImageForFinishedGame.setLayoutX(width / 2.0 - titleImageForFinishedGame.getFitWidth() / 2.0);
		titleImageForFinishedGame.setLayoutY(0.0574 * height);
		
		titleLabelForFinishedGame.setPrefSize(0.5052 * width, 0.1111 * height);
		titleLabelForFinishedGame.setLayoutX(0.2479 * width);
		titleLabelForFinishedGame.setLayoutY(0.0807 * height);
		titleLabelForFinishedGame.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0495 * width));
		
		innerShadow.setOffsetX(0.0041 * width);
		innerShadow.setOffsetY(0.0041 * width);
		
		textForResultsDuration.setWrappingWidth(0.7813 * width);
		textForResultsDuration.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0260 * width));
		textForResultsDuration.setStyle("-fx-stroke: white; -fx-stroke-width: " + 0.0010 * width + ";");
		textForResultsDuration.setX(width / 2.0 - textForResultsDuration.getBoundsInLocal().getWidth() / 2.0);
		
		textForResults.setWrappingWidth(0.7813 * width);
		textForResults.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0260 * width));
		textForResults.setStyle("-fx-stroke: white; -fx-stroke-width: " + 0.0010 * width + ";");
		textForResults.setX(width / 2.0 - textForResults.getBoundsInLocal().getWidth() / 2.0);
		
		textForResults2.setWrappingWidth(0.7813 * width);
		textForResults2.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0260 * width));
		textForResults2.setStyle("-fx-stroke: white; -fx-stroke-width: " + 0.0010 * width + ";");
		textForResults2.setX(width / 2.0 - textForResults2.getBoundsInLocal().getWidth() / 2.0);
		
		if(gameMode == GAMEMODE.TIME_ATTACK_GAMEMODE)
		{
			textForResults.setLayoutY(0.3241 * height);
			textForResults2.setLayoutY(0.5185 * height);
		}
		else
		{
			textForResultsDuration.setLayoutY(0.2778 * height);
			textForResults.setLayoutY(0.4074 * height);
			textForResults2.setLayoutY(0.5417 * height);
		}
		
		restartGameFromFinishedScreenButton.setPrefSize(0.2188 * width, 0.0926 * height);
		restartGameFromFinishedScreenButton.setLayoutX(width / 2.0 - restartGameFromFinishedScreenButton.getPrefWidth() / 2.0);
		restartGameFromFinishedScreenButton.setLayoutY(0.6944 * height);
		restartGameFromFinishedScreenButton.setFont(fontForButtons);
		restartGameFromFinishedScreenButton.getTooltip().setFont(fontForTooltips);
		restartGameFromFinishedScreenButton.getTooltip().setMaxWidth(maxWidthForTooltips);
		
		returnToWelcomeScreenFromFinishedGameScreenButton.setPrefSize(0.3646 * width, 0.0926 * height);
		returnToWelcomeScreenFromFinishedGameScreenButton.setLayoutX(width / 2.0 - returnToWelcomeScreenFromFinishedGameScreenButton.getPrefWidth() / 2.0);
		returnToWelcomeScreenFromFinishedGameScreenButton.setLayoutY(0.8194 * height);
		returnToWelcomeScreenFromFinishedGameScreenButton.setFont(fontForButtons);
		returnToWelcomeScreenFromFinishedGameScreenButton.getTooltip().setFont(fontForTooltips);
		returnToWelcomeScreenFromFinishedGameScreenButton.getTooltip().setMaxWidth(maxWidthForTooltips);
		
		//DROP SHADOW
		dropShadowForNodes.setRadius(0.0104 * width);
		dropShadowForNodes.setOffsetX(-0.0052 * width);
		dropShadowForNodes.setOffsetY(-0.0052 * width);
		
		dropShadowForText.setRadius(0.0026 * width);
		dropShadowForText.setOffsetX(-0.0021 * width);
		dropShadowForText.setOffsetY(-0.0021 * width);
	}
	
	protected void recalculateBackground(double width, double height)
	{
		super.recalculateBackground(width, height);
		
		worldMapBackground.setLayoutX(getWorldMapLayoutX() * width);
		worldMapBackground.setLayoutY(getWorldMapLayoutY() * height);
		worldMapBackground.setFitWidth(getWorldMapFitWidth() * width);
		worldMapBackground.setFitHeight(getWorldMapFitHeight() * height);
		
		welcomeScreenImage.setLayoutX(0);
		welcomeScreenImage.setLayoutY(0);
		welcomeScreenImage.setFitWidth(width);
		welcomeScreenImage.setFitHeight(height);
	}
	
	protected void setScreenRatioDependentImages()
	{
		woodenFrameImage.setImage(FRAME_IMAGE);
		worldMapBackground.setImage(WORLD_MAP);
	}
	
	public GameScreen()
	{
		//FUNDAMENTAL STUFF-------------------------------------------------------------------------------------
		questionMaker = new QuestionMaker();
		answerEvaluator = new AnswerEvaluator();
		pr = PROGRESS_BAR_COLOR.getPixelReader();
		random = new Random();
		
		welcomeScreenImage = new CustomImageView(false, false, false, true, CacheHint.SPEED);
		welcomeScreenImage.setSmooth(false);
		welcomeScreenImage.setPreserveRatio(false);
		welcomeScreenImage.setCache(true);
		welcomeScreenImage.setCacheHint(CacheHint.SPEED);
		
		worldMapBackground = new CustomImageView(false, false, false, true, CacheHint.SPEED);
		worldMapBackground.setSmooth(false);
		worldMapBackground.setPreserveRatio(false);
		worldMapBackground.setCache(true);
		worldMapBackground.setCacheHint(CacheHint.SPEED);
		
		woodPanelFor5IconsImage = new CustomImageView(false, true, false, true, CacheHint.SPEED);
		woodPanelFor5IconsImage.setSmooth(false);
		woodPanelFor5IconsImage.setPreserveRatio(true);
		woodPanelFor5IconsImage.setCache(true);
		woodPanelFor5IconsImage.setCacheHint(CacheHint.SPEED);
		
		hBoxFor5Icons = new HBox();
		hBoxFor5Icons.setAlignment(Pos.CENTER);
		hBoxFor5Icons.setCache(true);
		hBoxFor5Icons.setCacheHint(CacheHint.SCALE);
		
		if (OS == OsCheck.OSType.Windows) hBoxFor5Icons.getChildren().addAll(soundIcon, minimizeIcon, moveIcon, fullScreenIcon, exitIcon);
		else hBoxFor5Icons.getChildren().addAll(exitIcon, fullScreenIcon, moveIcon, minimizeIcon, soundIcon);
		
		//MAIN GAME STUFF-------------------------------------------------------------------------------------
		
		//QUESTION NUMBER
		textForQuestionNumber = new Text();
		textForQuestionNumber.setTextAlignment(TextAlignment.LEFT);
		textForQuestionNumber.setFill(Color.valueOf("#7A301B"));
		textForQuestionNumber.setCache(true);
		textForQuestionNumber.setCacheHint(CacheHint.SCALE);
		
		numberOfQuestionAnimated = new Text();
		numberOfQuestionAnimated.setTextAlignment(TextAlignment.CENTER);
		numberOfQuestionAnimated.setFill(Color.valueOf("#7A301B"));
		
		//TIME PASSED
		textForTimePassed = new Text();
		textForTimePassed.setTextAlignment(TextAlignment.CENTER);
		textForTimePassed.setFill(Color.valueOf("#7A301B"));
		
		//HBOX FOR LIVES
		hBoxForLives = new HBox();
		hBoxForLives.setAlignment(Pos.CENTER_LEFT);
		
		imageViewForLives = new CustomImageView[5];
		for(int i = 0; i < 5; i++) imageViewForLives[i] = new CustomImageView(true, true, false, false, null);
		
		imageViewForLargeHeart = new CustomImageView(true, true, false, false, null);
		
		//10 SECONDS COUNTDOWN
		progressBarForCountdown = new ProgressBar();
		
		textForCountdown = new Text(" ");
		textForCountdown.setTextAlignment(TextAlignment.CENTER);
		textForCountdown.setFill(Color.valueOf("#7A301B"));
		
		//SCORE TEXT
		textForScore = new Text();
		textForScore.setTextAlignment(TextAlignment.CENTER);
		textForScore.setFill(Color.valueOf("#7A301B"));
		textForScore.setCache(true);
		textForScore.setCacheHint(CacheHint.SCALE);
		
		scoreAnimated = new Text();
		scoreAnimated.setTextAlignment(TextAlignment.CENTER);
		scoreAnimated.setFill(Color.valueOf("#7A301B"));
		
		//COMBO TEXT
		textForCombo = new Text();
		textForCombo.setTextAlignment(TextAlignment.LEFT);
		textForCombo.setFill(Color.valueOf("#7A301B"));
		
		comboAnimated = new Text();
		comboAnimated.setTextAlignment(TextAlignment.CENTER);
		comboAnimated.setFill(Color.valueOf("#7A301B"));
		
		//QUESTION TEXT
		textForQuestion = new Text();
		textForQuestion.setTextAlignment(TextAlignment.LEFT);
		textForQuestion.setFill(Color.valueOf("#7A301B"));
		textForQuestion.setCache(true);
		textForQuestion.setCacheHint(CacheHint.SCALE);
		
		//QUESTION IMAGE
		imageViewForQuestionImage = new CustomImageView(true, true, true, false, null);
		
		//IMAGE VIEW FOR LARGE ANSWER ICON
		imageViewForLargeAnswerIcon = new CustomImageView(true, true, false, false, null);
		
		//GRID PANE FOR 2 TEXT ANSWERS
		gridPaneFor2TextAnswers = new GridPane();
		gridPaneFor2TextAnswers.setAlignment(Pos.CENTER_LEFT);
		gridPaneFor2TextAnswers.setCache(true);
		gridPaneFor2TextAnswers.setCacheHint(CacheHint.SCALE);
		
		radioButtonsFor2TextAnswers = new RadioButton[2];
		radioButtonsFor2TextAnswers[0] = new RadioButton();
		radioButtonsFor2TextAnswers[0].setTextFill(Color.WHITE);
		radioButtonsFor2TextAnswers[0].setCursor(Cursor.HAND);
		
		radioButtonsFor2TextAnswers[1] = new RadioButton();
		radioButtonsFor2TextAnswers[1].setTextFill(Color.WHITE);
		radioButtonsFor2TextAnswers[1].setCursor(Cursor.HAND);
		
		toggleGroupFor2PossibleAnswers = new ToggleGroup();
		toggleGroupFor2PossibleAnswers.getToggles().addAll(radioButtonsFor2TextAnswers[0], radioButtonsFor2TextAnswers[1]);
		
		answerIconsFor2TextAnswers = new CustomImageView[2];
		answerIconsFor2TextAnswers[0] = new CustomImageView(true, true, false, false, null);
		answerIconsFor2TextAnswers[1] = new CustomImageView(true, true, false, false, null);
		
		gridPaneFor2TextAnswers.add(answerIconsFor2TextAnswers[0], 0, 0);
		gridPaneFor2TextAnswers.add(answerIconsFor2TextAnswers[1], 0, 1);
		gridPaneFor2TextAnswers.add(radioButtonsFor2TextAnswers[0], 1, 0);
		gridPaneFor2TextAnswers.add(radioButtonsFor2TextAnswers[1], 1, 1);
		
		//GRID PANE FOR 4 TEXT ANSWERS
		gridPaneFor4TextAnswers = new GridPane();
		gridPaneFor4TextAnswers.setAlignment(Pos.CENTER_LEFT);
		gridPaneFor4TextAnswers.setCache(true);
		gridPaneFor4TextAnswers.setCacheHint(CacheHint.SCALE);
		
		answerIconsFor4TextAnswers = new CustomImageView[4];
		radioButtonsFor4TextAnswers = new RadioButton[4];
		tooltipsForRadioButtonsFor4TextAnswers = new CustomTooltip[4];
		
		toggleGroupFor4PossibleAnswers = new ToggleGroup();
		
		for(int i = 0; i < 4; i++)
		{
			answerIconsFor4TextAnswers[i] = new CustomImageView(true, true, false, false, null);
			
			radioButtonsFor4TextAnswers[i] = new RadioButton();
			radioButtonsFor4TextAnswers[i].setTextFill(Color.WHITE);
			radioButtonsFor4TextAnswers[i].setCursor(Cursor.HAND);
			toggleGroupFor4PossibleAnswers.getToggles().add(radioButtonsFor4TextAnswers[i]);
			
			tooltipsForRadioButtonsFor4TextAnswers[i] = new CustomTooltip();
			
			gridPaneFor4TextAnswers.add(answerIconsFor4TextAnswers[i], 0, i);
			gridPaneFor4TextAnswers.add(radioButtonsFor4TextAnswers[i], 1, i);
		}
		
		//GRID PANE FOR 4 IMAGE ANSWERS
		gridPaneFor4ImageAnswers = new GridPane();
		gridPaneFor4ImageAnswers.setAlignment(Pos.CENTER);
		
		gridPaneForAnswerIconsFor4ImageAnswers = new GridPane();
		gridPaneForAnswerIconsFor4ImageAnswers.setAlignment(Pos.CENTER);
		gridPaneForAnswerIconsFor4ImageAnswers.setPickOnBounds(false);
		
		answerIconsFor4ImageAnswers = new CustomImageView[4];
		imageViewFor4ImageAnswers = new CustomImageView[4];
		
		for(int i = 0; i < 4; i++)
		{
			answerIconsFor4ImageAnswers[i] = new CustomImageView(true, true, false, false, null);
			
			imageViewFor4ImageAnswers[i] = new CustomImageView(true, true, true, false, null);
			imageViewFor4ImageAnswers[i].setCursor(Cursor.HAND);
			
			gridPaneFor4ImageAnswers.add(imageViewFor4ImageAnswers[i], i % 2, i / 2);
			gridPaneForAnswerIconsFor4ImageAnswers.add(answerIconsFor4ImageAnswers[i], i % 2, i / 2);
		}
		
		stackPaneFor4ImageAnswers = new StackPane();
		stackPaneFor4ImageAnswers.setAlignment(Pos.CENTER);
		stackPaneFor4ImageAnswers.getChildren().addAll(gridPaneFor4ImageAnswers, gridPaneForAnswerIconsFor4ImageAnswers);
		stackPaneFor4ImageAnswers.setCache(true);
		stackPaneFor4ImageAnswers.setCacheHint(CacheHint.SCALE);
		
		//BIG IMAGE
		imageViewForBigImage = new CustomImageView(true, true, false, false, null);
		
		stackPaneForBigImage = new StackPane();
		stackPaneForBigImage.getChildren().add(imageViewForBigImage);
		
		//NEXT BUTTON + PROGRESS BAR FOR NEXT BUTTON
		nextQuestionArrowImage = new CustomImageView(true, true, true, false, null);
		nextQuestionArrowImage.setRotate(180);
		
		nextQuestionButton = new CustomButton();
		nextQuestionButton.setStyle("-fx-background-color: transparent");
		nextQuestionButton.setGraphic(nextQuestionArrowImage);
		nextQuestionButton.setContentDisplay(ContentDisplay.TOP);
		nextQuestionButton.setTextAlignment(TextAlignment.CENTER);
		nextQuestionButton.setTextFill(Color.valueOf("#7A301B"));
		nextQuestionButton.setCursor(Cursor.HAND);
		nextQuestionButton.setCache(true);
		nextQuestionButton.setCacheHint(CacheHint.SCALE);
		
		progressBarFor2_5SecondsWaitForNextQuestion = new ProgressBar();
		progressBarFor2_5SecondsWaitForNextQuestion.setStyle("-fx-accent: #2ecc71;");
		
		vBoxForNextQuestionButtonAndProgressBar = new VBox();
		vBoxForNextQuestionButtonAndProgressBar.setAlignment(Pos.CENTER);
		vBoxForNextQuestionButtonAndProgressBar.getChildren().addAll(nextQuestionButton, progressBarFor2_5SecondsWaitForNextQuestion);
		vBoxForNextQuestionButtonAndProgressBar.setCache(true);
		vBoxForNextQuestionButtonAndProgressBar.setCacheHint(CacheHint.SCALE);
		
		//PAUSED GAME STUFF-------------------------------------------------------------------------------------
		pauseGameIcon = new CustomImageView(false, true, false, false, null);
		pauseGameIcon.setCursor(Cursor.HAND);
		
		pauseGameTooltip = new CustomTooltip();
		Tooltip.install(pauseGameIcon, pauseGameTooltip);
		
		popUpMessage = new Label();
		popUpMessage.setTextAlignment(TextAlignment.CENTER);
		popUpMessage.setTextFill(Color.WHITE);
		popUpMessage.setWrapText(true);
		
		returnToGameButton = new CustomButton();
		returnToGameButton.setTooltip(new CustomTooltip());
		
		restartGameButton = new CustomButton();
		restartGameButton.setTooltip(new CustomTooltip());
		
		returnToGamePropertiesButton = new CustomButton();
		returnToGamePropertiesButton.setTooltip(new CustomTooltip());
		
		returnToWelcomeScreenButton = new CustomButton();
		returnToWelcomeScreenButton.setTooltip(new CustomTooltip());
		
		vBoxForPausedGame = new VBox();
		vBoxForPausedGame.setAlignment(Pos.CENTER);
		vBoxForPausedGame.getChildren().addAll(returnToGameButton, restartGameButton, returnToGamePropertiesButton, returnToWelcomeScreenButton);
		vBoxForPausedGame.setCache(true);
		vBoxForPausedGame.setCacheHint(CacheHint.SPEED);
		
		//RESULTS SCREEN
		titleImageForFinishedGame = new CustomImageView(true, true, false, true, CacheHint.SPEED);
		
		titleLabelForFinishedGame = new Label();
		titleLabelForFinishedGame.setTextFill(Color.valueOf("#602000"));
		titleLabelForFinishedGame.setTextAlignment(TextAlignment.CENTER);
		titleLabelForFinishedGame.setAlignment(Pos.CENTER);
		titleLabelForFinishedGame.setCache(true);
		titleLabelForFinishedGame.setCacheHint(CacheHint.SCALE);
		
		textForResultsDuration = new Text();
		textForResultsDuration.setTextAlignment(TextAlignment.CENTER);
		textForResultsDuration.setFill(Color.valueOf("#7A301B"));
		textForResultsDuration.setCache(true);
		textForResultsDuration.setCacheHint(CacheHint.SCALE);
		
		textForResults = new Text();
		textForResults.setTextAlignment(TextAlignment.CENTER);
		textForResults.setFill(Color.valueOf("#7A301B"));
		textForResults.setCache(true);
		textForResults.setCacheHint(CacheHint.SCALE);
		
		textForResults2 = new Text();
		textForResults2.setTextAlignment(TextAlignment.CENTER);
		textForResults2.setFill(Color.valueOf("#7A301B"));
		textForResults2.setCache(true);
		textForResults2.setCacheHint(CacheHint.SCALE);
		
		restartGameFromFinishedScreenButton = new CustomButton();
		restartGameFromFinishedScreenButton.setTextAlignment(TextAlignment.CENTER);
		restartGameFromFinishedScreenButton.setTooltip(new CustomTooltip());
		
		returnToWelcomeScreenFromFinishedGameScreenButton = new CustomButton();
		returnToWelcomeScreenFromFinishedGameScreenButton.setTextAlignment(TextAlignment.CENTER);
		returnToWelcomeScreenFromFinishedGameScreenButton.setTooltip(new CustomTooltip());
		
		//EFFECTS-------------------------------------------------------------------------------------
		innerShadow = new InnerShadow();
		titleLabelForFinishedGame.setEffect(innerShadow);
		
		boxBlurForNodes = new BoxBlur();
		boxBlurForNodes.setWidth(20);
		boxBlurForNodes.setHeight(20);
		boxBlurForNodes.setIterations(2);
		
		dropShadowForNodes = new DropShadow();
		woodPanelFor5IconsImage.setEffect(dropShadowForNodes);
		titleImageForFinishedGame.setEffect(dropShadowForNodes);
		
		dropShadowForText = new DropShadow();
		boxBlurForText = new BoxBlur();
		boxBlurForText.setWidth(20);
		boxBlurForText.setHeight(20);
		boxBlurForText.setIterations(2);
		boxBlurForText.setInput(dropShadowForText);
		
		textForTimePassed.setEffect(dropShadowForText);
		textForQuestion.setEffect(dropShadowForText);
		textForQuestionNumber.setEffect(dropShadowForText);
		textForScore.setEffect(dropShadowForText);
		textForResultsDuration.setEffect(dropShadowForText);
		textForResults.setEffect(dropShadowForText);
		textForResults2.setEffect(dropShadowForText);
		textForCombo.setEffect(dropShadowForText);
		scoreAnimated.setEffect(dropShadowForText);
		comboAnimated.setEffect(dropShadowForText);
		
		nodesPane.getChildren().addAll(welcomeScreenImage, worldMapBackground, titleImageForFinishedGame, titleLabelForFinishedGame,
				woodPanelFor5IconsImage, hBoxFor5Icons, pauseGameIcon, textForQuestionNumber, progressBarForCountdown,
				vBoxForNextQuestionButtonAndProgressBar, textForScore, textForQuestion, imageViewForQuestionImage, gridPaneFor4TextAnswers,
				stackPaneFor4ImageAnswers, gridPaneFor2TextAnswers, textForTimePassed, textForCountdown, textForResultsDuration, textForCombo,
				hBoxForLives, stackPaneForBigImage, numberOfQuestionAnimated, imageViewForLargeAnswerIcon, scoreAnimated, comboAnimated,
				imageViewForLargeHeart, textForResults, textForResults2, restartGameFromFinishedScreenButton, returnToWelcomeScreenFromFinishedGameScreenButton,
				popUpMessage, vBoxForPausedGame, vBoxForSound);
		
		timelineToShowPopUpMessage = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					if(animationsUsed != ANIMATIONS.NO)
					{
						scaleTransitionForPopUpMessage.setToX(1);
						scaleTransitionForPopUpMessage.setToY(1);
						
						playPopUpSound();
						scaleTransitionForPopUpMessage.playFromStart();
					}
					else
					{
						popUpMessage.setScaleX(1);
						popUpMessage.setScaleY(1);
					}
				}),
				new KeyFrame(Duration.seconds(2.5), e ->
				{
					if(animationsUsed != ANIMATIONS.NO)
					{
						scaleTransitionForPopUpMessage.setToX(0);
						scaleTransitionForPopUpMessage.setToY(0);
						
						playMinimizeSound();
						scaleTransitionForPopUpMessage.playFromStart();
					}
					else
					{
						popUpMessage.setScaleX(0);
						popUpMessage.setScaleY(0);
					}
				}));
		
		setupListeners();
		
		if(animationsUsed != ANIMATIONS.NO)
		{
			setupLimitedAnimations();
		}
	}
	
	protected void setupListeners()
	{
		radioButtonsFor2TextAnswers[0].addEventHandler(ActionEvent.ACTION, answerEvaluator);
		radioButtonsFor2TextAnswers[1].addEventHandler(ActionEvent.ACTION, answerEvaluator);
		
		radioButtonsFor4TextAnswers[0].addEventHandler(ActionEvent.ACTION, answerEvaluator);
		radioButtonsFor4TextAnswers[1].addEventHandler(ActionEvent.ACTION, answerEvaluator);
		radioButtonsFor4TextAnswers[2].addEventHandler(ActionEvent.ACTION, answerEvaluator);
		radioButtonsFor4TextAnswers[3].addEventHandler(ActionEvent.ACTION, answerEvaluator);
		
		imageViewFor4ImageAnswers[0].addEventHandler(MouseEvent.MOUSE_CLICKED, answerEvaluator);
		imageViewFor4ImageAnswers[1].addEventHandler(MouseEvent.MOUSE_CLICKED, answerEvaluator);
		imageViewFor4ImageAnswers[2].addEventHandler(MouseEvent.MOUSE_CLICKED, answerEvaluator);
		imageViewFor4ImageAnswers[3].addEventHandler(MouseEvent.MOUSE_CLICKED, answerEvaluator);
		
		pauseGameIcon.setOnMousePressed(e -> pauseGameIcon.setImage(PAUSE_ICON_CLICKED));
		pauseGameIcon.setOnMouseReleased(e -> pauseGameIcon.setImage(PAUSE_ICON));
		pauseGameIcon.setOnMouseClicked(e ->
		{
			pauseGameIcon.setDisable(true);
			pauseGameIcon.setImage(PAUSE_ICON_CLICKED);
			
			openPauseMenu = true;
				
			timelineToShowPopUpMessage.playFromStart();
		});
		
		returnToGameButton.setOnMouseEntered(e -> playHoverSound());
		returnToGameButton.setOnAction(e ->
		{
			vBoxForPausedGame.setDisable(true);
			
			if(animationsUsed != ANIMATIONS.NO)
			{
				playButtonClickSound();
				
				translateTransitionFoVBoxForPausedGame.setToY(-1.0 * (vBoxForPausedGame.getLayoutY() + vBoxForPausedGame.getPrefHeight() + 20));
				translateTransitionFoVBoxForPausedGame.setOnFinished(ev ->
				{
					translateTransitionFoVBoxForPausedGame.setOnFinished(eve ->{});
					vBoxForPausedGame.setVisible(false);
					
					pauseGameIcon.setImage(PAUSE_ICON);
					
					if(getCombo() > 1) scaleTransitionForTextForCombo.play();
					
					removeBlurEffect();
					
					nextQuestionButton.setDisable(false);
					nextQuestionButton.fire();
				});
				
				playSlideSound();
				translateTransitionFoVBoxForPausedGame.playFromStart();
			}
			else
			{
				vBoxForPausedGame.setTranslateY(-1.0 * (vBoxForPausedGame.getLayoutY() + vBoxForPausedGame.getPrefHeight() + 20));
				vBoxForPausedGame.setVisible(false);
				
				pauseGameIcon.setImage(PAUSE_ICON);
				
				removeBlurEffect();
				
				nextQuestionButton.setDisable(false);
				nextQuestionButton.fire();
			}
		});
		
		restartGameButton.setOnMouseEntered(e -> playHoverSound());
		restartGameButton.setOnAction(e ->
		{
			playButtonClickSound();
			playRewindSound();
			
			if(animationsUsed != ANIMATIONS.NO)
			{
				translateTransitionFoVBoxForPausedGame.setToY(-1.0 * (vBoxForPausedGame.getLayoutY() + vBoxForPausedGame.getPrefHeight() + 20));
				
				translateTransitionFoVBoxForPausedGame.setOnFinished(ev ->
				{
					translateTransitionFoVBoxForPausedGame.setOnFinished(eve ->{});
					
					stopAllTimers();
					setInitialStateForAllNodes();
					
					timelineToShowAllStuff.playFromStart();
				});
				
				playSlideSound();
				translateTransitionFoVBoxForPausedGame.playFromStart();
			}
			else
			{
				stopAllTimers();
				setInitialStateForAllNodes();
			}
		});
		
		returnToGamePropertiesButton.setOnMouseEntered(e -> playHoverSound());
		returnToGamePropertiesButton.setOnAction(e ->
		{
			playButtonClickSound();
			
			returningToWelcomeScreen = false;
			
			removeBlurEffect();
			
			stopAllTimers();
			
			if(animationsUsed != ANIMATIONS.NO)
			{
				timelineToHideAllStuffFromGameScreen.setOnFinished(ev -> gamePropertiesScreen.showScreen(false));
				timelineToHideAllStuffFromGameScreen.playFromStart();
			}
			else gamePropertiesScreen.showScreen(false);
		});
		
		returnToWelcomeScreenButton.setOnMouseEntered(e -> playHoverSound());
		returnToWelcomeScreenButton.setOnAction(e ->
		{
			playButtonClickSound();
			
			returningToWelcomeScreen = true;
			
			stopAllTimers();
			
			if(animationsUsed != ANIMATIONS.NO)
			{
				timelineToHideAllStuffFromGameScreen.setOnFinished(ev -> {});
				timelineToHideAllStuffFromGameScreen.playFromStart();
			}
			else
			{
				welcomeScreen.showScreen(false);
				removeBlurEffect();
			}
		});
		
		nextQuestionButton.setOnMouseEntered(e -> playHoverSound());
		nextQuestionButton.setOnAction(e ->
		{
			playButtonClickSound();
			
			nextQuestionButton.setDisable(true);
			if(animationsUsed != ANIMATIONS.NO)
			{
				scaleTransitionForVBoxForNextQuestion.setToX(0);
				scaleTransitionForVBoxForNextQuestion.setToY(0);
				scaleTransitionForVBoxForNextQuestion.setOnFinished(ev ->
				{
					scaleTransitionForVBoxForNextQuestion.setOnFinished(eve -> {});
					
					vBoxForNextQuestionButtonAndProgressBar.setVisible(false);
				});
				scaleTransitionForVBoxForNextQuestion.playFromStart();
			}
			else vBoxForNextQuestionButtonAndProgressBar.setVisible(false);
			
			pauseGameIcon.setDisable(true);
			
			hideAnimations();
			
			if(gameMode == GAMEMODE.CLASSIC_GAMEMODE && getCurrentQuestionNumber() >= getNumberOfQuestionsForClassic() ||
			   gameMode == GAMEMODE.ENDLESS_GAMEMODE && getRemainingLivesForEndless() <= 0 || availableCategories.size() == 0) finishGame();
			else prepareForNextQuestion();
		});
		
		restartGameFromFinishedScreenButton.setOnMouseEntered(e -> playHoverSound());
		restartGameFromFinishedScreenButton.setOnAction(e ->
		{
			playButtonClickSound();
			playRewindSound();
			
			stopAllTimers();
			
			setInitialStateForAllNodes();
			
			if(animationsUsed != ANIMATIONS.NO) timelineToShowAllStuff.playFromStart();
		});
		
		returnToWelcomeScreenFromFinishedGameScreenButton.setOnMouseEntered(e -> playHoverSound());
		returnToWelcomeScreenFromFinishedGameScreenButton.setOnAction(e ->
		{
			playButtonClickSound();
			
			returningToWelcomeScreen = true;
			
			nextQuestionButton.setDisable(false);
			
			stopAllTimers();
			
			removeBlurEffect();
			
			if(animationsUsed != ANIMATIONS.NO)
			{
				timelineToHideAllStuffFromFinishedGameScreen.setOnFinished(ev -> {});
				timelineToHideAllStuffFromFinishedGameScreen.playFromStart();
			}
			else welcomeScreen.showScreen(false);
		});
		
		imageViewForQuestionImage.setOnMouseEntered(e ->
		{
			stackPaneForBigImage.setLayoutX(e.getSceneX() + 30);
			stackPaneForBigImage.setLayoutY(e.getSceneY() - imageViewForBigImage.getFitWidth() / 2.0);
			
			if(imageViewForQuestionImage.getImagePath().contains("coatOfArms") &&
			   (imageViewForQuestionImage.getImagePath().contains("Ινδία") || imageViewForQuestionImage.getImagePath().contains("Αμερικανική Σαμόα") ||
			    imageViewForQuestionImage.getImagePath().contains("Αυστρία") || imageViewForQuestionImage.getImagePath().contains("Ιράν") ||
			    imageViewForQuestionImage.getImagePath().contains("Καμερούν") || imageViewForQuestionImage.getImagePath().contains("Κένυα") ||
			    imageViewForQuestionImage.getImagePath().contains("Κομόρες") || imageViewForQuestionImage.getImagePath().contains("Κύπρος") ||
			    imageViewForQuestionImage.getImagePath().contains("Λεσότο") || imageViewForQuestionImage.getImagePath().contains("Λετονία") ||
			    imageViewForQuestionImage.getImagePath().contains("Νέα Καληδονία") || imageViewForQuestionImage.getImagePath().contains("Πακιστάν") ||
			    imageViewForQuestionImage.getImagePath().contains("Παλάου") || imageViewForQuestionImage.getImagePath().contains("Σουαζιλάνδη") ||
			    imageViewForQuestionImage.getImagePath().contains("Σουδάν") || imageViewForQuestionImage.getImagePath().contains("Νότια Αφρική")))
			{
				stackPaneForBigImage.setStyle(
						"-fx-background-color: #ffffffc7; -fx-border-color: black;" +
						"-fx-background-radius:" + 0.0208 * stage.getWidth() + ";" +
						"-fx-border-radius:" + 0.0208 * stage.getWidth() + ";" +
						"-fx-border-width:" + 0.0042 * stage.getWidth() + ";" +
						"-fx-padding:" + 0.0052 * stage.getWidth());
			}
			else
			{
				stackPaneForBigImage.setStyle(
						"-fx-background-color: #000000be; -fx-border-color: black;" +
						"-fx-background-radius:" + 0.0208 * stage.getWidth() + ";" +
						"-fx-border-radius:" + 0.0208 * stage.getWidth() + ";" +
						"-fx-border-width:" + 0.0042 * stage.getWidth() + ";" +
						"-fx-padding:" + 0.0052 * stage.getWidth());
			}
			
			String currentSize;
			if(imageViewForQuestionImage.getImagePath().contains("x250")) currentSize = "x250";
			else if(imageViewForQuestionImage.getImagePath().contains("x500")) currentSize = "x500";
			else currentSize = "x1000";
			
			if(imageViewForBigImage.getFitWidth() <= MAX_WIDTH_FOR_X250_IMAGES && currentSize.equals("x250") ||
			   imageViewForBigImage.getFitWidth() <= MAX_WIDTH_FOR_X500_IMAGES && currentSize.equals("x500") ||
			   imageViewForBigImage.getFitWidth() <= MAX_WIDTH_FOR_X1000_IMAGES && currentSize.equals("x1000"))
				imageViewForBigImage.setImage(imageViewForQuestionImage.getImage());
			else
			{
				String newSize;
				if(imageViewForBigImage.getFitWidth() <= MAX_WIDTH_FOR_X500_IMAGES) newSize = "x500";
				else newSize = "x1000";
				
				String newPath = imageViewForQuestionImage.getImagePath().replace(currentSize, newSize);
				if(!imageViewForBigImage.getImagePath().equals(newPath))
				{
					imageViewForBigImage.setImage(new Image(newPath));
					imageViewForBigImage.setImagePath(newPath);
				}
			}
			stackPaneForBigImage.setVisible(true);
		});
		
		imageViewForQuestionImage.setOnMouseMoved(e ->
		{
			stackPaneForBigImage.setLayoutX(e.getSceneX() + 30);
			stackPaneForBigImage.setLayoutY(e.getSceneY() - imageViewForBigImage.getFitWidth() / 2.0);
		});
		
		imageViewForQuestionImage.setOnMouseExited(e -> stackPaneForBigImage.setVisible(false));
		
		for(int i = 0; i < imageViewFor4ImageAnswers.length; i++)
		{
			int finalI = i;
			if(i < 2)
			{
				imageViewFor4ImageAnswers[i].setOnMouseEntered(e ->
				{
					stackPaneForBigImage.setLayoutX(e.getSceneX() + 30);
					stackPaneForBigImage.setLayoutY(e.getSceneY() - imageViewForBigImage.getFitWidth() / 2.0);
					
					if(imageViewForQuestionImage.getImagePath().contains("coatOfArms") &&
					   (imageViewForQuestionImage.getImagePath().contains("Ινδία") || imageViewForQuestionImage.getImagePath().contains("Αμερικανική Σαμόα") ||
					    imageViewForQuestionImage.getImagePath().contains("Αυστρία") || imageViewForQuestionImage.getImagePath().contains("Ιράν") ||
					    imageViewForQuestionImage.getImagePath().contains("Καμερούν") || imageViewForQuestionImage.getImagePath().contains("Κένυα") ||
					    imageViewForQuestionImage.getImagePath().contains("Κομόρες") || imageViewForQuestionImage.getImagePath().contains("Κύπρος") ||
					    imageViewForQuestionImage.getImagePath().contains("Λεσότο") || imageViewForQuestionImage.getImagePath().contains("Λετονία") ||
					    imageViewForQuestionImage.getImagePath().contains("Νέα Καληδονία") || imageViewForQuestionImage.getImagePath().contains("Πακιστάν") ||
					    imageViewForQuestionImage.getImagePath().contains("Παλάου") || imageViewForQuestionImage.getImagePath().contains("Σουαζιλάνδη") ||
					    imageViewForQuestionImage.getImagePath().contains("Σουδάν") || imageViewForQuestionImage.getImagePath().contains("Νότια Αφρική")))
					{
						stackPaneForBigImage.setStyle(
								"-fx-background-color: #ffffffc7; -fx-border-color: black;" +
								"-fx-background-radius:" + 0.0208 * stage.getWidth() + ";" +
								"-fx-border-radius:" + 0.0208 * stage.getWidth() + ";" +
								"-fx-border-width:" + 0.0042 * stage.getWidth() + ";" +
								"-fx-padding:" + 0.0052 * stage.getWidth());
					}
					else
					{
						stackPaneForBigImage.setStyle(
								"-fx-background-color: #000000be; -fx-border-color: black;" +
								"-fx-background-radius:" + 0.0208 * stage.getWidth() + ";" +
								"-fx-border-radius:" + 0.0208 * stage.getWidth() + ";" +
								"-fx-border-width:" + 0.0042 * stage.getWidth() + ";" +
								"-fx-padding:" + 0.0052 * stage.getWidth());
					}
					
					String currentSize;
					if(imageViewFor4ImageAnswers[finalI].getImagePath().contains("x250")) currentSize = "x250";
					else if(imageViewFor4ImageAnswers[finalI].getImagePath().contains("x500")) currentSize = "x500";
					else currentSize = "x1000";
					
					if(imageViewForBigImage.getFitWidth() <= MAX_WIDTH_FOR_X250_IMAGES && currentSize.equals("x250") ||
					   imageViewForBigImage.getFitWidth() <= MAX_WIDTH_FOR_X500_IMAGES && currentSize.equals("x500") ||
					   imageViewForBigImage.getFitWidth() <= MAX_WIDTH_FOR_X1000_IMAGES && currentSize.equals("x1000"))
						imageViewForBigImage.setImage(imageViewFor4ImageAnswers[finalI].getImage());
					else
					{
						String newSize;
						if(imageViewForBigImage.getFitWidth() <= MAX_WIDTH_FOR_X500_IMAGES) newSize = "x500";
						else newSize = "x1000";
						
						String newPath = imageViewFor4ImageAnswers[finalI].getImagePath().replace(currentSize, newSize);
						if(!imageViewForBigImage.getImagePath().equals(newPath))
						{
							imageViewForBigImage.setImage(new Image(newPath));
							imageViewForBigImage.setImagePath(newPath);
						}
					}
					stackPaneForBigImage.setVisible(true);
				});
				
				imageViewFor4ImageAnswers[i].setOnMouseMoved(e ->
				{
					stackPaneForBigImage.setLayoutX(e.getSceneX() + 30);
					stackPaneForBigImage.setLayoutY(e.getSceneY() - imageViewForBigImage.getFitWidth() / 2.0);
				});
			}
			else
			{
				imageViewFor4ImageAnswers[i].setOnMouseEntered(e ->
				{
					stackPaneForBigImage.setLayoutX(e.getSceneX() + 30);
					stackPaneForBigImage.setLayoutY(e.getSceneY() - imageViewForBigImage.getFitWidth() - 30);
					
					if(imageViewForQuestionImage.getImagePath().contains("coatOfArms") &&
					   (imageViewForQuestionImage.getImagePath().contains("Ινδία") || imageViewForQuestionImage.getImagePath().contains("Αμερικανική Σαμόα") ||
					    imageViewForQuestionImage.getImagePath().contains("Αυστρία") || imageViewForQuestionImage.getImagePath().contains("Ιράν") ||
					    imageViewForQuestionImage.getImagePath().contains("Καμερούν") || imageViewForQuestionImage.getImagePath().contains("Κένυα") ||
					    imageViewForQuestionImage.getImagePath().contains("Κομόρες") || imageViewForQuestionImage.getImagePath().contains("Κύπρος") ||
					    imageViewForQuestionImage.getImagePath().contains("Λεσότο") || imageViewForQuestionImage.getImagePath().contains("Λετονία") ||
					    imageViewForQuestionImage.getImagePath().contains("Νέα Καληδονία") || imageViewForQuestionImage.getImagePath().contains("Πακιστάν") ||
					    imageViewForQuestionImage.getImagePath().contains("Παλάου") || imageViewForQuestionImage.getImagePath().contains("Σουαζιλάνδη") ||
					    imageViewForQuestionImage.getImagePath().contains("Σουδάν") || imageViewForQuestionImage.getImagePath().contains("Νότια Αφρική")))
					{
						stackPaneForBigImage.setStyle(
								"-fx-background-color: #ffffffc7; -fx-border-color: black;" +
								"-fx-background-radius:" + 0.0208 * stage.getWidth() + ";" +
								"-fx-border-radius:" + 0.0208 * stage.getWidth() + ";" +
								"-fx-border-width:" + 0.0042 * stage.getWidth() + ";" +
								"-fx-padding:" + 0.0052 * stage.getWidth());
					}
					else
					{
						stackPaneForBigImage.setStyle(
								"-fx-background-color: #000000be; -fx-border-color: black;" +
								"-fx-background-radius:" + 0.0208 * stage.getWidth() + ";" +
								"-fx-border-radius:" + 0.0208 * stage.getWidth() + ";" +
								"-fx-border-width:" + 0.0042 * stage.getWidth() + ";" +
								"-fx-padding:" + 0.0052 * stage.getWidth());
					}
					
					String currentSize;
					if(imageViewFor4ImageAnswers[finalI].getImagePath().contains("x250")) currentSize = "x250";
					else if(imageViewFor4ImageAnswers[finalI].getImagePath().contains("x500")) currentSize = "x500";
					else currentSize = "x1000";
					
					if(imageViewForBigImage.getFitWidth() <= MAX_WIDTH_FOR_X250_IMAGES && currentSize.equals("x250") ||
					   imageViewForBigImage.getFitWidth() <= MAX_WIDTH_FOR_X500_IMAGES && currentSize.equals("x500") ||
					   imageViewForBigImage.getFitWidth() <= MAX_WIDTH_FOR_X1000_IMAGES && currentSize.equals("x1000"))
						imageViewForBigImage.setImage(imageViewFor4ImageAnswers[finalI].getImage());
					else
					{
						String newSize;
						if(imageViewForBigImage.getFitWidth() <= MAX_WIDTH_FOR_X500_IMAGES) newSize = "x500";
						else newSize = "x1000";
						
						String newPath = imageViewFor4ImageAnswers[finalI].getImagePath().replace(currentSize, newSize);
						if(!imageViewForBigImage.getImagePath().equals(newPath))
						{
							imageViewForBigImage.setImage(new Image(newPath));
							imageViewForBigImage.setImagePath(newPath);
						}
					}
					stackPaneForBigImage.setVisible(true);
				});
				
				imageViewFor4ImageAnswers[i].setOnMouseMoved(e ->
				{
					stackPaneForBigImage.setLayoutX(e.getSceneX() + 30);
					stackPaneForBigImage.setLayoutY(e.getSceneY() - imageViewForBigImage.getFitWidth() - 30);
				});
			}
			
			imageViewFor4ImageAnswers[i].setOnMouseExited(e -> stackPaneForBigImage.setVisible(false));
		}
		
		soundIcon.setOnMouseClicked(e ->
		{
			soundIcon.setDisable(true);
			
			if (!vBoxForSound.isVisible())
			{
				setSoundIcon(soundIcon, true);
				
				if(animationsUsed != ANIMATIONS.NO) timelineToShowSoundOptions.play();
				else
				{
					if(OS == OsCheck.OSType.Windows)
					{
						textForScore.setTranslateY(0.2065 * stage.getHeight());
						textForCombo.setTranslateY(0.2065 * stage.getHeight());
					}
					
					vBoxForSound.setTranslateX(0);
					vBoxForSound.setVisible(true);
				}
			}
			else
			{
				setSoundIcon(soundIcon, false);
				
				if(animationsUsed != ANIMATIONS.NO) timelineToHideSoundOptions.play();
				else
				{
					if (OS == OsCheck.OSType.Windows) vBoxForSound.setTranslateX(stage.getWidth() - vBoxForSound.getLayoutX() + 20);
					else vBoxForSound.setTranslateX(-1.0 * (vBoxForSound.getLayoutX() + vBoxForSound.getPrefWidth() + 20));
					
					vBoxForSound.setVisible(false);
					
					if(OS == OsCheck.OSType.Windows)
					{
						textForScore.setTranslateY(0);
						textForCombo.setTranslateY(0);
					}
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
	}
	
	private void hideAnimations()
	{
		if(animationsUsed != ANIMATIONS.NO)
		{
			timelineForScoreAnimated.stop();
			timelineForScoreAnimated.playFrom(Duration.millis(2100));
			
			if(getCombo() > 1)
			{
				timelineForComboAnimated.stop();
				timelineForComboAnimated.playFrom(Duration.millis(2100));
			}
		}
		else
		{
			timelineForScoreNoAnimated.stop();
			timelineForScoreNoAnimated.playFrom(Duration.millis(2450));
			
			if(getCombo() > 1)
			{
				timelineForComboNoAnimated.stop();
				timelineForComboNoAnimated.playFrom(Duration.millis(2450));
			}
		}
	}
	
	private void setupTimers()
	{
		timelineToCountMilliseconds = new Timeline(
				new KeyFrame(Duration.millis(10), e -> setMillisecondsPassed(getMillisecondsPassed() + 10)));
		timelineToCountMilliseconds.setCycleCount(Animation.INDEFINITE);
		
		if(animationsUsed != ANIMATIONS.NO)
		{
			timelineToShowNumberOfQuestion = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				numberOfQuestionAnimated.setText(String.valueOf(getCurrentQuestionNumber()));
				
				scaleTransitionForNumberOfQuestionAnimated.setToX(6);
				scaleTransitionForNumberOfQuestionAnimated.setToY(6);
				
				translateTransitionForNumberOfQuestionAnimated.setToX(0);
				translateTransitionForNumberOfQuestionAnimated.setToY(0);
				
				parallelTransitionForNumberOfQuestionAnimated.playFromStart();
			}), new KeyFrame(Duration.millis(350), e ->
			{
				scaleTransitionForNumberOfQuestionAnimated.setToX(1);
				scaleTransitionForNumberOfQuestionAnimated.setToY(1);
				
				translateTransitionForNumberOfQuestionAnimated.setToX(textForQuestionNumber.getLayoutX() - numberOfQuestionAnimated.getLayoutX());
				translateTransitionForNumberOfQuestionAnimated.setToY(textForQuestionNumber.getLayoutY() - numberOfQuestionAnimated.getLayoutY());
				
				parallelTransitionForNumberOfQuestionAnimated.playFromStart();
			}),
			new KeyFrame(Duration.millis(700), e -> updateQuestionNumberText()),
			new KeyFrame(Duration.millis(750), e ->
			{
				parallelTransitionForNumberOfQuestionAnimated.stop();
				numberOfQuestionAnimated.setScaleX(0);
				numberOfQuestionAnimated.setScaleY(0);
				numberOfQuestionAnimated.setTranslateX(0);
				numberOfQuestionAnimated.setTranslateY(0);
			}));
			
			timelineForLargeAnswerIcon = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				scaleTransitionForLargeAnswerIcon.setToX(10);
				scaleTransitionForLargeAnswerIcon.setToY(10);
				
				translateTransitionForLargeAnswerIcon.setToX(0);
				translateTransitionForLargeAnswerIcon.setToY(0);
				
				parallelTransitionForLargeAnswerIcon.playFromStart();
			}),
			new KeyFrame(Duration.millis(350), e ->
			{
				scaleTransitionForLargeAnswerIcon.setToX(1);
				scaleTransitionForLargeAnswerIcon.setToY(1);
				
				double x = 0, y = 0;
				if (gridPaneFor2TextAnswers.isVisible())
				{
					int index;
					if (imageViewForLargeAnswerIcon.getImage().equals(CORRECT_ICON_BIG)) index = questionMaker.getPositionOfCorrectAnswer();
					else index = 1 - questionMaker.getPositionOfCorrectAnswer();
					
					x = answerIconsFor2TextAnswers[index].localToScene(answerIconsFor2TextAnswers[index].getBoundsInLocal()).getMinX() - imageViewForLargeAnswerIcon.getLayoutX();
					y = answerIconsFor2TextAnswers[index].localToScene(answerIconsFor2TextAnswers[index].getBoundsInLocal()).getMinY() -
					    answerIconsFor2TextAnswers[index].getFitWidth() / 2.0 - imageViewForLargeAnswerIcon.getLayoutY();
				}
				else if (gridPaneFor4TextAnswers.isVisible())
				{
					int index;
					if (imageViewForLargeAnswerIcon.getImage().equals(CORRECT_ICON_BIG)) index = questionMaker.getPositionOfCorrectAnswer();
					else
					{
						if (radioButtonsFor4TextAnswers[0].isSelected()) index = 0;
						else if (radioButtonsFor4TextAnswers[1].isSelected()) index = 1;
						else if (radioButtonsFor4TextAnswers[2].isSelected()) index = 2;
						else index = 3;
					}
					
					x = answerIconsFor4TextAnswers[index].localToScene(answerIconsFor4TextAnswers[index].getBoundsInLocal()).getMinX() - imageViewForLargeAnswerIcon.getLayoutX();
					y = answerIconsFor4TextAnswers[index].localToScene(answerIconsFor4TextAnswers[index].getBoundsInLocal()).getMinY() -
					    answerIconsFor4TextAnswers[index].getFitWidth() / 2.0 - imageViewForLargeAnswerIcon.getLayoutY();
				}
				
				translateTransitionForLargeAnswerIcon.setToX(x);
				translateTransitionForLargeAnswerIcon.setToY(y);
				
				parallelTransitionForLargeAnswerIcon.playFromStart();
			}),
			new KeyFrame(Duration.millis(750), e -> {}));
			
			timelineForComboAnimated = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					scaleTransitionForComboAnimated.setToX(2);
					scaleTransitionForComboAnimated.setToY(2);
					
					translateTransitionForComboAnimated.setToX(0);
					translateTransitionForComboAnimated.setToY(0);
					
					parallelTransitionForComboAnimated.playFromStart();
				}),
				new KeyFrame(Duration.millis(2100), e ->
				{
					scaleTransitionForComboAnimated.setToX(0);
					scaleTransitionForComboAnimated.setToY(0);
					
					Bounds bounds = textForCombo.localToScene(textForCombo.getBoundsInLocal());
					double x      = bounds.getMinX() - comboAnimated.getBoundsInLocal().getWidth() / 2.0 - comboAnimated.getLayoutX();
					double y      = bounds.getMinY() + bounds.getHeight() - comboAnimated.getLayoutY();
					
					translateTransitionForComboAnimated.setToX(x);
					translateTransitionForComboAnimated.setToY(y);
					
					parallelTransitionForComboAnimated.playFromStart();
				}),
				new KeyFrame(Duration.millis(2350), e ->
				{
					textForCombo.setText("x" + getCombo());
					textForCombo.setLayoutX(textForScore.getLayoutX() + textForScore.getBoundsInLocal().getWidth() / 2.0 - textForCombo.getBoundsInLocal().getWidth() / 2.0);
					startTextForComboAnimation(getCombo());
				}),
				new KeyFrame(Duration.millis(2450), e ->
				{
					parallelTransitionForComboAnimated.stop();
					comboAnimated.setScaleX(0);
					comboAnimated.setScaleY(0);
					comboAnimated.setTranslateX(0);
					comboAnimated.setTranslateY(0);
				}));
			
			timelineForScoreAnimated = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					worldMapBackground.setEffect(boxBlurForNodes);
					if(hBoxForLives.isVisible()) hBoxForLives.setEffect(boxBlurForNodes);
					if(progressBarForCountdown.isVisible()) progressBarForCountdown.setEffect(boxBlurForNodes);
					if(imageViewForQuestionImage.isVisible()) imageViewForQuestionImage.setEffect(boxBlurForNodes);
					textForCountdown.setEffect(boxBlurForNodes);
					pauseGameIcon.setEffect(boxBlurForNodes);
					if(textForTimePassed.isVisible()) textForTimePassed.setEffect(boxBlurForText);
					textForQuestion.setEffect(boxBlurForText);
					textForQuestionNumber.setEffect(boxBlurForText);
					textForScore.setEffect(boxBlurForText);
					textForCombo.setEffect(boxBlurForText);
					
					if (getCombo() < 2) scoreAnimated.setLayoutY(stage.getHeight() / 2.0 + scoreAnimated.getLayoutBounds().getHeight() / 2.0);
					else scoreAnimated.setLayoutY(0.3704 * stage.getHeight());
					
					scaleTransitionForScoreAnimated.setToX(2);
					scaleTransitionForScoreAnimated.setToY(2);
					
					translateTransitionForScoreAnimated.setToX(0);
					translateTransitionForScoreAnimated.setToY(0);
					
					parallelTransitionForScoreAnimated.playFromStart();
				}),
				new KeyFrame(Duration.millis(2100), e ->
				{
					removeBlurEffect();
					
					scaleTransitionForScoreAnimated.setToX(0);
					scaleTransitionForScoreAnimated.setToY(0);
					
					Bounds bounds = textForScore.localToScene(textForScore.getBoundsInLocal());
					double x      = bounds.getMinX() - scoreAnimated.getBoundsInLocal().getWidth() / 2.0 - scoreAnimated.getLayoutX();
					double y      = bounds.getMinY() + bounds.getHeight() - scoreAnimated.getLayoutY();
					
					translateTransitionForScoreAnimated.setToX(x);
					translateTransitionForScoreAnimated.setToY(y);
					
					parallelTransitionForScoreAnimated.playFromStart();
				}),
				new KeyFrame(Duration.millis(2350), e -> updateScoreText()),
				new KeyFrame(Duration.millis(2450), e ->
				{
					parallelTransitionForScoreAnimated.stop();
					scoreAnimated.setScaleX(0);
					scoreAnimated.setScaleY(0);
					scoreAnimated.setTranslateX(0);
					scoreAnimated.setTranslateY(0);
					
					if(openPauseMenu) showPauseMenu();
				}));
		}
		else
		{
			timelineForScoreNoAnimated = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					worldMapBackground.setEffect(boxBlurForNodes);
					if(hBoxForLives.isVisible()) hBoxForLives.setEffect(boxBlurForNodes);
					if(progressBarForCountdown.isVisible()) progressBarForCountdown.setEffect(boxBlurForNodes);
					if(imageViewForQuestionImage.isVisible()) imageViewForQuestionImage.setEffect(boxBlurForNodes);
					textForCountdown.setEffect(boxBlurForNodes);
					pauseGameIcon.setEffect(boxBlurForNodes);
					if(textForTimePassed.isVisible()) textForTimePassed.setEffect(boxBlurForText);
					textForQuestion.setEffect(boxBlurForText);
					textForQuestionNumber.setEffect(boxBlurForText);
					textForScore.setEffect(boxBlurForText);
					textForCombo.setEffect(boxBlurForText);
					
					if (getCombo() < 2) scoreAnimated.setLayoutY(stage.getHeight() / 2.0 + scoreAnimated.getLayoutBounds().getHeight() / 2.0);
					else scoreAnimated.setLayoutY(0.3704 * stage.getHeight());
					
					scoreAnimated.setVisible(true);
				}),
				new KeyFrame(Duration.millis(2450), e ->
				{
					removeBlurEffect();
					
					updateScoreText();
					scoreAnimated.setVisible(false);
					
					if(openPauseMenu) showPauseMenu();
				}));
		
			timelineForComboNoAnimated = new Timeline(
				new KeyFrame(Duration.millis(0), e -> comboAnimated.setVisible(true)),
				new KeyFrame(Duration.millis(2450), e ->
				{
					textForCombo.setText("x" + getCombo());
					textForCombo.setLayoutX(textForScore.getLayoutX() + textForScore.getBoundsInLocal().getWidth() / 2.0 - textForCombo.getBoundsInLocal().getWidth() / 2.0);
					textForCombo.setScaleX(1);
					textForCombo.setScaleY(1);
					textForCombo.setVisible(true);
					
					comboAnimated.setVisible(false);
				}));
		}
		
		timelineForLifeLostAnimation = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				worldMapBackground.setEffect(boxBlurForNodes);
				if(hBoxForLives.isVisible()) hBoxForLives.setEffect(boxBlurForNodes);
				if(progressBarForCountdown.isVisible()) progressBarForCountdown.setEffect(boxBlurForNodes);
				if(imageViewForQuestionImage.isVisible()) imageViewForQuestionImage.setEffect(boxBlurForNodes);
				textForCountdown.setEffect(boxBlurForNodes);
				if(textForTimePassed.isVisible()) textForTimePassed.setEffect(boxBlurForText);
				textForQuestion.setEffect(boxBlurForText);
				textForQuestionNumber.setEffect(boxBlurForText);
				textForScore.setEffect(boxBlurForText);
				textForCombo.setEffect(boxBlurForText);
				
				imageViewForLargeHeart.setImage(HEART_ICON_BIG);
				
				if(animationsUsed != ANIMATIONS.NO)
				{
					scaleTransitionForImageViewForLargeHeart.setToX(10);
					scaleTransitionForImageViewForLargeHeart.setToY(10);
					
					translateTransitionForImageViewForLargeHeart.setToX(0);
					translateTransitionForImageViewForLargeHeart.setToY(0);
					
					parallelTransitionForLifeLostAnimation.playFromStart();
				}
				else imageViewForLargeHeart.setVisible(true);
			}),
			new KeyFrame(Duration.millis(500), e ->
			{
				playHeartBreakingSound();
				
				imageViewForLargeHeart.setImage(HEART_ICON_BROKEN_BIG);
			}),
			new KeyFrame(Duration.millis(900), e ->
			{
				removeBlurEffect();
				
				if(animationsUsed != ANIMATIONS.NO)
				{
					scaleTransitionForImageViewForLargeHeart.setToX(1);
					scaleTransitionForImageViewForLargeHeart.setToY(1);
					
					double x = imageViewForLives[getRemainingLivesForEndless()].localToScene(imageViewForLives[getRemainingLivesForEndless()].getBoundsInLocal()).getMinX()
					           - imageViewForLargeHeart.getLayoutX();
					double y = imageViewForLives[getRemainingLivesForEndless()].localToScene(imageViewForLives[getRemainingLivesForEndless()].getBoundsInLocal()).getMinY()
					           - imageViewForLargeHeart.getLayoutY();
					
					translateTransitionForImageViewForLargeHeart.setToX(x);
					translateTransitionForImageViewForLargeHeart.setToY(y);
					
					parallelTransitionForLifeLostAnimation.playFromStart();
				}
			}),
			new KeyFrame(Duration.millis(1300), e ->
			{
				if(animationsUsed != ANIMATIONS.NO)
				{
					imageViewForLives[getRemainingLivesForEndless()].setImage(HEART_ICON_LOST_SMALL);
					
					parallelTransitionForLifeLostAnimation.stop();
					imageViewForLargeHeart.setScaleX(0);
					imageViewForLargeHeart.setScaleY(0);
					imageViewForLargeHeart.setTranslateX(0);
					imageViewForLargeHeart.setTranslateY(0);
				}
				else imageViewForLargeHeart.setVisible(false);
			}));
		
		if(timelineFor2_5SecondsWaitForNextQuestion == null)
		{
			timelineFor2_5SecondsWaitForNextQuestion = new Timeline(
				new KeyFrame(Duration.millis(0), e -> progressBarFor2_5SecondsWaitForNextQuestion.setProgress(getProgressFor2_5SecondsWait() / 8500.0)),
				new KeyFrame(Duration.millis(50), e ->
				{
//					timelineFor2_5SecondsWaitForNextQuestion.stop();
					setProgressFor2_5SecondsWait(getProgressFor2_5SecondsWait() + 150);
					if (getProgressFor2_5SecondsWait() >= 8500)
					{
						stopTimelineFor2_5SecondsWait();
						
						nextQuestionButton.fire();
					}
				}));
			timelineFor2_5SecondsWaitForNextQuestion.setCycleCount(Animation.INDEFINITE);
		}
		
		if ((gameMode == GAMEMODE.CLASSIC_GAMEMODE || gameMode == GAMEMODE.ENDLESS_GAMEMODE) && timelineForTimePassedCount == null)
		{
			timelineForTimePassedCount = new Timeline(
				new KeyFrame(Duration.millis(0), e -> setReadableTimePassed()),
				new KeyFrame(Duration.seconds(1), e -> setSecondsPassed(getSecondsPassed() + 1)));
			timelineForTimePassedCount.setCycleCount(Animation.INDEFINITE);
		}
		
		if (gameMode == GAMEMODE.TIME_ATTACK_GAMEMODE && getDurationInMinutesForTimeAttack() == 1 && timelineFor1MinuteProgressbarForTimeAttack == null)
		{
			timelineFor1MinuteProgressbarForTimeAttack = new Timeline(new KeyFrame(Duration.millis(0), e ->
			{
				progressBarForCountdown.setProgress(getCurrentProgressForProgressbar() / 10000.0);
				int x = (10000 - getCurrentProgressForProgressbar()) / 10;
				if (x < 1000) progressBarForCountdown.setStyle("-fx-accent: #" + pr.getColor(x, 0).toString().replace("0x", "") + ";");
			}), new KeyFrame(Duration.millis(150), e ->
			{
				setCurrentProgressForProgressbar(getCurrentProgressForProgressbar() - 25);
				if (getCurrentProgressForProgressbar() < 0)
				{
					progressBarForCountdown.setId("red");
					progressBarForCountdown.setProgress(0);
					timelineFor1MinuteProgressbarForTimeAttack.stop();
				}
			}));
			timelineFor1MinuteProgressbarForTimeAttack.setCycleCount(Animation.INDEFINITE);
		}
		else if (gameMode == GAMEMODE.TIME_ATTACK_GAMEMODE && getDurationInMinutesForTimeAttack() == 2 && timelineFor2MinutesProgressbarForTimeAttack == null)
		{
			timelineFor2MinutesProgressbarForTimeAttack = new Timeline(new KeyFrame(Duration.millis(0), e ->
			{
				progressBarForCountdown.setProgress(getCurrentProgressForProgressbar() / 10000.0);
				int x = (10000 - getCurrentProgressForProgressbar()) / 10;
				if (x < 1000) progressBarForCountdown.setStyle("-fx-accent: #" + pr.getColor(x, 0).toString().replace("0x", "") + ";");
			}), new KeyFrame(Duration.millis(300), e ->
			{
				setCurrentProgressForProgressbar(getCurrentProgressForProgressbar() - 25);
				if (getCurrentProgressForProgressbar() < 0)
				{
					progressBarForCountdown.setId("red");
					progressBarForCountdown.setProgress(0);
					timelineFor2MinutesProgressbarForTimeAttack.stop();
				}
			}));
			timelineFor2MinutesProgressbarForTimeAttack.setCycleCount(Animation.INDEFINITE);
		}
		else if (gameMode == GAMEMODE.TIME_ATTACK_GAMEMODE && getDurationInMinutesForTimeAttack() == 5 && timelineFor5MinutesProgressbarForTimeAttack == null)
		{
			timelineFor5MinutesProgressbarForTimeAttack = new Timeline(new KeyFrame(Duration.millis(0), e ->
			{
				progressBarForCountdown.setProgress(getCurrentProgressForProgressbar() / 10000.0);
				int x = (10000 - getCurrentProgressForProgressbar()) / 10;
				if (x < 1000) progressBarForCountdown.setStyle("-fx-accent: #" + pr.getColor(x, 0).toString().replace("0x", "") + ";");
			}), new KeyFrame(Duration.millis(750), e ->
			{
				setCurrentProgressForProgressbar(getCurrentProgressForProgressbar() - 25);
				if (getCurrentProgressForProgressbar() < 0)
				{
					progressBarForCountdown.setId("red");
					progressBarForCountdown.setProgress(0);
					timelineFor5MinutesProgressbarForTimeAttack.stop();
				}
			}));
			timelineFor5MinutesProgressbarForTimeAttack.setCycleCount(Animation.INDEFINITE);
		}
		else if (gameMode == GAMEMODE.TIME_ATTACK_GAMEMODE && getDurationInMinutesForTimeAttack() == 10 && timelineFor10MinutesProgressbarForTimeAttack == null)
		{
			timelineFor10MinutesProgressbarForTimeAttack = new Timeline(new KeyFrame(Duration.millis(0), e ->
			{
				progressBarForCountdown.setProgress(getCurrentProgressForProgressbar() / 10000.0);
				int x = (10000 - getCurrentProgressForProgressbar()) / 10;
				if (x < 1000) progressBarForCountdown.setStyle("-fx-accent: #" + pr.getColor(x, 0).toString().replace("0x", "") + ";");
			}), new KeyFrame(Duration.millis(1500), e ->
			{
				setCurrentProgressForProgressbar(getCurrentProgressForProgressbar() - 25);
				if (getCurrentProgressForProgressbar() < 0)
				{
					progressBarForCountdown.setId("red");
					progressBarForCountdown.setProgress(0);
					timelineFor10MinutesProgressbarForTimeAttack.stop();
				}
			}));
			timelineFor10MinutesProgressbarForTimeAttack.setCycleCount(Animation.INDEFINITE);
		}
		
		if (gameMode == GAMEMODE.TIME_ATTACK_GAMEMODE && timelineToCountSecondsForTimeAttack == null)
		{
			timelineToCountSecondsForTimeAttack = new Timeline(new KeyFrame(Duration.millis(0), e ->
			{
				setReadableRemainingTimeForTimeAttack();
				
				if ((getDurationInMinutesForTimeAttack() == 1 || getDurationInMinutesForTimeAttack() == 2) && getRemainingSecondsForTimeAttack() <= 15 ||
				    (getDurationInMinutesForTimeAttack() == 5 || getDurationInMinutesForTimeAttack() == 10) && getRemainingSecondsForTimeAttack() <= 30)
				{
					if (getRemainingSecondsForTimeAttack() <= 15) playPopUpSound();
					
					if((getDurationInMinutesForTimeAttack() == 1 || getDurationInMinutesForTimeAttack() == 2) && getRemainingSecondsForTimeAttack() == 15 ||
					   (getDurationInMinutesForTimeAttack() == 5 || getDurationInMinutesForTimeAttack() == 10) && getRemainingSecondsForTimeAttack() == 30)
							playClockTickingSound();
					
					if (animationsUsed != ANIMATIONS.NO)
					{
						scaleTransitionForTextForCountdown.setToX(1.5);
						scaleTransitionForTextForCountdown.setToY(1.5);
						
						scaleTransitionForTextForCountdown.playFromStart();
					}
				}
			}), new KeyFrame(Duration.millis(500), e ->
			{
				if (animationsUsed != ANIMATIONS.NO && ((getDurationInMinutesForTimeAttack() == 1 || getDurationInMinutesForTimeAttack() == 2) && getRemainingSecondsForTimeAttack() <= 16 ||
				                                        (getDurationInMinutesForTimeAttack() == 5 || getDurationInMinutesForTimeAttack() == 10) && getRemainingSecondsForTimeAttack() <= 31))
				{
					scaleTransitionForTextForCountdown.setToX(0);
					scaleTransitionForTextForCountdown.setToY(0);
					
					scaleTransitionForTextForCountdown.playFromStart();
				}
				else if (animationsUsed == ANIMATIONS.NO && getRemainingSecondsForTimeAttack() == 0)
				{
					textForCountdown.setScaleX(0);
					textForCountdown.setScaleY(0);
				}
			}), new KeyFrame(Duration.seconds(1), e ->
			{
				setRemainingSecondsForTimeAttack(getRemainingSecondsForTimeAttack() - 1);
				if (getRemainingSecondsForTimeAttack() < 0)
				{
					timelineToCountSecondsForTimeAttack.stop();
					stopClockTickingSound();
					playTimeOverSound();
					
					setFunctional2TextAnswers(false);
					setFunctional4TextAnswers(false);
					setFunctional4ImageAnswers(false);
					
					new Timeline(new KeyFrame(Duration.seconds(1), ev -> finishGame())).play();
				}
			}));
			timelineToCountSecondsForTimeAttack.setCycleCount(Animation.INDEFINITE);
		}
		
		if(isQuestionCountdownEnabled() && timelineToCountSecondsForQuestionCountdown == null)
		{
			timelineToCountSecondsForQuestionCountdown = new Timeline(new KeyFrame(Duration.millis(0), e ->
			{
				int currentSecond = (int) Math.ceil(getCurrentProgressForProgressbar() / 100.0);
				
				textForCountdown.setText(String.valueOf(currentSecond));
				textForCountdown.setLayoutX(stage.getWidth() / 2.0 - textForCountdown.getBoundsInLocal().getWidth() / 2.0);
				
				if (currentSecond <= 3) playPopUpSound();
				
				if (animationsUsed != ANIMATIONS.NO)
				{
					if (currentSecond <= 3)
					{
						scaleTransitionForTextForCountdown.setToX(2);
						scaleTransitionForTextForCountdown.setToY(2);
					}
					else
					{
						scaleTransitionForTextForCountdown.setToX(1);
						scaleTransitionForTextForCountdown.setToY(1);
					}
					
					scaleTransitionForTextForCountdown.playFromStart();
				}
			}), new KeyFrame(Duration.millis(500), e ->
			{
				if (animationsUsed != ANIMATIONS.NO)
				{
					scaleTransitionForTextForCountdown.setToX(0);
					scaleTransitionForTextForCountdown.setToY(0);
					
					scaleTransitionForTextForCountdown.playFromStart();
				}
				else if (animationsUsed == ANIMATIONS.NO && textForCountdown.getText().equals("0"))
				{
					textForCountdown.setScaleX(0);
					textForCountdown.setScaleY(0);
				}
			}), new KeyFrame(Duration.seconds(1), e ->
			{
				if (textForCountdown.getText().equals("0")) timelineToCountSecondsForQuestionCountdown.stop();
			}));
			timelineToCountSecondsForQuestionCountdown.setCycleCount(Animation.INDEFINITE);
			
			timelineForQuestionCountdown = new Timeline(new KeyFrame(Duration.millis(0), e ->
			{
				progressBarForCountdown.setProgress(getCurrentProgressForProgressbar() / 1000.0);
				
				int x = 1000 - getCurrentProgressForProgressbar();
				if (x < 1000)
				{
					String color = pr.getColor(x, 0).toString().replace("0x", "");
					progressBarForCountdown.setStyle("-fx-accent: #" + color + ";");
				}
			}), new KeyFrame(Duration.millis(50), e ->
			{
				setCurrentProgressForProgressbar(getCurrentProgressForProgressbar() - 5);
				if (getCurrentProgressForProgressbar() < 0)
				{
					if (gridPaneFor2TextAnswers.isVisible()) setFunctional2TextAnswers(false);
					else if (gridPaneFor4TextAnswers.isVisible()) setFunctional4TextAnswers(false);
					else setFunctional4ImageAnswers(false);
					
					progressBarForCountdown.setId("red");
					timelineForQuestionCountdown.stop();
					stopClockTickingSound();
					playTimeOverSound();
					
					answerEvaluator.hintToCorrectAnswer();
					pauseTimePassedCounting();
					
					if (gameMode == GAMEMODE.ENDLESS_GAMEMODE) loseALife();
					
					int points = newScorePoints(false);
					setScorePoints(getScorePoints() + points);
					if(getScorePoints() < 0) setScorePoints(0);
					showScoreAnimated(points);
					
					if(getCombo() > 1)
					{
						if(animationsUsed != ANIMATIONS.NO)
						{
							scaleTransitionForTextForCombo.setDuration(Duration.millis(300));
							scaleTransitionForTextForCombo.setCycleCount(1);
							scaleTransitionForTextForCombo.setAutoReverse(false);
							scaleTransitionForTextForCombo.setFromX(textForCombo.getScaleX());
							scaleTransitionForTextForCombo.setFromY(textForCombo.getScaleY());
							scaleTransitionForTextForCombo.setToX(0);
							scaleTransitionForTextForCombo.setToY(0);
							scaleTransitionForTextForCombo.playFromStart();
						}
						else textForCombo.setVisible(false);
					}
					setCombo(0);
					
					nextQuestionButton.setDisable(false);
					startTimelineFor2_5SecondsWait();
				}
			}));
			timelineForQuestionCountdown.setCycleCount(Animation.INDEFINITE);
		}
		
		timelineToCloseTextForCountdown = new Timeline(
				new KeyFrame(Duration.millis(100), e ->
				{
					scaleTransitionForTextForCountdown.setToX(0);
					scaleTransitionForTextForCountdown.setToY(0);
					scaleTransitionForTextForCountdown.playFromStart();
				}));
	}
	
	protected void setupLimitedAnimations()
	{
		scaleTransitionForTextForCombo = new ScaleTransition();
		scaleTransitionForTextForCombo.setNode(textForCombo);
		
		scaleTransitionForScoreAnimated = new ScaleTransition(Duration.millis(300), scoreAnimated);
		translateTransitionForScoreAnimated = new TranslateTransition(Duration.millis(300), scoreAnimated);
		parallelTransitionForScoreAnimated = new ParallelTransition(scaleTransitionForScoreAnimated, translateTransitionForScoreAnimated);
		
		scaleTransitionForComboAnimated = new ScaleTransition(Duration.millis(300), comboAnimated);
		translateTransitionForComboAnimated = new TranslateTransition(Duration.millis(300), comboAnimated);
		parallelTransitionForComboAnimated = new ParallelTransition(scaleTransitionForComboAnimated, translateTransitionForComboAnimated);
		
		scaleTransitionForLargeAnswerIcon = new ScaleTransition(Duration.millis(300), imageViewForLargeAnswerIcon);
		translateTransitionForLargeAnswerIcon = new TranslateTransition(Duration.millis(300), imageViewForLargeAnswerIcon);
		parallelTransitionForLargeAnswerIcon = new ParallelTransition(scaleTransitionForLargeAnswerIcon, translateTransitionForLargeAnswerIcon);
		
		scaleTransitionForImageViewForLargeHeart = new ScaleTransition(Duration.millis(300), imageViewForLargeHeart);
		translateTransitionForImageViewForLargeHeart = new TranslateTransition(Duration.millis(300), imageViewForLargeHeart);
		parallelTransitionForLifeLostAnimation = new ParallelTransition(scaleTransitionForImageViewForLargeHeart, translateTransitionForImageViewForLargeHeart);
		
		scaleTransitionForNumberOfQuestionAnimated = new ScaleTransition(Duration.millis(300), numberOfQuestionAnimated);
		translateTransitionForNumberOfQuestionAnimated = new TranslateTransition(Duration.millis(300), numberOfQuestionAnimated);
		parallelTransitionForNumberOfQuestionAnimated = new ParallelTransition(scaleTransitionForNumberOfQuestionAnimated, translateTransitionForNumberOfQuestionAnimated);
		
		fadeTransitionForWorldMapImage = new FadeTransition(Duration.millis(400), worldMapBackground);
		fadeTransitionForWorldMapImage.setToValue(0);
		fadeTransitionForWorldMapImage.setOnFinished(ev -> welcomeScreen.showScreen(false));
	
		ScaleTransition scaleTransitionFor2TextAnswers_1_1 = new ScaleTransition(Duration.millis(200), radioButtonsFor2TextAnswers[0]);
		scaleTransitionFor2TextAnswers_1_1.setFromX(0);
		scaleTransitionFor2TextAnswers_1_1.setFromY(0);
		scaleTransitionFor2TextAnswers_1_1.setToX(1.2);
		scaleTransitionFor2TextAnswers_1_1.setToY(1.2);
		
		ScaleTransition scaleTransitionFor2TextAnswers_1_2 = new ScaleTransition(Duration.millis(50), radioButtonsFor2TextAnswers[0]);
		scaleTransitionFor2TextAnswers_1_2.setFromX(1.2);
		scaleTransitionFor2TextAnswers_1_2.setFromY(1.2);
		scaleTransitionFor2TextAnswers_1_2.setToX(1);
		scaleTransitionFor2TextAnswers_1_2.setToY(1);
		
		ScaleTransition scaleTransitionFor2TextAnswers_2_1 = new ScaleTransition(Duration.millis(200), radioButtonsFor2TextAnswers[1]);
		scaleTransitionFor2TextAnswers_2_1.setFromX(0);
		scaleTransitionFor2TextAnswers_2_1.setFromY(0);
		scaleTransitionFor2TextAnswers_2_1.setToX(1.2);
		scaleTransitionFor2TextAnswers_2_1.setToY(1.2);
		
		ScaleTransition scaleTransitionFor2TextAnswers_2_2 = new ScaleTransition(Duration.millis(50), radioButtonsFor2TextAnswers[1]);
		scaleTransitionFor2TextAnswers_2_2.setFromX(1.2);
		scaleTransitionFor2TextAnswers_2_2.setFromY(1.2);
		scaleTransitionFor2TextAnswers_2_2.setToX(1);
		scaleTransitionFor2TextAnswers_2_2.setToY(1);
		
		sequentialTransitionsFor2TextAnswers = new SequentialTransition[2];
		sequentialTransitionsFor2TextAnswers[0] = new SequentialTransition(scaleTransitionFor2TextAnswers_1_1, scaleTransitionFor2TextAnswers_1_2);
		sequentialTransitionsFor2TextAnswers[1] = new SequentialTransition(scaleTransitionFor2TextAnswers_2_1, scaleTransitionFor2TextAnswers_2_2);
		
		ScaleTransition scaleTransitionFor4TextAnswers_1_1 = new ScaleTransition(Duration.millis(200), radioButtonsFor4TextAnswers[0]);
		scaleTransitionFor4TextAnswers_1_1.setFromX(0);
		scaleTransitionFor4TextAnswers_1_1.setFromY(0);
		scaleTransitionFor4TextAnswers_1_1.setToX(1.2);
		scaleTransitionFor4TextAnswers_1_1.setToY(1.2);
		
		ScaleTransition scaleTransitionFor4TextAnswers_1_2 = new ScaleTransition(Duration.millis(50), radioButtonsFor4TextAnswers[0]);
		scaleTransitionFor4TextAnswers_1_2.setFromX(1.2);
		scaleTransitionFor4TextAnswers_1_2.setFromY(1.2);
		scaleTransitionFor4TextAnswers_1_2.setToX(1);
		scaleTransitionFor4TextAnswers_1_2.setToY(1);
		
		ScaleTransition scaleTransitionFor4TextAnswers_2_1 = new ScaleTransition(Duration.millis(200), radioButtonsFor4TextAnswers[1]);
		scaleTransitionFor4TextAnswers_2_1.setFromX(0);
		scaleTransitionFor4TextAnswers_2_1.setFromY(0);
		scaleTransitionFor4TextAnswers_2_1.setToX(1.2);
		scaleTransitionFor4TextAnswers_2_1.setToY(1.2);
		
		ScaleTransition scaleTransitionFor4TextAnswers_2_2 = new ScaleTransition(Duration.millis(50), radioButtonsFor4TextAnswers[1]);
		scaleTransitionFor4TextAnswers_2_2.setFromX(1.2);
		scaleTransitionFor4TextAnswers_2_2.setFromY(1.2);
		scaleTransitionFor4TextAnswers_2_2.setToX(1);
		scaleTransitionFor4TextAnswers_2_2.setToY(1);
		
		ScaleTransition scaleTransitionFor4TextAnswers_3_1 = new ScaleTransition(Duration.millis(200), radioButtonsFor4TextAnswers[2]);
		scaleTransitionFor4TextAnswers_3_1.setFromX(0);
		scaleTransitionFor4TextAnswers_3_1.setFromY(0);
		scaleTransitionFor4TextAnswers_3_1.setToX(1.2);
		scaleTransitionFor4TextAnswers_3_1.setToY(1.2);
		
		ScaleTransition scaleTransitionFor4TextAnswers_3_2 = new ScaleTransition(Duration.millis(50), radioButtonsFor4TextAnswers[2]);
		scaleTransitionFor4TextAnswers_3_2.setFromX(1.2);
		scaleTransitionFor4TextAnswers_3_2.setFromY(1.2);
		scaleTransitionFor4TextAnswers_3_2.setToX(1);
		scaleTransitionFor4TextAnswers_3_2.setToY(1);
		
		ScaleTransition scaleTransitionFor4TextAnswers_4_1 = new ScaleTransition(Duration.millis(200), radioButtonsFor4TextAnswers[3]);
		scaleTransitionFor4TextAnswers_4_1.setFromX(0);
		scaleTransitionFor4TextAnswers_4_1.setFromY(0);
		scaleTransitionFor4TextAnswers_4_1.setToX(1.2);
		scaleTransitionFor4TextAnswers_4_1.setToY(1.2);
		
		ScaleTransition scaleTransitionFor4TextAnswers_4_2 = new ScaleTransition(Duration.millis(50), radioButtonsFor4TextAnswers[3]);
		scaleTransitionFor4TextAnswers_4_2.setFromX(1.2);
		scaleTransitionFor4TextAnswers_4_2.setFromY(1.2);
		scaleTransitionFor4TextAnswers_4_2.setToX(1);
		scaleTransitionFor4TextAnswers_4_2.setToY(1);
		
		sequentialTransitionsFor4TextAnswers = new SequentialTransition[4];
		sequentialTransitionsFor4TextAnswers[0] = new SequentialTransition(scaleTransitionFor4TextAnswers_1_1, scaleTransitionFor4TextAnswers_1_2);
		sequentialTransitionsFor4TextAnswers[1] = new SequentialTransition(scaleTransitionFor4TextAnswers_2_1, scaleTransitionFor4TextAnswers_2_2);
		sequentialTransitionsFor4TextAnswers[2] = new SequentialTransition(scaleTransitionFor4TextAnswers_3_1, scaleTransitionFor4TextAnswers_3_2);
		sequentialTransitionsFor4TextAnswers[3] = new SequentialTransition(scaleTransitionFor4TextAnswers_4_1, scaleTransitionFor4TextAnswers_4_2);
		
		ScaleTransition scaleTransitionFor4ImageAnswers_1_1 = new ScaleTransition(Duration.millis(200), imageViewFor4ImageAnswers[0]);
		scaleTransitionFor4ImageAnswers_1_1.setFromX(0);
		scaleTransitionFor4ImageAnswers_1_1.setFromY(0);
		scaleTransitionFor4ImageAnswers_1_1.setToX(1.2);
		scaleTransitionFor4ImageAnswers_1_1.setToY(1.2);
		
		ScaleTransition scaleTransitionFor4ImageAnswers_1_2 = new ScaleTransition(Duration.millis(50), imageViewFor4ImageAnswers[0]);
		scaleTransitionFor4ImageAnswers_1_2.setFromX(1.2);
		scaleTransitionFor4ImageAnswers_1_2.setFromY(1.2);
		scaleTransitionFor4ImageAnswers_1_2.setToX(1);
		scaleTransitionFor4ImageAnswers_1_2.setToY(1);
		
		ScaleTransition scaleTransitionFor4ImageAnswers_2_1 = new ScaleTransition(Duration.millis(200), imageViewFor4ImageAnswers[1]);
		scaleTransitionFor4ImageAnswers_2_1.setFromX(0);
		scaleTransitionFor4ImageAnswers_2_1.setFromY(0);
		scaleTransitionFor4ImageAnswers_2_1.setToX(1.2);
		scaleTransitionFor4ImageAnswers_2_1.setToY(1.2);
		
		ScaleTransition scaleTransitionFor4ImageAnswers_2_2 = new ScaleTransition(Duration.millis(50), imageViewFor4ImageAnswers[1]);
		scaleTransitionFor4ImageAnswers_2_2.setFromX(1.2);
		scaleTransitionFor4ImageAnswers_2_2.setFromY(1.2);
		scaleTransitionFor4ImageAnswers_2_2.setToX(1);
		scaleTransitionFor4ImageAnswers_2_2.setToY(1);
		
		ScaleTransition scaleTransitionFor4ImageAnswers_3_1 = new ScaleTransition(Duration.millis(200), imageViewFor4ImageAnswers[2]);
		scaleTransitionFor4ImageAnswers_3_1.setFromX(0);
		scaleTransitionFor4ImageAnswers_3_1.setFromY(0);
		scaleTransitionFor4ImageAnswers_3_1.setToX(1.2);
		scaleTransitionFor4ImageAnswers_3_1.setToY(1.2);
		
		ScaleTransition scaleTransitionFor4ImageAnswers_3_2 = new ScaleTransition(Duration.millis(50), imageViewFor4ImageAnswers[2]);
		scaleTransitionFor4ImageAnswers_3_2.setFromX(1.2);
		scaleTransitionFor4ImageAnswers_3_2.setFromY(1.2);
		scaleTransitionFor4ImageAnswers_3_2.setToX(1);
		scaleTransitionFor4ImageAnswers_3_2.setToY(1);
		
		ScaleTransition scaleTransitionFor4ImageAnswers_4_1 = new ScaleTransition(Duration.millis(200), imageViewFor4ImageAnswers[3]);
		scaleTransitionFor4ImageAnswers_4_1.setFromX(0);
		scaleTransitionFor4ImageAnswers_4_1.setFromY(0);
		scaleTransitionFor4ImageAnswers_4_1.setToX(1.2);
		scaleTransitionFor4ImageAnswers_4_1.setToY(1.2);
		
		ScaleTransition scaleTransitionFor4ImageAnswers_4_2 = new ScaleTransition(Duration.millis(50), imageViewFor4ImageAnswers[3]);
		scaleTransitionFor4ImageAnswers_4_2.setFromX(1.2);
		scaleTransitionFor4ImageAnswers_4_2.setFromY(1.2);
		scaleTransitionFor4ImageAnswers_4_2.setToX(1);
		scaleTransitionFor4ImageAnswers_4_2.setToY(1);
		
		sequentialTransitionsFor4ImageAnswers = new SequentialTransition[4];
		sequentialTransitionsFor4ImageAnswers[0] = new SequentialTransition(scaleTransitionFor4ImageAnswers_1_1, scaleTransitionFor4ImageAnswers_1_2);
		sequentialTransitionsFor4ImageAnswers[1] = new SequentialTransition(scaleTransitionFor4ImageAnswers_2_1, scaleTransitionFor4ImageAnswers_2_2);
		sequentialTransitionsFor4ImageAnswers[2] = new SequentialTransition(scaleTransitionFor4ImageAnswers_3_1, scaleTransitionFor4ImageAnswers_3_2);
		sequentialTransitionsFor4ImageAnswers[3] = new SequentialTransition(scaleTransitionFor4ImageAnswers_4_1, scaleTransitionFor4ImageAnswers_4_2);
		
		scaleTransitionFor2TextAnswers = new ScaleTransition[2];
		scaleTransitionFor2TextAnswers[0] = new ScaleTransition(Duration.millis(300), radioButtonsFor2TextAnswers[0]);
		scaleTransitionFor2TextAnswers[1] = new ScaleTransition(Duration.millis(300), radioButtonsFor2TextAnswers[1]);
		
		scaleTransitionFor4TextAnswers = new ScaleTransition[4];
		scaleTransitionFor4TextAnswers[0] = new ScaleTransition(Duration.millis(300), radioButtonsFor4TextAnswers[0]);
		scaleTransitionFor4TextAnswers[1] = new ScaleTransition(Duration.millis(300), radioButtonsFor4TextAnswers[1]);
		scaleTransitionFor4TextAnswers[2] = new ScaleTransition(Duration.millis(300), radioButtonsFor4TextAnswers[2]);
		scaleTransitionFor4TextAnswers[3] = new ScaleTransition(Duration.millis(300), radioButtonsFor4TextAnswers[3]);
		
		scaleTransitionFor4ImageAnswers = new ScaleTransition[4];
		scaleTransitionFor4ImageAnswers[0] = new ScaleTransition(Duration.millis(300), imageViewFor4ImageAnswers[0]);
		scaleTransitionFor4ImageAnswers[1] = new ScaleTransition(Duration.millis(300), imageViewFor4ImageAnswers[1]);
		scaleTransitionFor4ImageAnswers[2] = new ScaleTransition(Duration.millis(300), imageViewFor4ImageAnswers[2]);
		scaleTransitionFor4ImageAnswers[3] = new ScaleTransition(Duration.millis(300), imageViewFor4ImageAnswers[3]);
		
		scaleTransitionForAnswerIconsFor2TextAnswers = new ScaleTransition[2];
		scaleTransitionForAnswerIconsFor2TextAnswers[0] = new ScaleTransition(Duration.millis(100), answerIconsFor2TextAnswers[0]);
		scaleTransitionForAnswerIconsFor2TextAnswers[1] = new ScaleTransition(Duration.millis(100), answerIconsFor2TextAnswers[1]);
		
		scaleTransitionForAnswerIconsFor4TextAnswers = new ScaleTransition[4];
		scaleTransitionForAnswerIconsFor4TextAnswers[0] = new ScaleTransition(Duration.millis(100), answerIconsFor4TextAnswers[0]);
		scaleTransitionForAnswerIconsFor4TextAnswers[1] = new ScaleTransition(Duration.millis(100), answerIconsFor4TextAnswers[1]);
		scaleTransitionForAnswerIconsFor4TextAnswers[2] = new ScaleTransition(Duration.millis(100), answerIconsFor4TextAnswers[2]);
		scaleTransitionForAnswerIconsFor4TextAnswers[3] = new ScaleTransition(Duration.millis(100), answerIconsFor4TextAnswers[3]);
		
		scaleTransitionForAnswerIconsFor4ImageAnswers = new ScaleTransition[4];
		scaleTransitionForAnswerIconsFor4ImageAnswers[0] = new ScaleTransition(Duration.millis(150), answerIconsFor4ImageAnswers[0]);
		scaleTransitionForAnswerIconsFor4ImageAnswers[1] = new ScaleTransition(Duration.millis(150), answerIconsFor4ImageAnswers[1]);
		scaleTransitionForAnswerIconsFor4ImageAnswers[2] = new ScaleTransition(Duration.millis(150), answerIconsFor4ImageAnswers[2]);
		scaleTransitionForAnswerIconsFor4ImageAnswers[3] = new ScaleTransition(Duration.millis(150), answerIconsFor4ImageAnswers[3]);
		
		translateTransitionForTitleImageForFinishedGame = new TranslateTransition(Duration.millis(300), titleImageForFinishedGame);
		
		translateTransitionForTextForCombo = new TranslateTransition(Duration.millis(300), textForCombo);
		
		scaleTransitionForHBoxForLives = new ScaleTransition(Duration.millis(300), hBoxForLives);
		
		scaleTransitionForTitleLabelForFinishedGame = new ScaleTransition(Duration.millis(300), titleLabelForFinishedGame);
		
		translateTransitionFoVBoxForPausedGame = new TranslateTransition(Duration.millis(300), vBoxForPausedGame);
		
		translateTransitionForVBoxForSound = new TranslateTransition(Duration.millis(300), vBoxForSound);
		
		translateTransitionForWoodPanelFor5IconsImage = new TranslateTransition(Duration.millis(300), woodPanelFor5IconsImage);
		
		scaleTransitionForHBoxFor5Icons = new ScaleTransition(Duration.millis(300), hBoxFor5Icons);
		
		scaleTransitionForPauseGameIcon = new ScaleTransition(Duration.millis(300), pauseGameIcon);
		
		scaleTransitionForProgressBarForCountdown = new ScaleTransition(Duration.millis(300), progressBarForCountdown);
		
		scaleTransitionForVBoxForNextQuestion = new ScaleTransition(Duration.millis(300), vBoxForNextQuestionButtonAndProgressBar);
		
		scaleTransitionForTextForQuestionNumber = new ScaleTransition(Duration.millis(300), textForQuestionNumber);
		
		scaleTransitionForPopUpMessage = new ScaleTransition(Duration.millis(200), popUpMessage);
		
		scaleTransitionForTextForTimePassed = new ScaleTransition(Duration.millis(300), textForTimePassed);
		
		scaleTransitionForTextForScore = new ScaleTransition(Duration.millis(300), textForScore);
		
		translateTransitionForScoreText = new TranslateTransition(Duration.millis(300), textForScore);
		
		scaleTransitionForTextForCountdown = new ScaleTransition(Duration.millis(250), textForCountdown);
		
		scaleTransitionForTextForQuestion = new ScaleTransition(Duration.millis(300), textForQuestion);
		
		scaleTransitionForGridPaneFor4TextAnswers = new ScaleTransition(Duration.millis(300), gridPaneFor4TextAnswers);
		
		scaleTransitionForGridPaneFor2TextAnswers = new ScaleTransition(Duration.millis(300), gridPaneFor2TextAnswers);
		
		scaleTransitionForStackPaneFor4ImageAnswers = new ScaleTransition(Duration.millis(300), stackPaneFor4ImageAnswers);
		
		scaleTransitionForQuestionImage = new ScaleTransition(Duration.millis(300), imageViewForQuestionImage);
		
		scaleTransitionForTextForResultsDuration = new ScaleTransition(Duration.millis(300), textForResultsDuration);
		
		scaleTransitionForTextForResults = new ScaleTransition(Duration.millis(300), textForResults);
		scaleTransitionForTextForResults2 = new ScaleTransition(Duration.millis(300), textForResults2);
		
		scaleTransitionForRestartGameButton = new ScaleTransition(Duration.millis(300), restartGameFromFinishedScreenButton);
		
		scaleTransitionForReturnToWelcomeScreenButton = new ScaleTransition(Duration.millis(300), returnToWelcomeScreenFromFinishedGameScreenButton);
		
		timelineToShow2TextAnswers = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				playPopUpSound();
				sequentialTransitionsFor2TextAnswers[0].playFromStart();
			}),
			new KeyFrame(Duration.millis(150), e ->
			{
				playPopUpSound();
				sequentialTransitionsFor2TextAnswers[1].playFromStart();
			}),
			new KeyFrame(Duration.millis(400), e ->
			{
				startTimers();
				pauseGameIcon.setDisable(false);
			}));
		
		timelineToShow4TextAnswers = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					playPopUpSound();
					sequentialTransitionsFor4TextAnswers[0].playFromStart();
				}),
				new KeyFrame(Duration.millis(200), e ->
				{
					playPopUpSound();
					sequentialTransitionsFor4TextAnswers[1].playFromStart();
				}),
				new KeyFrame(Duration.millis(400), e ->
				{
					playPopUpSound();
					sequentialTransitionsFor4TextAnswers[2].playFromStart();
				}),
				new KeyFrame(Duration.millis(600), e ->
				{
					playPopUpSound();
					sequentialTransitionsFor4TextAnswers[3].playFromStart();
				}),
				new KeyFrame(Duration.millis(850), e ->
				{
					startTimers();
					pauseGameIcon.setDisable(false);
				}));
		
		timelineToShow4ImageAnswers = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					playPopUpSound();
					sequentialTransitionsFor4ImageAnswers[0].playFromStart();
				}),
				new KeyFrame(Duration.millis(200), e ->
				{
					playPopUpSound();
					sequentialTransitionsFor4ImageAnswers[1].playFromStart();
				}),
				new KeyFrame(Duration.millis(400), e ->
				{
					playPopUpSound();
					sequentialTransitionsFor4ImageAnswers[2].playFromStart();
				}),
				new KeyFrame(Duration.millis(600), e ->
				{
					playPopUpSound();
					sequentialTransitionsFor4ImageAnswers[3].playFromStart();
				}),
				new KeyFrame(Duration.millis(850), e ->
				{
					startTimers();
					pauseGameIcon.setDisable(false);
				}));
		
		timelineToShowAllStuff = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				translateTransitionForWoodPanelFor5IconsImage.setToY(0);
				
				playSlideSound();
				translateTransitionForWoodPanelFor5IconsImage.playFromStart();
			}),
			new KeyFrame(Duration.millis(300), e ->
			{
				scaleTransitionForHBoxFor5Icons.setToX(1);
				scaleTransitionForHBoxFor5Icons.setToY(1);
				scaleTransitionForPauseGameIcon.setToX(1);
				scaleTransitionForPauseGameIcon.setToY(1);
				scaleTransitionForVBoxForNextQuestion.setToX(1);
				scaleTransitionForVBoxForNextQuestion.setToY(1);
				scaleTransitionForTextForQuestionNumber.setToX(1);
				scaleTransitionForTextForQuestionNumber.setToY(1);
				scaleTransitionForTextForScore.setToX(1);
				scaleTransitionForTextForScore.setToY(1);
				
				playPopUpSound();
				scaleTransitionForHBoxFor5Icons.playFromStart();
				scaleTransitionForPauseGameIcon.playFromStart();
				scaleTransitionForTextForQuestionNumber.playFromStart();
				scaleTransitionForTextForScore.playFromStart();
				scaleTransitionForVBoxForNextQuestion.playFromStart();
				
				if(hBoxForLives.isVisible())
				{
					scaleTransitionForHBoxForLives.setToX(1);
					scaleTransitionForHBoxForLives.setToY(1);
					scaleTransitionForHBoxForLives.playFromStart();
				}
				if(progressBarForCountdown.isVisible())
				{
					scaleTransitionForProgressBarForCountdown.setToX(1);
					scaleTransitionForProgressBarForCountdown.setToY(1);
					scaleTransitionForProgressBarForCountdown.playFromStart();
					
					if(gameMode == GAMEMODE.TIME_ATTACK_GAMEMODE)
					{
						scaleTransitionForTextForCountdown.setToX(1);
						scaleTransitionForTextForCountdown.setToY(1);
						scaleTransitionForTextForCountdown.playFromStart();
					}
				}
				if(textForTimePassed.isVisible())
				{
					scaleTransitionForTextForTimePassed.setToX(1);
					scaleTransitionForTextForTimePassed.setToY(1);
					scaleTransitionForTextForTimePassed.playFromStart();
				}
				questionMaker.decideNextQuestion();
				timelineToShowNumberOfQuestion.playFromStart();
			}));
		
		timelineToHideAllStuffFromGameScreen = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				translateTransitionFoVBoxForPausedGame.setToY(-1.0 * (vBoxForPausedGame.getLayoutY() + vBoxForPausedGame.getPrefHeight() + 20));
				translateTransitionFoVBoxForPausedGame.setOnFinished(ev ->
				{
					translateTransitionFoVBoxForPausedGame.setOnFinished(eve ->{});
					
					removeBlurEffect();
				});
				
				playSlideSound();
				translateTransitionFoVBoxForPausedGame.playFromStart();
			}),
			new KeyFrame(Duration.millis(300), e ->
			{
				scaleTransitionForTextForQuestion.setToX(0);
				scaleTransitionForTextForQuestion.setToY(0);
				scaleTransitionForTextForQuestion.playFromStart();
				
				playMinimizeSound();
				
				hideQuestionImage();
				
				if(textForCountdown.isVisible() && textForCountdown.getScaleX() != 0)
				{
					scaleTransitionForTextForCountdown.setToX(0);
					scaleTransitionForTextForCountdown.setToY(0);
					scaleTransitionForTextForCountdown.playFromStart();
				}
				
				if(gridPaneFor2TextAnswers.isVisible())
				{
					scaleTransitionForGridPaneFor2TextAnswers.setToX(0);
					scaleTransitionForGridPaneFor2TextAnswers.setToY(0);
					scaleTransitionForGridPaneFor2TextAnswers.playFromStart();
				}
				else if(gridPaneFor4TextAnswers.isVisible())
				{
					scaleTransitionForGridPaneFor4TextAnswers.setToX(0);
					scaleTransitionForGridPaneFor4TextAnswers.setToY(0);
					scaleTransitionForGridPaneFor4TextAnswers.playFromStart();
				}
				else if(stackPaneFor4ImageAnswers.isVisible())
				{
					scaleTransitionForStackPaneFor4ImageAnswers.setToX(0);
					scaleTransitionForStackPaneFor4ImageAnswers.setToY(0);
					scaleTransitionForStackPaneFor4ImageAnswers.playFromStart();
				}
			}),
			new KeyFrame(Duration.millis(600), e ->
			{
				scaleTransitionForHBoxFor5Icons.setToX(0);
				scaleTransitionForHBoxFor5Icons.setToY(0);
				scaleTransitionForPauseGameIcon.setToX(0);
				scaleTransitionForPauseGameIcon.setToY(0);
				scaleTransitionForVBoxForNextQuestion.setToX(0);
				scaleTransitionForVBoxForNextQuestion.setToY(0);
				scaleTransitionForTextForQuestionNumber.setToX(0);
				scaleTransitionForTextForQuestionNumber.setToY(0);
				scaleTransitionForTextForScore.setToX(0);
				scaleTransitionForTextForScore.setToY(0);
				
				playMinimizeSound();
				scaleTransitionForHBoxFor5Icons.playFromStart();
				scaleTransitionForPauseGameIcon.playFromStart();
				scaleTransitionForVBoxForNextQuestion.playFromStart();
				scaleTransitionForTextForQuestionNumber.playFromStart();
				scaleTransitionForTextForScore.playFromStart();
				
				if(hBoxForLives.isVisible())
				{
					scaleTransitionForHBoxForLives.setToX(0);
					scaleTransitionForHBoxForLives.setToY(0);
					scaleTransitionForHBoxForLives.playFromStart();
				}
				if(progressBarForCountdown.isVisible())
				{
					scaleTransitionForProgressBarForCountdown.setToX(0);
					scaleTransitionForProgressBarForCountdown.setToY(0);
					scaleTransitionForProgressBarForCountdown.playFromStart();
				}
				if(textForTimePassed.isVisible())
				{
					scaleTransitionForTextForTimePassed.setToX(0);
					scaleTransitionForTextForTimePassed.setToY(0);
					scaleTransitionForTextForTimePassed.playFromStart();
				}
				if(textForCombo.isVisible())
				{
					scaleTransitionForTextForCombo.setDuration(Duration.millis(300));
					scaleTransitionForTextForCombo.setCycleCount(1);
					scaleTransitionForTextForCombo.setAutoReverse(false);
					scaleTransitionForTextForCombo.setFromX(textForCombo.getScaleX());
					scaleTransitionForTextForCombo.setFromY(textForCombo.getScaleY());
					scaleTransitionForTextForCombo.setToX(0);
					scaleTransitionForTextForCombo.setToY(0);
					scaleTransitionForTextForCombo.playFromStart();
				}
			}),
			new KeyFrame(Duration.millis(900), e ->
			{
				translateTransitionForWoodPanelFor5IconsImage.setToY(-1.0 * (woodPanelFor5IconsImage.getLayoutY() + woodPanelFor5IconsImage.getBoundsInParent().getHeight() + 20));
				
				playSlideSound();
				translateTransitionForWoodPanelFor5IconsImage.playFromStart();
				
				if(vBoxForSound.isVisible())
				{
					if (OS == OsCheck.OSType.Windows) translateTransitionForVBoxForSound.setToX(stage.getWidth() - vBoxForSound.getLayoutX() + 20);
					else translateTransitionForVBoxForSound.setToX(-1.0 * (vBoxForSound.getLayoutX() + vBoxForSound.getPrefWidth() + 20));
					translateTransitionForVBoxForSound.playFromStart();
				}
			}),
			new KeyFrame(Duration.millis(1200), e ->
			{
				vBoxForSound.setVisible(false);
				
				if(returningToWelcomeScreen)
				{
					welcomeScreenImage.setVisible(true);
					fadeTransitionForWorldMapImage.playFromStart();
				}
			}));
		
		timelineToFinishGame = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					scaleTransitionForTextForQuestion.setToX(0);
					scaleTransitionForTextForQuestion.setToY(0);
					scaleTransitionForTextForQuestion.playFromStart();
					
					playMinimizeSound();
					
					if(imageViewForQuestionImage.isVisible())
					{
						scaleTransitionForQuestionImage.setToX(0);
						scaleTransitionForQuestionImage.setToY(0);
						scaleTransitionForQuestionImage.playFromStart();
					}
					
					if(gridPaneFor2TextAnswers.isVisible())
					{
						scaleTransitionForGridPaneFor2TextAnswers.setToX(0);
						scaleTransitionForGridPaneFor2TextAnswers.setToY(0);
						scaleTransitionForGridPaneFor2TextAnswers.playFromStart();
					}
					else if(gridPaneFor4TextAnswers.isVisible())
					{
						scaleTransitionForGridPaneFor4TextAnswers.setToX(0);
						scaleTransitionForGridPaneFor4TextAnswers.setToY(0);
						scaleTransitionForGridPaneFor4TextAnswers.playFromStart();
					}
					else if(stackPaneFor4ImageAnswers.isVisible())
					{
						scaleTransitionForStackPaneFor4ImageAnswers.setToX(0);
						scaleTransitionForStackPaneFor4ImageAnswers.setToY(0);
						scaleTransitionForStackPaneFor4ImageAnswers.playFromStart();
					}
					
					if(scoreAnimated.getScaleX() != 0)
					{
						timelineForScoreAnimated.stop();
						parallelTransitionForScoreAnimated.stop();
						scaleTransitionForScoreAnimated.setToX(0);
						scaleTransitionForScoreAnimated.setToY(0);
						translateTransitionForScoreAnimated.setToX(scoreAnimated.getTranslateX());
						translateTransitionForScoreAnimated.setToY(scoreAnimated.getTranslateY());
						parallelTransitionForScoreAnimated.playFromStart();
					}
					if(comboAnimated.getScaleX() != 0)
					{
						timelineForComboAnimated.stop();
						parallelTransitionForComboAnimated.stop();
						scaleTransitionForComboAnimated.setToX(0);
						scaleTransitionForComboAnimated.setToY(0);
						translateTransitionForComboAnimated.setToX(comboAnimated.getTranslateX());
						translateTransitionForComboAnimated.setToY(comboAnimated.getTranslateY());
						parallelTransitionForComboAnimated.playFromStart();
					}
				}),
				new KeyFrame(Duration.millis(300), e ->
				{
					scaleTransitionForPauseGameIcon.setToX(0);
					scaleTransitionForPauseGameIcon.setToY(0);
					scaleTransitionForVBoxForNextQuestion.setToX(0);
					scaleTransitionForVBoxForNextQuestion.setToY(0);
					scaleTransitionForTextForQuestionNumber.setToX(0);
					scaleTransitionForTextForQuestionNumber.setToY(0);
					scaleTransitionForTextForScore.setToX(0);
					scaleTransitionForTextForScore.setToY(0);
					
					playMinimizeSound();
					scaleTransitionForPauseGameIcon.playFromStart();
					scaleTransitionForVBoxForNextQuestion.playFromStart();
					scaleTransitionForTextForQuestionNumber.playFromStart();
					scaleTransitionForTextForScore.playFromStart();
					
					if(hBoxForLives.isVisible())
					{
						scaleTransitionForHBoxForLives.setToX(0);
						scaleTransitionForHBoxForLives.setToY(0);
						scaleTransitionForHBoxForLives.playFromStart();
					}
					if(progressBarForCountdown.isVisible())
					{
						scaleTransitionForProgressBarForCountdown.setToX(0);
						scaleTransitionForProgressBarForCountdown.setToY(0);
						scaleTransitionForProgressBarForCountdown.playFromStart();
					}
					if(textForTimePassed.isVisible())
					{
						scaleTransitionForTextForTimePassed.setToX(0);
						scaleTransitionForTextForTimePassed.setToY(0);
						scaleTransitionForTextForTimePassed.playFromStart();
					}
					textForCombo.setText("");
				}),
				new KeyFrame(Duration.millis(600), e ->
				{
					titleImageForFinishedGame.setVisible(true);
					
					translateTransitionForTitleImageForFinishedGame.setToY(0);
					
					playSlideSound();
					translateTransitionForTitleImageForFinishedGame.playFromStart();
				}),
				new KeyFrame(Duration.millis(900), e ->
				{
					titleLabelForFinishedGame.setVisible(true);
					
					scaleTransitionForTitleLabelForFinishedGame.setCycleCount(1);
					scaleTransitionForTitleLabelForFinishedGame.setAutoReverse(false);
					scaleTransitionForTitleLabelForFinishedGame.setDuration(Duration.millis(300));
					scaleTransitionForTitleLabelForFinishedGame.setFromX(0);
					scaleTransitionForTitleLabelForFinishedGame.setFromY(0);
					scaleTransitionForTitleLabelForFinishedGame.setToX(0.95);
					scaleTransitionForTitleLabelForFinishedGame.setToY(0.95);
					
					if(animationsUsed == ANIMATIONS.ALL)
					{
						scaleTransitionForTitleLabelForFinishedGame.setOnFinished(ev ->
						{
							scaleTransitionForTitleLabelForFinishedGame.setOnFinished(eve -> {});
							startTextAnimationForTitleLabelForFinishedGame();
						});
					}
					else scaleTransitionForTitleLabelForFinishedGame.setOnFinished(ev -> {});
					
					playPopUpSound();
					scaleTransitionForTitleLabelForFinishedGame.playFromStart();
				}),
				new KeyFrame(Duration.millis(1200), e ->
				{
					if(gameMode != GAMEMODE.TIME_ATTACK_GAMEMODE)
					{
						textForResultsDuration.setVisible(true);
						scaleTransitionForTextForResultsDuration.setToX(1);
						
						scaleTransitionForTextForResultsDuration.playFromStart();
					}
					
					textForResults.setVisible(true);
					scaleTransitionForTextForResults.setToX(1);
					
					textForResults2.setVisible(true);
					scaleTransitionForTextForResults2.setToX(1);
					
					playPopUpSound();
					scaleTransitionForTextForResults.playFromStart();
					scaleTransitionForTextForResults2.playFromStart();
				}),
				new KeyFrame(Duration.millis(1500), e ->
				{
					if(isGameWon()) playGameWonSound();
					else playGameLostSound();
					
					restartGameFromFinishedScreenButton.setVisible(true);
					returnToWelcomeScreenFromFinishedGameScreenButton.setVisible(true);
					
					scaleTransitionForRestartGameButton.setToX(1);
					scaleTransitionForRestartGameButton.setToY(1);
					
					scaleTransitionForReturnToWelcomeScreenButton.setToX(1);
					scaleTransitionForReturnToWelcomeScreenButton.setToY(1);
					
					playPopUpSound();
					scaleTransitionForRestartGameButton.playFromStart();
					scaleTransitionForReturnToWelcomeScreenButton.playFromStart();
				}));
		
		timelineToHideAllStuffFromFinishedGameScreen = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					scaleTransitionForRestartGameButton.setToX(0);
					scaleTransitionForRestartGameButton.setToY(0);
					
					scaleTransitionForReturnToWelcomeScreenButton.setToX(0);
					scaleTransitionForReturnToWelcomeScreenButton.setToY(0);
					
					playMinimizeSound();
					scaleTransitionForRestartGameButton.playFromStart();
					scaleTransitionForReturnToWelcomeScreenButton.playFromStart();
				}),
				new KeyFrame(Duration.millis(300), e ->
				{
					if(gameMode != GAMEMODE.TIME_ATTACK_GAMEMODE)
					{
						scaleTransitionForTextForResultsDuration.setToX(0);
						
						scaleTransitionForTextForResultsDuration.playFromStart();
					}
					
					scaleTransitionForTextForResults.setToX(0);
					scaleTransitionForTextForResults2.setToX(0);
					
					playMinimizeSound();
					scaleTransitionForTextForResults.playFromStart();
					scaleTransitionForTextForResults2.playFromStart();
				}),
				new KeyFrame(Duration.millis(600), e ->
				{
					scaleTransitionForHBoxFor5Icons.setToX(0);
					scaleTransitionForHBoxFor5Icons.setToY(0);
					
					scaleTransitionForTitleLabelForFinishedGame.setCycleCount(1);
					scaleTransitionForTitleLabelForFinishedGame.setAutoReverse(false);
					scaleTransitionForTitleLabelForFinishedGame.setDuration(Duration.millis(300));
					scaleTransitionForTitleLabelForFinishedGame.setFromX(titleLabelForFinishedGame.getScaleX());
					scaleTransitionForTitleLabelForFinishedGame.setFromY(titleLabelForFinishedGame.getScaleY());
					scaleTransitionForTitleLabelForFinishedGame.setToX(0);
					scaleTransitionForTitleLabelForFinishedGame.setToY(0);
					scaleTransitionForTitleLabelForFinishedGame.setOnFinished(ev -> {});
					
					playMinimizeSound();
					scaleTransitionForHBoxFor5Icons.playFromStart();
					scaleTransitionForTitleLabelForFinishedGame.playFromStart();
				}),
				new KeyFrame(Duration.millis(900), e ->
				{
					translateTransitionForWoodPanelFor5IconsImage.setToY(-1.0 * (woodPanelFor5IconsImage.getLayoutY() + woodPanelFor5IconsImage.getBoundsInParent().getHeight() + 20));
					translateTransitionForTitleImageForFinishedGame.setToY(-1.0 * (titleImageForFinishedGame.getLayoutY() + titleImageForFinishedGame.getBoundsInParent().getHeight() + 20));
					
					playSlideSound();
					translateTransitionForWoodPanelFor5IconsImage.playFromStart();
					translateTransitionForTitleImageForFinishedGame.playFromStart();
					
					if(vBoxForSound.isVisible())
					{
						if (OS == OsCheck.OSType.Windows) translateTransitionForVBoxForSound.setToX(stage.getWidth() - vBoxForSound.getLayoutX() + 20);
						else translateTransitionForVBoxForSound.setToX(-1.0 * (vBoxForSound.getLayoutX() + vBoxForSound.getPrefWidth() + 20));
						translateTransitionForVBoxForSound.playFromStart();
					}
				}),
				new KeyFrame(Duration.millis(1200), e ->
				{
					vBoxForSound.setVisible(false);
					
					welcomeScreenImage.setVisible(true);
					fadeTransitionForWorldMapImage.playFromStart();
				}));
		
		timelineToShowSoundOptions = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				soundIcon.setDisable(true);
				
				if(OS == OsCheck.OSType.Windows)
				{
					translateTransitionForScoreText.setToY(0.2065 * stage.getHeight());
					translateTransitionForTextForCombo.setToY(0.2065 * stage.getHeight());
					
					translateTransitionForScoreText.playFromStart();
					translateTransitionForTextForCombo.playFromStart();
				}
				
				playSlideSound();
			}),
			new KeyFrame(Duration.millis(150), e ->
			{
				vBoxForSound.setVisible(true);
				translateTransitionForVBoxForSound.setToX(0);
				
				translateTransitionForVBoxForSound.playFromStart();
			}),
			new KeyFrame(Duration.millis(450), e -> soundIcon.setDisable(false)));
		
		timelineToHideSoundOptions = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				soundIcon.setDisable(true);
				
				if (OS == OsCheck.OSType.Windows) translateTransitionForVBoxForSound.setToX(stage.getWidth() - vBoxForSound.getLayoutX() + 20);
				else translateTransitionForVBoxForSound.setToX(-1.0 * (vBoxForSound.getLayoutX() + vBoxForSound.getPrefWidth() + 20));
				
				playSlideSound();
				translateTransitionForVBoxForSound.playFromStart();
			}),
			new KeyFrame(Duration.millis(150), e ->
			{
				if( OS == OsCheck.OSType.Windows)
				{
					translateTransitionForScoreText.setToY(0);
					translateTransitionForTextForCombo.setToY(0);
					
					translateTransitionForScoreText.playFromStart();
					translateTransitionForTextForCombo.playFromStart();
				}
			}),
			new KeyFrame(Duration.millis(450), e ->
			{
				soundIcon.setDisable(false);
				vBoxForSound.setVisible(false);
			}));
	}
	
	protected void setupAdvancedAnimations()
	{
		
	}
	
	public void showScreen(GAMEMODE gameMode)
	{
		this.gameMode = gameMode;
		setInitialStateForAllNodes();
		
		if (fullScreenMode) setFullScreenMode();
		else setWindowedMode();
		
		mainScene.setRoot(anchorPane);
		
		if(animationsUsed != ANIMATIONS.NO)
			timelineToShowAllStuff.playFromStart();
	}
	
	private void initializeGame()
	{
		currentGame = new Game();
		gameStartedTime = LocalDateTime.now();
		
		//SETUP VARIABLES BASED ON GAME MODE-------------------------------------------------------------
		if(availableCategories == null) availableCategories = new ArrayList<>();
		else availableCategories.clear();
		
		for (int i = 0; i < NUM_ALL_CATEGORIES; i++)
			if (isCategorySelected(i)) availableCategories.add(i);
		
		int[] categories = new int[availableCategories.size()];
		for(int i = 0; i < availableCategories.size(); i++) categories[i] = availableCategories.get(i);
		currentGame.setQuestionCategories(categories);
		
		questionMaker.setAvailableQuestions();
		
		if(gameMode == GAMEMODE.ENDLESS_GAMEMODE)
		{
			setRemainingLivesForEndless(getLivesForEndless());
			
			hBoxForLives.setVisible(true);
			
			hBoxForLives.getChildren().clear();
			
			if(getLivesForEndless() == 1)
			{
				imageViewForLives[0].setImage(HEART_ICON_SMALL);
				hBoxForLives.getChildren().add(imageViewForLives[0]);
			}
			else if(getLivesForEndless() == 3)
			{
				for(int i = 0; i < 5; i++) imageViewForLives[i].setImage(HEART_ICON_SMALL);
				hBoxForLives.getChildren().addAll(imageViewForLives[0], imageViewForLives[1], imageViewForLives[2]);
			}
			else
			{
				for(int i = 0; i < 5; i++) imageViewForLives[i].setImage(HEART_ICON_SMALL);
				hBoxForLives.getChildren().addAll(imageViewForLives[0], imageViewForLives[1], imageViewForLives[2], imageViewForLives[3], imageViewForLives[4]);
			}
		}
		else hBoxForLives.setVisible(false);
		
		if(gameMode == GAMEMODE.CLASSIC_GAMEMODE && isTimerForClassic() || gameMode == GAMEMODE.ENDLESS_GAMEMODE && isTimerForEndless() || gameMode == GAMEMODE.TIME_ATTACK_GAMEMODE)
		{
			if(gameMode == GAMEMODE.TIME_ATTACK_GAMEMODE)
			{
				setQuestionCountdownEnabled(false);
				
				setCurrentProgressForProgressbar(10000);
				
				if(getDurationInMinutesForTimeAttack() == 1) setRemainingSecondsForTimeAttack(60);
				else if (getDurationInMinutesForTimeAttack() == 2) setRemainingSecondsForTimeAttack(120);
				else if (getDurationInMinutesForTimeAttack() == 5) setRemainingSecondsForTimeAttack(300);
				else setRemainingSecondsForTimeAttack(600);
				
				setReadableRemainingTimeForTimeAttack();
			}
			else setQuestionCountdownEnabled(true);
			
			progressBarForCountdown.setId("normal");
			progressBarForCountdown.setStyle("-fx-accent: #2ecc71;");
			progressBarForCountdown.setProgress(1);
			
			progressBarForCountdown.setVisible(true);
			textForCountdown.setVisible(true);
		}
		else
		{
			setQuestionCountdownEnabled(false);
			progressBarForCountdown.setVisible(false);
			textForCountdown.setVisible(false);
		}
		
		if(gameMode == GAMEMODE.CLASSIC_GAMEMODE || gameMode == GAMEMODE.ENDLESS_GAMEMODE)
		{
			textForTimePassed.setVisible(true);
			setSecondsPassed(0);
			setReadableTimePassed();
		}
		else textForTimePassed.setVisible(false);
		
		setMillisecondsPassed(0);
		setCurrentQuestionNumber(1);
		updateQuestionNumberText();
		setCorrectAnswers(0);
		setCombo(0);
		setScorePoints(0);
		updateScoreText();
	}
	
	private void setInitialStateForAllNodes()
	{
		//SETUP IMAGES-------------------------------------------------------------
		if(woodenFrameImage.getImage() == null || !woodenFrameImage.getImage().equals(FRAME_IMAGE))
		{
			woodenFrameImage.setImage(FRAME_IMAGE);
			titleImageForFinishedGame.setImage(EMPTY_WOOD_BACKGROUND_PANEL_SMALL_ROPE);
			welcomeScreenImage.setImage(CHALKBOARD_BACKGROUND_IMAGE);
			worldMapBackground.setImage(WORLD_MAP);
			woodPanelFor5IconsImage.setImage(WOOD_BACKGROUND_IMAGE_FOR_5_BUTTONS);
			nextQuestionArrowImage.setImage(BACK_ARROW);
		}
		
		initializeGame();
		
		setupTimers();
		
		//SETUP POSITIONS BASED ON ANIMATIONS-------------------------------------------------------------
		if(animationsUsed != ANIMATIONS.NO)
		{
			woodPanelFor5IconsImage.setTranslateY(-1.0 * (woodPanelFor5IconsImage.getLayoutY() + woodPanelFor5IconsImage.getBoundsInParent().getHeight() + 20));
			
			hBoxFor5Icons.setScaleX(0);
			hBoxFor5Icons.setScaleY(0);
			
			pauseGameIcon.setScaleX(0);
			pauseGameIcon.setScaleY(0);
			
			textForQuestionNumber.setScaleX(0);
			textForQuestionNumber.setScaleY(0);
			
			numberOfQuestionAnimated.setScaleX(0);
			numberOfQuestionAnimated.setScaleY(0);
			numberOfQuestionAnimated.setVisible(true);
			
			if(gameMode == GAMEMODE.ENDLESS_GAMEMODE)
			{
				hBoxForLives.setScaleX(0);
				hBoxForLives.setScaleY(0);
			}
			
			if(progressBarForCountdown.isVisible())
			{
				progressBarForCountdown.setScaleX(0);
				progressBarForCountdown.setScaleY(0);
			}
			if(textForTimePassed.isVisible())
			{
				textForTimePassed.setScaleX(0);
				textForTimePassed.setScaleY(0);
			}
			
			scoreAnimated.setScaleX(0);
			scoreAnimated.setScaleY(0);
			scoreAnimated.setVisible(true);
			
			comboAnimated.setScaleX(0);
			comboAnimated.setScaleY(0);
			comboAnimated.setVisible(true);
			
			imageViewForLargeHeart.setScaleX(0);
			imageViewForLargeHeart.setScaleY(0);
			imageViewForLargeHeart.setVisible(true);
			
			imageViewForLargeAnswerIcon.setScaleX(0);
			imageViewForLargeAnswerIcon.setScaleY(0);
			imageViewForLargeAnswerIcon.setVisible(true);
			
			textForScore.setScaleX(0);
			textForScore.setScaleY(0);
			
			vBoxForNextQuestionButtonAndProgressBar.setScaleX(0);
			vBoxForNextQuestionButtonAndProgressBar.setScaleY(0);
			
			textForQuestion.setScaleX(0);
			textForQuestion.setScaleY(0);
			
			imageViewForQuestionImage.setScaleX(0);
			imageViewForQuestionImage.setScaleY(0);
			
			gridPaneFor2TextAnswers.setScaleX(0);
			gridPaneFor2TextAnswers.setScaleY(0);
			
			radioButtonsFor2TextAnswers[0].setScaleX(0);
			radioButtonsFor2TextAnswers[0].setScaleY(0);
			radioButtonsFor2TextAnswers[1].setScaleX(0);
			radioButtonsFor2TextAnswers[1].setScaleY(0);
			
			answerIconsFor2TextAnswers[0].setScaleX(0);
			answerIconsFor2TextAnswers[0].setScaleY(0);
			answerIconsFor2TextAnswers[1].setScaleX(0);
			answerIconsFor2TextAnswers[1].setScaleY(0);
			
			gridPaneFor4TextAnswers.setScaleX(0);
			gridPaneFor4TextAnswers.setScaleY(0);
			
			radioButtonsFor4TextAnswers[0].setScaleX(0);
			radioButtonsFor4TextAnswers[0].setScaleY(0);
			radioButtonsFor4TextAnswers[1].setScaleX(0);
			radioButtonsFor4TextAnswers[1].setScaleY(0);
			radioButtonsFor4TextAnswers[2].setScaleX(0);
			radioButtonsFor4TextAnswers[2].setScaleY(0);
			radioButtonsFor4TextAnswers[3].setScaleX(0);
			radioButtonsFor4TextAnswers[3].setScaleY(0);
			
			answerIconsFor4TextAnswers[0].setScaleX(0);
			answerIconsFor4TextAnswers[0].setScaleY(0);
			answerIconsFor4TextAnswers[1].setScaleX(0);
			answerIconsFor4TextAnswers[1].setScaleY(0);
			answerIconsFor4TextAnswers[2].setScaleX(0);
			answerIconsFor4TextAnswers[2].setScaleY(0);
			answerIconsFor4TextAnswers[3].setScaleX(0);
			answerIconsFor4TextAnswers[3].setScaleY(0);
			
			stackPaneFor4ImageAnswers.setScaleX(0);
			stackPaneFor4ImageAnswers.setScaleY(0);
			
			imageViewFor4ImageAnswers[0].setScaleX(0);
			imageViewFor4ImageAnswers[0].setScaleY(0);
			imageViewFor4ImageAnswers[1].setScaleX(0);
			imageViewFor4ImageAnswers[1].setScaleY(0);
			imageViewFor4ImageAnswers[2].setScaleX(0);
			imageViewFor4ImageAnswers[2].setScaleY(0);
			imageViewFor4ImageAnswers[3].setScaleX(0);
			imageViewFor4ImageAnswers[3].setScaleY(0);
			
			answerIconsFor4ImageAnswers[0].setScaleX(0);
			answerIconsFor4ImageAnswers[0].setScaleY(0);
			answerIconsFor4ImageAnswers[1].setScaleX(0);
			answerIconsFor4ImageAnswers[1].setScaleY(0);
			answerIconsFor4ImageAnswers[2].setScaleX(0);
			answerIconsFor4ImageAnswers[2].setScaleY(0);
			answerIconsFor4ImageAnswers[3].setScaleX(0);
			answerIconsFor4ImageAnswers[3].setScaleY(0);
			
			//FINISHED GAME
			titleImageForFinishedGame.setTranslateY(-1.0 * (titleImageForFinishedGame.getLayoutY() + titleImageForFinishedGame.getBoundsInParent().getHeight() + 20));
			
			titleLabelForFinishedGame.setScaleX(0);
			titleLabelForFinishedGame.setScaleY(0);
			
			textForResultsDuration.setScaleX(0);
			textForResults.setScaleX(0);
			textForResults2.setScaleX(0);
			
			restartGameFromFinishedScreenButton.setScaleX(0);
			restartGameFromFinishedScreenButton.setScaleY(0);
			
			returnToWelcomeScreenFromFinishedGameScreenButton.setScaleX(0);
			returnToWelcomeScreenFromFinishedGameScreenButton.setScaleY(0);
		}
		else
		{
			woodPanelFor5IconsImage.setTranslateY(0);
			
			hBoxFor5Icons.setScaleX(1);
			hBoxFor5Icons.setScaleY(1);
			
			pauseGameIcon.setScaleX(1);
			pauseGameIcon.setScaleY(1);
			
			textForQuestionNumber.setScaleX(1);
			textForQuestionNumber.setScaleY(1);
			
			numberOfQuestionAnimated.setVisible(false);
			
			if(gameMode == GAMEMODE.ENDLESS_GAMEMODE)
			{
				hBoxForLives.setScaleX(1);
				hBoxForLives.setScaleY(1);
			}
			
			if(progressBarForCountdown.isVisible())
			{
				progressBarForCountdown.setScaleX(1);
				progressBarForCountdown.setScaleY(1);
			}
			if(textForTimePassed.isVisible())
			{
				textForTimePassed.setScaleX(1);
				textForTimePassed.setScaleY(1);
			}
			
			scoreAnimated.setScaleX(2);
			scoreAnimated.setScaleY(2);
			scoreAnimated.setVisible(false);
			
			comboAnimated.setScaleX(2);
			comboAnimated.setScaleY(2);
			comboAnimated.setVisible(false);
			
			imageViewForLargeHeart.setScaleX(10);
			imageViewForLargeHeart.setScaleY(10);
			imageViewForLargeHeart.setVisible(false);
			
			imageViewForLargeAnswerIcon.setScaleX(10);
			imageViewForLargeAnswerIcon.setScaleY(10);
			imageViewForLargeAnswerIcon.setVisible(false);
			
			textForScore.setScaleX(1);
			textForScore.setScaleY(1);
			
			vBoxForNextQuestionButtonAndProgressBar.setScaleX(1);
			vBoxForNextQuestionButtonAndProgressBar.setScaleY(1);
			
			textForQuestion.setScaleX(1);
			textForQuestion.setScaleY(1);
			
			imageViewForQuestionImage.setScaleX(1);
			imageViewForQuestionImage.setScaleY(1);
			
			gridPaneFor2TextAnswers.setScaleX(1);
			gridPaneFor2TextAnswers.setScaleY(1);
			
			radioButtonsFor2TextAnswers[0].setScaleX(1);
			radioButtonsFor2TextAnswers[0].setScaleY(1);
			radioButtonsFor2TextAnswers[1].setScaleX(1);
			radioButtonsFor2TextAnswers[1].setScaleY(1);
			
			answerIconsFor2TextAnswers[0].setScaleX(1);
			answerIconsFor2TextAnswers[0].setScaleY(1);
			answerIconsFor2TextAnswers[1].setScaleX(1);
			answerIconsFor2TextAnswers[1].setScaleY(1);
			
			gridPaneFor4TextAnswers.setScaleX(1);
			gridPaneFor4TextAnswers.setScaleY(1);
			
			radioButtonsFor4TextAnswers[0].setScaleX(1);
			radioButtonsFor4TextAnswers[0].setScaleY(1);
			radioButtonsFor4TextAnswers[1].setScaleX(1);
			radioButtonsFor4TextAnswers[1].setScaleY(1);
			radioButtonsFor4TextAnswers[2].setScaleX(1);
			radioButtonsFor4TextAnswers[2].setScaleY(1);
			radioButtonsFor4TextAnswers[3].setScaleX(1);
			radioButtonsFor4TextAnswers[3].setScaleY(1);
			
			answerIconsFor4TextAnswers[0].setScaleX(1);
			answerIconsFor4TextAnswers[0].setScaleY(1);
			answerIconsFor4TextAnswers[1].setScaleX(1);
			answerIconsFor4TextAnswers[1].setScaleY(1);
			answerIconsFor4TextAnswers[2].setScaleX(1);
			answerIconsFor4TextAnswers[2].setScaleY(1);
			answerIconsFor4TextAnswers[3].setScaleX(1);
			answerIconsFor4TextAnswers[3].setScaleY(1);
			
			stackPaneFor4ImageAnswers.setScaleX(1);
			stackPaneFor4ImageAnswers.setScaleY(1);
			
			imageViewFor4ImageAnswers[0].setScaleX(1);
			imageViewFor4ImageAnswers[0].setScaleY(1);
			imageViewFor4ImageAnswers[1].setScaleX(1);
			imageViewFor4ImageAnswers[1].setScaleY(1);
			imageViewFor4ImageAnswers[2].setScaleX(1);
			imageViewFor4ImageAnswers[2].setScaleY(1);
			imageViewFor4ImageAnswers[3].setScaleX(1);
			imageViewFor4ImageAnswers[3].setScaleY(1);
			
			answerIconsFor4ImageAnswers[0].setScaleX(1);
			answerIconsFor4ImageAnswers[0].setScaleY(1);
			answerIconsFor4ImageAnswers[1].setScaleX(1);
			answerIconsFor4ImageAnswers[1].setScaleY(1);
			answerIconsFor4ImageAnswers[2].setScaleX(1);
			answerIconsFor4ImageAnswers[2].setScaleY(1);
			answerIconsFor4ImageAnswers[3].setScaleX(1);
			answerIconsFor4ImageAnswers[3].setScaleY(1);
			
			//FINISHED GAME
			titleImageForFinishedGame.setTranslateY(0);
			
			titleLabelForFinishedGame.setScaleX(1);
			titleLabelForFinishedGame.setScaleY(1);
			
			textForResultsDuration.setScaleX(1);
			textForResults.setScaleX(1);
			textForResults2.setScaleX(1);
			
			restartGameFromFinishedScreenButton.setScaleX(1);
			restartGameFromFinishedScreenButton.setScaleY(1);
			
			returnToWelcomeScreenFromFinishedGameScreenButton.setScaleX(1);
			returnToWelcomeScreenFromFinishedGameScreenButton.setScaleY(1);
		}
		
		//SETUP POSITIONS INDEPENDENT OF ANIMATIONS-------------------------------------------------------------
		popUpMessage.setScaleX(0);
		popUpMessage.setScaleY(0);
		
		openPauseMenu = false;
		
		setFunctional2TextAnswers(true);
		setFunctional4TextAnswers(true);
		setFunctional4ImageAnswers(true);
		
		restoreGridPaneFor2TextAnswers();
		restoreGridPaneFor4TextAnswers();
		restoreStackPaneFor4ImageAnswers();
		
		setProgressFor2_5SecondsWait(0);
		
		textForCountdown.setScaleX(0);
		textForCountdown.setScaleY(0);
		
		welcomeScreenImage.setVisible(false);
		
		worldMapBackground.setOpacity(1);
		
		textForScore.setTranslateY(0);
		textForCombo.setTranslateY(0);
		
		nextQuestionButton.setDisable(true);
		
		textForCombo.setText("");
		
		pauseGameIcon.setDisable(false);
		pauseGameIcon.setVisible(true);
		textForQuestionNumber.setVisible(true);
		textForScore.setVisible(true);
		textForQuestion.setVisible(true);
		vBoxForNextQuestionButtonAndProgressBar.setVisible(false);
		
		titleImageForFinishedGame.setVisible(false);
		titleLabelForFinishedGame.setVisible(false);
		textForResultsDuration.setVisible(false);
		textForResults.setVisible(false);
		textForResults2.setVisible(false);
		restartGameFromFinishedScreenButton.setVisible(false);
		returnToWelcomeScreenFromFinishedGameScreenButton.setVisible(false);
		
		stackPaneForBigImage.setVisible(false);
		
		imageViewForQuestionImage.setVisible(false);
		gridPaneFor4TextAnswers.setVisible(false);
		gridPaneFor2TextAnswers.setVisible(false);
		stackPaneFor4ImageAnswers.setVisible(false);
		
		pauseGameIcon.setImage(PAUSE_ICON);
		
		removeBlurEffect();
		
		vBoxForPausedGame.setTranslateY(-1.0 * (vBoxForPausedGame.getLayoutY() + vBoxForPausedGame.getPrefHeight() + 20));
		vBoxForPausedGame.setVisible(false);
		vBoxForPausedGame.setDisable(true);
		
		if (OS == OsCheck.OSType.Windows) vBoxForSound.setTranslateX(stage.getWidth() - vBoxForSound.getLayoutX() + 20);
		else vBoxForSound.setTranslateX(-1.0 * (vBoxForSound.getLayoutX() + vBoxForSound.getPrefWidth() + 20));
		
		vBoxForSound.setVisible(false);
		
		updateStrings();
		
		if(animationsUsed == ANIMATIONS.NO) questionMaker.decideNextQuestion();
	}
	
	private void updateStrings()
	{
		popUpMessage.setText(languageResourceBundle.getString("pauseScreenAfterCurrentQuestion"));
				
		pauseGameTooltip.setText(languageResourceBundle.getString("pauseIconTooltip"));
		returnToGameButton.setText(languageResourceBundle.getString("returnToGameButton"));
		returnToGameButton.getTooltip().setText(languageResourceBundle.getString("returnToGameTooltip"));
		restartGameButton.setText(languageResourceBundle.getString("restartGameButton"));
		restartGameButton.getTooltip().setText(languageResourceBundle.getString("restartGameTooltip"));
		returnToGamePropertiesButton.setText(languageResourceBundle.getString("returnToGamePropertiesButton"));
		returnToGamePropertiesButton.getTooltip().setText(languageResourceBundle.getString("returnToGamePropertiesTooltip"));
		returnToWelcomeScreenButton.setText(languageResourceBundle.getString("returnToWelcomeScreenButton"));
		returnToWelcomeScreenButton.getTooltip().setText(languageResourceBundle.getString("returnToWelcomeScreenTooltip"));
		
		nextQuestionButton.setText(languageResourceBundle.getString("nextQuestionButton"));
		
		soundOptionsToolTip.setText(languageResourceBundle.getString("soundOptionsTooltip"));
		minimizeTooltip.setText(languageResourceBundle.getString("minimizeTooltip"));
		exitTooltip.setText(languageResourceBundle.getString("exitTooltip"));
		
		radioButtonsFor2TextAnswers[0].setText(languageResourceBundle.getString("radioButtonCorrect"));
		radioButtonsFor2TextAnswers[1].setText(languageResourceBundle.getString("radioButtonIncorrect"));
		
		titleLabelForFinishedGame.setText(languageResourceBundle.getString("titleForFinishedGame"));
		
		restartGameFromFinishedScreenButton.setText(languageResourceBundle.getString("restartGameButton"));
		restartGameFromFinishedScreenButton.getTooltip().setText(languageResourceBundle.getString("restartGameFromFinishedGameTooltip"));
		
		returnToWelcomeScreenFromFinishedGameScreenButton.setText(languageResourceBundle.getString("returnToWelcomeScreenButton"));
		returnToWelcomeScreenFromFinishedGameScreenButton.getTooltip().setText(languageResourceBundle.getString("returnToWelcomeScreenFromFinishedGameTooltip"));
		
		updateMasterVolumeText();
		updateMusicVolumeText();
		updateOtherVolumeText();
	}
	
	private void addBlurEffect()
	{
		worldMapBackground.setEffect(boxBlurForNodes);
		pauseGameIcon.setEffect(boxBlurForNodes);
		vBoxForNextQuestionButtonAndProgressBar.setEffect(boxBlurForNodes);
		if(hBoxForLives.isVisible()) hBoxForLives.setEffect(boxBlurForNodes);
		if(progressBarForCountdown.isVisible()) progressBarForCountdown.setEffect(boxBlurForNodes);
		if(imageViewForQuestionImage.isVisible()) imageViewForQuestionImage.setEffect(boxBlurForNodes);
		if(stackPaneFor4ImageAnswers.isVisible()) stackPaneFor4ImageAnswers.setEffect(boxBlurForNodes);
		if(gridPaneFor4TextAnswers.isVisible()) gridPaneFor4TextAnswers.setEffect(boxBlurForNodes);
		if(gridPaneFor2TextAnswers.isVisible()) gridPaneFor2TextAnswers.setEffect(boxBlurForNodes);
		textForCountdown.setEffect(boxBlurForNodes);
		if(numberOfQuestionAnimated.getScaleX() != 0) numberOfQuestionAnimated.setEffect(boxBlurForNodes);
		if(imageViewForLargeHeart.getScaleX() != 0) imageViewForLargeHeart.setEffect(boxBlurForNodes);
		
		scoreAnimated.setEffect(boxBlurForText);
		comboAnimated.setEffect(boxBlurForText);
		
		if(textForTimePassed.isVisible()) textForTimePassed.setEffect(boxBlurForText);
		textForQuestion.setEffect(boxBlurForText);
		textForQuestionNumber.setEffect(boxBlurForText);
		textForScore.setEffect(boxBlurForText);
		textForCombo.setEffect(boxBlurForText);
	}
	
	private void removeBlurEffect()
	{
		worldMapBackground.setEffect(null);
		pauseGameIcon.setEffect(null);
		vBoxForNextQuestionButtonAndProgressBar.setEffect(null);
		hBoxForLives.setEffect(null);
		progressBarForCountdown.setEffect(null);
		imageViewForQuestionImage.setEffect(null);
		stackPaneFor4ImageAnswers.setEffect(null);
		gridPaneFor4TextAnswers.setEffect(null);
		gridPaneFor2TextAnswers.setEffect(null);
		textForCountdown.setEffect(null);
		numberOfQuestionAnimated.setEffect(null);
		imageViewForLargeHeart.setEffect(null);
		
		scoreAnimated.setEffect(dropShadowForText);
		comboAnimated.setEffect(dropShadowForText);
		
		textForTimePassed.setEffect(dropShadowForText);
		textForQuestion.setEffect(dropShadowForText);
		textForQuestionNumber.setEffect(dropShadowForText);
		textForScore.setEffect(dropShadowForText);
		textForCombo.setEffect(dropShadowForText);
	}
	
	private void setReadableTimePassed()
	{
		StringBuilder sb = new StringBuilder();
		
		int seconds = getSecondsPassed() % 60;
		int minutes = getSecondsPassed() / 60;
		int hours = minutes / 60;
		
		if(hours != 0)
		{
			sb.append(hours + ":");
			minutes = minutes % 60;
		}
		if(minutes < 10) sb.append("0");
		sb.append(minutes + ":");
		if(seconds < 10) sb.append("0");
		sb.append(seconds);
		
		textForTimePassed.setText(sb.toString());
		textForTimePassed.setLayoutX(stage.getWidth() / 2.0 - textForTimePassed.getBoundsInLocal().getWidth() / 2.0);
	}
	
	private void setReadableRemainingTimeForTimeAttack()
	{
		StringBuilder sb = new StringBuilder();
		
		int minutes = getRemainingSecondsForTimeAttack() / 60;
		int seconds = getRemainingSecondsForTimeAttack() % 60;
		
		if(minutes != 0) sb.append(minutes + ":");
		if(minutes != 0 && seconds < 10) sb.append("0");
		
		sb.append(seconds);
		textForCountdown.setText(sb.toString());
		textForCountdown.setLayoutX(stage.getWidth() / 2.0 - textForCountdown.getBoundsInLocal().getWidth() / 2.0);
	}
	
	private void updateQuestionNumberText()
	{
		if(gameMode == GAMEMODE.CLASSIC_GAMEMODE)
		{
			if(getCurrentLanguage() == LANGUAGE.GREEK) textForQuestionNumber.setText(String.valueOf(getCurrentQuestionNumber()) + "η ερώτηση από τις " + getNumberOfQuestionsForClassic());
			else
			{
				int n = getCurrentQuestionNumber();
				if(n == 1) textForQuestionNumber.setText(String.valueOf(n) + "st question of  " + getNumberOfQuestionsForClassic());
				else if(n == 2) textForQuestionNumber.setText(String.valueOf(n) + "nd question of  " + getNumberOfQuestionsForClassic());
				else if(n == 3) textForQuestionNumber.setText(String.valueOf(n) + "rd question of  " + getNumberOfQuestionsForClassic());
				else textForQuestionNumber.setText(String.valueOf(n) + "th question of  " + getNumberOfQuestionsForClassic());
			}
		}
		else
		{
			if(getCurrentLanguage() == LANGUAGE.GREEK) textForQuestionNumber.setText(String.valueOf(getCurrentQuestionNumber()) + "η ερώτηση");
			else
			{
				int n = getCurrentQuestionNumber();
				if(n == 1) textForQuestionNumber.setText(String.valueOf(n) + "st question");
				else if(n == 2) textForQuestionNumber.setText(String.valueOf(n) + "nd question");
				else if(n == 3) textForQuestionNumber.setText(String.valueOf(n) + "rd question");
				else textForQuestionNumber.setText(String.valueOf(n) + "th question");
			}
		}
		
		if(OS != OsCheck.OSType.Windows) textForQuestionNumber.setLayoutX(stage.getWidth() - textForQuestionNumber.getBoundsInLocal().getWidth() - 0.0469 * stage.getWidth());
	}
	
	private void updateScoreText()
	{
		if(getCurrentLanguage() == LANGUAGE.GREEK) textForScore.setText("Σκορ: " + getCorrectAnswers() + "/" + getCurrentQuestionNumber() + "\n" + getScorePoints() + " πόντοι");
		else textForScore.setText("Score: " + getCorrectAnswers() + "/" + getCurrentQuestionNumber() + "\n" + getScorePoints() + " points");
		
		textForScore.setLayoutX(0.8625 * stage.getWidth() - textForScore.getBoundsInLocal().getWidth() * 0.95 / 2.0);
	}
	
	private void showComboMessage(int combo)
	{
		if(combo <= 17) comboAnimated.setText(languageResourceBundle.getString("x" + combo));
		else comboAnimated.setText("x" + combo);
		
		comboAnimated.setLayoutX(stage.getWidth() / 2.0 - comboAnimated.getBoundsInLocal().getWidth() / 2.0);
		
		if(animationsUsed != ANIMATIONS.NO) timelineForComboAnimated.playFromStart();
		else timelineForComboNoAnimated.playFromStart();
	}
	
	private int newScorePoints(boolean isCorrect)
	{
		double scorePoints;
		
		if(isCorrect)
		{
			if(gameMode == GAMEMODE.TIME_ATTACK_GAMEMODE)
			{
				int seconds = answerEvaluator.getSecondsLastQuestionTook();
				
				if(questionMaker.getTypeOfQuestion() == 1)
				{
					if(seconds <= 10) scorePoints = 50 + 50 * (10 - seconds) / 10.0;
					else scorePoints = 50;
				}
				else if(questionMaker.getTypeOfQuestion() == 2)
				{
					if(seconds <= 10) scorePoints = 150 + 50 * (10 - seconds) / 10.0;
					else scorePoints = 150;
				}
				else
				{
					if(seconds <= 10) scorePoints = 350 + 50 * (10 - seconds) / 10.0;
					else scorePoints = 350;
				}
			}
			else
			{
				if(isQuestionCountdownEnabled())
				{
					if(questionMaker.getTypeOfQuestion() == 1) scorePoints = 50 + 50 * answerEvaluator.getCurrentProgressOfProgressBar();
					else if(questionMaker.getTypeOfQuestion() == 2) scorePoints = 150 + 50 * answerEvaluator.getCurrentProgressOfProgressBar();
					else scorePoints = 350 + 50 * answerEvaluator.getCurrentProgressOfProgressBar();
				}
				else
				{
					if(questionMaker.getTypeOfQuestion() == 1) scorePoints = 75;
					else if(questionMaker.getTypeOfQuestion() == 2) scorePoints = 175;
					else scorePoints = 375;
				}
			}
			
			switch (getCombo())
			{
				case 0:case 1:case 2:break;
				case 3:case 4: scorePoints *= 1.2;break;
				case 5:case 6: scorePoints *= 1.4;break;
				case 7:case 8: scorePoints *= 1.6;break;
				case 9:case 10: scorePoints *= 1.8;break;
				default:scorePoints *= 2.0;break;
			}
			
			if(questionMaker.isEasyQuestion()) scorePoints *= 0.6;
			else scorePoints *= 1.4;
		}
		else
		{
			if(questionMaker.getTypeOfQuestion() == 1) scorePoints = -50;
			else scorePoints = -40;
			
			if(questionMaker.isEasyQuestion()) scorePoints *= 1.4;
			else scorePoints *= 0.6;
		}
		
		return (int) Math.round(scorePoints);
	}
	
	private void startTimers()
	{
		if(isQuestionCountdownEnabled()) startQuestionCountdown();
		
		if(progressBarForCountdown.getProgress() == 1)
		{
			if(gameMode == GAMEMODE.TIME_ATTACK_GAMEMODE) startTimeAttackCountdown();
			else startTimePassedCounting();
			
			timelineToCountMilliseconds.playFromStart();
		}
		else
		{
			if(gameMode == GAMEMODE.TIME_ATTACK_GAMEMODE) resumeTimeAttackCountdown();
			else resumeTimePassedCounting();
			
			timelineToCountMilliseconds.play();
		}
	}
	
	private void stopAllTimers()
	{
		if(timelineFor2_5SecondsWaitForNextQuestion != null) timelineFor2_5SecondsWaitForNextQuestion.stop();
		
		if(timelineForTimePassedCount != null) timelineForTimePassedCount.stop();
		
		if(timelineForQuestionCountdown != null) timelineForQuestionCountdown.stop();
		if(timelineToCountSecondsForQuestionCountdown != null) timelineToCountSecondsForQuestionCountdown.stop();
		
		if(timelineFor1MinuteProgressbarForTimeAttack != null) timelineFor1MinuteProgressbarForTimeAttack.stop();
		if(timelineFor2MinutesProgressbarForTimeAttack != null) timelineFor2MinutesProgressbarForTimeAttack.stop();
		if(timelineFor5MinutesProgressbarForTimeAttack != null) timelineFor5MinutesProgressbarForTimeAttack.stop();
		if(timelineFor10MinutesProgressbarForTimeAttack != null) timelineFor10MinutesProgressbarForTimeAttack.stop();
		if(timelineToCountSecondsForTimeAttack != null) timelineToCountSecondsForTimeAttack.stop();
		
		timelineToCountMilliseconds.stop();
	}
	
	private void startTextForComboAnimation(int combo)
	{
		if(textForCombo.getScaleX() != 1.0)
		{
			scaleTransitionForTextForCombo.setDuration(Duration.millis(100));
			scaleTransitionForTextForCombo.setCycleCount(1);
			scaleTransitionForTextForCombo.setAutoReverse(false);
			scaleTransitionForTextForCombo.setFromX(textForCombo.getScaleX());
			scaleTransitionForTextForCombo.setFromY(textForCombo.getScaleY());
			scaleTransitionForTextForCombo.setToX(1);
			scaleTransitionForTextForCombo.setToY(1);
			scaleTransitionForTextForCombo.setOnFinished(e ->
			{
				scaleTransitionForTextForCombo.setOnFinished(ev -> {});
				
				startTextForComboAnimation(combo);
			});
			scaleTransitionForTextForCombo.playFromStart();
		}
		else
		{
			scaleTransitionForTextForCombo.setCycleCount(Animation.INDEFINITE);
			scaleTransitionForTextForCombo.setAutoReverse(true);
			scaleTransitionForTextForCombo.setFromX(1);
			scaleTransitionForTextForCombo.setFromY(1);
			
			if(combo < 5)
			{
				scaleTransitionForTextForCombo.setDuration(Duration.millis(1200));
				scaleTransitionForTextForCombo.setToX(1.2);
				scaleTransitionForTextForCombo.setToY(1.2);
			}
			else if(combo < 10)
			{
				scaleTransitionForTextForCombo.setDuration(Duration.millis(1000));
				scaleTransitionForTextForCombo.setToX(1.4);
				scaleTransitionForTextForCombo.setToY(1.4);
			}
			else if(combo < 15)
			{
				scaleTransitionForTextForCombo.setDuration(Duration.millis(800));
				scaleTransitionForTextForCombo.setToX(1.6);
				scaleTransitionForTextForCombo.setToY(1.6);
			}
			else
			{
				scaleTransitionForTextForCombo.setDuration(Duration.millis(600));
				scaleTransitionForTextForCombo.setToX(1.8);
				scaleTransitionForTextForCombo.setToY(1.8);
			}
			scaleTransitionForTextForCombo.playFromStart();
		}
	}
	
	private void startTimePassedCounting()
	{
		timelineForTimePassedCount.playFromStart();
	}
	
	private void resumeTimePassedCounting()
	{
		timelineForTimePassedCount.play();
	}
	
	private void pauseTimePassedCounting()
	{
		timelineForTimePassedCount.pause();
	}
	
	private void startQuestionCountdown()
	{
		setCurrentProgressForProgressbar(1000);
		progressBarForCountdown.setId("normal");
		progressBarForCountdown.setProgress(1);
		
		if(animationsUsed == ANIMATIONS.NO)
		{
			textForCountdown.setScaleX(1);
			textForCountdown.setScaleY(1);
		}
		
		playClockTickingSound();
		
		timelineForQuestionCountdown.playFromStart();
		timelineToCountSecondsForQuestionCountdown.playFromStart();
	}
	
	private void stopQuestionCountdown()
	{
		timelineToCountSecondsForQuestionCountdown.stop();
		timelineForQuestionCountdown.stop();
		
		progressBarForCountdown.setProgress(0);
		
		stopClockTickingSound();
		
		if(animationsUsed != ANIMATIONS.NO) timelineToCloseTextForCountdown.playFromStart();
		else
		{
			textForCountdown.setScaleX(0);
			textForCountdown.setScaleY(0);
		}
	}
	
	private void startTimeAttackCountdown()
	{
		if(animationsUsed == ANIMATIONS.NO)
		{
			textForCountdown.setScaleX(1);
			textForCountdown.setScaleY(1);
		}
		
		if(getDurationInMinutesForTimeAttack() == 1) timelineFor1MinuteProgressbarForTimeAttack.playFromStart();
		else if(getDurationInMinutesForTimeAttack() == 2) timelineFor2MinutesProgressbarForTimeAttack.playFromStart();
		else if(getDurationInMinutesForTimeAttack() == 5) timelineFor5MinutesProgressbarForTimeAttack.playFromStart();
		else timelineFor10MinutesProgressbarForTimeAttack.playFromStart();
		
		timelineToCountSecondsForTimeAttack.playFromStart();
	}
	
	private void resumeTimeAttackCountdown()
	{
		if(getDurationInMinutesForTimeAttack() == 1) timelineFor1MinuteProgressbarForTimeAttack.play();
		else if(getDurationInMinutesForTimeAttack() == 2) timelineFor2MinutesProgressbarForTimeAttack.play();
		else if(getDurationInMinutesForTimeAttack() == 5) timelineFor5MinutesProgressbarForTimeAttack.play();
		else timelineFor10MinutesProgressbarForTimeAttack.play();
		
		timelineToCountSecondsForTimeAttack.play();
	}
	
	private void pauseTimeAttackCountdown()
	{
		if(getDurationInMinutesForTimeAttack() == 1) timelineFor1MinuteProgressbarForTimeAttack.pause();
		else if(getDurationInMinutesForTimeAttack() == 2) timelineFor2MinutesProgressbarForTimeAttack.pause();
		else if(getDurationInMinutesForTimeAttack() == 5) timelineFor5MinutesProgressbarForTimeAttack.pause();
		else timelineFor10MinutesProgressbarForTimeAttack.pause();
		
		timelineToCountSecondsForTimeAttack.pause();
	}
	
	private void startTimelineFor2_5SecondsWait()
	{
		if(!vBoxForPausedGame.isVisible())
		{
			setProgressFor2_5SecondsWait(0);
			progressBarFor2_5SecondsWaitForNextQuestion.setProgress(0);
			
			vBoxForNextQuestionButtonAndProgressBar.setVisible(true);
			
			if(animationsUsed != ANIMATIONS.NO)
			{
				scaleTransitionForVBoxForNextQuestion.setToX(1);
				scaleTransitionForVBoxForNextQuestion.setToY(1);
				scaleTransitionForVBoxForNextQuestion.setOnFinished(e ->
				{
					scaleTransitionForVBoxForNextQuestion.setOnFinished(ev ->
					{});
					timelineFor2_5SecondsWaitForNextQuestion.playFromStart();
				});
				scaleTransitionForVBoxForNextQuestion.playFromStart();
			}
			else timelineFor2_5SecondsWaitForNextQuestion.playFromStart();
		}
	}
	
	private void stopTimelineFor2_5SecondsWait()
	{
		timelineFor2_5SecondsWaitForNextQuestion.stop();
	}
	
	public void pauseTimelineFor2_5SecondsWait()
	{
		timelineFor2_5SecondsWaitForNextQuestion.pause();
	}
	
	public void resumeTimelineFor2_5SecondsWait()
	{
		timelineFor2_5SecondsWaitForNextQuestion.play();
	}
	
	private void setFunctional2TextAnswers(boolean functional)
	{
		if(functional)
		{
			answerIconsFor2TextAnswers[0].setImage(null);
			answerIconsFor2TextAnswers[1].setImage(null);
			
			radioButtonsFor2TextAnswers[0].setDisable(false);
			radioButtonsFor2TextAnswers[1].setDisable(false);
		}
		else
		{
			radioButtonsFor2TextAnswers[0].setDisable(true);
			radioButtonsFor2TextAnswers[1].setDisable(true);
		}
	}
	
	private void setFunctional4TextAnswers(boolean functional)
	{
		if(functional)
		{
			answerIconsFor4TextAnswers[0].setImage(null);
			answerIconsFor4TextAnswers[1].setImage(null);
			answerIconsFor4TextAnswers[2].setImage(null);
			answerIconsFor4TextAnswers[3].setImage(null);
			
			radioButtonsFor4TextAnswers[0].setDisable(false);
			radioButtonsFor4TextAnswers[1].setDisable(false);
			radioButtonsFor4TextAnswers[2].setDisable(false);
			radioButtonsFor4TextAnswers[3].setDisable(false);
		}
		else
		{
			radioButtonsFor4TextAnswers[0].setDisable(true);
			radioButtonsFor4TextAnswers[1].setDisable(true);
			radioButtonsFor4TextAnswers[2].setDisable(true);
			radioButtonsFor4TextAnswers[3].setDisable(true);
		}
	}
	
	private void setFunctional4ImageAnswers(boolean functional)
	{
		if(functional)
		{
			answerIconsFor4ImageAnswers[0].setImage(null);
			answerIconsFor4ImageAnswers[1].setImage(null);
			answerIconsFor4ImageAnswers[2].setImage(null);
			answerIconsFor4ImageAnswers[3].setImage(null);
			
			imageViewFor4ImageAnswers[0].setDisable(false);
			imageViewFor4ImageAnswers[1].setDisable(false);
			imageViewFor4ImageAnswers[2].setDisable(false);
			imageViewFor4ImageAnswers[3].setDisable(false);
		}
		else
		{
			imageViewFor4ImageAnswers[0].setDisable(true);
			imageViewFor4ImageAnswers[1].setDisable(true);
			imageViewFor4ImageAnswers[2].setDisable(true);
			imageViewFor4ImageAnswers[3].setDisable(true);
		}
	}
	
	private void restoreGridPaneFor2TextAnswers()
	{
		radioButtonsFor2TextAnswers[0].setStyle("");
		radioButtonsFor2TextAnswers[1].setStyle("");
		
		toggleGroupFor2PossibleAnswers.selectToggle(null);
	}
	
	private void restoreGridPaneFor4TextAnswers()
	{
		radioButtonsFor4TextAnswers[0].setStyle("");
		radioButtonsFor4TextAnswers[1].setStyle("");
		radioButtonsFor4TextAnswers[2].setStyle("");
		radioButtonsFor4TextAnswers[3].setStyle("");
		
		toggleGroupFor4PossibleAnswers.selectToggle(null);
	}
	
	private void restoreStackPaneFor4ImageAnswers()
	{
		imageViewFor4ImageAnswers[0].setStyle("");
		imageViewFor4ImageAnswers[1].setStyle("");
		imageViewFor4ImageAnswers[2].setStyle("");
		imageViewFor4ImageAnswers[3].setStyle("");
	}
	
	private void showQuestionImage(boolean up)
	{
		if(textForTimePassed.isVisible())
		{
			if(!up) imageViewForQuestionImage.setLayoutY(0.3750 * stage.getHeight());
			else imageViewForQuestionImage.setLayoutY(0.2824 * stage.getHeight());
		}
		else
		{
			if(!up) imageViewForQuestionImage.setLayoutY(0.3565 * stage.getHeight());
			else imageViewForQuestionImage.setLayoutY(0.2639 * stage.getHeight());
		}
		
		if(!imageViewForQuestionImage.isVisible())
		{
			if (animationsUsed != ANIMATIONS.NO)
			{
				imageViewForQuestionImage.setVisible(true);
				
				scaleTransitionForQuestionImage.setToX(1);
				scaleTransitionForQuestionImage.setToY(1);
				
				scaleTransitionForQuestionImage.playFromStart();
			}
			else imageViewForQuestionImage.setVisible(true);
		}
	}
	
	private void hideQuestionImage()
	{
		if(imageViewForQuestionImage.isVisible())
		{
			if (animationsUsed != ANIMATIONS.NO)
			{
				scaleTransitionForQuestionImage.setToX(0);
				scaleTransitionForQuestionImage.setToY(0);
				scaleTransitionForQuestionImage.setOnFinished(e ->
				{
					scaleTransitionForQuestionImage.setOnFinished(ev -> {});
					
					imageViewForQuestionImage.setVisible(false);
				});
				
				scaleTransitionForQuestionImage.playFromStart();
			}
			else imageViewForQuestionImage.setVisible(false);
		}
	}
	
	private void showGridPaneFor2TextAnswers()
	{
		if(!gridPaneFor2TextAnswers.isVisible())
		{
			if(animationsUsed != ANIMATIONS.NO)
			{
				if(gridPaneFor4TextAnswers.isVisible())
				{
					scaleTransitionForGridPaneFor4TextAnswers.setToX(0);
					scaleTransitionForGridPaneFor4TextAnswers.setToY(0);
					
					scaleTransitionForGridPaneFor4TextAnswers.setOnFinished(e ->
					{
						scaleTransitionForGridPaneFor4TextAnswers.setOnFinished(ev -> {});
						
						gridPaneFor4TextAnswers.setVisible(false);
						gridPaneFor2TextAnswers.setVisible(true);
						
						scaleTransitionForGridPaneFor2TextAnswers.setToX(1);
						scaleTransitionForGridPaneFor2TextAnswers.setToY(1);
						
						scaleTransitionForGridPaneFor2TextAnswers.setOnFinished(eve ->
						{
							scaleTransitionForGridPaneFor2TextAnswers.setOnFinished(even ->{});
							
							timelineToShow2TextAnswers.playFromStart();
						});
						
						playMaximizeSound();
						scaleTransitionForGridPaneFor2TextAnswers.playFromStart();
					});
					playMinimizeSound();
					scaleTransitionForGridPaneFor4TextAnswers.playFromStart();
				}
				else if(stackPaneFor4ImageAnswers.isVisible())
				{
					scaleTransitionForStackPaneFor4ImageAnswers.setToX(0);
					scaleTransitionForStackPaneFor4ImageAnswers.setToY(0);
					
					scaleTransitionForStackPaneFor4ImageAnswers.setOnFinished(e ->
					{
						scaleTransitionForStackPaneFor4ImageAnswers.setOnFinished(ev -> {});
						
						stackPaneFor4ImageAnswers.setVisible(false);
						gridPaneFor2TextAnswers.setVisible(true);
						
						scaleTransitionForGridPaneFor2TextAnswers.setToX(1);
						scaleTransitionForGridPaneFor2TextAnswers.setToY(1);
						
						scaleTransitionForGridPaneFor2TextAnswers.setOnFinished(eve ->
						{
							scaleTransitionForGridPaneFor2TextAnswers.setOnFinished(even ->{});
							
							timelineToShow2TextAnswers.playFromStart();
						});
						
						playMaximizeSound();
						scaleTransitionForGridPaneFor2TextAnswers.playFromStart();
					});
					playMinimizeSound();
					scaleTransitionForStackPaneFor4ImageAnswers.playFromStart();
				}
				else
				{
					gridPaneFor2TextAnswers.setVisible(true);
					
					scaleTransitionForGridPaneFor2TextAnswers.setToX(1);
					scaleTransitionForGridPaneFor2TextAnswers.setToY(1);
					
					scaleTransitionForGridPaneFor2TextAnswers.setOnFinished(e ->
					{
						scaleTransitionForGridPaneFor2TextAnswers.setOnFinished(ev ->{});
						
						timelineToShow2TextAnswers.playFromStart();
					});
					
					playMaximizeSound();
					scaleTransitionForGridPaneFor2TextAnswers.playFromStart();
				}
			}
			else
			{
				gridPaneFor2TextAnswers.setVisible(true);
				gridPaneFor4TextAnswers.setVisible(false);
				stackPaneFor4ImageAnswers.setVisible(false);
				
				startTimers();
				pauseGameIcon.setDisable(false);
			}
		}
		else if(animationsUsed != ANIMATIONS.NO) timelineToShow2TextAnswers.playFromStart();
		else
		{
			startTimers();
			pauseGameIcon.setDisable(false);
		}
	}
	
	private void showGridPaneFor4TextAnswers()
	{
		if(!gridPaneFor4TextAnswers.isVisible())
		{
			if(animationsUsed != ANIMATIONS.NO)
			{
				if(gridPaneFor2TextAnswers.isVisible())
				{
					scaleTransitionForGridPaneFor2TextAnswers.setToX(0);
					scaleTransitionForGridPaneFor2TextAnswers.setToY(0);
					
					scaleTransitionForGridPaneFor2TextAnswers.setOnFinished(e ->
					{
						scaleTransitionForGridPaneFor2TextAnswers.setOnFinished(ev -> {});
						
						gridPaneFor2TextAnswers.setVisible(false);
						gridPaneFor4TextAnswers.setVisible(true);
						
						scaleTransitionForGridPaneFor4TextAnswers.setToX(1);
						scaleTransitionForGridPaneFor4TextAnswers.setToY(1);
						
						scaleTransitionForGridPaneFor4TextAnswers.setOnFinished(eve ->
						{
							scaleTransitionForGridPaneFor4TextAnswers.setOnFinished(even ->{});
							
							timelineToShow4TextAnswers.playFromStart();
						});
						
						playMaximizeSound();
						scaleTransitionForGridPaneFor4TextAnswers.playFromStart();
					});
					playMinimizeSound();
					scaleTransitionForGridPaneFor2TextAnswers.playFromStart();
				}
				else if(stackPaneFor4ImageAnswers.isVisible())
				{
					scaleTransitionForStackPaneFor4ImageAnswers.setToX(0);
					scaleTransitionForStackPaneFor4ImageAnswers.setToY(0);
					
					scaleTransitionForStackPaneFor4ImageAnswers.setOnFinished(e ->
					{
						scaleTransitionForStackPaneFor4ImageAnswers.setOnFinished(ev -> {});
						
						stackPaneFor4ImageAnswers.setVisible(false);
						gridPaneFor4TextAnswers.setVisible(true);
						
						scaleTransitionForGridPaneFor4TextAnswers.setToX(1);
						scaleTransitionForGridPaneFor4TextAnswers.setToY(1);
						
						scaleTransitionForGridPaneFor4TextAnswers.setOnFinished(eve ->
						{
							scaleTransitionForGridPaneFor4TextAnswers.setOnFinished(even ->{});
							
							timelineToShow4TextAnswers.playFromStart();
						});
						
						playMaximizeSound();
						scaleTransitionForGridPaneFor4TextAnswers.playFromStart();
					});
					playMinimizeSound();
					scaleTransitionForStackPaneFor4ImageAnswers.playFromStart();
				}
				else
				{
					gridPaneFor4TextAnswers.setVisible(true);
					
					scaleTransitionForGridPaneFor4TextAnswers.setToX(1);
					scaleTransitionForGridPaneFor4TextAnswers.setToY(1);
					
					scaleTransitionForGridPaneFor4TextAnswers.setOnFinished(eve ->
					{
						scaleTransitionForGridPaneFor4TextAnswers.setOnFinished(even ->{});
						
						timelineToShow4TextAnswers.playFromStart();
					});
					
					playMaximizeSound();
					scaleTransitionForGridPaneFor4TextAnswers.playFromStart();
				}
			}
			else
			{
				gridPaneFor2TextAnswers.setVisible(false);
				gridPaneFor4TextAnswers.setVisible(true);
				stackPaneFor4ImageAnswers.setVisible(false);
				
				startTimers();
				pauseGameIcon.setDisable(false);
			}
		}
		else if(animationsUsed != ANIMATIONS.NO) timelineToShow4TextAnswers.playFromStart();
		else
		{
			startTimers();
			pauseGameIcon.setDisable(false);
		}
	}
	
	private void showStackPaneFor4ImageAnswers()
	{
		if(!stackPaneFor4ImageAnswers.isVisible())
		{
			if(animationsUsed != ANIMATIONS.NO)
			{
				if(gridPaneFor2TextAnswers.isVisible())
				{
					scaleTransitionForGridPaneFor2TextAnswers.setToX(0);
					scaleTransitionForGridPaneFor2TextAnswers.setToY(0);
					
					scaleTransitionForGridPaneFor2TextAnswers.setOnFinished(e ->
					{
						scaleTransitionForGridPaneFor2TextAnswers.setOnFinished(ev -> {});
						
						gridPaneFor2TextAnswers.setVisible(false);
						stackPaneFor4ImageAnswers.setVisible(true);
						
						scaleTransitionForStackPaneFor4ImageAnswers.setToX(1);
						scaleTransitionForStackPaneFor4ImageAnswers.setToY(1);
						
						scaleTransitionForStackPaneFor4ImageAnswers.setOnFinished(eve ->
						{
							scaleTransitionForStackPaneFor4ImageAnswers.setOnFinished(even ->{});
							
							timelineToShow4ImageAnswers.playFromStart();
						});
						
						playMaximizeSound();
						scaleTransitionForStackPaneFor4ImageAnswers.playFromStart();
					});
					playMinimizeSound();
					scaleTransitionForGridPaneFor2TextAnswers.playFromStart();
				}
				else if(gridPaneFor4TextAnswers.isVisible())
				{
					scaleTransitionForGridPaneFor4TextAnswers.setToX(0);
					scaleTransitionForGridPaneFor4TextAnswers.setToY(0);
					
					scaleTransitionForGridPaneFor4TextAnswers.setOnFinished(e ->
					{
						scaleTransitionForGridPaneFor4TextAnswers.setOnFinished(ev -> {});
						
						gridPaneFor4TextAnswers.setVisible(false);
						stackPaneFor4ImageAnswers.setVisible(true);
						
						scaleTransitionForStackPaneFor4ImageAnswers.setToX(1);
						scaleTransitionForStackPaneFor4ImageAnswers.setToY(1);
						
						scaleTransitionForStackPaneFor4ImageAnswers.setOnFinished(eve ->
						{
							scaleTransitionForStackPaneFor4ImageAnswers.setOnFinished(even ->{});
							
							timelineToShow4ImageAnswers.playFromStart();
						});
						
						playMaximizeSound();
						scaleTransitionForStackPaneFor4ImageAnswers.playFromStart();
					});
					playMinimizeSound();
					scaleTransitionForGridPaneFor4TextAnswers.playFromStart();
				}
				else
				{
					stackPaneFor4ImageAnswers.setVisible(true);
					
					scaleTransitionForStackPaneFor4ImageAnswers.setToX(1);
					scaleTransitionForStackPaneFor4ImageAnswers.setToY(1);
					
					scaleTransitionForStackPaneFor4ImageAnswers.setOnFinished(eve ->
					{
						scaleTransitionForStackPaneFor4ImageAnswers.setOnFinished(even ->{});
						
						timelineToShow4ImageAnswers.playFromStart();
					});
					
					playMaximizeSound();
					scaleTransitionForStackPaneFor4ImageAnswers.playFromStart();
				}
			}
			else
			{
				gridPaneFor2TextAnswers.setVisible(false);
				gridPaneFor4TextAnswers.setVisible(false);
				stackPaneFor4ImageAnswers.setVisible(true);
				
				startTimers();
				pauseGameIcon.setDisable(false);
			}
		}
		else if(animationsUsed != ANIMATIONS.NO) timelineToShow4ImageAnswers.playFromStart();
		else
		{
			startTimers();
			pauseGameIcon.setDisable(false);
		}
	}
	
	private void showPauseMenu()
	{
		openPauseMenu = false;
		
		addBlurEffect();
		
		vBoxForPausedGame.setVisible(true);
		if(animationsUsed != ANIMATIONS.NO)
		{
			scaleTransitionForTextForCombo.pause();
			
			translateTransitionFoVBoxForPausedGame.setToY(0);
			translateTransitionFoVBoxForPausedGame.setOnFinished(ev ->
			{
				translateTransitionFoVBoxForPausedGame.setOnFinished(eve ->{});
				vBoxForPausedGame.setDisable(false);
			});
			playSlideSound();
			translateTransitionFoVBoxForPausedGame.playFromStart();
		}
		else
		{
			vBoxForPausedGame.setTranslateY(0);
			vBoxForPausedGame.setDisable(false);
		}
	}
	
	private void saveGameStatistics()
	{
		currentGame.setPlayerName(getCurrentPlayer().getOriginalName());
		currentGame.setGameStartedTime(gameStartedTime);
		
		if(gameMode != GAMEMODE.TIME_ATTACK_GAMEMODE)
		{
			currentGame.setGameDurationInSeconds(getSecondsPassed());
			
			if(gameMode == GAMEMODE.ENDLESS_GAMEMODE) currentGame.setLivesForEndless(getLivesForEndless());
		}
		else currentGame.setGameDurationInSeconds(getDurationInSecondsForTimeAttack());
		
		currentGame.setIsCountdownEnabled(isQuestionCountdownEnabled());
		
		currentGame.setGameMode(gameMode);
		currentGame.setDifficultyLevel(getDifficultyLevel());
		currentGame.setNumberOfAllQuestions(getCurrentQuestionNumber());
		currentGame.setNumberOfCorrectQuestions(getCorrectAnswers());
		currentGame.setScorePoints(getScorePoints());
		currentGame.setMaxCombo(getMaxCombo());
		
		currentGame.setAverageAnswerTime(getMillisecondsPassed() / getCurrentQuestionNumber() / 1000.0);
		
		games.add(0, currentGame);
		
		FilesIO.writeGameScores();
	}
	
	private void updatePlayerStatistics(Player cp)
	{
		cp.setAverageAnswerTime((cp.getAverageAnswerTime() * cp.getTotalQuestionsAnswered() + getMillisecondsPassed() / 1000.0) /
				(cp.getTotalQuestionsAnswered() + getCurrentQuestionNumber()));
		
		cp.setTotalTimePlayed(cp.getTotalTimePlayed() + (int) getMillisecondsPassed() / 1000);
		
		if(getScorePoints() > cp.getHighestScorePoints()) cp.setHighestScorePoints(getScorePoints());
		cp.setTotalCorrectAnswers(cp.getTotalCorrectAnswers() + getCorrectAnswers());
		cp.setTotalQuestionsAnswered(cp.getTotalQuestionsAnswered() + getCurrentQuestionNumber());
		
		if(getMaxCombo() > cp.getMaxCombo()) cp.setMaxCombo(getMaxCombo());
		
		FilesIO.writePlayersFile();
	}
	
	private void finishGame()
	{
		stopAllTimers();
		
		updatePlayerStatistics(getCurrentPlayer());
		saveGameStatistics();
		
		if(gameMode != GAMEMODE.TIME_ATTACK_GAMEMODE) textForResultsDuration.setText(getResultsDurationMessage());
		
		textForResults.setText(getResultsMessage());
		textForResults2.setText(getResults2Message());
		
		if(animationsUsed != ANIMATIONS.NO) timelineToFinishGame.playFromStart();
		else
		{
			pauseGameIcon.setVisible(false);
			textForQuestionNumber.setVisible(false);
			textForScore.setVisible(false);
			textForTimePassed.setVisible(false);
			progressBarForCountdown.setVisible(false);
			hBoxForLives.setVisible(false);
			textForQuestion.setVisible(false);
			imageViewForQuestionImage.setVisible(false);
			gridPaneFor2TextAnswers.setVisible(false);
			gridPaneFor4TextAnswers.setVisible(false);
			stackPaneFor4ImageAnswers.setVisible(false);
			vBoxForNextQuestionButtonAndProgressBar.setVisible(false);
			
			titleImageForFinishedGame.setTranslateY(0);
			titleImageForFinishedGame.setVisible(true);
			
			titleLabelForFinishedGame.setScaleX(1);
			titleLabelForFinishedGame.setScaleY(1);
			titleLabelForFinishedGame.setVisible(true);
			
			if(gameMode != GAMEMODE.TIME_ATTACK_GAMEMODE)
			{
				textForResultsDuration.setScaleX(1);
				textForResultsDuration.setVisible(true);
			}
			
			comboAnimated.setVisible(false);
			timelineForComboNoAnimated.stop();
			textForCombo.setText("");
			
			textForResults.setScaleX(1);
			textForResults.setVisible(true);
			
			textForResults2.setScaleX(1);
			textForResults2.setVisible(true);
			
			restartGameFromFinishedScreenButton.setScaleX(1);
			restartGameFromFinishedScreenButton.setScaleY(1);
			restartGameFromFinishedScreenButton.setVisible(true);
			
			returnToWelcomeScreenFromFinishedGameScreenButton.setScaleX(1);
			returnToWelcomeScreenFromFinishedGameScreenButton.setScaleY(1);
			returnToWelcomeScreenFromFinishedGameScreenButton.setVisible(true);
			
			if(isGameWon()) playGameWonSound();
			else playGameLostSound();
		}
	}
	
	private boolean isGameWon()
	{
		if(gameMode == GAMEMODE.CLASSIC_GAMEMODE)
		{
			return getCorrectAnswers() > getNumberOfQuestionsForClassic() / 2;
		}
		else if(gameMode == GAMEMODE.TIME_ATTACK_GAMEMODE)
		{
			return !(getDurationInMinutesForTimeAttack() == 1 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 8 ||
					getDurationInMinutesForTimeAttack() == 1 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 4 ||
					getDurationInMinutesForTimeAttack() == 2 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 16 ||
					getDurationInMinutesForTimeAttack() == 2 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 8 ||
					getDurationInMinutesForTimeAttack() == 5 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 35 ||
					getDurationInMinutesForTimeAttack() == 5 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 25 ||
					getDurationInMinutesForTimeAttack() == 10 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 60 ||
					getDurationInMinutesForTimeAttack() == 10 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 40);
		}
		else
		{
			return !(getLivesForEndless() == 1 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 10 ||
					getLivesForEndless() == 1 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 5 ||
					getLivesForEndless() == 3 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 30 ||
					getLivesForEndless() == 3 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 15 ||
					getLivesForEndless() == 5 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 45 ||
					getLivesForEndless() == 5 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 25);
		}
	}
	
	private String getResultsDurationMessage()
	{
		int seconds = getSecondsPassed() % 60;
		int minutes = getSecondsPassed() / 60;
		int hours = minutes / 60;
		
		StringBuilder sb = new StringBuilder();
		
		if(getCurrentLanguage() == LANGUAGE.GREEK)
		{
			sb.append("Το παιχνίδι ");
			
			if(gameMode == GAMEMODE.CLASSIC_GAMEMODE) sb.append("των " + getNumberOfQuestionsForClassic() + " ερωτήσεων ");
			
			sb.append("διήρκησε ");
			
			if(hours != 0)
			{
				sb.append(hours);
				
				minutes = minutes % 60;
				
				if(hours == 1) sb.append(" ώρα");
				else sb.append(" ώρες");
				
				if(minutes != 0 && seconds != 0) sb.append(", ");
				else if(minutes != 0 || seconds != 0) sb.append(" και ");
				else sb.append(".");
			}
			if(minutes != 0)
			{
				sb.append(minutes);
				
				if(minutes == 1) sb.append(" λεπτό");
				else sb.append(" λεπτά");
				
				if(seconds != 0) sb.append(" και ");
				else sb.append(".");
			}
			if(seconds != 0)
			{
				sb.append(seconds);
				
				if(seconds == 1) sb.append(" δευτερόλεπτο.");
				else sb.append(" δευτερόλεπτα.");
			}
		}
		else
		{
			sb.append("The ");
			
			if(gameMode == GAMEMODE.CLASSIC_GAMEMODE) sb.append(getNumberOfQuestionsForClassic() + " questions ");
			
			sb.append("game lasted ");
			
			if(hours != 0)
			{
				sb.append(hours);
				
				if(hours == 1) sb.append(" hour");
				else sb.append(" hours");
				
				if(minutes != 0 && seconds != 0) sb.append(", ");
				else if(minutes != 0 || seconds != 0) sb.append(" and ");
				else sb.append(".");
			}
			if(minutes != 0)
			{
				sb.append(minutes);
				
				if(minutes == 1) sb.append(" minute");
				else sb.append(" minutes");
				
				if(seconds != 0) sb.append(" and ");
				else sb.append(".");
			}
			if(seconds != 0)
			{
				sb.append(seconds);
				
				if(seconds == 1) sb.append(" second.");
				else sb.append(" seconds.");
			}
		}
		
		return sb.toString();
	}
	
	private String getResults2Message()
	{
		StringBuilder sb = new StringBuilder();
		
		if(getMaxCombo() > 1)
		{
			sb.append(String.format(languageResourceBundle.getString("finishedMaxCombo"), getMaxCombo()));
			
			if(getCurrentLanguage() == LANGUAGE.GREEK) sb.append(" και ");
			else sb.append(" and ");
			
			if(numberFormatForUI.format(currentGame.getAverageAnswerTime()).equals("1"))
				sb.append(languageResourceBundle.getString("finishedAverageAnswerTime1").toLowerCase());
			else sb.append(String.format(languageResourceBundle.getString("finishedAverageAnswerTime2"), numberFormatForUI.format(currentGame.getAverageAnswerTime())).toLowerCase());
		}
		else
		{
			if(numberFormatForUI.format(currentGame.getAverageAnswerTime()).equals("1"))
				sb.append(languageResourceBundle.getString("finishedAverageAnswerTime1"));
			else sb.append(String.format(languageResourceBundle.getString("finishedAverageAnswerTime2"), numberFormatForUI.format(currentGame.getAverageAnswerTime())));
		}
		
		return sb.toString();
	}
	
	private String getResultsMessage()
	{
		if(gameMode == GAMEMODE.CLASSIC_GAMEMODE)
		{
			if(getCorrectAnswers() == 0) return String.format(languageResourceBundle.getString("finishedClassic0"), getNumberOfQuestionsForClassic());
			else if(getCorrectAnswers() == 1) return String.format(languageResourceBundle.getString("finishedClassic1"), getNumberOfQuestionsForClassic(), getScorePoints());
			else if(getCorrectAnswers() <= getNumberOfQuestionsForClassic() / 2)
				return String.format(languageResourceBundle.getString("finishedClassic2"), getCorrectAnswers(), getNumberOfQuestionsForClassic(), getScorePoints());
			else if(getCorrectAnswers() < getNumberOfQuestionsForClassic())
				return String.format(languageResourceBundle.getString("finishedClassic3"), getCorrectAnswers(), getNumberOfQuestionsForClassic(), getScorePoints());
			else return String.format(languageResourceBundle.getString("finishedClassic4"), getNumberOfQuestionsForClassic(), getScorePoints());
		}
		else if(gameMode == GAMEMODE.TIME_ATTACK_GAMEMODE)
		{
			if(getDurationInMinutesForTimeAttack() == 1 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 8 ||
			   getDurationInMinutesForTimeAttack() == 1 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 4 ||
			   getDurationInMinutesForTimeAttack() == 2 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 16 ||
			   getDurationInMinutesForTimeAttack() == 2 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 8 ||
			   getDurationInMinutesForTimeAttack() == 5 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 35 ||
			   getDurationInMinutesForTimeAttack() == 5 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 25 ||
			   getDurationInMinutesForTimeAttack() == 10 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 60 ||
			   getDurationInMinutesForTimeAttack() == 10 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 40)
			{
				if(getCorrectAnswers() == 0) return languageResourceBundle.getString("finishedTimeAttack0");
				else if(getCorrectAnswers() == 1) return String.format(languageResourceBundle.getString("finishedTimeAttack1"), getScorePoints());
				else return String.format(languageResourceBundle.getString("finishedTimeAttack2"), getCorrectAnswers(), getScorePoints());
			}
			else if(getDurationInMinutesForTimeAttack() == 1 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 12 ||
			        getDurationInMinutesForTimeAttack() == 1 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 8 ||
			        getDurationInMinutesForTimeAttack() == 2 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 24 ||
			        getDurationInMinutesForTimeAttack() == 2 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 15 ||
			        getDurationInMinutesForTimeAttack() == 5 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 50 ||
			        getDurationInMinutesForTimeAttack() == 5 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 30 ||
			        getDurationInMinutesForTimeAttack() == 10 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 100 ||
			        getDurationInMinutesForTimeAttack() == 10 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 60)
				return String.format(languageResourceBundle.getString("finishedTimeAttack3"), getCorrectAnswers(), getScorePoints());
			else return String.format(languageResourceBundle.getString("finishedTimeAttack4"), getCorrectAnswers(), getScorePoints());
		}
		else
		{
			if(getCorrectAnswers() == 0)
			{
				if(getCurrentQuestionNumber() == 1) return languageResourceBundle.getString("finishedEndless0");
				else return String.format(languageResourceBundle.getString("finishedEndless1"), getCurrentQuestionNumber());
			}
			else if(getCorrectAnswers() == 1) return String.format(languageResourceBundle.getString("finishedEndless2"), getCurrentQuestionNumber(), getScorePoints());
			else
			{
				if(getLivesForEndless() == 1 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 10 ||
				   getLivesForEndless() == 1 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 5 ||
				   getLivesForEndless() == 3 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 30 ||
				   getLivesForEndless() == 3 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 15 ||
				   getLivesForEndless() == 5 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 45 ||
				   getLivesForEndless() == 5 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 25)
						return String.format(languageResourceBundle.getString("finishedEndless3"), getCorrectAnswers(), getCurrentQuestionNumber(), getScorePoints());
				else return String.format(languageResourceBundle.getString("finishedEndless4"), getCorrectAnswers(), getCurrentQuestionNumber(), getScorePoints());
			}
		}
	}
	
	private void startTextAnimationForTitleLabelForFinishedGame()
	{
		if(titleLabelForFinishedGame.getScaleX() != 0.95)
		{
			double time = (Math.abs(titleLabelForFinishedGame.getScaleX() - 0.95) / 0.1) * TEXT_SCALE_ANIMATION_TIME;
			
			scaleTransitionForTitleLabelForFinishedGame.setDuration(Duration.millis(time));
			scaleTransitionForTitleLabelForFinishedGame.setFromX(titleLabelForFinishedGame.getScaleX());
			scaleTransitionForTitleLabelForFinishedGame.setFromY(titleLabelForFinishedGame.getScaleY());
			scaleTransitionForTitleLabelForFinishedGame.setToX(0.95);
			scaleTransitionForTitleLabelForFinishedGame.setToY(0.95);
			scaleTransitionForTitleLabelForFinishedGame.setCycleCount(1);
			scaleTransitionForTitleLabelForFinishedGame.setAutoReverse(false);
			
			scaleTransitionForTitleLabelForFinishedGame.setOnFinished(e ->
			{
				scaleTransitionForTitleLabelForFinishedGame.setDuration(Duration.millis(TEXT_SCALE_ANIMATION_TIME));
				scaleTransitionForTitleLabelForFinishedGame.setFromX(0.95);
				scaleTransitionForTitleLabelForFinishedGame.setFromY(0.95);
				scaleTransitionForTitleLabelForFinishedGame.setToX(1.03);
				scaleTransitionForTitleLabelForFinishedGame.setToY(1.03);
				scaleTransitionForTitleLabelForFinishedGame.setCycleCount(Animation.INDEFINITE);
				scaleTransitionForTitleLabelForFinishedGame.setAutoReverse(true);
				scaleTransitionForTitleLabelForFinishedGame.setOnFinished(null);
				
				scaleTransitionForTitleLabelForFinishedGame.playFromStart();
			});
			
			scaleTransitionForTitleLabelForFinishedGame.playFromStart();
		}
		else
		{
			scaleTransitionForTitleLabelForFinishedGame.setDuration(Duration.millis(TEXT_SCALE_ANIMATION_TIME));
			scaleTransitionForTitleLabelForFinishedGame.setFromX(0.95);
			scaleTransitionForTitleLabelForFinishedGame.setFromY(0.95);
			scaleTransitionForTitleLabelForFinishedGame.setToX(1.03);
			scaleTransitionForTitleLabelForFinishedGame.setToY(1.03);
			scaleTransitionForTitleLabelForFinishedGame.setCycleCount(Animation.INDEFINITE);
			scaleTransitionForTitleLabelForFinishedGame.setAutoReverse(true);
			scaleTransitionForTitleLabelForFinishedGame.setOnFinished(null);
			
			scaleTransitionForTitleLabelForFinishedGame.playFromStart();
		}
	}
	
	private void loseALife()
	{
		setRemainingLivesForEndless(getRemainingLivesForEndless() - 1);
		
		timelineForLifeLostAnimation.playFromStart();
		
		if(animationsUsed == ANIMATIONS.NO) imageViewForLives[getRemainingLivesForEndless()].setImage(HEART_ICON_LOST_SMALL);
	}
	
	private void prepareForNextQuestion()
	{
		setCurrentQuestionNumber(getCurrentQuestionNumber() + 1);
		updateScoreText();
		
		if(animationsUsed != ANIMATIONS.NO) timelineToShowNumberOfQuestion.playFromStart();
		else updateQuestionNumberText();
		
		if (animationsUsed != ANIMATIONS.NO)
		{
			if (gridPaneFor2TextAnswers.isVisible())
			{
				restoreGridPaneFor2TextAnswers();
				
				scaleTransitionFor2TextAnswers[0].setToX(0);
				scaleTransitionFor2TextAnswers[0].setToY(0);
				scaleTransitionFor2TextAnswers[1].setToX(0);
				scaleTransitionFor2TextAnswers[1].setToY(0);
				
				scaleTransitionForAnswerIconsFor2TextAnswers[0].setToX(0);
				scaleTransitionForAnswerIconsFor2TextAnswers[0].setToY(0);
				scaleTransitionForAnswerIconsFor2TextAnswers[1].setToX(0);
				scaleTransitionForAnswerIconsFor2TextAnswers[1].setToY(0);
				
				playMinimizeSound();
				scaleTransitionFor2TextAnswers[0].playFromStart();
				scaleTransitionFor2TextAnswers[1].playFromStart();
				
				scaleTransitionForAnswerIconsFor2TextAnswers[0].playFromStart();
				scaleTransitionForAnswerIconsFor2TextAnswers[1].playFromStart();
			}
			else if (gridPaneFor4TextAnswers.isVisible())
			{
				restoreGridPaneFor4TextAnswers();
				
				scaleTransitionFor4TextAnswers[0].setToX(0);
				scaleTransitionFor4TextAnswers[0].setToY(0);
				scaleTransitionFor4TextAnswers[1].setToX(0);
				scaleTransitionFor4TextAnswers[1].setToY(0);
				scaleTransitionFor4TextAnswers[2].setToX(0);
				scaleTransitionFor4TextAnswers[2].setToY(0);
				scaleTransitionFor4TextAnswers[3].setToX(0);
				scaleTransitionFor4TextAnswers[3].setToY(0);
				
				scaleTransitionForAnswerIconsFor4TextAnswers[0].setToX(0);
				scaleTransitionForAnswerIconsFor4TextAnswers[0].setToY(0);
				scaleTransitionForAnswerIconsFor4TextAnswers[1].setToX(0);
				scaleTransitionForAnswerIconsFor4TextAnswers[1].setToY(0);
				scaleTransitionForAnswerIconsFor4TextAnswers[2].setToX(0);
				scaleTransitionForAnswerIconsFor4TextAnswers[2].setToY(0);
				scaleTransitionForAnswerIconsFor4TextAnswers[3].setToX(0);
				scaleTransitionForAnswerIconsFor4TextAnswers[3].setToY(0);
				
				playMinimizeSound();
				scaleTransitionFor4TextAnswers[0].playFromStart();
				scaleTransitionFor4TextAnswers[1].playFromStart();
				scaleTransitionFor4TextAnswers[2].playFromStart();
				scaleTransitionFor4TextAnswers[3].playFromStart();
				
				scaleTransitionForAnswerIconsFor4TextAnswers[0].playFromStart();
				scaleTransitionForAnswerIconsFor4TextAnswers[1].playFromStart();
				scaleTransitionForAnswerIconsFor4TextAnswers[2].playFromStart();
				scaleTransitionForAnswerIconsFor4TextAnswers[3].playFromStart();
				
			}
			else if (stackPaneFor4ImageAnswers.isVisible())
			{
				restoreStackPaneFor4ImageAnswers();
				
				scaleTransitionFor4ImageAnswers[0].setToX(0);
				scaleTransitionFor4ImageAnswers[0].setToY(0);
				scaleTransitionFor4ImageAnswers[1].setToX(0);
				scaleTransitionFor4ImageAnswers[1].setToY(0);
				scaleTransitionFor4ImageAnswers[2].setToX(0);
				scaleTransitionFor4ImageAnswers[2].setToY(0);
				scaleTransitionFor4ImageAnswers[3].setToX(0);
				scaleTransitionFor4ImageAnswers[3].setToY(0);
				
				scaleTransitionForAnswerIconsFor4ImageAnswers[0].setToX(0);
				scaleTransitionForAnswerIconsFor4ImageAnswers[0].setToY(0);
				scaleTransitionForAnswerIconsFor4ImageAnswers[1].setToX(0);
				scaleTransitionForAnswerIconsFor4ImageAnswers[1].setToY(0);
				scaleTransitionForAnswerIconsFor4ImageAnswers[2].setToX(0);
				scaleTransitionForAnswerIconsFor4ImageAnswers[2].setToY(0);
				scaleTransitionForAnswerIconsFor4ImageAnswers[3].setToX(0);
				scaleTransitionForAnswerIconsFor4ImageAnswers[3].setToY(0);
				
				playMinimizeSound();
				scaleTransitionFor4ImageAnswers[0].playFromStart();
				scaleTransitionFor4ImageAnswers[1].playFromStart();
				scaleTransitionFor4ImageAnswers[2].playFromStart();
				scaleTransitionFor4ImageAnswers[3].playFromStart();
				
				scaleTransitionForAnswerIconsFor4ImageAnswers[0].playFromStart();
				scaleTransitionForAnswerIconsFor4ImageAnswers[1].playFromStart();
				scaleTransitionForAnswerIconsFor4ImageAnswers[2].playFromStart();
				scaleTransitionForAnswerIconsFor4ImageAnswers[3].playFromStart();
			}
			
			if (imageViewForQuestionImage.isVisible()) hideQuestionImage();
			
			scaleTransitionForTextForQuestion.setToX(0);
			scaleTransitionForTextForQuestion.setToY(0);
			
			scaleTransitionForTextForQuestion.setOnFinished(e ->
				{
					scaleTransitionForTextForQuestion.setOnFinished(ev -> {});
																			
					if(gridPaneFor2TextAnswers.isVisible()) setFunctional2TextAnswers(true);
					else if(gridPaneFor4TextAnswers.isVisible()) setFunctional4TextAnswers(true);
					else setFunctional4ImageAnswers(true);
					
					questionMaker.decideNextQuestion();
				});
			
			scaleTransitionForTextForQuestion.playFromStart();
		}
		else
		{
			if (gridPaneFor2TextAnswers.isVisible())
			{
				restoreGridPaneFor2TextAnswers();
				setFunctional2TextAnswers(true);
			}
			else if (gridPaneFor4TextAnswers.isVisible())
			{
				restoreGridPaneFor4TextAnswers();
				setFunctional4TextAnswers(true);
			}
			else if (stackPaneFor4ImageAnswers.isVisible())
			{
				restoreStackPaneFor4ImageAnswers();
				setFunctional4ImageAnswers(true);
			}
			
			questionMaker.decideNextQuestion();
		}
	}
	
	private void showScoreAnimated(int points)
	{
		if(getCurrentLanguage() == LANGUAGE.GREEK)
		{
			if (points > 0) scoreAnimated.setText("+" + points + " πόντοι");
			else scoreAnimated.setText(points + " πόντοι");
		}
		else
		{
			if (points > 0) scoreAnimated.setText("+" + points + " points");
			else scoreAnimated.setText(points + " points");
		}
		
		scoreAnimated.setLayoutX(stage.getWidth() / 2.0 - scoreAnimated.getLayoutBounds().getWidth() / 2.0);
		
		if(animationsUsed != ANIMATIONS.NO) timelineForScoreAnimated.playFromStart();
		else timelineForScoreNoAnimated.playFromStart();
	}
	
	private void showNextQuestionNodes(boolean showQuestionImage, int answers)
	{
		if(animationsUsed != ANIMATIONS.NO)
		{
			scaleTransitionForTextForQuestion.setToX(1);
			scaleTransitionForTextForQuestion.setToY(1);
			scaleTransitionForTextForQuestion.playFromStart();
		}
		
		if(showQuestionImage)
		{
			if(answers == 1) showQuestionImage(false);
			else showQuestionImage(true);
		}
		else hideQuestionImage();
		
		if(answers == 1) showGridPaneFor2TextAnswers();
		else if(answers == 2) showGridPaneFor4TextAnswers();
		else if (answers == 3) showStackPaneFor4ImageAnswers();
	}
	
	private void hideComboText()
	{
		if(getCombo() > 1)
		{
			if(animationsUsed != ANIMATIONS.NO)
			{
				scaleTransitionForTextForCombo.stop();
				
				scaleTransitionForTextForCombo.setDuration(Duration.millis(300));
				scaleTransitionForTextForCombo.setCycleCount(1);
				scaleTransitionForTextForCombo.setAutoReverse(false);
				scaleTransitionForTextForCombo.setFromX(textForCombo.getScaleX());
				scaleTransitionForTextForCombo.setFromY(textForCombo.getScaleY());
				scaleTransitionForTextForCombo.setToX(0);
				scaleTransitionForTextForCombo.setToY(0);
				
				scaleTransitionForTextForCombo.setOnFinished(e ->
				{
					scaleTransitionForTextForCombo.setOnFinished(ev -> {});
					
					textForCombo.setText("");
					textForCombo.setScaleX(1);
					textForCombo.setScaleY(1);
				});
				
				scaleTransitionForTextForCombo.playFromStart();
			}
			else textForCombo.setText("");
		}
		setCombo(0);
	}
	
	private class AnswerEvaluator implements EventHandler<Event>
	{
		private Object source;
		private int corPos;
		private int previousRemainingSecondsForTimeAttack, secondsLastQuestionTook;
		private double currentProgressOfProgressBar;
		int points;
		
		public void handle(Event e)
		{
			source = e.getSource();
			
			timelineToCountMilliseconds.pause();
			
			if(progressBarForCountdown.isVisible())
			{
				if(isQuestionCountdownEnabled())
				{
					currentProgressOfProgressBar = progressBarForCountdown.getProgress();
					stopQuestionCountdown();
				}
				else
				{
					if(getCurrentQuestionNumber() == 1) previousRemainingSecondsForTimeAttack = getDurationInMinutesForTimeAttack() * 60;
					
					secondsLastQuestionTook = previousRemainingSecondsForTimeAttack - getRemainingSecondsForTimeAttack();
					previousRemainingSecondsForTimeAttack = getRemainingSecondsForTimeAttack();
					
					pauseTimeAttackCountdown();
				}
			}
			
			if(gameMode != GAMEMODE.TIME_ATTACK_GAMEMODE) pauseTimePassedCounting();
			
			pauseGameIcon.setDisable(true);
			
			corPos = questionMaker.getPositionOfCorrectAnswer();
			
			if(source == radioButtonsFor2TextAnswers[0] || source == radioButtonsFor2TextAnswers[1])
			{
				setFunctional2TextAnswers(false);
				
				if(source == radioButtonsFor2TextAnswers[corPos])
				{
					correctAnswerReaction();
					
					if(animationsUsed != ANIMATIONS.NO)
					{
						imageViewForLargeAnswerIcon.setImage(CORRECT_ICON_BIG);
						
						answerIconsFor2TextAnswers[corPos].setScaleX(1);
						answerIconsFor2TextAnswers[corPos].setScaleY(1);
						
						timelineForLargeAnswerIcon.setOnFinished(ev ->
						{
							timelineForLargeAnswerIcon.setOnFinished(eve ->{});
							
							answerIconsFor2TextAnswers[corPos].setImage(CORRECT_ICON_SMALL);
							answerIconsFor2TextAnswers[corPos].setScaleX(1);
							answerIconsFor2TextAnswers[corPos].setScaleY(1);
							
							parallelTransitionForLargeAnswerIcon.stop();
							imageViewForLargeAnswerIcon.setScaleX(0);
							imageViewForLargeAnswerIcon.setScaleY(0);
							imageViewForLargeAnswerIcon.setTranslateX(0);
							imageViewForLargeAnswerIcon.setTranslateY(0);
							
							if(!openPauseMenu)
							{
								nextQuestionButton.setDisable(false);
								startTimelineFor2_5SecondsWait();
							}
						});
						
						timelineForLargeAnswerIcon.playFromStart();
					}
					else
					{
						answerIconsFor2TextAnswers[corPos].setImage(CORRECT_ICON_SMALL);
						
						if(!openPauseMenu)
						{
							nextQuestionButton.setDisable(false);
							startTimelineFor2_5SecondsWait();
						}
					}
				}
				else
				{
					hideComboText();
					
					if(gameMode == GAMEMODE.ENDLESS_GAMEMODE)
					{
						loseALife();
						
						answerIconsFor2TextAnswers[1 - corPos].setImage(INCORRECT_ICON_SMALL);
						
						hintToCorrectAnswer();
						
						if (animationsUsed != ANIMATIONS.NO)
						{
							scaleTransitionForAnswerIconsFor2TextAnswers[1 - corPos].setToX(1);
							scaleTransitionForAnswerIconsFor2TextAnswers[1 - corPos].setToY(1);
							
							scaleTransitionForAnswerIconsFor2TextAnswers[1 - corPos].playFromStart();
						}
						
						if(!openPauseMenu)
						{
							nextQuestionButton.setDisable(false);
							startTimelineFor2_5SecondsWait();
						}
					}
					else
					{
						wrongAnswerReaction();
						
						if(animationsUsed != ANIMATIONS.NO)
						{
							imageViewForLargeAnswerIcon.setImage(INCORRECT_ICON_BIG);
							
							answerIconsFor2TextAnswers[1 - corPos].setScaleX(1);
							answerIconsFor2TextAnswers[1 - corPos].setScaleY(1);
							
							timelineForLargeAnswerIcon.setOnFinished(ev ->
							{
								timelineForLargeAnswerIcon.setOnFinished( eve ->{});
								
								answerIconsFor2TextAnswers[1 - corPos].setImage(INCORRECT_ICON_SMALL);
								answerIconsFor2TextAnswers[1 - corPos].setScaleX(1);
								answerIconsFor2TextAnswers[1 - corPos].setScaleY(1);
								
								hintToCorrectAnswer();
								
								parallelTransitionForLargeAnswerIcon.stop();
								imageViewForLargeAnswerIcon.setScaleX(0);
								imageViewForLargeAnswerIcon.setScaleY(0);
								imageViewForLargeAnswerIcon.setTranslateX(0);
								imageViewForLargeAnswerIcon.setTranslateY(0);
								
								if(!openPauseMenu)
								{
									nextQuestionButton.setDisable(false);
									startTimelineFor2_5SecondsWait();
								}
							});
							
							timelineForLargeAnswerIcon.playFromStart();
						}
						else
						{
							answerIconsFor2TextAnswers[1 - corPos].setImage(INCORRECT_ICON_SMALL);
							hintToCorrectAnswer();
							
							if(!openPauseMenu)
							{
								nextQuestionButton.setDisable(false);
								startTimelineFor2_5SecondsWait();
							}
						}
					}
				}
			}
			else if(source == radioButtonsFor4TextAnswers[0] || source == radioButtonsFor4TextAnswers[1] ||
			        source == radioButtonsFor4TextAnswers[2] || source == radioButtonsFor4TextAnswers[3])
			{
				setFunctional4TextAnswers(false);
				
				if(source == radioButtonsFor4TextAnswers[corPos])
				{
					correctAnswerReaction();
					
					if(animationsUsed != ANIMATIONS.NO)
					{
						imageViewForLargeAnswerIcon.setImage(CORRECT_ICON_BIG);
						
						answerIconsFor4TextAnswers[corPos].setScaleX(1);
						answerIconsFor4TextAnswers[corPos].setScaleY(1);
						
						timelineForLargeAnswerIcon.setOnFinished(ev ->
						{
							timelineForLargeAnswerIcon.setOnFinished( eve ->{});
							
							answerIconsFor4TextAnswers[corPos].setImage(CORRECT_ICON_SMALL);
							answerIconsFor4TextAnswers[corPos].setScaleX(1);
							answerIconsFor4TextAnswers[corPos].setScaleY(1);
							
							parallelTransitionForLargeAnswerIcon.stop();
							imageViewForLargeAnswerIcon.setScaleX(0);
							imageViewForLargeAnswerIcon.setScaleY(0);
							imageViewForLargeAnswerIcon.setTranslateX(0);
							imageViewForLargeAnswerIcon.setTranslateY(0);
							
							if(!openPauseMenu)
							{
								nextQuestionButton.setDisable(false);
								startTimelineFor2_5SecondsWait();
							}
						});
						
						timelineForLargeAnswerIcon.playFromStart();
					}
					else
					{
						answerIconsFor4TextAnswers[corPos].setImage(CORRECT_ICON_SMALL);
						
						if(!openPauseMenu)
						{
							nextQuestionButton.setDisable(false);
							startTimelineFor2_5SecondsWait();
						}
					}
				}
				else
				{
					hideComboText();
					
					int index;
					if (source == radioButtonsFor4TextAnswers[0]) index = 0;
					else if (source == radioButtonsFor4TextAnswers[1]) index = 1;
					else if (source == radioButtonsFor4TextAnswers[2]) index = 2;
					else index = 3;
					
					if(gameMode == GAMEMODE.ENDLESS_GAMEMODE)
					{
						loseALife();
						
						answerIconsFor4TextAnswers[index].setImage(INCORRECT_ICON_SMALL);
						
						hintToCorrectAnswer();
						
						if (animationsUsed != ANIMATIONS.NO)
						{
							scaleTransitionForAnswerIconsFor4TextAnswers[index].setToX(1);
							scaleTransitionForAnswerIconsFor4TextAnswers[index].setToY(1);
							
							scaleTransitionForAnswerIconsFor4TextAnswers[index].playFromStart();
						}
						
						if(!openPauseMenu)
						{
							nextQuestionButton.setDisable(false);
							startTimelineFor2_5SecondsWait();
						}
					}
					else
					{
						wrongAnswerReaction();
						
						if(animationsUsed != ANIMATIONS.NO)
						{
							imageViewForLargeAnswerIcon.setImage(INCORRECT_ICON_BIG);
							
							answerIconsFor4TextAnswers[index].setScaleX(1);
							answerIconsFor4TextAnswers[index].setScaleY(1);
									
							timelineForLargeAnswerIcon.setOnFinished(ev ->
							{
								timelineForLargeAnswerIcon.setOnFinished( eve ->{});
								
								answerIconsFor4TextAnswers[index].setImage(INCORRECT_ICON_SMALL);
								answerIconsFor4TextAnswers[index].setScaleX(1);
								answerIconsFor4TextAnswers[index].setScaleY(1);
								
								hintToCorrectAnswer();
								
								parallelTransitionForLargeAnswerIcon.stop();
								imageViewForLargeAnswerIcon.setScaleX(0);
								imageViewForLargeAnswerIcon.setScaleY(0);
								imageViewForLargeAnswerIcon.setTranslateX(0);
								imageViewForLargeAnswerIcon.setTranslateY(0);
								
								if(!openPauseMenu)
								{
									nextQuestionButton.setDisable(false);
									startTimelineFor2_5SecondsWait();
								}
							});
							
							timelineForLargeAnswerIcon.playFromStart();
						}
						else
						{
							answerIconsFor4TextAnswers[index].setImage(INCORRECT_ICON_SMALL);
							hintToCorrectAnswer();
							
							if(!openPauseMenu)
							{
								nextQuestionButton.setDisable(false);
								startTimelineFor2_5SecondsWait();
							}
						}
					}
				}
			}
			else
			{
				setFunctional4ImageAnswers(false);
				
				if(source == imageViewFor4ImageAnswers[corPos])
				{
					correctAnswerReaction();
					
					answerIconsFor4ImageAnswers[corPos].setImage(CORRECT_ICON_BIG);
					
					if(animationsUsed != ANIMATIONS.NO)
					{
						scaleTransitionForAnswerIconsFor4ImageAnswers[corPos].setToX(1);
						scaleTransitionForAnswerIconsFor4ImageAnswers[corPos].setToY(1);
						
						scaleTransitionForAnswerIconsFor4ImageAnswers[corPos].playFromStart();
					}
				}
				else
				{
					hideComboText();
					
					if(gameMode == GAMEMODE.ENDLESS_GAMEMODE) loseALife();
					else wrongAnswerReaction();
					
					int index;
					if(source == imageViewFor4ImageAnswers[0]) index = 0;
					else if(source == imageViewFor4ImageAnswers[1]) index = 1;
					else if(source == imageViewFor4ImageAnswers[2]) index = 2;
					else index = 3;
					
					answerIconsFor4ImageAnswers[index].setImage(INCORRECT_ICON_BIG);
					
					if(animationsUsed != ANIMATIONS.NO)
					{
						scaleTransitionForAnswerIconsFor4ImageAnswers[index].setToX(1);
						scaleTransitionForAnswerIconsFor4ImageAnswers[index].setToY(1);
						
						scaleTransitionForAnswerIconsFor4ImageAnswers[index].playFromStart();
					}
					hintToCorrectAnswer();
				}
				
				if(!openPauseMenu)
				{
					nextQuestionButton.setDisable(false);
					startTimelineFor2_5SecondsWait();
				}
			}
		}
		
		private void correctAnswerReaction()
		{
			playCorrectAnswerSimpleSound();
			setCorrectAnswers(getCorrectAnswers() + 1);
			
			combo++;
			if(combo > 1) showComboMessage(combo);
			if(combo > getMaxCombo()) setMaxCombo(combo);
			
			points = newScorePoints(true);
			setScorePoints(getScorePoints() + points);
			showScoreAnimated(points);
		}
		
		private void wrongAnswerReaction()
		{
			playWrongAnswerSimpleSound();
			
			points = newScorePoints(false);
			setScorePoints(getScorePoints() + points);
			if(getScorePoints() < 0) setScorePoints(0);
			showScoreAnimated(points);
		}
		
		private void hintToCorrectAnswer()
		{
			if(gridPaneFor2TextAnswers.isVisible()) radioButtonsFor2TextAnswers[questionMaker.getPositionOfCorrectAnswer()].setStyle("-fx-background-color: #2ecc71;");
			else if(gridPaneFor4TextAnswers.isVisible()) radioButtonsFor4TextAnswers[questionMaker.getPositionOfCorrectAnswer()].setStyle("-fx-background-color: #2ecc71;");
			else
			{
				answerIconsFor4ImageAnswers[questionMaker.getPositionOfCorrectAnswer()].setImage(CORRECT_ICON_BIG);
				
				if(animationsUsed != ANIMATIONS.NO)
				{
					scaleTransitionForAnswerIconsFor4ImageAnswers[questionMaker.getPositionOfCorrectAnswer()].setToX(1);
					scaleTransitionForAnswerIconsFor4ImageAnswers[questionMaker.getPositionOfCorrectAnswer()].setToY(1);
					scaleTransitionForAnswerIconsFor4ImageAnswers[questionMaker.getPositionOfCorrectAnswer()].playFromStart();
				}
			}
		}
		
		private double getCurrentProgressOfProgressBar()
		{
			return currentProgressOfProgressBar;
		}
		
		private int getSecondsLastQuestionTook()
		{
			return secondsLastQuestionTook;
		}
	}

	private class QuestionMaker
	{
		private final short NUM_ALL_QUESTION_CATEGORIES = 17;
		private final short COUNTRIES_CAPITALS = 0, COUNTRIES_CITIES = 1, CONTINENTS_CITIES = 2, COUNTRIES_LANGUAGES = 3,
				COUNTRIES_CURRENCY = 4, COUNTRIES_POPULATION = 5, CONTINENTS_POPULATION = 6, COUNTRIES_AREA = 7,
				CONTINENTS_AREA = 8, CONTINENTS_COUNTRIES = 9, CONTINENTS_LARGEST_COUNTRIES = 10, SOVEREIGN_DEPENDENT_COUNTRIES = 11, COUNTRIES_FLAGS = 12,
				COUNTRIES_COAT_OF_ARMS = 13, COUNTRIES_LOCATION = 14, COUNTRIES_LOCATION_ALTERNATIVE = 15, CONTINENTS_LOCATION = 16;
		
		private ArrayList<Integer>[] availableQuestionsArray = new ArrayList[NUM_ALL_QUESTION_CATEGORIES];
		private Thread setAvailableQuestionsThread;
		
		private int randomCategoryPositionInListWithChosenCategories;
		
		private int randomIndexForMultipleChoice[] = new int[3];
		private String randomStringForMultipleChoice[] = new String[3];
		
		private int correctIndex;
		private int positionOfCorrectAnswer = -1;
		private String correctAnswerString;
		
		private int wayOfAskingCurrentQuestion;
		
		private boolean easyQuestion;
		private int typeOfQuestion; // 1 == 2 choices | 2 == 4 choices | 3 == bonus
		
		//IMAGE PROPERTIES
		private String questionImageSize;
		private String answerImageSize;

		//LANGUAGES ONLY STUFF
		private String[] languages;
		//POPULATION ONLY STUFF
		int[] countriesPopulationBoundaries;
		//AREA ONLY STUFF
		int[] countriesByArea;
		//CURRENCY ONLY STUFF
		private String[] currencies;
		//SOVEREIGN STATES ONLY STUFF
		private int[] dependentStates;
		
		void setAvailableQuestions()
		{
			setAvailableQuestionsThread = new Thread(() ->
			{
				for(ArrayList<Integer> list: availableQuestionsArray)
				{
					if(list != null && !list.isEmpty()) list.clear();
				}
				
				for (Integer chosenCategory : availableCategories)
				{
					if (chosenCategory == CAT_COUNTRY_CAPITALS)
					{
						if (availableQuestionsArray[COUNTRIES_CAPITALS] == null) availableQuestionsArray[COUNTRIES_CAPITALS] = new ArrayList<>();
						
						if (getDifficultyLevel() == DIFFICULTY.EASY)
						{
							for (int y = 0; y < NUM_ALL_COUNTRIES; y++) if (countries[y].askCapital() == 1) availableQuestionsArray[COUNTRIES_CAPITALS].add(y);
						}
						else if (getDifficultyLevel() == DIFFICULTY.DIFFICULT)
						{
							for (int y = 0; y < NUM_ALL_COUNTRIES; y++) if (countries[y].askCapital() == 2) availableQuestionsArray[COUNTRIES_CAPITALS].add(y);
						}
					}
					else if (chosenCategory == CAT_CONTINENT_COUNTRY_LARGEST_CITIES)
					{
						if (availableQuestionsArray[COUNTRIES_CITIES] == null) availableQuestionsArray[COUNTRIES_CITIES] = new ArrayList<>();
						
						if (availableQuestionsArray[CONTINENTS_CITIES] == null) availableQuestionsArray[CONTINENTS_CITIES] = new ArrayList<>();
						
						if (getDifficultyLevel() == DIFFICULTY.EASY)
						{
							for (int y = 0; y < NUM_ALL_COUNTRIES; y++) if (countries[y].askLargestCity() == 1) availableQuestionsArray[COUNTRIES_CITIES].add(y);
						}
						else if (getDifficultyLevel() == DIFFICULTY.DIFFICULT)
						{
							for (int y = 0; y < NUM_ALL_COUNTRIES; y++) if (countries[y].askLargestCity() == 2) availableQuestionsArray[COUNTRIES_CITIES].add(y);
						}
						
						for (int y = 1; y < NUM_ALL_CONTINENTS - 1; y++) availableQuestionsArray[CONTINENTS_CITIES].add(y);
					}
					else if (chosenCategory == CAT_COUNTRY_LANGUAGES)
					{
						if (availableQuestionsArray[COUNTRIES_LANGUAGES] == null) availableQuestionsArray[COUNTRIES_LANGUAGES] = new ArrayList<>();
						
						for (int y = 0; y < NUM_ALL_COUNTRIES; y++) if (countries[y].askLanguage() != 0) availableQuestionsArray[COUNTRIES_LANGUAGES].add(y);
					}
					else if (chosenCategory == CAT_COUNTRY_CURRENCY)
					{
						if (availableQuestionsArray[COUNTRIES_CURRENCY] == null) availableQuestionsArray[COUNTRIES_CURRENCY] = new ArrayList<>();
						
						if (getDifficultyLevel() == DIFFICULTY.EASY)
						{
							for (int y = 0; y < NUM_ALL_COUNTRIES; y++) if (countries[y].askCurrency() == 1) availableQuestionsArray[COUNTRIES_CURRENCY].add(y);
						}
						else if (getDifficultyLevel() == DIFFICULTY.DIFFICULT)
						{
							for (int y = 0; y < NUM_ALL_COUNTRIES; y++) if (countries[y].askCurrency() == 2) availableQuestionsArray[COUNTRIES_CURRENCY].add(y);
						}
					}
					else if (chosenCategory == CAT_CONTINENT_COUNTRY_POPULATION)
					{
						if (availableQuestionsArray[COUNTRIES_POPULATION] == null) availableQuestionsArray[COUNTRIES_POPULATION] = new ArrayList<>();
						
						if (availableQuestionsArray[CONTINENTS_POPULATION] == null) availableQuestionsArray[CONTINENTS_POPULATION] = new ArrayList<>();
						
						if (getDifficultyLevel() == DIFFICULTY.EASY)
						{
							for (int y = 0; y < NUM_ALL_COUNTRIES; y++) if (countries[y].hasEasyLocation()) availableQuestionsArray[COUNTRIES_POPULATION].add(y);
						}
						else if (getDifficultyLevel() == DIFFICULTY.DIFFICULT)
						{
							for (int y = 0; y < NUM_ALL_COUNTRIES; y++) if (!countries[y].hasEasyLocation()) availableQuestionsArray[COUNTRIES_POPULATION].add(y);
						}
						
						if (getDifficultyLevel() == DIFFICULTY.EASY)
						{
							availableQuestionsArray[CONTINENTS_POPULATION].add(1);
							availableQuestionsArray[CONTINENTS_POPULATION].add(2);
						}
						else if (getDifficultyLevel() == DIFFICULTY.DIFFICULT)
						{
							availableQuestionsArray[CONTINENTS_POPULATION].add(3);
							availableQuestionsArray[CONTINENTS_POPULATION].add(4);
							availableQuestionsArray[CONTINENTS_POPULATION].add(5);
							availableQuestionsArray[CONTINENTS_POPULATION].add(6);
						}
					}
					else if (chosenCategory == CAT_CONTINENT_COUNTRY_AREA)
					{
						if (availableQuestionsArray[COUNTRIES_AREA] == null) availableQuestionsArray[COUNTRIES_AREA] = new ArrayList<>();
						
						if (availableQuestionsArray[CONTINENTS_AREA] == null) availableQuestionsArray[CONTINENTS_AREA] = new ArrayList<>();
						
						if (getDifficultyLevel() == DIFFICULTY.EASY)
						{
							for (int y = 0; y < NUM_ALL_COUNTRIES; y++) if (countries[y].hasEasyLocation()) availableQuestionsArray[COUNTRIES_AREA].add(y);
						}
						else if (getDifficultyLevel() == DIFFICULTY.DIFFICULT)
						{
							for (int y = 0; y < NUM_ALL_COUNTRIES; y++) if (!countries[y].hasEasyLocation()) availableQuestionsArray[COUNTRIES_AREA].add(y);
						}
						
						if (getDifficultyLevel() == DIFFICULTY.EASY)
						{
							availableQuestionsArray[CONTINENTS_AREA].add(1);
							availableQuestionsArray[CONTINENTS_AREA].add(2);
						}
						else if (getDifficultyLevel() == DIFFICULTY.DIFFICULT)
						{
							availableQuestionsArray[CONTINENTS_AREA].add(0);
							availableQuestionsArray[CONTINENTS_AREA].add(3);
							availableQuestionsArray[CONTINENTS_AREA].add(4);
							availableQuestionsArray[CONTINENTS_AREA].add(5);
							availableQuestionsArray[CONTINENTS_AREA].add(6);
						}
					}
					else if (chosenCategory == CAT_CONTINENTS_COUNTRIES)
					{
						if (availableQuestionsArray[CONTINENTS_COUNTRIES] == null) availableQuestionsArray[CONTINENTS_COUNTRIES] = new ArrayList<>();
						
						if (availableQuestionsArray[CONTINENTS_LARGEST_COUNTRIES] == null) availableQuestionsArray[CONTINENTS_LARGEST_COUNTRIES] = new ArrayList<>();
						
						if (getDifficultyLevel() == DIFFICULTY.EASY)
						{
							for (int y = 0; y < NUM_ALL_COUNTRIES; y++) if (countries[y].askContinent() == 1) availableQuestionsArray[CONTINENTS_COUNTRIES].add(y);
						}
						else if (getDifficultyLevel() == DIFFICULTY.DIFFICULT)
						{
							for (int y = 0; y < NUM_ALL_COUNTRIES; y++) if (countries[y].askContinent() == 2) availableQuestionsArray[CONTINENTS_COUNTRIES].add(y);
						}
						
						for (int y = 1; y < NUM_ALL_CONTINENTS - 1; y++) availableQuestionsArray[CONTINENTS_LARGEST_COUNTRIES].add(y);
					}
					else if (chosenCategory == CAT_SOVEREIGN_DEPENDENT_COUNTRIES)
					{
						if (availableQuestionsArray[SOVEREIGN_DEPENDENT_COUNTRIES] == null) availableQuestionsArray[SOVEREIGN_DEPENDENT_COUNTRIES] = new ArrayList<>();
						
						for (int y = 0; y < NUM_ALL_COUNTRIES; y++)
						{
							if (!countries[y].isSovereignState() && !countries[y].getNameInGreek().equals("Υπερδνειστερία") && !countries[y].getNameInGreek().equals("Δημοκρατία της Κίνας (Ταϊβάν)") &&
							    !countries[y].getNameInGreek().equals("Βρετανικές Παρθένες Νήσοι") && !countries[y].getNameInGreek().equals("Αμερικανικές Παρθένες Νήσοι") &&
							    !countries[y].getNameInGreek().equals("Αμερικανική Σαμόα") && !countries[y].getNameInGreek().equals("Γαλλική Γουιάνα") && !countries[y].getNameInGreek().equals("Γαλλική Πολυνησία")
							    && !countries[y].getNameInGreek().equals("Νήσος των Χριστουγέννων"))
							{ availableQuestionsArray[SOVEREIGN_DEPENDENT_COUNTRIES].add(y); }
						}
					}
					else if (chosenCategory == CAT_COUNTRY_FLAGS)
					{
						if (availableQuestionsArray[COUNTRIES_FLAGS] == null) availableQuestionsArray[COUNTRIES_FLAGS] = new ArrayList<>();
						
						if (getDifficultyLevel() == DIFFICULTY.EASY)
						{
							for (int y = 0; y < NUM_ALL_COUNTRIES; y++)
							{ if (countries[y].hasEasyFlag()) availableQuestionsArray[COUNTRIES_FLAGS].add(y); }
						}
						else if (getDifficultyLevel() == DIFFICULTY.DIFFICULT)
						{
							for (int y = 0; y < NUM_ALL_COUNTRIES; y++)
							{ if (!countries[y].hasEasyFlag()) availableQuestionsArray[COUNTRIES_FLAGS].add(y); }
						}
					}
					else if (chosenCategory == CAT_COUNTRY_COAT_OF_ARMS)
					{
						if (availableQuestionsArray[COUNTRIES_COAT_OF_ARMS] == null) availableQuestionsArray[COUNTRIES_COAT_OF_ARMS] = new ArrayList<>();
						
						for (int y = 0; y < NUM_ALL_COUNTRIES; y++) if (countries[y].askForCoatOfArms()) availableQuestionsArray[COUNTRIES_COAT_OF_ARMS].add(y);
					}
					else if (chosenCategory == CAT_CONTINENT_COUNTRY_LOCATION)
					{
						if (availableQuestionsArray[COUNTRIES_LOCATION] == null) availableQuestionsArray[COUNTRIES_LOCATION] = new ArrayList<>();
						else availableQuestionsArray[COUNTRIES_LOCATION].clear();
						
						if (availableQuestionsArray[COUNTRIES_LOCATION_ALTERNATIVE] == null) availableQuestionsArray[COUNTRIES_LOCATION_ALTERNATIVE] = new ArrayList<>();
						else availableQuestionsArray[COUNTRIES_LOCATION_ALTERNATIVE].clear();
						
						if (availableQuestionsArray[CONTINENTS_LOCATION] == null) availableQuestionsArray[CONTINENTS_LOCATION] = new ArrayList<>();
						else availableQuestionsArray[CONTINENTS_LOCATION].clear();
						
						if (getDifficultyLevel() == DIFFICULTY.EASY)
						{
							for (int y = 0; y < NUM_ALL_COUNTRIES; y++) if (countries[y].hasEasyLocation()) availableQuestionsArray[COUNTRIES_LOCATION].add(y);
							for (int y = 0; y < NUM_ALL_COUNTRIES; y++) if (countries[y].askGeographicalCharacteristics() == 1) availableQuestionsArray[COUNTRIES_LOCATION_ALTERNATIVE].add(y);
						}
						else if (getDifficultyLevel() == DIFFICULTY.DIFFICULT)
						{
							for (int y = 0; y < NUM_ALL_COUNTRIES; y++) if (!countries[y].hasEasyLocation()) availableQuestionsArray[COUNTRIES_LOCATION].add(y);
							for (int y = 0; y < NUM_ALL_COUNTRIES; y++) if (countries[y].askGeographicalCharacteristics() == 2) availableQuestionsArray[COUNTRIES_LOCATION_ALTERNATIVE].add(y);
							for (int y = 0; y < NUM_ALL_CONTINENTS; y++) availableQuestionsArray[CONTINENTS_LOCATION].add(y);
						}
					}
				}
				
				for(ArrayList<Integer> list: availableQuestionsArray)
				{
					if(list != null && !list.isEmpty()) Collections.shuffle(list);
				}
				
				if(availableQuestionsArray[COUNTRIES_AREA] != null && !availableQuestionsArray[COUNTRIES_AREA].isEmpty())
				{
					countriesByArea = new int[availableQuestionsArray[COUNTRIES_AREA].size()];
					Scanner input = null;
					int counter = 0;
					
					try
					{
						if(getDifficultyLevel() == DIFFICULTY.EASY) input = new Scanner(this.getClass().getResourceAsStream("/dataFiles/countriesByAreaEasy.data"), "UTF-8");
						else if(getDifficultyLevel() == DIFFICULTY.DIFFICULT) input = new Scanner(this.getClass().getResourceAsStream("/dataFiles/countriesByAreaDifficult.data"), "UTF-8");
						
						while(input.hasNext())
						{
							countriesByArea[counter] = input.nextInt();
							counter++;
						}
						input.close();
					}
					catch(Exception e)
					{
						if(input != null) input.close();
						
						Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load data for countries' area", e));
					}
				}
				
				if(availableQuestionsArray[SOVEREIGN_DEPENDENT_COUNTRIES] != null && !availableQuestionsArray[SOVEREIGN_DEPENDENT_COUNTRIES].isEmpty())
				{
					dependentStates = new int[availableQuestionsArray[SOVEREIGN_DEPENDENT_COUNTRIES].size()];
					Scanner input = null;
					int counter = 0;
					
					try
					{
						input = new Scanner(this.getClass().getResourceAsStream("/dataFiles/dependentStates.data"), "UTF-8");
						
						while(input.hasNext())
						{
							dependentStates[counter] = input.nextInt();
							counter++;
						}
						input.close();
					}
					catch(Exception e)
					{
						if(input != null) input.close();
						
						Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load data for dependent states", e));
					}
				}
				
				if(availableQuestionsArray[COUNTRIES_LANGUAGES] != null && !availableQuestionsArray[COUNTRIES_LANGUAGES].isEmpty())
				{
					languages = new String[NUM_COUNTRIES_INDIVIDUAL_LANGUAGE];
					Scanner input = null;
					int counter = 0;
					
					try
					{
						if(getCurrentLanguage() == LANGUAGE.GREEK) input = new Scanner(this.getClass().getResourceAsStream("/dataFiles/languagesGreek.data"), "UTF-8");
						else input = new Scanner(this.getClass().getResourceAsStream("/dataFiles/languagesEnglish.data"), "UTF-8");
						
						while(input.hasNext())
						{
							languages[counter] = input.nextLine();
							counter++;
						}
						input.close();
					}
					catch(Exception e)
					{
						if(input != null) input.close();
						
						Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load data for languages", e));
					}
				}
				
				if(availableQuestionsArray[COUNTRIES_POPULATION] != null && !availableQuestionsArray[COUNTRIES_POPULATION].isEmpty())
				{
					countriesPopulationBoundaries = new int[16];
					countriesPopulationBoundaries[0] = 0;
					countriesPopulationBoundaries[1] = 500;
					countriesPopulationBoundaries[2] = 1000;
					countriesPopulationBoundaries[3] = 10000;
					countriesPopulationBoundaries[4] = 100000;
					countriesPopulationBoundaries[5] = 500000;
					countriesPopulationBoundaries[6] = 1000000;
					countriesPopulationBoundaries[7] = 5000000;
					countriesPopulationBoundaries[8] = 10000000;
					countriesPopulationBoundaries[9] = 20000000;
					countriesPopulationBoundaries[10] = 50000000;
					countriesPopulationBoundaries[11] = 100000000;
					countriesPopulationBoundaries[12] = 200000000;
					countriesPopulationBoundaries[13] = 500000000;
					countriesPopulationBoundaries[14] = 1000000000;
					countriesPopulationBoundaries[15] = 2000000000;
				}
				
				if(availableQuestionsArray[COUNTRIES_CURRENCY] != null && !availableQuestionsArray[COUNTRIES_CURRENCY].isEmpty())
				{
					currencies = new String[NUM_INDIVIDUAL_CURRENCIES];
					Scanner input = null;
					int counter = 0;
					
					try
					{
						if(getCurrentLanguage() == LANGUAGE.GREEK) input = new Scanner(this.getClass().getResourceAsStream("/dataFiles/currenciesGreek.data"), "UTF-8");
						else input = new Scanner(this.getClass().getResourceAsStream("/dataFiles/currenciesEnglish.data"), "UTF-8");
						
						while(input.hasNext())
						{
							currencies[counter] = input.nextLine();
							counter++;
						}
						input.close();
					}
					catch(Exception e)
					{
						if(input != null) input.close();
						
						Platform.runLater(() -> new ErrorScreen("Error occurred while trying to load data for currency", e));
					}
				}
			});
			setAvailableQuestionsThread.start();
		}
		
		void decideNextQuestion()
		{
			try {setAvailableQuestionsThread.join();}catch (Exception ignored){}
			
			setImageSizes();
			
			randomCategoryPositionInListWithChosenCategories = random.nextInt(availableCategories.size());
			int index = availableCategories.get(randomCategoryPositionInListWithChosenCategories);
			
			if(index == CAT_COUNTRY_CAPITALS)
			{
				countriesCapitals();
				
				if(availableQuestionsArray[COUNTRIES_CAPITALS].isEmpty()) availableCategories.remove(randomCategoryPositionInListWithChosenCategories);
			}
			else if(index == CAT_CONTINENT_COUNTRY_LARGEST_CITIES)
			{
				if((random.nextInt(5) > 0 || availableQuestionsArray[CONTINENTS_CITIES].isEmpty()) && !availableQuestionsArray[COUNTRIES_CITIES].isEmpty()) countriesLargestCities();
				else continentsLargestCities();
				
				if(availableQuestionsArray[COUNTRIES_CITIES].isEmpty() && availableQuestionsArray[CONTINENTS_CITIES].isEmpty()) availableCategories.remove(randomCategoryPositionInListWithChosenCategories);
			}
			else if(index == CAT_COUNTRY_LANGUAGES)
			{
				countriesLanguages();
				
				if(availableQuestionsArray[COUNTRIES_LANGUAGES].isEmpty()) availableCategories.remove(randomCategoryPositionInListWithChosenCategories);
			}
			else if(index == CAT_COUNTRY_CURRENCY)
			{
				countriesCurrency();
				
				if(availableQuestionsArray[COUNTRIES_CURRENCY].isEmpty()) availableCategories.remove(randomCategoryPositionInListWithChosenCategories);
			}
			else if(index == CAT_CONTINENT_COUNTRY_POPULATION)
			{
				if((random.nextInt(5) > 0 || availableQuestionsArray[CONTINENTS_POPULATION].isEmpty()) && !availableQuestionsArray[COUNTRIES_POPULATION].isEmpty()) countriesPopulation();
				else continentsPopulation();
				
				if(availableQuestionsArray[COUNTRIES_POPULATION].isEmpty() && availableQuestionsArray[CONTINENTS_POPULATION].isEmpty()) availableCategories.remove(randomCategoryPositionInListWithChosenCategories);
			}
			else if(index == CAT_CONTINENT_COUNTRY_AREA)
			{
				if((random.nextInt(5) > 0 || availableQuestionsArray[CONTINENTS_AREA].isEmpty()) && !availableQuestionsArray[COUNTRIES_AREA].isEmpty()) countriesArea();
				else continentsArea();
				
				if(availableQuestionsArray[COUNTRIES_AREA].isEmpty() && availableQuestionsArray[CONTINENTS_AREA].isEmpty()) availableCategories.remove(randomCategoryPositionInListWithChosenCategories);
			}
			else if(index == CAT_CONTINENTS_COUNTRIES)
			{
				if((random.nextInt(5) > 0 || availableQuestionsArray[CONTINENTS_LARGEST_COUNTRIES].isEmpty()) && !availableQuestionsArray[CONTINENTS_COUNTRIES].isEmpty()) continentsCountries();
				else continentsLargestCountries();
				
				if(availableQuestionsArray[CONTINENTS_COUNTRIES].isEmpty() && availableQuestionsArray[CONTINENTS_LARGEST_COUNTRIES].isEmpty())
					availableCategories.remove(randomCategoryPositionInListWithChosenCategories);
			}
			else if(index == CAT_SOVEREIGN_DEPENDENT_COUNTRIES)
			{
				sovereignDependentCountries();
				
				if(availableQuestionsArray[SOVEREIGN_DEPENDENT_COUNTRIES].isEmpty()) availableCategories.remove(randomCategoryPositionInListWithChosenCategories);
			}
			else if(index == CAT_COUNTRY_FLAGS)
			{
				countriesFlags();
				
				if(availableQuestionsArray[COUNTRIES_FLAGS].isEmpty()) availableCategories.remove(randomCategoryPositionInListWithChosenCategories);
			}
			else if(index == CAT_COUNTRY_COAT_OF_ARMS)
			{
				countriesCoatOfArms();
				
				if(availableQuestionsArray[COUNTRIES_COAT_OF_ARMS].isEmpty()) availableCategories.remove(randomCategoryPositionInListWithChosenCategories);
			}
			else if(index == CAT_CONTINENT_COUNTRY_LOCATION)
			{
				int y;
				if(getDifficultyLevel() != DIFFICULTY.DIFFICULT)
				{
					y = random.nextInt(10);
					if ((y > 3 || availableQuestionsArray[COUNTRIES_LOCATION_ALTERNATIVE].isEmpty() && availableQuestionsArray[CONTINENTS_LOCATION].isEmpty()) &&
					    !availableQuestionsArray[COUNTRIES_LOCATION].isEmpty()) countriesLocation();
					else if (y > 0 || availableQuestionsArray[CONTINENTS_LOCATION].isEmpty()) countriesLocationAlternative();
					else continentsLocation();
					
					if(availableQuestionsArray[COUNTRIES_LOCATION].isEmpty() && availableQuestionsArray[COUNTRIES_LOCATION_ALTERNATIVE].isEmpty() && availableQuestionsArray[CONTINENTS_LOCATION].isEmpty())
						availableCategories.remove(randomCategoryPositionInListWithChosenCategories);
				}
				else
				{
					y = random.nextInt(9);
					if ((y > 2 || availableQuestionsArray[COUNTRIES_LOCATION_ALTERNATIVE].isEmpty()) && !availableQuestionsArray[COUNTRIES_LOCATION].isEmpty()) countriesLocation();
					else countriesLocationAlternative();
					
					if(availableQuestionsArray[COUNTRIES_LOCATION].isEmpty() && availableQuestionsArray[COUNTRIES_LOCATION_ALTERNATIVE].isEmpty())
						availableCategories.remove(randomCategoryPositionInListWithChosenCategories);
				}
			}
		}
		
		private void countriesCapitals()
		{
			correctIndex = availableQuestionsArray[COUNTRIES_CAPITALS].get(0);
			availableQuestionsArray[COUNTRIES_CAPITALS].remove(0);
			
			easyQuestion = countries[correctIndex].askCapital() == 1;
			
			wayOfAskingCurrentQuestion = random.nextInt(3) + 1;
			
			if(wayOfAskingCurrentQuestion != 3)
			{
				do randomIndexForMultipleChoice[0] = random.nextInt(countries.length);
				while(countries[correctIndex].askCapital() != countries[randomIndexForMultipleChoice[0]].askCapital() ||
				      countries[randomIndexForMultipleChoice[0]].getCapitalName().equals(countries[correctIndex].getCapitalName()));
				do randomIndexForMultipleChoice[1] = random.nextInt(countries.length);
				while (countries[correctIndex].askCapital() != countries[randomIndexForMultipleChoice[1]].askCapital() ||
						countries[randomIndexForMultipleChoice[1]].getCapitalName().equals(countries[correctIndex].getCapitalName()) ||
						countries[randomIndexForMultipleChoice[1]].getCapitalName().equals(countries[randomIndexForMultipleChoice[0]].getCapitalName()));
				do randomIndexForMultipleChoice[2] = random.nextInt(countries.length);
				while (countries[correctIndex].askCapital() != countries[randomIndexForMultipleChoice[1]].askCapital() ||
						countries[randomIndexForMultipleChoice[2]].getCapitalName().equals(countries[correctIndex].getCapitalName()) ||
						countries[randomIndexForMultipleChoice[2]].getCapitalName().equals(countries[randomIndexForMultipleChoice[0]].getCapitalName()) ||
						countries[randomIndexForMultipleChoice[2]].getCapitalName().equals(countries[randomIndexForMultipleChoice[1]].getCapitalName()));
			}
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					textForQuestion.setText("Ποια χώρα έχει πρωτεύουσα " + GreekLanguageHelper.getAccusative(countries[correctIndex].getArticleForCapital(),
							GreekLanguageHelper.needsN(countries[correctIndex].getCapitalName())).toLowerCase() + " " + countries[correctIndex].getCapitalName() + ";");
					correctAnswerString = countries[correctIndex].getNameInGreek();
					
					for(int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getNameInGreek();
				}
				else
				{
					textForQuestion.setText(countries[correctIndex].getCapitalName() + " is the capital of...?");
					correctAnswerString = countries[correctIndex].getNameInEnglish();
					
					for(int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getNameInEnglish();
				}
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποια είναι η πρωτεύουσα " + GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
				                                                     " " + countries[correctIndex].getGenitiveCaseOfCountry() + ";");
				else textForQuestion.setText("What is the capital of " + countries[correctIndex].getNameInEnglish() + "?");
				
				correctAnswerString = countries[correctIndex].getCapitalName();
				
				for(int i = 0; i < 3; i++)
					randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getCapitalName();
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				
				if (positionOfCorrectAnswer == 0)
				{
					if (getCurrentLanguage() == LANGUAGE.GREEK)
					{
						String verb;
						if(countries[correctIndex].getArticleForCountry().equals("Οι") || countries[correctIndex].getArticleForCountry().equals("Τα"))
							verb = " έχουν";
						else verb = " έχει";
						textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek() + verb +
						                        " πρωτεύουσα " + GreekLanguageHelper.getAccusative(countries[correctIndex].getArticleForCapital(),
								GreekLanguageHelper.needsN(countries[correctIndex].getCapitalName())).toLowerCase() + " " + countries[correctIndex].getCapitalName() + ".");
					}
					else textForQuestion.setText(countries[correctIndex].getCapitalName() +
					                             " is the capital of " + countries[correctIndex].getNameInEnglish() + ".");
				}
				else
				{
					int index;
					do index = random.nextInt(countries.length);
					while(countries[correctIndex].askCapital() != countries[index].askCapital() ||
							countries[index].getCapitalName().equals(countries[correctIndex].getCapitalName()));
					
					if (getCurrentLanguage() == LANGUAGE.GREEK)
					{
						String verb;
						if(countries[correctIndex].getArticleForCountry().equals("Οι") || countries[correctIndex].getArticleForCountry().equals("Τα"))
							verb = " έχουν";
						else verb = " έχει";
						textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek() + verb +
						                        " πρωτεύουσα " + GreekLanguageHelper.getAccusative(countries[index].getArticleForCapital(),
								GreekLanguageHelper.needsN(countries[index].getCapitalName())).toLowerCase() + " " + countries[index].getCapitalName() + ".");
					}
					else textForQuestion.setText(countries[index].getCapitalName() + " is the capital of " + countries[correctIndex].getNameInEnglish() + ".");
				}
			}
			
			if(wayOfAskingCurrentQuestion == 3)
			{
				typeOfQuestion = 1;
				showNextQuestionNodes(false, 1);
			}
			else
			{
				typeOfQuestion = 2;
				set4TextAnswers();
				showNextQuestionNodes(false, 2);
			}
		}
		
		private void countriesLargestCities()
		{
			correctIndex = availableQuestionsArray[COUNTRIES_CITIES].get(0);
			availableQuestionsArray[COUNTRIES_CITIES].remove(0);
			
			int y;
			if(getDifficultyLevel() == DIFFICULTY.EASY) y = 0;
			else y = random.nextInt(3); // decide to ask largest, or 2nd largest, or 3rd
			
			easyQuestion = countries[correctIndex].askLargestCity() == 1 || y == 0;
			
			wayOfAskingCurrentQuestion = random.nextInt(3) + 1;
			
			if(wayOfAskingCurrentQuestion != 3)
			{
				do randomIndexForMultipleChoice[0] = random.nextInt(countries.length);
				while(countries[correctIndex].askLargestCity() != countries[randomIndexForMultipleChoice[0]].askLargestCity() ||
				      randomIndexForMultipleChoice[0] == correctIndex);
				do randomIndexForMultipleChoice[1] = random.nextInt(countries.length);
				while (countries[correctIndex].askLargestCity() != countries[randomIndexForMultipleChoice[1]].askLargestCity() ||
				       randomIndexForMultipleChoice[1] == correctIndex ||
				       randomIndexForMultipleChoice[1] == randomIndexForMultipleChoice[0]);
				do randomIndexForMultipleChoice[2] = random.nextInt(countries.length);
				while (countries[correctIndex].askLargestCity() != countries[randomIndexForMultipleChoice[2]].askLargestCity() ||
				       randomIndexForMultipleChoice[2] == correctIndex ||
				       randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[0] ||
				       randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[1]);
			}
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				if(countries[correctIndex].getLargestCities().length == 1 || y == 0)
				{
					if(getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η πόλη " + countries[correctIndex].getLargestCities()[0] + " είναι η μεγαλύτερη πόλη της χώρας...;");
					else textForQuestion.setText(countries[correctIndex].getLargestCities()[0] + " is the largest city of...?");
				}
				else
				{
					if(y == 1)
					{
						if(getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η πόλη " + countries[correctIndex].getLargestCities()[1] + " είναι η 2η μεγαλύτερη πόλη της χώρας...;");
						else textForQuestion.setText(countries[correctIndex].getLargestCities()[1] + " is the 2nd largest city of...?");
					}
					else
					{
						if(getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η πόλη " + countries[correctIndex].getLargestCities()[2] + " είναι η 3η μεγαλύτερη πόλη της χώρας...;");
						else textForQuestion.setText(countries[correctIndex].getLargestCities()[2] + " is the 3nd largest city of...?");
					}
				}
				
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					correctAnswerString = countries[correctIndex].getNameInGreek();
					for(int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getNameInGreek();
				}
				else
				{
					correctAnswerString = countries[correctIndex].getNameInEnglish();
					for(int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getNameInEnglish();
				}
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				int r1, r2, r3;
				if(countries[correctIndex].getLargestCities().length == 1 || y == 0)
				{
					if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποια είναι η μεγαλύτερη πόλη " + GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
					                                                     " " + countries[correctIndex].getGenitiveCaseOfCountry() + ";");
					else textForQuestion.setText("What is the largest city of " + countries[correctIndex].getNameInEnglish() + "?");
					
					correctAnswerString = countries[correctIndex].getLargestCities()[0];
					
					if(countries[correctIndex].getLargestCities().length == 1)
					{
						for (int i = 0; i < 3; i++)
						{
							if (countries[randomIndexForMultipleChoice[i]].getLargestCities().length == 1) randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getLargestCities()[0];
							else randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getLargestCities()[random.nextInt(10)];
						}
					}
					else
					{
						r1 = random.nextInt(9) + 1;
						do r2 = random.nextInt(9) + 1; while(r2 == r1);
						do r3 = random.nextInt(9) + 1; while(r3 == r1 || r3 == r2);
						
						randomStringForMultipleChoice[0] = countries[correctIndex].getLargestCities()[r1];
						randomStringForMultipleChoice[1] = countries[correctIndex].getLargestCities()[r2];
						randomStringForMultipleChoice[2] = countries[correctIndex].getLargestCities()[r3];
					}
				}
				else
				{
					if(y == 1)
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποια είναι η 2η μεγαλύτερη πόλη " + GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
						                                                     " " + countries[correctIndex].getGenitiveCaseOfCountry() + ";");
						else textForQuestion.setText("What is the 2nd largest city of " + countries[correctIndex].getNameInEnglish() + "?");
						
						correctAnswerString = countries[correctIndex].getLargestCities()[1];
						
						do r1 = random.nextInt(10); while(r1 == 1);
						do r2 = random.nextInt(10); while(r2 == 1 || r2 == r1);
						do r3 = random.nextInt(10); while(r3 == 1 || r3 == r1 || r3 == r2);
					}
					else
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποια είναι η 3η μεγαλύτερη πόλη " + GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
						                                                     " " + countries[correctIndex].getGenitiveCaseOfCountry() + ";");
						else textForQuestion.setText("What is the 3rd largest city of " + countries[correctIndex].getNameInEnglish() + "?");
						
						correctAnswerString = countries[correctIndex].getLargestCities()[2];
						
						do r1 = random.nextInt(10); while(r1 == 2);
						do r2 = random.nextInt(10); while(r2 == 2 || r2 == r1);
						do r3 = random.nextInt(10); while(r3 == 2 || r3 == r1 || r3 == r2);
					}
					
					randomStringForMultipleChoice[0] = countries[correctIndex].getLargestCities()[r1];
					randomStringForMultipleChoice[1] = countries[correctIndex].getLargestCities()[r2];
					randomStringForMultipleChoice[2] = countries[correctIndex].getLargestCities()[r3];
				}
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				
				if (positionOfCorrectAnswer == 0)
				{
					if(countries[correctIndex].getLargestCities().length == 1 || y == 0)
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η πόλη " + countries[correctIndex].getLargestCities()[0] +
						                                                     " είναι η μεγαλύτερη πόλη " + GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
						                                                     " " + countries[correctIndex].getGenitiveCaseOfCountry() + ".");
						else textForQuestion.setText(countries[correctIndex].getLargestCities()[0] +
						                             " is the largest city of " + countries[correctIndex].getNameInEnglish() + ".");
					}
					else
					{
						if(y == 1)
						{
							if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η πόλη " + countries[correctIndex].getLargestCities()[1] +
							                                                     " είναι η 2η μεγαλύτερη πόλη " + GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
							                                                     " " + countries[correctIndex].getGenitiveCaseOfCountry() + ".");
							else textForQuestion.setText(countries[correctIndex].getLargestCities()[1] +
							                             " is the 2nd largest city of " + countries[correctIndex].getNameInEnglish() + ".");
						}
						else
						{
							if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η πόλη " + countries[correctIndex].getLargestCities()[2] +
							                                                     " είναι η 3η μεγαλύτερη πόλη " + GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
							                                                     " " + countries[correctIndex].getGenitiveCaseOfCountry() + ".");
							else textForQuestion.setText(countries[correctIndex].getLargestCities()[2] +
							                             " is the 3rd largest city of " + countries[correctIndex].getNameInEnglish() + ".");
						}
					}
				}
				else
				{
					String randomLar;
					int index;
					
					if (countries[correctIndex].getLargestCities().length == 1)
					{
						do index = random.nextInt(countries.length);
						while(index == correctIndex);
						
						if (countries[index].getLargestCities().length == 1) randomLar = countries[index].getLargestCities()[0];
						else randomLar = countries[index].getLargestCities()[random.nextInt(10)];
					}
					else
					{
						if(y == 0) index = random.nextInt(9) + 1;
						else if(y == 1) do index = random.nextInt(10); while(index == 1);
						else do index = random.nextInt(10); while(index == 2);
						randomLar = countries[correctIndex].getLargestCities()[index];
					}
					if(y == 0)
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η πόλη " + randomLar + " είναι η μεγαλύτερη πόλη " +
						                                                     GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
						                                                     " " + countries[correctIndex].getGenitiveCaseOfCountry() + ".");
						else textForQuestion.setText(randomLar + " is the largest city of " + countries[correctIndex].getNameInEnglish() + ".");
					}
					else if(y == 1)
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η πόλη " + randomLar + " είναι η 2η μεγαλύτερη πόλη " +
						                                                     GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
						                                                     " " + countries[correctIndex].getGenitiveCaseOfCountry() + ".");
						else textForQuestion.setText(randomLar + " is the 2nd largest city of " + countries[correctIndex].getNameInEnglish() + ".");
					}
					else
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η πόλη " + randomLar + " είναι η 3η μεγαλύτερη πόλη " +
						                                                     GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
						                                                     " " + countries[correctIndex].getGenitiveCaseOfCountry() + ".");
						else textForQuestion.setText(randomLar + " is the 3rd largest city of " + countries[correctIndex].getNameInEnglish() + ".");
					}
				}
			}
			
			if(wayOfAskingCurrentQuestion == 3)
			{
				typeOfQuestion = 1;
				showNextQuestionNodes(false, 1);
			}
			else
			{
				typeOfQuestion = 2;
				set4TextAnswers();
				showNextQuestionNodes(false, 2);
			}
		}
		
		private void continentsLargestCities()
		{
			correctIndex = availableQuestionsArray[CONTINENTS_CITIES].get(0);
			availableQuestionsArray[CONTINENTS_CITIES].remove(0);
			
			int y = random.nextInt(3); // decide to ask largest, or 2nd largest, or 3rd
			easyQuestion = y == 0;
			
			wayOfAskingCurrentQuestion = random.nextInt(2) + 1;
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				int r1, r2, r3;
				if(y == 0)
				{
					if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποια είναι η μεγαλύτερη πόλη της " + continents[correctIndex].getGenitiveCaseOfContinent() + ";");
					else textForQuestion.setText("What is the largest city of " + continents[correctIndex].getNameInEnglish() + "?");
					
					correctAnswerString = continents[correctIndex].getLargestCities()[0];
					
					r1 = random.nextInt(9) + 1;
					do r2 = random.nextInt(9) + 1; while(r2 == r1);
					do r3 = random.nextInt(9) + 1; while(r3 == r1 || r3 == r2);
				}
				else if(y == 1)
				{
					if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποια είναι η 2η μεγαλύτερη πόλη της " + continents[correctIndex].getGenitiveCaseOfContinent() + ";");
					else textForQuestion.setText("What is the 2nd largest city of " + continents[correctIndex].getNameInEnglish() + "?");
					
					correctAnswerString = continents[correctIndex].getLargestCities()[1];
					
					do r1 = random.nextInt(10); while(r1 == 1);
					do r2 = random.nextInt(10); while(r2 == 1 || r2 == r1);
					do r3 = random.nextInt(10); while(r3 == 1 || r3 == r1 || r3 == r2);
				}
				else
				{
					if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποια είναι η 3η μεγαλύτερη πόλη της " + continents[correctIndex].getGenitiveCaseOfContinent() + ";");
					else textForQuestion.setText("What is the 3rd largest city of " + continents[correctIndex].getNameInEnglish() + "?");
					
					correctAnswerString = continents[correctIndex].getLargestCities()[2];
					
					do r1 = random.nextInt(10); while(r1 == 2);
					do r2 = random.nextInt(10); while(r2 == 2 || r2 == r1);
					do r3 = random.nextInt(10); while(r3 == 2 || r3 == r1 || r3 == r2);
				}
				
				randomStringForMultipleChoice[0] = continents[correctIndex].getLargestCities()[r1];
				randomStringForMultipleChoice[1] = continents[correctIndex].getLargestCities()[r2];
				randomStringForMultipleChoice[2] = continents[correctIndex].getLargestCities()[r3];
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				
				if (positionOfCorrectAnswer == 0)
				{
					if(y == 0)
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η πόλη " + continents[correctIndex].getLargestCities()[0] +
						                                                     " είναι η μεγαλύτερη πόλη της " + continents[correctIndex].getGenitiveCaseOfContinent() + ".");
						else textForQuestion.setText(continents[correctIndex].getLargestCities()[0] +
						                             " is the largest city of " + continents[correctIndex].getNameInEnglish() + ".");
					}
					else if(y == 1)
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η πόλη " + continents[correctIndex].getLargestCities()[1] +
						                                                     " είναι η 2η μεγαλύτερη πόλη της " + continents[correctIndex].getGenitiveCaseOfContinent() + ".");
						else textForQuestion.setText(continents[correctIndex].getLargestCities()[1] +
						                             " is the 2nd largest city of " + continents[correctIndex].getNameInEnglish() + ".");
					}
					else
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η πόλη " + continents[correctIndex].getLargestCities()[2] +
						                                                     " είναι η 3η μεγαλύτερη πόλη της " + continents[correctIndex].getGenitiveCaseOfContinent() + ".");
						else textForQuestion.setText(continents[correctIndex].getLargestCities()[2] +
						                             " is the 3rd largest city of " + continents[correctIndex].getNameInEnglish() + ".");
					}
				}
				else
				{
					String randomLar;
					int index;
					
					if(y == 0) index = random.nextInt(9) + 1;
					else if(y == 1) do index = random.nextInt(10); while(index == 1);
					else do index = random.nextInt(10); while(index == 2);
					randomLar = continents[correctIndex].getLargestCities()[index];
					
					if(y == 0)
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η πόλη " + randomLar + " είναι η μεγαλύτερη πόλη της " + continents[correctIndex].getGenitiveCaseOfContinent() + ".");
						else textForQuestion.setText(randomLar + " is the largest city of " + continents[correctIndex].getNameInEnglish() + ".");
					}
					else if(y == 1)
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η πόλη " + randomLar + " είναι η 2η μεγαλύτερη πόλη της " + continents[correctIndex].getGenitiveCaseOfContinent() + ".");
						else textForQuestion.setText(randomLar + " is the 2nd largest city of " + continents[correctIndex].getNameInEnglish() + ".");
					}
					else
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η πόλη " + randomLar + " είναι η 3η μεγαλύτερη πόλη της " + continents[correctIndex].getGenitiveCaseOfContinent() + ".");
						else textForQuestion.setText(randomLar + " is the 3rd largest city of " + continents[correctIndex].getNameInEnglish() + ".");
					}
				}
			}
			
			if(wayOfAskingCurrentQuestion == 2)
			{
				typeOfQuestion = 1;
				showNextQuestionNodes(false, 1);
			}
			else
			{
				typeOfQuestion = 2;
				set4TextAnswers();
				showNextQuestionNodes(false, 2);
			}
		}
		
		private void countriesLanguages()
		{
			correctIndex = availableQuestionsArray[COUNTRIES_LANGUAGES].get(0);
			availableQuestionsArray[COUNTRIES_LANGUAGES].remove(0);
			
			easyQuestion = false;
			
			wayOfAskingCurrentQuestion = random.nextInt(3) + 1;
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				if(countries[correctIndex].askLanguage() == 1)
				{
					do randomIndexForMultipleChoice[0] = random.nextInt(countries.length);
					while(countries[randomIndexForMultipleChoice[0]].getLanguagesString().contains(countries[correctIndex].getLanguagesToAsk()[0]));
					do randomIndexForMultipleChoice[1] = random.nextInt(countries.length);
					while(countries[randomIndexForMultipleChoice[1]].getLanguagesString().contains(countries[correctIndex].getLanguagesToAsk()[0]) ||
					      randomIndexForMultipleChoice[1] == randomIndexForMultipleChoice[0]);
					do randomIndexForMultipleChoice[2] = random.nextInt(countries.length);
					while(countries[randomIndexForMultipleChoice[2]].getLanguagesString().contains(countries[correctIndex].getLanguagesToAsk()[0]) ||
					      randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[0] ||
					      randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[1]);
					
					if(getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Τα " + countries[correctIndex].getLanguagesToAsk()[0] + " σε ποια από τις ακόλουθες χώρες είναι η επίσημη γλώσσα;");
					else textForQuestion.setText(countries[correctIndex].getLanguagesToAsk()[0] + " is the official language of...?");
				}
				else
				{
					String lang = countries[correctIndex].getLanguagesToAsk()[random.nextInt(countries[correctIndex].getLanguagesToAsk().length)];
					
					do randomIndexForMultipleChoice[0] = random.nextInt(countries.length);
					while(countries[randomIndexForMultipleChoice[0]].getLanguagesString().contains(lang));
					do randomIndexForMultipleChoice[1] = random.nextInt(countries.length);
					while(countries[randomIndexForMultipleChoice[1]].getLanguagesString().contains(lang) ||
					      randomIndexForMultipleChoice[1] == randomIndexForMultipleChoice[0]);
					do randomIndexForMultipleChoice[2] = random.nextInt(countries.length);
					while(countries[randomIndexForMultipleChoice[2]].getLanguagesString().contains(lang) ||
					      randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[0] ||
					      randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[1]);
					
					if(getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Τα " + lang + " είναι μία από τις επίσημες γλώσσες της χώρας...;");
					else textForQuestion.setText(lang + " is one of the official languages of...?");
				}
				
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					correctAnswerString = countries[correctIndex].getNameInGreek();
					
					for(int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getNameInGreek();
				}
				else
				{
					correctAnswerString = countries[correctIndex].getNameInEnglish();
					
					for(int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getNameInEnglish();
				}
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				do randomIndexForMultipleChoice[0] = random.nextInt(languages.length);
				while(countries[correctIndex].getLanguagesString().contains(languages[randomIndexForMultipleChoice[0]]));
				do randomIndexForMultipleChoice[1] = random.nextInt(languages.length);
				while(countries[correctIndex].getLanguagesString().contains(languages[randomIndexForMultipleChoice[1]]) ||
				      randomIndexForMultipleChoice[1] == randomIndexForMultipleChoice[0]);
				do randomIndexForMultipleChoice[2] = random.nextInt(languages.length);
				while(countries[correctIndex].getLanguagesString().contains(languages[randomIndexForMultipleChoice[2]]) ||
				      randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[0] ||
				      randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[1]);
				
				if(countries[correctIndex].askLanguage() == 1)
				{
					if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποια είναι η επίσημη γλώσσα " + GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
							" " + countries[correctIndex].getGenitiveCaseOfCountry() + ";");
					else textForQuestion.setText("Which of the following is the official language of " + countries[correctIndex].getNameInEnglish() + "?");
					
					correctAnswerString = countries[correctIndex].getLanguagesToAsk()[0];
				}
				else
				{
					if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Μία από τις επίσημες γλώσσες " + GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
							" " + countries[correctIndex].getGenitiveCaseOfCountry() + " είναι τα...;");
					else textForQuestion.setText("Which of the following is one of the official languages of " + countries[correctIndex].getNameInEnglish() + "?");
					
					correctAnswerString = countries[correctIndex].getLanguagesToAsk()[random.nextInt(countries[correctIndex].getLanguagesToAsk().length)];
				}
				
				for(int i = 0; i < 3; i++)
					randomStringForMultipleChoice[i] = languages[randomIndexForMultipleChoice[i]];
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				
				if(positionOfCorrectAnswer == 0)
				{
					if(countries[correctIndex].askLanguage() == 1)
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Τα " + countries[correctIndex].getLanguagesToAsk()[0] +
						                                                     " είναι η επίσημη γλώσσα " + GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
								" " + countries[correctIndex].getGenitiveCaseOfCountry() + ".");
						else textForQuestion.setText(countries[correctIndex].getLanguagesToAsk()[0] +
						                             " is the official language of " + countries[correctIndex].getNameInEnglish() + ".");
					}
					else
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK)
							textForQuestion.setText("Τα " + countries[correctIndex].getLanguagesToAsk()[random.nextInt(countries[correctIndex].getLanguagesToAsk().length)] +
							                        " είναι μία από τις επίσημες γλώσσες " + GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
									" " + countries[correctIndex].getGenitiveCaseOfCountry() + ".");
						else textForQuestion.setText(countries[correctIndex].getLanguagesToAsk()[
								                             random.nextInt(countries[correctIndex].getLanguagesToAsk().length)] +
						                             " is one of the official languages of " + countries[correctIndex].getNameInEnglish() + ".");
					}
				}
				else
				{
					String randomCap;
					do randomCap = languages[random.nextInt(languages.length)];
					while(countries[correctIndex].getLanguagesString().contains(randomCap));
					
					if(countries[correctIndex].askLanguage() == 1)
					{
						if(getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Τα " + randomCap + " είναι η επίσημη γλώσσα " + GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
								" " + countries[correctIndex].getGenitiveCaseOfCountry() + ".");
						else textForQuestion.setText(randomCap + " is the official language of " + countries[correctIndex].getNameInEnglish() + ".");
					}
					else
					{
						if(getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Τα " + randomCap + " είναι μία από τις επίσημες γλώσσες " +
						                                                    GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
								" " + countries[correctIndex].getGenitiveCaseOfCountry() + ".");
						else textForQuestion.setText(randomCap + " is one of the official languages of " + countries[correctIndex].getNameInEnglish() + ".");
					}
				}
			}
			
			if(wayOfAskingCurrentQuestion == 3)
			{
				typeOfQuestion = 1;
				showNextQuestionNodes(false, 1);
			}
			else
			{
				typeOfQuestion = 2;
				set4TextAnswers();
				showNextQuestionNodes(false, 2);
			}
		}
		
		private void countriesCurrency()
		{
			correctIndex = availableQuestionsArray[COUNTRIES_CURRENCY].get(0);
			availableQuestionsArray[COUNTRIES_CURRENCY].remove(0);
			
			easyQuestion = countries[correctIndex].askCurrency() == 1;
			
			wayOfAskingCurrentQuestion = random.nextInt(2) + 1;
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				do randomIndexForMultipleChoice[0] = random.nextInt(currencies.length);
				while(currencies[randomIndexForMultipleChoice[0]].equals(countries[correctIndex].getCurrency().getName()));
				do randomIndexForMultipleChoice[1] = random.nextInt(currencies.length);
				while(currencies[randomIndexForMultipleChoice[1]].equals(countries[correctIndex].getCurrency().getName()) ||
				      randomIndexForMultipleChoice[1] == randomIndexForMultipleChoice[0]);
				do randomIndexForMultipleChoice[2] = random.nextInt(currencies.length);
				while(currencies[randomIndexForMultipleChoice[2]].equals(countries[correctIndex].getCurrency().getName()) ||
				      randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[0] ||
				      randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[1]);
				
				correctAnswerString = countries[correctIndex].getCurrency().getName();
				
				if(getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποιο είναι το νόμισμα " + GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
						" " + countries[correctIndex].getGenitiveCaseOfCountry() + ";");
				else textForQuestion.setText("What is the official currency of " + countries[correctIndex].getNameInEnglish() + "?");
				
				for(int i = 0; i < 3; i++) randomStringForMultipleChoice[i] = currencies[randomIndexForMultipleChoice[i]];
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				
				if(positionOfCorrectAnswer == 0)
				{
					if(getCurrentLanguage() == LANGUAGE.GREEK)
					{
						textForQuestion.setText(
								"Το νόμισμα " + countries[correctIndex].getCurrency().getName() +
								" είναι το επίσημο νόμισμα " + GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
								" " + countries[correctIndex].getGenitiveCaseOfCountry() + ".");
					}
					else
					{
						textForQuestion.setText(
								countries[correctIndex].getCurrency().getName() +
								" is the official currency of " + countries[correctIndex].getNameInEnglish() + ".");
					}
				}
				else
				{
					int index;
					do index = random.nextInt(currencies.length);
					while(currencies[index].equals(countries[correctIndex].getCurrency().getName()));
					
					if(getCurrentLanguage() == LANGUAGE.GREEK)
					{
						textForQuestion.setText(
								"Το νόμισμα " + countries[index].getCurrency().getName() +
								" είναι το επίσημο νόμισμα " + GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
								" " + countries[correctIndex].getGenitiveCaseOfCountry() + ".");
					}
					else
					{
						textForQuestion.setText(
								countries[index].getCurrency().getName() +
								" is the official currency of " + countries[correctIndex].getNameInEnglish() + ".");
					}
				}
			}
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				typeOfQuestion = 2;
				set4TextAnswers();
				showNextQuestionNodes(false, 2);
			}
			else
			{
				typeOfQuestion = 1;
				showNextQuestionNodes(false, 1);
			}
		}
		
		private void countriesPopulation()
		{
			correctIndex = availableQuestionsArray[COUNTRIES_POPULATION].get(0);
			availableQuestionsArray[COUNTRIES_POPULATION].remove(0);
			
			easyQuestion = countries[correctIndex].hasEasyLocation();
			
			wayOfAskingCurrentQuestion = random.nextInt(2) + 1;
			
			int lowBoundary = 0;
			
			for(int i = 0; i < countriesPopulationBoundaries.length; i++)
			{
				if(countries[correctIndex].getPopulation().getPopulation() < countriesPopulationBoundaries[i])
				{
					lowBoundary = i - 1;
					break;
				}
			}
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					String verb;
					if(countries[correctIndex].getArticleForCountry().equals("Οι") || countries[correctIndex].getArticleForCountry().equals("Τα"))
						verb = "έχουν ";
					else verb = "έχει ";
					textForQuestion.setText("Τι πληθυσμό " + verb + countries[correctIndex].getArticleForCountry().toLowerCase() + " " +
					                        countries[correctIndex].getNameInGreek() + ";");
				}
				else textForQuestion.setText("What is the population of " + countries[correctIndex].getNameInEnglish() + "?");
				
				if(lowBoundary == 0) positionOfCorrectAnswer = 0;
				else if(lowBoundary == 1) positionOfCorrectAnswer = random.nextInt(2);
				else if(lowBoundary == 2) positionOfCorrectAnswer = random.nextInt(3);
				else if(lowBoundary == 12) positionOfCorrectAnswer = random.nextInt(3) + 1;
				else if(lowBoundary == 13) positionOfCorrectAnswer = random.nextInt(2) + 2;
				else if(lowBoundary == 14) positionOfCorrectAnswer = 3;
				else positionOfCorrectAnswer = random.nextInt(4);
				
				if(positionOfCorrectAnswer == 0)
				{
					radioButtonsFor4TextAnswers[0].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary]) + " - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 1]));
					radioButtonsFor4TextAnswers[1].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 1]) + " - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 2]));
					radioButtonsFor4TextAnswers[2].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 2]) + " - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 3]));
					radioButtonsFor4TextAnswers[3].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 3]) + " - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 4]));
				}
				else if(positionOfCorrectAnswer == 1)
				{
					radioButtonsFor4TextAnswers[0].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary - 1]) + " - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary]));
					radioButtonsFor4TextAnswers[1].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary]) + " - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 1]));
					radioButtonsFor4TextAnswers[2].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 1]) + " - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 2]));
					radioButtonsFor4TextAnswers[3].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 2]) + " - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 3]));
				}
				else if(positionOfCorrectAnswer == 2)
				{
					radioButtonsFor4TextAnswers[0].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary - 2]) + " - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary - 1]));
					radioButtonsFor4TextAnswers[1].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary - 1]) + " - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary]));
					radioButtonsFor4TextAnswers[2].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary]) + " - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 1]));
					radioButtonsFor4TextAnswers[3].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 1]) + " - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 2]));
				}
				else
				{
					radioButtonsFor4TextAnswers[0].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary - 3]) + " - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary - 2]));
					radioButtonsFor4TextAnswers[1].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary - 2]) + " - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary - 1]));
					radioButtonsFor4TextAnswers[2].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary - 1]) + " - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary]));
					radioButtonsFor4TextAnswers[3].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary]) + " - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 1]));
				}
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				
				String s = "";
				
				if(positionOfCorrectAnswer == 0) s = numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary]) + " - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 1]);
				else
				{
					int rand = random.nextInt(2);
					if(lowBoundary == 14 || rand == 0) s = numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary - 1]) + " - " +
					                                       numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary]);
					else if(lowBoundary == 0 || rand == 1) s = numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 1]) + " - " +
					                                           numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 2]);
				}
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					String verb;
					if(countries[correctIndex].getArticleForCountry().equals("Οι") || countries[correctIndex].getArticleForCountry().equals("Τα"))
						verb = " έχουν";
					else verb = " έχει";
					textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " "
					                        + countries[correctIndex].getNameInGreek() + verb + " πληθυσμό " + s + " κατοίκους.");
				}
				else textForQuestion.setText(countries[correctIndex].getNameInEnglish() + "has a population of " + s + " inhabitants.");
			}
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				typeOfQuestion = 2;
				showNextQuestionNodes(false, 2);
			}
			else
			{
				typeOfQuestion = 1;
				showNextQuestionNodes(false, 1);
			}
		}
		
		private void continentsPopulation()
		{
			correctIndex = availableQuestionsArray[CONTINENTS_POPULATION].get(0);
			availableQuestionsArray[CONTINENTS_POPULATION].remove(0);
			
			easyQuestion = continents[correctIndex].getGlobalPopulationRanking() < 3;
			
			wayOfAskingCurrentQuestion = random.nextInt(3) + 1;
			
			String t = "";
			if(getCurrentLanguage() == LANGUAGE.ENGLISH)
			{
				if(continents[correctIndex].getGlobalPopulationRanking() == 2)
					t = "nd";
				else if(continents[correctIndex].getGlobalPopulationRanking() == 3)
					t = "rd";
				else t = "th";
			}
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				do randomIndexForMultipleChoice[0] = random.nextInt(continents.length - 1) + 1; while(randomIndexForMultipleChoice[0] == correctIndex);
				do randomIndexForMultipleChoice[1] = random.nextInt(continents.length - 1) + 1; while(randomIndexForMultipleChoice[1] == correctIndex ||
				                                                                              randomIndexForMultipleChoice[1] == randomIndexForMultipleChoice[0]);
				do randomIndexForMultipleChoice[2] = random.nextInt(continents.length - 1) + 1; while(randomIndexForMultipleChoice[2] == correctIndex ||
				                                                                              randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[0] ||
				                                                                              randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[1]);
				
				if(continents[correctIndex].getGlobalPopulationRanking() == 1)
				{
					if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποια είναι η μεγαλύτερη ήπειρος σε πληθυσμό;");
					else textForQuestion.setText("What is the largest continent by population?");
				}
				else
				{
					if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποια είναι η " + continents[correctIndex].getGlobalPopulationRanking() + "η μεγαλύτερη ήπειρος σε πληθυσμό;");
					else textForQuestion.setText("What is the " + continents[correctIndex].getGlobalPopulationRanking() + t + " largest continent by population?");
				}
				
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					correctAnswerString = continents[correctIndex].getNameInGreek();
					
					randomStringForMultipleChoice[0] = continents[randomIndexForMultipleChoice[0]].getNameInGreek();
					randomStringForMultipleChoice[1] = continents[randomIndexForMultipleChoice[1]].getNameInGreek();
					randomStringForMultipleChoice[2] = continents[randomIndexForMultipleChoice[2]].getNameInGreek();
				}
				else
				{
					correctAnswerString = continents[correctIndex].getNameInEnglish();
					
					randomStringForMultipleChoice[0] = continents[randomIndexForMultipleChoice[0]].getNameInEnglish();
					randomStringForMultipleChoice[1] = continents[randomIndexForMultipleChoice[1]].getNameInEnglish();
					randomStringForMultipleChoice[2] = continents[randomIndexForMultipleChoice[2]].getNameInEnglish();
				}
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				positionOfCorrectAnswer = random.nextInt(2);
				
				if (positionOfCorrectAnswer == 0)
				{
					if(continents[correctIndex].getGlobalPopulationRanking() == 1)
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι η μεγαλύτερη σε πληθυσμό ήπειρος.");
						else textForQuestion.setText(continents[correctIndex].getNameInEnglish() + " is the largest continent by population.");
					}
					else
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι η " +
						                                                     continents[correctIndex].getGlobalPopulationRanking() + "η μεγαλύτερη σε πληθυσμό ήπειρος.");
						else textForQuestion.setText(continents[correctIndex].getNameInEnglish() + " is the " +
						                             continents[correctIndex].getGlobalPopulationRanking() + t + " largest continent by population.");
					}
				}
				else
				{
					int index;
					do index = random.nextInt(6) + 1; while(index == continents[correctIndex].getGlobalPopulationRanking());
					
					if(index == 1)
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι η μεγαλύτερη σε πληθυσμό ήπειρος.");
						else textForQuestion.setText(continents[correctIndex].getNameInEnglish() + " is the largest continent by population.");
					}
					else
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι η " + index + "η μεγαλύτερη σε πληθυσμό ήπειρος.");
						else
						{
							if(continents[correctIndex].getGlobalPopulationRanking() == 2)
								t = "nd";
							else if(continents[correctIndex].getGlobalPopulationRanking() == 3)
								t = "rd";
							else t = "th";
							
							textForQuestion.setText(continents[correctIndex].getNameInEnglish() + " is the " + index + t + " largest continent by population.");
						}
					}
				}
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				boolean askIsLargerThan = random.nextBoolean();
				
				if (positionOfCorrectAnswer == 0)
				{
					if(continents[correctIndex].getGlobalPopulationRanking() == 1 || askIsLargerThan && continents[correctIndex].getGlobalPopulationRanking() != 6)
					{
						do randomIndexForMultipleChoice[0] = random.nextInt(continents.length - 1) + 1;
						while(continents[randomIndexForMultipleChoice[0]].getGlobalPopulationRanking() <= continents[correctIndex].getGlobalPopulationRanking());
						
						if (getCurrentLanguage() == LANGUAGE.GREEK)
						{
							randomStringForMultipleChoice[0] = continents[randomIndexForMultipleChoice[0]].getNameInGreek();
							if(randomStringForMultipleChoice[0].equals("Βόρεια Αμερική") || randomStringForMultipleChoice[0].equals("Νότια Αμερική"))
								textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι μεγαλύτερη σε πληθυσμό από τη " + randomStringForMultipleChoice[0] + ".");
							else textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι μεγαλύτερη σε πληθυσμό από την " + randomStringForMultipleChoice[0] + ".");
						}
						else textForQuestion.setText(continents[correctIndex].getNameInEnglish() + "is a larger continent by population than " +
						                             continents[randomIndexForMultipleChoice[0]].getNameInEnglish() + ".");
					}
					else
					{
						do randomIndexForMultipleChoice[0] = random.nextInt(continents.length - 1) + 1;
						while(continents[randomIndexForMultipleChoice[0]].getGlobalPopulationRanking() >= continents[correctIndex].getGlobalPopulationRanking());
						
						if (getCurrentLanguage() == LANGUAGE.GREEK)
						{
							randomStringForMultipleChoice[0] = continents[randomIndexForMultipleChoice[0]].getNameInGreek();
							if(randomStringForMultipleChoice[0].equals("Βόρεια Αμερική") || randomStringForMultipleChoice[0].equals("Νότια Αμερική"))
								textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι μικρότερη σε πληθυσμό από τη " + randomStringForMultipleChoice[0] + ".");
							else textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι μικρότερη σε πληθυσμό από την " + randomStringForMultipleChoice[0] + ".");
						}
						else textForQuestion.setText(continents[correctIndex].getNameInEnglish() + "is a smaller continent by population than " +
						                             continents[randomIndexForMultipleChoice[0]].getNameInEnglish() + ".");
					}
				}
				else
				{
					if(continents[correctIndex].getGlobalPopulationRanking() == 6 || askIsLargerThan && continents[correctIndex].getGlobalPopulationRanking() != 1)
					{
						do randomIndexForMultipleChoice[0] = random.nextInt(continents.length - 1) + 1;
						while(continents[randomIndexForMultipleChoice[0]].getGlobalPopulationRanking() >= continents[correctIndex].getGlobalPopulationRanking());
						
						if (getCurrentLanguage() == LANGUAGE.GREEK)
						{
							randomStringForMultipleChoice[0] = continents[randomIndexForMultipleChoice[0]].getNameInGreek();
							if(randomStringForMultipleChoice[0].equals("Βόρεια Αμερική") || randomStringForMultipleChoice[0].equals("Νότια Αμερική"))
								textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι μεγαλύτερη σε πληθυσμό από τη " + randomStringForMultipleChoice[0] + ".");
							else textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι μεγαλύτερη σε πληθυσμό από την " + randomStringForMultipleChoice[0] + ".");
						}
						else textForQuestion.setText(continents[correctIndex].getNameInEnglish() + "is a larger continent by population than " +
						                             continents[randomIndexForMultipleChoice[0]].getNameInEnglish() + ".");
					}
					else
					{
						do randomIndexForMultipleChoice[0] = random.nextInt(continents.length - 1) + 1;
						while(continents[randomIndexForMultipleChoice[0]].getGlobalPopulationRanking() <= continents[correctIndex].getGlobalPopulationRanking());
						
						if (getCurrentLanguage() == LANGUAGE.GREEK)
						{
							randomStringForMultipleChoice[0] = continents[randomIndexForMultipleChoice[0]].getNameInGreek();
							if(randomStringForMultipleChoice[0].equals("Βόρεια Αμερική") || randomStringForMultipleChoice[0].equals("Νότια Αμερική"))
								textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι μικρότερη σε πληθυσμό από τη " + randomStringForMultipleChoice[0] + ".");
							else textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι μικρότερη σε πληθυσμό από την " + randomStringForMultipleChoice[0] + ".");
						}
						else textForQuestion.setText(continents[correctIndex].getNameInEnglish() + "is a smaller continent by population than " +
						                             continents[randomIndexForMultipleChoice[0]].getNameInEnglish() + ".");
					}
				}
			}
			
			if(wayOfAskingCurrentQuestion > 1)
			{
				typeOfQuestion = 1;
				showNextQuestionNodes(false, 1);
			}
			else
			{
				typeOfQuestion = 2;
				set4TextAnswers();
				showNextQuestionNodes(false, 2);
			}
		}
		
		private void countriesArea()
		{
			correctIndex = availableQuestionsArray[COUNTRIES_AREA].get(0);
			availableQuestionsArray[COUNTRIES_AREA].remove(0);
			
//			easyQuestion = countries[correctIndex].hasEasyLocation();
			
			if(correctIndex == countriesByArea[0] || correctIndex == countriesByArea[countriesByArea.length - 1]) wayOfAskingCurrentQuestion = random.nextInt(2) + 2;
			else wayOfAskingCurrentQuestion = random.nextInt(3) + 1;
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				
			}
			else if (wayOfAskingCurrentQuestion == 2)
			{
				
			}
			else
			{
				
			}
		}
		
		private void continentsArea()
		{
			correctIndex = availableQuestionsArray[CONTINENTS_AREA].get(0);
			availableQuestionsArray[CONTINENTS_AREA].remove(0);
			
			easyQuestion = continents[correctIndex].getGlobalAreaRanking() < 3;
			
			wayOfAskingCurrentQuestion = random.nextInt(3) + 1;
			
			String t = "";
			if(getCurrentLanguage() == LANGUAGE.ENGLISH)
			{
				if(continents[correctIndex].getGlobalAreaRanking() == 2)
					t = "nd";
				else if(continents[correctIndex].getGlobalAreaRanking() == 3)
					t = "rd";
				else t = "th";
			}
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				do randomIndexForMultipleChoice[0] = random.nextInt(continents.length); while(randomIndexForMultipleChoice[0] == correctIndex);
				do randomIndexForMultipleChoice[1] = random.nextInt(continents.length); while(randomIndexForMultipleChoice[1] == correctIndex ||
				                                                                                      randomIndexForMultipleChoice[1] == randomIndexForMultipleChoice[0]);
				do randomIndexForMultipleChoice[2] = random.nextInt(continents.length); while(randomIndexForMultipleChoice[2] == correctIndex ||
				                                                                                      randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[0] ||
				                                                                                      randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[1]);
				
				if(continents[correctIndex].getGlobalAreaRanking() == 1)
				{
					if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποια είναι η μεγαλύτερη ήπειρος σε έκταση;");
					else textForQuestion.setText("What is the largest continent by area?");
				}
				else
				{
					if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποια είναι η " + continents[correctIndex].getGlobalAreaRanking() + "η μεγαλύτερη ήπειρος σε έκταση;");
					else textForQuestion.setText("What is the " + continents[correctIndex].getGlobalAreaRanking() + t + " largest continent by area?");
				}
				
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					correctAnswerString = continents[correctIndex].getNameInGreek();
					
					randomStringForMultipleChoice[0] = continents[randomIndexForMultipleChoice[0]].getNameInGreek();
					randomStringForMultipleChoice[1] = continents[randomIndexForMultipleChoice[1]].getNameInGreek();
					randomStringForMultipleChoice[2] = continents[randomIndexForMultipleChoice[2]].getNameInGreek();
				}
				else
				{
					correctAnswerString = continents[correctIndex].getNameInEnglish();
					
					randomStringForMultipleChoice[0] = continents[randomIndexForMultipleChoice[0]].getNameInEnglish();
					randomStringForMultipleChoice[1] = continents[randomIndexForMultipleChoice[1]].getNameInEnglish();
					randomStringForMultipleChoice[2] = continents[randomIndexForMultipleChoice[2]].getNameInEnglish();
				}
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				positionOfCorrectAnswer = random.nextInt(2);
				
				if (positionOfCorrectAnswer == 0)
				{
					if(continents[correctIndex].getGlobalAreaRanking() == 1)
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι η μεγαλύτερη σε έκταση ήπειρος.");
						else textForQuestion.setText(continents[correctIndex].getNameInEnglish() + " is the largest continent by area.");
					}
					else
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι η " +
						                                                     continents[correctIndex].getGlobalAreaRanking() + "η μεγαλύτερη σε έκταση ήπειρος.");
						else textForQuestion.setText(continents[correctIndex].getNameInEnglish() + " is the " +
						                             continents[correctIndex].getGlobalAreaRanking() + t + " largest continent by area.");
					}
				}
				else
				{
					int index;
					do index = random.nextInt(6) + 1; while(index == continents[correctIndex].getGlobalAreaRanking());
					
					if(index == 1)
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι η μεγαλύτερη σε έκταση ήπειρος.");
						else textForQuestion.setText(continents[correctIndex].getNameInEnglish() + " is the largest continent by area.");
					}
					else
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι η " + index + "η μεγαλύτερη σε έκταση ήπειρος.");
						else
						{
							if(continents[correctIndex].getGlobalAreaRanking() == 2)
								t = "nd";
							else if(continents[correctIndex].getGlobalAreaRanking() == 3)
								t = "rd";
							else t = "th";
							
							textForQuestion.setText(continents[correctIndex].getNameInEnglish() + " is the " + index + t + " largest continent by area.");
						}
					}
				}
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				boolean askIsLargerThan = random.nextBoolean();
				
				if (positionOfCorrectAnswer == 0)
				{
					if(continents[correctIndex].getGlobalAreaRanking() == 1 || askIsLargerThan && continents[correctIndex].getGlobalAreaRanking() != 6)
					{
						do randomIndexForMultipleChoice[0] = random.nextInt(continents.length - 1) + 1;
						while(continents[randomIndexForMultipleChoice[0]].getGlobalAreaRanking() <= continents[correctIndex].getGlobalAreaRanking());
						
						if (getCurrentLanguage() == LANGUAGE.GREEK)
						{
							randomStringForMultipleChoice[0] = continents[randomIndexForMultipleChoice[0]].getNameInGreek();
							if(randomStringForMultipleChoice[0].equals("Βόρεια Αμερική") || randomStringForMultipleChoice[0].equals("Νότια Αμερική"))
								textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι μεγαλύτερη σε έκταση από τη " + randomStringForMultipleChoice[0] + ".");
							else textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι μεγαλύτερη σε έκταση από την " + randomStringForMultipleChoice[0] + ".");
						}
						else textForQuestion.setText(continents[correctIndex].getNameInEnglish() + "is a larger continent by area than " +
						                             continents[randomIndexForMultipleChoice[0]].getNameInEnglish() + ".");
					}
					else
					{
						do randomIndexForMultipleChoice[0] = random.nextInt(continents.length - 1) + 1;
						while(continents[randomIndexForMultipleChoice[0]].getGlobalAreaRanking() >= continents[correctIndex].getGlobalAreaRanking());
						
						if (getCurrentLanguage() == LANGUAGE.GREEK)
						{
							randomStringForMultipleChoice[0] = continents[randomIndexForMultipleChoice[0]].getNameInGreek();
							if(randomStringForMultipleChoice[0].equals("Βόρεια Αμερική") || randomStringForMultipleChoice[0].equals("Νότια Αμερική"))
								textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι μικρότερη σε έκταση από τη " + randomStringForMultipleChoice[0] + ".");
							else textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι μικρότερη σε έκταση από την " + randomStringForMultipleChoice[0] + ".");
						}
						else textForQuestion.setText(continents[correctIndex].getNameInEnglish() + "is a smaller continent by area than " +
						                             continents[randomIndexForMultipleChoice[0]].getNameInEnglish() + ".");
					}
				}
				else
				{
					if(continents[correctIndex].getGlobalAreaRanking() == 6 || askIsLargerThan && continents[correctIndex].getGlobalAreaRanking() != 1)
					{
						do randomIndexForMultipleChoice[0] = random.nextInt(continents.length - 1) + 1;
						while(continents[randomIndexForMultipleChoice[0]].getGlobalAreaRanking() >= continents[correctIndex].getGlobalAreaRanking());
						
						if (getCurrentLanguage() == LANGUAGE.GREEK)
						{
							randomStringForMultipleChoice[0] = continents[randomIndexForMultipleChoice[0]].getNameInGreek();
							if(randomStringForMultipleChoice[0].equals("Βόρεια Αμερική") || randomStringForMultipleChoice[0].equals("Νότια Αμερική"))
								textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι μεγαλύτερη σε έκταση από τη " + randomStringForMultipleChoice[0] + ".");
							else textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι μεγαλύτερη σε έκταση από την " + randomStringForMultipleChoice[0] + ".");
						}
						else textForQuestion.setText(continents[correctIndex].getNameInEnglish() + "is a larger continent by area than " +
						                             continents[randomIndexForMultipleChoice[0]].getNameInEnglish() + ".");
					}
					else
					{
						do randomIndexForMultipleChoice[0] = random.nextInt(continents.length - 1) + 1;
						while(continents[randomIndexForMultipleChoice[0]].getGlobalAreaRanking() <= continents[correctIndex].getGlobalAreaRanking());
						
						if (getCurrentLanguage() == LANGUAGE.GREEK)
						{
							randomStringForMultipleChoice[0] = continents[randomIndexForMultipleChoice[0]].getNameInGreek();
							if(randomStringForMultipleChoice[0].equals("Βόρεια Αμερική") || randomStringForMultipleChoice[0].equals("Νότια Αμερική"))
								textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι μικρότερη σε έκταση από τη " + randomStringForMultipleChoice[0] + ".");
							else textForQuestion.setText("Η " + continents[correctIndex].getNameInGreek() + " είναι μικρότερη σε έκταση από την " + randomStringForMultipleChoice[0] + ".");
						}
						else textForQuestion.setText(continents[correctIndex].getNameInEnglish() + "is a smaller continent by area than " +
						                             continents[randomIndexForMultipleChoice[0]].getNameInEnglish() + ".");
					}
				}
			}
			
			if(wayOfAskingCurrentQuestion > 1)
			{
				typeOfQuestion = 1;
				showNextQuestionNodes(false, 1);
			}
			else
			{
				typeOfQuestion = 2;
				set4TextAnswers();
				showNextQuestionNodes(false, 2);
			}
		}
		
		private void continentsCountries()
		{
			correctIndex = availableQuestionsArray[CONTINENTS_COUNTRIES].get(0);
			availableQuestionsArray[CONTINENTS_COUNTRIES].remove(0);
			
			easyQuestion = countries[correctIndex].hasEasyLocation();
			
			wayOfAskingCurrentQuestion = random.nextInt(3) + 1;
			
			String verb, article;
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				do randomIndexForMultipleChoice[0] = random.nextInt(countries.length);
				while(countries[randomIndexForMultipleChoice[0]].getContinent().equals(countries[correctIndex].getContinent()) ||
				      countries[randomIndexForMultipleChoice[0]].hasEasyLocation() && !countries[correctIndex].hasEasyLocation() ||
				      !countries[randomIndexForMultipleChoice[0]].hasEasyLocation() && countries[correctIndex].hasEasyLocation());
				do randomIndexForMultipleChoice[1] = random.nextInt(countries.length);
				while (randomIndexForMultipleChoice[1] == randomIndexForMultipleChoice[0] ||
				       countries[randomIndexForMultipleChoice[1]].getContinent().equals(countries[correctIndex].getContinent()) ||
				       countries[randomIndexForMultipleChoice[1]].hasEasyLocation() && !countries[correctIndex].hasEasyLocation() ||
				       !countries[randomIndexForMultipleChoice[1]].hasEasyLocation() && countries[correctIndex].hasEasyLocation());
				do randomIndexForMultipleChoice[2] = random.nextInt(countries.length);
				while (randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[0] ||
				       randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[1] ||
				       countries[randomIndexForMultipleChoice[2]].getContinent().equals(countries[correctIndex].getContinent()) ||
				       countries[randomIndexForMultipleChoice[2]].hasEasyLocation() && !countries[correctIndex].hasEasyLocation() ||
				       !countries[randomIndexForMultipleChoice[2]].hasEasyLocation() && countries[correctIndex].hasEasyLocation());
				
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					if(countries[correctIndex].getContinent().equals("Βόρεια Αμερική") || countries[correctIndex].getContinent().equals("Νότια Αμερική"))
						textForQuestion.setText("Ποια χώρα από τις παρακάτω βρίσκεται στη " + countries[correctIndex].getContinent() + ";");
					else textForQuestion.setText("Ποια χώρα από τις παρακάτω βρίσκεται στην " + countries[correctIndex].getContinent() + ";");
					correctAnswerString = countries[correctIndex].getNameInGreek();
					
					for(int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getNameInGreek();
				}
				else
				{
					textForQuestion.setText("Which of the following countries belongs to " +
					                        countries[correctIndex].getContinent() + "?");
					correctAnswerString = countries[correctIndex].getNameInEnglish();
					
					for(int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getNameInEnglish();
				}
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				do randomIndexForMultipleChoice[0] = random.nextInt(continents.length - 1) + 1;
				while(continents[randomIndexForMultipleChoice[0]].getNameInGreek().equals(countries[correctIndex].getContinent()) ||
				      continents[randomIndexForMultipleChoice[0]].getNameInEnglish().equals(countries[correctIndex].getContinent()));
				do randomIndexForMultipleChoice[1] = random.nextInt(continents.length - 1) + 1;
				while (randomIndexForMultipleChoice[1] == randomIndexForMultipleChoice[0] ||
				       continents[randomIndexForMultipleChoice[1]].getNameInGreek().equals(countries[correctIndex].getContinent()) ||
				       continents[randomIndexForMultipleChoice[1]].getNameInEnglish().equals(countries[correctIndex].getContinent()));
				do randomIndexForMultipleChoice[2] = random.nextInt(continents.length - 1) + 1;
				while (randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[0] ||
				       randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[1] ||
				       continents[randomIndexForMultipleChoice[2]].getNameInGreek().equals(countries[correctIndex].getContinent()) ||
				       continents[randomIndexForMultipleChoice[2]].getNameInEnglish().equals(countries[correctIndex].getContinent()));
				
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					if(countries[correctIndex].getArticleForCountry().equals("Οι") || countries[correctIndex].getArticleForCountry().equals("Τα")) verb = " βρίσκονται ";
					else verb = " βρίσκεται ";
					
					textForQuestion.setText("Σε ποια ήπειρο" + verb + countries[correctIndex].getArticleForCountry().toLowerCase() + " " + countries[correctIndex].getNameInGreek() + ";");
					
					for(int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = continents[randomIndexForMultipleChoice[i]].getNameInGreek();
				}
				else
				{
					textForQuestion.setText("In which continent does " + countries[correctIndex].getNameInEnglish() + " belong to?");
					
					for(int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = continents[randomIndexForMultipleChoice[i]].getNameInEnglish();
				}
				correctAnswerString = countries[correctIndex].getContinent();
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					if(countries[correctIndex].getArticleForCountry().equals("Οι") || countries[correctIndex].getArticleForCountry().equals("Τα")) verb = " βρίσκονται ";
					else verb = " βρίσκεται ";
					
					if (positionOfCorrectAnswer == 0)
					{
						if(countries[correctIndex].getContinent().equals("Βόρεια Αμερική") || countries[correctIndex].getContinent().equals("Νότια Αμερική"))
							article = "στη ";
						else article = "στην ";
						textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek() +
						                        verb + article + countries[correctIndex].getContinent() + ".");
					}
					else
					{
						String randomCap;
						do randomCap = continents[random.nextInt(continents.length - 1) + 1].getNameInGreek();
						while(randomCap.equals(countries[correctIndex].getContinent()));
						
						if(randomCap.equals("Βόρεια Αμερική") || randomCap.equals("Νότια Αμερική"))
							article = "στη ";
						else article = "στην ";
						textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek() + verb + article + randomCap + ".");
					}
				}
				else
				{
					if (positionOfCorrectAnswer == 0)
						textForQuestion.setText(countries[correctIndex].getNameInEnglish() + " belongs to " + countries[correctIndex].getContinent() + ".");
					else
					{
						String randomCap;
						do randomCap = continents[random.nextInt(continents.length - 1) + 1].getNameInEnglish();
						while(randomCap.equals(countries[correctIndex].getContinent()));
						
						textForQuestion.setText(countries[correctIndex].getNameInEnglish() + " belongs to " + randomCap + ".");
					}
				}
			}
			
			if(wayOfAskingCurrentQuestion == 3)
			{
				typeOfQuestion = 1;
				showNextQuestionNodes(false, 1);
			}
			else
			{
				typeOfQuestion = 2;
				set4TextAnswers();
				showNextQuestionNodes(false, 2);
			}
		}
		
		private void continentsLargestCountries()
		{
			correctIndex = availableQuestionsArray[CONTINENTS_LARGEST_COUNTRIES].get(0);
			availableQuestionsArray[CONTINENTS_LARGEST_COUNTRIES].remove(0);
			
			int y = random.nextInt(3); // decide to ask largest, or 2nd largest, or 3rd
			easyQuestion = y == 0;
			
			wayOfAskingCurrentQuestion = random.nextInt(2) + 1;
			
			if(random.nextBoolean()) // if true ask by area, else ask by population
			{
				if(wayOfAskingCurrentQuestion == 1)
				{
					int r1, r2, r3;
					if(y == 0)
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποια είναι η μεγαλύτερη σε έκταση χώρα της " + continents[correctIndex].getGenitiveCaseOfContinent() + ";");
						else textForQuestion.setText("What is the largest country of " + continents[correctIndex].getNameInEnglish() + " by area?");
						
						correctAnswerString = continents[correctIndex].getLargestCountriesByArea()[0];
						
						r1 = random.nextInt(9) + 1;
						do r2 = random.nextInt(9) + 1; while(r2 == r1);
						do r3 = random.nextInt(9) + 1; while(r3 == r1 || r3 == r2);
					}
					else if(y == 1)
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποια είναι η 2η μεγαλύτερη σε έκταση χώρα της " + continents[correctIndex].getGenitiveCaseOfContinent() + ";");
						else textForQuestion.setText("What is the 2nd largest country of " + continents[correctIndex].getNameInEnglish() + " by area?");
						
						correctAnswerString = continents[correctIndex].getLargestCountriesByArea()[1];
						
						do r1 = random.nextInt(10); while(r1 == 1);
						do r2 = random.nextInt(10); while(r2 == 1 || r2 == r1);
						do r3 = random.nextInt(10); while(r3 == 1 || r3 == r1 || r3 == r2);
					}
					else
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποια είναι η 3η μεγαλύτερη σε έκταση χώρα της " + continents[correctIndex].getGenitiveCaseOfContinent() + ";");
						else textForQuestion.setText("What is the 3rd largest country of " + continents[correctIndex].getNameInEnglish() + " by area?");
						
						correctAnswerString = continents[correctIndex].getLargestCountriesByArea()[2];
						
						do r1 = random.nextInt(10); while(r1 == 2);
						do r2 = random.nextInt(10); while(r2 == 2 || r2 == r1);
						do r3 = random.nextInt(10); while(r3 == 2 || r3 == r1 || r3 == r2);
					}
					
					randomStringForMultipleChoice[0] = continents[correctIndex].getLargestCountriesByArea()[r1];
					randomStringForMultipleChoice[1] = continents[correctIndex].getLargestCountriesByArea()[r2];
					randomStringForMultipleChoice[2] = continents[correctIndex].getLargestCountriesByArea()[r3];
				}
				else
				{
					positionOfCorrectAnswer = random.nextInt(2);
					
					if (positionOfCorrectAnswer == 0)
					{
						if(y == 0)
						{
							if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η χώρα " + continents[correctIndex].getLargestCountriesByArea()[0] +
							                                                     " είναι η μεγαλύτερη σε έκταση χώρα της " + continents[correctIndex].getGenitiveCaseOfContinent() + ".");
							else textForQuestion.setText(continents[correctIndex].getLargestCountriesByArea()[0] +
							                             " is the largest country of " + continents[correctIndex].getNameInEnglish() + " by area.");
						}
						else if(y == 1)
						{
							if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η χώρα " + continents[correctIndex].getLargestCountriesByArea()[1] +
							                                                     " είναι η 2η μεγαλύτερη σε έκταση χώρα της " + continents[correctIndex].getGenitiveCaseOfContinent() + ".");
							else textForQuestion.setText(continents[correctIndex].getLargestCountriesByArea()[1] +
							                             " is the 2nd largest country of " + continents[correctIndex].getNameInEnglish() + " by area.");
						}
						else
						{
							if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η χώρα " + continents[correctIndex].getLargestCountriesByArea()[2] +
							                                                     " είναι η 3η μεγαλύτερη σε έκταση χώρα της " + continents[correctIndex].getGenitiveCaseOfContinent() + ".");
							else textForQuestion.setText(continents[correctIndex].getLargestCountriesByArea()[2] +
							                             " is the 3rd largest country of " + continents[correctIndex].getNameInEnglish() + " by area.");
						}
					}
					else
					{
						String randomLar;
						int index;
						
						if(y == 0) index = random.nextInt(9) + 1;
						else if(y == 1) do index = random.nextInt(10); while(index == 1);
						else do index = random.nextInt(10); while(index == 2);
						randomLar = continents[correctIndex].getLargestCountriesByArea()[index];
						
						if(y == 0)
						{
							if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η χώρα " + randomLar + " είναι η μεγαλύτερη σε έκταση χώρα της " +
							                                                     continents[correctIndex].getGenitiveCaseOfContinent() + ".");
							else textForQuestion.setText(randomLar + " is the largest country of " + continents[correctIndex].getNameInEnglish() + " by area.");
						}
						else if(y == 1)
						{
							if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η χώρα " + randomLar + " είναι η 2η μεγαλύτερη σε έκταση χώρα της " +
							                                                     continents[correctIndex].getGenitiveCaseOfContinent() + ".");
							else textForQuestion.setText(randomLar + " is the 2nd largest country of " + continents[correctIndex].getNameInEnglish() + " by area.");
						}
						else
						{
							if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η χώρα " + randomLar + " είναι η 3η μεγαλύτερη σε έκταση χώρα της " +
							                                                     continents[correctIndex].getGenitiveCaseOfContinent() + ".");
							else textForQuestion.setText(randomLar + " is the 3rd largest country of " + continents[correctIndex].getNameInEnglish() + " by area.");
						}
					}
				}
			}
			else
			{
				if(wayOfAskingCurrentQuestion == 1)
				{
					int r1, r2, r3;
					if(y == 0)
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποια είναι η μεγαλύτερη σε πληθυσμό χώρα της " + continents[correctIndex].getGenitiveCaseOfContinent() + ";");
						else textForQuestion.setText("What is the largest country of " + continents[correctIndex].getNameInEnglish() + " by population?");
						
						correctAnswerString = continents[correctIndex].getLargestCountriesByPopulation()[0];
						
						r1 = random.nextInt(9) + 1;
						do r2 = random.nextInt(9) + 1; while(r2 == r1);
						do r3 = random.nextInt(9) + 1; while(r3 == r1 || r3 == r2);
					}
					else if(y == 1)
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποια είναι η 2η μεγαλύτερη σε πληθυσμό χώρα της " + continents[correctIndex].getGenitiveCaseOfContinent() + ";");
						else textForQuestion.setText("What is the 2nd largest country of " + continents[correctIndex].getNameInEnglish() + " by population?");
						
						correctAnswerString = continents[correctIndex].getLargestCountriesByPopulation()[1];
						
						do r1 = random.nextInt(10); while(r1 == 1);
						do r2 = random.nextInt(10); while(r2 == 1 || r2 == r1);
						do r3 = random.nextInt(10); while(r3 == 1 || r3 == r1 || r3 == r2);
					}
					else
					{
						if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποια είναι η 3η μεγαλύτερη σε πληθυσμό χώρα της " + continents[correctIndex].getGenitiveCaseOfContinent() + ";");
						else textForQuestion.setText("What is the 3rd largest country of " + continents[correctIndex].getNameInEnglish() + " by population?");
						
						correctAnswerString = continents[correctIndex].getLargestCountriesByPopulation()[2];
						
						do r1 = random.nextInt(10); while(r1 == 2);
						do r2 = random.nextInt(10); while(r2 == 2 || r2 == r1);
						do r3 = random.nextInt(10); while(r3 == 2 || r3 == r1 || r3 == r2);
					}
					
					randomStringForMultipleChoice[0] = continents[correctIndex].getLargestCountriesByPopulation()[r1];
					randomStringForMultipleChoice[1] = continents[correctIndex].getLargestCountriesByPopulation()[r2];
					randomStringForMultipleChoice[2] = continents[correctIndex].getLargestCountriesByPopulation()[r3];
				}
				else
				{
					positionOfCorrectAnswer = random.nextInt(2);
					
					if (positionOfCorrectAnswer == 0)
					{
						if(y == 0)
						{
							if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η χώρα " + continents[correctIndex].getLargestCountriesByPopulation()[0] +
							                                                     " είναι η μεγαλύτερη σε πληθυσμό χώρα της " + continents[correctIndex].getGenitiveCaseOfContinent() + ".");
							else textForQuestion.setText(continents[correctIndex].getLargestCountriesByPopulation()[0] +
							                             " is the largest country of " + continents[correctIndex].getNameInEnglish() + " by population.");
						}
						else if(y == 1)
						{
							if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η χώρα " + continents[correctIndex].getLargestCountriesByPopulation()[1] +
							                                                     " είναι η 2η μεγαλύτερη σε πληθυσμό χώρα της " + continents[correctIndex].getGenitiveCaseOfContinent() + ".");
							else textForQuestion.setText(continents[correctIndex].getLargestCountriesByPopulation()[1] +
							                             " is the 2nd largest country of " + continents[correctIndex].getNameInEnglish() + " by population.");
						}
						else
						{
							if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η χώρα " + continents[correctIndex].getLargestCountriesByPopulation()[2] +
							                                                     " είναι η 3η μεγαλύτερη σε πληθυσμό χώρα της " + continents[correctIndex].getGenitiveCaseOfContinent() + ".");
							else textForQuestion.setText(continents[correctIndex].getLargestCountriesByPopulation()[2] +
							                             " is the 3rd largest country of " + continents[correctIndex].getNameInEnglish() + " by population.");
						}
					}
					else
					{
						String randomLar;
						int index;
						
						if(y == 0) index = random.nextInt(9) + 1;
						else if(y == 1) do index = random.nextInt(10); while(index == 1);
						else do index = random.nextInt(10); while(index == 2);
						randomLar = continents[correctIndex].getLargestCountriesByPopulation()[index];
						
						if(y == 0)
						{
							if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η χώρα " + randomLar + " είναι η μεγαλύτερη σε πληθυσμό χώρα της " +
							                                                     continents[correctIndex].getGenitiveCaseOfContinent() + ".");
							else textForQuestion.setText(randomLar + " is the largest country of " + continents[correctIndex].getNameInEnglish() + " by population.");
						}
						else if(y == 1)
						{
							if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η χώρα " + randomLar + " είναι η 2η μεγαλύτερη σε πληθυσμό χώρα της " +
							                                                     continents[correctIndex].getGenitiveCaseOfContinent() + ".");
							else textForQuestion.setText(randomLar + " is the 2nd largest country of " + continents[correctIndex].getNameInEnglish() + " by population.");
						}
						else
						{
							if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η χώρα " + randomLar + " είναι η 3η μεγαλύτερη σε πληθυσμό χώρα της " +
							                                                     continents[correctIndex].getGenitiveCaseOfContinent() + ".");
							else textForQuestion.setText(randomLar + " is the 3rd largest country of " + continents[correctIndex].getNameInEnglish() + " by population.");
						}
					}
				}
			}
			
			if(wayOfAskingCurrentQuestion == 2)
			{
				typeOfQuestion = 1;
				showNextQuestionNodes(false, 1);
			}
			else
			{
				typeOfQuestion = 2;
				set4TextAnswers();
				showNextQuestionNodes(false, 2);
			}
		}
		
		private void sovereignDependentCountries()
		{
			correctIndex = availableQuestionsArray[SOVEREIGN_DEPENDENT_COUNTRIES].get(0);
			availableQuestionsArray[SOVEREIGN_DEPENDENT_COUNTRIES].remove(0);
			
			easyQuestion = false;
			
			wayOfAskingCurrentQuestion = random.nextInt(3) + 1;
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				do randomIndexForMultipleChoice[0] = random.nextInt(countries.length);
				while(randomIndexForMultipleChoice[0] == correctIndex ||
				      countries[randomIndexForMultipleChoice[0]].getNameInGreek().equals(countries[correctIndex].getSovereignState()) ||
				      countries[randomIndexForMultipleChoice[0]].getNameInEnglish().equals(countries[correctIndex].getSovereignState()));
				do randomIndexForMultipleChoice[1] = random.nextInt(countries.length);
				while (randomIndexForMultipleChoice[1] == correctIndex ||
				       countries[randomIndexForMultipleChoice[1]].getNameInGreek().equals(countries[correctIndex].getSovereignState()) ||
				       countries[randomIndexForMultipleChoice[1]].getNameInEnglish().equals(countries[correctIndex].getSovereignState()) ||
				       randomIndexForMultipleChoice[1] == randomIndexForMultipleChoice[0]);
				do randomIndexForMultipleChoice[2] = random.nextInt(countries.length);
				while (randomIndexForMultipleChoice[2] == correctIndex ||
				       countries[randomIndexForMultipleChoice[2]].getNameInGreek().equals(countries[correctIndex].getSovereignState()) ||
				       countries[randomIndexForMultipleChoice[2]].getNameInEnglish().equals(countries[correctIndex].getSovereignState()) ||
				       randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[0] ||
				       randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[1]);
				
				int beSovereignState = random.nextInt(3); //0=dependent state, 1=pretend sovereign, 2=be sovereign
				String sovereignState = "";
				
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					String verb;
					
					if(beSovereignState != 2)
					{
						if(countries[correctIndex].getArticleForCountry().equals("Οι") || countries[correctIndex].getArticleForCountry().equals("Τα"))
							verb = "ανήκουν ";
						else verb = "ανήκει ";
						
						textForQuestion.setText("Σε ποιο κυρίαρχο κράτος " + verb + countries[correctIndex].getArticleForCountry().toLowerCase() + " "
						                        + countries[correctIndex].getNameInGreek() + ";");
						correctAnswerString = countries[correctIndex].getSovereignState();
						
						if(beSovereignState == 1) sovereignState = "Είναι ανεξάρτητο κράτος";
					}
					else
					{
						int r;
						do r = random.nextInt(countries.length);
						while(!countries[r].isSovereignState());
						
						if(countries[r].getArticleForCountry().equals("Οι") || countries[r].getArticleForCountry().equals("Τα"))
							verb = "ανήκουν ";
						else verb = "ανήκει ";
						
						textForQuestion.setText("Σε ποιο κυρίαρχο κράτος " + verb + countries[r].getArticleForCountry().toLowerCase() + " " + countries[r].getNameInGreek() + ";");
						correctAnswerString = "Είναι ανεξάρτητο κράτος";
					}
					
					for (int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getNameInGreek();
				}
				else
				{
					if(beSovereignState != 2)
					{
						textForQuestion.setText(countries[correctIndex].getNameInEnglish() + " belongs to which sovereign state?");
						correctAnswerString = countries[correctIndex].getSovereignState();
						
						if(beSovereignState == 1) sovereignState = "It is a sovereign state";
					}
					else
					{
						int r;
						do r = random.nextInt(countries.length);
						while(!countries[r].isSovereignState());
						
						textForQuestion.setText(countries[r].getNameInEnglish() + " belongs to which sovereign state?");
						correctAnswerString = "It is a sovereign state";
					}
					
					for (int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getNameInEnglish();
				}
				
				int pos;
				if(beSovereignState == 1)
				{
					pos = random.nextInt(3);
					randomStringForMultipleChoice[pos] = sovereignState;
				}
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				do randomIndexForMultipleChoice[0] = random.nextInt(dependentStates.length);
				while(countries[dependentStates[randomIndexForMultipleChoice[0]]].getSovereignState().equals(countries[correctIndex].getSovereignState()));
				do randomIndexForMultipleChoice[1] = random.nextInt(dependentStates.length);
				while (countries[dependentStates[randomIndexForMultipleChoice[1]]].getSovereignState().equals(countries[correctIndex].getSovereignState()) ||
				       randomIndexForMultipleChoice[1] == randomIndexForMultipleChoice[0]);
				do randomIndexForMultipleChoice[2] = random.nextInt(dependentStates.length);
				while (countries[dependentStates[randomIndexForMultipleChoice[2]]].getSovereignState().equals(countries[correctIndex].getSovereignState()) ||
				       randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[0] ||
				       randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[1]);
				
				boolean pretendNone = random.nextBoolean();
				String none = "";
				
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					int index = 0;
					for(int i = 0; i < countries.length; i++)
					{
						if(countries[i].getNameInGreek().equals(countries[correctIndex].getSovereignState()))
						{
							index = i;
							break;
						}
					}
					
					textForQuestion.setText("Ποιο εξαρτημένο κρατίδιο από τα ακόλουθα ανήκει " + GreekLanguageHelper.getArticleIn(countries[index].getArticleForCountry(),
							GreekLanguageHelper.needsN(countries[index].getNameInGreek())).toLowerCase() + " " + countries[index].getNameInGreek() + ";");
					correctAnswerString = countries[correctIndex].getNameInGreek();
					
					if(pretendNone) none = "Κανένα";
					
					for (int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = countries[dependentStates[randomIndexForMultipleChoice[i]]].getNameInGreek();
				}
				else
				{
					textForQuestion.setText("Which of the following dependent states belongs to " + countries[correctIndex].getSovereignState() + "?");
					correctAnswerString = countries[correctIndex].getNameInEnglish();
					
					if(pretendNone) none = "None";
					
					for (int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = countries[dependentStates[randomIndexForMultipleChoice[i]]].getNameInEnglish();
				}
				
				int pos;
				if(pretendNone)
				{
					boolean correctIsNone = random.nextBoolean();
					
					if(correctIsNone) correctAnswerString = none;
					else
					{
						pos = random.nextInt(3);
						randomStringForMultipleChoice[pos] = none;
					}
				}
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				
				if(positionOfCorrectAnswer == 0)
				{
					if (getCurrentLanguage() == LANGUAGE.GREEK)
					{
						int index = 0;
						for(int i = 0; i < countries.length; i++)
						{
							if(countries[i].getNameInGreek().equals(countries[correctIndex].getSovereignState()))
							{
								index = i;
								break;
							}
						}
						
						textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek() + " είναι εξαρτημένο κρατίδιο " +
						                        GreekLanguageHelper.getGenitive(countries[index].getArticleForCountry()).toLowerCase() + " " + countries[index].getGenitiveCaseOfCountry() + ".");
					}
					else textForQuestion.setText(countries[correctIndex].getNameInEnglish() + " is a dependent state that belongs to " + countries[correctIndex].getSovereignState() + ".");
				}
				else
				{
					int r;
					if (getCurrentLanguage() == LANGUAGE.GREEK)
					{
						do r = random.nextInt(countries.length);
						while(countries[r].getNameInGreek().equals(countries[correctIndex].getSovereignState()) || !countries[r].isSovereignState());
						
						textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek() + " είναι εξαρτημένο κρατίδιο " +
						                        GreekLanguageHelper.getGenitive(countries[r].getArticleForCountry()).toLowerCase() + " " + countries[r].getGenitiveCaseOfCountry() + ".");
					}
					else
					{
						do r = random.nextInt(countries.length);
						while(countries[r].getNameInEnglish().equals(countries[correctIndex].getSovereignState()) || !countries[r].isSovereignState());
						
						textForQuestion.setText(countries[correctIndex].getNameInEnglish() + " is a dependent state that belongs to " + countries[r].getNameInEnglish() + ".");
					}
				}
			}
			
			if(wayOfAskingCurrentQuestion == 3)
			{
				typeOfQuestion = 1;
				showNextQuestionNodes(false, 1);
			}
			else
			{
				typeOfQuestion = 2;
				set4TextAnswers();
				showNextQuestionNodes(false, 2);
			}
		}
		
		private void countriesFlags()
		{
			correctIndex = availableQuestionsArray[COUNTRIES_FLAGS].get(0);
			availableQuestionsArray[COUNTRIES_FLAGS].remove(0);
			
			easyQuestion = countries[correctIndex].hasEasyFlag();
			
			wayOfAskingCurrentQuestion = random.nextInt(3) + 1;
			
			if(wayOfAskingCurrentQuestion != 3)
			{
				do randomIndexForMultipleChoice[0] = random.nextInt(countries.length);
				while(countries[correctIndex].hasEasyFlag() != countries[randomIndexForMultipleChoice[0]].hasEasyFlag() ||
				      randomIndexForMultipleChoice[0] == correctIndex ||
				      (countries[randomIndexForMultipleChoice[0]].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[randomIndexForMultipleChoice[0]].getNameInGreek().equals("Βόρεια Ιρλανδία")) &&
				      (countries[correctIndex].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[correctIndex].getNameInGreek().equals("Βόρεια Ιρλανδία")));
				do randomIndexForMultipleChoice[1] = random.nextInt(countries.length);
				while(countries[correctIndex].hasEasyFlag() != countries[randomIndexForMultipleChoice[1]].hasEasyFlag() ||
				      randomIndexForMultipleChoice[1] == correctIndex ||
				      randomIndexForMultipleChoice[1] == randomIndexForMultipleChoice[0] ||
				      (countries[randomIndexForMultipleChoice[1]].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[randomIndexForMultipleChoice[1]].getNameInGreek().equals("Βόρεια Ιρλανδία")) &&
				      (countries[correctIndex].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[correctIndex].getNameInGreek().equals("Βόρεια Ιρλανδία")));
				do randomIndexForMultipleChoice[2] = random.nextInt(countries.length);
				while(countries[correctIndex].hasEasyFlag() != countries[randomIndexForMultipleChoice[2]].hasEasyFlag() ||
				      randomIndexForMultipleChoice[2] == correctIndex ||
				      randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[0] ||
				      randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[1] ||
				      (countries[randomIndexForMultipleChoice[2]].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[randomIndexForMultipleChoice[2]].getNameInGreek().equals("Βόρεια Ιρλανδία")) &&
				      (countries[correctIndex].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[correctIndex].getNameInGreek().equals("Βόρεια Ιρλανδία")));
			}
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					textForQuestion.setText("Ποια χώρα έχει αυτήν τη σημαία;");
					correctAnswerString = countries[correctIndex].getNameInGreek();
					
					for(int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getNameInGreek();
				}
				else
				{
					textForQuestion.setText("This flag belongs in which country?");
					correctAnswerString = countries[correctIndex].getNameInEnglish();
					
					for(int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getNameInEnglish();
				}
				imageViewForQuestionImage.setImage(getImage(ImageType.COUNTRY_FLAG, questionImageSize, countries[correctIndex].getNameInGreek(), true));
				imageViewForQuestionImage.setImagePath(getImagePath(ImageType.COUNTRY_FLAG, questionImageSize, countries[correctIndex].getNameInGreek()));
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποια είναι η σημαία " + GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
				                                                     " " + countries[correctIndex].getGenitiveCaseOfCountry() + ";");
				else textForQuestion.setText("Which is the flag of " + countries[correctIndex].getNameInEnglish() + "?");
				
				for(int i = 0; i < 3; i++)
					randomStringForMultipleChoice[i] = getImagePath(ImageType.COUNTRY_FLAG, answerImageSize, countries[randomIndexForMultipleChoice[i]].getNameInGreek());
				
				correctAnswerString = getImagePath(ImageType.COUNTRY_FLAG, answerImageSize, countries[correctIndex].getNameInGreek());
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				
				if(positionOfCorrectAnswer == 0)
				{
					if(getCurrentLanguage() == LANGUAGE.GREEK)
					{
						String verb;
						if(countries[correctIndex].getArticleForCountry().equals("Οι") || countries[correctIndex].getArticleForCountry().equals("Τα"))
							verb = "έχουν";
						else verb = "έχει";
						textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " +
						                        countries[correctIndex].getNameInGreek() + " " + verb + " αυτή τη σημαία:");
					}
					else textForQuestion.setText("The flag of " + countries[correctIndex].getNameInEnglish() + " is the following:");
					
					imageViewForQuestionImage.setImage(getImage(ImageType.COUNTRY_FLAG, questionImageSize, countries[correctIndex].getNameInGreek(), true));
					imageViewForQuestionImage.setImagePath(getImagePath(ImageType.COUNTRY_FLAG, questionImageSize, countries[correctIndex].getNameInGreek()));
				}
				else
				{
					int index;
					do index = random.nextInt(countries.length);
					while(countries[correctIndex].hasEasyFlag() != countries[index].hasEasyFlag() ||
					      index == correctIndex ||
					      (countries[index].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[index].getNameInGreek().equals("Βόρεια Ιρλανδία")) &&
					      (countries[correctIndex].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[correctIndex].getNameInGreek().equals("Βόρεια Ιρλανδία")));
					
					if(getCurrentLanguage() == LANGUAGE.GREEK)
					{
						String verb;
						if(countries[correctIndex].getArticleForCountry().equals("Οι") || countries[correctIndex].getArticleForCountry().equals("Τα"))
							verb = "έχουν";
						else verb = "έχει";
						textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " +
						                        countries[correctIndex].getNameInGreek() + " " + verb + " αυτή τη σημαία:");
					}
					else textForQuestion.setText("The flag of " + countries[correctIndex].getNameInEnglish() + " is the following:");
					
					imageViewForQuestionImage.setImage(getImage(ImageType.COUNTRY_FLAG, questionImageSize, countries[index].getNameInGreek(), true));
					imageViewForQuestionImage.setImagePath(getImagePath(ImageType.COUNTRY_FLAG, questionImageSize, countries[index].getNameInGreek()));
				}
			}
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				typeOfQuestion = 2;
				set4TextAnswers();
				showNextQuestionNodes(true, 2);
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				typeOfQuestion = 2;
				set4ImageAnswers();
				showNextQuestionNodes(false, 3);
			}
			else
			{
				typeOfQuestion = 1;
				showNextQuestionNodes(true, 1);
			}
		}
		
		private void countriesCoatOfArms()
		{
			correctIndex = availableQuestionsArray[COUNTRIES_COAT_OF_ARMS].get(0);
			availableQuestionsArray[COUNTRIES_COAT_OF_ARMS].remove(0);
			
			easyQuestion = countries[correctIndex].askForCoatOfArms();
			
			wayOfAskingCurrentQuestion = random.nextInt(3) + 1;
			
			if(wayOfAskingCurrentQuestion != 3)
			{
				do randomIndexForMultipleChoice[0] = random.nextInt(countries.length);
				while(randomIndexForMultipleChoice[0] == correctIndex ||
				      countries[correctIndex].askForCoatOfArms() != countries[randomIndexForMultipleChoice[0]].askForCoatOfArms() ||
				      (countries[randomIndexForMultipleChoice[0]].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[randomIndexForMultipleChoice[0]].getNameInGreek().equals("Αγγλία") ||
				       countries[randomIndexForMultipleChoice[0]].getNameInGreek().equals("Βόρεια Ιρλανδία") || countries[randomIndexForMultipleChoice[0]].getNameInGreek().equals("Ουαλία")
				       ||countries[randomIndexForMultipleChoice[0]].getNameInGreek().equals("Σκωτία")) &&
				      (countries[correctIndex].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[correctIndex].getNameInGreek().equals("Αγγλία")
				       || countries[correctIndex].getNameInGreek().equals("Βόρεια Ιρλανδία") || countries[correctIndex].getNameInGreek().equals("Ουαλία")
				       || countries[correctIndex].getNameInGreek().equals("Σκωτία")));
				do randomIndexForMultipleChoice[1] = random.nextInt(countries.length);
				while(randomIndexForMultipleChoice[1] == correctIndex ||
				      randomIndexForMultipleChoice[1] == randomIndexForMultipleChoice[0] ||
				      countries[correctIndex].askForCoatOfArms() != countries[randomIndexForMultipleChoice[1]].askForCoatOfArms() ||
				      (countries[randomIndexForMultipleChoice[0]].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[randomIndexForMultipleChoice[0]].getNameInGreek().equals("Αγγλία") ||
				       countries[randomIndexForMultipleChoice[0]].getNameInGreek().equals("Βόρεια Ιρλανδία") || countries[randomIndexForMultipleChoice[0]].getNameInGreek().equals("Ουαλία")
				       || countries[randomIndexForMultipleChoice[0]].getNameInGreek().equals("Σκωτία")) &&
				      (countries[correctIndex].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[correctIndex].getNameInGreek().equals("Αγγλία")
				       || countries[correctIndex].getNameInGreek().equals("Βόρεια Ιρλανδία") || countries[correctIndex].getNameInGreek().equals("Ουαλία")
				       || countries[correctIndex].getNameInGreek().equals("Σκωτία")));
				do randomIndexForMultipleChoice[2] = random.nextInt(countries.length);
				while(randomIndexForMultipleChoice[2] == correctIndex ||
				      randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[0] ||
				      randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[1] ||
				      countries[correctIndex].askForCoatOfArms() != countries[randomIndexForMultipleChoice[2]].askForCoatOfArms() ||
				      (countries[randomIndexForMultipleChoice[0]].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[randomIndexForMultipleChoice[0]].getNameInGreek().equals("Αγγλία") ||
				       countries[randomIndexForMultipleChoice[0]].getNameInGreek().equals("Βόρεια Ιρλανδία") || countries[randomIndexForMultipleChoice[0]].getNameInGreek().equals("Ουαλία")
				       || countries[randomIndexForMultipleChoice[0]].getNameInGreek().equals("Σκωτία")) &&
				      (countries[correctIndex].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[correctIndex].getNameInGreek().equals("Αγγλία")
				       || countries[correctIndex].getNameInGreek().equals("Βόρεια Ιρλανδία") || countries[correctIndex].getNameInGreek().equals("Ουαλία")
				       || countries[correctIndex].getNameInGreek().equals("Σκωτία")));
			}
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					textForQuestion.setText("Ποια χώρα έχει αυτό το εθνόσημο;");
					correctAnswerString = countries[correctIndex].getNameInGreek();
					
					for(int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getNameInGreek();
				}
				else
				{
					textForQuestion.setText("This coat of arms belongs in which country?");
					correctAnswerString = countries[correctIndex].getNameInEnglish();
					
					for(int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getNameInEnglish();
				}
				imageViewForQuestionImage.setImage(getImage(ImageType.COUNTRY_COAT_OF_ARMS, questionImageSize, countries[correctIndex].getNameInGreek(), true));
				imageViewForQuestionImage.setImagePath(getImagePath(ImageType.COUNTRY_COAT_OF_ARMS, questionImageSize, countries[correctIndex].getNameInGreek()));
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποιο είναι το εθνόσημο " + GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
				                                                     " " + countries[correctIndex].getGenitiveCaseOfCountry() + ";");
				else textForQuestion.setText("Which of the following is the coat of arms of " + countries[correctIndex].getNameInEnglish() + "?");
				
				for(int i = 0; i < 3; i++)
					randomStringForMultipleChoice[i] = getImagePath(ImageType.COUNTRY_COAT_OF_ARMS, answerImageSize, countries[randomIndexForMultipleChoice[i]].getNameInGreek());
				
				correctAnswerString = getImagePath(ImageType.COUNTRY_COAT_OF_ARMS, answerImageSize, countries[correctIndex].getNameInGreek());
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				
				if(positionOfCorrectAnswer == 0)
				{
					if(getCurrentLanguage() == LANGUAGE.GREEK)
					{
						String verb;
						if(countries[correctIndex].getArticleForCountry().equals("Οι") || countries[correctIndex].getArticleForCountry().equals("Τα"))
							verb = "έχουν";
						else verb = "έχει";
						
						textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " +
						                        countries[correctIndex].getNameInGreek() + " " + verb + " αυτό το εθνόσημο:");
					}
					else textForQuestion.setText("The coat of arms of " + countries[correctIndex].getNameInEnglish() + " is the following:");
					
					imageViewForQuestionImage.setImage(getImage(ImageType.COUNTRY_COAT_OF_ARMS, questionImageSize, countries[correctIndex].getNameInGreek(), true));
					imageViewForQuestionImage.setImagePath(getImagePath(ImageType.COUNTRY_COAT_OF_ARMS, questionImageSize, countries[correctIndex].getNameInGreek()));
				}
				else
				{
					int index;
					do index = random.nextInt(countries.length);
					while(countries[correctIndex].askForCoatOfArms() != countries[index].askForCoatOfArms() ||
					      (countries[index].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[index].getNameInGreek().equals("Αγγλία") ||
					       countries[index].getNameInGreek().equals("Βόρεια Ιρλανδία") || countries[index].getNameInGreek().equals("Ουαλία")
					       ||countries[index].getNameInGreek().equals("Σκωτία")) &&
					      (countries[correctIndex].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[correctIndex].getNameInGreek().equals("Αγγλία")
					       || countries[correctIndex].getNameInGreek().equals("Βόρεια Ιρλανδία") || countries[correctIndex].getNameInGreek().equals("Ουαλία")
					       || countries[correctIndex].getNameInGreek().equals("Σκωτία")) || index == correctIndex);
					
					if(getCurrentLanguage() == LANGUAGE.GREEK)
					{
						String verb;
						if(countries[correctIndex].getArticleForCountry().equals("Οι") || countries[correctIndex].getArticleForCountry().equals("Τα"))
							verb = "έχουν";
						else verb = "έχει";
						
						textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " +
						                        countries[correctIndex].getNameInGreek() + " " + verb + " αυτό το εθνόσημο:");
					}
					else textForQuestion.setText("The coat of arms of " + countries[correctIndex].getNameInEnglish() + " is the following:");
					
					imageViewForQuestionImage.setImage(getImage(ImageType.COUNTRY_COAT_OF_ARMS, questionImageSize, countries[index].getNameInGreek(), true));
					imageViewForQuestionImage.setImagePath(getImagePath(ImageType.COUNTRY_COAT_OF_ARMS, questionImageSize, countries[index].getNameInGreek()));
				}
			}
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				typeOfQuestion = 2;
				set4TextAnswers();
				showNextQuestionNodes(true, 2);
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				typeOfQuestion = 2;
				set4ImageAnswers();
				showNextQuestionNodes(false, 3);
			}
			else
			{
				typeOfQuestion = 1;
				showNextQuestionNodes(true, 1);
			}
		}
		
		private void countriesLocation()
		{
			correctIndex = availableQuestionsArray[COUNTRIES_LOCATION].get(0);
			availableQuestionsArray[COUNTRIES_LOCATION].remove(0);
			
			easyQuestion = countries[correctIndex].hasEasyLocation();
			
			wayOfAskingCurrentQuestion = random.nextInt(3) + 1;
			
			if(wayOfAskingCurrentQuestion != 3)
			{
				do randomIndexForMultipleChoice[0] = random.nextInt(countries.length);
				while(isIslandCountry(correctIndex) && (countries[correctIndex].isIslandCountry() != countries[randomIndexForMultipleChoice[0]].isIslandCountry()) ||
				      !haveSameContinent(correctIndex, randomIndexForMultipleChoice[0]) ||
				      randomIndexForMultipleChoice[0] == correctIndex);
				do randomIndexForMultipleChoice[1] = random.nextInt(countries.length);
				while(isIslandCountry(correctIndex) && (countries[correctIndex].isIslandCountry() != countries[randomIndexForMultipleChoice[1]].isIslandCountry()) ||
				      !haveSameContinent(correctIndex, randomIndexForMultipleChoice[1]) ||
				      randomIndexForMultipleChoice[1] == correctIndex ||
				      randomIndexForMultipleChoice[1] == randomIndexForMultipleChoice[0]);
				do randomIndexForMultipleChoice[2] = random.nextInt(countries.length);
				while(isIslandCountry(correctIndex) && (countries[correctIndex].isIslandCountry() != countries[randomIndexForMultipleChoice[2]].isIslandCountry()) ||
				      !haveSameContinent(correctIndex, randomIndexForMultipleChoice[2]) ||
				      randomIndexForMultipleChoice[2] == correctIndex ||
				      randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[0] ||
				      randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[1]);
			}
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					textForQuestion.setText("Ποια είναι η χώρα που τονίζεται με πράσινο χρώμα;");
					correctAnswerString = countries[correctIndex].getNameInGreek();
					
					for(int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getNameInGreek();
				}
				else
				{
					textForQuestion.setText("Which is the country highlighted in green?");
					correctAnswerString = countries[correctIndex].getNameInEnglish();
					
					for(int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getNameInEnglish();
				}
				imageViewForQuestionImage.setImage(getImage(ImageType.COUNTRY_LOCATION, questionImageSize, countries[correctIndex].getNameInGreek(), true));
				imageViewForQuestionImage.setImagePath(getImagePath(ImageType.COUNTRY_LOCATION, questionImageSize, countries[correctIndex].getNameInGreek()));
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Πού βρίσκεται " + countries[correctIndex].getArticleForCountry().toLowerCase() + " " + countries[correctIndex].getNameInGreek() + ";");
				else textForQuestion.setText("What is the location of " + countries[correctIndex].getNameInEnglish() + "?");
				
				for(int i = 0; i < 3; i++)
					randomStringForMultipleChoice[i] = getImagePath(ImageType.COUNTRY_LOCATION, answerImageSize, countries[randomIndexForMultipleChoice[i]].getNameInGreek());
				
				correctAnswerString = getImagePath(ImageType.COUNTRY_LOCATION, answerImageSize, countries[correctIndex].getNameInGreek());
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				
				if(positionOfCorrectAnswer == 0)
				{
					if(getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η τοποθεσία " + GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
					                                                    " " + countries[correctIndex].getGenitiveCaseOfCountry() + " είναι αυτή:");
					else textForQuestion.setText(countries[correctIndex].getNameInEnglish() + " is located here:");
					
					imageViewForQuestionImage.setImage(getImage(ImageType.COUNTRY_LOCATION, questionImageSize, countries[correctIndex].getNameInGreek(), true));
					imageViewForQuestionImage.setImagePath(getImagePath(ImageType.COUNTRY_LOCATION, questionImageSize, countries[correctIndex].getNameInGreek()));
				}
				else
				{
					int index;
					do index = random.nextInt(countries.length);
					while(isIslandCountry(correctIndex) && (countries[correctIndex].isIslandCountry() != countries[index].isIslandCountry()) ||
					      !isIslandCountry(correctIndex) && countries[index].isIslandCountry() ||
					      !haveSameContinent(correctIndex, index) || index == correctIndex);
					
					if(getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η τοποθεσία " + GreekLanguageHelper.getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
					                                                    " " + countries[correctIndex].getGenitiveCaseOfCountry() + " είναι αυτή:");
					else textForQuestion.setText(countries[correctIndex].getNameInEnglish() + " is located here:");
					
					imageViewForQuestionImage.setImage(getImage(ImageType.COUNTRY_LOCATION, questionImageSize, countries[index].getNameInGreek(), true));
					imageViewForQuestionImage.setImagePath(getImagePath(ImageType.COUNTRY_LOCATION, questionImageSize, countries[index].getNameInGreek()));
				}
			}
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				typeOfQuestion = 2;
				set4TextAnswers();
				showNextQuestionNodes(true, 2);
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				typeOfQuestion = 2;
				set4ImageAnswers();
				showNextQuestionNodes(false, 3);
			}
			else
			{
				typeOfQuestion = 1;
				showNextQuestionNodes(true, 1);
			}
		}
		
		private void countriesLocationAlternative()
		{
			correctIndex = availableQuestionsArray[COUNTRIES_LOCATION_ALTERNATIVE].get(0);
			availableQuestionsArray[COUNTRIES_LOCATION_ALTERNATIVE].remove(0);
			
			easyQuestion = countries[correctIndex].askGeographicalCharacteristics() == 1;
			
			//3 categories: 0=no sea, 1=sea, 2=island
			int category;
			
			if(!countries[correctIndex].hasSea()) category = 0;
			else if(!countries[correctIndex].isIslandCountry()) category = 1;
			else category = 2;
			
			wayOfAskingCurrentQuestion = random.nextInt(2) + 1;
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				if(category == 0)
				{
					do randomIndexForMultipleChoice[0] = random.nextInt(countries.length);
					while (!countries[randomIndexForMultipleChoice[0]].hasSea() ||
					       countries[randomIndexForMultipleChoice[0]].askGeographicalCharacteristics() == 0 ||
					       randomIndexForMultipleChoice[0] == correctIndex);
					do randomIndexForMultipleChoice[1] = random.nextInt(countries.length);
					while (!countries[randomIndexForMultipleChoice[1]].hasSea() ||
					       countries[randomIndexForMultipleChoice[1]].askGeographicalCharacteristics() == 0 ||
					       randomIndexForMultipleChoice[1] == correctIndex ||
					       randomIndexForMultipleChoice[1] == randomIndexForMultipleChoice[0]);
					do randomIndexForMultipleChoice[2] = random.nextInt(countries.length);
					while (!countries[randomIndexForMultipleChoice[2]].hasSea() ||
					       countries[randomIndexForMultipleChoice[2]].askGeographicalCharacteristics() == 0 ||
					       randomIndexForMultipleChoice[2] == correctIndex ||
					       randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[0] ||
					       randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[1]);
				}
				else if(category == 1)
				{
					do randomIndexForMultipleChoice[0] = random.nextInt(countries.length);
					while (countries[randomIndexForMultipleChoice[0]].hasSea() ||
					       randomIndexForMultipleChoice[0] == correctIndex);
					do randomIndexForMultipleChoice[1] = random.nextInt(countries.length);
					while (countries[randomIndexForMultipleChoice[1]].hasSea() ||
					       randomIndexForMultipleChoice[1] == correctIndex ||
					       randomIndexForMultipleChoice[1] == randomIndexForMultipleChoice[0]);
					do randomIndexForMultipleChoice[2] = random.nextInt(countries.length);
					while (countries[randomIndexForMultipleChoice[2]].hasSea() ||
					       randomIndexForMultipleChoice[2] == correctIndex ||
					       randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[0] ||
					       randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[1]);
				}
				else
				{
					do randomIndexForMultipleChoice[0] = random.nextInt(countries.length);
					while (countries[randomIndexForMultipleChoice[0]].isIslandCountry() ||
					       randomIndexForMultipleChoice[0] == correctIndex);
					do randomIndexForMultipleChoice[1] = random.nextInt(countries.length);
					while (countries[randomIndexForMultipleChoice[1]].isIslandCountry() ||
					       randomIndexForMultipleChoice[1] == correctIndex ||
					       randomIndexForMultipleChoice[1] == randomIndexForMultipleChoice[0]);
					do randomIndexForMultipleChoice[2] = random.nextInt(countries.length);
					while (countries[randomIndexForMultipleChoice[2]].isIslandCountry() ||
					       randomIndexForMultipleChoice[2] == correctIndex ||
					       randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[0] ||
					       randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[1]);
				}
				
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					if(category == 0) textForQuestion.setText("Ποια από τις παρακάτω χώρες δε βρέχεται από θάλασσα;");
					else if(category == 1) textForQuestion.setText("Ποια από τις παρακάτω χώρες βρέχεται από θάλασσα;");
					else textForQuestion.setText("Ποια από τις παρακάτω χώρες είναι νησιωτικό κράτος;");
					
					correctAnswerString = countries[correctIndex].getNameInGreek();
					
					for(int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getNameInGreek();
				}
				else
				{
					if(category == 0) textForQuestion.setText("Which of the following countries is landlocked?");
					else if(category == 1) textForQuestion.setText("Which of the following countries is a coastal country?");
					else textForQuestion.setText("Which of the following countries is an island country?");
					
					correctAnswerString = countries[correctIndex].getNameInEnglish();
					
					for(int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = countries[randomIndexForMultipleChoice[i]].getNameInEnglish();
				}
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					String verb;
					if(countries[correctIndex].getArticleForCountry().equals("Οι") || countries[correctIndex].getArticleForCountry().equals("Τα"))
						verb = " βρέχονται ";
					else verb = " βρέχεται ";
					
					if (positionOfCorrectAnswer == 0)
					{
						if(category == 0)
						{
							if(random.nextBoolean()) textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek() + " δε" + verb + "από θάλασσα.");
							else textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek() + " δεν είναι νησιωτικό κράτος.");
						}
						else if(category == 1)
						{
							if(random.nextBoolean()) textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek() + verb + "από θάλασσα.");
							else textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek() + " δεν είναι νησιωτικό κράτος.");
						}
						else
						{
							if(random.nextBoolean()) textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek() + " είναι νησιωτικό κράτος.");
							else textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek() + verb + "από θάλασσα.");
						}
					}
					else
					{
						if(category == 0)
						{
							if(random.nextBoolean()) textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek() + verb + "από θάλασσα.");
							else textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek() + " είναι νησιωτικό κράτος.");
						}
						else if(category == 1)
						{
							if(random.nextBoolean()) textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek() + " δε" + verb + "από θάλασσα.");
							else textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek() + " είναι νησιωτικό κράτος.");
						}
						else
						{
							if(random.nextBoolean()) textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek() + " δεν είναι νησιωτικό κράτος.");
							else textForQuestion.setText(countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek() + " δε" + verb + "από θάλασσα.");
						}
					}
				}
				else
				{
					if (positionOfCorrectAnswer == 0)
					{
						if(category == 0)
						{
							if(random.nextBoolean()) textForQuestion.setText(countries[correctIndex].getNameInEnglish() + " is landlocked.");
							else textForQuestion.setText(countries[correctIndex].getNameInEnglish() + " is not an island country.");
						}
						else if(category == 1)
						{
							if(random.nextBoolean()) textForQuestion.setText(countries[correctIndex].getNameInEnglish() + " is a coastal country.");
							else textForQuestion.setText(countries[correctIndex].getNameInEnglish() + " is not an island country.");
						}
						else
						{
							if(random.nextBoolean()) textForQuestion.setText(countries[correctIndex].getNameInEnglish() + " is an island country.");
							else textForQuestion.setText(countries[correctIndex].getNameInEnglish() + " is a coastal country.");
						}
					}
					else
					{
						if(category == 0)
						{
							if(random.nextBoolean()) textForQuestion.setText(countries[correctIndex].getNameInEnglish() + " is a coastal country.");
							else textForQuestion.setText(countries[correctIndex].getNameInEnglish() + " is an island country.");
						}
						else if(category == 1)
						{
							if(random.nextBoolean()) textForQuestion.setText(countries[correctIndex].getNameInEnglish() + " is landlocked.");
							else textForQuestion.setText(countries[correctIndex].getNameInEnglish() + " is an island country.");
						}
						else
						{
							if(random.nextBoolean()) textForQuestion.setText(countries[correctIndex].getNameInEnglish() + " is not an island country");
							else textForQuestion.setText(countries[correctIndex].getNameInEnglish() + " is landlocked.");
						}
					}
				}
			}
			
			if(wayOfAskingCurrentQuestion == 2)
			{
				typeOfQuestion = 1;
				showNextQuestionNodes(false, 1);
			}
			else
			{
				typeOfQuestion = 2;
				set4TextAnswers();
				showNextQuestionNodes(false, 2);
			}
		}
		
		private void continentsLocation()
		{
			correctIndex = availableQuestionsArray[CONTINENTS_LOCATION].get(0);
			availableQuestionsArray[CONTINENTS_LOCATION].remove(0);
			
			easyQuestion = true;
			
			wayOfAskingCurrentQuestion = random.nextInt(3) + 1;
			
			if(wayOfAskingCurrentQuestion != 3)
			{
				do randomIndexForMultipleChoice[0] = random.nextInt(continents.length);
				while(randomIndexForMultipleChoice[0] == correctIndex);
				do randomIndexForMultipleChoice[1] = random.nextInt(continents.length);
				while(randomIndexForMultipleChoice[1] == correctIndex ||
				      randomIndexForMultipleChoice[1] == randomIndexForMultipleChoice[0]);
				do randomIndexForMultipleChoice[2] = random.nextInt(continents.length);
				while(randomIndexForMultipleChoice[2] == correctIndex ||
				      randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[0] ||
				      randomIndexForMultipleChoice[2] == randomIndexForMultipleChoice[1]);
			}
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					textForQuestion.setText("Ποια είναι η ήπειρος που τονίζεται με πράσινο χρώμα;");
					correctAnswerString = continents[correctIndex].getNameInGreek();
					
					for(int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = continents[randomIndexForMultipleChoice[i]].getNameInGreek();
				}
				else
				{
					textForQuestion.setText("Which is the continent highlighted in green?");
					correctAnswerString = continents[correctIndex].getNameInEnglish();
					
					for(int i = 0; i < 3; i++)
						randomStringForMultipleChoice[i] = continents[randomIndexForMultipleChoice[i]].getNameInEnglish();
				}
				imageViewForQuestionImage.setImage(getImage(ImageType.CONTINENT_LOCATION, questionImageSize, continents[correctIndex].getNameInGreek(), true));
				imageViewForQuestionImage.setImagePath(getImagePath(ImageType.CONTINENT_LOCATION, questionImageSize, continents[correctIndex].getNameInGreek()));
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				if (getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Ποια από τις ακόλουθες είναι η ήπειρος " + continents[correctIndex].getNameInGreek() + ";");
				else textForQuestion.setText("Which of the following is the continent " + continents[correctIndex].getNameInEnglish() + "?");
				
				for(int i = 0; i < 3; i++)
					randomStringForMultipleChoice[i] = getImagePath(ImageType.CONTINENT_LOCATION, answerImageSize, continents[randomIndexForMultipleChoice[i]].getNameInGreek());
				
				correctAnswerString = getImagePath(ImageType.CONTINENT_LOCATION, answerImageSize, continents[correctIndex].getNameInGreek());
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				
				if(positionOfCorrectAnswer == 0)
				{
					if(getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η ήπειρος " + continents[correctIndex].getNameInGreek() + " είναι αυτή:");
					else textForQuestion.setText(continents[correctIndex].getNameInEnglish() + " is located here:");
					
					imageViewForQuestionImage.setImage(getImage(ImageType.CONTINENT_LOCATION, questionImageSize, continents[correctIndex].getNameInGreek(), true));
					imageViewForQuestionImage.setImagePath(getImagePath(ImageType.CONTINENT_LOCATION, questionImageSize, continents[correctIndex].getNameInGreek()));
				}
				else
				{
					int index;
					do index = random.nextInt(continents.length);
					while(index == correctIndex);
					
					if(getCurrentLanguage() == LANGUAGE.GREEK) textForQuestion.setText("Η ήπειρος " + continents[correctIndex].getNameInGreek() + " είναι αυτή:");
					else textForQuestion.setText(continents[correctIndex].getNameInEnglish() + " is located here:");
					
					imageViewForQuestionImage.setImage(getImage(ImageType.CONTINENT_LOCATION, questionImageSize, continents[index].getNameInGreek(), true));
					imageViewForQuestionImage.setImagePath(getImagePath(ImageType.CONTINENT_LOCATION, questionImageSize, continents[index].getNameInGreek()));
				}
			}
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				typeOfQuestion = 2;
				set4TextAnswers();
				showNextQuestionNodes(true, 2);
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				typeOfQuestion = 2;
				set4ImageAnswers();
				showNextQuestionNodes(false, 3);
			}
			else
			{
				typeOfQuestion = 1;
				showNextQuestionNodes(true, 1);
			}
		}
		
		private void catUSACapital()
		{
			
		}
		
		private void catUSALargestCities()
		{
			
		}
		
		private void catUSAYearEnteredUnion()
		{
			
		}
		
		private void catUSANumOfCounties()
		{
			
		}
		
		private void catUSAHouseSeats()
		{
			
		}
		
		private void catUSAPopulation()
		{
			
		}
		
		private void catUSAGeographicalCharacteristics()
		{
			
		}
		
		private void catUSAFlag()
		{
			
		}
		
		private void catUSALocation()
		{
			
		}
		
		private void catGREAdministrativeDivision()
		{
			
		}
		
		private void catGRELargestCities()
		{
			
		}
		
		private void catGRELargestMunicipalities()
		{
			
		}
		
		private void catGREGeographicalCharacteristics()
		{
			
		}
		
		private void catGREPopulation()
		{
			
		}
		
		private void catGRELocation()
		{
			
		}
		
		private void catAttractionsName()
		{
			
		}
		
		private void catAttractionsPhoto()
		{
			
		}
		
		private void catAttractionsLocation()
		{
			
		}
		
		private void set4TextAnswers()
		{
			positionOfCorrectAnswer = random.nextInt(4);
			radioButtonsFor4TextAnswers[positionOfCorrectAnswer].setText(correctAnswerString);
			
			if(positionOfCorrectAnswer == 0)
			{
				radioButtonsFor4TextAnswers[1].setText(randomStringForMultipleChoice[0]);
				radioButtonsFor4TextAnswers[2].setText(randomStringForMultipleChoice[1]);
				radioButtonsFor4TextAnswers[3].setText(randomStringForMultipleChoice[2]);
			}
			else if(positionOfCorrectAnswer == 1)
			{
				radioButtonsFor4TextAnswers[0].setText(randomStringForMultipleChoice[0]);
				radioButtonsFor4TextAnswers[2].setText(randomStringForMultipleChoice[1]);
				radioButtonsFor4TextAnswers[3].setText(randomStringForMultipleChoice[2]);
			}
			else if(positionOfCorrectAnswer == 2)
			{
				radioButtonsFor4TextAnswers[0].setText(randomStringForMultipleChoice[0]);
				radioButtonsFor4TextAnswers[1].setText(randomStringForMultipleChoice[1]);
				radioButtonsFor4TextAnswers[3].setText(randomStringForMultipleChoice[2]);
			}
			else
			{
				radioButtonsFor4TextAnswers[0].setText(randomStringForMultipleChoice[0]);
				radioButtonsFor4TextAnswers[1].setText(randomStringForMultipleChoice[1]);
				radioButtonsFor4TextAnswers[2].setText(randomStringForMultipleChoice[2]);
			}
			
			for(int i = 0; i < 4; i++)
			{
				if(radioButtonsFor4TextAnswers[i].getText().length() > 27)
				{
					tooltipsForRadioButtonsFor4TextAnswers[i].setText(radioButtonsFor4TextAnswers[i].getText());
					radioButtonsFor4TextAnswers[i].setTooltip(tooltipsForRadioButtonsFor4TextAnswers[i]);
				}
				else radioButtonsFor4TextAnswers[i].setTooltip(null);
			}
		}
		
		private void set4ImageAnswers()
		{
			positionOfCorrectAnswer = random.nextInt(4);
			imageViewFor4ImageAnswers[positionOfCorrectAnswer].setImage(new Image(correctAnswerString, true));
			imageViewFor4ImageAnswers[positionOfCorrectAnswer].setImagePath(correctAnswerString);
			
			if(positionOfCorrectAnswer == 0)
			{
				imageViewFor4ImageAnswers[1].setImage(new Image(randomStringForMultipleChoice[0], true));
				imageViewFor4ImageAnswers[2].setImage(new Image(randomStringForMultipleChoice[1], true));
				imageViewFor4ImageAnswers[3].setImage(new Image(randomStringForMultipleChoice[2], true));
				
				imageViewFor4ImageAnswers[1].setImagePath(randomStringForMultipleChoice[0]);
				imageViewFor4ImageAnswers[2].setImagePath(randomStringForMultipleChoice[1]);
				imageViewFor4ImageAnswers[3].setImagePath(randomStringForMultipleChoice[2]);
			}
			else if(positionOfCorrectAnswer == 1)
			{
				imageViewFor4ImageAnswers[0].setImage(new Image(randomStringForMultipleChoice[0], true));
				imageViewFor4ImageAnswers[2].setImage(new Image(randomStringForMultipleChoice[1], true));
				imageViewFor4ImageAnswers[3].setImage(new Image(randomStringForMultipleChoice[2], true));
				
				imageViewFor4ImageAnswers[0].setImagePath(randomStringForMultipleChoice[0]);
				imageViewFor4ImageAnswers[2].setImagePath(randomStringForMultipleChoice[1]);
				imageViewFor4ImageAnswers[3].setImagePath(randomStringForMultipleChoice[2]);
			}
			else if(positionOfCorrectAnswer == 2)
			{
				imageViewFor4ImageAnswers[0].setImage(new Image(randomStringForMultipleChoice[0], true));
				imageViewFor4ImageAnswers[1].setImage(new Image(randomStringForMultipleChoice[1], true));
				imageViewFor4ImageAnswers[3].setImage(new Image(randomStringForMultipleChoice[2], true));
				
				imageViewFor4ImageAnswers[0].setImagePath(randomStringForMultipleChoice[0]);
				imageViewFor4ImageAnswers[1].setImagePath(randomStringForMultipleChoice[1]);
				imageViewFor4ImageAnswers[3].setImagePath(randomStringForMultipleChoice[2]);
			}
			else
			{
				imageViewFor4ImageAnswers[0].setImage(new Image(randomStringForMultipleChoice[0], true));
				imageViewFor4ImageAnswers[1].setImage(new Image(randomStringForMultipleChoice[1], true));
				imageViewFor4ImageAnswers[2].setImage(new Image(randomStringForMultipleChoice[2], true));
				
				imageViewFor4ImageAnswers[0].setImagePath(randomStringForMultipleChoice[0]);
				imageViewFor4ImageAnswers[1].setImagePath(randomStringForMultipleChoice[1]);
				imageViewFor4ImageAnswers[2].setImagePath(randomStringForMultipleChoice[2]);
			}
		}
		
		private void setImageSizes()
		{
			if(imageViewForQuestionImage.getFitHeight() <= MAX_WIDTH_FOR_X250_IMAGES) questionImageSize = "x250";
			else if(imageViewForQuestionImage.getFitHeight() <= MAX_WIDTH_FOR_X500_IMAGES) questionImageSize = "x500";
			else questionImageSize = "x1000";
			
			if(imageViewFor4ImageAnswers[0].getFitWidth() <= MAX_WIDTH_FOR_X250_IMAGES) answerImageSize = "x250";
			else if(imageViewFor4ImageAnswers[0].getFitWidth() <= MAX_WIDTH_FOR_X500_IMAGES) answerImageSize = "x500";
			else answerImageSize = "x1000";
			
			
		}
		
		int getPositionOfCorrectAnswer()
		{
			return positionOfCorrectAnswer;
		}
		
		boolean isEasyQuestion()
		{
			return easyQuestion;
		}
		
		int getTypeOfQuestion()
		{
			return typeOfQuestion;
		}
		
		private boolean haveSameContinent(int index, int random)
		{
			return (countries[index].getContinent().equals("Βόρεια Αμερική") || countries[index].getContinent().equals("Νότια Αμερική") || countries[index].getContinent().equals("North America") || countries[index].getContinent().equals("South America")) &&
			       (countries[random].getContinent().equals("Βόρεια Αμερική") || countries[random].getContinent().equals("Νότια Αμερική") || countries[random].getContinent().equals("North America") || countries[random].getContinent().equals("South America")) ||
			       countries[index].getContinent().equals(countries[random].getContinent());
		}
		
		private boolean isIslandCountry(int index)
		{
			if(!countries[index].isIslandCountry()) return false;
			else
			{
				switch (countries[index].getNameInGreek())
				{
					case "Αγγλία":case "Βόρεια Ιρλανδία":case "Γροιλανδία":case "Δημοκρατία της Κίνας (Ταϊβάν)":case "Ηνωμένο Βασίλειο":
					case "Ιαπωνία":case "Ιρλανδία":case "Ισλανδία":case "Κύπρος":case "Μαδαγασκάρη":case "Μάλτα":case "Ουαλία":
					case "Σιγκαπούρη":case "Σκωτία":return false;
					default:return true;
				}
			}
		}
	}
	
	private void setCurrentQuestionNumber(int currentQuestionNumber)
	{
		this.currentQuestionNumber = currentQuestionNumber;
	}
	
	private int getCurrentQuestionNumber()
	{
		return currentQuestionNumber;
	}
	
	private boolean isQuestionCountdownEnabled()
	{
		return questionCountdownEnabled;
	}
	
	private void setQuestionCountdownEnabled(boolean questionCountdownEnabled)
	{
		this.questionCountdownEnabled = questionCountdownEnabled;
	}
	
	private int getCorrectAnswers()
	{
		return correctAnswers;
	}
	
	private void setCorrectAnswers(int correctAnswers)
	{
		this.correctAnswers = correctAnswers;
	}
	
	private int getScorePoints()
	{
		return scorePoints;
	}
	
	private void setScorePoints(int scorePoints)
	{
		this.scorePoints = scorePoints;
	}
	
	private int getRemainingSecondsForTimeAttack()
	{
		return remainingSecondsForTimeAttack;
	}
	
	private void setRemainingSecondsForTimeAttack(int remainingSecondsForTimeAttack)
	{
		this.remainingSecondsForTimeAttack = remainingSecondsForTimeAttack;
	}
	
	private int getCurrentProgressForProgressbar()
	{
		return currentProgressForProgressbar;
	}
	
	private void setCurrentProgressForProgressbar(int currentProgressForProgressbar)
	{
		this.currentProgressForProgressbar = currentProgressForProgressbar;
	}
	
	private int getSecondsPassed()
	{
		return secondsPassed;
	}
	
	private void setSecondsPassed(int secondsPassed)
	{
		this.secondsPassed = secondsPassed;
	}
	
	private void setProgressFor2_5SecondsWait(int progress)
	{
		progressFor4SecondsWait = progress;
	}
	
	private int getProgressFor2_5SecondsWait()
	{
		return progressFor4SecondsWait;
	}
	
	public void resumeTextAnimation()
	{
		scaleTransitionForTitleLabelForFinishedGame.play();
	}
	
	public void pauseTextAnimation()
	{
		scaleTransitionForTitleLabelForFinishedGame.pause();
	}
	
	private void setCombo(int combo)
	{
		this.combo = combo;
	}
	
	private int getCombo()
	{
		return combo;
	}
	
	private int getRemainingLivesForEndless()
	{
		return remainingLivesForEndless;
	}
	
	private void setRemainingLivesForEndless(int remainingLivesForEndless)
	{
		this.remainingLivesForEndless = remainingLivesForEndless;
	}
	
	private int getMaxCombo()
	{
		return maxCombo;
	}
	
	private void setMaxCombo(int maxCombo)
	{
		this.maxCombo = maxCombo;
	}
	
	private long getMillisecondsPassed()
	{
		return millisecondsPassed;
	}
	
	private void setMillisecondsPassed(long millisecondsPassed)
	{
		this.millisecondsPassed = millisecondsPassed;
	}
}
