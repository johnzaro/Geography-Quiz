package com.johnzaro.geographyquiz.screens;

import com.johnzaro.geographyquiz.core.*;
import com.johnzaro.geographyquiz.core.ImageStuff.Images;
import com.johnzaro.geographyquiz.core.customNodes.*;
import com.johnzaro.geographyquiz.dataStructures.Continent;
import com.johnzaro.geographyquiz.dataStructures.Game;
import com.johnzaro.geographyquiz.dataStructures.Player;
import com.johnzaro.geographyquiz.screens.errorScreens.ErrorScreen;
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
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.util.*;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;
import static com.johnzaro.geographyquiz.core.ImageStuff.ImageType.*;
import static com.johnzaro.geographyquiz.core.helperClasses.GreekLanguageHelper.*;

/**
 * Created by John on 26/11/2016.
 */

public class GameScreen extends CoreScreen
{
	private CustomHBox hBoxForLives;
	private CustomVBox vBoxForPausedGame, vBoxForNextQuestionButtonAndProgressBar;
	private CustomGridPane gridPaneFor4TextAnswers, gridPaneFor2TextAnswers, gridPaneFor4ImageAnswers;
	private StackPane stackPaneForBigImage;

	private CustomImageView welcomeScreenImage, worldMapBackground, titleImageForFinishedGame, pauseGameIcon,
			imageViewForLargeHeart, imageViewForLargeAnswerIcon, imageViewForQuestionImage, imageViewForBigImage;
	private CustomImageView[] answerIconsFor2TextAnswers, answerIconsFor4TextAnswers, imageViewForLives;
	private ImageViewWithBackground[] imageViewFor4ImageAnswers;

	private CustomLabel titleLabelForFinishedGame, popUpMessage;
	private CustomButton returnToGameButton, restartGameButton, returnToGamePropertiesButton, returnToWelcomeScreenButton, nextQuestionButton,
			restartGameFromFinishedScreenButton, returnToWelcomeScreenFromFinishedGameScreenButton;
	private ProgressBar progressBarForCountdown, progressBarFor2_5SecondsWaitForNextQuestion;
	private Text textForQuestionNumber, textForCountdown, textForScore, textForQuestion, textForTimePassed, textForResultsDuration,
			textForResults, textForResults2, textForCombo;
	private Text numberOfQuestionAnimated, comboAnimated, scoreAnimated;
	private CustomRadioButton[] radioButtonsFor2TextAnswers, radioButtonsFor4TextAnswers;

	private ToggleGroup toggleGroupFor4PossibleAnswers, toggleGroupFor2PossibleAnswers;

	private CustomTooltip pauseGameTooltip;
	private CustomTooltip[] tooltipsForRadioButtonsFor4TextAnswers;
	
	private RotateTransition rotateTransitionForTextForCombo;
	private FadeTransition fadeTransitionForWorldMapImage;
	private ScaleTransition scaleTransitionForTitleLabelForFinishedGame,
			scaleTransitionForPauseGameIcon, scaleTransitionForTextForTimePassed, scaleTransitionForGridPaneFor2TextAnswers,
			scaleTransitionForProgressBarForCountdown, scaleTransitionForTextForCountdown, scaleTransitionForTextForQuestionNumber,
			scaleTransitionForTextForScore, scaleTransitionForVBoxForNextQuestion, scaleTransitionForTextForQuestion,
			scaleTransitionForGridPaneFor4TextAnswers, scaleTransitionForGridPaneFor4ImageAnswers, scaleTransitionForQuestionImage,
			scaleTransitionForTextForResultsDuration, scaleTransitionForTextForResults, scaleTransitionForTextForResults2,
			scaleTransitionForRestartGameButton, scaleTransitionForReturnToWelcomeScreenButton, scaleTransitionForHBoxForLives,
			scaleTransitionForNumberOfQuestionAnimated, scaleTransitionForImageViewForLargeHeart, scaleTransitionForLargeAnswerIcon,
			scaleTransitionForTextForCombo, scaleTransitionForComboAnimated, scaleTransitionForScoreAnimated, scaleTransitionForPopUpMessage;
	private TranslateTransition translateTransitionForTitleImageForFinishedGame, translateTransitionForTitleLabelForFinishedGame,
			translateTransitionFoVBoxForPausedGame, translateTransitionForNumberOfQuestionAnimated,
			translateTransitionForImageViewForLargeHeart, translateTransitionForLargeAnswerIcon, translateTransitionForComboAnimated,
			translateTransitionForScoreAnimated;
	private ParallelTransition parallelTransitionForNumberOfQuestionAnimated, parallelTransitionForLifeLostAnimation,
			parallelTransitionForLargeAnswerIcon, parallelTransitionForComboAnimated, parallelTransitionForScoreAnimated,
			parallelTransitionForTextForCombo;

	private ScaleTransition[] scaleTransitionFor2TextAnswers, scaleTransitionFor4TextAnswers, scaleTransitionFor4ImageAnswers,
			scaleTransitionForAnswerIconsFor2TextAnswers, scaleTransitionForAnswerIconsFor4TextAnswers,
			scaleTransitionForAnswerIconsFor4ImageAnswers;
	private SequentialTransition[] sequentialTransitionsFor2TextAnswers, sequentialTransitionsFor4TextAnswers,
			sequentialTransitionsFor4ImageAnswers;
	private Timeline timelineToShow2TextAnswers, timelineToShow4TextAnswers, timelineToShow4ImageAnswers;

	private Timeline timelineToHideAllStuffFromGameScreen;
	private Timeline timelineForTimePassedCount, timelineForQuestionCountdown, timelineFor2_5SecondsWaitForNextQuestion,
			timelineToFinishGame, timelineToHideAllStuffFromFinishedGameScreen, timelineToCountSecondsForQuestionCountdown,
			timelineForLifeLostAnimation, timelineForLargeAnswerIcon, timelineToCloseTextForCountdown, timelineForComboAnimated,
			timelineForComboNoAnimated, timelineForScoreAnimated, timelineForScoreNoAnimated;
	private Timeline timelineFor1MinuteProgressbarForTimeAttack, timelineFor2MinutesProgressbarForTimeAttack,
			timelineFor5MinutesProgressbarForTimeAttack, timelineFor10MinutesProgressbarForTimeAttack, timelineToCountSecondsForTimeAttack,
			timelineToShowNumberOfQuestion, timelineToCountMilliseconds, timelineToShowPopUpMessage;
	
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
	private boolean questionCountdownEnabled, isFinishScreenShown;
	private int secondsPassed;
	private int remainingSecondsForTimeAttack;
	private int remainingLivesForEndless;

	protected void recalculateUI(double width, double height)
	{
		super.recalculateUI(width, height);

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

		String strokeWidth = "-fx-stroke-width: " + 0.0010 * width + ";";
		String strokeWidthLarge = "-fx-stroke-width: " + 0.0016 * width + ";";
		
		woodPanelFor1IconImage.setLayoutX(ratioProperties.getGame().getWoodPanelFor1IconImageLayoutX() * width);
		woodPanelFor1IconImage.setLayoutY(ratioProperties.getGame().getWoodPanelFor1IconImageLayoutY() * height);
		
		vBoxForSound.setLayoutX(0.7083 * width);
		vBoxForSound.setLayoutY(ratioProperties.getGame().getvBoxForSoundLayoutY() * height);
		if(vBoxForSound.getTranslateY() != 0) vBoxForSound.setTranslateY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
		
		soundButton.setLayoutY(ratioProperties.getGame().getSoundIconLayoutY() * height - soundButton.getFitWidth() / 2.0);
		
		pauseGameIcon.setFitWidth(veryLargeIconSize);
		pauseGameIcon.setLayoutX(0.9193 * width - pauseGameIcon.getFitWidth() / 2.0);
		pauseGameIcon.setLayoutY(0.0944 * height);
		pauseGameTooltip.setFont(font25P);
		vBoxForPausedGame.setPrefSize(0.4062 * width, 0.6019 * height);
		vBoxForPausedGame.setLayoutX(width / 2.0 - vBoxForPausedGame.getPrefWidth() / 2.0);
		vBoxForPausedGame.setLayoutY(height / 2.0 - vBoxForPausedGame.getPrefHeight() / 2.0);
		vBoxForPausedGame.setSpacing(0.0463 * height);
		vBoxForPausedGame.setStyle(cssBackgroundAndBorderBig + cssPadding30);
		if(vBoxForPausedGame.getTranslateY() != 0) vBoxForPausedGame.setTranslateY(-1.0 * (vBoxForPausedGame.getLayoutY() + vBoxForPausedGame.getPrefHeight() + 20));
		returnToGameButton.setPrefSize(vBoxForPausedGame.getPrefWidth(), 0.0926 * height);
		returnToGameButton.setFont(font45B);
		returnToGameButton.getTooltip().setFont(font25P);
		restartGameButton.setPrefSize(vBoxForPausedGame.getPrefWidth(), 0.0926 * height);
		restartGameButton.setFont(font45B);
		restartGameButton.getTooltip().setFont(font25P);
		returnToGamePropertiesButton.setPrefSize(vBoxForPausedGame.getPrefWidth(), 0.0926 * height);
		returnToGamePropertiesButton.setFont(font45B);
		returnToGamePropertiesButton.getTooltip().setFont(font25P);
		returnToWelcomeScreenButton.setPrefSize(vBoxForPausedGame.getPrefWidth(), 0.0926 * height);
		returnToWelcomeScreenButton.setFont(font45B);
		returnToWelcomeScreenButton.getTooltip().setFont(font25P);
		
		progressBarForCountdown.setPrefSize(0.2865 * width, 0.0463 * height);
		progressBarForCountdown.setLayoutX(width / 2.0 - progressBarForCountdown.getPrefWidth() / 2.0);
		
		if(progressBarForCountdown.isVisible())
		{
			numberOfQuestionAnimated.setFont(font45B);
			textForQuestionNumber.setFont(font45B);
			textForQuestionNumber.setLayoutY(0.1204 * height);
		}
		else
		{
			numberOfQuestionAnimated.setFont(font55B);
			textForQuestionNumber.setFont(font55B);
			textForQuestionNumber.setLayoutY(0.1250 * height);
		}
		textForQuestionNumber.setLayoutX(0.0469 * width);
		textForQuestionNumber.setStyle(strokeWidth);

		numberOfQuestionAnimated.setLayoutY(height / 2.0);
		numberOfQuestionAnimated.setLayoutX(width / 2.0 - numberOfQuestionAnimated.getLayoutBounds().getWidth() / 2.0);
		numberOfQuestionAnimated.setStyle(strokeWidth);

		textForCountdown.setFont(font80B);
		textForCountdown.setStyle(strokeWidthLarge);
		textForCountdown.setLayoutX(width / 2.0 - textForCountdown.getBoundsInLocal().getWidth() / 2.0);

		textForQuestion.setLayoutX(0.0521 * width);
		textForQuestion.setWrappingWidth(0.6458 * width);
		textForQuestion.setFont(font65B);
		textForQuestion.setStyle(strokeWidthLarge);
		if(textForQuestion.getTranslateX() != 0) textForQuestion.setTranslateX(0.1771 * width);

		textForScore.setFont(font45B);
		textForScore.setStyle(strokeWidth);
		textForScore.setLayoutX(0.8625 * width - textForScore.getBoundsInLocal().getWidth() / 2.0);
		textForScore.setLayoutY(0.3194 * height);
		scoreAnimated.setFont(font70B);
		scoreAnimated.setStyle(strokeWidthLarge);

		textForCombo.setLayoutX(textForScore.getLayoutX() + textForScore.getBoundsInLocal().getWidth() / 2.0 - textForCombo.getBoundsInLocal().getWidth() / 2.0);
		textForCombo.setLayoutY(0.4730 * height);
		textForCombo.setFont(font70B);
		textForCombo.setStyle(strokeWidthLarge);
		comboAnimated.setLayoutY(0.6018 * height);
		comboAnimated.setFont(font70B);
		comboAnimated.setStyle(strokeWidthLarge);

		if(textForTimePassed.isVisible())
		{
			if(progressBarForCountdown.isVisible())
			{
				textForTimePassed.setFont(font45B);
				textForTimePassed.setLayoutY(0.1204 * height);

				progressBarForCountdown.setLayoutY(0.1343 * height);
				textForCountdown.setLayoutY(0.1852 * height);
			}
			else
			{
				textForTimePassed.setFont(font55B);
				textForTimePassed.setLayoutY(0.1296 * height);
			}
			textForQuestion.setLayoutY(0.2593 * height);
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
			imageViewForQuestionImage.setFitHeight(0.3241 * height);

			if(gridPaneFor2TextAnswers.isVisible()) imageViewForQuestionImage.setLayoutY(0.3565 * height);
			else imageViewForQuestionImage.setLayoutY(0.2639 * height);
		}
		imageViewForQuestionImage.setLayoutX(width / 2.0 - imageViewForQuestionImage.getFitHeight() / 2.0);
		
		textForTimePassed.setLayoutX(width / 2.0 - textForTimePassed.getBoundsInLocal().getWidth() / 2.0);
		textForTimePassed.setStyle(strokeWidth);

		imageViewForLargeAnswerIcon.setFitWidth(largeIconSize);
		imageViewForLargeAnswerIcon.setLayoutX(width / 2.0 - imageViewForLargeAnswerIcon.getFitWidth() / 2.0);
		imageViewForLargeAnswerIcon.setLayoutY(height / 2.0 - imageViewForLargeAnswerIcon.getFitWidth() / 2.0);
		
		gridPaneFor2TextAnswers.setPrefSize(0.2083 * width, 0.2130 * height);
		gridPaneFor2TextAnswers.setLayoutX(width / 2.0 - gridPaneFor2TextAnswers.getPrefWidth() / 2.0);
		gridPaneFor2TextAnswers.setLayoutY(0.6991 * height);
		gridPaneFor2TextAnswers.setHgap(0.0052 * width);
		gridPaneFor2TextAnswers.setVgap(0.0130 * width);
		gridPaneFor2TextAnswers.setStyle(cssBackgroundAndBorderBig + cssPadding20);
		radioButtonsFor2TextAnswers[0].setFont(font40P);
		radioButtonsFor2TextAnswers[1].setFont(font40P);
		answerIconsFor2TextAnswers[0].setFitWidth(largeIconSize);
		answerIconsFor2TextAnswers[1].setFitWidth(largeIconSize);

		gridPaneFor4TextAnswers.setPrefSize(0.4427 * width, 0.3148 * height);
		gridPaneFor4TextAnswers.setLayoutX(width / 2.0 - gridPaneFor4TextAnswers.getPrefWidth() / 2.0);
		gridPaneFor4TextAnswers.setLayoutY(0.5972 * height);
		gridPaneFor4TextAnswers.setHgap(0.0052 * width);
		gridPaneFor4TextAnswers.setVgap(0.0078 * width);
		gridPaneFor4TextAnswers.setStyle(cssBackgroundAndBorderBig + cssPadding20);
		for(int i = 0; i < radioButtonsFor4TextAnswers.length; i++)
		{
			answerIconsFor4TextAnswers[i].setFitWidth(largeIconSize);
			radioButtonsFor4TextAnswers[i].setFont(font40P);
			radioButtonsFor4TextAnswers[i].setPrefWidth(0.8730 * gridPaneFor4TextAnswers.getPrefWidth());
			tooltipsForRadioButtonsFor4TextAnswers[i].setFont(font25P);
		}

		gridPaneFor4ImageAnswers.setHgap(0.02 * width);
		gridPaneFor4ImageAnswers.setVgap(0.02 * width);
		gridPaneFor4ImageAnswers.setPrefSize(0.4167 * width, 0.5370 * height);
		gridPaneFor4ImageAnswers.setLayoutX(width / 2.0 - gridPaneFor4ImageAnswers.getPrefWidth() / 2.0);
		gridPaneFor4ImageAnswers.setLayoutY(0.3565 * height);
		for(ImageViewWithBackground imageViewFor4ImageAnswer : imageViewFor4ImageAnswers)
		{
			imageViewFor4ImageAnswer.getImageView().setFitWidth(0.4310 * gridPaneFor4ImageAnswers.getPrefHeight());
			imageViewFor4ImageAnswer.getAnswerIcon().setFitWidth(0.4310 * gridPaneFor4ImageAnswers.getPrefHeight());
			imageViewFor4ImageAnswer.getAnswerIcon().setFitHeight(0.4310 * gridPaneFor4ImageAnswers.getPrefHeight());
			if(imageViewFor4ImageAnswer.hasWhiteBackground())
				imageViewFor4ImageAnswer.setStyle(WHITE_BACKGROUND_DARK_TRANSPARENT + BLACK_BORDERED + cssBackgroundAndBorderMedium + cssPadding10);
			else
				imageViewFor4ImageAnswer.setStyle(BLACK_BACKGROUND_DARK_TRANSPARENT + BLACK_BORDERED + cssBackgroundAndBorderMedium + cssPadding10);
		}
		
		hBoxForLives.setLayoutX(0.0469 * width);
		if(gameMode == GAMEMODE.ENDLESS_GAMEMODE)
		{
			hBoxForLives.setLayoutY(0.1435 * height);
			hBoxForLives.setSpacing(0.0026 * width);
			
			for (int i = 0; i < hBoxForLives.getChildren().size(); i++) imageViewForLives[i].setFitWidth(largeIconSize);
			
			imageViewForLargeHeart.setFitWidth(largeIconSize);
			imageViewForLargeHeart.setLayoutX(width / 2.0 - imageViewForLargeHeart.getFitWidth() / 2.0);
			imageViewForLargeHeart.setLayoutY(height / 2.0 - imageViewForLargeHeart.getFitWidth() / 2.0);
		}

		imageViewForBigImage.setFitWidth(0.3125 * width);

		vBoxForNextQuestionButtonAndProgressBar.setPrefSize(0.1563 * width, 0.1667 * height);
		vBoxForNextQuestionButtonAndProgressBar.setLayoutX(0.8021 * width);
		vBoxForNextQuestionButtonAndProgressBar.setLayoutY(0.7500 * height);
		vBoxForNextQuestionButtonAndProgressBar.setSpacing(0.0130 * height);
		nextQuestionButton.setPrefWidth(vBoxForNextQuestionButtonAndProgressBar.getPrefWidth());
		nextQuestionButton.setFitWidth(0.0703 * width);
		nextQuestionButton.setFont(font25P);
		progressBarFor2_5SecondsWaitForNextQuestion.setPrefSize(0.9 * vBoxForNextQuestionButtonAndProgressBar.getPrefWidth(), 0.0139 * height);
		progressBarFor2_5SecondsWaitForNextQuestion.setMinHeight(0.0139 * height);
		progressBarFor2_5SecondsWaitForNextQuestion.setMaxHeight(0.0139 * height);

		popUpMessage.setPrefSize(0.3333 * width, 0.1852 * height);
		popUpMessage.setLayoutX(width / 2.0 - popUpMessage.getPrefWidth() / 2.0);
		popUpMessage.setLayoutY(height / 2.0 - popUpMessage.getPrefHeight() / 2.0);
		popUpMessage.setStyle(cssBackgroundAndBorderBig + cssPadding10);
		popUpMessage.setFont(font25P);

		//FINISHED GAME STUFF----------------------------------------------------------------------------------------
		titleImageForFinishedGame.setFitWidth(0.4688 * width);
		titleImageForFinishedGame.setLayoutX(width / 2.0 - titleImageForFinishedGame.getFitWidth() / 2.0);
		titleImageForFinishedGame.setLayoutY(0.0574 * height);
		if(titleImageForFinishedGame.getTranslateX() != 0)
		{
			titleImageForFinishedGame.setTranslateX(-0.0364 * width);
			titleLabelForFinishedGame.setTranslateX(-0.0364 * width);
		}
		
		titleLabelForFinishedGame.setPrefSize(0.5052 * width, 0.1111 * height);
		titleLabelForFinishedGame.setLayoutX(0.2479 * width);
		titleLabelForFinishedGame.setLayoutY(0.0730 * height);
		titleLabelForFinishedGame.setFont(font95B);
		
		textForResultsDuration.setWrappingWidth(0.7813 * width);
		textForResultsDuration.setFont(font50B);
		textForResultsDuration.setStyle(strokeWidth);
		textForResultsDuration.setX(width / 2.0 - textForResultsDuration.getBoundsInLocal().getWidth() / 2.0);

		textForResults.setWrappingWidth(0.7813 * width);
		textForResults.setFont(font50B);
		textForResults.setStyle(strokeWidth);
		textForResults.setX(width / 2.0 - textForResults.getBoundsInLocal().getWidth() / 2.0);

		textForResults2.setWrappingWidth(0.7813 * width);
		textForResults2.setFont(font50B);
		textForResults2.setStyle(strokeWidth);
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
		restartGameFromFinishedScreenButton.setFont(font45B);
		restartGameFromFinishedScreenButton.getTooltip().setFont(font25P);

		returnToWelcomeScreenFromFinishedGameScreenButton.setPrefSize(0.3646 * width, 0.0926 * height);
		returnToWelcomeScreenFromFinishedGameScreenButton.setLayoutX(width / 2.0 - returnToWelcomeScreenFromFinishedGameScreenButton.getPrefWidth() / 2.0);
		returnToWelcomeScreenFromFinishedGameScreenButton.setLayoutY(0.8194 * height);
		returnToWelcomeScreenFromFinishedGameScreenButton.setFont(font45B);
		returnToWelcomeScreenFromFinishedGameScreenButton.getTooltip().setFont(font25P);
	}

	protected void recalculateBackground(double width, double height)
	{
		super.recalculateBackground(width, height);

		worldMapBackground.setLayoutX(getImageStuff().getWorldMapLayoutX() * width);
		worldMapBackground.setLayoutY(getImageStuff().getWorldMapLayoutY() * height);
		worldMapBackground.setFitWidth(getImageStuff().getWorldMapFitWidth() * width);
		worldMapBackground.setFitHeight(getImageStuff().getWorldMapFitHeight() * height);

		welcomeScreenImage.setLayoutX(0);
		welcomeScreenImage.setLayoutY(0);
		welcomeScreenImage.setFitWidth(width);
		welcomeScreenImage.setFitHeight(height);
	}
	
