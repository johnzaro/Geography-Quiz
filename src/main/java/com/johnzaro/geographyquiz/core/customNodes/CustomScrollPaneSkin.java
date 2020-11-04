package com.johnzaro.geographyquiz.core.customNodes;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.skin.ScrollPaneSkin;
import javafx.scene.layout.StackPane;

import java.lang.reflect.Field;

/**
 * Created by John on 14/11/2016.
 */

class CustomScrollPaneSkin extends ScrollPaneSkin
{
	private static Field viewRectField;
	
	static
	{
		try
		{
			viewRectField = ScrollPaneSkin.class.getDeclaredField("viewRect");
			viewRectField.setAccessible(true);
		}
		catch (ReflectiveOperationException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	CustomScrollPaneSkin(final ScrollPane scrollpane)
	{
		super(scrollpane);
		StackPane viewRect;
		try
		{
			viewRect = (StackPane) viewRectField.get(this);
		}
		catch (ReflectiveOperationException e)
		{
			throw new RuntimeException(e);
		}
		viewRect.setCache(false);
	}
}
