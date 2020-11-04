package com.johnzaro.geographyquiz.core.customNodes;

import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Skin;

/**
 * Created by John on 14/11/2016.
 */

public class CustomScrollPane extends ScrollPane
{
	protected Skin<?> createDefaultSkin()
	{
		return new CustomScrollPaneSkin(this);
	}
	
	public CustomScrollPane(boolean fitToHeight, boolean fitToWidth, boolean pannable, Cursor cursor, Node content, String styleClass, boolean cache, CacheHint cacheHint)
	{
		setFitToHeight(fitToHeight);
		setFitToWidth(fitToWidth);
		setPannable(pannable);
		if(cursor != null) setCursor(cursor);
		setContent(content);
		if(styleClass != null) getStyleClass().add(styleClass);
		setCache(cache);
		if(cache) setCacheHint(cacheHint);
	}
}
