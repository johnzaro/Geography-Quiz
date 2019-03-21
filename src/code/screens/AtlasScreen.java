package code.screens;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ArrayList;

import code.core.*;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

import static code.core.GlobalVariables.*;

public class AtlasScreen extends CoreScreenWithMovingBackground
{
	private ListView<String> listViewForCountriesAndContinents, listViewForUSA, listViewForGreece, listViewForAttractions;
	
	private ObservableList<String> countriesObservableNamesList, capitalsOfCountriesObservableNamesList,
			continentsObservableNamesList, statesOfUSAObservableNamesList, capitalsOfStatesObservableNamesList,
			greekDecentralizedAdministrationsObservableNamesList, greekRegionsObservableNamesList,
			greekRegionalUnitsObservableNamesList, attractionsObservableNamesList;
	
	private ObservableList<Short> countriesSortList, capitalsOfCountriesSortList, continentsSortList, statesOfUSASortList,
			capitalsOfStatesSortList, greekDecentralizedAdministrationsSortList, greekRegionsSortList, greekRegionalUnitsSortList,
			attractionsSortList, emptyObservableList;
	
	private CustomImageView previousChalkboardImage, woodPanelFor5IconsImage,
			titleImage, backArrowImage, flagForCountriesAndContinentsImageSmall, flagForUSAImageSmall, coatOfArmsForCountriesAndContinentsImageSmall,
			sealForUSAImageSmall, locationForCountriesAndContinentsImageSmall, locationForUSAImageSmall, logoForGreeceImageSmall, locationForGreeceSmall,
			attractionImageSmall, attractionLocationImageSmall, bigImage, previousInBigImageButton, nextInBigImageButton, exitBigImage, zoomInBigImage, zoomOutBigImage;
	private CustomTooltip countriesToggleButtonTooltip, USAToggleButtonTooltip, greekCountiesToggleButtonTooltip, attractionsToggleButtonTooltip;
	private ComboBox<String> optionsForCountriesAndContinentsComboBox, optionsForUSAComboBox, optionsForGreeceComboBox, optionsForAttractionsComboBox;
	private Label titleLabel, flagLabelForCountriesAndContinents, flagLabelForUSA, coatOfArmsLabelForCountriesAndContinents, sealLabelForUSA,
			locationLabelForCountriesAndContinents, locationLabelForUSA, logoLabelForGreece, locationLabelForGreece, attractionLabel, attractionLocationLabel, attractionBasicInfoLabel, labelForBigImage;
	private InnerShadow innerShadow;
	private DropShadow dropShadow;
	private HBox hBoxForToggleButtons, hBoxFor5Icons, hBoxMainForCountriesAndContinents, hBoxMainForUSA, hBoxMainForGreece, hBoxMainForAttractions, hBoxForFlagAndCoatOfArmsForCountriesAndContinents,
			hBoxForFlagAndCoatOfArmsForUSA;
	private VBox vBoxFor3ImagesForCountriesAndContinents, vBoxFor3ImagesForUSA, vBoxFor2ImagesForGreece, vBoxFor2ImagesForAttractions, vBoxForListViewForCountriesAndContinents, vBoxForListViewForUSA,
			vBoxForListViewForGreece, vBoxForListViewForAttractions, vBoxForGridPaneForAttractions;
	private ToggleButton continentsAndCountriesToggleButton, USAToggleButton, greeceToggleButton, attractionsToggleButton;
	private ToggleGroup toggleGroupForToggleButtons;
	private CustomButton backButton;
	private Rectangle rectangleForBigImage, rectangleToShowInfo;
	private TextArea textAreaForInfo;
	
	private GridPane gridPaneForLabelsForCountriesAndContinents, gridPaneForLabelsForUSA, gridPaneForGreece, gridPaneForAttractions;
	private CustomScrollPane scrollPaneForGridPaneForCountriesAndContinents, scrollPaneForGridPaneForUSA;
	private GridView<Short> gridViewForImagesForCountriesAndContinents, gridViewForImagesForUSA, gridViewForImagesForAttractions;

	private Label[][] gridPaneLabelsForCountriesAndContinents, gridPaneLabelsForUSA, gridPaneLabelsForGreece, gridPaneLabelsForAttractions;
	private Tooltip[] gridPaneTooltipsForCountriesAndContinents, gridPaneTooltipsForUSA, gridPaneTooltipsForGreece, gridPaneTooltipsForAttractions;
	
	private TranslateTransition translateTransitionForTitleImage, translateTransitionForTitleLabel, translateTransitionForVBoxForSound, translateTransitionForWoodImageFor5Icons,
			translateTransitionForHBoxMainForCountriesAndContinents, translateTransitionForHBoxMainForUSA, translateTransitionForHBoxMainForGreece, translateTransitionForHBoxMainForAttractions;
	private FadeTransition fadeTransitionForMovingEarthImage;
	private ScaleTransition scaleTransitionForTitleLabel, scaleTransitionForHBoxFor5Icons, scaleTransitionForBackButton,
			scaleTransitionForCountriesToggleButton, scaleTransitionForUSAStatesToggleButton, scaleTransitionForGreekCountiesToggleButton,
			scaleTransitionForAttractionsToggleButton, scaleTransitionForHBoxMainForCountriesAndContinents, scaleTransitionForHBoxMainForUSA,
			scaleTransitionForHBoxMainForGreece, scaleTransitionForHBoxMainForAttractions, scaleTransitionForRectangleForBigImage, scaleTransitionForBigImage,
			scaleTransitionForPreviousInBigImage, scaleTransitionForNextInBigImage, scaleTransitionForLabelInBigImage,
			scaleTransitionForZoomInInBigImage, scaleTransitionForZoomOutInBigImage, scaleTransitionForExitInBigImage, scaleTransitionForRectangleForInfo,
			scaleTransitionForTextAreaForInfo;

	private Timeline timeLineToShowAllStuff, timeLineToHideAllStuff, timelineToShowSoundOptions, timelineToHideSoundOptions;
	
	private Font fontBig;
	
	//VARIABLES NEEDED IN THIS CLASS
	private String previousBigImagePath = "", newBigImagePath = "", bigImageSize = "";
	
	private boolean pressedOutsideOfBigImage;
	private boolean isInfoOpen, pressedOutsideOfGreekInfo;
	
	private byte bigImageStatus = 0; // 0 = closed, 1 = preview, 2 = opened
	
	private int numberOfTextAreaLines; // number of lines for extended greek info
	private int indexInBigImageNormal; // position in list of images currently opened in bigImage
	
	private double xOffset = 0, yOffset = 0;
	
	private enum BigImageType
	{
		FLAG_FOR_COUNTRIES, COAT_OF_ARMS_FOR_COUNTRIES, LOCATION_FOR_COUNTRIES,
		LOCATION_FOR_CONTINENTS, 
		FLAG_FOR_USA, SEAL_FOR_USA, LOCATION_FOR_USA, 
		LOCATION_FOR_GREEK_DEC_ADMIN, LOGOS_FOR_GREEK_REGIONS, LOCATION_FOR_GREEK_REGIONS
	}
	private BigImageType typeOfNormalBigImage;
	
	private enum GridViewImagesForCountriesAndContinentsType
	{
		NONE, FLAG, COAT_OF_ARMS, LOCATION
	}
	private GridViewImagesForCountriesAndContinentsType typeOfGridViewImagesForCountriesAndContinents;
	
	private enum GridViewImagesForUSAType
	{
		NONE, FLAG, SEAL, LOCATION
	}
	private GridViewImagesForUSAType typeOfGridViewImagesForUSA;
	
	private enum GridViewImagesForAttractionsType
	{
		NONE, ATTRACTION, LOCATION
	}
	private GridViewImagesForAttractionsType typeOfGridViewImagesForAttractions;
	
	private ObservableList<String> optionsForCountriesAndContinentsObservableListInGreek =
			FXCollections.observableArrayList("Χώρες", "Πρωτεύσουσες χωρών", "Σημαίες χωρών", "Εθνόσημα χωρών", "Τοποθεσία χωρών", "Ήπειροι");
	private ObservableList<String> optionsForCountriesAndContinentsObservableListInEnglish =
			FXCollections.observableArrayList("Countries", "Capitals of countries", "Flags of countries", "Coat of arms of countries", "Location of countries", "Continents");
	
	private ObservableList<String> optionsForUSAObservableListInGreek =
			FXCollections.observableArrayList("Πολιτείες", "Πρωτεύσουσες των πολιτειών", "Σημαίες των πολιτειών", "Σφραγίδες των πολιτειών", "Τοποθεσία των πολιτειών");
	private ObservableList<String> optionsForUSAObservableListInEnglish =
			FXCollections.observableArrayList("States", "Capitals of states", "Flags of states", "Seals of states", "Location of states");
	
	private ObservableList<String> optionsForGreeceObservableListInGreek =
			FXCollections.observableArrayList("Αποκεντρωμένες Διοικήσεις", "Περιφέρειες" /*, "Περιφερειακές Ενότητες", "Δήμοι"*/ );
	private ObservableList<String> optionsForGreeceObservableListInEnglish =
			FXCollections.observableArrayList("Decentralized Administrations", "Regions" /*, "Regional Units", "Municipalities" */);
	
	private ObservableList<String> optionsForAttractionsObservableListInGreek =
			FXCollections.observableArrayList("Αξιοθέατα", "Φωτογραφίες από τα αξιοθέατα", "Τοποθεσία των αξιοθέατων");
	private ObservableList<String> optionsForAttractionsObservableListInEnglish =
			FXCollections.observableArrayList("Attractions", "Images of the attractions", "Location of the attractions");

