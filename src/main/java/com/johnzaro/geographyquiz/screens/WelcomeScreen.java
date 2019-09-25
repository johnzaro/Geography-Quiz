package com.johnzaro.geographyquiz.screens;

import com.johnzaro.geographyquiz.core.*;
import com.johnzaro.geographyquiz.core.OsCheck.OSType;
import com.johnzaro.geographyquiz.dataStructures.Player;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.controlsfx.control.SegmentedButton;

import java.util.Locale;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;

public class WelcomeScreen extends CoreScreen
{
	private ObservableList<String> usersObservableList, countriesLocalesObservableList;
	private ObservableList<Short> countriesLocalesSortList;
	
	private HBox hBoxFor4SettingsButtons, hBoxForEditUsernameButtons, hBoxForSwitchUserButtons, hBoxForAddUserButtons, hBoxForDeleteSavedNames,
			hBoxForLanguageSelection, hBoxForLanguagesButtons, hBoxForUnitOfLengthSelection, hBoxForUnitOfLengthRadioButtons,
			hBoxForSettingsAndInfoIcons, hBoxForCountrySelection, hBoxForPopUpConfirmationButtons;
	private VBox vBoxForSettings, vBoxForAnimationsUsedSettings, vBoxForMainButtons, vBoxForEditUsername, vBoxForSwitchUser, vBoxForAddUser, vBoxForPopUpAskConfirmation;
	private Label welcomeLabel, animationsUsedLabel, popUpAskConfirmationLabel, popUpMessageLabel, editUsernameLabel, switchUserLabel, addUserLabel,
			deleteSingleUserLabel, deleteAllUsersLabel, selectCountryLabel, selectLanguageLabel, selectUnitSystemLabel;
	private RadioButton metricSystemRadioButton, imperialSystemRadioButton;
	private TextField textFieldForEditUsername, textFieldForAddUser;
	private CustomImageView chalkboardBackgroundImage, gameNameWoodenImage, welcomeImage,
			leftGlobeStand, rightGlobeStand, leftGlobeImage, rightGlobeImage, woodPanelFor4IconsImage,
			woodPanelFor1IconImage, greekLanguageFlag, englishLanguageFlag, editUserIcon, switchUserIcon, addUserIcon, settingsIcon,
			editNameIcon, singlePlayerIcon, multiPlayerIcon, atlasIcon, scoresIcon, infoIcon;
	private CustomButton singlePlayerGameButton, multiPlayerGameButton, atlasButton, scoreBoardButton, greekLanguageButton, englishLanguageButton,
			cancelEditUsernameButton, cancelSwitchUserButton, cancelAddUserButton, confirmEditUsernameButton, confirmSwitchUserButton, confirmAddUserButton, 
			deleteAllDataButton, cancelPopUpActionButton, confirmPopUpActionButton;
	private CustomTooltip settingsTooltip, editNameTooltip, infoIconTooltip;
	private CustomCheckBox startInFullScreenCheckBox, confirmDeleteCheckBox;
	private ComboBox<String> usersComboBox, countriesComboBox;
	private Rectangle rectangleForInformationAboutGame;
	private ToggleButton noAnimationsToggleButton, limitedAnimationsToggleButton, allAnimationsToggleButton, editUsernameToggleButton, switchUserToggleButton, addUserToggleButton;
	private SegmentedButton animationsUsedSegmentedButton, usersEditSegmentedButton;
	private ToggleGroup toggleGroupForAnimationsUsedSegmentedButton, toggleGroupForUsersEditSegmentedButton, toggleGroupForUnitsOfLength;

	private ScaleTransition scaleTransitionForInfoButton, scaleTransitionForSoundIcon, scaleTransitionFor4Settings,
			scaleTransitionForWelcomeLabel, scaleTransitionForEditIcon, scaleTransitionForRectangleForInfoAboutGame, scaleTransitionForVBoxForSettings, scaleTransitionForSettingsIcon,
			scaleTransitionForPopUpMessage, scaleTransitionForPopUpAskConfirmation, scaleTransitionForVBoxForEditUsername, scaleTransitionForVBoxForSwitchUser, scaleTransitionForVBoxForAddUser,
			scaleTransitionForUsersEditSegmentedButton;
	private SequentialTransition sequentialTransitionForOnePlayerGameButton, sequentialTransitionForTwoPlayersGameButton, sequentialTransitionForAtlasButton,
			sequentialTransitionForScoreBoardButton, sequentialTransitionForInfoButton, sequentialTransitionForSettingsButton;
	private TranslateTransition translateTransitionForVBoxWithMainButtons, translateTransitionForVBoxForSound,
			translateTransitionForWoodPanelFor1IconImage, translateTransitionForWoodPanelFor4IconsImage, translateTransitionForGameNameImage,
			translateTransitionForLeftGlobeStand, translateTransitionForLeftGlobeImage, translateTransitionForRightGlobeStand, translateTransitionForRightGlobeImage;
	private ParallelTransition parallelTransitionForWelcomeImage, parallelTransitionForWelcomeLabel;
	private FadeTransition fadeInTransition;
	private Timeline timelineForLeftGlobe, timelineForRightGlobe, timelineToPopUpMainButtons,
			timelineToShowEditUsersVBox, timelineToHideEditUsersVBox,
			timelineToShowSoundOptions, timelineToHideSoundOptions,
			timelineToShowSettings, timelineToHideSettings, timelineToChangeUsersEditVBoxes,
			timelineToShowInformationAboutGame, timelineToHideInformationAboutGame,
			timelineToChangeLanguage, timelineToChangeLanguageByPlayer, timelineToShowAllStuff, timelineToHideAllStuff,
			timelineToShowPopUpMessage, timelineToShowConfirmationMessage, timelineToCancelConfirmationMessage;

	private BoxBlur boxBlurForNodes;
	private InnerShadow welcomeLabelInnerShadow;
	private DropShadow leftEarthShadow, rightEarthShadow, dropShadow;

	private byte leftGlobeStatus, rightGlobeStatus, leftGlobeCounter = 1, rightGlobeCounter = 1;
	private boolean previousTransitionOfLeftGlobeWasRight, previousTransitionOfRightGlobeWasRight;
	
	private enum POP_UP_ACTION { DELETE_SINGLE_USER, DELETE_ALL_USERS, DELETE_ALL_DATA, NULL}
	private POP_UP_ACTION popUpAction;

	protected void recalculateUI(double width, double height)
	{
		super.recalculateUI(width, height);
		
		hBoxFor4SettingsButtons.setPrefSize(0.1276 * width, 0.0787 * height);
		
		leftGlobeImage.setFitWidth(ratioProperties.getWelcome().getGlobeImageFitWidth() * width);
		rightGlobeImage.setFitWidth(ratioProperties.getWelcome().getGlobeImageFitWidth() * width);
		
		leftGlobeStand.setFitWidth(ratioProperties.getWelcome().getGlobeStandFitWidth() * width);
		leftGlobeStand.setLayoutX(ratioProperties.getWelcome().getLeftGlobeStandLayoutX() * width);
		leftGlobeStand.setLayoutY(ratioProperties.getWelcome().getGlobeStandLayoutY() * height);
		
		rightGlobeStand.setFitWidth(ratioProperties.getWelcome().getGlobeStandFitWidth() * width);
		rightGlobeStand.setLayoutX(width - leftGlobeStand.getLayoutX() - rightGlobeStand.getFitWidth());
		rightGlobeStand.setLayoutY(ratioProperties.getWelcome().getGlobeStandLayoutY() * height);
		
		gameNameWoodenImage.setLayoutY(ratioProperties.getWelcome().getGameNameImageLayoutY() * height);
		welcomeImage.setLayoutY(ratioProperties.getWelcome().getWelcomeImageLayoutY() * height);
		
		welcomeLabel.setLayoutY(ratioProperties.getWelcome().getWelcomeLabelLayoutY() * height);
		
		leftGlobeImage.setLayoutX(ratioProperties.getWelcome().getLeftGlobeImageLayoutX() * width);
		leftGlobeImage.setLayoutY(ratioProperties.getWelcome().getGlobeImageLayoutY() * height);
		rightGlobeImage.setLayoutX(ratioProperties.getWelcome().getRightGlobeImageLayoutX() * width);
		rightGlobeImage.setLayoutY(ratioProperties.getWelcome().getGlobeImageLayoutY() * height);
		
		woodPanelFor1IconImage.setLayoutY(ratioProperties.getWelcome().getWoodPanelFor1IconImageLayoutY() * height);
		soundIcon.setLayoutY(ratioProperties.getWelcome().getSoundIconLayoutY() * height);
		
		vBoxForSound.setLayoutY(ratioProperties.getWelcome().getvBoxForSoundLayoutY() * height);
		
		vBoxForSound.setPrefHeight(ratioProperties.getWelcome().getvBoxForSoundPrefHeight() * height);
		
		editNameIcon.setLayoutY(ratioProperties.getWelcome().getEditNameIconLayoutY() * height);
		
		hBoxForSettingsAndInfoIcons.setLayoutY(ratioProperties.getWelcome().gethBoxForSettingsAndInfoIconsLayoutY() * height);
		
		woodPanelFor4IconsImage.setLayoutY(ratioProperties.getWelcome().getWoodPanelFor4IconsImageLayoutY() * height);
		hBoxFor4SettingsButtons.setLayoutY(ratioProperties.getWelcome().gethBoxFor4SettingsButtonsLayoutY() * height);
		
		vBoxForMainButtons.setPrefWidth(ratioProperties.getWelcome().getvBoxForMainButtonsPrefWidth() * width);
		vBoxForMainButtons.setLayoutY(ratioProperties.getWelcome().getvBoxForMainButtonsLayoutY() * height);
		vBoxForMainButtons.setPrefHeight(ratioProperties.getWelcome().getvBoxForMainButtonsPrefHeight() * height);
		vBoxForMainButtons.setSpacing(ratioProperties.getWelcome().getvBoxForMainButtonsSpacing() * height);
		
		vBoxForEditUsername.setLayoutY(ratioProperties.getWelcome().getvBoxesForEditUsersLayoutY() * height);
		vBoxForSwitchUser.setLayoutY(ratioProperties.getWelcome().getvBoxesForEditUsersLayoutY() * height);
		vBoxForAddUser.setLayoutY(ratioProperties.getWelcome().getvBoxesForEditUsersLayoutY() * height);
		
		usersEditSegmentedButton.setLayoutY(ratioProperties.getWelcome().getUsersEditSegmentedButtonLayoutY() * height);
		
		vBoxForSettings.setLayoutY(ratioProperties.getWelcome().getvBoxForSettingsLayoutY() * height);
		
		rectangleForInformationAboutGame.setLayoutY(ratioProperties.getWelcome().getRectangleForInfoAboutGameLayoutY() * height);
		
		woodPanelFor1IconImage.setLayoutX(ratioProperties.getWelcome().getWoodPanelFor1IconImageLayoutX() * width);
		woodPanelFor4IconsImage.setLayoutX(ratioProperties.getWelcome().getWoodPanelFor4IconsImageLayoutX() * width);
		
		vBoxForSound.setLayoutX(ratioProperties.getWelcome().getvBoxForSoundLayoutX() * width);
		
		hBoxForSettingsAndInfoIcons.setLayoutX(0.7296 * width);

		double iconSize  = (0.0260) * width;
		
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
			metricSystemRadioButton.setId("small");
			imperialSystemRadioButton.setId("small");
		}
		else
		{
			metricSystemRadioButton.setId("big");
			imperialSystemRadioButton.setId("big");
		}
		
		vBoxForSound.setStyle(
				"-fx-background-color: #00000099; -fx-border-color: black;" +
				"-fx-background-radius:" + 0.0078 * width + ";" +
				"-fx-border-radius:" + 0.0078 * width + ";" +
				"-fx-border-width:" + 0.0026 * width + ";" +
		        "-fx-padding:" + 0.0052 * width + ";"
		                           );
		
		vBoxForSettings.setStyle(
				"-fx-background-color: #00000099; -fx-border-color: black;" +
				"-fx-background-radius:" + 0.0208 * width + ";" +
				"-fx-border-radius:" + 0.0208 * width + ";" +
				"-fx-border-width:" + 0.0050 * width + ";" +
				"-fx-padding:" + 0.0130 * width + ";"
		                     );
		
		vBoxForSettings.setPrefSize(0.5208 * width, 0.4815 * height);
		vBoxForSettings.setLayoutX(width / 2.0 - vBoxForSettings.getPrefWidth() / 2.0);
		vBoxForSettings.setSpacing(0.0278 * height);
		
		noAnimationsToggleButton.setPrefWidth(vBoxForSettings.getPrefWidth() / 3.0);
		limitedAnimationsToggleButton.setPrefWidth(vBoxForSettings.getPrefWidth() / 3.0);
		allAnimationsToggleButton.setPrefWidth(vBoxForSettings.getPrefWidth() / 3.0);
		
		vBoxForAnimationsUsedSettings.setSpacing(0.0093 * height);
		
		hBoxForCountrySelection.setPrefWidth(vBoxForSettings.getPrefWidth());
		hBoxForCountrySelection.setSpacing(0.0052 * width);
		
		selectCountryLabel.setPrefWidth(0.4 * hBoxForCountrySelection.getPrefWidth());
		countriesComboBox.setPrefWidth(0.6 * hBoxForCountrySelection.getPrefWidth());
		
		hBoxForLanguageSelection.setPrefWidth(vBoxForSettings.getPrefWidth());
		hBoxForLanguageSelection.setSpacing(0.0052 * width);
		
		selectLanguageLabel.setPrefWidth(0.4 * hBoxForLanguageSelection.getPrefWidth());
		
		hBoxForLanguagesButtons.setPrefWidth(0.6 * hBoxForLanguageSelection.getPrefWidth());
		hBoxForLanguagesButtons.setSpacing(0.0208 * width);
		
		hBoxForUnitOfLengthSelection.setPrefWidth(vBoxForSettings.getPrefWidth());
		hBoxForUnitOfLengthSelection.setSpacing(0.0052 * width);
		
		selectUnitSystemLabel.setPrefWidth(0.4 * hBoxForUnitOfLengthSelection.getPrefWidth());
		
		hBoxForUnitOfLengthRadioButtons.setPrefWidth(0.6 * hBoxForUnitOfLengthSelection.getPrefWidth());
		hBoxForUnitOfLengthRadioButtons.setSpacing(0.0208 * width);
		
		deleteAllDataButton.setPrefSize(0.2865 * width, 0.0741 * height);
		
		hBoxForSettingsAndInfoIcons.setSpacing(0.0100 * width);
		
		double maxWidth = (0.2604) * width;
		moveTooltip.setMaxWidth(maxWidth);
		exitTooltip.setMaxWidth(maxWidth);
		fullScreenTooltip.setMaxWidth(maxWidth);
		minimizeTooltip.setMaxWidth(maxWidth);
		soundOptionsToolTip.setMaxWidth(maxWidth);
		editNameTooltip.setMaxWidth(maxWidth);
		settingsTooltip.setMaxWidth(maxWidth);
		infoIconTooltip.setMaxWidth(maxWidth);
		singlePlayerGameButton.getTooltip().setMaxWidth(maxWidth);
		multiPlayerGameButton.getTooltip().setMaxWidth(maxWidth);
		atlasButton.getTooltip().setMaxWidth(maxWidth);
		scoreBoardButton.getTooltip().setMaxWidth(maxWidth);
		cancelEditUsernameButton.getTooltip().setMaxWidth(maxWidth);
		confirmEditUsernameButton.getTooltip().setMaxWidth(maxWidth);
		cancelSwitchUserButton.getTooltip().setMaxWidth(maxWidth);
		confirmSwitchUserButton.getTooltip().setMaxWidth(maxWidth);
		cancelAddUserButton.getTooltip().setMaxWidth(maxWidth);
		confirmAddUserButton.getTooltip().setMaxWidth(maxWidth);
		welcomeLabel.getTooltip().setMaxWidth(maxWidth);
		greekLanguageButton.getTooltip().setMaxWidth(maxWidth);
		englishLanguageButton.getTooltip().setMaxWidth(maxWidth);
		deleteAllDataButton.getTooltip().setMaxWidth(maxWidth);
		deleteSingleUserLabel.getTooltip().setMaxWidth(maxWidth);
		deleteAllUsersLabel.getTooltip().setMaxWidth(maxWidth);

		gameNameWoodenImage.setFitWidth(0.625 * width);
		gameNameWoodenImage.setLayoutX(width / 2.0 - gameNameWoodenImage.getFitWidth() / 2.0);

		welcomeImage.setFitWidth(0.5156 * width);
		welcomeImage.setLayoutX(width / 2.0 - welcomeImage.getFitWidth() / 2.0);

		woodPanelFor1IconImage.setFitWidth(0.0482 * width);
		woodPanelFor4IconsImage.setFitWidth(0.1354 * width);
		
