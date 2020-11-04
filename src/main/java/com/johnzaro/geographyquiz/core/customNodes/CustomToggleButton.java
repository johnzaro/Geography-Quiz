package com.johnzaro.geographyquiz.core.customNodes;

import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;

import static com.johnzaro.geographyquiz.core.GlobalVariables.getAudioStuff;

public class CustomToggleButton extends ToggleButton
{
	public CustomToggleButton(boolean isSegmented, String styleClass, Node graphic, boolean addTooltip, boolean cache, CacheHint cacheHint)
	{
		setFocusTraversable(false);
		setCursor(Cursor.HAND);
		setGraphicTextGap(10);
		if(isSegmented) getStyleClass().add("segmentedToggleButton");
		if(styleClass != null) getStyleClass().add(styleClass);
		if(graphic != null) setGraphic(graphic);
		if(addTooltip) setTooltip(new CustomTooltip());
		setCache(cache);
		if(cache) setCacheHint(cacheHint);
		
		setOnMouseEntered(e -> getAudioStuff().playHoverSound());
		setOnMouseClicked(e -> getAudioStuff().playButtonClickSound());
	}
}
