package com.johnzaro.geographyquiz.core.customNodes;

import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;

public class CustomComboBox<T> extends ComboBox<T>
{
	public CustomComboBox()
	{
		setCursor(Cursor.HAND);
	}
}
