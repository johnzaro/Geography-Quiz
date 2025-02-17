package com.johnzaro.geographyquiz.screens;

import com.johnzaro.geographyquiz.core.ImageStuff.ImageType;
import com.johnzaro.geographyquiz.core.ImageStuff.Images;
import com.johnzaro.geographyquiz.core.customNodes.*;
import com.johnzaro.geographyquiz.core.helperClasses.MouseStationaryHelper;
import com.johnzaro.geographyquiz.core.helperClasses.UrlOpener;
import com.johnzaro.geographyquiz.dataStructures.*;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

import java.util.ArrayList;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;

public class AtlasScreen extends CoreScreenWithMovingBackground
{
	private ListView<String> listViewForCountriesAndContinents, listViewForUSA, listViewForGreece, listViewForAttractions;

	private ObservableList<String> countriesObservableNamesList, capitalsOfCountriesObservableNamesList,
			continentsObservableNamesList, statesOfUSAObservableNamesList, capitalsOfStatesObservableNamesList,
			greekDecentralizedAdministrationsObservableNamesList, greekRegionsObservableNamesList,
			greekRegionalUnitsObservableNamesList, attractionsObservableNamesList;

	private ObservableList<Integer> countriesSortList, capitalsOfCountriesSortList, continentsSortList, statesOfUSASortList,
			capitalsOfStatesSortList, greekDecentralizedAdministrationsSortList, greekRegionsSortList, greekRegionalUnitsSortList,
			attractionsSortList, emptyObservableList;

	private CustomImageView previousChalkboardImage,
			titleImage, flagForCountriesImageSmall, flagForUSAImageSmall, coatOfArmsForCountriesImageSmall,
			sealForUSAImageSmall, locationForCountriesAndContinentsImageSmall, locationForUSAImageSmall, logoForGreeceImageSmall, locationForGreeceImageSmall,
			attractionImageSmall, attractionLocationImageSmall, bigImage;
	private CustomComboBox<String> optionsForCountriesAndContinentsComboBox, optionsForUSAComboBox, optionsForGreeceComboBox, optionsForAttractionsComboBox;
	private CustomLabel titleLabel, flagLabelForCountriesAndContinents, flagLabelForUSA, coatOfArmsLabelForCountriesAndContinents, sealLabelForUSA,
			locationLabelForCountriesAndContinents, locationLabelForUSA, logoLabelForGreece, locationLabelForGreece, attractionLabel, attractionLocationLabel, labelForBigImage;
	private CustomHBox hBoxForToggleButtons, hBoxMainForCountriesAndContinents, hBoxMainForUSA, hBoxMainForGreece, hBoxMainForAttractions, hBoxForFlagAndCoatOfArmsForCountriesAndContinents,
			hBoxForFlagAndCoatOfArmsForUSA;
	private CustomVBox vBoxFor3ImagesForCountriesAndContinents, vBoxFor3ImagesForUSA, vBoxFor2ImagesForGreece, vBoxFor2ImagesForAttractions, vBoxForListViewForCountriesAndContinents, vBoxForListViewForUSA,
			vBoxForListViewForGreece, vBoxForListViewForAttractions, vBoxForGridPaneForAttractions;
	private CustomToggleButton countriesAndContinentsToggleButton, USAToggleButton, greeceToggleButton, attractionsToggleButton;
	private ToggleGroup toggleGroupForToggleButtons;
	private CustomButton backButton, previousInBigImageButton, nextInBigImageButton, exitBigImageButton, zoomInBigImageButton, zoomOutBigImageButton;
	private CustomRectangle rectangleForBigImage, rectangleToShowInfo;
	private TextArea textAreaForInfo, attractionBasicInfoLabel;

	private CustomGridPane gridPaneForLabelsForCountriesAndContinents, gridPaneForLabelsForUSA, gridPaneForGreece, gridPaneForAttractions;
	private CustomScrollPane scrollPaneForGridPaneForCountriesAndContinents, scrollPaneForGridPaneForUSA, scrollPaneForAttractionsBasicInfo;
	private GridView<Integer> gridViewForImagesForCountriesAndContinents, gridViewForImagesForUSA, gridViewForImagesForAttractions;

	private CustomLabel[][] gridPaneLabelsForCountriesAndContinents, gridPaneLabelsForUSA, gridPaneLabelsForGreece, gridPaneLabelsForAttractions;

	private TranslateTransition translateTransitionForTitleImage, translateTransitionForTitleLabel,
			translateTransitionForHBoxMainForCountriesAndContinents, translateTransitionForHBoxMainForUSA, translateTransitionForHBoxMainForGreece, translateTransitionForHBoxMainForAttractions;
	private FadeTransition fadeTransitionForMovingEarthImage;
	private ScaleTransition scaleTransitionForTitleLabel, scaleTransitionForBackButton,
			scaleTransitionForCountriesToggleButton, scaleTransitionForUSAStatesToggleButton, scaleTransitionForGreekCountiesToggleButton,
			scaleTransitionForAttractionsToggleButton, scaleTransitionForHBoxMainForCountriesAndContinents, scaleTransitionForHBoxMainForUSA,
			scaleTransitionForHBoxMainForGreece, scaleTransitionForHBoxMainForAttractions, scaleTransitionForRectangleForBigImage, scaleTransitionForBigImage,
			scaleTransitionForPreviousInBigImage, scaleTransitionForNextInBigImage, scaleTransitionForLabelInBigImage,
			scaleTransitionForZoomInInBigImage, scaleTransitionForZoomOutInBigImage, scaleTransitionForExitInBigImage, scaleTransitionForRectangleForInfo,
			scaleTransitionForTextAreaForInfo;
	private double viewPortX, viewPortY;

	private DropShadow nextInBigImageButtonShadow;

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
		
		titleImage.setFitWidth(0.4688 * width);
		titleImage.setLayoutX(width / 2.0 - titleImage.getFitWidth() / 2.0);
		titleImage.setLayoutY(ratioProperties.getAtlas().getTitleImageSetY() * height);
		if(titleImage.getTranslateX() != 0)
		{
			titleImage.setTranslateX(-0.0416 * width);
			titleLabel.setTranslateX(-0.0416 * width);
		}
		
		titleLabel.setPrefSize(0.4375 * width, 0.1296 * height);
		titleLabel.setLayoutX(ratioProperties.getAtlas().getTitleLabelSetX() * width);
		titleLabel.setLayoutY(ratioProperties.getAtlas().getTitleLabelSetY() * height);
		titleLabel.setFont(font95B);
		
		woodPanelFor1IconImage.setLayoutX(ratioProperties.getScoreBoard().getWoodPanelFor1IconImageLayoutX() * width - woodPanelFor1IconImage.getFitWidth() / 2.0);
		woodPanelFor1IconImage.setLayoutY(ratioProperties.getScoreBoard().getWoodPanelFor1IconImageLayoutY() * height);
		
		vBoxForSound.setLayoutX(0.7031 * width);
		vBoxForSound.setLayoutY(ratioProperties.getAtlas().getvBoxForSoundLayoutY() * height);
		if(vBoxForSound.getTranslateY() != 0) vBoxForSound.setTranslateY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
		
		soundButton.setLayoutY(ratioProperties.getScoreBoard().getSoundIconLayoutY() * height - soundButton.getFitWidth() / 2.0);
		
		//HBOX FOR TOGGLE BUTTONS ------------------------------------------------------
		hBoxForToggleButtons.setPrefSize(0.8594 * width, 0.0741 * height);
		hBoxForToggleButtons.setLayoutX(width / 2.0 - hBoxForToggleButtons.getPrefWidth() / 2.0);
		hBoxForToggleButtons.setLayoutY(ratioProperties.getAtlas().gethBoxForToggleButtonsLayoutY() * height);
		hBoxForToggleButtons.setSpacing(0.0078 * width);
		countriesAndContinentsToggleButton.setPrefWidth(0.2500 * hBoxForToggleButtons.getPrefWidth());
		countriesAndContinentsToggleButton.setFont(font25P);
		countriesAndContinentsToggleButton.getTooltip().setFont(font25P);
		USAToggleButton.setPrefWidth(0.2500 * hBoxForToggleButtons.getPrefWidth());
		USAToggleButton.setFont(font25P);
		USAToggleButton.getTooltip().setFont(font25P);
		greeceToggleButton.setPrefWidth(0.2500 * hBoxForToggleButtons.getPrefWidth());
		greeceToggleButton.setFont(font25P);
		greeceToggleButton.getTooltip().setFont(font25P);
		attractionsToggleButton.setPrefWidth(0.2500 * hBoxForToggleButtons.getPrefWidth());
		attractionsToggleButton.setFont(font25P);
		attractionsToggleButton.getTooltip().setFont(font25P);

		//BACK BUTTON ------------------------------------------------------
		backButton.setFitWidth(0.0703 * width);
		backButton.setLayoutX(0.0420 * width);
		backButton.setLayoutY(0.8241 * height);
		backButton.setFont(font25P);
		backButton.getTooltip().setFont(font25P);

		double gridHeight, leftWidth, rightWidth;
		//COUNTRIES AND CONTINENTS STUFF ------------------------------------------------------
		hBoxMainForCountriesAndContinents.setPrefSize(0.9167 * width, 0.6055 * height);
		hBoxMainForCountriesAndContinents.setLayoutX(width / 2.0 - hBoxMainForCountriesAndContinents.getPrefWidth() / 2.0);
		if (width < 1050) hBoxMainForCountriesAndContinents.setLayoutY(0.3306 * height); // 357 -> 1080
		else if (width < 1150) hBoxMainForCountriesAndContinents.setLayoutY(0.3241 * height); // 350 -> 1080
		else hBoxMainForCountriesAndContinents.setLayoutY(0.3194 * height); // 345 -> 1080
		hBoxMainForCountriesAndContinents.setSpacing(0.0078 * width);

		if(getIndexInOptionsForCountriesAndContinents() == 0 || getIndexInOptionsForCountriesAndContinents() == 1 || getIndexInOptionsForCountriesAndContinents() == 5)
			vBoxForListViewForCountriesAndContinents.setPrefWidth(0.2083 * width);
		else vBoxForListViewForCountriesAndContinents.setPrefWidth(0.1710 * width);
		vBoxForListViewForCountriesAndContinents.setPrefHeight(hBoxMainForCountriesAndContinents.getPrefHeight());
		vBoxForListViewForCountriesAndContinents.setSpacing(0.0093 * height);

		optionsForCountriesAndContinentsComboBox.setPrefSize(vBoxForListViewForCountriesAndContinents.getPrefWidth(), 0.0278 * height);
		optionsForCountriesAndContinentsComboBox.setStyle(cssFont20P);

		listViewForCountriesAndContinents.setPrefSize(vBoxForListViewForCountriesAndContinents.getPrefWidth(), 0.4444 * height);
		listViewForCountriesAndContinents.setStyle(cssFont20P);

		gridPaneForLabelsForCountriesAndContinents.setPrefWidth(0.4427 * width);
		gridHeight = Math.floor(hBoxMainForCountriesAndContinents.getPrefHeight() / 15.0 - 0.0009 * height);
		if(getIndexInOptionsForCountriesAndContinents() == 0 || getIndexInOptionsForCountriesAndContinents() == 1)
		{
			leftWidth  = 0.3556 * gridPaneForLabelsForCountriesAndContinents.getPrefWidth();
			rightWidth = gridPaneForLabelsForCountriesAndContinents.getPrefWidth() - leftWidth;
		}
		else
		{
			leftWidth  = 0.4778 * gridPaneForLabelsForCountriesAndContinents.getPrefWidth();
			rightWidth = gridPaneForLabelsForCountriesAndContinents.getPrefWidth() - leftWidth;
		}
		for(CustomLabel[] gridPaneLabelsForCountriesAndContinent : gridPaneLabelsForCountriesAndContinents)
		{
			gridPaneLabelsForCountriesAndContinent[0].setPrefSize(leftWidth, gridHeight);
			gridPaneLabelsForCountriesAndContinent[1].setPrefSize(rightWidth, gridHeight);
			gridPaneLabelsForCountriesAndContinent[0].setMaxSize(leftWidth, gridHeight);
			gridPaneLabelsForCountriesAndContinent[1].setMaxSize(rightWidth, gridHeight);

			gridPaneLabelsForCountriesAndContinent[0].setFont(font25P);
			gridPaneLabelsForCountriesAndContinent[1].setFont(font25P);

			gridPaneLabelsForCountriesAndContinent[1].getTooltip().setFont(font25P);
		}
		scrollPaneForGridPaneForCountriesAndContinents.setPrefHeight(15 * gridHeight + 0.0098 * height);
		scrollPaneForGridPaneForCountriesAndContinents.setStyle(cssPadding5);

		vBoxFor3ImagesForCountriesAndContinents.setSpacing(0.0208 * width);
		vBoxFor3ImagesForCountriesAndContinents.setPrefSize(0.2344 * width, hBoxMainForCountriesAndContinents.getPrefHeight());
		hBoxForFlagAndCoatOfArmsForCountriesAndContinents.setSpacing(0.0208 * width);
		hBoxForFlagAndCoatOfArmsForCountriesAndContinents.setPrefSize(vBoxFor3ImagesForCountriesAndContinents.getPrefWidth(), 0.4 * vBoxFor3ImagesForCountriesAndContinents.getPrefHeight());
		flagForCountriesImageSmall.setFitWidth(0.45 * hBoxForFlagAndCoatOfArmsForCountriesAndContinents.getPrefWidth());
		flagLabelForCountriesAndContinents.setFont(font30B);
		coatOfArmsForCountriesImageSmall.setFitWidth(0.45 * hBoxForFlagAndCoatOfArmsForCountriesAndContinents.getPrefWidth());
		coatOfArmsLabelForCountriesAndContinents.setFont(font30B);
		locationForCountriesAndContinentsImageSmall.setFitWidth(0.45 * vBoxFor3ImagesForCountriesAndContinents.getPrefHeight());
		locationLabelForCountriesAndContinents.setPrefSize(vBoxFor3ImagesForCountriesAndContinents.getPrefWidth(), 0.6 * vBoxFor3ImagesForCountriesAndContinents.getPrefWidth());
		locationLabelForCountriesAndContinents.setFont(font30B);

		gridViewForImagesForCountriesAndContinents.setPrefWidth(width - 2 *
				(hBoxMainForCountriesAndContinents.getLayoutX() + vBoxForListViewForCountriesAndContinents.getPrefWidth() + hBoxMainForCountriesAndContinents.getSpacing()));
		gridViewForImagesForCountriesAndContinents.setPrefHeight(hBoxMainForCountriesAndContinents.getPrefHeight());
		gridViewForImagesForCountriesAndContinents.setStyle(cssPadding10);
		gridViewForImagesForCountriesAndContinents.setCellWidth((gridViewForImagesForCountriesAndContinents.getPrefWidth() - 40) / 4);
		gridViewForImagesForCountriesAndContinents.setCellHeight(0.2315 * height);

		//USA STUFF ------------------------------------------------------
		hBoxMainForUSA.setPrefSize(0.9167 * width, 0.6055 * height);
		hBoxMainForUSA.setLayoutX(width / 2.0 - hBoxMainForUSA.getPrefWidth() / 2.0);
		if (width < 1050) hBoxMainForUSA.setLayoutY(0.3306 * height); // 357 -> 1080
		else if (width < 1150) hBoxMainForUSA.setLayoutY(0.3241 * height); // 350 -> 1080
		else hBoxMainForUSA.setLayoutY(0.3194 * height); // 345 -> 1080
		hBoxMainForUSA.setSpacing(0.0078 * width);
		
		if(getIndexInOptionsForUSA() == 0 || getIndexInOptionsForUSA() == 1) vBoxForListViewForUSA.setPrefWidth(0.2083 * width);
		else vBoxForListViewForUSA.setPrefWidth(0.1610 * width);
		vBoxForListViewForUSA.setPrefHeight(hBoxMainForUSA.getPrefHeight());
		vBoxForListViewForUSA.setSpacing(0.0093 * height);
		
		optionsForUSAComboBox.setPrefSize(0.2083 * width, 0.0278 * height);
		optionsForUSAComboBox.setStyle(cssFont20P);

		listViewForUSA.setPrefSize(0.2083 * width, 0.4444 * height);
		listViewForUSA.setStyle(cssFont20P);

		gridPaneForLabelsForUSA.setPrefWidth(0.4427 * width);
		gridHeight = Math.floor(hBoxMainForUSA.getPrefHeight() / 15.0 - 0.0009 * height);
		leftWidth = 0.4353 * gridPaneForLabelsForUSA.getPrefWidth();
		rightWidth = 0.5647 * gridPaneForLabelsForUSA.getPrefWidth();
		for(CustomLabel[] customLabels : gridPaneLabelsForUSA)
		{
			customLabels[0].setPrefSize(leftWidth, gridHeight);
			customLabels[1].setPrefSize(rightWidth, gridHeight);
			customLabels[0].setMaxSize(leftWidth, gridHeight);
			customLabels[1].setMaxSize(rightWidth, gridHeight);

			customLabels[0].setFont(font25P);
			customLabels[1].setFont(font25P);

			customLabels[1].getTooltip().setFont(font25P);
		}
		scrollPaneForGridPaneForUSA.setPrefHeight(15 * gridHeight + 0.0098 * height);
		scrollPaneForGridPaneForUSA.setStyle(cssPadding5);

		vBoxFor3ImagesForUSA.setSpacing(0.0208 * width);
		vBoxFor3ImagesForUSA.setPrefSize(0.2344 * width, hBoxMainForUSA.getPrefHeight());
		hBoxForFlagAndCoatOfArmsForUSA.setPrefSize(vBoxFor3ImagesForUSA.getPrefWidth(), 0.4 * vBoxFor3ImagesForUSA.getPrefHeight());
		hBoxForFlagAndCoatOfArmsForUSA.setSpacing(0.0208 * width);
		flagForUSAImageSmall.setFitWidth(0.45 * hBoxForFlagAndCoatOfArmsForUSA.getPrefWidth());
		flagLabelForUSA.setFont(font30B);
		sealForUSAImageSmall.setFitWidth(0.45 * hBoxForFlagAndCoatOfArmsForUSA.getPrefWidth());
		sealLabelForUSA.setFont(font30B);
		locationForUSAImageSmall.setFitWidth(0.45 * vBoxFor3ImagesForUSA.getPrefHeight());
		locationLabelForUSA.setPrefSize(vBoxFor3ImagesForUSA.getPrefWidth(), 0.6 * vBoxFor3ImagesForUSA.getPrefWidth());
		locationLabelForUSA.setFont(font30B);

		gridViewForImagesForUSA.setPrefWidth(width - 2 * (hBoxMainForUSA.getLayoutX() + vBoxForListViewForUSA.getPrefWidth() + hBoxMainForUSA.getSpacing()));
		gridViewForImagesForUSA.setPrefHeight(hBoxMainForUSA.getPrefHeight());
		gridViewForImagesForUSA.setStyle(cssPadding10);
		gridViewForImagesForUSA.setCellWidth((gridViewForImagesForUSA.getPrefWidth() - 40) / 4);
		gridViewForImagesForUSA.setCellHeight(0.2315 * height);

		//GREECE STUFF
		hBoxMainForGreece.setPrefSize(0.9167 * width, 0.6055 * height);
		hBoxMainForGreece.setLayoutX(width / 2.0 - hBoxMainForGreece.getPrefWidth() / 2.0);
		if (width < 1050) hBoxMainForGreece.setLayoutY(0.3306 * height); // 357 -> 1080
		else if (width < 1150) hBoxMainForGreece.setLayoutY(0.3241 * height); // 350 -> 1080
		else hBoxMainForGreece.setLayoutY(0.3194 * height); // 345 -> 1080
		hBoxMainForGreece.setSpacing(0.0078 * width);
		
		vBoxForListViewForGreece.setPrefSize(0.2083 * width, hBoxMainForGreece.getPrefHeight());
		vBoxForListViewForGreece.setSpacing(0.0093 * height);

		optionsForGreeceComboBox.setPrefSize(0.2083 * width, 0.0278 * height);
		optionsForGreeceComboBox.setStyle(cssFont20P);

		listViewForGreece.setPrefSize(0.2083 * width, 0.4398 * height);
		listViewForGreece.setStyle(cssFont20P);

		gridPaneForGreece.setPrefWidth(0.4427 * width);
		gridPaneForGreece.setStyle(cssPadding5);
		gridHeight = Math.floor(hBoxMainForGreece.getPrefHeight() / 15.0 - 0.0009 * height);
		leftWidth = 0.4353 * gridPaneForGreece.getPrefWidth();
		rightWidth = 0.5647 * gridPaneForGreece.getPrefWidth();
		for(CustomLabel[] customLabels : gridPaneLabelsForGreece)
		{
			customLabels[0].setPrefSize(leftWidth, gridHeight);
			customLabels[1].setPrefSize(rightWidth, gridHeight);
			customLabels[0].setMaxSize(leftWidth, gridHeight);
			customLabels[1].setMaxSize(rightWidth, gridHeight);
			customLabels[0].setFont(font25P);
			customLabels[1].setFont(font25P);

			customLabels[1].getTooltip().setFont(font25P);
		}

