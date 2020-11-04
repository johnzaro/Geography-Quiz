package com.johnzaro.geographyquiz.screens;

import com.johnzaro.geographyquiz.core.*;
import com.johnzaro.geographyquiz.core.ImageStuff.Images;
import com.johnzaro.geographyquiz.core.customNodes.*;
import com.johnzaro.geographyquiz.dataStructures.Game;
import com.johnzaro.geographyquiz.dataStructures.Player;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;

import java.time.LocalDateTime;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;

/**
 * Created by John on 30/1/2017.
 */

public class ScoreBoardScreen extends CoreScreenWithMovingBackground
{
	private ObservableList<Game> gamesObservableList;

	private CustomImageView previousChalkboardImage, titleImage, controlImage;
	private CustomLabel titleLabel, controlLabel, popUpAskConfirmationLabel, popUpMessageLabel;
	private CustomHBox hBoxForPopUpConfirmationButtons;
	private CustomVBox vBoxForControlButton, vBoxForPlayerStatistics, vBoxForPopUpAskConfirmation;
	private CustomComboBox<String> comboBoxForPlayerNames;
	private CustomButton backButton, deleteButton, cancelPopUpActionButton, confirmPopUpActionButton;
	private CustomGridPane gridPaneForPlayerStatistics;
	private TableView<Game> tableView;
	private TableColumn<Game, String> playerNameColumn, selectedCategoriesColumn;
	private TableColumn<Game, LocalDateTime> gameStartedTimeColumn;
	private TableColumn<Game, Integer> gameDurationColumn, numberOfAllQuestionsColumn, numberOfCorrectQuestionsColumn, scorePointsColumn, maxComboColumn;
	private TableColumn<Game, GAMEMODE> gameModeColumn;
	private TableColumn<Game, Double> averageAnswerTimeColumn;
	private TableColumn<Game, Boolean> isCountdownEnabledColumn;

	private PopOver popOverForSelectedCategories;
	private CustomCheckBox confirmDeleteCheckBox;
	private CheckBoxForPopOver continentsAndCountriesCheckBox, usaCheckBox, greeceCheckBox, attractionsCheckBox;
	private CheckBoxForPopOver[] continentsAndCountriesCheckBoxes, usaCheckBoxes, greeceCheckBoxes;
	private GridPane gridPaneForSelectedCategories;

	private Label[][] gridPaneLabels;

	private RotateTransition rotateTransitionForControlImage;
	private TranslateTransition translateTransitionForTableView, translateTransitionForVBoxForPlayerStatistics, translateTransitionForTitleImage,
			translateTransitionForTitleLabel;
	private FadeTransition fadeTransitionForMovingEarthImage;
	private ScaleTransition scaleTransitionForDeleteButton, scaleTransitionForGridPane,
			scaleTransitionForTableView, scaleTransitionForVBoxForPlayerStatistics,
			scaleTransitionForTitleLabel, scaleTransitionForBackButton,
			scaleTransitionForVBoxForControlButton, scaleTransitionForControlLabel,
			scaleTransitionForPopUpAskConfirmation, scaleTransitionForPopUpMessage;
	private Timeline timelineToShowConfirmationMessage, timelineToCancelConfirmationMessage, timelineToShowPopUpMessage;

	private boolean playerStatisticsScreen = false;

	private enum POP_UP_ACTION { DELETE_SAVED_SCORES, DELETE_PLAYER_STATISTICS, NULL}
	private POP_UP_ACTION popUpAction;

