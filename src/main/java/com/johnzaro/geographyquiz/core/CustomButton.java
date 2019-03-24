package com.johnzaro.geographyquiz.core;

import javafx.scene.Cursor;
import javafx.scene.control.Button;

public class CustomButton extends Button
{
	public CustomButton()
	{
		setCursor(Cursor.HAND);
	}
	
	public CustomButton(String text)
	{
		setText(text);
		setCursor(Cursor.HAND);
	}
}