		vBoxFor2ImagesForGreece.setSpacing(0.0052 * width);
		vBoxFor2ImagesForGreece.setPrefSize(0.2344 * width, hBoxMainForGreece.getPrefHeight());
		logoForGreeceImageSmall.setFitWidth(0.8 * vBoxFor2ImagesForGreece.getPrefWidth());
		logoLabelForGreece.setFont(font30B);
		locationForGreeceImageSmall.setFitWidth(0.8 * vBoxFor2ImagesForGreece.getPrefWidth());
		locationLabelForGreece.setFont(font30B);

		//ATTRACTIONS STUFF
		hBoxMainForAttractions.setPrefSize(0.9167 * width, 0.6055 * height);
		hBoxMainForAttractions.setLayoutX(width / 2.0 - hBoxMainForAttractions.getPrefWidth() / 2.0);
		if (width < 1050) hBoxMainForAttractions.setLayoutY(0.3306 * height); // 357 -> 1080
		else if (width < 1150) hBoxMainForAttractions.setLayoutY(0.3241 * height); // 350 -> 1080
		else hBoxMainForAttractions.setLayoutY(0.3194 * height); // 345 -> 1080
		hBoxMainForAttractions.setSpacing(0.0078 * width);
		
		vBoxForListViewForAttractions.setPrefSize(0.2083 * width, hBoxMainForAttractions.getPrefHeight());
		vBoxForListViewForAttractions.setSpacing(0.0093 * height);
		
		optionsForAttractionsComboBox.setPrefSize(0.2083 * width, 0.0278 * height);
		optionsForAttractionsComboBox.setStyle(cssFont20P);

		listViewForAttractions.setPrefSize(0.2083 * width, 0.4398 * height);
		listViewForAttractions.setStyle(cssFont20P);

		vBoxForGridPaneForAttractions.setPrefSize(0.4427 * width, hBoxMainForAttractions.getPrefHeight());
		vBoxForGridPaneForAttractions.setSpacing(0.0185 * height);

		gridPaneForAttractions.setPrefWidth(vBoxForGridPaneForAttractions.getPrefWidth());
		gridPaneForAttractions.setStyle(cssPadding5);

		gridHeight = Math.floor(hBoxMainForAttractions.getPrefHeight() / 15.0 - 0.0009 * height);
		leftWidth = 0.4353 * gridPaneForAttractions.getPrefWidth();
		rightWidth = 0.5647 * gridPaneForAttractions.getPrefWidth();
		for(CustomLabel[] gridPaneLabelsForAttraction : gridPaneLabelsForAttractions)
		{
			gridPaneLabelsForAttraction[0].setPrefSize(leftWidth, gridHeight);
			gridPaneLabelsForAttraction[1].setPrefSize(rightWidth, gridHeight);
			gridPaneLabelsForAttraction[0].setMaxSize(leftWidth, gridHeight);
			gridPaneLabelsForAttraction[1].setMaxSize(rightWidth, gridHeight);
			gridPaneLabelsForAttraction[0].setFont(font25P);
			gridPaneLabelsForAttraction[1].setFont(font25P);

			gridPaneLabelsForAttraction[1].getTooltip().setFont(font25P);
		}
		
		vBoxFor2ImagesForAttractions.setSpacing(0.0208 * width);
		vBoxFor2ImagesForAttractions.setPrefSize(0.2344 * width, hBoxMainForAttractions.getPrefHeight());
		attractionImageSmall.setFitWidth(0.87 * vBoxFor2ImagesForAttractions.getPrefWidth());
		attractionLabel.setFont(font30B);
		attractionLocationImageSmall.setFitWidth(0.87 * vBoxFor2ImagesForAttractions.getPrefWidth());
		attractionLocationLabel.setFont(font30B);

		attractionBasicInfoLabel.setFont(font25P);
		attractionBasicInfoLabel.setStyle(TEXT_FILL_DARK_GREY);
		scrollPaneForAttractionsBasicInfo.setPrefWidth(gridPaneForAttractions.getPrefWidth());
		scrollPaneForAttractionsBasicInfo.setMaxHeight(0.3333 * height);
		if(attractionBasicInfoLabel.lookup(".text") != null)
			scrollPaneForAttractionsBasicInfo.setPrefHeight(1.25 * attractionBasicInfoLabel.lookup(".text").getLayoutBounds().getHeight());
		scrollPaneForAttractionsBasicInfo.setStyle("-fx-border-width:" + 0.0031 * width + "; " +
				"-fx-padding:" + 0.0021 * width + " " + 0.0042 * width + " " + 0.0021 * width + " " + 0.0042 * width + ";");

		//MORE INFO ------------------------------------------------------
		if (getNumberOfTextAreaLines() < 4) textAreaForInfo.setPrefHeight(0.1500 * height);
		else if (getNumberOfTextAreaLines() < 6) textAreaForInfo.setPrefHeight(0.2000 * height);
		else if (getNumberOfTextAreaLines() < 8) textAreaForInfo.setPrefHeight(0.2700 * height);
		else if (getNumberOfTextAreaLines() < 11) textAreaForInfo.setPrefHeight(0.3700 * height);
		else if (getNumberOfTextAreaLines() < 16) textAreaForInfo.setPrefHeight(0.5000 * height);
		else textAreaForInfo.setPrefHeight(0.7000 * mainScene.getHeight());

		textAreaForInfo.setPrefWidth(0.3125 * width);
		textAreaForInfo.setLayoutX(width / 2.0 - textAreaForInfo.getPrefWidth() / 2.0);
		textAreaForInfo.setLayoutY(height / 2.0 - textAreaForInfo.getPrefHeight() / 2.0);
		textAreaForInfo.setFont(font25P);

		rectangleToShowInfo.setArcWidth(0.0208 * width);
		rectangleToShowInfo.setArcHeight(0.0208 * width);
		rectangleToShowInfo.setStrokeWidth(0.0042 * width);
		rectangleToShowInfo.setWidth(0.3333 * width);
		rectangleToShowInfo.setHeight(textAreaForInfo.getPrefHeight() + 0.0208 * height);
		rectangleToShowInfo.setLayoutX(width / 2.0 - rectangleToShowInfo.getWidth() / 2.0);
		rectangleToShowInfo.setLayoutY(height / 2.0 - rectangleToShowInfo.getHeight() / 2.0);

		//BIG IMAGE STUFF ------------------------------------------------------
		nextInBigImageButtonShadow.setRadius(0.0104 * width);
		nextInBigImageButtonShadow.setOffsetX(0.0026 * width);
		nextInBigImageButtonShadow.setOffsetY(0.0026 * width);
		
		bigImage.setFitWidth(0.7 * height);
		bigImage.setLayoutX(width / 2.0 - bigImage.getFitWidth() / 2.0);
		
