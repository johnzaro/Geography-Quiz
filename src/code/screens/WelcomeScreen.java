package code.screens;

import code.core.*;
import code.core.OsCheck.OSType;
import code.dataStructures.Player;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

import static code.core.GlobalVariables.*;

public class WelcomeScreen extends CoreScreen
{
	private ObservableList<String> originalNamesObservableList, countriesLocalesObservableList;
	private ObservableList<Short> countriesLocalesSortList;
	
	private HBox hBoxFor4SettingsButtons, hBoxForEditUsernameButtons, hBoxForSwitchUserButtons, hBoxForAddUserButtons, hBoxForDeleteSavedNames,
			hBoxForLanguageSelection, hBoxForLanguagesButtons, hBoxForUnitOfLengthSelection, hBoxForUnitOfLengthRadioButtons,
			hBoxForSettingsAndInfoIcons, hBoxForCountrySelection;
	private VBox vBoxForSettings, vBoxForAnimationsUsedSettings, vBoxForMainButtons, vBoxForEditUsername, vBoxForSwitchUser, vBoxForAddUser;
	private Label welcomeLabel, animationsUsedLabel, popUpMessage,
			deleteSingleNameLabel, deleteAllNamesLabel, selectCountryLabel, selectLanguageLabel, selectUnitSystemLabel;
	private RadioButton metricSystemRadioButton, imperialSystemRadioButton;
	private TextField textFieldForEditUsername, textFieldForAddUser;
	private ImageView chalkboardBackgroundImage, gameNameWoodenImage, welcomeImage,
			leftGlobeStand, rightGlobeStand, leftGlobeImage, rightGlobeImage, woodPanelFor4IconsImage,
			woodPanelFor1IconImage, greekLanguageFlag, englishLanguageFlag;
	private ImageView settingsIcon, editNameIcon, singlePlayerIcon, multiPlayerIcon, atlasIcon, scoresIcon, infoIcon;
	private Button singlePlayerGameButton, multiPlayerGameButton, atlasButton, scoreBoardButton, greekLanguageLabel, englishLanguageLabel,
			cancelEditUsernameButton, cancelSwitchUserButton, cancelAddUserButton, confirmEditUsernameButton, confirmSwitchUserButton, confirmAddUserButton, deleteAllDataButton;
	private CustomTooltip settingsTooltip, editNameTooltip, infoIconTooltip;
	private CustomCheckBox startInFullScreenCheckBox;
	private ComboBox<String> usersComboBox, countriesComboBox;
	private Rectangle rectangleForInformationAboutGame;
	private ToggleButton noAnimationsToggleButton, limitedAnimationsToggleButton, allAnimationsToggleButton, editUsernameToggleButton, switchUserToggleButton, addUserToggleButton;
	private SegmentedButton animationsUsedSegmentedButton, usersEditSegmentedButton;
	private ToggleGroup toggleGroupForAnimationsUsedSegmentedButton, toggleGroupForUsersEditSegmentedButton, toggleGroupForUnitsOfLength;

	private ScaleTransition scaleTransitionForInfoButton, scaleTransitionForSoundIcon, scaleTransitionFor4Settings,
			scaleTransitionForWelcomeLabel, scaleTransitionForEditIcon, scaleTransitionForRectangleForInfoAboutGame, scaleTransitionForVBoxForSettings, scaleTransitionForSettingsIcon,
			scaleTransitionMessageAllDataDeleted, scaleTransitionForVBoxForEditUsername, scaleTransitionForVBoxForSwitchUser, scaleTransitionForVBoxForAddUser,
			scaleTransitionForUsersEditSegmentedButton;
	private SequentialTransition sequentialTransitionForOnePlayerGameButton, sequentialTransitionForTwoPlayersGameButton, sequentialTransitionForAtlasButton,
			sequentialTransitionForScoreBoardButton, sequentialTransitionForInfoButton, sequentialTransitionForSettingsButton;
	private TranslateTransition translateTransitionForVBoxWithMainButtons, translateTransitionForVBoxForSound,
			translateTransitionForWoodPanelFor1IconImage, translateTransitionForWoodPanelFor4IconsImage, translateTransitionForGameNameImage,
			translateTransitionForLeftGlobeStand, translateTransitionForLeftGlobeImage, translateTransitionForRightGlobeStand, translateTransitionForRightGlobeImage;
	private ParallelTransition parallelTransitionForWelcomeImage, parallelTransitionForWelcomeLabel;
	private FadeTransition fadeInTransition;
	private Timeline timelineForLeftGlobe, timelineForRightGlobe, timelineToPopUpMainButtons,
			timelineToHideMainButtonsVBoxAndShowEditUsersVBox, timelineToHideEditUsersVBoxAndShowMainButtonsVBox,
			timelineToShowSoundOptions, timelineToHideSoundOptions,
			timelineToShowSettings, timelineToHideSettings, timelineToChangeUsersEditVBoxes,
			timelineToShowInformationAboutGame, timelineToHideInformationAboutGame,
			timelineToChangeLanguage, timelineToChangeLanguageByPlayer, timelineToShowAllStuff, timelineToHideAllStuff, timelineToShowPopUpMessage;

	private InnerShadow welcomeLabelInnerShadow;
	private DropShadow leftEarthShadow, rightEarthShadow, dropShadow;

	private byte leftGlobeStatus, rightGlobeStatus, leftGlobeCounter = 1, rightGlobeCounter = 1;
	private boolean previousTransitionOfLeftGlobeWasRight, previousTransitionOfRightGlobeWasRight;

