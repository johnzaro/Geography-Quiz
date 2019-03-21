package core;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class SwitchButton extends Label
{
//	helper button inside the main button that is used to simulate the on-off switch
	private Button button;

//	variable to hold the button's state
	private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty();

//	Constructor for the button
	public SwitchButton()
	{
		setCursor(Cursor.HAND);

//		create the inside helper button
		button = new Button();
		button.setId("switch-button");
		button.setCursor(Cursor.HAND);
		button.setFont(Font.font("Comic Sans MS", 1));

//		add helper button as graphic to main button
		setGraphic(button);
	}

//	return the helper button
	public Button getButton()
	{
		return button;
	}

//	method to change button's state
	public void setValue(boolean value)
	{
//		update variable's value with the new one
		switchedOn.set(value);

		if(value)
		{
//			change button's label's text
			setText("ON ");
//			set style for the button
			setStyle("-fx-background-color: green;-fx-text-fill:white;-fx-background-radius: 8;");
//			set correct alignment for the helper button
			setContentDisplay(ContentDisplay.RIGHT);
			setAlignment(Pos.CENTER_RIGHT);
		}
		else
		{
//			change button's label's text
			setText("OFF");
//			set style for the button
			setStyle("-fx-background-color: rgb(127, 140, 141);-fx-text-fill:black;-fx-background-radius: 8;");
//			set correct alignment for the helper button
			setContentDisplay(ContentDisplay.LEFT);
			setAlignment(Pos.CENTER_LEFT);
		}
	}

//	get button's value
	public boolean getValue()
	{
		return switchedOn.get();
	}

//	update button's UI
	public void recalculateUI(double buttonWidth, double buttonHeight, double fontSize)
	{
		button.setPrefWidth(buttonWidth);
		button.setPrefHeight(buttonHeight);
		
		button.setMinWidth(buttonWidth);
		button.setMinHeight(buttonHeight);
		
		button.setMaxWidth(buttonWidth);
		button.setMaxHeight(buttonHeight);
		
		setFont(Font.font("Comic Sans MS", fontSize));
	}
}