		hBoxFor4SettingsButtons.setPrefSize(0.1354 * width, 0.0787 * height);

		vBoxForSound.setPrefWidth(0.1350 * width);

		greekLanguageFlag.setFitWidth(1.1 * iconSize);
		englishLanguageFlag.setFitWidth(1.1 * iconSize);

		welcomeLabel.setLayoutX(0.2448 * width);
		welcomeLabel.setPrefSize(0.5104 * width, 0.1248 * height);

		welcomeLabelInnerShadow.setOffsetX(0.0041 * width);
		welcomeLabelInnerShadow.setOffsetY(0.0041 * width);
		
		hBoxFor4SettingsButtons.setSpacing(0.0052 * width);
		
		leftEarthShadow.setRadius(0.0104 * width);
		leftEarthShadow.setOffsetX(-0.0026 * width);
		leftEarthShadow.setOffsetY(-0.0052 * width);
		
		rightEarthShadow.setRadius(0.0104 * width);
		rightEarthShadow.setOffsetX(-0.0052 * width);
		rightEarthShadow.setOffsetY(-0.0026 * width);
		
		dropShadow.setRadius(0.0104 * width);
		dropShadow.setOffsetX(-0.0052 * width);
		dropShadow.setOffsetY(-0.0052 * width);
		
		editNameIcon.setLayoutX(0.7656 * width);
		
		editNameIcon.setFitWidth(iconSize);
		
		settingsIcon.setFitWidth(1.4 * iconSize);
		infoIcon.setFitWidth(1.4 * iconSize);
		
		soundIcon.setFitWidth(1.2 * iconSize);
		minimizeIcon.setFitWidth(iconSize);
		moveIcon.setFitWidth(iconSize);
		fullScreenIcon.setFitWidth(iconSize);
		exitIcon.setFitWidth(iconSize);
		
		soundIcon.setLayoutX(woodPanelFor1IconImage.getLayoutX() + woodPanelFor1IconImage.getFitWidth() / 2.0 - soundIcon.getFitWidth() / 2.0);
		hBoxFor4SettingsButtons.setLayoutX(woodPanelFor4IconsImage.getLayoutX());

		vBoxForMainButtons.setLayoutX(width / 2.0 - vBoxForMainButtons.getPrefWidth() / 2.0);

		editUserIcon.setFitHeight(0.8 * iconSize);
		switchUserIcon.setFitHeight(0.8 * iconSize);
		addUserIcon.setFitHeight(0.8 * iconSize);
		
		singlePlayerIcon.setFitHeight(iconSize);
		multiPlayerIcon.setFitHeight(iconSize);
		atlasIcon.setFitHeight(iconSize);
		scoresIcon.setFitHeight(iconSize);

		singlePlayerGameButton.setPrefWidth(vBoxForMainButtons.getPrefWidth());
		singlePlayerGameButton.setPrefHeight((vBoxForMainButtons.getPrefHeight() - 3 * vBoxForMainButtons.getSpacing()) / 4.0);

		multiPlayerGameButton.setPrefWidth(vBoxForMainButtons.getPrefWidth());
		multiPlayerGameButton.setPrefHeight((vBoxForMainButtons.getPrefHeight() - 3 * vBoxForMainButtons.getSpacing()) / 4.0);

		atlasButton.setPrefWidth(vBoxForMainButtons.getPrefWidth());
		atlasButton.setPrefHeight((vBoxForMainButtons.getPrefHeight() - 3 * vBoxForMainButtons.getSpacing()) / 4.0);

		scoreBoardButton.setPrefWidth(vBoxForMainButtons.getPrefWidth());
		scoreBoardButton.setPrefHeight((vBoxForMainButtons.getPrefHeight() - 3 * vBoxForMainButtons.getSpacing()) / 4.0);
		
		String vBoxesForEditUsersStyle = "-fx-background-color: #00000099; -fx-border-color: black;" +
				"-fx-background-radius:" + 0.0208 * width + ";" +
				"-fx-border-radius:" + 0.0208 * width + ";" +
				"-fx-border-width:" + 0.0050 * width + ";" +
				"-fx-padding:" + 0.0156 * width;
		
		vBoxForEditUsername.setPrefWidth(0.4688 * width);
		vBoxForEditUsername.setLayoutX(width / 2.0 - vBoxForEditUsername.getPrefWidth() / 2.0);
		vBoxForEditUsername.setSpacing(0.0278 * height);
		vBoxForEditUsername.setStyle(vBoxesForEditUsersStyle);
		
		vBoxForSwitchUser.setPrefWidth(0.4688 * width);
		vBoxForSwitchUser.setLayoutX(width / 2.0 - vBoxForSwitchUser.getPrefWidth() / 2.0);
		vBoxForSwitchUser.setSpacing(0.0278 * height);
		vBoxForSwitchUser.setStyle(vBoxesForEditUsersStyle);
		
		vBoxForAddUser.setPrefWidth(0.4688 * width);
		vBoxForAddUser.setLayoutX(width / 2.0 - vBoxForAddUser.getPrefWidth() / 2.0);
		vBoxForAddUser.setSpacing(0.0278 * height);
		vBoxForAddUser.setStyle(vBoxesForEditUsersStyle);
		
		usersEditSegmentedButton.setLayoutX(width / 2.0 - vBoxForEditUsername.getPrefWidth() / 2.0);
		
		textFieldForEditUsername.setPrefWidth(vBoxForEditUsername.getPrefWidth());
		
		textFieldForAddUser.setPrefWidth(vBoxForAddUser.getPrefWidth());
		
		usersComboBox.setPrefWidth(vBoxForEditUsername.getPrefWidth());
		
		hBoxForDeleteSavedNames.setPrefWidth(vBoxForEditUsername.getPrefWidth());
		hBoxForDeleteSavedNames.setSpacing(0.0130 * width);
		
		deleteSingleUserLabel.setPrefWidth(0.5 * hBoxForDeleteSavedNames.getPrefWidth());
		deleteAllUsersLabel.setPrefWidth(0.5 * hBoxForDeleteSavedNames.getPrefWidth());
		
		hBoxForEditUsernameButtons.setPrefWidth(vBoxForEditUsername.getPrefWidth());
		hBoxForEditUsernameButtons.setSpacing(0.0130 * width);
		
		hBoxForSwitchUserButtons.setPrefWidth(vBoxForSwitchUser.getPrefWidth());
		hBoxForSwitchUserButtons.setSpacing(0.0130 * width);
		
		hBoxForAddUserButtons.setPrefWidth(vBoxForAddUser.getPrefWidth());
		hBoxForAddUserButtons.setSpacing(0.0130 * width);

		cancelEditUsernameButton.setPrefWidth(hBoxForEditUsernameButtons.getPrefWidth() / 2.0);
		confirmEditUsernameButton.setPrefWidth(hBoxForEditUsernameButtons.getPrefWidth() / 2.0);
		
		cancelSwitchUserButton.setPrefWidth(hBoxForSwitchUserButtons.getPrefWidth() / 2.0);
		confirmSwitchUserButton.setPrefWidth(hBoxForSwitchUserButtons.getPrefWidth() / 2.0);
		
		cancelAddUserButton.setPrefWidth(hBoxForAddUserButtons.getPrefWidth() / 2.0);
		confirmAddUserButton.setPrefWidth(hBoxForAddUserButtons.getPrefWidth() / 2.0);

		rectangleForInformationAboutGame.setWidth(0.4427 * width);
		rectangleForInformationAboutGame.setHeight(0.6018 * height);
		rectangleForInformationAboutGame.setArcWidth(0.0208 * width);
		rectangleForInformationAboutGame.setArcHeight(0.0208 * width);
		rectangleForInformationAboutGame.setStrokeWidth(0.0042 * width);
		rectangleForInformationAboutGame.setLayoutX(width / 2.0 - rectangleForInformationAboutGame.getWidth() / 2.0);
		
		Font fontForButtons  = Font.font("Comic Sans MS", FontWeight.BOLD, 0.0234 * width);
		Font fontForLabels = Font.font("Comic Sans MS", 0.0094 * width);
		Font fontForNewName = Font.font("Comic Sans MS", 0.0182 * width);
		
		popUpMessageLabel.setPrefSize(0.2864 * width, 0.2315 * height);
		popUpMessageLabel.setLayoutX(width / 2.0 - popUpMessageLabel.getPrefWidth() / 2.0);
		popUpMessageLabel.setLayoutY(height / 2.0 - popUpMessageLabel.getPrefHeight() / 2.0);
		popUpMessageLabel.setStyle(
				String.format("-fx-background-color: #000000ce; -fx-border-color: black;-fx-background-radius:%spx;-fx-border-radius:%spx;-fx-border-width:%spx;-fx-padding:%spx;",
				0.0208 * width, 0.0208 * width, 0.0050 * width, 0.0052 * width));
		
		vBoxForPopUpAskConfirmation.setPrefSize(0.5200 * width, 0.4400 * height);
		vBoxForPopUpAskConfirmation.setLayoutX(width / 2.0 - vBoxForPopUpAskConfirmation.getPrefWidth() / 2.0);
		vBoxForPopUpAskConfirmation.setLayoutY(height / 2.0 - vBoxForPopUpAskConfirmation.getPrefHeight() / 2.0);
		vBoxForPopUpAskConfirmation.setStyle(
				String.format("-fx-background-color: #000000ce; -fx-border-color: black;-fx-background-radius:%spx;-fx-border-radius:%spx;-fx-border-width:%spx;-fx-padding:0 %spx %spx %spx;",
				0.0208 * width, 0.0208 * width, 0.0050 * width, 0.0156 * width, 0.0156 * width, 0.0156 * width));
		
		confirmDeleteCheckBox.setStyle("-fx-padding:" + 0.0463 * height + "px 0 " + 0.0463 * height + "px 0;");
		
		hBoxForPopUpConfirmationButtons.setPrefWidth(vBoxForPopUpAskConfirmation.getPrefWidth());
		hBoxForPopUpConfirmationButtons.setSpacing(0.0130 * width);
		
		cancelPopUpActionButton.setPrefWidth(hBoxForPopUpConfirmationButtons.getPrefWidth() / 2.0);
		confirmPopUpActionButton.setPrefWidth(hBoxForPopUpConfirmationButtons.getPrefWidth() / 2.0);
		
		cancelPopUpActionButton.setFont(fontForButtons);
		confirmPopUpActionButton.setFont(fontForButtons);
		
		masterVolumeLabel.setFont(fontForLabels);
		musicVolumeLabel.setFont(fontForLabels);
		soundEffectsVolumeLabel.setFont(fontForLabels);
		
		startInFullScreenCheckBox.setFont(fontForNewName);
		animationsUsedLabel.setFont(fontForNewName);
		
		editUsernameLabel.setFont(fontForButtons);
		switchUserLabel.setFont(fontForButtons);
		addUserLabel.setFont(fontForButtons);
		
		noAnimationsToggleButton.setFont(Font.font("Comic Sans MS", 0.013 * width));
		limitedAnimationsToggleButton.setFont(Font.font("Comic Sans MS", 0.013 * width));
		allAnimationsToggleButton.setFont(Font.font("Comic Sans MS", 0.013 * width));
		
		editUsernameToggleButton.setPrefWidth(vBoxForEditUsername.getPrefWidth() / 3.0);
		switchUserToggleButton.setPrefWidth(vBoxForEditUsername.getPrefWidth() / 3.0);
		addUserToggleButton.setPrefWidth(vBoxForEditUsername.getPrefWidth() / 3.0);
		
		editUsernameToggleButton.setFont(Font.font("Comic Sans MS", 0.013 * width));
		switchUserToggleButton.setFont(Font.font("Comic Sans MS", 0.013 * width));
		addUserToggleButton.setFont(Font.font("Comic Sans MS", 0.013 * width));
		
		selectCountryLabel.setFont(fontForNewName);
		countriesComboBox.setStyle("-fx-font:" + 0.0130 * width + "px \"Comic Sans MS\";");
		
		selectLanguageLabel.setFont(fontForNewName);
		
		greekLanguageButton.setFont(Font.font("Comic Sans MS", 0.0115 * width));
		englishLanguageButton.setFont(Font.font("Comic Sans MS", 0.0115 * width));
		
		selectUnitSystemLabel.setFont(fontForNewName);
		
		metricSystemRadioButton.setFont(fontForNewName);
		imperialSystemRadioButton.setFont(fontForNewName);
		
		deleteAllDataButton.setFont(fontForNewName);
		
		singlePlayerGameButton.setFont(fontForButtons);
		multiPlayerGameButton.setFont(fontForButtons);
		atlasButton.setFont(fontForButtons);
		scoreBoardButton.setFont(fontForButtons);
		cancelEditUsernameButton.setFont(fontForButtons);
		confirmEditUsernameButton.setFont(fontForButtons);
		cancelSwitchUserButton.setFont(fontForButtons);
		confirmSwitchUserButton.setFont(fontForButtons);
		cancelAddUserButton.setFont(fontForButtons);
		confirmAddUserButton.setFont(fontForButtons);
		
		textFieldForEditUsername.setFont(Font.font("Comic Sans MS", 0.0182 * width));
		textFieldForAddUser.setFont(Font.font("Comic Sans MS", 0.0182 * width));
		usersComboBox.setStyle("-fx-font:" + 0.0156 * width + "px \"Comic Sans MS\";");
		deleteSingleUserLabel.setFont(fontForNewName);
		deleteAllUsersLabel.setFont(fontForNewName);
		
		popUpMessageLabel.setFont(Font.font("Comic Sans MS", 0.02 * width));
		popUpAskConfirmationLabel.setFont(Font.font("Comic Sans MS", 0.02 * width));
		confirmDeleteCheckBox.setFont(Font.font("Comic Sans MS", 0.02 * width));
		
		welcomeLabel.getTooltip().setFont(fontForTooltips);
		singlePlayerGameButton.getTooltip().setFont(fontForTooltips);
		multiPlayerGameButton.getTooltip().setFont(fontForTooltips);
		atlasButton.getTooltip().setFont(fontForTooltips);
		scoreBoardButton.getTooltip().setFont(fontForTooltips);
		infoIconTooltip.setFont(fontForTooltips);
		minimizeTooltip.setFont(fontForTooltips);
		moveTooltip.setFont(fontForTooltips);
		fullScreenTooltip.setFont(fontForTooltips);
		exitTooltip.setFont(fontForTooltips);
		soundOptionsToolTip.setFont(fontForTooltips);
		settingsTooltip.setFont(fontForTooltips);
		editNameTooltip.setFont(fontForTooltips);
		greekLanguageButton.getTooltip().setFont(fontForTooltips);
		englishLanguageButton.getTooltip().setFont(fontForTooltips);
		textFieldForEditUsername.getTooltip().setFont(fontForTooltips);
		textFieldForAddUser.getTooltip().setFont(fontForTooltips);
		cancelEditUsernameButton.getTooltip().setFont(fontForTooltips);
		confirmEditUsernameButton.getTooltip().setFont(fontForTooltips);
		cancelSwitchUserButton.getTooltip().setFont(fontForTooltips);
		confirmSwitchUserButton.getTooltip().setFont(fontForTooltips);
		cancelAddUserButton.getTooltip().setFont(fontForTooltips);
		confirmAddUserButton.getTooltip().setFont(fontForTooltips);
		deleteAllDataButton.getTooltip().setFont(fontForTooltips);
		deleteSingleUserLabel.getTooltip().setFont(fontForTooltips);
		deleteAllUsersLabel.getTooltip().setFont(fontForTooltips);
		
