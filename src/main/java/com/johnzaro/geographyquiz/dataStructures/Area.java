package com.johnzaro.geographyquiz.dataStructures;

import static com.johnzaro.geographyquiz.core.GlobalVariables.*;

public class Area
{
	private double areaInKilometers;
	private double areaInMiles;

	private int globalRanking;

	private double percentOfWater;

	private int coastlineInKilometers;
	private int coastlineInMiles;

	private int bordersInKilometers;
	private int bordersInMiles;
	
	public String getCoastlineBasedOnLocaleToString()
	{
		if(getCurrentPlayer().getUnitSystem() == UNIT_SYSTEM.METRIC) return numberFormatForUI.format(coastlineInKilometers) + "km";
		else return numberFormatForUI.format(coastlineInMiles) + "mi";
	}
	
	public String getBordersBasedOnLocaleToString()
	{
		if(getCurrentPlayer().getUnitSystem() == UNIT_SYSTEM.METRIC) return numberFormatForUI.format(bordersInKilometers) + "km";
		else return numberFormatForUI.format(bordersInMiles) + "mi";
	}
	
	public String getAreaBasedOnLocaleToString()
	{
		if(getCurrentPlayer().getUnitSystem() == UNIT_SYSTEM.METRIC) return numberFormatForUI.format(areaInKilometers) + "km\u00B2";
		else return numberFormatForUI.format(areaInMiles) + " sq mi";
	}

	public double getAreaInKilometers()
	{
		return areaInKilometers;
	}

	public void setAreaInKilometers(double areaInKilometers)
	{
		this.areaInKilometers = areaInKilometers;
	}

	public double getAreaInMiles()
	{
		return areaInMiles;
	}

	public void setAreaInMiles(double areaInMiles)
	{
		this.areaInMiles = areaInMiles;
	}

	public int getGlobalRanking()
	{
		return globalRanking;
	}

	public void setGlobalRanking(int globalRanking)
	{
		this.globalRanking = globalRanking;
	}

	public double getPercentOfWater()
	{
		return percentOfWater;
	}

	public void setPercentOfWater(double percentOfWater)
	{
		this.percentOfWater = percentOfWater;
	}

	public int getBordersInKilometers()
	{
		return bordersInKilometers;
	}

	public void setBordersInKilometers(int bordersInKilometers)
	{
		this.bordersInKilometers = bordersInKilometers;
	}

	public int getBordersInMiles()
	{
		return bordersInMiles;
	}

	public void setBordersInMiles(int bordersInMiles)
	{
		this.bordersInMiles = bordersInMiles;
	}

	public int getCoastlineInKilometers()
	{
		return coastlineInKilometers;
	}

	public void setCoastlineInKilometers(int coastlineInKilometers)
	{
		this.coastlineInKilometers = coastlineInKilometers;
	}

	public int getCoastlineInMiles()
	{
		return coastlineInMiles;
	}

	public void setCoastlineInMiles(int coastlineInMiles)
	{
		this.coastlineInMiles = coastlineInMiles;
	}
}
