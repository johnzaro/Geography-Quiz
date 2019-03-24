package com.johnzaro.geographyquiz.core;

import javafx.scene.Cursor;
import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;

/**
 * Created by John on 26/10/2016.
 */
public class CustomCheckBox extends CheckBox
{
	public CustomCheckBox()
	{
		setTextFill(Color.WHITE);
		setCursor(Cursor.HAND);
		setId("customCheckbox");
		setFocusTraversable(false);
	}
}
