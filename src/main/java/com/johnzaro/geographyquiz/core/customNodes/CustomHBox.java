package com.johnzaro.geographyquiz.core.customNodes;

import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.layout.HBox;

public class CustomHBox extends HBox
{
	public CustomHBox(boolean fillHeight, Pos alignment, boolean cache, CacheHint cacheHint)
	{
		setFillHeight(fillHeight);
		if(alignment != null) setAlignment(alignment);
		setCache(cache);
		if(cache) setCacheHint(cacheHint);
	}
}
