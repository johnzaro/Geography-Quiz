package com.johnzaro.geographyquiz.core.customNodes;

import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.layout.GridPane;

public class CustomGridPane extends GridPane
{
	public CustomGridPane(Pos alignment, String styleClass, boolean cache, CacheHint cacheHint)
	{
		if(alignment != null) setAlignment(alignment);
		if(styleClass != null) getStyleClass().add(styleClass);
		setCache(cache);
		if(cache) setCacheHint(cacheHint);
	}
}
