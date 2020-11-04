package com.johnzaro.geographyquiz.screens;

import com.johnzaro.geographyquiz.core.*;
import com.johnzaro.geographyquiz.core.ImageStuff.Images;
import com.johnzaro.geographyquiz.core.customNodes.*;
import com.johnzaro.geographyquiz.dataStructures.Player;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.controlsfx.control.SegmentedButton;

import java.util.Locale;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;
import static com.johnzaro.geographyquiz.core.GlobalVariables.getCurrentLanguage;
import static com.johnzaro.geographyquiz.core.helperClasses.GreekLanguageHelper.getEditedOriginalName;
import static com.johnzaro.geographyquiz.core.helperClasses.GreekLanguageHelper.getStringWithoutTones;

public class WelcomeScreen extends CoreScreen
{
	private ObservableList<String> playersObservableList, countriesLocalesObservableList, languagesObservableList;
	private ObservableList<Short> countriesLocalesSortList;
	private ObservableList<String> emptyObservableList;

	private CustomHBox hBoxForEditPlayerNameButtons, hBoxForSwitchPlayerButtons, hBoxForAddPlayerButtons, hBoxForDeleteSavedNames,
			hBoxForLanguageSelection, hBoxForUnitOfLengthSelection, hBoxForUnitOfLengthRadioButtons,
			hBoxForSettingsAndInfoIcons, hBoxForCountrySelection, hBoxForPopUpConfirmationButtons;
	private CustomVBox vBoxForSettings, vBoxForAnimationsUsedSettings, vBoxForMainButtons, vBoxForEditPlayerName, vBoxForSwitchPlayer, vBoxForAddPlayer, vBoxForPopUpAskConfirmation;
	private CustomLabel welcomeLabel, animationsUsedLabel, popUpAskConfirmationLabel, popUpMessageLabel, editPlayerLabel, switchPlayerLabel, addPlayerLabel,
			selectCountryLabel, selectLanguageLabel, selectUnitSystemLabel;
	private CustomRadioButton metricSystemRadioButton, imperialSystemRadioButton;
	private CustomTextField textFieldForEditPlayerName, textFieldForAddPlayer;
	private CustomImageView chalkboardBackgroundImage, gameNameWoodenImage, welcomeImage,
			leftGlobeStand, rightGlobeStand, leftGlobeImage, rightGlobeImage,
			editPlayerIcon, switchPlayerIcon, addPlayerIcon;
	private CustomButton editUsersButton, deleteSingleUserButton, deleteAllUsersButton, singlePlayerGameButton, multiPlayerGameButton, atlasButton,
			scoreBoardButton, cancelEditPlayerNameButton, cancelSwitchPlayerButton, cancelAddPlayerButton, confirmEditPlayerNameButton, confirmSwitchPlayerButton,
			confirmAddPlayerButton, deleteAllDataButton, cancelPopUpActionButton, confirmPopUpActionButton, settingsButton, infoButton;
	private CustomCheckBox startInFullScreenCheckBox, confirmDeleteCheckBox;
	private CustomComboBox<String> playersComboBox, countriesComboBox, languageComboBox;
	private CustomRectangle rectangleForInformationAboutGame;
	private CustomToggleButton noAnimationsToggleButton, limitedAnimationsToggleButton, allAnimationsToggleButton, editPlayerToggleButton, switchPlayerToggleButton, addPlayerToggleButton;
	private SegmentedButton animationsUsedSegmentedButton, playersEditSegmentedButton;
	private ToggleGroup toggleGroupForAnimationsUsedSegmentedButton, toggleGroupForUsersEditSegmentedButton, toggleGroupForUnitsOfLength;

	private ScaleTransition scaleTransitionForInfoButton,
			scaleTransitionForWelcomeLabel, scaleTransitionForEditIcon, scaleTransitionForRectangleForInfoAboutGame, scaleTransitionForVBoxForSettings, scaleTransitionForSettingsIcon,
			scaleTransitionForPopUpMessage, scaleTransitionForPopUpAskConfirmation, scaleTransitionForVBoxForEditUsername, scaleTransitionForVBoxForSwitchUser, scaleTransitionForVBoxForAddUser,
			scaleTransitionForUsersEditSegmentedButton;
	private SequentialTransition sequentialTransitionForOnePlayerGameButton, sequentialTransitionForTwoPlayersGameButton, sequentialTransitionForAtlasButton,
			sequentialTransitionForScoreBoardButton, sequentialTransitionForInfoButton, sequentialTransitionForSettingsButton;
	private TranslateTransition translateTransitionForVBoxWithMainButtons,
			translateTransitionForGameNameImage, translateTransitionForLeftGlobeStand,
			translateTransitionForLeftGlobeImage, translateTransitionForRightGlobeStand,
			translateTransitionForRightGlobeImage;
	private ParallelTransition parallelTransitionForWelcomeImage, parallelTransitionForWelcomeLabel;
	private FadeTransition fadeInTransition;
	private Timeline timelineForLeftGlobe, timelineForRightGlobe, timelineToPopUpMainButtons,
			timelineToShowEditUsersVBox, timelineToHideEditUsersVBox,
			timelineToShowSettings, timelineToHideSettings, timelineToChangeUsersEditVBoxes,
			timelineToShowInformationAboutGame, timelineToHideInformationAboutGame,
			timelineToChangeLanguage, timelineToChangeLanguageByPlayer,
			timelineToShowPopUpMessage, timelineToShowConfirmationMessage,
			timelineToCancelConfirmationMessage;

	private DropShadow leftEarthShadow, rightEarthShadow, rightStandShadow;

	private int leftGlobeStatus, rightGlobeStatus, leftGlobeCounter = 1, rightGlobeCounter = 1;
	private boolean previousTransitionOfLeftGlobeWasRight, previousTransitionOfRightGlobeWasRight;

	private enum POP_UP_ACTION { DELETE_SINGLE_USER, DELETE_ALL_USERS, DELETE_ALL_DATA, NULL}
	private POP_UP_ACTION popUpAction;

	private boolean firstTime = true;

	protected void recalculateUI(double width, double height)
	{
		super.recalculateUI(width, height);
		
		if (width < 1200)
		{
			metricSystemRadioButton.setId("small");
			imperialSystemRadioButton.setId("small");
		}
		else
		{
			metricSystemRadioButton.setId("big");
			imperialSystemRadioButton.setId("big");
		}
		
		gameNameWoodenImage.setFitWidth(0.625 * width);
		gameNameWoodenImage.setLayoutX(width / 2.0 - gameNameWoodenImage.getFitWidth() / 2.0);
		gameNameWoodenImage.setLayoutY(ratioProperties.getWelcome().getGameNameImageLayoutY() * height);
		
		welcomeImage.setFitWidth(0.5156 * width);
		welcomeImage.setLayoutX(width / 2.0 - welcomeImage.getFitWidth() / 2.0);
		welcomeImage.setLayoutY(ratioProperties.getWelcome().getWelcomeImageLayoutY() * height);
		
		welcomeLabel.setPrefSize(0.5104 * width, 0.1248 * height);
		welcomeLabel.setLayoutX(0.2448 * width);
		welcomeLabel.setLayoutY(ratioProperties.getWelcome().getWelcomeLabelLayoutY() * height);
		welcomeLabel.getTooltip().setFont(font25P);
		
		woodPanelFor1IconImage.setLayoutX(ratioProperties.getWelcome().getWoodPanelFor1IconImageLayoutX() * width);
		woodPanelFor1IconImage.setLayoutY(ratioProperties.getWelcome().getWoodPanelFor1IconImageLayoutY() * height);
		
		vBoxForSound.setLayoutX(soundButton.getLayoutX() + soundButton.getFitWidth() / 2.0 - vBoxForSound.getPrefWidth() / 2.0);
		vBoxForSound.setLayoutY(ratioProperties.getWelcome().getvBoxForSoundLayoutY() * height);
		if(vBoxForSound.getTranslateX() != 0) vBoxForSound.setTranslateX(width - vBoxForSound.getLayoutX() + 20);
		
		soundButton.setLayoutY(ratioProperties.getWelcome().getSoundIconLayoutY() * height);
		
		leftGlobeStand.setFitWidth(ratioProperties.getWelcome().getGlobeStandFitWidth() * width);
		leftGlobeStand.setLayoutX(ratioProperties.getWelcome().getLeftGlobeStandLayoutX() * width);
		leftGlobeStand.setLayoutY(ratioProperties.getWelcome().getGlobeStandLayoutY() * height);
		
		rightStandShadow.setRadius(shadowRadius);
		rightStandShadow.setOffsetX(-1.0 * shadowOffset);
		rightStandShadow.setOffsetY(shadowOffset);
		rightGlobeStand.setFitWidth(ratioProperties.getWelcome().getGlobeStandFitWidth() * width);
		rightGlobeStand.setLayoutX(width - leftGlobeStand.getLayoutX() - rightGlobeStand.getFitWidth());
		rightGlobeStand.setLayoutY(ratioProperties.getWelcome().getGlobeStandLayoutY() * height);
		
		leftEarthShadow.setRadius(0.0104 * width);
		leftEarthShadow.setOffsetX(-0.0013 * width);
		leftEarthShadow.setOffsetY(-0.0026 * width);
		leftGlobeImage.setFitWidth(ratioProperties.getWelcome().getGlobeImageFitWidth() * width);
		leftGlobeImage.setLayoutX(ratioProperties.getWelcome().getLeftGlobeImageLayoutX() * width);
		leftGlobeImage.setLayoutY(ratioProperties.getWelcome().getGlobeImageLayoutY() * height);
		
		rightEarthShadow.setRadius(0.0104 * width);
		rightEarthShadow.setOffsetX(-0.0026 * width);
		rightEarthShadow.setOffsetY(-0.0013 * width);
		rightGlobeImage.setFitWidth(ratioProperties.getWelcome().getGlobeImageFitWidth() * width);
		rightGlobeImage.setLayoutX(ratioProperties.getWelcome().getRightGlobeImageLayoutX() * width);
		rightGlobeImage.setLayoutY(ratioProperties.getWelcome().getGlobeImageLayoutY() * height);
		
		vBoxForMainButtons.setPrefSize(ratioProperties.getWelcome().getvBoxForMainButtonsPrefWidth() * width, ratioProperties.getWelcome().getvBoxForMainButtonsPrefHeight() * height);
		vBoxForMainButtons.setLayoutX(width / 2.0 - vBoxForMainButtons.getPrefWidth() / 2.0);
		vBoxForMainButtons.setLayoutY(ratioProperties.getWelcome().getvBoxForMainButtonsLayoutY() * height);
		vBoxForMainButtons.setSpacing(ratioProperties.getWelcome().getvBoxForMainButtonsSpacing() * height);
		if(vBoxForMainButtons.getTranslateY() != 0) vBoxForMainButtons.setTranslateY(height - vBoxForMainButtons.getLayoutY() + 20);
		
		singlePlayerGameButton.setFitHeight(mediumIconSize);
		singlePlayerGameButton.setPrefWidth(vBoxForMainButtons.getPrefWidth());
		singlePlayerGameButton.setPrefHeight((vBoxForMainButtons.getPrefHeight() - 3 * vBoxForMainButtons.getSpacing()) / 4.0);
		singlePlayerGameButton.setFont(font45B);
		singlePlayerGameButton.getTooltip().setFont(font25P);
		
		multiPlayerGameButton.setFitHeight(mediumIconSize);
		multiPlayerGameButton.setPrefWidth(vBoxForMainButtons.getPrefWidth());
		multiPlayerGameButton.setPrefHeight((vBoxForMainButtons.getPrefHeight() - 3 * vBoxForMainButtons.getSpacing()) / 4.0);
		multiPlayerGameButton.setFont(font45B);
		multiPlayerGameButton.getTooltip().setFont(font25P);
		
		atlasButton.setFitHeight(mediumIconSize);
		atlasButton.setPrefWidth(vBoxForMainButtons.getPrefWidth());
		atlasButton.setPrefHeight((vBoxForMainButtons.getPrefHeight() - 3 * vBoxForMainButtons.getSpacing()) / 4.0);
		atlasButton.setFont(font45B);
		atlasButton.getTooltip().setFont(font25P);
		
		scoreBoardButton.setFitHeight(mediumIconSize);
		scoreBoardButton.setPrefWidth(vBoxForMainButtons.getPrefWidth());
		scoreBoardButton.setPrefHeight((vBoxForMainButtons.getPrefHeight() - 3 * vBoxForMainButtons.getSpacing()) / 4.0);
		scoreBoardButton.setFont(font45B);
		scoreBoardButton.getTooltip().setFont(font25P);
		
		editUsersButton.setFitWidth(mediumIconSize);
		editUsersButton.setLayoutX(0.7656 * width);
		editUsersButton.setLayoutY(ratioProperties.getWelcome().getEditNameIconLayoutY() * height);
		editUsersButton.getTooltip().setFont(font25P);
		
		vBoxForEditPlayerName.setPrefWidth(0.4688 * width);
		vBoxForEditPlayerName.setLayoutX(width / 2.0 - vBoxForEditPlayerName.getPrefWidth() / 2.0);
		vBoxForEditPlayerName.setLayoutY(ratioProperties.getWelcome().getvBoxesForEditUsersLayoutY() * height);
		vBoxForEditPlayerName.setSpacing(0.0278 * height);
		vBoxForEditPlayerName.setStyle(cssBackgroundAndBorderBig + cssPadding30);
		editPlayerToggleButton.setPrefWidth(vBoxForEditPlayerName.getPrefWidth() / 3.0);
		editPlayerToggleButton.setFont(font25P);
		editPlayerLabel.setFont(font45B);
		editPlayerIcon.setFitHeight(smallIconSize);
		textFieldForEditPlayerName.setPrefWidth(vBoxForEditPlayerName.getPrefWidth());
		textFieldForEditPlayerName.setFont(font35P);
		textFieldForEditPlayerName.getTooltip().setFont(font25P);
		hBoxForEditPlayerNameButtons.setPrefWidth(vBoxForEditPlayerName.getPrefWidth());
		hBoxForEditPlayerNameButtons.setSpacing(0.0130 * width);
		cancelEditPlayerNameButton.setPrefWidth(hBoxForEditPlayerNameButtons.getPrefWidth() / 2.0);
		cancelEditPlayerNameButton.setFont(font45B);
		cancelEditPlayerNameButton.getTooltip().setFont(font25P);
		confirmEditPlayerNameButton.setPrefWidth(hBoxForEditPlayerNameButtons.getPrefWidth() / 2.0);
		confirmEditPlayerNameButton.setFont(font45B);
		confirmEditPlayerNameButton.getTooltip().setFont(font25P);
		
		
		vBoxForSwitchPlayer.setPrefWidth(vBoxForEditPlayerName.getPrefWidth());
		vBoxForSwitchPlayer.setLayoutX(width / 2.0 - vBoxForSwitchPlayer.getPrefWidth() / 2.0);
		vBoxForSwitchPlayer.setLayoutY(ratioProperties.getWelcome().getvBoxesForEditUsersLayoutY() * height);
		vBoxForSwitchPlayer.setSpacing(0.0278 * height);
		vBoxForSwitchPlayer.setStyle(cssBackgroundAndBorderBig + cssPadding30);
		playersComboBox.setPrefWidth(vBoxForEditPlayerName.getPrefWidth());
		playersComboBox.setStyle(cssFont30P);
		switchPlayerIcon.setFitHeight(smallIconSize);
		hBoxForDeleteSavedNames.setPrefWidth(vBoxForEditPlayerName.getPrefWidth());
		hBoxForDeleteSavedNames.setSpacing(0.0130 * width);
		deleteSingleUserButton.setPrefWidth(0.5 * hBoxForDeleteSavedNames.getPrefWidth());
		deleteSingleUserButton.setFont(font35P);
		deleteSingleUserButton.getTooltip().setFont(font25P);
		deleteAllUsersButton.setPrefWidth(0.5 * hBoxForDeleteSavedNames.getPrefWidth());
		deleteAllUsersButton.setFont(font35P);
		deleteAllUsersButton.getTooltip().setFont(font25P);
		switchPlayerToggleButton.setPrefWidth(vBoxForEditPlayerName.getPrefWidth() / 3.0);
		switchPlayerToggleButton.setFont(font25P);
		switchPlayerLabel.setFont(font45B);
		hBoxForSwitchPlayerButtons.setPrefWidth(vBoxForSwitchPlayer.getPrefWidth());
		hBoxForSwitchPlayerButtons.setSpacing(0.0130 * width);
		cancelSwitchPlayerButton.setPrefWidth(hBoxForSwitchPlayerButtons.getPrefWidth() / 2.0);
		cancelSwitchPlayerButton.setFont(font45B);
		cancelSwitchPlayerButton.getTooltip().setFont(font25P);
		confirmSwitchPlayerButton.setPrefWidth(hBoxForSwitchPlayerButtons.getPrefWidth() / 2.0);
		confirmSwitchPlayerButton.setFont(font45B);
		confirmSwitchPlayerButton.getTooltip().setFont(font25P);
		
		addPlayerIcon.setFitHeight(smallIconSize);
		vBoxForAddPlayer.setPrefWidth(vBoxForEditPlayerName.getPrefWidth());
		vBoxForAddPlayer.setLayoutX(width / 2.0 - vBoxForAddPlayer.getPrefWidth() / 2.0);
		vBoxForAddPlayer.setLayoutY(ratioProperties.getWelcome().getvBoxesForEditUsersLayoutY() * height);
		vBoxForAddPlayer.setSpacing(0.0278 * height);
		vBoxForAddPlayer.setStyle(cssBackgroundAndBorderBig + cssPadding30);
		textFieldForAddPlayer.setPrefWidth(vBoxForAddPlayer.getPrefWidth());
		textFieldForAddPlayer.setFont(font35P);
		textFieldForAddPlayer.getTooltip().setFont(font25P);
		addPlayerToggleButton.setPrefWidth(vBoxForEditPlayerName.getPrefWidth() / 3.0);
		addPlayerToggleButton.setFont(font25P);
		addPlayerLabel.setFont(font45B);
		hBoxForAddPlayerButtons.setPrefWidth(vBoxForAddPlayer.getPrefWidth());
		hBoxForAddPlayerButtons.setSpacing(0.0130 * width);
		cancelAddPlayerButton.setPrefWidth(hBoxForAddPlayerButtons.getPrefWidth() / 2.0);
		cancelAddPlayerButton.setFont(font45B);
		cancelAddPlayerButton.getTooltip().setFont(font25P);
		confirmAddPlayerButton.setPrefWidth(hBoxForAddPlayerButtons.getPrefWidth() / 2.0);
		confirmAddPlayerButton.setFont(font45B);
		confirmAddPlayerButton.getTooltip().setFont(font25P);
		
		playersEditSegmentedButton.setLayoutX(width / 2.0 - vBoxForEditPlayerName.getPrefWidth() / 2.0);
		playersEditSegmentedButton.setLayoutY(ratioProperties.getWelcome().getUsersEditSegmentedButtonLayoutY() * height);
		
		hBoxForSettingsAndInfoIcons.setLayoutX(0.7296 * width);
		hBoxForSettingsAndInfoIcons.setLayoutY(ratioProperties.getWelcome().gethBoxForSettingsAndInfoIconsLayoutY() * height);
		hBoxForSettingsAndInfoIcons.setSpacing(0.0100 * width);
		
		settingsButton.setFitWidth(veryLargeIconSize);
		settingsButton.getTooltip().setFont(font25P);
		infoButton.setFitWidth(veryLargeIconSize);
		infoButton.getTooltip().setFont(font25P);
		
		rectangleForInformationAboutGame.setWidth(0.4427 * width);
		rectangleForInformationAboutGame.setHeight(0.6018 * height);
		rectangleForInformationAboutGame.setLayoutX(width / 2.0 - rectangleForInformationAboutGame.getWidth() / 2.0);
		rectangleForInformationAboutGame.setLayoutY(ratioProperties.getWelcome().getRectangleForInfoAboutGameLayoutY() * height);
		rectangleForInformationAboutGame.setArcWidth(0.0208 * width);
		rectangleForInformationAboutGame.setArcHeight(0.0208 * width);
		rectangleForInformationAboutGame.setStrokeWidth(0.0042 * width);
		
		vBoxForSettings.setPrefSize(0.5208 * width, 0.5741 * height);
		vBoxForSettings.setLayoutX(width / 2.0 - vBoxForSettings.getPrefWidth() / 2.0);
		vBoxForSettings.setLayoutY(ratioProperties.getWelcome().getvBoxForSettingsLayoutY() * height);
		vBoxForSettings.setStyle(cssBackgroundAndBorderBig + cssPadding30);
		vBoxForSettings.setSpacing(0.0278 * height);
		
		startInFullScreenCheckBox.setFont(font30P);
		
		animationsUsedLabel.setFont(font35P);
		noAnimationsToggleButton.setPrefWidth(vBoxForSettings.getPrefWidth() / 3.0);
		noAnimationsToggleButton.setFont(font25P);
		allAnimationsToggleButton.setPrefWidth(vBoxForSettings.getPrefWidth() / 3.0);
		allAnimationsToggleButton.setFont(font25P);
		limitedAnimationsToggleButton.setPrefWidth(vBoxForSettings.getPrefWidth() / 3.0);
		limitedAnimationsToggleButton.setFont(font25P);
		vBoxForAnimationsUsedSettings.setSpacing(0.0093 * height);
		
		hBoxForCountrySelection.setPrefWidth(vBoxForSettings.getPrefWidth());
		hBoxForCountrySelection.setSpacing(0.0052 * width);
		selectCountryLabel.setPrefWidth(0.4 * hBoxForCountrySelection.getPrefWidth());
		selectCountryLabel.setFont(font35P);
		countriesComboBox.setPrefWidth(0.6 * hBoxForCountrySelection.getPrefWidth());
		countriesComboBox.setStyle(cssFont25P);
		
		hBoxForLanguageSelection.setPrefWidth(vBoxForSettings.getPrefWidth());
		hBoxForLanguageSelection.setSpacing(0.0052 * width);
		selectLanguageLabel.setPrefWidth(0.4 * hBoxForLanguageSelection.getPrefWidth());
		selectLanguageLabel.setFont(font35P);
		languageComboBox.setPrefWidth(0.6 * hBoxForLanguageSelection.getPrefWidth());
		languageComboBox.setStyle(cssFont25P);
		
		hBoxForUnitOfLengthSelection.setPrefWidth(vBoxForSettings.getPrefWidth());
		hBoxForUnitOfLengthSelection.setSpacing(0.0052 * width);
		hBoxForUnitOfLengthRadioButtons.setPrefWidth(0.6 * hBoxForUnitOfLengthSelection.getPrefWidth());
		hBoxForUnitOfLengthRadioButtons.setSpacing(0.0208 * width);
		selectUnitSystemLabel.setPrefWidth(0.4 * hBoxForUnitOfLengthSelection.getPrefWidth());
		selectUnitSystemLabel.setFont(font35P);
		metricSystemRadioButton.setFont(font30P);
		imperialSystemRadioButton.setFont(font30P);
		
		deleteAllDataButton.setPrefSize(0.2865 * width, 0.0741 * height);
		deleteAllDataButton.setFont(font30P);
		deleteAllDataButton.getTooltip().setFont(font25P);
		
		popUpMessageLabel.setPrefSize(0.2864 * width, 0.2315 * height);
		popUpMessageLabel.setLayoutX(width / 2.0 - popUpMessageLabel.getPrefWidth() / 2.0);
		popUpMessageLabel.setLayoutY(height / 2.0 - popUpMessageLabel.getPrefHeight() / 2.0);
		popUpMessageLabel.setStyle(cssBackgroundAndBorderBig + cssPadding10);
		popUpMessageLabel.setFont(font40P);
		vBoxForPopUpAskConfirmation.setPrefSize(0.5800 * width, 0.4800 * height);
		vBoxForPopUpAskConfirmation.setLayoutX(width / 2.0 - vBoxForPopUpAskConfirmation.getPrefWidth() / 2.0);
		vBoxForPopUpAskConfirmation.setLayoutY(height / 2.0 - vBoxForPopUpAskConfirmation.getPrefHeight() / 2.0);
		vBoxForPopUpAskConfirmation.setStyle(cssBackgroundAndBorderBig + cssPadding30);
		popUpAskConfirmationLabel.setMaxWidth(vBoxForPopUpAskConfirmation.getPrefWidth() - 0.03 * width);
		popUpAskConfirmationLabel.setFont(font40P);
		confirmDeleteCheckBox.setStyle("-fx-padding:" + 0.0463 * height + "px 0 " + 0.0463 * height + "px 0;");
		confirmDeleteCheckBox.setFont(font40P);
		hBoxForPopUpConfirmationButtons.setPrefWidth(vBoxForPopUpAskConfirmation.getPrefWidth());
		hBoxForPopUpConfirmationButtons.setSpacing(0.0130 * width);
		cancelPopUpActionButton.setPrefWidth(hBoxForPopUpConfirmationButtons.getPrefWidth() / 2.0);
		cancelPopUpActionButton.setFont(font45B);
		confirmPopUpActionButton.setPrefWidth(hBoxForPopUpConfirmationButtons.getPrefWidth() / 2.0);
		confirmPopUpActionButton.setFont(font45B);

		int selectedIndex = languageComboBox.getSelectionModel().getSelectedIndex();
		languageComboBox.setItems(emptyObservableList);
		languageComboBox.setItems(languagesObservableList);
		languageComboBox.getSelectionModel().select(selectedIndex);

		setTextAndFontForWelcomeLabel();
	}