	public GameScreen()
	{
		//FUNDAMENTAL STUFF-------------------------------------------------------------------------------------
		questionMaker = new QuestionMaker();
		answerEvaluator = new AnswerEvaluator();
		pr = images.get(Images.PROGRESS_COLOR).getPixelReader();
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

		//MAIN GAME STUFF-------------------------------------------------------------------------------------

		//QUESTION NUMBER
		textForQuestionNumber = new Text();
		textForQuestionNumber.setTextAlignment(TextAlignment.LEFT);
		textForQuestionNumber.setFill(BROWN);
		textForQuestionNumber.setCache(true);
		textForQuestionNumber.setCacheHint(CacheHint.SCALE);
		textForQuestionNumber.getStyleClass().add("white-stroke");

		numberOfQuestionAnimated = new Text();
		numberOfQuestionAnimated.setTextAlignment(TextAlignment.CENTER);
		numberOfQuestionAnimated.setFill(BROWN);
		numberOfQuestionAnimated.getStyleClass().add("white-stroke");

		//TIME PASSED
		textForTimePassed = new Text();
		textForTimePassed.setTextAlignment(TextAlignment.CENTER);
		textForTimePassed.setFill(BROWN);
		textForTimePassed.getStyleClass().add("white-stroke");

		//HBOX FOR LIVES
		hBoxForLives = new CustomHBox(false, Pos.CENTER_LEFT, false, null);

		imageViewForLives = new CustomImageView[5];
		for(int i = 0; i < 5; i++) imageViewForLives[i] = new CustomImageView(true, true, false, false, null);

		imageViewForLargeHeart = new CustomImageView(true, true, false, false, null);

		//10 SECONDS COUNTDOWN
		progressBarForCountdown = new ProgressBar();

		textForCountdown = new Text(" ");
		textForCountdown.setTextAlignment(TextAlignment.CENTER);
		textForCountdown.setFill(BROWN);
		textForCountdown.getStyleClass().add("white-stroke");

		//SCORE TEXT
		textForScore = new Text();
		textForScore.setTextAlignment(TextAlignment.CENTER);
		textForScore.setFill(BROWN);
		textForScore.setCache(true);
		textForScore.setCacheHint(CacheHint.SCALE);
		textForScore.getStyleClass().add("white-stroke");

		scoreAnimated = new Text();
		scoreAnimated.setTextAlignment(TextAlignment.CENTER);
		scoreAnimated.setFill(BROWN);
		scoreAnimated.getStyleClass().add("white-stroke");

		//COMBO TEXT
		textForCombo = new Text();
		textForCombo.setTextAlignment(TextAlignment.LEFT);
		textForCombo.setFill(BROWN);
		textForCombo.getStyleClass().add("white-stroke");
		textForCombo.setCache(true);
		textForCombo.setCacheHint(CacheHint.SCALE_AND_ROTATE);

		comboAnimated = new Text();
		comboAnimated.setTextAlignment(TextAlignment.CENTER);
		comboAnimated.setFill(BROWN);
		comboAnimated.getStyleClass().add("white-stroke");

		//QUESTION TEXT
		textForQuestion = new Text();
		textForQuestion.setTextAlignment(TextAlignment.LEFT);
		textForQuestion.setFill(BROWN);
		textForQuestion.setCache(true);
		textForQuestion.setCacheHint(CacheHint.SCALE);
		textForQuestion.getStyleClass().add("white-stroke");

		//QUESTION IMAGE
		imageViewForQuestionImage = new CustomImageView(true, true, true, false, null);

		//IMAGE VIEW FOR LARGE ANSWER ICON
		imageViewForLargeAnswerIcon = new CustomImageView(true, true, false, false, null);

		//GRID PANE FOR 2 TEXT ANSWERS
		gridPaneFor2TextAnswers = new CustomGridPane(Pos.CENTER_LEFT, "black-bordered-background-dark", true, CacheHint.SCALE);

		radioButtonsFor2TextAnswers = new CustomRadioButton[2];
		radioButtonsFor2TextAnswers[0] = new CustomRadioButton(null, false);
		radioButtonsFor2TextAnswers[1] = new CustomRadioButton(null, false);

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
		gridPaneFor4TextAnswers = new CustomGridPane(Pos.CENTER_LEFT, "black-bordered-background-dark", true, CacheHint.SCALE);

		answerIconsFor4TextAnswers = new CustomImageView[4];
		radioButtonsFor4TextAnswers = new CustomRadioButton[4];
		tooltipsForRadioButtonsFor4TextAnswers = new CustomTooltip[4];

		toggleGroupFor4PossibleAnswers = new ToggleGroup();

		for(int i = 0; i < 4; i++)
		{
			answerIconsFor4TextAnswers[i] = new CustomImageView(true, true, false, false, null);

			radioButtonsFor4TextAnswers[i] = new CustomRadioButton(null, false);

			toggleGroupFor4PossibleAnswers.getToggles().add(radioButtonsFor4TextAnswers[i]);

			tooltipsForRadioButtonsFor4TextAnswers[i] = new CustomTooltip();

			gridPaneFor4TextAnswers.add(answerIconsFor4TextAnswers[i], 0, i);
			gridPaneFor4TextAnswers.add(radioButtonsFor4TextAnswers[i], 1, i);
		}

		//GRID PANE FOR 4 IMAGE ANSWERS
		gridPaneFor4ImageAnswers = new CustomGridPane(Pos.CENTER, null, true, CacheHint.SCALE);

		imageViewFor4ImageAnswers = new ImageViewWithBackground[4];

		for(int i = 0; i < 4; i++)
		{
			imageViewFor4ImageAnswers[i] = new ImageViewWithBackground();

			gridPaneFor4ImageAnswers.add(imageViewFor4ImageAnswers[i], i % 2, i / 2);
		}
		
		//BIG IMAGE
		imageViewForBigImage = new CustomImageView(true, true, false, false, null);

		stackPaneForBigImage = new StackPane();
		stackPaneForBigImage.setMouseTransparent(true);
		stackPaneForBigImage.getChildren().add(imageViewForBigImage);

		//NEXT BUTTON + PROGRESS BAR FOR NEXT BUTTON
		nextQuestionButton = new CustomButton(null, images.get(Images.BACK_ARROW), ContentDisplay.TOP, TextAlignment.CENTER, CustomButton.COLOR, true, true,false, true, CacheHint.SCALE);
		nextQuestionButton.getGraphic().setRotate(180);

		progressBarFor2_5SecondsWaitForNextQuestion = new ProgressBar();
		progressBarFor2_5SecondsWaitForNextQuestion.setStyle("-fx-accent: " + GREEN_COLOR + ";");

		vBoxForNextQuestionButtonAndProgressBar = new CustomVBox(false, Pos.CENTER, null, true, CacheHint.SCALE);
		vBoxForNextQuestionButtonAndProgressBar.getChildren().addAll(nextQuestionButton, progressBarFor2_5SecondsWaitForNextQuestion);

		//PAUSED GAME STUFF-------------------------------------------------------------------------------------
		pauseGameIcon = new CustomImageView(false, true, false, false, null);
		pauseGameIcon.setCursor(Cursor.HAND);

		pauseGameTooltip = new CustomTooltip();
		Tooltip.install(pauseGameIcon, pauseGameTooltip);

		popUpMessage = new CustomLabel(null, TextAlignment.CENTER, WHITE, "black-bordered-background-dark", null, null, false, false, true, false, false, null);
		popUpMessage.setMouseTransparent(true);

		returnToGameButton = new CustomButton(null, null, null, null, null, false, true,true, true, CacheHint.SCALE);
		restartGameButton = new CustomButton(null, null, null, null, null, false, true,true, true, CacheHint.SCALE);
		returnToGamePropertiesButton = new CustomButton(null, null, null, null, null, false, true,true, true, CacheHint.SCALE);
		returnToWelcomeScreenButton = new CustomButton(null, null, null, null, null, false, true,true, true, CacheHint.SCALE);

		vBoxForPausedGame = new CustomVBox(false, Pos.CENTER, "black-bordered-background-light", true, CacheHint.SPEED);
		vBoxForPausedGame.getChildren().addAll(returnToGameButton, restartGameButton, returnToGamePropertiesButton, returnToWelcomeScreenButton);

		//RESULTS SCREEN
		titleImageForFinishedGame = new CustomImageView(true, true, false, true, CacheHint.SPEED);

		titleLabelForFinishedGame = new CustomLabel(Pos.CENTER, TextAlignment.CENTER, DARK_BROWN, null, null, null, false, false, false, false, true, CacheHint.SCALE);

		textForResultsDuration = new Text();
		textForResultsDuration.setTextAlignment(TextAlignment.CENTER);
		textForResultsDuration.setFill(BROWN);
		textForResultsDuration.setCache(true);
		textForResultsDuration.setCacheHint(CacheHint.SCALE);
		textForResultsDuration.getStyleClass().add("white-stroke");

		textForResults = new Text();
		textForResults.setTextAlignment(TextAlignment.CENTER);
		textForResults.setFill(BROWN);
		textForResults.setCache(true);
		textForResults.setCacheHint(CacheHint.SCALE);
		textForResults.getStyleClass().add("white-stroke");

		textForResults2 = new Text();
		textForResults2.setTextAlignment(TextAlignment.CENTER);
		textForResults2.setFill(BROWN);
		textForResults2.setCache(true);
		textForResults2.setCacheHint(CacheHint.SCALE);
		textForResults2.getStyleClass().add("white-stroke");

		restartGameFromFinishedScreenButton = new CustomButton(null, null, null, TextAlignment.CENTER, null, false, true,true, true, CacheHint.SCALE);
		returnToWelcomeScreenFromFinishedGameScreenButton = new CustomButton(null, null, null, TextAlignment.CENTER, null, false, true,true, true, CacheHint.SCALE);

		//EFFECTS-------------------------------------------------------------------------------------
		titleLabelForFinishedGame.setEffect(innerShadow);
		titleImageForFinishedGame.setEffect(dropShadow);
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
		pauseGameIcon.setEffect(dropShadow);
		vBoxForPausedGame.setEffect(dropShadow);
		vBoxForNextQuestionButtonAndProgressBar.setEffect(dropShadow);
		gridPaneFor2TextAnswers.setEffect(dropShadow);
		gridPaneFor4TextAnswers.setEffect(dropShadow);
		popUpMessage.setEffect(dropShadow);
		imageViewForLargeHeart.setEffect(dropShadow);
		hBoxForLives.setEffect(dropShadow);
		progressBarForCountdown.setEffect(dropShadow);
		imageViewForQuestionImage.setEffect(dropShadow);
		restartGameFromFinishedScreenButton.setEffect(dropShadow);
		returnToWelcomeScreenFromFinishedGameScreenButton.setEffect(dropShadow);
		imageViewForLargeAnswerIcon.setEffect(dropShadow);
		numberOfQuestionAnimated.setEffect(dropShadow);
		stackPaneForBigImage.setEffect(dropShadow);
		textForCountdown.setEffect(dropShadowForText);
		imageViewFor4ImageAnswers[0].setEffect(dropShadow);
		imageViewFor4ImageAnswers[1].setEffect(dropShadow);
		imageViewFor4ImageAnswers[2].setEffect(dropShadow);
		imageViewFor4ImageAnswers[3].setEffect(dropShadow);

		nodesPane.getChildren().addAll(welcomeScreenImage, worldMapBackground, titleImageForFinishedGame, titleLabelForFinishedGame,
				pauseGameIcon, textForQuestionNumber, progressBarForCountdown, woodPanelFor1IconImage, soundButton,
				vBoxForNextQuestionButtonAndProgressBar, textForScore, textForQuestion, imageViewForQuestionImage, gridPaneFor4TextAnswers,
				gridPaneFor4ImageAnswers, gridPaneFor2TextAnswers, textForTimePassed, textForCountdown, textForResultsDuration, textForCombo,
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

						getAudioStuff().playPopUpSound();
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

						getAudioStuff().playMinimizeSound();
						scaleTransitionForPopUpMessage.playFromStart();
					}
					else
					{
						popUpMessage.setScaleX(0);
						popUpMessage.setScaleY(0);
					}
				}));

		setupListeners();

		if(animationsUsed != ANIMATIONS.NO) setupLimitedAnimations();
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

		pauseGameIcon.setOnMouseClicked(e ->
		{
			pauseGameIcon.setDisable(true);
			pauseGameIcon.setImage(images.get(Images.PAUSE_CLICKED));

			openPauseMenu = true;

			timelineToShowPopUpMessage.playFromStart();
		});

		returnToGameButton.setOnAction(e ->
		{
			vBoxForPausedGame.setDisable(true);

			if(animationsUsed != ANIMATIONS.NO)
			{
				translateTransitionFoVBoxForPausedGame.setToY(-1.0 * (vBoxForPausedGame.getLayoutY() + vBoxForPausedGame.getPrefHeight() + 20));
				translateTransitionFoVBoxForPausedGame.setOnFinished(ev ->
				{
					translateTransitionFoVBoxForPausedGame.setOnFinished(eve ->{});
					vBoxForPausedGame.setVisible(false);

					pauseGameIcon.setImage(images.get(Images.PAUSE));

					if(getCombo() > 1) parallelTransitionForTextForCombo.play();

					removeBlurEffect();

					nextQuestionButton.setDisable(false);
					nextQuestionButton.fire();
				});

				getAudioStuff().playSlideSound();
				translateTransitionFoVBoxForPausedGame.playFromStart();
			}
			else
			{
				vBoxForPausedGame.setTranslateY(-1.0 * (vBoxForPausedGame.getLayoutY() + vBoxForPausedGame.getPrefHeight() + 20));
				vBoxForPausedGame.setVisible(false);

				pauseGameIcon.setImage(images.get(Images.PAUSE_CLICKED));

				removeBlurEffect();

				nextQuestionButton.setDisable(false);
				nextQuestionButton.fire();
			}
		});

		restartGameButton.setOnAction(e ->
		{
			getAudioStuff().playRewindSound();

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

				getAudioStuff().playSlideSound();
				translateTransitionFoVBoxForPausedGame.playFromStart();
			}
			else
			{
				stopAllTimers();
				setInitialStateForAllNodes();
			}
		});

		returnToGamePropertiesButton.setOnAction(e ->
		{
			returningToWelcomeScreen = false;

			removeBlurEffect();

			stopAllTimers();

			if(animationsUsed != ANIMATIONS.NO)
			{
				timelineToHideAllStuffFromGameScreen.setOnFinished(ev ->
				{
					gamePropertiesScreen.setFromWelcomeScreen(false);
					showOtherScreen(gamePropertiesScreen);
				});
				timelineToHideAllStuffFromGameScreen.playFromStart();
			}
			else
			{
				gamePropertiesScreen.setFromWelcomeScreen(false);
				showOtherScreen(gamePropertiesScreen);
			}
		});

		returnToWelcomeScreenButton.setOnAction(e ->
		{
			returningToWelcomeScreen = true;

			stopAllTimers();

			if(animationsUsed != ANIMATIONS.NO)
			{
				timelineToHideAllStuffFromGameScreen.setOnFinished(ev -> {});
				timelineToHideAllStuffFromGameScreen.playFromStart();
			}
			else
			{
				showOtherScreen(welcomeScreen);
				removeBlurEffect();
			}
		});

		nextQuestionButton.setOnAction(e ->
		{
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

		restartGameFromFinishedScreenButton.setOnAction(e ->
		{
			getAudioStuff().playRewindSound();

			stopAllTimers();

			setInitialStateForAllNodes();

			if(animationsUsed != ANIMATIONS.NO) timelineToShowAllStuff.playFromStart();
		});

		returnToWelcomeScreenFromFinishedGameScreenButton.setOnAction(e ->
		{
			returningToWelcomeScreen = true;

			nextQuestionButton.setDisable(false);

			stopAllTimers();

			removeBlurEffect();

			if(animationsUsed != ANIMATIONS.NO)
			{
				timelineToHideAllStuffFromFinishedGameScreen.setOnFinished(ev -> {});
				timelineToHideAllStuffFromFinishedGameScreen.playFromStart();
			}
			else showOtherScreen(welcomeScreen);
		});

		imageViewForQuestionImage.setOnMouseEntered(e ->
		{
			stackPaneForBigImage.setLayoutX(e.getSceneX() + 30);
			stackPaneForBigImage.setLayoutY(e.getSceneY() - imageViewForBigImage.getFitWidth() / 2.0);

			String style;
			if(imageViewForQuestionImage.getImagePath().contains("coatOfArms") && getImageStuff().requiresWhiteBackground(imageViewForQuestionImage.getImagePath()))
				style = BLACK_BORDERED + WHITE_BACKGROUND_DARK_TRANSPARENT;
			else
				style = BLACK_BORDERED + BLACK_BACKGROUND_DARK_TRANSPARENT;
			style += cssBackgroundAndBorderBig + cssPadding10;
			stackPaneForBigImage.setStyle(style);

			String currentSize;
			if(imageViewForQuestionImage.getImagePath().contains("x250")) currentSize = "x250";
			else if(imageViewForQuestionImage.getImagePath().contains("x500")) currentSize = "x500";
			else currentSize = "x1000";

			if(imageViewForBigImage.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X250_IMAGES && currentSize.equals("x250") ||
			   imageViewForBigImage.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X500_IMAGES && currentSize.equals("x500") ||
			   imageViewForBigImage.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X1000_IMAGES && currentSize.equals("x1000"))
				imageViewForBigImage.setImage(imageViewForQuestionImage.getImage());
			else
			{
				String newSize;
				if(imageViewForBigImage.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X500_IMAGES) newSize = "x500";
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
			imageViewFor4ImageAnswers[i].getImageView().setOnMouseEntered(e ->
			{
				stackPaneForBigImage.setLayoutX(e.getSceneX() + 30);
				
				if(finalI < 2) stackPaneForBigImage.setLayoutY(e.getSceneY() - imageViewForBigImage.getFitWidth() / 2.0);
				else stackPaneForBigImage.setLayoutY(e.getSceneY() - imageViewForBigImage.getFitWidth() - 30);
				
				String style;
				if(imageViewFor4ImageAnswers[finalI].getImageView().getImagePath().contains("coatOfArms") &&
						getImageStuff().requiresWhiteBackground(imageViewFor4ImageAnswers[finalI].getImageView().getImagePath()))
					style = BLACK_BORDERED + WHITE_BACKGROUND_DARK_TRANSPARENT;
				else
					style = BLACK_BORDERED + BLACK_BACKGROUND_DARK_TRANSPARENT;
				style += cssBackgroundAndBorderBig + cssPadding10;
				stackPaneForBigImage.setStyle(style);

				String currentSize;
				if(imageViewFor4ImageAnswers[finalI].getImageView().getImagePath().contains("x250")) currentSize = "x250";
				else if(imageViewFor4ImageAnswers[finalI].getImageView().getImagePath().contains("x500")) currentSize = "x500";
				else currentSize = "x1000";

				if(imageViewForBigImage.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X250_IMAGES && currentSize.equals("x250") ||
				   imageViewForBigImage.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X500_IMAGES && currentSize.equals("x500") ||
				   imageViewForBigImage.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X1000_IMAGES && currentSize.equals("x1000"))
					imageViewForBigImage.setImage(imageViewFor4ImageAnswers[finalI].getImageView().getImage());
				else
				{
					String newSize;
					if(imageViewForBigImage.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X500_IMAGES) newSize = "x500";
					else newSize = "x1000";

					String newPath = imageViewFor4ImageAnswers[finalI].getImageView().getImagePath().replace(currentSize, newSize);
					if(!imageViewForBigImage.getImagePath().equals(newPath))
					{
						imageViewForBigImage.setImage(new Image(newPath));
						imageViewForBigImage.setImagePath(newPath);
					}
				}
				stackPaneForBigImage.setVisible(true);
			});

			imageViewFor4ImageAnswers[i].getImageView().setOnMouseMoved(e ->
			{
				stackPaneForBigImage.setLayoutX(e.getSceneX() + 30);
				
				if(finalI < 2) stackPaneForBigImage.setLayoutY(e.getSceneY() - imageViewForBigImage.getFitWidth() / 2.0);
				else stackPaneForBigImage.setLayoutY(e.getSceneY() - imageViewForBigImage.getFitWidth() - 30);
			});

			imageViewFor4ImageAnswers[i].getImageView().setOnMouseExited(e -> stackPaneForBigImage.setVisible(false));
		}

		soundButton.setOnAction(e ->
		{
			soundButton.setDisable(true);

			if (!vBoxForSound.isVisible())
			{
				if(animationsUsed != ANIMATIONS.NO) timelineToShowSoundOptions.play();
				else
				{
					if(isFinishScreenShown)
					{
						titleImageForFinishedGame.setTranslateX(-0.0364 * mainScene.getWidth());
						titleLabelForFinishedGame.setTranslateX(-0.0364 * mainScene.getWidth());
					}

					vBoxForSound.setTranslateY(0);
					vBoxForSound.setVisible(true);
				}
			}
			else
			{
				if(animationsUsed != ANIMATIONS.NO) timelineToHideSoundOptions.play();
				else
				{
					if(isFinishScreenShown)
					{
						titleImageForFinishedGame.setTranslateX(0);
						titleLabelForFinishedGame.setTranslateX(0);
					}

					vBoxForSound.setTranslateY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
					vBoxForSound.setVisible(false);
				}
			}
			soundButton.setDisable(false);
		});
	}

	private void hideAnimations()
	{
		if(animationsUsed != ANIMATIONS.NO)
		{
			timelineForScoreAnimated.stop();
			timelineForScoreAnimated.playFrom(Duration.millis(2000));

			if(getCombo() > 1)
			{
				timelineForComboAnimated.stop();
				timelineForComboAnimated.playFrom(Duration.millis(2000));
			}
		}
		else
		{
			timelineForScoreNoAnimated.stop();
			timelineForScoreNoAnimated.playFrom(Duration.millis(2000));

			if(getCombo() > 1)
			{
				timelineForComboNoAnimated.stop();
				timelineForComboNoAnimated.playFrom(Duration.millis(2000));
			}
		}
	}

	private void setupTimers()
	{
		timelineToCountMilliseconds = new Timeline(new KeyFrame(Duration.millis(10), e -> setMillisecondsPassed(getMillisecondsPassed() + 10)));
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
				}), new KeyFrame(Duration.millis(300), e ->
				{
					scaleTransitionForNumberOfQuestionAnimated.setToX(1);
					scaleTransitionForNumberOfQuestionAnimated.setToY(1);

					translateTransitionForNumberOfQuestionAnimated.setToX(textForQuestionNumber.getLayoutX() - numberOfQuestionAnimated.getLayoutX());
					translateTransitionForNumberOfQuestionAnimated.setToY(textForQuestionNumber.getLayoutY() - numberOfQuestionAnimated.getLayoutY());

					parallelTransitionForNumberOfQuestionAnimated.playFromStart();
				}),
				new KeyFrame(Duration.millis(550), e ->
				{
					updateQuestionNumberText();
					
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
				new KeyFrame(Duration.millis(300), e ->
				{
					scaleTransitionForLargeAnswerIcon.setToX(1);
					scaleTransitionForLargeAnswerIcon.setToY(1);

					double x = 0, y = 0;
					if (gridPaneFor2TextAnswers.isVisible())
					{
						int index = questionMaker.getPositionOfCorrectAnswer();
						if (!imageViewForLargeAnswerIcon.getImage().equals(images.get(Images.CORRECT_BIG))) index = 1 - index;
						
						answerIconsFor2TextAnswers[index].setScaleX(1);
						answerIconsFor2TextAnswers[index].setScaleY(1);

						x = answerIconsFor2TextAnswers[index].localToScene(answerIconsFor2TextAnswers[index].getBoundsInLocal()).getMinX() - imageViewForLargeAnswerIcon.getLayoutX();
						y = answerIconsFor2TextAnswers[index].localToScene(answerIconsFor2TextAnswers[index].getBoundsInLocal()).getMinY() -
							answerIconsFor2TextAnswers[index].getFitWidth() / 2.0 - imageViewForLargeAnswerIcon.getLayoutY();
					}
					else if (gridPaneFor4TextAnswers.isVisible())
					{
						int index = -1;
						if (imageViewForLargeAnswerIcon.getImage().equals(images.get(Images.CORRECT_BIG))) index = questionMaker.getPositionOfCorrectAnswer();
						else for(int i = 0; i < radioButtonsFor4TextAnswers.length; i++) if(radioButtonsFor4TextAnswers[i].isSelected()) index = i;
						
						answerIconsFor4TextAnswers[index].setScaleX(1);
						answerIconsFor4TextAnswers[index].setScaleY(1);
						
						x = answerIconsFor4TextAnswers[index].localToScene(answerIconsFor4TextAnswers[index].getBoundsInLocal()).getMinX() - imageViewForLargeAnswerIcon.getLayoutX();
						y = answerIconsFor4TextAnswers[index].localToScene(answerIconsFor4TextAnswers[index].getBoundsInLocal()).getMinY() -
							answerIconsFor4TextAnswers[index].getFitWidth() / 2.0 - imageViewForLargeAnswerIcon.getLayoutY();
					}

					translateTransitionForLargeAnswerIcon.setToX(x);
					translateTransitionForLargeAnswerIcon.setToY(y);

					parallelTransitionForLargeAnswerIcon.playFromStart();
				}),
				new KeyFrame(Duration.millis(550), e ->
				{
					if (gridPaneFor2TextAnswers.isVisible())
					{
						int index = questionMaker.getPositionOfCorrectAnswer();
						if (imageViewForLargeAnswerIcon.getImage().equals(images.get(Images.CORRECT_BIG)))
							answerIconsFor2TextAnswers[index].setImage(images.get(Images.CORRECT_SMALL));
						else
						{
							index = 1 - index;
							answerIconsFor2TextAnswers[index].setImage(images.get(Images.INCORRECT_SMALL));
							answerEvaluator.hintToCorrectAnswer();
						}
					}
					else if (gridPaneFor4TextAnswers.isVisible())
					{
						int index = -1;
						if (imageViewForLargeAnswerIcon.getImage().equals(images.get(Images.CORRECT_BIG)))
						{
							index = questionMaker.getPositionOfCorrectAnswer();
							answerIconsFor4TextAnswers[index].setImage(images.get(Images.CORRECT_SMALL));
						}
						else
						{
							for(int i = 0; i < radioButtonsFor4TextAnswers.length; i++) if(radioButtonsFor4TextAnswers[i].isSelected()) index = i;
							answerIconsFor4TextAnswers[index].setImage(images.get(Images.INCORRECT_SMALL));
							answerEvaluator.hintToCorrectAnswer();
						}
					}
					
					parallelTransitionForLargeAnswerIcon.stop();
					imageViewForLargeAnswerIcon.setScaleX(0);
					imageViewForLargeAnswerIcon.setScaleY(0);
					imageViewForLargeAnswerIcon.setTranslateX(0);
					imageViewForLargeAnswerIcon.setTranslateY(0);
					
					if(!openPauseMenu) showNextQuestionButton();
				}));

			timelineForComboAnimated = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					scaleTransitionForComboAnimated.setToX(2);
					scaleTransitionForComboAnimated.setToY(2);

					translateTransitionForComboAnimated.setToX(0);
					translateTransitionForComboAnimated.setToY(0);

					parallelTransitionForComboAnimated.playFromStart();
				}),
				new KeyFrame(Duration.millis(2000), e ->
				{
					scaleTransitionForComboAnimated.setToX(0);
					scaleTransitionForComboAnimated.setToY(0);

					Bounds bounds = textForCombo.localToScene(textForCombo.getBoundsInLocal());
					double x = bounds.getMinX() - comboAnimated.getBoundsInLocal().getWidth() / 2.0 - comboAnimated.getLayoutX();
					double y = bounds.getMinY() + bounds.getHeight() - comboAnimated.getLayoutY();

					translateTransitionForComboAnimated.setToX(x);
					translateTransitionForComboAnimated.setToY(y);

					parallelTransitionForComboAnimated.playFromStart();
				}),
				new KeyFrame(Duration.millis(2250), e ->
				{
					textForCombo.setText("x" + getCombo());
					textForCombo.setLayoutX(textForScore.getLayoutX() + textForScore.getBoundsInLocal().getWidth() / 2.0 - textForCombo.getBoundsInLocal().getWidth() / 2.0);
					startTextForComboAnimation(getCombo());
					
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
				new KeyFrame(Duration.millis(2000), e ->
				{
					removeBlurEffect();

					scaleTransitionForScoreAnimated.setToX(0);
					scaleTransitionForScoreAnimated.setToY(0);

					Bounds bounds = textForScore.localToScene(textForScore.getBoundsInLocal());
					double x = bounds.getMinX() - scoreAnimated.getBoundsInLocal().getWidth() / 2.0 - scoreAnimated.getLayoutX();
					double y = bounds.getMinY() + bounds.getHeight() - scoreAnimated.getLayoutY();

					translateTransitionForScoreAnimated.setToX(x);
					translateTransitionForScoreAnimated.setToY(y);

					parallelTransitionForScoreAnimated.playFromStart();
				}),
				new KeyFrame(Duration.millis(2250), e ->
				{
					updateScoreText();
					
					parallelTransitionForScoreAnimated.stop();
					scoreAnimated.setScaleX(0);
					scoreAnimated.setScaleY(0);
					scoreAnimated.setTranslateX(0);
					scoreAnimated.setTranslateY(0);
					
					if(openPauseMenu) showPauseMenu();
				}));
			
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
					
					imageViewForLargeHeart.setImage(images.get(Images.HEART_BIG));
					
					scaleTransitionForImageViewForLargeHeart.setToX(10);
					scaleTransitionForImageViewForLargeHeart.setToY(10);
					
					translateTransitionForImageViewForLargeHeart.setToX(0);
					translateTransitionForImageViewForLargeHeart.setToY(0);
					
					parallelTransitionForLifeLostAnimation.playFromStart();
				}),
				new KeyFrame(Duration.millis(500), e ->
				{
					getAudioStuff().playHeartBreakingSound();
					imageViewForLargeHeart.setImage(images.get(Images.HEART_BROKEN_BIG));
				}),
				new KeyFrame(Duration.millis(800), e ->
				{
					removeBlurEffect();
					
					scaleTransitionForImageViewForLargeHeart.setToX(1);
					scaleTransitionForImageViewForLargeHeart.setToY(1);
					
					double x = imageViewForLives[getRemainingLivesForEndless()].localToScene(imageViewForLives[getRemainingLivesForEndless()].getBoundsInLocal()).getMinX()
							- imageViewForLargeHeart.getLayoutX();
					double y = imageViewForLives[getRemainingLivesForEndless()].localToScene(imageViewForLives[getRemainingLivesForEndless()].getBoundsInLocal()).getMinY()
							- imageViewForLargeHeart.getLayoutY();
					
					translateTransitionForImageViewForLargeHeart.setToX(x);
					translateTransitionForImageViewForLargeHeart.setToY(y);
					
					parallelTransitionForLifeLostAnimation.playFromStart();
				}),
				new KeyFrame(Duration.millis(1050), e ->
				{
					imageViewForLives[getRemainingLivesForEndless()].setImage(images.get(Images.HEART_LOST_SMALL));
					
					parallelTransitionForLifeLostAnimation.stop();
					imageViewForLargeHeart.setScaleX(0);
					imageViewForLargeHeart.setScaleY(0);
					imageViewForLargeHeart.setTranslateX(0);
					imageViewForLargeHeart.setTranslateY(0);
					
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
				new KeyFrame(Duration.millis(2000), e ->
				{
					removeBlurEffect();

					updateScoreText();
					scoreAnimated.setVisible(false);

					if(openPauseMenu) showPauseMenu();
				}));

			timelineForComboNoAnimated = new Timeline(
				new KeyFrame(Duration.millis(0), e -> comboAnimated.setVisible(true)),
				new KeyFrame(Duration.millis(2000), e ->
				{
					textForCombo.setText("x" + getCombo());
					textForCombo.setLayoutX(textForScore.getLayoutX() + textForScore.getBoundsInLocal().getWidth() / 2.0 - textForCombo.getBoundsInLocal().getWidth() / 2.0);
					textForCombo.setScaleX(1);
					textForCombo.setScaleY(1);
					textForCombo.setVisible(true);

					comboAnimated.setVisible(false);
				}));
			
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
					
					imageViewForLargeHeart.setImage(images.get(Images.HEART_BIG));
					imageViewForLargeHeart.setVisible(true);
				}),
				new KeyFrame(Duration.millis(400), e ->
				{
					getAudioStuff().playHeartBreakingSound();
					imageViewForLargeHeart.setImage(images.get(Images.HEART_BROKEN_BIG));
				}),
				new KeyFrame(Duration.millis(800), e ->
				{
					removeBlurEffect();
					
					imageViewForLargeHeart.setVisible(false);
					
					if(openPauseMenu) showPauseMenu();
				}));
		}

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
					if (getRemainingSecondsForTimeAttack() <= 15) getAudioStuff().playPopUpSound();

					if((getDurationInMinutesForTimeAttack() == 1 || getDurationInMinutesForTimeAttack() == 2) && getRemainingSecondsForTimeAttack() == 15 ||
					   (getDurationInMinutesForTimeAttack() == 5 || getDurationInMinutesForTimeAttack() == 10) && getRemainingSecondsForTimeAttack() == 30)
							getAudioStuff().playClockTickingSound();

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
					getAudioStuff().stopClockTickingSound();
					getAudioStuff().playTimeOverSound();

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

				if (currentSecond <= 3) getAudioStuff().playPopUpSound();

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

			timelineForQuestionCountdown = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				progressBarForCountdown.setProgress(getCurrentProgressForProgressbar() / 1000.0);

				int x = 1000 - getCurrentProgressForProgressbar();
				if (x < 1000)
				{
					String color = pr.getColor(x, 0).toString().replace("0x", "");
					progressBarForCountdown.setStyle("-fx-accent: #" + color + ";");
				}
			}),
			new KeyFrame(Duration.millis(50), e ->
			{
				setCurrentProgressForProgressbar(getCurrentProgressForProgressbar() - 5);
				if (getCurrentProgressForProgressbar() < 0)
				{
					if (gridPaneFor2TextAnswers.isVisible()) setFunctional2TextAnswers(false);
					else if (gridPaneFor4TextAnswers.isVisible()) setFunctional4TextAnswers(false);
					else setFunctional4ImageAnswers(false);

					progressBarForCountdown.setId("red");
					timelineForQuestionCountdown.stop();
					getAudioStuff().stopClockTickingSound();
					getAudioStuff().playTimeOverSound();

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
							rotateTransitionForTextForCombo.setDuration(Duration.millis(200));
							rotateTransitionForTextForCombo.setCycleCount(1);
							rotateTransitionForTextForCombo.setAutoReverse(false);
							scaleTransitionForTextForCombo.setDuration(Duration.millis(200));
							scaleTransitionForTextForCombo.setCycleCount(1);
							scaleTransitionForTextForCombo.setAutoReverse(false);
							scaleTransitionForTextForCombo.setFromX(textForCombo.getScaleX());
							scaleTransitionForTextForCombo.setFromY(textForCombo.getScaleY());
							scaleTransitionForTextForCombo.setToX(0);
							scaleTransitionForTextForCombo.setToY(0);
							parallelTransitionForTextForCombo.playFromStart();
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
		rotateTransitionForTextForCombo = new RotateTransition();
		rotateTransitionForTextForCombo.setNode(textForCombo);
		scaleTransitionForTextForCombo = new ScaleTransition();
		scaleTransitionForTextForCombo.setNode(textForCombo);
		parallelTransitionForTextForCombo = new ParallelTransition(rotateTransitionForTextForCombo, scaleTransitionForTextForCombo);

		scaleTransitionForScoreAnimated = new ScaleTransition(Duration.millis(200), scoreAnimated);
		translateTransitionForScoreAnimated = new TranslateTransition(Duration.millis(200), scoreAnimated);
		parallelTransitionForScoreAnimated = new ParallelTransition(scaleTransitionForScoreAnimated, translateTransitionForScoreAnimated);

		scaleTransitionForComboAnimated = new ScaleTransition(Duration.millis(200), comboAnimated);
		translateTransitionForComboAnimated = new TranslateTransition(Duration.millis(200), comboAnimated);
		parallelTransitionForComboAnimated = new ParallelTransition(scaleTransitionForComboAnimated, translateTransitionForComboAnimated);

		scaleTransitionForLargeAnswerIcon = new ScaleTransition(Duration.millis(200), imageViewForLargeAnswerIcon);
		translateTransitionForLargeAnswerIcon = new TranslateTransition(Duration.millis(200), imageViewForLargeAnswerIcon);
		parallelTransitionForLargeAnswerIcon = new ParallelTransition(scaleTransitionForLargeAnswerIcon, translateTransitionForLargeAnswerIcon);

		scaleTransitionForImageViewForLargeHeart = new ScaleTransition(Duration.millis(200), imageViewForLargeHeart);
		translateTransitionForImageViewForLargeHeart = new TranslateTransition(Duration.millis(200), imageViewForLargeHeart);
		parallelTransitionForLifeLostAnimation = new ParallelTransition(scaleTransitionForImageViewForLargeHeart, translateTransitionForImageViewForLargeHeart);

		scaleTransitionForNumberOfQuestionAnimated = new ScaleTransition(Duration.millis(200), numberOfQuestionAnimated);
		translateTransitionForNumberOfQuestionAnimated = new TranslateTransition(Duration.millis(200), numberOfQuestionAnimated);
		parallelTransitionForNumberOfQuestionAnimated = new ParallelTransition(scaleTransitionForNumberOfQuestionAnimated, translateTransitionForNumberOfQuestionAnimated);

		fadeTransitionForWorldMapImage = new FadeTransition(Duration.millis(300), worldMapBackground);
		fadeTransitionForWorldMapImage.setToValue(0);
		fadeTransitionForWorldMapImage.setOnFinished(ev -> showOtherScreen(welcomeScreen));

		ScaleTransition scaleTransitionFor2TextAnswers_1_1 = new ScaleTransition(Duration.millis(150), radioButtonsFor2TextAnswers[0]);
		scaleTransitionFor2TextAnswers_1_1.setFromX(0);
		scaleTransitionFor2TextAnswers_1_1.setFromY(0);
		scaleTransitionFor2TextAnswers_1_1.setToX(1.2);
		scaleTransitionFor2TextAnswers_1_1.setToY(1.2);

		ScaleTransition scaleTransitionFor2TextAnswers_1_2 = new ScaleTransition(Duration.millis(50), radioButtonsFor2TextAnswers[0]);
		scaleTransitionFor2TextAnswers_1_2.setFromX(1.2);
		scaleTransitionFor2TextAnswers_1_2.setFromY(1.2);
		scaleTransitionFor2TextAnswers_1_2.setToX(1);
		scaleTransitionFor2TextAnswers_1_2.setToY(1);

		ScaleTransition scaleTransitionFor2TextAnswers_2_1 = new ScaleTransition(Duration.millis(150), radioButtonsFor2TextAnswers[1]);
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

		ScaleTransition scaleTransitionFor4TextAnswers_1_1 = new ScaleTransition(Duration.millis(150), radioButtonsFor4TextAnswers[0]);
		scaleTransitionFor4TextAnswers_1_1.setFromX(0);
		scaleTransitionFor4TextAnswers_1_1.setFromY(0);
		scaleTransitionFor4TextAnswers_1_1.setToX(1.2);
		scaleTransitionFor4TextAnswers_1_1.setToY(1.2);

		ScaleTransition scaleTransitionFor4TextAnswers_1_2 = new ScaleTransition(Duration.millis(50), radioButtonsFor4TextAnswers[0]);
		scaleTransitionFor4TextAnswers_1_2.setFromX(1.2);
		scaleTransitionFor4TextAnswers_1_2.setFromY(1.2);
		scaleTransitionFor4TextAnswers_1_2.setToX(1);
		scaleTransitionFor4TextAnswers_1_2.setToY(1);

		ScaleTransition scaleTransitionFor4TextAnswers_2_1 = new ScaleTransition(Duration.millis(150), radioButtonsFor4TextAnswers[1]);
		scaleTransitionFor4TextAnswers_2_1.setFromX(0);
		scaleTransitionFor4TextAnswers_2_1.setFromY(0);
		scaleTransitionFor4TextAnswers_2_1.setToX(1.2);
		scaleTransitionFor4TextAnswers_2_1.setToY(1.2);

		ScaleTransition scaleTransitionFor4TextAnswers_2_2 = new ScaleTransition(Duration.millis(50), radioButtonsFor4TextAnswers[1]);
		scaleTransitionFor4TextAnswers_2_2.setFromX(1.2);
		scaleTransitionFor4TextAnswers_2_2.setFromY(1.2);
		scaleTransitionFor4TextAnswers_2_2.setToX(1);
		scaleTransitionFor4TextAnswers_2_2.setToY(1);

		ScaleTransition scaleTransitionFor4TextAnswers_3_1 = new ScaleTransition(Duration.millis(150), radioButtonsFor4TextAnswers[2]);
		scaleTransitionFor4TextAnswers_3_1.setFromX(0);
		scaleTransitionFor4TextAnswers_3_1.setFromY(0);
		scaleTransitionFor4TextAnswers_3_1.setToX(1.2);
		scaleTransitionFor4TextAnswers_3_1.setToY(1.2);

		ScaleTransition scaleTransitionFor4TextAnswers_3_2 = new ScaleTransition(Duration.millis(50), radioButtonsFor4TextAnswers[2]);
		scaleTransitionFor4TextAnswers_3_2.setFromX(1.2);
		scaleTransitionFor4TextAnswers_3_2.setFromY(1.2);
		scaleTransitionFor4TextAnswers_3_2.setToX(1);
		scaleTransitionFor4TextAnswers_3_2.setToY(1);

		ScaleTransition scaleTransitionFor4TextAnswers_4_1 = new ScaleTransition(Duration.millis(150), radioButtonsFor4TextAnswers[3]);
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

		ScaleTransition scaleTransitionFor4ImageAnswers_1_1 = new ScaleTransition(Duration.millis(150), imageViewFor4ImageAnswers[0]);
		scaleTransitionFor4ImageAnswers_1_1.setFromX(0);
		scaleTransitionFor4ImageAnswers_1_1.setFromY(0);
		scaleTransitionFor4ImageAnswers_1_1.setToX(1.2);
		scaleTransitionFor4ImageAnswers_1_1.setToY(1.2);

		ScaleTransition scaleTransitionFor4ImageAnswers_1_2 = new ScaleTransition(Duration.millis(50), imageViewFor4ImageAnswers[0]);
		scaleTransitionFor4ImageAnswers_1_2.setFromX(1.2);
		scaleTransitionFor4ImageAnswers_1_2.setFromY(1.2);
		scaleTransitionFor4ImageAnswers_1_2.setToX(1);
		scaleTransitionFor4ImageAnswers_1_2.setToY(1);

		ScaleTransition scaleTransitionFor4ImageAnswers_2_1 = new ScaleTransition(Duration.millis(150), imageViewFor4ImageAnswers[1]);
		scaleTransitionFor4ImageAnswers_2_1.setFromX(0);
		scaleTransitionFor4ImageAnswers_2_1.setFromY(0);
		scaleTransitionFor4ImageAnswers_2_1.setToX(1.2);
		scaleTransitionFor4ImageAnswers_2_1.setToY(1.2);

		ScaleTransition scaleTransitionFor4ImageAnswers_2_2 = new ScaleTransition(Duration.millis(50), imageViewFor4ImageAnswers[1]);
		scaleTransitionFor4ImageAnswers_2_2.setFromX(1.2);
		scaleTransitionFor4ImageAnswers_2_2.setFromY(1.2);
		scaleTransitionFor4ImageAnswers_2_2.setToX(1);
		scaleTransitionFor4ImageAnswers_2_2.setToY(1);

		ScaleTransition scaleTransitionFor4ImageAnswers_3_1 = new ScaleTransition(Duration.millis(150), imageViewFor4ImageAnswers[2]);
		scaleTransitionFor4ImageAnswers_3_1.setFromX(0);
		scaleTransitionFor4ImageAnswers_3_1.setFromY(0);
		scaleTransitionFor4ImageAnswers_3_1.setToX(1.2);
		scaleTransitionFor4ImageAnswers_3_1.setToY(1.2);

		ScaleTransition scaleTransitionFor4ImageAnswers_3_2 = new ScaleTransition(Duration.millis(50), imageViewFor4ImageAnswers[2]);
		scaleTransitionFor4ImageAnswers_3_2.setFromX(1.2);
		scaleTransitionFor4ImageAnswers_3_2.setFromY(1.2);
		scaleTransitionFor4ImageAnswers_3_2.setToX(1);
		scaleTransitionFor4ImageAnswers_3_2.setToY(1);

		ScaleTransition scaleTransitionFor4ImageAnswers_4_1 = new ScaleTransition(Duration.millis(150), imageViewFor4ImageAnswers[3]);
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
		scaleTransitionFor2TextAnswers[0] = new ScaleTransition(Duration.millis(200), radioButtonsFor2TextAnswers[0]);
		scaleTransitionFor2TextAnswers[1] = new ScaleTransition(Duration.millis(200), radioButtonsFor2TextAnswers[1]);

		scaleTransitionFor4TextAnswers = new ScaleTransition[4];
		scaleTransitionFor4TextAnswers[0] = new ScaleTransition(Duration.millis(200), radioButtonsFor4TextAnswers[0]);
		scaleTransitionFor4TextAnswers[1] = new ScaleTransition(Duration.millis(200), radioButtonsFor4TextAnswers[1]);
		scaleTransitionFor4TextAnswers[2] = new ScaleTransition(Duration.millis(200), radioButtonsFor4TextAnswers[2]);
		scaleTransitionFor4TextAnswers[3] = new ScaleTransition(Duration.millis(200), radioButtonsFor4TextAnswers[3]);

		scaleTransitionFor4ImageAnswers = new ScaleTransition[4];
		scaleTransitionFor4ImageAnswers[0] = new ScaleTransition(Duration.millis(200), imageViewFor4ImageAnswers[0]);
		scaleTransitionFor4ImageAnswers[1] = new ScaleTransition(Duration.millis(200), imageViewFor4ImageAnswers[1]);
		scaleTransitionFor4ImageAnswers[2] = new ScaleTransition(Duration.millis(200), imageViewFor4ImageAnswers[2]);
		scaleTransitionFor4ImageAnswers[3] = new ScaleTransition(Duration.millis(200), imageViewFor4ImageAnswers[3]);

		scaleTransitionForAnswerIconsFor2TextAnswers = new ScaleTransition[2];
		scaleTransitionForAnswerIconsFor2TextAnswers[0] = new ScaleTransition(Duration.millis(100), answerIconsFor2TextAnswers[0]);
		scaleTransitionForAnswerIconsFor2TextAnswers[1] = new ScaleTransition(Duration.millis(100), answerIconsFor2TextAnswers[1]);

		scaleTransitionForAnswerIconsFor4TextAnswers = new ScaleTransition[4];
		scaleTransitionForAnswerIconsFor4TextAnswers[0] = new ScaleTransition(Duration.millis(100), answerIconsFor4TextAnswers[0]);
		scaleTransitionForAnswerIconsFor4TextAnswers[1] = new ScaleTransition(Duration.millis(100), answerIconsFor4TextAnswers[1]);
		scaleTransitionForAnswerIconsFor4TextAnswers[2] = new ScaleTransition(Duration.millis(100), answerIconsFor4TextAnswers[2]);
		scaleTransitionForAnswerIconsFor4TextAnswers[3] = new ScaleTransition(Duration.millis(100), answerIconsFor4TextAnswers[3]);

		scaleTransitionForAnswerIconsFor4ImageAnswers = new ScaleTransition[4];
		scaleTransitionForAnswerIconsFor4ImageAnswers[0] = new ScaleTransition(Duration.millis(150), imageViewFor4ImageAnswers[0].getAnswerIcon());
		scaleTransitionForAnswerIconsFor4ImageAnswers[1] = new ScaleTransition(Duration.millis(150), imageViewFor4ImageAnswers[1].getAnswerIcon());
		scaleTransitionForAnswerIconsFor4ImageAnswers[2] = new ScaleTransition(Duration.millis(150), imageViewFor4ImageAnswers[2].getAnswerIcon());
		scaleTransitionForAnswerIconsFor4ImageAnswers[3] = new ScaleTransition(Duration.millis(150), imageViewFor4ImageAnswers[3].getAnswerIcon());

		translateTransitionForTitleImageForFinishedGame = new TranslateTransition(Duration.millis(200), titleImageForFinishedGame);

		translateTransitionForTitleLabelForFinishedGame = new TranslateTransition(Duration.millis(200), titleLabelForFinishedGame);

		scaleTransitionForHBoxForLives = new ScaleTransition(Duration.millis(200), hBoxForLives);

		scaleTransitionForTitleLabelForFinishedGame = new ScaleTransition(Duration.millis(200), titleLabelForFinishedGame);

		translateTransitionFoVBoxForPausedGame = new TranslateTransition(Duration.millis(200), vBoxForPausedGame);

		translateTransitionForVBoxForSound = new TranslateTransition(Duration.millis(200), vBoxForSound);

		scaleTransitionForPauseGameIcon = new ScaleTransition(Duration.millis(200), pauseGameIcon);

		scaleTransitionForProgressBarForCountdown = new ScaleTransition(Duration.millis(200), progressBarForCountdown);

		scaleTransitionForVBoxForNextQuestion = new ScaleTransition(Duration.millis(200), vBoxForNextQuestionButtonAndProgressBar);

		scaleTransitionForTextForQuestionNumber = new ScaleTransition(Duration.millis(200), textForQuestionNumber);

		scaleTransitionForPopUpMessage = new ScaleTransition(Duration.millis(100), popUpMessage);

		scaleTransitionForTextForTimePassed = new ScaleTransition(Duration.millis(200), textForTimePassed);

		scaleTransitionForTextForScore = new ScaleTransition(Duration.millis(200), textForScore);

		scaleTransitionForTextForCountdown = new ScaleTransition(Duration.millis(150), textForCountdown);

		scaleTransitionForTextForQuestion = new ScaleTransition(Duration.millis(200), textForQuestion);

		scaleTransitionForGridPaneFor4TextAnswers = new ScaleTransition(Duration.millis(200), gridPaneFor4TextAnswers);

		scaleTransitionForGridPaneFor2TextAnswers = new ScaleTransition(Duration.millis(200), gridPaneFor2TextAnswers);

		scaleTransitionForGridPaneFor4ImageAnswers = new ScaleTransition(Duration.millis(200), gridPaneFor4ImageAnswers);

		scaleTransitionForQuestionImage = new ScaleTransition(Duration.millis(200), imageViewForQuestionImage);

		scaleTransitionForTextForResultsDuration = new ScaleTransition(Duration.millis(200), textForResultsDuration);

		scaleTransitionForTextForResults = new ScaleTransition(Duration.millis(200), textForResults);
		scaleTransitionForTextForResults2 = new ScaleTransition(Duration.millis(200), textForResults2);

		scaleTransitionForRestartGameButton = new ScaleTransition(Duration.millis(200), restartGameFromFinishedScreenButton);

		scaleTransitionForReturnToWelcomeScreenButton = new ScaleTransition(Duration.millis(200), returnToWelcomeScreenFromFinishedGameScreenButton);

		translateTransitionForWoodPanelFor1IconImage = new TranslateTransition(Duration.millis(200), woodPanelFor1IconImage);

		scaleTransitionForSoundIcon = new ScaleTransition(Duration.millis(200), soundButton);

		timelineToShow2TextAnswers = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				getAudioStuff().playPopUpSound();
				sequentialTransitionsFor2TextAnswers[0].playFromStart();
			}),
			new KeyFrame(Duration.millis(150), e ->
			{
				getAudioStuff().playPopUpSound();
				sequentialTransitionsFor2TextAnswers[1].playFromStart();
			}),
			new KeyFrame(Duration.millis(350), e ->
			{
				startTimers();
				pauseGameIcon.setDisable(false);
			}));

		timelineToShow4TextAnswers = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				getAudioStuff().playPopUpSound();
				sequentialTransitionsFor4TextAnswers[0].playFromStart();
			}),
			new KeyFrame(Duration.millis(150), e ->
			{
				getAudioStuff().playPopUpSound();
				sequentialTransitionsFor4TextAnswers[1].playFromStart();
			}),
			new KeyFrame(Duration.millis(300), e ->
			{
				getAudioStuff().playPopUpSound();
				sequentialTransitionsFor4TextAnswers[2].playFromStart();
			}),
			new KeyFrame(Duration.millis(450), e ->
			{
				getAudioStuff().playPopUpSound();
				sequentialTransitionsFor4TextAnswers[3].playFromStart();
			}),
			new KeyFrame(Duration.millis(650), e ->
			{
				startTimers();
				pauseGameIcon.setDisable(false);
			}));

		timelineToShow4ImageAnswers = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				getAudioStuff().playPopUpSound();
				sequentialTransitionsFor4ImageAnswers[0].playFromStart();
			}),
			new KeyFrame(Duration.millis(150), e ->
			{
				getAudioStuff().playPopUpSound();
				sequentialTransitionsFor4ImageAnswers[1].playFromStart();
			}),
			new KeyFrame(Duration.millis(300), e ->
			{
				getAudioStuff().playPopUpSound();
				sequentialTransitionsFor4ImageAnswers[2].playFromStart();
			}),
			new KeyFrame(Duration.millis(450), e ->
			{
				getAudioStuff().playPopUpSound();
				sequentialTransitionsFor4ImageAnswers[3].playFromStart();
			}),
			new KeyFrame(Duration.millis(650), e ->
			{
				startTimers();
				pauseGameIcon.setDisable(false);
			}));

		timelineToShowAllStuff = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				getAudioStuff().playSlideSound();

				translateTransitionForWoodPanelFor1IconImage.setToY(0);
				translateTransitionForWoodPanelFor1IconImage.playFromStart();
			}),
			new KeyFrame(Duration.millis(200), e ->
			{
				scaleTransitionForSoundIcon.setToX(1);
				scaleTransitionForSoundIcon.setToY(1);

				scaleTransitionForPauseGameIcon.setToX(1);
				scaleTransitionForPauseGameIcon.setToY(1);
				scaleTransitionForVBoxForNextQuestion.setToX(1);
				scaleTransitionForVBoxForNextQuestion.setToY(1);
				scaleTransitionForTextForQuestionNumber.setToX(1);
				scaleTransitionForTextForQuestionNumber.setToY(1);
				scaleTransitionForTextForScore.setToX(1);
				scaleTransitionForTextForScore.setToY(1);

				getAudioStuff().playPopUpSound();
				scaleTransitionForPauseGameIcon.playFromStart();
				scaleTransitionForTextForQuestionNumber.playFromStart();
				scaleTransitionForTextForScore.playFromStart();
				scaleTransitionForVBoxForNextQuestion.playFromStart();
				scaleTransitionForSoundIcon.playFromStart();

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

				getAudioStuff().playSlideSound();
				translateTransitionFoVBoxForPausedGame.playFromStart();
			}),
			new KeyFrame(Duration.millis(200), e ->
			{
				scaleTransitionForTextForQuestion.setToX(0);
				scaleTransitionForTextForQuestion.setToY(0);
				scaleTransitionForTextForQuestion.playFromStart();

				getAudioStuff().playMinimizeSound();

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
				else if(gridPaneFor4ImageAnswers.isVisible())
				{
					scaleTransitionForGridPaneFor4ImageAnswers.setToX(0);
					scaleTransitionForGridPaneFor4ImageAnswers.setToY(0);
					scaleTransitionForGridPaneFor4ImageAnswers.playFromStart();
				}
			}),
			new KeyFrame(Duration.millis(400), e ->
			{
				scaleTransitionForSoundIcon.setToX(0);
				scaleTransitionForSoundIcon.setToY(0);

				scaleTransitionForPauseGameIcon.setToX(0);
				scaleTransitionForPauseGameIcon.setToY(0);
				scaleTransitionForVBoxForNextQuestion.setToX(0);
				scaleTransitionForVBoxForNextQuestion.setToY(0);
				scaleTransitionForTextForQuestionNumber.setToX(0);
				scaleTransitionForTextForQuestionNumber.setToY(0);
				scaleTransitionForTextForScore.setToX(0);
				scaleTransitionForTextForScore.setToY(0);

				getAudioStuff().playMinimizeSound();
				scaleTransitionForPauseGameIcon.playFromStart();
				scaleTransitionForVBoxForNextQuestion.playFromStart();
				scaleTransitionForTextForQuestionNumber.playFromStart();
				scaleTransitionForTextForScore.playFromStart();
				scaleTransitionForSoundIcon.playFromStart();

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
					rotateTransitionForTextForCombo.setDuration(Duration.millis(200));
					rotateTransitionForTextForCombo.setCycleCount(1);
					rotateTransitionForTextForCombo.setAutoReverse(false);
					scaleTransitionForTextForCombo.setDuration(Duration.millis(200));
					scaleTransitionForTextForCombo.setCycleCount(1);
					scaleTransitionForTextForCombo.setAutoReverse(false);
					scaleTransitionForTextForCombo.setFromX(textForCombo.getScaleX());
					scaleTransitionForTextForCombo.setFromY(textForCombo.getScaleY());
					scaleTransitionForTextForCombo.setToX(0);
					scaleTransitionForTextForCombo.setToY(0);
					parallelTransitionForTextForCombo.playFromStart();
				}
			}),
			new KeyFrame(Duration.millis(800), e ->
			{
				getAudioStuff().playSlideSound();

				translateTransitionForWoodPanelFor1IconImage.setToY(-1.0 * (woodPanelFor1IconImage.getLayoutY() + woodPanelFor1IconImage.getBoundsInParent().getHeight()));
				translateTransitionForWoodPanelFor1IconImage.playFromStart();

				if(vBoxForSound.isVisible())
				{
					setCorrectSoundIcon(false);

					translateTransitionForVBoxForSound.setToY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
					translateTransitionForVBoxForSound.playFromStart();
				}
			}),
			new KeyFrame(Duration.millis(1000), e ->
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

				getAudioStuff().playMinimizeSound();

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
				else if(gridPaneFor4ImageAnswers.isVisible())
				{
					scaleTransitionForGridPaneFor4ImageAnswers.setToX(0);
					scaleTransitionForGridPaneFor4ImageAnswers.setToY(0);
					scaleTransitionForGridPaneFor4ImageAnswers.playFromStart();
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
			new KeyFrame(Duration.millis(200), e ->
			{
				scaleTransitionForPauseGameIcon.setToX(0);
				scaleTransitionForPauseGameIcon.setToY(0);
				scaleTransitionForVBoxForNextQuestion.setToX(0);
				scaleTransitionForVBoxForNextQuestion.setToY(0);
				scaleTransitionForTextForQuestionNumber.setToX(0);
				scaleTransitionForTextForQuestionNumber.setToY(0);
				scaleTransitionForTextForScore.setToX(0);
				scaleTransitionForTextForScore.setToY(0);

				getAudioStuff().playMinimizeSound();
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
			new KeyFrame(Duration.millis(400), e ->
			{
				titleImageForFinishedGame.setVisible(true);

				if(vBoxForSound.isVisible())
				{
					titleImageForFinishedGame.setTranslateX(-0.0364 * mainScene.getWidth());
					titleLabelForFinishedGame.setTranslateX(-0.0364 * mainScene.getWidth());
				}
				else
				{
					titleImageForFinishedGame.setTranslateX(0);
					titleLabelForFinishedGame.setTranslateX(0);
				}

				translateTransitionForTitleImageForFinishedGame.setToX(titleLabelForFinishedGame.getTranslateX());
				translateTransitionForTitleImageForFinishedGame.setToY(0);

				getAudioStuff().playSlideSound();
				translateTransitionForTitleImageForFinishedGame.playFromStart();
			}),
			new KeyFrame(Duration.millis(600), e ->
			{
				titleLabelForFinishedGame.setVisible(true);

				scaleTransitionForTitleLabelForFinishedGame.setCycleCount(1);
				scaleTransitionForTitleLabelForFinishedGame.setAutoReverse(false);
				scaleTransitionForTitleLabelForFinishedGame.setDuration(Duration.millis(200));
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

				getAudioStuff().playPopUpSound();
				scaleTransitionForTitleLabelForFinishedGame.playFromStart();
			}),
			new KeyFrame(Duration.millis(800), e ->
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

				getAudioStuff().playPopUpSound();
				scaleTransitionForTextForResults.playFromStart();
				scaleTransitionForTextForResults2.playFromStart();
			}),
			new KeyFrame(Duration.millis(1000), e ->
			{
				if(isGameWon()) getAudioStuff().playGameWonSound();
				else getAudioStuff().playGameLostSound();

				restartGameFromFinishedScreenButton.setVisible(true);
				returnToWelcomeScreenFromFinishedGameScreenButton.setVisible(true);

				scaleTransitionForRestartGameButton.setToX(1);
				scaleTransitionForRestartGameButton.setToY(1);

				scaleTransitionForReturnToWelcomeScreenButton.setToX(1);
				scaleTransitionForReturnToWelcomeScreenButton.setToY(1);

				getAudioStuff().playPopUpSound();
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

				getAudioStuff().playMinimizeSound();
				scaleTransitionForRestartGameButton.playFromStart();
				scaleTransitionForReturnToWelcomeScreenButton.playFromStart();
			}),
			new KeyFrame(Duration.millis(200), e ->
			{
				if(gameMode != GAMEMODE.TIME_ATTACK_GAMEMODE)
				{
					scaleTransitionForTextForResultsDuration.setToX(0);

					scaleTransitionForTextForResultsDuration.playFromStart();
				}

				scaleTransitionForTextForResults.setToX(0);
				scaleTransitionForTextForResults2.setToX(0);

				getAudioStuff().playMinimizeSound();
				scaleTransitionForTextForResults.playFromStart();
				scaleTransitionForTextForResults2.playFromStart();
			}),
			new KeyFrame(Duration.millis(400), e ->
			{
				scaleTransitionForSoundIcon.setToX(0);
				scaleTransitionForSoundIcon.setToY(0);

				scaleTransitionForTitleLabelForFinishedGame.setCycleCount(1);
				scaleTransitionForTitleLabelForFinishedGame.setAutoReverse(false);
				scaleTransitionForTitleLabelForFinishedGame.setDuration(Duration.millis(200));
				scaleTransitionForTitleLabelForFinishedGame.setFromX(titleLabelForFinishedGame.getScaleX());
				scaleTransitionForTitleLabelForFinishedGame.setFromY(titleLabelForFinishedGame.getScaleY());
				scaleTransitionForTitleLabelForFinishedGame.setToX(0);
				scaleTransitionForTitleLabelForFinishedGame.setToY(0);
				scaleTransitionForTitleLabelForFinishedGame.setOnFinished(ev -> {});

				getAudioStuff().playMinimizeSound();
				scaleTransitionForTitleLabelForFinishedGame.playFromStart();
				scaleTransitionForSoundIcon.playFromStart();
			}),
			new KeyFrame(Duration.millis(600), e ->
			{
				getAudioStuff().playSlideSound();

				translateTransitionForTitleImageForFinishedGame.setToX(titleLabelForFinishedGame.getTranslateX());
				translateTransitionForTitleImageForFinishedGame.setToY(-1.0 * (titleImageForFinishedGame.getLayoutY() + titleImageForFinishedGame.getBoundsInParent().getHeight() + 20));
				translateTransitionForWoodPanelFor1IconImage.setToY(-1.0 * (woodPanelFor1IconImage.getLayoutY() + woodPanelFor1IconImage.getBoundsInParent().getHeight()));

				translateTransitionForWoodPanelFor1IconImage.playFromStart();
				translateTransitionForTitleImageForFinishedGame.playFromStart();

				if(vBoxForSound.isVisible())
				{
					setCorrectSoundIcon(false);

					translateTransitionForVBoxForSound.setToY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
					translateTransitionForVBoxForSound.playFromStart();
				}
			}),
			new KeyFrame(Duration.millis(800), e ->
			{
				vBoxForSound.setVisible(false);

				welcomeScreenImage.setVisible(true);
				fadeTransitionForWorldMapImage.playFromStart();
			}));

		timelineToShowSoundOptions = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					soundButton.setDisable(true);

					if(isFinishScreenShown)
					{
						translateTransitionForTitleImageForFinishedGame.setToX(-0.0364 * mainScene.getWidth());
						translateTransitionForTitleLabelForFinishedGame.setToX(-0.0364 * mainScene.getWidth());

						getAudioStuff().playSlideSound();
						translateTransitionForTitleImageForFinishedGame.playFromStart();
						translateTransitionForTitleLabelForFinishedGame.playFromStart();
					}
					else
					{
						vBoxForSound.setVisible(true);
						translateTransitionForVBoxForSound.setToY(0);

						getAudioStuff().playSlideSound();
						translateTransitionForVBoxForSound.playFromStart();
					}
				}),
				new KeyFrame(Duration.millis(100), e ->
				{
					if(isFinishScreenShown)
					{
						vBoxForSound.setVisible(true);
						translateTransitionForVBoxForSound.setToY(0);

						translateTransitionForVBoxForSound.playFromStart();
					}
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
					if(isFinishScreenShown)
					{
						translateTransitionForTitleImageForFinishedGame.setToX(0);
						translateTransitionForTitleLabelForFinishedGame.setToX(0);

						translateTransitionForTitleImageForFinishedGame.playFromStart();
						translateTransitionForTitleLabelForFinishedGame.playFromStart();
					}
				}),
				new KeyFrame(Duration.millis(300), e ->
				{
					soundButton.setDisable(false);
					vBoxForSound.setVisible(false);
				}));
	}

	protected void setupAdvancedAnimations()
	{

	}

	protected void showScreen()
	{
		super.showScreen();

		if(animationsUsed != ANIMATIONS.NO)
			timelineToShowAllStuff.playFromStart();
	}

	private void initializeGame()
	{
		currentGame = new Game();
		gameStartedTime = LocalDateTime.now();

		isFinishScreenShown = false;

		//SETUP VARIABLES BASED ON GAME MODE-------------------------------------------------------------
		if(availableCategories == null) availableCategories = new ArrayList<>();
		else availableCategories.clear();

		for (int i = 0; i < NUM_ALL_CATEGORIES; i++)
			if (isCategorySelected(i)) availableCategories.add(i);

		Integer[] categories = availableCategories.toArray(new Integer[0]);
		currentGame.setQuestionCategories(categories);

		questionMaker.setAvailableQuestions();

		if(gameMode == GAMEMODE.ENDLESS_GAMEMODE)
		{
			setRemainingLivesForEndless(getLivesForEndless());

			hBoxForLives.setVisible(true);

			hBoxForLives.getChildren().clear();

			if(getLivesForEndless() == 1)
			{
				imageViewForLives[0].setImage(images.get(Images.HEART_SMALL));
				hBoxForLives.getChildren().add(imageViewForLives[0]);
			}
			else if(getLivesForEndless() == 3)
			{
				for(int i = 0; i < 5; i++) imageViewForLives[i].setImage(images.get(Images.HEART_SMALL));
				hBoxForLives.getChildren().addAll(imageViewForLives[0], imageViewForLives[1], imageViewForLives[2]);
			}
			else
			{
				for(int i = 0; i < 5; i++) imageViewForLives[i].setImage(images.get(Images.HEART_SMALL));
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
			progressBarForCountdown.setStyle("-fx-accent: " + GREEN_COLOR + ";");
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

	protected void setInitialStateForAllNodes()
	{
		//SETUP IMAGES-------------------------------------------------------------
		if(woodenFrameImage.getImage() == null || !woodenFrameImage.getImage().equals(images.get(Images.FRAME)))
		{
			woodenFrameImage.setImage(images.get(Images.FRAME));
			titleImageForFinishedGame.setImage(images.get(Images.EMPTY_WOOD_BACKGROUND_SMALL_ROPE));
			woodPanelFor1IconImage.setImage(images.get(Images.EMPTY_WOOD_BACKGROUND_1_ICON));
			welcomeScreenImage.setImage(images.get(Images.CHALKBOARD_BACKGROUND));
			worldMapBackground.setImage(images.get(Images.WORLD_MAP));
			nextQuestionButton.setImage(images.get(Images.BACK_ARROW));
		}

		initializeGame();

		setupTimers();

		//SETUP POSITIONS BASED ON ANIMATIONS-------------------------------------------------------------
		if(animationsUsed != ANIMATIONS.NO)
		{
			woodPanelFor1IconImage.setTranslateY(-1.0 * (woodPanelFor1IconImage.getLayoutY() + woodPanelFor1IconImage.getBoundsInParent().getHeight()));

			soundButton.setScaleX(0);
			soundButton.setScaleY(0);

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

			gridPaneFor4ImageAnswers.setScaleX(0);
			gridPaneFor4ImageAnswers.setScaleY(0);

			imageViewFor4ImageAnswers[0].setScaleX(0);
			imageViewFor4ImageAnswers[0].setScaleY(0);
			imageViewFor4ImageAnswers[1].setScaleX(0);
			imageViewFor4ImageAnswers[1].setScaleY(0);
			imageViewFor4ImageAnswers[2].setScaleX(0);
			imageViewFor4ImageAnswers[2].setScaleY(0);
			imageViewFor4ImageAnswers[3].setScaleX(0);
			imageViewFor4ImageAnswers[3].setScaleY(0);
			
			imageViewFor4ImageAnswers[0].getAnswerIcon().setScaleX(0);
			imageViewFor4ImageAnswers[0].getAnswerIcon().setScaleY(0);
			imageViewFor4ImageAnswers[1].getAnswerIcon().setScaleX(0);
			imageViewFor4ImageAnswers[1].getAnswerIcon().setScaleY(0);
			imageViewFor4ImageAnswers[2].getAnswerIcon().setScaleX(0);
			imageViewFor4ImageAnswers[2].getAnswerIcon().setScaleY(0);
			imageViewFor4ImageAnswers[3].getAnswerIcon().setScaleX(0);
			imageViewFor4ImageAnswers[3].getAnswerIcon().setScaleY(0);

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
			woodPanelFor1IconImage.setTranslateY(0);

			soundButton.setScaleX(1);
			soundButton.setScaleY(1);

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

			gridPaneFor4ImageAnswers.setScaleX(1);
			gridPaneFor4ImageAnswers.setScaleY(1);

			imageViewFor4ImageAnswers[0].setScaleX(1);
			imageViewFor4ImageAnswers[0].setScaleY(1);
			imageViewFor4ImageAnswers[1].setScaleX(1);
			imageViewFor4ImageAnswers[1].setScaleY(1);
			imageViewFor4ImageAnswers[2].setScaleX(1);
			imageViewFor4ImageAnswers[2].setScaleY(1);
			imageViewFor4ImageAnswers[3].setScaleX(1);
			imageViewFor4ImageAnswers[3].setScaleY(1);
			
			imageViewFor4ImageAnswers[0].getAnswerIcon().setScaleX(1);
			imageViewFor4ImageAnswers[0].getAnswerIcon().setScaleY(1);
			imageViewFor4ImageAnswers[1].getAnswerIcon().setScaleX(1);
			imageViewFor4ImageAnswers[1].getAnswerIcon().setScaleY(1);
			imageViewFor4ImageAnswers[2].getAnswerIcon().setScaleX(1);
			imageViewFor4ImageAnswers[2].getAnswerIcon().setScaleY(1);
			imageViewFor4ImageAnswers[3].getAnswerIcon().setScaleX(1);
			imageViewFor4ImageAnswers[3].getAnswerIcon().setScaleY(1);

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
		gridPaneFor4ImageAnswers.setVisible(false);

		pauseGameIcon.setImage(images.get(Images.PAUSE));

		removeBlurEffect();

		vBoxForPausedGame.setTranslateY(-1.0 * (vBoxForPausedGame.getLayoutY() + vBoxForPausedGame.getPrefHeight() + 20));
		vBoxForPausedGame.setVisible(false);
		vBoxForPausedGame.setDisable(true);

		vBoxForSound.setTranslateY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
		vBoxForSound.setVisible(false);

		setCorrectSoundIcon(false);

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

		soundButton.getTooltip().setText(languageResourceBundle.getString("soundOptionsTooltip"));

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
		if(gridPaneFor4ImageAnswers.isVisible()) gridPaneFor4ImageAnswers.setEffect(boxBlurForNodes);
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
		pauseGameIcon.setEffect(dropShadow);
		vBoxForNextQuestionButtonAndProgressBar.setEffect(dropShadow);
		hBoxForLives.setEffect(dropShadow);
		progressBarForCountdown.setEffect(dropShadow);
		imageViewForQuestionImage.setEffect(dropShadow);
		gridPaneFor4ImageAnswers.setEffect(null);
		gridPaneFor4TextAnswers.setEffect(dropShadow);
		gridPaneFor2TextAnswers.setEffect(dropShadow);
		textForCountdown.setEffect(null);
		numberOfQuestionAnimated.setEffect(dropShadow);
		imageViewForLargeHeart.setEffect(dropShadow);

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
			rotateTransitionForTextForCombo.setDuration(Duration.millis(100));
			rotateTransitionForTextForCombo.setCycleCount(1);
			rotateTransitionForTextForCombo.setAutoReverse(false);
			scaleTransitionForTextForCombo.setDuration(Duration.millis(100));
			scaleTransitionForTextForCombo.setCycleCount(1);
			scaleTransitionForTextForCombo.setAutoReverse(false);
			scaleTransitionForTextForCombo.setFromX(textForCombo.getScaleX());
			scaleTransitionForTextForCombo.setFromY(textForCombo.getScaleY());
			scaleTransitionForTextForCombo.setToX(1);
			scaleTransitionForTextForCombo.setToY(1);
			parallelTransitionForTextForCombo.setOnFinished(e ->
			{
				parallelTransitionForTextForCombo.setOnFinished(ev -> {});

				startTextForComboAnimation(combo);
			});
			parallelTransitionForTextForCombo.playFromStart();
		}
		else
		{
			rotateTransitionForTextForCombo.setCycleCount(Animation.INDEFINITE);
			rotateTransitionForTextForCombo.setAutoReverse(true);
			scaleTransitionForTextForCombo.setCycleCount(Animation.INDEFINITE);
			scaleTransitionForTextForCombo.setAutoReverse(true);
			scaleTransitionForTextForCombo.setFromX(1);
			scaleTransitionForTextForCombo.setFromY(1);

			if(combo < 5)
			{
				rotateTransitionForTextForCombo.setDuration(Duration.millis(500));
				scaleTransitionForTextForCombo.setDuration(Duration.millis(1200));
				rotateTransitionForTextForCombo.setFromAngle(-2);
				rotateTransitionForTextForCombo.setToAngle(2);
				scaleTransitionForTextForCombo.setToX(1.2);
				scaleTransitionForTextForCombo.setToY(1.2);
			}
			else if(combo < 10)
			{
				rotateTransitionForTextForCombo.setDuration(Duration.millis(400));
				scaleTransitionForTextForCombo.setDuration(Duration.millis(1000));
				rotateTransitionForTextForCombo.setFromAngle(-4);
				rotateTransitionForTextForCombo.setToAngle(4);
				scaleTransitionForTextForCombo.setToX(1.4);
				scaleTransitionForTextForCombo.setToY(1.4);
			}
			else if(combo < 15)
			{
				rotateTransitionForTextForCombo.setDuration(Duration.millis(300));
				scaleTransitionForTextForCombo.setDuration(Duration.millis(800));
				rotateTransitionForTextForCombo.setFromAngle(-6);
				rotateTransitionForTextForCombo.setToAngle(6);
				scaleTransitionForTextForCombo.setToX(1.6);
				scaleTransitionForTextForCombo.setToY(1.6);
			}
			else if(combo < 20)
			{
				rotateTransitionForTextForCombo.setDuration(Duration.millis(200));
				scaleTransitionForTextForCombo.setDuration(Duration.millis(600));
				rotateTransitionForTextForCombo.setFromAngle(-8);
				rotateTransitionForTextForCombo.setToAngle(8);
				scaleTransitionForTextForCombo.setToX(1.8);
				scaleTransitionForTextForCombo.setToY(1.8);
			}
			else
			{
				rotateTransitionForTextForCombo.setDuration(Duration.millis(100));
				scaleTransitionForTextForCombo.setDuration(Duration.millis(400));
				rotateTransitionForTextForCombo.setFromAngle(-10);
				rotateTransitionForTextForCombo.setToAngle(10);
				scaleTransitionForTextForCombo.setToX(2.0);
				scaleTransitionForTextForCombo.setToY(2.0);
			}
			parallelTransitionForTextForCombo.playFromStart();
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

		getAudioStuff().playClockTickingSound();

		timelineForQuestionCountdown.playFromStart();
		timelineToCountSecondsForQuestionCountdown.playFromStart();
	}

	private void stopQuestionCountdown()
	{
		timelineToCountSecondsForQuestionCountdown.stop();
		timelineForQuestionCountdown.stop();

		progressBarForCountdown.setProgress(0);

		getAudioStuff().stopClockTickingSound();

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
			imageViewFor4ImageAnswers[0].getAnswerIcon().setImage(null);
			imageViewFor4ImageAnswers[1].getAnswerIcon().setImage(null);
			imageViewFor4ImageAnswers[2].getAnswerIcon().setImage(null);
			imageViewFor4ImageAnswers[3].getAnswerIcon().setImage(null);

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
		radioButtonsFor2TextAnswers[0].getStyleClass().remove("green-background-color");
		radioButtonsFor2TextAnswers[1].getStyleClass().remove("green-background-color");

		toggleGroupFor2PossibleAnswers.selectToggle(null);
	}

	private void restoreGridPaneFor4TextAnswers()
	{
		for(int i = 0; i < radioButtonsFor4TextAnswers.length; i++)
			radioButtonsFor4TextAnswers[i].getStyleClass().remove("green-background-color");

		toggleGroupFor4PossibleAnswers.selectToggle(null);
	}

	private void restoreStackPaneFor4ImageAnswers()
	{
		for(int i = 0; i < imageViewFor4ImageAnswers.length; i++)
			imageViewFor4ImageAnswers[i].getStyleClass().remove("green-background-color");
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

						getAudioStuff().playMaximizeSound();
						scaleTransitionForGridPaneFor2TextAnswers.playFromStart();
					});
					getAudioStuff().playMinimizeSound();
					scaleTransitionForGridPaneFor4TextAnswers.playFromStart();
				}
				else if(gridPaneFor4ImageAnswers.isVisible())
				{
					scaleTransitionForGridPaneFor4ImageAnswers.setToX(0);
					scaleTransitionForGridPaneFor4ImageAnswers.setToY(0);

					scaleTransitionForGridPaneFor4ImageAnswers.setOnFinished(e ->
					{
						scaleTransitionForGridPaneFor4ImageAnswers.setOnFinished(ev -> {});

						gridPaneFor4ImageAnswers.setVisible(false);
						gridPaneFor2TextAnswers.setVisible(true);

						scaleTransitionForGridPaneFor2TextAnswers.setToX(1);
						scaleTransitionForGridPaneFor2TextAnswers.setToY(1);

						scaleTransitionForGridPaneFor2TextAnswers.setOnFinished(eve ->
						{
							scaleTransitionForGridPaneFor2TextAnswers.setOnFinished(even ->{});

							timelineToShow2TextAnswers.playFromStart();
						});

						getAudioStuff().playMaximizeSound();
						scaleTransitionForGridPaneFor2TextAnswers.playFromStart();
					});
					getAudioStuff().playMinimizeSound();
					scaleTransitionForGridPaneFor4ImageAnswers.playFromStart();
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

					getAudioStuff().playMaximizeSound();
					scaleTransitionForGridPaneFor2TextAnswers.playFromStart();
				}
			}
			else
			{
				gridPaneFor2TextAnswers.setVisible(true);
				gridPaneFor4TextAnswers.setVisible(false);
				gridPaneFor4ImageAnswers.setVisible(false);

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

						getAudioStuff().playMaximizeSound();
						scaleTransitionForGridPaneFor4TextAnswers.playFromStart();
					});
					getAudioStuff().playMinimizeSound();
					scaleTransitionForGridPaneFor2TextAnswers.playFromStart();
				}
				else if(gridPaneFor4ImageAnswers.isVisible())
				{
					scaleTransitionForGridPaneFor4ImageAnswers.setToX(0);
					scaleTransitionForGridPaneFor4ImageAnswers.setToY(0);

					scaleTransitionForGridPaneFor4ImageAnswers.setOnFinished(e ->
					{
						scaleTransitionForGridPaneFor4ImageAnswers.setOnFinished(ev -> {});
						
						gridPaneFor4ImageAnswers.setVisible(false);
						gridPaneFor4TextAnswers.setVisible(true);

						scaleTransitionForGridPaneFor4TextAnswers.setToX(1);
						scaleTransitionForGridPaneFor4TextAnswers.setToY(1);

						scaleTransitionForGridPaneFor4TextAnswers.setOnFinished(eve ->
						{
							scaleTransitionForGridPaneFor4TextAnswers.setOnFinished(even ->{});

							timelineToShow4TextAnswers.playFromStart();
						});

						getAudioStuff().playMaximizeSound();
						scaleTransitionForGridPaneFor4TextAnswers.playFromStart();
					});
					getAudioStuff().playMinimizeSound();
					scaleTransitionForGridPaneFor4ImageAnswers.playFromStart();
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

					getAudioStuff().playMaximizeSound();
					scaleTransitionForGridPaneFor4TextAnswers.playFromStart();
				}
			}
			else
			{
				gridPaneFor2TextAnswers.setVisible(false);
				gridPaneFor4TextAnswers.setVisible(true);
				gridPaneFor4ImageAnswers.setVisible(false);

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
		if(!gridPaneFor4ImageAnswers.isVisible())
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
						gridPaneFor4ImageAnswers.setVisible(true);

						scaleTransitionForGridPaneFor4ImageAnswers.setToX(1);
						scaleTransitionForGridPaneFor4ImageAnswers.setToY(1);

						scaleTransitionForGridPaneFor4ImageAnswers.setOnFinished(eve ->
						{
							scaleTransitionForGridPaneFor4ImageAnswers.setOnFinished(even ->{});

							timelineToShow4ImageAnswers.playFromStart();
						});

						getAudioStuff().playMaximizeSound();
						scaleTransitionForGridPaneFor4ImageAnswers.playFromStart();
					});
					getAudioStuff().playMinimizeSound();
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
						gridPaneFor4ImageAnswers.setVisible(true);

						scaleTransitionForGridPaneFor4ImageAnswers.setToX(1);
						scaleTransitionForGridPaneFor4ImageAnswers.setToY(1);

						scaleTransitionForGridPaneFor4ImageAnswers.setOnFinished(eve ->
						{
							scaleTransitionForGridPaneFor4ImageAnswers.setOnFinished(even ->{});

							timelineToShow4ImageAnswers.playFromStart();
						});

						getAudioStuff().playMaximizeSound();
						scaleTransitionForGridPaneFor4ImageAnswers.playFromStart();
					});
					getAudioStuff().playMinimizeSound();
					scaleTransitionForGridPaneFor4TextAnswers.playFromStart();
				}
				else
				{
					gridPaneFor4ImageAnswers.setVisible(true);

					scaleTransitionForGridPaneFor4ImageAnswers.setToX(1);
					scaleTransitionForGridPaneFor4ImageAnswers.setToY(1);

					scaleTransitionForGridPaneFor4ImageAnswers.setOnFinished(eve ->
					{
						scaleTransitionForGridPaneFor4ImageAnswers.setOnFinished(even ->{});

						timelineToShow4ImageAnswers.playFromStart();
					});

					getAudioStuff().playMaximizeSound();
					scaleTransitionForGridPaneFor4ImageAnswers.playFromStart();
				}
			}
			else
			{
				gridPaneFor2TextAnswers.setVisible(false);
				gridPaneFor4TextAnswers.setVisible(false);
				gridPaneFor4ImageAnswers.setVisible(true);

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
			parallelTransitionForTextForCombo.pause();

			translateTransitionFoVBoxForPausedGame.setToY(0);
			translateTransitionFoVBoxForPausedGame.setOnFinished(ev ->
			{
				translateTransitionFoVBoxForPausedGame.setOnFinished(eve ->{});
				vBoxForPausedGame.setDisable(false);
			});
			getAudioStuff().playSlideSound();
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

		isFinishScreenShown = true;

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
			gridPaneFor4ImageAnswers.setVisible(false);
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

			if(isGameWon()) getAudioStuff().playGameWonSound();
			else getAudioStuff().playGameLostSound();
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
				if(getCorrectAnswers() == 0) return String.format(languageResourceBundle.getString("finishedTimeAttack0"), getCurrentQuestionNumber());
				else if(getCorrectAnswers() == 1) return String.format(languageResourceBundle.getString("finishedTimeAttack1"), getCurrentQuestionNumber(), getScorePoints());
				else return String.format(languageResourceBundle.getString("finishedTimeAttack2"), getCorrectAnswers(), getCurrentQuestionNumber(), getScorePoints());
			}
			else if(getDurationInMinutesForTimeAttack() == 1 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 12 ||
			        getDurationInMinutesForTimeAttack() == 1 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 8 ||
			        getDurationInMinutesForTimeAttack() == 2 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 24 ||
			        getDurationInMinutesForTimeAttack() == 2 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 15 ||
			        getDurationInMinutesForTimeAttack() == 5 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 50 ||
			        getDurationInMinutesForTimeAttack() == 5 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 30 ||
			        getDurationInMinutesForTimeAttack() == 10 && getDifficultyLevel() == DIFFICULTY.EASY && getCorrectAnswers() < 100 ||
			        getDurationInMinutesForTimeAttack() == 10 && getDifficultyLevel() == DIFFICULTY.DIFFICULT && getCorrectAnswers() < 60)
				return String.format(languageResourceBundle.getString("finishedTimeAttack3"), getCorrectAnswers(), getCurrentQuestionNumber(), getScorePoints());
			else return String.format(languageResourceBundle.getString("finishedTimeAttack4"), getCorrectAnswers(), getCurrentQuestionNumber(), getScorePoints());
		}
		else
		{
			if(getCorrectAnswers() == 0)
			{
				if(getCurrentQuestionNumber() == 1) return String.format(languageResourceBundle.getString("finishedEndless0"), getCurrentQuestionNumber());
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

		if(animationsUsed == ANIMATIONS.NO) imageViewForLives[getRemainingLivesForEndless()].setImage(images.get(Images.HEART_LOST_SMALL));
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

				getAudioStuff().playMinimizeSound();
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

				getAudioStuff().playMinimizeSound();
				scaleTransitionFor4TextAnswers[0].playFromStart();
				scaleTransitionFor4TextAnswers[1].playFromStart();
				scaleTransitionFor4TextAnswers[2].playFromStart();
				scaleTransitionFor4TextAnswers[3].playFromStart();

				scaleTransitionForAnswerIconsFor4TextAnswers[0].playFromStart();
				scaleTransitionForAnswerIconsFor4TextAnswers[1].playFromStart();
				scaleTransitionForAnswerIconsFor4TextAnswers[2].playFromStart();
				scaleTransitionForAnswerIconsFor4TextAnswers[3].playFromStart();

			}
			else if (gridPaneFor4ImageAnswers.isVisible())
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

				getAudioStuff().playMinimizeSound();
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
			else if (gridPaneFor4ImageAnswers.isVisible())
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
				parallelTransitionForTextForCombo.stop();

				rotateTransitionForTextForCombo.setDuration(Duration.millis(200));
				rotateTransitionForTextForCombo.setCycleCount(1);
				rotateTransitionForTextForCombo.setAutoReverse(false);
				scaleTransitionForTextForCombo.setDuration(Duration.millis(200));
				scaleTransitionForTextForCombo.setCycleCount(1);
				scaleTransitionForTextForCombo.setAutoReverse(false);
				scaleTransitionForTextForCombo.setFromX(textForCombo.getScaleX());
				scaleTransitionForTextForCombo.setFromY(textForCombo.getScaleY());
				scaleTransitionForTextForCombo.setToX(0);
				scaleTransitionForTextForCombo.setToY(0);
				
				parallelTransitionForTextForCombo.setOnFinished(e ->
				{
					parallelTransitionForTextForCombo.setOnFinished(ev -> {});

					textForCombo.setText("");
					textForCombo.setScaleX(1);
					textForCombo.setScaleY(1);
				});
				parallelTransitionForTextForCombo.playFromStart();
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

			if(gridPaneFor2TextAnswers.isVisible())
			{
				setFunctional2TextAnswers(false);

				if(source == radioButtonsFor2TextAnswers[corPos])
				{
					correctAnswerReaction();

					if(animationsUsed != ANIMATIONS.NO)
					{
						imageViewForLargeAnswerIcon.setImage(images.get(Images.CORRECT_BIG));
						timelineForLargeAnswerIcon.playFromStart();
					}
					else
					{
						answerIconsFor2TextAnswers[corPos].setImage(images.get(Images.CORRECT_SMALL));
						if(!openPauseMenu) showNextQuestionButton();
					}
				}
				else
				{
					hideComboText();

					if(gameMode == GAMEMODE.ENDLESS_GAMEMODE)
					{
						loseALife();

						answerIconsFor2TextAnswers[1 - corPos].setImage(images.get(Images.INCORRECT_SMALL));

						hintToCorrectAnswer();

						if (animationsUsed != ANIMATIONS.NO)
						{
							scaleTransitionForAnswerIconsFor2TextAnswers[1 - corPos].setToX(1);
							scaleTransitionForAnswerIconsFor2TextAnswers[1 - corPos].setToY(1);

							scaleTransitionForAnswerIconsFor2TextAnswers[1 - corPos].playFromStart();
						}
						if(!openPauseMenu) showNextQuestionButton();
					}
					else
					{
						wrongAnswerReaction();

						if(animationsUsed != ANIMATIONS.NO)
						{
							imageViewForLargeAnswerIcon.setImage(images.get(Images.INCORRECT_BIG));
							timelineForLargeAnswerIcon.playFromStart();
						}
						else
						{
							answerIconsFor2TextAnswers[1 - corPos].setImage(images.get(Images.INCORRECT_SMALL));
							hintToCorrectAnswer();
							if(!openPauseMenu) showNextQuestionButton();
						}
					}
				}
			}
			else if(gridPaneFor4TextAnswers.isVisible())
			{
				setFunctional4TextAnswers(false);

				if(source == radioButtonsFor4TextAnswers[corPos])
				{
					correctAnswerReaction();

					if(animationsUsed != ANIMATIONS.NO)
					{
						imageViewForLargeAnswerIcon.setImage(images.get(Images.CORRECT_BIG));
						timelineForLargeAnswerIcon.playFromStart();
					}
					else
					{
						answerIconsFor4TextAnswers[corPos].setImage(images.get(Images.CORRECT_SMALL));
						if(!openPauseMenu) showNextQuestionButton();
					}
				}
				else
				{
					hideComboText();

					int index = -1;
					for(int i = 0; i < radioButtonsFor4TextAnswers.length; i++) if(radioButtonsFor4TextAnswers[i].isSelected()) index = i;

					if(gameMode == GAMEMODE.ENDLESS_GAMEMODE)
					{
						loseALife();

						answerIconsFor4TextAnswers[index].setImage(images.get(Images.INCORRECT_SMALL));

						hintToCorrectAnswer();

						if (animationsUsed != ANIMATIONS.NO)
						{
							scaleTransitionForAnswerIconsFor4TextAnswers[index].setToX(1);
							scaleTransitionForAnswerIconsFor4TextAnswers[index].setToY(1);

							scaleTransitionForAnswerIconsFor4TextAnswers[index].playFromStart();
						}
						if(!openPauseMenu) showNextQuestionButton();
					}
					else
					{
						wrongAnswerReaction();

						if(animationsUsed != ANIMATIONS.NO)
						{
							imageViewForLargeAnswerIcon.setImage(images.get(Images.INCORRECT_BIG));
							timelineForLargeAnswerIcon.playFromStart();
						}
						else
						{
							answerIconsFor4TextAnswers[index].setImage(images.get(Images.INCORRECT_SMALL));
							hintToCorrectAnswer();
							if(!openPauseMenu) showNextQuestionButton();
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

					imageViewFor4ImageAnswers[corPos].getAnswerIcon().setImage(images.get(Images.CORRECT_BIG));

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

					int index = -1;
					for(int i = 0; i < imageViewFor4ImageAnswers.length; i++) if(source == imageViewFor4ImageAnswers[i]) index = i;
					
					imageViewFor4ImageAnswers[index].getAnswerIcon().setImage(images.get(Images.INCORRECT_BIG));
					if(animationsUsed != ANIMATIONS.NO)
					{
						scaleTransitionForAnswerIconsFor4ImageAnswers[index].setToX(1);
						scaleTransitionForAnswerIconsFor4ImageAnswers[index].setToY(1);
						scaleTransitionForAnswerIconsFor4ImageAnswers[index].playFromStart();
					}
					hintToCorrectAnswer();
				}
				if(!openPauseMenu) showNextQuestionButton();
			}
		}

		private void correctAnswerReaction()
		{
			getAudioStuff().playCorrectAnswerSimpleSound();
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
			getAudioStuff().playWrongAnswerSimpleSound();

			points = newScorePoints(false);
			setScorePoints(getScorePoints() + points);
			if(getScorePoints() < 0) setScorePoints(0);
			showScoreAnimated(points);
		}

		private void hintToCorrectAnswer()
		{
			if(gridPaneFor2TextAnswers.isVisible())
				radioButtonsFor2TextAnswers[questionMaker.getPositionOfCorrectAnswer()].getStyleClass().add("green-background-color");
			else if(gridPaneFor4TextAnswers.isVisible())
				radioButtonsFor4TextAnswers[questionMaker.getPositionOfCorrectAnswer()].getStyleClass().add("green-background-color");
			else
			{
				imageViewFor4ImageAnswers[questionMaker.getPositionOfCorrectAnswer()].getAnswerIcon().setImage(images.get(Images.CORRECT_BIG));

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

		private int randIndForMultChoice[] = new int[3];
		private String randStrForMultChoice[] = new String[3];

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
				for(ArrayList<Integer> list: availableQuestionsArray) if(list != null && !list.isEmpty()) list.clear();

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

						for (int y = 1; y < Continent.NUMBER_OF_CONTINENTS - 1; y++) availableQuestionsArray[CONTINENTS_CITIES].add(y);
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
						
						if(countriesByArea == null || countriesByArea.length != availableQuestionsArray[COUNTRIES_AREA].size())
						countriesByArea = availableQuestionsArray[COUNTRIES_AREA].stream().mapToInt(i -> i).toArray();
						
						for(int i = 0; i < countriesByArea.length; i++)
						{
							for(int j = 1; j < (countriesByArea.length - i); j++)
							{
								int temp;
								if(countries[countriesByArea[j - 1]].getArea().getAreaInKilometers() <
										countries[countriesByArea[j]].getArea().getAreaInKilometers())
								{
									temp = countriesByArea[j - 1];
									countriesByArea[j - 1] = countriesByArea[j];
									countriesByArea[j] = temp;
								}
							}
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

						for (int y = 1; y < Continent.NUMBER_OF_CONTINENTS - 1; y++) availableQuestionsArray[CONTINENTS_LARGEST_COUNTRIES].add(y);
					}
					else if (chosenCategory == CAT_SOVEREIGN_DEPENDENT_COUNTRIES)
					{
						if (availableQuestionsArray[SOVEREIGN_DEPENDENT_COUNTRIES] == null)
							availableQuestionsArray[SOVEREIGN_DEPENDENT_COUNTRIES] = new ArrayList<>();
						for (int y = 0, i = 0; y < NUM_ALL_COUNTRIES; y++)
						{
							if (!countries[y].isSovereignState() &&
								!countries[y].getNameInGreek().equals("Υπερδνειστερία") &&
								!countries[y].getNameInGreek().equals("Δημοκρατία της Κίνας (Ταϊβάν)") &&
							    !countries[y].getNameInGreek().equals("Βρετανικές Παρθένες Νήσοι") &&
								!countries[y].getNameInGreek().equals("Αμερικανικές Παρθένες Νήσοι") &&
							    !countries[y].getNameInGreek().equals("Αμερικανική Σαμόα") &&
								!countries[y].getNameInGreek().equals("Γαλλική Γουιάνα") &&
								!countries[y].getNameInGreek().equals("Γαλλική Πολυνησία") &&
								!countries[y].getNameInGreek().equals("Νήσος των Χριστουγέννων"))
									availableQuestionsArray[SOVEREIGN_DEPENDENT_COUNTRIES].add(y);
						}
						if(dependentStates == null)
							dependentStates = availableQuestionsArray[SOVEREIGN_DEPENDENT_COUNTRIES].stream().mapToInt(i -> i).toArray();
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
							for (int y = 0; y < Continent.NUMBER_OF_CONTINENTS; y++) availableQuestionsArray[CONTINENTS_LOCATION].add(y);
						}
					}
				}

				for(ArrayList<Integer> list: availableQuestionsArray) if(list != null && !list.isEmpty()) Collections.shuffle(list);

				if(availableQuestionsArray[COUNTRIES_LANGUAGES] != null && !availableQuestionsArray[COUNTRIES_LANGUAGES].isEmpty())
				{
					languages = new String[NUM_COUNTRIES_INDIVIDUAL_LANGUAGE];
					Scanner input = null;
					int counter = 0;

					try
					{
						if(getCurrentLanguage() == LANGUAGE.GREEK)
							input = new Scanner(this.getClass().getResourceAsStream("/dataFiles/languagesGreek.data"),
									"UTF-8");
						else input = new Scanner(this.getClass().getResourceAsStream("/dataFiles/languagesEnglish.data"),
								"UTF-8");

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
					if(countriesPopulationBoundaries == null)
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
				}

				if(availableQuestionsArray[COUNTRIES_CURRENCY] != null && !availableQuestionsArray[COUNTRIES_CURRENCY].isEmpty())
				{
					currencies = new String[NUM_INDIVIDUAL_CURRENCIES];
					Scanner input = null;
					int counter = 0;

					try
					{
						if(getCurrentLanguage() == LANGUAGE.GREEK)
							input = new Scanner(this.getClass().getResourceAsStream("/dataFiles/currenciesGreek.data"),
									"UTF-8");
						else input = new Scanner(this.getClass().getResourceAsStream("/dataFiles/currenciesEnglish.data"),
								"UTF-8");

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
				if((random.nextInt(5) > 0 || availableQuestionsArray[CONTINENTS_CITIES].isEmpty()) && !availableQuestionsArray[COUNTRIES_CITIES].isEmpty())
					countriesLargestCities();
				else continentsLargestCities();

				if(availableQuestionsArray[COUNTRIES_CITIES].isEmpty() && availableQuestionsArray[CONTINENTS_CITIES].isEmpty())
					availableCategories.remove(randomCategoryPositionInListWithChosenCategories);
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
				if((random.nextInt(5) > 0 || availableQuestionsArray[CONTINENTS_POPULATION].isEmpty()) &&
						!availableQuestionsArray[COUNTRIES_POPULATION].isEmpty())
					countriesPopulation();
				else continentsPopulation();

				if(availableQuestionsArray[COUNTRIES_POPULATION].isEmpty() && availableQuestionsArray[CONTINENTS_POPULATION].isEmpty())
					availableCategories.remove(randomCategoryPositionInListWithChosenCategories);
			}
			else if(index == CAT_CONTINENT_COUNTRY_AREA)
			{
				if((random.nextInt(5) > 0 || availableQuestionsArray[CONTINENTS_AREA].isEmpty()) && !availableQuestionsArray[COUNTRIES_AREA].isEmpty())
					countriesArea();
				else continentsArea();

				if(availableQuestionsArray[COUNTRIES_AREA].isEmpty() && availableQuestionsArray[CONTINENTS_AREA].isEmpty())
					availableCategories.remove(randomCategoryPositionInListWithChosenCategories);
			}
			else if(index == CAT_CONTINENTS_COUNTRIES)
			{
				if((random.nextInt(5) > 0 || availableQuestionsArray[CONTINENTS_LARGEST_COUNTRIES].isEmpty()) &&
						!availableQuestionsArray[CONTINENTS_COUNTRIES].isEmpty())
					continentsCountries();
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

					if(availableQuestionsArray[COUNTRIES_LOCATION].isEmpty() && availableQuestionsArray[COUNTRIES_LOCATION_ALTERNATIVE].isEmpty() &&
							availableQuestionsArray[CONTINENTS_LOCATION].isEmpty())
						availableCategories.remove(randomCategoryPositionInListWithChosenCategories);
				}
				else
				{
					y = random.nextInt(9);
					if ((y > 2 || availableQuestionsArray[COUNTRIES_LOCATION_ALTERNATIVE].isEmpty()) && !availableQuestionsArray[COUNTRIES_LOCATION].isEmpty())
						countriesLocation();
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
				do randIndForMultChoice[0] = random.nextInt(countries.length);
				while(countries[correctIndex].askCapital() != countries[randIndForMultChoice[0]].askCapital() ||
				      countries[randIndForMultChoice[0]].getCapitalName().equals(countries[correctIndex].getCapitalName()));
				do randIndForMultChoice[1] = random.nextInt(countries.length);
				while (countries[correctIndex].askCapital() != countries[randIndForMultChoice[1]].askCapital() ||
						countries[randIndForMultChoice[1]].getCapitalName().equals(countries[correctIndex].getCapitalName()) ||
						countries[randIndForMultChoice[1]].getCapitalName().equals(countries[randIndForMultChoice[0]].getCapitalName()));
				do randIndForMultChoice[2] = random.nextInt(countries.length);
				while (countries[correctIndex].askCapital() != countries[randIndForMultChoice[1]].askCapital() ||
						countries[randIndForMultChoice[2]].getCapitalName().equals(countries[correctIndex].getCapitalName()) ||
						countries[randIndForMultChoice[2]].getCapitalName().equals(countries[randIndForMultChoice[0]].getCapitalName()) ||
						countries[randIndForMultChoice[2]].getCapitalName().equals(countries[randIndForMultChoice[1]].getCapitalName()));
			}

			if(wayOfAskingCurrentQuestion == 1)
			{
				String capital;
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					capital = getAccusative(countries[correctIndex].getArticleForCapital(),
							needsN(countries[correctIndex].getCapitalName())).toLowerCase() + " " + countries[correctIndex].getCapitalName();
					correctAnswerString = countries[correctIndex].getNameInGreek();
					for(int i = 0; i < 3; i++)
						randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getNameInGreek();
				}
				else
				{
					capital = countries[correctIndex].getCapitalName();
					correctAnswerString = countries[correctIndex].getNameInEnglish();
					for(int i = 0; i < 3; i++)
						randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getNameInEnglish();
				}
				textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesCapitalsWay1"), capital));
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				String country = getCurrentLanguage() == LANGUAGE.GREEK ?
						getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
								" " + countries[correctIndex].getGenitiveCaseOfCountry() : countries[correctIndex].getNameInEnglish();
				
				textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesCapitalWay2"), country));
				correctAnswerString = countries[correctIndex].getCapitalName();
				for(int i = 0; i < 3; i++)
					randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getCapitalName();
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				int index;
				String capital, country;
				if (positionOfCorrectAnswer == 0) index = correctIndex;
				else
				{
					do index = random.nextInt(countries.length);
					while(countries[correctIndex].askCapital() != countries[index].askCapital() ||
							countries[index].getCapitalName().equals(countries[correctIndex].getCapitalName()));
				}
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					capital = countries[index].getArticleForCapital() + " " + countries[index].getCapitalName();
					country = getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
							" " + countries[correctIndex].getGenitiveCaseOfCountry();
				}
				else
				{
					capital = countries[index].getCapitalName();
					country = countries[correctIndex].getNameInEnglish();
				}
				textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesCapitalWay3"), capital, country));
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
			if(getDifficultyLevel() == DIFFICULTY.EASY) y = 1;
			else y = random.nextInt(3) + 1; // decide to ask largest, or 2nd largest, or 3rd

			easyQuestion = countries[correctIndex].askLargestCity() == 1 || y == 1;

			wayOfAskingCurrentQuestion = random.nextInt(3) + 1;

			if(wayOfAskingCurrentQuestion != 3)
			{
				do randIndForMultChoice[0] = random.nextInt(countries.length);
				while(countries[correctIndex].askLargestCity() != countries[randIndForMultChoice[0]].askLargestCity() ||
				      randIndForMultChoice[0] == correctIndex);
				do randIndForMultChoice[1] = random.nextInt(countries.length);
				while (countries[correctIndex].askLargestCity() != countries[randIndForMultChoice[1]].askLargestCity() ||
				       randIndForMultChoice[1] == correctIndex ||
				       randIndForMultChoice[1] == randIndForMultChoice[0]);
				do randIndForMultChoice[2] = random.nextInt(countries.length);
				while (countries[correctIndex].askLargestCity() != countries[randIndForMultChoice[2]].askLargestCity() ||
				       randIndForMultChoice[2] == correctIndex ||
				       randIndForMultChoice[2] == randIndForMultChoice[0] ||
				       randIndForMultChoice[2] == randIndForMultChoice[1]);
			}
			
			String pos = getNum(y), city = countries[correctIndex].getLargestCities()[Math.min(countries[correctIndex].getLargestCities().length - 1, y - 1)];
			String country = getCurrentLanguage() == LANGUAGE.GREEK ? getGenitive(
					countries[correctIndex].getArticleForCountry()).toLowerCase() + " " +
						countries[correctIndex].getGenitiveCaseOfCountry() : countries[correctIndex].getNameInEnglish();

			if(wayOfAskingCurrentQuestion == 1)
			{
				textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesLargestCitiesWay1"), city, pos));
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					correctAnswerString = countries[correctIndex].getNameInGreek();
					for(int i = 0; i < 3; i++)
						randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getNameInGreek();
				}
				else
				{
					correctAnswerString = countries[correctIndex].getNameInEnglish();
					for(int i = 0; i < 3; i++)
						randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getNameInEnglish();
				}
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				int r1, r2, r3;
				if(countries[correctIndex].getLargestCities().length == 1 || y == 1)
				{
					correctAnswerString = countries[correctIndex].getLargestCities()[0];
					if(countries[correctIndex].getLargestCities().length == 1)
					{
						for (int i = 0; i < 3; i++)
						{
							if (countries[randIndForMultChoice[i]].getLargestCities().length == 1)
								randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getLargestCities()[0];
							else randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getLargestCities()[random.nextInt(10)];
						}
					}
					else
					{
						r1 = random.nextInt(9) + 1;
						do r2 = random.nextInt(9) + 1; while(r2 == r1);
						do r3 = random.nextInt(9) + 1; while(r3 == r1 || r3 == r2);
						randStrForMultChoice[0] = countries[correctIndex].getLargestCities()[r1];
						randStrForMultChoice[1] = countries[correctIndex].getLargestCities()[r2];
						randStrForMultChoice[2] = countries[correctIndex].getLargestCities()[r3];
					}
				}
				else
				{
					correctAnswerString = countries[correctIndex].getLargestCities()[y - 1];
					do r1 = random.nextInt(10); while(r1 == y - 1);
					do r2 = random.nextInt(10); while(r2 == y - 1 || r2 == r1);
					do r3 = random.nextInt(10); while(r3 == y - 1 || r3 == r1 || r3 == r2);
					randStrForMultChoice[0] = countries[correctIndex].getLargestCities()[r1];
					randStrForMultChoice[1] = countries[correctIndex].getLargestCities()[r2];
					randStrForMultChoice[2] = countries[correctIndex].getLargestCities()[r3];
				}
				textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesLargestCitiesWay2"), pos, country));
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				if(positionOfCorrectAnswer == 1)
				{
					int index;
					if (countries[correctIndex].getLargestCities().length == 1)
					{
						do index = random.nextInt(countries.length);
						while(index == correctIndex);
						if (countries[index].getLargestCities().length == 1) city = countries[index].getLargestCities()[0];
						else city = countries[index].getLargestCities()[random.nextInt(10)];
					}
					else
					{
						if(y == 1) index = random.nextInt(9) + 1;
						else if(y == 2) do index = random.nextInt(10); while(index == 1);
						else do index = random.nextInt(10); while(index == 2);
						city = countries[correctIndex].getLargestCities()[index];
					}
				}
				textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesLargestCitiesWay3"), city, pos, country));
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

			int y = random.nextInt(3) + 1; // decide to ask largest, or 2nd largest, or 3rd
			easyQuestion = y == 1;

			wayOfAskingCurrentQuestion = random.nextInt(2) + 1;
			
			String continent = getCurrentLanguage() == LANGUAGE.GREEK ?
					continents[correctIndex].getGenitiveCaseOfContinent() :
					continents[correctIndex].getNameInEnglish();
			String pos = getNum(y), city = continents[correctIndex].getLargestCities()[y - 1];

			if(wayOfAskingCurrentQuestion == 1)
			{
				int r1, r2, r3;
				correctAnswerString = continents[correctIndex].getLargestCities()[y - 1];
				if(y == 1)
				{
					r1 = random.nextInt(9) + 1;
					do r2 = random.nextInt(9) + 1; while(r2 == r1);
					do r3 = random.nextInt(9) + 1; while(r3 == r1 || r3 == r2);
				}
				else
				{
					do r1 = random.nextInt(10); while(r1 == y - 1);
					do r2 = random.nextInt(10); while(r2 == y - 1 || r2 == r1);
					do r3 = random.nextInt(10); while(r3 == y - 1 || r3 == r1 || r3 == r2);
				}
				randStrForMultChoice[0] = continents[correctIndex].getLargestCities()[r1];
				randStrForMultChoice[1] = continents[correctIndex].getLargestCities()[r2];
				randStrForMultChoice[2] = continents[correctIndex].getLargestCities()[r3];
				textForQuestion.setText(String.format(questionsResourceBundle.getString("continentsLargestCitiesWay1"), pos, continent));
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				if(positionOfCorrectAnswer == 1)
				{
					int index;
					if(y == 1) index = random.nextInt(9) + 1;
					else if(y == 2) do index = random.nextInt(10); while(index == 1);
					else do index = random.nextInt(10); while(index == 2);
					city = continents[correctIndex].getLargestCities()[index];
				}
				textForQuestion.setText(String.format(questionsResourceBundle.getString("continentsLargestCitiesWay2"), city, pos, continent));
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
			
			String country = getCurrentLanguage() == LANGUAGE.GREEK ?
					getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() + " " +
					countries[correctIndex].getGenitiveCaseOfCountry() :
					countries[correctIndex].getNameInEnglish();
			
			String lang;
			if(countries[correctIndex].askLanguage() == 1) lang = countries[correctIndex].getLanguagesToAsk()[0];
			else lang = countries[correctIndex].getLanguagesToAsk()[random.nextInt(countries[correctIndex].getLanguagesToAsk().length)];

			if(wayOfAskingCurrentQuestion == 1)
			{
				if(countries[correctIndex].askLanguage() == 1)
					textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesLanguagesWay1a"), lang));
				else textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesLanguagesWay1b"), lang));
				do randIndForMultChoice[0] = random.nextInt(countries.length);
				while(countries[randIndForMultChoice[0]].getLanguagesString().contains(lang));
				do randIndForMultChoice[1] = random.nextInt(countries.length);
				while(countries[randIndForMultChoice[1]].getLanguagesString().contains(lang) ||
						randIndForMultChoice[1] == randIndForMultChoice[0]);
				do randIndForMultChoice[2] = random.nextInt(countries.length);
				while(countries[randIndForMultChoice[2]].getLanguagesString().contains(lang) ||
						randIndForMultChoice[2] == randIndForMultChoice[0] ||
						randIndForMultChoice[2] == randIndForMultChoice[1]);
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					correctAnswerString = countries[correctIndex].getNameInGreek();
					for(int i = 0; i < 3; i++) randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getNameInGreek();
				}
				else
				{
					correctAnswerString = countries[correctIndex].getNameInEnglish();
					for(int i = 0; i < 3; i++) randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getNameInEnglish();
				}
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				do randIndForMultChoice[0] = random.nextInt(languages.length);
				while(countries[correctIndex].getLanguagesString().contains(languages[randIndForMultChoice[0]]));
				do randIndForMultChoice[1] = random.nextInt(languages.length);
				while(countries[correctIndex].getLanguagesString().contains(languages[randIndForMultChoice[1]]) ||
				      randIndForMultChoice[1] == randIndForMultChoice[0]);
				do randIndForMultChoice[2] = random.nextInt(languages.length);
				while(countries[correctIndex].getLanguagesString().contains(languages[randIndForMultChoice[2]]) ||
				      randIndForMultChoice[2] == randIndForMultChoice[0] ||
				      randIndForMultChoice[2] == randIndForMultChoice[1]);
				if(countries[correctIndex].askLanguage() == 1)
				{
					textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesLanguagesWay2a"), country));
					correctAnswerString = countries[correctIndex].getLanguagesToAsk()[0];
				}
				else
				{
					textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesLanguagesWay2b"), country));
					correctAnswerString = countries[correctIndex].getLanguagesToAsk()[random.nextInt(countries[correctIndex].getLanguagesToAsk().length)];
				}
				for(int i = 0; i < 3; i++) randStrForMultChoice[i] = languages[randIndForMultChoice[i]];
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				if(positionOfCorrectAnswer == 1)
				{
					do lang = languages[random.nextInt(languages.length)];
					while(countries[correctIndex].getLanguagesString().contains(lang));
				}
				if(countries[correctIndex].askLanguage() == 1)
					textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesLanguagesWay3a"), lang, country));
				else textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesLanguagesWay3b"), lang, country));
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
			
			String country = getCurrentLanguage() == LANGUAGE.GREEK ?
					getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
							" " + countries[correctIndex].getGenitiveCaseOfCountry() :
					countries[correctIndex].getNameInEnglish();

			if(wayOfAskingCurrentQuestion == 1)
			{
				do randIndForMultChoice[0] = random.nextInt(currencies.length);
				while(currencies[randIndForMultChoice[0]].equals(countries[correctIndex].getCurrency().getName()));
				do randIndForMultChoice[1] = random.nextInt(currencies.length);
				while(currencies[randIndForMultChoice[1]].equals(countries[correctIndex].getCurrency().getName()) ||
				      randIndForMultChoice[1] == randIndForMultChoice[0]);
				do randIndForMultChoice[2] = random.nextInt(currencies.length);
				while(currencies[randIndForMultChoice[2]].equals(countries[correctIndex].getCurrency().getName()) ||
				      randIndForMultChoice[2] == randIndForMultChoice[0] ||
				      randIndForMultChoice[2] == randIndForMultChoice[1]);
				correctAnswerString = countries[correctIndex].getCurrency().getName();
				textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesCurrencyWay1"), country));
				for(int i = 0; i < 3; i++) randStrForMultChoice[i] = currencies[randIndForMultChoice[i]];
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				int index = correctIndex;
				if(positionOfCorrectAnswer == 1)
				{
					do index = random.nextInt(currencies.length);
					while(currencies[index].equals(countries[correctIndex].getCurrency().getName()));
				}
				textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesCurrencyWay2"),
						countries[index].getCurrency().getName(), country));
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
			
			String country = getCurrentLanguage() == LANGUAGE.GREEK ?
					getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() + " " +
							countries[correctIndex].getGenitiveCaseOfCountry() :
					countries[correctIndex].getNameInEnglish();

			if(wayOfAskingCurrentQuestion == 1)
			{
				textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesPopulationWay1"), country));
				if(lowBoundary == 0) positionOfCorrectAnswer = 0;
				else if(lowBoundary == 1) positionOfCorrectAnswer = random.nextInt(2);
				else if(lowBoundary == 2) positionOfCorrectAnswer = random.nextInt(3);
				else if(lowBoundary == 12) positionOfCorrectAnswer = random.nextInt(3) + 1;
				else if(lowBoundary == 13) positionOfCorrectAnswer = random.nextInt(2) + 2;
				else if(lowBoundary == 14) positionOfCorrectAnswer = 3;
				else positionOfCorrectAnswer = random.nextInt(4);

				if(positionOfCorrectAnswer == 0)
				{
					radioButtonsFor4TextAnswers[0].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary]) +
							" - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 1]));
					radioButtonsFor4TextAnswers[1].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 1]) +
							" - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 2]));
					radioButtonsFor4TextAnswers[2].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 2]) +
							" - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 3]));
					radioButtonsFor4TextAnswers[3].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 3]) +
							" - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 4]));
				}
				else if(positionOfCorrectAnswer == 1)
				{
					radioButtonsFor4TextAnswers[0].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary - 1]) +
							" - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary]));
					radioButtonsFor4TextAnswers[1].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary]) +
							" - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 1]));
					radioButtonsFor4TextAnswers[2].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 1]) +
							" - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 2]));
					radioButtonsFor4TextAnswers[3].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 2]) +
							" - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 3]));
				}
				else if(positionOfCorrectAnswer == 2)
				{
					radioButtonsFor4TextAnswers[0].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary - 2]) +
							" - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary - 1]));
					radioButtonsFor4TextAnswers[1].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary - 1]) +
							" - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary]));
					radioButtonsFor4TextAnswers[2].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary]) +
							" - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 1]));
					radioButtonsFor4TextAnswers[3].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 1]) +
							" - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 2]));
				}
				else
				{
					radioButtonsFor4TextAnswers[0].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary - 3]) +
							" - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary - 2]));
					radioButtonsFor4TextAnswers[1].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary - 2]) +
							" - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary - 1]));
					radioButtonsFor4TextAnswers[2].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary - 1]) +
							" - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary]));
					radioButtonsFor4TextAnswers[3].setText(numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary]) +
							" - " + numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 1]));
				}
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);

				String s = numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary]) + " - " +
						numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 1]);
				if(positionOfCorrectAnswer == 1)
				{
					int rand = random.nextInt(2);
					if(lowBoundary == 14 || rand == 0)
						s = numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary - 1]) + " - " +
								numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary]);
					else if(lowBoundary == 0 || rand == 1)
						s = numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 1]) + " - " +
								numberFormatForUI.format(countriesPopulationBoundaries[lowBoundary + 2]);
				}
				textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesPopulationWay2"), country, s));
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

			String continent = getCurrentLanguage() == LANGUAGE.GREEK ?
					continents[correctIndex].getNameInGreek() : continents[correctIndex].getNameInEnglish();
			
			String t = getNum(continents[correctIndex].getGlobalPopulationRanking());

			if(wayOfAskingCurrentQuestion == 1)
			{
				do randIndForMultChoice[0] = random.nextInt(continents.length - 1) + 1;
				while(randIndForMultChoice[0] == correctIndex);
				do randIndForMultChoice[1] = random.nextInt(continents.length - 1) + 1;
				while(randIndForMultChoice[1] == correctIndex || randIndForMultChoice[1] == randIndForMultChoice[0]);
				do randIndForMultChoice[2] = random.nextInt(continents.length - 1) + 1;
				while(randIndForMultChoice[2] == correctIndex ||
						randIndForMultChoice[2] == randIndForMultChoice[0] ||
						randIndForMultChoice[2] == randIndForMultChoice[1]);
				textForQuestion.setText(String.format(questionsResourceBundle.getString("continentsPopulationWay1"), t));
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					correctAnswerString = continents[correctIndex].getNameInGreek();
					randStrForMultChoice[0] = continents[randIndForMultChoice[0]].getNameInGreek();
					randStrForMultChoice[1] = continents[randIndForMultChoice[1]].getNameInGreek();
					randStrForMultChoice[2] = continents[randIndForMultChoice[2]].getNameInGreek();
				}
				else
				{
					correctAnswerString = continents[correctIndex].getNameInEnglish();
					randStrForMultChoice[0] = continents[randIndForMultChoice[0]].getNameInEnglish();
					randStrForMultChoice[1] = continents[randIndForMultChoice[1]].getNameInEnglish();
					randStrForMultChoice[2] = continents[randIndForMultChoice[2]].getNameInEnglish();
				}
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				positionOfCorrectAnswer = random.nextInt(2);
				if(positionOfCorrectAnswer == 1)
				{
					int index;
					do index = random.nextInt(6) + 1;
					while(index == continents[correctIndex].getGlobalPopulationRanking());
					t = getNum(index);
				}
				textForQuestion.setText(String.format(questionsResourceBundle.getString("continentsPopulationWay2"), continent, t));
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				boolean askIsLargerThan = random.nextBoolean();
				
				int index;
				String cont2, cont, type;
				if(positionOfCorrectAnswer == 0)
				{
					if(continents[correctIndex].getGlobalPopulationRanking() == 1 || askIsLargerThan && continents[correctIndex].getGlobalPopulationRanking() != 6)
					{
						do index = random.nextInt(continents.length - 1) + 1;
						while(continents[index].getGlobalPopulationRanking() <= continents[correctIndex].getGlobalPopulationRanking());
						type = questionsResourceBundle.getString("moreString");
					}
					else
					{
						do index = random.nextInt(continents.length - 1) + 1;
						while(continents[index].getGlobalPopulationRanking() >= continents[correctIndex].getGlobalPopulationRanking());
						type = questionsResourceBundle.getString("lessString");
					}
				}
				else
				{
					if(continents[correctIndex].getGlobalPopulationRanking() == 6 || askIsLargerThan && continents[correctIndex].getGlobalPopulationRanking() != 1)
					{
						do index = random.nextInt(continents.length - 1) + 1;
						while(continents[index].getGlobalPopulationRanking() >= continents[correctIndex].getGlobalPopulationRanking());
						type = questionsResourceBundle.getString("moreString");
					}
					else
					{
						do index = random.nextInt(continents.length - 1) + 1;
						while(continents[index].getGlobalPopulationRanking() <= continents[correctIndex].getGlobalPopulationRanking());
						type = questionsResourceBundle.getString("lessString");
					}
				}
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					cont = continents[correctIndex].getNameInGreek();
					cont2 = continents[index].getNameInGreek();
					if(needsN(cont2)) cont2 = "ν " + cont2;
					else cont2 = " " + cont2;
				}
				else
				{
					cont = continents[correctIndex].getNameInEnglish();
					cont2 = continents[index].getNameInEnglish();
				}
				textForQuestion.setText(String.format(questionsResourceBundle.getString("continentsPopulationWay3"), cont, type, cont2));
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

		/*not ready*/
		private void countriesArea()
		{
			correctIndex = availableQuestionsArray[COUNTRIES_AREA].get(0);
			availableQuestionsArray[COUNTRIES_AREA].remove(0);
			
			int correctIndexInCountriesByArea = -1;
			for(int i = 0; i < countriesByArea.length; i++)
			{
				if(correctIndex == countriesByArea[i])
				{
					correctIndexInCountriesByArea = i;
					break;
				}
			}

			easyQuestion = countries[correctIndex].hasEasyLocation();

			wayOfAskingCurrentQuestion = random.nextInt(2) + 1;

			boolean askLarger;
			String type;
			if(wayOfAskingCurrentQuestion == 1)
			{
				if(correctIndexInCountriesByArea == 0 || correctIndexInCountriesByArea == 1 || correctIndexInCountriesByArea == 2) askLarger = true;
				else if(correctIndexInCountriesByArea == countriesByArea.length - 1 || correctIndexInCountriesByArea == countriesByArea.length - 2 || correctIndexInCountriesByArea == countriesByArea.length - 3) askLarger = false;
				else askLarger = random.nextBoolean();
				
				if(askLarger) type = questionsResourceBundle.getString("largerString");
				else  type = questionsResourceBundle.getString("smallerString");
				
				int bound = askLarger ? correctIndexInCountriesByArea : (countriesByArea.length - 1) - correctIndexInCountriesByArea;
				int offset = askLarger ? 0 : correctIndexInCountriesByArea + 1;
				
				do randIndForMultChoice[0] = random.nextInt(bound) + offset;
				while(randIndForMultChoice[0] == correctIndex);
				do randIndForMultChoice[1] = random.nextInt(bound) + offset;
				while(randIndForMultChoice[1] == correctIndex ||
						randIndForMultChoice[1] == randIndForMultChoice[0]);
				do randIndForMultChoice[2] = random.nextInt(bound) + offset;
				while(randIndForMultChoice[2] == correctIndex ||
						randIndForMultChoice[2] == randIndForMultChoice[0] ||
						randIndForMultChoice[2] == randIndForMultChoice[1]);
			}
			else if (wayOfAskingCurrentQuestion == 2)
			{

			}
		}

		private void continentsArea()
		{
			correctIndex = availableQuestionsArray[CONTINENTS_AREA].get(0);
			availableQuestionsArray[CONTINENTS_AREA].remove(0);

			easyQuestion = continents[correctIndex].getGlobalAreaRanking() < 3;

			wayOfAskingCurrentQuestion = random.nextInt(3) + 1;

			String t = getNum(continents[correctIndex].getGlobalAreaRanking());

			if(wayOfAskingCurrentQuestion == 1)
			{
				do randIndForMultChoice[0] = random.nextInt(continents.length);
				while(randIndForMultChoice[0] == correctIndex);
				do randIndForMultChoice[1] = random.nextInt(continents.length);
				while(randIndForMultChoice[1] == correctIndex ||
						randIndForMultChoice[1] == randIndForMultChoice[0]);
				do randIndForMultChoice[2] = random.nextInt(continents.length);
				while(randIndForMultChoice[2] == correctIndex ||
						randIndForMultChoice[2] == randIndForMultChoice[0] ||
						randIndForMultChoice[2] == randIndForMultChoice[1]);
				textForQuestion.setText(String.format(questionsResourceBundle.getString("continentsAreaWay1"), t));
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					correctAnswerString = continents[correctIndex].getNameInGreek();
					randStrForMultChoice[0] = continents[randIndForMultChoice[0]].getNameInGreek();
					randStrForMultChoice[1] = continents[randIndForMultChoice[1]].getNameInGreek();
					randStrForMultChoice[2] = continents[randIndForMultChoice[2]].getNameInGreek();
				}
				else
				{
					correctAnswerString = continents[correctIndex].getNameInEnglish();
					randStrForMultChoice[0] = continents[randIndForMultChoice[0]].getNameInEnglish();
					randStrForMultChoice[1] = continents[randIndForMultChoice[1]].getNameInEnglish();
					randStrForMultChoice[2] = continents[randIndForMultChoice[2]].getNameInEnglish();
				}
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				positionOfCorrectAnswer = random.nextInt(2);
				if(positionOfCorrectAnswer == 1)
				{
					int index;
					do index = random.nextInt(6) + 1;
					while(index == continents[correctIndex].getGlobalAreaRanking());
					t = getNum(index);
				}
				String cont = getCurrentLanguage() == LANGUAGE.GREEK ? continents[correctIndex].getNameInGreek() : continents[correctIndex].getNameInEnglish();
				textForQuestion.setText(String.format(questionsResourceBundle.getString("continentsAreaWay2"), cont, t));
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				boolean askIsLargerThan = random.nextBoolean();
				
				int index;
				String cont2, cont, type;
				if (positionOfCorrectAnswer == 0)
				{
					if(continents[correctIndex].getGlobalAreaRanking() == 1 || askIsLargerThan && continents[correctIndex].getGlobalAreaRanking() != 6)
					{
						do index = random.nextInt(continents.length - 1) + 1;
						while(continents[index].getGlobalAreaRanking() <= continents[correctIndex].getGlobalAreaRanking());
						type = questionsResourceBundle.getString("largerString");
					}
					else
					{
						do index = random.nextInt(continents.length - 1) + 1;
						while(continents[index].getGlobalAreaRanking() >= continents[correctIndex].getGlobalAreaRanking());
						type = questionsResourceBundle.getString("smallerString");
					}
				}
				else
				{
					if(continents[correctIndex].getGlobalAreaRanking() == 6 || askIsLargerThan && continents[correctIndex].getGlobalAreaRanking() != 1)
					{
						do index = random.nextInt(continents.length - 1) + 1;
						while(continents[index].getGlobalAreaRanking() >= continents[correctIndex].getGlobalAreaRanking());
						type = questionsResourceBundle.getString("largerString");
					}
					else
					{
						do index = random.nextInt(continents.length - 1) + 1;
						while(continents[index].getGlobalAreaRanking() <= continents[correctIndex].getGlobalAreaRanking());
						type = questionsResourceBundle.getString("smallerString");
					}
				}
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					cont = continents[correctIndex].getNameInGreek();
					cont2 = continents[index].getNameInGreek();
					if(needsN(cont2)) cont2 = "ν " + cont2;
					else cont2 = " " + cont2;
				}
				else
				{
					cont = continents[correctIndex].getNameInEnglish();
					cont2 = continents[index].getNameInEnglish();
				}
				textForQuestion.setText(String.format(questionsResourceBundle.getString("continentsAreaWay3"), cont, type, cont2));
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

			String article, s, cont;

			if(wayOfAskingCurrentQuestion == 1)
			{
				do randIndForMultChoice[0] = random.nextInt(countries.length);
				while(countries[randIndForMultChoice[0]].getContinent().equals(countries[correctIndex].getContinent()) ||
				      countries[randIndForMultChoice[0]].hasEasyLocation() && !countries[correctIndex].hasEasyLocation() ||
				      !countries[randIndForMultChoice[0]].hasEasyLocation() && countries[correctIndex].hasEasyLocation());
				do randIndForMultChoice[1] = random.nextInt(countries.length);
				while (randIndForMultChoice[1] == randIndForMultChoice[0] ||
				       countries[randIndForMultChoice[1]].getContinent().equals(countries[correctIndex].getContinent()) ||
				       countries[randIndForMultChoice[1]].hasEasyLocation() && !countries[correctIndex].hasEasyLocation() ||
				       !countries[randIndForMultChoice[1]].hasEasyLocation() && countries[correctIndex].hasEasyLocation());
				do randIndForMultChoice[2] = random.nextInt(countries.length);
				while (randIndForMultChoice[2] == randIndForMultChoice[0] ||
				       randIndForMultChoice[2] == randIndForMultChoice[1] ||
				       countries[randIndForMultChoice[2]].getContinent().equals(countries[correctIndex].getContinent()) ||
				       countries[randIndForMultChoice[2]].hasEasyLocation() && !countries[correctIndex].hasEasyLocation() ||
				       !countries[randIndForMultChoice[2]].hasEasyLocation() && countries[correctIndex].hasEasyLocation());

				cont = countries[correctIndex].getContinent();
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					if(needsN(cont)) cont = "ν " + cont;
					else cont = " " + cont;
					
					correctAnswerString = countries[correctIndex].getNameInGreek();
					for(int i = 0; i < 3; i++)
						randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getNameInGreek();
				}
				else
				{
					correctAnswerString = countries[correctIndex].getNameInEnglish();
					for(int i = 0; i < 3; i++)
						randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getNameInEnglish();
				}
				textForQuestion.setText(String.format(questionsResourceBundle.getString("continentsCountriesWay1"), cont));
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				do randIndForMultChoice[0] = random.nextInt(continents.length - 1) + 1;
				while(continents[randIndForMultChoice[0]].getNameInGreek().equals(countries[correctIndex].getContinent()) ||
				      continents[randIndForMultChoice[0]].getNameInEnglish().equals(countries[correctIndex].getContinent()));
				do randIndForMultChoice[1] = random.nextInt(continents.length - 1) + 1;
				while (randIndForMultChoice[1] == randIndForMultChoice[0] ||
				       continents[randIndForMultChoice[1]].getNameInGreek().equals(countries[correctIndex].getContinent()) ||
				       continents[randIndForMultChoice[1]].getNameInEnglish().equals(countries[correctIndex].getContinent()));
				do randIndForMultChoice[2] = random.nextInt(continents.length - 1) + 1;
				while (randIndForMultChoice[2] == randIndForMultChoice[0] ||
				       randIndForMultChoice[2] == randIndForMultChoice[1] ||
				       continents[randIndForMultChoice[2]].getNameInGreek().equals(countries[correctIndex].getContinent()) ||
				       continents[randIndForMultChoice[2]].getNameInEnglish().equals(countries[correctIndex].getContinent()));
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					if(isArticleInPlural(countries[correctIndex].getArticleForCountry())) s = "βρίσκονται ";
					else s = "βρίσκεται ";
					s += countries[correctIndex].getArticleForCountry().toLowerCase() + " " + countries[correctIndex].getNameInGreek();
					for(int i = 0; i < 3; i++)
						randStrForMultChoice[i] = continents[randIndForMultChoice[i]].getNameInGreek();
				}
				else
				{
					s = countries[correctIndex].getNameInEnglish();
					for(int i = 0; i < 3; i++)
						randStrForMultChoice[i] = continents[randIndForMultChoice[i]].getNameInEnglish();
				}
				textForQuestion.setText(String.format(questionsResourceBundle.getString("continentsCountriesWay2"), s));
				correctAnswerString = countries[correctIndex].getContinent();
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				int index = correctIndex;
				if(positionOfCorrectAnswer == 1)
				{
					if(getCurrentLanguage() == LANGUAGE.GREEK)
					{
						do index = random.nextInt(continents.length - 1) + 1;
						while(continents[index].getNameInGreek().equals(countries[correctIndex].getContinent()));
					}
					else
					{
						do index = random.nextInt(continents.length - 1) + 1;
						while(continents[index].getNameInEnglish().equals(countries[correctIndex].getContinent()));
					}
				}
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					s = countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek();
					if(isArticleInPlural(countries[correctIndex].getArticleForCountry())) s += " βρίσκονται ";
					else s += " βρίσκεται ";
					if (positionOfCorrectAnswer == 0) cont = countries[correctIndex].getContinent();
					else cont = continents[index].getNameInGreek();
					if(needsN(cont)) s += "στην ";
					else s += "στη ";
					s += cont + ".";
					textForQuestion.setText(s);
				}
				else
				{
					if (positionOfCorrectAnswer == 0) cont = countries[correctIndex].getContinent();
					else cont = continents[index].getNameInEnglish();
					textForQuestion.setText(countries[correctIndex].getNameInEnglish() + " belongs to " + cont + ".");
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

			int y = random.nextInt(3) + 1; // decide to ask largest, or 2nd largest, or 3rd
			easyQuestion = y == 1;

			wayOfAskingCurrentQuestion = random.nextInt(2) + 1;
			
			String pos = getNum(y);
			String continent = getCurrentLanguage() == LANGUAGE.GREEK ? continents[correctIndex].getGenitiveCaseOfContinent() :
					continents[correctIndex].getNameInEnglish();
			
			if(random.nextBoolean()) // if true ask by area, else ask by population
			{
				if(wayOfAskingCurrentQuestion == 1)
				{
					int r1, r2, r3;
					if(y == 1)
					{
						r1 = random.nextInt(9) + 1;
						do r2 = random.nextInt(9) + 1; while(r2 == r1);
						do r3 = random.nextInt(9) + 1; while(r3 == r1 || r3 == r2);
					}
					else
					{
						do r1 = random.nextInt(10); while(r1 == y - 1);
						do r2 = random.nextInt(10); while(r2 == y - 1 || r2 == r1);
						do r3 = random.nextInt(10); while(r3 == y - 1 || r3 == r1 || r3 == r2);
					}
					textForQuestion.setText(String.format(questionsResourceBundle.getString("continentsLargestCountriesAreaWay1"), pos, continent));
					correctAnswerString = continents[correctIndex].getLargestCountriesByArea()[y - 1];
					randStrForMultChoice[0] = continents[correctIndex].getLargestCountriesByArea()[r1];
					randStrForMultChoice[1] = continents[correctIndex].getLargestCountriesByArea()[r2];
					randStrForMultChoice[2] = continents[correctIndex].getLargestCountriesByArea()[r3];
				}
				else
				{
					positionOfCorrectAnswer = random.nextInt(2);
					
					String country = continents[correctIndex].getLargestCountriesByArea()[y - 1];
					if(positionOfCorrectAnswer == 1)
					{
						int index;
						if(y == 1) index = random.nextInt(9) + 1;
						else do index = random.nextInt(10); while(index == y - 1);
						country = continents[correctIndex].getLargestCountriesByArea()[index];
					}
					textForQuestion.setText(String.format(questionsResourceBundle.getString("continentsLargestCountriesAreaWay2"), country, pos, continent));
				}
			}
			else
			{
				if(wayOfAskingCurrentQuestion == 1)
				{
					int r1, r2, r3;
					if(y == 0)
					{
						r1 = random.nextInt(9) + 1;
						do r2 = random.nextInt(9) + 1; while(r2 == r1);
						do r3 = random.nextInt(9) + 1; while(r3 == r1 || r3 == r2);
					}
					else
					{
						do r1 = random.nextInt(10); while(r1 == y - 1);
						do r2 = random.nextInt(10); while(r2 == y - 1 || r2 == r1);
						do r3 = random.nextInt(10); while(r3 == y - 1 || r3 == r1 || r3 == r2);
					}
					textForQuestion.setText(String.format(questionsResourceBundle.getString("continentsLargestCountriesPopulationWay1"), pos, continent));
					correctAnswerString = continents[correctIndex].getLargestCountriesByPopulation()[y - 1];
					randStrForMultChoice[0] = continents[correctIndex].getLargestCountriesByPopulation()[r1];
					randStrForMultChoice[1] = continents[correctIndex].getLargestCountriesByPopulation()[r2];
					randStrForMultChoice[2] = continents[correctIndex].getLargestCountriesByPopulation()[r3];
				}
				else
				{
					positionOfCorrectAnswer = random.nextInt(2);
					
					String country = continents[correctIndex].getLargestCountriesByPopulation()[y - 1];
					if(positionOfCorrectAnswer == 1)
					{
						int index;
						if(y == 1) index = random.nextInt(9) + 1;
						else do index = random.nextInt(10); while(index == y - 1);
						country = continents[correctIndex].getLargestCountriesByPopulation()[index];
					}
					textForQuestion.setText(String.format(questionsResourceBundle.getString("continentsLargestCountriesPopulationWay2"),
							country, pos, continent));
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
				do randIndForMultChoice[0] = random.nextInt(countries.length);
				while(randIndForMultChoice[0] == correctIndex ||
				      countries[randIndForMultChoice[0]].getNameInGreek().equals(countries[correctIndex].getSovereignState()) ||
				      countries[randIndForMultChoice[0]].getNameInEnglish().equals(countries[correctIndex].getSovereignState()));
				do randIndForMultChoice[1] = random.nextInt(countries.length);
				while (randIndForMultChoice[1] == correctIndex ||
				       countries[randIndForMultChoice[1]].getNameInGreek().equals(countries[correctIndex].getSovereignState()) ||
				       countries[randIndForMultChoice[1]].getNameInEnglish().equals(countries[correctIndex].getSovereignState()) ||
				       randIndForMultChoice[1] == randIndForMultChoice[0]);
				do randIndForMultChoice[2] = random.nextInt(countries.length);
				while (randIndForMultChoice[2] == correctIndex ||
				       countries[randIndForMultChoice[2]].getNameInGreek().equals(countries[correctIndex].getSovereignState()) ||
				       countries[randIndForMultChoice[2]].getNameInEnglish().equals(countries[correctIndex].getSovereignState()) ||
				       randIndForMultChoice[2] == randIndForMultChoice[0] ||
				       randIndForMultChoice[2] == randIndForMultChoice[1]);

				int index = correctIndex, beSovereignState = random.nextInt(3); //0=dependent state, 1=pretend sovereign, 2=be sovereign
				String s;
				if(beSovereignState == 2)
				{
					do index = random.nextInt(countries.length);
					while(!countries[index].isSovereignState());
				}
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					String verb, article = countries[index].getArticleForCountry();
					if(isArticleInPlural(article)) verb = "ανήκουν ";
					else verb = "ανήκει ";
					s = verb + article.toLowerCase() + " " + countries[index].getNameInGreek();
					
					for (int i = 0; i < 3; i++) randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getNameInGreek();
				}
				else
				{
					s = countries[index].getNameInEnglish();
					for (int i = 0; i < 3; i++) randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getNameInEnglish();
				}
				textForQuestion.setText(String.format(questionsResourceBundle.getString("sovereignDependentCountriesWay1"), s));
				
				if(beSovereignState == 2) correctAnswerString = questionsResourceBundle.getString("stringSovereignState");
				else correctAnswerString = countries[index].getSovereignState();
				
				if(beSovereignState == 1)
					randStrForMultChoice[random.nextInt(3)] = questionsResourceBundle.getString("stringSovereignState");
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				do randIndForMultChoice[0] = random.nextInt(dependentStates.length);
				while(countries[dependentStates[randIndForMultChoice[0]]].getSovereignState().equals(countries[correctIndex].getSovereignState()));
				do randIndForMultChoice[1] = random.nextInt(dependentStates.length);
				while (countries[dependentStates[randIndForMultChoice[1]]].getSovereignState().equals(countries[correctIndex].getSovereignState()) ||
				       randIndForMultChoice[1] == randIndForMultChoice[0]);
				do randIndForMultChoice[2] = random.nextInt(dependentStates.length);
				while (countries[dependentStates[randIndForMultChoice[2]]].getSovereignState().equals(countries[correctIndex].getSovereignState()) ||
				       randIndForMultChoice[2] == randIndForMultChoice[0] ||
				       randIndForMultChoice[2] == randIndForMultChoice[1]);

				String s;
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
					s = getArticleIn(countries[index].getArticleForCountry(), needsN(countries[index].getNameInGreek())).toLowerCase() + " " +
							countries[index].getNameInGreek();
					correctAnswerString = countries[correctIndex].getNameInGreek();
					for (int i = 0; i < 3; i++) randStrForMultChoice[i] = countries[dependentStates[randIndForMultChoice[i]]].getNameInGreek();
				}
				else
				{
					s = countries[correctIndex].getSovereignState();
					correctAnswerString = countries[correctIndex].getNameInEnglish();
					for (int i = 0; i < 3; i++) randStrForMultChoice[i] = countries[dependentStates[randIndForMultChoice[i]]].getNameInEnglish();
				}
				textForQuestion.setText(String.format(questionsResourceBundle.getString("sovereignDependentCountriesWay2"), s));
				if(random.nextBoolean()) // if true, add option "none of the above" as the correct or a wrong answer
				{
					if(random.nextBoolean()) correctAnswerString = questionsResourceBundle.getString("stringNoneOfTheAbove");
					else randStrForMultChoice[random.nextInt(3)] = questionsResourceBundle.getString("stringNoneOfTheAbove");
				}
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);

				String dep, sov;
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
						dep = countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek();
						sov = getGenitive(countries[index].getArticleForCountry()).toLowerCase() + " " + countries[index].getGenitiveCaseOfCountry();
					}
					else
					{
						dep = countries[correctIndex].getNameInEnglish();
						sov = countries[correctIndex].getSovereignState();
					}
				}
				else
				{
					int r;
					if (getCurrentLanguage() == LANGUAGE.GREEK)
					{
						do r = random.nextInt(countries.length);
						while(countries[r].getNameInGreek().equals(countries[correctIndex].getSovereignState()) || !countries[r].isSovereignState());
						dep = countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek();
						sov = getGenitive(countries[r].getArticleForCountry()).toLowerCase() + " " + countries[r].getGenitiveCaseOfCountry();
					}
					else
					{
						do r = random.nextInt(countries.length);
						while(countries[r].getNameInEnglish().equals(countries[correctIndex].getSovereignState()) || !countries[r].isSovereignState());
						dep = countries[correctIndex].getNameInEnglish();
						sov = countries[r].getNameInEnglish();
					}
				}
				textForQuestion.setText(String.format(questionsResourceBundle.getString("sovereignDependentCountriesWay3"), dep, sov));
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
				do randIndForMultChoice[0] = random.nextInt(countries.length);
				while(countries[correctIndex].hasEasyFlag() != countries[randIndForMultChoice[0]].hasEasyFlag() ||
				      randIndForMultChoice[0] == correctIndex ||
				      (countries[randIndForMultChoice[0]].getNameInGreek().equals("Ηνωμένο Βασίλειο") ||
							  countries[randIndForMultChoice[0]].getNameInGreek().equals("Βόρεια Ιρλανδία")) &&
				      (countries[correctIndex].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[correctIndex].getNameInGreek().equals("Βόρεια Ιρλανδία")));
				do randIndForMultChoice[1] = random.nextInt(countries.length);
				while(countries[correctIndex].hasEasyFlag() != countries[randIndForMultChoice[1]].hasEasyFlag() ||
				      randIndForMultChoice[1] == correctIndex ||
				      randIndForMultChoice[1] == randIndForMultChoice[0] ||
				      (countries[randIndForMultChoice[1]].getNameInGreek().equals("Ηνωμένο Βασίλειο") ||
							  countries[randIndForMultChoice[1]].getNameInGreek().equals("Βόρεια Ιρλανδία")) &&
				      (countries[correctIndex].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[correctIndex].getNameInGreek().equals("Βόρεια Ιρλανδία")));
				do randIndForMultChoice[2] = random.nextInt(countries.length);
				while(countries[correctIndex].hasEasyFlag() != countries[randIndForMultChoice[2]].hasEasyFlag() ||
				      randIndForMultChoice[2] == correctIndex ||
				      randIndForMultChoice[2] == randIndForMultChoice[0] ||
				      randIndForMultChoice[2] == randIndForMultChoice[1] ||
				      (countries[randIndForMultChoice[2]].getNameInGreek().equals("Ηνωμένο Βασίλειο") ||
							  countries[randIndForMultChoice[2]].getNameInGreek().equals("Βόρεια Ιρλανδία")) &&
				      (countries[correctIndex].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[correctIndex].getNameInGreek().equals("Βόρεια Ιρλανδία")));
			}

			if(wayOfAskingCurrentQuestion == 1)
			{
				textForQuestion.setText(questionsResourceBundle.getString("countriesFlagsWay1"));
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					correctAnswerString = countries[correctIndex].getNameInGreek();
					for(int i = 0; i < 3; i++)
						randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getNameInGreek();
				}
				else
				{
					correctAnswerString = countries[correctIndex].getNameInEnglish();
					for(int i = 0; i < 3; i++)
						randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getNameInEnglish();
				}
				imageViewForQuestionImage.setImage(getImageStuff().getImage(
						COUNTRY_FLAG, questionImageSize, countries[correctIndex].getNameInEnglish(), true));
				imageViewForQuestionImage.setImagePath(getImageStuff().getImagePath(
						COUNTRY_FLAG, questionImageSize, countries[correctIndex].getNameInEnglish()));
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				String s = getCurrentLanguage() == LANGUAGE.GREEK ? getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
						" " + countries[correctIndex].getGenitiveCaseOfCountry() : countries[correctIndex].getNameInEnglish();
				textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesFlagsWay2"), s));
				
				correctAnswerString = getImageStuff().getImagePath(COUNTRY_FLAG, answerImageSize, countries[correctIndex].getNameInEnglish());
				for(int i = 0; i < 3; i++)
					randStrForMultChoice[i] = getImageStuff().getImagePath(
							COUNTRY_FLAG, answerImageSize, countries[randIndForMultChoice[i]].getNameInEnglish());
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				int index = correctIndex;
				if(positionOfCorrectAnswer == 1)
				{
					do index = random.nextInt(countries.length);
					while(countries[correctIndex].hasEasyFlag() != countries[index].hasEasyFlag() || index == correctIndex ||
					(countries[index].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[index].getNameInGreek().equals("Βόρεια Ιρλανδία")) &&
					(countries[correctIndex].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[correctIndex].getNameInGreek().equals("Βόρεια Ιρλανδία")));
				}
				String s;
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					String article = countries[correctIndex].getArticleForCountry();
					s = article + " " + countries[correctIndex].getNameInGreek();
					if(isArticleInPlural(article)) s += " έχουν";
					else s += " έχει";
				}
				else s = countries[correctIndex].getNameInEnglish();
				textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesFlagsWay3"), s));
				
				imageViewForQuestionImage.setImage(getImageStuff().getImage(
						COUNTRY_FLAG, questionImageSize, countries[index].getNameInEnglish(), true));
				imageViewForQuestionImage.setImagePath(getImageStuff().getImagePath(COUNTRY_FLAG, questionImageSize, countries[index].getNameInEnglish()));
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
				do randIndForMultChoice[0] = random.nextInt(countries.length);
				while(randIndForMultChoice[0] == correctIndex ||
				      countries[correctIndex].askForCoatOfArms() != countries[randIndForMultChoice[0]].askForCoatOfArms() ||
				      (countries[randIndForMultChoice[0]].getNameInGreek().equals("Ηνωμένο Βασίλειο") ||
							  countries[randIndForMultChoice[0]].getNameInGreek().equals("Αγγλία") ||
				       countries[randIndForMultChoice[0]].getNameInGreek().equals("Βόρεια Ιρλανδία") ||
							  countries[randIndForMultChoice[0]].getNameInGreek().equals("Ουαλία")
				       ||countries[randIndForMultChoice[0]].getNameInGreek().equals("Σκωτία")) &&
				      (countries[correctIndex].getNameInGreek().equals("Ηνωμένο Βασίλειο") ||
							  countries[correctIndex].getNameInGreek().equals("Αγγλία")
				       || countries[correctIndex].getNameInGreek().equals("Βόρεια Ιρλανδία") ||
							  countries[correctIndex].getNameInGreek().equals("Ουαλία")
				       || countries[correctIndex].getNameInGreek().equals("Σκωτία")));
				do randIndForMultChoice[1] = random.nextInt(countries.length);
				while(randIndForMultChoice[1] == correctIndex ||
				      randIndForMultChoice[1] == randIndForMultChoice[0] ||
				      countries[correctIndex].askForCoatOfArms() != countries[randIndForMultChoice[1]].askForCoatOfArms() ||
				      (countries[randIndForMultChoice[0]].getNameInGreek().equals("Ηνωμένο Βασίλειο") ||
							  countries[randIndForMultChoice[0]].getNameInGreek().equals("Αγγλία") ||
				       countries[randIndForMultChoice[0]].getNameInGreek().equals("Βόρεια Ιρλανδία") ||
							  countries[randIndForMultChoice[0]].getNameInGreek().equals("Ουαλία")
				       || countries[randIndForMultChoice[0]].getNameInGreek().equals("Σκωτία")) &&
				      (countries[correctIndex].getNameInGreek().equals("Ηνωμένο Βασίλειο") ||
							  countries[correctIndex].getNameInGreek().equals("Αγγλία")
				       || countries[correctIndex].getNameInGreek().equals("Βόρεια Ιρλανδία") ||
							  countries[correctIndex].getNameInGreek().equals("Ουαλία")
				       || countries[correctIndex].getNameInGreek().equals("Σκωτία")));
				do randIndForMultChoice[2] = random.nextInt(countries.length);
				while(randIndForMultChoice[2] == correctIndex ||
				      randIndForMultChoice[2] == randIndForMultChoice[0] ||
				      randIndForMultChoice[2] == randIndForMultChoice[1] ||
				      countries[correctIndex].askForCoatOfArms() != countries[randIndForMultChoice[2]].askForCoatOfArms() ||
				      (countries[randIndForMultChoice[0]].getNameInGreek().equals("Ηνωμένο Βασίλειο") ||
							  countries[randIndForMultChoice[0]].getNameInGreek().equals("Αγγλία") ||
				       countries[randIndForMultChoice[0]].getNameInGreek().equals("Βόρεια Ιρλανδία") ||
							  countries[randIndForMultChoice[0]].getNameInGreek().equals("Ουαλία")
				       || countries[randIndForMultChoice[0]].getNameInGreek().equals("Σκωτία")) &&
				      (countries[correctIndex].getNameInGreek().equals("Ηνωμένο Βασίλειο") ||
							  countries[correctIndex].getNameInGreek().equals("Αγγλία")
				       || countries[correctIndex].getNameInGreek().equals("Βόρεια Ιρλανδία") ||
							  countries[correctIndex].getNameInGreek().equals("Ουαλία")
				       || countries[correctIndex].getNameInGreek().equals("Σκωτία")));
			}

			if(wayOfAskingCurrentQuestion == 1)
			{
				textForQuestion.setText(questionsResourceBundle.getString("countriesCoatOfArmsWay1"));
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					correctAnswerString = countries[correctIndex].getNameInGreek();
					for(int i = 0; i < 3; i++)
						randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getNameInGreek();
				}
				else
				{
					correctAnswerString = countries[correctIndex].getNameInEnglish();
					for(int i = 0; i < 3; i++)
						randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getNameInEnglish();
				}
				imageViewForQuestionImage.setImage(getImageStuff().getImage(
						COUNTRY_COAT_OF_ARMS, questionImageSize, countries[correctIndex].getNameInEnglish(), true));
				imageViewForQuestionImage.setImagePath(getImageStuff().getImagePath(
						COUNTRY_COAT_OF_ARMS, questionImageSize, countries[correctIndex].getNameInEnglish()));
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				String s = getCurrentLanguage() == LANGUAGE.GREEK ? getGenitive(countries[correctIndex].getArticleForCountry()).toLowerCase() +
						" " + countries[correctIndex].getGenitiveCaseOfCountry() : countries[correctIndex].getNameInEnglish();
				textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesCoatOfArmsWay2"), s));
				
				correctAnswerString = getImageStuff().getImagePath(COUNTRY_COAT_OF_ARMS, answerImageSize, countries[correctIndex].getNameInEnglish());
				for(int i = 0; i < 3; i++)
					randStrForMultChoice[i] = getImageStuff().getImagePath(
							COUNTRY_COAT_OF_ARMS, answerImageSize, countries[randIndForMultChoice[i]].getNameInEnglish());
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);

				String s;
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					String verb;
					String article = countries[correctIndex].getArticleForCountry();
					if(isArticleInPlural(article)) verb = " έχουν";
					else verb = " έχει";
					s = article + " " + countries[correctIndex].getNameInGreek() + verb;
				}
				else s = countries[correctIndex].getNameInEnglish();
				textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesCoatOfArmsWay3"), s));
				int index = correctIndex;
				if(positionOfCorrectAnswer == 1)
				{
					do index = random.nextInt(countries.length);
					while(countries[correctIndex].askForCoatOfArms() != countries[index].askForCoatOfArms() ||
					(countries[index].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[index].getNameInGreek().equals("Αγγλία") ||
					countries[index].getNameInGreek().equals("Βόρεια Ιρλανδία") || countries[index].getNameInGreek().equals("Ουαλία")
					||countries[index].getNameInGreek().equals("Σκωτία")) &&
					(countries[correctIndex].getNameInGreek().equals("Ηνωμένο Βασίλειο") || countries[correctIndex].getNameInGreek().equals("Αγγλία")
					|| countries[correctIndex].getNameInGreek().equals("Βόρεια Ιρλανδία") || countries[correctIndex].getNameInGreek().equals("Ουαλία")
					|| countries[correctIndex].getNameInGreek().equals("Σκωτία")) || index == correctIndex);
				}
				imageViewForQuestionImage.setImage(getImageStuff().getImage(
						COUNTRY_COAT_OF_ARMS, questionImageSize, countries[index].getNameInEnglish(), true));
				imageViewForQuestionImage.setImagePath(getImageStuff().getImagePath(
						COUNTRY_COAT_OF_ARMS, questionImageSize, countries[index].getNameInEnglish()));
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
				do randIndForMultChoice[0] = random.nextInt(countries.length);
				while(isIslandCountry(correctIndex) && (countries[correctIndex].isIslandCountry() != countries[randIndForMultChoice[0]].isIslandCountry()) ||
				      !haveSameContinent(correctIndex, randIndForMultChoice[0]) ||
				      randIndForMultChoice[0] == correctIndex);
				do randIndForMultChoice[1] = random.nextInt(countries.length);
				while(isIslandCountry(correctIndex) && (countries[correctIndex].isIslandCountry() != countries[randIndForMultChoice[1]].isIslandCountry()) ||
				      !haveSameContinent(correctIndex, randIndForMultChoice[1]) ||
				      randIndForMultChoice[1] == correctIndex ||
				      randIndForMultChoice[1] == randIndForMultChoice[0]);
				do randIndForMultChoice[2] = random.nextInt(countries.length);
				while(isIslandCountry(correctIndex) && (countries[correctIndex].isIslandCountry() != countries[randIndForMultChoice[2]].isIslandCountry()) ||
				      !haveSameContinent(correctIndex, randIndForMultChoice[2]) ||
				      randIndForMultChoice[2] == correctIndex ||
				      randIndForMultChoice[2] == randIndForMultChoice[0] ||
				      randIndForMultChoice[2] == randIndForMultChoice[1]);
			}

			if(wayOfAskingCurrentQuestion == 1)
			{
				textForQuestion.setText(questionsResourceBundle.getString("countriesLocationWay1"));
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					correctAnswerString = countries[correctIndex].getNameInGreek();
					for(int i = 0; i < 3; i++)
						randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getNameInGreek();
				}
				else
				{
					correctAnswerString = countries[correctIndex].getNameInEnglish();
					for(int i = 0; i < 3; i++)
						randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getNameInEnglish();
				}
				imageViewForQuestionImage.setImage(getImageStuff().getImage(
						COUNTRY_LOCATION, questionImageSize, countries[correctIndex].getNameInEnglish(), true));
				imageViewForQuestionImage.setImagePath(getImageStuff().getImagePath(
						COUNTRY_LOCATION, questionImageSize, countries[correctIndex].getNameInEnglish()));
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				String s;
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					String verb;
					String article = countries[correctIndex].getArticleForCountry();
					if(isArticleInPlural(article)) verb = "βρίσκονται ";
					else verb = "βρίσκεται ";
					s = verb + article.toLowerCase() + " " + countries[correctIndex].getNameInGreek();
				}
				else s = countries[correctIndex].getNameInEnglish();
				
				textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesLocationWay2"), s));
				
				correctAnswerString = getImageStuff().getImagePath(COUNTRY_LOCATION, answerImageSize, countries[correctIndex].getNameInEnglish());
				for(int i = 0; i < 3; i++)
					randStrForMultChoice[i] = getImageStuff().getImagePath(
							COUNTRY_LOCATION, answerImageSize, countries[randIndForMultChoice[i]].getNameInEnglish());

			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				
				String s;
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					s = countries[correctIndex].getArticleForCountry() + " " + countries[correctIndex].getNameInGreek();
					if(isArticleInPlural(countries[correctIndex].getArticleForCountry())) s += " τονίζονται";
					else s += " τονίζεται";
				}
				else s = countries[correctIndex].getNameInEnglish();
				textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesLocationWay3"), s));
				int index = correctIndex;
				if(positionOfCorrectAnswer == 1)
				{
					do index = random.nextInt(countries.length);
					while(isIslandCountry(correctIndex) && (countries[correctIndex].isIslandCountry() != countries[index].isIslandCountry()) ||
							!isIslandCountry(correctIndex) && countries[index].isIslandCountry() ||
							!haveSameContinent(correctIndex, index) || index == correctIndex);
				}
				imageViewForQuestionImage.setImage(getImageStuff().getImage(
						COUNTRY_LOCATION, questionImageSize, countries[index].getNameInEnglish(), true));
				imageViewForQuestionImage.setImagePath(getImageStuff().getImagePath(
						COUNTRY_LOCATION, questionImageSize, countries[index].getNameInEnglish()));
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
					do randIndForMultChoice[0] = random.nextInt(countries.length);
					while (!countries[randIndForMultChoice[0]].hasSea() ||
					       countries[randIndForMultChoice[0]].askGeographicalCharacteristics() == 0 ||
					       randIndForMultChoice[0] == correctIndex);
					do randIndForMultChoice[1] = random.nextInt(countries.length);
					while (!countries[randIndForMultChoice[1]].hasSea() ||
					       countries[randIndForMultChoice[1]].askGeographicalCharacteristics() == 0 ||
					       randIndForMultChoice[1] == correctIndex ||
					       randIndForMultChoice[1] == randIndForMultChoice[0]);
					do randIndForMultChoice[2] = random.nextInt(countries.length);
					while (!countries[randIndForMultChoice[2]].hasSea() ||
					       countries[randIndForMultChoice[2]].askGeographicalCharacteristics() == 0 ||
					       randIndForMultChoice[2] == correctIndex ||
					       randIndForMultChoice[2] == randIndForMultChoice[0] ||
					       randIndForMultChoice[2] == randIndForMultChoice[1]);
				}
				else if(category == 1)
				{
					do randIndForMultChoice[0] = random.nextInt(countries.length);
					while (countries[randIndForMultChoice[0]].hasSea() ||
					       randIndForMultChoice[0] == correctIndex);
					do randIndForMultChoice[1] = random.nextInt(countries.length);
					while (countries[randIndForMultChoice[1]].hasSea() ||
					       randIndForMultChoice[1] == correctIndex ||
					       randIndForMultChoice[1] == randIndForMultChoice[0]);
					do randIndForMultChoice[2] = random.nextInt(countries.length);
					while (countries[randIndForMultChoice[2]].hasSea() ||
					       randIndForMultChoice[2] == correctIndex ||
					       randIndForMultChoice[2] == randIndForMultChoice[0] ||
					       randIndForMultChoice[2] == randIndForMultChoice[1]);
				}
				else
				{
					do randIndForMultChoice[0] = random.nextInt(countries.length);
					while (countries[randIndForMultChoice[0]].isIslandCountry() ||
					       randIndForMultChoice[0] == correctIndex);
					do randIndForMultChoice[1] = random.nextInt(countries.length);
					while (countries[randIndForMultChoice[1]].isIslandCountry() ||
					       randIndForMultChoice[1] == correctIndex ||
					       randIndForMultChoice[1] == randIndForMultChoice[0]);
					do randIndForMultChoice[2] = random.nextInt(countries.length);
					while (countries[randIndForMultChoice[2]].isIslandCountry() ||
					       randIndForMultChoice[2] == correctIndex ||
					       randIndForMultChoice[2] == randIndForMultChoice[0] ||
					       randIndForMultChoice[2] == randIndForMultChoice[1]);
				}
				if(category == 0) textForQuestion.setText(questionsResourceBundle.getString("countriesLocationAlternativeWay1_0"));
				else if(category == 1) textForQuestion.setText(questionsResourceBundle.getString("countriesLocationAlternativeWay1_1"));
				else textForQuestion.setText(questionsResourceBundle.getString("countriesLocationAlternativeWay1_2"));
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					correctAnswerString = countries[correctIndex].getNameInGreek();
					for(int i = 0; i < 3; i++) randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getNameInGreek();
				}
				else
				{
					correctAnswerString = countries[correctIndex].getNameInEnglish();
					for(int i = 0; i < 3; i++) randStrForMultChoice[i] = countries[randIndForMultChoice[i]].getNameInEnglish();
				}
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				
				boolean type1 = random.nextBoolean();
				String s, verb = "";
				if (getCurrentLanguage() == LANGUAGE.GREEK)
				{
					String article = countries[correctIndex].getArticleForCountry();
					if(isArticleInPlural(article)) verb = " βρέχονται";
					else verb = " βρέχεται";
					s = article + " " + countries[correctIndex].getNameInGreek();
				}
				else s = countries[correctIndex].getNameInEnglish();
				
				if(positionOfCorrectAnswer == 0 && category == 0 && type1 ||
					positionOfCorrectAnswer == 1 && category == 1 && type1 ||
					positionOfCorrectAnswer == 1 && category == 2 && !type1)
							textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesLocationAlternativeWay2_landlocked"),
									s, verb));
				else if(positionOfCorrectAnswer == 0 && category == 0 && !type1 ||
						positionOfCorrectAnswer == 0 && category == 1 && !type1 ||
						positionOfCorrectAnswer == 1 && category == 2 && type1)
							textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesLocationAlternativeWay2_notIsland"), s));
				else if(positionOfCorrectAnswer == 0 && category == 1 && type1 ||
						positionOfCorrectAnswer == 0 && category == 2 && !type1 ||
						positionOfCorrectAnswer == 1 && category == 0 && type1)
							textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesLocationAlternativeWay2_notLandlocked"),
									s + verb));
				else textForQuestion.setText(String.format(questionsResourceBundle.getString("countriesLocationAlternativeWay2_island"), s));
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
				do randIndForMultChoice[0] = random.nextInt(continents.length);
				while(randIndForMultChoice[0] == correctIndex);
				do randIndForMultChoice[1] = random.nextInt(continents.length);
				while(randIndForMultChoice[1] == correctIndex ||
				      randIndForMultChoice[1] == randIndForMultChoice[0]);
				do randIndForMultChoice[2] = random.nextInt(continents.length);
				while(randIndForMultChoice[2] == correctIndex ||
				      randIndForMultChoice[2] == randIndForMultChoice[0] ||
				      randIndForMultChoice[2] == randIndForMultChoice[1]);
			}
			
			String cont;
			if (getCurrentLanguage() == LANGUAGE.GREEK) cont = continents[correctIndex].getNameInGreek();
			else cont = continents[correctIndex].getNameInEnglish();
			
			if(wayOfAskingCurrentQuestion == 1)
			{
				if(getCurrentLanguage() == LANGUAGE.GREEK)
				{
					correctAnswerString = continents[correctIndex].getNameInGreek();
					for(int i = 0; i < 3; i++) randStrForMultChoice[i] = continents[randIndForMultChoice[i]].getNameInGreek();
				}
				else
				{
					correctAnswerString = continents[correctIndex].getNameInEnglish();
					for(int i = 0; i < 3; i++) randStrForMultChoice[i] = continents[randIndForMultChoice[i]].getNameInEnglish();
				}
				textForQuestion.setText(questionsResourceBundle.getString("continentsLocationWay1"));
				imageViewForQuestionImage.setImage(getImageStuff().getImage(
						CONTINENT_LOCATION, questionImageSize, continents[correctIndex].getNameInEnglish(), true));
				imageViewForQuestionImage.setImagePath(getImageStuff().getImagePath(
						CONTINENT_LOCATION, questionImageSize, continents[correctIndex].getNameInEnglish()));
			}
			else if(wayOfAskingCurrentQuestion == 2)
			{
				textForQuestion.setText(String.format(questionsResourceBundle.getString("continentsLocationWay2"), cont));
				correctAnswerString = getImageStuff().getImagePath(
						CONTINENT_LOCATION, answerImageSize, continents[correctIndex].getNameInEnglish());
				for(int i = 0; i < 3; i++)
					randStrForMultChoice[i] = getImageStuff().getImagePath(
							CONTINENT_LOCATION, answerImageSize,
							continents[randIndForMultChoice[i]].getNameInEnglish());
			}
			else
			{
				positionOfCorrectAnswer = random.nextInt(2);
				int index = correctIndex;
				if(positionOfCorrectAnswer == 1)
				{
					do index = random.nextInt(continents.length);
					while(index == correctIndex);
				}
				imageViewForQuestionImage.setImage(getImageStuff().getImage(CONTINENT_LOCATION,
						questionImageSize, continents[index].getNameInEnglish(), true));
				imageViewForQuestionImage.setImagePath(getImageStuff().getImagePath(CONTINENT_LOCATION,
						questionImageSize, continents[index].getNameInEnglish()));
				textForQuestion.setText(String.format(questionsResourceBundle.getString("continentsLocationWay3"), cont));
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
			
			List<Integer> list = new ArrayList<>(4);
			list.add(0);list.add(1);list.add(2);list.add(3);
			list.remove(positionOfCorrectAnswer);
			
			radioButtonsFor4TextAnswers[list.get(0)].setText(randStrForMultChoice[0]);
			radioButtonsFor4TextAnswers[list.get(1)].setText(randStrForMultChoice[1]);
			radioButtonsFor4TextAnswers[list.get(2)].setText(randStrForMultChoice[2]);
			
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
			imageViewFor4ImageAnswers[positionOfCorrectAnswer].getImageView().setImage(new Image(correctAnswerString, true));
			imageViewFor4ImageAnswers[positionOfCorrectAnswer].getImageView().setImagePath(correctAnswerString);
			
			List<Integer> list = new ArrayList<>(4);
			list.add(0);list.add(1);list.add(2);list.add(3);
			list.remove(positionOfCorrectAnswer);
			
			imageViewFor4ImageAnswers[list.get(0)].getImageView().setImage(new Image(randStrForMultChoice[0], true));
			imageViewFor4ImageAnswers[list.get(1)].getImageView().setImage(new Image(randStrForMultChoice[1], true));
			imageViewFor4ImageAnswers[list.get(2)].getImageView().setImage(new Image(randStrForMultChoice[2], true));
			
			imageViewFor4ImageAnswers[list.get(0)].getImageView().setImagePath(randStrForMultChoice[0]);
			imageViewFor4ImageAnswers[list.get(1)].getImageView().setImagePath(randStrForMultChoice[1]);
			imageViewFor4ImageAnswers[list.get(2)].getImageView().setImagePath(randStrForMultChoice[2]);
			
			for(int i = 0; i < imageViewFor4ImageAnswers.length; i++)
			{
				if(imageViewFor4ImageAnswers[i].getImageView().getImagePath().contains("coatOfArms") &&
					getImageStuff().requiresWhiteBackground(imageViewFor4ImageAnswers[i].getImageView().getImagePath()))
				{
					imageViewFor4ImageAnswers[i].setWhiteBackground();
				}
				else
					imageViewFor4ImageAnswers[i].setTransparentBackground();
			}
		}

		private void setImageSizes()
		{
			if(imageViewForQuestionImage.getFitHeight() <= getImageStuff().MAX_WIDTH_FOR_X250_IMAGES) questionImageSize = "x250";
			else if(imageViewForQuestionImage.getFitHeight() <= getImageStuff().MAX_WIDTH_FOR_X500_IMAGES) questionImageSize = "x500";
			else questionImageSize = "x1000";

			if(imageViewFor4ImageAnswers[0].getImageView().getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X250_IMAGES) answerImageSize = "x250";
			else if(imageViewFor4ImageAnswers[0].getImageView().getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X500_IMAGES) answerImageSize = "x500";
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
		
		private String getNum(int pos)
		{
			String res = "";
			if(pos > 1)
			{
				if(getCurrentLanguage() == LANGUAGE.GREEK)
					res = " " + pos + "η";
				if(getCurrentLanguage() == LANGUAGE.ENGLISH)
				{
					if(pos == 2)
						res = " 2nd";
					else if(pos == 3)
						res = " 3rd";
					else
						res = " " + pos + "th";
				}
			}
			return res;
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
	
	private void showNextQuestionButton()
	{
		nextQuestionButton.setDisable(false);
		startTimelineFor2_5SecondsWait();
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

	public void setGameMode(GAMEMODE gameMode)
	{
		this.gameMode = gameMode;
	}
	
	public class ImageViewWithBackground extends StackPane
	{
		private CustomImageView imageView, answerIcon;
		private boolean hasWhiteBackground;
		
		public ImageViewWithBackground()
		{
			setPickOnBounds(true);
			setCursor(Cursor.HAND);
			imageView = new CustomImageView(true, true, true, false, null);
			answerIcon = new CustomImageView(true, true, false, false, null);
			getChildren().addAll(imageView, answerIcon);
		}
		
		public CustomImageView getImageView()
		{
			return imageView;
		}
		
		public CustomImageView getAnswerIcon()
		{
			return answerIcon;
		}
		
		public void setWhiteBackground()
		{
			hasWhiteBackground = true;
			setStyle(WHITE_BACKGROUND_DARK_TRANSPARENT + BLACK_BORDERED + cssBackgroundAndBorderMedium + cssPadding10);
		}
		
		public void setTransparentBackground()
		{
			hasWhiteBackground = false;
			setStyle(BLACK_BACKGROUND_DARK_TRANSPARENT + BLACK_BORDERED + cssBackgroundAndBorderMedium + cssPadding10);
		}
		
		public boolean hasWhiteBackground()
		{
			return hasWhiteBackground;
		}
	}
	
	public void pauseComboAnimation()
	{
		parallelTransitionForTextForCombo.pause();
	}
	
	public void resumeComboAnimation()
	{
		parallelTransitionForTextForCombo.play();
	}
}
