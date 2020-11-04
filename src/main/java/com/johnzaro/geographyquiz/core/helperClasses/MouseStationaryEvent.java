package com.johnzaro.geographyquiz.core.helperClasses;

import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.input.InputEvent;

public abstract class MouseStationaryEvent extends InputEvent
{
	private MouseStationaryEvent(Object source, EventTarget target, EventType<? extends MouseStationaryEvent> type)
	{
		super(source, target, type);
	}

	public abstract Point2D getPosition();
}