		setTextAndFontForWelcomeLabel();
	}
	
	protected void recalculateBackground(double width, double height)
	{
		super.recalculateBackground(width, height);
		
		chalkboardBackgroundImage.setFitWidth(width);
		chalkboardBackgroundImage.setFitHeight(height);
	}
	
	protected void setScreenRatioDependentImages()
	{
		woodenFrameImage.setImage(FRAME_IMAGE);
		chalkboardBackgroundImage.setImage(CHALKBOARD_BACKGROUND_IMAGE);
	}
	
	public WelcomeScreen()
	{
		//BACKGROUND IMAGES
		chalkboardBackgroundImage = new CustomImageView(false, false, false, true, CacheHint.SPEED);
		chalkboardBackgroundImage.setLayoutX(0);
		chalkboardBackgroundImage.setLayoutX(0);

		//GAME NAME IMAGE
		gameNameWoodenImage = new CustomImageView(true, true, false, true, CacheHint.SPEED);

		//PLAYER NAME IMAGE, WELCOME LABEL AND EDIT NAME ICON
		welcomeImage = new CustomImageView(true, true, false, true, CacheHint.SPEED);
		
		welcomeLabel = new Label();
		welcomeLabel.setAlignment(Pos.CENTER);
		welcomeLabel.setTextAlignment(TextAlignment.CENTER);
		welcomeLabel.setTextFill(Color.valueOf("602000"));
		welcomeLabelInnerShadow = new InnerShadow();
		welcomeLabel.setEffect(welcomeLabelInnerShadow);
		welcomeLabel.setTooltip(new CustomTooltip());
		welcomeLabel.setCache(true);
		welcomeLabel.setCacheHint(CacheHint.SCALE);
		
		editNameIcon = new CustomImageView(false, true, true, true, CacheHint.SCALE);
		editNameIcon.setCursor(Cursor.HAND);
		
		editNameTooltip = new CustomTooltip();
		Tooltip.install(editNameIcon, editNameTooltip);
		
		//WOOD IMAGE AND BOX FOR 2 ICON SETTINGS
		woodPanelFor1IconImage = new CustomImageView(true, true, false, true, CacheHint.SPEED);
		
		//WOOD IMAGE AND BOX FOR 4 ICON SETTINGS
		woodPanelFor4IconsImage = new CustomImageView(true, true, false, true, CacheHint.SPEED);
		
		hBoxFor4SettingsButtons = new HBox();
		hBoxFor4SettingsButtons.setAlignment(Pos.CENTER);
		hBoxFor4SettingsButtons.setFillHeight(true);
		hBoxFor4SettingsButtons.setCache(true);
		hBoxFor4SettingsButtons.setCacheHint(CacheHint.SCALE);
		
		//POSITION OF SETTINGS BOXES BASED ON O.S.
		if (OS == OsCheck.OSType.Windows) hBoxFor4SettingsButtons.getChildren().addAll(minimizeIcon, moveIcon, fullScreenIcon, exitIcon);
		else hBoxFor4SettingsButtons.getChildren().addAll(exitIcon, fullScreenIcon, moveIcon, minimizeIcon);
		
		//LEFT STAND GLOBE AND SHADOW
		leftGlobeStand = new CustomImageView(true, true, false, true, CacheHint.SPEED);
		
		leftGlobeImage = new CustomImageView(false, true, false, false, null);
		leftGlobeImage.setRotate(-20);
		
		leftEarthShadow = new DropShadow();
		leftGlobeImage.setEffect(leftEarthShadow);
		
		//RIGHT STAND GLOBE AND SHADOW
		rightGlobeStand = new CustomImageView(true, true, false, true, CacheHint.SPEED);
		rightGlobeStand.setSmooth(true);
		rightGlobeStand.setPreserveRatio(true);
		rightGlobeStand.setCache(true);
		rightGlobeStand.setCacheHint(CacheHint.SPEED);
		
		rightGlobeImage = new CustomImageView(false, true, false, false, null);
		rightGlobeImage.setRotate(20);
		
		rightEarthShadow = new DropShadow();
		rightGlobeImage.setEffect(rightEarthShadow);
		
		//START IN FULL SCREEN CHECKBOX
		startInFullScreenCheckBox = new CustomCheckBox();
		
		//STUFF ABOUT ANIMATIONS USED
		animationsUsedLabel = new Label();
		animationsUsedLabel.setTextFill(Color.WHITE);
		animationsUsedLabel.setUnderline(true);
		animationsUsedLabel.setTextAlignment(TextAlignment.CENTER);
		
		noAnimationsToggleButton = new ToggleButton();
		noAnimationsToggleButton.setCursor(Cursor.HAND);
		noAnimationsToggleButton.getStyleClass().addAll("segmentedToggleButton", "first");
		
		limitedAnimationsToggleButton = new ToggleButton();
		limitedAnimationsToggleButton.setCursor(Cursor.HAND);
		limitedAnimationsToggleButton.getStyleClass().add("segmentedToggleButton");
		
		allAnimationsToggleButton = new ToggleButton();
		allAnimationsToggleButton.setCursor(Cursor.HAND);
		allAnimationsToggleButton.getStyleClass().addAll("segmentedToggleButton", "last");
		
		toggleGroupForAnimationsUsedSegmentedButton = new ToggleGroup();
		toggleGroupForAnimationsUsedSegmentedButton.getToggles().addAll(noAnimationsToggleButton, limitedAnimationsToggleButton, allAnimationsToggleButton);
		
		animationsUsedSegmentedButton = new SegmentedButton(noAnimationsToggleButton, limitedAnimationsToggleButton, allAnimationsToggleButton);
		animationsUsedSegmentedButton.setToggleGroup(toggleGroupForAnimationsUsedSegmentedButton);
		
		vBoxForAnimationsUsedSettings = new VBox();
		vBoxForAnimationsUsedSettings.setAlignment(Pos.CENTER);
		vBoxForAnimationsUsedSettings.getChildren().addAll(animationsUsedLabel, animationsUsedSegmentedButton);

		//COUNTRY LABEL & COMBOBOX
		selectCountryLabel = new Label();
		selectCountryLabel.setTextFill(Color.WHITE);
		selectCountryLabel.setUnderline(true);
		selectCountryLabel.setAlignment(Pos.CENTER);
		
		countriesComboBox = new ComboBox<>();
		countriesComboBox.setCursor(Cursor.HAND);
		
		hBoxForCountrySelection = new HBox();
		hBoxForCountrySelection.setFillHeight(true);
		hBoxForCountrySelection.setAlignment(Pos.CENTER);
		hBoxForCountrySelection.getChildren().addAll(selectCountryLabel, countriesComboBox);
		
		//LANGUAGE LABELS AND BOX
		selectLanguageLabel = new Label();
		selectLanguageLabel.setTextFill(Color.WHITE);
		selectLanguageLabel.setUnderline(true);
		selectLanguageLabel.setAlignment(Pos.CENTER);
		
		greekLanguageFlag = new CustomImageView(true, true, false, true, CacheHint.SCALE);
		
		greekLanguageButton = new CustomButton();
		greekLanguageButton.setGraphic(greekLanguageFlag);
		greekLanguageButton.setStyle("-fx-background-color: transparent");
		greekLanguageButton.setContentDisplay(ContentDisplay.TOP);
		greekLanguageButton.setTextFill(Color.WHITE);
		greekLanguageButton.setTooltip(new CustomTooltip());
		
		englishLanguageFlag = new CustomImageView(true, true, false, true, CacheHint.SCALE);
		
		englishLanguageButton = new CustomButton();
		englishLanguageButton.setGraphic(englishLanguageFlag);
		englishLanguageButton.setStyle("-fx-background-color: transparent");
		englishLanguageButton.setContentDisplay(ContentDisplay.TOP);
		englishLanguageButton.setTextFill(Color.WHITE);
		englishLanguageButton.setTooltip(new CustomTooltip());
		
		hBoxForLanguagesButtons = new HBox();
		hBoxForLanguagesButtons.setAlignment(Pos.CENTER);
		hBoxForLanguagesButtons.setFillHeight(true);
		hBoxForLanguagesButtons.getChildren().addAll(englishLanguageButton, greekLanguageButton);

		hBoxForLanguageSelection = new HBox();
		hBoxForLanguageSelection.setAlignment(Pos.CENTER);
		hBoxForLanguageSelection.setFillHeight(true);
		hBoxForLanguageSelection.getChildren().addAll(selectLanguageLabel, hBoxForLanguagesButtons);
		
		//UNITS OF LENGTH
		selectUnitSystemLabel = new Label();
		selectUnitSystemLabel.setTextFill(Color.WHITE);
		selectUnitSystemLabel.setUnderline(true);
		selectUnitSystemLabel.setAlignment(Pos.CENTER);
		
		metricSystemRadioButton = new RadioButton();
		metricSystemRadioButton.setTextFill(Color.WHITE);
		metricSystemRadioButton.setCursor(Cursor.HAND);
		
		imperialSystemRadioButton = new RadioButton();
		imperialSystemRadioButton.setTextFill(Color.WHITE);
		imperialSystemRadioButton.setCursor(Cursor.HAND);
		
		toggleGroupForUnitsOfLength = new ToggleGroup();
		toggleGroupForUnitsOfLength.getToggles().addAll(metricSystemRadioButton, imperialSystemRadioButton);
		
		hBoxForUnitOfLengthRadioButtons = new HBox();
		hBoxForUnitOfLengthRadioButtons.setAlignment(Pos.CENTER);
		hBoxForUnitOfLengthRadioButtons.getChildren().addAll(metricSystemRadioButton, imperialSystemRadioButton);
		
		hBoxForUnitOfLengthSelection = new HBox();
		hBoxForUnitOfLengthSelection.getChildren().addAll(selectUnitSystemLabel, hBoxForUnitOfLengthRadioButtons);
		
		//DELETE ALL DATA LABEL
		deleteAllDataButton = new CustomButton();
		deleteAllDataButton.setTooltip(new CustomTooltip());
		
		//BOX FOR ALL SETTINGS
		vBoxForSettings = new VBox();
		vBoxForSettings.setAlignment(Pos.CENTER);
		vBoxForSettings.setFillWidth(true);
		vBoxForSettings.getChildren().addAll(startInFullScreenCheckBox, vBoxForAnimationsUsedSettings, hBoxForCountrySelection,
				hBoxForLanguageSelection, hBoxForUnitOfLengthSelection, deleteAllDataButton);
		vBoxForSettings.setCache(true);
		vBoxForSettings.setCacheHint(CacheHint.SPEED);
		
		//SETTINGS ICON
		settingsIcon = new CustomImageView(true, true, false, true, CacheHint.SCALE);
		settingsIcon.setCursor(Cursor.HAND);
		
		settingsTooltip = new CustomTooltip();
		Tooltip.install(settingsIcon, settingsTooltip);
		
		//INFO ICON AND RECTANGLE FOR INFO
		infoIcon = new CustomImageView(true, true, false, true, CacheHint.SCALE);
		infoIcon.setCursor(Cursor.HAND);
		
		infoIconTooltip = new CustomTooltip();
		Tooltip.install(infoIcon, infoIconTooltip);
		
		hBoxForSettingsAndInfoIcons = new HBox();
		hBoxForSettingsAndInfoIcons.setAlignment(Pos.CENTER);
		hBoxForSettingsAndInfoIcons.setFillHeight(true);
		hBoxForSettingsAndInfoIcons.getChildren().addAll(infoIcon, settingsIcon);
		
		rectangleForInformationAboutGame = new Rectangle();
		rectangleForInformationAboutGame.setSmooth(true);
		rectangleForInformationAboutGame.setStroke(Color.BLACK);
		rectangleForInformationAboutGame.setFill(Color.valueOf("00000099"));
		rectangleForInformationAboutGame.setCache(true);
		rectangleForInformationAboutGame.setCacheHint(CacheHint.SCALE);
		
		//BASIC BUTTONS BOX AND STUFF
		singlePlayerIcon = new CustomImageView(true, true, false, true, CacheHint.SCALE);
		multiPlayerIcon = new CustomImageView(true, true, false, true, CacheHint.SCALE);
		atlasIcon = new CustomImageView(true, true, false, true, CacheHint.SCALE);
		scoresIcon = new CustomImageView(true, true, false, true, CacheHint.SCALE);
		
		singlePlayerGameButton = new CustomButton();
		singlePlayerGameButton.setGraphic(singlePlayerIcon);
		singlePlayerGameButton.setGraphicTextGap(10);
		singlePlayerGameButton.setTooltip(new CustomTooltip());
		singlePlayerGameButton.setCache(true);
		singlePlayerGameButton.setCacheHint(CacheHint.SCALE);
		
		multiPlayerGameButton = new CustomButton();
		multiPlayerGameButton.setGraphic(multiPlayerIcon);
		multiPlayerGameButton.setGraphicTextGap(10);
		multiPlayerGameButton.setTooltip(new CustomTooltip());
		multiPlayerGameButton.setCache(true);
		multiPlayerGameButton.setCacheHint(CacheHint.SCALE);
		
		atlasButton = new CustomButton();
		atlasButton.setGraphic(atlasIcon);
		atlasButton.setGraphicTextGap(10);
		atlasButton.setTooltip(new CustomTooltip());
		atlasButton.setCache(true);
		atlasButton.setCacheHint(CacheHint.SCALE);
		
		scoreBoardButton = new CustomButton();
		scoreBoardButton.setGraphic(scoresIcon);
		scoreBoardButton.setGraphicTextGap(10);
		scoreBoardButton.setTooltip(new CustomTooltip());
		scoreBoardButton.setCache(true);
		scoreBoardButton.setCacheHint(CacheHint.SCALE);
		
		vBoxForMainButtons = new VBox();
		vBoxForMainButtons.setAlignment(Pos.CENTER);
		vBoxForMainButtons.getChildren().addAll(singlePlayerGameButton, multiPlayerGameButton, atlasButton, scoreBoardButton);

		//NEW NAME BOX AND STUFF
		editUserIcon = new CustomImageView(true, true, false, true, CacheHint.SCALE);
		switchUserIcon = new CustomImageView(true, true, false, true, CacheHint.SCALE);
		addUserIcon = new CustomImageView(true, true, false, true, CacheHint.SCALE);
		
		editUsernameLabel = new Label();
		editUsernameLabel.setAlignment(Pos.CENTER);
		editUsernameLabel.setTextFill(Color.WHITE);
		
		switchUserLabel = new Label();
		switchUserLabel.setAlignment(Pos.CENTER);
		switchUserLabel.setTextFill(Color.WHITE);
		
		addUserLabel = new Label();
		addUserLabel.setAlignment(Pos.CENTER);
		addUserLabel.setTextFill(Color.WHITE);
		
		editUsernameToggleButton = new ToggleButton();
		editUsernameToggleButton.setCursor(Cursor.HAND);
		editUsernameToggleButton.setGraphic(editUserIcon);
		editUsernameToggleButton.setGraphicTextGap(10);
		editUsernameToggleButton.setCache(true);
		editUsernameToggleButton.setCacheHint(CacheHint.SCALE);
		editUsernameToggleButton.getStyleClass().addAll("segmentedToggleButton", "first");
		
		switchUserToggleButton = new ToggleButton();
		switchUserToggleButton.setCursor(Cursor.HAND);
		switchUserToggleButton.setGraphic(switchUserIcon);
		switchUserToggleButton.setGraphicTextGap(10);
		switchUserToggleButton.setCache(true);
		switchUserToggleButton.setCacheHint(CacheHint.SCALE);
		switchUserToggleButton.getStyleClass().add("segmentedToggleButton");
		
		addUserToggleButton = new ToggleButton();
		addUserToggleButton.setCursor(Cursor.HAND);
		addUserToggleButton.setGraphic(addUserIcon);
		addUserToggleButton.setGraphicTextGap(10);
		addUserToggleButton.setCache(true);
		addUserToggleButton.setCacheHint(CacheHint.SCALE);
		addUserToggleButton.getStyleClass().addAll("segmentedToggleButton", "last");
		
		toggleGroupForUsersEditSegmentedButton = new ToggleGroup();
		toggleGroupForUsersEditSegmentedButton.getToggles().addAll(editUsernameToggleButton, switchUserToggleButton, addUserToggleButton);
		
		usersEditSegmentedButton = new SegmentedButton(editUsernameToggleButton, switchUserToggleButton, addUserToggleButton);
		usersEditSegmentedButton.setToggleGroup(toggleGroupForUsersEditSegmentedButton);

		textFieldForEditUsername = new TextField();
		textFieldForEditUsername.setCursor(Cursor.TEXT);
		textFieldForEditUsername.setTooltip(new CustomTooltip());
		
		textFieldForAddUser = new TextField();
		textFieldForAddUser.setCursor(Cursor.TEXT);
		textFieldForAddUser.setTooltip(new CustomTooltip());
		
		usersComboBox = new ComboBox<>();
		usersComboBox.setCursor(Cursor.HAND);
		
		deleteSingleUserLabel = new Label();
		deleteSingleUserLabel.setAlignment(Pos.CENTER);
		deleteSingleUserLabel.setTextFill(Color.WHITE);
		deleteSingleUserLabel.setUnderline(true);
		deleteSingleUserLabel.setCursor(Cursor.HAND);
		deleteSingleUserLabel.setTooltip(new CustomTooltip());
		
		deleteAllUsersLabel = new Label();
		deleteAllUsersLabel.setAlignment(Pos.CENTER);
		deleteAllUsersLabel.setTextFill(Color.WHITE);
		deleteAllUsersLabel.setUnderline(true);
		deleteAllUsersLabel.setCursor(Cursor.HAND);
		deleteAllUsersLabel.setTooltip(new CustomTooltip());
		
		hBoxForDeleteSavedNames = new HBox();
		hBoxForDeleteSavedNames.setFillHeight(true);
		hBoxForDeleteSavedNames.setAlignment(Pos.CENTER);
		hBoxForDeleteSavedNames.getChildren().addAll(deleteAllUsersLabel, deleteSingleUserLabel);
				
		cancelEditUsernameButton = new CustomButton();
		cancelEditUsernameButton.setTooltip(new CustomTooltip());

		confirmEditUsernameButton = new CustomButton();
		confirmEditUsernameButton.setDisable(true);
		confirmEditUsernameButton.setTooltip(new CustomTooltip());
		
		hBoxForEditUsernameButtons = new HBox();
		hBoxForEditUsernameButtons.setAlignment(Pos.CENTER);
		hBoxForEditUsernameButtons.setFillHeight(true);
		hBoxForEditUsernameButtons.getChildren().addAll(cancelEditUsernameButton, confirmEditUsernameButton);
		
		cancelSwitchUserButton = new CustomButton();
		cancelSwitchUserButton.setTooltip(new CustomTooltip());
		
		confirmSwitchUserButton = new CustomButton();
		confirmSwitchUserButton.setDisable(true);
		confirmSwitchUserButton.setTooltip(new CustomTooltip());
		
		hBoxForSwitchUserButtons = new HBox();
		hBoxForSwitchUserButtons.setFillHeight(true);
		hBoxForSwitchUserButtons.getChildren().addAll(cancelSwitchUserButton, confirmSwitchUserButton);
		
		cancelAddUserButton = new CustomButton();
		cancelAddUserButton.setTooltip(new CustomTooltip());
		
		confirmAddUserButton = new CustomButton();
		confirmAddUserButton.setDisable(true);
		confirmAddUserButton.setTooltip(new CustomTooltip());
		
		hBoxForAddUserButtons = new HBox();
		hBoxForAddUserButtons.setAlignment(Pos.CENTER);
		hBoxForAddUserButtons.setFillHeight(true);
		hBoxForAddUserButtons.getChildren().addAll(cancelAddUserButton, confirmAddUserButton);
		
		vBoxForEditUsername = new VBox();
		vBoxForEditUsername.setAlignment(Pos.CENTER);
		vBoxForEditUsername.getChildren().addAll(editUsernameLabel, textFieldForEditUsername, hBoxForEditUsernameButtons);
		vBoxForEditUsername.setCache(true);
		vBoxForEditUsername.setCacheHint(CacheHint.SCALE);
		
		vBoxForSwitchUser = new VBox();
		vBoxForSwitchUser.setAlignment(Pos.CENTER);
		vBoxForSwitchUser.getChildren().addAll(switchUserLabel, usersComboBox, hBoxForDeleteSavedNames, hBoxForSwitchUserButtons);
		vBoxForSwitchUser.setCache(true);
		vBoxForSwitchUser.setCacheHint(CacheHint.SCALE);
		
		vBoxForAddUser = new VBox();
		vBoxForAddUser.setAlignment(Pos.CENTER);
		vBoxForAddUser.getChildren().addAll(addUserLabel, textFieldForAddUser, hBoxForAddUserButtons);
		vBoxForAddUser.setCache(true);
		vBoxForAddUser.setCacheHint(CacheHint.SCALE);
		
		//POP UP MESSAGES
		popUpMessageLabel = new Label();
		popUpMessageLabel.setTextAlignment(TextAlignment.CENTER);
		popUpMessageLabel.setTextFill(Color.WHITE);
		popUpMessageLabel.setWrapText(true);
		
		confirmDeleteCheckBox = new CustomCheckBox();
		
		popUpAskConfirmationLabel = new Label();
		popUpAskConfirmationLabel.setTextAlignment(TextAlignment.CENTER);
		popUpAskConfirmationLabel.setTextFill(Color.WHITE);
		popUpAskConfirmationLabel.setWrapText(true);
		
		cancelPopUpActionButton = new CustomButton();
		confirmPopUpActionButton = new CustomButton();
		
		hBoxForPopUpConfirmationButtons = new HBox();
		hBoxForPopUpConfirmationButtons.setAlignment(Pos.CENTER);
		hBoxForPopUpConfirmationButtons.setFillHeight(true);
		hBoxForPopUpConfirmationButtons.getChildren().addAll(cancelPopUpActionButton, confirmPopUpActionButton);
		
		vBoxForPopUpAskConfirmation = new VBox();
		vBoxForPopUpAskConfirmation.setAlignment(Pos.CENTER);
		vBoxForPopUpAskConfirmation.getChildren().addAll(popUpAskConfirmationLabel, confirmDeleteCheckBox, hBoxForPopUpConfirmationButtons);
		vBoxForPopUpAskConfirmation.setCache(true);
		vBoxForPopUpAskConfirmation.setCacheHint(CacheHint.SCALE);
		
		boxBlurForNodes = new BoxBlur();
		boxBlurForNodes.setWidth(20);
		boxBlurForNodes.setHeight(20);
		boxBlurForNodes.setIterations(2);
		
		//ADD DROP SHADOW EFFECT TO NODES
		dropShadow = new DropShadow();
		woodenFrameImage.setEffect(dropShadow);
		woodPanelFor1IconImage.setEffect(dropShadow);
		woodPanelFor4IconsImage.setEffect(dropShadow);
		gameNameWoodenImage.setEffect(dropShadow);
		welcomeImage.setEffect(dropShadow);
		leftGlobeStand.setEffect(dropShadow);
		rightGlobeStand.setEffect(dropShadow);
		editNameIcon.setEffect(dropShadow);
		settingsIcon.setEffect(dropShadow);
		infoIcon.setEffect(dropShadow);

		//ADD EVERYTHING TO ANCHOR PANE
		nodesPane.getChildren().addAll(
				chalkboardBackgroundImage, welcomeImage, welcomeLabel, gameNameWoodenImage,
				leftGlobeStand, rightGlobeStand, woodPanelFor1IconImage,
				leftGlobeImage, rightGlobeImage, editNameIcon,
				woodPanelFor4IconsImage, vBoxForMainButtons, hBoxForSettingsAndInfoIcons, usersEditSegmentedButton,
				vBoxForEditUsername, vBoxForSwitchUser, vBoxForAddUser, rectangleForInformationAboutGame, vBoxForSound,
				vBoxForSettings, hBoxFor4SettingsButtons, soundIcon, vBoxForPopUpAskConfirmation, popUpMessageLabel);
		
		loadUsersObservableList();
		
		setupPopUpAnimations();
		
		fadeInTransition = new FadeTransition(Duration.millis(800), anchorPane);
		fadeInTransition.setToValue(1.0);
		
		if(animationsUsed != ANIMATIONS.NO)
		{
			setupLimitedAnimations();
			
			fadeInTransition.setOnFinished(e -> timelineToShowAllStuff.playFromStart());
		}
		
		setupListeners();
		
		multiPlayerGameButton.setDisable(true);
	}
	
	private void disableUI()
	{
		editNameIcon.setDisable(true);
		soundIcon.setDisable(true);
		hBoxForSettingsAndInfoIcons.setDisable(true);
		leftGlobeImage.setDisable(true);
		rightGlobeImage.setDisable(true);
		vBoxForSettings.setDisable(true);
		usersEditSegmentedButton.setDisable(true);
		vBoxForSwitchUser.setDisable(true);
		vBoxForSound.setDisable(true);
	}
	
	private void enableUI()
	{
		editNameIcon.setDisable(false);
		soundIcon.setDisable(false);
		hBoxForSettingsAndInfoIcons.setDisable(false);
		leftGlobeImage.setDisable(false);
		rightGlobeImage.setDisable(false);
		vBoxForSettings.setDisable(false);
		usersEditSegmentedButton.setDisable(false);
		vBoxForSwitchUser.setDisable(false);
		vBoxForSound.setDisable(false);
	}
	
	private void addBlurEffect()
	{
		chalkboardBackgroundImage.setEffect(boxBlurForNodes);
		woodPanelFor1IconImage.setEffect(boxBlurForNodes);
		gameNameWoodenImage.setEffect(boxBlurForNodes);
		welcomeImage.setEffect(boxBlurForNodes);
		welcomeLabel.setEffect(boxBlurForNodes);
		leftGlobeStand.setEffect(boxBlurForNodes);
		rightGlobeStand.setEffect(boxBlurForNodes);
		editNameIcon.setEffect(boxBlurForNodes);
		soundIcon.setEffect(boxBlurForNodes);
		hBoxForSettingsAndInfoIcons.setEffect(boxBlurForNodes);
		leftGlobeImage.setEffect(boxBlurForNodes);
		rightGlobeImage.setEffect(boxBlurForNodes);
		vBoxForSettings.setEffect(boxBlurForNodes);
		usersEditSegmentedButton.setEffect(boxBlurForNodes);
		vBoxForSwitchUser.setEffect(boxBlurForNodes);
		vBoxForSound.setEffect(boxBlurForNodes);
	}
	
	private void removeBlurEffect()
	{
		chalkboardBackgroundImage.setEffect(null);
		woodPanelFor1IconImage.setEffect(dropShadow);
		gameNameWoodenImage.setEffect(dropShadow);
		welcomeImage.setEffect(dropShadow);
		welcomeLabel.setEffect(welcomeLabelInnerShadow);
		leftGlobeStand.setEffect(dropShadow);
		rightGlobeStand.setEffect(dropShadow);
		editNameIcon.setEffect(dropShadow);
		soundIcon.setEffect(null);
		hBoxForSettingsAndInfoIcons.setEffect(null);
		leftGlobeImage.setEffect(null);
		rightGlobeImage.setEffect(null);
		vBoxForSettings.setEffect(null);
		usersEditSegmentedButton.setEffect(null);
		vBoxForSwitchUser.setEffect(null);
		vBoxForSound.setEffect(null);
	}
	
	public void showScreen(boolean firstTime)
	{
		if(firstTime)
		{
			hasNotInitializedWindowedMode = true;
			if(animationsUsed != ANIMATIONS.NO) anchorPane.setOpacity(0);
		}
		
		if (fullScreenMode) setFullScreenMode();
		else setWindowedMode();
		
		setInitialStateForAllNodes(firstTime);
		
		mainScene.setRoot(anchorPane);
	}
	
	private void setInitialStateForAllNodes(boolean firstTime)
	{
//		--------------- Load images if not already set ---------------
		if(woodenFrameImage.getImage() == null || !woodenFrameImage.getImage().equals(FRAME_IMAGE))
		{
			woodenFrameImage.setImage(FRAME_IMAGE);
			chalkboardBackgroundImage.setImage(CHALKBOARD_BACKGROUND_IMAGE);
			woodPanelFor1IconImage.setImage(WOOD_BACKGROUND_IMAGE_FOR_1_BUTTON);
			woodPanelFor4IconsImage.setImage(WOOD_BACKGROUND_IMAGE_FOR_4_BUTTONS);
			welcomeImage.setImage(EMPTY_WOOD_BACKGROUND_PANEL_SMALL_ROPE);
			gameNameWoodenImage.setImage(GAME_NAME_IMAGE);
			
			leftGlobeStand.setImage(LEFT_GLOBE_STAND_IMAGE);
			rightGlobeStand.setImage(RIGHT_GLOBE_STAND_IMAGE);
			
			editUserIcon.setImage(EDIT_USERNAME_ICON);
			switchUserIcon.setImage(SWITCH_USER_ICON);
			addUserIcon.setImage(ADD_USER_ICON);
			
			settingsIcon.setImage(SETTINGS_ICON);
			minimizeIcon.setImage(MINIMIZE_ICON);
			exitIcon.setImage(EXIT_ICON);
			singlePlayerIcon.setImage(SINGLE_PLAYER_ICON);
			multiPlayerIcon.setImage(MULTI_PLAYER_ICON);
			atlasIcon.setImage(ATLAS_ICON);
			scoresIcon.setImage(SCORES_ICON);
			infoIcon.setImage(INFO_ICON);
			greekLanguageFlag.setImage(GREEK_FLAG_ICON);
			englishLanguageFlag.setImage(ENGLISH_FLAG_ICON);
			editNameIcon.setImage(PENCIL_ICON);
		}
		
		if(leftGlobeImage.getImage() == null)
		{
			leftGlobeImage.setImage(animatedGlobe[0]);
			rightGlobeImage.setImage(animatedGlobe[0]);
		}
		
		settingsIcon.setImage(SETTINGS_ICON);
		
		updateStrings();
		
		if(countriesLocalesObservableList == null) loadCountriesLocalesObservableList();
		
		usersEditSegmentedButton.getToggleGroup().selectToggle(editUsernameToggleButton);
		
//		--------------- SET POSITIONS ---------------
		vBoxForSound.setVisible(false);
		if(OS == OsCheck.OSType.Windows) vBoxForSound.setTranslateX(-1.0 * (vBoxForSound.getLayoutX() + vBoxForSound.getPrefWidth() + 20));
		else vBoxForSound.setTranslateX(stage.getWidth() - vBoxForSound.getLayoutX() + 20);
		
		rectangleForInformationAboutGame.setScaleX(0);
		rectangleForInformationAboutGame.setScaleY(0);
		rectangleForInformationAboutGame.setVisible(false);
		
		vBoxForSettings.setScaleX(0);
		vBoxForSettings.setScaleY(0);
		vBoxForSettings.setVisible(false);
		
		vBoxForMainButtons.setTranslateY(0);
		vBoxForMainButtons.setVisible(true);
		
		usersEditSegmentedButton.setScaleX(0);
		usersEditSegmentedButton.setScaleY(0);
		usersEditSegmentedButton.setVisible(false);
		
		vBoxForEditUsername.setScaleX(0);
		vBoxForEditUsername.setScaleY(0);
		vBoxForEditUsername.setVisible(false);
		
		vBoxForSwitchUser.setScaleX(0);
		vBoxForSwitchUser.setScaleY(0);
		vBoxForSwitchUser.setVisible(false);
		
		vBoxForAddUser.setScaleX(0);
		vBoxForAddUser.setScaleY(0);
		vBoxForAddUser.setVisible(false);
		
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
			woodPanelFor4IconsImage.setTranslateY(-1.0 * (woodPanelFor4IconsImage.getLayoutY() + woodPanelFor4IconsImage.getBoundsInParent().getHeight()));
			woodPanelFor1IconImage.setTranslateY(-1.0 * (woodPanelFor1IconImage.getLayoutY() + woodPanelFor1IconImage.getBoundsInParent().getHeight()));
			gameNameWoodenImage.setTranslateY(-1.0 * (gameNameWoodenImage.getLayoutY() + gameNameWoodenImage.getBoundsInParent().getHeight()));
			leftGlobeStand.setTranslateX(-1.0 * (leftGlobeStand.getLayoutX() + leftGlobeStand.getFitWidth() + 20));
			leftGlobeImage.setTranslateX(-1.0 * (leftGlobeStand.getLayoutX() + leftGlobeStand.getFitWidth() + 20));
			rightGlobeStand.setTranslateX(stage.getWidth() - rightGlobeStand.getLayoutX() + 20);
			rightGlobeImage.setTranslateX(stage.getWidth() - rightGlobeStand.getLayoutX() + 20);
			
			welcomeImage.setTranslateY(-0.1759 * stage.getHeight());
			welcomeImage.setScaleY(0);
			
			vBoxForMainButtons.setTranslateY(0);
			
			soundIcon.setScaleX(0);
			soundIcon.setScaleY(0);
			hBoxFor4SettingsButtons.setScaleX(0);
			hBoxFor4SettingsButtons.setScaleY(0);
			welcomeLabel.setScaleX(0);
			welcomeLabel.setScaleY(0);
			editNameIcon.setScaleX(0);
			editNameIcon.setScaleY(0);
			singlePlayerGameButton.setScaleX(0);
			singlePlayerGameButton.setScaleY(0);
			multiPlayerGameButton.setScaleX(0);
			multiPlayerGameButton.setScaleY(0);
			atlasButton.setScaleX(0);
			atlasButton.setScaleY(0);
			scoreBoardButton.setScaleX(0);
			scoreBoardButton.setScaleY(0);
			infoIcon.setScaleX(0);
			infoIcon.setScaleY(0);
			settingsIcon.setScaleX(0);
			settingsIcon.setScaleY(0);
			
			if(firstTime) fadeInTransition.playFromStart();
			else timelineToShowAllStuff.playFromStart();
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
				playCheckBoxSelectedSound();
				
				confirmPopUpActionButton.setDisable(false);
			}
			else
			{
				playCheckBoxDeselectedSound();
				
				confirmPopUpActionButton.setDisable(true);
			}
		});
		
		cancelPopUpActionButton.setOnMouseEntered(e -> playHoverSound());
		confirmPopUpActionButton.setOnMouseEntered(e -> playHoverSound());
		
		cancelPopUpActionButton.setOnAction(e -> timelineToCancelConfirmationMessage.playFromStart());
		
		confirmPopUpActionButton.setOnAction(e ->
		{
			enableUI();
			
			if(popUpAction == POP_UP_ACTION.DELETE_SINGLE_USER)
			{
				usersObservableList.remove(usersComboBox.getSelectionModel().getSelectedIndex());
				playersArrayList.remove(usersComboBox.getSelectionModel().getSelectedIndex());
				FilesIO.writePlayersFile();
				
				popUpMessageLabel.setText(languageResourceBundle.getString("deleteSingleUserSuccessfullyMessage"));
			}
			else if(popUpAction == POP_UP_ACTION.DELETE_ALL_USERS)
			{
				usersObservableList.remove(1, usersObservableList.size());
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
		
		editNameIcon.setOnMouseClicked(e ->
		{
			editNameIcon.setImage(PENCIL_ICON_DISABLED);
			
			usersComboBox.getSelectionModel().select(0);
			
			if(animationsUsed != ANIMATIONS.NO) timelineToShowEditUsersVBox.playFromStart();
			else
			{
				vBoxForMainButtons.setVisible(false);
				vBoxForMainButtons.setTranslateY(stage.getHeight() - vBoxForMainButtons.getLayoutY() + 20);
				
				usersEditSegmentedButton.setScaleX(1);
				usersEditSegmentedButton.setScaleY(1);
				usersEditSegmentedButton.setVisible(true);
				
				if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == editUsernameToggleButton)
				{
					vBoxForEditUsername.setScaleX(1);
					vBoxForEditUsername.setScaleY(1);
					vBoxForEditUsername.setVisible(true);
				}
				else if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == switchUserToggleButton)
				{
					vBoxForSwitchUser.setScaleX(1);
					vBoxForSwitchUser.setScaleY(1);
					vBoxForSwitchUser.setVisible(true);
				}
				else
				{
					vBoxForAddUser.setScaleX(1);
					vBoxForAddUser.setScaleY(1);
					vBoxForAddUser.setVisible(true);
				}
				
				settingsIcon.setDisable(true);
				settingsIcon.setScaleX(0);
				settingsIcon.setScaleY(0);
				
				infoIcon.setDisable(true);
				infoIcon.setScaleX(0);
				infoIcon.setScaleY(0);
			}
		});
		
		editUsernameToggleButton.setOnMouseEntered(e -> playHoverSound());
		
		switchUserToggleButton.setOnMouseEntered(e -> playHoverSound());
		
		addUserToggleButton.setOnMouseEntered(e -> playHoverSound());
		
		toggleGroupForUsersEditSegmentedButton.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
		{
			if(newValue == null) toggleGroupForUsersEditSegmentedButton.selectToggle(oldValue);
			else if(oldValue != null && usersEditSegmentedButton.isVisible()) changeUsersEditVBox();
		});
		
