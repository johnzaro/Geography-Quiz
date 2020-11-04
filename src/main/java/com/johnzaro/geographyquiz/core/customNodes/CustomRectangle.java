package com.johnzaro.geographyquiz.core.customNodes;

import javafx.scene.CacheHint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CustomRectangle extends Rectangle
{
	public CustomRectangle(boolean smooth, Color stroke, Color fill, boolean cache, CacheHint cacheHint)
	{
		setSmooth(smooth);
		if(stroke != null) setStroke(stroke);
		if(fill != null) setFill(fill);
		setCache(cache);
		if(cache) setCacheHint(cacheHint);
	}
}
