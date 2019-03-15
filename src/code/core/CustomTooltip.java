package code.core;

import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class CustomTooltip extends Tooltip
{
	public CustomTooltip()
	{
		setShowDelay(Duration.millis(100));
		setShowDuration(Duration.INDEFINITE);
		setHideDelay(Duration.ZERO);
	}
}