//		EDIT USERNAME LISTENERS--------------------------------------------------------------------------------
		textFieldForEditUsername.textProperty().addListener((observableValue, oldValue, newValue) ->
		{
			newValue = newValue.trim();
			
			if (newValue.isEmpty() || usernameAlreadyExists(newValue))
			{
				confirmEditUsernameButton.setDisable(true);
				
				if(newValue.isEmpty())
				{
					textFieldForEditUsername.setStyle("-fx-control-inner-background:" + NORMAL_COLOR + ";");
					textFieldForEditUsername.getTooltip().setText(languageResourceBundle.getString("textFieldEmpty"));
				}
				else
				{
					textFieldForEditUsername.setStyle("-fx-control-inner-background:" + RED_COLOR + ";");
					textFieldForEditUsername.getTooltip().setText(languageResourceBundle.getString("textFieldRejectedUsernameTooltip"));
				}
			}
			else
			{
				textFieldForEditUsername.setStyle("-fx-control-inner-background:" + GREEN_COLOR + ";");
				textFieldForEditUsername.getTooltip().setText(languageResourceBundle.getString("textFieldAcceptedUsernameTooltip"));
				
				confirmEditUsernameButton.setDisable(false);
			}
		});
		
		textFieldForEditUsername.setOnAction(e ->
		{
			if (!confirmEditUsernameButton.isDisabled()) editUsername(textFieldForEditUsername.getText());
		});
		
		cancelEditUsernameButton.setOnAction(e ->
		{
			playButtonClickSound();
			
			if(animationsUsed != ANIMATIONS.NO) timelineToHideEditUsersVBox.playFromStart();
			else hideEditUsersBoxesNoAnimations();
		});
		
		cancelEditUsernameButton.setOnMouseEntered(e -> playHoverSound());
		
		confirmEditUsernameButton.setOnAction(e -> editUsername(textFieldForEditUsername.getText()));
		
		confirmEditUsernameButton.setOnMouseEntered(e -> playHoverSound());
		