	protected void recalculateUI(double width, double height)
	{
		super.recalculateUI(width, height);
		
		titleImage.setFitWidth(0.4688 * width);
		titleImage.setLayoutX(width / 2.0 - titleImage.getFitWidth() / 2.0);
		titleImage.setLayoutY(ratioProperties.getScoreBoard().getTitleImageSetY() * height);
		if(titleImage.getTranslateX() != 0)
		{
			titleImage.setTranslateX(-0.0416 * width);
			titleLabel.setTranslateX(-0.0416 * width);
		}
		
		titleLabel.setPrefSize(0.4375 * width, 0.1296 * height);
		titleLabel.setLayoutX(ratioProperties.getScoreBoard().getTitleLabelSetX() * width);
		titleLabel.setLayoutY(ratioProperties.getScoreBoard().getTitleLabelSetY() * height);
		if(!playerStatisticsScreen) titleLabel.setFont(font95B);
		else titleLabel.setFont(font85B);
		
		woodPanelFor1IconImage.setLayoutX(ratioProperties.getScoreBoard().getWoodPanelFor1IconImageLayoutX() * width - woodPanelFor1IconImage.getFitWidth() / 2.0);
		woodPanelFor1IconImage.setLayoutY(ratioProperties.getScoreBoard().getWoodPanelFor1IconImageLayoutY() * height);
		
		vBoxForSound.setLayoutX(0.7031 * width);
		vBoxForSound.setLayoutY(ratioProperties.getScoreBoard().getvBoxForSoundLayoutY() * height);
		if(vBoxForSound.getTranslateY() != 0) vBoxForSound.setTranslateY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));

		soundButton.setLayoutY(ratioProperties.getScoreBoard().getSoundIconLayoutY() * height - soundButton.getFitWidth() / 2.0);

		tableView.setPrefSize(0.9058 * width, 0.4814 * height);
		tableView.setLayoutX(width / 2.0 - tableView.getPrefWidth() / 2.0);
		tableView.setLayoutY(0.2315 * height);
		tableView.setStyle(cssFont15P + cssPadding10);
		if(tableView.getTranslateX() != 0) tableView.setTranslateX(-1.0 * (tableView.getLayoutX() + tableView.getPrefWidth() + 20));
		
		popOverForSelectedCategories.setCornerRadius(0.0078 * width);
		gridPaneForSelectedCategories.setHgap(0.0026 * width);
		gridPaneForSelectedCategories.setVgap(0.0111 * height);
		gridPaneForSelectedCategories.setPadding(new Insets(0.0104 * width));
		
		vBoxForPlayerStatistics.setPrefSize(0.4167 * width, 0.4167 * height);
		vBoxForPlayerStatistics.setLayoutX(width / 2.0 - vBoxForPlayerStatistics.getPrefWidth() / 2.0);
		vBoxForPlayerStatistics.setLayoutY(height / 2.0 - vBoxForPlayerStatistics.getPrefHeight() / 2.0);
		vBoxForPlayerStatistics.setSpacing(0.0185 * height);
		if(vBoxForPlayerStatistics.getTranslateX() != 0) vBoxForPlayerStatistics.setTranslateX(width - vBoxForPlayerStatistics.getLayoutX() + 20);

		comboBoxForPlayerNames.setPrefWidth(0.5 * vBoxForPlayerStatistics.getPrefWidth());
		comboBoxForPlayerNames.setStyle(cssFont25P);
		
		gridPaneForPlayerStatistics.setPrefWidth(vBoxForPlayerStatistics.getPrefWidth());
		gridPaneForPlayerStatistics.setStyle(cssPadding10);
		double gridPaneLabelsWidth = 0.5 * gridPaneForPlayerStatistics.getPrefWidth();
		
		for(Label[] gridPaneLabel : gridPaneLabels)
		{
			gridPaneLabel[0].setPrefSize(gridPaneLabelsWidth, gridPaneLabelsWidth);
			gridPaneLabel[0].setMaxSize(gridPaneLabelsWidth, gridPaneLabelsWidth);
			gridPaneLabel[0].setFont(font25P);
			
			gridPaneLabel[1].setPrefSize(gridPaneLabelsWidth, gridPaneLabelsWidth);
			gridPaneLabel[1].setMaxSize(gridPaneLabelsWidth, gridPaneLabelsWidth);
			gridPaneLabel[1].setFont(font25P);
		}

		deleteButton.setPrefSize(0.2750 * width, 0.0648 * height);
		deleteButton.setLayoutX(width / 2.0 - deleteButton.getPrefWidth() / 2.0);
		deleteButton.setLayoutY(0.7315 * height);
		deleteButton.setFont(font25P);
		
		vBoxForControlButton.setPrefWidth(0.1502 * width);
		vBoxForControlButton.setLayoutX(width / 2.0 - vBoxForControlButton.getPrefWidth() / 2.0);
		vBoxForControlButton.setLayoutY(0.8148 * height);
		controlImage.setFitWidth(0.0833 * width);
		controlLabel.setFont(font30B);
		
		backButton.setFitWidth(0.0703 * width);
		backButton.setLayoutX(0.0420 * width);
		backButton.setLayoutY(0.8241 * height);
		backButton.setFont(font25P);
		backButton.getTooltip().setFont(font25P);

		popUpMessageLabel.setPrefSize(0.2864 * width, 0.2315 * height);
		popUpMessageLabel.setLayoutX(width / 2.0 - popUpMessageLabel.getPrefWidth() / 2.0);
		popUpMessageLabel.setLayoutY(height / 2.0 - popUpMessageLabel.getPrefHeight() / 2.0);
		popUpMessageLabel.setStyle(cssBackgroundAndBorderBig + cssPadding10);
		popUpMessageLabel.setFont(font40P);
		
		vBoxForPopUpAskConfirmation.setPrefSize(0.5200 * width, 0.4400 * height);
		vBoxForPopUpAskConfirmation.setLayoutX(width / 2.0 - vBoxForPopUpAskConfirmation.getPrefWidth() / 2.0);
		vBoxForPopUpAskConfirmation.setLayoutY(height / 2.0 - vBoxForPopUpAskConfirmation.getPrefHeight() / 2.0);
		vBoxForPopUpAskConfirmation.setStyle(cssBackgroundAndBorderBig + cssPadding30);
		popUpAskConfirmationLabel.setPrefWidth(vBoxForPopUpAskConfirmation.getPrefWidth());
		popUpAskConfirmationLabel.setFont(font40P);
		confirmDeleteCheckBox.setStyle("-fx-padding:" + 0.0463 * height + "px 0 " + 0.0463 * height + "px 0;");
		confirmDeleteCheckBox.setFont(font40P);
		hBoxForPopUpConfirmationButtons.setPrefWidth(vBoxForPopUpAskConfirmation.getPrefWidth());
		hBoxForPopUpConfirmationButtons.setSpacing(0.0130 * width);
		cancelPopUpActionButton.setPrefWidth(hBoxForPopUpConfirmationButtons.getPrefWidth() / 2.0);
		cancelPopUpActionButton.setFont(font45B);
		confirmPopUpActionButton.setPrefWidth(hBoxForPopUpConfirmationButtons.getPrefWidth() / 2.0);
		confirmPopUpActionButton.setFont(font45B);
		
		Insets pos1 = new Insets(0, 0, 0, 0.0198 * width);
		for(CheckBoxForPopOver continentsAndCountriesCheckBox : continentsAndCountriesCheckBoxes) GridPane.setMargin(continentsAndCountriesCheckBox, pos1);
		for(CheckBoxForPopOver checkBox : usaCheckBoxes) GridPane.setMargin(checkBox, pos1);
		for(CheckBoxForPopOver checkBox : greeceCheckBoxes) GridPane.setMargin(checkBox, pos1);
	}

	protected void recalculateBackground(double width, double height)
	{
		super.recalculateBackground(width, height);

		previousChalkboardImage.setFitWidth(width);
		previousChalkboardImage.setFitHeight(height);
	}
	
	public ScoreBoardScreen()
	{
		//FUNDAMENTAL STUFF-------------------------------------------------------------------------------------
		previousChalkboardImage = new CustomImageView(false, false, false, true, CacheHint.SPEED);
		previousChalkboardImage.setLayoutX(0);
		previousChalkboardImage.setLayoutY(0);

		movingEarthImage = new CustomImageView(false, true, false, false, null);

		titleImage = new CustomImageView(true, true, false, true, CacheHint.SPEED);

		titleLabel = new CustomLabel(Pos.CENTER, TextAlignment.CENTER, DARK_BROWN, null, null, null, false, false, false, false, true, CacheHint.SCALE);
		titleLabel.setEffect(innerShadow);

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
				new TableCell<>()
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
									setText("Endless (" + games.get(this.getIndex()).getLivesForEndless() + " " + languageResourceBundle.getString("life") + ")");
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
				new TableCell<>()
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
			new TableCell<>()
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

		deleteButton = new CustomButton(null, null, null, null, null, false, true,false, true, CacheHint.SCALE);

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

		gridPaneForSelectedCategories.add(continentsAndCountriesCheckBox, 0, 0);
		for(int i = 0; i < continentsAndCountriesCheckBoxes.length; i++)
			gridPaneForSelectedCategories.add(continentsAndCountriesCheckBoxes[i], 0, i + 1);
		gridPaneForSelectedCategories.add(usaCheckBox, 1, 0);
		for(int i = 0; i < usaCheckBoxes.length; i++)
			gridPaneForSelectedCategories.add(usaCheckBoxes[i], 1, i + 1);
		gridPaneForSelectedCategories.add(greeceCheckBox, 2, 0);
		for(int i = 0; i < greeceCheckBoxes.length; i++)
			gridPaneForSelectedCategories.add(greeceCheckBoxes[i], 2, i + 1);

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
		comboBoxForPlayerNames = new CustomComboBox<>();

		gridPaneForPlayerStatistics = new CustomGridPane(Pos.CENTER, "orange-background-color", false, null);

		gridPaneLabels = new Label[6][2];
		for(int i = 0; i < gridPaneLabels.length; i++)
		{
			gridPaneLabels[i][0] = new Label();
			gridPaneLabels[i][1] = new Label();

			if(i % 2 == 0)
			{
				gridPaneLabels[i][0].getStyleClass().add("grid-item-even");
				gridPaneLabels[i][1].getStyleClass().add("grid-item-even");
			}
			else
			{
				gridPaneLabels[i][0].getStyleClass().add("grid-item-odd");
				gridPaneLabels[i][1].getStyleClass().add("grid-item-odd");
			}

			gridPaneForPlayerStatistics.add(gridPaneLabels[i][0], 0, i);
			gridPaneForPlayerStatistics.add(gridPaneLabels[i][1], 1, i);
		}

		vBoxForPlayerStatistics = new CustomVBox(false, Pos.CENTER, null, false, null);
		vBoxForPlayerStatistics.getChildren().addAll(comboBoxForPlayerNames, gridPaneForPlayerStatistics);

		//CONTROL BUTTON
		controlImage = new CustomImageView(true, true, true, true, CacheHint.ROTATE);
		controlImage.setRotate(180);
		controlImage.setCursor(Cursor.HAND);

		controlLabel = new CustomLabel(null, TextAlignment.CENTER, BROWN, null, null, null, true, false, false, false, true, CacheHint.SCALE);

		vBoxForControlButton = new CustomVBox(false, Pos.CENTER, null, false, null);
		vBoxForControlButton.getChildren().addAll(controlImage, controlLabel);

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

		backButton = new CustomButton(null, images.get(Images.BACK_ARROW), ContentDisplay.TOP, null, CustomButton.COLOR, true, true,true, true, CacheHint.SCALE);

		//DROP SHADOW EFFECT
		woodenFrameImage.setEffect(dropShadow);
		titleImage.setEffect(dropShadow);
		tableView.setEffect(dropShadow);
		vBoxForPlayerStatistics.setEffect(dropShadow);
		backButton.setEffect(dropShadow);
		deleteButton.setEffect(dropShadow);
		vBoxForControlButton.setEffect(dropShadow);
		vBoxForPopUpAskConfirmation.setEffect(dropShadow);

		nodesPane.getChildren().addAll(previousChalkboardImage, movingEarthImage,
				titleImage, titleLabel, woodPanelFor1IconImage, vBoxForSound,
				soundButton, backButton, tableView, vBoxForPlayerStatistics,
				deleteButton, vBoxForControlButton, vBoxForPopUpAskConfirmation, popUpMessageLabel);
		
		if(animationsUsed != ANIMATIONS.NO) setupLimitedAnimations();

		setupPopUpAnimations();

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
				popUpAction = POP_UP_ACTION.DELETE_PLAYER_STATISTICS;
				popUpAskConfirmationLabel.setText(languageResourceBundle.getString("resetPlayerStatisticsConfirmationMessage"));
				confirmPopUpActionButton.setText(languageResourceBundle.getString("resetAll"));
				timelineToShowConfirmationMessage.playFromStart();
			}
			else
			{
				popUpAction = POP_UP_ACTION.DELETE_SAVED_SCORES;
				popUpAskConfirmationLabel.setText(languageResourceBundle.getString("deleteAllSavedScoresConfirmationMessage"));
				confirmPopUpActionButton.setText(languageResourceBundle.getString("deleteAll"));
				timelineToShowConfirmationMessage.playFromStart();
			}
		});

		vBoxForControlButton.setOnMouseEntered(e -> controlLabel.setUnderline(true));
		vBoxForControlButton.setOnMouseExited(e -> controlLabel.setUnderline(false));
		vBoxForControlButton.setOnMouseClicked(e ->
		{
			getAudioStuff().playButtonClickSound();

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

					translateTransitionForVBoxForPlayerStatistics.setToX(mainScene.getWidth() - vBoxForPlayerStatistics.getLayoutX() + 20);
					translateTransitionForVBoxForPlayerStatistics.setOnFinished(ev ->
					{
						translateTransitionForVBoxForPlayerStatistics.setOnFinished(eve -> {});

						vBoxForPlayerStatistics.setVisible(false);
					});
					translateTransitionForVBoxForPlayerStatistics.playFromStart();

					tableView.setVisible(true);
					translateTransitionForTableView.setToX(0);
					getAudioStuff().playSlideSound();
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
					getAudioStuff().playSlideSound();
					translateTransitionForVBoxForPlayerStatistics.playFromStart();
				}

				scaleTransitionForTitleLabel.setOnFinished(ev ->
				{
					if(playerStatisticsScreen)
					{
						titleLabel.setText(languageResourceBundle.getString("playerStatistics"));
						titleLabel.setFont(font85B);
					}
					else
					{
						titleLabel.setText(languageResourceBundle.getString("scoreBoard"));
						titleLabel.setFont(font95B);
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

					if(playerStatisticsScreen) deleteButton.setText(languageResourceBundle.getString("resetPlayerStatisticsButton"));
					else deleteButton.setText(languageResourceBundle.getString("deleteAllSavedScoresButton"));

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
					titleLabel.setFont(font95B);

					vBoxForPlayerStatistics.setTranslateX(mainScene.getWidth() - vBoxForPlayerStatistics.getLayoutX() + 20);
					vBoxForPlayerStatistics.setVisible(false);

					deleteButton.setText(languageResourceBundle.getString("deleteAllSavedScoresButton"));

					tableView.setTranslateX(0);
					tableView.setVisible(true);
				}
				else
				{
					playerStatisticsScreen = true;

					controlImage.setRotate(0);
					controlLabel.setText(languageResourceBundle.getString("scoreBoard"));
					titleLabel.setText(languageResourceBundle.getString("playerStatistics"));
					titleLabel.setFont(font85B);

					tableView.setTranslateX(-1.0 * (tableView.getLayoutX() + tableView.getPrefWidth() + 20));
					tableView.setVisible(false);

					deleteButton.setText(languageResourceBundle.getString("resetPlayerStatisticsButton"));

					vBoxForPlayerStatistics.setTranslateX(0);
					vBoxForPlayerStatistics.setVisible(true);
				}
				vBoxForControlButton.setDisable(false);
			}
		});

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

			if(popUpAction == POP_UP_ACTION.DELETE_SAVED_SCORES)
			{
				int index = comboBoxForPlayerNames.getSelectionModel().getSelectedIndex();
				playersArrayList.get(index).resetPlayerStatistics();

				repaintPlayerStatistics(index, true);
				FilesIO.writePlayersFile();

				popUpMessageLabel.setText(languageResourceBundle.getString("deleteAllSavedScoresSuccessfulMessage"));
			}
			else if(popUpAction == POP_UP_ACTION.DELETE_PLAYER_STATISTICS)
			{
				games.clear();
				gamesObservableList.clear();

				tableView.refresh();

				FilesIO.writeGameScores();

				popUpMessageLabel.setText(languageResourceBundle.getString("resetPlayerStatisticsSuccessfulMessage"));
			}

			timelineToShowPopUpMessage.playFromStart();
		});

		backButton.setOnAction(e ->
		{
			if(animationsUsed != ANIMATIONS.NO) timelineToHideAllStuff.playFromStart();
			else showOtherScreen(welcomeScreen);
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
	}

	protected void setupLimitedAnimations()
	{
		scaleTransitionForPopUpMessage = new ScaleTransition(Duration.millis(200), popUpMessageLabel);
		scaleTransitionForPopUpAskConfirmation = new ScaleTransition(Duration.millis(200), vBoxForPopUpAskConfirmation);

		translateTransitionForTitleImage = new TranslateTransition(Duration.millis(200), titleImage);

		scaleTransitionForTitleLabel = new ScaleTransition();
		scaleTransitionForTitleLabel.setNode(titleLabel);

		translateTransitionForTitleLabel = new TranslateTransition(Duration.millis(200), titleLabel);

		translateTransitionForVBoxForSound = new TranslateTransition(Duration.millis(200), vBoxForSound);

		scaleTransitionForBackButton = new ScaleTransition(Duration.millis(200), backButton);

		translateTransitionForTableView = new TranslateTransition(Duration.millis(400), tableView);
		translateTransitionForVBoxForPlayerStatistics = new TranslateTransition(Duration.millis(400), vBoxForPlayerStatistics);

		scaleTransitionForTableView = new ScaleTransition(Duration.millis(200), tableView);
		scaleTransitionForVBoxForPlayerStatistics = new ScaleTransition(Duration.millis(200), vBoxForPlayerStatistics);

		scaleTransitionForGridPane = new ScaleTransition(Duration.millis(200), gridPaneForPlayerStatistics);

		scaleTransitionForDeleteButton = new ScaleTransition(Duration.millis(200), deleteButton);

		rotateTransitionForControlImage = new RotateTransition(Duration.millis(400), controlImage);
		scaleTransitionForControlLabel = new ScaleTransition(Duration.millis(200), controlLabel);
		scaleTransitionForVBoxForControlButton = new ScaleTransition(Duration.millis(200), vBoxForControlButton);

		fadeTransitionForMovingEarthImage = new FadeTransition(Duration.millis(300), movingEarthImage);

		translateTransitionForWoodPanelFor1IconImage = new TranslateTransition(Duration.millis(200), woodPanelFor1IconImage);

		scaleTransitionForSoundIcon = new ScaleTransition(Duration.millis(200), soundButton);

		timelineToShowAllStuff = new Timeline(
				new KeyFrame(Duration.millis(0), e ->
				{
					backButton.setDisable(true);

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

					if(animationsUsed == ANIMATIONS.ALL)
					{
						scaleTransitionForTitleLabel.setOnFinished(ev ->
						{
							scaleTransitionForTitleLabel.setOnFinished(eve -> {});
							startTextAnimation();
						});
					}

					getAudioStuff().playPopUpSound();
					scaleTransitionForTitleLabel.playFromStart();
					scaleTransitionForBackButton.playFromStart();
					scaleTransitionForSoundIcon.playFromStart();
				}),
				new KeyFrame(Duration.millis(700), e ->
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

					getAudioStuff().playPopUpSound();
					scaleTransitionForVBoxForControlButton.playFromStart();
					scaleTransitionForDeleteButton.playFromStart();
				}),
				new KeyFrame(Duration.millis(900), e -> backButton.setDisable(false)));

		timelineToHideAllStuff = new Timeline(
				new KeyFrame(Duration.millis(200), e ->
				{
					backButton.setDisable(true);

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
					getAudioStuff().playMinimizeSound();
					scaleTransitionForVBoxForControlButton.playFromStart();
					scaleTransitionForDeleteButton.playFromStart();
				}),
				new KeyFrame(Duration.millis(400), e ->
				{
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
				new KeyFrame(Duration.millis(1000), e ->
				{
					backButton.setDisable(false);

					if(animationsUsed == ANIMATIONS.ALL) pauseEarthAnimation();
					showOtherScreen(welcomeScreen);
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

		if(animationsUsed != ANIMATIONS.NO) timelineToShowAllStuff.playFromStart();
	}

	protected void setInitialStateForAllNodes()
	{
		if(previousChalkboardImage.getImage() == null || !woodenFrameImage.getImage().equals(images.get(Images.FRAME)))
		{
			previousChalkboardImage.setImage(images.get(Images.CHALKBOARD_BACKGROUND));
			woodenFrameImage.setImage(images.get(Images.FRAME));
			movingEarthImage.setImage(images.get(Images.MOVING_EARTH_1));
			woodPanelFor1IconImage.setImage(images.get(Images.EMPTY_WOOD_BACKGROUND_1_ICON));
			titleImage.setImage(images.get(Images.EMPTY_WOOD_BACKGROUND_SMALL_ROPE));
			controlImage.setImage(images.get(Images.BACK_ARROW));
		}

		if(animationsUsed != ANIMATIONS.NO)
		{
			woodPanelFor1IconImage.setTranslateY(-1.0 * (woodPanelFor1IconImage.getLayoutY() + woodPanelFor1IconImage.getBoundsInParent().getHeight()));

			movingEarthImage.setOpacity(0);

			titleImage.setTranslateX(0);
			titleImage.setTranslateY(-1.0 * (titleImage.getLayoutY() + titleImage.getBoundsInParent().getHeight() + 20));

			titleLabel.setTranslateX(0);
			titleLabel.setTranslateY(0);

			backButton.setScaleX(0);
			backButton.setScaleY(0);

			soundButton.setScaleX(0);
			soundButton.setScaleY(0);

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

			backButton.setScaleX(1);
			backButton.setScaleY(1);

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

		if(playerStatisticsScreen)
		{
			tableView.setTranslateX(-1.0 * (tableView.getLayoutX() + tableView.getPrefWidth() + 20));
			tableView.setVisible(false);

			vBoxForPlayerStatistics.setTranslateX(0);
			vBoxForPlayerStatistics.setVisible(true);
		}
		else
		{
			vBoxForPlayerStatistics.setTranslateX(mainScene.getWidth() - vBoxForPlayerStatistics.getLayoutX() + 20);
			vBoxForPlayerStatistics.setVisible(false);

			tableView.setTranslateX(0);
			tableView.setVisible(true);
		}

		gamesObservableList = FXCollections.observableList(games);
		tableView.setItems(gamesObservableList);
//		GUIUtils.autoFitTable(tableView);

		ObservableList<String> playerNamesObservableList = FXCollections.observableArrayList();
		for(Player player: playersArrayList)
			playerNamesObservableList.add(player.getOriginalName());
		comboBoxForPlayerNames.setItems(playerNamesObservableList);
		comboBoxForPlayerNames.getSelectionModel().selectFirst();

		updateStrings();

		setCorrectSoundIcon(false);

		vBoxForSound.setTranslateY(-1.0 * (vBoxForSound.getLayoutY() + vBoxForSound.getPrefHeight() + 20));
		vBoxForSound.setVisible(false);

		popUpAction = POP_UP_ACTION.NULL;

		vBoxForPopUpAskConfirmation.setVisible(false);
		vBoxForPopUpAskConfirmation.setScaleX(0);
		vBoxForPopUpAskConfirmation.setScaleY(0);

		popUpMessageLabel.setVisible(false);
		popUpMessageLabel.setScaleX(0);
		popUpMessageLabel.setScaleY(0);
	}

	private void disableUI()
	{
		movingEarthImage.setDisable(true);
		backButton.setDisable(true);
		tableView.setDisable(true);
		vBoxForPlayerStatistics.setDisable(true);
		deleteButton.setDisable(true);
		vBoxForControlButton.setDisable(true);
	}

	private void enableUI()
	{
		movingEarthImage.setDisable(false);
		backButton.setDisable(false);
		tableView.setDisable(false);
		vBoxForPlayerStatistics.setDisable(false);
		deleteButton.setDisable(false);
		vBoxForControlButton.setDisable(false);
	}

	private void addBlurEffect()
	{
		movingEarthImage.setEffect(boxBlurForNodes);
		titleImage.setEffect(boxBlurForNodes);
		titleLabel.setEffect(boxBlurForText);
		backButton.setEffect(boxBlurForNodes);
		tableView.setEffect(boxBlurForNodes);
		vBoxForPlayerStatistics.setEffect(boxBlurForNodes);
		deleteButton.setEffect(boxBlurForNodes);
		vBoxForControlButton.setEffect(boxBlurForNodes);
	}

	private void removeBlurEffect()
	{
		movingEarthImage.setEffect(null);
		titleImage.setEffect(dropShadow);
		titleLabel.setEffect(innerShadow);
		backButton.setEffect(dropShadow);
		tableView.setEffect(dropShadow);
		vBoxForPlayerStatistics.setEffect(dropShadow);
		deleteButton.setEffect(dropShadow);
		vBoxForControlButton.setEffect(dropShadow);
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
				if(playSound) getAudioStuff().playMaximizeSound();
				scaleTransitionForGridPane.playFromStart();
			});

			if(playSound) getAudioStuff().playMinimizeSound();
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
		backButton.getTooltip().setText(languageResourceBundle.getString("backButtonTooltipReturnToPreviousScreen"));

		//ICON TOOLTIPS
		soundButton.getTooltip().setText(languageResourceBundle.getString("soundOptionsTooltip"));

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
			deleteButton.setText(languageResourceBundle.getString("resetPlayerStatisticsButton"));
		}
		else
		{
			controlLabel.setText(languageResourceBundle.getString("playerStatistics"));
			deleteButton.setText(languageResourceBundle.getString("deleteAllSavedScoresButton"));
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

		cancelPopUpActionButton.setText(languageResourceBundle.getString("cancel"));
		confirmDeleteCheckBox.setText(languageResourceBundle.getString("confirmDeleteCheckBox"));
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
			setTextFill(WHITE);
			setFocusTraversable(false);
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
	
	public PopOver getPopOver()
	{
		return popOverForSelectedCategories;
	}
}