	protected void recalculateUI(double width, double height)
	{
		super.recalculateUI(width, height);
		
		hBoxFor4SettingsButtons.setPrefSize(0.1276 * width, 0.0787 * height);

		if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_9)
		{
			leftGlobeImage.setFitWidth(0.1614 * width);
			rightGlobeImage.setFitWidth(0.1614 * width);
			
			leftGlobeStand.setFitWidth(0.1578 * width);
			leftGlobeStand.setLayoutX(0.0334 * width);
			leftGlobeStand.setLayoutY(0.5213 * height);
			
			rightGlobeStand.setFitWidth(0.1578 * width);
			rightGlobeStand.setLayoutX(width - leftGlobeStand.getLayoutX() - rightGlobeStand.getFitWidth());
			rightGlobeStand.setLayoutY(0.5213 * height);

			gameNameWoodenImage.setLayoutY(0.0305 * height);
			welcomeImage.setLayoutY(0.2540 * height);
			
			welcomeLabel.setLayoutY(0.2735 * height);

			leftGlobeImage.setLayoutX(0.0495 * width);
			leftGlobeImage.setLayoutY(0.5480 * height);
			rightGlobeImage.setLayoutX(0.7885 * width);
			rightGlobeImage.setLayoutY(0.5480 * height);

			woodPanelFor1IconImage.setLayoutY(0.0574 * height);
			soundIcon.setLayoutY(0.1140 * height);

			vBoxForSound.setLayoutY(0.2000 * height);
			
			vBoxForSound.setPrefHeight(0.1759 * height);
			
			editNameIcon.setLayoutY(0.3250 * height);

			hBoxForSettingsAndInfoIcons.setLayoutX(0.7296 * width);
			hBoxForSettingsAndInfoIcons.setLayoutY(0.8287 * height);

			woodPanelFor4IconsImage.setLayoutY(0.0574 * height);
			hBoxFor4SettingsButtons.setLayoutY(0.1000 * height);

			vBoxForMainButtons.setPrefWidth(0.3906 * width);
			vBoxForMainButtons.setLayoutY(0.4490 * height);
			vBoxForMainButtons.setPrefHeight(0.4630 * height);
			vBoxForMainButtons.setSpacing(0.0185 * height);

			vBoxForEditUsername.setLayoutY(0.5185 * height);
			vBoxForSwitchUser.setLayoutY(0.5185 * height);
			vBoxForAddUser.setLayoutY(0.5185 * height);
			
			usersEditSegmentedButton.setLayoutY(0.4352 * height);
			
			rectangleForInformationAboutGame.setLayoutY(0.2963 * height);

			if (OS == OSType.Windows)
			{
				woodPanelFor1IconImage.setLayoutX(0.0667 * width);
				woodPanelFor4IconsImage.setLayoutX(0.8214 * width);
				
				vBoxForSound.setLayoutX(0.0420 * width);
			}
			else
			{
				woodPanelFor1IconImage.setLayoutX(0.8665 * width);
				woodPanelFor4IconsImage.setLayoutX(0.0427 * width);
				
				vBoxForSound.setLayoutX(0.8230 * width);
			}
		}
		else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_10)
		{
			leftGlobeImage.setFitWidth(0.1625 * width);
			rightGlobeImage.setFitWidth(0.1625 * width);
			
			leftGlobeStand.setFitWidth(0.1577 * width);
			leftGlobeStand.setLayoutX(0.0335 * width);
			leftGlobeStand.setLayoutY(0.5638 * height);
			
			rightGlobeStand.setFitWidth(0.1577 * width);
			rightGlobeStand.setLayoutX(width - leftGlobeStand.getLayoutX() - rightGlobeStand.getFitWidth());
			rightGlobeStand.setLayoutY(0.5638 * height);

			gameNameWoodenImage.setLayoutY(0.0306 * height);
			welcomeImage.setLayoutY(0.2286 * height);
			
			welcomeLabel.setLayoutY(0.2476 * height);

			leftGlobeImage.setLayoutX(0.0506 * width);
			leftGlobeImage.setLayoutY(0.5857 * height);
			rightGlobeImage.setLayoutX(0.7875 * width);
			rightGlobeImage.setLayoutY(0.5857 * height);

			woodPanelFor1IconImage.setLayoutY(0.0552 * height);
			soundIcon.setLayoutY(0.1050 * height);

			vBoxForSound.setLayoutY(0.1824 * height);
			
			vBoxForSound.setPrefHeight(0.1714 * height);

			editNameIcon.setLayoutY(0.2915 * height);
			
			hBoxForSettingsAndInfoIcons.setLayoutX(0.7296 * width);
			hBoxForSettingsAndInfoIcons.setLayoutY(0.8287 * height);

			woodPanelFor4IconsImage.setLayoutY(0.0552 * height);
			hBoxFor4SettingsButtons.setLayoutY(0.0885 * height);

			vBoxForMainButtons.setPrefWidth(0.4167 * width);
			vBoxForMainButtons.setLayoutY(0.4238 * height);
			vBoxForMainButtons.setPrefHeight(0.4630 * height);
			vBoxForMainButtons.setSpacing(0.0185 * height);

			vBoxForEditUsername.setLayoutY(0.4429 * height);
			
			rectangleForInformationAboutGame.setLayoutY(0.2857 * height);

			if (OS == OSType.Windows)
			{
				woodPanelFor1IconImage.setLayoutX(0.0667 * width);
				woodPanelFor4IconsImage.setLayoutX(0.8232 * width);
				
				vBoxForSound.setLayoutX(0.0421 * width);
			}
			else
			{
				woodPanelFor1IconImage.setLayoutX(0.8454 * width);
				woodPanelFor4IconsImage.setLayoutX(0.0429 * width);
				
				vBoxForSound.setLayoutX(0.8226 * width);
			}
		}
		else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_25_16
				|| getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_3_2)
		{
			if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_25_16)
			{
				woodPanelFor1IconImage.setLayoutY(0.0556 * height);
				soundIcon.setLayoutY(0.0898 * height);
				
				woodPanelFor4IconsImage.setLayoutY(0.0557 * height);
				hBoxFor4SettingsButtons.setLayoutY(0.0879 * height);
				
				leftGlobeStand.setLayoutY(0.5723 * height);
				rightGlobeStand.setLayoutY(0.5723 * height);
				
				leftGlobeImage.setLayoutY(0.5928 * height);
				rightGlobeImage.setLayoutY(0.5928 * height);
				
				welcomeImage.setLayoutY(0.2266 * height);
				welcomeLabel.setLayoutY(0.2393 * height);
				
				editNameIcon.setLayoutY(0.2881 * height);
			}
			else
			{
				woodPanelFor1IconImage.setLayoutY(0.0541 * height);
				soundIcon.setLayoutY(0.0836 * height);
				
				woodPanelFor4IconsImage.setLayoutY(0.0541 * height);
				hBoxFor4SettingsButtons.setLayoutY(0.0840 * height);
				
				leftGlobeStand.setLayoutY(0.5877 * height);
				rightGlobeStand.setLayoutY(0.5877 * height);
				
				leftGlobeImage.setLayoutY(0.6082 * height);
				rightGlobeImage.setLayoutY(0.6082 * height);
				
				welcomeImage.setLayoutY(0.2189 * height);
				welcomeLabel.setLayoutY(0.2316 * height);
				
				editNameIcon.setLayoutY(0.2777 * height);
			}
			
			hBoxForSettingsAndInfoIcons.setLayoutX(0.7296 * width);
			hBoxForSettingsAndInfoIcons.setLayoutY(0.8287 * height);
			
			leftGlobeImage.setFitWidth(0.1625 * width);
			rightGlobeImage.setFitWidth(0.1625 * width);
			
			leftGlobeStand.setFitWidth(0.1575 * width);
			leftGlobeStand.setLayoutX(0.0331 * width);
			
			rightGlobeStand.setFitWidth(0.1575 * width);
			rightGlobeStand.setLayoutX(width - leftGlobeStand.getLayoutX() - rightGlobeStand.getFitWidth());

			gameNameWoodenImage.setLayoutY(0.0306 * height);
			
			leftGlobeImage.setLayoutX(0.0506 * width);
			rightGlobeImage.setLayoutX(0.7875 * width);

			vBoxForSound.setLayoutY(0.1787 * height);
