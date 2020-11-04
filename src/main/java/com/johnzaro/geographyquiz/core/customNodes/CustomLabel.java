package com.johnzaro.geographyquiz.core.customNodes;

import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class CustomLabel extends Label
{
	public CustomLabel(Pos alignment, TextAlignment textAlignment, Color textFill, String styleClass,
					   Node graphic, ContentDisplay contentDisplay, boolean handCursor, boolean underline,
					   boolean wrapText, boolean addTooltip, boolean cache, CacheHint cacheHint)
	{
		if(alignment != null) setAlignment(alignment);
		if(textAlignment != null) setTextAlignment(textAlignment);
		if(textFill != null) setTextFill(textFill);
		if(styleClass != null) getStyleClass().add(styleClass);
		if(graphic != null) setGraphic(graphic);
		if(contentDisplay != null) setContentDisplay(contentDisplay);
		if(handCursor) setCursor(Cursor.HAND);
		setUnderline(underline);
		setWrapText(wrapText);
		if(addTooltip) setTooltip(new CustomTooltip());
		setCache(cache);
		if(cache) setCacheHint(cacheHint);
	}
}
