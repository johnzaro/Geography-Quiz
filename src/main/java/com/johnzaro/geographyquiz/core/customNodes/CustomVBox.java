package com.johnzaro.geographyquiz.core.customNodes;

import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.layout.VBox;

public class CustomVBox extends VBox
{
	public CustomVBox(boolean fillWidth, Pos alignment, String styleClass, boolean cache, CacheHint cacheHint)
	{
		setFillWidth(fillWidth);
		if(alignment != null) setAlignment(alignment);
		if(styleClass != null) getStyleClass().add(styleClass);
		setCache(cache);
		if(cache) setCacheHint(cacheHint);
	}
}
