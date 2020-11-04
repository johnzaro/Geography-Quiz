package com.johnzaro.geographyquiz.core.helperClasses;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import org.reactfx.EventStream;
import org.reactfx.util.Either;

import java.time.Duration;

import static javafx.scene.input.MouseEvent.MOUSE_MOVED;
import static org.reactfx.EventStreams.eventsOf;

public class MouseStationaryHelper
{
	private final EventStream<Either<Point2D, Void>> stationaryEvents;

	public MouseStationaryHelper(Node node, Duration delay)
	{
		EventStream<MouseEvent> mouseEvents = eventsOf(node, MouseEvent.ANY);
		EventStream<Point2D> stationaryPositions = mouseEvents.successionEnds(delay).filter(e -> e.getEventType() == MOUSE_MOVED).map(e -> new Point2D(e.getX(), e.getY()));
		EventStream<Void> stoppers = mouseEvents.supply((Void) null);
		stationaryEvents = stationaryPositions.or(stoppers).distinct();
	}

	public EventStream<Either<Point2D, Void>> events()
	{
		return stationaryEvents;
	}
}