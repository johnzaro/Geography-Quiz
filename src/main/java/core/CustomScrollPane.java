package core;

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
}
