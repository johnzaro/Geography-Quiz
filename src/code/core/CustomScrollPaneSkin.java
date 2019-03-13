package code.core;

import javafx.scene.control.skin.ScrollPaneSkin;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;

import java.lang.reflect.Field;

/**
 * Created by John on 14/11/2016.
 */
public class CustomScrollPaneSkin extends ScrollPaneSkin
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
	
	private StackPane viewRect;
	
	public CustomScrollPaneSkin(final ScrollPane scrollpane)
	{
		super(scrollpane);
		try
		{
			this.viewRect = (StackPane) viewRectField.get(this);
		}
		catch (ReflectiveOperationException e)
		{
			throw new RuntimeException(e);
		}
		this.viewRect.setCache(false);
	}
}
