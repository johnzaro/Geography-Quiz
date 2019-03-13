package code.core;

import javafx.scene.image.ImageView;

/**
 * Created by John on 20/12/2016.
 */
public class CustomImageView extends ImageView
{
	private String imagePath = "";
	
	public String getImagePath()
	{
		return imagePath;
	}
	
	public void setImagePath(String imagePath)
	{
		this.imagePath = imagePath;
	}
}