//			vBoxForSettings.setLayoutY(0.1787 * height);
			
			vBoxForSound.setPrefHeight(0.1714 * height);
//			vBoxForSettings.setPrefHeight(0.2600 * height);

			infoIcon.setLayoutX(0.7604 * width);
			infoIcon.setLayoutY(0.8048 * height);

			vBoxForMainButtons.setPrefWidth(0.4395 * width);
			vBoxForMainButtons.setLayoutY(0.4297 * height);
			vBoxForMainButtons.setPrefHeight(0.4492 * height);
			vBoxForMainButtons.setSpacing(0.0185 * height);

			vBoxForEditUsername.setLayoutY(0.4329 * height);
			
			rectangleForInformationAboutGame.setLayoutY(0.2832 * height);

			if (OS == OSType.Windows)
			{
				woodPanelFor1IconImage.setLayoutX(0.0663 * width);
				woodPanelFor4IconsImage.setLayoutX(0.8219 * width);
				
				vBoxForSound.setLayoutX(0.0431 * width);
//				vBoxForSettings.setLayoutX(0.0431 * width);
			}
			else
			{
				woodPanelFor1IconImage.setLayoutX(0.8454 * width);
				woodPanelFor4IconsImage.setLayoutX(0.0419 * width);
				
				vBoxForSound.setLayoutX(0.8220 * width);
//				vBoxForSettings.setLayoutX(0.8220 * width);
			}
		}
		else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_4_3
				|| getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_5_4)
		{
			leftGlobeImage.setFitWidth(0.1757 * width);
			rightGlobeImage.setFitWidth(0.1757 * width);

			if (getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_5_4)
			{
				gameNameWoodenImage.setLayoutY(0.0225 * height);
				welcomeImage.setLayoutY(0.1797 * height);
				
				welcomeLabel.setLayoutY(0.1806 * height);
				
				hBoxFor4SettingsButtons.setLayoutY(0.0615 * height);
				soundIcon.setLayoutY(0.0820 * height);
				
				vBoxForSound.setLayoutY(0.1416 * height);
			
				leftGlobeImage.setLayoutY(0.6573 * height);
				rightGlobeImage.setLayoutY(0.6573 * height);
				
				leftGlobeStand.setLayoutY(0.6367 * height);
				rightGlobeStand.setLayoutY(0.6367 * height);
				
				editNameIcon.setLayoutY(0.2295 * height);
				
				hBoxForSettingsAndInfoIcons.setLayoutY(0.8850 * height);
			}
			else
			{
				gameNameWoodenImage.setLayoutY(0.0219 * height);
				welcomeImage.setLayoutY(0.1886 * height);
				
				welcomeLabel.setLayoutY(0.1933 * height);
				
				hBoxFor4SettingsButtons.setLayoutY(0.0638 * height);
				soundIcon.setLayoutY(0.0850 * height);
				
				vBoxForSound.setLayoutY(0.1505 * height);
				vBoxForSettings.setLayoutY(0.1505 * height);
				
				leftGlobeImage.setLayoutY(0.6371 * height);
				rightGlobeImage.setLayoutY(0.6371 * height);
				
				leftGlobeStand.setLayoutY(0.6152 * height);
				rightGlobeStand.setLayoutY(0.6152 * height);
				
				editNameIcon.setLayoutY(0.2419 * height);
				
				hBoxForSettingsAndInfoIcons.setLayoutY(0.8800 * height);
			}
			
			hBoxForSettingsAndInfoIcons.setLayoutX(0.7296 * width);
			
			leftGlobeStand.setFitWidth(0.1714 * width);
			leftGlobeStand.setLayoutX(0.0329 * width);
			
			rightGlobeStand.setFitWidth(0.1714 * width);
			rightGlobeStand.setLayoutX(width - leftGlobeStand.getLayoutX() - rightGlobeStand.getFitWidth());

			leftGlobeImage.setLayoutX(0.0493 * width);
			rightGlobeImage.setLayoutX(0.7729 * width);

			woodPanelFor1IconImage.setLayoutY(0.0419 * height);
			woodPanelFor4IconsImage.setLayoutY(0.0428 * height);
			
			vBoxForSound.setPrefHeight(0.1476 * height);
			
			infoIcon.setLayoutX(0.7571 * width);
			infoIcon.setLayoutY(0.8810 * height);

			vBoxForMainButtons.setPrefWidth(0.4395 * width);
			vBoxForMainButtons.setLayoutY(0.3824 * height);
			vBoxForMainButtons.setPrefHeight(0.5042 * height);
			vBoxForMainButtons.setSpacing(0.0252 * height);
			
			vBoxForEditUsername.setLayoutY(0.4261 * height);
			
			rectangleForInformationAboutGame.setLayoutY(0.2715 * height);

			if (OS == OSType.Windows)
			{
				woodPanelFor1IconImage.setLayoutX(0.0657 * width);
				woodPanelFor4IconsImage.setLayoutX(0.8214 * width);
				
				vBoxForSound.setLayoutX(0.0414 * width);
//				vBoxForSettings.setLayoutX(0.0414 * width);
			}
			else
			{
				woodPanelFor1IconImage.setLayoutX(0.8459 * width);
				woodPanelFor4IconsImage.setLayoutX(0.0429 * width);
				
				vBoxForSound.setLayoutX(0.8228 * width);
//				vBoxForSettings.setLayoutX(0.8228 * width);
			}
		}

		double iconSize  = (0.0260) * width;
		
		if (width < 1100)
		{
			masterVolumeSlider.setId("small");
			musicVolumeSlider.setId("small");
			soundEffectsVolumeSlider.setId("small");
			
			metricSystemRadioButton.setId("small");
			imperialSystemRadioButton.setId("small");
		}
		else if (width < 1700)
		{
			masterVolumeSlider.setId("medium");
			musicVolumeSlider.setId("medium");
			soundEffectsVolumeSlider.setId("medium");
			
			metricSystemRadioButton.setId("medium");
			imperialSystemRadioButton.setId("medium");
		}
		else if (width < 3000)
		{
			masterVolumeSlider.setId("big");
			musicVolumeSlider.setId("big");
			soundEffectsVolumeSlider.setId("big");
			
			metricSystemRadioButton.setId("big");
			imperialSystemRadioButton.setId("big");
		}
		else
		{
			masterVolumeSlider.setId("veryBig");
			musicVolumeSlider.setId("veryBig");
			soundEffectsVolumeSlider.setId("veryBig");
			
			metricSystemRadioButton.setId("veryBig");
			imperialSystemRadioButton.setId("veryBig");
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
		vBoxForSettings.setLayoutY(0.2843 * height);
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
		greekLanguageLabel.getTooltip().setMaxWidth(maxWidth);
		englishLanguageLabel.getTooltip().setMaxWidth(maxWidth);
		deleteAllDataButton.getTooltip().setMaxWidth(maxWidth);
		deleteSingleNameLabel.getTooltip().setMaxWidth(maxWidth);
		deleteAllNamesLabel.getTooltip().setMaxWidth(maxWidth);

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
		
		vBoxForEditUsername.setPrefSize(0.4688 * width, 0.2361 * height);
		vBoxForEditUsername.setLayoutX(width / 2.0 - vBoxForEditUsername.getPrefWidth() / 2.0);
		vBoxForEditUsername.setSpacing(0.0278 * height);
		vBoxForEditUsername.setStyle(vBoxesForEditUsersStyle);
		
		vBoxForSwitchUser.setPrefSize(0.4688 * width, 0.4120 * height);
		vBoxForSwitchUser.setLayoutX(width / 2.0 - vBoxForSwitchUser.getPrefWidth() / 2.0);
		vBoxForSwitchUser.setSpacing(0.0278 * height);
		vBoxForSwitchUser.setStyle(vBoxesForEditUsersStyle);
		
		vBoxForAddUser.setPrefSize(0.4688 * width, 0.4120 * height);
		vBoxForAddUser.setLayoutX(width / 2.0 - vBoxForAddUser.getPrefWidth() / 2.0);
		vBoxForAddUser.setSpacing(0.0278 * height);
		vBoxForAddUser.setStyle(vBoxesForEditUsersStyle);
		
		usersEditSegmentedButton.setLayoutX(width / 2.0 - vBoxForEditUsername.getPrefWidth() / 2.0);
		
		textFieldForEditUsername.setPrefWidth(vBoxForEditUsername.getPrefWidth());
		
		textFieldForAddUser.setPrefWidth(vBoxForAddUser.getPrefWidth());
		
		usersComboBox.setPrefWidth(vBoxForEditUsername.getPrefWidth());
		
		hBoxForDeleteSavedNames.setPrefWidth(vBoxForEditUsername.getPrefWidth());
		hBoxForDeleteSavedNames.setSpacing(0.0130 * width);
		
		deleteSingleNameLabel.setPrefWidth(0.5 * hBoxForDeleteSavedNames.getPrefWidth());
		deleteAllNamesLabel.setPrefWidth(0.5 * hBoxForDeleteSavedNames.getPrefWidth());
		
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
		
		popUpMessage.setPrefSize(0.2864 * width, 0.2315 * height);
		popUpMessage.setLayoutX(width / 2.0 - popUpMessage.getPrefWidth() / 2.0);
		popUpMessage.setLayoutY(height / 2.0 - popUpMessage.getPrefHeight() / 2.0);
		popUpMessage.setStyle(
				"-fx-background-color: #000000ce; -fx-border-color: black;" +
				"-fx-background-radius:" + 0.0208 * width + ";" +
				"-fx-border-radius:" + 0.0208 * width + ";" +
				"-fx-border-width:" + 0.0050 * width + ";" +
				"-fx-padding:" + 0.0052 * width + ";"
		                     );
		
		masterVolumeLabel.setFont(fontForLabels);
		musicVolumeLabel.setFont(fontForLabels);
		soundEffectsVolumeLabel.setFont(fontForLabels);
		
		startInFullScreenCheckBox.setFont(fontForNewName);
		animationsUsedLabel.setFont(fontForNewName);
		
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
		
		greekLanguageLabel.setFont(Font.font("Comic Sans MS", 0.0115 * width));
		englishLanguageLabel.setFont(Font.font("Comic Sans MS", 0.0115 * width));
		
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
		deleteSingleNameLabel.setFont(fontForNewName);
		deleteAllNamesLabel.setFont(fontForNewName);
		
		popUpMessage.setFont(fontForTooltips);
		
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
		greekLanguageLabel.getTooltip().setFont(fontForTooltips);
		englishLanguageLabel.getTooltip().setFont(fontForTooltips);
		textFieldForEditUsername.getTooltip().setFont(fontForTooltips);
		textFieldForAddUser.getTooltip().setFont(fontForTooltips);
		cancelEditUsernameButton.getTooltip().setFont(fontForTooltips);
		confirmEditUsernameButton.getTooltip().setFont(fontForTooltips);
		cancelSwitchUserButton.getTooltip().setFont(fontForTooltips);
		confirmSwitchUserButton.getTooltip().setFont(fontForTooltips);
		cancelAddUserButton.getTooltip().setFont(fontForTooltips);
		confirmAddUserButton.getTooltip().setFont(fontForTooltips);
		deleteAllDataButton.getTooltip().setFont(fontForTooltips);
		deleteSingleNameLabel.getTooltip().setFont(fontForTooltips);
		deleteAllNamesLabel.getTooltip().setFont(fontForTooltips);
		
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
		chalkboardBackgroundImage = new ImageView();
		chalkboardBackgroundImage.setLayoutX(0);
		chalkboardBackgroundImage.setLayoutX(0);
		chalkboardBackgroundImage.setSmooth(false);
		chalkboardBackgroundImage.setPreserveRatio(false);
		chalkboardBackgroundImage.setCache(true);
		chalkboardBackgroundImage.setCacheHint(CacheHint.SPEED);

		//GAME NAME IMAGE
		gameNameWoodenImage = new ImageView();
		gameNameWoodenImage.setSmooth(true);
		gameNameWoodenImage.setPreserveRatio(true);
		gameNameWoodenImage.setCache(true);
		gameNameWoodenImage.setCacheHint(CacheHint.SPEED);

		//PLAYER NAME IMAGE, WELCOME LABEL AND EDIT NAME ICON
		welcomeImage = new ImageView();
		welcomeImage.setSmooth(true);
		welcomeImage.setPreserveRatio(true);
		welcomeImage.setCache(true);
		welcomeImage.setCacheHint(CacheHint.SPEED);
		
		welcomeLabel = new Label();
		welcomeLabel.setAlignment(Pos.CENTER);
		welcomeLabel.setTextAlignment(TextAlignment.CENTER);
		welcomeLabel.setTextFill(Color.valueOf("602000"));
		welcomeLabelInnerShadow = new InnerShadow();
		welcomeLabel.setEffect(welcomeLabelInnerShadow);
		welcomeLabel.setTooltip(new CustomTooltip());
		welcomeLabel.setCache(true);
		welcomeLabel.setCacheHint(CacheHint.SCALE);
		
		editNameIcon = new ImageView();
		editNameIcon.setCursor(Cursor.HAND);
		editNameIcon.setPreserveRatio(true);
		editNameIcon.setPickOnBounds(true);
		editNameTooltip = new CustomTooltip();
		Tooltip.install(editNameIcon, editNameTooltip);
		editNameIcon.setCache(true);
		editNameIcon.setCacheHint(CacheHint.SCALE);
		
		//WOOD IMAGE AND BOX FOR 2 ICON SETTINGS
		woodPanelFor1IconImage = new ImageView();
		woodPanelFor1IconImage.setSmooth(true);
		woodPanelFor1IconImage.setPreserveRatio(true);
		woodPanelFor1IconImage.setCache(true);
		woodPanelFor1IconImage.setCacheHint(CacheHint.SPEED);
		
		//WOOD IMAGE AND BOX FOR 4 ICON SETTINGS
		woodPanelFor4IconsImage = new ImageView();
		woodPanelFor4IconsImage.setSmooth(true);
		woodPanelFor4IconsImage.setPreserveRatio(true);
		woodPanelFor4IconsImage.setCache(true);
		woodPanelFor4IconsImage.setCacheHint(CacheHint.SPEED);
		
		hBoxFor4SettingsButtons = new HBox();
		hBoxFor4SettingsButtons.setAlignment(Pos.CENTER);
		hBoxFor4SettingsButtons.setFillHeight(true);
		hBoxFor4SettingsButtons.setCache(true);
		hBoxFor4SettingsButtons.setCacheHint(CacheHint.SCALE);
		
		//POSITION OF SETTINGS BOXES BASED ON O.S.
		if (OS == OsCheck.OSType.Windows) hBoxFor4SettingsButtons.getChildren().addAll(minimizeIcon, moveIcon, fullScreenIcon, exitIcon);
		else hBoxFor4SettingsButtons.getChildren().addAll(exitIcon, fullScreenIcon, moveIcon, minimizeIcon);
		
		//LEFT STAND GLOBE AND SHADOW
		leftGlobeStand = new ImageView();
		leftGlobeStand.setSmooth(true);
		leftGlobeStand.setPreserveRatio(true);
		leftGlobeStand.setCache(true);
		leftGlobeStand.setCacheHint(CacheHint.SPEED);
		
		leftGlobeImage = new ImageView();
		leftGlobeImage.setRotate(-20);
		leftGlobeImage.setSmooth(false);
		leftGlobeImage.setPreserveRatio(true);
		
		leftEarthShadow = new DropShadow();
		leftGlobeImage.setEffect(leftEarthShadow);
		
		//RIGHT STAND GLOBE AND SHADOW
		rightGlobeStand = new ImageView();
		rightGlobeStand.setSmooth(true);
		rightGlobeStand.setPreserveRatio(true);
		rightGlobeStand.setCache(true);
		rightGlobeStand.setCacheHint(CacheHint.SPEED);
		
		rightGlobeImage = new ImageView();
		rightGlobeImage.setRotate(20);
		rightGlobeImage.setSmooth(false);
		rightGlobeImage.setPreserveRatio(true);
		
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
		
		greekLanguageLabel = new Button();
		greekLanguageFlag = new ImageView();
		greekLanguageFlag.setPreserveRatio(true);
		greekLanguageFlag.setSmooth(true);
		greekLanguageLabel.setGraphic(greekLanguageFlag);
		greekLanguageLabel.setStyle("-fx-background-color: transparent");
		greekLanguageLabel.setContentDisplay(ContentDisplay.TOP);
		greekLanguageLabel.setCursor(Cursor.HAND);
		greekLanguageLabel.setTextFill(Color.WHITE);
		greekLanguageLabel.setTooltip(new CustomTooltip());
		
		englishLanguageLabel = new Button();
		englishLanguageFlag = new ImageView();
		englishLanguageFlag.setPreserveRatio(true);
		englishLanguageFlag.setSmooth(true);
		englishLanguageLabel.setGraphic(englishLanguageFlag);
		englishLanguageLabel.setStyle("-fx-background-color: transparent");
		englishLanguageLabel.setContentDisplay(ContentDisplay.TOP);
		englishLanguageLabel.setCursor(Cursor.HAND);
		englishLanguageLabel.setTextFill(Color.WHITE);
		englishLanguageLabel.setTooltip(new CustomTooltip());
		
		hBoxForLanguagesButtons = new HBox();
		hBoxForLanguagesButtons.setAlignment(Pos.CENTER);
		hBoxForLanguagesButtons.setFillHeight(true);
		hBoxForLanguagesButtons.getChildren().addAll(englishLanguageLabel, greekLanguageLabel);

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
		deleteAllDataButton = new Button();
		deleteAllDataButton.setCursor(Cursor.HAND);
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
		settingsIcon = new ImageView();
		settingsIcon.setCursor(Cursor.HAND);
		settingsIcon.setPreserveRatio(true);
		settingsIcon.setSmooth(true);
		settingsIcon.setCache(true);
		settingsIcon.setCacheHint(CacheHint.SCALE);
		settingsTooltip = new CustomTooltip();
		Tooltip.install(settingsIcon, settingsTooltip);
		
		//INFO ICON AND RECTANGLE FOR INFO
		infoIcon = new ImageView();
		infoIcon.setCursor(Cursor.HAND);
		infoIcon.setPreserveRatio(true);
		infoIcon.setSmooth(true);
		infoIcon.setCache(true);
		infoIcon.setCacheHint(CacheHint.SCALE);
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
		singlePlayerGameButton = new Button();
		singlePlayerIcon = new ImageView();
		singlePlayerIcon.setPreserveRatio(true);
		singlePlayerIcon.setSmooth(true);
		singlePlayerGameButton.setGraphic(singlePlayerIcon);
		singlePlayerGameButton.setGraphicTextGap(10);
		singlePlayerGameButton.setCursor(Cursor.HAND);
		singlePlayerGameButton.setTooltip(new CustomTooltip());
		singlePlayerGameButton.setCache(true);
		singlePlayerGameButton.setCacheHint(CacheHint.SCALE);
		
		multiPlayerGameButton = new Button();
		multiPlayerIcon = new ImageView();
		multiPlayerIcon.setPreserveRatio(true);
		multiPlayerIcon.setSmooth(true);
		multiPlayerGameButton.setGraphic(multiPlayerIcon);
		multiPlayerGameButton.setGraphicTextGap(10);
		multiPlayerGameButton.setCursor(Cursor.HAND);
		multiPlayerGameButton.setTooltip(new CustomTooltip());
		multiPlayerGameButton.setCache(true);
		multiPlayerGameButton.setCacheHint(CacheHint.SCALE);
		
		atlasButton = new Button();
		atlasIcon = new ImageView();
		atlasIcon.setPreserveRatio(true);
		atlasIcon.setSmooth(true);
		atlasButton.setGraphic(atlasIcon);
		atlasButton.setGraphicTextGap(10);
		atlasButton.setCursor(Cursor.HAND);
		atlasButton.setTooltip(new CustomTooltip());
		atlasButton.setCache(true);
		atlasButton.setCacheHint(CacheHint.SCALE);
		
		scoreBoardButton = new Button();
		scoresIcon = new ImageView();
		scoresIcon.setPreserveRatio(true);
		scoresIcon.setSmooth(true);
		scoreBoardButton.setGraphic(scoresIcon);
		scoreBoardButton.setGraphicTextGap(10);
		scoreBoardButton.setCursor(Cursor.HAND);
		scoreBoardButton.setTooltip(new CustomTooltip());
		scoreBoardButton.setCache(true);
		scoreBoardButton.setCacheHint(CacheHint.SCALE);
		
		vBoxForMainButtons = new VBox();
		vBoxForMainButtons.setAlignment(Pos.CENTER);
		vBoxForMainButtons.getChildren().addAll(singlePlayerGameButton, multiPlayerGameButton, atlasButton, scoreBoardButton);

		//NEW NAME BOX AND STUFF
		editUsernameToggleButton = new ToggleButton();
		editUsernameToggleButton.setCursor(Cursor.HAND);
		editUsernameToggleButton.getStyleClass().addAll("segmentedToggleButton", "first");
		
		switchUserToggleButton = new ToggleButton();
		switchUserToggleButton.setCursor(Cursor.HAND);
		switchUserToggleButton.getStyleClass().add("segmentedToggleButton");
		
		addUserToggleButton = new ToggleButton();
		addUserToggleButton.setCursor(Cursor.HAND);
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
		
		deleteSingleNameLabel = new Label();
		deleteSingleNameLabel.setAlignment(Pos.CENTER);
		deleteSingleNameLabel.setTextFill(Color.WHITE);
		deleteSingleNameLabel.setUnderline(true);
		deleteSingleNameLabel.setCursor(Cursor.HAND);
		deleteSingleNameLabel.setTooltip(new CustomTooltip());
		
		deleteAllNamesLabel = new Label();
		deleteAllNamesLabel.setAlignment(Pos.CENTER);
		deleteAllNamesLabel.setTextFill(Color.WHITE);
		deleteAllNamesLabel.setUnderline(true);
		deleteAllNamesLabel.setCursor(Cursor.HAND);
		deleteAllNamesLabel.setTooltip(new CustomTooltip());
		
		hBoxForDeleteSavedNames = new HBox();
		hBoxForDeleteSavedNames.setFillHeight(true);
		hBoxForDeleteSavedNames.setAlignment(Pos.CENTER);
		hBoxForDeleteSavedNames.getChildren().addAll(deleteAllNamesLabel, deleteSingleNameLabel);
				
		cancelEditUsernameButton = new Button();
		cancelEditUsernameButton.setCursor(Cursor.HAND);
		cancelEditUsernameButton.setTooltip(new CustomTooltip());

		confirmEditUsernameButton = new Button();
		confirmEditUsernameButton.setCursor(Cursor.HAND);
		confirmEditUsernameButton.setDisable(true);
		confirmEditUsernameButton.setTooltip(new CustomTooltip());
		
		hBoxForEditUsernameButtons = new HBox();
		hBoxForEditUsernameButtons.setAlignment(Pos.CENTER);
		hBoxForEditUsernameButtons.setFillHeight(true);
		hBoxForEditUsernameButtons.getChildren().addAll(cancelEditUsernameButton, confirmEditUsernameButton);
		
		cancelSwitchUserButton = new Button();
		cancelSwitchUserButton.setCursor(Cursor.HAND);
		cancelSwitchUserButton.setTooltip(new CustomTooltip());
		
		confirmSwitchUserButton = new Button();
		confirmSwitchUserButton.setCursor(Cursor.HAND);
		confirmSwitchUserButton.setDisable(true);
		confirmSwitchUserButton.setTooltip(new CustomTooltip());
		
		hBoxForSwitchUserButtons = new HBox();
		hBoxForSwitchUserButtons.setAlignment(Pos.CENTER);
		hBoxForSwitchUserButtons.setFillHeight(true);
		hBoxForSwitchUserButtons.getChildren().addAll(cancelSwitchUserButton, confirmSwitchUserButton);
		
		cancelAddUserButton = new Button();
		cancelAddUserButton.setCursor(Cursor.HAND);
		cancelAddUserButton.setTooltip(new CustomTooltip());
		
		confirmAddUserButton = new Button();
		confirmAddUserButton.setCursor(Cursor.HAND);
		confirmAddUserButton.setDisable(true);
		confirmAddUserButton.setTooltip(new CustomTooltip());
		
		hBoxForAddUserButtons = new HBox();
		hBoxForAddUserButtons.setAlignment(Pos.CENTER);
		hBoxForAddUserButtons.setFillHeight(true);
		hBoxForAddUserButtons.getChildren().addAll(cancelAddUserButton, confirmAddUserButton);
		
		vBoxForEditUsername = new VBox();
		vBoxForEditUsername.setAlignment(Pos.TOP_CENTER);
		vBoxForEditUsername.getChildren().addAll(textFieldForEditUsername, hBoxForEditUsernameButtons);
		vBoxForEditUsername.setCache(true);
		vBoxForEditUsername.setCacheHint(CacheHint.SCALE);
		
		vBoxForSwitchUser = new VBox();
		vBoxForSwitchUser.setAlignment(Pos.TOP_CENTER);
		vBoxForSwitchUser.getChildren().addAll(usersComboBox, hBoxForDeleteSavedNames, hBoxForSwitchUserButtons);
		vBoxForSwitchUser.setCache(true);
		vBoxForSwitchUser.setCacheHint(CacheHint.SCALE);
		
		vBoxForAddUser = new VBox();
		vBoxForAddUser.setAlignment(Pos.TOP_CENTER);
		vBoxForAddUser.getChildren().addAll(textFieldForAddUser, hBoxForAddUserButtons);
		vBoxForAddUser.setCache(true);
		vBoxForAddUser.setCacheHint(CacheHint.SCALE);
		
		//MESSAGE ALL DATA DELETED
		popUpMessage = new Label();
		popUpMessage.setTextAlignment(TextAlignment.CENTER);
		popUpMessage.setTextFill(Color.WHITE);
		popUpMessage.setWrapText(true);
		
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
				vBoxForSettings, hBoxFor4SettingsButtons, soundIcon, popUpMessage);
		
		originalNamesObservableList = FXCollections.observableArrayList();
		for(Player player : playersArrayList) originalNamesObservableList.add(player.getOriginalName());
		usersComboBox.setItems(originalNamesObservableList);
		
		timelineToShowPopUpMessage = new Timeline(
          new KeyFrame(Duration.millis(0), e ->
          {
              if(animationsUsed != ANIMATIONS.NO)
              {
                  scaleTransitionMessageAllDataDeleted.setToX(1);
                  scaleTransitionMessageAllDataDeleted.setToY(1);

	              playPopUpSound();
                  scaleTransitionMessageAllDataDeleted.playFromStart();
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
                  scaleTransitionMessageAllDataDeleted.setToX(0);
                  scaleTransitionMessageAllDataDeleted.setToY(0);

	              playMinimizeSound();
                  scaleTransitionMessageAllDataDeleted.playFromStart();
              }
              else
              {
                  popUpMessage.setScaleX(0);
                  popUpMessage.setScaleY(0);
              }
          }));
		
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
	
	void showScreen(boolean firstTime)
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
		
		setUIToggleValuesBasedOnSettings();
		
		setTextAndFontForWelcomeLabel();
		
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
		
		popUpMessage.setScaleX(0);
		popUpMessage.setScaleY(0);
		
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
	
	private void changeUsersEditVBox()
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
		editNameIcon.setOnMouseClicked(e ->
		{
			editNameIcon.setImage(PENCIL_ICON_DISABLED);
			
			usersComboBox.getSelectionModel().select(0);
			
			if(animationsUsed != ANIMATIONS.NO) timelineToHideMainButtonsVBoxAndShowEditUsersVBox.playFromStart();
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
			else
			{
				if(oldValue != null && usersEditSegmentedButton.isVisible())
				{
					if(animationsUsed != ANIMATIONS.NO) timelineToChangeUsersEditVBoxes.playFromStart();
					else changeUsersEditVBox();
				}
			}
		});
		
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
		
		textFieldForEditUsername.setOnAction(e ->
		{
			if (!confirmEditUsernameButton.isDisabled()) changeCurrentPlayer(textFieldForEditUsername.getText(), getEditedOriginalName(textFieldForEditUsername.getText()));
		});
		
		textFieldForAddUser.setOnAction(e ->
		{
			if (!confirmAddUserButton.isDisabled()) changeCurrentPlayer(textFieldForEditUsername.getText(), getEditedOriginalName(textFieldForEditUsername.getText()));
		});
		
		cancelEditUsernameButton.setOnAction(e ->
		{
			playButtonClickSound();
			
			if(animationsUsed != ANIMATIONS.NO) timelineToHideEditUsersVBoxAndShowMainButtonsVBox.playFromStart();
			else hideNewNameBoxAndShowButtonsBoxNoAnimations();
		});
		
		cancelEditUsernameButton.setOnMouseEntered(e -> playHoverSound());
		
		cancelSwitchUserButton.setOnAction(e ->
		{
			playButtonClickSound();
			
			if(animationsUsed != ANIMATIONS.NO) timelineToHideEditUsersVBoxAndShowMainButtonsVBox.playFromStart();
			else hideNewNameBoxAndShowButtonsBoxNoAnimations();
		});
		
		cancelSwitchUserButton.setOnMouseEntered(e -> playHoverSound());
		
		cancelAddUserButton.setOnAction(e ->
		{
			playButtonClickSound();
			
			if(animationsUsed != ANIMATIONS.NO) timelineToHideEditUsersVBoxAndShowMainButtonsVBox.playFromStart();
			else hideNewNameBoxAndShowButtonsBoxNoAnimations();
		});
		
		cancelAddUserButton.setOnMouseEntered(e -> playHoverSound());
		
		confirmEditUsernameButton.setOnAction(e -> changeCurrentPlayer(textFieldForEditUsername.getText(), getEditedOriginalName(textFieldForEditUsername.getText())));
		
		confirmEditUsernameButton.setOnMouseEntered(e -> playHoverSound());
		
		confirmSwitchUserButton.setOnAction(e -> {});
		
		confirmSwitchUserButton.setOnMouseEntered(e -> playHoverSound());
		
		confirmAddUserButton.setOnAction(e -> {});
		
		confirmAddUserButton.setOnMouseEntered(e -> playHoverSound());
		
		deleteSingleNameLabel.setOnMouseClicked(e ->
		{
			int i = usersComboBox.getSelectionModel().getSelectedIndex();
			if(i == 0)
			{
				popUpMessage.setText(languageResourceBundle.getString("deleteSingleNameNoNameSelectedMessage"));
				timelineToShowPopUpMessage.playFromStart();
			}
			else if(i == 1)
			{
				popUpMessage.setText(languageResourceBundle.getString("deleteSingleNameUnableToDeleteMessage"));
				timelineToShowPopUpMessage.playFromStart();
			}
			else
			{
				String s = textFieldForEditUsername.getText();
				
				if(s.equals(usersComboBox.getSelectionModel().getSelectedItem()))
					usersComboBox.getSelectionModel().select(0);
				else
				{
					usersComboBox.getSelectionModel().select(0);
					textFieldForEditUsername.setText(s);
					textFieldForEditUsername.positionCaret(s.length());
				}
				
				originalNamesObservableList.remove(i);
				playersArrayList.remove(i - 1);
				
				FilesIO.writePlayersFile();
				
				popUpMessage.setText(languageResourceBundle.getString("deleteSingleNameSuccessfullyMessage"));
				timelineToShowPopUpMessage.playFromStart();
			}
		});
		
		deleteAllNamesLabel.setOnMouseClicked(e ->
		{
			originalNamesObservableList.remove(2, originalNamesObservableList.size());
			
			for(int i = 1; i < playersArrayList.size(); i++) playersArrayList.remove(i);
			
			FilesIO.writePlayersFile();
			
			popUpMessage.setText(languageResourceBundle.getString("deleteAllNamesSuccessfullyMessage"));
			timelineToShowPopUpMessage.playFromStart();
		});
		
		deleteAllDataButton.setOnMouseEntered(e -> playHoverSound());
		
		deleteAllDataButton.setOnMouseClicked(e ->
		{
			playButtonClickSound();
			
			if(FilesIO.deleteAllData())
			{
				popUpMessage.setText(languageResourceBundle.getString("messageAllDataDeletedSuccessfully"));
				timelineToShowPopUpMessage.playFromStart();
			}
		});
		
		usersComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
		{
			if(newValue != null)
			{
			
			}
		});
		
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
		
		toggleGroupForUnitsOfLength.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
		{
			if(oldValue != null) playRadioButtonSelectedSound();
			
			if(metricSystemRadioButton.isSelected()) getCurrentPlayer().setUnitSystem(UNIT_SYSTEM.METRIC);
			else getCurrentPlayer().setUnitSystem(UNIT_SYSTEM.IMPERIAL);
		});
		
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
		
		englishLanguageLabel.setOnMouseEntered(e -> playHoverSound());
		greekLanguageLabel.setOnMouseEntered(e -> playHoverSound());
		
		englishLanguageLabel.setOnAction(e ->
		{
			playButtonClickSound();
			
			englishLanguageLabel.setUnderline(true);
			greekLanguageLabel.setUnderline(false);
			
			if(getCurrentLanguage() == LANGUAGE.GREEK)
			{
				getCurrentPlayer().setLanguage(LANGUAGE.ENGLISH);
				
				if(animationsUsed != ANIMATIONS.NO) timelineToChangeLanguage.playFromStart();
				else changeLanguage();
			}
		});
		
		greekLanguageLabel.setOnAction(e ->
			{
				playButtonClickSound();
				
				greekLanguageLabel.setUnderline(true);
				englishLanguageLabel.setUnderline(false);
				
				if(getCurrentLanguage() == LANGUAGE.ENGLISH)
				{
					getCurrentPlayer().setLanguage(LANGUAGE.GREEK);
					
					if(animationsUsed != ANIMATIONS.NO) timelineToChangeLanguage.playFromStart();
					else changeLanguage();
				}
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
		
		scaleTransitionMessageAllDataDeleted = new ScaleTransition(Duration.millis(200), popUpMessage);

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
					
					scaleTransitionForWelcomeLabel.setOnFinished(ev -> {});
					
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
							scaleTransitionForWelcomeLabel.setOnFinished(eve -> {});
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
		
		timelineToHideMainButtonsVBoxAndShowEditUsersVBox = new Timeline(
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

		timelineToHideEditUsersVBoxAndShowMainButtonsVBox = new Timeline(
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
		if(getCurrentLanguage() == LANGUAGE.GREEK) greekLanguageLabel.fire();
		else englishLanguageLabel.fire();
		
		if(getCurrentPlayer().getUnitSystem() == UNIT_SYSTEM.METRIC) metricSystemRadioButton.setSelected(true);
		else imperialSystemRadioButton.setSelected(true);
		
		startInFullScreenCheckBox.setSelected(startAtFullScreen);
		
		if(getCurrentPlayer().getLocaleIndex() != -1) selectCountryInComboBoxBasedOnLocale();
		
		if(animationsUsed == ANIMATIONS.NO) noAnimationsToggleButton.setSelected(true);
		else if(animationsUsed == ANIMATIONS.LIMITED) limitedAnimationsToggleButton.setSelected(true);
		else allAnimationsToggleButton.setSelected(true);
	}
	
	private void changeCurrentPlayer(String originalName, String editedName)
	{
		LANGUAGE prevLan = getCurrentLanguage();
		
		int index = -1;
		for (int i = 1; i < originalNamesObservableList.size(); i++)
		{
			if (originalNamesObservableList.get(i).equals(originalName)) index = i;
		}
		
		if(index == -1)
		{
			originalNamesObservableList.add(1, originalName);
			
			playersArrayList.add(0, new Player(originalName, editedName));
			
			FilesIO.writePlayersFile();
		}
		else if(index != 1)
		{
			originalNamesObservableList.remove(index);
			originalNamesObservableList.add(1, originalName);
			
			Player tempPlayer = playersArrayList.remove(index - 1);
			playersArrayList.add(0, tempPlayer);
			
			FilesIO.writePlayersFile();
		}
		if(index != 1)
		{
			PowerOn.setSettingsBasedOnCurrentPlayer();
			setUIToggleValuesBasedOnSettings();
			
			if(animationsUsed != ANIMATIONS.NO)
			{
				if(prevLan != getCurrentLanguage()) timelineToChangeLanguageByPlayer.playFromStart();
				else
				{
					timelineToHideEditUsersVBoxAndShowMainButtonsVBox.playFromStart();
					
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
			}
			else
			{
				if(prevLan != getCurrentLanguage()) changeLanguage();
				
				hideNewNameBoxAndShowButtonsBoxNoAnimations();
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
		scaleTransitionForWelcomeLabel.play();
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
		textFieldForEditUsername.setPromptText(languageResourceBundle.getString("textFieldForEditUsernamePromptText"));
		textFieldForAddUser.setPromptText(languageResourceBundle.getString("textFieldForAddUserPromptText"));
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
		greekLanguageLabel.setText(languageResourceBundle.getString("greekLanguageLabel"));
		greekLanguageLabel.getTooltip().setText(languageResourceBundle.getString("greekLanguageTooltip"));
		englishLanguageLabel.setText(languageResourceBundle.getString("englishLanguageLabel"));
		englishLanguageLabel.getTooltip().setText(languageResourceBundle.getString("englishLanguageTooltip"));
		deleteAllDataButton.setText(languageResourceBundle.getString("deleteAllDataLabel"));
		deleteAllDataButton.getTooltip().setText(languageResourceBundle.getString("deleteAllDataTooltip"));
		deleteSingleNameLabel.setText(languageResourceBundle.getString("deleteSingleNameLabel"));
		deleteSingleNameLabel.getTooltip().setText(languageResourceBundle.getString("deleteSingleNameTooltip"));
		deleteAllNamesLabel.setText(languageResourceBundle.getString("deleteAllNamesLabel"));
		deleteAllNamesLabel.getTooltip().setText(languageResourceBundle.getString("deleteAllNamesTooltip"));
	}
	
	private void changeLanguage()
	{
		PowerOn.loadLanguageResourceBundle();
		
		updateStrings();
		
		if (getCurrentLanguage() == LANGUAGE.GREEK)
			GAME_NAME_IMAGE = new Image("/resources/images/backgrounds/gameNameGreek.png", 0.7 * primaryScreenWidth, 0, true, false);
		else
			GAME_NAME_IMAGE  = new Image("/resources/images/backgrounds/gameNameEnglish.png", 0.7 * primaryScreenWidth, 0, true, false);
		
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
	
	private void hideNewNameBoxAndShowButtonsBoxNoAnimations()
	{
		editNameIcon.setImage(PENCIL_ICON);
		
		textFieldForEditUsername.setText("");
		textFieldForAddUser.setText("");
		
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
}