//		SWITCH USER LISTENERS--------------------------------------------------------------------------------
		usersComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
		{
			if(usersComboBox.getSelectionModel().getSelectedIndex() == 0)
			{
				confirmSwitchUserButton.setDisable(true);
				deleteSingleUserLabel.getTooltip().setText(languageResourceBundle.getString("deleteSingleUserUnableToDeleteMessage"));
			}
			else
			{
				confirmSwitchUserButton.setDisable(false);
				deleteSingleUserLabel.getTooltip().setText(languageResourceBundle.getString("deleteSingleUserTooltip"));
			}
		});
		
		deleteSingleUserLabel.setOnMouseClicked(e ->
		{
			if(usersComboBox.getSelectionModel().getSelectedIndex() > 0)
			{
				popUpAction = POP_UP_ACTION.DELETE_SINGLE_USER;
				popUpAskConfirmationLabel.setText(languageResourceBundle.getString("deleteSingleUserConfirmationMessage"));
				confirmPopUpActionButton.setText(languageResourceBundle.getString("deleteSingleUserLabel"));
				timelineToShowConfirmationMessage.playFromStart();
			}
		});
		
		deleteAllUsersLabel.setOnMouseClicked(e ->
		{
			popUpAction = POP_UP_ACTION.DELETE_ALL_USERS;
			popUpAskConfirmationLabel.setText(languageResourceBundle.getString("deleteAllUsersConfirmationMessage"));
			confirmPopUpActionButton.setText(languageResourceBundle.getString("deleteAllLabel"));
			
			timelineToShowConfirmationMessage.playFromStart();
		});
		
		cancelSwitchUserButton.setOnAction(e ->
		{
			playButtonClickSound();
			
			if(animationsUsed != ANIMATIONS.NO) timelineToHideEditUsersVBox.playFromStart();
			else hideEditUsersBoxesNoAnimations();
		});
		
		cancelSwitchUserButton.setOnMouseEntered(e -> playHoverSound());
		
		confirmSwitchUserButton.setOnAction(e -> changeCurrentPlayer(usersComboBox.getSelectionModel().getSelectedIndex()));
		
		confirmSwitchUserButton.setOnMouseEntered(e -> playHoverSound());
		
//		ADD USER LISTENERS--------------------------------------------------------------------------------
		textFieldForAddUser.textProperty().addListener((observableValue, oldValue, newValue) ->
		{
			newValue = newValue.trim();
			if (newValue.isEmpty() || usernameAlreadyExists(newValue))
			{
				confirmAddUserButton.setDisable(true);
				
				if(newValue.isEmpty())
				{
					textFieldForAddUser.setStyle("-fx-control-inner-background:" + NORMAL_COLOR + ";");
					textFieldForAddUser.getTooltip().setText(languageResourceBundle.getString("textFieldEmpty"));
				}
				else
				{
					textFieldForAddUser.setStyle("-fx-control-inner-background:" + RED_COLOR + ";");
					textFieldForAddUser.getTooltip().setText(languageResourceBundle.getString("textFieldRejectedUsernameTooltip"));
				}
			}
			else
			{
				textFieldForAddUser.setStyle("-fx-control-inner-background:" + GREEN_COLOR + ";");
				textFieldForAddUser.getTooltip().setText(languageResourceBundle.getString("textFieldAcceptedUsernameTooltip"));
				
				confirmAddUserButton.setDisable(false);
			}
		});
		
		textFieldForAddUser.setOnAction(e ->
		{
			if (!confirmAddUserButton.isDisabled()) addUser(textFieldForAddUser.getText());
		});
		
		cancelAddUserButton.setOnAction(e ->
		{
			playButtonClickSound();
			
			if(animationsUsed != ANIMATIONS.NO) timelineToHideEditUsersVBox.playFromStart();
			else hideEditUsersBoxesNoAnimations();
		});
		
		cancelAddUserButton.setOnMouseEntered(e -> playHoverSound());
		
		confirmAddUserButton.setOnAction(e -> addUser(textFieldForAddUser.getText()));
		
		confirmAddUserButton.setOnMouseEntered(e -> playHoverSound());


//		START IN FULL SCREEN CHECKBOX LISTENER--------------------------------------------------------------------------------
		startInFullScreenCheckBox.setOnAction(e ->
		{
			if (startInFullScreenCheckBox.isSelected())
			{
				playCheckBoxSelectedSound();
				
				startAtFullScreen = true;
			}
			else
			{
				playCheckBoxDeselectedSound();
				
				startAtFullScreen = false;
			}
			getCurrentPlayer().setStartAtFullScreen(startAtFullScreen);
		});
		