	protected void recalculateBackground(double width, double height)
	{
		super.recalculateBackground(width, height);

		chalkboardBackgroundImage.setFitWidth(width);
		chalkboardBackgroundImage.setFitHeight(height);
	}

	public WelcomeScreen()
	{
		//BACKGROUND IMAGES
		chalkboardBackgroundImage = new CustomImageView(false, false, false, true, CacheHint.SPEED);
		chalkboardBackgroundImage.setLayoutX(0);
		chalkboardBackgroundImage.setLayoutY(0);

		//GAME NAME IMAGE
		gameNameWoodenImage = new CustomImageView(true, true, false, true, CacheHint.SPEED);

		//PLAYER NAME IMAGE, WELCOME LABEL AND EDIT NAME ICON
		welcomeImage = new CustomImageView(true, true, false, true, CacheHint.SPEED);

		welcomeLabel = new CustomLabel(Pos.CENTER, TextAlignment.CENTER, DARK_BROWN, null, null, null, false, false, false, true, true, CacheHint.SCALE);
		welcomeLabel.setEffect(innerShadow);

		editUsersButton = new CustomButton(null, images.get(Images.PENCIL), null, null, null, true, true, true, true, CacheHint.SCALE);

		//LEFT STAND GLOBE AND SHADOW
		leftGlobeStand = new CustomImageView(true, true, false, true, CacheHint.SPEED);

		leftGlobeImage = new CustomImageView(false, true, false, false, null);
		leftGlobeImage.setRotate(-20);

		leftEarthShadow = new DropShadow();
		leftGlobeImage.setEffect(leftEarthShadow);

		//RIGHT STAND GLOBE AND SHADOW
		rightGlobeStand = new CustomImageView(true, true, false, true, CacheHint.SPEED);
		rightGlobeStand.setScaleX(-1.0);

		rightStandShadow = new DropShadow();
		rightGlobeStand.setEffect(rightStandShadow);

		rightGlobeImage = new CustomImageView(false, true, false, false, null);
		rightGlobeImage.setRotate(20);

		rightEarthShadow = new DropShadow();
		rightGlobeImage.setEffect(rightEarthShadow);

		//START IN FULL SCREEN CHECKBOX
		startInFullScreenCheckBox = new CustomCheckBox();

		//STUFF ABOUT ANIMATIONS USED
		animationsUsedLabel = new CustomLabel(null, TextAlignment.CENTER, WHITE, null, null, null, false, true, false, false, false, null);

		noAnimationsToggleButton = new CustomToggleButton(true, "first", null, false, false, null);
		limitedAnimationsToggleButton = new CustomToggleButton(true, null, null, false, false, null);
		allAnimationsToggleButton = new CustomToggleButton(true, "last", null, false, false, null);

		toggleGroupForAnimationsUsedSegmentedButton = new ToggleGroup();
		toggleGroupForAnimationsUsedSegmentedButton.getToggles().addAll(noAnimationsToggleButton, limitedAnimationsToggleButton, allAnimationsToggleButton);

		animationsUsedSegmentedButton = new SegmentedButton(noAnimationsToggleButton, limitedAnimationsToggleButton, allAnimationsToggleButton);
		animationsUsedSegmentedButton.setToggleGroup(toggleGroupForAnimationsUsedSegmentedButton);

		vBoxForAnimationsUsedSettings = new CustomVBox(false, Pos.CENTER, null, false, null);
		vBoxForAnimationsUsedSettings.getChildren().addAll(animationsUsedLabel, animationsUsedSegmentedButton);

		//COUNTRY LABEL & COMBOBOX
		selectCountryLabel = new CustomLabel(Pos.CENTER,null, WHITE, null, null, null, false, true, false, false, false, null);

		countriesComboBox = new CustomComboBox<>();

		hBoxForCountrySelection = new CustomHBox(true, Pos.CENTER, false, null);
		hBoxForCountrySelection.getChildren().addAll(selectCountryLabel, countriesComboBox);

		//LANGUAGE LABELS AND BOX
		selectLanguageLabel = new CustomLabel(Pos.CENTER,null, WHITE, null, null, null, false, true, false, false, false, null);

		setupLanguagesComboBox();

		languageComboBox = new CustomComboBox<>();

		languageComboBox.setCellFactory(lv -> new ListCell<>()
				{
					protected void updateItem(String item, boolean empty)
					{
						super.updateItem(item, empty);
						if(item == null || empty)
						{
							setText(null);
							setGraphic(null);
						}
						else
						{
							setText(item);
							if(item.equals(languageResourceBundle.getString("englishLanguageLabel")))
								setGraphic(new CustomImageView(images.get(Images.ENGLISH_FLAG), true, true, false, false, null));
							else if(item.equals(languageResourceBundle.getString("greekLanguageLabel")))
								setGraphic(new CustomImageView(images.get(Images.GREEK_FLAG), true, true, false, false, null));
							setGraphicTextGap(10);
							((ImageView)getGraphic()).setFitWidth(0.0286 * mainScene.getWidth());
						}
					}
				});
		languageComboBox.setButtonCell(languageComboBox.getCellFactory().call(null));
		languageComboBox.setItems(languagesObservableList);

		hBoxForLanguageSelection = new CustomHBox(true, Pos.CENTER, false, null);
		hBoxForLanguageSelection.getChildren().addAll(selectLanguageLabel, languageComboBox);

		//UNITS OF LENGTH
		selectUnitSystemLabel = new CustomLabel(Pos.CENTER,null, WHITE, null, null, null, false, true, false, false, false, null);

		metricSystemRadioButton = new CustomRadioButton(null, true);
		imperialSystemRadioButton = new CustomRadioButton(null, true);

		toggleGroupForUnitsOfLength = new ToggleGroup();
		toggleGroupForUnitsOfLength.getToggles().addAll(metricSystemRadioButton, imperialSystemRadioButton);

		hBoxForUnitOfLengthRadioButtons = new CustomHBox(false, Pos.CENTER, false, null);
		hBoxForUnitOfLengthRadioButtons.getChildren().addAll(metricSystemRadioButton, imperialSystemRadioButton);

		hBoxForUnitOfLengthSelection = new CustomHBox(false, null, false, null);
		hBoxForUnitOfLengthSelection.getChildren().addAll(selectUnitSystemLabel, hBoxForUnitOfLengthRadioButtons);

		//DELETE ALL DATA LABEL
		deleteAllDataButton = new CustomButton(null, null, null, null, null, false, true, true, false, null);

		//BOX FOR ALL SETTINGS
		vBoxForSettings = new CustomVBox(true, Pos.CENTER, "black-bordered-background-light", true, CacheHint.SPEED);
		vBoxForSettings.getChildren().addAll(startInFullScreenCheckBox, vBoxForAnimationsUsedSettings, hBoxForCountrySelection,
				hBoxForLanguageSelection, hBoxForUnitOfLengthSelection, deleteAllDataButton);

		settingsButton = new CustomButton(null, images.get(Images.SETTINGS), null, null, null, true, true, true, true, CacheHint.SCALE);
		infoButton = new CustomButton(null, images.get(Images.INFO), null, null, null, true, true, true, true, CacheHint.SCALE);

		hBoxForSettingsAndInfoIcons = new CustomHBox(true, Pos.CENTER, false, null);
		hBoxForSettingsAndInfoIcons.getChildren().addAll(infoButton, settingsButton);

		rectangleForInformationAboutGame = new CustomRectangle(true, BLACK, LIGHT_BLACK, true, CacheHint.SCALE);

		//BASIC BUTTONS BOX AND STUFF
		singlePlayerGameButton = new CustomButton(null, images.get(Images.SINGLE_PLAYER), null, null,  null, false, true,true, true, CacheHint.SCALE);
		multiPlayerGameButton = new CustomButton(null, images.get(Images.MULTI_PLAYER), null, null,  null, false, true,true, true, CacheHint.SCALE);
		atlasButton = new CustomButton(null, images.get(Images.GLOBE), null, null,  null, false, true, true,true, CacheHint.SCALE);
		scoreBoardButton = new CustomButton(null, images.get(Images.SCORES), null, null,  null, false, true,true, true, CacheHint.SCALE);

		vBoxForMainButtons = new CustomVBox(false, Pos.CENTER, null, false, null);
		vBoxForMainButtons.getChildren().addAll(singlePlayerGameButton, multiPlayerGameButton, atlasButton, scoreBoardButton);

		//NEW NAME BOX AND STUFF
		editPlayerIcon = new CustomImageView(true, true, false, true, CacheHint.SCALE);
		switchPlayerIcon = new CustomImageView(true, true, false, true, CacheHint.SCALE);
		addPlayerIcon = new CustomImageView(true, true, false, true, CacheHint.SCALE);

		editPlayerLabel = new CustomLabel(Pos.CENTER,null, WHITE, null, null, null, false, false, false, false, false, null);
		switchPlayerLabel = new CustomLabel(Pos.CENTER,null, WHITE, null, null, null, false, false, false, false, false, null);
		addPlayerLabel = new CustomLabel(Pos.CENTER,null, WHITE, null, null, null, false, false, false, false, false, null);

		editPlayerToggleButton = new CustomToggleButton(true, "first", editPlayerIcon, false, true, CacheHint.SCALE);
		switchPlayerToggleButton = new CustomToggleButton(true, null, switchPlayerIcon, false, true, CacheHint.SCALE);
		addPlayerToggleButton = new CustomToggleButton(true, "last", addPlayerIcon, false, true, CacheHint.SCALE);

		toggleGroupForUsersEditSegmentedButton = new ToggleGroup();
		toggleGroupForUsersEditSegmentedButton.getToggles().addAll(editPlayerToggleButton, switchPlayerToggleButton, addPlayerToggleButton);

		playersEditSegmentedButton = new SegmentedButton(editPlayerToggleButton, switchPlayerToggleButton, addPlayerToggleButton);
		playersEditSegmentedButton.setToggleGroup(toggleGroupForUsersEditSegmentedButton);

		textFieldForEditPlayerName = new CustomTextField();
		textFieldForAddPlayer = new CustomTextField();

		playersComboBox = new CustomComboBox<>();

		deleteSingleUserButton = new CustomButton(null, null, null, null, WHITE, true, true,true, false, null);
		deleteAllUsersButton = new CustomButton(null, null, null, null, WHITE, true, true,true, false, null);

		hBoxForDeleteSavedNames = new CustomHBox(true, Pos.CENTER, false, null);
		hBoxForDeleteSavedNames.getChildren().addAll(deleteAllUsersButton, deleteSingleUserButton);

		cancelEditPlayerNameButton = new CustomButton(null, null, null, null, null, false, true,false, false, null);
		cancelEditPlayerNameButton.setTooltip(new CustomTooltip());

		confirmEditPlayerNameButton = new CustomButton(null, null, null, null, null, false, true,false, false, null);
		confirmEditPlayerNameButton.setDisable(true);
		confirmEditPlayerNameButton.setTooltip(new CustomTooltip());

		hBoxForEditPlayerNameButtons = new CustomHBox(true, Pos.CENTER, false, null);
		hBoxForEditPlayerNameButtons.getChildren().addAll(cancelEditPlayerNameButton, confirmEditPlayerNameButton);

		cancelSwitchPlayerButton = new CustomButton(null, null, null, null, null, false, true,false, false, null);
		cancelSwitchPlayerButton.setTooltip(new CustomTooltip());

		confirmSwitchPlayerButton = new CustomButton(null, null, null, null, null, false, true,false, false, null);
		confirmSwitchPlayerButton.setDisable(true);
		confirmSwitchPlayerButton.setTooltip(new CustomTooltip());

		hBoxForSwitchPlayerButtons = new CustomHBox(true, null, false, null);
		hBoxForSwitchPlayerButtons.getChildren().addAll(cancelSwitchPlayerButton, confirmSwitchPlayerButton);

		cancelAddPlayerButton = new CustomButton(null, null, null, null, null, false, true,false, false, null);
		cancelAddPlayerButton.setTooltip(new CustomTooltip());

		confirmAddPlayerButton = new CustomButton(null, null, null, null, null, false, true,false, false, null);
		confirmAddPlayerButton.setDisable(true);
		confirmAddPlayerButton.setTooltip(new CustomTooltip());

		hBoxForAddPlayerButtons = new CustomHBox(true, Pos.CENTER, false, null);
		hBoxForAddPlayerButtons.getChildren().addAll(cancelAddPlayerButton, confirmAddPlayerButton);

		vBoxForEditPlayerName = new CustomVBox(false, Pos.CENTER, "black-bordered-background-light", true, CacheHint.SCALE);
		vBoxForEditPlayerName.getChildren().addAll(editPlayerLabel, textFieldForEditPlayerName, hBoxForEditPlayerNameButtons);

		vBoxForSwitchPlayer = new CustomVBox(false, Pos.CENTER, "black-bordered-background-light", true, CacheHint.SCALE);
		vBoxForSwitchPlayer.getChildren().addAll(switchPlayerLabel, playersComboBox, hBoxForDeleteSavedNames, hBoxForSwitchPlayerButtons);

		vBoxForAddPlayer = new CustomVBox(false, Pos.CENTER, "black-bordered-background-light", true, CacheHint.SCALE);
		vBoxForAddPlayer.getChildren().addAll(addPlayerLabel, textFieldForAddPlayer, hBoxForAddPlayerButtons);

		//POP UP MESSAGES
		popUpMessageLabel = new CustomLabel(null, TextAlignment.CENTER, WHITE, "black-bordered-background-dark", null, null, false, false, true, false, false, null);

		confirmDeleteCheckBox = new CustomCheckBox();

		popUpAskConfirmationLabel = new CustomLabel(null, TextAlignment.CENTER, WHITE, null, null, null, false, false, true, false, false, null);

		cancelPopUpActionButton = new CustomButton(null, null, null, null, null, false, true,false, false, null);
		confirmPopUpActionButton = new CustomButton(null, null, null, null, null, false, true,false, false, null);

		hBoxForPopUpConfirmationButtons = new CustomHBox(true, Pos.CENTER, false, null);
		hBoxForPopUpConfirmationButtons.getChildren().addAll(cancelPopUpActionButton, confirmPopUpActionButton);

		vBoxForPopUpAskConfirmation = new CustomVBox(false, Pos.CENTER, "black-bordered-background-dark", true, CacheHint.SCALE);
		vBoxForPopUpAskConfirmation.getChildren().addAll(popUpAskConfirmationLabel, confirmDeleteCheckBox, hBoxForPopUpConfirmationButtons);

		//ADD DROP SHADOW EFFECT TO NODES
		woodenFrameImage.setEffect(dropShadow);
		gameNameWoodenImage.setEffect(dropShadow);
		welcomeImage.setEffect(dropShadow);
		leftGlobeStand.setEffect(dropShadow);
		editUsersButton.setEffect(dropShadow);
		hBoxForSettingsAndInfoIcons.setEffect(dropShadow);
		playersEditSegmentedButton.setEffect(dropShadow);
		vBoxForEditPlayerName.setEffect(dropShadow);
		vBoxForSwitchPlayer.setEffect(dropShadow);
		vBoxForAddPlayer.setEffect(dropShadow);
		rectangleForInformationAboutGame.setEffect(dropShadow);
		vBoxForSettings.setEffect(dropShadow);
		vBoxForMainButtons.setEffect(dropShadow);
		vBoxForPopUpAskConfirmation.setEffect(dropShadow);
		popUpMessageLabel.setEffect(dropShadow);

		//ADD EVERYTHING TO ANCHOR PANE
		nodesPane.getChildren().addAll(
				chalkboardBackgroundImage, welcomeImage, welcomeLabel, gameNameWoodenImage,
				leftGlobeStand, rightGlobeStand, woodPanelFor1IconImage,
				leftGlobeImage, rightGlobeImage, editUsersButton,
				vBoxForMainButtons, hBoxForSettingsAndInfoIcons, playersEditSegmentedButton,
				vBoxForEditPlayerName, vBoxForSwitchPlayer, vBoxForAddPlayer, rectangleForInformationAboutGame,
				vBoxForSound, vBoxForSettings, soundButton, vBoxForPopUpAskConfirmation, popUpMessageLabel);

		setupPopUpAnimations();

		fadeInTransition = new FadeTransition(Duration.millis(800), anchorPane);
		fadeInTransition.setToValue(1.0);

		if(animationsUsed != ANIMATIONS.NO)
		{
			setupLimitedAnimations();
			if(animationsUsed == ANIMATIONS.ALL) setupAdvancedAnimations();

			fadeInTransition.setOnFinished(e -> timelineToShowAllStuff.playFromStart());
		}

		setupListeners();

		loadUsersObservableList();

		multiPlayerGameButton.setDisable(true);
	}