		rectangleForBigImage.setArcWidth(0.0208 * width);
		rectangleForBigImage.setArcHeight(0.0208 * width);
		rectangleForBigImage.setStrokeWidth(0.0042 * width);
		rectangleForBigImage.setWidth(bigImage.getFitWidth() + 0.0208 * width);
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
		labelForBigImage.setFont(font25P);
		labelForBigImage.setPrefSize(0.9 * rectangleForBigImage.getWidth(), 0.0370 * height);
		labelForBigImage.setLayoutX(width / 2.0 - labelForBigImage.getPrefWidth() / 2.0);
		labelForBigImage.setLayoutY(bigImage.getLayoutY() + bigImage.getFitHeight() + 0.0050 * height);
		previousInBigImageButton.setFitWidth(0.0781 * width);
		previousInBigImageButton.setLayoutX(width / 2.0 - previousInBigImageButton.getFitWidth() - 0.0104 * width);
		previousInBigImageButton.setLayoutY(rectangleForBigImage.getLayoutY() + rectangleForBigImage.getHeight() - previousInBigImageButton.getHeight() - 0.0139 * height);
		nextInBigImageButton.setFitWidth(0.0781 * width);
		nextInBigImageButton.setLayoutX(width / 2.0 + 0.0104 * width);
		nextInBigImageButton.setLayoutY(rectangleForBigImage.getLayoutY() + rectangleForBigImage.getHeight() - nextInBigImageButton.getHeight() - 0.0139 * height);
		zoomInBigImageButton.setFitWidth(0.0313 * width);
		zoomInBigImageButton.setLayoutX(exitBigImageButton.getLayoutX() - zoomInBigImageButton.getFitWidth() - 0.0052 * width);
		zoomInBigImageButton.setLayoutY(rectangleForBigImage.getLayoutY() + rectangleForBigImage.getHeight() - zoomInBigImageButton.getHeight() - 0.0185 * height);
		zoomOutBigImageButton.setFitWidth(0.0313 * width);
		zoomOutBigImageButton.setLayoutX(zoomInBigImageButton.getLayoutX() - zoomOutBigImageButton.getFitWidth() - 0.0052 * width);
		zoomOutBigImageButton.setLayoutY(rectangleForBigImage.getLayoutY() + rectangleForBigImage.getHeight() - zoomOutBigImageButton.getHeight() - 0.0185 * height);
		exitBigImageButton.setFitWidth(0.0313 * width);
		exitBigImageButton.setLayoutX(rectangleForBigImage.getLayoutX() + rectangleForBigImage.getWidth() - exitBigImageButton.getFitWidth() - 0.0104 * width);
		exitBigImageButton.setLayoutY(rectangleForBigImage.getLayoutY() + rectangleForBigImage.getHeight() - exitBigImageButton.getHeight() - 0.0185 * height);
	}

	protected void recalculateBackground(double width, double height)
	{
		super.recalculateBackground(width, height);

		previousChalkboardImage.setFitWidth(width);
		previousChalkboardImage.setFitHeight(height);
	}

	public AtlasScreen()
	{
		//FUNDAMENTAL STUFF-------------------------------------------------------------------------------------
		previousChalkboardImage = new CustomImageView(true, false, false, true, CacheHint.SPEED);
		previousChalkboardImage.setLayoutX(0);
		previousChalkboardImage.setLayoutY(0);

		movingEarthImage = new CustomImageView(true, true, false, false, null);

		titleImage = new CustomImageView(true, true, false, true, CacheHint.SPEED);

		titleLabel = new CustomLabel(Pos.CENTER, TextAlignment.CENTER, DARK_BROWN, null, null, null, false, false, false, false, true, CacheHint.SCALE);
		titleLabel.setEffect(innerShadow);

		//TOGGLE BUTTONS-------------------------------------------------------------------------------------
		countriesAndContinentsToggleButton = new CustomToggleButton(false, null, null, true, false, null);
		USAToggleButton = new CustomToggleButton(false, null, null, true, false, null);
		greeceToggleButton = new CustomToggleButton(false, null, null, true, false, null);
		attractionsToggleButton = new CustomToggleButton(false, null, null, true, false, null);

		toggleGroupForToggleButtons = new ToggleGroup();
		toggleGroupForToggleButtons.getToggles().addAll(countriesAndContinentsToggleButton, USAToggleButton, greeceToggleButton, attractionsToggleButton);

		hBoxForToggleButtons = new CustomHBox(true, Pos.CENTER, true, CacheHint.SCALE);
		hBoxForToggleButtons.getChildren().addAll(countriesAndContinentsToggleButton, USAToggleButton, greeceToggleButton, attractionsToggleButton);

		//COUNTRIES AND CONTINENTS STUFF-------------------------------------------------------------------------------------
		optionsForCountriesAndContinentsComboBox = new CustomComboBox<>();
		optionsForCountriesAndContinentsComboBox.setPickOnBounds(false);

		listViewForCountriesAndContinents = new ListView<>();
		listViewForCountriesAndContinents.setCursor(Cursor.HAND);
		listViewForCountriesAndContinents.setPickOnBounds(false);

		vBoxForListViewForCountriesAndContinents = new CustomVBox(true, Pos.TOP_CENTER, null, false, null);
		vBoxForListViewForCountriesAndContinents.getChildren().addAll(optionsForCountriesAndContinentsComboBox, listViewForCountriesAndContinents);

		gridPaneForLabelsForCountriesAndContinents = new CustomGridPane(Pos.CENTER, null, false, null);

		gridPaneLabelsForCountriesAndContinents = new CustomLabel[17][2];

		for(int i = 0; i < gridPaneLabelsForCountriesAndContinents.length; i++)
		{
			if(i % 2 == 0)
			{
				gridPaneLabelsForCountriesAndContinents[i][0] =
						new CustomLabel(null, null, null, "grid-item-even", null, null, false, false, false, false, false, null);
				gridPaneLabelsForCountriesAndContinents[i][1] =
						new CustomLabel(null, null, null, "grid-item-even", null, null, false, false, false, true, false, null);
			}
			else
			{
				gridPaneLabelsForCountriesAndContinents[i][0] =
						new CustomLabel(null, null, null, "grid-item-odd", null, null, false, false, false, false, false, null);
				gridPaneLabelsForCountriesAndContinents[i][1] =
						new CustomLabel(null, null, null, "grid-item-odd", null, null, false, false, false, true, false, null);
			}

			if(i < 15)
			{
				gridPaneForLabelsForCountriesAndContinents.add(gridPaneLabelsForCountriesAndContinents[i][0], 0, i);
				gridPaneForLabelsForCountriesAndContinents.add(gridPaneLabelsForCountriesAndContinents[i][1], 1, i);
			}
		}

		scrollPaneForGridPaneForCountriesAndContinents = new CustomScrollPane(false, true, true, null, gridPaneForLabelsForCountriesAndContinents, "orange-background-color", true, CacheHint.SCALE);
		scrollPaneForGridPaneForCountriesAndContinents.setPickOnBounds(false);

		flagForCountriesImageSmall = new CustomImageView(false, true, false, false, null);
		coatOfArmsForCountriesImageSmall = new CustomImageView(false, true, false, false, null);
		locationForCountriesAndContinentsImageSmall = new CustomImageView(false, true, true, false, null);

		flagLabelForCountriesAndContinents = new CustomLabel(Pos.BOTTOM_CENTER, TextAlignment.CENTER, BROWN, null, flagForCountriesImageSmall, ContentDisplay.TOP, true, false, false, false, false, null);
		coatOfArmsLabelForCountriesAndContinents = new CustomLabel(Pos.BOTTOM_CENTER, TextAlignment.CENTER, BROWN, null, coatOfArmsForCountriesImageSmall, ContentDisplay.TOP, true, false, false, false, false, null);
		locationLabelForCountriesAndContinents = new CustomLabel(Pos.BASELINE_CENTER, TextAlignment.CENTER, BROWN, null, locationForCountriesAndContinentsImageSmall, ContentDisplay.TOP, true, false, false, false, false, null);

		hBoxForFlagAndCoatOfArmsForCountriesAndContinents = new CustomHBox(false, Pos.BOTTOM_CENTER, false, null);
		hBoxForFlagAndCoatOfArmsForCountriesAndContinents.getChildren().addAll(flagLabelForCountriesAndContinents, coatOfArmsLabelForCountriesAndContinents);

		vBoxFor3ImagesForCountriesAndContinents = new CustomVBox(true, Pos.CENTER, null, false, null);
		vBoxFor3ImagesForCountriesAndContinents.getChildren().addAll(hBoxForFlagAndCoatOfArmsForCountriesAndContinents, locationLabelForCountriesAndContinents);

		hBoxMainForCountriesAndContinents = new CustomHBox(false, Pos.TOP_LEFT, true, CacheHint.SCALE);
		hBoxMainForCountriesAndContinents.getChildren().addAll(vBoxForListViewForCountriesAndContinents, scrollPaneForGridPaneForCountriesAndContinents, vBoxFor3ImagesForCountriesAndContinents);

		gridViewForImagesForCountriesAndContinents = new GridView<>();
		gridViewForImagesForCountriesAndContinents.setHorizontalCellSpacing(0);
		gridViewForImagesForCountriesAndContinents.setVerticalCellSpacing(0);
		gridViewForImagesForCountriesAndContinents.setCellFactory(gridView -> new LabelGridCell());
		gridViewForImagesForCountriesAndContinents.getStyleClass().add("orange-background-color");

		//USA STUFF-------------------------------------------------------------------------------------
		optionsForUSAComboBox = new CustomComboBox<>();
		optionsForUSAComboBox.setPickOnBounds(false);

		listViewForUSA = new ListView<>();
		listViewForUSA.setCursor(Cursor.HAND);
		listViewForUSA.setPickOnBounds(false);

		vBoxForListViewForUSA = new CustomVBox(true, Pos.TOP_CENTER, null, false, null);
		vBoxForListViewForUSA.getChildren().addAll(optionsForUSAComboBox, listViewForUSA);

		gridPaneForLabelsForUSA = new CustomGridPane(Pos.CENTER, null, false, null);

		gridPaneLabelsForUSA = new CustomLabel[18][2];

		for(int i = 0; i < gridPaneLabelsForUSA.length; i++)
		{
			if(i % 2 == 0)
			{
				gridPaneLabelsForUSA[i][0] =
						new CustomLabel(null, null, null, "grid-item-even", null, null, false, false, false, false, false, null);
				gridPaneLabelsForUSA[i][1] =
						new CustomLabel(null, null, null, "grid-item-even", null, null, false, false, false, true, false, null);
			}
			else
			{
				gridPaneLabelsForUSA[i][0] =
						new CustomLabel(null, null, null, "grid-item-odd", null, null, false, false, false, false, false, null);
				gridPaneLabelsForUSA[i][1] =
						new CustomLabel(null, null, null, "grid-item-odd", null, null, false, false, false, true, false, null);
			}

			gridPaneForLabelsForUSA.add(gridPaneLabelsForUSA[i][0], 0, i);
			gridPaneForLabelsForUSA.add(gridPaneLabelsForUSA[i][1], 1, i);
		}

		gridPaneLabelsForUSA[2][1].setCursor(Cursor.HAND);

		scrollPaneForGridPaneForUSA = new CustomScrollPane(false, true, true, Cursor.MOVE, gridPaneForLabelsForUSA, "orange-background-color", true, CacheHint.SCALE);
		scrollPaneForGridPaneForUSA.setPickOnBounds(false);

		flagForUSAImageSmall = new CustomImageView(false, true, true, false, null);
		sealForUSAImageSmall = new CustomImageView(false, true, true, false, null);
		locationForUSAImageSmall = new CustomImageView(false, true, true, false, null);

		flagLabelForUSA = new CustomLabel(Pos.BOTTOM_CENTER, TextAlignment.CENTER, BROWN, null, flagForUSAImageSmall, ContentDisplay.TOP, true, false, false, false, false, null);
		sealLabelForUSA = new CustomLabel(Pos.BOTTOM_CENTER, TextAlignment.CENTER, BROWN, null, sealForUSAImageSmall, ContentDisplay.TOP, true, false, false, false, false, null);
		locationLabelForUSA = new CustomLabel(Pos.BASELINE_CENTER, TextAlignment.CENTER, BROWN, null, locationForUSAImageSmall, ContentDisplay.TOP, true, false, false, false, false, null);

		hBoxForFlagAndCoatOfArmsForUSA = new CustomHBox(false, Pos.BOTTOM_CENTER, false, null);
		hBoxForFlagAndCoatOfArmsForUSA.setPickOnBounds(false);
		hBoxForFlagAndCoatOfArmsForUSA.getChildren().addAll(flagLabelForUSA, sealLabelForUSA);

		vBoxFor3ImagesForUSA = new CustomVBox(true, Pos.CENTER, null, false, null);
		vBoxFor3ImagesForUSA.setPickOnBounds(false);
		vBoxFor3ImagesForUSA.getChildren().addAll(hBoxForFlagAndCoatOfArmsForUSA, locationLabelForUSA);

		hBoxMainForUSA = new CustomHBox(false, Pos.TOP_LEFT, true, CacheHint.SCALE);
		hBoxMainForUSA.setPickOnBounds(false);
		hBoxMainForUSA.getChildren().addAll(vBoxForListViewForUSA, scrollPaneForGridPaneForUSA, vBoxFor3ImagesForUSA);

		gridViewForImagesForUSA = new GridView<>();
		gridViewForImagesForUSA.setHorizontalCellSpacing(0);
		gridViewForImagesForUSA.setVerticalCellSpacing(0);
		gridViewForImagesForUSA.setCellFactory(gridView -> new LabelGridCell());
		gridViewForImagesForUSA.getStyleClass().add("orange-background-color");

		//GREECE STUFF-------------------------------------------------------------------------------------
		optionsForGreeceComboBox = new CustomComboBox<>();
		optionsForGreeceComboBox.setPickOnBounds(false);

		listViewForGreece = new ListView<>();
		listViewForGreece.setCursor(Cursor.HAND);
		listViewForGreece.setPickOnBounds(false);

		vBoxForListViewForGreece = new CustomVBox(true, Pos.TOP_CENTER, null, false, null);
		vBoxForListViewForGreece.setPickOnBounds(false);
		vBoxForListViewForGreece.getChildren().addAll(optionsForGreeceComboBox, listViewForGreece);

		gridPaneForGreece = new CustomGridPane(Pos.CENTER, "orange-background-color", false, null);

		gridPaneLabelsForGreece = new CustomLabel[15][2];

		for(int i = 0; i < gridPaneLabelsForGreece.length; i++)
		{
			if(i % 2 == 0)
			{
				gridPaneLabelsForGreece[i][0] =
						new CustomLabel(null, null, null, "grid-item-even", null, null, false, false, false, false, false, null);
				gridPaneLabelsForGreece[i][1] =
						new CustomLabel(null, null, null, "grid-item-even", null, null, false, false, false, true, false, null);
			}
			else
			{
				gridPaneLabelsForGreece[i][0] =
						new CustomLabel(null, null, null, "grid-item-odd", null, null, false, false, false, false, false, null);
				gridPaneLabelsForGreece[i][1] =
						new CustomLabel(null, null, null, "grid-item-odd", null, null, false, false, false, true, false, null);
			}

			gridPaneForGreece.add(gridPaneLabelsForGreece[i][0], 0, i);
			gridPaneForGreece.add(gridPaneLabelsForGreece[i][1], 1, i);
		}

		logoForGreeceImageSmall = new CustomImageView(false, true, true, false, null);
		locationForGreeceImageSmall = new CustomImageView(false, true, true, false, null);

		logoLabelForGreece = new CustomLabel(Pos.BASELINE_CENTER, TextAlignment.CENTER, BROWN, null, logoForGreeceImageSmall, ContentDisplay.TOP, true, false, false, false, false, null);
		locationLabelForGreece = new CustomLabel(Pos.BASELINE_CENTER, TextAlignment.CENTER, BROWN, null, locationForGreeceImageSmall, ContentDisplay.TOP, true, false, false, false, false, null);

		vBoxFor2ImagesForGreece = new CustomVBox(true, Pos.CENTER, null, false, null);
		vBoxFor2ImagesForGreece.setPickOnBounds(false);
		vBoxFor2ImagesForGreece.getChildren().addAll(logoLabelForGreece, locationLabelForGreece);

		hBoxMainForGreece = new CustomHBox(false, Pos.TOP_LEFT, true, CacheHint.SCALE);
		hBoxMainForGreece.setPickOnBounds(false);
		hBoxMainForGreece.getChildren().addAll(vBoxForListViewForGreece, gridPaneForGreece, vBoxFor2ImagesForGreece);

		//ATTRACTIONS STUFF-------------------------------------------------------------------------------------
		optionsForAttractionsComboBox = new CustomComboBox<>();
		optionsForAttractionsComboBox.setPickOnBounds(false);

		listViewForAttractions = new ListView<>();
		listViewForAttractions.setCursor(Cursor.HAND);
		listViewForAttractions.setPickOnBounds(false);

		vBoxForListViewForAttractions = new CustomVBox(true, Pos.TOP_CENTER, null, false, null);
		vBoxForListViewForAttractions.setPickOnBounds(false);
		vBoxForListViewForAttractions.getChildren().addAll(optionsForAttractionsComboBox, listViewForAttractions);

		gridPaneForAttractions = new CustomGridPane(Pos.CENTER, "orange-background-color", false, null);

		gridPaneLabelsForAttractions = new CustomLabel[6][2];

		for(int i = 0; i < gridPaneLabelsForAttractions.length; i++)
		{
			if(i % 2 == 0)
			{
				gridPaneLabelsForAttractions[i][0] =
						new CustomLabel(null, null, null, "grid-item-even", null, null, false, false, false, false, false, null);
				gridPaneLabelsForAttractions[i][1] =
						new CustomLabel(null, null, null, "grid-item-even", null, null, false, false, false, true, false, null);
			}
			else
			{
				gridPaneLabelsForAttractions[i][0] =
						new CustomLabel(null, null, null, "grid-item-odd", null, null, false, false, false, false, false, null);
				gridPaneLabelsForAttractions[i][1] =
						new CustomLabel(null, null, null, "grid-item-odd", null, null, false, false, false, true, false, null);
			}

			gridPaneForAttractions.add(gridPaneLabelsForAttractions[i][0], 0, i);
			gridPaneForAttractions.add(gridPaneLabelsForAttractions[i][1], 1, i);
		}

		gridPaneLabelsForAttractions[4][1].setCursor(Cursor.HAND);
		gridPaneLabelsForAttractions[5][1].setCursor(Cursor.HAND);

		gridPaneLabelsForAttractions[4][1].getTooltip().setText(languageResourceBundle.getString("mapsTooltip"));
		gridPaneLabelsForAttractions[5][1].getTooltip().setText(languageResourceBundle.getString("websiteTooltip"));

		attractionBasicInfoLabel = new TextArea();
		attractionBasicInfoLabel.setWrapText(true);
		attractionBasicInfoLabel.setEditable(false);

		scrollPaneForAttractionsBasicInfo =
				new CustomScrollPane(true, true, true, null, attractionBasicInfoLabel, "orange-border-light-background-color", true, CacheHint.SCALE);

		vBoxForGridPaneForAttractions = new CustomVBox(true, Pos.TOP_CENTER, null, false, null);
		vBoxForGridPaneForAttractions.getChildren().addAll(gridPaneForAttractions, scrollPaneForAttractionsBasicInfo);

		attractionImageSmall = new CustomImageView(false, true, true, false, null);
		attractionLocationImageSmall = new CustomImageView(false, true, true, false, null);

		attractionLabel = new CustomLabel(Pos.BASELINE_CENTER, TextAlignment.CENTER, BROWN, null, attractionImageSmall, ContentDisplay.TOP, true, false, false, false, false, null);
		attractionLocationLabel = new CustomLabel(Pos.BASELINE_CENTER, TextAlignment.CENTER, BROWN, null, attractionLocationImageSmall, ContentDisplay.TOP, true, false, false, false, false, null);

		vBoxFor2ImagesForAttractions = new CustomVBox(true, Pos.CENTER, null, false, null);
		vBoxFor2ImagesForAttractions.setPickOnBounds(false);
		vBoxFor2ImagesForAttractions.getChildren().addAll(attractionLabel, attractionLocationLabel);

		hBoxMainForAttractions = new CustomHBox(false, Pos.TOP_LEFT, true, CacheHint.SCALE);
		hBoxMainForAttractions.setPickOnBounds(false);
		hBoxMainForAttractions.getChildren().addAll(vBoxForListViewForAttractions, vBoxForGridPaneForAttractions, vBoxFor2ImagesForAttractions);

		gridViewForImagesForAttractions = new GridView<>();
		gridViewForImagesForAttractions.setHorizontalCellSpacing(0);
		gridViewForImagesForAttractions.setVerticalCellSpacing(0);
		gridViewForImagesForAttractions.setCellFactory(gridView -> new LabelGridCell());

		//MORE INFO STUFF -------------------------------------------------------------------------------------
		rectangleToShowInfo = new CustomRectangle(true, BLACK, DARK_BLACK, false, null);

		textAreaForInfo = new TextArea();
		textAreaForInfo.setEditable(false);
		textAreaForInfo.setWrapText(true);

		//BIG IMAGE STUFF -------------------------------------------------------------------------------------
		rectangleForBigImage = new CustomRectangle(true, BLACK, LIGHT_BLACK, true, CacheHint.SCALE);

		bigImage = new CustomImageView(false, true, true, true, CacheHint.SCALE);

		labelForBigImage = new CustomLabel(Pos.CENTER, null, WHITE, null, null, null, false, false, false, false, true, CacheHint.SCALE);

		previousInBigImageButton = new CustomButton(null, images.get(Images.BACK_ARROW), null, null, null, true, true, false, true, CacheHint.SCALE);
		nextInBigImageButton = new CustomButton(null, images.get(Images.BACK_ARROW), null, null, null, true, true, false, true, CacheHint.SCALE);
		nextInBigImageButton.setRotate(180);
		exitBigImageButton = new CustomButton(null, images.get(Images.X), null, null, null, true, true, false, true, CacheHint.SCALE);
		zoomInBigImageButton = new CustomButton(null, images.get(Images.ZOOM_IN), null, null, null, true, true, false, true, CacheHint.SCALE);
		zoomOutBigImageButton = new CustomButton(null, images.get(Images.ZOOM_OUT), null, null, null, true, true, false, true, CacheHint.SCALE);

		backButton = new CustomButton(null, images.get(Images.BACK_ARROW), ContentDisplay.TOP, null, CustomButton.COLOR, true, true,true, true, CacheHint.SCALE);

		nextInBigImageButtonShadow = new DropShadow();

		woodenFrameImage.setEffect(dropShadow);
		titleImage.setEffect(dropShadow);
		hBoxForToggleButtons.setEffect(dropShadow);
		backButton.setEffect(dropShadow);
		rectangleForBigImage.setEffect(dropShadow);
		optionsForCountriesAndContinentsComboBox.setEffect(dropShadow);
		listViewForCountriesAndContinents.setEffect(dropShadow);
		scrollPaneForGridPaneForCountriesAndContinents.setEffect(dropShadow);
		gridViewForImagesForCountriesAndContinents.setEffect(dropShadow);
		flagForCountriesImageSmall.setEffect(dropShadow);
		coatOfArmsForCountriesImageSmall.setEffect(dropShadow);
		locationForCountriesAndContinentsImageSmall.setEffect(dropShadow);
		optionsForUSAComboBox.setEffect(dropShadow);
		listViewForUSA.setEffect(dropShadow);
		scrollPaneForGridPaneForUSA.setEffect(dropShadow);
		gridViewForImagesForUSA.setEffect(dropShadow);
		flagForUSAImageSmall.setEffect(dropShadow);
		sealForUSAImageSmall.setEffect(dropShadow);
		locationForUSAImageSmall.setEffect(dropShadow);
		optionsForGreeceComboBox.setEffect(dropShadow);
		listViewForGreece.setEffect(dropShadow);
		gridPaneForGreece.setEffect(dropShadow);
		logoForGreeceImageSmall.setEffect(dropShadow);
		locationForGreeceImageSmall.setEffect(dropShadow);
		optionsForAttractionsComboBox.setEffect(dropShadow);
		listViewForAttractions.setEffect(dropShadow);
		gridPaneForAttractions.setEffect(dropShadow);
		scrollPaneForAttractionsBasicInfo.setEffect(dropShadow);
		attractionImageSmall.setEffect(dropShadow);
		attractionLocationImageSmall.setEffect(dropShadow);
		rectangleToShowInfo.setEffect(dropShadow);
		bigImage.setEffect(dropShadow);
		previousInBigImageButton.setEffect(dropShadow);
		nextInBigImageButton.setEffect(nextInBigImageButtonShadow);
		zoomOutBigImageButton.setEffect(dropShadow);
		zoomInBigImageButton.setEffect(dropShadow);
		exitBigImageButton.setEffect(dropShadow);

		hBoxMainForUSA.setVisible(false);
		hBoxMainForGreece.setVisible(false);
		hBoxMainForAttractions.setVisible(false);

		nodesPane.getChildren().addAll(previousChalkboardImage, movingEarthImage, woodPanelFor1IconImage,
				titleImage, titleLabel,
				hBoxForToggleButtons, hBoxMainForCountriesAndContinents, hBoxMainForUSA,
				hBoxMainForGreece, hBoxMainForAttractions, soundButton, vBoxForSound, backButton,
				rectangleForBigImage, bigImage, labelForBigImage, previousInBigImageButton, nextInBigImageButton,
				exitBigImageButton, zoomInBigImageButton, zoomOutBigImageButton, rectangleToShowInfo, textAreaForInfo);
		
		if(animationsUsed != ANIMATIONS.NO) setupLimitedAnimations();

		setupListeners();
	}

	protected void setupListeners()
	{
		toggleGroupForToggleButtons.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
		{
			if(newValue == null)
				Platform.runLater(() -> toggleGroupForToggleButtons.selectToggle(oldValue));
			else
			{
				if(oldValue != null)
				{
					hBoxForToggleButtons.setDisable(true);
					backButton.setDisable(true);
				}

				boolean leftMove = makeLeftMove(oldValue, newValue);

				if(animationsUsed != ANIMATIONS.NO)
				{
					if(oldValue != null) getAudioStuff().playSlideSound();

					if(oldValue == countriesAndContinentsToggleButton)
					{
						translateTransitionForHBoxMainForCountriesAndContinents.setToX(-1 * (hBoxMainForCountriesAndContinents.getLayoutX() + hBoxMainForCountriesAndContinents.getPrefWidth() + 20));
						translateTransitionForHBoxMainForCountriesAndContinents.setOnFinished(ev -> hBoxMainForCountriesAndContinents.setVisible(false));
						translateTransitionForHBoxMainForCountriesAndContinents.playFromStart();
					}
					else if(oldValue == USAToggleButton)
					{
						if(leftMove) translateTransitionForHBoxMainForUSA.setToX(-1 * (hBoxMainForUSA.getLayoutX() + hBoxMainForUSA.getPrefWidth() + 20));
						else translateTransitionForHBoxMainForUSA.setToX(mainScene.getWidth() - hBoxMainForUSA.getLayoutX() + 20);
						translateTransitionForHBoxMainForUSA.setOnFinished(ev -> hBoxMainForUSA.setVisible(false));
						translateTransitionForHBoxMainForUSA.playFromStart();
					}
					else if(oldValue == greeceToggleButton)
					{
						if(leftMove) translateTransitionForHBoxMainForGreece.setToX(-1 * (hBoxMainForGreece.getLayoutX() + hBoxMainForGreece.getPrefWidth() + 20));
						else translateTransitionForHBoxMainForGreece.setToX(mainScene.getWidth() - hBoxMainForGreece.getLayoutX() + 20);
						translateTransitionForHBoxMainForGreece.setOnFinished(ev -> hBoxMainForGreece.setVisible(false));
						translateTransitionForHBoxMainForGreece.playFromStart();
					}
					else if(oldValue == attractionsToggleButton)
					{
						translateTransitionForHBoxMainForAttractions.setToX(mainScene.getWidth() - hBoxMainForAttractions.getLayoutX() + 20);
						translateTransitionForHBoxMainForAttractions.setOnFinished(ev -> hBoxMainForAttractions.setVisible(false));
						translateTransitionForHBoxMainForAttractions.playFromStart();
					}

					if (toggleGroupForToggleButtons.getSelectedToggle() == countriesAndContinentsToggleButton)
					{
						countriesAndContinentsToggleButtonPressed();

						if(newValue == countriesAndContinentsToggleButton && !hBoxMainForCountriesAndContinents.isVisible())
						{
							hBoxMainForCountriesAndContinents.setTranslateX(-1 * (hBoxMainForCountriesAndContinents.getLayoutX() + hBoxMainForCountriesAndContinents.getPrefWidth() + 20));
							translateTransitionForHBoxMainForCountriesAndContinents.setToX(0);

							translateTransitionForHBoxMainForCountriesAndContinents.setOnFinished(evn ->
							{
								hBoxForToggleButtons.setDisable(false);
								backButton.setDisable(false);
							});

							hBoxMainForCountriesAndContinents.setVisible(true);
							translateTransitionForHBoxMainForCountriesAndContinents.playFromStart();
						}
					}
					else if (toggleGroupForToggleButtons.getSelectedToggle() == USAToggleButton)
					{
						usaStatesToggleButtonPressed();

						if(newValue == USAToggleButton && !hBoxMainForUSA.isVisible())
						{
							if(leftMove) hBoxMainForUSA.setTranslateX(mainScene.getWidth() - hBoxMainForUSA.getLayoutX() + 20);
							else hBoxMainForUSA.setTranslateX(-1 * (hBoxMainForUSA.getLayoutX() + hBoxMainForUSA.getPrefWidth() + 20));
							translateTransitionForHBoxMainForUSA.setToX(0);

							translateTransitionForHBoxMainForUSA.setOnFinished(evn ->
							{
								hBoxForToggleButtons.setDisable(false);
								backButton.setDisable(false);
							});

							hBoxMainForUSA.setVisible(true);
							translateTransitionForHBoxMainForUSA.playFromStart();
						}
					}
					else if (toggleGroupForToggleButtons.getSelectedToggle() == greeceToggleButton)
					{
						greeceToggleButtonPressed();

						if(newValue == greeceToggleButton && !hBoxMainForGreece.isVisible())
						{
							if(leftMove) hBoxMainForGreece.setTranslateX(mainScene.getWidth() - hBoxMainForGreece.getLayoutX() + 20);
							else hBoxMainForGreece.setTranslateX(-1 * (hBoxMainForGreece.getLayoutX() + hBoxMainForGreece.getPrefWidth() + 20));
							translateTransitionForHBoxMainForGreece.setToX(0);

							translateTransitionForHBoxMainForGreece.setOnFinished(evn ->
							{
								hBoxForToggleButtons.setDisable(false);
								backButton.setDisable(false);
							});

							hBoxMainForGreece.setVisible(true);
							translateTransitionForHBoxMainForGreece.playFromStart();
						}
					}
					else if (toggleGroupForToggleButtons.getSelectedToggle() == attractionsToggleButton)
					{
						attractionsToggleButtonPressed();

						if(newValue == attractionsToggleButton && !hBoxMainForAttractions.isVisible())
						{
							hBoxMainForAttractions.setTranslateX(mainScene.getWidth() - hBoxMainForAttractions.getLayoutX() + 20);
							translateTransitionForHBoxMainForAttractions.setToX(0);

							translateTransitionForHBoxMainForAttractions.setOnFinished(evn ->
							{
								hBoxForToggleButtons.setDisable(false);
								backButton.setDisable(false);
							});

							hBoxMainForAttractions.setVisible(true);
							translateTransitionForHBoxMainForAttractions.playFromStart();
						}
					}
				}
				else
				{
					if(oldValue == countriesAndContinentsToggleButton)
					{
						hBoxMainForCountriesAndContinents.setTranslateX(-1 * (hBoxMainForCountriesAndContinents.getLayoutX() + hBoxMainForCountriesAndContinents.getPrefWidth() + 20));
						hBoxMainForCountriesAndContinents.setVisible(false);
					}
					else if(oldValue == USAToggleButton)
					{
						if(leftMove) hBoxMainForUSA.setTranslateX(-1 * (hBoxMainForUSA.getLayoutX() + hBoxMainForUSA.getPrefWidth() + 20));
						else hBoxMainForUSA.setTranslateX(mainScene.getWidth() - hBoxMainForUSA.getLayoutX() + 20);
						hBoxMainForUSA.setVisible(false);
					}
					else if(oldValue == greeceToggleButton)
					{
						if(leftMove) hBoxMainForGreece.setTranslateX(-1 * (hBoxMainForGreece.getLayoutX() + hBoxMainForGreece.getPrefWidth() + 20));
						else hBoxMainForGreece.setTranslateX(mainScene.getWidth() - hBoxMainForGreece.getLayoutX() + 20);
						hBoxMainForGreece.setVisible(false);
					}
					else if(oldValue == attractionsToggleButton)
					{
						hBoxMainForAttractions.setTranslateX(mainScene.getWidth() - hBoxMainForAttractions.getLayoutX() + 20);
						hBoxMainForAttractions.setVisible(false);
					}

					if (toggleGroupForToggleButtons.getSelectedToggle() == countriesAndContinentsToggleButton)
					{
						countriesAndContinentsToggleButtonPressed();

						hBoxMainForCountriesAndContinents.setTranslateX(0);
						hBoxMainForCountriesAndContinents.setVisible(true);

						hBoxForToggleButtons.setDisable(false);
						backButton.setDisable(false);
					}
					else if (toggleGroupForToggleButtons.getSelectedToggle() == USAToggleButton)
					{
						usaStatesToggleButtonPressed();

						hBoxMainForUSA.setTranslateX(0);
						hBoxMainForUSA.setVisible(true);

						hBoxForToggleButtons.setDisable(false);
						backButton.setDisable(false);
					}
					else if (toggleGroupForToggleButtons.getSelectedToggle() == greeceToggleButton)
					{
						greeceToggleButtonPressed();

						hBoxMainForGreece.setTranslateX(0);
						hBoxMainForGreece.setVisible(true);

						hBoxForToggleButtons.setDisable(false);
						backButton.setDisable(false);
					}
					else if(toggleGroupForToggleButtons.getSelectedToggle() == attractionsToggleButton)
					{
						attractionsToggleButtonPressed();

						hBoxMainForAttractions.setTranslateX(0);
						hBoxMainForAttractions.setVisible(true);

						hBoxForToggleButtons.setDisable(false);
						backButton.setDisable(false);
					}
				}
			}
		});

		optionsForCountriesAndContinentsComboBox.valueProperty().addListener((observable, oldValue, newValue) ->
		{
			nodesPane.requestFocus();

			if(oldValue != null && newValue != null)
			{
				setIndexInOptionsForCountriesAndContinents(optionsForCountriesAndContinentsComboBox.getSelectionModel().getSelectedIndex());
				if (animationsUsed != ANIMATIONS.NO)
				{
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

							hBoxForToggleButtons.setDisable(false);
							backButton.setDisable(false);
						});

						getAudioStuff().playMaximizeSound();
						scaleTransitionForHBoxMainForCountriesAndContinents.playFromStart();
					});
					getAudioStuff().playMinimizeSound();
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

							hBoxForToggleButtons.setDisable(false);
							backButton.setDisable(false);
						});

						getAudioStuff().playMaximizeSound();
						scaleTransitionForHBoxMainForUSA.playFromStart();
					});
					getAudioStuff().playMinimizeSound();
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

							hBoxForToggleButtons.setDisable(false);
							backButton.setDisable(false);
						});

						getAudioStuff().playMaximizeSound();
						scaleTransitionForHBoxMainForGreece.playFromStart();
					});
					getAudioStuff().playMinimizeSound();
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

							hBoxForToggleButtons.setDisable(false);
							backButton.setDisable(false);
						});

						getAudioStuff().playMaximizeSound();
						scaleTransitionForHBoxMainForAttractions.playFromStart();
					});
					getAudioStuff().playMinimizeSound();
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
					if(bigImageStatus == 0 && flagForCountriesImageSmall.getImage() != null)
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
					if(bigImageStatus == 0 && coatOfArmsForCountriesImageSmall.getImage() != null)
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
			if(flagForCountriesImageSmall.getImage() != null)
			{
				setIndexInBigImageNormal(getIndexInListViewForCountriesAndContinents());
				if(getIndexInOptionsForCountriesAndContinents() == 0)
					setupBigImageNormal(BigImageType.FLAG_FOR_COUNTRIES, countriesSortList.get(getIndexInListViewForCountriesAndContinents()));
				else if(getIndexInOptionsForCountriesAndContinents() == 1)
					setupBigImageNormal(BigImageType.FLAG_FOR_COUNTRIES, capitalsOfCountriesSortList.get(getIndexInListViewForCountriesAndContinents()));
			}
		});

		flagLabelForCountriesAndContinents.setOnMouseExited(e ->
		{
			if(bigImageStatus == 1 && flagForCountriesImageSmall.getImage() != null)
			{
				if(e.getSceneX() < flagLabelForCountriesAndContinents.localToScene(flagLabelForCountriesAndContinents.getBoundsInLocal()).getMinX()
				   || e.getSceneX() > flagLabelForCountriesAndContinents.localToScene(flagLabelForCountriesAndContinents.getBoundsInLocal()).getMaxX()
				   || e.getSceneY() < flagLabelForCountriesAndContinents.localToScene(flagLabelForCountriesAndContinents.getBoundsInLocal()).getMinY()
				   || e.getSceneY() > flagLabelForCountriesAndContinents.localToScene(flagLabelForCountriesAndContinents.getBoundsInLocal()).getMaxY())
					closeBigImage();
			}
		});

		coatOfArmsLabelForCountriesAndContinents.setOnMouseClicked(e ->
		{
			if(coatOfArmsForCountriesImageSmall.getImage() != null)
			{
				setIndexInBigImageNormal(getIndexInListViewForCountriesAndContinents());
				if(getIndexInOptionsForCountriesAndContinents() == 0)
					setupBigImageNormal(BigImageType.COAT_OF_ARMS_FOR_COUNTRIES, countriesSortList.get(getIndexInListViewForCountriesAndContinents()));
				else if(getIndexInOptionsForCountriesAndContinents() == 1)
					setupBigImageNormal(BigImageType.COAT_OF_ARMS_FOR_COUNTRIES, capitalsOfCountriesSortList.get(getIndexInListViewForCountriesAndContinents()));
			}
		});

		coatOfArmsLabelForCountriesAndContinents.setOnMouseExited(e ->
		{
			if(bigImageStatus == 1 && coatOfArmsForCountriesImageSmall.getImage() != null)
			{
				if(e.getSceneX() < coatOfArmsForCountriesImageSmall.localToScene(coatOfArmsForCountriesImageSmall.getBoundsInLocal()).getMinX()
				   || e.getSceneX() + 5 > coatOfArmsForCountriesImageSmall.localToScene(coatOfArmsForCountriesImageSmall.getBoundsInLocal()).getMaxX()
				   || e.getSceneY() < coatOfArmsForCountriesImageSmall.localToScene(coatOfArmsForCountriesImageSmall.getBoundsInLocal()).getMinY()
				   || e.getSceneY() > coatOfArmsForCountriesImageSmall.localToScene(coatOfArmsForCountriesImageSmall.getBoundsInLocal()).getMaxY())
					closeBigImage();
			}
		});

		locationLabelForCountriesAndContinents.setOnMouseClicked(e ->
		{
			if(locationForCountriesAndContinentsImageSmall.getImage() != null)
			{
				setIndexInBigImageNormal(getIndexInListViewForCountriesAndContinents());
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
						sb.append(s).append(", ");
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
					else gridPaneLabelsForCountriesAndContinents[10][1].setText(countries[index].getArea().getPercentOfWater() + "%");

					gridPaneLabelsForCountriesAndContinents[12][1].setText(countries[index].getPopulation().getPopulationDensityBasedOnLocaleToString());

					gridPaneLabelsForCountriesAndContinents[13][1].setText(
							countries[index].getCurrency().getName() + " (" + countries[index].getCurrency().getSymbol() + ") - " +
							countries[index].getCurrency().getISOCode());

					if (getCurrentLanguage() == LANGUAGE.GREEK)
					{
						gridPaneLabelsForCountriesAndContinents[0][1].setText(countries[index].getNameInGreek());

						if (countries[index].isSovereignState()) gridPaneLabelsForCountriesAndContinents[3][1].setText("Ναι");
						else gridPaneLabelsForCountriesAndContinents[3][1].setText(countries[index].getSovereignState());

						if (countries[index].isIslandCountry()) gridPaneLabelsForCountriesAndContinents[6][1].setText("Είναι νησιωτικό κράτος");
						else if (countries[index].hasSea()) gridPaneLabelsForCountriesAndContinents[6][1].setText("Βρέχεται από θάλασσα");
						else gridPaneLabelsForCountriesAndContinents[6][1].setText("Δε βρέχεται από θάλασσα");

						if (countries[index].getArea().getGlobalRanking() != 0) sb.append(" (").append(countries[index].getArea().getGlobalRanking()).append("η)");
						gridPaneLabelsForCountriesAndContinents[7][1].setText(sb.toString());

						sb = new StringBuilder();
						sb.append(numberFormatForUI.format(countries[index].getPopulation().getPopulation())).append(" κάτοικοι");
						if (countries[index].getPopulation().getGlobalRanking() != 0) sb.append(" (").append(countries[index].getPopulation().getGlobalRanking()).append("η)");
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
							sb.append(" (").append(countries[index].getArea().getGlobalRanking());
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
							sb.append(" (").append(countries[index].getPopulation().getGlobalRanking());
							if (countries[index].getPopulation().getGlobalRanking() % 10 == 1) sb.append("st)");
							else if (countries[index].getPopulation().getGlobalRanking() % 10 == 2) sb.append("nd)");
							else if (countries[index].getPopulation().getGlobalRanking() % 10 == 3) sb.append("rd)");
							else sb.append("th)");
						}
						gridPaneLabelsForCountriesAndContinents[11][1].setText(sb.toString());

						sb = new StringBuilder();
						sb.append(countries[index].getCurrency().getSubdivision());
						if (sb.length() < 20) sb.append(" (").append(countries[index].getCurrency().getNumberOfSubDivisions()).append(")");
						gridPaneLabelsForCountriesAndContinents[14][1].setText(sb.toString());
					}
					for(CustomLabel[] gridPaneLabelForCountriesAndContinents : gridPaneLabelsForCountriesAndContinents)
						gridPaneLabelForCountriesAndContinents[1].getTooltip().setText(gridPaneLabelForCountriesAndContinents[1].getText());

					//SET IMAGES
					if(flagForCountriesImageSmall.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X250_IMAGES)
					{
						flagForCountriesImageSmall.setImage(getImageStuff().getImage(ImageType.COUNTRY_FLAG, "x250", countries[index].getNameInEnglish(), true));
						coatOfArmsForCountriesImageSmall.setImage(getImageStuff().getImage(ImageType.COUNTRY_COAT_OF_ARMS, "x250", countries[index].getNameInEnglish(), true));
					}
					else
					{
						flagForCountriesImageSmall.setImage(getImageStuff().getImage(ImageType.COUNTRY_FLAG, "x500", countries[index].getNameInEnglish(), true));
						coatOfArmsForCountriesImageSmall.setImage(getImageStuff().getImage(ImageType.COUNTRY_COAT_OF_ARMS, "x500", countries[index].getNameInEnglish(), true));
					}
					if(locationForCountriesAndContinentsImageSmall.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X250_IMAGES)
						locationForCountriesAndContinentsImageSmall.setImage(getImageStuff().getImage(ImageType.COUNTRY_LOCATION, "x250", countries[index].getNameInEnglish(), true));
					else if(locationForCountriesAndContinentsImageSmall.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X500_IMAGES)
						locationForCountriesAndContinentsImageSmall.setImage(getImageStuff().getImage(ImageType.COUNTRY_LOCATION, "x500", countries[index].getNameInEnglish(), true));
					else locationForCountriesAndContinentsImageSmall.setImage(getImageStuff().getImage(ImageType.COUNTRY_LOCATION, "x1000", countries[index].getNameInEnglish(), true));
				}
				else if(getIndexInOptionsForCountriesAndContinents() == 5 && newValue.intValue() < Continent.NUMBER_OF_CONTINENTS)
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
						gridPaneLabelsForCountriesAndContinents[8][1].setText(continents[index].getAreaBasedOnLocaleToString() + " (" + continents[index].getGlobalAreaRanking() + "η)");

						gridPaneLabelsForCountriesAndContinents[9][1].setText(continents[index].getCoastlineBasedOnLocaleToString());

						gridPaneLabelsForCountriesAndContinents[10][1].setText(continents[index].getPercentOfEarth() + "%");
						gridPaneLabelsForCountriesAndContinents[11][1].setText(continents[index].getPercentOfLandOfEarth() + "%");

						gridPaneLabelsForCountriesAndContinents[12][1].setText(continents[index].getHighestPoint());
						gridPaneLabelsForCountriesAndContinents[13][1].setText(continents[index].getLowestPoint());
						gridPaneLabelsForCountriesAndContinents[14][1].setText("-");
						gridPaneLabelsForCountriesAndContinents[15][1].setText("-");
						gridPaneLabelsForCountriesAndContinents[16][1].setText(continents[index].getTimeZones());

						for(CustomLabel[] gridPaneLabelForCountriesAndContinents : gridPaneLabelsForCountriesAndContinents)
							gridPaneLabelForCountriesAndContinents[1].getTooltip().setText(gridPaneLabelForCountriesAndContinents[1].getText());
					}
					else
					{
                        gridPaneLabelsForCountriesAndContinents[2][1].setCursor(Cursor.HAND);
                        gridPaneLabelsForCountriesAndContinents[3][1].setCursor(Cursor.HAND);
                        gridPaneLabelsForCountriesAndContinents[4][1].setCursor(Cursor.HAND);

                        gridPaneLabelsForCountriesAndContinents[2][1].getTooltip().setText(null);
                        gridPaneLabelsForCountriesAndContinents[3][1].getTooltip().setText(null);
                        gridPaneLabelsForCountriesAndContinents[4][1].getTooltip().setText(null);

						gridPaneLabelsForCountriesAndContinents[1][1].setText(continents[index].getNumberOfCountries());

						gridPaneLabelsForCountriesAndContinents[5][1].setText(continents[index].getLanguages());

						gridPaneLabelsForCountriesAndContinents[7][1].setText(continents[index].getPopulationDensityBasedOnLocaleToString());

						gridPaneLabelsForCountriesAndContinents[9][1].setText(continents[index].getCoastlineBasedOnLocaleToString());

						gridPaneLabelsForCountriesAndContinents[10][1].setText(continents[index].getPercentOfEarth() + "%");
						gridPaneLabelsForCountriesAndContinents[11][1].setText(continents[index].getPercentOfLandOfEarth() + "%");

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

							gridPaneLabelsForCountriesAndContinents[6][1].setText(numberFormatForUI.format(continents[index].getPopulation()) + " κάτοικοι (" + continents[index].getGlobalPopulationRanking() + "η)");

							gridPaneLabelsForCountriesAndContinents[8][1].setText(continents[index].getAreaBasedOnLocaleToString() + " (" + continents[index].getGlobalAreaRanking() + "η)");
						}
						else
						{
							gridPaneLabelsForCountriesAndContinents[0][1].setText(continents[index].getNameInEnglish());

                            gridPaneLabelsForCountriesAndContinents[2][1].setText("Click here to see them");
                            gridPaneLabelsForCountriesAndContinents[3][1].setText("Click here to see them");
                            gridPaneLabelsForCountriesAndContinents[4][1].setText("Click here to see them");

                            StringBuilder sb;

							sb = new StringBuilder();
							sb.append(numberFormatForUI.format(continents[index].getPopulation())).append(" inhabitants (").append(continents[index].getGlobalPopulationRanking());
							if (continents[index].getGlobalPopulationRanking() == 1) sb.append("st)");
							else if (continents[index].getGlobalPopulationRanking() == 2) sb.append("nd)");
							else if (continents[index].getGlobalPopulationRanking() == 3) sb.append("rd)");
							else sb.append("th)");
							gridPaneLabelsForCountriesAndContinents[6][1].setText(sb.toString());

							sb = new StringBuilder();
							sb.append(continents[index].getAreaBasedOnLocaleToString()).append(" (").append(continents[index].getGlobalAreaRanking());
							if (continents[index].getGlobalAreaRanking() == 1) sb.append("st)");
							else if (continents[index].getGlobalAreaRanking() == 2) sb.append("nd)");
							else if (continents[index].getGlobalAreaRanking() == 3) sb.append("rd)");
							else sb.append("th)");
							gridPaneLabelsForCountriesAndContinents[8][1].setText(sb.toString());
						}
						for(CustomLabel[] gridPaneLabelForCountriesAndContinents : gridPaneLabelsForCountriesAndContinents)
							gridPaneLabelForCountriesAndContinents[1].getTooltip().setText(gridPaneLabelForCountriesAndContinents[1].getText());
					}

					if (locationLabelForCountriesAndContinents.getPrefWidth() <= getImageStuff().MAX_WIDTH_FOR_X250_IMAGES)
						locationForCountriesAndContinentsImageSmall.setImage(getImageStuff().getImage(ImageType.CONTINENT_LOCATION, "x250", continents[index].getNameInEnglish(), true));
					else if (locationLabelForCountriesAndContinents.getPrefWidth() <= getImageStuff().MAX_WIDTH_FOR_X500_IMAGES)
						locationForCountriesAndContinentsImageSmall.setImage(getImageStuff().getImage(ImageType.CONTINENT_LOCATION, "x500", continents[index].getNameInEnglish(), true));
					else locationForCountriesAndContinentsImageSmall.setImage(getImageStuff().getImage(ImageType.CONTINENT_LOCATION, "x1000", continents[index].getNameInEnglish(), true));
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
				setIndexInBigImageNormal(getIndexInListViewForUSA());
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
				setIndexInBigImageNormal(getIndexInListViewForUSA());
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
				setIndexInBigImageNormal(getIndexInListViewForUSA());
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
				     newValue.intValue() < StateOfUSA.NUMBER_OF_USA_STATES)
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

					gridPaneLabelsForUSA[10][1].setText(statesOfUSA[index].getLandAreaBasedOnLocaleToString() + " (" + statesOfUSA[index].getLandPercent() + "%)");
					gridPaneLabelsForUSA[11][1].setText(statesOfUSA[index].getWaterAreaBasedOnLocaleToString() + " (" + statesOfUSA[index].getWaterPercent() + "%)");

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

						gridPaneLabelsForUSA[7][1].setText(numberFormatForUI.format(statesOfUSA[index].getPopulation()) + " κάτοικοι (" + statesOfUSA[index].getPopulationRank() + "η)");

						gridPaneLabelsForUSA[9][1].setText(statesOfUSA[index].getTotalAreaBasedOnLocaleToString() + " (" + statesOfUSA[index].getAreaRanking() + "η)");

						if(statesOfUSA[index].hasAccessToTheOcean()) gridPaneLabelsForUSA[13][1].setText("Ναι");
						else gridPaneLabelsForUSA[13][1].setText("Όχι");
					}
					else
					{
						gridPaneLabelsForUSA[0][1].setText(statesOfUSA[index].getNameInEnglish() + " (" + statesOfUSA[index].getAbbreviation() + ")");

						gridPaneLabelsForUSA[2][1].setText("Click here to see them");

						StringBuilder sb = new StringBuilder();
						sb.append(numberFormatForUI.format(statesOfUSA[index].getPopulation())).append(" inhabitants (").append(statesOfUSA[index].getPopulationRank());
						if (statesOfUSA[index].getPopulationRank() == 1) sb.append("st)");
						else if (statesOfUSA[index].getPopulationRank() == 2) sb.append("nd)");
						else if (statesOfUSA[index].getPopulationRank() == 3) sb.append("rd)");
						else sb.append("th)");
						gridPaneLabelsForUSA[7][1].setText(sb.toString());

						sb = new StringBuilder();
						sb.append(statesOfUSA[index].getTotalAreaBasedOnLocaleToString()).append(" (").append(statesOfUSA[index].getAreaRanking());
						if (statesOfUSA[index].getAreaRanking() == 1) sb.append("st)");
						else if (statesOfUSA[index].getAreaRanking() == 2) sb.append("nd)");
						else if (statesOfUSA[index].getAreaRanking() == 3) sb.append("rd)");
						else sb.append("th)");
						gridPaneLabelsForUSA[9][1].setText(sb.toString());

						if(statesOfUSA[index].hasAccessToTheOcean()) gridPaneLabelsForUSA[13][1].setText("Yes");
						else gridPaneLabelsForUSA[13][1].setText("No");
					}
					//SET IMAGES
					if(flagForUSAImageSmall.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X250_IMAGES)
					{
						flagForUSAImageSmall.setImage(getImageStuff().getImage(ImageType.USA_FLAG, "x250", statesOfUSA[index].getNameInEnglish(), true));
						sealForUSAImageSmall.setImage(getImageStuff().getImage(ImageType.USA_SEAL, "x250", statesOfUSA[index].getNameInEnglish(), true));
					}
					else
					{
						flagForUSAImageSmall.setImage(getImageStuff().getImage(ImageType.USA_FLAG, "x500", statesOfUSA[index].getNameInEnglish(), true));
						sealForUSAImageSmall.setImage(getImageStuff().getImage(ImageType.USA_SEAL, "x500", statesOfUSA[index].getNameInEnglish(), true));
					}
					if(locationForUSAImageSmall.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X250_IMAGES)
						locationForUSAImageSmall.setImage(getImageStuff().getImage(ImageType.USA_LOCATION, "x250", statesOfUSA[index].getNameInEnglish(), true));
					else if(locationForUSAImageSmall.getFitHeight() <= getImageStuff().MAX_WIDTH_FOR_X500_IMAGES)
						locationForUSAImageSmall.setImage(getImageStuff().getImage(ImageType.USA_LOCATION, "x500", statesOfUSA[index].getNameInEnglish(), true));
					else
						locationForUSAImageSmall.setImage(getImageStuff().getImage(ImageType.USA_LOCATION, "x1000", statesOfUSA[index].getNameInEnglish(), true));
				}

				for(CustomLabel[] gridPaneLabelForUSA : gridPaneLabelsForUSA) gridPaneLabelForUSA[1].getTooltip().setText(gridPaneLabelForUSA[1].getText());
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
					if(bigImageStatus == 0 && locationForGreeceImageSmall.getImage() != null)
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
				setIndexInBigImageNormal(getIndexInListViewForGreece());
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
			if(locationForGreeceImageSmall.getImage() != null)
			{
				setIndexInBigImageNormal(getIndexInListViewForGreece());
				setIndexInBigImageNormal(getIndexInListViewForGreece());
				if(getIndexInOptionsForGreece() == 0)
					setupBigImageNormal(BigImageType.LOCATION_FOR_GREEK_DEC_ADMIN, greekDecentralizedAdministrationsSortList.get(getIndexInListViewForGreece()));
				else if(getIndexInOptionsForGreece() == 1)
					setupBigImageNormal(BigImageType.LOCATION_FOR_GREEK_REGIONS, greekRegionsSortList.get(getIndexInListViewForGreece()));
			}
		});

		locationLabelForGreece.setOnMouseExited(e ->
		{
			if (bigImageStatus == 1 && locationForGreeceImageSmall.getImage() != null)
			{
				if(e.getSceneX() < locationForGreeceImageSmall.localToScene(locationForGreeceImageSmall.getBoundsInLocal()).getMinX()
				   || e.getSceneX() > locationForGreeceImageSmall.localToScene(locationForGreeceImageSmall.getBoundsInLocal()).getMaxX()
				   || e.getSceneY() < locationForGreeceImageSmall.localToScene(locationForGreeceImageSmall.getBoundsInLocal()).getMinY()
				   || e.getSceneY() > locationForGreeceImageSmall.localToScene(locationForGreeceImageSmall.getBoundsInLocal()).getMaxY())
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
				if (e.isPrimaryButtonDown())
				{
					if(getCurrentLanguage() == LANGUAGE.GREEK) UrlOpener.openGreekWikiURL(gridPaneLabelsForGreece[6][1].getText());
					else UrlOpener.openURL(gridPaneLabelsForGreece[6][1].getText());
				}
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
					if(e.isPrimaryButtonDown())
					{
						if(getCurrentLanguage() == LANGUAGE.GREEK && getIndexInOptionsForGreece() == 1) UrlOpener.openGreekWikiURL(gridPaneLabelsForGreece[10][1].getText());
						else UrlOpener.openURL(gridPaneLabelsForGreece[10][1].getText());
					}
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
					if (e.isPrimaryButtonDown())
					{
						if(getCurrentLanguage() == LANGUAGE.GREEK) UrlOpener.openGreekWikiURL(gridPaneLabelsForGreece[11][1].getText());
						else UrlOpener.openURL(gridPaneLabelsForGreece[11][1].getText());
					}
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

				if (getIndexInOptionsForGreece() == 0 && newValue.intValue() < GreekDecentralizedAdministration.NUMBER_OF_GREEK_DECENTRALIZED_ADMINISTRATIONS)
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

						gridPaneLabelsForGreece[3][1].setText(numberFormatForUI.format(greekDecAdm[index].getPopulation()) + " κάτοικοι");

						gridPaneLabelsForGreece[7][1].setText("(" + greekDecAdm[index].getNumberOfRegions() + ") Κάνε κλικ για να τις δεις");
						gridPaneLabelsForGreece[8][1].setText("(" + greekDecAdm[index].getNumberOfRegionalUnits() + ") Κάνε κλικ για να τις δεις");
						gridPaneLabelsForGreece[9][1].setText("(" + greekDecAdm[index].getNumberOfMunicipalities() + ") Κάνε κλικ για να τους δεις");
					}
					else
					{
						gridPaneLabelsForGreece[0][1].setText(greekDecAdm[index].getNameInEnglish());

						gridPaneLabelsForGreece[3][1].setText(numberFormatForUI.format(greekDecAdm[index].getPopulation()) + " inhabitants");

						gridPaneLabelsForGreece[7][1].setText("(" + greekDecAdm[index].getNumberOfRegions() + ") Click here to see them");
						gridPaneLabelsForGreece[8][1].setText("(" + greekDecAdm[index].getNumberOfRegionalUnits() + ") Click here to see them");
						gridPaneLabelsForGreece[9][1].setText("(" + greekDecAdm[index].getNumberOfMunicipalities() + ") Click here to see them");
					}

					//Images

					logoForGreeceImageSmall.setImage(getImageStuff().getImage(ImageType.GREECE_DEC_ADM_LOGO, "", greekDecAdm[index].getNameInEnglish(), true));

					if (locationForGreeceImageSmall.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X250_IMAGES)
						locationForGreeceImageSmall.setImage(getImageStuff().getImage(ImageType.GREECE_DEC_ADM_LOCATION, "x250", greekDecAdm[index].getNameInEnglish(), true));
					else if (locationForGreeceImageSmall.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X500_IMAGES)
						locationForGreeceImageSmall.setImage(getImageStuff().getImage(ImageType.GREECE_DEC_ADM_LOCATION, "x500", greekDecAdm[index].getNameInEnglish(), true));
					else locationForGreeceImageSmall.setImage(getImageStuff().getImage(ImageType.GREECE_DEC_ADM_LOCATION, "x1000", greekDecAdm[index].getNameInEnglish(), true));

					//Tooltips
					for(int i = 0; i < 5; i++) gridPaneLabelsForGreece[i][1].getTooltip().setText(gridPaneLabelsForGreece[i][1].getText());
				}
				else if(getIndexInOptionsForGreece() == 1 && newValue.intValue() < GreekRegion.NUMBER_OF_GREEK_REGIONS)
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

						gridPaneLabelsForGreece[5][1].setText(numberFormatForUI.format(greekRegions[index].getPopulation()) + " κάτοικοι (" + greekRegions[index].getPopulationRanking() + "η)");

						gridPaneLabelsForGreece[6][1].setText(greekRegions[index].getPopulationDensityPerSquareKilometer() + " κατ./km\u00B2, " + greekRegions[index].getPopulationDensityPerSquareMile() + " κατ./ sq mi");

						gridPaneLabelsForGreece[7][1].setText(greekRegions[index].getAreaBasedOnLocaleToString() + " (" + greekRegions[index].getAreaRanking() + "η)");

						gridPaneLabelsForGreece[11][1].setText("(" + greekRegions[index].getNumberOfRegionalUnits() + ") Κάνε κλικ για να τις δεις");
						gridPaneLabelsForGreece[12][1].setText("(" + greekRegions[index].getNumberOfMunicipalities() + ") Κάνε κλικ για να τους δεις");
					}
					else
					{
						gridPaneLabelsForGreece[0][1].setText(greekRegions[index].getNameInEnglish());

						StringBuilder sb;

						sb = new StringBuilder();
						sb.append(numberFormatForUI.format(greekRegions[index].getPopulation())).append(" inhabitants (").append(greekRegions[index].getPopulationRanking());
						if (greekRegions[index].getPopulationRanking() % 10 == 1) sb.append("st)");
						else if (greekRegions[index].getPopulationRanking() % 10 == 2) sb.append("nd)");
						else if (greekRegions[index].getPopulationRanking() % 10 == 3) sb.append("rd)");
						else sb.append("th)");
						gridPaneLabelsForGreece[5][1].setText(sb.toString());

						gridPaneLabelsForGreece[6][1].setText(greekRegions[index].getPopulationDensityPerSquareKilometer() + " inh./km\u00B2, " + greekRegions[index].getPopulationDensityPerSquareMile() + " inh./ sq mi");

						sb = new StringBuilder();
						sb.append(greekRegions[index].getAreaBasedOnLocaleToString());
						sb.append(" (").append(greekRegions[index].getAreaRanking());
						if (greekRegions[index].getAreaRanking() % 10 == 1) sb.append("st)");
						else if (greekRegions[index].getAreaRanking() % 10 == 2) sb.append("nd)");
						else if (greekRegions[index].getAreaRanking() % 10 == 3) sb.append("rd)");
						else sb.append("th)");
						gridPaneLabelsForGreece[7][1].setText(sb.toString());

						gridPaneLabelsForGreece[11][1].setText("(" + greekRegions[index].getNumberOfRegionalUnits() + ") Click here to see them");
						gridPaneLabelsForGreece[12][1].setText("(" + greekRegions[index].getNumberOfMunicipalities() + ") Click here to see them");
					}

					//Images
					logoForGreeceImageSmall.setImage(getImageStuff().getImage(ImageType.GREECE_REGION_LOGO, "", greekRegions[index].getNameInEnglish(), true));

					if (locationForGreeceImageSmall.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X250_IMAGES)
						locationForGreeceImageSmall.setImage(getImageStuff().getImage(ImageType.GREECE_REGION_LOCATION, "x250", greekRegions[index].getNameInEnglish(), true));
					else if (locationForGreeceImageSmall.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X500_IMAGES)
						locationForGreeceImageSmall.setImage(getImageStuff().getImage(ImageType.GREECE_REGION_LOCATION, "x500", greekRegions[index].getNameInEnglish(), true));
					else locationForGreeceImageSmall.setImage(getImageStuff().getImage(ImageType.GREECE_REGION_LOCATION, "x1000", greekRegions[index].getNameInEnglish(), true));

					//Tooltips
					for(int i = 0; i < 9; i++) gridPaneLabelsForGreece[i][1].getTooltip().setText(gridPaneLabelsForGreece[i][1].getText());
				}
				else if(getIndexInOptionsForGreece() == 2 && newValue.intValue() < GreekRegionalUnit.NUMBER_OF_GREEK_REGIONAL_UNITS)
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

						gridPaneLabelsForGreece[6][1].setText(numberFormatForUI.format(greekRegionalUnits[index].getPopulation()) + " κάτοικοι (" + greekRegionalUnits[index].getPopulationRanking() + "η)");

						gridPaneLabelsForGreece[7][1].setText(greekRegionalUnits[index].getPopulationDensityPerSquareKilometer() + " κατ./km\u00B2, " + greekRegionalUnits[index].getPopulationDensityPerSquareMile() + " κατ./ sq mi");

						gridPaneLabelsForGreece[8][1].setText(greekRegionalUnits[index].getAreaBasedOnLocaleToString() + " (" + greekRegionalUnits[index].getAreaRanking() + "η)");

						gridPaneLabelsForGreece[12][1].setText("(" + greekRegionalUnits[index].getNumberOfMunicipalities() + ") Κάνε κλικ για να τους δεις");
					}
					else
					{
						gridPaneLabelsForGreece[0][1].setText(greekRegionalUnits[index].getNameInEnglish());

						StringBuilder sb;

						sb = new StringBuilder();
						sb.append(numberFormatForUI.format(greekRegionalUnits[index].getPopulation())).append(" inhabitants (").append(greekRegionalUnits[index].getPopulationRanking());
						if (greekRegionalUnits[index].getPopulationRanking() % 10 == 1) sb.append("st)");
						else if (greekRegionalUnits[index].getPopulationRanking() % 10 == 2) sb.append("nd)");
						else if (greekRegionalUnits[index].getPopulationRanking() % 10 == 3) sb.append("rd)");
						else sb.append("th)");
						gridPaneLabelsForGreece[6][1].setText(sb.toString());

						gridPaneLabelsForGreece[7][1].setText(greekRegionalUnits[index].getPopulationDensityPerSquareKilometer() + " inh./km\u00B2, " + greekRegionalUnits[index].getPopulationDensityPerSquareMile() + " inh./ sq mi");

						sb = new StringBuilder();
						sb.append(greekRegionalUnits[index].getAreaBasedOnLocaleToString());
						sb.append(" (").append(greekRegionalUnits[index].getAreaRanking());
						if (greekRegionalUnits[index].getAreaRanking() % 10 == 1) sb.append("st)");
						else if (greekRegionalUnits[index].getAreaRanking() % 10 == 2) sb.append("nd)");
						else if (greekRegionalUnits[index].getAreaRanking() % 10 == 3) sb.append("rd)");
						else sb.append("th)");
						gridPaneLabelsForGreece[8][1].setText(sb.toString());

						gridPaneLabelsForGreece[12][1].setText("(" + greekRegionalUnits[index].getNumberOfMunicipalities() + ") Click here to see them");
					}

					//Images
					logoForGreeceImageSmall.setImage(null);
					locationForGreeceImageSmall.setImage(null);

					//							logoForGreeceImageSmall.setImage(new Image("/images/greece/regions/logos/" + greekRegionalUnits[index].getNameInGreek() + ".jpg"));
					//
					//							if (locationLabelForGreece.getPrefWidth() <= 600)
					//								locationForGreeceSmall.setImage(new Image("/images/greece/regions/inMap/x500/" + greekRegionalUnits[index].getNameInGreek() + ".jpg"));
					//							else if (locationLabelForGreece.getPrefWidth() <= 1100)
					//								locationForGreeceSmall.setImage(new Image("/images/greece/regions/inMap/x1000/" + greekRegionalUnits[index].getNameInGreek() + ".jpg"));
					//							else locationForGreeceSmall.setImage(new Image("/images/greece/regions/inMap/x2000/" + greekRegionalUnits[index].getNameInGreek() + ".jpg"));

					//Tooltips
					for(int i = 0; i < 10; i++) gridPaneLabelsForGreece[i][1].getTooltip().setText(gridPaneLabelsForGreece[i][1].getText());
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

				if (getIndexInOptionsForAttractions() == 0 && newValue.intValue() < Attraction.getNumberOfAttractions())
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

					scrollPaneForAttractionsBasicInfo.setPrefHeight(1.25 * attractionBasicInfoLabel.lookup(".text").getBoundsInLocal().getHeight());

					//Images