	protected void recalculateUI(double width, double height)
	{
		super.recalculateUI(width, height);
		
		if (width < 1850)
		{
			masterVolumeSlider.setId("small");
			musicVolumeSlider.setId("small");
			soundEffectsVolumeSlider.setId("small");
		}
		else if (width < 2200)
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
		
		//VARIABLES SET
		double iconSize  = 0.0260 * width;
		
		double tooltipMaxWidth = 0.2604 * width;
		
		Font fontForButtons  = Font.font("Comic Sans MS", 0.0156 * width);
		Font fontForLabels= Font.font("Comic Sans MS", FontWeight.BOLD, 0.0146 * width); // 26 -> 1920
		fontBig = Font.font("Comic Sans MS", 0.0125 * width); // 26 -> 1920
		Font fontForSound = Font.font("Comic Sans MS", 0.0094 * width);
		
		//SCREEN DEPENDENT STUFF
		if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_9)
		{
			titleImage.setLayoutY(0.0509 * height);

			titleLabel.setLayoutX(0.2813 * width);
			titleLabel.setLayoutY(0.0593 * height);

			woodPanelFor5IconsImage.setLayoutY(0.0361 * height);

			hBoxFor5Icons.setLayoutY(0.0917 * height);
			
			vBoxForSound.setLayoutY(0.0648 * height);
			vBoxForSound.setPrefSize(0.1146 * width, 0.1296 * height);

			hBoxForToggleButtons.setLayoutY(0.2296 * height);
		}
		else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_10)
		{
			titleImage.setLayoutY(0.0574 * height);
			
			titleLabel.setLayoutX(0.2813 * width);
			titleLabel.setLayoutY(0.0593 * height);
			
			woodPanelFor5IconsImage.setLayoutY(0.0438 * height);
			
			hBoxFor5Icons.setLayoutY(0.0914 * height);
			
			vBoxForSound.setLayoutY(0.0722 * height);
			vBoxForSound.setPrefSize(0.1149 * width, 0.1389 * height);
			
			hBoxForToggleButtons.setLayoutY(0.2296 * height);
		}
		else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_25_16 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_3_2)
		{
			titleImage.setLayoutY(0.0574 * height);
			
			titleLabel.setLayoutX(0.2813 * width);
			titleLabel.setLayoutY(0.0586 * height);
			
			woodPanelFor5IconsImage.setLayoutY(0.0459 * height);
			
			hBoxFor5Icons.setLayoutY(0.0901 * height);
			
			vBoxForSound.setLayoutY(0.0722 * height);
			vBoxForSound.setPrefSize(0.1156 * width, 0.1389 * height);
			
			hBoxForToggleButtons.setLayoutY(0.2296 * height);
		}
		else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_4_3 || getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_5_4)
		{
			if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_4_3)
			{
				titleLabel.setLayoutY(0.0333 * height);
				
				hBoxFor5Icons.setLayoutY(0.0667 * height);
			}
			else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_5_4)
			{
				titleLabel.setLayoutY(0.0313 * height);
				
				hBoxFor5Icons.setLayoutY(0.0625 * height);
			}
			
			titleImage.setLayoutY(0.0428 * height);
			
			titleLabel.setLayoutX(0.2813 * width);
			
			woodPanelFor5IconsImage.setLayoutY(0.0324 * height);
			
			vBoxForSound.setLayoutY(0.0560 * height);
			vBoxForSound.setPrefSize(0.1150 * width, 0.1095 * height);
			
			hBoxForToggleButtons.setLayoutY((0.2047) * height);
		}
		
		dropShadow.setRadius(0.0104 * width);
		dropShadow.setOffsetX(-0.0052 * width);
		dropShadow.setOffsetY(-0.0052 * width);
		
		//5 ICONS WOODEN PANEL AND SOUND VBOX ------------------------------------------------------
		woodPanelFor5IconsImage.setFitWidth(0.1667 * width);
		
		if (OS == OsCheck.OSType.Windows)
		{
			woodPanelFor5IconsImage.setLayoutX(0.7672 * width);
			vBoxForSound.setLayoutX(0.6396 * width);
		}
		else
		{
			woodPanelFor5IconsImage.setLayoutX(0.0656 * width);
			vBoxForSound.setLayoutX(0.2448 * width);
		}
		
		hBoxFor5Icons.setPrefSize(0.1667 * width, 0.0509 * height);
		hBoxFor5Icons.setSpacing(0.0052 * width);
		hBoxFor5Icons.setLayoutX(woodPanelFor5IconsImage.getLayoutX());
		
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
		
		minimizeTooltip.setMaxWidth(tooltipMaxWidth);
		moveTooltip.setMaxWidth(tooltipMaxWidth);
		fullScreenTooltip.setMaxWidth(tooltipMaxWidth);
		exitTooltip.setMaxWidth(tooltipMaxWidth);
		
		masterVolumeLabel.setFont(fontForSound);
		musicVolumeLabel.setFont(fontForSound);
		soundEffectsVolumeLabel.setFont(fontForSound);
		
		soundOptionsToolTip.setFont(fontForTooltips);
		minimizeTooltip.setFont(fontForTooltips);
		moveTooltip.setFont(fontForTooltips);
		fullScreenTooltip.setFont(fontForTooltips);
		exitTooltip.setFont(fontForTooltips);
		
		//TITLE IMAGE ------------------------------------------------------
		titleImage.setFitWidth(0.4688 * width);
		titleImage.setLayoutX(width / 2.0 - titleImage.getFitWidth() / 2.0);
		
		if(titleImage.getTranslateX() != 0)
		{
			if (OS == OsCheck.OSType.Windows)
			{
				titleImage.setTranslateX(-0.1068 * width);
				titleLabel.setTranslateX(-0.1068 * width);
			}
			else
			{
				titleImage.setTranslateX(0.1068 * width);
				titleLabel.setTranslateX(0.1068 * width);
			}
		}
		
		//TITLE LABEL ------------------------------------------------------
		titleLabel.setPrefSize(0.4375 * width, 0.1296 * height);
		
		innerShadow.setOffsetX(0.0041 * width);
		innerShadow.setOffsetY(0.0041 * width);
		titleLabel.setEffect(innerShadow);
		
		if(getCurrentLanguage() == LANGUAGE.GREEK) titleLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0495 * width));
		else titleLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0495 * width));
		
		//HBOX FOR TOGGLE BUTTONS ------------------------------------------------------
		hBoxForToggleButtons.setPrefSize(0.8594 * width, 0.0741 * height);
		hBoxForToggleButtons.setLayoutX(width / 2.0 - hBoxForToggleButtons.getPrefWidth() / 2.0);
		hBoxForToggleButtons.setSpacing(0.0078 * width);
		
		countriesToggleButtonTooltip.setMaxWidth(tooltipMaxWidth);
		USAToggleButtonTooltip.setMaxWidth(tooltipMaxWidth);
		greekCountiesToggleButtonTooltip.setMaxWidth(tooltipMaxWidth);
		attractionsToggleButtonTooltip.setMaxWidth(tooltipMaxWidth);
		
		continentsAndCountriesToggleButton.setPrefWidth(0.2500 * hBoxForToggleButtons.getPrefWidth());
		USAToggleButton.setPrefWidth(0.2500 * hBoxForToggleButtons.getPrefWidth());
		greeceToggleButton.setPrefWidth(0.2500 * hBoxForToggleButtons.getPrefWidth());
		attractionsToggleButton.setPrefWidth(0.2500 * hBoxForToggleButtons.getPrefWidth());
		
		continentsAndCountriesToggleButton.setFont(fontForButtons);
		USAToggleButton.setFont(fontForButtons);
		greeceToggleButton.setFont(fontForButtons);
		attractionsToggleButton.setFont(fontForButtons);
		
		countriesToggleButtonTooltip.setFont(fontForTooltips);
		USAToggleButtonTooltip.setFont(fontForTooltips);
		greekCountiesToggleButtonTooltip.setFont(fontForTooltips);
		attractionsToggleButtonTooltip.setFont(fontForTooltips);
		
		//BACK BUTTON ------------------------------------------------------
		backArrowImage.setFitWidth(0.0703 * width);
		backButton.setLayoutY(0.8148 * height);
		
		if (width < 1300) backButton.setLayoutX(0.0313 * width);
		else if (width < 2000) backButton.setLayoutX(0.0339 * width);
		else backButton.setLayoutX(0.0365 * width);
		
		backButton.setFont(fontForLabels);
		
		double gridHeight;
		double leftWidth;
		double rightWidth;
		
		//COUNTRIES AND CONTINENTS STUFF ------------------------------------------------------
		if(getCategoryCurrentlyViewing() == 1)
		{
			hBoxMainForCountriesAndContinents.setPrefSize(0.9167 * width, 0.6055 * height);
			hBoxMainForCountriesAndContinents.setLayoutX(width / 2.0 - hBoxMainForCountriesAndContinents.getPrefWidth() / 2.0);
			if (width < 1050) hBoxMainForCountriesAndContinents.setLayoutY(0.3306 * height); // 357 -> 1080
			else if (width < 1150) hBoxMainForCountriesAndContinents.setLayoutY(0.3241 * height); // 350 -> 1080
			else hBoxMainForCountriesAndContinents.setLayoutY(0.3194 * height); // 345 -> 1080
			hBoxMainForCountriesAndContinents.setSpacing(0.0078 * width);
			
			if(getIndexInOptionsForCountriesAndContinents() == 0 ||
			   getIndexInOptionsForCountriesAndContinents() == 1 ||
			   getIndexInOptionsForCountriesAndContinents() == 5)
				vBoxForListViewForCountriesAndContinents.setPrefWidth(0.2083 * width);
			else vBoxForListViewForCountriesAndContinents.setPrefWidth(0.1289 * width);
			vBoxForListViewForCountriesAndContinents.setPrefHeight(hBoxMainForCountriesAndContinents.getPrefHeight());
			vBoxForListViewForCountriesAndContinents.setSpacing(0.0093 * height);
			
			optionsForCountriesAndContinentsComboBox.setPrefSize(vBoxForListViewForCountriesAndContinents.getPrefWidth(), 0.0278 * height);
			optionsForCountriesAndContinentsComboBox.setStyle("-fx-font:" + 0.0104 * width + "px \"Comic Sans MS\";");
			
			listViewForCountriesAndContinents.setPrefSize(vBoxForListViewForCountriesAndContinents.getPrefWidth(), 0.4444 * height);
			listViewForCountriesAndContinents.setStyle("-fx-font:" + 0.0104 * width + "px \"Comic Sans MS\";");
			
			gridPaneForLabelsForCountriesAndContinents.setPrefWidth(0.4688 * width);
			
			gridHeight = Math.floor(hBoxMainForCountriesAndContinents.getPrefHeight() / 15.0 - 0.0009 * height);
			
			if(getIndexInOptionsForCountriesAndContinents() == 0 ||
			   getIndexInOptionsForCountriesAndContinents() == 1)
			{
				leftWidth  = 0.3556 * gridPaneForLabelsForCountriesAndContinents.getPrefWidth();
				rightWidth = gridPaneForLabelsForCountriesAndContinents.getPrefWidth() - leftWidth;
			}
			else
			{
				leftWidth  = 0.4778 * gridPaneForLabelsForCountriesAndContinents.getPrefWidth();
				rightWidth = gridPaneForLabelsForCountriesAndContinents.getPrefWidth() - leftWidth;
			}
			
			for (int i = 0; i < gridPaneLabelsForCountriesAndContinents.length; i++)
			{
				gridPaneLabelsForCountriesAndContinents[i][0].setPrefSize(leftWidth, gridHeight);
				gridPaneLabelsForCountriesAndContinents[i][0].setMaxSize(leftWidth, gridHeight);
				
				gridPaneLabelsForCountriesAndContinents[i][0].setFont(fontBig);
				gridPaneTooltipsForCountriesAndContinents[i].setFont(fontForTooltips);
				
				gridPaneLabelsForCountriesAndContinents[i][1].setPrefSize(rightWidth, gridHeight);
				gridPaneLabelsForCountriesAndContinents[i][1].setMaxSize(rightWidth, gridHeight);
				
				gridPaneLabelsForCountriesAndContinents[i][1].setFont(fontBig);
				gridPaneTooltipsForCountriesAndContinents[i].setFont(fontForTooltips);
				
				gridPaneTooltipsForCountriesAndContinents[i].setMaxWidth(0.3646 * width);
			}
			scrollPaneForGridPaneForCountriesAndContinents.setPrefHeight(15 * gridHeight + 0.0098 * height);
			scrollPaneForGridPaneForCountriesAndContinents.setStyle("-fx-background-color: #AB5C3D;" + "-fx-padding: " + 0.0026 * width + ";");
			
			vBoxFor3ImagesForCountriesAndContinents.setPrefSize(0.2083 * width, hBoxMainForCountriesAndContinents.getPrefHeight());
			
			hBoxForFlagAndCoatOfArmsForCountriesAndContinents.setSpacing(0.0052 * width);
			hBoxForFlagAndCoatOfArmsForCountriesAndContinents.setPrefSize(vBoxFor3ImagesForCountriesAndContinents.getPrefWidth(), 0.4 * vBoxFor3ImagesForCountriesAndContinents.getPrefHeight());
			
			locationLabelForUSA.setPrefSize(vBoxFor3ImagesForCountriesAndContinents.getPrefWidth(), 0.6 * vBoxFor3ImagesForCountriesAndContinents.getPrefWidth());
			
			flagForCountriesAndContinentsImageSmall.setFitWidth(0.45 * hBoxForFlagAndCoatOfArmsForCountriesAndContinents.getPrefWidth());
			coatOfArmsForCountriesAndContinentsImageSmall.setFitWidth(0.45 * hBoxForFlagAndCoatOfArmsForCountriesAndContinents.getPrefWidth());
			locationForCountriesAndContinentsImageSmall.setFitWidth(0.5 * vBoxFor3ImagesForCountriesAndContinents.getPrefHeight());
			
			flagLabelForCountriesAndContinents.setFont(fontForLabels);
			coatOfArmsLabelForCountriesAndContinents.setFont(fontForLabels);
			locationLabelForCountriesAndContinents.setFont(fontForLabels);
			
			gridViewForImagesForCountriesAndContinents.setPrefWidth(width - 2 * (
					hBoxMainForCountriesAndContinents.getLayoutX() + vBoxForListViewForCountriesAndContinents.getPrefWidth() +     hBoxMainForCountriesAndContinents.getSpacing()));
			gridViewForImagesForCountriesAndContinents.setPrefWidth(0.6771 * width);
			gridViewForImagesForCountriesAndContinents.setPrefHeight(hBoxMainForCountriesAndContinents.getPrefHeight());
			gridViewForImagesForCountriesAndContinents.setStyle("-fx-background-color: #AB5C3D;" +
			                                                    "-fx-padding: " + 0.0042 * width + ";");
			gridViewForImagesForCountriesAndContinents.setCellWidth((gridViewForImagesForCountriesAndContinents.getPrefWidth() - 40) / 4);
			gridViewForImagesForCountriesAndContinents.setCellHeight(0.2315 * height);
		}
		
		//USA STUFF ------------------------------------------------------
		else if(getCategoryCurrentlyViewing() == 2)
		{
			hBoxMainForUSA.setPrefSize(0.9167 * width, 0.6055 * height);
			hBoxMainForUSA.setLayoutX(width / 2.0 - hBoxMainForUSA.getPrefWidth() / 2.0);
			if (width < 1050) hBoxMainForUSA.setLayoutY(0.3306 * height); // 357 -> 1080
			else if (width < 1150) hBoxMainForUSA.setLayoutY(0.3241 * height); // 350 -> 1080
			else hBoxMainForUSA.setLayoutY(0.3194 * height); // 345 -> 1080
			hBoxMainForUSA.setSpacing(0.0078 * width);
			
			optionsForUSAComboBox.setPrefSize(0.2083 * width, 0.0278 * height);
			optionsForUSAComboBox.setStyle("-fx-font:" + 0.0104 * width + "px \"Comic Sans MS\";");
			
			listViewForUSA.setPrefSize(0.2083 * width, 0.4444 * height);
			listViewForUSA.setStyle("-fx-font:" + 0.0104 * width + "px \"Comic Sans MS\";");
			
			if(getIndexInOptionsForUSA() == 0 ||
			   getIndexInOptionsForUSA() == 1)
				vBoxForListViewForUSA.setPrefWidth(0.2083 * width);
			else vBoxForListViewForUSA.setPrefWidth(0.1289 * width);
			vBoxForListViewForUSA.setPrefHeight(hBoxMainForUSA.getPrefHeight());
			vBoxForListViewForUSA.setSpacing(0.0093 * height);
			
			gridPaneForLabelsForUSA.setPrefWidth(0.4427 * width);
			
			gridHeight = Math.floor(hBoxMainForUSA.getPrefHeight() / 15.0 - 0.0009 * height);
			leftWidth = 0.4353 * gridPaneForLabelsForUSA.getPrefWidth();
			rightWidth = 0.5647 * gridPaneForLabelsForUSA.getPrefWidth();
			
			for (int i = 0; i < gridPaneLabelsForUSA.length; i++)
			{
				gridPaneLabelsForUSA[i][0].setPrefSize(leftWidth, gridHeight);
				gridPaneLabelsForUSA[i][0].setMaxSize(leftWidth, gridHeight);
				
				gridPaneLabelsForUSA[i][0].setFont(fontBig);
				gridPaneTooltipsForUSA[i].setFont(fontForTooltips);
				
				gridPaneLabelsForUSA[i][1].setPrefSize(rightWidth, gridHeight);
				gridPaneLabelsForUSA[i][1].setMaxSize(rightWidth, gridHeight);
				
				gridPaneLabelsForUSA[i][1].setFont(fontBig);
				gridPaneTooltipsForUSA[i].setFont(fontForTooltips);
				
				gridPaneTooltipsForUSA[i].setMaxWidth(0.3646 * width);
			}
			
			scrollPaneForGridPaneForUSA.setPrefHeight(15 * gridHeight + 0.0098 * height);
			scrollPaneForGridPaneForUSA.setStyle("-fx-background-color: #AB5C3D;" + "-fx-padding: " + 0.0026 * width + ";");
			
			vBoxFor3ImagesForUSA.setPrefSize(0.2344 * width, hBoxMainForUSA.getPrefHeight());
			
			hBoxForFlagAndCoatOfArmsForUSA.setPrefSize(vBoxFor3ImagesForUSA.getPrefWidth(), 0.4 * vBoxFor3ImagesForUSA.getPrefHeight());
			hBoxForFlagAndCoatOfArmsForUSA.setSpacing(0.0052 * width);
			
			locationLabelForUSA.setPrefSize(vBoxFor3ImagesForUSA.getPrefWidth(), 0.6 * vBoxFor3ImagesForUSA.getPrefWidth());
			
			flagLabelForUSA.setFont(fontForLabels);
			sealLabelForUSA.setFont(fontForLabels);
			locationLabelForUSA.setFont(fontForLabels);
			
			flagForUSAImageSmall.setFitWidth(0.45 * hBoxForFlagAndCoatOfArmsForUSA.getPrefWidth());
			sealForUSAImageSmall.setFitWidth(0.45 * hBoxForFlagAndCoatOfArmsForUSA.getPrefWidth());
			locationForUSAImageSmall.setFitWidth(0.5 * vBoxFor3ImagesForUSA.getPrefHeight());
			
			gridViewForImagesForUSA.setPrefWidth(width - 2 * (hBoxMainForUSA.getLayoutX() + vBoxForListViewForUSA.getPrefWidth() + hBoxMainForUSA.getSpacing()));
			gridViewForImagesForUSA.setPrefHeight(hBoxMainForUSA.getPrefHeight());
			gridViewForImagesForUSA.setStyle("-fx-background-color: #AB5C3D;" +
			                                                    "-fx-padding: " + 0.0042 * width + ";");
			gridViewForImagesForUSA.setCellWidth((gridViewForImagesForUSA.getPrefWidth() - 40) / 4);
			gridViewForImagesForUSA.setCellHeight(0.2315 * height);
		}
		//GREECE STUFF
		else if(getCategoryCurrentlyViewing() == 3)
		{
			hBoxMainForGreece.setPrefSize(0.9167 * width, 0.6055 * height);
			hBoxMainForGreece.setLayoutX(width / 2.0 - hBoxMainForGreece.getPrefWidth() / 2.0);
			if (width < 1050) hBoxMainForGreece.setLayoutY(0.3306 * height); // 357 -> 1080
			else if (width < 1150) hBoxMainForGreece.setLayoutY(0.3241 * height); // 350 -> 1080
			else hBoxMainForGreece.setLayoutY(0.3194 * height); // 345 -> 1080
			hBoxMainForGreece.setSpacing(0.0078 * width);
			
			optionsForGreeceComboBox.setPrefSize(0.2083 * width, 0.0278 * height);
			optionsForGreeceComboBox.setStyle("-fx-font:" + 0.0104 * width + "px \"Comic Sans MS\";");
			
			listViewForGreece.setPrefSize(0.2083 * width, 0.4398 * height);
			listViewForGreece.setStyle("-fx-font:" + 0.0104 * width + "px \"Comic Sans MS\";");
			
			vBoxForListViewForGreece.setPrefSize(0.2083 * width, hBoxMainForGreece.getPrefHeight());
			vBoxForListViewForGreece.setSpacing(0.0093 * height);
			
			gridPaneForGreece.setPrefWidth(0.4427 * width);
			gridPaneForGreece.setStyle("-fx-background-color: #AB5C3D;" + "-fx-padding: " + 0.0031 * width + ";");
			
			gridHeight = Math.floor(hBoxMainForGreece.getPrefHeight() / 15.0 - 0.0009 * height);
			leftWidth = 0.4353 * gridPaneForGreece.getPrefWidth();
			rightWidth = 0.5647 * gridPaneForGreece.getPrefWidth();
			
			for (int i = 0; i < gridPaneLabelsForGreece.length; i++)
			{
				gridPaneLabelsForGreece[i][0].setPrefSize(leftWidth, gridHeight);
				gridPaneLabelsForGreece[i][0].setMaxSize(leftWidth, gridHeight);
				
				gridPaneLabelsForGreece[i][0].setFont(fontBig);
				gridPaneTooltipsForGreece[i].setFont(fontForTooltips);
				
				gridPaneLabelsForGreece[i][1].setPrefSize(rightWidth, gridHeight);
				gridPaneLabelsForGreece[i][1].setMaxSize(rightWidth, gridHeight);
				
				gridPaneLabelsForGreece[i][1].setFont(fontBig);
				gridPaneTooltipsForGreece[i].setFont(fontForTooltips);
				
				gridPaneTooltipsForGreece[i].setMaxWidth(0.3646 * width);
			}
			
			vBoxFor2ImagesForGreece.setPrefSize(0.2344 * width, hBoxMainForGreece.getPrefHeight());
			
			logoLabelForGreece.setFont(fontForLabels);
			locationLabelForGreece.setFont(fontForLabels);
			
			logoForGreeceImageSmall.setFitWidth(0.87 * vBoxFor2ImagesForGreece.getPrefWidth());
			locationForGreeceSmall.setFitWidth(0.87 * vBoxFor2ImagesForGreece.getPrefWidth());
		}
		//ATTRACTIONS STUFF
		else if(getCategoryCurrentlyViewing() == 4)
		{
			hBoxMainForAttractions.setPrefSize(0.9167 * width, 0.6055 * height);
			hBoxMainForAttractions.setLayoutX(width / 2.0 - hBoxMainForAttractions.getPrefWidth() / 2.0);
			if (width < 1050) hBoxMainForAttractions.setLayoutY(0.3306 * height); // 357 -> 1080
			else if (width < 1150) hBoxMainForAttractions.setLayoutY(0.3241 * height); // 350 -> 1080
			else hBoxMainForAttractions.setLayoutY(0.3194 * height); // 345 -> 1080
			hBoxMainForAttractions.setSpacing(0.0078 * width);
			
			optionsForAttractionsComboBox.setPrefSize(0.2083 * width, 0.0278 * height);
			optionsForAttractionsComboBox.setStyle("-fx-font:" + 0.0104 * width + "px \"Comic Sans MS\";");
			
			listViewForAttractions.setPrefSize(0.2083 * width, 0.4398 * height);
			listViewForAttractions.setStyle("-fx-font:" + 0.0104 * width + "px \"Comic Sans MS\";");
			
			vBoxForListViewForAttractions.setPrefSize(0.2083 * width, hBoxMainForAttractions.getPrefHeight());
			vBoxForListViewForAttractions.setSpacing(0.0093 * height);
			
			vBoxForGridPaneForAttractions.setPrefSize(0.4427 * width, hBoxMainForAttractions.getPrefHeight());
			vBoxForGridPaneForAttractions.setSpacing(0.0185 * height);
			
			gridPaneForAttractions.setPrefWidth(vBoxForGridPaneForAttractions.getPrefWidth());
			gridPaneForAttractions.setStyle("-fx-background-color: #AB5C3D;" + "-fx-padding: " + 0.0031 * width + ";");
			
			gridHeight = Math.floor(hBoxMainForAttractions.getPrefHeight() / 15.0 - 0.0009 * height);
			leftWidth = 0.4353 * gridPaneForAttractions.getPrefWidth();
			rightWidth = 0.5647 * gridPaneForAttractions.getPrefWidth();
			
			for (int i = 0; i < gridPaneLabelsForAttractions.length; i++)
			{
				gridPaneLabelsForAttractions[i][0].setPrefSize(leftWidth, gridHeight);
				gridPaneLabelsForAttractions[i][0].setMaxSize(leftWidth, gridHeight);
				
				gridPaneLabelsForAttractions[i][0].setFont(fontBig);
				gridPaneTooltipsForAttractions[i].setFont(fontForTooltips);
				
				gridPaneLabelsForAttractions[i][1].setPrefSize(rightWidth, gridHeight);
				gridPaneLabelsForAttractions[i][1].setMaxSize(rightWidth, gridHeight);
				
				gridPaneLabelsForAttractions[i][1].setFont(fontBig);
				gridPaneTooltipsForAttractions[i].setFont(fontForTooltips);
				
				gridPaneTooltipsForAttractions[i].setMaxWidth(0.3646 * width);
			}
			
			attractionBasicInfoLabel.setPrefWidth(gridPaneForAttractions.getPrefWidth());
			attractionBasicInfoLabel.setFont(fontBig);
			attractionBasicInfoLabel.setStyle("-fx-background-color: #FFEBCD; -fx-text-fill: #323232;" +
			                                  "-fx-border-color: #AB5C3D; " +
			                                  "-fx-border-width:" + 0.0031 * width + "; " +
			                                  "-fx-padding:" + 0.0021 * width + " " + 0.0042 * width + " " + 0.0021 * width + " " + 0.0042 * width + ";");
			
			vBoxFor2ImagesForAttractions.setPrefSize(0.2344 * width, hBoxMainForAttractions.getPrefHeight());
			
			attractionLabel.setFont(fontForLabels);
			attractionLocationLabel.setFont(fontForLabels);
			
			attractionImageSmall.setFitWidth(0.87 * vBoxFor2ImagesForAttractions.getPrefWidth());
			attractionLocationImageSmall.setFitWidth(0.87 * vBoxFor2ImagesForAttractions.getPrefWidth());
		}
		
		//MORE INFO ------------------------------------------------------
		if (getNumberOfTextAreaLines() < 4) textAreaForInfo.setPrefHeight(0.1500 * height);
		else if (getNumberOfTextAreaLines() < 6) textAreaForInfo.setPrefHeight(0.2000 * height);
		else if (getNumberOfTextAreaLines() < 8) textAreaForInfo.setPrefHeight(0.2700 * height);
		else if (getNumberOfTextAreaLines() < 11) textAreaForInfo.setPrefHeight(0.3700 * height);
		else if (getNumberOfTextAreaLines() < 16) textAreaForInfo.setPrefHeight(0.5000 * height);
		else textAreaForInfo.setPrefHeight(0.7000 * stage.getHeight());
		
		textAreaForInfo.setPrefWidth(0.3125 * width);
		textAreaForInfo.setLayoutX(width / 2.0 - textAreaForInfo.getPrefWidth() / 2.0);
		textAreaForInfo.setLayoutY(height / 2.0 - textAreaForInfo.getPrefHeight() / 2.0);
		textAreaForInfo.setFont(fontBig);
		
		rectangleToShowInfo.setArcWidth(0.0208 * width);
		rectangleToShowInfo.setArcHeight(0.0208 * width);
		rectangleToShowInfo.setStrokeWidth(0.0042 * width);
		rectangleToShowInfo.setWidth(0.3333 * width);
		rectangleToShowInfo.setHeight(textAreaForInfo.getPrefHeight() + 0.0208 * height);
		rectangleToShowInfo.setLayoutX(width / 2.0 - rectangleToShowInfo.getWidth() / 2.0);
		rectangleToShowInfo.setLayoutY(height / 2.0 - rectangleToShowInfo.getHeight() / 2.0);
		
		//BIG IMAGE STUFF ------------------------------------------------------
		
		rectangleForBigImage.setArcWidth(0.0208 * width);
		rectangleForBigImage.setArcHeight(0.0208 * width);
		rectangleForBigImage.setStrokeWidth(0.0042 * width);
		
		bigImage.setFitWidth(0.7 * height);
		bigImage.setLayoutX(width / 2.0 - bigImage.getFitWidth() / 2.0);
		
		rectangleForBigImage.setWidth(bigImage.getFitWidth() + 0.0208 * primaryScreenWidth);
		if (rectangleForBigImage.getWidth() < 0.4271 * width) rectangleForBigImage.setWidth(0.4271 * width);
		rectangleForBigImage.setLayoutX(width / 2.0 - rectangleForBigImage.getWidth() / 2.0);
		
		if (typeOfNormalBigImage == BigImageType.LOGOS_FOR_GREEK_REGIONS)
		{
			bigImage.setFitHeight(bigImage.getFitWidth() / 2.25);
			
			rectangleForBigImage.setHeight(bigImage.getFitHeight() + 0.1574 * height);
			rectangleForBigImage.setLayoutY(height / 2.0 - rectangleForBigImage.getHeight() / 2.0);
			
			bigImage.setLayoutY(rectangleForBigImage.getLayoutY() + (rectangleForBigImage.getHeight() - 0.1296 * height) / 2.0 - bigImage.getFitHeight() / 2.0);
		}
		else
		{
			bigImage.setFitHeight(0.7 * height);
			
			rectangleForBigImage.setHeight(bigImage.getFitHeight() + 0.1481 * height);
			rectangleForBigImage.setLayoutY(height / 2.0 - rectangleForBigImage.getHeight() / 2.0);
			
			bigImage.setLayoutY(rectangleForBigImage.getLayoutY() + 0.0185 * height);
		}
		
		if(bigImageStatus != 0)
		{
			if (labelForBigImage.getText().length() > 55) labelForBigImage.setFont(Font.font("Comic Sans MS", 0.0119 * width));
			else labelForBigImage.setFont(fontBig);
			labelForBigImage.setPrefSize(0.9 * rectangleForBigImage.getWidth(), 0.0370 * height);
			labelForBigImage.setLayoutX(width / 2.0 - labelForBigImage.getPrefWidth() / 2.0);
			labelForBigImage.setLayoutY(bigImage.getLayoutY() + bigImage.getFitHeight() + 0.0050 * height);
			
			previousInBigImageButton.setFitWidth(0.0781 * width);
			previousInBigImageButton.setLayoutX(width / 2.0 - previousInBigImageButton.getFitWidth() - 0.0104 * width);
			previousInBigImageButton.setLayoutY(rectangleForBigImage.getLayoutY() + rectangleForBigImage.getHeight() - previousInBigImageButton.getBoundsInLocal().getHeight() - 0.0139 * height);
			
			nextInBigImageButton.setFitWidth(0.0781 * width);
			nextInBigImageButton.setLayoutX(width / 2.0 + 0.0104 * width);
			nextInBigImageButton.setLayoutY(rectangleForBigImage.getLayoutY() + rectangleForBigImage.getHeight() - nextInBigImageButton.getBoundsInLocal().getHeight() - 0.0139 * height);
			
			exitBigImage.setFitWidth(0.0313 * width);
			exitBigImage.setLayoutX(rectangleForBigImage.getLayoutX() + rectangleForBigImage.getWidth() - exitBigImage.getFitWidth() - 0.0104 * width);
			exitBigImage.setLayoutY(rectangleForBigImage.getLayoutY() + rectangleForBigImage.getHeight() - exitBigImage.getBoundsInLocal().getHeight() - 0.0185 * height);
			
			zoomInBigImage.setFitWidth(0.0313 * width);
			zoomInBigImage.setLayoutX(exitBigImage.getLayoutX() - zoomInBigImage.getFitWidth() - 0.0052 * width);
			zoomInBigImage.setLayoutY(rectangleForBigImage.getLayoutY() + rectangleForBigImage.getHeight() - zoomInBigImage.getBoundsInLocal().getHeight() - 0.0185 * height);
			
			zoomOutBigImage.setFitWidth(0.0313 * width);
			zoomOutBigImage.setLayoutX(zoomInBigImage.getLayoutX() - zoomOutBigImage.getFitWidth() - 0.0052 * width);
			zoomOutBigImage.setLayoutY(rectangleForBigImage.getLayoutY() + rectangleForBigImage.getHeight() - zoomOutBigImage.getBoundsInLocal().getHeight() - 0.0185 * height);
		}
	}
	
	protected void recalculateBackground(double width, double height)
	{
		super.recalculateBackground(width, height);
		
		if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_9)
		{
			movingEarthImage.setFitWidth(0.9500 * width);
			movingEarthImage.setLayoutY(0.0500 * height);
		}
		else if(getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_16_10
				|| getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_25_16
				|| getCurrentScreenRatioEnum() == SUPPORTED_SCREEN_RATIOS.RATIO_3_2)
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
		
		previousChalkboardImage.setFitWidth(width);
		previousChalkboardImage.setFitHeight(height);
	}
	
	protected void setScreenRatioDependentImages()
	{
		woodenFrameImage.setImage(FRAME_IMAGE);
		movingEarthImage.setImage(MOVING_EARTH_IMAGE_1);
		setViewPortProperties();
	}
	
	public AtlasScreen()
	{
		//FUNDAMENTAL STUFF-------------------------------------------------------------------------------------
		previousChalkboardImage = new CustomImageView(true, false, false, true, CacheHint.SPEED);
		previousChalkboardImage.setLayoutX(0);
		previousChalkboardImage.setLayoutY(0);

		movingEarthImage = new CustomImageView(true, true, false, false, null);

		woodPanelFor5IconsImage = new CustomImageView(true, true, false, true, CacheHint.SPEED);

		hBoxFor5Icons = new HBox();
		hBoxFor5Icons.setAlignment(Pos.CENTER);
		hBoxFor5Icons.setCache(true);
		hBoxFor5Icons.setCacheHint(CacheHint.SCALE);

		if (OS == OsCheck.OSType.Windows) hBoxFor5Icons.getChildren().addAll(soundIcon, minimizeIcon, moveIcon, fullScreenIcon, exitIcon);
		else hBoxFor5Icons.getChildren().addAll(exitIcon, fullScreenIcon, moveIcon, minimizeIcon, soundIcon);

		titleImage = new CustomImageView(true, true, false, true, CacheHint.SPEED);

		innerShadow = new InnerShadow();

		titleLabel = new Label();
		titleLabel.setTextFill(Color.valueOf("#602000"));
		titleLabel.setTextAlignment(TextAlignment.CENTER);
		titleLabel.setAlignment(Pos.CENTER);
		titleLabel.setEffect(innerShadow);
		titleLabel.setCache(true);
		titleLabel.setCacheHint(CacheHint.SCALE);
		
		//DROP SHADOW EFFECT
		dropShadow = new DropShadow();
		woodenFrameImage.setEffect(dropShadow);
		titleImage.setEffect(dropShadow);
		woodPanelFor5IconsImage.setEffect(dropShadow);
		
		//TOGGLE BUTTONS-------------------------------------------------------------------------------------
		countriesToggleButtonTooltip = new CustomTooltip();

		USAToggleButtonTooltip = new CustomTooltip();

		greekCountiesToggleButtonTooltip = new CustomTooltip();

		attractionsToggleButtonTooltip = new CustomTooltip();

		continentsAndCountriesToggleButton = new ToggleButton();
		continentsAndCountriesToggleButton.setCursor(Cursor.HAND);
		continentsAndCountriesToggleButton.setTooltip(countriesToggleButtonTooltip);
		continentsAndCountriesToggleButton.setFocusTraversable(false);
		
		USAToggleButton = new ToggleButton();
		USAToggleButton.setCursor(Cursor.HAND);
		USAToggleButton.setTooltip(USAToggleButtonTooltip);
		USAToggleButton.setFocusTraversable(false);
		
		greeceToggleButton = new ToggleButton();
		greeceToggleButton.setCursor(Cursor.HAND);
		greeceToggleButton.setTooltip(greekCountiesToggleButtonTooltip);
		greeceToggleButton.setFocusTraversable(false);
		
		attractionsToggleButton = new ToggleButton();
		attractionsToggleButton.setCursor(Cursor.HAND);
		attractionsToggleButton.setTooltip(attractionsToggleButtonTooltip);
		attractionsToggleButton.setFocusTraversable(false);
		
		toggleGroupForToggleButtons = new ToggleGroup();
		toggleGroupForToggleButtons.getToggles().addAll(continentsAndCountriesToggleButton, USAToggleButton, greeceToggleButton, attractionsToggleButton);

		hBoxForToggleButtons = new HBox();
		hBoxForToggleButtons.setAlignment(Pos.CENTER);
		hBoxForToggleButtons.setFillHeight(true);
		hBoxForToggleButtons.getChildren().addAll(continentsAndCountriesToggleButton, USAToggleButton, greeceToggleButton, attractionsToggleButton);
		hBoxForToggleButtons.setCache(true);
		hBoxForToggleButtons.setCacheHint(CacheHint.SCALE);
		
		//COUNTRIES AND CONTINENTS STUFF-------------------------------------------------------------------------------------
		optionsForCountriesAndContinentsComboBox = new ComboBox<>();
		optionsForCountriesAndContinentsComboBox.setCursor(Cursor.HAND);
		
		listViewForCountriesAndContinents = new ListView<>();
		listViewForCountriesAndContinents.setCursor(Cursor.HAND);
		
		vBoxForListViewForCountriesAndContinents = new VBox();
		vBoxForListViewForCountriesAndContinents.setAlignment(Pos.TOP_CENTER);
		vBoxForListViewForCountriesAndContinents.setFillWidth(true);
		vBoxForListViewForCountriesAndContinents.getChildren().addAll(optionsForCountriesAndContinentsComboBox, listViewForCountriesAndContinents);
		
		gridPaneForLabelsForCountriesAndContinents = new GridPane();
		gridPaneForLabelsForCountriesAndContinents.setAlignment(Pos.CENTER);
		
		gridPaneLabelsForCountriesAndContinents = new Label[17][2];
		gridPaneTooltipsForCountriesAndContinents = new Tooltip[17];
		
		for(int i = 0; i < gridPaneLabelsForCountriesAndContinents.length; i++)
		{
			gridPaneLabelsForCountriesAndContinents[i][0] = new Label();
			gridPaneLabelsForCountriesAndContinents[i][1] = new Label();
			
			if(i % 2 == 0)
			{
				gridPaneLabelsForCountriesAndContinents[i][0].setStyle("-fx-background-color: #FFEBCD; -fx-text-fill: #323232;");
				gridPaneLabelsForCountriesAndContinents[i][1].setStyle("-fx-background-color: #FFEBCD; -fx-text-fill: #323232;");
			}
			else
			{
				gridPaneLabelsForCountriesAndContinents[i][0].setStyle("-fx-background-color: #EDD1A6; -fx-text-fill: #323232;");
				gridPaneLabelsForCountriesAndContinents[i][1].setStyle("-fx-background-color: #EDD1A6; -fx-text-fill: #323232;");
			}
			
			gridPaneTooltipsForCountriesAndContinents[i] = new CustomTooltip();
			
			gridPaneLabelsForCountriesAndContinents[i][1].setTooltip(gridPaneTooltipsForCountriesAndContinents[i]);
			
			if(i < 15)
			{
				gridPaneForLabelsForCountriesAndContinents.add(gridPaneLabelsForCountriesAndContinents[i][0], 0, i);
				gridPaneForLabelsForCountriesAndContinents.add(gridPaneLabelsForCountriesAndContinents[i][1], 1, i);
			}
		}
		
		scrollPaneForGridPaneForCountriesAndContinents = new CustomScrollPane();
		scrollPaneForGridPaneForCountriesAndContinents.setFitToWidth(true);
		scrollPaneForGridPaneForCountriesAndContinents.setPannable(true);
		scrollPaneForGridPaneForCountriesAndContinents.setContent(gridPaneForLabelsForCountriesAndContinents);
		scrollPaneForGridPaneForCountriesAndContinents.setCache(true);
		scrollPaneForGridPaneForCountriesAndContinents.setCacheHint(CacheHint.SCALE);
		
		flagForCountriesAndContinentsImageSmall = new CustomImageView(false, true, true, false, null);
		
		flagLabelForCountriesAndContinents = new Label();
		flagLabelForCountriesAndContinents.setGraphic(flagForCountriesAndContinentsImageSmall);
		flagLabelForCountriesAndContinents.setContentDisplay(ContentDisplay.TOP);
		flagLabelForCountriesAndContinents.setTextAlignment(TextAlignment.CENTER);
		flagLabelForCountriesAndContinents.setAlignment(Pos.BOTTOM_CENTER);
		flagLabelForCountriesAndContinents.setTextFill(Color.valueOf("#7A301B"));
		flagLabelForCountriesAndContinents.setCursor(Cursor.HAND);
		
		coatOfArmsForCountriesAndContinentsImageSmall = new CustomImageView(false, true, true, false, null);
		
		coatOfArmsLabelForCountriesAndContinents = new Label();
		coatOfArmsLabelForCountriesAndContinents.setGraphic(coatOfArmsForCountriesAndContinentsImageSmall);
		coatOfArmsLabelForCountriesAndContinents.setContentDisplay(ContentDisplay.TOP);
		coatOfArmsLabelForCountriesAndContinents.setTextAlignment(TextAlignment.CENTER);
		coatOfArmsLabelForCountriesAndContinents.setAlignment(Pos.BOTTOM_CENTER);
		coatOfArmsLabelForCountriesAndContinents.setTextFill(Color.valueOf("#7A301B"));
		coatOfArmsLabelForCountriesAndContinents.setCursor(Cursor.HAND);
		
		locationForCountriesAndContinentsImageSmall = new CustomImageView(false, true, true, false, null);
		
		locationLabelForCountriesAndContinents = new Label();
		locationLabelForCountriesAndContinents.setGraphic(locationForCountriesAndContinentsImageSmall);
		locationLabelForCountriesAndContinents.setContentDisplay(ContentDisplay.TOP);
		locationLabelForCountriesAndContinents.setTextAlignment(TextAlignment.CENTER);
		locationLabelForCountriesAndContinents.setAlignment(Pos.BASELINE_CENTER);
		locationLabelForCountriesAndContinents.setTextFill(Color.valueOf("#7A301B"));
		locationLabelForCountriesAndContinents.setCursor(Cursor.HAND);
		
		hBoxForFlagAndCoatOfArmsForCountriesAndContinents = new HBox();
		hBoxForFlagAndCoatOfArmsForCountriesAndContinents.setAlignment(Pos.BOTTOM_CENTER);
		hBoxForFlagAndCoatOfArmsForCountriesAndContinents.setFillHeight(false);
		hBoxForFlagAndCoatOfArmsForCountriesAndContinents.getChildren().addAll(flagLabelForCountriesAndContinents, coatOfArmsLabelForCountriesAndContinents);
		
		vBoxFor3ImagesForCountriesAndContinents = new VBox();
		vBoxFor3ImagesForCountriesAndContinents.setAlignment(Pos.CENTER);
		vBoxFor3ImagesForCountriesAndContinents.setFillWidth(true);
		vBoxFor3ImagesForCountriesAndContinents.getChildren().addAll(hBoxForFlagAndCoatOfArmsForCountriesAndContinents, locationLabelForCountriesAndContinents);
		
		hBoxMainForCountriesAndContinents = new HBox();
		hBoxMainForCountriesAndContinents.setAlignment(Pos.TOP_LEFT);
		hBoxMainForCountriesAndContinents.setFillHeight(false);
		hBoxMainForCountriesAndContinents.getChildren().addAll(vBoxForListViewForCountriesAndContinents, scrollPaneForGridPaneForCountriesAndContinents, vBoxFor3ImagesForCountriesAndContinents);
		hBoxMainForCountriesAndContinents.setCache(true);
		hBoxMainForCountriesAndContinents.setCacheHint(CacheHint.SCALE);
		
		gridViewForImagesForCountriesAndContinents = new GridView<>();
		gridViewForImagesForCountriesAndContinents.setHorizontalCellSpacing(0);
		gridViewForImagesForCountriesAndContinents.setVerticalCellSpacing(0);
		gridViewForImagesForCountriesAndContinents.setCellFactory(gridView -> new LabelGridCell());
		
		//USA STUFF-------------------------------------------------------------------------------------
		optionsForUSAComboBox = new ComboBox<>();
		optionsForUSAComboBox.setCursor(Cursor.HAND);
		
		listViewForUSA = new ListView<>();
		listViewForUSA.setCursor(Cursor.HAND);
		
		vBoxForListViewForUSA = new VBox();
		vBoxForListViewForUSA.setAlignment(Pos.TOP_CENTER);
		vBoxForListViewForUSA.setFillWidth(true);
		vBoxForListViewForUSA.getChildren().addAll(optionsForUSAComboBox, listViewForUSA);
		
		gridPaneForLabelsForUSA = new GridPane();
		gridPaneForLabelsForUSA.setAlignment(Pos.CENTER);
		
		gridPaneLabelsForUSA = new Label[18][2];
		gridPaneTooltipsForUSA = new Tooltip[18];
		
		for(int i = 0; i < gridPaneLabelsForUSA.length; i++)
		{
			gridPaneLabelsForUSA[i][0] = new Label();
			gridPaneLabelsForUSA[i][1] = new Label();
			
			if(i % 2 == 0)
			{
				gridPaneLabelsForUSA[i][0].setStyle("-fx-background-color: #FFEBCD; -fx-text-fill: #323232;");
				gridPaneLabelsForUSA[i][1].setStyle("-fx-background-color: #FFEBCD; -fx-text-fill: #323232;");
			}
			else
			{
				gridPaneLabelsForUSA[i][0].setStyle("-fx-background-color: #EDD1A6; -fx-text-fill: #323232;");
				gridPaneLabelsForUSA[i][1].setStyle("-fx-background-color: #EDD1A6; -fx-text-fill: #323232;");
			}
			
			gridPaneTooltipsForUSA[i] = new CustomTooltip();
			
			gridPaneLabelsForUSA[i][1].setTooltip(gridPaneTooltipsForUSA[i]);
			
			gridPaneForLabelsForUSA.add(gridPaneLabelsForUSA[i][0], 0, i);
			gridPaneForLabelsForUSA.add(gridPaneLabelsForUSA[i][1], 1, i);
		}
		
		gridPaneLabelsForUSA[2][1].setCursor(Cursor.HAND);
		
		scrollPaneForGridPaneForUSA = new CustomScrollPane();
		scrollPaneForGridPaneForUSA.setFitToWidth(true);
		scrollPaneForGridPaneForUSA.setCursor(Cursor.MOVE);
		scrollPaneForGridPaneForUSA.setPannable(true);
		scrollPaneForGridPaneForUSA.setContent(gridPaneForLabelsForUSA);
		
		flagForUSAImageSmall = new CustomImageView(false, true, true, false, null);
		
		flagLabelForUSA = new Label();
		flagLabelForUSA.setGraphic(flagForUSAImageSmall);
		flagLabelForUSA.setContentDisplay(ContentDisplay.TOP);
		flagLabelForUSA.setTextAlignment(TextAlignment.CENTER);
		flagLabelForUSA.setAlignment(Pos.BOTTOM_CENTER);
		flagLabelForUSA.setTextFill(Color.valueOf("#7A301B"));
		flagLabelForUSA.setCursor(Cursor.HAND);
		
		sealForUSAImageSmall = new CustomImageView(false, true, true, false, null);
		
		sealLabelForUSA = new Label();
		sealLabelForUSA.setGraphic(sealForUSAImageSmall);
		sealLabelForUSA.setContentDisplay(ContentDisplay.TOP);
		sealLabelForUSA.setTextAlignment(TextAlignment.CENTER);
		sealLabelForUSA.setAlignment(Pos.BOTTOM_CENTER);
		sealLabelForUSA.setTextFill(Color.valueOf("#7A301B"));
		sealLabelForUSA.setCursor(Cursor.HAND);
		
		locationForUSAImageSmall = new CustomImageView(false, true, true, false, null);
		
		locationLabelForUSA = new Label();
		locationLabelForUSA.setGraphic(locationForUSAImageSmall);
		locationLabelForUSA.setContentDisplay(ContentDisplay.TOP);
		locationLabelForUSA.setTextAlignment(TextAlignment.CENTER);
		locationLabelForUSA.setAlignment(Pos.BASELINE_CENTER);
		locationLabelForUSA.setTextFill(Color.valueOf("#7A301B"));
		locationLabelForUSA.setCursor(Cursor.HAND);
		
		hBoxForFlagAndCoatOfArmsForUSA = new HBox();
		hBoxForFlagAndCoatOfArmsForUSA.setAlignment(Pos.BOTTOM_CENTER);
		hBoxForFlagAndCoatOfArmsForUSA.setPickOnBounds(false);
		hBoxForFlagAndCoatOfArmsForUSA.setFillHeight(false);
		hBoxForFlagAndCoatOfArmsForUSA.getChildren().addAll(flagLabelForUSA, sealLabelForUSA);
		
		vBoxFor3ImagesForUSA = new VBox();
		vBoxFor3ImagesForUSA.setAlignment(Pos.CENTER);
		vBoxFor3ImagesForUSA.setPickOnBounds(false);
		vBoxFor3ImagesForUSA.setFillWidth(true);
		vBoxFor3ImagesForUSA.getChildren().addAll(hBoxForFlagAndCoatOfArmsForUSA, locationLabelForUSA);
		
		hBoxMainForUSA = new HBox();
		hBoxMainForUSA.setAlignment(Pos.TOP_LEFT);
		hBoxMainForUSA.setPickOnBounds(false);
		hBoxMainForUSA.setFillHeight(false);
		hBoxMainForUSA.getChildren().addAll(vBoxForListViewForUSA, scrollPaneForGridPaneForUSA, vBoxFor3ImagesForUSA);
		hBoxMainForUSA.setCache(true);
		hBoxMainForUSA.setCacheHint(CacheHint.SCALE);
		
		gridViewForImagesForUSA = new GridView<>();
		gridViewForImagesForUSA.setHorizontalCellSpacing(0);
		gridViewForImagesForUSA.setVerticalCellSpacing(0);
		gridViewForImagesForUSA.setCellFactory(gridView -> new LabelGridCell());
		
		//GREECE STUFF-------------------------------------------------------------------------------------
		optionsForGreeceComboBox = new ComboBox<>();
		optionsForGreeceComboBox.setCursor(Cursor.HAND);
		
		listViewForGreece = new ListView<>();
		listViewForGreece.setCursor(Cursor.HAND);
		
		vBoxForListViewForGreece = new VBox();
		vBoxForListViewForGreece.setAlignment(Pos.TOP_CENTER);
		vBoxForListViewForGreece.setPickOnBounds(false);
		vBoxForListViewForGreece.setFillWidth(true);
		vBoxForListViewForGreece.getChildren().addAll(optionsForGreeceComboBox, listViewForGreece);
		
		gridPaneForGreece = new GridPane();
		gridPaneForGreece.setAlignment(Pos.CENTER);
		
		gridPaneLabelsForGreece = new Label[15][2];
		gridPaneTooltipsForGreece = new Tooltip[15];
		
		for(int i = 0; i < gridPaneLabelsForGreece.length; i++)
		{
			gridPaneLabelsForGreece[i][0] = new Label();
			gridPaneLabelsForGreece[i][1] = new Label();
			
			if(i % 2 == 0)
			{
				gridPaneLabelsForGreece[i][0].setStyle("-fx-background-color: #FFEBCD; -fx-text-fill: #323232;");
				gridPaneLabelsForGreece[i][1].setStyle("-fx-background-color: #FFEBCD; -fx-text-fill: #323232;");
			}
			else
			{
				gridPaneLabelsForGreece[i][0].setStyle("-fx-background-color: #EDD1A6; -fx-text-fill: #323232;");
				gridPaneLabelsForGreece[i][1].setStyle("-fx-background-color: #EDD1A6; -fx-text-fill: #323232;");
			}
			
			gridPaneTooltipsForGreece[i] = new CustomTooltip();
			
			gridPaneLabelsForGreece[i][1].setTooltip(gridPaneTooltipsForGreece[i]);
			
			gridPaneForGreece.add(gridPaneLabelsForGreece[i][0], 0, i);
			gridPaneForGreece.add(gridPaneLabelsForGreece[i][1], 1, i);
		}
		
		logoForGreeceImageSmall = new CustomImageView(false, true, true, false, null);

		logoLabelForGreece = new Label();
		logoLabelForGreece.setGraphic(logoForGreeceImageSmall);
		logoLabelForGreece.setContentDisplay(ContentDisplay.TOP);
		logoLabelForGreece.setTextAlignment(TextAlignment.CENTER);
		logoLabelForGreece.setAlignment(Pos.BASELINE_CENTER);
		logoLabelForGreece.setTextFill(Color.valueOf("#7A301B"));

		locationForGreeceSmall = new CustomImageView(false, true, true, false, null);

		locationLabelForGreece = new Label();
		locationLabelForGreece.setGraphic(locationForGreeceSmall);
		locationLabelForGreece.setContentDisplay(ContentDisplay.TOP);
		locationLabelForGreece.setTextAlignment(TextAlignment.CENTER);
		locationLabelForGreece.setAlignment(Pos.BASELINE_CENTER);
		locationLabelForGreece.setTextFill(Color.valueOf("#7A301B"));
		locationLabelForGreece.setCursor(Cursor.HAND);

		vBoxFor2ImagesForGreece = new VBox();
		vBoxFor2ImagesForGreece.setAlignment(Pos.CENTER);
		vBoxFor2ImagesForGreece.setPickOnBounds(false);
		vBoxFor2ImagesForGreece.setFillWidth(true);
		vBoxFor2ImagesForGreece.getChildren().addAll(logoLabelForGreece, locationLabelForGreece);
		
		hBoxMainForGreece = new HBox();
		hBoxMainForGreece.setAlignment(Pos.TOP_LEFT);
		hBoxMainForGreece.setPickOnBounds(false);
		hBoxMainForGreece.setFillHeight(false);
		hBoxMainForGreece.getChildren().addAll(vBoxForListViewForGreece, gridPaneForGreece, vBoxFor2ImagesForGreece);
		hBoxMainForGreece.setCache(true);
		hBoxMainForGreece.setCacheHint(CacheHint.SCALE);
		
		//ATTRACTIONS STUFF-------------------------------------------------------------------------------------
		optionsForAttractionsComboBox = new ComboBox<>();
		optionsForAttractionsComboBox.setCursor(Cursor.HAND);
		
		listViewForAttractions = new ListView<>();
		listViewForAttractions.setCursor(Cursor.HAND);
		
		vBoxForListViewForAttractions = new VBox();
		vBoxForListViewForAttractions.setAlignment(Pos.TOP_CENTER);
		vBoxForListViewForAttractions.setPickOnBounds(false);
		vBoxForListViewForAttractions.setFillWidth(true);
		vBoxForListViewForAttractions.getChildren().addAll(optionsForAttractionsComboBox, listViewForAttractions);
		
		gridPaneForAttractions = new GridPane();
		gridPaneForAttractions.setAlignment(Pos.CENTER);
		
		gridPaneLabelsForAttractions = new Label[6][2];
		gridPaneTooltipsForAttractions = new Tooltip[6];
		
		for(int i = 0; i < gridPaneLabelsForAttractions.length; i++)
		{
			gridPaneLabelsForAttractions[i][0] = new Label();
			gridPaneLabelsForAttractions[i][1] = new Label();
			
			if(i % 2 == 0)
			{
				gridPaneLabelsForAttractions[i][0].setStyle("-fx-background-color: #FFEBCD; -fx-text-fill: #323232;");
				gridPaneLabelsForAttractions[i][1].setStyle("-fx-background-color: #FFEBCD; -fx-text-fill: #323232;");
			}
			else
			{
				gridPaneLabelsForAttractions[i][0].setStyle("-fx-background-color: #EDD1A6; -fx-text-fill: #323232;");
				gridPaneLabelsForAttractions[i][1].setStyle("-fx-background-color: #EDD1A6; -fx-text-fill: #323232;");
			}
			
			gridPaneTooltipsForAttractions[i] = new CustomTooltip();
			
			gridPaneLabelsForAttractions[i][1].setTooltip(gridPaneTooltipsForAttractions[i]);
			
			gridPaneForAttractions.add(gridPaneLabelsForAttractions[i][0], 0, i);
			gridPaneForAttractions.add(gridPaneLabelsForAttractions[i][1], 1, i);
		}
		
		attractionBasicInfoLabel = new Label();
		attractionBasicInfoLabel.setWrapText(true);
		
		vBoxForGridPaneForAttractions = new VBox();
		vBoxForGridPaneForAttractions.setAlignment(Pos.TOP_CENTER);
		vBoxForGridPaneForAttractions.setFillWidth(true);
		vBoxForGridPaneForAttractions.getChildren().addAll(gridPaneForAttractions, attractionBasicInfoLabel);
		
		attractionImageSmall = new CustomImageView(false, true, true, false, null);
		
		attractionLabel = new Label();
		attractionLabel.setGraphic(attractionImageSmall);
		attractionLabel.setContentDisplay(ContentDisplay.TOP);
		attractionLabel.setTextAlignment(TextAlignment.CENTER);
		attractionLabel.setAlignment(Pos.BASELINE_CENTER);
		attractionLabel.setTextFill(Color.valueOf("#7A301B"));
		
		attractionLocationImageSmall = new CustomImageView(false, true, true, false, null);
		
		attractionLocationLabel = new Label();
		attractionLocationLabel.setGraphic(attractionLocationImageSmall);
		attractionLocationLabel.setContentDisplay(ContentDisplay.TOP);
		attractionLocationLabel.setTextAlignment(TextAlignment.CENTER);
		attractionLocationLabel.setAlignment(Pos.BASELINE_CENTER);
		attractionLocationLabel.setTextFill(Color.valueOf("#7A301B"));
		attractionLocationLabel.setCursor(Cursor.HAND);
		
		vBoxFor2ImagesForAttractions = new VBox();
		vBoxFor2ImagesForAttractions.setAlignment(Pos.CENTER);
		vBoxFor2ImagesForAttractions.setPickOnBounds(false);
		vBoxFor2ImagesForAttractions.setFillWidth(true);
		vBoxFor2ImagesForAttractions.getChildren().addAll(attractionLabel, attractionLocationLabel);
		
		hBoxMainForAttractions = new HBox();
		hBoxMainForAttractions.setAlignment(Pos.TOP_LEFT);
		hBoxMainForAttractions.setPickOnBounds(false);
		hBoxMainForAttractions.setFillHeight(false);
		hBoxMainForAttractions.getChildren().addAll(vBoxForListViewForAttractions, vBoxForGridPaneForAttractions, vBoxFor2ImagesForAttractions);
		hBoxMainForAttractions.setCache(true);
		hBoxMainForAttractions.setCacheHint(CacheHint.SCALE);
		
		gridViewForImagesForAttractions = new GridView<>();
		gridViewForImagesForAttractions.setHorizontalCellSpacing(0);
		gridViewForImagesForAttractions.setVerticalCellSpacing(0);
		gridViewForImagesForAttractions.setCellFactory(gridView -> new LabelGridCell());
		
		//MORE INFO STUFF -------------------------------------------------------------------------------------
		rectangleToShowInfo = new Rectangle();
		rectangleToShowInfo.setSmooth(true);
		rectangleToShowInfo.setStroke(Color.BLACK);
		rectangleToShowInfo.setFill(Color.valueOf("000000be"));
		
		textAreaForInfo = new TextArea();
		textAreaForInfo.setEditable(false);
		textAreaForInfo.setWrapText(true);
		
		//BIG IMAGE STUFF -------------------------------------------------------------------------------------
		rectangleForBigImage = new Rectangle();
		rectangleForBigImage.setSmooth(true);
		rectangleForBigImage.setStroke(Color.BLACK);
		rectangleForBigImage.setFill(Color.valueOf("00000099"));
		rectangleForBigImage.setCache(true);
		rectangleForBigImage.setCacheHint(CacheHint.SCALE);
		
		bigImage = new CustomImageView(false, true, true, true, CacheHint.SCALE);
		
		labelForBigImage = new Label();
		labelForBigImage.setAlignment(Pos.CENTER);
		labelForBigImage.setTextFill(Color.WHITE);
		labelForBigImage.setCache(true);
		labelForBigImage.setCacheHint(CacheHint.SCALE);
		
		previousInBigImageButton = new CustomImageView(BACK_ARROW, true, true, true, true, CacheHint.SCALE);
		previousInBigImageButton.setCursor(Cursor.HAND);
		
		nextInBigImageButton = new CustomImageView(BACK_ARROW, true, true, true, true, CacheHint.SCALE);
		nextInBigImageButton.setRotate(180);
		nextInBigImageButton.setCursor(Cursor.HAND);
		
		exitBigImage = new CustomImageView(X_ICON, true, true, false, true, CacheHint.SCALE);
		exitBigImage.setCursor(Cursor.HAND);
		
		zoomInBigImage = new CustomImageView(ZOOM_IN_ICON, true, true, true, true, CacheHint.SCALE);
		zoomInBigImage.setCursor(Cursor.HAND);
		
		zoomOutBigImage = new CustomImageView(ZOOM_OUT_ICON, true, true, true, true, CacheHint.SCALE);
		zoomOutBigImage.setCursor(Cursor.HAND);
		
		//BACK ARROW-------------------------------------------------------------------------------------
		backArrowImage = new CustomImageView(BACK_ARROW, true, true, true, false, null);

		backButton = new CustomButton();
		backButton.setStyle("-fx-background-color: transparent");
		backButton.setGraphic(backArrowImage);
		backButton.setContentDisplay(ContentDisplay.TOP);
		backButton.setTextAlignment(TextAlignment.CENTER);
		backButton.setTextFill(Color.valueOf("#7A301B"));
		backButton.setCache(true);
		backButton.setCacheHint(CacheHint.SCALE);
		
		hBoxMainForUSA.setVisible(false);
		hBoxMainForGreece.setVisible(false);
		hBoxMainForAttractions.setVisible(false);
		
		nodesPane.getChildren().addAll(previousChalkboardImage, movingEarthImage, titleImage, titleLabel,
				hBoxForToggleButtons, woodPanelFor5IconsImage, hBoxMainForCountriesAndContinents, hBoxMainForUSA,
				hBoxMainForGreece, hBoxMainForAttractions, vBoxForSound, backButton, hBoxFor5Icons,
				rectangleForBigImage, bigImage, labelForBigImage, previousInBigImageButton, nextInBigImageButton,
				exitBigImage, zoomInBigImage, zoomOutBigImage, rectangleToShowInfo, textAreaForInfo);
		
		if(animationsUsed != ANIMATIONS.NO)
		{
			setupLimitedAnimations();
			if(animationsUsed == ANIMATIONS.ALL) setupAdvancedAnimations();
		}
		
		setupListeners();
	}
	
	protected void setupListeners()
	{
		toggleGroupForToggleButtons.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
		{
			if(newValue == null)
				Platform.runLater(() ->
				{
					playButtonClickSound();
					toggleGroupForToggleButtons.selectToggle(oldValue);
				});
			else
			{
				if(oldValue != null)
				{
					playButtonClickSound();
					
					hBoxFor5Icons.setDisable(true);
					hBoxForToggleButtons.setDisable(true);
					backButton.setDisable(true);
				}
				
				if (getCategoryCurrentlyViewing() == 1)
				{
					if(animationsUsed != ANIMATIONS.NO)
					{
						if(oldValue == USAToggleButton)
						{
							translateTransitionForHBoxMainForUSA.setToX(stage.getWidth() - hBoxMainForUSA.getLayoutX() + 20);
							translateTransitionForHBoxMainForUSA.setOnFinished(ev ->
							{
								hBoxMainForUSA.setVisible(false);
								
								countriesAndContinentsToggleButtonPressed();
								
								hBoxMainForCountriesAndContinents.setTranslateX(-1 * (hBoxMainForCountriesAndContinents.getLayoutX() + hBoxMainForCountriesAndContinents.getPrefWidth() + 20));
								translateTransitionForHBoxMainForCountriesAndContinents.setToX(0);
								
								translateTransitionForHBoxMainForCountriesAndContinents.setOnFinished(evn ->
								{
									hBoxFor5Icons.setDisable(false);
									hBoxForToggleButtons.setDisable(false);
									backButton.setDisable(false);
								});
								
								hBoxMainForCountriesAndContinents.setVisible(true);
								translateTransitionForHBoxMainForCountriesAndContinents.playFromStart();
							});
							translateTransitionForHBoxMainForUSA.playFromStart();
						}
						else if(oldValue == greeceToggleButton)
						{
							translateTransitionForHBoxMainForGreece.setToX(stage.getWidth() - hBoxMainForGreece.getLayoutX() + 20);
							translateTransitionForHBoxMainForGreece.setOnFinished(ev ->
							{
								hBoxMainForGreece.setVisible(false);
								
								countriesAndContinentsToggleButtonPressed();
								
								hBoxMainForCountriesAndContinents.setTranslateX(-1 * (hBoxMainForCountriesAndContinents.getLayoutX() + hBoxMainForCountriesAndContinents.getPrefWidth() + 20));
								translateTransitionForHBoxMainForCountriesAndContinents.setToX(0);
								
								translateTransitionForHBoxMainForCountriesAndContinents.setOnFinished(evn ->
								{
									hBoxFor5Icons.setDisable(false);
									hBoxForToggleButtons.setDisable(false);
									backButton.setDisable(false);
								});
								
								hBoxMainForCountriesAndContinents.setVisible(true);
								translateTransitionForHBoxMainForCountriesAndContinents.playFromStart();
							});
							translateTransitionForHBoxMainForGreece.playFromStart();
						}
						else if(oldValue == attractionsToggleButton)
						{
							translateTransitionForHBoxMainForAttractions.setToX(stage.getWidth() - hBoxMainForAttractions.getLayoutX() + 20);
							translateTransitionForHBoxMainForAttractions.setOnFinished(ev ->
							{
								hBoxMainForAttractions.setVisible(false);
								
								countriesAndContinentsToggleButtonPressed();
								
								hBoxMainForCountriesAndContinents.setTranslateX(-1 * (hBoxMainForCountriesAndContinents.getLayoutX() + hBoxMainForCountriesAndContinents.getPrefWidth() + 20));
								translateTransitionForHBoxMainForCountriesAndContinents.setToX(0);
								
								translateTransitionForHBoxMainForCountriesAndContinents.setOnFinished(evn ->
								{
									hBoxFor5Icons.setDisable(false);
									hBoxForToggleButtons.setDisable(false);
									backButton.setDisable(false);
								});
								
								hBoxMainForCountriesAndContinents.setVisible(true);
								translateTransitionForHBoxMainForCountriesAndContinents.playFromStart();
							});
							translateTransitionForHBoxMainForAttractions.playFromStart();
						}
						else if(oldValue == null)
							countriesAndContinentsToggleButtonPressed();
					}
					else
					{
						if(oldValue == USAToggleButton)
						{
							hBoxMainForUSA.setTranslateX(stage.getWidth() - hBoxMainForUSA.getLayoutX() + 20);
							hBoxMainForUSA.setVisible(false);
						}
						else if(oldValue == greeceToggleButton)
						{
							hBoxMainForGreece.setTranslateX(stage.getWidth() - hBoxMainForGreece.getLayoutX() + 20);
							hBoxMainForGreece.setVisible(false);
						}
						else if(oldValue == attractionsToggleButton)
						{
							hBoxMainForAttractions.setTranslateX(stage.getWidth() - hBoxMainForAttractions.getLayoutX() + 20);
							hBoxMainForAttractions.setVisible(false);
						}
						else if(oldValue == null)
							countriesAndContinentsToggleButtonPressed();
						
						countriesAndContinentsToggleButtonPressed();
						
						hBoxMainForCountriesAndContinents.setTranslateX(0);
						hBoxMainForCountriesAndContinents.setVisible(true);
						
						hBoxFor5Icons.setDisable(false);
						hBoxForToggleButtons.setDisable(false);
						backButton.setDisable(false);
					}
				}
				else if (getCategoryCurrentlyViewing() == 2)
				{
					if(animationsUsed != ANIMATIONS.NO)
					{
						if (oldValue == continentsAndCountriesToggleButton)
						{
							translateTransitionForHBoxMainForCountriesAndContinents.setToX(-1 * (hBoxMainForCountriesAndContinents.getLayoutX() + hBoxMainForCountriesAndContinents.getPrefWidth() + 20));
							translateTransitionForHBoxMainForCountriesAndContinents.setOnFinished(ev ->
							{
								hBoxMainForCountriesAndContinents.setVisible(false);
								
								usaStatesToggleButtonPressed();
								
								hBoxMainForUSA.setTranslateX(stage.getWidth() - hBoxMainForUSA.getLayoutX() + 20);
								translateTransitionForHBoxMainForUSA.setToX(0);
								
								translateTransitionForHBoxMainForUSA.setOnFinished(evn ->
								{
									hBoxFor5Icons.setDisable(false);
									hBoxForToggleButtons.setDisable(false);
									backButton.setDisable(false);
								});
								
								hBoxMainForUSA.setVisible(true);
								translateTransitionForHBoxMainForUSA.playFromStart();
							});
							translateTransitionForHBoxMainForCountriesAndContinents.playFromStart();
						}
						else if (oldValue == greeceToggleButton)
						{
							translateTransitionForHBoxMainForGreece.setToX(stage.getWidth() - hBoxMainForGreece.getLayoutX() + 20);
							translateTransitionForHBoxMainForGreece.setOnFinished(ev ->
							{
								hBoxMainForGreece.setVisible(false);
								
								usaStatesToggleButtonPressed();
								
								hBoxMainForUSA.setTranslateX(-1 * (hBoxMainForUSA.getLayoutX() + hBoxMainForUSA.getPrefWidth() + 20));
								translateTransitionForHBoxMainForUSA.setToX(0);
								
								translateTransitionForHBoxMainForUSA.setOnFinished(evn ->
								{
									hBoxFor5Icons.setDisable(false);
									hBoxForToggleButtons.setDisable(false);
									backButton.setDisable(false);
								});
								
								hBoxMainForUSA.setVisible(true);
								translateTransitionForHBoxMainForUSA.playFromStart();
							});
							translateTransitionForHBoxMainForGreece.playFromStart();
						}
						else if(oldValue == attractionsToggleButton)
						{
							translateTransitionForHBoxMainForAttractions.setToX(stage.getWidth() - hBoxMainForAttractions.getLayoutX() + 20);
							translateTransitionForHBoxMainForAttractions.setOnFinished(ev ->
							{
								hBoxMainForAttractions.setVisible(false);
								
								usaStatesToggleButtonPressed();
								
								hBoxMainForUSA.setTranslateX(-1 * (hBoxMainForUSA.getLayoutX() + hBoxMainForUSA.getPrefWidth() + 20));
								translateTransitionForHBoxMainForUSA.setToX(0);
								
								translateTransitionForHBoxMainForUSA.setOnFinished(evn ->
								{
									hBoxFor5Icons.setDisable(false);
									hBoxForToggleButtons.setDisable(false);
									backButton.setDisable(false);
								});
								
								hBoxMainForUSA.setVisible(true);
								translateTransitionForHBoxMainForUSA.playFromStart();
							});
							translateTransitionForHBoxMainForAttractions.playFromStart();
						}
						else if(oldValue == null)
							usaStatesToggleButtonPressed();
					}
					else
					{
						if(oldValue == continentsAndCountriesToggleButton)
						{
							hBoxMainForCountriesAndContinents.setTranslateX(stage.getWidth() - hBoxMainForCountriesAndContinents.getLayoutX() + 20);
							hBoxMainForCountriesAndContinents.setVisible(false);
						}
						else if(oldValue == greeceToggleButton)
						{
							hBoxMainForGreece.setTranslateX(stage.getWidth() - hBoxMainForGreece.getLayoutX() + 20);
							hBoxMainForGreece.setVisible(false);
						}
						else if(oldValue == attractionsToggleButton)
						{
							hBoxMainForAttractions.setTranslateX(stage.getWidth() - hBoxMainForAttractions.getLayoutX() + 20);
							hBoxMainForAttractions.setVisible(false);
						}
						else if(oldValue == null)
							usaStatesToggleButtonPressed();
						
						usaStatesToggleButtonPressed();
						
						hBoxMainForUSA.setTranslateX(0);
						hBoxMainForUSA.setVisible(true);
						
						hBoxFor5Icons.setDisable(false);
						hBoxForToggleButtons.setDisable(false);
						backButton.setDisable(false);
					}
				}
				else if (getCategoryCurrentlyViewing() == 3)
				{
					if(animationsUsed != ANIMATIONS.NO)
					{
						if (oldValue == continentsAndCountriesToggleButton)
						{
							translateTransitionForHBoxMainForCountriesAndContinents.setToX(-1 * (hBoxMainForCountriesAndContinents.getLayoutX() + hBoxMainForCountriesAndContinents.getPrefWidth() + 20));
							translateTransitionForHBoxMainForCountriesAndContinents.setOnFinished(ev ->
							{
								hBoxMainForCountriesAndContinents.setVisible(false);
								
								greeceToggleButtonPressed();
								
								hBoxMainForGreece.setTranslateX(stage.getWidth() - hBoxMainForGreece.getLayoutX() + 20);
								translateTransitionForHBoxMainForGreece.setToX(0);
								
								translateTransitionForHBoxMainForGreece.setOnFinished(evn ->
								{
									hBoxFor5Icons.setDisable(false);
									hBoxForToggleButtons.setDisable(false);
									backButton.setDisable(false);
								});
								
								hBoxMainForGreece.setVisible(true);
								translateTransitionForHBoxMainForGreece.playFromStart();
							});
							translateTransitionForHBoxMainForCountriesAndContinents.playFromStart();
						}
						else if(oldValue == USAToggleButton)
						{
							translateTransitionForHBoxMainForUSA.setToX(-1 * (hBoxMainForUSA.getLayoutX() + hBoxMainForUSA.getPrefWidth() + 20));
							translateTransitionForHBoxMainForUSA.setOnFinished(ev ->
							{
								hBoxMainForUSA.setVisible(false);
								
								greeceToggleButtonPressed();
								
								hBoxMainForGreece.setTranslateX(stage.getWidth() - hBoxMainForGreece.getLayoutX() + 20);
								translateTransitionForHBoxMainForGreece.setToX(0);
								
								translateTransitionForHBoxMainForGreece.setOnFinished(evn ->
								{
									hBoxFor5Icons.setDisable(false);
									hBoxForToggleButtons.setDisable(false);
									backButton.setDisable(false);
								});
								
								hBoxMainForGreece.setVisible(true);
								translateTransitionForHBoxMainForGreece.playFromStart();
							});
							translateTransitionForHBoxMainForUSA.playFromStart();
						}
						else if(oldValue == attractionsToggleButton)
						{
							translateTransitionForHBoxMainForAttractions.setToX(stage.getWidth() - hBoxMainForAttractions.getLayoutX() + 20);
							translateTransitionForHBoxMainForAttractions.setOnFinished(ev ->
							{
								hBoxMainForAttractions.setVisible(false);
								
								greeceToggleButtonPressed();
								
								hBoxMainForGreece.setTranslateX(-1 * (hBoxMainForGreece.getLayoutX() + hBoxMainForGreece.getPrefWidth() + 20));
								translateTransitionForHBoxMainForGreece.setToX(0);
								
								translateTransitionForHBoxMainForGreece.setOnFinished(evn ->
								{
									hBoxFor5Icons.setDisable(false);
									hBoxForToggleButtons.setDisable(false);
									backButton.setDisable(false);
								});
								
								hBoxMainForGreece.setVisible(true);
								translateTransitionForHBoxMainForGreece.playFromStart();
							});
							translateTransitionForHBoxMainForAttractions.playFromStart();
						}
						else if(oldValue == null)
							greeceToggleButtonPressed();
					}
					else
					{
						if(oldValue == continentsAndCountriesToggleButton)
						{
							hBoxMainForCountriesAndContinents.setTranslateX(-1 * (hBoxMainForCountriesAndContinents.getLayoutX() + hBoxMainForCountriesAndContinents.getPrefWidth() + 20));
							hBoxMainForCountriesAndContinents.setVisible(false);
						}
						if(oldValue == USAToggleButton)
						{
							hBoxMainForUSA.setTranslateX(-1 * (hBoxMainForUSA.getLayoutX() + hBoxMainForUSA.getPrefWidth() + 20));
							hBoxMainForUSA.setVisible(false);
						}
						else if(oldValue == attractionsToggleButton)
						{
							hBoxMainForAttractions.setTranslateX(stage.getWidth() - hBoxMainForAttractions.getLayoutX() + 20);
							hBoxMainForAttractions.setVisible(false);
						}
						else if(oldValue == null)
							greeceToggleButtonPressed();
						
						greeceToggleButtonPressed();
						
						hBoxMainForGreece.setTranslateX(0);
						hBoxMainForGreece.setVisible(true);
						
						hBoxFor5Icons.setDisable(false);
						hBoxForToggleButtons.setDisable(false);
						backButton.setDisable(false);
					}
				}
				else if(getCategoryCurrentlyViewing() == 4)
				{
					if(animationsUsed != ANIMATIONS.NO)
					{
						if(oldValue == continentsAndCountriesToggleButton)
						{
							translateTransitionForHBoxMainForCountriesAndContinents.setToX(-1 * (hBoxMainForCountriesAndContinents.getLayoutX() + hBoxMainForCountriesAndContinents.getPrefWidth() + 20));
							translateTransitionForHBoxMainForCountriesAndContinents.setOnFinished(ev ->
							{
								hBoxMainForCountriesAndContinents.setVisible(false);
								
								attractionsToggleButtonPressed();
								
								hBoxMainForAttractions.setTranslateX(stage.getWidth() - hBoxMainForAttractions.getLayoutX() + 20);
								translateTransitionForHBoxMainForAttractions.setToX(0);
								
								translateTransitionForHBoxMainForAttractions.setOnFinished(evn ->
								{
									hBoxFor5Icons.setDisable(false);
									hBoxForToggleButtons.setDisable(false);
									backButton.setDisable(false);
								});
								
								hBoxMainForAttractions.setVisible(true);
								translateTransitionForHBoxMainForAttractions.playFromStart();
							});
							translateTransitionForHBoxMainForCountriesAndContinents.playFromStart();
						}
						else if(oldValue == USAToggleButton)
						{
							translateTransitionForHBoxMainForUSA.setToX(-1 * (hBoxMainForUSA.getLayoutX() + hBoxMainForUSA.getPrefWidth() + 20));
							translateTransitionForHBoxMainForUSA.setOnFinished(ev ->
							{
								hBoxMainForUSA.setVisible(false);
								
								attractionsToggleButtonPressed();
								
								hBoxMainForAttractions.setTranslateX(stage.getWidth() - hBoxMainForAttractions.getLayoutX() + 20);
								translateTransitionForHBoxMainForAttractions.setToX(0);
								
								translateTransitionForHBoxMainForAttractions.setOnFinished(evn ->
								{
									hBoxFor5Icons.setDisable(false);
									hBoxForToggleButtons.setDisable(false);
									backButton.setDisable(false);
								});
								
								hBoxMainForAttractions.setVisible(true);
								translateTransitionForHBoxMainForAttractions.playFromStart();
							});
							translateTransitionForHBoxMainForUSA.playFromStart();
						}
						else if(oldValue == greeceToggleButton)
						{
							translateTransitionForHBoxMainForGreece.setToX(-1 * (hBoxMainForGreece.getLayoutX() + hBoxMainForGreece.getPrefWidth() + 20));
							translateTransitionForHBoxMainForGreece.setOnFinished(ev ->
							{
								hBoxMainForGreece.setVisible(false);
								
								attractionsToggleButtonPressed();
								
								hBoxMainForAttractions.setTranslateX(stage.getWidth() - hBoxMainForAttractions.getLayoutX() + 20);
								translateTransitionForHBoxMainForAttractions.setToX(0);
								
								translateTransitionForHBoxMainForAttractions.setOnFinished(evn ->
								{
									hBoxFor5Icons.setDisable(false);
									hBoxForToggleButtons.setDisable(false);
									backButton.setDisable(false);
								});
								
								hBoxMainForAttractions.setVisible(true);
								translateTransitionForHBoxMainForAttractions.playFromStart();
							});
							translateTransitionForHBoxMainForGreece.playFromStart();
						}
						else if(oldValue == null)
							attractionsToggleButtonPressed();
					}
					else
					{
						if(oldValue == continentsAndCountriesToggleButton)
						{
							hBoxMainForCountriesAndContinents.setTranslateX(-1 * (hBoxMainForCountriesAndContinents.getLayoutX() + hBoxMainForCountriesAndContinents.getPrefWidth() + 20));
							hBoxMainForCountriesAndContinents.setVisible(false);
						}
						if(oldValue == USAToggleButton)
						{
							hBoxMainForUSA.setTranslateX(-1 * (hBoxMainForUSA.getLayoutX() + hBoxMainForUSA.getPrefWidth() + 20));
							hBoxMainForUSA.setVisible(false);
						}
						else if(oldValue == greeceToggleButton)
						{
							hBoxMainForGreece.setTranslateX(-1 * (hBoxMainForGreece.getLayoutX() + hBoxMainForGreece.getPrefWidth() + 20));
							hBoxMainForGreece.setVisible(false);
						}
						else if(oldValue == null)
							attractionsToggleButtonPressed();
						
						attractionsToggleButtonPressed();
						
						hBoxMainForAttractions.setTranslateX(0);
						hBoxMainForAttractions.setVisible(true);
						
						hBoxFor5Icons.setDisable(false);
						hBoxForToggleButtons.setDisable(false);
						backButton.setDisable(false);
					}
				}
			}
		});
		
		continentsAndCountriesToggleButton.setOnMouseEntered(e -> playHoverSound());
		
		USAToggleButton.setOnMouseEntered(e -> playHoverSound());
		
		greeceToggleButton.setOnMouseEntered(e -> playHoverSound());
		
		attractionsToggleButton.setOnMouseEntered(e -> playHoverSound());
		
		optionsForCountriesAndContinentsComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
		{
			nodesPane.requestFocus();
			
			if(oldValue != null && newValue != null)
			{
				setIndexInOptionsForCountriesAndContinents(optionsForCountriesAndContinentsComboBox.getSelectionModel().getSelectedIndex());
				if (animationsUsed != ANIMATIONS.NO)
				{
					hBoxFor5Icons.setDisable(true);
					hBoxForToggleButtons.setDisable(true);
					backButton.setDisable(true);
					
					scaleTransitionForHBoxMainForCountriesAndContinents.setToX(0);
					scaleTransitionForHBoxMainForCountriesAndContinents.setToY(0);
					scaleTransitionForHBoxMainForCountriesAndContinents.setOnFinished(e ->
					{
						countriesAndContinentsToggleButtonPressed();
						
						scaleTransitionForHBoxMainForCountriesAndContinents.setToX(1);
						scaleTransitionForHBoxMainForCountriesAndContinents.setToY(1);
						scaleTransitionForHBoxMainForCountriesAndContinents.setOnFinished(ev ->
						{
							scaleTransitionForHBoxMainForCountriesAndContinents.setOnFinished(eve -> {});
							
							hBoxFor5Icons.setDisable(false);
							hBoxForToggleButtons.setDisable(false);
							backButton.setDisable(false);
						});
						
						playMaximizeSound();
						scaleTransitionForHBoxMainForCountriesAndContinents.playFromStart();
					});
					playMinimizeSound();
					scaleTransitionForHBoxMainForCountriesAndContinents.playFromStart();
				}
				else countriesAndContinentsToggleButtonPressed();
			}
		});
		
		optionsForUSAComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
		{
			nodesPane.requestFocus();
			
			if(oldValue != null && newValue != null)
			{
				setIndexInOptionsForUSA(optionsForUSAComboBox.getSelectionModel().getSelectedIndex());
				
				if(animationsUsed != ANIMATIONS.NO)
				{
					hBoxFor5Icons.setDisable(true);
					hBoxForToggleButtons.setDisable(true);
					backButton.setDisable(true);
					
					scaleTransitionForHBoxMainForUSA.setToX(0);
					scaleTransitionForHBoxMainForUSA.setToY(0);
					scaleTransitionForHBoxMainForUSA.setOnFinished(e ->
					{
						usaStatesToggleButtonPressed();
						
						scaleTransitionForHBoxMainForUSA.setToX(1);
						scaleTransitionForHBoxMainForUSA.setToY(1);
						scaleTransitionForHBoxMainForUSA.setOnFinished(ev ->
						{
							scaleTransitionForHBoxMainForUSA.setOnFinished(eve -> {});
							
							hBoxFor5Icons.setDisable(false);
							hBoxForToggleButtons.setDisable(false);
							backButton.setDisable(false);
						});
						
						playMaximizeSound();
						scaleTransitionForHBoxMainForUSA.playFromStart();
					});
					playMinimizeSound();
					scaleTransitionForHBoxMainForUSA.playFromStart();
				}
				else usaStatesToggleButtonPressed();
			}
		});
		
		optionsForGreeceComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
		{
			nodesPane.requestFocus();
			
			if(oldValue != null && newValue != null)
			{
				setIndexInOptionsForGreece(optionsForGreeceComboBox.getSelectionModel().getSelectedIndex());
				
				if (animationsUsed != ANIMATIONS.NO)
				{
					hBoxFor5Icons.setDisable(true);
					hBoxForToggleButtons.setDisable(true);
					backButton.setDisable(true);
					
					scaleTransitionForHBoxMainForGreece.setToX(0);
					scaleTransitionForHBoxMainForGreece.setToY(0);
					scaleTransitionForHBoxMainForGreece.setOnFinished(e ->
					{
						greeceToggleButtonPressed();
						
						scaleTransitionForHBoxMainForGreece.setToX(1);
						scaleTransitionForHBoxMainForGreece.setToY(1);
						scaleTransitionForHBoxMainForGreece.setOnFinished(ev ->
						{
							scaleTransitionForHBoxMainForGreece.setOnFinished(eve ->
							{
							});
							
							hBoxFor5Icons.setDisable(false);
							hBoxForToggleButtons.setDisable(false);
							backButton.setDisable(false);
						});
						
						playMaximizeSound();
						scaleTransitionForHBoxMainForGreece.playFromStart();
					});
					playMinimizeSound();
					scaleTransitionForHBoxMainForGreece.playFromStart();
				}
				else greeceToggleButtonPressed();
			}
		});
		
		optionsForAttractionsComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
		{
			nodesPane.requestFocus();
			
			if(oldValue != null && newValue != null)
			{
				setIndexInOptionsForAttractions(optionsForAttractionsComboBox.getSelectionModel().getSelectedIndex());
				if (animationsUsed != ANIMATIONS.NO)
				{
					hBoxFor5Icons.setDisable(true);
					hBoxForToggleButtons.setDisable(true);
					backButton.setDisable(true);
					
					scaleTransitionForHBoxMainForAttractions.setToX(0);
					scaleTransitionForHBoxMainForAttractions.setToY(0);
					scaleTransitionForHBoxMainForAttractions.setOnFinished(e ->
					{
						attractionsToggleButtonPressed();
						
						scaleTransitionForHBoxMainForAttractions.setToX(1);
						scaleTransitionForHBoxMainForAttractions.setToY(1);
						scaleTransitionForHBoxMainForAttractions.setOnFinished(ev ->
						{
							scaleTransitionForHBoxMainForAttractions.setOnFinished(eve -> {});
							
							hBoxFor5Icons.setDisable(false);
							hBoxForToggleButtons.setDisable(false);
							backButton.setDisable(false);
						});
						
						playMaximizeSound();
						scaleTransitionForHBoxMainForAttractions.playFromStart();
					});
					playMinimizeSound();
					scaleTransitionForHBoxMainForAttractions.playFromStart();
				}
				else attractionsToggleButtonPressed();
			}
		});
		
		//COUNTRIES AND CONTINENTS MOUSE HELPERS AND LISTENERS---------------------------------------
		MouseStationaryHelper flagLabelMouseHelperForCountriesAndContinents = new MouseStationaryHelper(flagLabelForCountriesAndContinents, java.time.Duration.ofMillis(100));
		flagLabelMouseHelperForCountriesAndContinents.events().subscribe(either -> either.exec(
				pos ->
				{
					if(bigImageStatus == 0 && flagForCountriesAndContinentsImageSmall.getImage() != null)
					{
						if(getIndexInOptionsForCountriesAndContinents() == 0)
							setupBigImagePreview(BigImageType.FLAG_FOR_COUNTRIES, countriesSortList.get(getIndexInListViewForCountriesAndContinents()));
						else if(getIndexInOptionsForCountriesAndContinents() == 1)
							setupBigImagePreview(BigImageType.FLAG_FOR_COUNTRIES, capitalsOfCountriesSortList.get(getIndexInListViewForCountriesAndContinents()));
					}
				},
				stop -> {}));
		
		MouseStationaryHelper coatOfArmsLabelMouseHelperForCountriesAndContinents = new MouseStationaryHelper(coatOfArmsLabelForCountriesAndContinents, java.time.Duration.ofMillis(100));
		coatOfArmsLabelMouseHelperForCountriesAndContinents.events().subscribe(either -> either.exec(
				pos ->
				{
					if(bigImageStatus == 0 && coatOfArmsForCountriesAndContinentsImageSmall.getImage() != null)
					{
						if(getIndexInOptionsForCountriesAndContinents() == 0)
							setupBigImagePreview(BigImageType.COAT_OF_ARMS_FOR_COUNTRIES, countriesSortList.get(getIndexInListViewForCountriesAndContinents()));
						else if(getIndexInOptionsForCountriesAndContinents() == 1)
							setupBigImagePreview(BigImageType.COAT_OF_ARMS_FOR_COUNTRIES, capitalsOfCountriesSortList.get(getIndexInListViewForCountriesAndContinents()));
					}
				},
				stop -> {}));
		
		MouseStationaryHelper locationLabelMouseHelperForCountriesAndContinents = new MouseStationaryHelper(locationLabelForCountriesAndContinents, java.time.Duration.ofMillis(100));
		locationLabelMouseHelperForCountriesAndContinents.events().subscribe(either -> either.exec(
				pos ->
				{
					if(bigImageStatus == 0 && locationForCountriesAndContinentsImageSmall.getImage() != null)
					{
						if(getIndexInOptionsForCountriesAndContinents() == 0)
							setupBigImagePreview(BigImageType.LOCATION_FOR_COUNTRIES, countriesSortList.get(getIndexInListViewForCountriesAndContinents()));
						else if(getIndexInOptionsForCountriesAndContinents() == 1)
							setupBigImagePreview(BigImageType.LOCATION_FOR_COUNTRIES, capitalsOfCountriesSortList.get(getIndexInListViewForCountriesAndContinents()));
						else if(getIndexInOptionsForCountriesAndContinents() == 5)
							setupBigImagePreview(BigImageType.LOCATION_FOR_CONTINENTS, continentsSortList.get(getIndexInListViewForCountriesAndContinents()));
					}
				},
				stop -> {}));
		
		flagLabelForCountriesAndContinents.setOnMouseClicked(e ->
		{
			if(flagForCountriesAndContinentsImageSmall.getImage() != null)
			{
				if(getIndexInOptionsForCountriesAndContinents() == 0)
					setupBigImageNormal(BigImageType.FLAG_FOR_COUNTRIES, countriesSortList.get(getIndexInListViewForCountriesAndContinents()));
				else if(getIndexInOptionsForCountriesAndContinents() == 1)
					setupBigImageNormal(BigImageType.FLAG_FOR_COUNTRIES, capitalsOfCountriesSortList.get(getIndexInListViewForCountriesAndContinents()));
			}
		});
		
		flagLabelForCountriesAndContinents.setOnMouseExited(e ->
		{
			if(bigImageStatus == 1 && flagForCountriesAndContinentsImageSmall.getImage() != null)
			{
				if(e.getSceneX() < flagForCountriesAndContinentsImageSmall.localToScene(flagForCountriesAndContinentsImageSmall.getBoundsInLocal()).getMinX()
				   || e.getSceneX() > flagForCountriesAndContinentsImageSmall.localToScene(flagForCountriesAndContinentsImageSmall.getBoundsInLocal()).getMaxX()
				   || e.getSceneY() < flagForCountriesAndContinentsImageSmall.localToScene(flagForCountriesAndContinentsImageSmall.getBoundsInLocal()).getMinY()
				   || e.getSceneY() > flagForCountriesAndContinentsImageSmall.localToScene(flagForCountriesAndContinentsImageSmall.getBoundsInLocal()).getMaxY())
					closeBigImage();
			}
		});
		
		coatOfArmsLabelForCountriesAndContinents.setOnMouseClicked(e ->
		{
			if(coatOfArmsForCountriesAndContinentsImageSmall.getImage() != null)
			{
				if(getIndexInOptionsForCountriesAndContinents() == 0)
					setupBigImageNormal(BigImageType.COAT_OF_ARMS_FOR_COUNTRIES, countriesSortList.get(getIndexInListViewForCountriesAndContinents()));
				else if(getIndexInOptionsForCountriesAndContinents() == 1)
					setupBigImageNormal(BigImageType.COAT_OF_ARMS_FOR_COUNTRIES, capitalsOfCountriesSortList.get(getIndexInListViewForCountriesAndContinents()));
			}
		});
		
		coatOfArmsLabelForCountriesAndContinents.setOnMouseExited(e ->
		{
			if(bigImageStatus == 1 && coatOfArmsForCountriesAndContinentsImageSmall.getImage() != null)
			{
				if(e.getSceneX() < coatOfArmsForCountriesAndContinentsImageSmall.localToScene(coatOfArmsForCountriesAndContinentsImageSmall.getBoundsInLocal()).getMinX()
				   || e.getSceneX() + 5 > coatOfArmsForCountriesAndContinentsImageSmall.localToScene(coatOfArmsForCountriesAndContinentsImageSmall.getBoundsInLocal()).getMaxX()
				   || e.getSceneY() < coatOfArmsForCountriesAndContinentsImageSmall.localToScene(coatOfArmsForCountriesAndContinentsImageSmall.getBoundsInLocal()).getMinY()
				   || e.getSceneY() > coatOfArmsForCountriesAndContinentsImageSmall.localToScene(coatOfArmsForCountriesAndContinentsImageSmall.getBoundsInLocal()).getMaxY())
					closeBigImage();
			}
		});
		
		locationLabelForCountriesAndContinents.setOnMouseClicked(e ->
		{
			if(locationForCountriesAndContinentsImageSmall.getImage() != null)
			{
				if(getIndexInOptionsForCountriesAndContinents() == 0)
					setupBigImageNormal(BigImageType.LOCATION_FOR_COUNTRIES, countriesSortList.get(getIndexInListViewForCountriesAndContinents()));
				else if(getIndexInOptionsForCountriesAndContinents() == 1)
					setupBigImageNormal(BigImageType.LOCATION_FOR_COUNTRIES, capitalsOfCountriesSortList.get(getIndexInListViewForCountriesAndContinents()));
				else if(getIndexInOptionsForCountriesAndContinents() == 5)
					setupBigImageNormal(BigImageType.LOCATION_FOR_CONTINENTS, continentsSortList.get(getIndexInListViewForCountriesAndContinents()));
			}
		});
		
		locationLabelForCountriesAndContinents.setOnMouseExited(e ->
		{
			if (bigImageStatus == 1 && locationForCountriesAndContinentsImageSmall.getImage() != null)
			{
				if(e.getSceneX() < locationForCountriesAndContinentsImageSmall.localToScene(locationForCountriesAndContinentsImageSmall.getBoundsInLocal()).getMinX()
				   || e.getSceneX() > locationForCountriesAndContinentsImageSmall.localToScene(locationForCountriesAndContinentsImageSmall.getBoundsInLocal()).getMaxX()
				   || e.getSceneY() < locationForCountriesAndContinentsImageSmall.localToScene(locationForCountriesAndContinentsImageSmall.getBoundsInLocal()).getMinY()
				   || e.getSceneY() > locationForCountriesAndContinentsImageSmall.localToScene(locationForCountriesAndContinentsImageSmall.getBoundsInLocal()).getMaxY())
					closeBigImage();
			}
		});
		
		listViewForCountriesAndContinents.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
		{
			if (newValue.intValue() >= 0)
			{
				setIndexInListViewForCountriesAndContinents(newValue.intValue());
				
				if((getIndexInOptionsForCountriesAndContinents() == 0 ||
				    getIndexInOptionsForCountriesAndContinents() == 1) &&
				   newValue.intValue() < NUM_ALL_COUNTRIES)
				{
					int index;
					if(getIndexInOptionsForCountriesAndContinents() == 0)
						index = countriesSortList.get(newValue.intValue());
					else index = capitalsOfCountriesSortList.get(newValue.intValue());
					
					StringBuilder sb;
					
					gridPaneLabelsForCountriesAndContinents[1][1].setText(countries[index].getCapitalName());
					
					sb = new StringBuilder();
					for (String s : countries[index].getLargestCities())
						sb.append(s + ", ");
					sb.deleteCharAt(sb.length() - 1);
					sb.deleteCharAt(sb.length() - 1);
					gridPaneLabelsForCountriesAndContinents[2][1].setText(sb.toString());
					
					if(countries[index].getLargestCities().length < 2) gridPaneLabelsForCountriesAndContinents[2][0].setText(" " + languageResourceBundle.getString("largestCityLabel"));
					else gridPaneLabelsForCountriesAndContinents[2][0].setText(" " + languageResourceBundle.getString("largestCitiesLabel"));
					
					gridPaneLabelsForCountriesAndContinents[4][1].setText(countries[index].getContinent());
					
					gridPaneLabelsForCountriesAndContinents[5][1].setText(countries[index].getLanguagesString());
					
					sb = new StringBuilder();
					sb.append(countries[index].getArea().getAreaBasedOnLocaleToString());
					
					if (countries[index].getArea().getBordersInKilometers() == 0) gridPaneLabelsForCountriesAndContinents[8][1].setText("-");
					else gridPaneLabelsForCountriesAndContinents[8][1].setText(countries[index].getArea().getBordersBasedOnLocaleToString());
					
					if (countries[index].getArea().getCoastlineInKilometers() == 0) gridPaneLabelsForCountriesAndContinents[9][1].setText("-");
					else gridPaneLabelsForCountriesAndContinents[9][1].setText(countries[index].getArea().getCoastlineBasedOnLocaleToString());
					
					if (countries[index].getArea().getPercentOfWater() == 0) gridPaneLabelsForCountriesAndContinents[10][1].setText("0%");
					else gridPaneLabelsForCountriesAndContinents[10][1].setText(String.valueOf(countries[index].getArea().getPercentOfWater()) + "%");
					
					gridPaneLabelsForCountriesAndContinents[12][1].setText(countries[index].getPopulation().getPopulationDensityBasedOnLocaleToString());
					
					gridPaneLabelsForCountriesAndContinents[13][1].setText(
							countries[index].getCurrency().getName() + " (" + countries[index].getCurrency().getSymbol() + ") - " +
							countries[index].getCurrency().getISOCode()
					                                                      );
					
					if (getCurrentLanguage() == LANGUAGE.GREEK)
					{
						gridPaneLabelsForCountriesAndContinents[0][1].setText(countries[index].getNameInGreek());
						
						if (countries[index].isSovereignState()) gridPaneLabelsForCountriesAndContinents[3][1].setText("Ναι");
						else gridPaneLabelsForCountriesAndContinents[3][1].setText(countries[index].getSovereignState());
						
						if (countries[index].isIslandCountry()) gridPaneLabelsForCountriesAndContinents[6][1].setText("Είναι νησιωτικό κράτος");
						else if (countries[index].hasSea()) gridPaneLabelsForCountriesAndContinents[6][1].setText("Βρέχεται από θάλασσα");
						else gridPaneLabelsForCountriesAndContinents[6][1].setText("Δε βρέχεται από θάλασσα");
						
						if (countries[index].getArea().getGlobalRanking() != 0) sb.append(" (" + String.valueOf(countries[index].getArea().getGlobalRanking()) + "η)");
						gridPaneLabelsForCountriesAndContinents[7][1].setText(sb.toString());
						
						sb = new StringBuilder();
						sb.append(numberFormatForUI.format(countries[index].getPopulation().getPopulation())).append(" κάτοικοι");
						if (countries[index].getPopulation().getGlobalRanking() != 0) sb.append(" (" + String.valueOf(countries[index].getPopulation().getGlobalRanking()) + "η)");
						gridPaneLabelsForCountriesAndContinents[11][1].setText(sb.toString());
						
						sb = new StringBuilder();
						sb.append(countries[index].getCurrency().getSubdivision());
						if (sb.length() < 20) sb.append(" (").append(countries[index].getCurrency().getNumberOfSubDivisions()).append(")");
						gridPaneLabelsForCountriesAndContinents[14][1].setText(sb.toString());
					}
					else
					{
						gridPaneLabelsForCountriesAndContinents[0][1].setText(countries[index].getNameInEnglish());
						
						if (countries[index].isSovereignState()) gridPaneLabelsForCountriesAndContinents[3][1].setText("Yes");
						else gridPaneLabelsForCountriesAndContinents[3][1].setText(countries[index].getSovereignState());
						
						if (countries[index].isIslandCountry()) gridPaneLabelsForCountriesAndContinents[6][1].setText("It's an island country");
						else if (countries[index].hasSea()) gridPaneLabelsForCountriesAndContinents[6][1].setText("It is surrounded by sea");
						else gridPaneLabelsForCountriesAndContinents[6][1].setText("It isn't surrounded by sea");
						
						if (countries[index].getArea().getGlobalRanking() != 0)
						{
							sb.append(" (").append(String.valueOf(countries[index].getArea().getGlobalRanking()));
							if (countries[index].getArea().getGlobalRanking() % 10 == 1) sb.append("st)");
							else if (countries[index].getArea().getGlobalRanking() % 10 == 2) sb.append("nd)");
							else if (countries[index].getArea().getGlobalRanking() % 10 == 3) sb.append("rd)");
							else sb.append("th)");
						}
						gridPaneLabelsForCountriesAndContinents[7][1].setText(sb.toString());
						
						sb = new StringBuilder();
						sb.append(numberFormatForUI.format(countries[index].getPopulation().getPopulation())).append(" inhabitants");
						if (countries[index].getPopulation().getGlobalRanking() != 0)
						{
							sb.append(" (").append(String.valueOf(countries[index].getPopulation().getGlobalRanking()));
							if (countries[index].getPopulation().getGlobalRanking() % 10 == 1) sb.append("st)");
							else if (countries[index].getPopulation().getGlobalRanking() % 10 == 2) sb.append("nd)");
							else if (countries[index].getPopulation().getGlobalRanking() % 10 == 3) sb.append("rd)");
							else sb.append("th)");
						}
						gridPaneLabelsForCountriesAndContinents[11][1].setText(sb.toString());
						
						sb = new StringBuilder();
						sb.append(countries[index].getCurrency().getSubdivision());
						if (sb.length() < 20) sb.append(" (" + countries[index].getCurrency().getNumberOfSubDivisions() + ")");
						gridPaneLabelsForCountriesAndContinents[14][1].setText(sb.toString());
					}
                    for(int i = 0; i < gridPaneLabelsForCountriesAndContinents.length; i++) gridPaneTooltipsForCountriesAndContinents[i].setText(gridPaneLabelsForCountriesAndContinents[i][1].getText());

					//SET IMAGES
					if(flagForCountriesAndContinentsImageSmall.getFitWidth() <= MAX_WIDTH_FOR_X250_IMAGES)
					{
						flagForCountriesAndContinentsImageSmall.setImage(getImage(ImageType.COUNTRY_FLAG, "x250", countries[index].getNameInGreek(), true));
						coatOfArmsForCountriesAndContinentsImageSmall.setImage(getImage(ImageType.COUNTRY_COAT_OF_ARMS, "x250", countries[index].getNameInGreek(), true));
					}
					else
					{
						flagForCountriesAndContinentsImageSmall.setImage(getImage(ImageType.COUNTRY_FLAG, "x500", countries[index].getNameInGreek(), true));
						coatOfArmsForCountriesAndContinentsImageSmall.setImage(getImage(ImageType.COUNTRY_COAT_OF_ARMS, "x500", countries[index].getNameInGreek(), true));
					}
					if(locationForCountriesAndContinentsImageSmall.getFitWidth() <= MAX_WIDTH_FOR_X250_IMAGES)
						locationForCountriesAndContinentsImageSmall.setImage(getImage(ImageType.COUNTRY_LOCATION, "x250", countries[index].getNameInGreek(), true));
					else if(locationForCountriesAndContinentsImageSmall.getFitWidth() <= MAX_WIDTH_FOR_X500_IMAGES)
						locationForCountriesAndContinentsImageSmall.setImage(getImage(ImageType.COUNTRY_LOCATION, "x500", countries[index].getNameInGreek(), true));
					else locationForCountriesAndContinentsImageSmall.setImage(getImage(ImageType.COUNTRY_LOCATION, "x1000", countries[index].getNameInGreek(), true));
				}
				else if(getIndexInOptionsForCountriesAndContinents() == 5 && newValue.intValue() < NUM_ALL_CONTINENTS)
				{
					int index = continentsSortList.get(newValue.intValue());
					
					if(continents[index].getNameInGreek().equals("Ανταρκτική"))
					{
                        gridPaneLabelsForCountriesAndContinents[2][1].setCursor(Cursor.MOVE);
                        gridPaneLabelsForCountriesAndContinents[3][1].setCursor(Cursor.MOVE);
                        gridPaneLabelsForCountriesAndContinents[4][1].setCursor(Cursor.MOVE);

						if(getCurrentLanguage() == LANGUAGE.GREEK) gridPaneLabelsForCountriesAndContinents[0][1].setText(continents[index].getNameInGreek());
						else gridPaneLabelsForCountriesAndContinents[0][1].setText(continents[index].getNameInEnglish());
						
						gridPaneLabelsForCountriesAndContinents[1][1].setText("-");
						gridPaneLabelsForCountriesAndContinents[2][1].setText("-");
						gridPaneLabelsForCountriesAndContinents[3][1].setText("-");
						gridPaneLabelsForCountriesAndContinents[4][1].setText("-");
						gridPaneLabelsForCountriesAndContinents[5][1].setText("-");
						gridPaneLabelsForCountriesAndContinents[6][1].setText(continents[index].getPopulationString());
						gridPaneLabelsForCountriesAndContinents[7][1].setText("-");
						gridPaneLabelsForCountriesAndContinents[8][1].setText(continents[index].getAreaBasedOnLocaleToString() + " (" + String.valueOf(continents[index].getGlobalAreaRanking()) + "η)");
						
						gridPaneLabelsForCountriesAndContinents[9][1].setText(continents[index].getCoastlineBasedOnLocaleToString());
						
						gridPaneLabelsForCountriesAndContinents[10][1].setText(String.valueOf(continents[index].getPercentOfEarth()) + "%");
						gridPaneLabelsForCountriesAndContinents[11][1].setText(String.valueOf(continents[index].getPercentOfLandOfEarth()) + "%");
						
						gridPaneLabelsForCountriesAndContinents[12][1].setText(continents[index].getHighestPoint());
						gridPaneLabelsForCountriesAndContinents[13][1].setText(continents[index].getLowestPoint());
						gridPaneLabelsForCountriesAndContinents[14][1].setText("-");
						gridPaneLabelsForCountriesAndContinents[15][1].setText("-");
						gridPaneLabelsForCountriesAndContinents[16][1].setText(continents[index].getTimeZones());

                        for(int i = 0; i < gridPaneLabelsForCountriesAndContinents.length; i++)
                            gridPaneTooltipsForCountriesAndContinents[i].setText(gridPaneLabelsForCountriesAndContinents[i][1].getText());
					}
					else
					{
                        gridPaneLabelsForCountriesAndContinents[2][1].setCursor(Cursor.HAND);
                        gridPaneLabelsForCountriesAndContinents[3][1].setCursor(Cursor.HAND);
                        gridPaneLabelsForCountriesAndContinents[4][1].setCursor(Cursor.HAND);

                        gridPaneLabelsForCountriesAndContinents[2][1].setTooltip(null);
                        gridPaneLabelsForCountriesAndContinents[3][1].setTooltip(null);
                        gridPaneLabelsForCountriesAndContinents[4][1].setTooltip(null);

						gridPaneLabelsForCountriesAndContinents[1][1].setText(continents[index].getNumberOfCountries());

						gridPaneLabelsForCountriesAndContinents[5][1].setText(continents[index].getLanguages());
						
						gridPaneLabelsForCountriesAndContinents[7][1].setText(continents[index].getPopulationDensityBasedOnLocaleToString());
						
						gridPaneLabelsForCountriesAndContinents[9][1].setText(continents[index].getCoastlineBasedOnLocaleToString());
						
						gridPaneLabelsForCountriesAndContinents[10][1].setText(String.valueOf(continents[index].getPercentOfEarth()) + "%");
						gridPaneLabelsForCountriesAndContinents[11][1].setText(String.valueOf(continents[index].getPercentOfLandOfEarth()) + "%");
						
						gridPaneLabelsForCountriesAndContinents[12][1].setText(continents[index].getHighestPoint());
						gridPaneLabelsForCountriesAndContinents[13][1].setText(continents[index].getLowestPoint());
						gridPaneLabelsForCountriesAndContinents[14][1].setText(continents[index].getLongestRiver());
						gridPaneLabelsForCountriesAndContinents[15][1].setText(continents[index].getLargestLake());
						gridPaneLabelsForCountriesAndContinents[16][1].setText(continents[index].getTimeZones());
						
						if(getCurrentLanguage() == LANGUAGE.GREEK)
						{
							gridPaneLabelsForCountriesAndContinents[0][1].setText(continents[index].getNameInGreek());

                            gridPaneLabelsForCountriesAndContinents[2][1].setText("Κάνε κλικ για να τις δεις");
                            gridPaneLabelsForCountriesAndContinents[3][1].setText("Κάνε κλικ για να τις δεις");
                            gridPaneLabelsForCountriesAndContinents[4][1].setText("Κάνε κλικ για να τις δεις");

							gridPaneLabelsForCountriesAndContinents[6][1].setText(String.valueOf(numberFormatForUI.format(continents[index].getPopulation())) + " κάτοικοι (" +
							                                                      String.valueOf(continents[index].getGlobalPopulationRanking()) + "η)");
							
							gridPaneLabelsForCountriesAndContinents[8][1].setText(continents[index].getAreaBasedOnLocaleToString() + " (" +
									String.valueOf(continents[index].getGlobalAreaRanking()) + "η)");
						}
						else
						{
							gridPaneLabelsForCountriesAndContinents[0][1].setText(continents[index].getNameInEnglish());

                            gridPaneLabelsForCountriesAndContinents[2][1].setText("Click here to see them");
                            gridPaneLabelsForCountriesAndContinents[3][1].setText("Click here to see them");
                            gridPaneLabelsForCountriesAndContinents[4][1].setText("Click here to see them");

                            StringBuilder sb;

							sb = new StringBuilder();
							sb.append(String.valueOf(numberFormatForUI.format(continents[index].getPopulation())) + " inhabitants (" +
							          String.valueOf(continents[index].getGlobalPopulationRanking()));
							if (continents[index].getGlobalPopulationRanking() == 1) sb.append("st)");
							else if (continents[index].getGlobalPopulationRanking() == 2) sb.append("nd)");
							else if (continents[index].getGlobalPopulationRanking() == 3) sb.append("rd)");
							else sb.append("th)");
							gridPaneLabelsForCountriesAndContinents[6][1].setText(sb.toString());
							
							sb = new StringBuilder();
							sb.append(continents[index].getAreaBasedOnLocaleToString() + " (" + String.valueOf(continents[index].getGlobalAreaRanking()));
							if (continents[index].getGlobalAreaRanking() == 1) sb.append("st)");
							else if (continents[index].getGlobalAreaRanking() == 2) sb.append("nd)");
							else if (continents[index].getGlobalAreaRanking() == 3) sb.append("rd)");
							else sb.append("th)");
							gridPaneLabelsForCountriesAndContinents[8][1].setText(sb.toString());
						}
                        for(int i = 0; i < gridPaneLabelsForCountriesAndContinents.length; i++)
                            gridPaneTooltipsForCountriesAndContinents[i].setText(gridPaneLabelsForCountriesAndContinents[i][1].getText());
					}

					if (locationLabelForCountriesAndContinents.getPrefWidth() <= MAX_WIDTH_FOR_X250_IMAGES)
						locationForCountriesAndContinentsImageSmall.setImage(getImage(ImageType.CONTINENT_LOCATION, "x250", continents[index].getNameInGreek(), true));
					else if (locationLabelForCountriesAndContinents.getPrefWidth() <= MAX_WIDTH_FOR_X500_IMAGES)
						locationForCountriesAndContinentsImageSmall.setImage(getImage(ImageType.CONTINENT_LOCATION, "x500", continents[index].getNameInGreek(), true));
					else locationForCountriesAndContinentsImageSmall.setImage(getImage(ImageType.CONTINENT_LOCATION, "x1000", continents[index].getNameInGreek(), true));
				}
			}
		});

        gridPaneLabelsForCountriesAndContinents[2][1].setOnMouseClicked(e ->
        {
            if(getIndexInOptionsForCountriesAndContinents() == 5 && getIndexInListViewForCountriesAndContinents() > 0)
            {
                setupMoreInfo(continents[continentsSortList.get(getIndexInListViewForCountriesAndContinents())].getLargestCountriesByArea());
            }
        });
		
		gridPaneLabelsForCountriesAndContinents[3][1].setOnMouseClicked(e ->
        {
            if(getIndexInOptionsForCountriesAndContinents() == 5 && getIndexInListViewForCountriesAndContinents() > 0)
            {
                setupMoreInfo(continents[continentsSortList.get(getIndexInListViewForCountriesAndContinents())].getLargestCountriesByPopulation());
            }
        });
		
		gridPaneLabelsForCountriesAndContinents[4][1].setOnMouseClicked(e ->
        {
            if(getIndexInOptionsForCountriesAndContinents() == 5 && getIndexInListViewForCountriesAndContinents() > 0)
            {
                setupMoreInfo(continents[continentsSortList.get(getIndexInListViewForCountriesAndContinents())].getLargestCities());
            }
        });
		
		//USA MOUSE HELPERS AND LISTENERS---------------------------------------
		MouseStationaryHelper flagLabelMouseHelperForUSA = new MouseStationaryHelper(flagLabelForUSA, java.time.Duration.ofMillis(100));
		flagLabelMouseHelperForUSA.events().subscribe(either -> either.exec(
				pos ->
				{
					if(bigImageStatus == 0 && flagForUSAImageSmall.getImage() != null)
					{
						if(getIndexInOptionsForUSA() == 0)
							setupBigImagePreview(BigImageType.FLAG_FOR_USA, statesOfUSASortList.get(getIndexInListViewForUSA()));
						else if(getIndexInOptionsForUSA() == 1)
							setupBigImagePreview(BigImageType.FLAG_FOR_USA, capitalsOfStatesSortList.get(getIndexInListViewForUSA()));
					}
				},
				stop -> {}));
		
		MouseStationaryHelper sealLabelMouseHelperForUSA = new MouseStationaryHelper(sealLabelForUSA, java.time.Duration.ofMillis(100));
		sealLabelMouseHelperForUSA.events().subscribe(either -> either.exec(
				pos ->
				{
					if(bigImageStatus == 0 && sealForUSAImageSmall.getImage() != null)
					{
						if(getIndexInOptionsForUSA() == 0)
							setupBigImagePreview(BigImageType.SEAL_FOR_USA, statesOfUSASortList.get(getIndexInListViewForUSA()));
						else if(getIndexInOptionsForUSA() == 1)
							setupBigImagePreview(BigImageType.SEAL_FOR_USA, capitalsOfStatesSortList.get(getIndexInListViewForUSA()));
					}
				},
				stop -> {}));
		
		MouseStationaryHelper locationLabelMouseHelperForUSA = new MouseStationaryHelper(locationLabelForUSA, java.time.Duration.ofMillis(100));
		locationLabelMouseHelperForUSA.events().subscribe(either -> either.exec(
				pos ->
				{
					if(bigImageStatus == 0 && locationForUSAImageSmall.getImage() != null)
					{
						if(getIndexInOptionsForUSA() == 0)
							setupBigImagePreview(BigImageType.LOCATION_FOR_USA, statesOfUSASortList.get(getIndexInListViewForUSA()));
						else if(getIndexInOptionsForUSA() == 1)
							setupBigImagePreview(BigImageType.LOCATION_FOR_USA, capitalsOfStatesSortList.get(getIndexInListViewForUSA()));
					}
				},
				stop -> {}));
		
		flagLabelForUSA.setOnMouseClicked(e ->
		{
			if(flagForUSAImageSmall.getImage() != null)
			{
				if(getIndexInOptionsForUSA() == 0)
					setupBigImageNormal(BigImageType.FLAG_FOR_USA, statesOfUSASortList.get(getIndexInListViewForUSA()));
				else if(getIndexInOptionsForUSA() == 1)
					setupBigImageNormal(BigImageType.FLAG_FOR_USA, capitalsOfStatesSortList.get(getIndexInListViewForUSA()));
			}
		});
		
		flagLabelForUSA.setOnMouseExited(e ->
		{
			if(bigImageStatus == 1 && flagForUSAImageSmall.getImage() != null)
			{
				if(e.getSceneX() < flagForUSAImageSmall.localToScene(flagForUSAImageSmall.getBoundsInLocal()).getMinX()
				   || e.getSceneX() > flagForUSAImageSmall.localToScene(flagForUSAImageSmall.getBoundsInLocal()).getMaxX()
				   || e.getSceneY() < flagForUSAImageSmall.localToScene(flagForUSAImageSmall.getBoundsInLocal()).getMinY()
				   || e.getSceneY() > flagForUSAImageSmall.localToScene(flagForUSAImageSmall.getBoundsInLocal()).getMaxY())
					closeBigImage();
			}
		});
		
		sealLabelForUSA.setOnMouseClicked(e ->
		{
			if(sealForUSAImageSmall.getImage() != null)
			{
				if(getIndexInOptionsForUSA() == 0)
					setupBigImageNormal(BigImageType.SEAL_FOR_USA, statesOfUSASortList.get(getIndexInListViewForUSA()));
				else if(getIndexInOptionsForUSA() == 1)
					setupBigImageNormal(BigImageType.SEAL_FOR_USA, capitalsOfStatesSortList.get(getIndexInListViewForUSA()));
			}
		});
		
		sealLabelForUSA.setOnMouseExited(e ->
		{
			if(bigImageStatus == 1 && sealForUSAImageSmall.getImage() != null)
			{
				if(e.getSceneX() < sealForUSAImageSmall.localToScene(sealForUSAImageSmall.getBoundsInLocal()).getMinX()
				   || e.getSceneX() + 5 > sealForUSAImageSmall.localToScene(sealForUSAImageSmall.getBoundsInLocal()).getMaxX()
				   || e.getSceneY() < sealForUSAImageSmall.localToScene(sealForUSAImageSmall.getBoundsInLocal()).getMinY()
				   || e.getSceneY() > sealForUSAImageSmall.localToScene(sealForUSAImageSmall.getBoundsInLocal()).getMaxY())
					closeBigImage();
			}
		});
		
		locationLabelForUSA.setOnMouseClicked(e ->
		{
			if(locationForUSAImageSmall.getImage() != null)
			{
				if(getIndexInOptionsForUSA() == 0)
					setupBigImageNormal(BigImageType.LOCATION_FOR_USA, statesOfUSASortList.get(getIndexInListViewForUSA()));
				else if(getIndexInOptionsForUSA() == 1)
					setupBigImageNormal(BigImageType.LOCATION_FOR_USA, capitalsOfStatesSortList.get(getIndexInListViewForUSA()));
			}
		});
		
		locationLabelForUSA.setOnMouseExited(e ->
		{
			if (bigImageStatus == 1 && locationForUSAImageSmall.getImage() != null)
			{
				if(e.getSceneX() < locationForUSAImageSmall.localToScene(locationForUSAImageSmall.getBoundsInLocal()).getMinX()
				   || e.getSceneX() > locationForUSAImageSmall.localToScene(locationForUSAImageSmall.getBoundsInLocal()).getMaxX()
				   || e.getSceneY() < locationForUSAImageSmall.localToScene(locationForUSAImageSmall.getBoundsInLocal()).getMinY()
				   || e.getSceneY() > locationForUSAImageSmall.localToScene(locationForUSAImageSmall.getBoundsInLocal()).getMaxY())
					closeBigImage();
			}
		});
		
		listViewForUSA.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
		{
			if(newValue.intValue() >= 0)
			{
				setIndexInListViewForUSA(newValue.intValue());
				
				if ((getIndexInOptionsForUSA() == 0 ||
				     getIndexInOptionsForUSA() == 1) &&
				     newValue.intValue() < NUM_ALL_USA_STATES)
				{
					int index;
					if(getIndexInOptionsForUSA() == 0)
						index = statesOfUSASortList.get(newValue.intValue());
					else index = capitalsOfStatesSortList.get(newValue.intValue());
					
					gridPaneLabelsForUSA[1][1].setText(statesOfUSA[index].getCapitalName());
					gridPaneLabelsForUSA[3][1].setText(dateForUI.format(statesOfUSA[index].getDateEnteredUnion()));
					gridPaneLabelsForUSA[4][1].setText(statesOfUSA[index].getSpokenLanguages());
					gridPaneLabelsForUSA[5][1].setText(String.valueOf(statesOfUSA[index].getNumberOfCounties()));
					gridPaneLabelsForUSA[6][1].setText(String.valueOf(statesOfUSA[index].getHouseSeats()));
					
					gridPaneLabelsForUSA[8][1].setText(statesOfUSA[index].getPopulationDensityBasedOnLocaleToString());
					
					gridPaneLabelsForUSA[10][1].setText(statesOfUSA[index].getLandAreaBasedOnLocaleToString() + " (" + String.valueOf(statesOfUSA[index].getLandPercent()) + "%)");
					gridPaneLabelsForUSA[11][1].setText(statesOfUSA[index].getWaterAreaBasedOnLocaleToString() + " (" + String.valueOf(statesOfUSA[index].getWaterPercent()) + "%)");
					
					if(statesOfUSA[index].getCoastlineLengthInKilometers() == 0) gridPaneLabelsForUSA[12][1].setText("-");
					else gridPaneLabelsForUSA[12][1].setText(statesOfUSA[index].getCoastlineBasedOnLocaleToString());
					
					gridPaneLabelsForUSA[14][1].setText(statesOfUSA[index].getHighestPoint());
					gridPaneLabelsForUSA[15][1].setText(statesOfUSA[index].getMeanPoint());
					gridPaneLabelsForUSA[16][1].setText(statesOfUSA[index].getLowestPoint());
					gridPaneLabelsForUSA[17][1].setText(statesOfUSA[index].getTimeZones());
					
					if(getCurrentLanguage() == LANGUAGE.GREEK)
					{
						gridPaneLabelsForUSA[0][1].setText(statesOfUSA[index].getNameInGreek() + " (" + statesOfUSA[index].getAbbreviation() + ")");
						
						gridPaneLabelsForUSA[2][1].setText("Κάνε κλικ για να τις δεις");
						
						gridPaneLabelsForUSA[7][1].setText(String.valueOf(numberFormatForUI.format(statesOfUSA[index].getPopulation())) + " κάτοικοι (" +
						                                   String.valueOf(statesOfUSA[index].getPopulationRank()) + "η)");
						
						gridPaneLabelsForUSA[9][1].setText(statesOfUSA[index].getTotalAreaBasedOnLocaleToString() + " (" + String.valueOf(statesOfUSA[index].getAreaRanking()) + "η)");
						
						if(statesOfUSA[index].hasAccessToTheOcean()) gridPaneLabelsForUSA[13][1].setText("Ναι");
						else gridPaneLabelsForUSA[13][1].setText("Όχι");
					}
					else
					{
						gridPaneLabelsForUSA[0][1].setText(statesOfUSA[index].getNameInEnglish() + " (" + statesOfUSA[index].getAbbreviation() + ")");
						
						gridPaneLabelsForUSA[2][1].setText("Click here to see them");
						
						StringBuilder sb = new StringBuilder();
						sb.append(String.valueOf(numberFormatForUI.format(statesOfUSA[index].getPopulation()))).append(" inhabitants (").append(String.valueOf(statesOfUSA[index].getPopulationRank()));
						if (statesOfUSA[index].getPopulationRank() == 1) sb.append("st)");
						else if (statesOfUSA[index].getPopulationRank() == 2) sb.append("nd)");
						else if (statesOfUSA[index].getPopulationRank() == 3) sb.append("rd)");
						else sb.append("th)");
						gridPaneLabelsForUSA[7][1].setText(sb.toString());
						
						sb = new StringBuilder();
						sb.append(statesOfUSA[index].getTotalAreaBasedOnLocaleToString() + " (" + String.valueOf(statesOfUSA[index].getAreaRanking()));
						if (statesOfUSA[index].getAreaRanking() == 1) sb.append("st)");
						else if (statesOfUSA[index].getAreaRanking() == 2) sb.append("nd)");
						else if (statesOfUSA[index].getAreaRanking() == 3) sb.append("rd)");
						else sb.append("th)");
						gridPaneLabelsForUSA[9][1].setText(sb.toString());
						
						if(statesOfUSA[index].hasAccessToTheOcean()) gridPaneLabelsForUSA[13][1].setText("Yes");
						else gridPaneLabelsForUSA[13][1].setText("No");
					}
					//SET IMAGES
					if(flagForUSAImageSmall.getFitWidth() <= MAX_WIDTH_FOR_X250_IMAGES)
					{
						flagForUSAImageSmall.setImage(getImage(ImageType.USA_FLAG, "x250", statesOfUSA[index].getNameInGreek(), true));
						sealForUSAImageSmall.setImage(getImage(ImageType.USA_SEAL, "x250", statesOfUSA[index].getNameInGreek(), true));
					}
					else
					{
						flagForUSAImageSmall.setImage(getImage(ImageType.USA_FLAG, "x500", statesOfUSA[index].getNameInGreek(), true));
						sealForUSAImageSmall.setImage(getImage(ImageType.USA_SEAL, "x500", statesOfUSA[index].getNameInGreek(), true));
					}
					if(locationForUSAImageSmall.getFitWidth() <= MAX_WIDTH_FOR_X250_IMAGES)
						locationForUSAImageSmall.setImage(getImage(ImageType.USA_LOCATION, "x250", statesOfUSA[index].getNameInGreek(), true));
					else if(locationForUSAImageSmall.getFitHeight() <= MAX_WIDTH_FOR_X500_IMAGES)
						locationForUSAImageSmall.setImage(getImage(ImageType.USA_LOCATION, "x500", statesOfUSA[index].getNameInGreek(), true));
					else
						locationForUSAImageSmall.setImage(getImage(ImageType.USA_LOCATION, "x1000", statesOfUSA[index].getNameInGreek(), true));
				}
				
				for(int i = 0; i < gridPaneLabelsForUSA.length; i++) gridPaneTooltipsForUSA[i].setText(gridPaneLabelsForUSA[i][1].getText());
			}
		});
		
		gridPaneLabelsForUSA[2][1].setOnMouseClicked(e ->
		{
			if(getIndexInOptionsForUSA() == 0)
			{
				setupMoreInfo(statesOfUSA[statesOfUSASortList.get(getIndexInListViewForUSA())].getLargestCities());
			}
			else if(getIndexInOptionsForUSA() == 1)
			{
				setupMoreInfo(statesOfUSA[capitalsOfStatesSortList.get(getIndexInListViewForUSA())].getLargestCities());
			}
		});
		
		//GREECE MOUSE HELPERS AND LISTENERS---------------------------------------
		MouseStationaryHelper greekLogoLabelMouseHelper = new MouseStationaryHelper(logoLabelForGreece, java.time.Duration.ofMillis(100));
		greekLogoLabelMouseHelper.events().subscribe(either -> either.exec(
				pos ->
				{
					if(bigImageStatus == 0 && logoForGreeceImageSmall.getImage() != null)
					{
						if(getIndexInOptionsForGreece() == 1)
							setupBigImagePreview(BigImageType.LOGOS_FOR_GREEK_REGIONS, greekRegionsSortList.get(getIndexInListViewForGreece()));
					}
				},
				stop -> {}));
		
		MouseStationaryHelper greeceInMapLabelMouseHelper = new MouseStationaryHelper(locationLabelForGreece, java.time.Duration.ofMillis(100));
		greeceInMapLabelMouseHelper.events().subscribe(either -> either.exec(
				pos ->
				{
					if(bigImageStatus == 0 && locationForGreeceSmall.getImage() != null)
					{
						if(getIndexInOptionsForGreece() == 0)
							setupBigImagePreview(BigImageType.LOCATION_FOR_GREEK_DEC_ADMIN, greekDecentralizedAdministrationsSortList.get(getIndexInListViewForGreece()));
						else if(getIndexInOptionsForGreece() == 1)
							setupBigImagePreview(BigImageType.LOCATION_FOR_GREEK_REGIONS, greekRegionsSortList.get(getIndexInListViewForGreece()));
					}
				},
				stop -> {}));
		
		logoLabelForGreece.setOnMouseClicked(e ->
		{
			if(logoForGreeceImageSmall.getImage() != null)
			{
				if(getIndexInOptionsForGreece() == 1)
					setupBigImageNormal(BigImageType.LOGOS_FOR_GREEK_REGIONS, greekRegionsSortList.get(getIndexInListViewForGreece()));
			}
		});
		
		logoLabelForGreece.setOnMouseExited(e ->
		{
			if (bigImageStatus == 1 && logoForGreeceImageSmall.getImage() != null)
			{
				if(e.getSceneX() < logoForGreeceImageSmall.localToScene(logoForGreeceImageSmall.getBoundsInLocal()).getMinX()
				   || e.getSceneX() > logoForGreeceImageSmall.localToScene(logoForGreeceImageSmall.getBoundsInLocal()).getMaxX()
				   || e.getSceneY() < logoForGreeceImageSmall.localToScene(logoForGreeceImageSmall.getBoundsInLocal()).getMinY()
				   || e.getSceneY() > logoForGreeceImageSmall.localToScene(logoForGreeceImageSmall.getBoundsInLocal()).getMaxY())
					closeBigImage();
			}
		});
		
		locationLabelForGreece.setOnMouseClicked(e ->
		{
			if(locationForGreeceSmall.getImage() != null)
			{
				if(getIndexInOptionsForGreece() == 0)
					setupBigImageNormal(BigImageType.LOCATION_FOR_GREEK_DEC_ADMIN, greekDecentralizedAdministrationsSortList.get(getIndexInListViewForGreece()));
				else if(getIndexInOptionsForGreece() == 1)
					setupBigImageNormal(BigImageType.LOCATION_FOR_GREEK_REGIONS, greekRegionsSortList.get(getIndexInListViewForGreece()));
			}
		});
		
		locationLabelForGreece.setOnMouseExited(e ->
		{
			if (bigImageStatus == 1 && locationForGreeceSmall.getImage() != null)
			{
				if(e.getSceneX() < locationForGreeceSmall.localToScene(locationForGreeceSmall.getBoundsInLocal()).getMinX()
				   || e.getSceneX() > locationForGreeceSmall.localToScene(locationForGreeceSmall.getBoundsInLocal()).getMaxX()
				   || e.getSceneY() < locationForGreeceSmall.localToScene(locationForGreeceSmall.getBoundsInLocal()).getMinY()
				   || e.getSceneY() > locationForGreeceSmall.localToScene(locationForGreeceSmall.getBoundsInLocal()).getMaxY())
					closeBigImage();
			}
		});
		
		gridPaneLabelsForGreece[5][1].setOnMousePressed(e ->
		{
			if(getIndexInOptionsForGreece() == 0)
			{
				if (e.isPrimaryButtonDown()) UrlOpener.openURL(gridPaneLabelsForGreece[5][1].getText());
				else if (e.isSecondaryButtonDown())
				{
					clipboardContent.putString(gridPaneLabelsForGreece[5][1].getText());
					clipboard.setContent(clipboardContent);
					clipboardContent.clear();
				}
			}
		});
		
		gridPaneLabelsForGreece[6][1].setOnMousePressed(e ->
		{
			if(getIndexInOptionsForGreece() == 0)
			{
				if (e.isPrimaryButtonDown()) UrlOpener.openURL(gridPaneLabelsForGreece[6][1].getText());
				else if (e.isSecondaryButtonDown())
				{
					clipboardContent.putString(gridPaneLabelsForGreece[6][1].getText());
					clipboard.setContent(clipboardContent);
					clipboardContent.clear();
				}
			}
		});
		
		gridPaneLabelsForGreece[7][1].setOnMouseClicked(e ->
		{
			if(getIndexInOptionsForGreece() == 0)
			{
				setupMoreInfo(greekDecAdm[greekDecentralizedAdministrationsSortList.get(getIndexInListViewForGreece())].getRegions());
			}
		});
		
		gridPaneLabelsForGreece[8][1].setOnMouseClicked(e ->
		{
			if(getIndexInOptionsForGreece() == 0)
			{
				setupMoreInfo(greekDecAdm[greekDecentralizedAdministrationsSortList.get(getIndexInListViewForGreece())].getRegionalUnits());
			}
		});
		
		gridPaneLabelsForGreece[9][1].setOnMousePressed(e ->
		{
			if(getIndexInOptionsForGreece() == 1)
			{
				if (e.isPrimaryButtonDown()) UrlOpener.openURL(gridPaneLabelsForGreece[9][1].getText());
				else if (e.isSecondaryButtonDown())
				{
					clipboardContent.putString(gridPaneLabelsForGreece[9][1].getText());
					clipboard.setContent(clipboardContent);
					clipboardContent.clear();
				}
			}
		});
		
		gridPaneLabelsForGreece[9][1].setOnMouseClicked(e ->
		{
			if (getIndexInOptionsForGreece() == 0)
			{
				setupMoreInfo(greekDecAdm[greekDecentralizedAdministrationsSortList.get(getIndexInListViewForGreece())].getMunicipalities());
			}
		});
		
		gridPaneLabelsForGreece[10][1].setOnMousePressed(e ->
		{
			if(getIndexInOptionsForGreece() == 1 || getIndexInOptionsForGreece() == 2)
			{
				if(!gridPaneLabelsForGreece[10][1].getText().equals("-"))
				{
					if(e.isPrimaryButtonDown()) UrlOpener.openURL(gridPaneLabelsForGreece[10][1].getText());
					else if(e.isSecondaryButtonDown())
					{
						clipboardContent.putString(gridPaneLabelsForGreece[10][1].getText());
						clipboard.setContent(clipboardContent);
						clipboardContent.clear();
					}
				}
			}
		});
		
		gridPaneLabelsForGreece[11][1].setOnMousePressed(e ->
		{
			if(getIndexInOptionsForGreece() == 2)
			{
				if(!gridPaneLabelsForGreece[11][1].getText().equals("-"))
				{
					if (e.isPrimaryButtonDown()) UrlOpener.openURL(gridPaneLabelsForGreece[11][1].getText());
					else if (e.isSecondaryButtonDown())
					{
						clipboardContent.putString(gridPaneLabelsForGreece[11][1].getText());
						clipboard.setContent(clipboardContent);
						clipboardContent.clear();
					}
				}
			}
		});
		
		gridPaneLabelsForGreece[11][1].setOnMouseClicked(e ->
		{
			if (getIndexInOptionsForGreece() == 1)
			{
				setupMoreInfo(greekRegions[greekRegionsSortList.get(getIndexInListViewForGreece())].getRegionalUnits());
			}
		});
		
		gridPaneLabelsForGreece[12][1].setOnMouseClicked(e ->
		{
			if(getIndexInOptionsForGreece() == 1)
			{
				setupMoreInfo(greekRegions[greekRegionsSortList.get(getIndexInListViewForGreece())].getMunicipalities());
			}
			else if(getIndexInOptionsForGreece() == 2)
			{
				setupMoreInfo(greekRegionalUnits[greekRegionalUnitsSortList.get(getIndexInListViewForGreece())].getMunicipalities());
			}
		});
		
		listViewForGreece.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
		{
			if (newValue.intValue() >= 0)
			{
				setIndexInListViewForGreece(newValue.intValue());
				
				if (getIndexInOptionsForGreece() == 0 && newValue.intValue() < NUM_ALL_GREEK_DEC_ADMIN)
				{
					int index = greekDecentralizedAdministrationsSortList.get(newValue.intValue());
					
					gridPaneLabelsForGreece[1][1].setText(greekDecAdm[index].getHeadquarters());
					gridPaneLabelsForGreece[2][1].setText(greekDecAdm[index].getYearFormed());
					
					gridPaneLabelsForGreece[4][1].setText(greekDecAdm[index].getAreaBasedOnLocaleToString());
					
					gridPaneLabelsForGreece[5][1].setText(greekDecAdm[index].getWebsite());
					gridPaneLabelsForGreece[6][1].setText(greekDecAdm[index].getWikipediaLink());
					
					if (getCurrentLanguage() == LANGUAGE.GREEK)
					{
						gridPaneLabelsForGreece[0][1].setText(greekDecAdm[index].getNameInGreek());
						
						gridPaneLabelsForGreece[3][1].setText(String.valueOf(numberFormatForUI.format(greekDecAdm[index].getPopulation()) + " κάτοικοι"));
						
						gridPaneLabelsForGreece[7][1].setText("(" + greekDecAdm[index].getNumberOfRegions() + ") Κάνε κλικ για να τις δεις");
						gridPaneLabelsForGreece[8][1].setText("(" + greekDecAdm[index].getNumberOfRegionalUnits() + ") Κάνε κλικ για να τις δεις");
						gridPaneLabelsForGreece[9][1].setText("(" + greekDecAdm[index].getNumberOfMunicipalities() + ") Κάνε κλικ για να τους δεις");
					}
					else
					{
						gridPaneLabelsForGreece[0][1].setText(greekDecAdm[index].getNameInEnglish());
						
						gridPaneLabelsForGreece[3][1].setText(String.valueOf(numberFormatForUI.format(greekDecAdm[index].getPopulation()) + " inhabitants"));
						
						gridPaneLabelsForGreece[7][1].setText("(" + greekDecAdm[index].getNumberOfRegions() + ") Click here to see them");
						gridPaneLabelsForGreece[8][1].setText("(" + greekDecAdm[index].getNumberOfRegionalUnits() + ") Click here to see them");
						gridPaneLabelsForGreece[9][1].setText("(" + greekDecAdm[index].getNumberOfMunicipalities() + ") Click here to see them");
					}
					
					//Images
					
					logoForGreeceImageSmall.setImage(getImage(ImageType.GREECE_DEC_ADM_LOGO, "", greekDecAdm[index].getNameInGreek(), true));
					
					if (locationForGreeceSmall.getFitWidth() <= MAX_WIDTH_FOR_X250_IMAGES)
						locationForGreeceSmall.setImage(getImage(ImageType.GREECE_DEC_ADM_LOCATION, "x250", greekDecAdm[index].getNameInGreek(), true));
					else if (locationForGreeceSmall.getFitWidth() <= MAX_WIDTH_FOR_X500_IMAGES)
						locationForGreeceSmall.setImage(getImage(ImageType.GREECE_DEC_ADM_LOCATION, "x500", greekDecAdm[index].getNameInGreek(), true));
					else locationForGreeceSmall.setImage(getImage(ImageType.GREECE_DEC_ADM_LOCATION, "x1000", greekDecAdm[index].getNameInGreek(), true));
					
					//Tooltips
					for(int i = 0; i < 5; i++) gridPaneTooltipsForGreece[i].setText(gridPaneLabelsForGreece[i][1].getText());
				}
				else if(getIndexInOptionsForGreece() == 1 && newValue.intValue() < NUM_ALL_GREEK_REGIONS)
				{
					int index = greekRegionsSortList.get(newValue.intValue());
					
					gridPaneLabelsForGreece[1][1].setText(greekRegions[index].getSeat());
					gridPaneLabelsForGreece[2][1].setText(greekRegions[index].getDecentralizedAdministration());
					gridPaneLabelsForGreece[3][1].setText(greekRegions[index].getLargestCity());
					gridPaneLabelsForGreece[4][1].setText(greekRegions[index].getLargestMunicipality());
					gridPaneLabelsForGreece[8][1].setText(greekRegions[index].getHighestPoint());
					gridPaneLabelsForGreece[9][1].setText(greekRegions[index].getWebsite());
					gridPaneLabelsForGreece[10][1].setText(greekRegions[index].getWikipediaLink());
					
					if (getCurrentLanguage() == LANGUAGE.GREEK)
					{
						gridPaneLabelsForGreece[0][1].setText(greekRegions[index].getNameInGreek());
						
						gridPaneLabelsForGreece[5][1].setText(String.valueOf(numberFormatForUI.format(greekRegions[index].getPopulation()) + " κάτοικοι (" +
						                                                     String.valueOf(greekRegions[index].getPopulationRanking()) + "η)"));
						
						gridPaneLabelsForGreece[6][1].setText(String.valueOf(greekRegions[index].getPopulationDensityPerSquareKilometer()) + " κατ./km\u00B2, "
						                                      + String.valueOf(greekRegions[index].getPopulationDensityPerSquareMile()) + " κατ./ sq mi");
						
						gridPaneLabelsForGreece[7][1].setText(greekRegions[index].getAreaBasedOnLocaleToString() + " (" + String.valueOf(greekRegions[index].getAreaRanking()) + "η)");
						
						gridPaneLabelsForGreece[11][1].setText("(" + greekRegions[index].getNumberOfRegionalUnits() + ") Κάνε κλικ για να τις δεις");
						gridPaneLabelsForGreece[12][1].setText("(" + greekRegions[index].getNumberOfMunicipalities() + ") Κάνε κλικ για να τους δεις");
					}
					else
					{
						gridPaneLabelsForGreece[0][1].setText(greekRegions[index].getNameInEnglish());
						
						StringBuilder sb;
						
						sb = new StringBuilder();
						sb.append(String.valueOf(numberFormatForUI.format(greekRegions[index].getPopulation()) + " inhabitants (" + String.valueOf(greekRegions[index].getPopulationRanking())));
						if (greekRegions[index].getPopulationRanking() % 10 == 1) sb.append("st)");
						else if (greekRegions[index].getPopulationRanking() % 10 == 2) sb.append("nd)");
						else if (greekRegions[index].getPopulationRanking() % 10 == 3) sb.append("rd)");
						else sb.append("th)");
						gridPaneLabelsForGreece[5][1].setText(sb.toString());
						
						gridPaneLabelsForGreece[6][1].setText(String.valueOf(greekRegions[index].getPopulationDensityPerSquareKilometer()) + " inh./km\u00B2, "
						                                      + String.valueOf(greekRegions[index].getPopulationDensityPerSquareMile()) + " inh./ sq mi");
						
						sb = new StringBuilder();
						sb.append(greekRegions[index].getAreaBasedOnLocaleToString());
						sb.append(" (").append(String.valueOf(greekRegions[index].getAreaRanking()));
						if (greekRegions[index].getAreaRanking() % 10 == 1) sb.append("st)");
						else if (greekRegions[index].getAreaRanking() % 10 == 2) sb.append("nd)");
						else if (greekRegions[index].getAreaRanking() % 10 == 3) sb.append("rd)");
						else sb.append("th)");
						gridPaneLabelsForGreece[7][1].setText(sb.toString());
						
						gridPaneLabelsForGreece[11][1].setText("(" + greekRegions[index].getNumberOfRegionalUnits() + ") Click here to see them");
						gridPaneLabelsForGreece[12][1].setText("(" + greekRegions[index].getNumberOfMunicipalities() + ") Click here to see them");
					}
					
					//Images
					logoForGreeceImageSmall.setImage(getImage(ImageType.GREECE_REGION_LOGO, "", greekRegions[index].getNameInGreek(), true));
					
					if (locationForGreeceSmall.getFitWidth() <= MAX_WIDTH_FOR_X250_IMAGES)
						locationForGreeceSmall.setImage(getImage(ImageType.GREECE_REGION_LOCATION, "x250", greekRegions[index].getNameInGreek(), true));
					else if (locationForGreeceSmall.getFitWidth() <= MAX_WIDTH_FOR_X500_IMAGES)
						locationForGreeceSmall.setImage(getImage(ImageType.GREECE_REGION_LOCATION, "x500", greekRegions[index].getNameInGreek(), true));
					else locationForGreeceSmall.setImage(getImage(ImageType.GREECE_REGION_LOCATION, "x1000", greekRegions[index].getNameInGreek(), true));
					
					//Tooltips
					for(int i = 0; i < 9; i++) gridPaneTooltipsForGreece[i].setText(gridPaneLabelsForGreece[i][1].getText());
				}
				else if(getIndexInOptionsForGreece() == 2 && newValue.intValue() < NUM_ALL_GREEK_REGIONAL_UNITS)
				{
					int index = greekRegionalUnitsSortList.get(newValue.intValue());
					
					gridPaneLabelsForGreece[1][1].setText(greekRegionalUnits[index].getCapital());
					gridPaneLabelsForGreece[2][1].setText(greekRegionalUnits[index].getDecentralizedAdministration());
					gridPaneLabelsForGreece[3][1].setText(greekRegionalUnits[index].getRegion());
					gridPaneLabelsForGreece[4][1].setText(greekRegionalUnits[index].getLargestCity());
					gridPaneLabelsForGreece[5][1].setText(greekRegionalUnits[index].getLargestMunicipality());
					gridPaneLabelsForGreece[9][1].setText(greekRegionalUnits[index].getHighestPoint());
					gridPaneLabelsForGreece[10][1].setText(greekRegionalUnits[index].getWebsite());
					gridPaneLabelsForGreece[11][1].setText(greekRegionalUnits[index].getWikipediaLink());
					
					if (getCurrentLanguage() == LANGUAGE.GREEK)
					{
						gridPaneLabelsForGreece[0][1].setText(greekRegionalUnits[index].getNameInGreek());
						
						gridPaneLabelsForGreece[6][1].setText(String.valueOf(numberFormatForUI.format(greekRegionalUnits[index].getPopulation()) + " κάτοικοι (" +
						                                                     String.valueOf(greekRegionalUnits[index].getPopulationRanking()) + "η)"));
						
						gridPaneLabelsForGreece[7][1].setText(String.valueOf(greekRegionalUnits[index].getPopulationDensityPerSquareKilometer()) + " κατ./km\u00B2, "
						                                      + String.valueOf(greekRegionalUnits[index].getPopulationDensityPerSquareMile()) + " κατ./ sq mi");
						
						gridPaneLabelsForGreece[8][1].setText(String.valueOf(greekRegionalUnits[index].getAreaBasedOnLocaleToString() + " (" + String.valueOf(greekRegionalUnits[index].getAreaRanking()) + "η)"));
						
						gridPaneLabelsForGreece[12][1].setText("(" + greekRegionalUnits[index].getNumberOfMunicipalities() + ") Κάνε κλικ για να τους δεις");
					}
					else
					{
						gridPaneLabelsForGreece[0][1].setText(greekRegionalUnits[index].getNameInEnglish());
						
						StringBuilder sb;
						
						sb = new StringBuilder();
						sb.append(String.valueOf(numberFormatForUI.format(greekRegionalUnits[index].getPopulation()) + " inhabitants (" + String.valueOf(greekRegionalUnits[index].getPopulationRanking())));
						if (greekRegionalUnits[index].getPopulationRanking() % 10 == 1) sb.append("st)");
						else if (greekRegionalUnits[index].getPopulationRanking() % 10 == 2) sb.append("nd)");
						else if (greekRegionalUnits[index].getPopulationRanking() % 10 == 3) sb.append("rd)");
						else sb.append("th)");
						gridPaneLabelsForGreece[6][1].setText(sb.toString());
						
						gridPaneLabelsForGreece[7][1].setText(String.valueOf(greekRegionalUnits[index].getPopulationDensityPerSquareKilometer()) + " inh./km\u00B2, "
						                                      + String.valueOf(greekRegionalUnits[index].getPopulationDensityPerSquareMile()) + " inh./ sq mi");
						
						sb = new StringBuilder();
						sb.append(String.valueOf(greekRegionalUnits[index].getAreaBasedOnLocaleToString()));
						sb.append(" (" + String.valueOf(greekRegionalUnits[index].getAreaRanking()));
						if (greekRegionalUnits[index].getAreaRanking() % 10 == 1) sb.append("st)");
						else if (greekRegionalUnits[index].getAreaRanking() % 10 == 2) sb.append("nd)");
						else if (greekRegionalUnits[index].getAreaRanking() % 10 == 3) sb.append("rd)");
						else sb.append("th)");
						gridPaneLabelsForGreece[8][1].setText(sb.toString());
						
						gridPaneLabelsForGreece[12][1].setText("(" + greekRegionalUnits[index].getNumberOfMunicipalities() + ") Click here to see them");
					}
					
					//Images
					logoForGreeceImageSmall.setImage(null);
					locationForGreeceSmall.setImage(null);
					
					//							logoForGreeceImageSmall.setImage(new Image("/resources/images/greece/regions/logos/" + greekRegionalUnits[index].getNameInGreek() + ".jpg"));
					//
					//							if (locationLabelForGreece.getPrefWidth() <= 600)
					//								locationForGreeceSmall.setImage(new Image("/resources/images/greece/regions/inMap/x500/" + greekRegionalUnits[index].getNameInGreek() + ".jpg"));
					//							else if (locationLabelForGreece.getPrefWidth() <= 1100)
					//								locationForGreeceSmall.setImage(new Image("/resources/images/greece/regions/inMap/x1000/" + greekRegionalUnits[index].getNameInGreek() + ".jpg"));
					//							else locationForGreeceSmall.setImage(new Image("/resources/images/greece/regions/inMap/x2000/" + greekRegionalUnits[index].getNameInGreek() + ".jpg"));
					
					//Tooltips
					for(int i = 0; i < 10; i++) gridPaneTooltipsForGreece[i].setText(gridPaneLabelsForGreece[i][1].getText());
				}
				else if(getIndexInOptionsForGreece() == 3 && newValue.intValue() < NUM_ALL_GREEK_MUNICIPALITIES)
				{
					
				}
			}
		});
		
		//ATTRACTIONS STUFF-------------------------------------------------------------------------------
		listViewForAttractions.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
		{
			if (newValue.intValue() >= 0)
			{
				setIndexInListViewForAttractions(newValue.intValue());
				
				if (getIndexInOptionsForAttractions() == 0 && newValue.intValue() < NUM_ALL_ATTRACTIONS)
				{
					int index = attractionsSortList.get(newValue.intValue());
					
					if(getCurrentLanguage() == LANGUAGE.GREEK) gridPaneLabelsForAttractions[0][1].setText(attractions[index].getNameInGreek());
					else gridPaneLabelsForAttractions[0][1].setText(attractions[index].getNameInEnglish());
					
					gridPaneLabelsForAttractions[1][1].setText(attractions[index].getCountry());
					gridPaneLabelsForAttractions[2][1].setText(attractions[index].getCity());
					gridPaneLabelsForAttractions[3][1].setText(attractions[index].getYearBuilt());
					gridPaneLabelsForAttractions[4][1].setText(attractions[index].getCoordinates());
					gridPaneLabelsForAttractions[5][1].setText(attractions[index].getWikipediaLink());
					
					attractionBasicInfoLabel.setText(attractions[index].getBasicInfo());
					
					//Images
					
//					logoForGreeceImageSmall.setImage(new Image("/resources/images/greece/decentralizedAdministrations/logos/" + greekDecAdm[index].getNameInGreek() + ".jpg"));
//
//					if (locationLabelForGreece.getPrefWidth() <= 600)
//						locationForGreeceSmall.setImage(new Image("/resources/images/greece/decentralizedAdministrations/inMap/x500/" + greekDecAdm[index].getNameInGreek() + ".jpg"));
//					else if (locationLabelForGreece.getPrefWidth() <= 1100)
//						locationForGreeceSmall.setImage(new Image("/resources/images/greece/decentralizedAdministrations/inMap/x1000/" + greekDecAdm[index].getNameInGreek() + ".jpg"));
//					else locationForGreeceSmall.setImage(new Image("/resources/images/greece/decentralizedAdministrations/inMap/x2000/" + greekDecAdm[index].getNameInGreek() + ".jpg"));
					
					//Tooltips
					for(int i = 0; i < 5; i++) gridPaneTooltipsForAttractions[i].setText(gridPaneLabelsForAttractions[i][1].getText());
				}
			}
		});
		
		//BIG IMAGE STUFF-------------------------------------------------------------------------------
		rectangleForBigImage.setOnMouseExited(e ->
		{
			if(bigImageStatus == 1)
			{
				if(e.getSceneX() < rectangleForBigImage.getLayoutX()
				   || e.getSceneX() > rectangleForBigImage.getLayoutX() + rectangleForBigImage.getWidth()
				   || e.getSceneY() < rectangleForBigImage.getLayoutY()
				   || e.getSceneY() > rectangleForBigImage.getLayoutY() + rectangleForBigImage.getHeight())
					closeBigImage();
			}
		});
		
		bigImage.setOnMouseMoved(e ->
		{
			if(bigImageStatus == 1)
			{
				if(e.getSceneX() < rectangleForBigImage.getLayoutX()
				   || e.getSceneX() > rectangleForBigImage.getLayoutX() + rectangleForBigImage.getWidth()
				   || e.getSceneY() < rectangleForBigImage.getLayoutY()
				   || e.getSceneY() > rectangleForBigImage.getLayoutY() + rectangleForBigImage.getHeight())
					closeBigImage();
			}
		});
		
		previousInBigImageButton.setOnMouseClicked(e -> makeAStepInBigImage(-1));
		
		nextInBigImageButton.setOnMouseClicked(e -> makeAStepInBigImage(1));
		
		zoomInBigImage.setOnMousePressed(e -> zoomInBigImage.setImage(ZOOM_IN_ICON_CLICKED));
		
		zoomInBigImage.setOnMouseClicked(e -> zoomIn());
		
		zoomInBigImage.setOnMouseReleased(e -> zoomInBigImage.setImage(ZOOM_IN_ICON));
		
		zoomOutBigImage.setOnMousePressed(e -> zoomOutBigImage.setImage(ZOOM_OUT_ICON_CLICKED));
		
		zoomOutBigImage.setOnMouseClicked(e -> zoomOut());
		
		zoomOutBigImage.setOnMouseReleased(e -> zoomOutBigImage.setImage(ZOOM_OUT_ICON));
		
		exitBigImage.setOnMousePressed(e -> exitBigImage.setImage(X_ICON_CLICKED));
		
		exitBigImage.setOnMouseClicked(e -> closeBigImage());
		
		exitBigImage.setOnMouseReleased(e -> exitBigImage.setImage(X_ICON));
		
		bigImage.setOnScroll(e ->
		{
			if(typeOfNormalBigImage != BigImageType.LOGOS_FOR_GREEK_REGIONS)
			{
				if (e.getDeltaY() > 0) zoomIn();
				else zoomOut();
			}
		});
		
		bigImage.setOnMousePressed(e ->
		{
			if(bigImage.getViewport() != null)
			{
				xOffset = e.getX();
				yOffset = e.getY();
				viewPortX = bigImage.getViewport().getMinX();
				viewPortY = bigImage.getViewport().getMinY();
			}
		});
		
		bigImage.setOnMouseDragged(e ->
		{
			if(bigImage.getViewport() != null)
			{
				bigImage.setViewport(new Rectangle2D(
                    viewPortX - (e.getX() - xOffset) *  bigImage.getViewport().getWidth() / bigImage.getFitWidth(),
                    viewPortY - (e.getY() - yOffset) *  bigImage.getViewport().getWidth() / bigImage.getFitWidth(),
                    bigImage.getViewport().getWidth(), bigImage.getViewport().getHeight()));
			}
		});
		
		anchorPane.addEventFilter(MouseEvent.MOUSE_PRESSED, e ->
		{
			if(bigImageStatus == 2)
			{
				pressedOutsideOfBigImage = (e.getSceneX() < rectangleForBigImage.getLayoutX() || e.getSceneX() > rectangleForBigImage.getLayoutX() + rectangleForBigImage.getWidth()
						|| e.getSceneY() < rectangleForBigImage.getLayoutY() || e.getSceneY() > rectangleForBigImage.getLayoutY() + rectangleForBigImage.getHeight())
						&& !anchorPane.getCursor().equals(Cursor.H_RESIZE) && !anchorPane.getCursor().equals(Cursor.V_RESIZE)
						&& !minimizeIcon.isHover() && !moveIcon.isHover() && !fullScreenIcon.isHover() && !exitIcon.isHover();
			}
			else if(isInfoOpen)
			{
				pressedOutsideOfGreekInfo = (e.getSceneX() < rectangleToShowInfo.getLayoutX() || e.getSceneX() > rectangleToShowInfo.getLayoutX() + rectangleToShowInfo.getWidth()
						|| e.getSceneY() < rectangleToShowInfo.getLayoutY() || e.getSceneY() > rectangleToShowInfo.getLayoutY() + rectangleToShowInfo.getHeight())
						&& !anchorPane.getCursor().equals(Cursor.H_RESIZE) && !anchorPane.getCursor().equals(Cursor.V_RESIZE)
						&& !minimizeIcon.isHover() && !moveIcon.isHover() && !fullScreenIcon.isHover() && !exitIcon.isHover();
			}
		});
		
		anchorPane.addEventFilter(MouseEvent.MOUSE_RELEASED, e ->
		{
			if(bigImageStatus == 2)
			{
				if(e.getSceneX() < rectangleForBigImage.getLayoutX() || e.getSceneX() > rectangleForBigImage.getLayoutX() + rectangleForBigImage.getWidth()
				   || e.getSceneY() < rectangleForBigImage.getLayoutY() || e.getSceneY() > rectangleForBigImage.getLayoutY() + rectangleForBigImage.getHeight())
					if(pressedOutsideOfBigImage) closeBigImage();
			}
			else if(isInfoOpen)
			{
				if(e.getSceneX() < rectangleToShowInfo.getLayoutX() || e.getSceneX() > rectangleToShowInfo.getLayoutX() + rectangleToShowInfo.getWidth()
				   || e.getSceneY() < rectangleToShowInfo.getLayoutY() || e.getSceneY() > rectangleToShowInfo.getLayoutY() + rectangleToShowInfo.getHeight())
					if(pressedOutsideOfGreekInfo) closeMoreInfo();
			}
		});
		
		//-----------------------------------------------------------------------------
		
		backButton.setOnAction(e ->
		{
			playButtonClickSound();
			
			if(animationsUsed != ANIMATIONS.NO) timeLineToHideAllStuff.playFromStart();
			else
			{
				welcomeScreen.showScreen(false);
				
				gridViewForImagesForCountriesAndContinents.setItems(emptyObservableList);
				gridViewForImagesForUSA.setItems(emptyObservableList);
			}
		});
		
		backButton.setOnMouseEntered(e -> playHoverSound());
		
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
						titleImage.setTranslateX(-0.1068 * stage.getWidth());
						titleLabel.setTranslateX(-0.1068 * stage.getWidth());
					}
					else
					{
						titleImage.setTranslateX(0.1068 * stage.getWidth());
						titleLabel.setTranslateX(0.1068 * stage.getWidth());
					}
					vBoxForSound.setTranslateY(0);
					vBoxForSound.setVisible(true);
				}
			}
			else
			{
				setSoundIcon(soundIcon, false);
				
				if(animationsUsed != ANIMATIONS.NO) timelineToHideSoundOptions.play();
				else
				{
					vBoxForSound.setTranslateY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
					titleImage.setTranslateX(0);
					titleLabel.setTranslateX(0);
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
		
		anchorPane.addEventFilter(KeyEvent.KEY_RELEASED, e ->
		{
			if(e.getCode() == KeyCode.ESCAPE)
			{
				if(bigImageStatus != 0) closeBigImage();
				else if(isInfoOpen) closeMoreInfo();
				else if(fullScreenMode) setWindowedMode();
			}
		});
		
		anchorPane.addEventFilter(KeyEvent.KEY_RELEASED, e ->
		{
			if(e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.UP)
			{
				if(bigImageStatus == 2 && !previousInBigImageButton.isDisabled())
				{
					makeAStepInBigImage(-1);
				}
			}
		});
		
		anchorPane.addEventFilter(KeyEvent.KEY_RELEASED, e ->
		{
			if(e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.DOWN)
			{
				if(bigImageStatus == 2 && !nextInBigImageButton.isDisabled())
				{
					makeAStepInBigImage(1);
				}
			}
		});
	}
	
	protected void setupLimitedAnimations()
	{
		translateTransitionForTitleImage = new TranslateTransition(Duration.millis(300), titleImage);
		scaleTransitionForTitleLabel = new ScaleTransition(Duration.millis(300), titleLabel);
		translateTransitionForTitleLabel = new TranslateTransition(Duration.millis(300), titleLabel);
		
		translateTransitionForWoodImageFor5Icons = new TranslateTransition(Duration.millis(300), woodPanelFor5IconsImage);
		scaleTransitionForHBoxFor5Icons = new ScaleTransition(Duration.millis(300), hBoxFor5Icons);
		
		translateTransitionForVBoxForSound = new TranslateTransition(Duration.millis(300), vBoxForSound);
		
		scaleTransitionForBackButton = new ScaleTransition(Duration.millis(300), backButton);
		
		scaleTransitionForCountriesToggleButton = new ScaleTransition(Duration.millis(300), continentsAndCountriesToggleButton);
		scaleTransitionForUSAStatesToggleButton = new ScaleTransition(Duration.millis(300), USAToggleButton);
		scaleTransitionForGreekCountiesToggleButton = new ScaleTransition(Duration.millis(300), greeceToggleButton);
		scaleTransitionForAttractionsToggleButton = new ScaleTransition(Duration.millis(300), attractionsToggleButton);
		
		scaleTransitionForRectangleForBigImage = new ScaleTransition(Duration.millis(300), rectangleForBigImage);
		scaleTransitionForBigImage = new ScaleTransition(Duration.millis(300), bigImage);
		scaleTransitionForLabelInBigImage = new ScaleTransition(Duration.millis(300), labelForBigImage);
		scaleTransitionForPreviousInBigImage = new ScaleTransition(Duration.millis(300), previousInBigImageButton);
		scaleTransitionForNextInBigImage = new ScaleTransition(Duration.millis(300), nextInBigImageButton);
		scaleTransitionForExitInBigImage = new ScaleTransition(Duration.millis(300), exitBigImage);
		scaleTransitionForZoomInInBigImage = new ScaleTransition(Duration.millis(300), zoomInBigImage);
		scaleTransitionForZoomOutInBigImage = new ScaleTransition(Duration.millis(300), zoomOutBigImage);
		
		scaleTransitionForRectangleForInfo = new ScaleTransition(Duration.millis(200), rectangleToShowInfo);
		scaleTransitionForTextAreaForInfo = new ScaleTransition(Duration.millis(200), textAreaForInfo);
		
		scaleTransitionForHBoxMainForCountriesAndContinents = new ScaleTransition(Duration.millis(300), hBoxMainForCountriesAndContinents);
		scaleTransitionForHBoxMainForUSA = new ScaleTransition(Duration.millis(300), hBoxMainForUSA);
		scaleTransitionForHBoxMainForGreece = new ScaleTransition(Duration.millis(300), hBoxMainForGreece);
		scaleTransitionForHBoxMainForAttractions = new ScaleTransition(Duration.millis(300), hBoxMainForAttractions);
		
		translateTransitionForHBoxMainForCountriesAndContinents = new TranslateTransition(Duration.millis(400), hBoxMainForCountriesAndContinents);
		translateTransitionForHBoxMainForUSA = new TranslateTransition(Duration.millis(400), hBoxMainForUSA);
		translateTransitionForHBoxMainForGreece = new TranslateTransition(Duration.millis(400), hBoxMainForGreece);
		translateTransitionForHBoxMainForAttractions = new TranslateTransition(Duration.millis(400), hBoxMainForAttractions);
		
		fadeTransitionForMovingEarthImage = new FadeTransition(Duration.millis(400), movingEarthImage);
		
		timeLineToShowAllStuff = new Timeline(
         new KeyFrame(Duration.millis(0), e ->
         {
             backButton.setDisable(true);
             hBoxForToggleButtons.setDisable(true);
             hBoxFor5Icons.setDisable(true);
	         hBoxMainForCountriesAndContinents.setDisable(true);
	         hBoxMainForUSA.setDisable(true);
	         hBoxMainForGreece.setDisable(true);
	         hBoxMainForAttractions.setDisable(true);

             fadeTransitionForMovingEarthImage.setToValue(1);
             fadeTransitionForMovingEarthImage.playFromStart();
         }),
         new KeyFrame(Duration.millis(400), e ->
         {
             translateTransitionForTitleImage.setToX(0);
             translateTransitionForTitleImage.setToY(0);
             translateTransitionForWoodImageFor5Icons.setToY(0);

             playSlideSound();
             translateTransitionForTitleImage.playFromStart();
             translateTransitionForWoodImageFor5Icons.playFromStart();
         }),
         new KeyFrame(Duration.millis(700), e ->
         {
             scaleTransitionForTitleLabel.setDuration(Duration.millis(300));
             scaleTransitionForTitleLabel.setCycleCount(1);
             scaleTransitionForTitleLabel.setAutoReverse(false);
             scaleTransitionForTitleLabel.setFromX(0);
             scaleTransitionForTitleLabel.setFromY(0);
             scaleTransitionForTitleLabel.setToX(0.90);
             scaleTransitionForTitleLabel.setToY(0.90);

             scaleTransitionForHBoxFor5Icons.setToX(1);
             scaleTransitionForHBoxFor5Icons.setToY(1);
             scaleTransitionForBackButton.setToX(1);
             scaleTransitionForBackButton.setToY(1);

             scaleTransitionForCountriesToggleButton.setToX(1);
             scaleTransitionForCountriesToggleButton.setToY(1);
             scaleTransitionForUSAStatesToggleButton.setToX(1);
             scaleTransitionForUSAStatesToggleButton.setToY(1);
             scaleTransitionForGreekCountiesToggleButton.setToX(1);
             scaleTransitionForGreekCountiesToggleButton.setToY(1);
             scaleTransitionForAttractionsToggleButton.setToX(1);
             scaleTransitionForAttractionsToggleButton.setToY(1);

             if(animationsUsed == ANIMATIONS.ALL)
             {
                 scaleTransitionForTitleLabel.setOnFinished(ev ->
                 {
                     scaleTransitionForTitleLabel.setOnFinished(eve -> {});
                     startTextAnimation();
                 });
             }

             playPopUpSound();
             scaleTransitionForCountriesToggleButton.playFromStart();
             scaleTransitionForUSAStatesToggleButton.playFromStart();
             scaleTransitionForGreekCountiesToggleButton.playFromStart();
             scaleTransitionForAttractionsToggleButton.playFromStart();
             scaleTransitionForTitleLabel.playFromStart();
             scaleTransitionForHBoxFor5Icons.playFromStart();
             scaleTransitionForBackButton.playFromStart();
         }),
         new KeyFrame(Duration.millis(1000), e ->
         {
	         if(hBoxMainForCountriesAndContinents.isVisible())
	         {
		         scaleTransitionForHBoxMainForCountriesAndContinents.setToX(1);
		         scaleTransitionForHBoxMainForCountriesAndContinents.setToY(1);
		
		         playPopUpSound();
		         scaleTransitionForHBoxMainForCountriesAndContinents.playFromStart();
	         }
	         else if(hBoxMainForUSA.isVisible())
	         {
		         scaleTransitionForHBoxMainForUSA.setToX(1);
		         scaleTransitionForHBoxMainForUSA.setToY(1);
		
		         playPopUpSound();
		         scaleTransitionForHBoxMainForUSA.playFromStart();
	         }
	         else if(hBoxMainForGreece.isVisible())
	         {
		         scaleTransitionForHBoxMainForGreece.setToX(1);
		         scaleTransitionForHBoxMainForGreece.setToY(1);
		
		         playPopUpSound();
		         scaleTransitionForHBoxMainForGreece.playFromStart();
	         }
	         else if(hBoxMainForAttractions.isVisible())
	         {
		         scaleTransitionForHBoxMainForAttractions.setToX(1);
		         scaleTransitionForHBoxMainForAttractions.setToY(1);
		
		         playPopUpSound();
		         scaleTransitionForHBoxMainForAttractions.playFromStart();
	         }
         }),
         new KeyFrame(Duration.millis(1300), e ->
         {
	         backButton.setDisable(false);
	         hBoxForToggleButtons.setDisable(false);
	         hBoxFor5Icons.setDisable(false);
	         hBoxMainForCountriesAndContinents.setDisable(false);
	         hBoxMainForUSA.setDisable(false);
	         hBoxMainForGreece.setDisable(false);
	         hBoxMainForAttractions.setDisable(false);
         }));
		
		timeLineToHideAllStuff = new Timeline(
         new KeyFrame(Duration.millis(200), e ->
         {
	         backButton.setDisable(true);
	         hBoxForToggleButtons.setDisable(true);
	         hBoxFor5Icons.setDisable(true);
	         hBoxMainForCountriesAndContinents.setDisable(true);
	         hBoxMainForUSA.setDisable(true);
	         hBoxMainForGreece.setDisable(true);
	         hBoxMainForAttractions.setDisable(true);
	
	         if(hBoxMainForCountriesAndContinents.isVisible())
	         {
		         scaleTransitionForHBoxMainForCountriesAndContinents.setToX(0);
		         scaleTransitionForHBoxMainForCountriesAndContinents.setToY(0);
		
		         playMinimizeSound();
		         scaleTransitionForHBoxMainForCountriesAndContinents.playFromStart();
	         }
	         else if(hBoxMainForUSA.isVisible())
	         {
		         scaleTransitionForHBoxMainForUSA.setToX(0);
		         scaleTransitionForHBoxMainForUSA.setToY(0);
		
		         playMinimizeSound();
		         scaleTransitionForHBoxMainForUSA.playFromStart();
	         }
	         else if(hBoxMainForGreece.isVisible())
	         {
		         scaleTransitionForHBoxMainForGreece.setToX(0);
		         scaleTransitionForHBoxMainForGreece.setToY(0);
		
		         playMinimizeSound();
		         scaleTransitionForHBoxMainForGreece.playFromStart();
	         }
	         else if(hBoxMainForAttractions.isVisible())
	         {
		         scaleTransitionForHBoxMainForAttractions.setToX(0);
		         scaleTransitionForHBoxMainForAttractions.setToY(0);
		
		         playMinimizeSound();
		         scaleTransitionForHBoxMainForAttractions.playFromStart();
	         }
         }),
         new KeyFrame(Duration.millis(500), e ->
         {
	         if(gridViewForImagesForCountriesAndContinents.getItems() != emptyObservableList) gridViewForImagesForCountriesAndContinents.setItems(emptyObservableList);
	         else if(gridViewForImagesForUSA.getItems() != emptyObservableList) gridViewForImagesForUSA.setItems(emptyObservableList);
	         
             scaleTransitionForTitleLabel.setDuration(Duration.millis(300));
             scaleTransitionForTitleLabel.setFromX(titleLabel.getScaleX());
             scaleTransitionForTitleLabel.setFromY(titleLabel.getScaleY());
             scaleTransitionForTitleLabel.setToX(0);
             scaleTransitionForTitleLabel.setToY(0);
             scaleTransitionForTitleLabel.setAutoReverse(false);
             scaleTransitionForTitleLabel.setCycleCount(1);
	
	         scaleTransitionForTitleLabel.setOnFinished(ev -> {});

             scaleTransitionForHBoxFor5Icons.setToX(0);
             scaleTransitionForHBoxFor5Icons.setToY(0);
             scaleTransitionForBackButton.setToX(0);
             scaleTransitionForBackButton.setToY(0);

             scaleTransitionForCountriesToggleButton.setToX(0);
             scaleTransitionForCountriesToggleButton.setToY(0);
             scaleTransitionForUSAStatesToggleButton.setToX(0);
             scaleTransitionForUSAStatesToggleButton.setToY(0);
             scaleTransitionForGreekCountiesToggleButton.setToX(0);
             scaleTransitionForGreekCountiesToggleButton.setToY(0);
             scaleTransitionForAttractionsToggleButton.setToX(0);
             scaleTransitionForAttractionsToggleButton.setToY(0);

             scaleTransitionForCountriesToggleButton.playFromStart();
             scaleTransitionForUSAStatesToggleButton.playFromStart();
             scaleTransitionForGreekCountiesToggleButton.playFromStart();
             scaleTransitionForAttractionsToggleButton.playFromStart();

             playMinimizeSound();
             scaleTransitionForTitleLabel.playFromStart();
             scaleTransitionForHBoxFor5Icons.playFromStart();
             scaleTransitionForBackButton.playFromStart();
         }),
         new KeyFrame(Duration.millis(800), e ->
         {
             translateTransitionForTitleImage.setToY(-1.0 * (titleImage.getLayoutY() + titleImage.getBoundsInParent().getHeight() + 20));
             translateTransitionForWoodImageFor5Icons.setToY(-1.0 * (woodPanelFor5IconsImage.getLayoutY() + woodPanelFor5IconsImage.getBoundsInParent().getHeight() + 20));

             playSlideSound();
             translateTransitionForTitleImage.playFromStart();
             translateTransitionForWoodImageFor5Icons.playFromStart();

             if(vBoxForSound.isVisible())
             {
                 translateTransitionForVBoxForSound.setToY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
                 translateTransitionForVBoxForSound.playFromStart();
             }
         }),
         new KeyFrame(Duration.millis(1100), e ->
         {
             fadeTransitionForMovingEarthImage.setToValue(0);
             fadeTransitionForMovingEarthImage.playFromStart();
         }),
         new KeyFrame(Duration.millis(1500), e ->
         {
             if(animationsUsed == ANIMATIONS.ALL) pauseEarthAnimation();
             welcomeScreen.showScreen(false);
	
	         backButton.setDisable(false);
	         hBoxForToggleButtons.setDisable(false);
	         hBoxFor5Icons.setDisable(false);
	         hBoxMainForCountriesAndContinents.setDisable(false);
	         hBoxMainForUSA.setDisable(false);
	         hBoxMainForGreece.setDisable(false);
	         hBoxMainForAttractions.setDisable(false);
         }));
		
		timelineToShowSoundOptions = new Timeline(
            new KeyFrame(Duration.millis(0), e ->
             {
                 soundIcon.setDisable(true);

                 if(OS == OsCheck.OSType.Windows)
                 {
                     translateTransitionForTitleImage.setToX(-0.1068 * stage.getWidth());
                     translateTransitionForTitleLabel.setToX(-0.1068 * stage.getWidth());
                 }
                 else
                 {
                     translateTransitionForTitleImage.setToX(0.1068 * stage.getWidth());
                     translateTransitionForTitleLabel.setToX(0.1068 * stage.getWidth());
                 }
                 translateTransitionForTitleImage.setToY(0);
                 translateTransitionForTitleLabel.setToY(0);

                 playSlideSound();
                 translateTransitionForTitleImage.playFromStart();
                 translateTransitionForTitleLabel.playFromStart();
             }),
             new KeyFrame(Duration.millis(150), e ->
             {
                 vBoxForSound.setVisible(true);
                 translateTransitionForVBoxForSound.setToY(0);

                 playSlideSound();
                 translateTransitionForVBoxForSound.playFromStart();
             }),
             new KeyFrame(Duration.millis(450), e -> soundIcon.setDisable(false)));
		
		timelineToHideSoundOptions = new Timeline(
         new KeyFrame(Duration.millis(0), e ->
         {
             soundIcon.setDisable(true);

             translateTransitionForVBoxForSound.setToY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));

             playSlideSound();
             translateTransitionForVBoxForSound.playFromStart();
         }),
         new KeyFrame(Duration.millis(150), e ->
         {
             translateTransitionForTitleImage.setToY(0);
             translateTransitionForTitleImage.setToX(0);
             translateTransitionForTitleLabel.setToY(0);
             translateTransitionForTitleLabel.setToX(0);

             playSlideSound();
             translateTransitionForTitleImage.playFromStart();
             translateTransitionForTitleLabel.playFromStart();
         }),
         new KeyFrame(Duration.millis(450), e ->
         {
             soundIcon.setDisable(false);
             vBoxForSound.setVisible(false);
         }));
	}
	
	void showScreen()
	{
		setInitialStateForAllNodes();
		
		if (fullScreenMode) setFullScreenMode();
		else setWindowedMode();
		
		mainScene.setRoot(anchorPane);
		
		if(animationsUsed != ANIMATIONS.NO) timeLineToShowAllStuff.playFromStart();
	}
	
	private void setInitialStateForAllNodes()
	{
		if(previousChalkboardImage.getImage() == null || !woodenFrameImage.getImage().equals(FRAME_IMAGE))
		{
			previousChalkboardImage.setImage(CHALKBOARD_BACKGROUND_IMAGE);
			woodenFrameImage.setImage(FRAME_IMAGE);
			movingEarthImage.setImage(MOVING_EARTH_IMAGE_1);
			woodPanelFor5IconsImage.setImage(WOOD_BACKGROUND_IMAGE_FOR_5_BUTTONS);
			titleImage.setImage(EMPTY_WOOD_BACKGROUND_PANEL_SMALL_ROPE);
			backArrowImage.setImage(BACK_ARROW);
			previousInBigImageButton.setImage(BACK_ARROW);
			nextInBigImageButton.setImage(BACK_ARROW);
			exitBigImage.setImage(X_ICON);
			zoomInBigImage.setImage(ZOOM_IN_ICON);
			zoomOutBigImage.setImage(ZOOM_OUT_ICON);
			backArrowImage.setImage(BACK_ARROW);
		}
		
		//IF ATLAS SCREEN LOADS FOR THE FIRST TIME INITIALIZE OBSERVABLE LISTS
		if(countriesObservableNamesList == null)
		{
			countriesObservableNamesList = FXCollections.observableArrayList();
			countriesObservableNamesList.add("-");
			countriesSortList = FXCollections.observableArrayList();
			
			capitalsOfCountriesObservableNamesList = FXCollections.observableArrayList();
			capitalsOfCountriesObservableNamesList.add("-");
			capitalsOfCountriesSortList = FXCollections.observableArrayList();
			
			continentsObservableNamesList = FXCollections.observableArrayList();
			continentsObservableNamesList.add("-");
			continentsSortList = FXCollections.observableArrayList();
			
			statesOfUSAObservableNamesList = FXCollections.observableArrayList();
			statesOfUSAObservableNamesList.add("-");
			statesOfUSASortList = FXCollections.observableArrayList();
			
			capitalsOfStatesObservableNamesList = FXCollections.observableArrayList();
			capitalsOfStatesObservableNamesList.add("-");
			capitalsOfStatesSortList = FXCollections.observableArrayList();
			
			greekDecentralizedAdministrationsObservableNamesList = FXCollections.observableArrayList();
			greekDecentralizedAdministrationsObservableNamesList.add("-");
			greekDecentralizedAdministrationsSortList = FXCollections.observableArrayList();
			
			greekRegionsObservableNamesList = FXCollections.observableArrayList();
			greekRegionsObservableNamesList.add("-");
			greekRegionsSortList = FXCollections.observableArrayList();
			
			greekRegionalUnitsObservableNamesList = FXCollections.observableArrayList();
			greekRegionalUnitsObservableNamesList.add("-");
			greekRegionalUnitsSortList = FXCollections.observableArrayList();
			
			attractionsObservableNamesList = FXCollections.observableArrayList();
			attractionsObservableNamesList.add("-");
			attractionsSortList = FXCollections.observableArrayList();
			
			emptyObservableList = FXCollections.observableArrayList();
		}
		
		//IF ATLAS SCREEN LOADS FOR THE FIRST TIME OR LANGUAGE IS CHANGED LOAD AGAIN DATA IN OBSERVABLE LISTS
		if(getCurrentLanguage() == LANGUAGE.GREEK && !countriesObservableNamesList.get(0).equals(countries[0].getNameInGreek()))
		{
			countriesObservableNamesList.clear();
			countriesSortList.clear();
			for (short i = 0; i < NUM_ALL_COUNTRIES; i++)
			{
				String s = countries[i].getNameInGreek();
				switch (s)
				{
					case "Πρώην Γιουγκοσλαβική Δημοκρατία της Μακεδονίας":s = "Πρώην Γιουγκοσλαβική\nΔημοκρατία της Μακεδονίας";break;
					case "Κοινοπολιτεία των Βορείων Μαριανών Νήσων":s = "Κοινοπολιτεία των Βορείων\nΜαριανών Νήσων";break;
					case "Ομόσπονδες Πολιτείες της Μικρονησίας":s = "Ομόσπονδες Πολιτείες\nτης Μικρονησίας";break;
				}
				
				countriesObservableNamesList.add(s);
				countriesSortList.add(i);
			}
			
			capitalsOfCountriesObservableNamesList.clear();
			capitalsOfCountriesSortList.clear();
			ArrayList<Short> tempList = new ArrayList<>();
			for (short i = 0; i < NUM_ALL_COUNTRIES; i++)
			{
				String s = countries[i].getCapitalName();
				switch (s)
				{
					case "Σρι Δζαγιαβαρντενεπούρα Κόττε":s = "Σρι Δζαγιαβαρντενεπούρα\nΚόττε";break;
					case "Φλάινγκ Φις Κόουβ (\"Ο Καταυλισμός\")":s = "Φλάινγκ Φις Κόουβ\n(\"Ο Καταυλισμός\")";break;
				}
				
				
				capitalsOfCountriesObservableNamesList.add(s);
				capitalsOfCountriesSortList.add(i);
				tempList.add(countries[i].getPositionInCapitals());
			}
			for (short i = 0; i < NUM_ALL_COUNTRIES - 1; i++)
				for (short y = 0; y < NUM_ALL_COUNTRIES - i - 1; y++)
					if ((tempList.get(y).compareTo(tempList.get(y + 1))) > 0)
					{
						String temp = capitalsOfCountriesObservableNamesList.get(y);
						short s = capitalsOfCountriesSortList.get(y);
						short t = tempList.get(y);
						
						capitalsOfCountriesObservableNamesList.set(y, capitalsOfCountriesObservableNamesList.get(y + 1));
						capitalsOfCountriesSortList.set(y, capitalsOfCountriesSortList.get(y + 1));
						tempList.set(y, tempList.get(y + 1));
						
						capitalsOfCountriesObservableNamesList.set(y + 1, temp);
						capitalsOfCountriesSortList.set(y + 1, s);
						tempList.set(y + 1, t);
					}
			
			continentsObservableNamesList.clear();
			continentsSortList.clear();
			for (short i = 0; i < NUM_ALL_CONTINENTS; i++)
			{
				continentsObservableNamesList.add(continents[i].getNameInGreek());
				continentsSortList.add(i);
			}
			
			statesOfUSAObservableNamesList.clear();
			statesOfUSASortList.clear();
			for (short i = 0; i < NUM_ALL_USA_STATES; i++)
			{
				statesOfUSAObservableNamesList.add(statesOfUSA[i].getNameInGreek());
				statesOfUSASortList.add(i);
			}
			
			capitalsOfStatesObservableNamesList.clear();
			capitalsOfStatesSortList.clear();
			tempList.clear();
			for (short i = 0; i < NUM_ALL_USA_STATES; i++)
			{
				capitalsOfStatesObservableNamesList.add(statesOfUSA[i].getCapitalName());
				capitalsOfStatesSortList.add(i);
				tempList.add(statesOfUSA[i].getPositionInCapitals());
			}
			for (short i = 0; i < NUM_ALL_USA_STATES - 1; i++)
				for (short y = 0; y < NUM_ALL_USA_STATES - i - 1; y++)
					if ((tempList.get(y).compareTo(tempList.get(y + 1))) > 0)
					{
						String temp = capitalsOfStatesObservableNamesList.get(y);
						short s = capitalsOfStatesSortList.get(y);
						short t = tempList.get(y);
						
						capitalsOfStatesObservableNamesList.set(y, capitalsOfStatesObservableNamesList.get(y + 1));
						capitalsOfStatesSortList.set(y, capitalsOfStatesSortList.get(y + 1));
						tempList.set(y, tempList.get(y + 1));
						
						capitalsOfStatesObservableNamesList.set(y + 1, temp);
						capitalsOfStatesSortList.set(y + 1, s);
						tempList.set(y + 1, t);
					}
			
			greekDecentralizedAdministrationsObservableNamesList.clear();
			greekDecentralizedAdministrationsSortList.clear();
			for (short i = 0; i < NUM_ALL_GREEK_DEC_ADMIN; i++)
			{
				String s = greekDecAdm[i].getNameInGreek();
				switch (s)
				{
					case "Αποκεντρωμένη Διοίκηση Ηπείρου - Δυτικής Μακεδονίας":s = "Αποκεντρωμένη Διοίκηση Ηπείρου -\nΔυτικής Μακεδονίας";break;
					case "Αποκεντρωμένη Διοίκηση Θεσσαλίας - Στερεάς Ελλάδας":s = "Αποκεντρωμένη Διοίκηση Θεσσαλίας -\nΣτερεάς Ελλάδας";break;
					case "Αποκεντρωμένη Διοίκηση Μακεδονίας - Θράκης":s = "Αποκεντρωμένη Διοίκηση Μακεδονίας -\nΘράκης";break;
					case "Αποκεντρωμένη Διοίκηση Πελοποννήσου, Δυτικής Ελλάδας και Ιονίου":s = "Αποκεντρωμένη Διοίκηση Πελοπον-\nνήσου, Δυτικής Ελλάδας και Ιονίου";break;
				}
				
				greekDecentralizedAdministrationsObservableNamesList.add(s);
				greekDecentralizedAdministrationsSortList.add(i);
			}
			
			greekRegionsObservableNamesList.clear();
			greekRegionsSortList.clear();
			for (short i = 0; i < NUM_ALL_GREEK_REGIONS; i++)
			{
				String s = greekRegions[i].getNameInGreek();
				if(s.equals("Περιφέρεια Ανατολικής Μακεδονίας και Θράκης")) s = "Περιφέρεια Ανατολικής Μακεδονίας\nκαι Θράκης";
				
				greekRegionsObservableNamesList.add(s);
				greekRegionsSortList.add(i);
			}
			
			greekRegionalUnitsObservableNamesList.clear();
			greekRegionalUnitsSortList.clear();
			for (short i = 0; i < NUM_ALL_GREEK_REGIONAL_UNITS; i++)
			{
				String s = greekRegionalUnits[i].getNameInGreek();
				switch (s)
				{
					case "Περιφερειακή ενότητα Αιτωλοακαρνανίας":s = "Περιφερειακή ενότητα\nΑιτωλοακαρνανίας";break;
					case "Περιφερειακή ενότητα Ανατολικής Αττικής":s = "Περιφερειακή ενότητα\nΑνατολικής Αττικής";break;
					case "Περιφερειακή ενότητα Βορείου Τομέα Αθηνών":s = "Περιφερειακή ενότητα\nΒορείου Τομέα Αθηνών";break;
					case "Περιφερειακή ενότητα Δυτικής Αττικής":s = "Περιφερειακή ενότητα\nΔυτικής Αττικής";break;
					case "Περιφερειακή ενότητα Δυτικού Τομέα Αθηνών":s = "Περιφερειακή ενότητα\nΔυτικού Τομέα Αθηνών";break;
					case "Περιφερειακή ενότητα Θεσσαλονίκης":s = "Περιφερειακή ενότητα\nΘεσσαλονίκης";break;
					case "Περιφερειακή ενότητα Κέας - Κύθνου":s = "Περιφερειακή ενότητα\nΚέας - Κύθνου";break;
					case "Περιφερειακή ενότητα Κεντρικού Τομέα Αθηνών":s = "Περιφερειακή ενότητα\nΚεντρικού Τομέα Αθηνών";break;
					case "Περιφερειακή ενότητα Νήσων Αττικής":s = "Περιφερειακή ενότητα Νήσων\nΑττικής";break;
					case "Περιφερειακή ενότητα Νοτίου Τομέα Αθηνών":s = "Περιφερειακή ενότητα\nΝοτίου Τομέα Αθηνών";break;
				}
				
				greekRegionalUnitsObservableNamesList.add(s);
				greekRegionalUnitsSortList.add(i);
			}
			
//			attractionsObservableNamesList.clear();
//			attractionsSortList.clear();
//			for (short i = 0; i < NUM_ALL_ATTRACTIONS; i++)
//			{
//				String s = attractions[i].getNameInGreek();
//
//				switch (s)
//				{
//					case "Βασιλικό Αστεροσκοπείο του Γκρήνουιτς":s = "Βασιλικό Αστεροσκοπείο του\nΓκρήνουιτς";break;
//					case "Καθεδρικός Ναός του Σωτήρος Χριστού":s = "Καθεδρικός Ναός του Σωτήρος\nΧριστού";break;
//				}
//
//				attractionsObservableNamesList.add(s);
//				attractionsSortList.add(i);
//			}
		}
		else if (getCurrentLanguage() == LANGUAGE.ENGLISH && !countriesObservableNamesList.get(0).equals(countries[0].getNameInEnglish()))
		{
			countriesObservableNamesList.clear();
			countriesSortList.clear();
			for (short i = 0; i < NUM_ALL_COUNTRIES; i++)
			{
				String s = countries[i].getNameInEnglish();
				switch (s)
				{
					case "Federated States of Micronesia":s = "Federated States\nof Micronesia";break;
					case "Former Yugoslav Republic of Macedonia":s = "Former Yugoslav\nRepublic of Macedonia";break;
					case "Commonwealth of the Northern Mariana Islands":s = "Commonwealth of the\nNorthern Mariana Islands";break;
				}
				
				countriesObservableNamesList.add(s);
				countriesSortList.add(i);
			}
			for (short i = 0; i < NUM_ALL_COUNTRIES - 1; i++)
				for (short y = 0; y < NUM_ALL_COUNTRIES - i - 1; y++)
					if (countriesObservableNamesList.get(y).compareTo(countriesObservableNamesList.get(y + 1)) > 0)
					{
						String temp = countriesObservableNamesList.get(y);
						short s = countriesSortList.get(y);
						
						countriesObservableNamesList.set(y, countriesObservableNamesList.get(y + 1));
						countriesSortList.set(y, countriesSortList.get(y + 1));
						
						countriesObservableNamesList.set(y + 1, temp);
						countriesSortList.set(y + 1, s);
					}
			
			capitalsOfCountriesObservableNamesList.clear();
			capitalsOfCountriesSortList.clear();
			for (short i = 0; i < NUM_ALL_COUNTRIES; i++)
			{
				String s = countries[i].getCapitalName();
				if(s.equals("Flying Fish Cove (\"The Settlement\")")) s = "Flying Fish Cove\n(\"The Settlement\")";
				
				capitalsOfCountriesObservableNamesList.add(s);
				capitalsOfCountriesSortList.add(i);
			}
			for (short i = 0; i < NUM_ALL_COUNTRIES - 1; i++)
				for (short y = 0; y < NUM_ALL_COUNTRIES - i - 1; y++)
					if (capitalsOfCountriesObservableNamesList.get(y).compareTo(capitalsOfCountriesObservableNamesList.get(y + 1)) > 0)
					{
						String temp = capitalsOfCountriesObservableNamesList.get(y);
						short s = capitalsOfCountriesSortList.get(y);
						
						capitalsOfCountriesObservableNamesList.set(y, capitalsOfCountriesObservableNamesList.get(y + 1));
						capitalsOfCountriesSortList.set(y, capitalsOfCountriesSortList.get(y + 1));
						
						capitalsOfCountriesObservableNamesList.set(y + 1, temp);
						capitalsOfCountriesSortList.set(y + 1, s);
					}
			
			continentsObservableNamesList.clear();
			continentsSortList.clear();
			for (short i = 0; i < NUM_ALL_CONTINENTS; i++)
			{
				continentsObservableNamesList.add(continents[i].getNameInEnglish());
				continentsSortList.add(i);
			}
			for (short i = 0; i < NUM_ALL_CONTINENTS - 1; i++)
				for (short y = 0; y < NUM_ALL_CONTINENTS - i - 1; y++)
					if (continentsObservableNamesList.get(y).compareTo(continentsObservableNamesList.get(y + 1)) > 0)
					{
						String temp = continentsObservableNamesList.get(y);
						short s = continentsSortList.get(y);
						
						continentsObservableNamesList.set(y, continentsObservableNamesList.get(y + 1));
						continentsSortList.set(y, continentsSortList.get(y + 1));
						
						continentsObservableNamesList.set(y + 1, temp);
						continentsSortList.set(y + 1, s);
					}
			
			statesOfUSAObservableNamesList.clear();
			statesOfUSASortList.clear();
			for (short i = 0; i < NUM_ALL_USA_STATES; i++)
			{
				statesOfUSAObservableNamesList.add(statesOfUSA[i].getNameInEnglish());
				statesOfUSASortList.add(i);
			}
			for (short i = 0; i < NUM_ALL_USA_STATES - 1; i++)
				for (short y = 0; y < NUM_ALL_USA_STATES - i - 1; y++)
					if (statesOfUSAObservableNamesList.get(y).compareTo(statesOfUSAObservableNamesList.get(y + 1)) > 0)
					{
						String temp = statesOfUSAObservableNamesList.get(y);
						short s = statesOfUSASortList.get(y);
						
						statesOfUSAObservableNamesList.set(y, statesOfUSAObservableNamesList.get(y + 1));
						statesOfUSASortList.set(y, statesOfUSASortList.get(y + 1));
						
						statesOfUSAObservableNamesList.set(y + 1, temp);
						statesOfUSASortList.set(y + 1, s);
					}
			
			capitalsOfStatesObservableNamesList.clear();
			capitalsOfStatesSortList.clear();
			for (short i = 0; i < NUM_ALL_USA_STATES; i++)
			{
				capitalsOfStatesObservableNamesList.add(statesOfUSA[i].getCapitalName());
				capitalsOfStatesSortList.add(i);
			}
			for (short i = 0; i < NUM_ALL_USA_STATES - 1; i++)
				for (short y = 0; y < NUM_ALL_USA_STATES - i - 1; y++)
					if (capitalsOfStatesObservableNamesList.get(y).compareTo(capitalsOfStatesObservableNamesList.get(y + 1)) > 0)
					{
						String temp = capitalsOfStatesObservableNamesList.get(y);
						short s = capitalsOfStatesSortList.get(y);
						
						capitalsOfStatesObservableNamesList.set(y, capitalsOfStatesObservableNamesList.get(y + 1));
						capitalsOfStatesSortList.set(y, capitalsOfStatesSortList.get(y + 1));
						
						capitalsOfStatesObservableNamesList.set(y + 1, temp);
						capitalsOfStatesSortList.set(y + 1, s);
					}
			
			greekDecentralizedAdministrationsObservableNamesList.clear();
			greekDecentralizedAdministrationsSortList.clear();
			for (short i = 0; i < NUM_ALL_GREEK_DEC_ADMIN; i++)
			{
				String s = greekDecAdm[i].getNameInEnglish();
				switch (s)
				{
					case "Decentralized Administration of Attica":s = "Decentralized Administration of\nAttica";break;
					case "Decentralized Administration of Crete":s = "Decentralized Administration of\nCrete";break;
					case "Decentralized Administration of the Aegean":s = "Decentralized Administration of\nthe Aegean";break;
					case "Decentralized Administration of Epirus and Western Macedonia":s = "Decentralized Administration of\nEpirus and Western Macedonia";break;
					case "Decentralized Administration of Thessaly and Central Greece":s = "Decentralized Administration of\nThessaly and Central Greece";break;
					case "Decentralized Administration of Macedonia and Thrace":s = "Decentralized Administration of\nMacedonia and Thrace";break;
					case "Decentralized Administration of Peloponnese, Western Greece and the Ionian":s = "Decentralized Administration of\nPeloponnese, Western Greece and\nthe Ionian";break;
				}
				
				greekDecentralizedAdministrationsObservableNamesList.add(s);
				greekDecentralizedAdministrationsSortList.add(i);
			}
			for (short i = 0; i < NUM_ALL_GREEK_DEC_ADMIN - 1; i++)
				for (short y = 0; y < NUM_ALL_GREEK_DEC_ADMIN - i - 1; y++)
					if (greekDecentralizedAdministrationsObservableNamesList.get(y).compareTo(greekDecentralizedAdministrationsObservableNamesList.get(y + 1)) > 0)
					{
						String temp = greekDecentralizedAdministrationsObservableNamesList.get(y);
						short s = greekDecentralizedAdministrationsSortList.get(y);
						
						greekDecentralizedAdministrationsObservableNamesList.set(y, greekDecentralizedAdministrationsObservableNamesList.get(y + 1));
						greekDecentralizedAdministrationsSortList.set(y, greekDecentralizedAdministrationsSortList.get(y + 1));
						
						greekDecentralizedAdministrationsObservableNamesList.set(y + 1, temp);
						greekDecentralizedAdministrationsSortList.set(y + 1, s);
					}
			
			greekRegionsObservableNamesList.clear();
			greekRegionsSortList.clear();
			for (short i = 0; i < NUM_ALL_GREEK_REGIONS; i++)
			{
				String s = greekRegions[i].getNameInEnglish();
				if(s.equals("Region of Eastern Macedonia and Thrace")) s = "Region of Eastern Macedonia and\nThrace";
				
				greekRegionsObservableNamesList.add(s);
				greekRegionsSortList.add(i);
			}
			for (short i = 0; i < NUM_ALL_GREEK_REGIONS - 1; i++)
				for (short y = 0; y < NUM_ALL_GREEK_REGIONS - i - 1; y++)
					if (greekRegionsObservableNamesList.get(y).compareTo(greekRegionsObservableNamesList.get(y + 1)) > 0)
					{
						String temp = greekRegionsObservableNamesList.get(y);
						short s = greekRegionsSortList.get(y);
						
						greekRegionsObservableNamesList.set(y, greekRegionsObservableNamesList.get(y + 1));
						greekRegionsSortList.set(y, greekRegionsSortList.get(y + 1));
						
						greekRegionsObservableNamesList.set(y + 1, temp);
						greekRegionsSortList.set(y + 1, s);
					}
			
			greekRegionalUnitsObservableNamesList.clear();
			greekRegionalUnitsSortList.clear();
			for (short i = 0; i < NUM_ALL_GREEK_REGIONAL_UNITS; i++)
			{
				greekRegionalUnitsObservableNamesList.add(greekRegionalUnits[i].getNameInEnglish());
				greekRegionalUnitsSortList.add(i);
			}
			for (short i = 0; i < NUM_ALL_GREEK_REGIONAL_UNITS - 1; i++)
				for (short y = 0; y < NUM_ALL_GREEK_REGIONAL_UNITS - i - 1; y++)
					if (greekRegionalUnitsObservableNamesList.get(y).compareTo(greekRegionalUnitsObservableNamesList.get(y + 1)) > 0)
					{
						String temp = greekRegionalUnitsObservableNamesList.get(y);
						short s = greekRegionalUnitsSortList.get(y);
						
						greekRegionalUnitsObservableNamesList.set(y, greekRegionalUnitsObservableNamesList.get(y + 1));
						greekRegionalUnitsSortList.set(y, greekRegionalUnitsSortList.get(y + 1));
						
						greekRegionalUnitsObservableNamesList.set(y + 1, temp);
						greekRegionalUnitsSortList.set(y + 1, s);
					}
			
			attractionsObservableNamesList.clear();
			attractionsSortList.clear();
			for (short i = 0; i < NUM_ALL_ATTRACTIONS; i++)
			{
				attractionsObservableNamesList.add(attractions[i].getNameInEnglish());
				attractionsSortList.add(i);
			}
			for (short i = 0; i < NUM_ALL_ATTRACTIONS - 1; i++)
				for (short y = 0; y < NUM_ALL_ATTRACTIONS - i - 1; y++)
					if (attractionsObservableNamesList.get(y).compareTo(attractionsObservableNamesList.get(y + 1)) > 0)
					{
						String temp = attractionsObservableNamesList.get(y);
						short s = attractionsSortList.get(y);
						
						attractionsObservableNamesList.set(y, attractionsObservableNamesList.get(y + 1));
						attractionsSortList.set(y, attractionsSortList.get(y + 1));
						
						attractionsObservableNamesList.set(y + 1, temp);
						attractionsSortList.set(y + 1, s);
					}
		}
		
		if(animationsUsed != ANIMATIONS.NO)
		{
			movingEarthImage.setOpacity(0);
			
			titleImage.setTranslateX(0);
			titleImage.setTranslateY(-1.0 * (titleImage.getLayoutY() + titleImage.getBoundsInParent().getHeight() + 20));
			
			titleLabel.setTranslateX(0);
			titleLabel.setTranslateY(0);
			
			woodPanelFor5IconsImage.setTranslateY(-1.0 * (woodPanelFor5IconsImage.getLayoutY() + woodPanelFor5IconsImage.getBoundsInParent().getHeight() + 20));
			
			continentsAndCountriesToggleButton.setScaleX(0);
			continentsAndCountriesToggleButton.setScaleY(0);
			USAToggleButton.setScaleX(0);
			USAToggleButton.setScaleY(0);
			greeceToggleButton.setScaleX(0);
			greeceToggleButton.setScaleY(0);
			attractionsToggleButton.setScaleX(0);
			attractionsToggleButton.setScaleY(0);
			
			if(hBoxMainForCountriesAndContinents.isVisible())
			{
				hBoxMainForCountriesAndContinents.setScaleX(0);
				hBoxMainForCountriesAndContinents.setScaleY(0);
			}
			else if(hBoxMainForUSA.isVisible())
			{
				hBoxMainForUSA.setScaleX(0);
				hBoxMainForUSA.setScaleY(0);
			}
			else if(hBoxMainForGreece.isVisible())
			{
				hBoxMainForGreece.setScaleX(0);
				hBoxMainForGreece.setScaleY(0);
			}
			else if(hBoxMainForAttractions.isVisible())
			{
				hBoxMainForAttractions.setScaleX(0);
				hBoxMainForAttractions.setScaleY(0);
			}
			
			backButton.setScaleX(0);
			backButton.setScaleY(0);
			
			hBoxFor5Icons.setScaleX(0);
			hBoxFor5Icons.setScaleY(0);
			
			titleLabel.setScaleX(0);
			titleLabel.setScaleY(0);
			
			if(animationsUsed == ANIMATIONS.ALL) playEarthAnimation();
		}
		else
		{
			titleImage.setTranslateX(0);
			titleImage.setTranslateY(0);
			
			titleLabel.setTranslateX(0);
			titleLabel.setTranslateY(0);
			
			woodPanelFor5IconsImage.setTranslateY(0);
			
			movingEarthImage.setOpacity(1);
			
			continentsAndCountriesToggleButton.setScaleX(1);
			continentsAndCountriesToggleButton.setScaleY(1);
			USAToggleButton.setScaleX(1);
			USAToggleButton.setScaleY(1);
			greeceToggleButton.setScaleX(1);
			greeceToggleButton.setScaleY(1);
			attractionsToggleButton.setScaleX(1);
			attractionsToggleButton.setScaleY(1);
			
			if(hBoxMainForCountriesAndContinents.isVisible())
			{
				hBoxMainForCountriesAndContinents.setScaleX(1);
				hBoxMainForCountriesAndContinents.setScaleY(1);
			}
			else if(hBoxMainForUSA.isVisible())
			{
				hBoxMainForUSA.setScaleX(1);
				hBoxMainForUSA.setScaleY(1);
			}
			else if(hBoxMainForGreece.isVisible())
			{
				hBoxMainForGreece.setScaleX(1);
				hBoxMainForGreece.setScaleY(1);
			}
			else if(hBoxMainForAttractions.isVisible())
			{
				hBoxMainForAttractions.setScaleX(1);
				hBoxMainForAttractions.setScaleY(1);
			}
			
			backButton.setScaleX(1);
			backButton.setScaleY(1);
			
			hBoxFor5Icons.setScaleX(1);
			hBoxFor5Icons.setScaleY(1);
			
			titleLabel.setScaleX(1);
			titleLabel.setScaleY(1);
			
			backButton.setDisable(false);
			hBoxForToggleButtons.setDisable(false);
			soundIcon.setDisable(false);
			minimizeIcon.setDisable(false);
			fullScreenIcon.setDisable(false);
		}
		
		if(animationsUsed == ANIMATIONS.ALL) movingEarthImage.setCursor(Cursor.HAND);
		else movingEarthImage.setCursor(Cursor.DEFAULT);
		
		vBoxForSound.setTranslateY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
		vBoxForSound.setVisible(false);
		
		rectangleForBigImage.setScaleX(0);
		rectangleForBigImage.setScaleY(0);
		bigImage.setScaleX(0);
		bigImage.setScaleY(0);
		
		rectangleToShowInfo.setScaleX(0);
		rectangleToShowInfo.setScaleY(0);
		textAreaForInfo.setScaleX(0);
		textAreaForInfo.setScaleY(0);
		
		labelForBigImage.setScaleX(0);
		labelForBigImage.setScaleY(0);
		previousInBigImageButton.setScaleX(0);
		previousInBigImageButton.setScaleY(0);
		nextInBigImageButton.setScaleX(0);
		nextInBigImageButton.setScaleY(0);
		exitBigImage.setScaleX(0);
		exitBigImage.setScaleY(0);
		zoomInBigImage.setScaleX(0);
		zoomInBigImage.setScaleX(0);
		zoomOutBigImage.setScaleY(0);
		zoomOutBigImage.setScaleY(0);
		
		//		--------------- SET VARIABLES AND TEXT ---------------
		if (typeOfGridViewImagesForCountriesAndContinents == null) typeOfGridViewImagesForCountriesAndContinents = GridViewImagesForCountriesAndContinentsType.NONE;
		if (typeOfGridViewImagesForUSA == null) typeOfGridViewImagesForUSA = GridViewImagesForUSAType.NONE;
		if (typeOfGridViewImagesForAttractions == null) typeOfGridViewImagesForAttractions = GridViewImagesForAttractionsType.NONE;
		
		setViewPortProperties();
		
		//COMBO BOXES
		int o1 = 0, o2 = 0, o3 = 0, o4 = 0;
		if(getIndexInOptionsForCountriesAndContinents() != -1) o1 = getIndexInOptionsForCountriesAndContinents();
		if(getIndexInOptionsForUSA() != -1) o2 = getIndexInOptionsForUSA();
		if(getIndexInOptionsForGreece() != -1) o3 = getIndexInOptionsForGreece();
		if(getIndexInOptionsForAttractions() != -1) o4 = getIndexInOptionsForAttractions();
		
		updateStrings();
		
		setIndexInOptionsForCountriesAndContinents(o1);
		setIndexInOptionsForUSA(o2);
		setIndexInOptionsForGreece(o3);
		setIndexInOptionsForAttractions(o4);
		
		if(getCategoryCurrentlyViewing() == 0) continentsAndCountriesToggleButton.setSelected(true);
		else if(getCategoryCurrentlyViewing() == 1) countriesAndContinentsToggleButtonPressed();
		else if(getCategoryCurrentlyViewing() == 2) usaStatesToggleButtonPressed();
		else if(getCategoryCurrentlyViewing() == 3) greeceToggleButtonPressed();
		else if(getCategoryCurrentlyViewing() == 4) attractionsToggleButtonPressed();
	}
	
	private void countriesAndContinentsToggleButtonPressed()
	{
		if((getIndexInOptionsForCountriesAndContinents() == 0 ||
		    getIndexInOptionsForCountriesAndContinents() == 1 ||
		    getIndexInOptionsForCountriesAndContinents() == 5) &&
		    typeOfGridViewImagesForCountriesAndContinents != GridViewImagesForCountriesAndContinentsType.NONE)
		{
			listViewForCountriesAndContinents.setVisible(true);
			hBoxMainForCountriesAndContinents.getChildren().remove(gridViewForImagesForCountriesAndContinents);
			hBoxMainForCountriesAndContinents.getChildren().addAll(scrollPaneForGridPaneForCountriesAndContinents, vBoxFor3ImagesForCountriesAndContinents);
			
			gridViewForImagesForCountriesAndContinents.setItems(emptyObservableList);
			
			typeOfGridViewImagesForCountriesAndContinents = GridViewImagesForCountriesAndContinentsType.NONE;
		}
		else if((getIndexInOptionsForCountriesAndContinents() == 2 ||
		         getIndexInOptionsForCountriesAndContinents() == 3 ||
		         getIndexInOptionsForCountriesAndContinents() == 4) &&
		         typeOfGridViewImagesForCountriesAndContinents == GridViewImagesForCountriesAndContinentsType.NONE)
		{
			listViewForCountriesAndContinents.setVisible(false);
			hBoxMainForCountriesAndContinents.getChildren().removeAll(scrollPaneForGridPaneForCountriesAndContinents, vBoxFor3ImagesForCountriesAndContinents);
			hBoxMainForCountriesAndContinents.getChildren().add(gridViewForImagesForCountriesAndContinents);
		}

        gridPaneLabelsForCountriesAndContinents[2][1].setTooltip(gridPaneTooltipsForCountriesAndContinents[2]);
        gridPaneLabelsForCountriesAndContinents[3][1].setTooltip(gridPaneTooltipsForCountriesAndContinents[3]);
        gridPaneLabelsForCountriesAndContinents[4][1].setTooltip(gridPaneTooltipsForCountriesAndContinents[4]);

		if (getIndexInOptionsForCountriesAndContinents() == 0)
		{
			flagLabelForCountriesAndContinents.setText(languageResourceBundle.getString("flagLabel"));
			coatOfArmsLabelForCountriesAndContinents.setText(languageResourceBundle.getString("coatOfArmsLabel"));
			
			listViewForCountriesAndContinents.setItems(countriesObservableNamesList);
			
			scrollPaneForGridPaneForCountriesAndContinents.setCursor(Cursor.DEFAULT);

            gridPaneLabelsForCountriesAndContinents[2][1].setCursor(null);
            gridPaneLabelsForCountriesAndContinents[3][1].setCursor(null);
            gridPaneLabelsForCountriesAndContinents[4][1].setCursor(null);

			if(gridPaneForLabelsForCountriesAndContinents.getChildren().contains(gridPaneLabelsForCountriesAndContinents[15][0]))
			{
				gridPaneForLabelsForCountriesAndContinents.getChildren().removeAll(
						gridPaneLabelsForCountriesAndContinents[15][0], gridPaneLabelsForCountriesAndContinents[15][1],
						gridPaneLabelsForCountriesAndContinents[16][0], gridPaneLabelsForCountriesAndContinents[16][1]);
			}
		}
		else if (getIndexInOptionsForCountriesAndContinents() == 1)
		{
			flagLabelForCountriesAndContinents.setText(languageResourceBundle.getString("flagLabel"));
			coatOfArmsLabelForCountriesAndContinents.setText(languageResourceBundle.getString("coatOfArmsLabel"));
			
			listViewForCountriesAndContinents.setItems(capitalsOfCountriesObservableNamesList);
			
			scrollPaneForGridPaneForCountriesAndContinents.setCursor(Cursor.DEFAULT);
			
			if(gridPaneForLabelsForCountriesAndContinents.getChildren().contains(gridPaneLabelsForCountriesAndContinents[15][0]))
			{
				gridPaneForLabelsForCountriesAndContinents.getChildren().removeAll(
						gridPaneLabelsForCountriesAndContinents[15][0], gridPaneLabelsForCountriesAndContinents[15][1],
						gridPaneLabelsForCountriesAndContinents[16][0], gridPaneLabelsForCountriesAndContinents[16][1]);
			}
		}
		else if (getIndexInOptionsForCountriesAndContinents() == 2 &&
		         typeOfGridViewImagesForCountriesAndContinents != GridViewImagesForCountriesAndContinentsType.FLAG ||
		         typeOfGridViewImagesForCountriesAndContinents == GridViewImagesForCountriesAndContinentsType.FLAG &&
		         gridViewForImagesForCountriesAndContinents.getItems() == emptyObservableList)
		{
			gridViewForImagesForCountriesAndContinents.setItems(emptyObservableList);
			gridViewForImagesForCountriesAndContinents.setItems(countriesSortList);
			
			typeOfGridViewImagesForCountriesAndContinents = GridViewImagesForCountriesAndContinentsType.FLAG;
		}
		else if (getIndexInOptionsForCountriesAndContinents() == 3 &&
		         typeOfGridViewImagesForCountriesAndContinents != GridViewImagesForCountriesAndContinentsType.COAT_OF_ARMS ||
		         typeOfGridViewImagesForCountriesAndContinents == GridViewImagesForCountriesAndContinentsType.COAT_OF_ARMS &&
		         gridViewForImagesForCountriesAndContinents.getItems() == emptyObservableList)
		{
			gridViewForImagesForCountriesAndContinents.setItems(emptyObservableList);
			gridViewForImagesForCountriesAndContinents.setItems(countriesSortList);
			
			typeOfGridViewImagesForCountriesAndContinents = GridViewImagesForCountriesAndContinentsType.COAT_OF_ARMS;
		}
		else if (getIndexInOptionsForCountriesAndContinents() == 4 &&
		         typeOfGridViewImagesForCountriesAndContinents != GridViewImagesForCountriesAndContinentsType.LOCATION ||
		         typeOfGridViewImagesForCountriesAndContinents == GridViewImagesForCountriesAndContinentsType.LOCATION &&
		         gridViewForImagesForCountriesAndContinents.getItems() == emptyObservableList)
		{
			gridViewForImagesForCountriesAndContinents.setItems(emptyObservableList);
			gridViewForImagesForCountriesAndContinents.setItems(countriesSortList);
			
			typeOfGridViewImagesForCountriesAndContinents = GridViewImagesForCountriesAndContinentsType.LOCATION;
		}
		else
		{
			flagLabelForCountriesAndContinents.setText("");
			coatOfArmsLabelForCountriesAndContinents.setText("");
			
			flagForCountriesAndContinentsImageSmall.setImage(null);
			coatOfArmsForCountriesAndContinentsImageSmall.setImage(null);
			
			listViewForCountriesAndContinents.setItems(continentsObservableNamesList);
			
			scrollPaneForGridPaneForCountriesAndContinents.setCursor(Cursor.MOVE);
			
			if(!gridPaneForLabelsForCountriesAndContinents.getChildren().contains(gridPaneLabelsForCountriesAndContinents[15][0]))
			{
				gridPaneForLabelsForCountriesAndContinents.add(gridPaneLabelsForCountriesAndContinents[15][0], 0, 15);
				gridPaneForLabelsForCountriesAndContinents.add(gridPaneLabelsForCountriesAndContinents[15][1], 1, 15);
				gridPaneForLabelsForCountriesAndContinents.add(gridPaneLabelsForCountriesAndContinents[16][0], 0, 16);
				gridPaneForLabelsForCountriesAndContinents.add(gridPaneLabelsForCountriesAndContinents[16][1], 1, 16);
			}
		}
		
		listViewForCountriesAndContinents.getSelectionModel().select(1);
		listViewForCountriesAndContinents.getSelectionModel().selectFirst();
		listViewForCountriesAndContinents.scrollTo(getIndexInListViewForCountriesAndContinents());
		
		setNamesForGridPaneLabels();
		
		recalculateUI(stage.getWidth(), stage.getHeight());
	}
	
	private void usaStatesToggleButtonPressed()
	{
		setNamesForGridPaneLabels();
		
		if((getIndexInOptionsForUSA() == 0 ||
		    getIndexInOptionsForUSA() == 1) &&
		    typeOfGridViewImagesForUSA != GridViewImagesForUSAType.NONE)
		{
			listViewForUSA.setVisible(true);
			hBoxMainForUSA.getChildren().remove(gridViewForImagesForUSA);
			hBoxMainForUSA.getChildren().addAll(scrollPaneForGridPaneForUSA, vBoxFor3ImagesForUSA);
			
			gridViewForImagesForUSA.setItems(emptyObservableList);
			
			typeOfGridViewImagesForUSA = GridViewImagesForUSAType.NONE;
		}
		else if((getIndexInOptionsForUSA() == 2 ||
		         getIndexInOptionsForUSA() == 3 ||
		         getIndexInOptionsForUSA() == 4) &&
		        typeOfGridViewImagesForUSA == GridViewImagesForUSAType.NONE)
		{
			listViewForUSA.setVisible(false);
			hBoxMainForUSA.getChildren().removeAll(scrollPaneForGridPaneForUSA, vBoxFor3ImagesForUSA);
			hBoxMainForUSA.getChildren().add(gridViewForImagesForUSA);
		}
		
		if(getIndexInOptionsForUSA() == 0)
		{
			listViewForUSA.setItems(statesOfUSAObservableNamesList);
		}
		else if(getIndexInOptionsForUSA() == 1)
		{
			listViewForUSA.setItems(capitalsOfStatesObservableNamesList);
		}
		else if (getIndexInOptionsForUSA() == 2 &&
		         typeOfGridViewImagesForUSA != GridViewImagesForUSAType.FLAG ||
		         typeOfGridViewImagesForUSA == GridViewImagesForUSAType.FLAG &&
		         gridViewForImagesForUSA.getItems() == emptyObservableList)
		{
			gridViewForImagesForUSA.setItems(emptyObservableList);
			gridViewForImagesForUSA.setItems(statesOfUSASortList);
			
			typeOfGridViewImagesForUSA = GridViewImagesForUSAType.FLAG;
		}
		else if (getIndexInOptionsForUSA() == 3 &&
		         typeOfGridViewImagesForUSA != GridViewImagesForUSAType.SEAL ||
		         typeOfGridViewImagesForUSA == GridViewImagesForUSAType.SEAL &&
		         gridViewForImagesForUSA.getItems() == emptyObservableList)
		{
			gridViewForImagesForUSA.setItems(emptyObservableList);
			gridViewForImagesForUSA.setItems(statesOfUSASortList);
			
			typeOfGridViewImagesForUSA = GridViewImagesForUSAType.SEAL;
		}
		else if (getIndexInOptionsForUSA() == 4 &&
		         typeOfGridViewImagesForUSA != GridViewImagesForUSAType.LOCATION ||
		         typeOfGridViewImagesForUSA == GridViewImagesForUSAType.LOCATION &&
		         gridViewForImagesForUSA.getItems() == emptyObservableList)
		{
			gridViewForImagesForUSA.setItems(emptyObservableList);
			gridViewForImagesForUSA.setItems(statesOfUSASortList);
			
			typeOfGridViewImagesForUSA = GridViewImagesForUSAType.LOCATION;
		}
		
		listViewForUSA.getSelectionModel().select(1);
		listViewForUSA.getSelectionModel().selectFirst();
		listViewForUSA.scrollTo(getIndexInListViewForUSA());
		
		recalculateUI(stage.getWidth(), stage.getHeight());
	}
	
	private void greeceToggleButtonPressed()
	{
		setNamesForGridPaneLabels();
		
		if (getIndexInOptionsForGreece() == 0)
		{
			gridPaneLabelsForGreece[10][1].setText("");
			gridPaneLabelsForGreece[11][1].setText("");
			gridPaneLabelsForGreece[12][1].setText("");
			gridPaneLabelsForGreece[13][1].setText("");
			gridPaneLabelsForGreece[14][1].setText("");
			
			gridPaneTooltipsForGreece[5].setText(languageResourceBundle.getString("websiteTooltip"));
			gridPaneTooltipsForGreece[6].setText(languageResourceBundle.getString("websiteTooltip"));
			gridPaneLabelsForGreece[5][1].setCursor(Cursor.HAND);
			gridPaneLabelsForGreece[6][1].setCursor(Cursor.HAND);
			gridPaneLabelsForGreece[7][1].setCursor(Cursor.HAND);
			gridPaneLabelsForGreece[8][1].setCursor(Cursor.HAND);
			gridPaneLabelsForGreece[9][1].setCursor(Cursor.HAND);
			gridPaneLabelsForGreece[10][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[11][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[12][1].setCursor(Cursor.DEFAULT);
			
			gridPaneLabelsForGreece[7][1].setTooltip(null);
			gridPaneLabelsForGreece[8][1].setTooltip(null);
			gridPaneLabelsForGreece[9][1].setTooltip(null);
			gridPaneLabelsForGreece[10][1].setTooltip(null);
			gridPaneLabelsForGreece[11][1].setTooltip(null);
			gridPaneLabelsForGreece[12][1].setTooltip(null);
			gridPaneLabelsForGreece[13][1].setTooltip(null);
			gridPaneLabelsForGreece[14][1].setTooltip(null);
			
			logoLabelForGreece.setCursor(Cursor.DEFAULT);
			
			listViewForGreece.setItems(greekDecentralizedAdministrationsObservableNamesList);
		}
		else if (getIndexInOptionsForGreece() == 1)
		{
			gridPaneLabelsForGreece[13][1].setText("");
			gridPaneLabelsForGreece[14][1].setText("");
			
			gridPaneTooltipsForGreece[9].setText(languageResourceBundle.getString("websiteTooltip"));
			gridPaneTooltipsForGreece[10].setText(languageResourceBundle.getString("websiteTooltip"));
			gridPaneLabelsForGreece[5][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[6][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[7][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[8][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[9][1].setCursor(Cursor.HAND);
			gridPaneLabelsForGreece[10][1].setCursor(Cursor.HAND);
			gridPaneLabelsForGreece[11][1].setCursor(Cursor.HAND);
			gridPaneLabelsForGreece[12][1].setCursor(Cursor.HAND);
			
			if(gridPaneLabelsForGreece[7][1].getTooltip() == null) gridPaneLabelsForGreece[7][1].setTooltip(gridPaneTooltipsForGreece[7]);
			if(gridPaneLabelsForGreece[8][1].getTooltip() == null) gridPaneLabelsForGreece[8][1].setTooltip(gridPaneTooltipsForGreece[8]);
			if(gridPaneLabelsForGreece[9][1].getTooltip() == null) gridPaneLabelsForGreece[9][1].setTooltip(gridPaneTooltipsForGreece[9]);
			if(gridPaneLabelsForGreece[10][1].getTooltip() == null) gridPaneLabelsForGreece[10][1].setTooltip(gridPaneTooltipsForGreece[10]);
			
			gridPaneLabelsForGreece[11][1].setTooltip(null);
			gridPaneLabelsForGreece[12][1].setTooltip(null);
			gridPaneLabelsForGreece[13][1].setTooltip(null);
			gridPaneLabelsForGreece[14][1].setTooltip(null);
			
			logoLabelForGreece.setCursor(Cursor.HAND);
			
			listViewForGreece.setItems(greekRegionsObservableNamesList);
		}
		else if(getIndexInOptionsForGreece() == 2)
		{
			gridPaneLabelsForGreece[13][1].setText("");
			gridPaneLabelsForGreece[14][1].setText("");
			
			gridPaneTooltipsForGreece[10].setText(languageResourceBundle.getString("websiteTooltip"));
			gridPaneTooltipsForGreece[11].setText(languageResourceBundle.getString("websiteTooltip"));
			gridPaneLabelsForGreece[5][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[6][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[7][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[8][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[9][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[10][1].setCursor(Cursor.HAND);
			gridPaneLabelsForGreece[11][1].setCursor(Cursor.HAND);
			gridPaneLabelsForGreece[12][1].setCursor(Cursor.HAND);
			
			if(gridPaneLabelsForGreece[7][1].getTooltip() == null) gridPaneLabelsForGreece[7][1].setTooltip(gridPaneTooltipsForGreece[7]);
			if(gridPaneLabelsForGreece[8][1].getTooltip() == null) gridPaneLabelsForGreece[8][1].setTooltip(gridPaneTooltipsForGreece[8]);
			if(gridPaneLabelsForGreece[9][1].getTooltip() == null) gridPaneLabelsForGreece[9][1].setTooltip(gridPaneTooltipsForGreece[9]);
			if(gridPaneLabelsForGreece[10][1].getTooltip() == null) gridPaneLabelsForGreece[10][1].setTooltip(gridPaneTooltipsForGreece[10]);
			if(gridPaneLabelsForGreece[11][1].getTooltip() == null) gridPaneLabelsForGreece[11][1].setTooltip(gridPaneTooltipsForGreece[11]);
			
			gridPaneLabelsForGreece[12][1].setTooltip(null);
			gridPaneLabelsForGreece[13][1].setTooltip(null);
			gridPaneLabelsForGreece[14][1].setTooltip(null);
			
			logoLabelForGreece.setCursor(Cursor.DEFAULT);
			
			listViewForGreece.setItems(greekRegionalUnitsObservableNamesList);
		}
		
		listViewForGreece.getSelectionModel().select(1);
		listViewForGreece.getSelectionModel().selectFirst();
		listViewForGreece.scrollTo(getIndexInListViewForGreece());
		
		recalculateUI(stage.getWidth(), stage.getHeight());
	}
	
	private void attractionsToggleButtonPressed()
	{
		setNamesForGridPaneLabels();
		
		if((getIndexInOptionsForAttractions() == 0) &&
		   typeOfGridViewImagesForAttractions != GridViewImagesForAttractionsType.NONE)
		{
			listViewForAttractions.setVisible(true);
			hBoxMainForAttractions.getChildren().remove(gridViewForImagesForAttractions);
			hBoxMainForAttractions.getChildren().addAll(gridPaneForAttractions, vBoxFor2ImagesForAttractions);
			
			gridViewForImagesForAttractions.setItems(emptyObservableList);
			
			typeOfGridViewImagesForAttractions = GridViewImagesForAttractionsType.NONE;
		}
		else if((getIndexInOptionsForAttractions() == 1 ||
		         getIndexInOptionsForAttractions() == 2) &&
		        typeOfGridViewImagesForAttractions == GridViewImagesForAttractionsType.NONE)
		{
			listViewForAttractions.setVisible(false);
			hBoxMainForAttractions.getChildren().removeAll(gridPaneForAttractions, vBoxFor2ImagesForAttractions);
			hBoxMainForAttractions.getChildren().add(gridViewForImagesForAttractions);
		}
		
		if(getIndexInOptionsForAttractions() == 0)
		{
			listViewForAttractions.setItems(attractionsObservableNamesList);
		}
		else if(getIndexInOptionsForAttractions() == 1 &&
		        typeOfGridViewImagesForAttractions != GridViewImagesForAttractionsType.ATTRACTION ||
		        typeOfGridViewImagesForAttractions == GridViewImagesForAttractionsType.ATTRACTION &&
		        gridViewForImagesForAttractions.getItems() == emptyObservableList)
		{
			gridViewForImagesForAttractions.setItems(emptyObservableList);
			gridViewForImagesForAttractions.setItems(attractionsSortList);
			
			typeOfGridViewImagesForAttractions = GridViewImagesForAttractionsType.ATTRACTION;
		}
		else if (getIndexInOptionsForAttractions() == 2 &&
		         typeOfGridViewImagesForAttractions != GridViewImagesForAttractionsType.LOCATION ||
		         typeOfGridViewImagesForAttractions == GridViewImagesForAttractionsType.LOCATION &&
		         gridViewForImagesForAttractions.getItems() == emptyObservableList)
		{
			gridViewForImagesForAttractions.setItems(emptyObservableList);
			gridViewForImagesForAttractions.setItems(attractionsSortList);
			
			typeOfGridViewImagesForAttractions = GridViewImagesForAttractionsType.LOCATION;
		}
		
		listViewForAttractions.getSelectionModel().select(1);
		listViewForAttractions.getSelectionModel().selectFirst();
		listViewForAttractions.scrollTo(getIndexInOptionsForAttractions());
		
		recalculateUI(stage.getWidth(), stage.getHeight());
	}

	private void setupMoreInfo(String[] array)
	{
		hBoxMainForCountriesAndContinents.setDisable(true);
		hBoxMainForGreece.setDisable(true);
		hBoxForToggleButtons.setDisable(true);
		backButton.setDisable(true);
		soundIcon.setDisable(true);

		setNumberOfTextAreaLines(array.length);

		if(getNumberOfTextAreaLines() < 4) textAreaForInfo.setPrefHeight(0.15 * stage.getHeight());
		else if(getNumberOfTextAreaLines() < 6) textAreaForInfo.setPrefHeight(0.2 * stage.getHeight());
		else if(getNumberOfTextAreaLines() < 8) textAreaForInfo.setPrefHeight(0.27 * stage.getHeight());
		else if(getNumberOfTextAreaLines() < 11) textAreaForInfo.setPrefHeight(0.37 * stage.getHeight());
		else if(getNumberOfTextAreaLines() < 16) textAreaForInfo.setPrefHeight(0.5 * stage.getHeight());
		else textAreaForInfo.setPrefHeight(0.7 * stage.getHeight());
		textAreaForInfo.setLayoutY(stage.getHeight() / 2.0 - textAreaForInfo.getPrefHeight() / 2.0);

		rectangleToShowInfo.setHeight(textAreaForInfo.getPrefHeight() + 1.0 / 48.0 * stage.getHeight());
		rectangleToShowInfo.setLayoutY(stage.getHeight() / 2.0 - rectangleToShowInfo.getHeight() / 2.0);

		int i = 1;
		StringBuilder sb = new StringBuilder();
		for(String s: array)
		{
			if(i < 10) sb.append(i + ")  ");
			else sb.append(i + ") ");

			sb.append(s + "\n");
			i++;
		}
		sb.deleteCharAt(sb.length() - 1);

		textAreaForInfo.setText(sb.toString());
		
		isInfoOpen = true;
		
		if(animationsUsed != ANIMATIONS.NO)
		{
			scaleTransitionForRectangleForInfo.setToX(1);
			scaleTransitionForRectangleForInfo.setToY(1);
			scaleTransitionForTextAreaForInfo.setToX(1);
			scaleTransitionForTextAreaForInfo.setToY(1);
			
			playPopUpSound();
			scaleTransitionForRectangleForInfo.playFromStart();
			scaleTransitionForTextAreaForInfo.playFromStart();
		}
		else
		{
			rectangleToShowInfo.setScaleX(1);
			rectangleToShowInfo.setScaleY(1);
			textAreaForInfo.setScaleX(1);
			textAreaForInfo.setScaleY(1);
		}
	}

	private void closeMoreInfo()
	{
		if(animationsUsed != ANIMATIONS.NO)
		{
			scaleTransitionForRectangleForInfo.setToX(0);
			scaleTransitionForRectangleForInfo.setToY(0);
			scaleTransitionForTextAreaForInfo.setToX(0);
			scaleTransitionForTextAreaForInfo.setToY(0);
			
			scaleTransitionForRectangleForInfo.setOnFinished(e ->
			{
				scaleTransitionForRectangleForInfo.setOnFinished(ev -> {});
				
				hBoxMainForCountriesAndContinents.setDisable(false);
				hBoxMainForGreece.setDisable(false);
				hBoxForToggleButtons.setDisable(false);
				backButton.setDisable(false);
				soundIcon.setDisable(false);
				
				isInfoOpen = false;
			});
			
			playMinimizeSound();
			scaleTransitionForTextAreaForInfo.playFromStart();
			scaleTransitionForRectangleForInfo.playFromStart();
		}
		else
		{
			rectangleToShowInfo.setScaleX(0);
			rectangleToShowInfo.setScaleY(0);
			textAreaForInfo.setScaleX(0);
			textAreaForInfo.setScaleY(0);
			
			hBoxMainForCountriesAndContinents.setDisable(false);
			hBoxMainForGreece.setDisable(false);
			hBoxForToggleButtons.setDisable(false);
			backButton.setDisable(false);
			soundIcon.setDisable(false);
			
			isInfoOpen = false;
		}
		
	}

	private void zoomIn()
	{
		if(bigImage.getViewport() == null)
		{
			bigImage.setCursor(Cursor.HAND);
			String name = "";
			if(getCategoryCurrentlyViewing() == 1)
			{
				if (getIndexInOptionsForCountriesAndContinents() == 0)
					name = countries[countriesSortList.get(getIndexInListViewForCountriesAndContinents())].getNameInGreek();
				else if (getIndexInOptionsForCountriesAndContinents() == 1)
					name = countries[capitalsOfCountriesSortList.get(getIndexInListViewForCountriesAndContinents())].getNameInGreek();
				else if (getIndexInOptionsForCountriesAndContinents() == 5)
					name = continents[continentsSortList.get(getIndexInListViewForCountriesAndContinents())].getNameInGreek();
				else
					name = countries[countriesSortList.get(getIndexInBigImageNormal())].getNameInGreek();
			}
			else if(getCategoryCurrentlyViewing() == 2)
			{
				if (getIndexInOptionsForUSA() == 0)
					name = statesOfUSA[statesOfUSASortList.get(getIndexInListViewForUSA())].getNameInGreek();
				else if (getIndexInOptionsForUSA() == 1)
					name = statesOfUSA[capitalsOfStatesSortList.get(getIndexInListViewForUSA())].getNameInGreek();
				else
					name = statesOfUSA[statesOfUSASortList.get(getIndexInBigImageNormal())].getNameInGreek();
			}
			else if(getCategoryCurrentlyViewing() == 3)
			{
				if(getIndexInOptionsForGreece() == 0)
					name = greekDecAdm[greekDecentralizedAdministrationsSortList.get(getIndexInListViewForGreece())].getNameInGreek();
				else if(getIndexInOptionsForGreece() == 1)
					name = greekRegions[greekRegionsSortList.get(getIndexInListViewForGreece())].getNameInGreek();
			}

			if (typeOfNormalBigImage == BigImageType.FLAG_FOR_COUNTRIES) newBigImagePath = getImagePath(ImageType.COUNTRY_FLAG, "x2000", name);
			else if (typeOfNormalBigImage == BigImageType.COAT_OF_ARMS_FOR_COUNTRIES) newBigImagePath = getImagePath(ImageType.COUNTRY_COAT_OF_ARMS, "x3000", name);
			else if (typeOfNormalBigImage == BigImageType.LOCATION_FOR_COUNTRIES) newBigImagePath = getImagePath(ImageType.COUNTRY_LOCATION, "x2000", name);
			else if (typeOfNormalBigImage == BigImageType.LOCATION_FOR_CONTINENTS) newBigImagePath = getImagePath(ImageType.CONTINENT_LOCATION, "x2000", name);
			else if (typeOfNormalBigImage == BigImageType.FLAG_FOR_USA) newBigImagePath = getImagePath(ImageType.USA_FLAG, "x2000", name);
			else if (typeOfNormalBigImage == BigImageType.SEAL_FOR_USA) newBigImagePath = getImagePath(ImageType.USA_SEAL, "x3000", name);
			else if (typeOfNormalBigImage == BigImageType.LOCATION_FOR_USA) newBigImagePath = getImagePath(ImageType.USA_LOCATION, "x2000", name);
			else if(typeOfNormalBigImage == BigImageType.LOCATION_FOR_GREEK_DEC_ADMIN) newBigImagePath = getImagePath(ImageType.GREECE_DEC_ADM_LOCATION, "x2000", name);
			else if(typeOfNormalBigImage == BigImageType.LOCATION_FOR_GREEK_REGIONS) newBigImagePath = getImagePath(ImageType.GREECE_REGION_LOCATION, "x2000", name);

			if(!previousBigImagePath.equals(newBigImagePath))
			{
				previousBigImagePath = newBigImagePath;
				bigImage.setImage(new Image(newBigImagePath));
			}

			bigImage.setViewport(new Rectangle2D(0.1 * bigImage.getImage().getWidth(), 0.1 * bigImage.getImage().getHeight(),
					                                    0.8 * bigImage.getImage().getWidth(), 0.8 * bigImage.getImage().getHeight()));
		}
		else
		{
			if(bigImage.getViewport().getWidth() - 0.2 * bigImage.getImage().getWidth() > 0 &&
			   bigImage.getViewport().getHeight() - 0.2 * bigImage.getImage().getHeight() > 0)

				bigImage.setViewport(new Rectangle2D(
						                                    bigImage.getViewport().getMinX() + 0.1 * bigImage.getImage().getWidth(),
						                                    bigImage.getViewport().getMinY() + 0.1 * bigImage.getImage().getHeight(),
						                                    bigImage.getViewport().getWidth() - 0.2 * bigImage.getImage().getWidth(),
						                                    bigImage.getViewport().getHeight() - 0.2 * bigImage.getImage().getHeight()
						));
			else if(bigImage.getViewport().getWidth() - 0.1 * bigImage.getImage().getWidth() > 0 &&
			        bigImage.getViewport().getHeight() - 0.1 * bigImage.getImage().getHeight() > 0)

				bigImage.setViewport(new Rectangle2D(
					                                    bigImage.getViewport().getMinX() + 0.05 * bigImage.getImage().getWidth(),
					                                    bigImage.getViewport().getMinY() + 0.05 * bigImage.getImage().getHeight(),
					                                    bigImage.getViewport().getWidth() - 0.1 * bigImage.getImage().getWidth(),
					                                    bigImage.getViewport().getHeight() - 0.1 * bigImage.getImage().getHeight()
					));
		}
	}

	private void zoomOut()
	{
		if(bigImage.getViewport() != null)
		{
			if(bigImage.getViewport().getMinX() - 0.1 * bigImage.getImage().getWidth() >= 0 &&
			   bigImage.getViewport().getMinY() - 0.1 * bigImage.getImage().getHeight() >= 0)

				bigImage.setViewport(new Rectangle2D(
						                                    bigImage.getViewport().getMinX() - 0.1 * bigImage.getImage().getWidth(),
						                                    bigImage.getViewport().getMinY() - 0.1 * bigImage.getImage().getHeight(),
						                                    bigImage.getViewport().getWidth() + 0.2 * bigImage.getImage().getWidth(),
						                                    bigImage.getViewport().getHeight() + 0.2 * bigImage.getImage().getHeight()
						));
			else
			{
				bigImage.setViewport(null);
				bigImage.setCursor(Cursor.DEFAULT);
			}
		}
	}
	
	private void makeAStepInBigImage(int step)
	{
		int index = 0;
		if(getCategoryCurrentlyViewing() == 1)
		{
			if(getIndexInOptionsForCountriesAndContinents() == 0 ||
			   getIndexInOptionsForCountriesAndContinents() == 1 ||
			   getIndexInOptionsForCountriesAndContinents() == 5)
			{
				setIndexInBigImageNormal(getIndexInListViewForCountriesAndContinents() + step);
				setIndexInListViewForCountriesAndContinents(getIndexInBigImageNormal());
				
				listViewForCountriesAndContinents.scrollTo(getIndexInListViewForCountriesAndContinents());
				
				if (getIndexInOptionsForCountriesAndContinents() == 0)
					index = countriesSortList.get(getIndexInBigImageNormal());
				else if (getIndexInOptionsForCountriesAndContinents() == 1)
					index = capitalsOfCountriesSortList.get(getIndexInBigImageNormal());
				else if (getIndexInOptionsForCountriesAndContinents() == 5)
					index = continentsSortList.get(getIndexInBigImageNormal());
			}
			else
			{
				setIndexInBigImageNormal(getIndexInBigImageNormal() + step);
				index = countriesSortList.get(getIndexInBigImageNormal());
			}
		}
		else if(getCategoryCurrentlyViewing() == 2)
		{
			if(getIndexInOptionsForUSA() == 0 ||
			   getIndexInOptionsForUSA() == 1)
			{
				setIndexInBigImageNormal(getIndexInListViewForUSA() + step);
				setIndexInListViewForUSA(getIndexInBigImageNormal());
				
				listViewForUSA.scrollTo(getIndexInListViewForUSA());
				
				if (getIndexInOptionsForUSA() == 0)
					index = statesOfUSASortList.get(getIndexInBigImageNormal());
				else if (getIndexInOptionsForUSA() == 1)
					index = capitalsOfStatesSortList.get(getIndexInBigImageNormal());
			}
			else
			{
				setIndexInBigImageNormal(getIndexInBigImageNormal() + step);
				index = statesOfUSASortList.get(getIndexInBigImageNormal());
			}
		}
		else if(getCategoryCurrentlyViewing() == 3)
		{
			setIndexInBigImageNormal(getIndexInListViewForGreece() + step);
			setIndexInListViewForGreece(getIndexInBigImageNormal());
			
			listViewForGreece.scrollTo(getIndexInListViewForGreece());
			
			if(getIndexInOptionsForGreece() == 0)
				index = greekDecentralizedAdministrationsSortList.get(getIndexInBigImageNormal());
			else if(getIndexInOptionsForGreece() == 1)
				index = greekRegionsSortList.get(getIndexInBigImageNormal());
		}
		
		bigImage.setViewport(null);
		setupBigImageNormal(typeOfNormalBigImage, index);
	}
	
	private void processBigImageNormal(BigImageType type, int index)
	{
		bigImageStatus = 2;
		typeOfNormalBigImage = type;
		
		String name = "";
		if (getCategoryCurrentlyViewing() == 1)
		{
			if (getIndexInOptionsForCountriesAndContinents() != 5)
			{
				name = countries[index].getNameInGreek();
				if (getCurrentLanguage() == LANGUAGE.GREEK) labelForBigImage.setText(name);
				else labelForBigImage.setText(countries[index].getNameInEnglish());
			}
			else
			{
				name = continents[index].getNameInGreek();
				if (getCurrentLanguage() == LANGUAGE.GREEK) labelForBigImage.setText(name);
				else labelForBigImage.setText(continents[index].getNameInEnglish());
			}
			
			if (getIndexInListViewForCountriesAndContinents() == 0) previousInBigImageButton.setDisable(true);
			else previousInBigImageButton.setDisable(false);
			
			if (getIndexInOptionsForCountriesAndContinents() == 0 && getIndexInListViewForCountriesAndContinents() == NUM_ALL_COUNTRIES - 1 ||
			    getIndexInOptionsForCountriesAndContinents() == 5 && getIndexInListViewForCountriesAndContinents() == NUM_ALL_CONTINENTS - 1)
					nextInBigImageButton.setDisable(true);
			else nextInBigImageButton.setDisable(false);
		}
		else if(getCategoryCurrentlyViewing() == 2)
		{
			name = statesOfUSA[index].getNameInGreek();
			if (getCurrentLanguage() == LANGUAGE.GREEK) labelForBigImage.setText(name);
			else labelForBigImage.setText(statesOfUSA[index].getNameInEnglish());
			
			if (getIndexInListViewForUSA() == 0) previousInBigImageButton.setDisable(true);
			else previousInBigImageButton.setDisable(false);
			
			if (getIndexInOptionsForUSA() == 0 && getIndexInListViewForUSA() == NUM_ALL_USA_STATES - 1)
				nextInBigImageButton.setDisable(true);
			else nextInBigImageButton.setDisable(false);
		}
		else if(getCategoryCurrentlyViewing() == 3)
		{
			if (getIndexInOptionsForGreece() == 0)
			{
				name = greekDecAdm[index].getNameInGreek();
				if (getCurrentLanguage() == LANGUAGE.GREEK) labelForBigImage.setText(name);
				else labelForBigImage.setText(greekDecAdm[index].getNameInEnglish());
				
				if (labelForBigImage.getText().length() > 55) labelForBigImage.setFont(Font.font("Comic Sans MS", (0.0119) * stage.getWidth()));
				else if (labelForBigImage.getFont() != fontBig) labelForBigImage.setFont(fontBig);
			}
			else if (getIndexInOptionsForGreece() == 1)
			{
				name = greekRegions[index].getNameInGreek();
				if (getCurrentLanguage() == LANGUAGE.GREEK) labelForBigImage.setText(name);
				else labelForBigImage.setText(greekRegions[index].getNameInEnglish());
			}
			
			if (getIndexInListViewForGreece() == 0) previousInBigImageButton.setDisable(true);
			else previousInBigImageButton.setDisable(false);
			
			if (getIndexInOptionsForGreece() == 0 && getIndexInListViewForGreece() == NUM_ALL_GREEK_DEC_ADMIN - 1 ||
			    getIndexInOptionsForGreece() == 1 && getIndexInListViewForGreece() == NUM_ALL_GREEK_REGIONS - 1 ||
			    getIndexInOptionsForGreece() == 2 && getIndexInListViewForGreece() == NUM_ALL_GREEK_REGIONAL_UNITS - 1 ||
			    getIndexInOptionsForGreece() == 3 && getIndexInListViewForGreece() == NUM_ALL_GREEK_MUNICIPALITIES - 1)
					nextInBigImageButton.setDisable(true);
			else nextInBigImageButton.setDisable(false);
		}
		
		if (labelForBigImage.getText().length() < 55 && labelForBigImage.getFont() != fontBig) labelForBigImage.setFont(fontBig);
		
		
		if(type == BigImageType.LOGOS_FOR_GREEK_REGIONS) bigImageSize = "";
		else if (bigImage.getFitWidth() <= MAX_WIDTH_FOR_X500_IMAGES && !bigImageSize.equals("x500")) bigImageSize = "x500";
		else if (bigImage.getFitWidth() <= MAX_WIDTH_FOR_X1000_IMAGES && !bigImageSize.equals("x1000")) bigImageSize = "x1000";
		else bigImageSize = "x2000";
		
		if (type == BigImageType.FLAG_FOR_COUNTRIES) newBigImagePath = getImagePath(ImageType.COUNTRY_FLAG, bigImageSize, name);
		else if (type == BigImageType.COAT_OF_ARMS_FOR_COUNTRIES)
		{
			newBigImagePath = getImagePath(ImageType.COUNTRY_COAT_OF_ARMS, bigImageSize, name);
			
			switch (name)
			{
				case "Ινδία":case "Αμερικανική Σαμόα":case "Αυστρία":case "Ιράν":case "Καμερούν":case "Κένυα":case "Κομόρες":case "Κύπρος":
				case "Λεσότο":case "Λετονία":case "Νέα Καληδονία":case "Πακιστάν":case "Παλάου":case "Σουαζιλάνδη":case "Σουδάν":case "Νότια Αφρική":
					rectangleForBigImage.setFill(Color.valueOf("ffffffc7"));
					labelForBigImage.setTextFill(Color.BLACK);
					break;
				default:
					rectangleForBigImage.setFill(Color.valueOf("00000099"));
					labelForBigImage.setTextFill(Color.WHITE);
					break;
			}
		}
		else if (type == BigImageType.LOCATION_FOR_COUNTRIES) newBigImagePath = getImagePath(ImageType.COUNTRY_LOCATION, bigImageSize, name);
		else if (type == BigImageType.LOCATION_FOR_CONTINENTS) newBigImagePath = getImagePath(ImageType.CONTINENT_LOCATION, bigImageSize, name);
		else if (type == BigImageType.FLAG_FOR_USA) newBigImagePath = getImagePath(ImageType.USA_FLAG, bigImageSize, name);
		else if (type == BigImageType.SEAL_FOR_USA) newBigImagePath = getImagePath(ImageType.USA_SEAL, bigImageSize, name);
		else if (type == BigImageType.LOCATION_FOR_USA) newBigImagePath = getImagePath(ImageType.USA_LOCATION, bigImageSize, name);
		else if(type == BigImageType.LOCATION_FOR_GREEK_DEC_ADMIN) newBigImagePath = getImagePath(ImageType.GREECE_DEC_ADM_LOCATION, bigImageSize, name);
		else if(type == BigImageType.LOCATION_FOR_GREEK_REGIONS) newBigImagePath = getImagePath(ImageType.GREECE_REGION_LOCATION, bigImageSize, name);
		else if (type == BigImageType.LOGOS_FOR_GREEK_REGIONS) newBigImagePath = getImagePath(ImageType.GREECE_REGION_LOGO, bigImageSize, name);
		
		if (!previousBigImagePath.equals(newBigImagePath))
		{
			previousBigImagePath = newBigImagePath;
			bigImage.setImage(new Image(newBigImagePath));
		}
		
		if (type == BigImageType.LOGOS_FOR_GREEK_REGIONS)
		{
			bigImage.setFitHeight(bigImage.getFitWidth() / 2.25);
			
			rectangleForBigImage.setHeight(bigImage.getFitHeight() + (17.0 / 108.0) * stage.getHeight());
			rectangleForBigImage.setLayoutY(stage.getHeight() / 2.0 - rectangleForBigImage.getHeight() / 2.0);
			
			bigImage.setLayoutY(rectangleForBigImage.getLayoutY() + (rectangleForBigImage.getHeight() - (7.0 / 54.0) * stage.getHeight()) / 2.0 - bigImage.getFitHeight() / 2.0);
		}
		else
		{
			bigImage.setFitHeight(0.7 * stage.getHeight());
			
			rectangleForBigImage.setHeight(bigImage.getFitHeight() + (4.0 / 27.0) * stage.getHeight());
			rectangleForBigImage.setLayoutY(stage.getHeight() / 2.0 - rectangleForBigImage.getHeight() / 2.0);
			
			bigImage.setLayoutY(rectangleForBigImage.getLayoutY() + (1.0 / 54.0) * stage.getHeight());
		}
		
		labelForBigImage.setLayoutY(bigImage.getLayoutY() + bigImage.getFitHeight() + (1.0 / 200.0) * stage.getHeight());
		previousInBigImageButton.setLayoutY(rectangleForBigImage.getLayoutY() + rectangleForBigImage.getHeight() - previousInBigImageButton.getBoundsInLocal().getHeight() - (1.0 / 72.0) * stage.getHeight());
		nextInBigImageButton.setLayoutY(rectangleForBigImage.getLayoutY() + rectangleForBigImage.getHeight() - nextInBigImageButton.getBoundsInLocal().getHeight() - (1.0 / 72.0) * stage.getHeight());
		exitBigImage.setLayoutY(rectangleForBigImage.getLayoutY() + rectangleForBigImage.getHeight() - exitBigImage.getBoundsInLocal().getHeight() - (1.0 / 54.0) * stage.getHeight());
	}
	
	private void setupBigImageNormal(BigImageType type, int index)
	{
		if(animationsUsed != ANIMATIONS.NO)
		{
			if (rectangleForBigImage.getScaleX() != 0 && bigImageStatus != 2)
			{
				scaleTransitionForRectangleForBigImage.setToX(0);
				scaleTransitionForRectangleForBigImage.setToY(0);
				scaleTransitionForBigImage.setToX(0);
				scaleTransitionForBigImage.setToY(0);
				
				scaleTransitionForRectangleForBigImage.setOnFinished(e ->
				{
					scaleTransitionForRectangleForBigImage.setOnFinished(ev -> {});
					
					if (rectangleForBigImage.getFill().equals(Color.valueOf("ffffffc7")))
					{
						rectangleForBigImage.setFill(Color.valueOf("00000099"));
						labelForBigImage.setTextFill(Color.WHITE);
					}
					setupBigImageNormal(type, index);
				});
				
				scaleTransitionForBigImage.playFromStart();
				scaleTransitionForRectangleForBigImage.playFromStart();
			}
			else
			{
				processBigImageNormal(type, index);
				recalculateUI(stage.getWidth(), stage.getHeight());
				
				if (rectangleForBigImage.getScaleX() == 0)
				{
					scaleTransitionForRectangleForBigImage.setToX(1);
					scaleTransitionForRectangleForBigImage.setToY(1);
					scaleTransitionForBigImage.setToX(1);
					scaleTransitionForBigImage.setToY(1);
					scaleTransitionForLabelInBigImage.setToX(1);
					scaleTransitionForLabelInBigImage.setToY(1);
					scaleTransitionForPreviousInBigImage.setToX(1);
					scaleTransitionForPreviousInBigImage.setToY(1);
					scaleTransitionForNextInBigImage.setToX(1);
					scaleTransitionForNextInBigImage.setToY(1);
					scaleTransitionForExitInBigImage.setToX(1);
					scaleTransitionForExitInBigImage.setToY(1);
					scaleTransitionForZoomInInBigImage.setToX(1);
					scaleTransitionForZoomInInBigImage.setToY(1);
					scaleTransitionForZoomOutInBigImage.setToX(1);
					scaleTransitionForZoomOutInBigImage.setToY(1);
					
					scaleTransitionForRectangleForBigImage.setOnFinished(e ->
					{
						scaleTransitionForRectangleForBigImage.setOnFinished(ev -> {});
						
						scaleTransitionForBigImage.setOnFinished(eve ->
						{
							scaleTransitionForBigImage.setOnFinished(even -> {});
									
							hBoxMainForCountriesAndContinents.setDisable(true);
							hBoxMainForUSA.setDisable(true);
							hBoxMainForGreece.setDisable(true);
							hBoxForToggleButtons.setDisable(true);
							backButton.setDisable(true);
							soundIcon.setDisable(true);
						});
						
						scaleTransitionForBigImage.playFromStart();
						scaleTransitionForLabelInBigImage.playFromStart();
						scaleTransitionForExitInBigImage.playFromStart();
						scaleTransitionForPreviousInBigImage.playFromStart();
						scaleTransitionForNextInBigImage.playFromStart();
						
						if(type != BigImageType.LOGOS_FOR_GREEK_REGIONS)
						{
							scaleTransitionForZoomInInBigImage.playFromStart();
							scaleTransitionForZoomOutInBigImage.playFromStart();
						}
					});
					
					playPopUpSound();
					scaleTransitionForRectangleForBigImage.playFromStart();
				}
			}
		}
		else
		{
			processBigImageNormal(type, index);
			recalculateUI(stage.getWidth(), stage.getHeight());
			
			rectangleForBigImage.setScaleX(1);
			rectangleForBigImage.setScaleY(1);
			bigImage.setScaleX(1);
			bigImage.setScaleY(1);
			labelForBigImage.setScaleX(1);
			labelForBigImage.setScaleY(1);
			exitBigImage.setScaleX(1);
			exitBigImage.setScaleY(1);
			previousInBigImageButton.setScaleX(1);
			previousInBigImageButton.setScaleY(1);
			nextInBigImageButton.setScaleX(1);
			nextInBigImageButton.setScaleY(1);
			
			if(type != BigImageType.LOGOS_FOR_GREEK_REGIONS)
			{
				zoomInBigImage.setScaleX(1);
				zoomInBigImage.setScaleY(1);
				zoomOutBigImage.setScaleX(1);
				zoomOutBigImage.setScaleY(1);
			}
			
			hBoxMainForCountriesAndContinents.setDisable(true);
			hBoxMainForUSA.setDisable(true);
			hBoxMainForGreece.setDisable(true);
			hBoxForToggleButtons.setDisable(true);
			backButton.setDisable(true);
			soundIcon.setDisable(true);
		}
	}

	private void processBigImagePreview(BigImageType type, int index)
	{
		bigImageStatus = 1;
		
		String name = "";
		if (getCategoryCurrentlyViewing() == 1)
		{
			if (getIndexInOptionsForCountriesAndContinents() == 0 ||
			    getIndexInOptionsForCountriesAndContinents() == 1)
				name = countries[index].getNameInGreek();
			else if (getIndexInOptionsForCountriesAndContinents() == 5)
				name = continents[index].getNameInGreek();
		}
		else if(getCategoryCurrentlyViewing() == 2) name = statesOfUSA[index].getNameInGreek();
		else if(getCategoryCurrentlyViewing() == 3)
		{
			if (getIndexInOptionsForGreece() == 0) name = greekDecAdm[index].getNameInGreek();
			else if (getIndexInOptionsForGreece() == 1) name = greekRegions[index].getNameInGreek();
		}
		
		if(type == BigImageType.LOGOS_FOR_GREEK_REGIONS) bigImageSize = "";
		else if (bigImage.getFitWidth() <= MAX_WIDTH_FOR_X500_IMAGES && !bigImageSize.equals("x500")) bigImageSize = "x500";
		else if (bigImage.getFitWidth() <= MAX_WIDTH_FOR_X1000_IMAGES && !bigImageSize.equals("x1000")) bigImageSize = "x1000";
		else bigImageSize = "x2000";
		
		if (type == BigImageType.FLAG_FOR_COUNTRIES) newBigImagePath = getImagePath(ImageType.COUNTRY_FLAG, bigImageSize, name);
		else if (type == BigImageType.COAT_OF_ARMS_FOR_COUNTRIES)
		{
			newBigImagePath = getImagePath(ImageType.COUNTRY_COAT_OF_ARMS, bigImageSize, name);
			
			if (name.equals("Ινδία") || name.equals("Αμερικανική Σαμόα") || name.equals("Αυστρία") || name.equals("Ιράν") || name.equals("Καμερούν") ||
			    name.equals("Κένυα") || name.equals("Κομόρες") || name.equals("Κύπρος") || name.equals("Λεσότο") || name.equals("Λετονία") || name.equals("Νέα Καληδονία") ||
			    name.equals("Πακιστάν") || name.equals("Παλάου") || name.equals("Σουαζιλάνδη") || name.equals("Σουδάν") || name.equals("Νότια Αφρική"))
			{
				rectangleForBigImage.setFill(Color.valueOf("ffffffc7"));
				labelForBigImage.setTextFill(Color.BLACK);
			}
			else
			{
				rectangleForBigImage.setFill(Color.valueOf("00000099"));
				labelForBigImage.setTextFill(Color.WHITE);
			}
		}
		else if (type == BigImageType.LOCATION_FOR_COUNTRIES) newBigImagePath = getImagePath(ImageType.COUNTRY_LOCATION, bigImageSize, name);
		else if (type == BigImageType.LOCATION_FOR_CONTINENTS) newBigImagePath = getImagePath(ImageType.CONTINENT_LOCATION, bigImageSize, name);
		else if (type == BigImageType.FLAG_FOR_USA) newBigImagePath = getImagePath(ImageType.USA_FLAG, bigImageSize, name);
		else if (type == BigImageType.SEAL_FOR_USA) newBigImagePath = getImagePath(ImageType.USA_SEAL, bigImageSize, name);
		else if (type == BigImageType.LOCATION_FOR_USA) newBigImagePath = getImagePath(ImageType.USA_LOCATION, bigImageSize, name);
		else if(type == BigImageType.LOCATION_FOR_GREEK_DEC_ADMIN) newBigImagePath = getImagePath(ImageType.GREECE_DEC_ADM_LOCATION, bigImageSize, name);
		else if(type == BigImageType.LOCATION_FOR_GREEK_REGIONS) newBigImagePath = getImagePath(ImageType.GREECE_REGION_LOCATION, bigImageSize, name);
		else if (type == BigImageType.LOGOS_FOR_GREEK_REGIONS) newBigImagePath = getImagePath(ImageType.GREECE_REGION_LOGO, bigImageSize, name);
		
		if (!previousBigImagePath.equals(newBigImagePath))
		{
			previousBigImagePath = newBigImagePath;
			bigImage.setImage(new Image(newBigImagePath));
		}
		
		if (type == BigImageType.LOGOS_FOR_GREEK_REGIONS)
		{
			bigImage.setFitHeight(bigImage.getFitWidth() / 2.25);
			bigImage.setLayoutY(stage.getHeight() / 2.0 - bigImage.getFitHeight() / 2.0);
		}
		else
		{
			bigImage.setFitHeight(0.7 * stage.getHeight());
			bigImage.setLayoutY(stage.getHeight() / 2.0 - bigImage.getFitHeight() / 2.0);
		}
		
		if (type == BigImageType.FLAG_FOR_COUNTRIES && !name.equals("Βατικανό") && !name.equals("Βέλγιο") && !name.equals("Ελβετία") && !name.equals("Μονακό") && !name.equals("Νεπάλ") && !name.equals("Νίγηρας"))
		{
			rectangleForBigImage.setHeight(0.8 * bigImage.getFitHeight());
			rectangleForBigImage.setLayoutY(stage.getHeight() / 2.0 - rectangleForBigImage.getHeight() / 2.0);
		}
		else
		{
			rectangleForBigImage.setHeight(bigImage.getFitHeight() + (1.0 / 27.0) * stage.getHeight());
			rectangleForBigImage.setLayoutY(stage.getHeight() / 2.0 - rectangleForBigImage.getHeight() / 2.0);
		}
	}
	
	private void setupBigImagePreview(BigImageType type, int index)
	{
		if(animationsUsed != ANIMATIONS.NO)
		{
			if (rectangleForBigImage.getScaleX() != 0 || bigImage.getScaleX() != 0)
			{
				scaleTransitionForRectangleForBigImage.setToX(0);
				scaleTransitionForRectangleForBigImage.setToY(0);
				scaleTransitionForBigImage.setToX(0);
				scaleTransitionForBigImage.setToY(0);
				
				scaleTransitionForRectangleForBigImage.setOnFinished(ev ->
				{
					scaleTransitionForRectangleForBigImage.setOnFinished(eve ->
					{
					});
					
					if (rectangleForBigImage.getFill().equals(Color.valueOf("ffffffc7")))
					{
						rectangleForBigImage.setFill(Color.valueOf("00000099"));
						labelForBigImage.setTextFill(Color.WHITE);
					}
					setupBigImagePreview(type, index);
				});
				
				scaleTransitionForBigImage.playFromStart();
				scaleTransitionForRectangleForBigImage.playFromStart();
			}
			else
			{
				processBigImagePreview(type, index);
				
				scaleTransitionForRectangleForBigImage.setToX(1);
				scaleTransitionForRectangleForBigImage.setToY(1);
				scaleTransitionForBigImage.setToX(1);
				scaleTransitionForBigImage.setToY(1);
				
				playPopUpSound();
				scaleTransitionForRectangleForBigImage.playFromStart();
				scaleTransitionForBigImage.playFromStart();
			}
		}
		else
		{
			processBigImagePreview(type, index);
			rectangleForBigImage.setScaleX(1);
			rectangleForBigImage.setScaleY(1);
			bigImage.setScaleX(1.0);
			bigImage.setScaleY(1.0);
		}
	}

	private void closeBigImage()
	{
		if(animationsUsed != ANIMATIONS.NO)
		{
			scaleTransitionForRectangleForBigImage.setToX(0);
			scaleTransitionForRectangleForBigImage.setToY(0);
			scaleTransitionForBigImage.setToX(0);
			scaleTransitionForBigImage.setToY(0);
			
			if (bigImageStatus == 2)
			{
				bigImageStatus = 0;
				
				bigImage.setViewport(null);
				
				scaleTransitionForLabelInBigImage.setToX(0);
				scaleTransitionForLabelInBigImage.setToY(0);
				scaleTransitionForPreviousInBigImage.setToX(0);
				scaleTransitionForPreviousInBigImage.setToY(0);
				scaleTransitionForNextInBigImage.setToX(0);
				scaleTransitionForNextInBigImage.setToY(0);
				scaleTransitionForExitInBigImage.setToX(0);
				scaleTransitionForExitInBigImage.setToY(0);
				scaleTransitionForZoomInInBigImage.setToX(0);
				scaleTransitionForZoomInInBigImage.setToY(0);
				scaleTransitionForZoomOutInBigImage.setToX(0);
				scaleTransitionForZoomOutInBigImage.setToY(0);
				
				scaleTransitionForBigImage.setOnFinished(e ->
				{
					scaleTransitionForBigImage.setOnFinished(ev -> {});
					
					scaleTransitionForRectangleForBigImage.setOnFinished(eve ->
					{
						scaleTransitionForRectangleForBigImage.setOnFinished(even -> {});
						
						hBoxMainForCountriesAndContinents.setDisable(false);
						hBoxMainForUSA.setDisable(false);
						hBoxMainForGreece.setDisable(false);
						hBoxForToggleButtons.setDisable(false);
						backButton.setDisable(false);
						soundIcon.setDisable(false);
						
						if (rectangleForBigImage.getFill().equals(Color.valueOf("ffffffc7")))
						{
							rectangleForBigImage.setFill(Color.valueOf("00000099"));
							labelForBigImage.setTextFill(Color.WHITE);
						}
					});
					scaleTransitionForRectangleForBigImage.playFromStart();
				});
				
				playMinimizeSound();
				scaleTransitionForBigImage.playFromStart();
				scaleTransitionForLabelInBigImage.playFromStart();
				scaleTransitionForPreviousInBigImage.playFromStart();
				scaleTransitionForNextInBigImage.playFromStart();
				scaleTransitionForExitInBigImage.playFromStart();
				scaleTransitionForZoomInInBigImage.playFromStart();
				scaleTransitionForZoomOutInBigImage.playFromStart();
			}
			else
			{
				bigImageStatus = 0;
				
				if (rectangleForBigImage.getFill().equals(Color.valueOf("ffffffc7")))
				{
					scaleTransitionForRectangleForBigImage.setOnFinished(e ->
					{
						rectangleForBigImage.setFill(Color.valueOf("00000099"));
						labelForBigImage.setTextFill(Color.WHITE);
						
						scaleTransitionForRectangleForBigImage.setOnFinished(ev -> {});
					});
				}
				
				playMinimizeSound();
				scaleTransitionForBigImage.playFromStart();
				scaleTransitionForRectangleForBigImage.playFromStart();
			}
		}
		else
		{
			rectangleForBigImage.setScaleX(0);
			rectangleForBigImage.setScaleY(0);
			bigImage.setScaleX(0);
			bigImage.setScaleY(0);
			
			if (bigImageStatus == 2)
			{
				bigImageStatus = 0;
				
				bigImage.setViewport(null);
				
				labelForBigImage.setScaleX(0);
				labelForBigImage.setScaleY(0);
				previousInBigImageButton.setScaleX(0);
				previousInBigImageButton.setScaleY(0);
				nextInBigImageButton.setScaleX(0);
				nextInBigImageButton.setScaleY(0);
				exitBigImage.setScaleX(0);
				exitBigImage.setScaleY(0);
				zoomInBigImage.setScaleX(0);
				zoomInBigImage.setScaleY(0);
				zoomOutBigImage.setScaleX(0);
				zoomOutBigImage.setScaleY(0);
				
				hBoxMainForCountriesAndContinents.setDisable(false);
				hBoxMainForUSA.setDisable(false);
				hBoxMainForGreece.setDisable(false);
				hBoxForToggleButtons.setDisable(false);
				backButton.setDisable(false);
				soundIcon.setDisable(false);
			}
			else bigImageStatus = 0;
			
			if (rectangleForBigImage.getFill().equals(Color.valueOf("ffffffc7")))
			{
				rectangleForBigImage.setFill(Color.valueOf("00000099"));
				labelForBigImage.setTextFill(Color.WHITE);
			}
		}
	}

	private void setNamesForGridPaneLabels()
	{
		if(getCategoryCurrentlyViewing() == 1)
		{
			if((getIndexInOptionsForCountriesAndContinents() == 0 ||
			    getIndexInOptionsForCountriesAndContinents() == 1) &&
			   !gridPaneLabelsForCountriesAndContinents[14][0].getText().equals("   " + languageResourceBundle.getString("subdivisionLabel")))
			{
				gridPaneLabelsForCountriesAndContinents[0][0].setText(" " + languageResourceBundle.getString("nameLabel"));
				gridPaneLabelsForCountriesAndContinents[1][0].setText(" " + languageResourceBundle.getString("capitalLabel"));
				gridPaneLabelsForCountriesAndContinents[2][0].setText(" " + languageResourceBundle.getString("largestCitiesLabel"));
				gridPaneLabelsForCountriesAndContinents[3][0].setText(" " + languageResourceBundle.getString("sovereignStateLabel"));
				gridPaneLabelsForCountriesAndContinents[4][0].setText(" " + languageResourceBundle.getString("continentLabel"));
				gridPaneLabelsForCountriesAndContinents[5][0].setText(" " + languageResourceBundle.getString("officialLanguagesLabel"));
				gridPaneLabelsForCountriesAndContinents[6][0].setText(" " + languageResourceBundle.getString("propertiesLabel"));
				gridPaneLabelsForCountriesAndContinents[7][0].setText(" " + languageResourceBundle.getString("areaLabel"));
				gridPaneLabelsForCountriesAndContinents[8][0].setText(" " + languageResourceBundle.getString("bordersLengthLabel"));
				gridPaneLabelsForCountriesAndContinents[9][0].setText(" " + languageResourceBundle.getString("coastlineLengthLabel"));
				gridPaneLabelsForCountriesAndContinents[10][0].setText(" " + languageResourceBundle.getString("percentOfWaterLabel"));
				gridPaneLabelsForCountriesAndContinents[11][0].setText(" " + languageResourceBundle.getString("populationLabel"));
				gridPaneLabelsForCountriesAndContinents[12][0].setText("   " + languageResourceBundle.getString("densityLabel"));
				gridPaneLabelsForCountriesAndContinents[13][0].setText(" " + languageResourceBundle.getString("currencyLabel"));
				gridPaneLabelsForCountriesAndContinents[14][0].setText("   " + languageResourceBundle.getString("subdivisionLabel"));
				gridPaneLabelsForCountriesAndContinents[15][0].setText("");
				gridPaneLabelsForCountriesAndContinents[16][0].setText("");
			}
			else if(getIndexInOptionsForCountriesAndContinents() == 5 &&
				!gridPaneLabelsForCountriesAndContinents[14][0].getText().equals(" " + languageResourceBundle.getString("timeZonesLabel")))
			{
				gridPaneLabelsForCountriesAndContinents[0][0].setText(" " + languageResourceBundle.getString("nameLabel"));
				gridPaneLabelsForCountriesAndContinents[1][0].setText(" " + languageResourceBundle.getString("numberOfCountriesLabel"));
				gridPaneLabelsForCountriesAndContinents[2][0].setText(" " + languageResourceBundle.getString("largestCountriesByArea"));
				gridPaneLabelsForCountriesAndContinents[3][0].setText(" " + languageResourceBundle.getString("largestCountriesByPopulation"));
				gridPaneLabelsForCountriesAndContinents[4][0].setText(" " + languageResourceBundle.getString("largestCitiesLabel"));
				gridPaneLabelsForCountriesAndContinents[5][0].setText(" " + languageResourceBundle.getString("languagesLabel"));
				gridPaneLabelsForCountriesAndContinents[6][0].setText(" " + languageResourceBundle.getString("populationLabel"));
				gridPaneLabelsForCountriesAndContinents[7][0].setText("   " + languageResourceBundle.getString("densityLabel"));
				gridPaneLabelsForCountriesAndContinents[8][0].setText(" " + languageResourceBundle.getString("areaLabel"));
				gridPaneLabelsForCountriesAndContinents[9][0].setText(" " + languageResourceBundle.getString("coastlineLengthLabel"));
				gridPaneLabelsForCountriesAndContinents[10][0].setText(" " + languageResourceBundle.getString("percentOfTheEarthLabel"));
				gridPaneLabelsForCountriesAndContinents[11][0].setText(" " + languageResourceBundle.getString("percentOfLandOfTheEarthLabel"));
				gridPaneLabelsForCountriesAndContinents[12][0].setText(" " + languageResourceBundle.getString("highestPointLabel"));
				gridPaneLabelsForCountriesAndContinents[13][0].setText(" " + languageResourceBundle.getString("lowestPointLabel"));
				gridPaneLabelsForCountriesAndContinents[14][0].setText(" " + languageResourceBundle.getString("longestRiverLabel"));
				gridPaneLabelsForCountriesAndContinents[15][0].setText(" " + languageResourceBundle.getString("LargestLakeLabel"));
				gridPaneLabelsForCountriesAndContinents[16][0].setText(" " + languageResourceBundle.getString("timeZonesLabel"));
			}
		}
		else if(getCategoryCurrentlyViewing() == 2)
		{
			if((getIndexInOptionsForUSA() == 0 ||
			    getIndexInOptionsForUSA() == 1) &&
			   !gridPaneLabelsForUSA[0][0].getText().equals(" " + languageResourceBundle.getString("nameLabel")))
			{
				gridPaneLabelsForUSA[0][0].setText(" " + languageResourceBundle.getString("nameLabel"));
				gridPaneLabelsForUSA[1][0].setText(" " + languageResourceBundle.getString("capitalLabel"));
				gridPaneLabelsForUSA[2][0].setText(" " + languageResourceBundle.getString("largestCitiesLabel"));
				gridPaneLabelsForUSA[3][0].setText(" " + languageResourceBundle.getString("dateEnteredUnion"));
				gridPaneLabelsForUSA[4][0].setText(" " + languageResourceBundle.getString("languagesLabel"));
				gridPaneLabelsForUSA[5][0].setText(" " + languageResourceBundle.getString("numberOfCounties"));
				gridPaneLabelsForUSA[6][0].setText(" " + languageResourceBundle.getString("houseSeats"));
				gridPaneLabelsForUSA[7][0].setText(" " + languageResourceBundle.getString("populationLabel"));
				gridPaneLabelsForUSA[8][0].setText("   " + languageResourceBundle.getString("densityLabel"));
				gridPaneLabelsForUSA[9][0].setText(" " + languageResourceBundle.getString("totalAreaLabel"));
				gridPaneLabelsForUSA[10][0].setText("   " + languageResourceBundle.getString("landArea"));
				gridPaneLabelsForUSA[11][0].setText("   " + languageResourceBundle.getString("waterArea"));
				gridPaneLabelsForUSA[12][0].setText(" " + languageResourceBundle.getString("coastlineLengthLabel"));
				gridPaneLabelsForUSA[13][0].setText(" " + languageResourceBundle.getString("accessToTheOcean"));
				gridPaneLabelsForUSA[14][0].setText(" " + languageResourceBundle.getString("highestPointLabel"));
				gridPaneLabelsForUSA[15][0].setText(" " + languageResourceBundle.getString("meanPoint"));
				gridPaneLabelsForUSA[16][0].setText(" " + languageResourceBundle.getString("lowestPointLabel"));
				gridPaneLabelsForUSA[17][0].setText(" " + languageResourceBundle.getString("timeZonesLabel"));
			}
		}
		else if(getCategoryCurrentlyViewing() == 3)
		{
			if(getIndexInOptionsForGreece() == 0 &&
			   !gridPaneLabelsForGreece[3][0].getText().equals(" " + languageResourceBundle.getString("populationLabel")))
			{
				gridPaneLabelsForGreece[0][0].setText(" " + languageResourceBundle.getString("nameLabel"));
				gridPaneLabelsForGreece[1][0].setText(" " + languageResourceBundle.getString("headquartersLabel"));
				gridPaneLabelsForGreece[2][0].setText(" " + languageResourceBundle.getString("yearFormedLabel"));
				gridPaneLabelsForGreece[3][0].setText(" " + languageResourceBundle.getString("populationLabel"));
				gridPaneLabelsForGreece[4][0].setText(" " + languageResourceBundle.getString("areaLabel"));
				gridPaneLabelsForGreece[5][0].setText(" " + languageResourceBundle.getString("websiteLabel"));
				gridPaneLabelsForGreece[6][0].setText(" " + languageResourceBundle.getString("wikipediaLinkLabel"));
				gridPaneLabelsForGreece[7][0].setText(" " + languageResourceBundle.getString("regionsLabel"));
				gridPaneLabelsForGreece[8][0].setText(" " + languageResourceBundle.getString("regionalUnitsLabel"));
				gridPaneLabelsForGreece[9][0].setText(" " + languageResourceBundle.getString("municipalitiesLabel"));
				gridPaneLabelsForGreece[10][0].setText("");
				gridPaneLabelsForGreece[11][0].setText("");
				gridPaneLabelsForGreece[12][0].setText("");
				gridPaneLabelsForGreece[13][0].setText("");
				gridPaneLabelsForGreece[14][0].setText("");
			}
			else if(getIndexInOptionsForGreece() == 1 &&
			        !gridPaneLabelsForGreece[3][0].getText().equals(" " + languageResourceBundle.getString("largestCityLabel")))
			{
				gridPaneLabelsForGreece[0][0].setText(" " + languageResourceBundle.getString("nameLabel"));
				gridPaneLabelsForGreece[1][0].setText(" " + languageResourceBundle.getString("headquartersLabel"));
				gridPaneLabelsForGreece[2][0].setText(" " + languageResourceBundle.getString("decentralizedAdministrationLabel"));
				gridPaneLabelsForGreece[3][0].setText(" " + languageResourceBundle.getString("largestCityLabel"));
				gridPaneLabelsForGreece[4][0].setText(" " + languageResourceBundle.getString("largestMunicipalityLabel"));
				gridPaneLabelsForGreece[5][0].setText(" " + languageResourceBundle.getString("populationLabel"));
				gridPaneLabelsForGreece[6][0].setText("   " + languageResourceBundle.getString("densityLabel"));
				gridPaneLabelsForGreece[7][0].setText(" " + languageResourceBundle.getString("areaLabel"));
				gridPaneLabelsForGreece[8][0].setText(" " + languageResourceBundle.getString("highestPointLabel"));
				gridPaneLabelsForGreece[9][0].setText(" " + languageResourceBundle.getString("websiteLabel"));
				gridPaneLabelsForGreece[10][0].setText(" " + languageResourceBundle.getString("wikipediaLinkLabel"));
				gridPaneLabelsForGreece[11][0].setText(" " + languageResourceBundle.getString("regionalUnitsLabel"));
				gridPaneLabelsForGreece[12][0].setText(" " + languageResourceBundle.getString("municipalitiesLabel"));
				gridPaneLabelsForGreece[13][0].setText("");
				gridPaneLabelsForGreece[14][0].setText("");
			}
			else if(getIndexInOptionsForGreece() == 2 &&
			        !gridPaneLabelsForGreece[3][0].getText().equals(" " + languageResourceBundle.getString("region")))
			{
				gridPaneLabelsForGreece[0][0].setText(" " + languageResourceBundle.getString("nameLabel"));
				gridPaneLabelsForGreece[1][0].setText(" " + languageResourceBundle.getString("capitalLabel"));
				gridPaneLabelsForGreece[2][0].setText(" " + languageResourceBundle.getString("decentralizedAdministrationLabel"));
				gridPaneLabelsForGreece[3][0].setText(" " + languageResourceBundle.getString("region"));
				gridPaneLabelsForGreece[4][0].setText(" " + languageResourceBundle.getString("largestCityLabel"));
				gridPaneLabelsForGreece[5][0].setText(" " + languageResourceBundle.getString("largestMunicipalityLabel"));
				gridPaneLabelsForGreece[6][0].setText(" " + languageResourceBundle.getString("populationLabel"));
				gridPaneLabelsForGreece[7][0].setText("   " + languageResourceBundle.getString("densityLabel"));
				gridPaneLabelsForGreece[8][0].setText(" " + languageResourceBundle.getString("areaLabel"));
				gridPaneLabelsForGreece[9][0].setText(" " + languageResourceBundle.getString("highestPointLabel"));
				gridPaneLabelsForGreece[10][0].setText(" " + languageResourceBundle.getString("websiteLabel"));
				gridPaneLabelsForGreece[11][0].setText(" " + languageResourceBundle.getString("wikipediaLinkLabel"));
				gridPaneLabelsForGreece[12][0].setText(" " + languageResourceBundle.getString("municipalitiesLabel"));
				gridPaneLabelsForGreece[13][0].setText("");
				gridPaneLabelsForGreece[14][0].setText("");
			}
		}
		else if(getCategoryCurrentlyViewing() == 4)
		{
			if(getIndexInOptionsForAttractions() == 0 &&
			   !gridPaneLabelsForAttractions[0][0].getText().equals(" " + languageResourceBundle.getString("nameLabel")))
			{
				gridPaneLabelsForAttractions[0][0].setText(" " + languageResourceBundle.getString("nameLabel"));
				gridPaneLabelsForAttractions[1][0].setText(" " + languageResourceBundle.getString("attractionCountry"));
				gridPaneLabelsForAttractions[2][0].setText(" " + languageResourceBundle.getString("attractionCity"));
				gridPaneLabelsForAttractions[3][0].setText(" " + languageResourceBundle.getString("attractionYearBuilt"));
				gridPaneLabelsForAttractions[4][0].setText(" " + languageResourceBundle.getString("attractionCoordinates"));
				gridPaneLabelsForAttractions[5][0].setText(" " + languageResourceBundle.getString("wikipediaLinkLabel"));
			}
		}
	}
	
	private void updateStrings()
	{
		//TITLE LABEL
		titleLabel.setText(languageResourceBundle.getString("titleForAtlasScreenLabel"));
		
		//TOGGLE BUTTONS TEXT
		continentsAndCountriesToggleButton.setText(languageResourceBundle.getString("continentsAndCountries"));
		USAToggleButton.setText(languageResourceBundle.getString("usa"));
		greeceToggleButton.setText(languageResourceBundle.getString("greece"));
		attractionsToggleButton.setText(languageResourceBundle.getString("attractions"));
		
		//TOGGLE BUTTONS TOOLTIP
		countriesToggleButtonTooltip.setText(languageResourceBundle.getString("countriesToggleButtonTooltip"));
		USAToggleButtonTooltip.setText(languageResourceBundle.getString("USAStatesToggleButtonTooltip"));
		greekCountiesToggleButtonTooltip.setText(languageResourceBundle.getString("greekCountiesToggleButtonTooltip"));
		attractionsToggleButtonTooltip.setText(languageResourceBundle.getString("attractionsToggleButtonTooltip"));
		
		//BACK BUTTON
		backButton.setText(languageResourceBundle.getString("backButton"));
		
		//ICON TOOLTIPS
		soundOptionsToolTip.setText(languageResourceBundle.getString("soundOptionsTooltip"));
		minimizeTooltip.setText(languageResourceBundle.getString("minimizeTooltip"));
		exitTooltip.setText(languageResourceBundle.getString("exitTooltip"));
		
		//IMAGE LABELS
		flagLabelForCountriesAndContinents.setText(languageResourceBundle.getString("flagLabel"));
		coatOfArmsLabelForCountriesAndContinents.setText(languageResourceBundle.getString("coatOfArmsLabel"));
		locationLabelForCountriesAndContinents.setText(languageResourceBundle.getString("locationLabel"));
		
		flagLabelForUSA.setText(languageResourceBundle.getString("flagLabel"));
		sealLabelForUSA.setText(languageResourceBundle.getString("sealLabel"));
		locationLabelForUSA.setText(languageResourceBundle.getString("locationLabel"));
		
		logoLabelForGreece.setText(languageResourceBundle.getString("logoLabel"));
		locationLabelForGreece.setText(languageResourceBundle.getString("locationLabel"));
		
		attractionLabel.setText(languageResourceBundle.getString("attractionImageLabel"));
		attractionLocationLabel.setText(languageResourceBundle.getString("locationLabel"));
		
		//COMBO BOXES
		if(getCurrentLanguage() == LANGUAGE.GREEK)
		{
			optionsForCountriesAndContinentsComboBox.setItems(optionsForCountriesAndContinentsObservableListInGreek);
			optionsForUSAComboBox.setItems(optionsForUSAObservableListInGreek);
			optionsForGreeceComboBox.setItems(optionsForGreeceObservableListInGreek);
			optionsForAttractionsComboBox.setItems(optionsForAttractionsObservableListInGreek);
		}
		else
		{
			optionsForCountriesAndContinentsComboBox.setItems(optionsForCountriesAndContinentsObservableListInEnglish);
			optionsForUSAComboBox.setItems(optionsForUSAObservableListInEnglish);
			optionsForGreeceComboBox.setItems(optionsForGreeceObservableListInEnglish);
			optionsForAttractionsComboBox.setItems(optionsForAttractionsObservableListInEnglish);
		}
		
		//VOLUME TEXT
		updateMasterVolumeText();
		updateMusicVolumeText();
		updateOtherVolumeText();
	}
	
	private void startTextAnimation()
	{
		if(titleLabel.getScaleX() != 0.90)
		{
			double time = (Math.abs(titleLabel.getScaleX() - 0.90) * 10) * TEXT_SCALE_ANIMATION_TIME;
			
			scaleTransitionForTitleLabel.setDuration(Duration.millis(time));
			scaleTransitionForTitleLabel.setFromX(titleLabel.getScaleX());
			scaleTransitionForTitleLabel.setFromY(titleLabel.getScaleY());
			scaleTransitionForTitleLabel.setToX(0.90);
			scaleTransitionForTitleLabel.setToY(0.90);
			scaleTransitionForTitleLabel.setCycleCount(1);
			scaleTransitionForTitleLabel.setAutoReverse(false);
			
			scaleTransitionForTitleLabel.setOnFinished(e ->
			{
				scaleTransitionForTitleLabel.setDuration(Duration.millis(TEXT_SCALE_ANIMATION_TIME));
				scaleTransitionForTitleLabel.setFromX(0.90);
				scaleTransitionForTitleLabel.setFromY(0.90);
				scaleTransitionForTitleLabel.setToX(1.05);
				scaleTransitionForTitleLabel.setToY(1.05);
				scaleTransitionForTitleLabel.setCycleCount(Animation.INDEFINITE);
				scaleTransitionForTitleLabel.setAutoReverse(true);
				scaleTransitionForTitleLabel.setOnFinished(null);
				
				scaleTransitionForTitleLabel.playFromStart();
			});
			
			scaleTransitionForTitleLabel.playFromStart();
		}
		else
		{
			scaleTransitionForTitleLabel.setDuration(Duration.millis(TEXT_SCALE_ANIMATION_TIME));
			scaleTransitionForTitleLabel.setFromX(0.90);
			scaleTransitionForTitleLabel.setFromY(0.90);
			scaleTransitionForTitleLabel.setToX(1.05);
			scaleTransitionForTitleLabel.setToY(1.05);
			scaleTransitionForTitleLabel.setCycleCount(Animation.INDEFINITE);
			scaleTransitionForTitleLabel.setAutoReverse(true);
			scaleTransitionForTitleLabel.setOnFinished(null);
			
			scaleTransitionForTitleLabel.playFromStart();
		}
	}
	
	public void resumeTextAnimation()
	{
		scaleTransitionForTitleLabel.play();
	}
	
	public void pauseTextAnimation()
	{
		scaleTransitionForTitleLabel.pause();
	}
	
	public class LabelGridCell extends GridCell<Short>
	{
		private ImageView imageView;
		private Label label;
		String imageName;
		int i;
		
		LabelGridCell()
		{
			imageView = new CustomImageView(false, true, false, false, null);
			imageView.setSmooth(false);
			imageView.setPreserveRatio(true);
			
			label = new Label();
			label.setGraphic(imageView);
			label.setContentDisplay(ContentDisplay.TOP);
			label.setTextAlignment(TextAlignment.CENTER);
			label.setAlignment(Pos.BASELINE_CENTER);
			label.setTextFill(Color.valueOf("#7A301B"));
			label.setCursor(Cursor.HAND);
		}
		
		protected void updateItem(Short item, boolean empty)
		{
			super.updateItem(item, empty);
			
			if (empty || item == null)
			{
				setText(null);
				setGraphic(null);
			}
            else
			{
				i = this.getIndex();
				
				if(getCategoryCurrentlyViewing() == 1)
				{
					if(getCurrentLanguage() == LANGUAGE.GREEK) imageName = countries[item].getNameInGreek();
					else imageName = countries[item].getNameInEnglish();

					if(typeOfGridViewImagesForCountriesAndContinents == GridViewImagesForCountriesAndContinentsType.FLAG)
					{
						imageView.setImage(getImage(ImageType.COUNTRY_FLAG, "x250", imageName, true));
						
						label.setOnMouseClicked(e ->
						{
							setIndexInBigImageNormal(i);
							setupBigImageNormal(BigImageType.FLAG_FOR_COUNTRIES, item);
						});
					}
					else if(typeOfGridViewImagesForCountriesAndContinents == GridViewImagesForCountriesAndContinentsType.COAT_OF_ARMS)
					{
						imageView.setImage(getImage(ImageType.COUNTRY_COAT_OF_ARMS, "x250", imageName, true));
						
						label.setOnMouseClicked(e ->
						{
							setIndexInBigImageNormal(i);
							setupBigImageNormal(BigImageType.COAT_OF_ARMS_FOR_COUNTRIES, item);
						});
					}
					else
					{
						imageView.setImage(getImage(ImageType.COUNTRY_LOCATION,  "x250", imageName, true));
						
						label.setOnMouseClicked(e ->
						{
							setIndexInBigImageNormal(i);
							setupBigImageNormal(BigImageType.LOCATION_FOR_COUNTRIES, item);
						});
					}
					
					label.setPrefWidth(gridViewForImagesForCountriesAndContinents.getCellWidth());
					label.setPrefHeight(gridViewForImagesForCountriesAndContinents.getCellHeight());
				}
				else if(getCategoryCurrentlyViewing() == 2)
				{
					if(getCurrentLanguage() == LANGUAGE.GREEK) imageName = statesOfUSA[item].getNameInGreek();
					else imageName = statesOfUSA[item].getNameInEnglish();

					if(typeOfGridViewImagesForUSA == GridViewImagesForUSAType.FLAG)
					{
						imageView.setImage(getImage(ImageType.USA_FLAG, "x250", imageName, true));
						
						label.setOnMouseClicked(e ->
						{
							setIndexInBigImageNormal(i);
							setupBigImageNormal(BigImageType.FLAG_FOR_USA, item);
						});
					}
					else if(typeOfGridViewImagesForUSA == GridViewImagesForUSAType.SEAL)
					{
						imageView.setImage(getImage(ImageType.USA_SEAL, "x250", imageName, true));
						
						label.setOnMouseClicked(e ->
						{
							setIndexInBigImageNormal(i);
							setupBigImageNormal(BigImageType.SEAL_FOR_USA, item);
						});
					}
					else
					{
						imageView.setImage(getImage(ImageType.USA_LOCATION, "x250", imageName, true));
						
						label.setOnMouseClicked(e ->
						{
							setIndexInBigImageNormal(i);
							setupBigImageNormal(BigImageType.LOCATION_FOR_USA, item);
						});
					}
					
					label.setPrefWidth(gridViewForImagesForUSA.getCellWidth());
					label.setPrefHeight(gridViewForImagesForUSA.getCellHeight());
				}
				
				imageView.setFitWidth(0.65 * label.getPrefWidth());
				
				label.setText(imageName);
				label.setFont(fontBig);
				
				if (imageName != null && imageName.length() > 25)
				{
					Tooltip tooltip = new CustomTooltip();
					tooltip.setText(imageName);
					tooltip.setFont(fontForTooltips);
					tooltip.setMaxWidth(0.3646 * stage.getWidth());
					label.setTooltip(tooltip);
				}
				else label.setTooltip(null);
				
				if ((i / 4) % 2 == 0 && i % 2 == 0 || (i / 4) % 2 == 1 && i % 2 == 1) label.setStyle("-fx-background-color: #FFEBCD; -fx-text-fill: #323232;");
				else label.setStyle("-fx-background-color: #EDD1A6; -fx-text-fill: #323232;");
				
				this.setGraphic(label);
			}
		}
	}
	
	private int getCategoryCurrentlyViewing()
	{
		if(toggleGroupForToggleButtons.getSelectedToggle() == null) return 0;
		else if(toggleGroupForToggleButtons.getSelectedToggle() == continentsAndCountriesToggleButton) return 1;
		else if(toggleGroupForToggleButtons.getSelectedToggle() == USAToggleButton) return 2;
		else if(toggleGroupForToggleButtons.getSelectedToggle() == greeceToggleButton) return 3;
		else return 4;
	}
	
	private int getIndexInBigImageNormal()
	{
		return indexInBigImageNormal;
	}
	
	private void setIndexInBigImageNormal(int indexInBigImageNormal)
	{
		this.indexInBigImageNormal = indexInBigImageNormal;
	}
	
	private int getIndexInOptionsForCountriesAndContinents()
	{
		return optionsForCountriesAndContinentsComboBox.getSelectionModel().getSelectedIndex();
	}
	
	private void setIndexInOptionsForCountriesAndContinents(int indexInOptionsForCountriesAndContinents)
	{
		optionsForCountriesAndContinentsComboBox.getSelectionModel().select(indexInOptionsForCountriesAndContinents);
	}
	
	private int getIndexInOptionsForUSA()
	{
		return optionsForUSAComboBox.getSelectionModel().getSelectedIndex();
	}
	
	private void setIndexInOptionsForUSA(int indexInOptionsForUSA)
	{
		optionsForUSAComboBox.getSelectionModel().select(indexInOptionsForUSA);
	}
	
	private int getIndexInOptionsForGreece()
	{
		return optionsForGreeceComboBox.getSelectionModel().getSelectedIndex();
	}
	
	private void setIndexInOptionsForGreece(int indexInOptionsForGreece)
	{
		optionsForGreeceComboBox.getSelectionModel().select(indexInOptionsForGreece);
	}
	
	private int getIndexInOptionsForAttractions()
	{
		return optionsForAttractionsComboBox.getSelectionModel().getSelectedIndex();
	}
	
	private void setIndexInOptionsForAttractions(int indexInOptionsForAttractions)
	{
		optionsForAttractionsComboBox.getSelectionModel().select(indexInOptionsForAttractions);
	}
	
	private int getIndexInListViewForCountriesAndContinents()
	{
		return listViewForCountriesAndContinents.getSelectionModel().getSelectedIndex();
	}
	
	private void setIndexInListViewForCountriesAndContinents(int indexInListViewForCountriesAndContinents)
	{
		listViewForCountriesAndContinents.getSelectionModel().select(indexInListViewForCountriesAndContinents);
	}
	
	private int getIndexInListViewForUSA()
	{
		return listViewForUSA.getSelectionModel().getSelectedIndex();
	}
	
	private void setIndexInListViewForUSA(int indexInListViewForUSA)
	{
		listViewForUSA.getSelectionModel().select(indexInListViewForUSA);
	}
	
	private int getIndexInListViewForGreece()
	{
		return listViewForGreece.getSelectionModel().getSelectedIndex();
	}
	
	private void setIndexInListViewForGreece(int indexInListViewForGreece)
	{
		listViewForGreece.getSelectionModel().select(indexInListViewForGreece);
	}
	
	private int getIndexInListViewForAttractions()
	{
		return listViewForAttractions.getSelectionModel().getSelectedIndex();
	}
	
	private void setIndexInListViewForAttractions(int indexInListViewForAttractions)
	{
		listViewForAttractions.getSelectionModel().select(indexInListViewForAttractions);
	}
	
	private void setNumberOfTextAreaLines(int num)
	{
		numberOfTextAreaLines = num;
	}
	
	private int getNumberOfTextAreaLines()
	{
		return numberOfTextAreaLines;
	}
}
