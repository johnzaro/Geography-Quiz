package com.johnzaro.geographyquiz.core.customNodes;

import javafx.scene.Cursor;
import javafx.scene.control.TextField;

public class CustomTextField extends TextField
{
	public CustomTextField()
	{
		setCursor(Cursor.TEXT);
		setTooltip(new CustomTooltip());
	}
}