//					logoForGreeceImageSmall.setImage(new Image("/images/greece/decentralizedAdministrations/logos/" + greekDecAdm[index].getNameInGreek() + ".jpg"));
//
//					if (locationLabelForGreece.getPrefWidth() <= 600)
//						locationForGreeceSmall.setImage(new Image("/images/greece/decentralizedAdministrations/inMap/x500/" + greekDecAdm[index].getNameInGreek() + ".jpg"));
//					else if (locationLabelForGreece.getPrefWidth() <= 1100)
//						locationForGreeceSmall.setImage(new Image("/images/greece/decentralizedAdministrations/inMap/x1000/" + greekDecAdm[index].getNameInGreek() + ".jpg"));
//					else locationForGreeceSmall.setImage(new Image("/images/greece/decentralizedAdministrations/inMap/x2000/" + greekDecAdm[index].getNameInGreek() + ".jpg"));

					//Tooltips
					for(int i = 0; i < 4; i++) gridPaneLabelsForAttractions[i][1].getTooltip().setText(gridPaneLabelsForAttractions[i][1].getText());
				}
			}
		});

		gridPaneLabelsForAttractions[4][1].setOnMousePressed(e ->
		{
			if(e.isPrimaryButtonDown())
			{
				UrlOpener.openGoogleMapsLink(gridPaneLabelsForAttractions[4][1].getText());
			}
			else if(e.isSecondaryButtonDown())
			{
				clipboardContent.putString(gridPaneLabelsForAttractions[4][1].getText());
				clipboard.setContent(clipboardContent);
				clipboardContent.clear();
			}
		});

		gridPaneLabelsForAttractions[5][1].setOnMousePressed(e ->
		{
			if(e.isPrimaryButtonDown())
			{
				if(getCurrentLanguage() == LANGUAGE.GREEK) UrlOpener.openGreekWikiURL(gridPaneLabelsForAttractions[5][1].getText());
				else UrlOpener.openURL(gridPaneLabelsForAttractions[5][1].getText());
			}
			else if(e.isSecondaryButtonDown())
			{
				clipboardContent.putString(gridPaneLabelsForAttractions[5][1].getText());
				clipboard.setContent(clipboardContent);
				clipboardContent.clear();
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

		previousInBigImageButton.setOnAction(e -> makeAStepInBigImage(-1));
		nextInBigImageButton.setOnAction(e -> makeAStepInBigImage(1));

		zoomInBigImageButton.setOnMousePressed(e -> zoomInBigImageButton.setImage(images.get(Images.ZOOM_IN_CLICKED)));
		zoomInBigImageButton.setOnAction(e -> zoomIn());
		zoomInBigImageButton.setOnMouseReleased(e -> zoomInBigImageButton.setImage(images.get(Images.ZOOM_IN)));

		zoomOutBigImageButton.setOnMousePressed(e -> zoomOutBigImageButton.setImage(images.get(Images.ZOOM_OUT_CLICKED)));
		zoomOutBigImageButton.setOnAction(e -> zoomOut());
		zoomOutBigImageButton.setOnMouseReleased(e -> zoomOutBigImageButton.setImage(images.get(Images.ZOOM_OUT)));

		exitBigImageButton.setOnMousePressed(e -> exitBigImageButton.setImage(images.get(Images.X_CLICKED)));
		exitBigImageButton.setOnAction(e -> closeBigImage());
		exitBigImageButton.setOnMouseReleased(e -> exitBigImageButton.setImage(images.get(Images.X)));

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
						&& !anchorPane.getCursor().equals(Cursor.H_RESIZE) && !anchorPane.getCursor().equals(Cursor.V_RESIZE);
			}
			else if(isInfoOpen)
			{
				pressedOutsideOfGreekInfo = (e.getSceneX() < rectangleToShowInfo.getLayoutX() || e.getSceneX() > rectangleToShowInfo.getLayoutX() + rectangleToShowInfo.getWidth()
						|| e.getSceneY() < rectangleToShowInfo.getLayoutY() || e.getSceneY() > rectangleToShowInfo.getLayoutY() + rectangleToShowInfo.getHeight())
						&& !anchorPane.getCursor().equals(Cursor.H_RESIZE) && !anchorPane.getCursor().equals(Cursor.V_RESIZE);
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
			if(animationsUsed != ANIMATIONS.NO) timelineToHideAllStuff.playFromStart();
			else
			{
				showOtherScreen(welcomeScreen);

				gridViewForImagesForCountriesAndContinents.setItems(emptyObservableList);
				gridViewForImagesForUSA.setItems(emptyObservableList);
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
					titleImage.setTranslateX(-0.0416 * mainScene.getWidth());
					titleLabel.setTranslateX(-0.0416 * mainScene.getWidth());
					vBoxForSound.setTranslateY(0);
					vBoxForSound.setVisible(true);
				}
			}
			else
			{
				if(animationsUsed != ANIMATIONS.NO) timelineToHideSoundOptions.play();
				else
				{
					vBoxForSound.setTranslateY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
					titleImage.setTranslateX(0);
					titleLabel.setTranslateX(0);
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

		anchorPane.addEventFilter(KeyEvent.KEY_RELEASED, e ->
		{
			if(e.getCode() == KeyCode.ESCAPE)
			{
				if(bigImageStatus != 0) closeBigImage();
				else if(isInfoOpen) closeMoreInfo();
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
		translateTransitionForTitleImage = new TranslateTransition(Duration.millis(200), titleImage);
		scaleTransitionForTitleLabel = new ScaleTransition(Duration.millis(200), titleLabel);
		translateTransitionForTitleLabel = new TranslateTransition(Duration.millis(200), titleLabel);

		translateTransitionForVBoxForSound = new TranslateTransition(Duration.millis(200), vBoxForSound);

		scaleTransitionForBackButton = new ScaleTransition(Duration.millis(200), backButton);

		scaleTransitionForCountriesToggleButton = new ScaleTransition(Duration.millis(200), countriesAndContinentsToggleButton);
		scaleTransitionForUSAStatesToggleButton = new ScaleTransition(Duration.millis(200), USAToggleButton);
		scaleTransitionForGreekCountiesToggleButton = new ScaleTransition(Duration.millis(200), greeceToggleButton);
		scaleTransitionForAttractionsToggleButton = new ScaleTransition(Duration.millis(200), attractionsToggleButton);

		scaleTransitionForRectangleForBigImage = new ScaleTransition(Duration.millis(150), rectangleForBigImage);
		scaleTransitionForBigImage = new ScaleTransition(Duration.millis(150), bigImage);
		scaleTransitionForLabelInBigImage = new ScaleTransition(Duration.millis(150), labelForBigImage);
		scaleTransitionForPreviousInBigImage = new ScaleTransition(Duration.millis(150), previousInBigImageButton);
		scaleTransitionForNextInBigImage = new ScaleTransition(Duration.millis(150), nextInBigImageButton);
		scaleTransitionForExitInBigImage = new ScaleTransition(Duration.millis(150), exitBigImageButton);
		scaleTransitionForZoomInInBigImage = new ScaleTransition(Duration.millis(150), zoomInBigImageButton);
		scaleTransitionForZoomOutInBigImage = new ScaleTransition(Duration.millis(150), zoomOutBigImageButton);

		scaleTransitionForRectangleForInfo = new ScaleTransition(Duration.millis(200), rectangleToShowInfo);
		scaleTransitionForTextAreaForInfo = new ScaleTransition(Duration.millis(200), textAreaForInfo);

		scaleTransitionForHBoxMainForCountriesAndContinents = new ScaleTransition(Duration.millis(200), hBoxMainForCountriesAndContinents);
		scaleTransitionForHBoxMainForUSA = new ScaleTransition(Duration.millis(200), hBoxMainForUSA);
		scaleTransitionForHBoxMainForGreece = new ScaleTransition(Duration.millis(200), hBoxMainForGreece);
		scaleTransitionForHBoxMainForAttractions = new ScaleTransition(Duration.millis(200), hBoxMainForAttractions);

		translateTransitionForHBoxMainForCountriesAndContinents = new TranslateTransition(Duration.millis(200), hBoxMainForCountriesAndContinents);
		translateTransitionForHBoxMainForUSA = new TranslateTransition(Duration.millis(200), hBoxMainForUSA);
		translateTransitionForHBoxMainForGreece = new TranslateTransition(Duration.millis(200), hBoxMainForGreece);
		translateTransitionForHBoxMainForAttractions = new TranslateTransition(Duration.millis(200), hBoxMainForAttractions);

		fadeTransitionForMovingEarthImage = new FadeTransition(Duration.millis(300), movingEarthImage);

		translateTransitionForWoodPanelFor1IconImage = new TranslateTransition(Duration.millis(200), woodPanelFor1IconImage);

		scaleTransitionForSoundIcon = new ScaleTransition(Duration.millis(200), soundButton);

		timelineToShowAllStuff = new Timeline(
         new KeyFrame(Duration.millis(0), e ->
         {
             backButton.setDisable(true);
             hBoxForToggleButtons.setDisable(true);
	         hBoxMainForCountriesAndContinents.setDisable(true);
	         hBoxMainForUSA.setDisable(true);
	         hBoxMainForGreece.setDisable(true);
	         hBoxMainForAttractions.setDisable(true);

             fadeTransitionForMovingEarthImage.setToValue(1);
             fadeTransitionForMovingEarthImage.playFromStart();
         }),
         new KeyFrame(Duration.millis(300), e ->
         {
             translateTransitionForTitleImage.setToX(0);
             translateTransitionForTitleImage.setToY(0);

			 translateTransitionForWoodPanelFor1IconImage.setToY(0);

             getAudioStuff().playSlideSound();
             translateTransitionForTitleImage.playFromStart();
			 translateTransitionForWoodPanelFor1IconImage.playFromStart();
         }),
         new KeyFrame(Duration.millis(500), e ->
         {
			 scaleTransitionForSoundIcon.setToX(1);
			 scaleTransitionForSoundIcon.setToY(1);

             scaleTransitionForTitleLabel.setDuration(Duration.millis(200));
             scaleTransitionForTitleLabel.setCycleCount(1);
             scaleTransitionForTitleLabel.setAutoReverse(false);
             scaleTransitionForTitleLabel.setFromX(0);
             scaleTransitionForTitleLabel.setFromY(0);
             scaleTransitionForTitleLabel.setToX(0.90);
             scaleTransitionForTitleLabel.setToY(0.90);

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

             getAudioStuff().playPopUpSound();
             scaleTransitionForCountriesToggleButton.playFromStart();
             scaleTransitionForUSAStatesToggleButton.playFromStart();
             scaleTransitionForGreekCountiesToggleButton.playFromStart();
             scaleTransitionForAttractionsToggleButton.playFromStart();
             scaleTransitionForTitleLabel.playFromStart();
             scaleTransitionForBackButton.playFromStart();
			 scaleTransitionForSoundIcon.playFromStart();
         }),
         new KeyFrame(Duration.millis(700), e ->
         {
	         if(hBoxMainForCountriesAndContinents.isVisible())
	         {
		         scaleTransitionForHBoxMainForCountriesAndContinents.setToX(1);
		         scaleTransitionForHBoxMainForCountriesAndContinents.setToY(1);

		         getAudioStuff().playPopUpSound();
		         scaleTransitionForHBoxMainForCountriesAndContinents.playFromStart();
	         }
	         else if(hBoxMainForUSA.isVisible())
	         {
		         scaleTransitionForHBoxMainForUSA.setToX(1);
		         scaleTransitionForHBoxMainForUSA.setToY(1);

		         getAudioStuff().playPopUpSound();
		         scaleTransitionForHBoxMainForUSA.playFromStart();
	         }
	         else if(hBoxMainForGreece.isVisible())
	         {
		         scaleTransitionForHBoxMainForGreece.setToX(1);
		         scaleTransitionForHBoxMainForGreece.setToY(1);

		         getAudioStuff().playPopUpSound();
		         scaleTransitionForHBoxMainForGreece.playFromStart();
	         }
	         else if(hBoxMainForAttractions.isVisible())
	         {
		         scaleTransitionForHBoxMainForAttractions.setToX(1);
		         scaleTransitionForHBoxMainForAttractions.setToY(1);

		         getAudioStuff().playPopUpSound();
		         scaleTransitionForHBoxMainForAttractions.playFromStart();
	         }
         }),
         new KeyFrame(Duration.millis(900), e ->
         {
	         backButton.setDisable(false);
	         hBoxForToggleButtons.setDisable(false);
	         hBoxMainForCountriesAndContinents.setDisable(false);
	         hBoxMainForUSA.setDisable(false);
	         hBoxMainForGreece.setDisable(false);
	         hBoxMainForAttractions.setDisable(false);
         }));

		timelineToHideAllStuff = new Timeline(
         new KeyFrame(Duration.millis(200), e ->
         {
	         backButton.setDisable(true);
	         hBoxForToggleButtons.setDisable(true);
	         hBoxMainForCountriesAndContinents.setDisable(true);
	         hBoxMainForUSA.setDisable(true);
	         hBoxMainForGreece.setDisable(true);
	         hBoxMainForAttractions.setDisable(true);

	         if(hBoxMainForCountriesAndContinents.isVisible())
	         {
		         scaleTransitionForHBoxMainForCountriesAndContinents.setToX(0);
		         scaleTransitionForHBoxMainForCountriesAndContinents.setToY(0);

		         getAudioStuff().playMinimizeSound();
		         scaleTransitionForHBoxMainForCountriesAndContinents.playFromStart();
	         }
	         else if(hBoxMainForUSA.isVisible())
	         {
		         scaleTransitionForHBoxMainForUSA.setToX(0);
		         scaleTransitionForHBoxMainForUSA.setToY(0);

		         getAudioStuff().playMinimizeSound();
		         scaleTransitionForHBoxMainForUSA.playFromStart();
	         }
	         else if(hBoxMainForGreece.isVisible())
	         {
		         scaleTransitionForHBoxMainForGreece.setToX(0);
		         scaleTransitionForHBoxMainForGreece.setToY(0);

		         getAudioStuff().playMinimizeSound();
		         scaleTransitionForHBoxMainForGreece.playFromStart();
	         }
	         else if(hBoxMainForAttractions.isVisible())
	         {
		         scaleTransitionForHBoxMainForAttractions.setToX(0);
		         scaleTransitionForHBoxMainForAttractions.setToY(0);

		         getAudioStuff().playMinimizeSound();
		         scaleTransitionForHBoxMainForAttractions.playFromStart();
	         }
         }),
         new KeyFrame(Duration.millis(400), e ->
         {
	         if(gridViewForImagesForCountriesAndContinents.getItems() != emptyObservableList) gridViewForImagesForCountriesAndContinents.setItems(emptyObservableList);
	         else if(gridViewForImagesForUSA.getItems() != emptyObservableList) gridViewForImagesForUSA.setItems(emptyObservableList);

			 scaleTransitionForSoundIcon.setToX(0);
			 scaleTransitionForSoundIcon.setToY(0);

             scaleTransitionForTitleLabel.setDuration(Duration.millis(200));
             scaleTransitionForTitleLabel.setFromX(titleLabel.getScaleX());
             scaleTransitionForTitleLabel.setFromY(titleLabel.getScaleY());
             scaleTransitionForTitleLabel.setToX(0);
             scaleTransitionForTitleLabel.setToY(0);
             scaleTransitionForTitleLabel.setAutoReverse(false);
             scaleTransitionForTitleLabel.setCycleCount(1);

	         scaleTransitionForTitleLabel.setOnFinished(ev -> {});

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

             getAudioStuff().playMinimizeSound();
             scaleTransitionForTitleLabel.playFromStart();
             scaleTransitionForBackButton.playFromStart();
			 scaleTransitionForSoundIcon.playFromStart();
         }),
         new KeyFrame(Duration.millis(600), e ->
         {
             translateTransitionForTitleImage.setToY(-1.0 * (titleImage.getLayoutY() + titleImage.getBoundsInParent().getHeight() + 20));
			 translateTransitionForWoodPanelFor1IconImage.setToY(-1.0 * (woodPanelFor1IconImage.getLayoutY() + woodPanelFor1IconImage.getBoundsInParent().getHeight()));

             getAudioStuff().playSlideSound();
             translateTransitionForTitleImage.playFromStart();
			 translateTransitionForWoodPanelFor1IconImage.playFromStart();

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

             fadeTransitionForMovingEarthImage.setToValue(0);
             fadeTransitionForMovingEarthImage.playFromStart();
         }),
         new KeyFrame(Duration.millis(1100), e ->
         {
             if(animationsUsed == ANIMATIONS.ALL) pauseEarthAnimation();
             showOtherScreen(welcomeScreen);

	         backButton.setDisable(false);
	         hBoxForToggleButtons.setDisable(false);
	         hBoxMainForCountriesAndContinents.setDisable(false);
	         hBoxMainForUSA.setDisable(false);
	         hBoxMainForGreece.setDisable(false);
	         hBoxMainForAttractions.setDisable(false);
         }));

		timelineToShowSoundOptions = new Timeline(
		new KeyFrame(Duration.millis(0), e ->
		{
			soundButton.setDisable(true);

			translateTransitionForTitleImage.setToX(-0.0416 * mainScene.getWidth());
			translateTransitionForTitleLabel.setToX(-0.0416 * mainScene.getWidth());
			translateTransitionForTitleImage.setToY(0);
			translateTransitionForTitleLabel.setToY(0);

			getAudioStuff().playSlideSound();
			translateTransitionForTitleImage.playFromStart();
			translateTransitionForTitleLabel.playFromStart();
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
             translateTransitionForTitleImage.setToY(0);
             translateTransitionForTitleImage.setToX(0);
             translateTransitionForTitleLabel.setToY(0);
             translateTransitionForTitleLabel.setToX(0);

             translateTransitionForTitleImage.playFromStart();
             translateTransitionForTitleLabel.playFromStart();
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
		if(previousChalkboardImage.getImage() == null || !woodenFrameImage.getImage().equals(images.get(Images.FRAME)))
		{
			previousChalkboardImage.setImage(images.get(Images.CHALKBOARD_BACKGROUND));
			woodenFrameImage.setImage(images.get(Images.FRAME));
			movingEarthImage.setImage(images.get(Images.MOVING_EARTH_1));
			titleImage.setImage(images.get(Images.EMPTY_WOOD_BACKGROUND_SMALL_ROPE));
			woodPanelFor1IconImage.setImage(images.get(Images.EMPTY_WOOD_BACKGROUND_1_ICON));
			previousInBigImageButton.setImage(images.get(Images.BACK_ARROW));
			nextInBigImageButton.setImage(images.get(Images.BACK_ARROW));
			exitBigImageButton.setImage(images.get(Images.X));
			zoomInBigImageButton.setImage(images.get(Images.ZOOM_IN));
			zoomOutBigImageButton.setImage(images.get(Images.ZOOM_OUT));
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
			for (int i = 0; i < NUM_ALL_COUNTRIES; i++)
			{
				String s = countries[i].getNameInGreek();
				switch (s)
				{
					case "Κοινοπολιτεία των Βορείων Μαριανών Νήσων":s = "Κοινοπολιτεία των Βορείων\nΜαριανών Νήσων";break;
					case "Ομόσπονδες Πολιτείες της Μικρονησίας":s = "Ομόσπονδες Πολιτείες\nτης Μικρονησίας";break;
				}

				countriesObservableNamesList.add(s);
				countriesSortList.add(i);
			}

			capitalsOfCountriesObservableNamesList.clear();
			capitalsOfCountriesSortList.clear();
			ArrayList<Integer> tempList = new ArrayList<>();
			for (int i = 0; i < NUM_ALL_COUNTRIES; i++)
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
			for (int i = 0; i < NUM_ALL_COUNTRIES - 1; i++)
				for (int y = 0; y < NUM_ALL_COUNTRIES - i - 1; y++)
					if ((tempList.get(y).compareTo(tempList.get(y + 1))) > 0)
					{
						String temp = capitalsOfCountriesObservableNamesList.get(y);
						int s = capitalsOfCountriesSortList.get(y);
						int t = tempList.get(y);

						capitalsOfCountriesObservableNamesList.set(y, capitalsOfCountriesObservableNamesList.get(y + 1));
						capitalsOfCountriesSortList.set(y, capitalsOfCountriesSortList.get(y + 1));
						tempList.set(y, tempList.get(y + 1));

						capitalsOfCountriesObservableNamesList.set(y + 1, temp);
						capitalsOfCountriesSortList.set(y + 1, s);
						tempList.set(y + 1, t);
					}

			continentsObservableNamesList.clear();
			continentsSortList.clear();
			for (int i = 0; i < Continent.NUMBER_OF_CONTINENTS; i++)
			{
				continentsObservableNamesList.add(continents[i].getNameInGreek());
				continentsSortList.add(i);
			}

			statesOfUSAObservableNamesList.clear();
			statesOfUSASortList.clear();
			for (int i = 0; i < StateOfUSA.NUMBER_OF_USA_STATES; i++)
			{
				statesOfUSAObservableNamesList.add(statesOfUSA[i].getNameInGreek());
				statesOfUSASortList.add(i);
			}

			capitalsOfStatesObservableNamesList.clear();
			capitalsOfStatesSortList.clear();
			tempList.clear();
			for (int i = 0; i < StateOfUSA.NUMBER_OF_USA_STATES; i++)
			{
				capitalsOfStatesObservableNamesList.add(statesOfUSA[i].getCapitalName());
				capitalsOfStatesSortList.add(i);
				tempList.add(statesOfUSA[i].getPositionInCapitals());
			}
			for (int i = 0; i < StateOfUSA.NUMBER_OF_USA_STATES - 1; i++)
				for (int y = 0; y < StateOfUSA.NUMBER_OF_USA_STATES - i - 1; y++)
					if ((tempList.get(y).compareTo(tempList.get(y + 1))) > 0)
					{
						String temp = capitalsOfStatesObservableNamesList.get(y);
						int s = capitalsOfStatesSortList.get(y);
						int t = tempList.get(y);

						capitalsOfStatesObservableNamesList.set(y, capitalsOfStatesObservableNamesList.get(y + 1));
						capitalsOfStatesSortList.set(y, capitalsOfStatesSortList.get(y + 1));
						tempList.set(y, tempList.get(y + 1));

						capitalsOfStatesObservableNamesList.set(y + 1, temp);
						capitalsOfStatesSortList.set(y + 1, s);
						tempList.set(y + 1, t);
					}

			greekDecentralizedAdministrationsObservableNamesList.clear();
			greekDecentralizedAdministrationsSortList.clear();
			for (int i = 0; i < GreekDecentralizedAdministration.NUMBER_OF_GREEK_DECENTRALIZED_ADMINISTRATIONS; i++)
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
			for (int i = 0; i < GreekRegion.NUMBER_OF_GREEK_REGIONS; i++)
			{
				String s = greekRegions[i].getNameInGreek();
				if(s.equals("Περιφέρεια Ανατολικής Μακεδονίας και Θράκης")) s = "Περιφέρεια Ανατολικής Μακεδονίας\nκαι Θράκης";

				greekRegionsObservableNamesList.add(s);
				greekRegionsSortList.add(i);
			}

			greekRegionalUnitsObservableNamesList.clear();
			greekRegionalUnitsSortList.clear();
			for (int i = 0; i < GreekRegionalUnit.NUMBER_OF_GREEK_REGIONAL_UNITS; i++)
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

			attractionsObservableNamesList.clear();
			attractionsSortList.clear();
			for (int i = 0; i < Attraction.getNumberOfAttractions(); i++)
			{
				String s = attractions[i].getNameInGreek();

				switch (s)
				{
					case "Βασιλικό Αστεροσκοπείο του Γκρήνουιτς":s = "Βασιλικό Αστεροσκοπείο του\nΓκρήνουιτς";break;
					case "Καθεδρικός Ναός του Σωτήρος Χριστού":s = "Καθεδρικός Ναός του Σωτήρος\nΧριστού";break;
				}

				attractionsObservableNamesList.add(s);
				attractionsSortList.add(i);
			}
		}
		else if (getCurrentLanguage() == LANGUAGE.ENGLISH && !countriesObservableNamesList.get(0).equals(countries[0].getNameInEnglish()))
		{
			countriesObservableNamesList.clear();
			countriesSortList.clear();
			for (int i = 0; i < NUM_ALL_COUNTRIES; i++)
			{
				String s = countries[i].getNameInEnglish();
				switch (s)
				{
					case "Federated States of Micronesia":s = "Federated States\nof Micronesia";break;
					case "Commonwealth of the Northern Mariana Islands":s = "Commonwealth of the\nNorthern Mariana Islands";break;
				}

				countriesObservableNamesList.add(s);
				countriesSortList.add(i);
			}
			for (int i = 0; i < NUM_ALL_COUNTRIES - 1; i++)
				for (int y = 0; y < NUM_ALL_COUNTRIES - i - 1; y++)
					if (countriesObservableNamesList.get(y).compareTo(countriesObservableNamesList.get(y + 1)) > 0)
					{
						String temp = countriesObservableNamesList.get(y);
						int s = countriesSortList.get(y);

						countriesObservableNamesList.set(y, countriesObservableNamesList.get(y + 1));
						countriesSortList.set(y, countriesSortList.get(y + 1));

						countriesObservableNamesList.set(y + 1, temp);
						countriesSortList.set(y + 1, s);
					}

			capitalsOfCountriesObservableNamesList.clear();
			capitalsOfCountriesSortList.clear();
			for (int i = 0; i < NUM_ALL_COUNTRIES; i++)
			{
				String s = countries[i].getCapitalName();
				if(s.equals("Flying Fish Cove (\"The Settlement\")")) s = "Flying Fish Cove\n(\"The Settlement\")";

				capitalsOfCountriesObservableNamesList.add(s);
				capitalsOfCountriesSortList.add(i);
			}
			for (int i = 0; i < NUM_ALL_COUNTRIES - 1; i++)
				for (int y = 0; y < NUM_ALL_COUNTRIES - i - 1; y++)
					if (capitalsOfCountriesObservableNamesList.get(y).compareTo(capitalsOfCountriesObservableNamesList.get(y + 1)) > 0)
					{
						String temp = capitalsOfCountriesObservableNamesList.get(y);
						int s = capitalsOfCountriesSortList.get(y);

						capitalsOfCountriesObservableNamesList.set(y, capitalsOfCountriesObservableNamesList.get(y + 1));
						capitalsOfCountriesSortList.set(y, capitalsOfCountriesSortList.get(y + 1));

						capitalsOfCountriesObservableNamesList.set(y + 1, temp);
						capitalsOfCountriesSortList.set(y + 1, s);
					}

			continentsObservableNamesList.clear();
			continentsSortList.clear();
			for (int i = 0; i < Continent.NUMBER_OF_CONTINENTS; i++)
			{
				continentsObservableNamesList.add(continents[i].getNameInEnglish());
				continentsSortList.add(i);
			}
			for (int i = 0; i < Continent.NUMBER_OF_CONTINENTS - 1; i++)
				for (int y = 0; y < Continent.NUMBER_OF_CONTINENTS - i - 1; y++)
					if (continentsObservableNamesList.get(y).compareTo(continentsObservableNamesList.get(y + 1)) > 0)
					{
						String temp = continentsObservableNamesList.get(y);
						int s = continentsSortList.get(y);

						continentsObservableNamesList.set(y, continentsObservableNamesList.get(y + 1));
						continentsSortList.set(y, continentsSortList.get(y + 1));

						continentsObservableNamesList.set(y + 1, temp);
						continentsSortList.set(y + 1, s);
					}

			statesOfUSAObservableNamesList.clear();
			statesOfUSASortList.clear();
			for (int i = 0; i < StateOfUSA.NUMBER_OF_USA_STATES; i++)
			{
				statesOfUSAObservableNamesList.add(statesOfUSA[i].getNameInEnglish());
				statesOfUSASortList.add(i);
			}
			for (int i = 0; i < StateOfUSA.NUMBER_OF_USA_STATES - 1; i++)
				for (int y = 0; y < StateOfUSA.NUMBER_OF_USA_STATES - i - 1; y++)
					if (statesOfUSAObservableNamesList.get(y).compareTo(statesOfUSAObservableNamesList.get(y + 1)) > 0)
					{
						String temp = statesOfUSAObservableNamesList.get(y);
						int s = statesOfUSASortList.get(y);

						statesOfUSAObservableNamesList.set(y, statesOfUSAObservableNamesList.get(y + 1));
						statesOfUSASortList.set(y, statesOfUSASortList.get(y + 1));

						statesOfUSAObservableNamesList.set(y + 1, temp);
						statesOfUSASortList.set(y + 1, s);
					}

			capitalsOfStatesObservableNamesList.clear();
			capitalsOfStatesSortList.clear();
			for (int i = 0; i < StateOfUSA.NUMBER_OF_USA_STATES; i++)
			{
				capitalsOfStatesObservableNamesList.add(statesOfUSA[i].getCapitalName());
				capitalsOfStatesSortList.add(i);
			}
			for (int i = 0; i < StateOfUSA.NUMBER_OF_USA_STATES - 1; i++)
				for (int y = 0; y < StateOfUSA.NUMBER_OF_USA_STATES - i - 1; y++)
					if (capitalsOfStatesObservableNamesList.get(y).compareTo(capitalsOfStatesObservableNamesList.get(y + 1)) > 0)
					{
						String temp = capitalsOfStatesObservableNamesList.get(y);
						int s = capitalsOfStatesSortList.get(y);

						capitalsOfStatesObservableNamesList.set(y, capitalsOfStatesObservableNamesList.get(y + 1));
						capitalsOfStatesSortList.set(y, capitalsOfStatesSortList.get(y + 1));

						capitalsOfStatesObservableNamesList.set(y + 1, temp);
						capitalsOfStatesSortList.set(y + 1, s);
					}

			greekDecentralizedAdministrationsObservableNamesList.clear();
			greekDecentralizedAdministrationsSortList.clear();
			for (int i = 0; i < GreekDecentralizedAdministration.NUMBER_OF_GREEK_DECENTRALIZED_ADMINISTRATIONS; i++)
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
			for (int i = 0; i < GreekDecentralizedAdministration.NUMBER_OF_GREEK_DECENTRALIZED_ADMINISTRATIONS - 1; i++)
				for (int y = 0; y < GreekDecentralizedAdministration.NUMBER_OF_GREEK_DECENTRALIZED_ADMINISTRATIONS - i - 1; y++)
					if (greekDecentralizedAdministrationsObservableNamesList.get(y).compareTo(greekDecentralizedAdministrationsObservableNamesList.get(y + 1)) > 0)
					{
						String temp = greekDecentralizedAdministrationsObservableNamesList.get(y);
						int s = greekDecentralizedAdministrationsSortList.get(y);

						greekDecentralizedAdministrationsObservableNamesList.set(y, greekDecentralizedAdministrationsObservableNamesList.get(y + 1));
						greekDecentralizedAdministrationsSortList.set(y, greekDecentralizedAdministrationsSortList.get(y + 1));

						greekDecentralizedAdministrationsObservableNamesList.set(y + 1, temp);
						greekDecentralizedAdministrationsSortList.set(y + 1, s);
					}

			greekRegionsObservableNamesList.clear();
			greekRegionsSortList.clear();
			for (int i = 0; i < GreekRegion.NUMBER_OF_GREEK_REGIONS; i++)
			{
				String s = greekRegions[i].getNameInEnglish();
				if(s.equals("Region of Eastern Macedonia and Thrace")) s = "Region of Eastern Macedonia and\nThrace";

				greekRegionsObservableNamesList.add(s);
				greekRegionsSortList.add(i);
			}
			for (int i = 0; i < GreekRegion.NUMBER_OF_GREEK_REGIONS - 1; i++)
				for (int y = 0; y < GreekRegion.NUMBER_OF_GREEK_REGIONS - i - 1; y++)
					if (greekRegionsObservableNamesList.get(y).compareTo(greekRegionsObservableNamesList.get(y + 1)) > 0)
					{
						String temp = greekRegionsObservableNamesList.get(y);
						int s = greekRegionsSortList.get(y);

						greekRegionsObservableNamesList.set(y, greekRegionsObservableNamesList.get(y + 1));
						greekRegionsSortList.set(y, greekRegionsSortList.get(y + 1));

						greekRegionsObservableNamesList.set(y + 1, temp);
						greekRegionsSortList.set(y + 1, s);
					}

			greekRegionalUnitsObservableNamesList.clear();
			greekRegionalUnitsSortList.clear();
			for (int i = 0; i < GreekRegionalUnit.NUMBER_OF_GREEK_REGIONAL_UNITS; i++)
			{
				greekRegionalUnitsObservableNamesList.add(greekRegionalUnits[i].getNameInEnglish());
				greekRegionalUnitsSortList.add(i);
			}
			for (int i = 0; i < GreekRegionalUnit.NUMBER_OF_GREEK_REGIONAL_UNITS - 1; i++)
				for (int y = 0; y < GreekRegionalUnit.NUMBER_OF_GREEK_REGIONAL_UNITS - i - 1; y++)
					if (greekRegionalUnitsObservableNamesList.get(y).compareTo(greekRegionalUnitsObservableNamesList.get(y + 1)) > 0)
					{
						String temp = greekRegionalUnitsObservableNamesList.get(y);
						int s = greekRegionalUnitsSortList.get(y);

						greekRegionalUnitsObservableNamesList.set(y, greekRegionalUnitsObservableNamesList.get(y + 1));
						greekRegionalUnitsSortList.set(y, greekRegionalUnitsSortList.get(y + 1));

						greekRegionalUnitsObservableNamesList.set(y + 1, temp);
						greekRegionalUnitsSortList.set(y + 1, s);
					}

			attractionsObservableNamesList.clear();
			attractionsSortList.clear();
			for (int i = 0; i < Attraction.getNumberOfAttractions(); i++)
			{
				attractionsObservableNamesList.add(attractions[i].getNameInEnglish());
				attractionsSortList.add(i);
			}
			for (int i = 0; i < Attraction.getNumberOfAttractions() - 1; i++)
				for (int y = 0; y < Attraction.getNumberOfAttractions() - i - 1; y++)
					if (attractionsObservableNamesList.get(y).compareTo(attractionsObservableNamesList.get(y + 1)) > 0)
					{
						String temp = attractionsObservableNamesList.get(y);
						int s = attractionsSortList.get(y);

						attractionsObservableNamesList.set(y, attractionsObservableNamesList.get(y + 1));
						attractionsSortList.set(y, attractionsSortList.get(y + 1));

						attractionsObservableNamesList.set(y + 1, temp);
						attractionsSortList.set(y + 1, s);
					}
		}

		if(animationsUsed != ANIMATIONS.NO)
		{
			woodPanelFor1IconImage.setTranslateY(-1.0 * (woodPanelFor1IconImage.getLayoutY() + woodPanelFor1IconImage.getBoundsInParent().getHeight()));

			soundButton.setScaleX(0);
			soundButton.setScaleY(0);

			movingEarthImage.setOpacity(0);

			titleImage.setTranslateX(0);
			titleImage.setTranslateY(-1.0 * (titleImage.getLayoutY() + titleImage.getBoundsInParent().getHeight() + 20));

			titleLabel.setTranslateX(0);
			titleLabel.setTranslateY(0);

			countriesAndContinentsToggleButton.setScaleX(0);
			countriesAndContinentsToggleButton.setScaleY(0);
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

			titleLabel.setScaleX(0);
			titleLabel.setScaleY(0);
		}
		else
		{
			woodPanelFor1IconImage.setTranslateY(0);

			soundButton.setScaleX(1);
			soundButton.setScaleY(1);

			titleImage.setTranslateX(0);
			titleImage.setTranslateY(0);

			titleLabel.setTranslateX(0);
			titleLabel.setTranslateY(0);

			movingEarthImage.setOpacity(1);

			countriesAndContinentsToggleButton.setScaleX(1);
			countriesAndContinentsToggleButton.setScaleY(1);
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

			titleLabel.setScaleX(1);
			titleLabel.setScaleY(1);

			backButton.setDisable(false);
			hBoxForToggleButtons.setDisable(false);
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

		setCorrectSoundIcon(false);

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
		exitBigImageButton.setScaleX(0);
		exitBigImageButton.setScaleY(0);
		zoomInBigImageButton.setScaleX(0);
		zoomInBigImageButton.setScaleX(0);
		zoomOutBigImageButton.setScaleY(0);
		zoomOutBigImageButton.setScaleY(0);

		//		--------------- SET VARIABLES AND TEXT ---------------
		if (typeOfGridViewImagesForCountriesAndContinents == null) typeOfGridViewImagesForCountriesAndContinents = GridViewImagesForCountriesAndContinentsType.NONE;
		if (typeOfGridViewImagesForUSA == null) typeOfGridViewImagesForUSA = GridViewImagesForUSAType.NONE;
		if (typeOfGridViewImagesForAttractions == null) typeOfGridViewImagesForAttractions = GridViewImagesForAttractionsType.NONE;

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

		if(toggleGroupForToggleButtons.getSelectedToggle() == null) countriesAndContinentsToggleButton.setSelected(true);
		else if(toggleGroupForToggleButtons.getSelectedToggle() == countriesAndContinentsToggleButton) countriesAndContinentsToggleButtonPressed();
		else if(toggleGroupForToggleButtons.getSelectedToggle() == USAToggleButton) usaStatesToggleButtonPressed();
		else if(toggleGroupForToggleButtons.getSelectedToggle() == greeceToggleButton) greeceToggleButtonPressed();
		else if(toggleGroupForToggleButtons.getSelectedToggle() == attractionsToggleButton) attractionsToggleButtonPressed();
	}

	private void countriesAndContinentsToggleButtonPressed()
	{
		setNamesForGridPaneLabels();

		if((getIndexInOptionsForCountriesAndContinents() == 5))
			scrollPaneForGridPaneForCountriesAndContinents.setId("");
		else
			scrollPaneForGridPaneForCountriesAndContinents.setId("no-vertical-scrollBar");

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

        gridPaneLabelsForCountriesAndContinents[2][1].getTooltip().setText(gridPaneLabelsForCountriesAndContinents[2][1].getText());
        gridPaneLabelsForCountriesAndContinents[3][1].getTooltip().setText(gridPaneLabelsForCountriesAndContinents[3][1].getText());
        gridPaneLabelsForCountriesAndContinents[4][1].getTooltip().setText(gridPaneLabelsForCountriesAndContinents[4][1].getText());

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

			flagForCountriesImageSmall.setImage(null);
			coatOfArmsForCountriesImageSmall.setImage(null);

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

		recalculateUI(mainScene.getWidth(), mainScene.getHeight());
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

		recalculateUI(mainScene.getWidth(), mainScene.getHeight());
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

			gridPaneLabelsForGreece[5][1].getTooltip().setText(languageResourceBundle.getString("websiteTooltip"));
			gridPaneLabelsForGreece[6][1].getTooltip().setText(languageResourceBundle.getString("websiteTooltip"));
			gridPaneLabelsForGreece[5][1].setCursor(Cursor.HAND);
			gridPaneLabelsForGreece[6][1].setCursor(Cursor.HAND);
			gridPaneLabelsForGreece[7][1].setCursor(Cursor.HAND);
			gridPaneLabelsForGreece[8][1].setCursor(Cursor.HAND);
			gridPaneLabelsForGreece[9][1].setCursor(Cursor.HAND);
			gridPaneLabelsForGreece[10][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[11][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[12][1].setCursor(Cursor.DEFAULT);

			gridPaneLabelsForGreece[7][1].getTooltip().setText(null);
			gridPaneLabelsForGreece[8][1].getTooltip().setText(null);
			gridPaneLabelsForGreece[9][1].getTooltip().setText(null);
			gridPaneLabelsForGreece[10][1].getTooltip().setText(null);
			gridPaneLabelsForGreece[11][1].getTooltip().setText(null);
			gridPaneLabelsForGreece[12][1].getTooltip().setText(null);
			gridPaneLabelsForGreece[13][1].getTooltip().setText(null);
			gridPaneLabelsForGreece[14][1].getTooltip().setText(null);

			logoLabelForGreece.setCursor(Cursor.DEFAULT);

			listViewForGreece.setItems(greekDecentralizedAdministrationsObservableNamesList);
		}
		else if (getIndexInOptionsForGreece() == 1)
		{
			gridPaneLabelsForGreece[13][1].setText("");
			gridPaneLabelsForGreece[14][1].setText("");

			gridPaneLabelsForGreece[9][1].getTooltip().setText(languageResourceBundle.getString("websiteTooltip"));
			gridPaneLabelsForGreece[10][1].getTooltip().setText(languageResourceBundle.getString("websiteTooltip"));
			gridPaneLabelsForGreece[5][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[6][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[7][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[8][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[9][1].setCursor(Cursor.HAND);
			gridPaneLabelsForGreece[10][1].setCursor(Cursor.HAND);
			gridPaneLabelsForGreece[11][1].setCursor(Cursor.HAND);
			gridPaneLabelsForGreece[12][1].setCursor(Cursor.HAND);

			if(gridPaneLabelsForGreece[7][1].getTooltip().getText() == null) gridPaneLabelsForGreece[7][1].getTooltip().setText(gridPaneLabelsForGreece[7][1].getText());
			if(gridPaneLabelsForGreece[8][1].getTooltip().getText() == null) gridPaneLabelsForGreece[8][1].getTooltip().setText(gridPaneLabelsForGreece[8][1].getText());
			if(gridPaneLabelsForGreece[9][1].getTooltip().getText() == null) gridPaneLabelsForGreece[9][1].getTooltip().setText(gridPaneLabelsForGreece[9][1].getText());
			if(gridPaneLabelsForGreece[10][1].getTooltip().getText() == null) gridPaneLabelsForGreece[10][1].getTooltip().setText(gridPaneLabelsForGreece[10][1].getText());

			gridPaneLabelsForGreece[11][1].getTooltip().setText(null);
			gridPaneLabelsForGreece[12][1].getTooltip().setText(null);
			gridPaneLabelsForGreece[13][1].getTooltip().setText(null);
			gridPaneLabelsForGreece[14][1].getTooltip().setText(null);

			logoLabelForGreece.setCursor(Cursor.HAND);

			listViewForGreece.setItems(greekRegionsObservableNamesList);
		}
		else if(getIndexInOptionsForGreece() == 2)
		{
			gridPaneLabelsForGreece[13][1].setText("");
			gridPaneLabelsForGreece[14][1].setText("");

			gridPaneLabelsForGreece[10][1].getTooltip().setText(languageResourceBundle.getString("websiteTooltip"));
			gridPaneLabelsForGreece[11][1].getTooltip().setText(languageResourceBundle.getString("websiteTooltip"));
			gridPaneLabelsForGreece[5][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[6][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[7][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[8][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[9][1].setCursor(Cursor.DEFAULT);
			gridPaneLabelsForGreece[10][1].setCursor(Cursor.HAND);
			gridPaneLabelsForGreece[11][1].setCursor(Cursor.HAND);
			gridPaneLabelsForGreece[12][1].setCursor(Cursor.HAND);

			if(gridPaneLabelsForGreece[7][1].getTooltip().getText() == null) gridPaneLabelsForGreece[7][1].getTooltip().setText(gridPaneLabelsForGreece[7][1].getText());
			if(gridPaneLabelsForGreece[8][1].getTooltip().getText() == null) gridPaneLabelsForGreece[8][1].getTooltip().setText(gridPaneLabelsForGreece[8][1].getText());
			if(gridPaneLabelsForGreece[9][1].getTooltip().getText() == null) gridPaneLabelsForGreece[9][1].getTooltip().setText(gridPaneLabelsForGreece[9][1].getText());
			if(gridPaneLabelsForGreece[10][1].getTooltip().getText() == null) gridPaneLabelsForGreece[10][1].getTooltip().setText(gridPaneLabelsForGreece[10][1].getText());
			if(gridPaneLabelsForGreece[11][1].getTooltip().getText() == null) gridPaneLabelsForGreece[11][1].getTooltip().setText(gridPaneLabelsForGreece[11][1].getText());

			gridPaneLabelsForGreece[12][1].getTooltip().setText(null);
			gridPaneLabelsForGreece[13][1].getTooltip().setText(null);
			gridPaneLabelsForGreece[14][1].getTooltip().setText(null);

			logoLabelForGreece.setCursor(Cursor.DEFAULT);

			listViewForGreece.setItems(greekRegionalUnitsObservableNamesList);
		}

		listViewForGreece.getSelectionModel().select(1);
		listViewForGreece.getSelectionModel().selectFirst();
		listViewForGreece.scrollTo(getIndexInListViewForGreece());

		recalculateUI(mainScene.getWidth(), mainScene.getHeight());
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

		recalculateUI(mainScene.getWidth(), mainScene.getHeight());
	}

	private void setupMoreInfo(String[] array)
	{
		hBoxMainForCountriesAndContinents.setDisable(true);
		hBoxMainForGreece.setDisable(true);
		hBoxForToggleButtons.setDisable(true);
		backButton.setDisable(true);
		soundButton.setDisable(true);

		setNumberOfTextAreaLines(array.length);

		if(getNumberOfTextAreaLines() < 4) textAreaForInfo.setPrefHeight(0.15 * mainScene.getHeight());
		else if(getNumberOfTextAreaLines() < 6) textAreaForInfo.setPrefHeight(0.2 * mainScene.getHeight());
		else if(getNumberOfTextAreaLines() < 8) textAreaForInfo.setPrefHeight(0.27 * mainScene.getHeight());
		else if(getNumberOfTextAreaLines() < 11) textAreaForInfo.setPrefHeight(0.37 * mainScene.getHeight());
		else if(getNumberOfTextAreaLines() < 16) textAreaForInfo.setPrefHeight(0.5 * mainScene.getHeight());
		else textAreaForInfo.setPrefHeight(0.7 * mainScene.getHeight());
		textAreaForInfo.setLayoutY(mainScene.getHeight() / 2.0 - textAreaForInfo.getPrefHeight() / 2.0);

		rectangleToShowInfo.setHeight(textAreaForInfo.getPrefHeight() + 1.0 / 48.0 * mainScene.getHeight());
		rectangleToShowInfo.setLayoutY(mainScene.getHeight() / 2.0 - rectangleToShowInfo.getHeight() / 2.0);

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

			getAudioStuff().playPopUpSound();
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
				soundButton.setDisable(false);

				isInfoOpen = false;
			});

			getAudioStuff().playMinimizeSound();
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
			soundButton.setDisable(false);

			isInfoOpen = false;
		}

	}

	private void zoomIn()
	{
		if(bigImage.getViewport() == null)
		{
			bigImage.setCursor(Cursor.HAND);
			String name = "";
			if(toggleGroupForToggleButtons.getSelectedToggle() == countriesAndContinentsToggleButton)
			{
				if (getIndexInOptionsForCountriesAndContinents() == 0)
					name = countries[countriesSortList.get(getIndexInListViewForCountriesAndContinents())].getNameInEnglish();
				else if (getIndexInOptionsForCountriesAndContinents() == 1)
					name = countries[capitalsOfCountriesSortList.get(getIndexInListViewForCountriesAndContinents())].getNameInEnglish();
				else if (getIndexInOptionsForCountriesAndContinents() == 5)
					name = continents[continentsSortList.get(getIndexInListViewForCountriesAndContinents())].getNameInEnglish();
				else
					name = countries[countriesSortList.get(getIndexInBigImageNormal())].getNameInEnglish();
			}
			else if(toggleGroupForToggleButtons.getSelectedToggle() == USAToggleButton)
			{
				if (getIndexInOptionsForUSA() == 0)
					name = statesOfUSA[statesOfUSASortList.get(getIndexInListViewForUSA())].getNameInEnglish();
				else if (getIndexInOptionsForUSA() == 1)
					name = statesOfUSA[capitalsOfStatesSortList.get(getIndexInListViewForUSA())].getNameInEnglish();
				else
					name = statesOfUSA[statesOfUSASortList.get(getIndexInBigImageNormal())].getNameInEnglish();
			}
			else if(toggleGroupForToggleButtons.getSelectedToggle() == greeceToggleButton)
			{
				if(getIndexInOptionsForGreece() == 0)
					name = greekDecAdm[greekDecentralizedAdministrationsSortList.get(getIndexInListViewForGreece())].getNameInEnglish();
				else if(getIndexInOptionsForGreece() == 1)
					name = greekRegions[greekRegionsSortList.get(getIndexInListViewForGreece())].getNameInEnglish();
			}

			if (typeOfNormalBigImage == BigImageType.FLAG_FOR_COUNTRIES) newBigImagePath = getImageStuff().getImagePath(ImageType.COUNTRY_FLAG, "x2000", name);
			else if (typeOfNormalBigImage == BigImageType.COAT_OF_ARMS_FOR_COUNTRIES) newBigImagePath = getImageStuff().getImagePath(ImageType.COUNTRY_COAT_OF_ARMS, "x3000", name);
			else if (typeOfNormalBigImage == BigImageType.LOCATION_FOR_COUNTRIES) newBigImagePath = getImageStuff().getImagePath(ImageType.COUNTRY_LOCATION, "x2000", name);
			else if (typeOfNormalBigImage == BigImageType.LOCATION_FOR_CONTINENTS) newBigImagePath = getImageStuff().getImagePath(ImageType.CONTINENT_LOCATION, "x2000", name);
			else if (typeOfNormalBigImage == BigImageType.FLAG_FOR_USA) newBigImagePath = getImageStuff().getImagePath(ImageType.USA_FLAG, "x2000", name);
			else if (typeOfNormalBigImage == BigImageType.SEAL_FOR_USA) newBigImagePath = getImageStuff().getImagePath(ImageType.USA_SEAL, "x3000", name);
			else if (typeOfNormalBigImage == BigImageType.LOCATION_FOR_USA) newBigImagePath = getImageStuff().getImagePath(ImageType.USA_LOCATION, "x2000", name);
			else if(typeOfNormalBigImage == BigImageType.LOCATION_FOR_GREEK_DEC_ADMIN) newBigImagePath = getImageStuff().getImagePath(ImageType.GREECE_DEC_ADM_LOCATION, "x2000", name);
			else if(typeOfNormalBigImage == BigImageType.LOCATION_FOR_GREEK_REGIONS) newBigImagePath = getImageStuff().getImagePath(ImageType.GREECE_REGION_LOCATION, "x2000", name);

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
		if(toggleGroupForToggleButtons.getSelectedToggle() == countriesAndContinentsToggleButton)
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
		else if(toggleGroupForToggleButtons.getSelectedToggle() == USAToggleButton)
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
		else if(toggleGroupForToggleButtons.getSelectedToggle() == greeceToggleButton)
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
		if (toggleGroupForToggleButtons.getSelectedToggle() == countriesAndContinentsToggleButton)
		{
			if (getIndexInOptionsForCountriesAndContinents() != 5)
			{
				name = countries[index].getNameInEnglish();
				if (getCurrentLanguage() == LANGUAGE.GREEK) labelForBigImage.setText(countries[index].getNameInGreek());
				else labelForBigImage.setText(name);
			}
			else
			{
				name = continents[index].getNameInEnglish();
				if (getCurrentLanguage() == LANGUAGE.GREEK) labelForBigImage.setText(continents[index].getNameInGreek());
				else labelForBigImage.setText(name);
			}

			if (getIndexInBigImageNormal() == 0)
				previousInBigImageButton.setDisable(true);
			else
				previousInBigImageButton.setDisable(false);
			
			if (getIndexInOptionsForCountriesAndContinents() == 5 && getIndexInBigImageNormal() == Continent.NUMBER_OF_CONTINENTS - 1 ||
					getIndexInOptionsForCountriesAndContinents() != 5 && getIndexInBigImageNormal() == NUM_ALL_COUNTRIES - 1)
				nextInBigImageButton.setDisable(true);
			else nextInBigImageButton.setDisable(false);
		}
		else if(toggleGroupForToggleButtons.getSelectedToggle() == USAToggleButton)
		{
			name = statesOfUSA[index].getNameInEnglish();
			if (getCurrentLanguage() == LANGUAGE.GREEK) labelForBigImage.setText(statesOfUSA[index].getNameInGreek());
			else labelForBigImage.setText(name);
			
			if (getIndexInBigImageNormal() == 0)
				previousInBigImageButton.setDisable(true);
			else previousInBigImageButton.setDisable(false);
			
			if (getIndexInBigImageNormal() == StateOfUSA.NUMBER_OF_USA_STATES - 1)
				nextInBigImageButton.setDisable(true);
			else nextInBigImageButton.setDisable(false);
		}
		else if(toggleGroupForToggleButtons.getSelectedToggle() == greeceToggleButton)
		{
			if (getIndexInOptionsForGreece() == 0)
			{
				name = greekDecAdm[index].getNameInEnglish();
				if (getCurrentLanguage() == LANGUAGE.GREEK) labelForBigImage.setText(greekDecAdm[index].getNameInGreek());
				else labelForBigImage.setText(name);

				labelForBigImage.setFont(font25P);
			}
			else if (getIndexInOptionsForGreece() == 1)
			{
				name = greekRegions[index].getNameInEnglish();
				if (getCurrentLanguage() == LANGUAGE.GREEK) labelForBigImage.setText(greekRegions[index].getNameInGreek());
				else labelForBigImage.setText(name);
			}

			if (getIndexInListViewForGreece() == 0) previousInBigImageButton.setDisable(true);
			else previousInBigImageButton.setDisable(false);

			if (getIndexInOptionsForGreece() == 0 && getIndexInListViewForGreece() == GreekDecentralizedAdministration.NUMBER_OF_GREEK_DECENTRALIZED_ADMINISTRATIONS - 1 ||
			    getIndexInOptionsForGreece() == 1 && getIndexInListViewForGreece() == GreekRegion.NUMBER_OF_GREEK_REGIONS - 1 ||
			    getIndexInOptionsForGreece() == 2 && getIndexInListViewForGreece() == GreekRegionalUnit.NUMBER_OF_GREEK_REGIONAL_UNITS - 1 ||
			    getIndexInOptionsForGreece() == 3 && getIndexInListViewForGreece() == NUM_ALL_GREEK_MUNICIPALITIES - 1)
					nextInBigImageButton.setDisable(true);
			else nextInBigImageButton.setDisable(false);
		}

		if (labelForBigImage.getText().length() < 55 && labelForBigImage.getFont() != font25P) labelForBigImage.setFont(font25P);
		
		if(type == BigImageType.LOGOS_FOR_GREEK_REGIONS) bigImageSize = "";
		else if (bigImage.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X500_IMAGES && !bigImageSize.equals("x500")) bigImageSize = "x500";
		else if (bigImage.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X1000_IMAGES && !bigImageSize.equals("x1000")) bigImageSize = "x1000";
		else bigImageSize = "x2000";

		if (type == BigImageType.FLAG_FOR_COUNTRIES) newBigImagePath = getImageStuff().getImagePath(ImageType.COUNTRY_FLAG, bigImageSize, name);
		else if (type == BigImageType.COAT_OF_ARMS_FOR_COUNTRIES)
		{
			newBigImagePath = getImageStuff().getImagePath(ImageType.COUNTRY_COAT_OF_ARMS, bigImageSize, name);

			if(getImageStuff().requiresWhiteBackground(name))
			{
				rectangleForBigImage.setFill(WHITE_TRANSPARENT);
				labelForBigImage.setTextFill(BLACK);
			}
			else
			{
				rectangleForBigImage.setFill(LIGHT_BLACK);
				labelForBigImage.setTextFill(WHITE);
			}
		}
		else if (type == BigImageType.LOCATION_FOR_COUNTRIES) newBigImagePath = getImageStuff().getImagePath(ImageType.COUNTRY_LOCATION, bigImageSize, name);
		else if (type == BigImageType.LOCATION_FOR_CONTINENTS) newBigImagePath = getImageStuff().getImagePath(ImageType.CONTINENT_LOCATION, bigImageSize, name);
		else if (type == BigImageType.FLAG_FOR_USA) newBigImagePath = getImageStuff().getImagePath(ImageType.USA_FLAG, bigImageSize, name);
		else if (type == BigImageType.SEAL_FOR_USA) newBigImagePath = getImageStuff().getImagePath(ImageType.USA_SEAL, bigImageSize, name);
		else if (type == BigImageType.LOCATION_FOR_USA) newBigImagePath = getImageStuff().getImagePath(ImageType.USA_LOCATION, bigImageSize, name);
		else if(type == BigImageType.LOCATION_FOR_GREEK_DEC_ADMIN) newBigImagePath = getImageStuff().getImagePath(ImageType.GREECE_DEC_ADM_LOCATION, bigImageSize, name);
		else if(type == BigImageType.LOCATION_FOR_GREEK_REGIONS) newBigImagePath = getImageStuff().getImagePath(ImageType.GREECE_REGION_LOCATION, bigImageSize, name);
		else if (type == BigImageType.LOGOS_FOR_GREEK_REGIONS) newBigImagePath = getImageStuff().getImagePath(ImageType.GREECE_REGION_LOGO, bigImageSize, name);

		if (!previousBigImagePath.equals(newBigImagePath))
		{
			previousBigImagePath = newBigImagePath;
			bigImage.setImage(new Image(newBigImagePath));
		}

		if (type == BigImageType.LOGOS_FOR_GREEK_REGIONS)
		{
			bigImage.setFitHeight(bigImage.getFitWidth() / 2.25);

			rectangleForBigImage.setHeight(bigImage.getFitHeight() + (17.0 / 108.0) * mainScene.getHeight());
			rectangleForBigImage.setLayoutY(mainScene.getHeight() / 2.0 - rectangleForBigImage.getHeight() / 2.0);

			bigImage.setLayoutY(rectangleForBigImage.getLayoutY() + (rectangleForBigImage.getHeight() - (7.0 / 54.0) * mainScene.getHeight()) / 2.0 - bigImage.getFitHeight() / 2.0);
		}
		else
		{
			bigImage.setFitHeight(0.7 * mainScene.getHeight());

			rectangleForBigImage.setHeight(bigImage.getFitHeight() + (4.0 / 27.0) * mainScene.getHeight());
			rectangleForBigImage.setLayoutY(mainScene.getHeight() / 2.0 - rectangleForBigImage.getHeight() / 2.0);

			bigImage.setLayoutY(rectangleForBigImage.getLayoutY() + (1.0 / 54.0) * mainScene.getHeight());
		}

		labelForBigImage.setLayoutY(bigImage.getLayoutY() + bigImage.getFitHeight() + (1.0 / 200.0) * mainScene.getHeight());
		previousInBigImageButton.setLayoutY(rectangleForBigImage.getLayoutY() + rectangleForBigImage.getHeight() - previousInBigImageButton.getBoundsInLocal().getHeight() - (1.0 / 72.0) * mainScene.getHeight());
		nextInBigImageButton.setLayoutY(rectangleForBigImage.getLayoutY() + rectangleForBigImage.getHeight() - nextInBigImageButton.getBoundsInLocal().getHeight() - (1.0 / 72.0) * mainScene.getHeight());
		exitBigImageButton.setLayoutY(rectangleForBigImage.getLayoutY() + rectangleForBigImage.getHeight() - exitBigImageButton.getBoundsInLocal().getHeight() - (1.0 / 54.0) * mainScene.getHeight());
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

					if (rectangleForBigImage.getFill().equals(WHITE_TRANSPARENT))
					{
						rectangleForBigImage.setFill(LIGHT_BLACK);
						labelForBigImage.setTextFill(WHITE);
					}
					setupBigImageNormal(type, index);
				});

				scaleTransitionForBigImage.playFromStart();
				scaleTransitionForRectangleForBigImage.playFromStart();
			}
			else
			{
				processBigImageNormal(type, index);
				recalculateUI(mainScene.getWidth(), mainScene.getHeight());

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
							soundButton.setDisable(true);
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

					getAudioStuff().playPopUpSound();
					scaleTransitionForRectangleForBigImage.playFromStart();
				}
			}
		}
		else
		{
			processBigImageNormal(type, index);
			recalculateUI(mainScene.getWidth(), mainScene.getHeight());

			rectangleForBigImage.setScaleX(1);
			rectangleForBigImage.setScaleY(1);
			bigImage.setScaleX(1);
			bigImage.setScaleY(1);
			labelForBigImage.setScaleX(1);
			labelForBigImage.setScaleY(1);
			exitBigImageButton.setScaleX(1);
			exitBigImageButton.setScaleY(1);
			previousInBigImageButton.setScaleX(1);
			previousInBigImageButton.setScaleY(1);
			nextInBigImageButton.setScaleX(1);
			nextInBigImageButton.setScaleY(1);

			if(type != BigImageType.LOGOS_FOR_GREEK_REGIONS)
			{
				zoomInBigImageButton.setScaleX(1);
				zoomInBigImageButton.setScaleY(1);
				zoomOutBigImageButton.setScaleX(1);
				zoomOutBigImageButton.setScaleY(1);
			}

			hBoxMainForCountriesAndContinents.setDisable(true);
			hBoxMainForUSA.setDisable(true);
			hBoxMainForGreece.setDisable(true);
			hBoxForToggleButtons.setDisable(true);
			backButton.setDisable(true);
			soundButton.setDisable(true);
		}
	}

	private void processBigImagePreview(BigImageType type, int index)
	{
		bigImageStatus = 1;

		String name = "";
		if (toggleGroupForToggleButtons.getSelectedToggle() == countriesAndContinentsToggleButton)
		{
			if (getIndexInOptionsForCountriesAndContinents() == 0 ||
			    getIndexInOptionsForCountriesAndContinents() == 1)
				name = countries[index].getNameInEnglish();
			else if (getIndexInOptionsForCountriesAndContinents() == 5)
				name = continents[index].getNameInEnglish();
		}
		else if(toggleGroupForToggleButtons.getSelectedToggle() == USAToggleButton) name = statesOfUSA[index].getNameInEnglish();
		else if(toggleGroupForToggleButtons.getSelectedToggle() == greeceToggleButton)
		{
			if (getIndexInOptionsForGreece() == 0) name = greekDecAdm[index].getNameInEnglish();
			else if (getIndexInOptionsForGreece() == 1) name = greekRegions[index].getNameInEnglish();
		}

		if(type == BigImageType.LOGOS_FOR_GREEK_REGIONS) bigImageSize = "";
		else if (bigImage.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X500_IMAGES && !bigImageSize.equals("x500")) bigImageSize = "x500";
		else if (bigImage.getFitWidth() <= getImageStuff().MAX_WIDTH_FOR_X1000_IMAGES && !bigImageSize.equals("x1000")) bigImageSize = "x1000";
		else bigImageSize = "x2000";

		if (type == BigImageType.FLAG_FOR_COUNTRIES) newBigImagePath = getImageStuff().getImagePath(ImageType.COUNTRY_FLAG, bigImageSize, name);
		else if (type == BigImageType.COAT_OF_ARMS_FOR_COUNTRIES)
		{
			newBigImagePath = getImageStuff().getImagePath(ImageType.COUNTRY_COAT_OF_ARMS, bigImageSize, name);

			if(getImageStuff().requiresWhiteBackground(name))
			{
				rectangleForBigImage.setFill(WHITE_TRANSPARENT);
				labelForBigImage.setTextFill(BLACK);
			}
			else
			{
				rectangleForBigImage.setFill(LIGHT_BLACK);
				labelForBigImage.setTextFill(WHITE);
			}
		}
		else if (type == BigImageType.LOCATION_FOR_COUNTRIES) newBigImagePath = getImageStuff().getImagePath(ImageType.COUNTRY_LOCATION, bigImageSize, name);
		else if (type == BigImageType.LOCATION_FOR_CONTINENTS) newBigImagePath = getImageStuff().getImagePath(ImageType.CONTINENT_LOCATION, bigImageSize, name);
		else if (type == BigImageType.FLAG_FOR_USA) newBigImagePath = getImageStuff().getImagePath(ImageType.USA_FLAG, bigImageSize, name);
		else if (type == BigImageType.SEAL_FOR_USA) newBigImagePath = getImageStuff().getImagePath(ImageType.USA_SEAL, bigImageSize, name);
		else if (type == BigImageType.LOCATION_FOR_USA) newBigImagePath = getImageStuff().getImagePath(ImageType.USA_LOCATION, bigImageSize, name);
		else if(type == BigImageType.LOCATION_FOR_GREEK_DEC_ADMIN) newBigImagePath = getImageStuff().getImagePath(ImageType.GREECE_DEC_ADM_LOCATION, bigImageSize, name);
		else if(type == BigImageType.LOCATION_FOR_GREEK_REGIONS) newBigImagePath = getImageStuff().getImagePath(ImageType.GREECE_REGION_LOCATION, bigImageSize, name);
		else if (type == BigImageType.LOGOS_FOR_GREEK_REGIONS) newBigImagePath = getImageStuff().getImagePath(ImageType.GREECE_REGION_LOGO, bigImageSize, name);

		if (!previousBigImagePath.equals(newBigImagePath))
		{
			previousBigImagePath = newBigImagePath;
			bigImage.setImage(new Image(newBigImagePath));
		}

		if (type == BigImageType.LOGOS_FOR_GREEK_REGIONS)
		{
			bigImage.setFitHeight(bigImage.getFitWidth() / 2.25);
			bigImage.setLayoutY(mainScene.getHeight() / 2.0 - bigImage.getFitHeight() / 2.0);
		}
		else
		{
			bigImage.setFitHeight(0.7 * mainScene.getHeight());
			bigImage.setLayoutY(mainScene.getHeight() / 2.0 - bigImage.getFitHeight() / 2.0);
		}

		if (type == BigImageType.FLAG_FOR_COUNTRIES && !name.equals("Βατικανό") && !name.equals("Βέλγιο") && !name.equals("Ελβετία") && !name.equals("Μονακό") && !name.equals("Νεπάλ") && !name.equals("Νίγηρας"))
		{
			rectangleForBigImage.setHeight(0.8 * bigImage.getFitHeight());
			rectangleForBigImage.setLayoutY(mainScene.getHeight() / 2.0 - rectangleForBigImage.getHeight() / 2.0);
		}
		else
		{
			rectangleForBigImage.setHeight(bigImage.getFitHeight() + (1.0 / 27.0) * mainScene.getHeight());
			rectangleForBigImage.setLayoutY(mainScene.getHeight() / 2.0 - rectangleForBigImage.getHeight() / 2.0);
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

					if (rectangleForBigImage.getFill().equals(WHITE_TRANSPARENT))
					{
						rectangleForBigImage.setFill(LIGHT_BLACK);
						labelForBigImage.setTextFill(WHITE);
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

				getAudioStuff().playPopUpSound();
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
						soundButton.setDisable(false);

						if (rectangleForBigImage.getFill().equals(WHITE_TRANSPARENT))
						{
							rectangleForBigImage.setFill(LIGHT_BLACK);
							labelForBigImage.setTextFill(WHITE);
						}
					});
					scaleTransitionForRectangleForBigImage.playFromStart();
				});

				getAudioStuff().playMinimizeSound();
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

				if (rectangleForBigImage.getFill().equals(WHITE_TRANSPARENT))
				{
					scaleTransitionForRectangleForBigImage.setOnFinished(e ->
					{
						rectangleForBigImage.setFill(LIGHT_BLACK);
						labelForBigImage.setTextFill(WHITE);

						scaleTransitionForRectangleForBigImage.setOnFinished(ev -> {});
					});
				}

				getAudioStuff().playMinimizeSound();
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
				exitBigImageButton.setScaleX(0);
				exitBigImageButton.setScaleY(0);
				zoomInBigImageButton.setScaleX(0);
				zoomInBigImageButton.setScaleY(0);
				zoomOutBigImageButton.setScaleX(0);
				zoomOutBigImageButton.setScaleY(0);

				hBoxMainForCountriesAndContinents.setDisable(false);
				hBoxMainForUSA.setDisable(false);
				hBoxMainForGreece.setDisable(false);
				hBoxForToggleButtons.setDisable(false);
				backButton.setDisable(false);
				soundButton.setDisable(false);
			}
			else bigImageStatus = 0;

			if (rectangleForBigImage.getFill().equals(WHITE_TRANSPARENT))
			{
				rectangleForBigImage.setFill(LIGHT_BLACK);
				labelForBigImage.setTextFill(WHITE);
			}
		}
	}

	private void setNamesForGridPaneLabels()
	{
		if(toggleGroupForToggleButtons.getSelectedToggle() == countriesAndContinentsToggleButton)
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
		else if(toggleGroupForToggleButtons.getSelectedToggle() == USAToggleButton)
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
		else if(toggleGroupForToggleButtons.getSelectedToggle() == greeceToggleButton)
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
		else if(toggleGroupForToggleButtons.getSelectedToggle() == attractionsToggleButton)
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
		countriesAndContinentsToggleButton.setText(languageResourceBundle.getString("continentsAndCountries"));
		USAToggleButton.setText(languageResourceBundle.getString("usa"));
		greeceToggleButton.setText(languageResourceBundle.getString("greece"));
		attractionsToggleButton.setText(languageResourceBundle.getString("attractions"));

		//TOGGLE BUTTONS TOOLTIP
		countriesAndContinentsToggleButton.getTooltip().setText(languageResourceBundle.getString("countriesToggleButtonTooltip"));
		USAToggleButton.getTooltip().setText(languageResourceBundle.getString("USAStatesToggleButtonTooltip"));
		greeceToggleButton.getTooltip().setText(languageResourceBundle.getString("greekCountiesToggleButtonTooltip"));
		attractionsToggleButton.getTooltip().setText(languageResourceBundle.getString("attractionsToggleButtonTooltip"));

		//BACK BUTTON
		backButton.setText(languageResourceBundle.getString("backButton"));
		backButton.getTooltip().setText(languageResourceBundle.getString("backButtonTooltipReturnToPreviousScreen"));

		//ICON TOOLTIPS
		soundButton.getTooltip().setText(languageResourceBundle.getString("soundOptionsTooltip"));

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

	public class LabelGridCell extends GridCell<Integer>
	{
		private ImageView imageView;
		private Label label;
		String imageName, text;
		int i;

		LabelGridCell()
		{
			imageView = new CustomImageView(false, true, false, false, null);

			label = new Label();
			label.setGraphic(imageView);
			label.setContentDisplay(ContentDisplay.TOP);
			label.setTextAlignment(TextAlignment.CENTER);
			label.setAlignment(Pos.BASELINE_CENTER);
			label.setTextFill(BROWN);
			label.setCursor(Cursor.HAND);
		}

		protected void updateItem(Integer item, boolean empty)
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

				if(toggleGroupForToggleButtons.getSelectedToggle() == countriesAndContinentsToggleButton)
				{
					imageName = countries[item].getNameInEnglish();
					
					if(getCurrentLanguage() == LANGUAGE.GREEK) text = countries[item].getNameInGreek();
					else text = countries[item].getNameInEnglish();

					if(typeOfGridViewImagesForCountriesAndContinents == GridViewImagesForCountriesAndContinentsType.FLAG)
					{
						imageView.setImage(getImageStuff().getImage(ImageType.COUNTRY_FLAG, "x250", imageName, false));

						label.setOnMouseClicked(e ->
						{
							setIndexInBigImageNormal(i);
							setupBigImageNormal(BigImageType.FLAG_FOR_COUNTRIES, item);
						});
					}
					else if(typeOfGridViewImagesForCountriesAndContinents == GridViewImagesForCountriesAndContinentsType.COAT_OF_ARMS)
					{
						imageView.setImage(getImageStuff().getImage(ImageType.COUNTRY_COAT_OF_ARMS, "x250", imageName, false));

						label.setOnMouseClicked(e ->
						{
							setIndexInBigImageNormal(i);
							setupBigImageNormal(BigImageType.COAT_OF_ARMS_FOR_COUNTRIES, item);
						});
					}
					else
					{
						imageView.setImage(getImageStuff().getImage(ImageType.COUNTRY_LOCATION,  "x250", imageName, false));

						label.setOnMouseClicked(e ->
						{
							setIndexInBigImageNormal(i);
							setupBigImageNormal(BigImageType.LOCATION_FOR_COUNTRIES, item);
						});
					}

					label.setPrefWidth(gridViewForImagesForCountriesAndContinents.getCellWidth());
					label.setPrefHeight(gridViewForImagesForCountriesAndContinents.getCellHeight());
				}
				else if(toggleGroupForToggleButtons.getSelectedToggle() == USAToggleButton)
				{
					imageName = statesOfUSA[item].getNameInEnglish();
					
					if(getCurrentLanguage() == LANGUAGE.GREEK) text = statesOfUSA[item].getNameInGreek();
					else text = statesOfUSA[item].getNameInEnglish();

					if(typeOfGridViewImagesForUSA == GridViewImagesForUSAType.FLAG)
					{
						imageView.setImage(getImageStuff().getImage(ImageType.USA_FLAG, "x250", imageName, false));

						label.setOnMouseClicked(e ->
						{
							setIndexInBigImageNormal(i);
							setupBigImageNormal(BigImageType.FLAG_FOR_USA, item);
						});
					}
					else if(typeOfGridViewImagesForUSA == GridViewImagesForUSAType.SEAL)
					{
						imageView.setImage(getImageStuff().getImage(ImageType.USA_SEAL, "x250", imageName, false));

						label.setOnMouseClicked(e ->
						{
							setIndexInBigImageNormal(i);
							setupBigImageNormal(BigImageType.SEAL_FOR_USA, item);
						});
					}
					else
					{
						imageView.setImage(getImageStuff().getImage(ImageType.USA_LOCATION, "x250", imageName, false));

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

				label.setText(text);
				label.setFont(font25P);

				if (text != null && text.length() > 18)
				{
					Tooltip tooltip = new CustomTooltip();
					tooltip.setText(text);
					tooltip.setFont(font25P);
					label.setTooltip(tooltip);
				}
				else label.setTooltip(null);

				if ((i / 4) % 2 == 0 && i % 2 == 0 || (i / 4) % 2 == 1 && i % 2 == 1) label.setStyle(GRID_ITEM_EVEN);
				else label.setStyle(GRID_ITEM_ODD);

				this.setGraphic(label);
			}
		}
	}

	private boolean makeLeftMove(Toggle oldButton, Toggle newButton)
	{
		return oldButton == countriesAndContinentsToggleButton && (newButton == USAToggleButton || newButton == greeceToggleButton || newButton == attractionsToggleButton) ||
				oldButton == USAToggleButton && (newButton == greeceToggleButton || newButton == attractionsToggleButton) ||
				oldButton == greeceToggleButton && newButton == attractionsToggleButton;
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
