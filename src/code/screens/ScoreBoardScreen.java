package code.screens;

import code.core.*;
import code.dataStructures.Game;
import code.dataStructures.Player;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;

import java.time.LocalDateTime;

import static code.core.GlobalVariables.*;

/**
 * Created by John on 30/1/2017.
 */

public class ScoreBoardScreen extends CoreScreenWithMovingBackground
{
	ObservableList<Game> gamesObservableList;
	ObservableList<String> playerNamesObservableList;
	
	private CustomImageView previousChalkboardImage, woodPanelFor5IconsImage, titleImage, backArrowImage, controlImage;
	private Label titleLabel, controlLabel;
	private InnerShadow innerShadow;
	private DropShadow dropShadow;
	private HBox hBoxFor5Icons;
	private VBox vBoxForControlButton, vBoxForPlayerStatistics;
	private ComboBox<String> comboBoxForPlayerNames;
	private CustomButton backButton, deleteButton;
	private GridPane gridPaneForPlayerStatistics;
	private TableView<Game> tableView;
	private TableColumn<Game, String> playerNameColumn, selectedCategoriesColumn;
	private TableColumn<Game, LocalDateTime> gameStartedTimeColumn;
	private TableColumn<Game, Integer> gameDurationColumn, numberOfAllQuestionsColumn, numberOfCorrectQuestionsColumn, scorePointsColumn, maxComboColumn;
	private TableColumn<Game, GAMEMODE> gameModeColumn;
	private TableColumn<Game, Double> averageAnswerTimeColumn;
	private TableColumn<Game, Boolean> isCountdownEnabledColumn;
	
	private PopOver popOverForSelectedCategories;
	private CheckBoxForPopOver continentsAndCountriesCheckBox, usaCheckBox, greeceCheckBox, attractionsCheckBox;
	private CheckBoxForPopOver[] continentsAndCountriesCheckBoxes, usaCheckBoxes, greeceCheckBoxes;
	private GridPane gridPaneForSelectedCategories;
	
	private Label[][] gridPaneLabels;
	
	private RotateTransition rotateTransitionForControlImage;
	private TranslateTransition translateTransitionForTableView, translateTransitionForVBoxForPlayerStatistics,
			translateTransitionForTitleImage, translateTransitionForTitleLabel, translateTransitionForVBoxForSound, translateTransitionForWoodImageFor5Icons;
	private FadeTransition fadeTransitionForMovingEarthImage;
	private ScaleTransition scaleTransitionForDeleteButton, scaleTransitionForGridPane, scaleTransitionForTableView,
			scaleTransitionForVBoxForPlayerStatistics, scaleTransitionForTitleLabel, scaleTransitionForHBoxFor5Icons,
			scaleTransitionForBackButton, scaleTransitionForVBoxForControlButton, scaleTransitionForControlLabel;
	private Timeline timeLineToShowAllStuff, timeLineToHideAllStuff, timelineToShowSoundOptions, timelineToHideSoundOptions;
	
	private Font fontBig, fontForTooltips;
	
	private boolean playerStatisticsScreen = false;
	
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
			titleLabel.setLayoutY(0.0641 * height);
			
			woodPanelFor5IconsImage.setLayoutY(0.0361 * height);
			
			hBoxFor5Icons.setLayoutY(0.0917 * height);
			
			vBoxForSound.setLayoutY(0.0648 * height);
			vBoxForSound.setPrefSize(0.1146 * width, 0.1296 * height);
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
		
		if(!playerStatisticsScreen) titleLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0495 * width));
		else titleLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0443 * width));
		
		//TABLE VIEW
		tableView.setPrefSize(0.8854 * width, 0.5 * height);
		tableView.setLayoutX(width / 2.0 - tableView.getPrefWidth() / 2.0);
		tableView.setLayoutY(0.2315 * height);
		tableView.setStyle("-fx-font:" + 0.0094 * width + "px \"Comic Sans MS\";" +
						"-fx-background-color: #AB5C3D; -fx-padding:" + 0.0052 * width + ";");
		
		gridPaneForSelectedCategories.setHgap(0.0026 * width);
		gridPaneForSelectedCategories.setVgap(0.0111 * height);
		gridPaneForSelectedCategories.setPadding(new Insets(0.0104 * width));
		
		popOverForSelectedCategories.setCornerRadius(0.0078 * width);
		