	private void disableUI()
	{
		editUsersButton.setDisable(true);
		hBoxForSettingsAndInfoIcons.setDisable(true);
		leftGlobeImage.setDisable(true);
		rightGlobeImage.setDisable(true);
		vBoxForSettings.setDisable(true);
		playersEditSegmentedButton.setDisable(true);
		vBoxForSwitchPlayer.setDisable(true);
	}

	private void enableUI()
	{
		editUsersButton.setDisable(false);
		hBoxForSettingsAndInfoIcons.setDisable(false);
		leftGlobeImage.setDisable(false);
		rightGlobeImage.setDisable(false);
		vBoxForSettings.setDisable(false);
		playersEditSegmentedButton.setDisable(false);
		vBoxForSwitchPlayer.setDisable(false);
	}

	private void addBlurEffect()
	{
		chalkboardBackgroundImage.setEffect(boxBlurForNodes);
		gameNameWoodenImage.setEffect(boxBlurForNodes);
		welcomeImage.setEffect(boxBlurForNodes);
		welcomeLabel.setEffect(boxBlurForNodes);
		leftGlobeStand.setEffect(boxBlurForNodes);
		rightGlobeStand.setEffect(boxBlurForNodes);
		editUsersButton.setEffect(boxBlurForNodes);
		hBoxForSettingsAndInfoIcons.setEffect(boxBlurForNodes);
		leftGlobeImage.setEffect(boxBlurForNodes);
		rightGlobeImage.setEffect(boxBlurForNodes);
		vBoxForSettings.setEffect(boxBlurForNodes);
		playersEditSegmentedButton.setEffect(boxBlurForNodes);
		vBoxForSwitchPlayer.setEffect(boxBlurForNodes);
	}

	private void removeBlurEffect()
	{
		chalkboardBackgroundImage.setEffect(null);
		gameNameWoodenImage.setEffect(dropShadow);
		welcomeImage.setEffect(dropShadow);
		welcomeLabel.setEffect(innerShadow);
		leftGlobeStand.setEffect(dropShadow);
		rightGlobeStand.setEffect(rightStandShadow);
		editUsersButton.setEffect(dropShadow);
		hBoxForSettingsAndInfoIcons.setEffect(dropShadow);
		leftGlobeImage.setEffect(leftEarthShadow);
		rightGlobeImage.setEffect(rightEarthShadow);
		vBoxForSettings.setEffect(dropShadow);
		playersEditSegmentedButton.setEffect(dropShadow);
		vBoxForSwitchPlayer.setEffect(dropShadow);
	}

	public void createScene()
	{
		mainScene = new Scene(anchorPane);
		for(String cssFile: cssFiles)
			mainScene.getStylesheets().add(getClass().getResource("/css/" + cssFile + ".css").toExternalForm());

		stage.setScene(mainScene);
		anchorPane.setVisible(false);
		stage.show();

		getScreenStuff().setStageSceneHeightDifference(stage.getHeight() - mainScene.getHeight());

		stage.minHeightProperty().bind(stage.widthProperty().divide(getScreenStuff().getCurrentScreenRatioValue()).add(getScreenStuff().getStageSceneHeightDifference()));
		stage.maxHeightProperty().bind(stage.widthProperty().divide(getScreenStuff().getCurrentScreenRatioValue()).add(getScreenStuff().getStageSceneHeightDifference()));

		if(animationsUsed != ANIMATIONS.NO) anchorPane.setOpacity(0);
	}

	public void showScreen()
	{
		super.showScreen();

		anchorPane.setVisible(true);
		if(animationsUsed != ANIMATIONS.NO)
		{
			if(firstTime)
				fadeInTransition.playFromStart();
			else timelineToShowAllStuff.playFromStart();
		}

		if(firstTime) firstTime = false;
	}

	protected void setInitialStateForAllNodes()
	{
//		--------------- Load images if not already set ---------------
		if(woodenFrameImage.getImage() == null || !woodenFrameImage.getImage().equals(images.get(Images.FRAME)))
		{
			woodenFrameImage.setImage(images.get(Images.FRAME));
			chalkboardBackgroundImage.setImage(images.get(Images.CHALKBOARD_BACKGROUND));
			woodPanelFor1IconImage.setImage(images.get(Images.EMPTY_WOOD_BACKGROUND_1_ICON));
			welcomeImage.setImage(images.get(Images.EMPTY_WOOD_BACKGROUND_SMALL_ROPE));
			gameNameWoodenImage.setImage(images.get(Images.GAME_NAME));

			leftGlobeStand.setImage(images.get(Images.GLOBE_STAND));
			rightGlobeStand.setImage(images.get(Images.GLOBE_STAND));

			editPlayerIcon.setImage(images.get(Images.EDIT_PLAYER_NAME));
			switchPlayerIcon.setImage(images.get(Images.SWITCH_PLAYER));
			addPlayerIcon.setImage(images.get(Images.ADD_PLAYER));

			settingsButton.setImage(images.get(Images.SETTINGS));
			singlePlayerGameButton.setImage(images.get(Images.SINGLE_PLAYER));
			multiPlayerGameButton.setImage(images.get(Images.MULTI_PLAYER));
			atlasButton.setImage(images.get(Images.GLOBE));
			scoreBoardButton.setImage(images.get(Images.SCORES));
			infoButton.setImage(images.get(Images.INFO));
			editUsersButton.setImage(images.get(Images.PENCIL));
		}

		if(leftGlobeImage.getImage() == null)
		{
			leftGlobeImage.setImage(getImageStuff().getAnimatedGlobes()[0]);
			rightGlobeImage.setImage(getImageStuff().getAnimatedGlobes()[0]);
		}

		if(languageComboBox.getSelectionModel().getSelectedIndex() == -1)
		{
			if(getCurrentLanguage() == LANGUAGE.ENGLISH) languageComboBox.getSelectionModel().select(0);
			else if(getCurrentLanguage() == LANGUAGE.GREEK) languageComboBox.getSelectionModel().select(1);
		}

		updateStrings();

		if(countriesLocalesObservableList == null) loadCountriesLocalesObservableList();

		playersEditSegmentedButton.getToggleGroup().selectToggle(editPlayerToggleButton);

//		--------------- SET POSITIONS ---------------
		vBoxForSound.setVisible(false);
		vBoxForSound.setTranslateX(mainScene.getWidth() - vBoxForSound.getLayoutX() + 20);

		setCorrectSoundIcon(false);

		rectangleForInformationAboutGame.setScaleX(0);
		rectangleForInformationAboutGame.setScaleY(0);
		rectangleForInformationAboutGame.setVisible(false);

		vBoxForSettings.setScaleX(0);
		vBoxForSettings.setScaleY(0);
		vBoxForSettings.setVisible(false);

		vBoxForMainButtons.setTranslateY(0);
		vBoxForMainButtons.setVisible(true);

		playersEditSegmentedButton.setScaleX(0);
		playersEditSegmentedButton.setScaleY(0);
		playersEditSegmentedButton.setVisible(false);

		vBoxForEditPlayerName.setScaleX(0);
		vBoxForEditPlayerName.setScaleY(0);
		vBoxForEditPlayerName.setVisible(false);

		vBoxForSwitchPlayer.setScaleX(0);
		vBoxForSwitchPlayer.setScaleY(0);
		vBoxForSwitchPlayer.setVisible(false);

		vBoxForAddPlayer.setScaleX(0);
		vBoxForAddPlayer.setScaleY(0);
		vBoxForAddPlayer.setVisible(false);

		popUpAction = POP_UP_ACTION.NULL;

		vBoxForPopUpAskConfirmation.setVisible(false);
		vBoxForPopUpAskConfirmation.setScaleX(0);
		vBoxForPopUpAskConfirmation.setScaleY(0);

		popUpMessageLabel.setVisible(false);
		popUpMessageLabel.setScaleX(0);
		popUpMessageLabel.setScaleY(0);

		setUIToggleValuesBasedOnSettings();

		setTextAndFontForWelcomeLabel();

		if(animationsUsed != ANIMATIONS.NO)
		{
			woodPanelFor1IconImage.setTranslateY(-1.0 * (woodPanelFor1IconImage.getLayoutY() + woodPanelFor1IconImage.getBoundsInParent().getHeight()));
			gameNameWoodenImage.setTranslateY(-1.0 * (gameNameWoodenImage.getLayoutY() + gameNameWoodenImage.getBoundsInParent().getHeight()));
			leftGlobeStand.setTranslateX(-1.0 * (leftGlobeStand.getLayoutX() + leftGlobeStand.getFitWidth() + 20));
			leftGlobeImage.setTranslateX(-1.0 * (leftGlobeStand.getLayoutX() + leftGlobeStand.getFitWidth() + 20));
			rightGlobeStand.setTranslateX(mainScene.getWidth() - rightGlobeStand.getLayoutX() + 20);
			rightGlobeImage.setTranslateX(mainScene.getWidth() - rightGlobeStand.getLayoutX() + 20);

			welcomeImage.setTranslateY(-0.1759 * mainScene.getHeight());
			welcomeImage.setScaleY(0);

			vBoxForMainButtons.setTranslateY(0);

			soundButton.setScaleX(0);
			soundButton.setScaleY(0);
			welcomeLabel.setScaleX(0);
			welcomeLabel.setScaleY(0);
			editUsersButton.setScaleX(0);
			editUsersButton.setScaleY(0);
			singlePlayerGameButton.setScaleX(0);
			singlePlayerGameButton.setScaleY(0);
			multiPlayerGameButton.setScaleX(0);
			multiPlayerGameButton.setScaleY(0);
			atlasButton.setScaleX(0);
			atlasButton.setScaleY(0);
			scoreBoardButton.setScaleX(0);
			scoreBoardButton.setScaleY(0);
			infoButton.setScaleX(0);
			infoButton.setScaleY(0);
			settingsButton.setScaleX(0);
			settingsButton.setScaleY(0);
		}
	}

	private boolean usernameAlreadyExists(String username)
	{
		for(Player player: playersArrayList)
		{
			if(getStringWithoutTones(player.getOriginalName()).equalsIgnoreCase(username)) return true;
		}
		return false;
	}

	protected void setupListeners()
	{
		confirmDeleteCheckBox.setOnAction(e ->
		{
			if (confirmDeleteCheckBox.isSelected())
			{
				getAudioStuff().playCheckBoxSelectedSound();

				confirmPopUpActionButton.setDisable(false);
			}
			else
			{
				getAudioStuff().playCheckBoxDeselectedSound();

				confirmPopUpActionButton.setDisable(true);
			}
		});

		cancelPopUpActionButton.setOnAction(e -> timelineToCancelConfirmationMessage.playFromStart());

		confirmPopUpActionButton.setOnAction(e ->
		{
			enableUI();

			if(popUpAction == POP_UP_ACTION.DELETE_SINGLE_USER)
			{
				playersObservableList.remove(playersComboBox.getSelectionModel().getSelectedIndex());
				playersArrayList.remove(playersComboBox.getSelectionModel().getSelectedIndex());
				FilesIO.writePlayersFile();

				popUpMessageLabel.setText(languageResourceBundle.getString("deleteSingleUserSuccessfullyMessage"));
			}
			else if(popUpAction == POP_UP_ACTION.DELETE_ALL_USERS)
			{
				playersObservableList.remove(1, playersObservableList.size());
				for(int i = 1; i < playersArrayList.size(); i++) playersArrayList.remove(i);
				FilesIO.writePlayersFile();

				popUpMessageLabel.setText(languageResourceBundle.getString("deleteAllUsersSuccessfullyMessage"));
			}
			else if(popUpAction == POP_UP_ACTION.DELETE_ALL_DATA)
			{
				if(FilesIO.deleteAllData())
				{
					setUIToggleValuesBasedOnSettings();
					loadUsersObservableList();

					popUpMessageLabel.setText(languageResourceBundle.getString("messageAllDataDeletedSuccessfully"));
				}
			}

			timelineToShowPopUpMessage.playFromStart();
		});

		editUsersButton.setOnAction(e ->
		{
			editUsersButton.setImage(images.get(Images.PENCIL_DISABLED));

			playersComboBox.getSelectionModel().select(0);

			if(animationsUsed != ANIMATIONS.NO) timelineToShowEditUsersVBox.playFromStart();
			else
			{
				vBoxForMainButtons.setVisible(false);
				vBoxForMainButtons.setTranslateY(mainScene.getHeight() - vBoxForMainButtons.getLayoutY() + 20);

				playersEditSegmentedButton.setScaleX(1);
				playersEditSegmentedButton.setScaleY(1);
				playersEditSegmentedButton.setVisible(true);

				if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == editPlayerToggleButton)
				{
					vBoxForEditPlayerName.setScaleX(1);
					vBoxForEditPlayerName.setScaleY(1);
					vBoxForEditPlayerName.setVisible(true);
				}
				else if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == switchPlayerToggleButton)
				{
					vBoxForSwitchPlayer.setScaleX(1);
					vBoxForSwitchPlayer.setScaleY(1);
					vBoxForSwitchPlayer.setVisible(true);
				}
				else
				{
					vBoxForAddPlayer.setScaleX(1);
					vBoxForAddPlayer.setScaleY(1);
					vBoxForAddPlayer.setVisible(true);
				}

				settingsButton.setDisable(true);
				settingsButton.setScaleX(0);
				settingsButton.setScaleY(0);

				infoButton.setDisable(true);
				infoButton.setScaleX(0);
				infoButton.setScaleY(0);
			}
		});

		toggleGroupForUsersEditSegmentedButton.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
		{
			if(newValue == null) toggleGroupForUsersEditSegmentedButton.selectToggle(oldValue);
			else if(oldValue != null && playersEditSegmentedButton.isVisible()) changeUsersEditVBox();
		});