//		ANIMATIONS USED LISTENERS--------------------------------------------------------------------------------
		noAnimationsToggleButton.setOnMouseEntered(e -> playHoverSound());
		
		limitedAnimationsToggleButton.setOnMouseEntered(e -> playHoverSound());
		
		allAnimationsToggleButton.setOnMouseEntered(e -> playHoverSound());
		
		toggleGroupForAnimationsUsedSegmentedButton.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
		{
			if(newValue == null) toggleGroupForAnimationsUsedSegmentedButton.selectToggle(oldValue);
			else
			{
				if(toggleGroupForAnimationsUsedSegmentedButton.getSelectedToggle() == noAnimationsToggleButton) animationsUsed = ANIMATIONS.NO;
				else if(toggleGroupForAnimationsUsedSegmentedButton.getSelectedToggle() == limitedAnimationsToggleButton) animationsUsed = ANIMATIONS.LIMITED;
				else animationsUsed = ANIMATIONS.ALL;
				
				getCurrentPlayer().setAnimationsUsed(animationsUsed);
				
				if (animationsUsed == ANIMATIONS.ALL)
				{
					Thread globeThread = null;
					if(animatedGlobe.length == 1)
					{
						globeThread = new Thread(PowerOn::powerOnAnimatedGlobeImages);
						globeThread.start();
						
						leftGlobeStatus = 1;
						rightGlobeStatus = -1;
						previousTransitionOfLeftGlobeWasRight = false;
						previousTransitionOfRightGlobeWasRight = false;
					}
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
					
					if(globeThread != null)
					{
						try {globeThread.join();}
						catch(Exception ex){}
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
						gamePropertiesScreen.setupLimitedAnimations();
						gameScreen.setupLimitedAnimations();
						atlasScreen.setupLimitedAnimations();
						scoreBoardScreen.setupLimitedAnimations();
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
		englishLanguageButton.setOnMouseEntered(e -> playHoverSound());
		greekLanguageButton.setOnMouseEntered(e -> playHoverSound());
		
		englishLanguageButton.setOnAction(e ->
		{
			playButtonClickSound();
			
			if(vBoxForSettings.isVisible() && !englishLanguageButton.isUnderline())
			{
				getCurrentPlayer().setLanguage(LANGUAGE.ENGLISH);
				
				if(animationsUsed != ANIMATIONS.NO) timelineToChangeLanguage.playFromStart();
				else changeLanguage();
			}
			
			englishLanguageButton.setUnderline(true);
			greekLanguageButton.setUnderline(false);
		});
		
		greekLanguageButton.setOnAction(e ->
			{
				playButtonClickSound();
				
				if(vBoxForSettings.isVisible() && !greekLanguageButton.isUnderline())
				{
					getCurrentPlayer().setLanguage(LANGUAGE.GREEK);
					
					if(animationsUsed != ANIMATIONS.NO) timelineToChangeLanguage.playFromStart();
					else changeLanguage();
				}
				
				greekLanguageButton.setUnderline(true);
				englishLanguageButton.setUnderline(false);
			});
		
//		UNITS OF LENGTH LISTENERS--------------------------------------------------------------------------------
		toggleGroupForUnitsOfLength.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
		{
			if(oldValue != null) playRadioButtonSelectedSound();
			
			if(metricSystemRadioButton.isSelected()) getCurrentPlayer().setUnitSystem(UNIT_SYSTEM.METRIC);
			else getCurrentPlayer().setUnitSystem(UNIT_SYSTEM.IMPERIAL);
		});
		
//		DELETE ALL DATA LISTENERS--------------------------------------------------------------------------------
		deleteAllDataButton.setOnMouseEntered(e -> playHoverSound());
		
		deleteAllDataButton.setOnMouseClicked(e ->
		{
			playButtonClickSound();
			
			popUpAction = POP_UP_ACTION.DELETE_ALL_DATA;
			popUpAskConfirmationLabel.setText(languageResourceBundle.getString("deleteAllDataConfirmationMessage"));
			confirmPopUpActionButton.setText(languageResourceBundle.getString("deleteAllLabel"));
			timelineToShowConfirmationMessage.playFromStart();
		});

		chalkboardBackgroundImage.setOnMouseClicked(e -> chalkboardBackgroundImage.requestFocus());

		soundIcon.setOnMouseClicked(e ->
			{
				soundIcon.setDisable(true);
				settingsIcon.setDisable(true);
				
				if (!vBoxForSound.isVisible())
				{
					setSoundIcon(soundIcon, true);
					
					if(animationsUsed != ANIMATIONS.NO) timelineToShowSoundOptions.playFromStart();
					else
					{
						vBoxForSound.setVisible(true);
						vBoxForSound.setTranslateX(0);
					}
					
				}
				else
				{
					setSoundIcon(soundIcon, false);
					
					if(animationsUsed != ANIMATIONS.NO) timelineToHideSoundOptions.playFromStart();
					else
					{
						if(OS == OSType.Windows) vBoxForSound.setTranslateX(-1.0 * (vBoxForSound.getLayoutX() + vBoxForSound.getPrefWidth() + 20));
						else vBoxForSound.setTranslateX(stage.getWidth() - vBoxForSound.getLayoutX() + 20);
						
						vBoxForSound.setVisible(false);
					}
				}
				soundIcon.setDisable(false);
				settingsIcon.setDisable(false);
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

		settingsIcon.setOnMouseClicked(e ->
			{
				if (!vBoxForSettings.isVisible())
				{
					settingsIcon.setImage(SETTINGS_ICON_CLICKED);
					
					if(animationsUsed != ANIMATIONS.NO) timelineToShowSettings.playFromStart();
					else
					{
						welcomeLabel.setScaleX(0);
						welcomeLabel.setScaleY(0);
						
						welcomeImage.setScaleY(0);
						welcomeImage.setTranslateY(-0.1759 * stage.getHeight());
						
						vBoxForMainButtons.setOpacity(0);
						vBoxForMainButtons.setTranslateY(stage.getHeight() - vBoxForMainButtons.getLayoutY() + 20);
						
						vBoxForSettings.setScaleX(1);
						vBoxForSettings.setScaleY(1);
						vBoxForSettings.setVisible(true);
						
						editNameIcon.setDisable(true);
						editNameIcon.setScaleX(0);
						editNameIcon.setScaleY(0);
						
						infoIcon.setDisable(true);
						infoIcon.setScaleX(0);
						infoIcon.setScaleY(0);
					}
				}
				else
				{
					settingsIcon.setImage(SETTINGS_ICON);
					
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
						
						editNameIcon.setScaleX(1);
						editNameIcon.setScaleY(1);
						editNameIcon.setDisable(false);
						
						infoIcon.setScaleX(1);
						infoIcon.setScaleY(1);
						infoIcon.setDisable(false);
					}
				}
			});
		
		settingsIcon.setOnMousePressed(e ->
		{
			if (!vBoxForSettings.isVisible() && settingsIcon.getImage() == SETTINGS_ICON) settingsIcon.setImage(SETTINGS_ICON_CLICKED);
			else if (vBoxForSettings.isVisible() && settingsIcon.getImage() == SETTINGS_ICON_CLICKED) settingsIcon.setImage(SETTINGS_ICON);
		});
		
		settingsIcon.setOnMouseReleased(e ->
		{
			if(!vBoxForSettings.isVisible() && settingsIcon.getImage() == SETTINGS_ICON_CLICKED) settingsIcon.setImage(SETTINGS_ICON);
			else if(vBoxForSettings.isVisible() && settingsIcon.getImage() == SETTINGS_ICON) settingsIcon.setImage(SETTINGS_ICON_CLICKED);
		});

		infoIcon.setOnMousePressed(e ->
		{
			if(rectangleForInformationAboutGame.isVisible()) infoIcon.setImage(INFO_ICON);
			else infoIcon.setImage(INFO_ICON_CLICKED);
		});

		infoIcon.setOnMouseClicked(e ->
			{
				if(!rectangleForInformationAboutGame.isVisible())
				{
					infoIcon.setImage(INFO_ICON_CLICKED);
					
					if(animationsUsed != ANIMATIONS.NO) timelineToShowInformationAboutGame.playFromStart();
					else
					{
						welcomeLabel.setScaleX(0);
						welcomeLabel.setScaleY(0);
						
						welcomeImage.setScaleY(0);
						welcomeImage.setTranslateY(-0.1759 * stage.getHeight());
						
						vBoxForMainButtons.setOpacity(0);
						vBoxForMainButtons.setTranslateY(stage.getHeight() - vBoxForMainButtons.getLayoutY() + 20);
						
						rectangleForInformationAboutGame.setScaleX(1);
						rectangleForInformationAboutGame.setScaleY(1);
						rectangleForInformationAboutGame.setVisible(true);
						
						editNameIcon.setDisable(true);
						editNameIcon.setScaleX(0);
						editNameIcon.setScaleY(0);
						
						settingsIcon.setDisable(true);
						settingsIcon.setScaleX(0);
						settingsIcon.setScaleY(0);
					}
				}
				else
				{
					infoIcon.setImage(INFO_ICON);
					
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
						
						editNameIcon.setScaleX(1);
						editNameIcon.setScaleY(1);
						editNameIcon.setDisable(false);
						
						settingsIcon.setScaleX(1);
						settingsIcon.setScaleY(1);
						settingsIcon.setDisable(false);
					}
				}
			});

		infoIcon.setOnMouseReleased(e ->
			{
				if(rectangleForInformationAboutGame.isVisible()) infoIcon.setImage(INFO_ICON_CLICKED);
				else infoIcon.setImage(INFO_ICON);
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
				playButtonClickSound();

				if(animationsUsed != ANIMATIONS.NO)
				{
					timelineToHideAllStuff.setOnFinished(ev -> gamePropertiesScreen.showScreen(true));
					timelineToHideAllStuff.playFromStart();
				}
				else gamePropertiesScreen.showScreen(true);
			});
		
		singlePlayerGameButton.setOnMouseEntered(e -> playHoverSound());

		multiPlayerGameButton.setOnAction(e ->
			{
				playButtonClickSound();
			});
		
		multiPlayerGameButton.setOnMouseEntered(e -> playHoverSound());

		atlasButton.setOnAction(e ->
			{
				playButtonClickSound();
				
				if(animationsUsed != ANIMATIONS.NO)
				{
					timelineToHideAllStuff.setOnFinished(ev -> atlasScreen.showScreen());
					timelineToHideAllStuff.playFromStart();
				}
				else atlasScreen.showScreen();
			});
		
		atlasButton.setOnMouseEntered(e -> playHoverSound());
		
		scoreBoardButton.setOnAction(e ->
			{
				playButtonClickSound();
				
				if(animationsUsed != ANIMATIONS.NO)
				{
					timelineToHideAllStuff.setOnFinished(ev -> scoreBoardScreen.showScreen());
					timelineToHideAllStuff.playFromStart();
				}
				else scoreBoardScreen.showScreen();
			});
		
		scoreBoardButton.setOnMouseEntered(e -> playHoverSound());

		anchorPane.setOnKeyPressed(e ->
			{
				if (e.getCode() == KeyCode.ESCAPE && fullScreenMode) setWindowedMode();
			});
	}

	protected void setupLimitedAnimations()
	{
		ScaleTransition scaleTransitionForOnePlayerGameButton1 = new ScaleTransition(Duration.millis(200));
		scaleTransitionForOnePlayerGameButton1.setToX(1.2);
		scaleTransitionForOnePlayerGameButton1.setToY(1.2);
		
		ScaleTransition scaleTransitionForOnePlayerGameButton2 = new ScaleTransition(Duration.millis(100));
		scaleTransitionForOnePlayerGameButton2.setToX(1);
		scaleTransitionForOnePlayerGameButton2.setToY(1);
		
		ScaleTransition scaleTransitionForTwoPlayersGameButton1 = new ScaleTransition(Duration.millis(200));
		scaleTransitionForTwoPlayersGameButton1.setToX(1.2);
		scaleTransitionForTwoPlayersGameButton1.setToY(1.2);
		
		ScaleTransition scaleTransitionForTwoPlayersGameButton2 = new ScaleTransition(Duration.millis(100));
		scaleTransitionForTwoPlayersGameButton2.setToX(1);
		scaleTransitionForTwoPlayersGameButton2.setToY(1);
		
		ScaleTransition scaleTransitionForAtlas1 = new ScaleTransition(Duration.millis(200));
		scaleTransitionForAtlas1.setToX(1.2);
		scaleTransitionForAtlas1.setToY(1.2);
		
		ScaleTransition scaleTransitionForAtlas2 = new ScaleTransition(Duration.millis(100));
		scaleTransitionForAtlas2.setToX(1);
		scaleTransitionForAtlas2.setToY(1);
		
		ScaleTransition scaleTransitionForScoreBoard1 = new ScaleTransition(Duration.millis(200));
		scaleTransitionForScoreBoard1.setToX(1.2);
		scaleTransitionForScoreBoard1.setToY(1.2);
		
		ScaleTransition scaleTransitionForScoreBoard2 = new ScaleTransition(Duration.millis(100));
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
		sequentialTransitionForInfoButton = new SequentialTransition(infoIcon, scaleTransitionForInfoButton1, scaleTransitionForInfoButton2);
		sequentialTransitionForSettingsButton = new SequentialTransition(settingsIcon, scaleTransitionForSettingsButton1, scaleTransitionForSettingsButton2);

		translateTransitionForWoodPanelFor1IconImage = new TranslateTransition(Duration.millis(300), woodPanelFor1IconImage);
		translateTransitionForWoodPanelFor4IconsImage = new TranslateTransition(Duration.millis(300), woodPanelFor4IconsImage);

		translateTransitionForGameNameImage = new TranslateTransition(Duration.millis(300), gameNameWoodenImage);
		
		TranslateTransition translateTransitionForWelcomeImage = new TranslateTransition(Duration.millis(300), welcomeImage);
		ScaleTransition scaleTransitionForWelcomeImage = new ScaleTransition(Duration.millis(300), welcomeImage);
		parallelTransitionForWelcomeImage = new ParallelTransition(translateTransitionForWelcomeImage, scaleTransitionForWelcomeImage);
		
		scaleTransitionForSoundIcon = new ScaleTransition(Duration.millis(300), soundIcon);
		scaleTransitionFor4Settings = new ScaleTransition(Duration.millis(300), hBoxFor4SettingsButtons);
		
		scaleTransitionForWelcomeLabel = new ScaleTransition();
		scaleTransitionForWelcomeLabel.setNode(welcomeLabel);
		
		ScaleTransition scaleTransitionForWelcomeLabelParallel = new ScaleTransition(Duration.millis(300), welcomeLabel);
		TranslateTransition translateTransitionForWelcomeLabel = new TranslateTransition(Duration.millis(300), welcomeLabel);
		parallelTransitionForWelcomeLabel = new ParallelTransition(translateTransitionForWelcomeLabel, scaleTransitionForWelcomeLabelParallel);
		
		scaleTransitionForEditIcon = new ScaleTransition(Duration.millis(300), editNameIcon);
		scaleTransitionForInfoButton = new ScaleTransition(Duration.millis(300), infoIcon);

		translateTransitionForLeftGlobeStand = new TranslateTransition(Duration.millis(300), leftGlobeStand);
		translateTransitionForLeftGlobeImage = new TranslateTransition(Duration.millis(300), leftGlobeImage);
		translateTransitionForRightGlobeStand = new TranslateTransition(Duration.millis(300), rightGlobeStand);
		translateTransitionForRightGlobeImage = new TranslateTransition(Duration.millis(300), rightGlobeImage);

		translateTransitionForVBoxWithMainButtons = new TranslateTransition(Duration.millis(300), vBoxForMainButtons);
		
		scaleTransitionForUsersEditSegmentedButton = new ScaleTransition(Duration.millis(300), usersEditSegmentedButton);
		
		scaleTransitionForVBoxForEditUsername = new ScaleTransition(Duration.millis(300), vBoxForEditUsername);
		
		scaleTransitionForVBoxForSwitchUser = new ScaleTransition(Duration.millis(300), vBoxForSwitchUser);
		
		scaleTransitionForVBoxForAddUser = new ScaleTransition(Duration.millis(300), vBoxForAddUser);

		translateTransitionForVBoxForSound = new TranslateTransition(Duration.millis(300), vBoxForSound);
		
		scaleTransitionForVBoxForSettings = new ScaleTransition(Duration.millis(300), vBoxForSettings);
		
		scaleTransitionForSettingsIcon = new ScaleTransition(Duration.millis(300), settingsIcon);
		
		scaleTransitionForRectangleForInfoAboutGame = new ScaleTransition(Duration.millis(300), rectangleForInformationAboutGame);
		
		scaleTransitionForPopUpMessage = new ScaleTransition(Duration.millis(300), popUpMessageLabel);
		scaleTransitionForPopUpAskConfirmation = new ScaleTransition(Duration.millis(300), vBoxForPopUpAskConfirmation);

		timelineToShowAllStuff = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				vBoxForMainButtons.setDisable(true);
				infoIcon.setDisable(true);
				editNameIcon.setDisable(true);

				translateTransitionForGameNameImage.setToY(0);
				translateTransitionForWoodPanelFor1IconImage.setToY(0);
				translateTransitionForWoodPanelFor4IconsImage.setToY(0);
				
				playSlideSound();
				translateTransitionForGameNameImage.playFromStart();
				translateTransitionForWoodPanelFor1IconImage.playFromStart();
				translateTransitionForWoodPanelFor4IconsImage.playFromStart();
			}),
			new KeyFrame(Duration.millis(300), e ->
			{
				scaleTransitionForWelcomeImage.setToY(1);
				translateTransitionForWelcomeImage.setToY(0);
				
				translateTransitionForLeftGlobeStand.setToX(0);
				translateTransitionForLeftGlobeImage.setToX(0);
				translateTransitionForRightGlobeStand.setToX(0);
				translateTransitionForRightGlobeImage.setToX(0);
				
				if(animationsUsed == ANIMATIONS.ALL) playGlobeAnimation();
				
				playSlideSound();
				parallelTransitionForWelcomeImage.playFromStart();
				translateTransitionForLeftGlobeStand.playFromStart();
				translateTransitionForLeftGlobeImage.playFromStart();
				translateTransitionForRightGlobeStand.playFromStart();
				translateTransitionForRightGlobeImage.playFromStart();
			}),
			new KeyFrame(Duration.millis(600), e ->
			{
				scaleTransitionForWelcomeLabel.setDuration(Duration.millis(300));
				scaleTransitionForWelcomeLabel.setFromX(0);
				scaleTransitionForWelcomeLabel.setFromY(0);
				scaleTransitionForWelcomeLabel.setToX(0.95);
				scaleTransitionForWelcomeLabel.setToY(0.95);
				scaleTransitionForWelcomeLabel.setCycleCount(1);
				scaleTransitionForWelcomeLabel.setAutoReverse(false);
				
				scaleTransitionForSoundIcon.setToX(1);
				scaleTransitionForSoundIcon.setToY(1);
				scaleTransitionFor4Settings.setToX(1);
				scaleTransitionFor4Settings.setToY(1);
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
				scaleTransitionFor4Settings.playFromStart();
				scaleTransitionForEditIcon.playFromStart();
			}),
			new KeyFrame(Duration.millis(1600), e ->
			{
				if (masterSliderVolume.get() != 0 && musicSliderVolume.get() != 0) playIntroductionSound();
				
				vBoxForMainButtons.setDisable(false);
				infoIcon.setDisable(false);
				editNameIcon.setDisable(false);
			})
		);

		timelineToHideAllStuff = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				vBoxForMainButtons.setDisable(true);
				infoIcon.setDisable(true);
				editNameIcon.setDisable(true);
				
				pauseWelcomeTextAnimation();
				
				timelineToPopUpMainButtons.stop();
				sequentialTransitionForInfoButton.stop();
				
				scaleTransitionForWelcomeLabel.setDuration(Duration.millis(300));
				scaleTransitionForWelcomeLabel.setFromX(welcomeLabel.getScaleX());
				scaleTransitionForWelcomeLabel.setFromY(welcomeLabel.getScaleY());
				scaleTransitionForWelcomeLabel.setToX(0);
				scaleTransitionForWelcomeLabel.setToY(0);
				scaleTransitionForWelcomeLabel.setAutoReverse(false);
				scaleTransitionForWelcomeLabel.setCycleCount(1);
				scaleTransitionForWelcomeLabel.setOnFinished(ev -> {});

				scaleTransitionForSoundIcon.setToX(0);
				scaleTransitionForSoundIcon.setToY(0);
				scaleTransitionFor4Settings.setToX(0);
				scaleTransitionFor4Settings.setToY(0);
				scaleTransitionForEditIcon.setToX(0);
				scaleTransitionForEditIcon.setToY(0);
				scaleTransitionForSettingsIcon.setToX(0);
				scaleTransitionForSettingsIcon.setToY(0);
				scaleTransitionForInfoButton.setToX(0);
				scaleTransitionForInfoButton.setToY(0);
				
				playSlideSound();
				translateTransitionForVBoxWithMainButtons.setToY(stage.getHeight() - vBoxForMainButtons.getLayoutY() + 20);
				translateTransitionForVBoxWithMainButtons.playFromStart();
				
				playMinimizeSound();
				scaleTransitionForWelcomeLabel.playFromStart();
				scaleTransitionForSoundIcon.playFromStart();
				scaleTransitionFor4Settings.playFromStart();
				scaleTransitionForEditIcon.playFromStart();
				scaleTransitionForSettingsIcon.playFromStart();
				scaleTransitionForInfoButton.playFromStart();
			}),
			new KeyFrame(Duration.millis(300), e ->
			{
				scaleTransitionForWelcomeImage.setToY(0);
				translateTransitionForWelcomeImage.setToY(-0.1759 * stage.getHeight());
				
				translateTransitionForLeftGlobeStand.setToX(-1.0 * (leftGlobeStand.getLayoutX() + leftGlobeStand.getFitWidth() + 20));
				translateTransitionForLeftGlobeImage.setToX(-1.0 * (leftGlobeStand.getLayoutX() + leftGlobeStand.getFitWidth() + 20));
				translateTransitionForRightGlobeStand.setToX(stage.getWidth() - rightGlobeStand.getLayoutX() + 20);
				translateTransitionForRightGlobeImage.setToX(stage.getWidth() - rightGlobeStand.getLayoutX() + 20);
				
				playSlideSound();
				parallelTransitionForWelcomeImage.playFromStart();
				translateTransitionForLeftGlobeStand.playFromStart();
				translateTransitionForLeftGlobeImage.playFromStart();
				translateTransitionForRightGlobeStand.playFromStart();
				translateTransitionForRightGlobeImage.playFromStart();
			}),
			new KeyFrame(Duration.millis(600), e ->
			{
				translateTransitionForGameNameImage.setToY(-1.0 * (gameNameWoodenImage.getLayoutY() + gameNameWoodenImage.getBoundsInParent().getHeight()));
				translateTransitionForWoodPanelFor1IconImage.setToY(-1.0 * (woodPanelFor1IconImage.getLayoutY() + woodPanelFor1IconImage.getBoundsInParent().getHeight()));
				translateTransitionForWoodPanelFor4IconsImage.setToY(-1.0 * (woodPanelFor4IconsImage.getLayoutY() + woodPanelFor4IconsImage.getBoundsInParent().getHeight()));
				
				if(vBoxForSettings.isVisible())
				{
					timelineToHideSettings.playFromStart();
					settingsIcon.setImage(SETTINGS_ICON);
				}
				else if(vBoxForSound.isVisible())
				{
					timelineToHideSoundOptions.playFromStart();
					setSoundIcon(soundIcon, false);
				}
				
				playSlideSound();
				translateTransitionForGameNameImage.playFromStart();
				translateTransitionForWoodPanelFor1IconImage.playFromStart();
				translateTransitionForWoodPanelFor4IconsImage.playFromStart();
			}),
			new KeyFrame(Duration.millis(900), e ->
			{
				if(animationsUsed == ANIMATIONS.ALL) stopGlobeAnimation();
				
				vBoxForMainButtons.setDisable(false);
				infoIcon.setDisable(false);
				editNameIcon.setDisable(false);
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
			   	
				   if(OS == OSType.Windows) translateTransitionForVBoxForSound.setToX(-1.0 * (vBoxForSound.getLayoutX() + vBoxForSound.getPrefWidth() + 20));
				   else translateTransitionForVBoxForSound.setToX(stage.getWidth() - vBoxForSound.getLayoutX() + 20);
				
				   translateTransitionForVBoxForSound.playFromStart();
			   }
			   
			   playSlideSound();
			   
			   translateTransitionForGameNameImage.setToY((-1.0 * (gameNameWoodenImage.getLayoutY() + gameNameWoodenImage.getBoundsInParent().getHeight())));
			   
			   translateTransitionForGameNameImage.playFromStart();
			   scaleTransitionForVBoxForSettings.playFromStart();
           }),
           new KeyFrame(Duration.millis(300), e -> changeLanguage()),
           new KeyFrame(Duration.millis(400), e ->
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
	
	           playSlideSound();
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
					
					if(OS == OSType.Windows) translateTransitionForVBoxForSound.setToX(-1.0 * (vBoxForSound.getLayoutX() + vBoxForSound.getPrefWidth() + 20));
					else translateTransitionForVBoxForSound.setToX(stage.getWidth() - vBoxForSound.getLayoutX() + 20);
					
					translateTransitionForVBoxForSound.playFromStart();
				}
				
				scaleTransitionForWelcomeLabel.stop();
				
				scaleTransitionForWelcomeImage.setToY(0);
				translateTransitionForWelcomeImage.setToY(-0.1759 * stage.getHeight());
				
				scaleTransitionForWelcomeLabelParallel.setToY(0);
				translateTransitionForWelcomeLabel.setToY(-0.1759 * stage.getHeight());
				
				scaleTransitionForEditIcon.setToX(0);
				scaleTransitionForEditIcon.setToY(0);
				
				playMinimizeSound();
				
				scaleTransitionForUsersEditSegmentedButton.setToX(0);
				scaleTransitionForUsersEditSegmentedButton.setToY(0);
				scaleTransitionForUsersEditSegmentedButton.playFromStart();
				
				if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == switchUserToggleButton)
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
			new KeyFrame(Duration.millis(300), e ->
			{
				usersEditSegmentedButton.setVisible(false);
				textFieldForEditUsername.setText("");
				textFieldForAddUser.setText("");
			
				playSlideSound();
				translateTransitionForGameNameImage.setToY((-1.0 * (gameNameWoodenImage.getLayoutY() + gameNameWoodenImage.getBoundsInParent().getHeight())));
				translateTransitionForGameNameImage.playFromStart();
			}),
			new KeyFrame(Duration.millis(600), e ->
			{
				changeLanguage();
				setTextAndFontForWelcomeLabel();
			}),
			new KeyFrame(Duration.millis(700), e ->
			{
				translateTransitionForGameNameImage.setToY(0);
				
				playSlideSound();
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
				infoIcon.setScaleX(0);
				infoIcon.setScaleY(0);
				settingsIcon.setScaleX(0);
				settingsIcon.setScaleY(0);
			}),
			new KeyFrame(Duration.millis(1000), e ->
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
				
				editNameIcon.setImage(PENCIL_ICON);
				scaleTransitionForEditIcon.setToX(1);
				scaleTransitionForEditIcon.setToY(1);
				
				playSlideSound();
				parallelTransitionForWelcomeImage.playFromStart();
				parallelTransitionForWelcomeLabel.playFromStart();
				timelineToPopUpMainButtons.playFromStart();
				scaleTransitionForEditIcon.playFromStart();
			}),
			new KeyFrame(Duration.millis(1300), e->
			{
				vBoxForEditUsername.setVisible(false);
				vBoxForEditUsername.setScaleX(0);
				vBoxForEditUsername.setScaleY(0);
				
				editNameIcon.setDisable(false);
				infoIcon.setDisable(false);
				settingsIcon.setDisable(false);
			})
		);

		timelineToPopUpMainButtons = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				playPopUpSound();
				sequentialTransitionForOnePlayerGameButton.playFromStart();
			}),
			new KeyFrame(Duration.millis(200), e ->
			{
				playPopUpSound();
				sequentialTransitionForTwoPlayersGameButton.playFromStart();
			}),
			new KeyFrame(Duration.millis(400), e ->
			{
				playPopUpSound();
				sequentialTransitionForAtlasButton.playFromStart();
			}),
			new KeyFrame(Duration.millis(600), e ->
			{
				playPopUpSound();
				sequentialTransitionForScoreBoardButton.playFromStart();
			}),
			new KeyFrame(Duration.millis(800), e ->
			{
				if(infoIcon.getScaleX() == 0)
				{
					playPopUpSound();
					sequentialTransitionForInfoButton.playFromStart();
				}
			}),
			new KeyFrame(Duration.millis(1000), e ->
			{
				if(settingsIcon.getScaleX() == 0)
				{
					playPopUpSound();
					sequentialTransitionForSettingsButton.playFromStart();
				}
			}));

		timelineToShowSoundOptions = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				soundIcon.setDisable(true);
				
				vBoxForSound.setVisible(true);
				translateTransitionForVBoxForSound.setToX(0);
				
				playSlideSound();
				translateTransitionForVBoxForSound.playFromStart();
			}),
			new KeyFrame(Duration.millis(300), e -> soundIcon.setDisable(false)));

		timelineToHideSoundOptions = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				soundIcon.setDisable(true);
				
				if(OS == OSType.Windows) translateTransitionForVBoxForSound.setToX(-1.0 * (vBoxForSound.getLayoutX() + vBoxForSound.getPrefWidth() + 20));
				else translateTransitionForVBoxForSound.setToX(stage.getWidth() - vBoxForSound.getLayoutX() + 20);
				
				playSlideSound();
				translateTransitionForVBoxForSound.playFromStart();
			}),
			new KeyFrame(Duration.millis(300), e ->
			{
				vBoxForSound.setVisible(false);
				
				soundIcon.setDisable(false);
			}));

		timelineToShowSettings = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					settingsIcon.setDisable(true);
					
					scaleTransitionForWelcomeLabel.setDuration(Duration.millis(300));
					scaleTransitionForWelcomeLabel.setFromX(welcomeLabel.getScaleX());
					scaleTransitionForWelcomeLabel.setFromY(welcomeLabel.getScaleY());
					scaleTransitionForWelcomeLabel.setToX(0);
					scaleTransitionForWelcomeLabel.setToY(0);
					scaleTransitionForWelcomeLabel.setAutoReverse(false);
					scaleTransitionForWelcomeLabel.setCycleCount(1);
					
					scaleTransitionForWelcomeLabel.setOnFinished(null);
					
					editNameIcon.setDisable(true);
					scaleTransitionForEditIcon.setToX(0);
					scaleTransitionForEditIcon.setToY(0);
					
					infoIcon.setDisable(true);
					scaleTransitionForInfoButton.setToX(0);
					scaleTransitionForInfoButton.setToY(0);
					
					playMinimizeSound();
					scaleTransitionForWelcomeLabel.playFromStart();
					scaleTransitionForEditIcon.playFromStart();
					scaleTransitionForInfoButton.playFromStart();
				}),
				new KeyFrame(Duration.millis(300), e ->
				{
					scaleTransitionForWelcomeImage.setToY(0);
					translateTransitionForWelcomeImage.setToY(-0.1759 * stage.getHeight());
					
					playSlideSound();
					parallelTransitionForWelcomeImage.playFromStart();
					
					translateTransitionForVBoxWithMainButtons.setToY(stage.getHeight() - vBoxForMainButtons.getLayoutY() + 20);
					translateTransitionForVBoxWithMainButtons.playFromStart();
				}),
				new KeyFrame(Duration.millis(600), e ->
				{
					vBoxForMainButtons.setOpacity(0);
					
					vBoxForSettings.setVisible(true);
					scaleTransitionForVBoxForSettings.setToX(1);
					scaleTransitionForVBoxForSettings.setToY(1);
					
					playMaximizeSound();
					scaleTransitionForVBoxForSettings.playFromStart();
				}),
				new KeyFrame(Duration.millis(900), e -> settingsIcon.setDisable(false)));

		timelineToHideSettings = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					settingsIcon.setDisable(true);
					scaleTransitionForVBoxForSettings.setToX(0);
					scaleTransitionForVBoxForSettings.setToY(0);
					
					playMinimizeSound();
					scaleTransitionForVBoxForSettings.playFromStart();
				}),
				new KeyFrame(Duration.millis(300), e ->
				{
					vBoxForSettings.setVisible(false);
					
					translateTransitionForWelcomeImage.setToY(0);
					scaleTransitionForWelcomeImage.setToY(1);
					
					playSlideSound();
					parallelTransitionForWelcomeImage.playFromStart();
					
					vBoxForMainButtons.setOpacity(1);
					translateTransitionForVBoxWithMainButtons.setToY(0);
					translateTransitionForVBoxWithMainButtons.playFromStart();
				}),
				new KeyFrame(Duration.millis(600), e ->
				{
					scaleTransitionForWelcomeLabel.setDuration(Duration.millis(300));
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
					
					playMaximizeSound();
					scaleTransitionForWelcomeLabel.playFromStart();
					scaleTransitionForEditIcon.playFromStart();
					scaleTransitionForInfoButton.playFromStart();
				}),
				new KeyFrame(Duration.millis(900), e ->
				{
					settingsIcon.setDisable(false);
					infoIcon.setDisable(false);
					editNameIcon.setDisable(false);
				}));
		
		timelineToShowEditUsersVBox = new Timeline(
			new KeyFrame(Duration.millis(0), e ->
			{
				editNameIcon.setDisable(true);
				editNameIcon.setImage(PENCIL_ICON_DISABLED);
				
				translateTransitionForVBoxWithMainButtons.setToY(stage.getHeight() - vBoxForMainButtons.getLayoutY() + 20);
				
				infoIcon.setDisable(true);
				scaleTransitionForInfoButton.setToX(0);
				scaleTransitionForInfoButton.setToY(0);
				
				settingsIcon.setDisable(true);
				scaleTransitionForSettingsIcon.setToX(0);
				scaleTransitionForSettingsIcon.setToY(0);
				
				playSlideSound();
				translateTransitionForVBoxWithMainButtons.playFromStart();
				scaleTransitionForInfoButton.playFromStart();
				scaleTransitionForSettingsIcon.playFromStart();
			}),
			new KeyFrame(Duration.millis(300), e ->
			{
				playMaximizeSound();
				
				vBoxForMainButtons.setVisible(false);
				
				usersEditSegmentedButton.setVisible(true);
				scaleTransitionForUsersEditSegmentedButton.setToX(1);
				scaleTransitionForUsersEditSegmentedButton.setToY(1);
				scaleTransitionForUsersEditSegmentedButton.playFromStart();
				
				if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == editUsernameToggleButton)
				{
					vBoxForEditUsername.setVisible(true);
					scaleTransitionForVBoxForEditUsername.setToX(1);
					scaleTransitionForVBoxForEditUsername.setToY(1);
					scaleTransitionForVBoxForEditUsername.playFromStart();
				}
				else if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == switchUserToggleButton)
				{
					vBoxForSwitchUser.setVisible(true);
					scaleTransitionForVBoxForSwitchUser.setToX(1);
					scaleTransitionForVBoxForSwitchUser.setToY(1);
					scaleTransitionForVBoxForSwitchUser.playFromStart();
				}
				else
				{
					vBoxForAddUser.setVisible(true);
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
				
				playMinimizeSound();
				
				scaleTransitionForUsersEditSegmentedButton.setToX(0);
				scaleTransitionForUsersEditSegmentedButton.setToY(0);
				scaleTransitionForUsersEditSegmentedButton.playFromStart();
				
				if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == editUsernameToggleButton)
				{
					scaleTransitionForVBoxForEditUsername.setToX(0);
					scaleTransitionForVBoxForEditUsername.setToY(0);
					scaleTransitionForVBoxForEditUsername.playFromStart();
				}
				else if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == switchUserToggleButton)
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
			new KeyFrame(Duration.millis(550), e ->
			{
				usersEditSegmentedButton.setVisible(false);
				vBoxForEditUsername.setVisible(false);
				vBoxForSwitchUser.setVisible(false);
				vBoxForAddUser.setVisible(false);
				
				textFieldForEditUsername.setText("");
				textFieldForAddUser.setText("");
				
				playSlideSound();
				vBoxForMainButtons.setVisible(true);
				translateTransitionForVBoxWithMainButtons.setToY(0);
				translateTransitionForVBoxWithMainButtons.playFromStart();
				
				editNameIcon.setImage(PENCIL_ICON);
				editNameIcon.setDisable(false);
				
				infoIcon.setDisable(false);
				settingsIcon.setDisable(false);
			}));
		
		timelineToChangeUsersEditVBoxes = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					playMinimizeSound();
					if(vBoxForEditUsername.isVisible())
					{
						scaleTransitionForVBoxForEditUsername.setToX(0);
						scaleTransitionForVBoxForEditUsername.setToY(0);
						scaleTransitionForVBoxForEditUsername.playFromStart();
					}
					else if(vBoxForSwitchUser.isVisible())
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
				new KeyFrame(Duration.millis(350), e ->
				{
					if(vBoxForEditUsername.isVisible()) vBoxForEditUsername.setVisible(false);
					else if(vBoxForSwitchUser.isVisible()) vBoxForSwitchUser.setVisible(false);
					else vBoxForAddUser.setVisible(false);
					
					playMaximizeSound();
					if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == editUsernameToggleButton)
					{
						vBoxForEditUsername.setVisible(true);
						scaleTransitionForVBoxForEditUsername.setToX(1);
						scaleTransitionForVBoxForEditUsername.setToY(1);
						scaleTransitionForVBoxForEditUsername.playFromStart();
					}
					else if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == switchUserToggleButton)
					{
						vBoxForSwitchUser.setVisible(true);
						scaleTransitionForVBoxForSwitchUser.setToX(1);
						scaleTransitionForVBoxForSwitchUser.setToY(1);
						scaleTransitionForVBoxForSwitchUser.playFromStart();
					}
					else
					{
						vBoxForAddUser.setVisible(true);
						scaleTransitionForVBoxForAddUser.setToX(1);
						scaleTransitionForVBoxForAddUser.setToY(1);
						scaleTransitionForVBoxForAddUser.playFromStart();
					}
				}));

		timelineToShowInformationAboutGame = new Timeline(
			 new KeyFrame(Duration.millis(0), e ->
			 {
				 infoIcon.setDisable(true);
				 
				 scaleTransitionForWelcomeLabel.setDuration(Duration.millis(300));
				 scaleTransitionForWelcomeLabel.setFromX(welcomeLabel.getScaleX());
				 scaleTransitionForWelcomeLabel.setFromY(welcomeLabel.getScaleY());
				 scaleTransitionForWelcomeLabel.setToX(0);
				 scaleTransitionForWelcomeLabel.setToY(0);
				 scaleTransitionForWelcomeLabel.setAutoReverse(false);
				 scaleTransitionForWelcomeLabel.setCycleCount(1);
				
				 scaleTransitionForWelcomeLabel.setOnFinished(ev -> {});
				 
				 editNameIcon.setDisable(true);
				 scaleTransitionForEditIcon.setToX(0);
				 scaleTransitionForEditIcon.setToY(0);
				 
				 settingsIcon.setDisable(true);
				 scaleTransitionForSettingsIcon.setToX(0);
				 scaleTransitionForSettingsIcon.setToY(0);
				 
				 playMinimizeSound();
				 scaleTransitionForWelcomeLabel.playFromStart();
				 scaleTransitionForEditIcon.playFromStart();
				 scaleTransitionForSettingsIcon.playFromStart();
			 }),
			 new KeyFrame(Duration.millis(300), e ->
			 {
			 	 scaleTransitionForWelcomeImage.setToY(0);
				 translateTransitionForWelcomeImage.setToY(-0.1759 * stage.getHeight());
				
				 playSlideSound();
				 parallelTransitionForWelcomeImage.playFromStart();
				 
				 translateTransitionForVBoxWithMainButtons.setToY(stage.getHeight() - vBoxForMainButtons.getLayoutY() + 20);
				 translateTransitionForVBoxWithMainButtons.playFromStart();
			 }),
			 new KeyFrame(Duration.millis(600), e ->
			 {
				 rectangleForInformationAboutGame.setVisible(true);
				 scaleTransitionForRectangleForInfoAboutGame.setToX(1);
				 scaleTransitionForRectangleForInfoAboutGame.setToY(1);
				 
				 playMaximizeSound();
				 scaleTransitionForRectangleForInfoAboutGame.playFromStart();
			 }),
			 new KeyFrame(Duration.millis(900), e -> infoIcon.setDisable(false))
		);

		timelineToHideInformationAboutGame = new Timeline(
             new KeyFrame(Duration.millis(0), e ->
             {
	             infoIcon.setDisable(true);
	             scaleTransitionForRectangleForInfoAboutGame.setToX(0);
	             scaleTransitionForRectangleForInfoAboutGame.setToY(0);
	             
	             playMinimizeSound();
		         scaleTransitionForRectangleForInfoAboutGame.playFromStart();
             }),
             new KeyFrame(Duration.millis(300), e ->
             {
             	rectangleForInformationAboutGame.setVisible(false);
             	
	             translateTransitionForWelcomeImage.setToY(0);
	             scaleTransitionForWelcomeImage.setToY(1);
	
	             playSlideSound();
				 parallelTransitionForWelcomeImage.playFromStart();
				 
				 vBoxForMainButtons.setOpacity(1);
				 translateTransitionForVBoxWithMainButtons.setToY(0);
				 translateTransitionForVBoxWithMainButtons.playFromStart();
             }),
			 new KeyFrame(Duration.millis(600), e ->
			 {
				 scaleTransitionForWelcomeLabel.setDuration(Duration.millis(300));
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
				 
				 playMaximizeSound();
				 scaleTransitionForWelcomeLabel.playFromStart();
				 scaleTransitionForEditIcon.playFromStart();
				 scaleTransitionForSettingsIcon.playFromStart();
			 }),
			 new KeyFrame(Duration.millis(900), e ->
			 {
			 	infoIcon.setDisable(false);
			 	editNameIcon.setDisable(false);
			 	settingsIcon.setDisable(false);
			 })
		);
	}

	protected void setupAdvancedAnimations()
	{
		leftGlobeStatus = 1;
		rightGlobeStatus = -1;
		previousTransitionOfLeftGlobeWasRight = false;
		previousTransitionOfRightGlobeWasRight = false;
		
		timelineForLeftGlobe = new Timeline(
				new KeyFrame(Duration.millis(50), e ->
				{
					if (leftGlobeStatus == 1)
					{
						leftGlobeCounter--;
						if (leftGlobeCounter < 1) leftGlobeCounter = 80;
					}
					else if (leftGlobeStatus == -1)
					{
						leftGlobeCounter++;
						if (leftGlobeCounter > 80) leftGlobeCounter = 1;
					}
					else timelineForLeftGlobe.stop();
					leftGlobeImage.setImage(animatedGlobe[leftGlobeCounter - 1]);
				}));
		timelineForLeftGlobe.setCycleCount(Timeline.INDEFINITE);
		
		timelineForRightGlobe = new Timeline(
				new KeyFrame(Duration.millis(50), e ->
				{
					if (rightGlobeStatus == 1)
					{
						rightGlobeCounter--;
						if (rightGlobeCounter < 1) rightGlobeCounter = 80;
					}
					else if (rightGlobeStatus == -1)
					{
						rightGlobeCounter++;
						if (rightGlobeCounter > 80) rightGlobeCounter = 1;
					}
					else timelineForRightGlobe.stop();
					rightGlobeImage.setImage(animatedGlobe[rightGlobeCounter - 1]);
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
		startInFullScreenCheckBox.setSelected(startAtFullScreen);
		
		if(animationsUsed == ANIMATIONS.NO) noAnimationsToggleButton.setSelected(true);
		else if(animationsUsed == ANIMATIONS.LIMITED) limitedAnimationsToggleButton.setSelected(true);
		else allAnimationsToggleButton.setSelected(true);
		
		if(getCurrentPlayer().getLocaleIndex() != -1) selectCountryInComboBoxBasedOnLocale();
		
		if(getCurrentLanguage() == LANGUAGE.GREEK) greekLanguageButton.fire();
		else englishLanguageButton.fire();
		
		if(getCurrentPlayer().getUnitSystem() == UNIT_SYSTEM.METRIC) metricSystemRadioButton.setSelected(true);
		else imperialSystemRadioButton.setSelected(true);
	}
	
	private void editUsername(String newUsername)
	{
		getCurrentPlayer().setOriginalName(newUsername);
		getCurrentPlayer().setEditedName(getEditedOriginalName(newUsername));
		
		usersObservableList.set(0, newUsername);
		
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
		stage.setX(primaryScreenWidth / 2.0 - windowWidth / 2.0);
		stage.setY(primaryScreenHeight / 2.0 - windowHeight / 2.0);
		stage.setWidth(windowWidth);
		stage.setHeight(windowHeight);
		recalculateBackground(windowWidth, windowHeight);
		recalculateUI(windowWidth, windowHeight);
	}
	
	private void changeCurrentPlayer(int index)
	{
		LANGUAGE prevLan = getCurrentLanguage();
		
		String username = usersObservableList.get(index);
		usersObservableList.remove(index);
		usersObservableList.add(0, username);
		
		Player tempPlayer = playersArrayList.remove(index);
		playersArrayList.add(0, tempPlayer);
		FilesIO.writePlayersFile();
		
		PowerOn.setSettingsBasedOnCurrentPlayer();
		
		updateStageWithNewWidth();
		
		setUIToggleValuesBasedOnSettings();
		
		if(animationsUsed != ANIMATIONS.NO)
		{
			if(prevLan != getCurrentLanguage()) timelineToChangeLanguageByPlayer.playFromStart();
			else
			{
				timelineToHideEditUsersVBox.playFromStart();
				
				hideAndShowWelcomeLabelWithNewUsernameAnimation();
			}
		}
		else
		{
			if(prevLan != getCurrentLanguage()) changeLanguage();
			
			hideEditUsersBoxesNoAnimations();
			setTextAndFontForWelcomeLabel();
		}
	}
	
	private void addUser(String username)
	{
		LANGUAGE prevLan = getCurrentLanguage();
		
		playersArrayList.add(0, new Player(username, getEditedOriginalName(username)));
		FilesIO.writePlayersFile();
		
		usersObservableList.add(0, username);
		
		PowerOn.setSettingsBasedOnCurrentPlayer();
		
		updateStageWithNewWidth();
		
		setUIToggleValuesBasedOnSettings();
		
		if(animationsUsed != ANIMATIONS.NO)
		{
			if(prevLan != getCurrentLanguage()) timelineToChangeLanguageByPlayer.playFromStart();
			else
			{
				timelineToHideEditUsersVBox.playFromStart();
				
				hideAndShowWelcomeLabelWithNewUsernameAnimation();
			}
		}
		else
		{
			if(prevLan != getCurrentLanguage()) changeLanguage();
			
			hideEditUsersBoxesNoAnimations();
			setTextAndFontForWelcomeLabel();
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
		soundOptionsToolTip.setText(languageResourceBundle.getString("soundOptionsTooltip"));
		settingsTooltip.setText(languageResourceBundle.getString("settingsTooltip"));

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
		editNameTooltip.setText(languageResourceBundle.getString("editNameTooltip"));
		infoIconTooltip.setText(languageResourceBundle.getString("infoIconTooltip"));
		minimizeTooltip.setText(languageResourceBundle.getString("minimizeTooltip"));

		if(fullScreenMode)
		{
			moveTooltip.setText(languageResourceBundle.getString("moveDisabledTooltip"));
			fullScreenTooltip.setText(languageResourceBundle.getString("exitFullScreenTooltip"));
		}
		else
		{
			moveTooltip.setText(languageResourceBundle.getString("moveTooltip"));
			fullScreenTooltip.setText(languageResourceBundle.getString("enterFullScreenTooltip"));
		}

		exitTooltip.setText(languageResourceBundle.getString("exitTooltip"));
		singlePlayerGameButton.setText(languageResourceBundle.getString("onePlayerGameButton"));
		singlePlayerGameButton.getTooltip().setText(languageResourceBundle.getString("onePlayerGameButtonTooltip"));
		multiPlayerGameButton.setText(languageResourceBundle.getString("twoPlayersGameButton"));
		multiPlayerGameButton.getTooltip().setText(languageResourceBundle.getString("twoPlayersGameButtonTooltip"));
		atlasButton.setText(languageResourceBundle.getString("atlasButton"));
		atlasButton.getTooltip().setText(languageResourceBundle.getString("atlasButtonTooltip"));
		scoreBoardButton.setText(languageResourceBundle.getString("scoreBoardButton"));
		scoreBoardButton.getTooltip().setText(languageResourceBundle.getString("scoreBoardButtonTooltip"));
		editUsernameToggleButton.setText(languageResourceBundle.getString("editUsernameLabel"));
		switchUserToggleButton.setText(languageResourceBundle.getString("switchUserLabel"));
		addUserToggleButton.setText(languageResourceBundle.getString("addUserLabel"));
		editUsernameLabel.setText(languageResourceBundle.getString("editUsernameLabel"));
		switchUserLabel.setText(languageResourceBundle.getString("switchUserLabel"));
		addUserLabel.setText(languageResourceBundle.getString("addUserLabel"));
		textFieldForEditUsername.setPromptText(languageResourceBundle.getString("textFieldForEditUsernamePromptText"));
		textFieldForEditUsername.getTooltip().setText(languageResourceBundle.getString("textFieldEmpty"));
		textFieldForAddUser.setPromptText(languageResourceBundle.getString("textFieldForAddUserPromptText"));
		textFieldForAddUser.getTooltip().setText(languageResourceBundle.getString("textFieldEmpty"));
		cancelEditUsernameButton.setText(languageResourceBundle.getString("cancel"));
		cancelEditUsernameButton.getTooltip().setText(languageResourceBundle.getString("cancelEditUsernameButtonTooltip"));
		cancelSwitchUserButton.setText(languageResourceBundle.getString("cancel"));
		cancelSwitchUserButton.getTooltip().setText(languageResourceBundle.getString("cancelSwitchUserButtonTooltip"));
		cancelAddUserButton.setText(languageResourceBundle.getString("cancel"));
		cancelAddUserButton.getTooltip().setText(languageResourceBundle.getString("cancelAddUserButtonTooltip"));
		confirmEditUsernameButton.setText(languageResourceBundle.getString("change"));
		confirmEditUsernameButton.getTooltip().setText(languageResourceBundle.getString("confirmEditUsernameButtonTooltip"));
		confirmSwitchUserButton.setText(languageResourceBundle.getString("switch"));
		confirmSwitchUserButton.getTooltip().setText(languageResourceBundle.getString("confirmSwitchUserButtonTooltip"));
		confirmAddUserButton.setText(languageResourceBundle.getString("add"));
		confirmAddUserButton.getTooltip().setText(languageResourceBundle.getString("confirmAddUserButtonTooltip"));
		greekLanguageButton.setText(languageResourceBundle.getString("greekLanguageLabel"));
		greekLanguageButton.getTooltip().setText(languageResourceBundle.getString("greekLanguageTooltip"));
		englishLanguageButton.setText(languageResourceBundle.getString("englishLanguageLabel"));
		englishLanguageButton.getTooltip().setText(languageResourceBundle.getString("englishLanguageTooltip"));
		deleteAllDataButton.setText(languageResourceBundle.getString("deleteAllDataLabel"));
		deleteAllDataButton.getTooltip().setText(languageResourceBundle.getString("deleteAllDataTooltip"));
		deleteSingleUserLabel.setText(languageResourceBundle.getString("deleteSingleUserLabel"));
		deleteSingleUserLabel.getTooltip().setText(languageResourceBundle.getString("deleteSingleUserUnableToDeleteMessage"));
		deleteAllUsersLabel.setText(languageResourceBundle.getString("deleteAllLabel"));
		deleteAllUsersLabel.getTooltip().setText(languageResourceBundle.getString("deleteAllUsersTooltip"));
		cancelPopUpActionButton.setText(languageResourceBundle.getString("cancel"));
		confirmDeleteCheckBox.setText(languageResourceBundle.getString("confirmDeleteCheckBox"));
	}
	
	private void changeLanguage()
	{
		PowerOn.loadLanguageResourceBundle(false);
		
		updateStrings();
		
		if (getCurrentLanguage() == LANGUAGE.GREEK)
			GAME_NAME_IMAGE = new Image(WelcomeScreen.class.getResource("/images/backgrounds/gameNameGreek.png").toExternalForm(), 0.7 * primaryScreenWidth, 0, true, false);
		else
			GAME_NAME_IMAGE  = new Image(WelcomeScreen.class.getResource("/images/backgrounds/gameNameEnglish.png").toExternalForm(), 0.7 * primaryScreenWidth, 0, true, false);
		
		gameNameWoodenImage.setImage(GAME_NAME_IMAGE);
		
		Thread t1 = new Thread(() ->
		{
			FilesIO.readCountriesXMLDataFile();
			FilesIO.readContinentsXMLDataFile();
		});

		Thread t2 = new Thread(() ->
		{
			FilesIO.readStatesOfUSAXMLDataFile();
			FilesIO.readGreekDecentralizedAdministrationsXMLDataFile();
			FilesIO.readGreekRegionsXMLDataFile();
			FilesIO.readGreekRegionalUnitsXMLDataFile();
			FilesIO.readAttractionsXMLDataFile();
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
			if(vBoxForEditUsername.isVisible())
			{
				vBoxForEditUsername.setVisible(false);
				vBoxForEditUsername.setScaleX(0);
				vBoxForEditUsername.setScaleY(0);
			}
			else if(vBoxForSwitchUser.isVisible())
			{
				vBoxForSwitchUser.setVisible(false);
				vBoxForSwitchUser.setScaleX(0);
				vBoxForSwitchUser.setScaleY(0);
			}
			else
			{
				vBoxForAddUser.setVisible(false);
				vBoxForAddUser.setScaleX(0);
				vBoxForAddUser.setScaleY(0);
			}
			
			if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == editUsernameToggleButton)
			{
				vBoxForEditUsername.setScaleX(1);
				vBoxForEditUsername.setScaleY(1);
				vBoxForEditUsername.setVisible(true);
			}
			else if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == switchUserToggleButton)
			{
				vBoxForSwitchUser.setScaleX(1);
				vBoxForSwitchUser.setScaleY(1);
				vBoxForSwitchUser.setVisible(true);
			}
			else
			{
				vBoxForAddUser.setScaleX(1);
				vBoxForAddUser.setScaleY(1);
				vBoxForAddUser.setVisible(true);
			}
		}
	}
	
	private void hideEditUsersBoxesNoAnimations()
	{
		editNameIcon.setImage(PENCIL_ICON);
		
		usersEditSegmentedButton.setVisible(false);
		usersEditSegmentedButton.setScaleX(0);
		usersEditSegmentedButton.setScaleY(0);
		
		if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == editUsernameToggleButton)
		{
			vBoxForEditUsername.setVisible(false);
			vBoxForEditUsername.setScaleX(0);
			vBoxForEditUsername.setScaleY(0);
		}
		else if(toggleGroupForUsersEditSegmentedButton.getSelectedToggle() == switchUserToggleButton)
		{
			vBoxForSwitchUser.setVisible(false);
			vBoxForSwitchUser.setScaleX(0);
			vBoxForSwitchUser.setScaleY(0);
		}
		else
		{
			vBoxForAddUser.setVisible(false);
			vBoxForAddUser.setScaleX(0);
			vBoxForAddUser.setScaleY(0);
		}
		
		textFieldForEditUsername.setText("");
		textFieldForAddUser.setText("");
		
		if(scaleTransitionForWelcomeLabel != null) scaleTransitionForWelcomeLabel.stop();
		welcomeLabel.setScaleX(1);
		welcomeLabel.setScaleY(1);
		
		vBoxForMainButtons.setTranslateY(0);
		vBoxForMainButtons.setVisible(true);
		
		editNameIcon.setDisable(false);
		
		infoIcon.setScaleX(1);
		infoIcon.setScaleY(1);
		infoIcon.setDisable(false);
		
		settingsIcon.setScaleX(1);
		settingsIcon.setScaleY(1);
		settingsIcon.setDisable(false);
	}
	
	private void hideAndShowWelcomeLabelWithNewUsernameAnimation()
	{
		scaleTransitionForWelcomeLabel.setDuration(Duration.millis(300));
		scaleTransitionForWelcomeLabel.setFromX(welcomeLabel.getScaleX());
		scaleTransitionForWelcomeLabel.setFromY(welcomeLabel.getScaleY());
		scaleTransitionForWelcomeLabel.setToX(0);
		scaleTransitionForWelcomeLabel.setToY(0);
		scaleTransitionForWelcomeLabel.setAutoReverse(false);
		scaleTransitionForWelcomeLabel.setCycleCount(1);
		
		scaleTransitionForWelcomeLabel.setOnFinished(e ->
		{
			setTextAndFontForWelcomeLabel();
			
			scaleTransitionForWelcomeLabel.setDuration(Duration.millis(300));
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
						case "Former Yugoslav Republic of Macedonia":s = "Former Yugoslav\nRepublic of Macedonia";break;
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
						case "Πρώην Γιουγκοσλαβική Δημοκρατία της Μακεδονίας":s = "Πρώην Γιουγκοσλαβική\nΔημοκρατία της Μακεδονίας";break;
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
		usersObservableList = FXCollections.observableArrayList();
		for(Player player : playersArrayList) usersObservableList.add(player.getOriginalName());
		usersComboBox.setItems(usersObservableList);
		usersComboBox.getSelectionModel().select(0);
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
					
					playMaximizeSound();
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
					
					playMinimizeSound();
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
				removeBlurEffect();
				enableUI();
				
				vBoxForPopUpAskConfirmation.setVisible(false);
				
				hBoxForPopUpConfirmationButtons.setDisable(false);
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
					
					playMinimizeSound();
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
					
					playMaximizeSound();
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
					
					playMinimizeSound();
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
}
