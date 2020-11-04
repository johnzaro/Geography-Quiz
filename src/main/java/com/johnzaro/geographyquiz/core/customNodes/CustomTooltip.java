package com.johnzaro.geographyquiz.core.customNodes;

import com.johnzaro.geographyquiz.core.GlobalVariables;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class CustomTooltip extends Tooltip
{
	public CustomTooltip()
	{
		textProperty().addListener((observable, oldValue, newValue) ->
		{
			if(newValue != null && !newValue.isEmpty()) setShowDelay(Duration.millis(100));
			else setShowDelay(Duration.INDEFINITE);
		});
		
//		maxWidthProperty().bind(GlobalVariables.stage.widthProperty().multiply(0.2604));
		
		setShowDuration(Duration.INDEFINITE);
		setHideDelay(Duration.ZERO);
		setWrapText(true);
		setHideOnEscape(false);
	}
}