//		EDIT USERNAME LISTENERS--------------------------------------------------------------------------------
		textFieldForEditPlayerName.textProperty().addListener((observableValue, oldValue, newValue) ->
		{
			newValue = newValue.trim();

			if (newValue.isEmpty() || usernameAlreadyExists(newValue))
			{
				confirmEditPlayerNameButton.setDisable(true);

				if(newValue.isEmpty())
				{
					textFieldForEditPlayerName.setStyle("-fx-control-inner-background:" + NORMAL_COLOR + ";");
					textFieldForEditPlayerName.getTooltip().setText(languageResourceBundle.getString("textFieldEmpty"));
				}
				else
				{
					textFieldForEditPlayerName.setStyle("-fx-control-inner-background:" + RED_COLOR + ";");
					textFieldForEditPlayerName.getTooltip().setText(languageResourceBundle.getString("textFieldRejectedUsernameTooltip"));
				}
			}
			else
			{
				textFieldForEditPlayerName.setStyle("-fx-control-inner-background:" + GREEN_COLOR + ";");
				textFieldForEditPlayerName.getTooltip().setText(languageResourceBundle.getString("textFieldAcceptedUsernameTooltip"));

				confirmEditPlayerNameButton.setDisable(false);
			}
		});

		textFieldForEditPlayerName.setOnAction(e ->
		{
			if (!confirmEditPlayerNameButton.isDisabled()) editUsername(textFieldForEditPlayerName.getText());
		});

		cancelEditPlayerNameButton.setOnAction(e ->
		{
			if(animationsUsed != ANIMATIONS.NO) timelineToHideEditUsersVBox.playFromStart();
			else hideEditUsersBoxesNoAnimations();
		});

		confirmEditPlayerNameButton.setOnAction(e -> editUsername(textFieldForEditPlayerName.getText()));

//		SWITCH USER LISTENERS--------------------------------------------------------------------------------
		playersComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
		{
			if(playersComboBox.getSelectionModel().getSelectedIndex() == 0)
			{
				confirmSwitchPlayerButton.setDisable(true);
				deleteSingleUserButton.getTooltip().setText(languageResourceBundle.getString("deleteSingleUserUnableToDeleteMessage"));
			}
			else
			{
				confirmSwitchPlayerButton.setDisable(false);
				deleteSingleUserButton.getTooltip().setText(languageResourceBundle.getString("deleteSingleUserTooltip"));
			}
		});

		deleteSingleUserButton.setOnAction(e ->
		{
			if(playersComboBox.getSelectionModel().getSelectedIndex() > 0)
			{
				popUpAction = POP_UP_ACTION.DELETE_SINGLE_USER;
				popUpAskConfirmationLabel.setText(languageResourceBundle.getString("deleteSingleUserConfirmationMessage"));
				confirmPopUpActionButton.setText(languageResourceBundle.getString("deleteSingleUserLabel"));
				timelineToShowConfirmationMessage.playFromStart();
			}
		});

		deleteAllUsersButton.setOnAction(e ->
		{
			popUpAction = POP_UP_ACTION.DELETE_ALL_USERS;
			popUpAskConfirmationLabel.setText(languageResourceBundle.getString("deleteAllUsersConfirmationMessage"));
			confirmPopUpActionButton.setText(languageResourceBundle.getString("deleteAll"));

			timelineToShowConfirmationMessage.playFromStart();
		});

		cancelSwitchPlayerButton.setOnAction(e ->
		{
			if(animationsUsed != ANIMATIONS.NO) timelineToHideEditUsersVBox.playFromStart();
			else hideEditUsersBoxesNoAnimations();
		});

		confirmSwitchPlayerButton.setOnAction(e -> changeCurrentPlayer(playersComboBox.getSelectionModel().getSelectedIndex()));

//		ADD USER LISTENERS--------------------------------------------------------------------------------
		textFieldForAddPlayer.textProperty().addListener((observableValue, oldValue, newValue) ->
		{
			newValue = newValue.trim();
			if (newValue.isEmpty() || usernameAlreadyExists(newValue))
			{
				confirmAddPlayerButton.setDisable(true);

				if(newValue.isEmpty())
				{
					textFieldForAddPlayer.setStyle("-fx-control-inner-background:" + NORMAL_COLOR + ";");
					textFieldForAddPlayer.getTooltip().setText(languageResourceBundle.getString("textFieldEmpty"));
				}
				else
				{
					textFieldForAddPlayer.setStyle("-fx-control-inner-background:" + RED_COLOR + ";");
					textFieldForAddPlayer.getTooltip().setText(languageResourceBundle.getString("textFieldRejectedUsernameTooltip"));
				}
			}
			else
			{
				textFieldForAddPlayer.setStyle("-fx-control-inner-background:" + GREEN_COLOR + ";");
				textFieldForAddPlayer.getTooltip().setText(languageResourceBundle.getString("textFieldAcceptedUsernameTooltip"));

				confirmAddPlayerButton.setDisable(false);
			}
		});

		textFieldForAddPlayer.setOnAction(e ->
		{
			if (!confirmAddPlayerButton.isDisabled()) addPlayer(textFieldForAddPlayer.getText());
		});

		cancelAddPlayerButton.setOnAction(e ->
		{
			if(animationsUsed != ANIMATIONS.NO) timelineToHideEditUsersVBox.playFromStart();
			else hideEditUsersBoxesNoAnimations();
		});

		confirmAddPlayerButton.setOnAction(e -> addPlayer(textFieldForAddPlayer.getText()));

//		START IN FULL SCREEN CHECKBOX LISTENER--------------------------------------------------------------------------------
		startInFullScreenCheckBox.setOnAction(e ->
		{
			if (startInFullScreenCheckBox.isSelected())
			{
				getAudioStuff().playCheckBoxSelectedSound();

				getCurrentPlayer().setStartAtFullScreen(true);
			}
			else
			{
				getAudioStuff().playCheckBoxDeselectedSound();

				getCurrentPlayer().setStartAtFullScreen(false);
			}
		});

//		ANIMATIONS USED LISTENERS--------------------------------------------------------------------------------
		toggleGroupForAnimationsUsedSegmentedButton.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
		{
			if(newValue == null) toggleGroupForAnimationsUsedSegmentedButton.selectToggle(oldValue);
			else if(oldValue != null)
			{
				if(toggleGroupForAnimationsUsedSegmentedButton.getSelectedToggle() == noAnimationsToggleButton) animationsUsed = ANIMATIONS.NO;
				else if(toggleGroupForAnimationsUsedSegmentedButton.getSelectedToggle() == limitedAnimationsToggleButton) animationsUsed = ANIMATIONS.LIMITED;
				else animationsUsed = ANIMATIONS.ALL;

				getCurrentPlayer().setAnimationsUsed(animationsUsed);

				if (animationsUsed == ANIMATIONS.ALL)
				{
					if(getImageStuff().getAnimatedGlobes().length == 1) getImageStuff().loadAnimatedGlobeImages();
					if(getImageStuff().getMovingEarthImageViewports().length == 1) getImageStuff().createViewports();
					
					if(timelineToShowAllStuff == null)
					{
						setupLimitedAnimations();
						new Thread(() ->
						{
							gamePropertiesScreen.setupLimitedAnimations();
							gameScreen.setupLimitedAnimations();
						}).start();
						new Thread(() ->
						{
							atlasScreen.setupLimitedAnimations();
							scoreBoardScreen.setupLimitedAnimations();
						}).start();
					}
					if(timelineForLeftGlobe == null)
					{
						setupAdvancedAnimations();
						new Thread(() ->
						{
							gamePropertiesScreen.setupAdvancedAnimations();
							gameScreen.setupAdvancedAnimations();
						}).start();
						new Thread(() ->
						{
							atlasScreen.setupAdvancedAnimations();
							scoreBoardScreen.setupAdvancedAnimations();
						}).start();
					}

					playGlobeAnimation();

					leftGlobeImage.setCursor(Cursor.HAND);
					rightGlobeImage.setCursor(Cursor.HAND);
				}
				else
				{
					if(animationsUsed == ANIMATIONS.LIMITED && timelineToShowAllStuff == null)
					{
						setupLimitedAnimations();
						
						new Thread(() ->
						{
							gamePropertiesScreen.setupLimitedAnimations();
							gameScreen.setupLimitedAnimations();
							atlasScreen.setupLimitedAnimations();
							scoreBoardScreen.setupLimitedAnimations();
						}).start();
					}

					if(timelineForLeftGlobe != null) stopGlobeAnimation();

					leftGlobeImage.setCursor(Cursor.DEFAULT);
					rightGlobeImage.setCursor(Cursor.DEFAULT);
				}
			}
		});

//		CHANGE COUNTRY LISTENERS--------------------------------------------------------------------------------
		countriesComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
		{
			if(oldValue != null && newValue != null && oldValue != newValue)
			{
				getCurrentPlayer().setLocale(new Locale(countries[countriesLocalesSortList.get(countriesComboBox.getSelectionModel().getSelectedIndex())].getLocaleLanguageCode(),
						countries[countriesLocalesSortList.get(countriesComboBox.getSelectionModel().getSelectedIndex())].getLocaleCountryCode()));

				getCurrentPlayer().setLocaleIndex(countriesLocalesSortList.get(countriesComboBox.getSelectionModel().getSelectedIndex()));

				PowerOn.setNumberAndDateFormats(getCurrentPlayer().getLocale());
			}
		});

//		CHANGE LANGUAGE LISTENERS--------------------------------------------------------------------------------
		languageComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
		{
			if(newValue != null && oldValue != null)
			{
				boolean languageChanged = false;
				if(languageComboBox.getSelectionModel().getSelectedIndex() == 0 && getCurrentLanguage() != LANGUAGE.ENGLISH)
				{
					languageChanged = true;
					getCurrentPlayer().setLanguage(LANGUAGE.ENGLISH);
				}
				else if(languageComboBox.getSelectionModel().getSelectedIndex() == 1 && getCurrentLanguage() != LANGUAGE.GREEK)
				{
					languageChanged = true;
					getCurrentPlayer().setLanguage(LANGUAGE.GREEK);
				}
				if(languageChanged)
				{
					if(animationsUsed != ANIMATIONS.NO) timelineToChangeLanguage.playFromStart();
					else changeLanguage();
				}
			}
		});

//		UNITS OF LENGTH LISTENERS--------------------------------------------------------------------------------
		toggleGroupForUnitsOfLength.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
		{
			if(metricSystemRadioButton.isSelected()) getCurrentPlayer().setUnitSystem(UNIT_SYSTEM.METRIC);
			else getCurrentPlayer().setUnitSystem(UNIT_SYSTEM.IMPERIAL);
		});

