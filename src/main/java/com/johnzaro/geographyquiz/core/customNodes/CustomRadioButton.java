package com.johnzaro.geographyquiz.core.customNodes;

import javafx.scene.Cursor;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;

import static com.johnzaro.geographyquiz.core.GlobalVariables.getAudioStuff;

public class CustomRadioButton extends RadioButton
{
	public CustomRadioButton(String text, boolean addSoundEffect)
	{
		setText(text);
		setFocusTraversable(false);
		setCursor(Cursor.HAND);
		setTextFill(Color.WHITE);
		
		if(addSoundEffect)
			setOnMouseClicked(e -> getAudioStuff().playRadioButtonSelectedSound());
	}
}
