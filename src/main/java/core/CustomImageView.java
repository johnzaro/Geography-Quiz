package core;

import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by John on 20/12/2016.
 */
public class CustomImageView extends ImageView
{
	private String imagePath = "";
	
	public CustomImageView(boolean smooth, boolean preserveRatio, boolean pickOnBounds, boolean cache, CacheHint cacheHint)
	{
		setSmooth(smooth);
		setPreserveRatio(preserveRatio);
		setPickOnBounds(pickOnBounds);
		setCache(cache);
		if(cache && cacheHint != null) setCacheHint(cacheHint);
	}
	
	public CustomImageView(Image image, boolean smooth, boolean preserveRatio, boolean pickOnBounds, boolean cache, CacheHint cacheHint)
	{
		setImage(image);
		setSmooth(smooth);
		setPreserveRatio(preserveRatio);
		setPickOnBounds(pickOnBounds);
		setCache(cache);
		if(cache && cacheHint != null) setCacheHint(cacheHint);
	}
	
	public String getImagePath()
	{
		return imagePath;
	}
	
	public void setImagePath(String imagePath)
	{
		this.imagePath = imagePath;
	}
}