//		DELETE ALL DATA LISTENERS-------------------------------------------------------------------------------
		deleteAllDataButton.setOnAction(e ->
		{
			popUpAction = POP_UP_ACTION.DELETE_ALL_DATA;
			popUpAskConfirmationLabel.setText(languageResourceBundle.getString("deleteAllDataConfirmationMessage"));
			confirmPopUpActionButton.setText(languageResourceBundle.getString("deleteAll"));
			timelineToShowConfirmationMessage.playFromStart();
		});

		chalkboardBackgroundImage.setOnMouseClicked(e -> chalkboardBackgroundImage.requestFocus());

		soundButton.setOnAction(e ->
		{
			soundButton.setDisable(true);
			settingsButton.setDisable(true);

			if (!vBoxForSound.isVisible())
			{
				setCorrectSoundIcon(true);

				if(animationsUsed != ANIMATIONS.NO) timelineToShowSoundOptions.playFromStart();
				else
				{
					vBoxForSound.setVisible(true);
					vBoxForSound.setTranslateX(0);
				}

			}
			else
			{
				setCorrectSoundIcon(false);

				if(animationsUsed != ANIMATIONS.NO) timelineToHideSoundOptions.playFromStart();
				else
				{
					vBoxForSound.setTranslateX(mainScene.getWidth() - vBoxForSound.getLayoutX() + 20);

					vBoxForSound.setVisible(false);
				}
			}
			soundButton.setDisable(false);
			settingsButton.setDisable(false);
		});

		settingsButton.setOnAction(e ->
		{
			if (!vBoxForSettings.isVisible())
			{
				settingsButton.setImage(images.get(Images.SETTINGS_CLICKED));

				if(animationsUsed != ANIMATIONS.NO) timelineToShowSettings.playFromStart();
				else
				{
					welcomeLabel.setScaleX(0);
					welcomeLabel.setScaleY(0);

					welcomeImage.setScaleY(0);
					welcomeImage.setTranslateY(-0.1759 * mainScene.getHeight());

					vBoxForMainButtons.setOpacity(0);
					vBoxForMainButtons.setTranslateY(mainScene.getHeight() - vBoxForMainButtons.getLayoutY() + 20);

					vBoxForSettings.setScaleX(1);
					vBoxForSettings.setScaleY(1);
					vBoxForSettings.setVisible(true);

					editUsersButton.setDisable(true);
					editUsersButton.setScaleX(0);
					editUsersButton.setScaleY(0);

					infoButton.setDisable(true);
					infoButton.setScaleX(0);
					infoButton.setScaleY(0);
				}
			}
			else
			{
				settingsButton.setImage(images.get(Images.SETTINGS));

				setTextAndFontForWelcomeLabel();

				FilesIO.writePlayersFile();

				if(animationsUsed != ANIMATIONS.NO) timelineToHideSettings.playFromStart();
				else
				{
					vBoxForSettings.setVisible(false);
					vBoxForSettings.setScaleX(0);
					vBoxForSettings.setScaleY(0);

					welcomeImage.setTranslateY(0);
					welcomeImage.setScaleY(1);

					vBoxForMainButtons.setTranslateY(0);
					vBoxForMainButtons.setOpacity(1);

					welcomeLabel.setScaleX(1);
					welcomeLabel.setScaleY(1);

					editUsersButton.setScaleX(1);
					editUsersButton.setScaleY(1);
					editUsersButton.setDisable(false);

					infoButton.setScaleX(1);
					infoButton.setScaleY(1);
					infoButton.setDisable(false);
				}
			}
		});

		infoButton.setOnAction(e ->
		{
			if(!rectangleForInformationAboutGame.isVisible())
			{
				infoButton.setImage(images.get(Images.INFO_CLICKED));

				if(animationsUsed != ANIMATIONS.NO) timelineToShowInformationAboutGame.playFromStart();
				else
				{
					welcomeLabel.setScaleX(0);
					welcomeLabel.setScaleY(0);

					welcomeImage.setScaleY(0);
					welcomeImage.setTranslateY(-0.1759 * mainScene.getHeight());

					vBoxForMainButtons.setOpacity(0);
					vBoxForMainButtons.setTranslateY(mainScene.getHeight() - vBoxForMainButtons.getLayoutY() + 20);

					rectangleForInformationAboutGame.setScaleX(1);
					rectangleForInformationAboutGame.setScaleY(1);
					rectangleForInformationAboutGame.setVisible(true);

					editUsersButton.setDisable(true);
					editUsersButton.setScaleX(0);
					editUsersButton.setScaleY(0);

					settingsButton.setDisable(true);
					settingsButton.setScaleX(0);
					settingsButton.setScaleY(0);
				}
			}
			else
			{
				infoButton.setImage(images.get(Images.INFO));

				if(animationsUsed != ANIMATIONS.NO) timelineToHideInformationAboutGame.playFromStart();
				else
				{
					rectangleForInformationAboutGame.setVisible(false);
					rectangleForInformationAboutGame.setScaleX(0);
					rectangleForInformationAboutGame.setScaleY(0);

					welcomeImage.setTranslateY(0);
					welcomeImage.setScaleY(1);

					vBoxForMainButtons.setTranslateY(0);
					vBoxForMainButtons.setOpacity(1);

					welcomeLabel.setScaleX(1);
					welcomeLabel.setScaleY(1);

					editUsersButton.setScaleX(1);
					editUsersButton.setScaleY(1);
					editUsersButton.setDisable(false);

					settingsButton.setScaleX(1);
					settingsButton.setScaleY(1);
					settingsButton.setDisable(false);
				}
			}
		});

		leftGlobeImage.setOnMouseClicked(e ->
		{
			if (animationsUsed == ANIMATIONS.ALL)
			{
				if (leftGlobeStatus == 1)
				{
					timelineForLeftGlobe.pause();
					previousTransitionOfLeftGlobeWasRight = true;
					leftGlobeStatus = 0;
				}
				else if (leftGlobeStatus == 0)
				{
					if (previousTransitionOfLeftGlobeWasRight) { leftGlobeStatus = -1; }
					else { leftGlobeStatus = 1; }
					timelineForLeftGlobe.playFromStart();
				}
				else
				{
					timelineForLeftGlobe.pause();
					previousTransitionOfLeftGlobeWasRight = false;
					leftGlobeStatus = 0;
				}
			}
		});

		rightGlobeImage.setOnMouseClicked(e ->
		{
			 if (animationsUsed == ANIMATIONS.ALL)
			 {
				 if (rightGlobeStatus == 1)
				 {
					 timelineForRightGlobe.pause();
					 previousTransitionOfRightGlobeWasRight = true;
					 rightGlobeStatus = 0;
				 }
				 else if (rightGlobeStatus == 0)
				 {
					 if (previousTransitionOfRightGlobeWasRight) { rightGlobeStatus = -1; }
					 else { rightGlobeStatus = 1; }
					 timelineForRightGlobe.playFromStart();
				 }
				 else
				 {
					 timelineForRightGlobe.pause();
					 previousTransitionOfRightGlobeWasRight = false;
					 rightGlobeStatus = 0;
				 }
			 }
		});

		singlePlayerGameButton.setOnAction(e ->
		{
			if(animationsUsed != ANIMATIONS.NO)
			{
				timelineToHideAllStuff.setOnFinished(ev ->
				{
					gamePropertiesScreen.setFromWelcomeScreen(true);
					showOtherScreen(gamePropertiesScreen);
				});
				timelineToHideAllStuff.playFromStart();
			}
			else
			{
				gamePropertiesScreen.setFromWelcomeScreen(true);
				showOtherScreen(gamePropertiesScreen);
			}
		});

		atlasButton.setOnAction(e ->
		{
			if(animationsUsed != ANIMATIONS.NO)
			{
				timelineToHideAllStuff.setOnFinished(ev -> showOtherScreen(atlasScreen));
				timelineToHideAllStuff.playFromStart();
			}
			else showOtherScreen(atlasScreen);
		});

		scoreBoardButton.setOnAction(e ->
		{
			if(animationsUsed != ANIMATIONS.NO)
			{
				timelineToHideAllStuff.setOnFinished(ev -> showOtherScreen(scoreBoardScreen));
				timelineToHideAllStuff.playFromStart();
			}
			else showOtherScreen(scoreBoardScreen);
		});
	}

	protected void setupLimitedAnimations()
	{
		ScaleTransition scaleTransitionForOnePlayerGameButton1 = new ScaleTransition(Duration.millis(150));
		scaleTransitionForOnePlayerGameButton1.setToX(1.2);
		scaleTransitionForOnePlayerGameButton1.setToY(1.2);

		ScaleTransition scaleTransitionForOnePlayerGameButton2 = new ScaleTransition(Duration.millis(50));
		scaleTransitionForOnePlayerGameButton2.setToX(1);
		scaleTransitionForOnePlayerGameButton2.setToY(1);

		ScaleTransition scaleTransitionForTwoPlayersGameButton1 = new ScaleTransition(Duration.millis(150));
		scaleTransitionForTwoPlayersGameButton1.setToX(1.2);
		scaleTransitionForTwoPlayersGameButton1.setToY(1.2);

		ScaleTransition scaleTransitionForTwoPlayersGameButton2 = new ScaleTransition(Duration.millis(50));
		scaleTransitionForTwoPlayersGameButton2.setToX(1);
		scaleTransitionForTwoPlayersGameButton2.setToY(1);

		ScaleTransition scaleTransitionForAtlas1 = new ScaleTransition(Duration.millis(150));
		scaleTransitionForAtlas1.setToX(1.2);
		scaleTransitionForAtlas1.setToY(1.2);

		ScaleTransition scaleTransitionForAtlas2 = new ScaleTransition(Duration.millis(50));
		scaleTransitionForAtlas2.setToX(1);
		scaleTransitionForAtlas2.setToY(1);

		ScaleTransition scaleTransitionForScoreBoard1 = new ScaleTransition(Duration.millis(150));
		scaleTransitionForScoreBoard1.setToX(1.2);
		scaleTransitionForScoreBoard1.setToY(1.2);

		ScaleTransition scaleTransitionForScoreBoard2 = new ScaleTransition(Duration.millis(50));
		scaleTransitionForScoreBoard2.setToX(1);
		scaleTransitionForScoreBoard2.setToY(1);

		ScaleTransition scaleTransitionForSettingsButton1 = new ScaleTransition(Duration.millis(150));
		scaleTransitionForSettingsButton1.setToX(2);
		scaleTransitionForSettingsButton1.setToY(2);

		ScaleTransition scaleTransitionForSettingsButton2 = new ScaleTransition(Duration.millis(50));
		scaleTransitionForSettingsButton2.setToX(1);
		scaleTransitionForSettingsButton2.setToY(1);

		ScaleTransition scaleTransitionForInfoButton1 = new ScaleTransition(Duration.millis(150));
		scaleTransitionForInfoButton1.setToX(2);
		scaleTransitionForInfoButton1.setToY(2);

		ScaleTransition scaleTransitionForInfoButton2 = new ScaleTransition(Duration.millis(50));
		scaleTransitionForInfoButton2.setToX(1);
		scaleTransitionForInfoButton2.setToY(1);

		sequentialTransitionForOnePlayerGameButton = new SequentialTransition(singlePlayerGameButton, scaleTransitionForOnePlayerGameButton1, scaleTransitionForOnePlayerGameButton2);
		sequentialTransitionForTwoPlayersGameButton = new SequentialTransition(multiPlayerGameButton, scaleTransitionForTwoPlayersGameButton1, scaleTransitionForTwoPlayersGameButton2);
		sequentialTransitionForAtlasButton = new SequentialTransition(atlasButton, scaleTransitionForAtlas1, scaleTransitionForAtlas2);
		sequentialTransitionForScoreBoardButton = new SequentialTransition(scoreBoardButton, scaleTransitionForScoreBoard1, scaleTransitionForScoreBoard2);
		sequentialTransitionForInfoButton = new SequentialTransition(infoButton, scaleTransitionForInfoButton1, scaleTransitionForInfoButton2);
		sequentialTransitionForSettingsButton = new SequentialTransition(settingsButton, scaleTransitionForSettingsButton1, scaleTransitionForSettingsButton2);

		translateTransitionForWoodPanelFor1IconImage = new TranslateTransition(Duration.millis(200), woodPanelFor1IconImage);

		translateTransitionForGameNameImage = new TranslateTransition(Duration.millis(200), gameNameWoodenImage);

		TranslateTransition translateTransitionForWelcomeImage = new TranslateTransition(Duration.millis(200), welcomeImage);
		ScaleTransition scaleTransitionForWelcomeImage = new ScaleTransition(Duration.millis(200), welcomeImage);
		parallelTransitionForWelcomeImage = new ParallelTransition(translateTransitionForWelcomeImage, scaleTransitionForWelcomeImage);

		scaleTransitionForSoundIcon = new ScaleTransition(Duration.millis(200), soundButton);

		scaleTransitionForWelcomeLabel = new ScaleTransition();
		scaleTransitionForWelcomeLabel.setNode(welcomeLabel);

		ScaleTransition scaleTransitionForWelcomeLabelParallel = new ScaleTransition(Duration.millis(200), welcomeLabel);
		TranslateTransition translateTransitionForWelcomeLabel = new TranslateTransition(Duration.millis(200), welcomeLabel);
		parallelTransitionForWelcomeLabel = new ParallelTransition(translateTransitionForWelcomeLabel, scaleTransitionForWelcomeLabelParallel);

		scaleTransitionForEditIcon = new ScaleTransition(Duration.millis(200), editUsersButton);
		scaleTransitionForInfoButton = new ScaleTransition(Duration.millis(200), infoButton);

		translateTransitionForLeftGlobeStand = new TranslateTransition(Duration.millis(200), leftGlobeStand);
		translateTransitionForLeftGlobeImage = new TranslateTransition(Duration.millis(200), leftGlobeImage);
		translateTransitionForRightGlobeStand = new TranslateTransition(Duration.millis(200), rightGlobeStand);
		translateTransitionForRightGlobeImage = new TranslateTransition(Duration.millis(200), rightGlobeImage);

		translateTransitionForVBoxWithMainButtons = new TranslateTransition(Duration.millis(200), vBoxForMainButtons);

		scaleTransitionForUsersEditSegmentedButton = new ScaleTransition(Duration.millis(200), playersEditSegmentedButton);

		scaleTransitionForVBoxForEditUsername = new ScaleTransition(Duration.millis(200), vBoxForEditPlayerName);

		scaleTransitionForVBoxForSwitchUser = new ScaleTransition(Duration.millis(200), vBoxForSwitchPlayer);

		scaleTransitionForVBoxForAddUser = new ScaleTransition(Duration.millis(200), vBoxForAddPlayer);

		translateTransitionForVBoxForSound = new TranslateTransition(Duration.millis(200), vBoxForSound);

		scaleTransitionForVBoxForSettings = new ScaleTransition(Duration.millis(200), vBoxForSettings);

		scaleTransitionForSettingsIcon = new ScaleTransition(Duration.millis(200), settingsButton);

		scaleTransitionForRectangleForInfoAboutGame = new ScaleTransition(Duration.millis(200), rectangleForInformationAboutGame);

		scaleTransitionForPopUpMessage = new ScaleTransition(Duration.millis(200), popUpMessageLabel);
		scaleTransitionForPopUpAskConfirmation = new ScaleTransition(Duration.millis(200), vBoxForPopUpAskConfirmation);

		timelineToShowAllStuff = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				vBoxForMainButtons.setDisable(true);
				infoButton.setDisable(true);
				editUsersButton.setDisable(true);

				translateTransitionForGameNameImage.setToY(0);
				translateTransitionForWoodPanelFor1IconImage.setToY(0);

				getAudioStuff().playSlideSound();
				translateTransitionForGameNameImage.playFromStart();
				translateTransitionForWoodPanelFor1IconImage.playFromStart();
			}),
			new KeyFrame(Duration.millis(200), e ->
			{
				scaleTransitionForWelcomeImage.setToY(1);
				translateTransitionForWelcomeImage.setToY(0);

				translateTransitionForLeftGlobeStand.setToX(0);
				translateTransitionForLeftGlobeImage.setToX(0);
				translateTransitionForRightGlobeStand.setToX(0);
				translateTransitionForRightGlobeImage.setToX(0);

				if(animationsUsed == ANIMATIONS.ALL) playGlobeAnimation();

				getAudioStuff().playSlideSound();
				parallelTransitionForWelcomeImage.playFromStart();
				translateTransitionForLeftGlobeStand.playFromStart();
				translateTransitionForLeftGlobeImage.playFromStart();
				translateTransitionForRightGlobeStand.playFromStart();
				translateTransitionForRightGlobeImage.playFromStart();
			}),
			new KeyFrame(Duration.millis(400), e ->
			{
				scaleTransitionForWelcomeLabel.setDuration(Duration.millis(200));
				scaleTransitionForWelcomeLabel.setFromX(0);
				scaleTransitionForWelcomeLabel.setFromY(0);
				scaleTransitionForWelcomeLabel.setToX(0.95);
				scaleTransitionForWelcomeLabel.setToY(0.95);
				scaleTransitionForWelcomeLabel.setCycleCount(1);
				scaleTransitionForWelcomeLabel.setAutoReverse(false);

				scaleTransitionForSoundIcon.setToX(1);
				scaleTransitionForSoundIcon.setToY(1);
				scaleTransitionForEditIcon.setToX(1);
				scaleTransitionForEditIcon.setToY(1);

				if(animationsUsed == ANIMATIONS.ALL)
				{
					scaleTransitionForWelcomeLabel.setOnFinished(ev ->
							{
								scaleTransitionForWelcomeLabel.setOnFinished(eve -> {});
								startWelcomeTextAnimation();
							});
				}

				timelineToPopUpMainButtons.playFromStart();

				scaleTransitionForWelcomeLabel.playFromStart();
				scaleTransitionForSoundIcon.playFromStart();
				scaleTransitionForEditIcon.playFromStart();
			}),
			new KeyFrame(Duration.millis(1300), e ->
			{
				if (getAudioStuff().getMasterSliderVolume() != 0 && getAudioStuff().getMusicSliderVolume() != 0) getAudioStuff().playIntroductionSound();

				vBoxForMainButtons.setDisable(false);
				infoButton.setDisable(false);
				editUsersButton.setDisable(false);
			})
		);

		timelineToHideAllStuff = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				vBoxForMainButtons.setDisable(true);
				infoButton.setDisable(true);
				editUsersButton.setDisable(true);

				pauseWelcomeTextAnimation();

				timelineToPopUpMainButtons.stop();
				sequentialTransitionForInfoButton.stop();

				scaleTransitionForWelcomeLabel.setDuration(Duration.millis(200));
				scaleTransitionForWelcomeLabel.setFromX(welcomeLabel.getScaleX());
				scaleTransitionForWelcomeLabel.setFromY(welcomeLabel.getScaleY());
				scaleTransitionForWelcomeLabel.setToX(0);
				scaleTransitionForWelcomeLabel.setToY(0);
				scaleTransitionForWelcomeLabel.setAutoReverse(false);
				scaleTransitionForWelcomeLabel.setCycleCount(1);
				scaleTransitionForWelcomeLabel.setOnFinished(ev -> {});

				scaleTransitionForSoundIcon.setToX(0);
				scaleTransitionForSoundIcon.setToY(0);
				scaleTransitionForEditIcon.setToX(0);
				scaleTransitionForEditIcon.setToY(0);
				scaleTransitionForSettingsIcon.setToX(0);
				scaleTransitionForSettingsIcon.setToY(0);
				scaleTransitionForInfoButton.setToX(0);
				scaleTransitionForInfoButton.setToY(0);

				getAudioStuff().playSlideSound();
				translateTransitionForVBoxWithMainButtons.setToY(mainScene.getHeight() - vBoxForMainButtons.getLayoutY() + 20);
				translateTransitionForVBoxWithMainButtons.playFromStart();
				
				getAudioStuff().playMinimizeSound();
				scaleTransitionForWelcomeLabel.playFromStart();
				scaleTransitionForSoundIcon.playFromStart();
				scaleTransitionForEditIcon.playFromStart();
				scaleTransitionForSettingsIcon.playFromStart();
				scaleTransitionForInfoButton.playFromStart();
			}),
			new KeyFrame(Duration.millis(200), e ->
			{
				scaleTransitionForWelcomeImage.setToY(0);
				translateTransitionForWelcomeImage.setToY(-0.1759 * mainScene.getHeight());

				translateTransitionForLeftGlobeStand.setToX(-1.0 * (leftGlobeStand.getLayoutX() + leftGlobeStand.getFitWidth() + 20));
				translateTransitionForLeftGlobeImage.setToX(-1.0 * (leftGlobeStand.getLayoutX() + leftGlobeStand.getFitWidth() + 20));
				translateTransitionForRightGlobeStand.setToX(mainScene.getWidth() - rightGlobeStand.getLayoutX() + 20);
				translateTransitionForRightGlobeImage.setToX(mainScene.getWidth() - rightGlobeStand.getLayoutX() + 20);

				getAudioStuff().playSlideSound();
				parallelTransitionForWelcomeImage.playFromStart();
				translateTransitionForLeftGlobeStand.playFromStart();
				translateTransitionForLeftGlobeImage.playFromStart();
				translateTransitionForRightGlobeStand.playFromStart();
				translateTransitionForRightGlobeImage.playFromStart();
			}),
			new KeyFrame(Duration.millis(400), e ->
			{
				translateTransitionForGameNameImage.setToY(-1.0 * (gameNameWoodenImage.getLayoutY() + gameNameWoodenImage.getBoundsInParent().getHeight()));
				translateTransitionForWoodPanelFor1IconImage.setToY(-1.0 * (woodPanelFor1IconImage.getLayoutY() + woodPanelFor1IconImage.getBoundsInParent().getHeight()));

				if(vBoxForSettings.isVisible())
				{
					timelineToHideSettings.playFromStart();
					settingsButton.setImage(images.get(Images.SETTINGS));
				}
				else if(vBoxForSound.isVisible())
				{
					setCorrectSoundIcon(false);

					translateTransitionForVBoxForSound.setToX(mainScene.getWidth() - vBoxForSound.getLayoutX() + 20);
					translateTransitionForVBoxForSound.playFromStart();
				}

				getAudioStuff().playSlideSound();
				translateTransitionForGameNameImage.playFromStart();
				translateTransitionForWoodPanelFor1IconImage.playFromStart();
			}),
			new KeyFrame(Duration.millis(600), e ->
			{
				if(animationsUsed == ANIMATIONS.ALL) stopGlobeAnimation();

				vBoxForMainButtons.setDisable(false);
				infoButton.setDisable(false);
				editUsersButton.setDisable(false);
			})
		);

		timelineToChangeLanguage = new Timeline(
           new KeyFrame(Duration.millis(0), e ->
           {
			   scaleTransitionForVBoxForSettings.setToX(0);
			   scaleTransitionForVBoxForSettings.setToY(0);

			   if(vBoxForSound.isVisible())
			   {
			   		translateTransitionForVBoxForSound.setOnFinished(ev ->
					{
						translateTransitionForVBoxForSound.setOnFinished(eve ->{});
						vBoxForSound.setOpacity(0);
					});
			   		translateTransitionForVBoxForSound.setToX(mainScene.getWidth() - vBoxForSound.getLayoutX() + 20);

			   		translateTransitionForVBoxForSound.playFromStart();
			   }

			   getAudioStuff().playSlideSound();

			   translateTransitionForGameNameImage.setToY((-1.0 * (gameNameWoodenImage.getLayoutY() + gameNameWoodenImage.getBoundsInParent().getHeight())));

			   translateTransitionForGameNameImage.playFromStart();
			   scaleTransitionForVBoxForSettings.playFromStart();
           }),
           new KeyFrame(Duration.millis(200), e -> changeLanguage()),
           new KeyFrame(Duration.millis(300), e ->
           {
			   if(vBoxForSound.getOpacity() == 0)
			   {
				   vBoxForSound.setOpacity(1);
				   translateTransitionForVBoxForSound.setToX(0);
				   translateTransitionForVBoxForSound.playFromStart();
			   }

			   scaleTransitionForVBoxForSettings.setToX(1);
			   scaleTransitionForVBoxForSettings.setToY(1);

	           translateTransitionForGameNameImage.setToY(0);

	           getAudioStuff().playSlideSound();
	           translateTransitionForGameNameImage.playFromStart();
	           scaleTransitionForVBoxForSettings.playFromStart();
           })
		);

		timelineToChangeLanguageByPlayer = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				if(vBoxForSound.isVisible())
				{
					translateTransitionForVBoxForSound.setOnFinished(ev ->
					{
						translateTransitionForVBoxForSound.setOnFinished(eve ->{});
						vBoxForSound.setOpacity(0);
					});

					translateTransitionForVBoxForSound.setToX(mainScene.getWidth() - vBoxForSound.getLayoutX() + 20);

					translateTransitionForVBoxForSound.playFromStart();
				}

				scaleTransitionForWelcomeLabel.stop();

				scaleTransitionForWelcomeImage.setToY(0);
				translateTransitionForWelcomeImage.setToY(-0.1759 * mainScene.getHeight());

				scaleTransitionForWelcomeLabelParallel.setToY(0);
				translateTransitionForWelcomeLabel.setToY(-0.1759 * mainScene.getHeight());

				scaleTransitionForEditIcon.setToX(0);
				scaleTransitionForEditIcon.setToY(0);
				
				getAudioStuff().playMinimizeSound();

				scaleTransitionForUsersEditSegmentedButton.setToX(0);
				scaleTransitionForUsersEditSegmentedButton.setToY(0);
				scaleTransitionForUsersEditSegmentedButton.playFromStart();

				if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == switchPlayerToggleButton)
				{
					scaleTransitionForVBoxForSwitchUser.setToX(0);
					scaleTransitionForVBoxForSwitchUser.setToY(0);
					scaleTransitionForVBoxForSwitchUser.playFromStart();
				}
				else
				{
					scaleTransitionForVBoxForAddUser.setToX(0);
					scaleTransitionForVBoxForAddUser.setToY(0);
					scaleTransitionForVBoxForAddUser.playFromStart();
				}

				parallelTransitionForWelcomeImage.playFromStart();
				parallelTransitionForWelcomeLabel.playFromStart();
				scaleTransitionForEditIcon.playFromStart();
			}),
			new KeyFrame(Duration.millis(200), e ->
			{
				playersEditSegmentedButton.setVisible(false);
				textFieldForEditPlayerName.setText("");
				textFieldForAddPlayer.setText("");

				getAudioStuff().playSlideSound();
				translateTransitionForGameNameImage.setToY((-1.0 * (gameNameWoodenImage.getLayoutY() + gameNameWoodenImage.getBoundsInParent().getHeight())));
				translateTransitionForGameNameImage.playFromStart();
			}),
			new KeyFrame(Duration.millis(400), e ->
			{
				changeLanguage();
				setTextAndFontForWelcomeLabel();
			}),
			new KeyFrame(Duration.millis(600), e ->
			{
				translateTransitionForGameNameImage.setToY(0);

				getAudioStuff().playSlideSound();
				translateTransitionForGameNameImage.playFromStart();

				vBoxForMainButtons.setTranslateY(0);
				vBoxForMainButtons.setVisible(true);
				singlePlayerGameButton.setScaleX(0);
				singlePlayerGameButton.setScaleY(0);
				multiPlayerGameButton.setScaleX(0);
				multiPlayerGameButton.setScaleY(0);
				atlasButton.setScaleX(0);
				atlasButton.setScaleY(0);
				scoreBoardButton.setScaleX(0);
				scoreBoardButton.setScaleY(0);
				infoButton.setScaleX(0);
				infoButton.setScaleY(0);
				settingsButton.setScaleX(0);
				settingsButton.setScaleY(0);
			}),
			new KeyFrame(Duration.millis(800), e ->
			{
				if(vBoxForSound.getOpacity() == 0)
				{
					vBoxForSound.setOpacity(1);
					translateTransitionForVBoxForSound.setToX(0);
					translateTransitionForVBoxForSound.playFromStart();
				}

				scaleTransitionForWelcomeImage.setToY(1);
				translateTransitionForWelcomeImage.setToY(0);

				scaleTransitionForWelcomeLabelParallel.setToY(1);
				translateTransitionForWelcomeLabel.setToY(0);

				if(animationsUsed == ANIMATIONS.ALL)
				{
					parallelTransitionForWelcomeLabel.setOnFinished(ev ->
					{
						parallelTransitionForWelcomeLabel.setOnFinished(null);
						startWelcomeTextAnimation();
					});
				}

				editUsersButton.setImage(images.get(Images.PENCIL));
				scaleTransitionForEditIcon.setToX(1);
				scaleTransitionForEditIcon.setToY(1);

				getAudioStuff().playSlideSound();
				parallelTransitionForWelcomeImage.playFromStart();
				parallelTransitionForWelcomeLabel.playFromStart();
				timelineToPopUpMainButtons.playFromStart();
				scaleTransitionForEditIcon.playFromStart();
			}),
			new KeyFrame(Duration.millis(1000), e->
			{
				vBoxForEditPlayerName.setVisible(false);
				vBoxForEditPlayerName.setScaleX(0);
				vBoxForEditPlayerName.setScaleY(0);

				editUsersButton.setDisable(false);
				infoButton.setDisable(false);
				settingsButton.setDisable(false);
			})
		);

		timelineToPopUpMainButtons = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				getAudioStuff().playPopUpSound();
				sequentialTransitionForOnePlayerGameButton.playFromStart();
			}),
			new KeyFrame(Duration.millis(150), e ->
			{
				getAudioStuff().playPopUpSound();
				sequentialTransitionForTwoPlayersGameButton.playFromStart();
			}),
			new KeyFrame(Duration.millis(300), e ->
			{
				getAudioStuff().playPopUpSound();
				sequentialTransitionForAtlasButton.playFromStart();
			}),
			new KeyFrame(Duration.millis(450), e ->
			{
				getAudioStuff().playPopUpSound();
				sequentialTransitionForScoreBoardButton.playFromStart();
			}),
			new KeyFrame(Duration.millis(600), e ->
			{
				if(infoButton.getScaleX() == 0)
				{
					getAudioStuff().playPopUpSound();
					sequentialTransitionForInfoButton.playFromStart();
				}
			}),
			new KeyFrame(Duration.millis(750), e ->
			{
				if(settingsButton.getScaleX() == 0)
				{
					getAudioStuff().playPopUpSound();
					sequentialTransitionForSettingsButton.playFromStart();
				}
			}));

		timelineToShowSoundOptions = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				soundButton.setDisable(true);

				vBoxForSound.setVisible(true);
				translateTransitionForVBoxForSound.setToX(0);

				getAudioStuff().playSlideSound();
				translateTransitionForVBoxForSound.playFromStart();
			}),
			new KeyFrame(Duration.millis(200), e -> soundButton.setDisable(false)));

		timelineToHideSoundOptions = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				soundButton.setDisable(true);

				translateTransitionForVBoxForSound.setToX(mainScene.getWidth() - vBoxForSound.getLayoutX() + 20);

				getAudioStuff().playSlideSound();
				translateTransitionForVBoxForSound.playFromStart();
			}),
			new KeyFrame(Duration.millis(200), e ->
			{
				vBoxForSound.setVisible(false);

				soundButton.setDisable(false);
			}));

		timelineToShowSettings = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					settingsButton.setDisable(true);

					scaleTransitionForWelcomeLabel.setDuration(Duration.millis(200));
					scaleTransitionForWelcomeLabel.setFromX(welcomeLabel.getScaleX());
					scaleTransitionForWelcomeLabel.setFromY(welcomeLabel.getScaleY());
					scaleTransitionForWelcomeLabel.setToX(0);
					scaleTransitionForWelcomeLabel.setToY(0);
					scaleTransitionForWelcomeLabel.setAutoReverse(false);
					scaleTransitionForWelcomeLabel.setCycleCount(1);

					scaleTransitionForWelcomeLabel.setOnFinished(null);

					editUsersButton.setDisable(true);
					scaleTransitionForEditIcon.setToX(0);
					scaleTransitionForEditIcon.setToY(0);

					infoButton.setDisable(true);
					scaleTransitionForInfoButton.setToX(0);
					scaleTransitionForInfoButton.setToY(0);

					getAudioStuff().playMinimizeSound();
					scaleTransitionForWelcomeLabel.playFromStart();
					scaleTransitionForEditIcon.playFromStart();
					scaleTransitionForInfoButton.playFromStart();
				}),
				new KeyFrame(Duration.millis(200), e ->
				{
					scaleTransitionForWelcomeImage.setToY(0);
					translateTransitionForWelcomeImage.setToY(-0.1759 * mainScene.getHeight());

					getAudioStuff().playSlideSound();
					parallelTransitionForWelcomeImage.playFromStart();

					translateTransitionForVBoxWithMainButtons.setToY(mainScene.getHeight() - vBoxForMainButtons.getLayoutY() + 20);
					translateTransitionForVBoxWithMainButtons.playFromStart();
				}),
				new KeyFrame(Duration.millis(400), e ->
				{
					vBoxForMainButtons.setOpacity(0);

					vBoxForSettings.setVisible(true);
					scaleTransitionForVBoxForSettings.setToX(1);
					scaleTransitionForVBoxForSettings.setToY(1);

					getAudioStuff().playMaximizeSound();
					scaleTransitionForVBoxForSettings.playFromStart();
				}),
				new KeyFrame(Duration.millis(600), e -> settingsButton.setDisable(false)));

		timelineToHideSettings = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					settingsButton.setDisable(true);
					scaleTransitionForVBoxForSettings.setToX(0);
					scaleTransitionForVBoxForSettings.setToY(0);

					getAudioStuff().playMinimizeSound();
					scaleTransitionForVBoxForSettings.playFromStart();
				}),
				new KeyFrame(Duration.millis(200), e ->
				{
					vBoxForSettings.setVisible(false);

					translateTransitionForWelcomeImage.setToY(0);
					scaleTransitionForWelcomeImage.setToY(1);

					getAudioStuff().playSlideSound();
					parallelTransitionForWelcomeImage.playFromStart();

					vBoxForMainButtons.setOpacity(1);
					translateTransitionForVBoxWithMainButtons.setToY(0);
					translateTransitionForVBoxWithMainButtons.playFromStart();
				}),
				new KeyFrame(Duration.millis(400), e ->
				{
					scaleTransitionForWelcomeLabel.setDuration(Duration.millis(200));
					scaleTransitionForWelcomeLabel.setAutoReverse(false);
					scaleTransitionForWelcomeLabel.setCycleCount(1);
					scaleTransitionForWelcomeLabel.setFromX(0);
					scaleTransitionForWelcomeLabel.setFromY(0);
					scaleTransitionForWelcomeLabel.setToX(0.95);
					scaleTransitionForWelcomeLabel.setToY(0.95);

					if(animationsUsed == ANIMATIONS.ALL)
					{
						scaleTransitionForWelcomeLabel.setOnFinished(ev ->
						{
							scaleTransitionForWelcomeLabel.setOnFinished(null);
							startWelcomeTextAnimation();
						});
					}

					scaleTransitionForEditIcon.setToX(1);
					scaleTransitionForEditIcon.setToY(1);
					scaleTransitionForInfoButton.setToX(1);
					scaleTransitionForInfoButton.setToY(1);

					getAudioStuff().playMaximizeSound();
					scaleTransitionForWelcomeLabel.playFromStart();
					scaleTransitionForEditIcon.playFromStart();
					scaleTransitionForInfoButton.playFromStart();
				}),
				new KeyFrame(Duration.millis(600), e ->
				{
					settingsButton.setDisable(false);
					infoButton.setDisable(false);
					editUsersButton.setDisable(false);
				}));

		timelineToShowEditUsersVBox = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				editUsersButton.setDisable(true);
				editUsersButton.setImage(images.get(Images.PENCIL_DISABLED));

				translateTransitionForVBoxWithMainButtons.setToY(mainScene.getHeight() - vBoxForMainButtons.getLayoutY() + 20);

				infoButton.setDisable(true);
				scaleTransitionForInfoButton.setToX(0);
				scaleTransitionForInfoButton.setToY(0);

				settingsButton.setDisable(true);
				scaleTransitionForSettingsIcon.setToX(0);
				scaleTransitionForSettingsIcon.setToY(0);

				getAudioStuff().playSlideSound();
				translateTransitionForVBoxWithMainButtons.playFromStart();
				scaleTransitionForInfoButton.playFromStart();
				scaleTransitionForSettingsIcon.playFromStart();
			}),
			new KeyFrame(Duration.millis(200), e ->
			{
				getAudioStuff().playMaximizeSound();

				vBoxForMainButtons.setVisible(false);

				playersEditSegmentedButton.setVisible(true);
				scaleTransitionForUsersEditSegmentedButton.setToX(1);
				scaleTransitionForUsersEditSegmentedButton.setToY(1);
				scaleTransitionForUsersEditSegmentedButton.playFromStart();

				if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == editPlayerToggleButton)
				{
					vBoxForEditPlayerName.setVisible(true);
					scaleTransitionForVBoxForEditUsername.setToX(1);
					scaleTransitionForVBoxForEditUsername.setToY(1);
					scaleTransitionForVBoxForEditUsername.playFromStart();
				}
				else if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == switchPlayerToggleButton)
				{
					vBoxForSwitchPlayer.setVisible(true);
					scaleTransitionForVBoxForSwitchUser.setToX(1);
					scaleTransitionForVBoxForSwitchUser.setToY(1);
					scaleTransitionForVBoxForSwitchUser.playFromStart();
				}
				else
				{
					vBoxForAddPlayer.setVisible(true);
					scaleTransitionForVBoxForAddUser.setToX(1);
					scaleTransitionForVBoxForAddUser.setToY(1);
					scaleTransitionForVBoxForAddUser.playFromStart();
				}
			}));

		timelineToHideEditUsersVBox = new Timeline(
			new KeyFrame(Duration.millis(200), e ->
			{
				scaleTransitionForInfoButton.setToX(1);
				scaleTransitionForInfoButton.setToY(1);

				scaleTransitionForSettingsIcon.setToX(1);
				scaleTransitionForSettingsIcon.setToY(1);

				getAudioStuff().playMinimizeSound();

				scaleTransitionForUsersEditSegmentedButton.setToX(0);
				scaleTransitionForUsersEditSegmentedButton.setToY(0);
				scaleTransitionForUsersEditSegmentedButton.playFromStart();

				if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == editPlayerToggleButton)
				{
					scaleTransitionForVBoxForEditUsername.setToX(0);
					scaleTransitionForVBoxForEditUsername.setToY(0);
					scaleTransitionForVBoxForEditUsername.playFromStart();
				}
				else if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == switchPlayerToggleButton)
				{
					scaleTransitionForVBoxForSwitchUser.setToX(0);
					scaleTransitionForVBoxForSwitchUser.setToY(0);
					scaleTransitionForVBoxForSwitchUser.playFromStart();
				}
				else
				{
					scaleTransitionForVBoxForAddUser.setToX(0);
					scaleTransitionForVBoxForAddUser.setToY(0);
					scaleTransitionForVBoxForAddUser.playFromStart();
				}

				scaleTransitionForInfoButton.playFromStart();
				scaleTransitionForSettingsIcon.playFromStart();
			}),
			new KeyFrame(Duration.millis(400), e ->
			{
				playersEditSegmentedButton.setVisible(false);
				vBoxForEditPlayerName.setVisible(false);
				vBoxForSwitchPlayer.setVisible(false);
				vBoxForAddPlayer.setVisible(false);

				textFieldForEditPlayerName.setText("");
				textFieldForAddPlayer.setText("");

				getAudioStuff().playSlideSound();
				vBoxForMainButtons.setVisible(true);
				translateTransitionForVBoxWithMainButtons.setToY(0);
				translateTransitionForVBoxWithMainButtons.playFromStart();

				editUsersButton.setImage(images.get(Images.PENCIL));
				editUsersButton.setDisable(false);

				infoButton.setDisable(false);
				settingsButton.setDisable(false);
			}));

		timelineToChangeUsersEditVBoxes = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					getAudioStuff().playMinimizeSound();
					if(vBoxForEditPlayerName.isVisible())
					{
						scaleTransitionForVBoxForEditUsername.setToX(0);
						scaleTransitionForVBoxForEditUsername.setToY(0);
						scaleTransitionForVBoxForEditUsername.playFromStart();
					}
					else if(vBoxForSwitchPlayer.isVisible())
					{
						scaleTransitionForVBoxForSwitchUser.setToX(0);
						scaleTransitionForVBoxForSwitchUser.setToY(0);
						scaleTransitionForVBoxForSwitchUser.playFromStart();
					}
					else
					{
						scaleTransitionForVBoxForAddUser.setToX(0);
						scaleTransitionForVBoxForAddUser.setToY(0);
						scaleTransitionForVBoxForAddUser.playFromStart();
					}
				}),
				new KeyFrame(Duration.millis(200), e ->
				{
					if(vBoxForEditPlayerName.isVisible()) vBoxForEditPlayerName.setVisible(false);
					else if(vBoxForSwitchPlayer.isVisible()) vBoxForSwitchPlayer.setVisible(false);
					else vBoxForAddPlayer.setVisible(false);

					getAudioStuff().playMaximizeSound();
					if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == editPlayerToggleButton)
					{
						vBoxForEditPlayerName.setVisible(true);
						scaleTransitionForVBoxForEditUsername.setToX(1);
						scaleTransitionForVBoxForEditUsername.setToY(1);
						scaleTransitionForVBoxForEditUsername.playFromStart();
					}
					else if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == switchPlayerToggleButton)
					{
						vBoxForSwitchPlayer.setVisible(true);
						scaleTransitionForVBoxForSwitchUser.setToX(1);
						scaleTransitionForVBoxForSwitchUser.setToY(1);
						scaleTransitionForVBoxForSwitchUser.playFromStart();
					}
					else
					{
						vBoxForAddPlayer.setVisible(true);
						scaleTransitionForVBoxForAddUser.setToX(1);
						scaleTransitionForVBoxForAddUser.setToY(1);
						scaleTransitionForVBoxForAddUser.playFromStart();
					}
				}));

		timelineToShowInformationAboutGame = new Timeline(
			 new KeyFrame(Duration.millis(0), e ->
			 {
				 infoButton.setDisable(true);

				 scaleTransitionForWelcomeLabel.setDuration(Duration.millis(200));
				 scaleTransitionForWelcomeLabel.setFromX(welcomeLabel.getScaleX());
				 scaleTransitionForWelcomeLabel.setFromY(welcomeLabel.getScaleY());
				 scaleTransitionForWelcomeLabel.setToX(0);
				 scaleTransitionForWelcomeLabel.setToY(0);
				 scaleTransitionForWelcomeLabel.setAutoReverse(false);
				 scaleTransitionForWelcomeLabel.setCycleCount(1);

				 scaleTransitionForWelcomeLabel.setOnFinished(ev -> {});

				 editUsersButton.setDisable(true);
				 scaleTransitionForEditIcon.setToX(0);
				 scaleTransitionForEditIcon.setToY(0);

				 settingsButton.setDisable(true);
				 scaleTransitionForSettingsIcon.setToX(0);
				 scaleTransitionForSettingsIcon.setToY(0);

				 getAudioStuff().playMinimizeSound();
				 scaleTransitionForWelcomeLabel.playFromStart();
				 scaleTransitionForEditIcon.playFromStart();
				 scaleTransitionForSettingsIcon.playFromStart();
			 }),
			 new KeyFrame(Duration.millis(200), e ->
			 {
			 	 scaleTransitionForWelcomeImage.setToY(0);
				 translateTransitionForWelcomeImage.setToY(-0.1759 * mainScene.getHeight());

				 getAudioStuff().playSlideSound();
				 parallelTransitionForWelcomeImage.playFromStart();

				 translateTransitionForVBoxWithMainButtons.setToY(mainScene.getHeight() - vBoxForMainButtons.getLayoutY() + 20);
				 translateTransitionForVBoxWithMainButtons.playFromStart();
			 }),
			 new KeyFrame(Duration.millis(400), e ->
			 {
				 rectangleForInformationAboutGame.setVisible(true);
				 scaleTransitionForRectangleForInfoAboutGame.setToX(1);
				 scaleTransitionForRectangleForInfoAboutGame.setToY(1);

				 getAudioStuff().playMaximizeSound();
				 scaleTransitionForRectangleForInfoAboutGame.playFromStart();
			 }),
			 new KeyFrame(Duration.millis(600), e -> infoButton.setDisable(false))
		);

		timelineToHideInformationAboutGame = new Timeline(
             new KeyFrame(Duration.millis(0), e ->
             {
	             infoButton.setDisable(true);
	             scaleTransitionForRectangleForInfoAboutGame.setToX(0);
	             scaleTransitionForRectangleForInfoAboutGame.setToY(0);

	             getAudioStuff().playMinimizeSound();
		         scaleTransitionForRectangleForInfoAboutGame.playFromStart();
             }),
             new KeyFrame(Duration.millis(200), e ->
             {
             	rectangleForInformationAboutGame.setVisible(false);

	             translateTransitionForWelcomeImage.setToY(0);
	             scaleTransitionForWelcomeImage.setToY(1);

	             getAudioStuff().playSlideSound();
				 parallelTransitionForWelcomeImage.playFromStart();

				 vBoxForMainButtons.setOpacity(1);
				 translateTransitionForVBoxWithMainButtons.setToY(0);
				 translateTransitionForVBoxWithMainButtons.playFromStart();
             }),
			 new KeyFrame(Duration.millis(400), e ->
			 {
				 scaleTransitionForWelcomeLabel.setDuration(Duration.millis(200));
				 scaleTransitionForWelcomeLabel.setAutoReverse(false);
				 scaleTransitionForWelcomeLabel.setCycleCount(1);
				 scaleTransitionForWelcomeLabel.setFromX(0);
				 scaleTransitionForWelcomeLabel.setFromY(0);
				 scaleTransitionForWelcomeLabel.setToX(0.95);
				 scaleTransitionForWelcomeLabel.setToY(0.95);

				 if(animationsUsed == ANIMATIONS.ALL)
				 {
					 scaleTransitionForWelcomeLabel.setOnFinished(ev ->
							 {
									scaleTransitionForWelcomeLabel.setOnFinished(eve -> {});
									startWelcomeTextAnimation();
							 });
				 }

				 scaleTransitionForEditIcon.setToX(1);
				 scaleTransitionForEditIcon.setToY(1);

				 scaleTransitionForSettingsIcon.setToX(1);
				 scaleTransitionForSettingsIcon.setToY(1);

				 getAudioStuff().playMaximizeSound();
				 scaleTransitionForWelcomeLabel.playFromStart();
				 scaleTransitionForEditIcon.playFromStart();
				 scaleTransitionForSettingsIcon.playFromStart();
			 }),
			 new KeyFrame(Duration.millis(600), e ->
			 {
			 	infoButton.setDisable(false);
			 	editUsersButton.setDisable(false);
			 	settingsButton.setDisable(false);
			 })
		);
	}

	protected void setupAdvancedAnimations()
	{
		leftGlobeStatus = -1;
		rightGlobeStatus = -1;
		previousTransitionOfLeftGlobeWasRight = false;
		previousTransitionOfRightGlobeWasRight = false;

		timelineForLeftGlobe = new Timeline(
				new KeyFrame(Duration.millis(getImageStuff().MILLIS_FOR_24FPS_ANIMATION), e ->
				{
					if (leftGlobeStatus == 1)
					{
						leftGlobeCounter--;
						if (leftGlobeCounter < 1) leftGlobeCounter = getImageStuff().NUMBER_OF_ANIMATED_GLOBES;
					}
					else if (leftGlobeStatus == -1)
					{
						leftGlobeCounter++;
						if (leftGlobeCounter > getImageStuff().NUMBER_OF_ANIMATED_GLOBES) leftGlobeCounter = 1;
					}
					else timelineForLeftGlobe.stop();
					if(getImageStuff().getAnimatedGlobes()[leftGlobeCounter - 1] != null)
						leftGlobeImage.setImage(getImageStuff().getAnimatedGlobes()[leftGlobeCounter - 1]);
				}));
		timelineForLeftGlobe.setCycleCount(Timeline.INDEFINITE);

		timelineForRightGlobe = new Timeline(
				new KeyFrame(Duration.millis(getImageStuff().MILLIS_FOR_24FPS_ANIMATION), e ->
				{
					if (rightGlobeStatus == 1)
					{
						rightGlobeCounter--;
						if (rightGlobeCounter < 1) rightGlobeCounter = getImageStuff().NUMBER_OF_ANIMATED_GLOBES;
					}
					else if (rightGlobeStatus == -1)
					{
						rightGlobeCounter++;
						if (rightGlobeCounter > getImageStuff().NUMBER_OF_ANIMATED_GLOBES) rightGlobeCounter = 1;
					}
					else timelineForRightGlobe.stop();
					if(getImageStuff().getAnimatedGlobes()[rightGlobeCounter - 1] != null)
						rightGlobeImage.setImage(getImageStuff().getAnimatedGlobes()[rightGlobeCounter - 1]);
				}));
		timelineForRightGlobe.setCycleCount(Timeline.INDEFINITE);
	}

	private void setTextAndFontForWelcomeLabel()
	{
		int length = getCurrentPlayer().getEditedName().length();
		double size;

		//		set correct font size based on name length
		if (length > 34) size = 0.0340 * welcomeLabel.getPrefWidth(); // 25 -> 735
		else if (length > 30) size = 0.0408 * welcomeLabel.getPrefWidth(); // 30 -> 735
		else if (length > 23) size = 0.0435 * welcomeLabel.getPrefWidth(); // 32 -> 735
		else if (length > 19) size = 0.0476 * welcomeLabel.getPrefWidth(); // 35 -> 735
		else if (length > 16) size = 0.0544 * welcomeLabel.getPrefWidth(); // 40 -> 735
		else if (length > 14) size = 0.0612 * welcomeLabel.getPrefWidth(); // 45 -> 735
		else if (length > 13) size = 0.0653 * welcomeLabel.getPrefWidth(); // 48 -> 735
		else if (length > 11) size = 0.0680 * welcomeLabel.getPrefWidth(); // 50 -> 735
		else if (length > 9) size = 0.0748 * welcomeLabel.getPrefWidth(); // 55 -> 735
		else if (length > 7) size = 0.0816 * welcomeLabel.getPrefWidth(); // 60 -> 735
		else if (length > 6) size = 0.0884 * welcomeLabel.getPrefWidth(); // 65 -> 735
		else if (length > 5) size = 0.0925 * welcomeLabel.getPrefWidth(); // 68 -> 735
		else if (length > 4) size = 0.0970 * welcomeLabel.getPrefWidth(); // 71 -> 735
		else size = 0.1020 * welcomeLabel.getPrefWidth(); // 75 -> 735

		welcomeLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, size));
		welcomeLabel.setText(String.format("%s%s", languageResourceBundle.getString("welcomeLabel"), getCurrentPlayer().getEditedName()));
	}

	private void setUIToggleValuesBasedOnSettings()
	{
		startInFullScreenCheckBox.setSelected(getCurrentPlayer().getStartAtFullScreen());

		if(animationsUsed == ANIMATIONS.NO) noAnimationsToggleButton.setSelected(true);
		else if(animationsUsed == ANIMATIONS.LIMITED) limitedAnimationsToggleButton.setSelected(true);
		else allAnimationsToggleButton.setSelected(true);

		if(getCurrentPlayer().getLocaleIndex() != -1) selectCountryInComboBoxBasedOnLocale();

		if(getCurrentPlayer().getUnitSystem() == UNIT_SYSTEM.METRIC) metricSystemRadioButton.setSelected(true);
		else imperialSystemRadioButton.setSelected(true);
	}

	private void editUsername(String newUsername)
	{
		getCurrentPlayer().setOriginalName(newUsername);
		getCurrentPlayer().setEditedName(getEditedOriginalName(newUsername));

		playersObservableList.set(0, newUsername);

		if(animationsUsed != ANIMATIONS.NO)
		{
			timelineToHideEditUsersVBox.playFromStart();

			hideAndShowWelcomeLabelWithNewUsernameAnimation();
		}
		else
		{
			hideEditUsersBoxesNoAnimations();
			setTextAndFontForWelcomeLabel();
		}
	}

	private void updateStageWithNewWidth()
	{
		stage.setX(getScreenStuff().getPrimaryScreenWidth() / 2.0 - getScreenStuff().getWindowWidth() / 2.0);
		stage.setY(getScreenStuff().getPrimaryScreenHeight() / 2.0 - getScreenStuff().getWindowHeight() / 2.0);
		stage.setWidth(getScreenStuff().getWindowWidth());
		stage.setHeight(getScreenStuff().getWindowHeight());
		recalculateBackground(getScreenStuff().getWindowWidth(), getScreenStuff().getWindowHeight());
		recalculateUI(getScreenStuff().getWindowWidth(), getScreenStuff().getWindowHeight());
	}

	private void changeCurrentPlayer(int index)
	{
		LANGUAGE prevLan = getCurrentLanguage();

		String username = playersObservableList.get(index);
		playersObservableList.remove(index);
		playersObservableList.add(0, username);

		Player tempPlayer = playersArrayList.remove(index);
		playersArrayList.add(0, tempPlayer);
		FilesIO.writePlayersFile();

		PowerOn.setSettingsBasedOnCurrentPlayer();

		updateStageWithNewWidth();

		setUIToggleValuesBasedOnSettings();

		if(prevLan != getCurrentLanguage())
		{
			FilesIO.writeCurrentLanguage(getCurrentLanguage());

			if(animationsUsed != ANIMATIONS.NO) timelineToChangeLanguageByPlayer.playFromStart();
			else changeLanguage();
		}
		else
		{
			if(animationsUsed != ANIMATIONS.NO)
			{
				timelineToHideEditUsersVBox.playFromStart();
				hideAndShowWelcomeLabelWithNewUsernameAnimation();
			}
			else
			{
				hideEditUsersBoxesNoAnimations();
				setTextAndFontForWelcomeLabel();
			}
		}
	}

	private void addPlayer(String username)
	{
		LANGUAGE prevLan = getCurrentLanguage();

		playersArrayList.add(0, new Player(username, getEditedOriginalName(username)));
		FilesIO.writePlayersFile();

		playersObservableList.add(0, username);

		PowerOn.setSettingsBasedOnCurrentPlayer();

		updateStageWithNewWidth();

		setUIToggleValuesBasedOnSettings();

		if(prevLan != getCurrentLanguage())
		{
			FilesIO.writeCurrentLanguage(getCurrentLanguage());

			if(animationsUsed != ANIMATIONS.NO) timelineToChangeLanguageByPlayer.playFromStart();
			else changeLanguage();
		}
		else
		{
			if(animationsUsed != ANIMATIONS.NO)
			{
				timelineToHideEditUsersVBox.playFromStart();
				hideAndShowWelcomeLabelWithNewUsernameAnimation();
			}
			else
			{
				hideEditUsersBoxesNoAnimations();
				setTextAndFontForWelcomeLabel();
			}
		}
	}

	public void playGlobeAnimation()
	{
		timelineForLeftGlobe.playFromStart();
		timelineForRightGlobe.playFromStart();

		leftGlobeImage.setCursor(Cursor.HAND);
		rightGlobeImage.setCursor(Cursor.HAND);
	}

	public void stopGlobeAnimation()
	{
		timelineForLeftGlobe.pause();
		timelineForRightGlobe.pause();

		leftGlobeImage.setCursor(Cursor.DEFAULT);
		rightGlobeImage.setCursor(Cursor.DEFAULT);
	}

	private void startWelcomeTextAnimation()
	{
		if(welcomeLabel.getScaleX() != 0.95)
		{
			double time = (Math.abs(welcomeLabel.getScaleX() - 0.95) * 10) * TEXT_SCALE_ANIMATION_TIME;
			scaleTransitionForWelcomeLabel.setDuration(Duration.millis(time));
			scaleTransitionForWelcomeLabel.setFromX(welcomeLabel.getScaleX());
			scaleTransitionForWelcomeLabel.setFromY(welcomeLabel.getScaleY());
			scaleTransitionForWelcomeLabel.setToX(0.95);
			scaleTransitionForWelcomeLabel.setToY(0.95);
			scaleTransitionForWelcomeLabel.setCycleCount(1);
			scaleTransitionForWelcomeLabel.setAutoReverse(false);

			scaleTransitionForWelcomeLabel.setOnFinished(e ->
			{
				scaleTransitionForWelcomeLabel.setDuration(Duration.millis(TEXT_SCALE_ANIMATION_TIME));
				scaleTransitionForWelcomeLabel.setFromX(0.95);
				scaleTransitionForWelcomeLabel.setFromY(0.95);
				scaleTransitionForWelcomeLabel.setToX(1.05);
				scaleTransitionForWelcomeLabel.setToY(1.05);
				scaleTransitionForWelcomeLabel.setCycleCount(Animation.INDEFINITE);
				scaleTransitionForWelcomeLabel.setAutoReverse(true);

				scaleTransitionForWelcomeLabel.setOnFinished(null);

				scaleTransitionForWelcomeLabel.playFromStart();
			});
			scaleTransitionForWelcomeLabel.playFromStart();
		}
		else
		{
			scaleTransitionForWelcomeLabel.setDuration(Duration.millis(TEXT_SCALE_ANIMATION_TIME));
			scaleTransitionForWelcomeLabel.setFromX(0.95);
			scaleTransitionForWelcomeLabel.setFromY(0.95);
			scaleTransitionForWelcomeLabel.setToX(1.05);
			scaleTransitionForWelcomeLabel.setToY(1.05);
			scaleTransitionForWelcomeLabel.setCycleCount(Animation.INDEFINITE);
			scaleTransitionForWelcomeLabel.setAutoReverse(true);

			scaleTransitionForWelcomeLabel.setOnFinished(null);

			scaleTransitionForWelcomeLabel.playFromStart();
		}
	}

	public void resumeWelcomeTextAnimation()
	{
		if(welcomeLabel.getScaleX() != 0) scaleTransitionForWelcomeLabel.play();
	}

	public void pauseWelcomeTextAnimation()
	{
		scaleTransitionForWelcomeLabel.pause();
	}

	private void stopWelcomeTextAnimation()
	{
		double time = (Math.abs(1 - welcomeLabel.getScaleX()) / 0.05) * 1000.0;
		scaleTransitionForWelcomeLabel.setDuration(Duration.millis(time));
		scaleTransitionForWelcomeLabel.setFromX(welcomeLabel.getScaleX());
		scaleTransitionForWelcomeLabel.setFromY(welcomeLabel.getScaleY());
		scaleTransitionForWelcomeLabel.setToX(1);
		scaleTransitionForWelcomeLabel.setToY(1);
		scaleTransitionForWelcomeLabel.setCycleCount(1);
		scaleTransitionForWelcomeLabel.setAutoReverse(false);

		scaleTransitionForWelcomeLabel.setOnFinished(null);

		scaleTransitionForWelcomeLabel.playFromStart();
	}

	private void updateStrings()
	{
		stage.setTitle(languageResourceBundle.getString("gameName"));
		soundButton.getTooltip().setText(languageResourceBundle.getString("soundOptionsTooltip"));

		animationsUsedLabel.setText(languageResourceBundle.getString("animationsUsageLabel"));
		startInFullScreenCheckBox.setText(languageResourceBundle.getString("startInFullScreenCheckBox"));

		noAnimationsToggleButton.setText(languageResourceBundle.getString("noAnimationsUsed"));
		limitedAnimationsToggleButton.setText(languageResourceBundle.getString("someAnimationsUsed"));
		allAnimationsToggleButton.setText(languageResourceBundle.getString("allAnimationsUsed"));

		selectCountryLabel.setText(languageResourceBundle.getString("selectCountryLabel"));
		selectLanguageLabel.setText(languageResourceBundle.getString("selectLanguageLabel"));
		selectUnitSystemLabel.setText(languageResourceBundle.getString("selectUnitSystemLabel"));
		metricSystemRadioButton.setText(languageResourceBundle.getString("metricRadioButton"));
		imperialSystemRadioButton.setText(languageResourceBundle.getString("imperialRadioButton"));

		updateMasterVolumeText();
		updateMusicVolumeText();
		updateOtherVolumeText();

		welcomeLabel.getTooltip().setText(languageResourceBundle.getString("welcomeLabelTooltip"));
		editUsersButton.getTooltip().setText(languageResourceBundle.getString("editNameTooltip"));
		infoButton.getTooltip().setText(languageResourceBundle.getString("infoIconTooltip"));
		settingsButton.getTooltip().setText(languageResourceBundle.getString("settingsTooltip"));

		singlePlayerGameButton.setText(languageResourceBundle.getString("onePlayerGameButton"));
		singlePlayerGameButton.getTooltip().setText(languageResourceBundle.getString("onePlayerGameButtonTooltip"));
		multiPlayerGameButton.setText(languageResourceBundle.getString("twoPlayersGameButton"));
		multiPlayerGameButton.getTooltip().setText(languageResourceBundle.getString("twoPlayersGameButtonTooltip"));
		atlasButton.setText(languageResourceBundle.getString("atlasButton"));
		atlasButton.getTooltip().setText(languageResourceBundle.getString("atlasButtonTooltip"));
		scoreBoardButton.setText(languageResourceBundle.getString("scoreBoardButton"));
		scoreBoardButton.getTooltip().setText(languageResourceBundle.getString("scoreBoardButtonTooltip"));
		editPlayerToggleButton.setText(languageResourceBundle.getString("editUsernameLabel"));
		switchPlayerToggleButton.setText(languageResourceBundle.getString("switchUserLabel"));
		addPlayerToggleButton.setText(languageResourceBundle.getString("addUserLabel"));
		editPlayerLabel.setText(languageResourceBundle.getString("editUsernameLabel"));
		switchPlayerLabel.setText(languageResourceBundle.getString("switchUserLabel"));
		addPlayerLabel.setText(languageResourceBundle.getString("addUserLabel"));
		textFieldForEditPlayerName.setPromptText(languageResourceBundle.getString("textFieldForEditUsernamePromptText"));
		textFieldForEditPlayerName.getTooltip().setText(languageResourceBundle.getString("textFieldEmpty"));
		textFieldForAddPlayer.setPromptText(languageResourceBundle.getString("textFieldForAddUserPromptText"));
		textFieldForAddPlayer.getTooltip().setText(languageResourceBundle.getString("textFieldEmpty"));
		cancelEditPlayerNameButton.setText(languageResourceBundle.getString("cancel"));
		cancelEditPlayerNameButton.getTooltip().setText(languageResourceBundle.getString("cancelEditUsernameButtonTooltip"));
		cancelSwitchPlayerButton.setText(languageResourceBundle.getString("cancel"));
		cancelSwitchPlayerButton.getTooltip().setText(languageResourceBundle.getString("cancelSwitchUserButtonTooltip"));
		cancelAddPlayerButton.setText(languageResourceBundle.getString("cancel"));
		cancelAddPlayerButton.getTooltip().setText(languageResourceBundle.getString("cancelAddUserButtonTooltip"));
		confirmEditPlayerNameButton.setText(languageResourceBundle.getString("change"));
		confirmEditPlayerNameButton.getTooltip().setText(languageResourceBundle.getString("confirmEditUsernameButtonTooltip"));
		confirmSwitchPlayerButton.setText(languageResourceBundle.getString("switch"));
		confirmSwitchPlayerButton.getTooltip().setText(languageResourceBundle.getString("confirmSwitchUserButtonTooltip"));
		confirmAddPlayerButton.setText(languageResourceBundle.getString("add"));
		confirmAddPlayerButton.getTooltip().setText(languageResourceBundle.getString("confirmAddUserButtonTooltip"));
		deleteAllDataButton.setText(languageResourceBundle.getString("deleteAllDataLabel"));
		deleteAllDataButton.getTooltip().setText(languageResourceBundle.getString("deleteAllDataTooltip"));
		deleteSingleUserButton.setText(languageResourceBundle.getString("deleteSingleUserLabel"));
		deleteSingleUserButton.getTooltip().setText(languageResourceBundle.getString("deleteSingleUserUnableToDeleteMessage"));
		deleteAllUsersButton.setText(languageResourceBundle.getString("deleteAll"));
		deleteAllUsersButton.getTooltip().setText(languageResourceBundle.getString("deleteAllUsersTooltip"));
		cancelPopUpActionButton.setText(languageResourceBundle.getString("cancel"));
		confirmDeleteCheckBox.setText(languageResourceBundle.getString("confirmDeleteCheckBox"));
	}

	private void changeLanguage()
	{
		PowerOn.loadLanguageResourceBundle(getCurrentLanguage());
		FilesIO.writeCurrentLanguage(getCurrentLanguage());

		updateStrings();

		getImageStuff().loadGameNameImage();
		gameNameWoodenImage.setImage(images.get(Images.GAME_NAME));

		Thread t1 = new Thread(() ->
		{
			FilesIO.readCountriesDataFile();
			FilesIO.readContinentsDataFile();
		});

		Thread t2 = new Thread(() ->
		{
			FilesIO.readStatesOfUSADataFile();
			FilesIO.readGreekDecentralizedAdministrationsDataFile();
			FilesIO.readGreekRegionsDataFile();
			FilesIO.readGreekRegionalUnitsDataFile();
			FilesIO.readAttractionsDataFile();
		});

		t1.start();
		t2.start();

		try
		{
			t1.join();
			t2.join();
		}
		catch (Exception e) {}

		loadCountriesLocalesObservableList();
	}

	private void changeUsersEditVBox()
	{
		if(animationsUsed != ANIMATIONS.NO) timelineToChangeUsersEditVBoxes.playFromStart();
		else
		{
			if(vBoxForEditPlayerName.isVisible())
			{
				vBoxForEditPlayerName.setVisible(false);
				vBoxForEditPlayerName.setScaleX(0);
				vBoxForEditPlayerName.setScaleY(0);
			}
			else if(vBoxForSwitchPlayer.isVisible())
			{
				vBoxForSwitchPlayer.setVisible(false);
				vBoxForSwitchPlayer.setScaleX(0);
				vBoxForSwitchPlayer.setScaleY(0);
			}
			else
			{
				vBoxForAddPlayer.setVisible(false);
				vBoxForAddPlayer.setScaleX(0);
				vBoxForAddPlayer.setScaleY(0);
			}

			if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == editPlayerToggleButton)
			{
				vBoxForEditPlayerName.setScaleX(1);
				vBoxForEditPlayerName.setScaleY(1);
				vBoxForEditPlayerName.setVisible(true);
			}
			else if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == switchPlayerToggleButton)
			{
				vBoxForSwitchPlayer.setScaleX(1);
				vBoxForSwitchPlayer.setScaleY(1);
				vBoxForSwitchPlayer.setVisible(true);
			}
			else
			{
				vBoxForAddPlayer.setScaleX(1);
				vBoxForAddPlayer.setScaleY(1);
				vBoxForAddPlayer.setVisible(true);
			}
		}
	}

	private void hideEditUsersBoxesNoAnimations()
	{
		editUsersButton.setImage(images.get(Images.PENCIL));

		playersEditSegmentedButton.setVisible(false);
		playersEditSegmentedButton.setScaleX(0);
		playersEditSegmentedButton.setScaleY(0);

		if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == editPlayerToggleButton)
		{
			vBoxForEditPlayerName.setVisible(false);
			vBoxForEditPlayerName.setScaleX(0);
			vBoxForEditPlayerName.setScaleY(0);
		}
		else if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == switchPlayerToggleButton)
		{
			vBoxForSwitchPlayer.setVisible(false);
			vBoxForSwitchPlayer.setScaleX(0);
			vBoxForSwitchPlayer.setScaleY(0);
		}
		else
		{
			vBoxForAddPlayer.setVisible(false);
			vBoxForAddPlayer.setScaleX(0);
			vBoxForAddPlayer.setScaleY(0);
		}

		textFieldForEditPlayerName.setText("");
		textFieldForAddPlayer.setText("");

		if(scaleTransitionForWelcomeLabel != null) scaleTransitionForWelcomeLabel.stop();
		welcomeLabel.setScaleX(1);
		welcomeLabel.setScaleY(1);

		vBoxForMainButtons.setTranslateY(0);
		vBoxForMainButtons.setVisible(true);

		editUsersButton.setDisable(false);

		infoButton.setScaleX(1);
		infoButton.setScaleY(1);
		infoButton.setDisable(false);

		settingsButton.setScaleX(1);
		settingsButton.setScaleY(1);
		settingsButton.setDisable(false);
	}

	private void hideAndShowWelcomeLabelWithNewUsernameAnimation()
	{
		scaleTransitionForWelcomeLabel.setDuration(Duration.millis(200));
		scaleTransitionForWelcomeLabel.setFromX(welcomeLabel.getScaleX());
		scaleTransitionForWelcomeLabel.setFromY(welcomeLabel.getScaleY());
		scaleTransitionForWelcomeLabel.setToX(0);
		scaleTransitionForWelcomeLabel.setToY(0);
		scaleTransitionForWelcomeLabel.setAutoReverse(false);
		scaleTransitionForWelcomeLabel.setCycleCount(1);

		scaleTransitionForWelcomeLabel.setOnFinished(e ->
		{
			setTextAndFontForWelcomeLabel();

			scaleTransitionForWelcomeLabel.setDuration(Duration.millis(200));
			scaleTransitionForWelcomeLabel.setFromX(0);
			scaleTransitionForWelcomeLabel.setFromY(0);
			scaleTransitionForWelcomeLabel.setToX(0.95);
			scaleTransitionForWelcomeLabel.setToY(0.95);
			scaleTransitionForWelcomeLabel.setCycleCount(1);
			scaleTransitionForWelcomeLabel.setAutoReverse(false);

			if(animationsUsed == ANIMATIONS.ALL)
			{
				scaleTransitionForWelcomeLabel.setOnFinished(ev ->
				{
					scaleTransitionForWelcomeLabel.setOnFinished(eve -> {});
					startWelcomeTextAnimation();
				});
			}
			else scaleTransitionForWelcomeLabel.setOnFinished(ev -> {});
			scaleTransitionForWelcomeLabel.playFromStart();
		});
		scaleTransitionForWelcomeLabel.playFromStart();
	}

	private void loadCountriesLocalesObservableList()
	{
		countriesLocalesObservableList = FXCollections.observableArrayList();
		countriesLocalesSortList = FXCollections.observableArrayList();

		countriesComboBox.setItems(countriesLocalesObservableList);

		if(getCurrentLanguage() == LANGUAGE.ENGLISH)
		{
			for(short i = 0; i < countries.length; i++)
			{
				if(!countries[i].getLocaleCountryCode().equals("-"))
				{
					String s = countries[i].getNameInEnglish();
					switch (s)
					{
						case "Federated States of Micronesia":s = "Federated States\nof Micronesia";break;
						case "Commonwealth of the Northern Mariana Islands":s = "Commonwealth of the\nNorthern Mariana Islands";break;
					}

					countriesLocalesObservableList.add(s);
					countriesLocalesSortList.add(i);
				}
			}

			for (short i = 0; i < countriesLocalesObservableList.size() - 1; i++)
				for (short y = 0; y < countriesLocalesObservableList.size() - i - 1; y++)
					if (countriesLocalesObservableList.get(y).compareTo(countriesLocalesObservableList.get(y + 1)) > 0)
					{
						String temp = countriesLocalesObservableList.get(y);
						short s = countriesLocalesSortList.get(y);

						countriesLocalesObservableList.set(y, countriesLocalesObservableList.get(y + 1));
						countriesLocalesSortList.set(y, countriesLocalesSortList.get(y + 1));

						countriesLocalesObservableList.set(y + 1, temp);
						countriesLocalesSortList.set(y + 1, s);
					}
		}
		else
		{
			for(short i = 0; i < countries.length; i++)
			{
				if(!countries[i].getLocaleCountryCode().equals("-"))
				{
					String s = countries[i].getNameInGreek();
					switch (s)
					{
						case "Κοινοπολιτεία των Βορείων Μαριανών Νήσων":s = "Κοινοπολιτεία των Βορείων\nΜαριανών Νήσων";break;
						case "Ομόσπονδες Πολιτείες της Μικρονησίας":s = "Ομόσπονδες Πολιτείες\nτης Μικρονησίας";break;
					}

					countriesLocalesObservableList.add(s);
					countriesLocalesSortList.add(i);
				}
			}
		}

		if(getCurrentPlayer().getLocaleIndex() == -1)
		{
			getCurrentPlayer().setLocaleIndex(getLocaleIndexBasedOnLocale(getCurrentPlayer().getLocale()));
		}

		selectCountryInComboBoxBasedOnLocale();
	}

	private void loadUsersObservableList()
	{
		playersObservableList = FXCollections.observableArrayList();
		for(Player player : playersArrayList) playersObservableList.add(player.getOriginalName());
		playersComboBox.setItems(playersObservableList);
		playersComboBox.getSelectionModel().select(0);
	}

	private void selectCountryInComboBoxBasedOnLocale()
	{
		for(int i = 0; i < countriesLocalesSortList.size(); i++)
		{
			if(countriesLocalesSortList.get(i) == getCurrentPlayer().getLocaleIndex())
			{
				countriesComboBox.getSelectionModel().select(i);
				break;
			}
		}
	}

	private void setupPopUpAnimations()
	{
		timelineToShowConfirmationMessage = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				disableUI();
				addBlurEffect();

				confirmDeleteCheckBox.setSelected(false);
				confirmPopUpActionButton.setDisable(true);
				hBoxForPopUpConfirmationButtons.setDisable(true);

				vBoxForPopUpAskConfirmation.setVisible(true);

				if(animationsUsed != ANIMATIONS.NO)
				{
					scaleTransitionForPopUpAskConfirmation.setToX(1);
					scaleTransitionForPopUpAskConfirmation.setToY(1);

					getAudioStuff().playMaximizeSound();
					scaleTransitionForPopUpAskConfirmation.playFromStart();
				}
				else
				{
					vBoxForPopUpAskConfirmation.setScaleX(1);
					vBoxForPopUpAskConfirmation.setScaleY(1);
				}
			}),
			new KeyFrame(Duration.millis(300), e -> hBoxForPopUpConfirmationButtons.setDisable(false)));

		timelineToCancelConfirmationMessage = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				hBoxForPopUpConfirmationButtons.setDisable(true);

				if(animationsUsed != ANIMATIONS.NO)
				{
					scaleTransitionForPopUpAskConfirmation.setToX(0);
					scaleTransitionForPopUpAskConfirmation.setToY(0);

					getAudioStuff().playMinimizeSound();
					scaleTransitionForPopUpAskConfirmation.playFromStart();
				}
				else
				{
					vBoxForPopUpAskConfirmation.setScaleX(0);
					vBoxForPopUpAskConfirmation.setScaleY(0);

					removeBlurEffect();
					enableUI();

					vBoxForPopUpAskConfirmation.setVisible(false);

					hBoxForPopUpConfirmationButtons.setDisable(false);
				}
			}),
			new KeyFrame(Duration.millis(300), e ->
			{
				if(animationsUsed != ANIMATIONS.NO)
				{
					removeBlurEffect();
					enableUI();

					vBoxForPopUpAskConfirmation.setVisible(false);

					hBoxForPopUpConfirmationButtons.setDisable(false);
				}
			}));

		timelineToShowPopUpMessage = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				popUpAction = POP_UP_ACTION.NULL;

				hBoxForPopUpConfirmationButtons.setDisable(true);

				if(animationsUsed != ANIMATIONS.NO)
				{
					scaleTransitionForPopUpAskConfirmation.setToX(0);
					scaleTransitionForPopUpAskConfirmation.setToY(0);

					getAudioStuff().playMinimizeSound();
					scaleTransitionForPopUpAskConfirmation.playFromStart();
				}
				else
				{
					vBoxForPopUpAskConfirmation.setScaleX(0);
					vBoxForPopUpAskConfirmation.setScaleY(0);
				}
			}),
			new KeyFrame(Duration.millis(300), e ->
			{
				popUpMessageLabel.setVisible(true);

				hBoxForPopUpConfirmationButtons.setDisable(false);

				if(animationsUsed != ANIMATIONS.NO)
				{
					scaleTransitionForPopUpMessage.setToX(1);
					scaleTransitionForPopUpMessage.setToY(1);

					getAudioStuff().playMaximizeSound();
					scaleTransitionForPopUpMessage.playFromStart();
				}
				else
				{
					popUpMessageLabel.setScaleX(1);
					popUpMessageLabel.setScaleY(1);
				}
			}),
			new KeyFrame(Duration.seconds(2.8), e ->
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
					popUpMessageLabel.setScaleX(0);
					popUpMessageLabel.setScaleY(0);
					popUpMessageLabel.setVisible(false);
					removeBlurEffect();
				}
			}),
			new KeyFrame(Duration.seconds(3.1), e ->
			{
				if(animationsUsed != ANIMATIONS.NO)
				{
					popUpMessageLabel.setVisible(false);
					removeBlurEffect();
				}
			}));
	}

	private void setupLanguagesComboBox()
	{
		if(languagesObservableList == null) languagesObservableList = FXCollections.observableArrayList();
		if(emptyObservableList == null) emptyObservableList = FXCollections.emptyObservableList();
		languagesObservableList.addAll(languageResourceBundle.getString("englishLanguageLabel"), languageResourceBundle.getString("greekLanguageLabel"));
	}
}
