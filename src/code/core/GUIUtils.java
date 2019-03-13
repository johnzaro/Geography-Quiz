package code.core;

/**
 * Created by John on 14/3/2017.
 */

import code.screens.ErrorScreen;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.skin.TableViewSkin;

import java.lang.reflect.Method;

public class GUIUtils
{
	private static Method columnToFitMethod;
	
	static
	{
		try
		{
			columnToFitMethod = TableViewSkin.class.getDeclaredMethod("resizeColumnToFitContent", TableColumn.class, int.class);
			columnToFitMethod.setAccessible(true);
		}
		catch(Exception e)
		{
			Platform.runLater(() -> new ErrorScreen("Error occurred in GUIUtils", e));
		}
	}
	
	public static void autoFitTable(TableView tableView)
	{
		tableView.getItems().addListener((ListChangeListener<Object>) c ->
				{
					for (Object column : tableView.getColumns())
					{
						try
						{
							columnToFitMethod.invoke(tableView.getSkin(), column, -1);
						}
						catch (Exception e)
						{
							Platform.runLater(() -> new ErrorScreen("Error occurred in GUIUtils", e));
						}
					}
				});
	}
}