//		GUIUtils.autoFitTable(tableView);
		
		//PLAYER STATISTICS
		vBoxForPlayerStatistics.setPrefSize(0.4167 * width, 0.4167 * height);
		vBoxForPlayerStatistics.setLayoutX(width / 2.0 - vBoxForPlayerStatistics.getPrefWidth() / 2.0);
		vBoxForPlayerStatistics.setLayoutY(height / 2.0 - vBoxForPlayerStatistics.getPrefHeight() / 2.0);
		vBoxForPlayerStatistics.setSpacing(0.0185 * height);
		
		comboBoxForPlayerNames.setPrefWidth(0.5 * vBoxForPlayerStatistics.getPrefWidth());
		
		gridPaneForPlayerStatistics.setPrefWidth(vBoxForPlayerStatistics.getPrefWidth());
		
		comboBoxForPlayerNames.setStyle("-fx-font:" + 0.0125 * width + "px \"Comic Sans MS\";");
		gridPaneForPlayerStatistics.setStyle("-fx-background-color: #AB5C3D;" + "-fx-padding: " + 0.0052 * width + ";");
		
		double gridPaneLabelsWidth = 0.5 * gridPaneForPlayerStatistics.getPrefWidth();
		for (int i = 0; i < gridPaneLabels.length; i++)
		{
			gridPaneLabels[i][0].setPrefSize(gridPaneLabelsWidth, gridPaneLabelsWidth);
			gridPaneLabels[i][0].setMaxSize(gridPaneLabelsWidth, gridPaneLabelsWidth);
			gridPaneLabels[i][0].setFont(fontBig);
			
			gridPaneLabels[i][1].setPrefSize(gridPaneLabelsWidth, gridPaneLabelsWidth);
			gridPaneLabels[i][1].setMaxSize(gridPaneLabelsWidth, gridPaneLabelsWidth);
			gridPaneLabels[i][1].setFont(fontBig);
		}
		
		if(tableView.getTranslateX() != 0) tableView.setTranslateX(-1.0 * (tableView.getLayoutX() + tableView.getPrefWidth() + 20));
		else vBoxForPlayerStatistics.setTranslateX(width - vBoxForPlayerStatistics.getLayoutX() + 20);
		
		//DELETE BUTTON
		deleteButton.setPrefWidth(0.3125 * width);
		deleteButton.setLayoutX(width / 2.0 - deleteButton.getPrefWidth() / 2.0);
		deleteButton.setLayoutY(0.7315 * height);
		
		//CONTROL BUTTON
		controlImage.setFitWidth(0.0833 * width);
		vBoxForControlButton.setPrefWidth(0.1402 * width);
		vBoxForControlButton.setLayoutX(width / 2.0 - vBoxForControlButton.getPrefWidth() / 2.0);
		vBoxForControlButton.setLayoutY(0.8148 * height);
		
		//BACK BUTTON ------------------------------------------------------
		backArrowImage.setFitWidth(0.0703 * width);
		backButton.setLayoutY(0.8148 * height);
		
		if (width < 1300) backButton.setLayoutX(0.0313 * width);
		else if (width < 2000) backButton.setLayoutX(0.0339 * width);
		else backButton.setLayoutX(0.0365 * width);
		
		controlLabel.setFont(fontForLabels);
		backButton.setFont(fontForLabels);
		deleteButton.setFont(fontForButtons);
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
	
	public ScoreBoardScreen()
	{
		//FUNDAMENTAL STUFF-------------------------------------------------------------------------------------
		previousChalkboardImage = new CustomImageView(false, false, false, true, CacheHint.SPEED);
		previousChalkboardImage.setLayoutX(0);
		previousChalkboardImage.setLayoutY(0);
		
		movingEarthImage = new CustomImageView(false, true, false, false, null);
		
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
		
		//TABLE VIEW
		tableView = new TableView<>();
		tableView.setEditable(false);
		tableView.setSelectionModel(null);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.setCache(true);
		tableView.setCacheHint(CacheHint.SCALE);
		
		playerNameColumn = new TableColumn<>();
		playerNameColumn.setSortable(false);
		playerNameColumn.setCellValueFactory(param -> param.getValue().playerNameProperty());
		
		gameStartedTimeColumn = new TableColumn<>();
		gameStartedTimeColumn.setSortable(false);
		gameStartedTimeColumn.setCellValueFactory(param -> param.getValue().gameStartedTimeProperty());
		gameStartedTimeColumn.setCellFactory(param ->
				new TableCell<Game, LocalDateTime>()
				{
					protected void updateItem(LocalDateTime item, boolean empty)
					{
						super.updateItem(item, empty);
						
						if (item == null || empty) setText(null);
						else setText(dateTimeForUI.format(item));
					}
				});
		
		gameDurationColumn = new TableColumn<>();
		gameDurationColumn.setSortable(false);
		gameDurationColumn.setCellValueFactory(param -> param.getValue().gameDurationInSecondsProperty());
		gameDurationColumn.setCellFactory(param ->
				new TableCell<>()
				{
					protected void updateItem(Integer item, boolean empty)
					{
						super.updateItem(item, empty);
						
						if (item == null || empty) setText(null);
						else setText(getReadablePlayedTime(item));
					}
				});
		
		gameModeColumn = new TableColumn<>();
		gameModeColumn.setSortable(false);
		gameModeColumn.setCellValueFactory(param -> param.getValue().gameModeProperty());
		gameModeColumn.setCellFactory(param ->
				new TableCell<>()
				{
					protected void updateItem(GAMEMODE item, boolean empty)
					{
						super.updateItem(item, empty);
						
						if (item == null || empty) setText(null);
						else
						{
							if(item == GAMEMODE.CLASSIC_GAMEMODE) setText("Classic");
							else if(item == GAMEMODE.TIME_ATTACK_GAMEMODE) setText("Time Attack");
							else
							{
								if(games.get(this.getIndex()).getLivesForEndless() == 1)
									setText("Endless (" + games.get(this.getIndex()).getLivesForEndless() + " " + languageResourceBundle.getString("life"));
								else setText("Endless (" + games.get(this.getIndex()).getLivesForEndless() + " " + languageResourceBundle.getString("lives") + ")");
							}
						}
					}
				});
		
		selectedCategoriesColumn = new TableColumn<>();
		selectedCategoriesColumn.setSortable(false);
		selectedCategoriesColumn.setCellFactory(param ->
				new TableCell<>()
				{
					protected void updateItem(String item, boolean empty)
					{
						super.updateItem(item, empty);
						
						if (empty) setText(null);
						else
						{
							if(getCurrentLanguage() == LANGUAGE.GREEK) setText("Προβολή");
							else setText("Show");
							
							setCursor(Cursor.HAND);
							setUnderline(true);
							setOnMouseClicked(e -> showSelectedCategories(this, this.getIndex()));
						}
					}
				});
		
		isCountdownEnabledColumn = new TableColumn<>();
		isCountdownEnabledColumn.setSortable(false);
		isCountdownEnabledColumn.setCellValueFactory(param -> param.getValue().isCountdownEnabledProperty());
		isCountdownEnabledColumn.setCellFactory(param ->
				new TableCell<Game, Boolean>()
				{
					protected void updateItem(Boolean item, boolean empty)
					{
						super.updateItem(item, empty);
						
						if (item == null || empty) setText(null);
						else
						{
							if(games.get(this.getIndex()).getGameMode() != GAMEMODE.TIME_ATTACK_GAMEMODE)
							{
								if(item) setText("\u2713");
								else setText("\u274C");
							}
							else setText("-");
						}
					}
				});
		
		numberOfAllQuestionsColumn = new TableColumn<>();
		numberOfAllQuestionsColumn.setSortable(false);
		numberOfAllQuestionsColumn.setCellValueFactory(param -> param.getValue().numberOfAllQuestionsProperty());
		
		numberOfCorrectQuestionsColumn = new TableColumn<>();
		numberOfCorrectQuestionsColumn.setSortable(false);
		numberOfCorrectQuestionsColumn.setCellValueFactory(param -> param.getValue().numberOfCorrectQuestionsProperty());
		
		scorePointsColumn = new TableColumn<>();
		scorePointsColumn.setSortable(false);
		scorePointsColumn.setCellValueFactory(param -> param.getValue().scorePointsProperty());
		
		maxComboColumn = new TableColumn<>();
		maxComboColumn.setSortable(false);
		maxComboColumn.setCellValueFactory(param -> param.getValue().maxComboProperty());
		
		averageAnswerTimeColumn = new TableColumn<>();
		averageAnswerTimeColumn.setSortable(false);
		averageAnswerTimeColumn.setCellValueFactory(param -> param.getValue().averageAnswerTimeProperty());
		averageAnswerTimeColumn.setCellFactory(param ->
			new TableCell<Game, Double>()
			{
				protected void updateItem(Double item, boolean empty)
				{
					super.updateItem(item, empty);
					
					if (item == null || empty) setText(null);
					else
					{
						if(getCurrentLanguage() == LANGUAGE.GREEK) setText(numberFormatForUI.format(item) + " δευτ.");
						else setText(numberFormatForUI.format(item) + " sec.");
					}
				}
			});
		
		tableView.getColumns().setAll(playerNameColumn, gameModeColumn, selectedCategoriesColumn, isCountdownEnabledColumn,
				numberOfAllQuestionsColumn, numberOfCorrectQuestionsColumn, scorePointsColumn,
				maxComboColumn, averageAnswerTimeColumn, gameStartedTimeColumn, gameDurationColumn);
		
		deleteButton = new CustomButton();
		deleteButton.setStyle("-fx-background-color: transparent");
		deleteButton.setTextFill(Color.valueOf("#7A301B"));
		deleteButton.setUnderline(true);
		deleteButton.setTextAlignment(TextAlignment.CENTER);
		deleteButton.setCache(true);
		deleteButton.setCacheHint(CacheHint.SCALE);
		
		//SELECTED CATEGORIES
		continentsAndCountriesCheckBox = new CheckBoxForPopOver();
		usaCheckBox = new CheckBoxForPopOver();
		greeceCheckBox = new CheckBoxForPopOver();
		attractionsCheckBox = new CheckBoxForPopOver();
		
		continentsAndCountriesCheckBoxes = new CheckBoxForPopOver[11];
		usaCheckBoxes = new CheckBoxForPopOver[9];
		greeceCheckBoxes = new CheckBoxForPopOver[7];
		
		for(int i = 0; i < continentsAndCountriesCheckBoxes.length; i++) continentsAndCountriesCheckBoxes[i] = new CheckBoxForPopOver();
		for(int i = 0; i < usaCheckBoxes.length; i++) usaCheckBoxes[i] = new CheckBoxForPopOver();
		for(int i = 0; i < greeceCheckBoxes.length; i++) greeceCheckBoxes[i] = new CheckBoxForPopOver();
		
		gridPaneForSelectedCategories = new GridPane();
		
		Insets pos1 = new Insets(0, 0, 0, 0.0198 * stage.getWidth());
		
		gridPaneForSelectedCategories.add(continentsAndCountriesCheckBox, 0, 0);
		for(int i = 0; i < continentsAndCountriesCheckBoxes.length; i++)
		{
			gridPaneForSelectedCategories.add(continentsAndCountriesCheckBoxes[i], 0, i + 1);
			gridPaneForSelectedCategories.setMargin(continentsAndCountriesCheckBoxes[i], pos1);
		}
		
		gridPaneForSelectedCategories.add(usaCheckBox, 1, 0);
		for(int i = 0; i < usaCheckBoxes.length; i++)
		{
			gridPaneForSelectedCategories.add(usaCheckBoxes[i], 1, i + 1);
			gridPaneForSelectedCategories.setMargin(usaCheckBoxes[i], pos1);
		}
		
		gridPaneForSelectedCategories.add(greeceCheckBox, 2, 0);
		for(int i = 0; i < greeceCheckBoxes.length; i++)
		{
			gridPaneForSelectedCategories.add(greeceCheckBoxes[i], 2, i + 1);
			gridPaneForSelectedCategories.setMargin(greeceCheckBoxes[i], pos1);
		}
		
		gridPaneForSelectedCategories.add(attractionsCheckBox, 3, 0);
		
		popOverForSelectedCategories = new PopOver(gridPaneForSelectedCategories);
		popOverForSelectedCategories.setDetachable(false);
		popOverForSelectedCategories.setAutoFix(true);
		popOverForSelectedCategories.setAutoHide(true);
		popOverForSelectedCategories.setHideOnEscape(true);
		popOverForSelectedCategories.setAnimated(true);
		popOverForSelectedCategories.setFadeInDuration(Duration.millis(200));
		popOverForSelectedCategories.setFadeOutDuration(Duration.millis(200));
		
		//PLAYER STATISTICS
		comboBoxForPlayerNames = new ComboBox<>();
		comboBoxForPlayerNames.setCursor(Cursor.HAND);
		
		gridPaneForPlayerStatistics = new GridPane();
		gridPaneForPlayerStatistics.setAlignment(Pos.CENTER);
		
		gridPaneLabels = new Label[6][2];
		for(int i = 0; i < gridPaneLabels.length; i++)
		{
			gridPaneLabels[i][0] = new Label();
			gridPaneLabels[i][1] = new Label();
			
			if(i % 2 == 0)
			{
				gridPaneLabels[i][0].setStyle("-fx-background-color: #FFEBCD; -fx-text-fill: #323232;");
				gridPaneLabels[i][1].setStyle("-fx-background-color: #FFEBCD; -fx-text-fill: #323232;");
			}
			else
			{
				gridPaneLabels[i][0].setStyle("-fx-background-color: #EDD1A6; -fx-text-fill: #323232;");
				gridPaneLabels[i][1].setStyle("-fx-background-color: #EDD1A6; -fx-text-fill: #323232;");
			}
			
			gridPaneForPlayerStatistics.add(gridPaneLabels[i][0], 0, i);
			gridPaneForPlayerStatistics.add(gridPaneLabels[i][1], 1, i);
		}
		
		vBoxForPlayerStatistics = new VBox();
		vBoxForPlayerStatistics.setAlignment(Pos.CENTER);
		vBoxForPlayerStatistics.getChildren().addAll(comboBoxForPlayerNames, gridPaneForPlayerStatistics);
		
		//CONTROL BUTTON
		controlImage = new CustomImageView(true, true, true, true, CacheHint.ROTATE);
		controlImage.setRotate(180);
		controlImage.setCursor(Cursor.HAND);
		
		controlLabel = new Label();
		controlLabel.setTextAlignment(TextAlignment.CENTER);
		controlLabel.setTextFill(Color.valueOf("#7A301B"));
		controlLabel.setCursor(Cursor.HAND);
		controlLabel.setCache(true);
		controlLabel.setCacheHint(CacheHint.SCALE);
		
		vBoxForControlButton = new VBox();
		vBoxForControlButton.setAlignment(Pos.CENTER);
		vBoxForControlButton.getChildren().addAll(controlImage, controlLabel);
		
		//DROP SHADOW EFFECT
		dropShadow = new DropShadow();
		woodenFrameImage.setEffect(dropShadow);
		titleImage.setEffect(dropShadow);
		woodPanelFor5IconsImage.setEffect(dropShadow);
		
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
		
		nodesPane.getChildren().addAll(previousChalkboardImage, movingEarthImage, titleImage, titleLabel,
				woodPanelFor5IconsImage, vBoxForSound, backButton, tableView, vBoxForPlayerStatistics, deleteButton, vBoxForControlButton, hBoxFor5Icons);
		
		if(animationsUsed != ANIMATIONS.NO)
		{
			setupLimitedAnimations();
			if(animationsUsed == ANIMATIONS.ALL) setupAdvancedAnimations();
		}
		
		setupListeners();
	}
	
	private void showSelectedCategories(Node node, int index)
	{
		continentsAndCountriesCheckBox.setSelected(false);
		continentsAndCountriesCheckBox.setIndeterminate(false);
		usaCheckBox.setSelected(false);
		usaCheckBox.setIndeterminate(false);
		greeceCheckBox.setSelected(false);
		greeceCheckBox.setIndeterminate(false);
		attractionsCheckBox.setSelected(false);
		
		for(CheckBoxForPopOver check: continentsAndCountriesCheckBoxes) check.setSelected(false);
		for(CheckBoxForPopOver check: usaCheckBoxes) check.setSelected(false);
		for(CheckBoxForPopOver check: greeceCheckBoxes) check.setSelected(false);
		
		for(int cat: games.get(index).getQuestionCategories())
		{
			switch(cat)
			{
				case 0: continentsAndCountriesCheckBoxes[0].setSelected(true); break;
				case 1: continentsAndCountriesCheckBoxes[1].setSelected(true); break;
				case 2: continentsAndCountriesCheckBoxes[2].setSelected(true); break;
				case 3: continentsAndCountriesCheckBoxes[3].setSelected(true); break;
				case 4: continentsAndCountriesCheckBoxes[4].setSelected(true); break;
				case 5: continentsAndCountriesCheckBoxes[5].setSelected(true); break;
				case 6: continentsAndCountriesCheckBoxes[6].setSelected(true); break;
				case 7: continentsAndCountriesCheckBoxes[7].setSelected(true); break;
				case 8: continentsAndCountriesCheckBoxes[8].setSelected(true); break;
				case 9: continentsAndCountriesCheckBoxes[9].setSelected(true); break;
				case 10: continentsAndCountriesCheckBoxes[10].setSelected(true); break;
				case 11: usaCheckBoxes[0].setSelected(true); break;
				case 12: usaCheckBoxes[1].setSelected(true); break;
				case 13: usaCheckBoxes[2].setSelected(true); break;
				case 14: usaCheckBoxes[3].setSelected(true); break;
				case 15: usaCheckBoxes[4].setSelected(true); break;
				case 16: usaCheckBoxes[5].setSelected(true); break;
				case 17: usaCheckBoxes[6].setSelected(true); break;
				case 18: usaCheckBoxes[7].setSelected(true); break;
				case 19: usaCheckBoxes[8].setSelected(true); break;
				case 20: greeceCheckBoxes[0].setSelected(true); break;
				case 21: greeceCheckBoxes[1].setSelected(true); break;
				case 22: greeceCheckBoxes[2].setSelected(true); break;
				case 23: greeceCheckBoxes[3].setSelected(true); break;
				case 24: greeceCheckBoxes[4].setSelected(true); break;
				case 25: greeceCheckBoxes[5].setSelected(true); break;
				case 26: greeceCheckBoxes[6].setSelected(true); break;
				case 27: greeceCheckBoxes[7].setSelected(true); break;
				case 28: attractionsCheckBox.setSelected(true); break;
			}
		}
		
		int counter = 0;
		
		for(CheckBoxForPopOver check: continentsAndCountriesCheckBoxes) if(check.isSelected()) counter++;
		if(counter == continentsAndCountriesCheckBoxes.length) continentsAndCountriesCheckBox.setSelected(true);
		else if(counter > 0) continentsAndCountriesCheckBox.setIndeterminate(true);
		
		counter = 0;
		for(CheckBoxForPopOver check: usaCheckBoxes) if(check.isSelected()) counter++;
		if(counter == usaCheckBoxes.length) usaCheckBox.setSelected(true);
		else if(counter > 0) usaCheckBox.setIndeterminate(true);
		
		counter = 0;
		for(CheckBoxForPopOver check: greeceCheckBoxes) if(check.isSelected()) counter++;
		if(counter == greeceCheckBoxes.length) greeceCheckBox.setSelected(true);
		else if(counter > 0) greeceCheckBox.setIndeterminate(true);
		
		popOverForSelectedCategories.show(node);
		if(!((Parent)popOverForSelectedCategories.getSkin().getNode()).getStylesheets().contains(popOverCSS))
			((Parent)popOverForSelectedCategories.getSkin().getNode()).getStylesheets().add(popOverCSS);
	}
	
	protected void setupListeners()
	{
		comboBoxForPlayerNames.valueProperty().addListener((observable, oldValue, newValue) ->
		{
			if(newValue != null && oldValue != newValue) repaintPlayerStatistics(comboBoxForPlayerNames.getSelectionModel().getSelectedIndex(), oldValue != null);
		});
		
		deleteButton.setOnAction(e ->
		{
			if(playerStatisticsScreen)
			{
				int index = comboBoxForPlayerNames.getSelectionModel().getSelectedIndex();
				playersArrayList.get(index).resetPlayerStatistics();
				
				repaintPlayerStatistics(index, true);
				
				FilesIO.writePlayersFile();
			}
			else
			{
				games.clear();
				gamesObservableList.clear();
				
				tableView.refresh();
				
				FilesIO.writeGameScores();
			}
		});
		
		vBoxForControlButton.setOnMouseClicked(e ->
		{
			vBoxForControlButton.setDisable(true);
			
			if(animationsUsed != ANIMATIONS.NO)
			{
				scaleTransitionForTitleLabel.stop();
				
				scaleTransitionForTitleLabel.setDuration(Duration.millis(200));
				scaleTransitionForTitleLabel.setAutoReverse(false);
				scaleTransitionForTitleLabel.setCycleCount(1);
				scaleTransitionForTitleLabel.setFromX(titleLabel.getScaleX());
				scaleTransitionForTitleLabel.setFromY(titleLabel.getScaleY());
				scaleTransitionForTitleLabel.setToX(0);
				scaleTransitionForTitleLabel.setToY(0);
				
				scaleTransitionForControlLabel.setToX(0);
				scaleTransitionForControlLabel.setToY(0);
				
				scaleTransitionForDeleteButton.setToX(0);
				scaleTransitionForDeleteButton.setToY(0);
				
				if(playerStatisticsScreen)
				{
					playerStatisticsScreen = false;
					
					rotateTransitionForControlImage.setToAngle(180);
					
					translateTransitionForVBoxForPlayerStatistics.setToX(stage.getWidth() - vBoxForPlayerStatistics.getLayoutX() + 20);
					translateTransitionForVBoxForPlayerStatistics.setOnFinished(ev ->
					{
						translateTransitionForVBoxForPlayerStatistics.setOnFinished(eve -> {});
						
						vBoxForPlayerStatistics.setVisible(false);
					});
					translateTransitionForVBoxForPlayerStatistics.playFromStart();
					
					tableView.setVisible(true);
					translateTransitionForTableView.setToX(0);
					playSlideSound();
					translateTransitionForTableView.playFromStart();
				}
				else
				{
					playerStatisticsScreen = true;
					
					rotateTransitionForControlImage.setToAngle(0);
					
					translateTransitionForTableView.setToX(-1.0 * (tableView.getLayoutX() + tableView.getPrefWidth() + 20));
					translateTransitionForTableView.setOnFinished(ev ->
					{
						translateTransitionForTableView.setOnFinished(eve -> {});
						
						tableView.setVisible(false);
					});
					translateTransitionForTableView.playFromStart();
					
					vBoxForPlayerStatistics.setVisible(true);
					translateTransitionForVBoxForPlayerStatistics.setToX(0);
					playSlideSound();
					translateTransitionForVBoxForPlayerStatistics.playFromStart();
				}
				
				scaleTransitionForTitleLabel.setOnFinished(ev ->
				{
					if(playerStatisticsScreen)
					{
						titleLabel.setText(languageResourceBundle.getString("playerStatistics"));
						titleLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0443 * stage.getWidth()));
					}
					else
					{
						titleLabel.setText(languageResourceBundle.getString("scoreBoard"));
						titleLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0495 * stage.getWidth()));
					}
					
					scaleTransitionForTitleLabel.setFromX(0);
					scaleTransitionForTitleLabel.setFromY(0);
					scaleTransitionForTitleLabel.setToX(0.9);
					scaleTransitionForTitleLabel.setToY(0.9);
					
					if(animationsUsed == ANIMATIONS.ALL)
					{
						scaleTransitionForTitleLabel.setOnFinished(eve ->
						{
							scaleTransitionForTitleLabel.setOnFinished(even -> {});
							startTextAnimation();
						});
					}
					else scaleTransitionForTitleLabel.setOnFinished(eve -> {});
					
					scaleTransitionForTitleLabel.playFromStart();
				});
				
				scaleTransitionForDeleteButton.setOnFinished(ev ->
				{
					scaleTransitionForDeleteButton.setOnFinished(eve -> {});
					
					if(playerStatisticsScreen) deleteButton.setText(languageResourceBundle.getString("resetPlayerStatistics"));
					else deleteButton.setText(languageResourceBundle.getString("deleteAllGameDataButton"));
					
					scaleTransitionForDeleteButton.setToX(1);
					scaleTransitionForDeleteButton.setToY(1);
					scaleTransitionForDeleteButton.playFromStart();
				});
				
				scaleTransitionForControlLabel.setOnFinished(ev ->
				{
					scaleTransitionForControlLabel.setOnFinished(eve ->
					{
						scaleTransitionForControlLabel.setOnFinished(even -> {});
						
						vBoxForControlButton.setDisable(false);
					});
					
					if(playerStatisticsScreen) controlLabel.setText(languageResourceBundle.getString("scoreBoard"));
					else controlLabel.setText(languageResourceBundle.getString("playerStatistics"));
					
					scaleTransitionForControlLabel.setToX(1);
					scaleTransitionForControlLabel.setToY(1);
					
					scaleTransitionForControlLabel.playFromStart();
				});
				
				rotateTransitionForControlImage.playFromStart();
				scaleTransitionForControlLabel.playFromStart();
				scaleTransitionForTitleLabel.playFromStart();
				scaleTransitionForDeleteButton.playFromStart();
			}
			else
			{
				if(playerStatisticsScreen)
				{
					playerStatisticsScreen = false;
					
					controlImage.setRotate(180);
					controlLabel.setText(languageResourceBundle.getString("playerStatistics"));
					titleLabel.setText(languageResourceBundle.getString("scoreBoard"));
					titleLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0495 * stage.getWidth()));
					
					vBoxForPlayerStatistics.setTranslateX(stage.getWidth() - vBoxForPlayerStatistics.getLayoutX() + 20);
					vBoxForPlayerStatistics.setVisible(false);
					
					deleteButton.setText(languageResourceBundle.getString("deleteAllGameDataButton"));
					
					tableView.setTranslateX(0);
					tableView.setVisible(true);
				}
				else
				{
					playerStatisticsScreen = true;
					
					controlImage.setRotate(0);
					controlLabel.setText(languageResourceBundle.getString("scoreBoard"));
					titleLabel.setText(languageResourceBundle.getString("playerStatistics"));
					titleLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 0.0443 * stage.getWidth()));
					
					tableView.setTranslateX(-1.0 * (tableView.getLayoutX() + tableView.getPrefWidth() + 20));
					tableView.setVisible(false);
					
					deleteButton.setText(languageResourceBundle.getString("resetPlayerStatistics"));
					
					vBoxForPlayerStatistics.setTranslateX(0);
					vBoxForPlayerStatistics.setVisible(true);
				}
				vBoxForControlButton.setDisable(false);
			}
		});
		vBoxForControlButton.setOnMouseEntered(e -> playHoverSound());
		
		backButton.setOnAction(e ->
		{
			playButtonClickSound();
			
			if(animationsUsed != ANIMATIONS.NO) timeLineToHideAllStuff.playFromStart();
			else welcomeScreen.showScreen(false);
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
				if(fullScreenMode) setWindowedMode();
			}
		});
	}
	
	protected void setupLimitedAnimations()
	{
		translateTransitionForTitleImage = new TranslateTransition(Duration.millis(300), titleImage);
		
		scaleTransitionForTitleLabel = new ScaleTransition();
		scaleTransitionForTitleLabel.setNode(titleLabel);
		
		translateTransitionForTitleLabel = new TranslateTransition(Duration.millis(300), titleLabel);
		
		translateTransitionForWoodImageFor5Icons = new TranslateTransition(Duration.millis(300), woodPanelFor5IconsImage);
		scaleTransitionForHBoxFor5Icons = new ScaleTransition(Duration.millis(300), hBoxFor5Icons);
		
		translateTransitionForVBoxForSound = new TranslateTransition(Duration.millis(300), vBoxForSound);
		
		scaleTransitionForBackButton = new ScaleTransition(Duration.millis(300), backButton);
		
		translateTransitionForTableView = new TranslateTransition(Duration.millis(400), tableView);
		translateTransitionForVBoxForPlayerStatistics = new TranslateTransition(Duration.millis(400), vBoxForPlayerStatistics);
		
		scaleTransitionForTableView = new ScaleTransition(Duration.millis(300), tableView);
		scaleTransitionForVBoxForPlayerStatistics = new ScaleTransition(Duration.millis(300), vBoxForPlayerStatistics);
		
		scaleTransitionForGridPane = new ScaleTransition(Duration.millis(300), gridPaneForPlayerStatistics);
		
		scaleTransitionForDeleteButton = new ScaleTransition(Duration.millis(200), deleteButton);
		
		rotateTransitionForControlImage = new RotateTransition(Duration.millis(400), controlImage);
		scaleTransitionForControlLabel = new ScaleTransition(Duration.millis(200), controlLabel);
		scaleTransitionForVBoxForControlButton = new ScaleTransition(Duration.millis(300), vBoxForControlButton);
		
		fadeTransitionForMovingEarthImage = new FadeTransition(Duration.millis(400), movingEarthImage);
		
		timeLineToShowAllStuff = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					backButton.setDisable(true);
					hBoxFor5Icons.setDisable(true);
					
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
					
					if(animationsUsed == ANIMATIONS.ALL)
					{
						scaleTransitionForTitleLabel.setOnFinished(ev ->
						{
							scaleTransitionForTitleLabel.setOnFinished(eve -> {});
							startTextAnimation();
						});
					}
					
					playPopUpSound();
					scaleTransitionForTitleLabel.playFromStart();
					scaleTransitionForHBoxFor5Icons.playFromStart();
					scaleTransitionForBackButton.playFromStart();
				}),
				new KeyFrame(Duration.millis(1000), e ->
				{
					if(playerStatisticsScreen)
					{
						scaleTransitionForVBoxForPlayerStatistics.setToX(1);
						scaleTransitionForVBoxForPlayerStatistics.setToY(1);
						scaleTransitionForVBoxForPlayerStatistics.playFromStart();
					}
					else
					{
						scaleTransitionForTableView.setToX(1);
						scaleTransitionForTableView.setToY(1);
						scaleTransitionForTableView.playFromStart();
					}
					
					scaleTransitionForDeleteButton.setToX(1);
					scaleTransitionForDeleteButton.setToY(1);
					
					scaleTransitionForVBoxForControlButton.setToX(1);
					scaleTransitionForVBoxForControlButton.setToY(1);
					playPopUpSound();
					scaleTransitionForVBoxForControlButton.playFromStart();
					scaleTransitionForDeleteButton.playFromStart();
				}),
				new KeyFrame(Duration.millis(1300), e ->
				{
					backButton.setDisable(false);
					hBoxFor5Icons.setDisable(false);
				}));
		
		timeLineToHideAllStuff = new Timeline(
				new KeyFrame(Duration.millis(200), e ->
				{
					backButton.setDisable(true);
					hBoxFor5Icons.setDisable(true);
					
					if(playerStatisticsScreen)
					{
						scaleTransitionForVBoxForPlayerStatistics.setToX(0);
						scaleTransitionForVBoxForPlayerStatistics.setToY(0);
						scaleTransitionForVBoxForPlayerStatistics.playFromStart();
					}
					else
					{
						scaleTransitionForTableView.setToX(0);
						scaleTransitionForTableView.setToY(0);
						scaleTransitionForTableView.playFromStart();
					}
					
					scaleTransitionForDeleteButton.setToX(0);
					scaleTransitionForDeleteButton.setToY(0);
					
					scaleTransitionForVBoxForControlButton.setToX(0);
					scaleTransitionForVBoxForControlButton.setToY(0);
					playMinimizeSound();
					scaleTransitionForVBoxForControlButton.playFromStart();
					scaleTransitionForDeleteButton.playFromStart();
				}),
				new KeyFrame(Duration.millis(500), e ->
				{
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
					setSoundIcon(soundIcon, false);
					
					fadeTransitionForMovingEarthImage.setToValue(0);
					fadeTransitionForMovingEarthImage.playFromStart();
				}),
				new KeyFrame(Duration.millis(1500), e ->
				{
					if(animationsUsed == ANIMATIONS.ALL) pauseEarthAnimation();
					welcomeScreen.showScreen(false);
					
					backButton.setDisable(false);
					hBoxFor5Icons.setDisable(false);
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
		if (fullScreenMode) setFullScreenMode();
		else setWindowedMode();
		
		setInitialStateForAllNodes();
		
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
			controlImage.setImage(BACK_ARROW);
		}
		
		if(animationsUsed != ANIMATIONS.NO)
		{
			movingEarthImage.setOpacity(0);
			
			titleImage.setTranslateX(0);
			titleImage.setTranslateY(-1.0 * (titleImage.getLayoutY() + titleImage.getBoundsInParent().getHeight() + 20));
			
			titleLabel.setTranslateX(0);
			titleLabel.setTranslateY(0);
			
			woodPanelFor5IconsImage.setTranslateY(-1.0 * (woodPanelFor5IconsImage.getLayoutY() + woodPanelFor5IconsImage.getBoundsInParent().getHeight() + 20));
			
			backButton.setScaleX(0);
			backButton.setScaleY(0);
			
			hBoxFor5Icons.setScaleX(0);
			hBoxFor5Icons.setScaleY(0);
			
			titleLabel.setScaleX(0);
			titleLabel.setScaleY(0);
			
			vBoxForControlButton.setScaleX(0);
			vBoxForControlButton.setScaleY(0);
			
			deleteButton.setScaleX(0);
			deleteButton.setScaleY(0);
			
			if(playerStatisticsScreen)
			{
				vBoxForPlayerStatistics.setScaleX(0);
				vBoxForPlayerStatistics.setScaleY(0);
			}
			else
			{
				tableView.setScaleX(0);
				tableView.setScaleY(0);
			}
			
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
			
			backButton.setScaleX(1);
			backButton.setScaleY(1);
			
			hBoxFor5Icons.setScaleX(1);
			hBoxFor5Icons.setScaleY(1);
			
			titleLabel.setScaleX(1);
			titleLabel.setScaleY(1);
			
			vBoxForControlButton.setScaleX(1);
			vBoxForControlButton.setScaleY(1);
			
			deleteButton.setScaleX(1);
			deleteButton.setScaleY(1);
			
			if(playerStatisticsScreen)
			{
				vBoxForPlayerStatistics.setScaleX(1);
				vBoxForPlayerStatistics.setScaleY(1);
			}
			else
			{
				tableView.setScaleX(1);
				tableView.setScaleY(1);
			}
			
			backButton.setDisable(false);
			soundIcon.setDisable(false);
			minimizeIcon.setDisable(false);
			fullScreenIcon.setDisable(false);
		}
		
		if(animationsUsed == ANIMATIONS.ALL) movingEarthImage.setCursor(Cursor.HAND);
		else movingEarthImage.setCursor(Cursor.DEFAULT);
		
		if(playerStatisticsScreen)
		{
			tableView.setTranslateX(-1.0 * (tableView.getLayoutX() + tableView.getPrefWidth() + 20));
			tableView.setVisible(false);
			
			vBoxForPlayerStatistics.setTranslateX(0);
			vBoxForPlayerStatistics.setVisible(true);
		}
		else
		{
			vBoxForPlayerStatistics.setTranslateX(stage.getWidth() - vBoxForPlayerStatistics.getLayoutX() + 20);
			vBoxForPlayerStatistics.setVisible(false);
			
			tableView.setTranslateX(0);
			tableView.setVisible(true);
		}
		
		gamesObservableList = FXCollections.observableList(games);
		tableView.setItems(gamesObservableList);
//		GUIUtils.autoFitTable(tableView);
		
		playerNamesObservableList = FXCollections.observableArrayList();
		for(Player player: playersArrayList)
			playerNamesObservableList.add(player.getOriginalName());
		comboBoxForPlayerNames.setItems(playerNamesObservableList);
		comboBoxForPlayerNames.getSelectionModel().selectFirst();
		
		setViewPortProperties();
		updateStrings();
		
		vBoxForSound.setTranslateY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
		vBoxForSound.setVisible(false);
	}
	
	private void repaintPlayerStatistics(int index, boolean playSound)
	{
		if(animationsUsed != ANIMATIONS.NO)
		{
			scaleTransitionForGridPane.setToX(0);
			scaleTransitionForGridPane.setToY(0);
			scaleTransitionForGridPane.setOnFinished(e ->
			{
				scaleTransitionForGridPane.setOnFinished(ev -> {});
				
				setPlayerStatistics(index);
				
				scaleTransitionForGridPane.setToX(1);
				scaleTransitionForGridPane.setToY(1);
				if(playSound) playMaximizeSound();
				scaleTransitionForGridPane.playFromStart();
			});
			
			if(playSound) playMinimizeSound();
			scaleTransitionForGridPane.playFromStart();
		}
		else setPlayerStatistics(index);
	}
	
	private void setPlayerStatistics(int index)
	{
		gridPaneLabels[1][1].setText(String.valueOf(playersArrayList.get(index).getTotalQuestionsAnswered()));
		gridPaneLabels[2][1].setText(String.valueOf(playersArrayList.get(index).getTotalCorrectAnswers()));
		gridPaneLabels[3][1].setText(getReadablePlayedTime(playersArrayList.get(index).getTotalTimePlayed()));
		gridPaneLabels[5][1].setText(String.valueOf(playersArrayList.get(index).getMaxCombo()));
		
		if(getCurrentLanguage() == LANGUAGE.GREEK)
		{
			gridPaneLabels[0][1].setText(String.valueOf(playersArrayList.get(index).getHighestScorePoints()) + " πόντοι");
			
			gridPaneLabels[4][1].setText(numberFormatForUI.format(playersArrayList.get(index).getAverageAnswerTime()) + " δευτ.");
		}
		else
		{
			gridPaneLabels[0][1].setText(String.valueOf(playersArrayList.get(index).getHighestScorePoints()) + " points");
			
			gridPaneLabels[4][1].setText(numberFormatForUI.format(playersArrayList.get(index).getAverageAnswerTime()) + " sec.");
			gridPaneLabels[4][1].setText(numberFormatForUI.format(playersArrayList.get(index).getAverageAnswerTime()) + " sec.");
		}
	}
	
	private void updateStrings()
	{
		//TITLE LABEL
		if(playerStatisticsScreen) titleLabel.setText(languageResourceBundle.getString("playerStatistics"));
		else titleLabel.setText(languageResourceBundle.getString("scoreBoard"));
		
		//BACK BUTTON
		backButton.setText(languageResourceBundle.getString("backButton"));
		
		//ICON TOOLTIPS
		soundOptionsToolTip.setText(languageResourceBundle.getString("soundOptionsTooltip"));
		minimizeTooltip.setText(languageResourceBundle.getString("minimizeTooltip"));
		exitTooltip.setText(languageResourceBundle.getString("exitTooltip"));
		
		//TABLE VIEW
		tableView.setPlaceholder(new Label(languageResourceBundle.getString("scoreboardEmpty")));
		
		playerNameColumn.setText(languageResourceBundle.getString("playerNameColumn"));
		gameModeColumn.setText(languageResourceBundle.getString("gameModeColumn"));
		selectedCategoriesColumn.setText(languageResourceBundle.getString("selectedCategoriesColumn"));
		isCountdownEnabledColumn.setText(languageResourceBundle.getString("countdownEnabledColumn"));
		numberOfAllQuestionsColumn.setText(languageResourceBundle.getString("numberOfAllQuestionsColumn"));
		numberOfCorrectQuestionsColumn.setText(languageResourceBundle.getString("numberOfCorrectQuestionsColumn"));
		scorePointsColumn.setText(languageResourceBundle.getString("scorePointsColumn"));
		maxComboColumn.setText(languageResourceBundle.getString("maxComboColumn"));
		averageAnswerTimeColumn.setText(languageResourceBundle.getString("averageAnswerTimeColumn"));
		gameStartedTimeColumn.setText(languageResourceBundle.getString("gameStartedTimeColumn"));
		gameDurationColumn.setText(languageResourceBundle.getString("gameDurationColumn"));
		
		continentsAndCountriesCheckBox.setText(languageResourceBundle.getString("extContinentsAndCountries"));
		usaCheckBox.setText(languageResourceBundle.getString("extUSA"));
		greeceCheckBox.setText(languageResourceBundle.getString("extGreece"));
		attractionsCheckBox.setText(languageResourceBundle.getString("extAttractions"));
		
		continentsAndCountriesCheckBoxes[0].setText(languageResourceBundle.getString("extCapital"));
		continentsAndCountriesCheckBoxes[1].setText(languageResourceBundle.getString("extLargestCities"));
		continentsAndCountriesCheckBoxes[2].setText(languageResourceBundle.getString("extOfficialLanguages"));
		continentsAndCountriesCheckBoxes[3].setText(languageResourceBundle.getString("extCurrency"));
		continentsAndCountriesCheckBoxes[4].setText(languageResourceBundle.getString("extPopulation"));
		continentsAndCountriesCheckBoxes[5].setText(languageResourceBundle.getString("extArea"));
		continentsAndCountriesCheckBoxes[6].setText(languageResourceBundle.getString("extContinents_Countries"));
		continentsAndCountriesCheckBoxes[7].setText(languageResourceBundle.getString("extSovereignStates"));
		continentsAndCountriesCheckBoxes[8].setText(languageResourceBundle.getString("extFlag"));
		continentsAndCountriesCheckBoxes[9].setText(languageResourceBundle.getString("extCoatOfArms"));
		continentsAndCountriesCheckBoxes[10].setText(languageResourceBundle.getString("extLocation"));
		usaCheckBoxes[0].setText(languageResourceBundle.getString("extCapital"));
		usaCheckBoxes[1].setText(languageResourceBundle.getString("extLargestCities"));
		usaCheckBoxes[2].setText(languageResourceBundle.getString("extUSAYearEnteredUnion"));
		usaCheckBoxes[3].setText(languageResourceBundle.getString("extUSANumberOfSeats"));
		usaCheckBoxes[4].setText(languageResourceBundle.getString("extPopulation"));
		usaCheckBoxes[5].setText(languageResourceBundle.getString("extArea"));
		usaCheckBoxes[6].setText(languageResourceBundle.getString("extGeographicalCharacteristics"));
		usaCheckBoxes[7].setText(languageResourceBundle.getString("extFlag"));
		usaCheckBoxes[8].setText(languageResourceBundle.getString("extLocation"));
		greeceCheckBoxes[0].setText(languageResourceBundle.getString("extAdministrativeDivision"));
		greeceCheckBoxes[1].setText(languageResourceBundle.getString("extLargestCities"));
		greeceCheckBoxes[2].setText(languageResourceBundle.getString("extLargestMunicipalities"));
		greeceCheckBoxes[3].setText(languageResourceBundle.getString("extGeographicalCharacteristics"));
		greeceCheckBoxes[4].setText(languageResourceBundle.getString("extPopulation"));
		greeceCheckBoxes[5].setText(languageResourceBundle.getString("extArea"));
		greeceCheckBoxes[6].setText(languageResourceBundle.getString("extLocation"));
		
		if(playerStatisticsScreen)
		{
			controlLabel.setText(languageResourceBundle.getString("scoreBoard"));
			deleteButton.setText(languageResourceBundle.getString("resetPlayerStatistics"));
		}
		else
		{
			controlLabel.setText(languageResourceBundle.getString("playerStatistics"));
			deleteButton.setText(languageResourceBundle.getString("deleteAllGameDataButton"));
		}
		
		gridPaneLabels[0][0].setText(" " + languageResourceBundle.getString("highestScoreLabel"));
		gridPaneLabels[1][0].setText(" " + languageResourceBundle.getString("totalAnswersLabel"));
		gridPaneLabels[2][0].setText(" " + languageResourceBundle.getString("totalCorrectAnswersLabel"));
		gridPaneLabels[3][0].setText(" " + languageResourceBundle.getString("totalTimePlayedLabel"));
		gridPaneLabels[4][0].setText(" " + languageResourceBundle.getString("averageAnswerTimeLabel"));
		gridPaneLabels[5][0].setText(" " + languageResourceBundle.getString("maxComboLabel"));
		
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
	
	private class CheckBoxForPopOver extends CheckBox
	{
		CheckBoxForPopOver()
		{
			setDisable(true);
			setTextFill(Color.WHITE);
			setFocusTraversable(false);
		}
	}
}
