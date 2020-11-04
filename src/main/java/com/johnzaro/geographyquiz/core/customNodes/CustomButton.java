package com.johnzaro.geographyquiz.core.customNodes;

import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;

public class CustomButton extends Button
{
	public static final Color COLOR = Color.valueOf("#7A301B");
	
	private ImageView graphic;
	
	public CustomButton(String text, Image image, ContentDisplay graphicPosition, TextAlignment textAlignment, Paint textFill, boolean isTransparent, boolean pickOnBounds, boolean addTooltip, boolean cache, CacheHint cacheHint)
	{
		setCursor(Cursor.HAND);
		setText(text);
		
		if(image != null)
			setImage(image);
		
		if(graphicPosition != null)
		{
			setContentDisplay(graphicPosition);
			setGraphicTextGap(10);
		}
		
		if(textAlignment != null)
			setTextAlignment(textAlignment);
		
		if(textFill != null)
			setTextFill(textFill);
		
		setCache(cache);
		if(cache)
			setCacheHint(cacheHint);
		
		if(isTransparent)
		{
			setId("transparent");
			setBackground(Background.EMPTY);
			setOnMouseEntered(e -> setUnderline(true));
			setOnMouseExited(e -> setUnderline(false));
		}
		else setId("normal");
		
		setPickOnBounds(pickOnBounds);
		
		if(addTooltip)
			setTooltip(new CustomTooltip());
		
		setOnMouseClicked(e -> getAudioStuff().playButtonClickSound());
	}
	
	public void setImage(Image image)
	{
		if(graphic == null) graphic = new CustomImageView(image, true, true, true, false, null);
		else graphic.setImage(image);
		
		setGraphic(graphic);
	}
	
	public void setFitWidth(double width)
	{
		graphic.setFitWidth(width);
	}
	
	public double getFitWidth()
	{
		return graphic.getFitWidth();
	}
	
	public void setFitHeight(double height)
	{
		graphic.setFitHeight(height);
	}
	
	public double getFitHeight()
	{
		return graphic.getFitHeight();
	}
	
	public Image getImage()
	{
		if(graphic != null && graphic.getImage() != null)
			return graphic.getImage();
		else return null;
	}
}
